package day11

import ByteArray2D
import printAnswer
import kotlin.math.abs

private fun main() {
    printAnswer(day = 11, testPart = 1, 374, ::part1)
    printAnswer(day = 11, testPart = 2, 0, ::part2)
}

private class CosmicGrid private constructor(private val grid: ByteArray2D) {
    val rowCount: Int
        get() = grid.rowCount

    val columnCount: Int
        get() = grid.columnCount

    operator fun get(row: Int, column: Int): Int = grid[row, column].toInt()

    operator fun get(index: Pair<Int, Int>): Int = grid[index.first, index.second].toInt()

    override fun toString(): String {
        val sb = StringBuilder((rowCount + 1) * columnCount)

        for (row in 0..<rowCount) {
            for (column in 0..<columnCount) {
                val char = when (this[row, column]) {
                    1 -> '#'
                    else -> '.'
                }
                sb.append(char)
            }
            sb.append('\n')
        }
        return sb.toString()
    }

    fun getGalaxyIndices(): List<Pair<Int, Int>> {
        val result = ArrayList<Pair<Int, Int>>()

        for (row in 0..<rowCount) {
            for (column in 0..<columnCount) {
                if (this[row, column] == 1) {
                    result.add(Pair(row, column))
                }
            }
        }
        return result
    }

    fun getSteps(start: Pair<Int, Int>, indices: List<Pair<Int, Int>>): List<Int> {
        val result = ArrayList<Int>(indices.size)

        for (index in indices) {
            result.add(abs(start.first - index.first) + abs(start.second - index.second))
        }
        return result
    }

    companion object {
        private fun getExpandedInput(input: List<String>): List<List<Char>> {
            val result = input
                .map { it.toMutableList() }
                .toMutableList()
            var i = result.lastIndex

            while (i >= 0) {
                if (result[i].all { it == '.' }) {
                    result.add(i, MutableList(result[i].size) { '.' })
                }
                i--
            }
            var j = result[0].lastIndex

            while (j >= 0) {
                var hasDotsOnly = true

                for (i2 in result.indices) {
                    if (result[i2][j] != '.') {
                        hasDotsOnly = false
                    }
                }
                if (hasDotsOnly) {
                    for (i2 in result.indices) {
                        result[i2].add(j, '.')
                    }
                }
                j--
            }
            return result
        }

        fun parse(input: List<String>): CosmicGrid {
            val expandedInput = getExpandedInput(input)
            val rowCount = expandedInput.size
            val columnCount = expandedInput[0].size
            val grid = ByteArray2D(rowCount, columnCount)
            val result = CosmicGrid(grid)

            for (row in 0..<rowCount) {
                for (column in 0..<columnCount) {
                    val char = expandedInput[row][column]

                    grid[row, column] = if (char == '#') 1 else 0
                }
            }
            return result
        }
    }
}

private fun part1(input: List<String>): Int {
    val cosmicGrid = CosmicGrid.parse(input)
    val galaxyIndices = cosmicGrid.getGalaxyIndices()
    var result = 0

    for (index in galaxyIndices) {
        result += cosmicGrid.getSteps(index, galaxyIndices.filter { it != index }).sum()
    }
    return result / 2
}

private fun part2(input: List<String>): Int {
    return input.size
}