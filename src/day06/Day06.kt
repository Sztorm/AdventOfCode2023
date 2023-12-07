package day06

import printAnswer
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

private fun main() {
    printAnswer(day = 6, testPart = 1, 288L, ::part1)
    printAnswer(day = 6, testPart = 2, 71503L, ::part2)
}

private val NUMBER_REGEX = Regex("([0-9]+)")

private fun findNumberOfWaysToBeatRecord(maxTime: Long, recordDistance: Long): Long {
    val preciseTime = maxTime.toDouble()
    val preciseDistance = recordDistance.toDouble()
    val delta = sqrt(preciseTime * preciseTime - 4.0 * preciseDistance)
    val preciseMinimumHeldTimeA: Double = (preciseTime - delta) * 0.5
    val preciseMinimumHeldTimeB: Double = (preciseTime + delta) * 0.5
    val epsilon = 0.001
    val minimumHeldTimeA = ceil(preciseMinimumHeldTimeA + epsilon).toLong()
    val minimumHeldTimeB = floor(preciseMinimumHeldTimeB - epsilon).toLong()

    return minimumHeldTimeB - minimumHeldTimeA + 1
}

private fun part1(input: List<String>): Long {
    val maxTimes: Sequence<Long> = NUMBER_REGEX
        .findAll(input[0])
        .map { it.value.toLong() }
    val recordDistances: Sequence<Long> = NUMBER_REGEX
        .findAll(input[1])
        .map { it.value.toLong() }

    return maxTimes
        .zip(recordDistances)
        .map { (t, d) -> findNumberOfWaysToBeatRecord(t, d) }
        .reduce { acc, n -> acc * n }
}

private fun part2(input: List<String>): Long {
    val maxTime: Long = NUMBER_REGEX
        .findAll(input[0])
        .fold("") { acc, matchResult -> acc + matchResult.value }
        .toLong()
    val maxDistance: Long = NUMBER_REGEX
        .findAll(input[1])
        .fold("") { acc, matchResult -> acc + matchResult.value }
        .toLong()

    return findNumberOfWaysToBeatRecord(maxTime, maxDistance)
}