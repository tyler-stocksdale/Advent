package com.tyler.advent.y2023

import com.tyler.advent.addEmUp
import java.io.File

object Day3 {
    private val inputFile = File("inputs/Day3.txt")

    private data class Number(
        val num: Int,
        val row: Int,
        val col: Int,
        val numDigits: Int,
    )

    private data class Symbol(
        val char: Char,
        val row: Int,
        val col: Int,
    )

    private fun Char?.isSymbol() = this != null && this != '.' && !isDigit()

    private fun List<String>.isSymbolOnLeft(row: Int, col: Int): Boolean {
        return getOrNull(row)?.getOrNull(col - 1).isSymbol().also { if (it) print("found symbol on left") }
    }

    private fun List<String>.isSymbolOnRight(row: Int, col: Int, numDigits: Int): Boolean {
        return getOrNull(row)?.getOrNull(col + numDigits).isSymbol().also { if (it) print("found symbol on right") }
    }

    private fun List<String>.isSymbolOnDiagonals(row: Int, col: Int, numDigits: Int): Boolean {
        return isSymbolOnLeft(row - 1, col).also { if (it) print(" top") } // top left
                || isSymbolOnRight(row - 1, col, numDigits).also { if (it) print(" top") } // top right
                || isSymbolOnLeft(row + 1, col).also { if (it) print(" bottom") } // bottom left
                || isSymbolOnRight(row + 1, col, numDigits).also { if (it) print(" bottom") } // bottom right
    }

    private fun List<String>.isSymbolOnTop(row: Int, col: Int, numDigits: Int): Boolean {
        var isSymbol = false
        repeat(numDigits) { digitNum ->
            isSymbol = isSymbol || getOrNull(row - 1)?.getOrNull(col + digitNum).isSymbol()
        }
        return isSymbol.also { if (it) print("found symbol on top") }
    }

    private fun List<String>.isSymbolOnBottom(row: Int, col: Int, numDigits: Int): Boolean {
        var isSymbol = false
        repeat(numDigits) { digitNum ->
            isSymbol = isSymbol || getOrNull(row + 1)?.getOrNull(col + digitNum).isSymbol()
        }
        return isSymbol.also { if (it) print("found symbol on bottom") }
    }

    private fun List<String>.findNumbersAndSymbols() = run {
        val numbers = mutableListOf<Number>()
        val symbols = mutableListOf<Symbol>()
        mapIndexed { row, line ->
            var currentNumStr = ""
            var currentNumStartIdx = -1
            line.forEachIndexed { col, char ->
                if (char.isDigit()) {
                    currentNumStr += char
                    if (currentNumStartIdx == -1) currentNumStartIdx = col

                    if (col == line.length - 1 && currentNumStr.isNotEmpty()) {
                        val numDigits = currentNumStr.length
                        numbers.add(
                            Number(
                                num = currentNumStr.toInt(),
                                row = row,
                                col = col - numDigits + 1,
                                numDigits = numDigits
                            )
                        )
                        currentNumStr = ""
                        currentNumStartIdx = -1
                    }
                } else if (!char.isDigit() && currentNumStr.isNotEmpty()) {
                    val numDigits = currentNumStr.length
                    numbers.add(
                        Number(num = currentNumStr.toInt(), row = row, col = col - numDigits, numDigits = numDigits)
                    )
                    currentNumStr = ""
                    currentNumStartIdx = -1
                }

                if (char.isSymbol()) {
                    symbols.add(Symbol(char = char, row = row, col = col))
                }
            }
        }

        numbers to symbols
    }

    fun problem1(): Int {
        val lines = inputFile.readLines()
        val partNumbers = mutableListOf<Int>()
        val (numbers, sybmols) = lines.findNumbersAndSymbols()

        numbers.forEach { num ->
            print("$num, ")
            val isPartNumber = lines.isSymbolOnLeft(num.row, num.col)
                    || lines.isSymbolOnRight(num.row, num.col, num.numDigits)
                    || lines.isSymbolOnDiagonals(num.row, num.col, num.numDigits)
                    || lines.isSymbolOnTop(num.row, num.col, num.numDigits)
                    || lines.isSymbolOnBottom(num.row, num.col, num.numDigits)
            if (isPartNumber) {
                print(", adding ${num.num} to part numbers ${partNumbers.addEmUp()} -> ")
                partNumbers.add(num.num)
                print(partNumbers.addEmUp())
            }
            println()
        }

        return partNumbers.addEmUp()
    }

    fun problem2(): Int {
        val lines = inputFile.readLines()
        val gearRatios = mutableListOf<Int>()
        val (numbers, symbols) = lines.findNumbersAndSymbols()

        symbols.filter { it.char == '*' }.forEach { symbol ->
            print("$symbol, ")
            val adjacentNumbers = numbers.filter { num ->
                num.row >= symbol.row - 1
                        && num.row <= symbol.row + 1
                        && num.col >= symbol.col - num.numDigits
                        && num.col <= symbol.col + 1
            }.distinct()
            print("adjacent:$adjacentNumbers, ")
            if (adjacentNumbers.size == 2) {
                print("adding ${adjacentNumbers[0].num * adjacentNumbers[1].num} + ${gearRatios.addEmUp()} = ")
                gearRatios.add(adjacentNumbers[0].num * adjacentNumbers[1].num)
                print(gearRatios.addEmUp())
            }
            println()
        }

        return gearRatios.addEmUp() // 75220503
    }
}