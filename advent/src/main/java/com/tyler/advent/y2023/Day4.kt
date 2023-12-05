package com.tyler.advent.y2023

import com.tyler.advent.addEmUp
import java.io.File
import kotlin.math.pow

object Day4 {
    private val inputFile = File("inputs/Day4.txt")

    private fun getNumMatchesList() = inputFile.readLines().map { line ->
            val (winningNums, myNums) = line.substringAfter(": ")
                .split(" | ")
                .map { listOfNumsStr -> listOfNumsStr.split(" ") }
                .map { listOfNums -> listOfNums.mapNotNull { numStr -> numStr.trim().toIntOrNull() } }

            myNums.filter { winningNums.contains(it) }.size
        }

    fun problem1(): Int {
        return getNumMatchesList()
            .map { numMatches -> 1 * 2.0.pow(numMatches - 1.0).toInt() }
            .addEmUp() // 24706
    }

    fun problem2(): Int {
        val numMatchesList = getNumMatchesList()
        val instances = MutableList(numMatchesList.size) { 1 }
        numMatchesList.forEachIndexed { idx, numMatches ->
            repeat(numMatches) { match ->
                repeat(instances[idx]) {
                    instances[idx + match + 1]++
                }
            }
        }
        return instances.addEmUp() // 13114317
    }
}