@file:Suppress("unused")

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

inline fun printAnswer(
    inputName: String, testInputName: String, expectedTestResult: Int, answerFunc: (List<String>) -> Int
) {
    val testInput: List<String> = readInput(testInputName)

    check(answerFunc(testInput) == expectedTestResult)

    val input: List<String> = readInput(inputName)

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