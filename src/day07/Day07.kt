package day07

import printAnswer
import java.lang.IllegalStateException

private fun main() {
    printAnswer(day = 7, testPart = 1, 6440L, ::part1)
    printAnswer(day = 7, testPart = 2, 5905L, ::part2)
}

private enum class Card1 {
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    JACK,
    QUEEN,
    KING,
    ACE
}

private enum class HandStrength {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND
}

private class Hand1(first: Card1, second: Card1, third: Card1, fourth: Card1, fifth: Card1) : Comparable<Hand1> {
    private val cardArray = IntArray(5).apply {
        this[0] = first.ordinal
        this[1] = second.ordinal
        this[2] = third.ordinal
        this[3] = fourth.ordinal
        this[4] = fifth.ordinal
    }
    val strength: HandStrength = findStrength()

    private fun findStrength(): HandStrength {
        val cardSet = cardArray.toSet()
        val countsByCard = cardSet.associateBy(
            { uniqueCard -> uniqueCard },
            { uniqueCard -> cardArray.count { it == uniqueCard } }
        )
        return when (cardSet.size) {
            1 -> HandStrength.FIVE_OF_A_KIND
            2 -> when {
                countsByCard.values.contains(4) -> HandStrength.FOUR_OF_A_KIND
                countsByCard.values.contains(3) -> HandStrength.FULL_HOUSE
                else -> throw IllegalStateException()
            }

            3 -> when {
                countsByCard.values.contains(3) -> HandStrength.THREE_OF_A_KIND
                countsByCard.values.contains(2) -> HandStrength.TWO_PAIR
                else -> throw IllegalStateException()
            }

            4 -> when {
                countsByCard.values.contains(2) -> HandStrength.ONE_PAIR
                else -> throw IllegalStateException()
            }

            5 -> HandStrength.HIGH_CARD
            else -> throw IllegalStateException()
        }
    }

    operator fun get(index: Int) = Card1.entries[cardArray[index]]

    private fun toString(card: Card1) = when (card) {
        Card1.TWO -> "2"
        Card1.THREE -> "3"
        Card1.FOUR -> "4"
        Card1.FIVE -> "5"
        Card1.SIX -> "6"
        Card1.SEVEN -> "7"
        Card1.EIGHT -> "8"
        Card1.NINE -> "9"
        Card1.TEN -> "T"
        Card1.JACK -> "J"
        Card1.QUEEN -> "Q"
        Card1.KING -> "K"
        Card1.ACE -> "A"
    }

    override fun compareTo(other: Hand1): Int {
        return when {
            strength.ordinal > other.strength.ordinal -> 1
            strength.ordinal < other.strength.ordinal -> -1
            else -> {
                for (i in 0..4) {
                    if (cardArray[i] > other.cardArray[i]) {
                        return 1
                    } else if (cardArray[i] < other.cardArray[i]) {
                        return -1
                    }
                }
                0
            }
        }
    }

    override fun toString(): String = cardArray.joinToString("") { toString(Card1.entries[it]) }
}

private fun parseCard1(input: Char) = when (input) {
    '2' -> Card1.TWO
    '3' -> Card1.THREE
    '4' -> Card1.FOUR
    '5' -> Card1.FIVE
    '6' -> Card1.SIX
    '7' -> Card1.SEVEN
    '8' -> Card1.EIGHT
    '9' -> Card1.NINE
    'T' -> Card1.TEN
    'J' -> Card1.JACK
    'Q' -> Card1.QUEEN
    'K' -> Card1.KING
    'A' -> Card1.ACE
    else -> null
}

private fun parseHand1(input: String): Hand1? {
    if (input.length != 5) {
        return null
    }
    val cards = input.map { parseCard1(it) }

    return if (cards.any { it == null }) null
    else Hand1(cards[0]!!, cards[1]!!, cards[2]!!, cards[3]!!, cards[4]!!)
}

private fun parseHand1Card1Pairs(input: List<String>) = input.map {
    val (handPart, bidPart) = it.split(' ')
    Pair(parseHand1(handPart)!!, bidPart.toInt())
}

private fun part1(input: List<String>): Long {
    val handBidPairs = parseHand1Card1Pairs(input)
    val sortedHandBidPairs = handBidPairs
        .sortedWith { (handA, _), (handB, _) -> handA.compareTo(handB) }

    return sortedHandBidPairs
        .foldIndexed(0L) { i, acc, (_, bid) -> acc + (i + 1) * bid }
}

private enum class Card2 {
    JOKER,
    TWO,
    THREE,
    FOUR,
    FIVE,
    SIX,
    SEVEN,
    EIGHT,
    NINE,
    TEN,
    QUEEN,
    KING,
    ACE
}

private class Hand2(first: Card2, second: Card2, third: Card2, fourth: Card2, fifth: Card2) : Comparable<Hand2> {
    private val cardArray = IntArray(5).apply {
        this[0] = first.ordinal
        this[1] = second.ordinal
        this[2] = third.ordinal
        this[3] = fourth.ordinal
        this[4] = fifth.ordinal
    }
    val strength: HandStrength = findStrength()

    private fun findStrength(): HandStrength {
        val cardSet = cardArray.toSet()
        val countsByCard = cardSet.associateBy(
            { uniqueCard -> uniqueCard },
            { uniqueCard -> cardArray.count { it == uniqueCard } }
        )
        val jokerCount = countsByCard[Card2.JOKER.ordinal] ?: 0

        return when (cardSet.size) {
            1 -> HandStrength.FIVE_OF_A_KIND
            2 -> {
                if (countsByCard.values.contains(4)) {
                    when (jokerCount) {
                        0 -> HandStrength.FOUR_OF_A_KIND
                        1 -> HandStrength.FIVE_OF_A_KIND
                        4 -> HandStrength.FIVE_OF_A_KIND
                        else -> throw IllegalStateException()
                    }
                } else if (countsByCard.values.contains(3)) {
                    when (jokerCount) {
                        0 -> HandStrength.FULL_HOUSE
                        2 -> HandStrength.FIVE_OF_A_KIND
                        3 -> HandStrength.FIVE_OF_A_KIND
                        else -> throw IllegalStateException()
                    }
                } else throw IllegalStateException()
            }

            3 -> {
                if (countsByCard.values.contains(3)) {
                    when (jokerCount) {
                        0 -> HandStrength.THREE_OF_A_KIND
                        1 -> HandStrength.FOUR_OF_A_KIND
                        3 -> HandStrength.FOUR_OF_A_KIND
                        else -> throw IllegalStateException()
                    }
                } else if (countsByCard.values.contains(2)) {
                    when (jokerCount) {
                        0 -> HandStrength.TWO_PAIR
                        1 -> HandStrength.FULL_HOUSE
                        2 -> HandStrength.FOUR_OF_A_KIND
                        else -> throw IllegalStateException()
                    }
                } else throw IllegalStateException()
            }

            4 -> {
                if (countsByCard.values.contains(2)) {
                    when (jokerCount) {
                        0 -> HandStrength.ONE_PAIR
                        1 -> HandStrength.THREE_OF_A_KIND
                        2 -> HandStrength.THREE_OF_A_KIND
                        else -> throw IllegalStateException()
                    }
                } else throw IllegalStateException()
            }

            5 -> when (jokerCount) {
                0 -> HandStrength.HIGH_CARD
                else -> HandStrength.ONE_PAIR
            }

            else -> throw IllegalStateException()
        }
    }

    operator fun get(index: Int) = Card2.entries[cardArray[index]]

    private fun toString(card: Card2) = when (card) {
        Card2.JOKER -> "J"
        Card2.TWO -> "2"
        Card2.THREE -> "3"
        Card2.FOUR -> "4"
        Card2.FIVE -> "5"
        Card2.SIX -> "6"
        Card2.SEVEN -> "7"
        Card2.EIGHT -> "8"
        Card2.NINE -> "9"
        Card2.TEN -> "T"
        Card2.QUEEN -> "Q"
        Card2.KING -> "K"
        Card2.ACE -> "A"
    }

    override fun compareTo(other: Hand2): Int {
        return when {
            strength.ordinal > other.strength.ordinal -> 1
            strength.ordinal < other.strength.ordinal -> -1
            else -> {
                for (i in 0..4) {
                    if (cardArray[i] > other.cardArray[i]) {
                        return 1
                    } else if (cardArray[i] < other.cardArray[i]) {
                        return -1
                    }
                }
                0
            }
        }
    }

    override fun toString(): String = cardArray.joinToString("") { toString(Card2.entries[it]) }
}

private fun parseCard2(input: Char) = when (input) {
    'J' -> Card2.JOKER
    '2' -> Card2.TWO
    '3' -> Card2.THREE
    '4' -> Card2.FOUR
    '5' -> Card2.FIVE
    '6' -> Card2.SIX
    '7' -> Card2.SEVEN
    '8' -> Card2.EIGHT
    '9' -> Card2.NINE
    'T' -> Card2.TEN
    'Q' -> Card2.QUEEN
    'K' -> Card2.KING
    'A' -> Card2.ACE
    else -> null
}

private fun parseHand2(input: String): Hand2? {
    if (input.length != 5) {
        return null
    }
    val cards = input.map { parseCard2(it) }

    return if (cards.any { it == null }) null
    else Hand2(cards[0]!!, cards[1]!!, cards[2]!!, cards[3]!!, cards[4]!!)
}

private fun parseHand2Card2Pairs(input: List<String>) = input.map {
    val (handPart, bidPart) = it.split(' ')
    Pair(parseHand2(handPart)!!, bidPart.toInt())
}

private fun part2(input: List<String>): Long {
    val handBidPairs = parseHand2Card2Pairs(input)
    val sortedHandBidPairs = handBidPairs
        .sortedWith { (handA, _), (handB, _) -> handA.compareTo(handB) }

    return sortedHandBidPairs
        .foldIndexed(0L) { i, acc, (_, bid) -> acc + (i + 1) * bid }
}