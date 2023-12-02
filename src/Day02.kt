import kotlin.math.max

private fun main() {
    printAnswers("Day02", "Day02_test_part1", 8, ::part1)
    printAnswers("Day02", "Day02_test_part2", 2286, ::part2)
}

private const val MAX_RED_COUNT = 12
private const val MAX_GREEN_COUNT = 13
private const val MAX_BLUE_COUNT = 14
private val GAME_ID_REGEX = Regex("Game ([0-9]+)")
private val RED_REGEX = Regex("([0-9]+) red")
private val GREEN_REGEX = Regex("([0-9]+) green")
private val BLUE_REGEX = Regex("([0-9]+) blue")

private fun part1(input: List<String>): Int = input.sumOf { line: String ->
    val (gameId, gamesLine) = line.split(':')
    val games = gamesLine.split(';')
    val id = GAME_ID_REGEX.find(gameId)!!.groupValues[1].toInt()
    val isPossible = games.all {
        val r = RED_REGEX.find(it)?.groupValues?.getOrElse(1) { "0" }?.toInt() ?: 0
        val g = GREEN_REGEX.find(it)?.groupValues?.getOrElse(1) { "0" }?.toInt() ?: 0
        val b = BLUE_REGEX.find(it)?.groupValues?.getOrElse(1) { "0" }?.toInt() ?: 0

        r <= MAX_RED_COUNT && g <= MAX_GREEN_COUNT && b <= MAX_BLUE_COUNT
    }
    if (isPossible) id
    else 0
}

private fun part2(input: List<String>): Int = input.sumOf { line: String ->
    val (_, gamesLine) = line.split(':')
    val games = gamesLine.split(';')
    val (maxR, maxG, maxB) = games.map {
        val r = RED_REGEX.find(it)?.groupValues?.getOrElse(1) { "0" }?.toInt() ?: 0
        val g = GREEN_REGEX.find(it)?.groupValues?.getOrElse(1) { "0" }?.toInt() ?: 0
        val b = BLUE_REGEX.find(it)?.groupValues?.getOrElse(1) { "0" }?.toInt() ?: 0

        Triple(r, g, b)
    }.fold(Triple(0, 0, 0)) { acc, triple ->
        Triple(
            max(acc.first, triple.first),
            max(acc.second, triple.second),
            max(acc.third, triple.third)
        )
    }
    maxR * maxG * maxB
}