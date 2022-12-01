package org.sheppard.aoc

import org.junit.jupiter.api.Test
import java.io.File

class Day01Runner : DayRunner(Day01(readInput("input/input01")))

abstract class DayRunner(private val day: Day) {

    @Test
    open fun part1() {
        println(day.part1())
    }

    @Test
    open fun part2() {
        println(day.part2())
    }
}

private fun readInput(file: String): String {
    return File(file).bufferedReader().readText()
}
