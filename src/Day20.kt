import kotlin.math.abs

fun main() {
    class Matrix (input: List<String>) {
        private var matrix = input.map {
            it.split("").drop(1).dropLast(1).toMutableList()
        }.toMutableList()
        private var r = 0
        private var c = 0
        val edge = 0
        val size = matrix.lastIndex
        private val distances = MutableList(size + 1) { MutableList(size + 1) { -1 } }

        init {
            outerLoop@ for (row in edge..size) {
                for (col in edge..size) {
                    if (matrix[row][col] == "S") {
                        r = row
                        c = col
                        break@outerLoop
                    }
                }
            }
            distances[r][c] = 0

            while (matrix[r][c] != "E") {
                val directions = listOf(r - 1 to c, r to c + 1, r + 1 to c, r to c - 1)
                for ((nr, nc) in directions) {
                    when {
                        checkEdges(nr, nc) -> continue
                        checkIfItsWall(nr, nc) -> continue
                        distances[nr][nc] != -1 -> continue
                        else -> {
                            distances[nr][nc] = distances[r][c] + 1
                            r = nr
                            c = nc
                        }
                    }
                }
            }
        }

        fun checkIfItsWall(r: Int, c: Int): Boolean = matrix[r][c] == "#"

        fun checkEdges(r: Int, c: Int): Boolean = r < edge || c < edge || r >= size || c >= size

        fun calculateDistance(initRow: Int, initCol: Int, finalRow: Int, finalCol: Int): Int =
            distances[initRow][initCol] - distances[finalRow][finalCol]
    }

    fun part1(input: List<String>): Int {
        val matrix = Matrix(input)
        var count = 0

        for(row in matrix.edge .. matrix.size) {
            for(col in matrix.edge .. matrix.size) {
                if(matrix.checkIfItsWall(row,col)) continue
                val possibleDir = listOf(row + 2 to col, row + 1 to col + 1, row to col + 2, row - 1 to col + 1)
                for ((nr, nc) in possibleDir) {
                    when {
                        matrix.checkEdges(nr, nc) -> continue
                        matrix.checkIfItsWall(nr, nc) -> continue
                        abs(matrix.calculateDistance(row, col, nr, nc)) >= 102 -> count += 1
                    }
                }
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        val matrix = Matrix(input)
        var count = 0

        for(row in matrix.edge .. matrix.size) {
            for(col in matrix.edge .. matrix.size) {
                if(matrix.checkIfItsWall(row,col)) continue
                for(radius in 2..20) {
                    for (dr in 0..radius) {
                        val dc = radius - dr
                        val possibleDir = setOf(
                            row + dr to col + dc,
                            row + dr to col - dc,
                            row - dr to col + dc,
                            row - dr to col - dc
                        )
                        for ((nr, nc) in possibleDir) {
                            when {
                                matrix.checkEdges(nr, nc) -> continue
                                matrix.checkIfItsWall(nr, nc) -> continue
                                matrix.calculateDistance(row, col, nr, nc) >= 100 + radius -> count += 1
                            }
                        }
                    }
                }
            }
        }

        return count
    }

    val input = readInput("Day20")
    part1(input).println()
    part2(input).println()
}