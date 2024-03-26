plugins {
  id("com.eygraber.conventions-kotlin-multiplatform")
  id("com.eygraber.conventions-detekt")
}

kotlin {
  kmpTargets(
    KmpTarget.Android,
    KmpTarget.Ios,
    KmpTarget.Js,
    KmpTarget.Jvm,
    KmpTarget.WasmJs,
    project = project,
  )
}
