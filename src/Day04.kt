private fun main() {
    printAnswer("Day04", "Day04_test_part1", 13, ::part1)
    printAnswer("Day04", "Day04_test_part2", 0, ::part2)
}

private fun part1(input: List<String>): Int = input.sumOf { line: String ->
    val (_, numbers) = line.split(':')
    val (winningNumbers, myNumbers) = numbers.split('|')
    val winningNumberSet = winningNumbers
        .split(' ')
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toSet()
    val myNumberSet = myNumbers
        .split(' ')
        .filter { it.isNotBlank() }
        .map { it.toInt() }
        .toSet()
    val myWinningNumberSet = winningNumberSet.intersect(myNumberSet)
    val points =
        if (myWinningNumberSet.isEmpty()) 0
        else 1 shl (myWinningNumberSet.size - 1)
    points
}

private fun part2(input: List<String>): Int {
    return input.size
}