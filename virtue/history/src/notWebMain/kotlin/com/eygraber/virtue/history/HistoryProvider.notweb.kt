package com.eygraber.virtue.history

import me.tatarka.inject.annotations.Provides

public actual interface HistoryProvider {
  @Provides public fun TimelineHistory.bind(): History = this
}
