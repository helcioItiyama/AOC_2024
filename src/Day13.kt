fun main() {
    fun getArcade(input: List<String>) = input.filter { it.isNotEmpty() }.map {
        val searchNumbers = """\d+""".toRegex()
        val (left, right) = searchNumbers.findAll(it).map { it.value }.toList()
        left.toDouble() to right.toDouble()
    }.chunked(3)

    fun getCounts(game: List<Pair<Double, Double>>, increase: Long = 0L): Pair<Double, Double> {
        val prizeX = game[2].first + increase
        val prizeY = game[2].second + increase
        val buttonAx = game[0].first
        val buttonAy = game[0].second
        val buttonBx = game[1].first
        val buttonBy = game[1].second
        val countA = (prizeX * buttonBy - prizeY * buttonBx) / (buttonAx * buttonBy - buttonAy * buttonBx)
        val countB = (prizeX - buttonAx * countA) / buttonBx
        return countA to countB
    }

    fun part1(input: List<String>): Long {
        val arcades = getArcade(input)
        var sum = 0L

        for (game in arcades) {
            val (countA, countB) = getCounts(game)
            if (countA % 1 == 0.0 && countB % 1 == 0.0) sum += (countA * 3 + countB).toLong()
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        val arcades = getArcade(input)
        var sum = 0L

        for (game in arcades) {
            val (countA, countB) = getCounts(game, 10_000_000_000_000)
            if (countA % 1 == 0.0 && countB % 1 == 0.0) sum += (countA * 3 + countB).toLong()
        }

        return sum
    }

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}