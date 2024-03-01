package com.eygraber.virtue.history

import com.eygraber.virtue.di.scopes.SessionSingleton
import com.eygraber.virtue.session.state.VirtueSessionStateManager
import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.update
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
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
    val currentItem get() = items[current]

    inline fun mutate(mutate: Timeline.() -> Timeline): Timeline = this.mutate()
  }

  internal val isRestored: Boolean = TIMELINE_KEY in sessionStateManager

  private val timeline = atomic(loadInitialTimeline())
  private val timelineFlow = MutableStateFlow(timeline.value)

  override val currentItem: History.Item get() = timeline.value.currentItem

  override val canGoBack: Boolean get() = timeline.value.current > 0
  override val canGoForward: Boolean get() = timeline.value.current < timeline.value.lastIndex

  override val updates: Flow<History.Item> = timelineFlow.map { it.currentItem }

  override fun initialize() {}

  override fun destroy() {}

  override fun push(payload: History.Item.Payload): History.Item {
    mutateTimeline {
      val newCurrent = current + 1

      val newItem = History.Item(
        index = current + 1,
        payload = payload,
      )

      if(current == items.lastIndex) {
        copy(
          items = items + newItem,
          current = newCurrent,
        )
      }
      else {
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

    return timeline.value.currentItem
  }

  override fun update(payload: History.Item.Payload): History.Item {
    mutateTimeline {
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

    return timeline.value.currentItem
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
    mutateTimeline {
      copy(
        current = (current - 1).coerceAtLeast(0),
      )
    }
  }

  override fun moveForward() {
    mutateTimeline {
      copy(
        current = (current + 1).coerceAtMost(items.lastIndex),
      )
    }
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
        items = listOf(History.Item(index = 0, payload = History.Item.Payload(urlPath = "/"))),
        current = 0,
      )

  private inline fun mutateTimeline(mutate: Timeline.() -> Timeline) {
    val current = timeline.value
    timeline.update { prev ->
      prev.mutate(mutate)
    }
    val new = timeline.value

    if(current != new) {
      timelineFlow.value = new
      storeTimeline()
    }
  }

  private fun storeTimeline() {
    sessionStateManager.putString(
      key = TIMELINE_KEY,
      value = Json.encodeToString(timeline.value),
    )
  }
}

private const val TIMELINE_KEY = "com.eygraber.virtue.history.TimelineHistory.timeline"
