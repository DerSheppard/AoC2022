package org.sheppard.aoc

class Day04(override val raw: String) : Day {

    private val lines = raw.lines().filter { l -> l != "" }

    override fun part1(): String {
        return getSectors().map { (it[0] >= it[2] && it[1] <= it[3]) || (it[2] >= it[0] && it[3] <= it[1]) }
            .count { it }.toString()
    }

    override fun part2(): String {
        return getSectors().map { it[1] < it[2] || it[3] < it[0] }.count { !it }.toString()
    }

    private fun getSectors(): List<List<Int>> {
        return lines.map { it.replace(Regex("[-,]"), " ").split(" ").map(String::toInt) }
    }
}