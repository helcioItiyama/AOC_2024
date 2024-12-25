import kotlin.math.floor

fun main() {
    fun part1(input: List<String>): Int {
        val seconds = 100
        val line = 103
        val column = 101

        return input.asSequence().map {
            val validate = """(-)?\d+""".toRegex()
            validate.findAll(it).map { e -> e.value.toInt() }.toList()
        }.map {
            val (x, y, mx, my) = it
            val newXPosition = x + seconds * mx
            val newYPosition = y + seconds * my
            val xAbsModule = (newXPosition % column + column) % column
            val yAbsModule = (newYPosition % line + line) % line
            Pair(xAbsModule, yAbsModule)
        }.filter { (x, y) ->
            val isXMiddle = x == floor(column.toDouble() / 2).toInt()
            val isYMiddle = y == floor(line.toDouble() / 2).toInt()
            !(isXMiddle || isYMiddle)
        }.fold(mutableListOf(0,0,0,0)) { acc, (x, y) ->
            val horizontalHalf = floor((column -1).toDouble() / 2).toInt()
            val verticalHalf = floor((line - 1).toDouble() / 2).toInt()
            if (x < horizontalHalf) {
                if(y < verticalHalf) { acc[0] += 1 } else { acc[2] += 1 }
            } else {
                if (y < verticalHalf) { acc[1] += 1 } else { acc[3] += 1 }
            }
            acc
        }.reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("test")
    part1(input).println()
//    part2(input).println()
}
