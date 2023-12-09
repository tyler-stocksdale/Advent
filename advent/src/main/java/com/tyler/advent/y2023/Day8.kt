package com.tyler.advent.y2023

import com.tyler.advent.lcm
import java.io.File

object Day8 {
    private val inputFile = File("inputs/Day8.txt")

    private val instructions = inputFile.readLines()[0].map {
        if (it == 'R') 1 else 0
    }

    private val map = mutableMapOf<String, List<String>>().apply {
        inputFile.readLines().drop(2).map {
            val (node, left, right) = it.split(" = (", ", ", ")")
            this[node] = listOf(left, right)
        }
    }

    private fun String.getStepsWhile(condition: (loc: String) -> Boolean): Int {
        var idx = 0
        var steps = 0
        var loc = this
        while (condition(loc)) {
            loc = map[loc]?.get(instructions[idx])!!
            idx = (idx + 1) % instructions.size
            steps++
        }
        return steps
    }

    fun problem1(): Int {
        return "AAA".getStepsWhile { it != "ZZZ" } // 18727
    }

    fun problem2(): Long {
        val steps = map.keys.filter { it.last() == 'A' }.map { loc ->
            loc.getStepsWhile { it.last() != 'Z' }
        }.map { it.toLong() }

        return steps.lcm() // 18,024,643,846,273
    }
}