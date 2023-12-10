package day10

import printAnswer

private fun main() {
    printAnswer(day = 10, testPart = 1, 8, ::part1)
    printAnswer(day = 10, testPart = 2, 0, ::part2)
}

private sealed interface Pipe {
    fun canConnectToNorthern(pipe: Pipe): Boolean
    fun canConnectToSouthern(pipe: Pipe): Boolean
    fun canConnectToEastern(pipe: Pipe): Boolean
    fun canConnectToWestern(pipe: Pipe): Boolean
}

/** The '|' pipe. */
private data object VerticalPipe : Pipe {
    override fun canConnectToNorthern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> true
        is HorizontalPipe -> false
        is NEPipe -> false
        is NWPipe -> false
        is SEPipe -> true
        is SWPipe -> true
    }

    override fun canConnectToSouthern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> true
        is HorizontalPipe -> false
        is NEPipe -> true
        is NWPipe -> true
        is SEPipe -> false
        is SWPipe -> false
    }

    override fun canConnectToEastern(pipe: Pipe): Boolean = false

    override fun canConnectToWestern(pipe: Pipe): Boolean = false

    override fun toString(): String = "|"
}

/** The '-' pipe. */
private data object HorizontalPipe : Pipe {
    override fun canConnectToNorthern(pipe: Pipe): Boolean = false

    override fun canConnectToSouthern(pipe: Pipe): Boolean = false

    override fun canConnectToEastern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> false
        is HorizontalPipe -> true
        is NEPipe -> false
        is NWPipe -> true
        is SEPipe -> false
        is SWPipe -> true
    }

    override fun canConnectToWestern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> false
        is HorizontalPipe -> true
        is NEPipe -> true
        is NWPipe -> false
        is SEPipe -> true
        is SWPipe -> false
    }

    override fun toString(): String = "-"
}

/** The 'L' pipe. */
private data object NEPipe : Pipe {
    override fun canConnectToNorthern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> true
        is HorizontalPipe -> false
        is NEPipe -> false
        is NWPipe -> false
        is SEPipe -> true
        is SWPipe -> true
    }

    override fun canConnectToSouthern(pipe: Pipe): Boolean = false

    override fun canConnectToEastern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> false
        is HorizontalPipe -> true
        is NEPipe -> false
        is NWPipe -> true
        is SEPipe -> false
        is SWPipe -> true
    }

    override fun canConnectToWestern(pipe: Pipe): Boolean = false

    override fun toString(): String = "L"
}

/** The 'J' pipe. */
private data object NWPipe : Pipe {
    override fun canConnectToNorthern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> true
        is HorizontalPipe -> false
        is NEPipe -> false
        is NWPipe -> false
        is SEPipe -> true
        is SWPipe -> true
    }

    override fun canConnectToSouthern(pipe: Pipe): Boolean = false

    override fun canConnectToEastern(pipe: Pipe): Boolean = false

    override fun canConnectToWestern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> false
        is HorizontalPipe -> true
        is NEPipe -> true
        is NWPipe -> false
        is SEPipe -> true
        is SWPipe -> false
    }

    override fun toString(): String = "J"
}

/** The '7' pipe. */
private data object SWPipe : Pipe {
    override fun canConnectToNorthern(pipe: Pipe): Boolean = false

    override fun canConnectToSouthern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> true
        is HorizontalPipe -> false
        is NEPipe -> true
        is NWPipe -> true
        is SEPipe -> false
        is SWPipe -> false
    }

    override fun canConnectToEastern(pipe: Pipe): Boolean = false

    override fun canConnectToWestern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> false
        is HorizontalPipe -> true
        is NEPipe -> true
        is NWPipe -> false
        is SEPipe -> true
        is SWPipe -> false
    }

    override fun toString(): String = "7"
}

/** The 'F' pipe. */
private data object SEPipe : Pipe {
    override fun canConnectToNorthern(pipe: Pipe): Boolean = false

    override fun canConnectToSouthern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> true
        is HorizontalPipe -> false
        is NEPipe -> true
        is NWPipe -> true
        is SEPipe -> false
        is SWPipe -> false
    }

    override fun canConnectToEastern(pipe: Pipe): Boolean = when (pipe) {
        is VerticalPipe -> false
        is HorizontalPipe -> true
        is NEPipe -> false
        is NWPipe -> true
        is SEPipe -> false
        is SWPipe -> true
    }

    override fun canConnectToWestern(pipe: Pipe): Boolean = false

    override fun toString(): String = "F"
}

private class PipeGrid private constructor(
    private val grid: ByteArray,
    val rowCount: Int,
    val columnCount: Int
) {
    operator fun get(row: Int, column: Int): Pipe? {
        return PIPES_BY_INDEX[grid[row * columnCount + column].toInt()]
    }

    fun getConnectedIndices(row: Int, column: Int): Pair<Pair<Int, Int>, Pair<Int, Int>> =
        when (this[row, column]) {
            HorizontalPipe -> Pair(
                if (isWithinBounds(row, column - 1)) Pair(row, column - 1)
                else Pair(-1, -1),
                if (isWithinBounds(row, column + 1)) Pair(row, column + 1)
                else Pair(-1, -1),
            )

            VerticalPipe -> Pair(
                if (isWithinBounds(row - 1, column)) Pair(row - 1, column)
                else Pair(-1, -1),
                if (isWithinBounds(row + 1, column)) Pair(row + 1, column)
                else Pair(-1, -1),
            )

            NEPipe -> Pair(
                if (isWithinBounds(row - 1, column)) Pair(row - 1, column)
                else Pair(-1, -1),
                if (isWithinBounds(row, column + 1)) Pair(row, column + 1)
                else Pair(-1, -1),
            )

            NWPipe -> Pair(
                if (isWithinBounds(row - 1, column)) Pair(row - 1, column)
                else Pair(-1, -1),
                if (isWithinBounds(row, column - 1)) Pair(row, column - 1)
                else Pair(-1, -1),
            )

            SWPipe -> Pair(
                if (isWithinBounds(row + 1, column)) Pair(row + 1, column)
                else Pair(-1, -1),
                if (isWithinBounds(row, column - 1)) Pair(row, column - 1)
                else Pair(-1, -1),
            )

            SEPipe -> Pair(
                if (isWithinBounds(row + 1, column)) Pair(row + 1, column)
                else Pair(-1, -1),
                if (isWithinBounds(row, column + 1)) Pair(row, column + 1)
                else Pair(-1, -1),
            )

            null -> Pair(Pair(-1, -1), Pair(-1, -1))
        }

    fun isWithinBounds(row: Int, column: Int): Boolean =
        row.toUInt() < rowCount.toUInt() && column.toUInt() < columnCount.toUInt()

    override fun toString(): String {
        val sb = StringBuilder((rowCount + 1) * columnCount)

        for (row in 0..<rowCount) {
            for (column in 0..<columnCount) {
                sb.append(this[row, column] ?: '.')
            }
            sb.append('\n')
        }
        return sb.toString()
    }

    companion object {
        private val PIPES_BY_INDEX = Array<Pipe?>(7) { null }.apply {
            this[1] = VerticalPipe
            this[2] = HorizontalPipe
            this[3] = NEPipe
            this[4] = NWPipe
            this[5] = SWPipe
            this[6] = SEPipe
        }

        private val PIPE_VALUES_BY_CHAR = mapOf(
            '.' to 0.toByte(),
            '|' to 1.toByte(),
            '-' to 2.toByte(),
            'L' to 3.toByte(),
            'J' to 4.toByte(),
            '7' to 5.toByte(),
            'F' to 6.toByte(),
        )

        private fun findMatchingPipeChar(
            row: Int, column: Int, input: List<String>, grid: PipeGrid
        ): Char {
            val hasNorthernConnection = grid.isWithinBounds(row - 1, column) &&
                    (input[row - 1][column] == '|' ||
                            input[row - 1][column] == 'F' ||
                            input[row - 1][column] == '7')
            val hasSouthernConnection = grid.isWithinBounds(row + 1, column) &&
                    (input[row + 1][column] == '|' ||
                            input[row + 1][column] == 'L' ||
                            input[row + 1][column] == 'J')
            val hasWesternConnection = grid.isWithinBounds(row, column - 1) &&
                    (input[row][column - 1] == '-' ||
                            input[row][column - 1] == 'L' ||
                            input[row][column - 1] == 'F')
            val hasEasternConnection = grid.isWithinBounds(row, column + 1) &&
                    (input[row][column + 1] == '-' ||
                            input[row][column + 1] == 'J' ||
                            input[row][column + 1] == '7')

            return when {
                hasNorthernConnection -> when {
                    hasSouthernConnection -> '|'
                    hasWesternConnection -> 'J'
                    hasEasternConnection -> 'L'
                    else -> '.'
                }

                hasSouthernConnection -> when {
                    hasWesternConnection -> '7'
                    hasEasternConnection -> 'F'
                    else -> '.'
                }

                hasWesternConnection -> when {
                    hasEasternConnection -> '-'
                    else -> '.'
                }

                else -> '.'
            }
        }

        fun parse(input: List<String>): Pair<PipeGrid, Pair<Int, Int>> {
            val rowCount = input.size
            val columnCount = input[0].length
            val grid = ByteArray(rowCount * columnCount)
            val result = PipeGrid(grid, rowCount, columnCount)
            var start = Pair(-1, -1)

            for (row in 0..<rowCount) {
                for (column in 0..<columnCount) {
                    val char = input[row][column]

                    if (char == 'S') {
                        start = Pair(row, column)
                        val matchingChar = findMatchingPipeChar(row, column, input, result)

                        grid[row * columnCount + column] =
                            PIPE_VALUES_BY_CHAR.getOrDefault(matchingChar, 0)
                    } else {
                        grid[row * columnCount + column] =
                            PIPE_VALUES_BY_CHAR.getOrDefault(char, 0)
                    }
                }
            }
            return Pair(result, start)
        }
    }
}

private fun part1(input: List<String>): Int {
    val (pipeGrid, start) = PipeGrid.parse(input)
    var (nextPipeA, nextPipeB) = pipeGrid.getConnectedIndices(start.first, start.second)
    var prevPipeA = start
    var prevPipeB = start
    var stepCount = 1

    while (nextPipeA != nextPipeB) {
        val (nextPipeAA, nextPipeAB) = pipeGrid.getConnectedIndices(nextPipeA.first, nextPipeA.second)

        if (prevPipeA == nextPipeAA) {
            prevPipeA = nextPipeA
            nextPipeA = nextPipeAB
        } else {
            prevPipeA = nextPipeA
            nextPipeA = nextPipeAA
        }
        val (nextPipeBA, nextPipeBB) = pipeGrid.getConnectedIndices(nextPipeB.first, nextPipeB.second)

        if (prevPipeB == nextPipeBA) {
            prevPipeB = nextPipeB
            nextPipeB = nextPipeBB
        } else {
            prevPipeB = nextPipeB
            nextPipeB = nextPipeBA
        }
        stepCount++
    }
    return stepCount
}

private fun part2(input: List<String>): Int {
    return input.size
}