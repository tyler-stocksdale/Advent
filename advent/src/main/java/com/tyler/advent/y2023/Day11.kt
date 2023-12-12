package com.tyler.advent.y2023

import com.tyler.advent.addEmUp
import java.io.File
import kotlin.math.abs

object Day11 {
    private val inputFile = File("inputs/Day11.txt")

    private data class Galaxy(val row: Long, val col: Long) {
        fun distanceTo(galaxy: Galaxy) = abs(this.row - galaxy.row) + abs(this.col - galaxy.col)
    }

    private fun getSum(multiplier: Long): Long {
        val spaceRowIdxs = mutableListOf<Int>()
        val map = inputFile.readLines().mapIndexed { idx, line ->
            line.toList().also { row ->
                if (row.all { it == '.' }) spaceRowIdxs.add(idx)
            }
        }

        val spaceColIdxs = mutableListOf<Int>()
        (0 until map[0].size).forEach { idx ->
            val col = map.map { it[idx] }
            if (col.all { it == '.' }) spaceColIdxs.add(idx)
        }

        val galaxies = mutableListOf<Galaxy>()
        map.forEachIndexed { row, chars ->
            chars.forEachIndexed { col, char ->
                val extraRows = spaceRowIdxs.count { it < row } * (multiplier - 1)
                val extraColumns = spaceColIdxs.count { it < col } * (multiplier - 1)
                if (char == '#') galaxies.add(Galaxy(row = row + extraRows, col = col + extraColumns))
            }
        }

        val distances = (0 until galaxies.size - 1).map { idx ->
            ((idx + 1) until galaxies.size).map { otherIdx ->
                galaxies[idx].distanceTo(galaxies[otherIdx])
            }
        }.flatten()

        return distances.addEmUp()
    }

    fun problem1(): Long {
        return getSum(2) // 9723824
    }

    fun problem2(): Long {
        return getSum(1_000_000) // 731244261352
    }
}