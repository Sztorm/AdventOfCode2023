package day08

import leastCommonMultiple
import printAnswer

private fun main() {
    printAnswer(day = 8, testPart = 1, 6L, ::part1)
    printAnswer(day = 8, testPart = 2, 6L, ::part2)
}

private val NODE_REGEX = Regex("[0-9A-Z]{3}")

private fun parseNodePair(input: String) = NODE_REGEX
    .findAll(input)
    .toList()
    .map { it.value }
    .let { Pair(it[0], Pair(it[1], it[2])) }

private fun part1(input: List<String>): Long {
    val instructions: String = input[0]
    val nodePairsByNode: Map<String, Pair<String, String>> = input
        .subList(fromIndex = 2, toIndex = input.size)
        .associate { parseNodePair(it) }
    val startNode = "AAA"

    return countStepsWhile(nodePairsByNode, instructions, startNode) { it != "ZZZ" }
}

private inline fun countStepsWhile(
    nodePairsByNode: Map<String, Pair<String, String>>,
    instructions: String,
    startNode: String,
    match: (String) -> Boolean
): Long {
    var currentNode = startNode
    var currentInstructionIndex = 0
    var stepCount = 0L

    while (match(currentNode)) {
        val pair = nodePairsByNode[currentNode]!!

        currentNode =
            if (instructions[currentInstructionIndex] == 'L') pair.first
            else pair.second
        currentInstructionIndex = (currentInstructionIndex + 1) % instructions.length
        stepCount++
    }
    return stepCount
}

private fun part2(input: List<String>): Long {
    val instructions: String = input[0]
    val nodePairsByNode: Map<String, Pair<String, String>> = input
        .subList(fromIndex = 2, toIndex = input.size)
        .associate { parseNodePair(it) }
    val startNodes: List<String> = nodePairsByNode.keys
        .filter { it[2] == 'A' }
    val stepCountsToAchieveZ: List<Long> = startNodes.map { startNode ->
        countStepsWhile(nodePairsByNode, instructions, startNode) { it[2] != 'Z' }
    }
    return stepCountsToAchieveZ.reduce { acc, n -> leastCommonMultiple(acc, n) }
}