package com.eygraber.virtue.history

import com.eygraber.uri.Uri
import com.eygraber.virtue.back.press.dispatch.OnBackPressedDispatcher
import com.eygraber.virtue.di.scopes.SessionSingleton
import kotlinx.coroutines.flow.Flow
import me.tatarka.inject.annotations.Inject
import org.w3c.dom.PopStateEvent
import org.w3c.dom.events.Event

public external interface HistoryState : JsAny {
  public val index: Int
  public val state: String
}

@Suppress("UNUSED_PARAMETER")
public fun historyState(index: Int, stateKey: String?): JsAny = js("({ index: index, stateKey: stateKey })")

@SessionSingleton
@Inject
public class WasmHistory(
  private val browserHistory: org.w3c.dom.History,
  private val browserWindow: org.w3c.dom.Window,
  private val history: TimelineHistory,
  private val backPressDispatcher: OnBackPressedDispatcher,
) : History {
  override val currentItem: History.Item? get() = history.currentItem
  override val canGoBack: Boolean get() = history.canGoBack
  override val canGoForward: Boolean get() = history.canGoForward

  override val changes: Flow<History.Item> = history.changes

  private val popStateListener: (Event) -> Unit = { event ->
    val state = (event as PopStateEvent).state?.unsafeCast<HistoryState>()
    if(state != null) {
      if(isBackPress(state)) {
        // if someone is going to intercept the back press (ignoring the session handler)
        // then we move the browser back to where it was before the back press (the url might flash)
        // it will cause another popstate to fire but this should be a no-op
        // since at that point the browser history and timeline history should be the same
        if(backPressDispatcher.hasEnabledCallbacks()) {
          browserHistory.go(1)
        }

        backPressDispatcher.onBackPressed()
      }
      else {
        handlePopStateEvent(state)
      }
    }
  }

  override fun initialize(initialUri: Uri) {
    browserWindow.addEventListener("popstate", popStateListener)

    history.initialize(initialUri)
  }

  override fun destroy() {
    browserWindow.removeEventListener("popstate", popStateListener)
    history.destroy()
  }

  override fun push(payload: History.Item.Payload): History.Item =
    history.push(payload).apply {
      browserHistory.pushState(historyState(index, payload.stateKey), "", payload.uri.toString())
    }

  override fun update(payload: History.Item.Payload): History.Item =
    history.update(payload).apply {
      browserHistory.replaceState(historyState(index, payload.stateKey), "", payload.uri.toString())
    }

  override fun move(delta: Int) {
    history.move(delta)
    browserHistory.go(delta)
  }

  override fun moveBack() {
    history.moveBack()
    browserHistory.back()
  }

  override fun moveForward() {
    history.moveForward()
    browserHistory.forward()
  }

  override fun onBackPressed() {
    // don't use browserHistory because this event came from the browser
    history.onBackPressed()
  }

  private fun handlePopStateEvent(eventState: HistoryState) {
    val current = currentItem?.index

    if(current != null) {
      val delta = eventState.index - current

      // don't use browserHistory, because this event came from the browser (would cause an infinite loop)
      history.move(delta)
    }
  }

  private fun isBackPress(eventState: HistoryState): Boolean {
    val currentItem = history.currentItem ?: return false
    return eventState.index == currentItem.index - 1
  }
}
