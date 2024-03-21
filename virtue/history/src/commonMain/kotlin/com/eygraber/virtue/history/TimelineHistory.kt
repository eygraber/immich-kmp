package com.eygraber.virtue.history

import com.eygraber.uri.Uri
import com.eygraber.virtue.di.scopes.SessionSingleton
import com.eygraber.virtue.session.state.VirtueSessionStateManager
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.tatarka.inject.annotations.Inject

@SessionSingleton
@Inject
public class TimelineHistory(
  private val sessionStateManager: VirtueSessionStateManager,
) : History {
  @Serializable
  private data class Timeline(
    val items: List<History.Item>,
    val current: Int,
  ) : List<History.Item> by items {
    val currentItem get() = if(current == -1) null else items[current]

    inline fun mutate(mutate: Timeline.() -> Timeline): Timeline = this.mutate()
  }

  private val timeline = atomic(loadInitialTimeline())
  private val timelineFlow = MutableStateFlow(timeline.value)

  override val currentItem: History.Item? get() = timeline.value.currentItem

  override val canGoBack: Boolean get() = timeline.value.current > 0
  override val canGoForward: Boolean get() = timeline.value.current < timeline.value.lastIndex

  override val changes: Flow<History.Item> = timelineFlow.filter { it.isNotEmpty() }.mapNotNull { it.currentItem }

  override fun initialize(initialUri: Uri) {
    val restoredCurrentItem = currentItem
    // if currentItem == null, there was no restoration, so push initialUri
    // if currentItem == initialUri, the app was recreated and we don't need to push
    // if currentItem != initialUri, the user navigated within the session outside of the app so we push it
    // e.g. they changed the browser url which is considered the same session but a new invocation of the app
    if(restoredCurrentItem == null || restoredCurrentItem.payload.uri != initialUri) {
      push(History.Item.Payload(initialUri))
    }
  }

  override fun destroy() {}

  override fun push(payload: History.Item.Payload): History.Item {
    val newTimeline = mutateTimeline {
      val newCurrent = current + 1

      val newItem = History.Item(
        index = current + 1,
        payload = payload,
      )

      if(current == -1 || current == items.lastIndex) {
        copy(
          items = items + newItem,
          current = newCurrent,
        )
      }
      else {
        val currentItem = requireNotNull(currentItem) {
          "currentItem shouldn't be able to be null"
        }

        // pushing prunes the timeline and we start a new future
        // we also remove the session state for the pruned history items
        sessionStateManager.remove(currentItem.payload.stateKey)

        val newItems = List(newCurrent + 1) { index ->
          if(index < newCurrent) {
            items[index]
          }
          else {
            newItem
          }
        }

        items.drop(current).forEach { removedItem ->
          sessionStateManager.remove(removedItem.payload.stateKey)
        }

        copy(
          items = newItems,
          current = newCurrent,
        )
      }
    }

    return requireNotNull(newTimeline.currentItem) {
      "currentItem shouldn't be able to be null"
    }
  }

  override fun update(payload: History.Item.Payload): History.Item {
    val updatedTimeline = mutateTimeline {
      copy(
        items = items.mapIndexed { index, item ->
          if(index != current) {
            item
          }
          else {
            item.copy(payload = payload)
          }
        },
      )
    }

    return requireNotNull(updatedTimeline.currentItem) {
      "currentItem shouldn't be able to be null"
    }
  }

  override fun move(delta: Int) {
    mutateTimeline {
      when(val moveToIndex = current + delta) {
        in 0..items.lastIndex -> copy(
          current = moveToIndex,
        )

        else -> this
      }
    }
  }

  override fun moveBack() {
    move(-1)
  }

  override fun moveForward() {
    move(1)
  }

  override fun onBackPressed() {
    moveBack()
  }

  private fun loadInitialTimeline() =
    sessionStateManager
      .getString(TIMELINE_KEY)
      ?.let { storedTimeline ->
        runCatching<Timeline> { Json.decodeFromString(storedTimeline) }.getOrNull()
      }
      ?: Timeline(
        items = emptyList(),
        current = -1,
      )

  private inline fun mutateTimeline(mutate: Timeline.() -> Timeline): Timeline {
    val current = timeline.value
    timeline.update { prev ->
      prev.mutate(mutate)
    }
    val new = timeline.value

    if(current != new) {
      timelineFlow.value = new
      storeTimeline()
    }

    return new
  }

  private fun storeTimeline() {
    sessionStateManager.putString(
      key = TIMELINE_KEY,
      value = Json.encodeToString(timeline.value),
    )
  }
}

private const val TIMELINE_KEY = "com.eygraber.virtue.history.TimelineHistory.timeline"
