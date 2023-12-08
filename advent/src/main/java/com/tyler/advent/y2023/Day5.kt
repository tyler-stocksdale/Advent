package com.tyler.advent.y2023

import com.tyler.advent.addEmUp
import java.io.File

object Day5 {
    private val inputFile = File("inputs/Day5.txt")

    private data class Map(
        val dest: Long,
        val source: Long,
        val range: Long,
    )

    private val firstRow = inputFile.readLines()[0]
    private val chunks = inputFile.readText().split("\n\n").drop(1).map { it.readMap() }

    private fun String.readMap() = split(":\n")[1].split("\n").map {
        val (dest, source, range) = it.split(" ").map { it.toLong() }
        Map(dest, source, range)
    }

    private fun List<List<Map>>.convertToLocation(seed: Long) =
        this[6].convert(
            this[5].convert(
                this[4].convert(
                    this[3].convert(
                        this[2].convert(
                            this[1].convert(
                                this[0].convert(seed)
                            )
                        )
                    )
                )
            )
        )

    private fun List<Map>.convert(source: Long) = map { map ->
        if (source >= map.source && source < map.source + map.range) {
            map.dest + source - map.source
        } else null
    }.find { it != null } ?: source

    fun problem1(): Int {
        val seeds = firstRow.split(": ")[1].split(" ").map { it.toLong() }
        val answer = seeds.minOf { seed -> chunks.convertToLocation(seed) }
        return answer.toInt() // 346433842
    }

    fun problem2(): Int {
        val seedRanges = firstRow.split(": ")[1].split(" ").map { it.toLong() }
        val seeds = (seedRanges.indices step 2).map { idx ->
            val seed = seedRanges[idx]
            val range = seedRanges[idx + 1]
            seed to range
        }

        var iter = 0L
        val total = seeds.map { it.second }.addEmUp() / 1_000_000

        val answer = seeds.minOf { (seed, range) ->
            var curr = seed
            var minLocation = Long.MAX_VALUE
            while (curr < seed + range) {
                minLocation = minOf(chunks.convertToLocation(curr), minLocation)
                curr++
                iter++
                if (iter % 1_000_000 == 0L) println("${iter/1_000_000}/$total")
            }
            minLocation
        }
        return answer.toInt() // 60294664
    }
}