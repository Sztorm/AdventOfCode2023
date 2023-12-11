@file:Suppress("unused")

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

class ByteArray2D(val rowCount: Int, val columnCount: Int) {
    private val array = ByteArray(rowCount * columnCount)

    operator fun get(row: Int, column: Int): Byte = array[row * columnCount + column]

    operator fun get(index: Pair<Int, Int>): Byte = array[index.first * columnCount + index.second]

    operator fun set(row: Int, column: Int, value: Byte) {
        array[row * columnCount + column] = value
    }

    operator fun set(index: Pair<Int, Int>, value: Byte) {
        array[index.first * columnCount + index.second] = value
    }

    fun isWithinBounds(row: Int, column: Int): Boolean =
        row.toUInt() < rowCount.toUInt() && column.toUInt() < columnCount.toUInt()

    fun count(predicate: (Byte) -> Boolean): Int = array.count(predicate)

    fun joinToString(rowSeparator: CharSequence = "\n", transform: (Byte) -> String): String {
        val sb = StringBuilder()
        for (row in 0..<rowCount) {
            for (column in 0..<columnCount) {
                sb.append(transform(this[row, column]))
            }
            sb.append(rowSeparator)
        }
        return sb.toString()
    }
}

inline fun printAnswer(day: Int, testPart: Int, expectedTestResult: Int, answerFunc: (List<String>) -> Int) {
    val dayNumberString = day.toString().padStart(length = 2, padChar = '0')
    val testInput: List<String> = readInput("day$dayNumberString/Day${dayNumberString}_test_part$testPart")

    check(answerFunc(testInput) == expectedTestResult)

    val input: List<String> = readInput("day$dayNumberString/Day$dayNumberString")

    println(answerFunc(input))
}

inline fun printAnswer(day: Int, testPart: Int, expectedTestResult: Long, answerFunc: (List<String>) -> Long) {
    val dayNumberString = day.toString().padStart(length = 2, padChar = '0')
    val testInput: List<String> = readInput("day$dayNumberString/Day${dayNumberString}_test_part$testPart")

    check(answerFunc(testInput) == expectedTestResult)

    val input: List<String> = readInput("day$dayNumberString/Day$dayNumberString")

    println(answerFunc(input))
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

tailrec fun greatestCommonDivisor(a: Long, b: Long): Long = when {
    b != 0L -> greatestCommonDivisor(b, a % b)
    else -> a
}

fun leastCommonMultiple(a: Long, b: Long): Long = a * (b / greatestCommonDivisor(a, b))