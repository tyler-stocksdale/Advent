package com.tyler.advent.y2023

import com.tyler.advent.addEmUp
import java.io.File

object Day2 {
    private val inputFile = File("inputs/Day2.txt")

    private enum class Color(val limit: Int, val str: String) {
        Red(12, "red"),
        Green(13, "green"),
        Blue(14, "blue"),
        ;
    }

    private fun String.toColor() = Color.values().find { it.str == this } ?: throw Exception("input problem")

    fun problem1(): Int {
        val answer = inputFile.readLines().mapNotNull { line ->
            val (id, gamesStr) = line.split(": ")
            var possible = true
            gamesStr.split("; ").forEach { game ->
                game.split(", ").forEach { cubeInfo ->
                    val (numberStr, colorStr) = cubeInfo.split(" ")
                    if (numberStr.toInt() > colorStr.toColor().limit) possible = false
                }
            }
            if (possible) id.substringAfter(" ").toInt() else null
        }.addEmUp()
        return answer // 2265
    }

    fun problem2(): Int {
        val answer = inputFile.readLines().map { line ->
            val gamesStr = line.substringAfter(": ")
            var fewestRed = 0
            var fewestGreen = 0
            var fewestBlue = 0

            gamesStr.split("; ").forEach { game ->
                game.split(", ").forEach { cubeInfo ->
                    val (numberStr, colorStr) = cubeInfo.split(" ")
                    val number = numberStr.toInt()
                    when (colorStr.toColor()) {
                        Color.Red -> if (number > fewestRed) fewestRed = number
                        Color.Green -> if (number > fewestGreen) fewestGreen = number
                        Color.Blue -> if (number > fewestBlue) fewestBlue = number
                    }
                }
            }
            fewestRed * fewestGreen * fewestBlue
        }.addEmUp()
        return answer // 64097
    }
}