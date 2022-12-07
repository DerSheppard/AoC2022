package org.sheppard.aoc

class Day07(override val raw: String) : Day {

    private val log = raw.lines().filter { l -> l != "" }.map { l -> l.split(" ") }

    override fun part1(): String {
        return traverseTree(buildTree()).filter { i -> i <= 100000 }.reduce { acc, i -> acc + i }.toString()
    }

    override fun part2(): String {
        val root = buildTree()
        val neededSpace = 30000000 - (70000000 - root.getFileSize())
        return traverseTree(root).filter { i -> i >= neededSpace }.min().toString()
    }

    private fun buildTree(): ElfDirectory {
        val root = ElfDirectory(null)
        var context = root

        for (command in log) {
            if (command[0] == "$" && command[1] == "cd") {
                context = when (command[2]) {
                    "/" -> root
                    ".." -> (context.parent ?: throw IllegalStateException("cannot traverse above root"))
                    else -> context.contents[command[2]] as ElfDirectory
                }
            } else if (command[0] != "$") {
                val file: ElfFile = if (command[0] == "dir") ElfDirectory(context) else ElfFile(command[0].toInt(), context)
                context.contents.putIfAbsent(command[1], file)
            }
        }

        return root
    }

    private fun traverseTree(dir: ElfDirectory): List<Int> {
        val result = mutableListOf(dir.getFileSize())
        dir.contents.values.filterIsInstance<ElfDirectory>().forEach { ele -> result += traverseTree(ele) }
        return result
    }

    private open class ElfFile(val size: Int, val parent: ElfDirectory?) {
        open fun getFileSize(): Int = size
    }

    private class ElfDirectory(parent: ElfDirectory?) : ElfFile(0, parent) {
        val contents: HashMap<String, ElfFile> = HashMap()
        override fun getFileSize(): Int = contents.values.map(ElfFile::getFileSize).reduce { acc, i -> acc + i }
    }
}