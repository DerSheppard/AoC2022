package org.sheppard.aoc

class Day02(override val raw: String) : Day {

    private var lines = raw.lines().filter { l -> l != "" }
    private val winCondition = mapOf('A' to 'C', 'B' to 'A', 'C' to 'B')
    private val loseCondition = mapOf('A' to 'B', 'B' to 'C', 'C' to 'A')

    override fun part1(): String {
        return lines.map { l -> getScore(l[0], l[2] - 23) }.reduce { s, e -> s + e }.toString()
    }


    override fun part2(): String {
        return lines.map { l -> getStrategy(l[0], l[2]) }.reduce { s, e -> s + e }.toString()
    }

    private fun getScore(opponent: Char, me: Char): Int {
        var score = 0
        if (me == opponent) score = 3
        else if (opponent == winCondition[me]) score = 6
        return (me.code - 64) + score
    }

    private fun getStrategy(opponent: Char, strategy: Char): Int {
        return when (strategy) {
            'X' -> (getScore(opponent, winCondition[opponent] ?: throw IllegalStateException()))
            'Y' -> (getScore(opponent, opponent))
            'Z' -> (getScore(opponent, loseCondition[opponent] ?: throw IllegalStateException()))
            else -> throw IllegalStateException()
        }
    }
}