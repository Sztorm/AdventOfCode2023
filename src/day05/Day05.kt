package day05

import printAnswer

private fun main() {
    printAnswer(day = 5, testPart = 1, 35L, ::part1)
    printAnswer(day = 5, testPart = 2, 46L, ::part2)
}

private data class Range(val destinationStart: Long, val sourceStart: Long, val length: Long) {
    fun getMappedNumber(number: Long): Long =
        if (number in sourceStart..<sourceStart + length) destinationStart + number - sourceStart
        else -1L
}

private enum class MapType {
    SEED_TO_SOIL,
    SOIL_TO_FERTILIZER,
    FERTILIZER_TO_WATER,
    WATER_TO_LIGHT,
    LIGHT_TO_TEMPERATURE,
    TEMPERATURE_TO_HUMIDITY,
    HUMIDITY_TO_LOCATION
}

private data class MappedSeed(
    val seed: Long,
    val soil: Long,
    val fertilizer: Long,
    val water: Long,
    val light: Long,
    val temperature: Long,
    val humidity: Long,
    val location: Long
)

private class Almanac(val seeds: List<Long>, private val maps: Array<out List<Range>>) {
    fun getMapBy(type: MapType): List<Range> = maps[type.ordinal]

    fun List<Range>.getMappedNumber(sourceNum: Long): Long {
        for (range in this) {
            val num = range.getMappedNumber(sourceNum)
            if (num > 0) {
                return num
            }
        }
        return sourceNum
    }

    fun getMappedSeed(seed: Long): MappedSeed {
        val soil = getMapBy(MapType.SEED_TO_SOIL).getMappedNumber(seed)
        val fertilizer = getMapBy(MapType.SOIL_TO_FERTILIZER).getMappedNumber(soil)
        val water = getMapBy(MapType.FERTILIZER_TO_WATER).getMappedNumber(fertilizer)
        val light = getMapBy(MapType.WATER_TO_LIGHT).getMappedNumber(water)
        val temperature = getMapBy(MapType.LIGHT_TO_TEMPERATURE).getMappedNumber(light)
        val humidity = getMapBy(MapType.TEMPERATURE_TO_HUMIDITY).getMappedNumber(temperature)
        val location = getMapBy(MapType.HUMIDITY_TO_LOCATION).getMappedNumber(humidity)

        return MappedSeed(seed, soil, fertilizer, water, light, temperature, humidity, location)
    }
}

private fun parseSeeds(input: String): List<Long> = input
    .substring(startIndex = "seeds:".length)
    .split(" ")
    .filter { it.isNotBlank() }
    .map { it.toLong() }

private fun parseRange(input: String): Range = input
    .split(' ')
    .filter { it.isNotBlank() }
    .let { Range(it[0].toLong(), it[1].toLong(), it[2].toLong()) }

private fun parseAlmanac(input: List<String>): Almanac {
    val seeds = parseSeeds(input[0])
    val mapArray = Array<ArrayList<Range>>(7) { ArrayList() }
    var i = 0
    var j = -1

    while (++i < input.size) {
        if (input[i].isEmpty()) {
            i += 2
            j++
        }
        mapArray[j].add(parseRange(input[i]))
    }
    return Almanac(seeds, mapArray)
}

private fun part1(input: List<String>): Long {
    val almanac = parseAlmanac(input)

    return almanac.seeds
        .map { almanac.getMappedSeed(it) }
        .minOf { it.location }
}

private fun part2(input: List<String>): Long {
    return input.size.toLong()
}