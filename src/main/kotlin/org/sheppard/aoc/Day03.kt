package org.sheppard.aoc

class Day03(override val raw: String) : Day {

    private val lines = raw.lines().filter { l -> l != "" }

    override fun part1(): String {
        return lines.map {
            val compartment1 = it.substring(0, it.length / 2).toSet()
            val compartment2 = it.substring(it.length / 2).toSet()
            getPriority(compartment1.intersect(compartment2).elementAt(0))
        }.reduce { sum, element -> sum + element }.toString()
    }

    override fun part2(): String {
        var acc = 0
        for (i in lines.indices step 3) {
            val c = lines[i].toSet().intersect(lines[i + 1].toSet().intersect(lines[i + 2].toSet())).elementAt(0)
            acc += getPriority(c)
        }
        return acc.toString()
    }

    private fun getPriority(c: Char): Int {
        return if (c.isLowerCase()) c.code - 96 else c.code - 38
    }
}