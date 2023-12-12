package com.tyler.advent.y2023

import com.tyler.advent.addEmUp
import java.io.File

object Day12 {
    private val inputFile = File("inputs/Day12.txt")

    private val table = mutableMapOf<String, Long>()
    private fun String.memoize(block: () -> Long): Long {
        return table[this] ?: block().also { table[this] = it }
    }

    private fun String.countPossibilities(counts: List<Int>): Long {
        val input = "$this | $counts"
        return input.memoize {
            when (this.firstOrNull()) {
                '.' -> this.drop(1).countPossibilities(counts)
                '?' -> {
                    this.replaceFirst('?', '.').countPossibilities(counts) +
                            this.replaceFirst('?', '#').countPossibilities(counts)
                }

                '#' -> {
                    val firstCount = counts.firstOrNull()
                    if (firstCount == null) {
                        0
                    } else if (this.length >= firstCount && this.take(firstCount).none { it == '.' }) {
                        var nextStr = this.drop(firstCount)
                        val nextCount = counts.drop(1)

                        if (nextStr.firstOrNull() == '?') nextStr = nextStr.replaceFirst('?', '.')

                        if (nextStr.firstOrNull() == '#') 0 else nextStr.countPossibilities(nextCount)
                    } else {
                        0
                    }
                }

                null -> if (counts.isEmpty()) 1 else 0
                else -> throw Exception("bad input")
            }.toLong()
        }
    }

    private fun solve(copies: Int = 1): Long {
        val answer = inputFile.readLines().map { line ->
            val (springsStr, countsStr) = line.split(" ")
            val counts = countsStr.split(",").map { it.toInt() }

            val springsStr5 = List(copies) { springsStr }.joinToString("?")
            val counts5 = List(copies) { counts }.flatten()

            springsStr5.countPossibilities(counts5)
        }

        return answer.addEmUp()
    }

    fun problem1(): Long {
        return solve() // 8419
    }

    fun problem2(): Long {
        return solve(copies = 5) // 160500973317706
    }
}