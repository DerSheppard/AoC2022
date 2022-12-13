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
class Day09Runner : DayRunner(Day09(readInput("input/input09")))
class Day10Runner : DayRunner(Day10(readInput("input/input10")))
class Day11Runner : DayRunner(Day11(readInput("input/input11")))
class Day12Runner : DayRunner(Day12(readInput("input/input12")))
class Day13Runner : DayRunner(Day13(readInput("input/input13")))

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
