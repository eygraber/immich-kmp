package com.eygraber.virtue.history

import com.eygraber.uri.Uri
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

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
      @Serializable(with = UriSerializer::class) val uri: Uri,
      val stateKey: String = randomString(length = 8),
    )
  }

  public val currentItem: Item?

  public val canGoBack: Boolean
  public val canGoForward: Boolean

  public val changes: Flow<Item>

  public fun initialize(initialUri: Uri)
  public fun destroy()

  public fun push(payload: Item.Payload): Item
  public fun update(payload: Item.Payload): Item
  public fun move(delta: Int)
  public fun moveBack()
  public fun moveForward()

  public fun onBackPressed()
}

private object UriSerializer : KSerializer<Uri> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("Uri", PrimitiveKind.STRING)

  override fun serialize(encoder: Encoder, value: Uri) {
    encoder.encodeString(value.toString())
  }

  override fun deserialize(decoder: Decoder): Uri = Uri.parse(decoder.decodeString())
}
