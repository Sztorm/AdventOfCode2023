package day11

import ByteArray2D
import printAnswer
import kotlin.math.abs

private fun main() {
    printAnswer(day = 11, testPart = 1, 374, ::part1)
    printAnswer(day = 11, testPart = 2, 82000210L, ::part2)
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

    fun getEmptyRowIndices(): List<Int> {
        val result = ArrayList<Int>()

        for (row in 0..<rowCount) {
            var isEmpty = true

            for (col in 0..<columnCount) {
                if (this[row, col] != 0) {
                    isEmpty = false
                }
            }
            if (isEmpty) {
                result.add(row)
            }
        }
        return result
    }

    fun getEmptyColumnIndices(): List<Int> {
        val result = ArrayList<Int>()

        for (col in 0..<columnCount) {
            var isEmpty = true

            for (row in 0..<rowCount) {
                if (this[row, col] != 0) {
                    isEmpty = false
                }
            }
            if (isEmpty) {
                result.add(col)
            }
        }
        return result
    }

    fun getGalaxyIndicesWithEmptyDistances(emptyDistance: Int): List<Pair<Int, Int>> {
        val emptyRowIndices = getEmptyRowIndices()
        val emptyColumnIndices = getEmptyColumnIndices()

        return getGalaxyIndices().map { index ->
            val emptyRowsCount = emptyRowIndices.count { it < index.first }
            val emptyColsCount = emptyColumnIndices.count { it < index.second }

            Pair(
                index.first + emptyRowsCount * (emptyDistance - 1),
                index.second + emptyColsCount * (emptyDistance - 1)
            )
        }
    }

    companion object {
        fun parse(input: List<String>): CosmicGrid {
            val rowCount = input.size
            val columnCount = input[0].length
            val grid = ByteArray2D(rowCount, columnCount)
            val result = CosmicGrid(grid)

            for (row in 0..<rowCount) {
                for (column in 0..<columnCount) {
                    val char = input[row][column]

                    grid[row, column] = if (char == '#') 1 else 0
                }
            }
            return result
        }
    }
}

private fun part1(input: List<String>): Int {
    val cosmicGrid = CosmicGrid.parse(input)
    val galaxyIndices = cosmicGrid
        .getGalaxyIndicesWithEmptyDistances(2)
        .toMutableList()
    var result = 0

    while (galaxyIndices.isNotEmpty()) {
        val index = galaxyIndices.removeLast()

        if (galaxyIndices.isEmpty()) {
            break
        }
        result += cosmicGrid
            .getSteps(index, galaxyIndices)
            .sum()
    }
    return result
}

private fun part2(input: List<String>): Long {
    val cosmicGrid = CosmicGrid.parse(input)
    val galaxyIndices = cosmicGrid
        .getGalaxyIndicesWithEmptyDistances(1000000)
        .toMutableList()
    var result = 0L

    while (galaxyIndices.isNotEmpty()) {
        val index = galaxyIndices.removeLast()

        if (galaxyIndices.isEmpty()) {
            break
        }
        result += cosmicGrid
            .getSteps(index, galaxyIndices)
            .sumOf { it.toLong() }
    }
    return result
}