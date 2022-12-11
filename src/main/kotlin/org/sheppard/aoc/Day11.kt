package org.sheppard.aoc

class Day11(override val raw: String) : Day {

    val lines = raw.lines().filter { l -> l != "" }

    override fun part1(): String {
        val monkeys = parseMonkeys()
        for (i in 1..20) monkeys.forEach { m -> m.takeTurn() }
        return monkeys.map(Monkey::inspections).sortedDescending().take(2).reduce { acc, i -> acc * i }.toString()
    }

    override fun part2(): String {
        val monkeys = parseMonkeys()
        val modulus = monkeys.map { m -> m.divisor }.reduce { acc, i -> acc * i }
        monkeys.forEach { m -> m.modulus = modulus }
        for (i in 1..10000) monkeys.forEach { m -> m.takeTurn() }
        return monkeys.map(Monkey::inspections).sortedDescending().take(2).reduce { acc, i -> acc * i }.toString()
    }

    private fun parseMonkeys(): List<Monkey> {
        val monkeys = ArrayList<Monkey>()

        for (i in lines.indices step 6) {
            val items = parseNumbers(lines[i + 1]).toCollection(ArrayDeque())
            val operation = lines[i + 2].split(" ").takeLast(3)
            val divisor = parseNumbers(lines[i + 3]).first().toLong()
            val trueMonkey = parseNumbers(lines[i + 4]).first().toInt()
            val falseMonkey = parseNumbers(lines[i + 5]).first().toInt()
            monkeys.add(Monkey(monkeys, items, operation, divisor, trueMonkey, falseMonkey))
        }

        return monkeys
    }

    private fun parseNumbers(string: String): List<Long> {
        return Regex("[0-9]+").findAll(string).map(MatchResult::value).map(String::toLong).toList()
    }

    private class Monkey(
        val monkeys: List<Monkey>,
        val items: ArrayDeque<Long>,
        val operation: List<String>,
        val divisor: Long,
        val trueMonkey: Int,
        val falseMonkey: Int
    ) {

        var inspections = 0L
        var modulus = -1L

        fun takeTurn() {
            while (!items.isEmpty()) {
                inspections++
                var item = executeOperation(items.removeFirst())

                if (modulus == -1L) item /= 3 // part 1 operation
                else item %= modulus // part 2 operation

                if (item % divisor == 0L) monkeys[trueMonkey].items.addLast(item)
                else monkeys[falseMonkey].items.addLast(item)
            }
        }

        private fun executeOperation(old: Long): Long {
            val a = if (operation[0] == "old") old else operation[0].toLong()
            val b = if (operation[2] == "old") old else operation[2].toLong()
            if (operation[1] == "+") return a + b
            return a * b
        }
    }
}