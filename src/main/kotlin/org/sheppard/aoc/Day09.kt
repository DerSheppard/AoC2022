package org.sheppard.aoc

import java.lang.IllegalStateException
import kotlin.math.absoluteValue

class Day09(override val raw: String) : Day {

    private var lines = raw.lines().filter { l -> l != "" }.map { l -> l.split(" ") }

    override fun part1() = simulateRope(2).toString()

    override fun part2() = simulateRope(10).toString()

    private fun simulateRope(length: Int): Int {
        val rope = Array(length) { Position(0, 0) }
        val tailVisits = HashSet<String>()

        for (line in lines) {
            for (movements in 1..line[1].toInt()) {
                when (line[0]) {
                    "U" -> rope.first().move(0, 1)
                    "D" -> rope.first().move(0, -1)
                    "R" -> rope.first().move(1, 0)
                    "L" -> rope.first().move(-1, 0)
                    else -> throw IllegalStateException("Cannot happen")
                }

                for (i in 1 until rope.size) rope[i].follow(rope[i - 1])
                tailVisits.add(rope.last().toString())
            }
        }

        return tailVisits.size
    }

    private class Position(var x: Int, var y: Int) {

        fun move(x: Int, y: Int) {
            this.x += x
            this.y += y
        }

        fun follow(pos: Position) {
            var relativeX = pos.x - x
            var relativeY = pos.y - y

            if (relativeX.absoluteValue <= 1 && relativeY.absoluteValue <= 1) return // tail doesn't move

            if (relativeX.absoluteValue > 1) relativeX /= 2 // cap 2 and -2 to 1 and -1 respectively
            if (relativeY.absoluteValue > 1) relativeY /= 2 // -//-
            this.move(relativeX, relativeY)
        }

        override fun toString(): String = "Position(x=$x, y=$y)"
    }
}