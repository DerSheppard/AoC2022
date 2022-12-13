package org.sheppard.aoc

class Day03(override val raw: String) : Day {

    private val lines = raw.lines().filter { l -> l != "" }

    override fun part1(): String {
        return lines.map {
            val compartment1 = it.substring(0, it.length / 2).toSet()
            val compartment2 = it.substring(it.length / 2).toSet()
            getPriority(compartment1.intersect(compartment2).first())
        }.reduce { sum, element -> sum + element }.toString()
    }

    override fun part2(): String {
        return (lines.indices step 3)
            .map { lines[it].toSet().intersect(lines[it + 1].toSet().intersect(lines[it + 2].toSet())).first() }
            .sumOf { getPriority(it) }.toString()
    }

    private fun getPriority(c: Char): Int = if (c.isLowerCase()) c.code - 96 else c.code - 38
}