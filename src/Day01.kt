fun main() {
    printAnswers("Day01", "Day01_test_part1", 142, ::part1)
    printAnswers("Day01", "Day01_test_part2", 281, ::part2)
}

inline fun printAnswers(
    inputName: String, testInputName: String, expectedTestResult: Int, answerFunc: (List<String>) -> Int
) {
    val testInput: List<String> = readInput(testInputName)

    check(answerFunc(testInput) == expectedTestResult)

    val input: List<String> = readInput(inputName)

    println(answerFunc(input))
}

fun part1(input: List<String>): Int = input.sumOf { line: String ->
    val firstDigit: Int = line.first { it.isDigit() }.digitToInt()
    val lastDigit: Int = line.last { it.isDigit() }.digitToInt()

    firstDigit * 10 + lastDigit
}

private val JOINED_DIGIT_LETTERS_TO_DIGITS = mapOf(
    "oneight" to "18",
    "twone" to "21",
    "threeight" to "38",
    "fiveight" to "58",
    "sevenine" to "79",
    "eightwo" to "82",
    "nineight" to "98",
)

private val SINGLE_LETTER_DIGITS_TO_DIGITS = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9",
)

private val JOINED_DIGIT_LETTER_REGEX = Regex(
    "(oneight)|(twone)|(threeight)|(fiveight)|(sevenine)|(eightwo)|(nineight)"
)
private val SINGLE_DIGIT_LETTER_REGEX = Regex(
    "(one)|(two)|(three)|(four)|(five)|(six)|(seven)|(eight)|(nine)"
)

fun part2(input: List<String>): Int = input.sumOf { line: String ->
    val digitizedLine = JOINED_DIGIT_LETTER_REGEX.replace(line) { matchResult ->
        JOINED_DIGIT_LETTERS_TO_DIGITS.getOrDefault(matchResult.value, "")
    }.let {
        SINGLE_DIGIT_LETTER_REGEX.replace(it) { matchResult ->
            SINGLE_LETTER_DIGITS_TO_DIGITS.getOrDefault(matchResult.value, "")
        }
    }
    val firstDigit: Int = digitizedLine.first { it.isDigit() }.digitToInt()
    val lastDigit: Int = digitizedLine.last { it.isDigit() }.digitToInt()

    firstDigit * 10 + lastDigit
}