package com.tyler.advent.y2023

import com.tyler.advent.multiply
import java.io.File

object Day6 {
    private val inputFile = File("inputs/Day6.txt")

    private fun calculateWins(raceTime: Long, recordDistance: Long): Long {
        var wins = 0L
        (0 until raceTime).forEach { timeHeld ->
            val distance = timeHeld * (raceTime - timeHeld)
            if (distance > recordDistance) wins++
        }
        return wins
    }

    fun problem1(): Long {
        val (raceTimes, recordDistances) = inputFile.readLines().map { line ->
            line.split(" ").drop(1).map { it.trim() }.filter { it.isNotEmpty() }.map { it.toLong() }
        }

        val numWinners = raceTimes.mapIndexed { idx, raceTime ->
            calculateWins(raceTime, recordDistances[idx])
        }
        return numWinners.multiply() // 220320
    }

    fun problem2(): Long {
        val (raceTime, recordDistance) = inputFile.readLines().map { line ->
            line.split(" ").drop(1).joinToString("") { it.trim() }.toLong()
        }

        return calculateWins(raceTime, recordDistance) // 34454850
    }
}