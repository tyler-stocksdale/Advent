package com.tyler.advent.y2023

import com.tyler.advent.addEmUp
import java.io.File

object Day9 {
    private val inputFile = File("inputs/Day9.txt")
    private val historyList = inputFile.readLines().map { line ->
        line.split(" ").map { it.toInt() }
    }

    private fun List<Int>.findDifferences() = (0 until size - 1)
        .map { idx -> this[idx + 1] - this[idx] }.toMutableList()

    fun problem1(): Int {
        val answer = historyList.map { history ->
            history.extrapolateNextValue()
        }.addEmUp()

        return answer // 1882395907
    }

    private fun List<Int>.extrapolateNextValue(): Int {
        return last() + getDifferences().last()
    }

    private fun List<Int>.getDifferences(): List<Int> {
        return findDifferences().apply {
            add(if (all { it == 0 }) 0 else extrapolateNextValue())
        }
    }

    fun problem2(): Int {
        val answer = historyList.map { history ->
            history.extrapolateFirstValue()
        }.addEmUp()

        return answer // 1005
    }

    private fun List<Int>.extrapolateFirstValue(): Int {
        return first() - getFirstDifferences().first()
    }

    private fun List<Int>.getFirstDifferences(): List<Int> {
        return findDifferences().apply {
            add(index = 0, if (all { it == 0 }) 0 else extrapolateFirstValue())
        }
    }
}