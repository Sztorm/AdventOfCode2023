fun main() {
    val testInput = readInput("Day01_test")

    check(part1(testInput) == 142)

    val input = readInput("Day01")

    println(part1(input))
    println(part2(input))
}

fun part1(input: List<String>): Int = input.sumOf { line: String ->
    val firstDigit: Int = line.first { it.isDigit() }.digitToInt()
    val lastDigit: Int = line.last { it.isDigit() }.digitToInt()

    firstDigit * 10 + lastDigit
}

fun part2(input: List<String>): Int {
    return input.size
}