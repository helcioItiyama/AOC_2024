fun main() {
    fun part1(input: List<String>): Int {
        val edge = 0
        val size = 70
        val bytes = 1024

        val grid = MutableList(size + 1) { MutableList(size + 1) { "." } }
        input.map { it.split(",").map { it.toInt() } }.forEachIndexed{ i, (c, r) ->
            if( i < bytes) grid[r][c] = "#"
        }

        val queue = mutableListOf(listOf(0, 0, 0))
        val hasSeen = mutableSetOf(Pair(0,0))
        var minSteps = 0

        while(queue.isNotEmpty()) {
            val (row, col, steps) = queue.removeFirst()
            val directions = listOf(Pair(row + 1, col), Pair(row, col + 1), Pair(row - 1, col), Pair(row, col - 1))
            for ((nextRow, nextCol) in directions) {
                when {
                    nextRow < edge || nextCol < edge || nextRow > size || nextCol > size -> continue
                    grid[nextRow][nextCol] == "#" -> continue
                    hasSeen.contains(Pair(nextRow, nextCol)) -> continue
                    nextRow == size && nextCol == size -> {
                        minSteps = steps + 1
                        break
                    }
                    else -> {
                        hasSeen.add(Pair(nextRow, nextCol))
                        queue.add(listOf(nextRow, nextCol, steps + 1))
                    }
                }
            }
        }

        return minSteps
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day18")
    part1(input).println()
//    part2(input).println()
}