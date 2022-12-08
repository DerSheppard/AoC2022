package org.sheppard.aoc

class Day08(override val raw: String) : Day {

    private val grid = raw.lines().filter { l -> l != "" }.map { l -> l.toCharArray().map { c -> c.code - 48 } }
    private val height = grid.size
    private val width = grid[0].size

    override fun part1(): String {
        val visibleTrees = Array(height) { BooleanArray(width) { false } }

        // left 2 right and right 2 left
        for (y in grid.indices) {
            var currentHeight = -1
            for (x in grid[y].indices) {
                if (grid[y][x] > currentHeight) {
                    visibleTrees[y][x] = true
                    currentHeight = grid[y][x]
                }
            }

            currentHeight = -1
            for (x in grid[y].indices.reversed()) {
                if (grid[y][x] > currentHeight) {
                    visibleTrees[y][x] = true
                    currentHeight = grid[y][x]
                }
            }
        }

        // top-down and bottom-up
        for (x in grid[0].indices) {
            var currentHeight = -1
            for (y in grid.indices) {
                if (grid[y][x] > currentHeight) {
                    visibleTrees[y][x] = true
                    currentHeight = grid[y][x]
                }
            }

            currentHeight = -1
            for (y in grid.indices.reversed()) {
                if (grid[y][x] > currentHeight) {
                    visibleTrees[y][x] = true
                    currentHeight = grid[y][x]
                }
            }
        }

        return visibleTrees.map { a -> a.count { it } }.reduce { acc, i -> acc + i }.toString()
    }

    override fun part2(): String {
        var maxScore = 0

        for (y in 1 until grid.size - 1) {
            for (x in 1 until grid.size - 1) {
                maxScore = maxScore.coerceAtLeast(calculateScore(x, y))
            }
        }

        return maxScore.toString()
    }

    private fun calculateScore(x: Int, y: Int): Int {
        var up = 0; var down = 0; var left = 0; var right = 0
        val treeHeight = grid[y][x]

        for (xIterator in x - 1 downTo 0) {
            left += 1
            if (grid[y][xIterator] >= treeHeight) break
        }

        for (xIterator in x + 1 until grid[y].size) {
            right += 1
            if (grid[y][xIterator] >= treeHeight) break
        }

        for (yIterator in y - 1 downTo 0) {
            up += 1
            if (grid[yIterator][x] >= treeHeight) break
        }

        for (yIterator in y + 1 until grid.size) {
            down += 1
            if (grid[yIterator][x] >= treeHeight) break
        }

        return up * down * left * right
    }
}