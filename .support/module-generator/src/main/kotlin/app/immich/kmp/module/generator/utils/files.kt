package app.immich.kmp.module.generator.utils

import java.io.File

internal infix operator fun String.div(other: String) = "$this${File.separator}$other"

internal fun File.insert(
  newLine: String,
  intoAlphabetizedSectionWithPrefix: String,
): Boolean {
  val fileText = readText()

  val fileLines = fileText.lines()

  val matchingLines = fileLines.filter { line ->
    line.startsWith(intoAlphabetizedSectionWithPrefix)
  }

  val insertionPoint =
    matchingLines
      .binarySearch(newLine)
      .takeIf {
        // if it's >= 0 then the newAddition is
        // already present so there's no need to insert
        it < 0
      }
      ?.let { insertionPoint ->
        -(insertionPoint + 1)
      }
      ?: return false

  val insertionIndex = if(insertionPoint < matchingLines.size) {
    val currentLineAtInsertionPoint = matchingLines[insertionPoint]
    fileText.indexOf(currentLineAtInsertionPoint)
  }
  else if(matchingLines.isEmpty()) {
    val fallback = "dependencies {"
    fileText.indexOf(fallback) + fallback.length
  }
  else {
    val currentLineAtInsertionPoint = matchingLines.last()
    fileText.indexOf(currentLineAtInsertionPoint) + currentLineAtInsertionPoint.length
  }

  val insertion = if(insertionPoint < matchingLines.size) {
    "$newLine${System.lineSeparator()}"
  }
  else {
    "${System.lineSeparator()}$newLine${System.lineSeparator()}"
  }

  writeText(
    buildString(fileText.length + newLine.length) {
      append(fileText)
      insert(insertionIndex, insertion)
    },
  )

  return true
}

internal fun File.insertMultiline(
  newLine: String,
  alphabetizedSectionExtractor: (String) -> String,
  lastLineSuffixResolver: String,
  vararg intoAlphabetizedSectionWithPrefix: String,
): Boolean {
  val fileText = readText()

  val fileLines = fileText.lines()

  val matchingLines =
    fileLines
      .filter { line ->
        intoAlphabetizedSectionWithPrefix.any { line.startsWith(it) }
      }

  val insertionPoint =
    matchingLines
      .map { line ->
        alphabetizedSectionExtractor(
          intoAlphabetizedSectionWithPrefix
            .fold(line) { lineToRemovePrefixFrom, prefix ->
              lineToRemovePrefixFrom.removePrefix(prefix)
            },
        )
      }
      .binarySearch(
        alphabetizedSectionExtractor(
          intoAlphabetizedSectionWithPrefix
            .fold(newLine) { lineToRemovePrefixFrom, prefix ->
              lineToRemovePrefixFrom.removePrefix(prefix)
            },
        ),
      )
      .takeIf {
        // if it's >= 0 then the newAddition is
        // already present so there's no need to insert
        it < 0
      }
      ?.let { insertionPoint ->
        -(insertionPoint + 1)
      }
      ?: return false

  val insertionIndex = if(insertionPoint < matchingLines.size) {
    val currentLineAtInsertionPoint = matchingLines[insertionPoint]
    fileText.indexOf(currentLineAtInsertionPoint)
  }
  else {
    val currentLineAtInsertionPoint = matchingLines.last()
    val currentLineIndex = fileText.indexOf(currentLineAtInsertionPoint)
    val currentLineUntilEOF = fileText.substring(currentLineIndex)
    currentLineIndex +
      currentLineUntilEOF.indexOf(lastLineSuffixResolver) +
      lastLineSuffixResolver.length
  }

  val insertion = if(insertionPoint < matchingLines.size) {
    "$newLine${System.lineSeparator()}${System.lineSeparator()}"
  }
  else {
    "${System.lineSeparator()}${System.lineSeparator()}$newLine"
  }

  writeText(
    buildString(fileText.length + newLine.length) {
      append(fileText)
      insert(insertionIndex, insertion)
    },
  )
  return true
}
