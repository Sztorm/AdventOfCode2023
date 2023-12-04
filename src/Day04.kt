private fun main() {
    printAnswer("Day04", "Day04_test_part1", 13, ::part1)
    printAnswer("Day04", "Day04_test_part2", 30, ::part2)
}

private data class Card(val winningNumbers: Set<Int>, val scratchedNumbers: Set<Int>) {
    val matchedNumbers: Set<Int> = winningNumbers.intersect(scratchedNumbers)

    companion object {
        fun parse(text: String): Card {
            val (_, numbers) = text.split(':')
            val (winningNumbers, scratchedNumbers) = numbers.split('|')
            val winningNumberSet = winningNumbers
                .split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()
            val scratchedNumberSet = scratchedNumbers
                .split(' ')
                .filter { it.isNotBlank() }
                .map { it.toInt() }
                .toSet()

            return Card(winningNumberSet, scratchedNumberSet)
        }
    }
}

private fun part1(input: List<String>): Int = input.sumOf { line: String ->
    val card = Card.parse(line)
    val points =
        if (card.matchedNumbers.isEmpty()) 0
        else 1 shl (card.matchedNumbers.size - 1)

    points
}

private fun part2(input: List<String>): Int {
    val cards = input.map { Card.parse(it) }
    val counts = IntArray(cards.size) { 1 }
    var total = 0

    for (i in counts.indices) {
        val card = cards[i]
        val count = counts[i]
        total += count

        for (j in 0..<card.matchedNumbers.size) {
            counts[i + j + 1] += count
        }
    }
    return total
}