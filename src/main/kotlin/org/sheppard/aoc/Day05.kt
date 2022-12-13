package org.sheppard.aoc

import java.util.*

class Day05(override val raw: String) : Day {

    private val lines = raw.lines()

    override fun part1(): String {
        val stacks = getCrateStacks()
        for (move in getMoves()) for (i in 1..move[0]) stacks[move[2] - 1].push(stacks[move[1] - 1].pop())
        return stacks.map { it.peek() }.joinToString("")
    }

    override fun part2(): String {
        val stacks = getCrateStacks()
        val temp = ArrayDeque<Char>()

        getMoves().forEach { move ->
            for (i in 1..move[0]) temp.push(stacks[move[1] - 1].pop())
            for (i in 1..move[0]) stacks[move[2] - 1].push(temp.pop())
        }

        return stacks.map { it.peek() }.joinToString("")
    }

    private fun getCrateStacks(): List<ArrayDeque<Char>> {
        val stacks = List(9) { ArrayDeque<Char>() }
        lines.subList(0, 8).reversed().forEach {
            for (i in 0..8) {
                val crate = it[1 + i * 4]
                if (crate != ' ') stacks[i].push(crate)
            }
        }
        return stacks
    }

    private fun getMoves(): List<List<Int>> {
        return lines.subList(10, lines.size)
            .filter { l -> l != "" }
            .map { Regex("[0-9]+").findAll(it).map(MatchResult::value).map(String::toInt).toList() }
    }
}