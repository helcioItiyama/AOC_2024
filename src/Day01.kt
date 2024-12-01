import kotlin.math.abs

fun getData(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> =
    input.fold(Pair(mutableListOf<Int>(), mutableListOf<Int>())) {
        (first, second), item ->
        val (left, right) = item.split("  ")
        first.add(left.trim().toInt())
        second.add(right.trim().toInt())
        Pair(first, second)
    }

fun getData2(input: List<String>): Pair<HashMap<Int, Int>, MutableList<Int>> =
    input.fold(Pair(HashMap<Int, Int>(), mutableListOf<Int>())) {
        (first, second), item ->
        val (left, right) = item.split("  ")
        first[left.trim().toInt()] = 0
        second.add(right.trim().toInt())
        Pair(first, second)
    }

fun main() {
    fun part1(input: List<String>): Int {
        var sum = 0
        val (initial, final) = getData(input)
        initial.sort()
        final.sort()
        input.forEachIndexed { i, _  ->
            sum += abs(initial[i] - final[i])
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        val (initial, final) = getData2(input)
        final.forEach {
            if (initial.containsKey(it)) initial[it] = initial[it]?.plus(it) ?: 0
        }
        for ((_, values) in initial) {
            sum += values
        }
        return sum
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
