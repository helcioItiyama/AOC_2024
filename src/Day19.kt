import kotlin.math.min

fun getTowelsAndPatterns (input: List<String>): Pair<List<String>, List<String>> {
    val index = input.indexOf("")
    val (first, second) = input.withIndex().partition { it.index < index }
    val towels = first.flatMap { it.value.split(", ") }
    val patterns = second.drop(1).map { it.value }
    return towels to patterns
}

fun main() {
    fun part1(input: List<String>): Int {
        val cache = HashMap<String, Boolean>()
        val (towels, patterns) = getTowelsAndPatterns(input)
        val maxTowelLength = towels.maxOf { it.length }

        fun checkPatterns(design: String): Boolean {
            if(cache.containsKey(design)) return cache[design]!!
            if (design == "") return true
            for (i in 1 .. min(design.length, maxTowelLength)) {
                if (towels.contains(design.take(i)) && checkPatterns(design.drop(i))) {
                    cache[design] = true
                    return true
                }
            }
            cache[design] = false
            return false
        }
        return patterns.count { checkPatterns(it) }
    }

    fun part2(input: List<String>): Long {
        val cacheSum = HashMap<String, Long>()
        val (towels, patterns) = getTowelsAndPatterns(input)
        val maxTowelLength = towels.maxOf { it.length }

        fun checkOptions(design: String): Long {
            if(cacheSum.containsKey(design)) return cacheSum[design]!!
            if (design == "") return 1
            var count = 0L
            for (i in 1 ..min(design.length, maxTowelLength)) {
                val hasTowel = towels.contains(design.take(i))
                if (hasTowel) {
                    count += checkOptions(design.drop(i))
                }
            }
            cacheSum[design] = count
            return count
        }
        return patterns.sumOf(::checkOptions)
    }

    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}