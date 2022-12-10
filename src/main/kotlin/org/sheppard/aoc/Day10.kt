package org.sheppard.aoc

class Day10(override val raw: String) : Day {

    private val instructions = raw.lines().filter { l -> l != "" }.map { l -> l.split(" ") }

    override fun part1(): String {
        val cpu = CPU()
        instructions.forEach { instr -> cpu.executeInstruction(instr) }

        var sum = 0
        for (i in 20..220 step 40) sum += (cpu.xHistory[i - 1] * i)
        return sum.toString()
    }

    override fun part2(): String {
        val cpu = CPU()
        instructions.forEach { instr -> cpu.executeInstruction(instr) }

        val sb = StringBuilder()
        for (i in cpu.xHistory.indices) {
            val pos = i % 40
            if (pos == 0) sb.appendLine()

            if (pos >= cpu.xHistory[i] - 1 && pos <= cpu.xHistory[i] + 1) sb.append('#')
            else sb.append('.')
        }

        return sb.toString()
    }

    private class CPU {

        var x: Int = 1
        val xHistory = mutableListOf<Int>()

        fun executeInstruction(instruction: List<String>) {
            when (instruction[0]) {
                "noop" -> cycle()
                "addx" -> {
                    cycle()
                    cycle()
                    x += instruction[1].toInt()
                }
            }
        }

        private fun cycle() = xHistory.add(x)
    }
}