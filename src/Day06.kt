fun main() {
    class Matrix(input: List<String>) {
        val data = input
            .map { it.split("").drop(1).dropLast(1).toMutableList() }
            .toMutableList()
        var row = 0
        var col = 0
        val sizes = data.size
        val edges = 0

        init {
            for (r in 0  until sizes) {
                for (c in 0 until sizes) {
                    if(data[r][c] == "^") {
                        row = r
                        col= c
                        break
                    }
                }
            }
        }

        fun checkIfsItsBlocked(mr: Int, mc: Int) = data[row + mr][col + mc] == "#"
    }

    fun part1(input: List<String>): Int {
        val matrix = Matrix(input)
        val positions = mutableSetOf<Pair<Int, Int>>()
        var movHor = -1
        var movVer = 0

        while (true) {
            positions.add(Pair(matrix.row, matrix.col))
            if (
                matrix.row + movHor < matrix.edges
                || matrix.row + movHor >= matrix.sizes
                || matrix.col + movVer < matrix.edges
                || matrix.col + movVer >= matrix.sizes
            ) break
            if (matrix.checkIfsItsBlocked(movHor,movVer)) {
                val temp = movVer
                movVer = -movHor
                movHor = temp
            } else {
                matrix.row += movHor
                matrix.col += movVer
            }
        }

        return positions.size
    }


    fun part2(input: List<String>): Int {
        var count = 0
        val matrix = Matrix(input)

        fun guardLoops (grid: List<List<String>>, r: Int, c: Int): Boolean {
            var movHor = -1
            var movVer = 0
            var nR = r
            var nC = c
            val positions = mutableSetOf<String>()

            while (true) {
                positions.add("$nR,$nC,$movHor,$movVer")
                if (
                    nR + movHor < matrix.edges
                    || nR + movHor >= matrix.sizes
                    || nC + movVer < matrix.edges
                    || nC + movVer >= matrix.sizes
                ) return false
                if (grid[nR + movHor][nC + movVer] == "#") {
                    val temp = movVer
                    movVer = -movHor
                    movHor = temp
                } else {
                    nR += movHor
                    nC += movVer
                }
                if (positions.contains("$nR,$nC,$movHor,$movVer")) return true
            }
        }

        for (nR in 0 until matrix.sizes) {
            for (nC in 0 until matrix.sizes) {
                if (matrix.data[nR][nC] != ".") continue
                matrix.data[nR][nC] = "#"
                if (guardLoops(matrix.data, matrix.row, matrix.col)) count += 1
                matrix.data[nR][nC] = "."
            }
        }

        return count
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}