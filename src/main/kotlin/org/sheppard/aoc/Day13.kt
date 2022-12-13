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
        packets += divider1
        packets += divider2
        packets.sort()
        return ((packets.indexOf(divider1) + 1) * (packets.indexOf(divider2) + 1)).toString()
    }

    private fun parseLine(line: String): Node {
        var current = Node(null, null)

        var i = 0
        while (++i < line.length - 1) {
            when (val c: Char = line[i]) {
                '[' -> {
                    val childNode = Node(null, current)
                    current.elements.add(childNode)
                    current = childNode
                }

                ']' -> current = current.parent ?: throw IllegalStateException("currentNode cannot become null")

                else -> {
                    if (c.isDigit()) {
                        val num = if (line[i + 1].isDigit()) "${c}${line[++i]}".toInt() else "$c".toInt()
                        current.elements.add(Node(num, current))
                    }
                }
            }
        }

        return current
    }

    private class Node(var number: Int?, val parent: Node?) : Comparable<Node> {

        val elements = ArrayList<Node>()

        override fun compareTo(other: Node): Int {
            if (this.number != null && other.number != null) {
                return if (this.number == other.number) 0 else this.number!! - other.number!!
            }

            //if one of them is a number, convert to list
            if (this.number != null) this.convertToList()
            else if (other.number != null) other.convertToList()

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

        private fun convertToList() {
            elements.add(Node(number, this))
            this.number = null
        }
    }
}