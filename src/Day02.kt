import kotlin.math.abs

fun checkIfIsSafe(line: List<Int>): Boolean {
    var isSafe = true
    val isIncreasing = line[1] > line[0]
    for (i in 0 until line.lastIndex) {
        if(isIncreasing) {
            val diff = line[i + 1] - line[i]
            if(diff > 3 || diff < 1) {
                isSafe = false
                break
            }
        } else {
            val diff = line[i] - line[i + 1]
            if(diff > 3 || diff < 1) {
                isSafe = false
                break
            }
        }
    }
    return isSafe
}

fun main() {
    fun part1(input: List<String>): Int {
        val levels = input.map { level ->
            level.split(" ").map { it.toInt() }
        }
        var count = 0
        levels.forEach { level ->
            val isSafe = checkIfIsSafe(level)
            if(isSafe) count++
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val levels = input.map { level ->
            level.split(" ").map { it.toInt() }
        }
        var count = 0
        levels.forEach { level ->
            var isSafe = false
            for (i in 0..level.lastIndex) {
                isSafe = checkIfIsSafe(level.filterIndexed { index, _ -> index != i })
                if(isSafe) break
           }
            if(isSafe) count++
        }
        return count
    }

    // Test if implementation meets criteria from the description, like:
    // check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    // val testInput = readInput("Day01_test")
    // check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}