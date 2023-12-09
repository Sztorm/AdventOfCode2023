package day09

import printAnswer

private fun main() {
    printAnswer(day = 9, testPart = 1, 114, ::part1)
    printAnswer(day = 9, testPart = 2, 5, ::part2)
}

private fun parseHistory(input: String): List<Int> =
    input.split(' ').map { it.toInt() }

private fun findSubHistoriesOf(history: List<Int>): List<List<Int>> {
    val subHistories = ArrayList<List<Int>>()
    subHistories.add(history)

    do {
        val prevSubHistory = subHistories.last()
        val subHistory = ArrayList<Int>()

        for (i in 0..<prevSubHistory.lastIndex) {
            subHistory.add(prevSubHistory[i + 1] - prevSubHistory[i])
        }
        subHistories.add(subHistory)
    } while (!subHistory.all { it == 0 })

    return subHistories
}

private fun findNextValueOf(history: List<Int>): Int {
    val subHistories: List<List<Int>> = findSubHistoriesOf(history)

    return subHistories.sumOf { it.last() }
}

private fun part1(input: List<String>): Int {
    val histories: List<List<Int>> = input.map { parseHistory(it) }
    val nextValues: List<Int> = histories.map { findNextValueOf(it) }

    return nextValues.sum()
}

private fun findPreviousValueOf(history: List<Int>): Int {
    val subHistories: List<List<Int>> = findSubHistoriesOf(history)

    return subHistories
        .asReversed()
        .fold(0) { acc, subHistory -> subHistory.first() - acc }
}

private fun part2(input: List<String>): Int {
    val histories: List<List<Int>> = input.map { parseHistory(it) }
    val nextValues: List<Int> = histories.map { findPreviousValueOf(it) }

    return nextValues.sum()
}