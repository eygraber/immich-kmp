package app.immich.kmp.module.generator.utils

internal fun String.kebabCaseToCamelCase(upperCamelCase: Boolean): String {
  var shouldCapitalize = upperCamelCase
  val original = this
  return buildString {
    for(s in original) {
      if(s == '-') {
        shouldCapitalize = true
      }
      else {
        append(if(shouldCapitalize) s.uppercaseChar() else s)

        if(shouldCapitalize) shouldCapitalize = false
      }
    }
  }
}

internal fun String.camelCaseToKebabCase() = fold(StringBuilder(length)) { acc, c ->
  if(c in 'A'..'Z') {
    (if(acc.isNotEmpty()) acc.append('-') else acc).append(c + ('a' - 'A'))
  }
  else {
    acc.append(c)
  }
}.toString()

internal fun String.camelCaseToDotCase() = fold(StringBuilder(length)) { acc, c ->
  if(c in 'A'..'Z') {
    (if(acc.isNotEmpty()) acc.append('.') else acc).append(c + ('a' - 'A'))
  }
  else {
    acc.append(c)
  }
}.toString()
