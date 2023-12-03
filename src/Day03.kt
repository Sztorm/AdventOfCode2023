private fun main() {
    printAnswer("Day03", "Day03_test_part1", 4361, ::part1)
    printAnswer("Day03", "Day03_test_part2", 0, ::part2)
}

private fun findSymbol(input: List<String>, i: Int, j: Int, length: Int): Char {
    if (j - 1 >= 0) {
        if (i - 1 >= 0 && input[i - 1][j - 1] != '.') {
            return input[i - 1][j - 1]
        }
        if (input[i][j - 1] != '.') {
            return input[i][j - 1]
        }
        if (i + 1 < input.size && input[i + 1][j - 1] != '.') {
            return input[i + 1][j - 1]
        }
    }
    var k = j

    if (i - 1 >= 0 && i + 1 < input.size) {
        while (k - j < length) {
            if (input[i - 1][k] != '.') {
                return input[i - 1][k]
            }
            if (input[i + 1][k] != '.') {
                return input[i + 1][k]
            }
            k++
        }
    } else if (i - 1 >= 0) {
        while (k - j < length) {
            if (input[i - 1][k] != '.') {
                return input[i - 1][k]
            }
            k++
        }
    } else {
        while (k - j < length) {
            if (input[i + 1][k] != '.') {
                return input[i + 1][k]
            }
            k++
        }
    }
    if (k < input.size) {
        if (i - 1 >= 0 && input[i - 1][k] != '.') {
            return input[i - 1][k]
        }
        if (input[i][k] != '.') {
            return input[i][k]
        }
        if (i + 1 < input.size && input[i + 1][k] != '.') {
            return input[i + 1][k]
        }
    }
    return '.'
}

private fun getNumber(numChars: List<Char>): Int {
    var num = 0
    var power = 1

    for (char in numChars.reversed()) {
        num += char.digitToInt() * power
        power *= 10
    }
    return num
}

private fun part1(input: List<String>): Int {
    var sum = 0
    val numChars = ArrayList<Char>()
    var i = 0
    var j = 0

    while (i < input.size) {
        while (j < input[i].length) {
            val startJ = j

            while (j < input[i].length && input[i][j].isDigit()) {
                numChars.add(input[i][j])
                j++
            }
            if (numChars.isNotEmpty()) {
                val symbol = findSymbol(input, i, startJ, numChars.size)

                if (symbol != '.') {
                    val num = getNumber(numChars)
                    sum += num
                }
                numChars.clear()
            }
            j++
        }
        i++
        j = 0
    }
    return sum
}

private fun part2(input: List<String>): Int {
    return input.size
}