package com.tyler.advent.y2023

import java.io.File

object Day10 {
    private val inputFile = File("inputs/Day10.txt")

    private enum class PipeType(val char: Char) {
        NSPipe('|'),
        EWPipe('-'),
        NEPipe('L'),
        NWPipe('J'),
        SWPipe('7'),
        SEPipe('F'),
        StartingPipe('S'),
    }

    private fun Char.toPipeType() = PipeType.values().find { it.char == this }

    private data class Pipe(val row: Int, val col: Int, val pipeType: PipeType) {
        fun isNorthOf(pipe: Pipe) = row == pipe.row - 1
        fun isSouthOf(pipe: Pipe) = row == pipe.row + 1
        fun isEastOf(pipe: Pipe) = col == pipe.col + 1
        fun isWestOf(pipe: Pipe) = col == pipe.col - 1

        fun connectsTo(pipe: Pipe) = when (pipeType) {
            PipeType.NSPipe -> pipe.isNorthOf(this) || pipe.isSouthOf(this)
            PipeType.EWPipe -> pipe.isEastOf(this) || pipe.isWestOf(this)
            PipeType.NEPipe -> pipe.isNorthOf(this) || pipe.isEastOf(this)
            PipeType.NWPipe -> pipe.isNorthOf(this) || pipe.isWestOf(this)
            PipeType.SWPipe -> pipe.isSouthOf(this) || pipe.isWestOf(this)
            PipeType.SEPipe -> pipe.isSouthOf(this) || pipe.isEastOf(this)
            PipeType.StartingPipe -> throw Exception("No.")
        }
    }

    private class Trail(val map: List<List<Pipe?>>, var curr: Pipe) {
        private lateinit var prev: Pipe
        private val trail = mutableListOf(curr)

        fun traverse(): List<Pipe> {
            move()
            while(curr != trail[0]) move()
            return trail
        }

        private fun north() = map.getOrNull(curr.row - 1)?.getOrNull(curr.col)
        private fun east() = map.getOrNull(curr.row)?.getOrNull(curr.col + 1)
        private fun south() = map.getOrNull(curr.row + 1)?.getOrNull(curr.col)
        private fun west() = map.getOrNull(curr.row)?.getOrNull(curr.col - 1)

        private fun move() = when(curr.pipeType) {
            PipeType.NSPipe -> if (prev.isNorthOf(curr)) moveSouth() else moveNorth()
            PipeType.EWPipe -> if (prev.isEastOf(curr)) moveWest() else moveEast()
            PipeType.NEPipe -> if (prev.isNorthOf(curr)) moveEast() else moveNorth()
            PipeType.NWPipe -> if (prev.isNorthOf(curr)) moveWest() else moveNorth()
            PipeType.SWPipe -> if (prev.isSouthOf(curr)) moveWest() else moveSouth()
            PipeType.SEPipe -> if (prev.isSouthOf(curr)) moveEast() else moveSouth()
            PipeType.StartingPipe -> move(listOf(north(), east(), south(), west()).firstNotNullOf {
                it?.takeIf { it.connectsTo(startingPipe) }
            })
        }

        private fun moveNorth() = move(north()!!)
        private fun moveEast() = move(east()!!)
        private fun moveSouth() = move(south()!!)
        private fun moveWest() = move(west()!!)
        private fun move(pipe: Pipe) {
            prev = curr
            curr = pipe
            trail.add(curr)
        }
    }

    private lateinit var startingPipe: Pipe
    private val map = inputFile.readLines().mapIndexed { row, line ->
        line.mapIndexed { col, char ->
            char.toPipeType()?.let { Pipe(row, col, it) }.also { if (it?.pipeType == PipeType.StartingPipe) startingPipe = it }
        }
    }
    private val trail = Trail(map, startingPipe).traverse()

    fun problem1(): Int {
        return trail.size / 2 // 6856
    }

    fun problem2(): Int {
        var numInside = 0
        map.forEach { pipes ->
            var isInside = false
            pipes.forEach { pipe ->
                if (isInside && !trail.contains(pipe)) numInside++

                val northPipes = listOf(PipeType.NEPipe, PipeType.NSPipe, PipeType.NWPipe)
                if (northPipes.contains(pipe?.pipeType) && trail.contains(pipe)) isInside = !isInside
            }
        }
        return numInside // 501
    }
}