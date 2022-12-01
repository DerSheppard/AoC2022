package org.sheppard.aoc

class Day01(override val raw: String) : Day {

    override fun part1(): String {
        return (getKcals().maxOrNull() ?: -1).toString()
    }

    override fun part2(): String {
        return getKcals().sortedDescending().subList(0, 3).reduce { s, e -> s + e }.toString()
    }

    private fun getKcals(): List<Int> {
        return raw.split("\n\n")
            .map { s -> s.lines().filter { l -> l != "" }.map(String::toInt).reduce { sum, e -> sum + e } }
    }
}