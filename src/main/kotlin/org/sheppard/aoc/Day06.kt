package org.sheppard.aoc

class Day06(override val raw: String) : Day {

    private val input = raw.lines()[0]

    override fun part1(): String {
        for (i in 0..input.length) if (input.substring(i, i + 4).isDistinct()) return (i + 4).toString()
        return "-1"
    }

    override fun part2(): String {
        for (i in 0..input.length) if (input.substring(i, i + 14).isDistinct()) return (i + 14).toString()
        return "-1"
    }

    private fun String.isDistinct(): Boolean = this.toSet().size == this.length
}