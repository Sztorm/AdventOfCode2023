package day08

import printAnswer

private fun main() {
    printAnswer(day = 8, testPart = 1, 6, ::part1)
    printAnswer(day = 8, testPart = 2, 0, ::part2)
}

private val NODE_REGEX = Regex("[A-Z]{3}")

private fun parseNodePair(input: String) = NODE_REGEX
    .findAll(input)
    .toList()
    .map { it.value }
    .let { Pair(it[0], Pair(it[1], it[2])) }

private fun part1(input: List<String>): Int {
    val instructions: String = input[0]
    val nodePairsByNode: Map<String, Pair<String, String>> = input
        .subList(fromIndex = 2, toIndex = input.size)
        .associate { parseNodePair(it) }
    var currentNode = "AAA"
    var currentInstructionIndex = 0
    var stepCount = 0

    while (currentNode != "ZZZ") {
        val pair = nodePairsByNode[currentNode]!!

        currentNode =
            if (instructions[currentInstructionIndex] == 'L') pair.first
            else pair.second
        currentInstructionIndex = (currentInstructionIndex + 1) % instructions.length
        stepCount++
    }
    return stepCount
}

private fun part2(input: List<String>): Int {
    return input.size
}