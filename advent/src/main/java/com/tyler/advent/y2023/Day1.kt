package com.tyler.advent.y2023

import java.io.File

object Day1 {
    private val inputFile = File("inputs/Day1.txt")

    fun problem1(): Int {
        val answer = inputFile.readLines().map { line ->
            val firstNum = line.first { it.isDigit() }
            val lastNum = line.last { it.isDigit() }
            "$firstNum$lastNum".toInt()
        }.reduce { acc, i -> acc + i }
        return answer
    }

    fun problem2():Int {
        val digitsStr = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val digits = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        val answer = inputFile.readLines().map { line ->
            val firstNum = (digitsStr.mapIndexed { idx, it -> idx to line.indexOf(it) } +
                    digits.mapIndexed { idx, it -> idx to line.indexOf(it) })
                .filter { it.second >= 0 }
                .minBy { it.second }
                .first

            val lastNum = (digitsStr.mapIndexed { idx, it -> idx to line.lastIndexOf(it) } +
                    digits.mapIndexed { idx, it -> idx to line.lastIndexOf(it) })
                .filter { it.second >= 0 }
                .maxBy { it.second }
                .first

            "$firstNum$lastNum".toInt()
        }.reduce { acc, i -> acc + i }
        return answer
    }
}