import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sqrt

private fun main() {
    printAnswer("Day06", "Day06_test_part1", 288, ::part1)
    printAnswer("Day06", "Day06_test_part2", 0, ::part2)
}

private val NUMBER_REGEX = Regex("([0-9]+)")

private fun part1(input: List<String>): Int {
    val times: Sequence<Int> = NUMBER_REGEX
        .findAll(input[0])
        .map { it.value.toInt() }
    val distances: Sequence<Int> = NUMBER_REGEX
        .findAll(input[1])
        .map { it.value.toInt() }
    val product: Int = times
        .zip(distances)
        .map { (time, distance) ->
            val preciseTime = time.toDouble()
            val preciseDistance = distance.toDouble()
            val delta = sqrt(preciseTime * preciseTime - 4.0 * preciseDistance)
            val preciseMinimumHeldTimeA: Double = (preciseTime - delta) * 0.5
            val preciseMinimumHeldTimeB: Double = (preciseTime + delta) * 0.5
            val epsilon = 0.001
            val minimumHeldTimeA = ceil(preciseMinimumHeldTimeA + epsilon).toInt()
            val minimumHeldTimeB = floor(preciseMinimumHeldTimeB - epsilon).toInt()
            val beatingTimeCount = minimumHeldTimeB - minimumHeldTimeA + 1

            beatingTimeCount

        }.reduce { acc, n -> acc * n }

    return product
}

private fun part2(input: List<String>): Int {
    return input.size
}