typealias CheckEndOfCounting = (r: Int, c: Int, pos: Int) -> Boolean

fun main() {
    class TopographicMap (input: List<String>) {
        val grid = input.map {
            it.split("").drop(1).dropLast(1).map { e -> e.toInt() }
        }
        private val left = 0
        private val right = grid[0].lastIndex
        private val top = 0
        private val bottom = grid.lastIndex
        val rowIndices = grid.indices
        val colIndices = grid[0].indices

        fun startClimbing(row: Int, col: Int, pos: Int, callback: CheckEndOfCounting ) {
            if (callback(row, col, pos)) return
            val nextStep = pos + 1
            // Go left
            if (col - 1 >= left && grid[row][col - 1] == nextStep)  {
                startClimbing(row, col - 1, grid[row][col - 1], callback)
            }
            // Go right
            if (col + 1 <= right && grid[row][col + 1] == nextStep) {
                startClimbing(row, col + 1, grid[row][col + 1], callback)
            }
            // Go top
            if (row - 1 >= top && grid[row - 1][col] == nextStep) {
                startClimbing(row - 1, col, grid[row - 1][col], callback)
            }
            // Go bottom
            if (row + 1 <= bottom && grid[row + 1][col] == nextStep) {
                startClimbing(row + 1, col, grid[row + 1][col], callback)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val topographicMap = TopographicMap(input)
        var count = 0
        val scores = mutableListOf<Int>()
        var peaks = mutableListOf<Pair<Int, Int>>()

        fun checkEndOfCounting(row: Int, col: Int, pos: Int): Boolean {
            if(pos == 9) {
                if (peaks.any { it.first == row && it.second == col }) return true
                count += 1
                peaks.add(Pair(row, col))
                return true
            }
            return false
        }

        for (row in topographicMap.rowIndices) {
            for (col in topographicMap.colIndices ) {
                if (topographicMap.grid[row][col] == 0) {
                    topographicMap.startClimbing(
                        row,
                        col,
                        topographicMap.grid[row][col],
                        ::checkEndOfCounting)
                    scores.add(count)
                    count = 0
                    peaks = mutableListOf()
                }
            }
        }
        return scores.sum()
    }

    fun part2(input: List<String>): Int {
        val topographicMap = TopographicMap(input)
        var count = 0
        val scores = mutableListOf<Int>()

        fun checkEndOfCounting(row: Int, col: Int, pos: Int): Boolean {
            if(pos == 9) {
                count += 1
                return true
            }
            return false
        }

        for (row in topographicMap.rowIndices) {
            for (col in topographicMap.colIndices ) {
                if (topographicMap.grid[row][col] == 0) {
                    topographicMap.startClimbing(
                        row,
                        col,
                        topographicMap.grid[row][col],
                        ::checkEndOfCounting)
                    scores.add(count)
                    count = 0
                }
            }
        }
        return scores.sum()
    }

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}