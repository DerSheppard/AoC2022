package org.sheppard.aoc

import org.sheppard.aoc.Day12.Direction.*

class Day12(override val raw: String) : Day {

    private val lines = raw.lines().filter { l -> l != "" }

    override fun part1(): String {
        return parseInput().startNode.stepsNeeded.toString()
    }

    override fun part2(): String {
        return parseInput().grid.flatMap { it.toList() }.filter { it.height == 'a'.code }.minOfOrNull { it.stepsNeeded }.toString()
    }

    private fun parseInput(): HeightMap {
        val grid = Array(lines.size) { Array(lines[0].length) { Node(0, 0, 0, emptyList()) } }
        var startNode: Node? = null
        var endNode: Node? = null

        for (y in grid.indices) {
            for (x in grid[y].indices) {
                val height = getHeight(x, y)

                val possibleDirections = mutableListOf<Direction>()
                if (y - 1 >= 0 && height - 1 <= getHeight(x, y - 1)) possibleDirections.add(UP)
                if (y + 1 < grid.size && height - 1 <= getHeight(x, y + 1)) possibleDirections.add(DOWN)
                if (x - 1 >= 0 && height - 1 <= getHeight(x - 1, y)) possibleDirections.add(LEFT)
                if (x + 1 < grid[y].size && height - 1 <= getHeight(x + 1, y)) possibleDirections.add(RIGHT)

                val node = Node(x, y, height, possibleDirections)
                grid[y][x] = node

                if (lines[y][x] == 'S') startNode = node
                else if (lines[y][x] == 'E') endNode = node
            }
        }

        return HeightMap(grid, startNode ?: throw IllegalStateException(), endNode ?: throw IllegalStateException())
    }

    private fun getHeight(x: Int, y: Int): Int = when (lines[y][x]) {
        'S' -> 'a'.code
        'E' -> 'z'.code
        else -> lines[y][x].code
    }

    private enum class Direction { UP, DOWN, LEFT, RIGHT }

    private class HeightMap(val grid: Array<Array<Node>>, val startNode: Node, endNode: Node) {

        // BFS to get min movement values for all nodes from end node
        init {
            endNode.stepsNeeded = 0
            val nodeQueue = ArrayDeque<Node>()
            nodeQueue.addLast(endNode)

            while (!nodeQueue.isEmpty()) {
                val current = nodeQueue.removeFirst()
                for (neighbour in current.possibleDirections.map { dir -> getNodeInDirection(current.x, current.y, dir) }) {
                    if (neighbour.stepsNeeded > current.stepsNeeded + 1) {
                        neighbour.stepsNeeded = current.stepsNeeded + 1
                        if (!nodeQueue.contains(neighbour)) nodeQueue.addLast(neighbour)
                    }
                }
            }
        }

        fun getNodeInDirection(x: Int, y: Int, direction: Direction): Node = when (direction) {
            UP -> grid[y - 1][x]
            DOWN -> grid[y + 1][x]
            LEFT -> grid[y][x - 1]
            RIGHT -> grid[y][x + 1]
        }
    }

    private class Node(val x: Int, val y: Int, val height: Int, val possibleDirections: List<Direction>) {
        var stepsNeeded = Int.MAX_VALUE
    }
}