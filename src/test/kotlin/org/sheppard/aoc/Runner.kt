package org.sheppard.aoc

import org.junit.jupiter.api.Test
import java.io.File

class Day01Runner : DayRunner(Day01(readInput("input/input01")))
class Day02Runner : DayRunner(Day02(readInput("input/input02")))
class Day03Runner : DayRunner(Day03(readInput("input/input03")))
class Day04Runner : DayRunner(Day04(readInput("input/input04")))
class Day05Runner : DayRunner(Day05(readInput("input/input05")))
class Day06Runner : DayRunner(Day06(readInput("input/input06")))
class Day07Runner : DayRunner(Day07(readInput("input/input07")))
class Day08Runner : DayRunner(Day08(readInput("input/input08")))

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
