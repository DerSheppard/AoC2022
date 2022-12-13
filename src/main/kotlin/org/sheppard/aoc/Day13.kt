package org.sheppard.aoc

class Day13(override val raw: String) : Day {

    val lines = raw.lines().filter { l -> l != "" }

    override fun part1(): String {
        val packets = lines.map { parseLine(it) }
        return (packets.indices step 2).asSequence().filter { packets[it] < (packets[it + 1]) }.sumOf { (it / 2) + 1 }.toString()
    }

    override fun part2(): String {
        val packets = lines.map { parseLine(it) }.toMutableList()
        val divider1 = parseLine("[[2]]")
        val divider2 = parseLine("[[6]]")
        packets.add(divider1)
        packets.add(divider2)
        packets.sort()
        return ((packets.indexOf(divider1) + 1) * (packets.indexOf(divider2) + 1)).toString()
    }

    private fun parseLine(line: String): Node {
        var current = Node(null)

        var i = 0
        while (++i < line.length - 1) when (val c: Char = line[i]) {
            '[' -> {
                val childNode = Node(current)
                current.elements.add(childNode)
                current = childNode
            }
            ']' -> {
                current = current.parent ?: throw IllegalStateException("currentNode cannot become null")
            }
            else -> if (c.isDigit()) {
                val number = if (line[i + 1].isDigit()) "${c}${line[++i]}".toInt() else "$c".toInt()
                current.elements.add(Node(current, number))
            }
        }

        return current
    }

    private class Node(val parent: Node?, var number: Int = Int.MIN_VALUE) : Comparable<Node> {

        val elements = ArrayList<Node>()

        override fun compareTo(other: Node): Int {
            if (this.isNumber() && other.isNumber()) return this.number - other.number

            //if one of them is a number, convert to list
            if (this.isNumber()) this.convertToList()
            else if (other.isNumber()) other.convertToList()

            //list comparison
            val leftIterator = this.elements.iterator()
            val rightIterator = other.elements.iterator()
            while (leftIterator.hasNext() || rightIterator.hasNext()) {
                if (!leftIterator.hasNext()) return -1
                if (!rightIterator.hasNext()) return 1
                val comparisonResult = leftIterator.next().compareTo(rightIterator.next())
                if (comparisonResult != 0) return comparisonResult
            }

            return 0
        }

        private fun isNumber(): Boolean = number != Int.MIN_VALUE

        private fun convertToList() {
            elements.add(Node( this, number))
            this.number = Int.MIN_VALUE
        }
    }
}