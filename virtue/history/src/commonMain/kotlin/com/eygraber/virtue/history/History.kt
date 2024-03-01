package com.eygraber.virtue.history

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.Serializable

private val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')

private fun randomString(length: Int): String =
  buildString(capacity = length) {
    var i = 0
    while(i < length) {
      append(allowedChars.random())
      i++
    }
  }

public interface History {
  @Serializable
  public data class Item(val index: Int, val payload: Payload) {
    @Serializable
    public data class Payload(
      val urlPath: String,
      val stateKey: String = randomString(length = 8),
    )
  }

  public val currentItem: Item

  public val canGoBack: Boolean
  public val canGoForward: Boolean

  public val updates: Flow<Item>

  public fun initialize()
  public fun destroy()

  public fun push(payload: Item.Payload): Item
  public fun update(payload: Item.Payload): Item
  public fun move(delta: Int)
  public fun moveBack()
  public fun moveForward()

  public fun onBackPressed()
}
