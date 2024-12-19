data class MatrixAndEdges (
    val row: Int,
    val col: Int,
    val sizes: Int,
    val edges: Int,
    val matrix: MutableList<MutableList<String>>
)

fun getMatrixAndEdges(input: List<String>): MatrixAndEdges  {
    val matrix = input
        .map { it.split("").drop(1).dropLast(1).toMutableList() }
        .toMutableList()
    var r = 0
    var c = 0
    val sizes = matrix.size
    val edges = 0

    for (row in 0  until sizes) {
        for (col in 0 until sizes) {
            if(matrix[row][col] == "^") {
                r = row
                c= col
                break
            }
        }
    }

    return MatrixAndEdges(
        row = r,
        col = c,
        sizes = sizes,
        edges = edges,
        matrix = matrix
    )
}

fun main() {
    fun part1(input: List<String>): Int {
        var (row, col, sizes, edges, matrix) = getMatrixAndEdges(input)
        val positions = mutableSetOf<Pair<Int, Int>>()
        var movHor = -1
        var movVer = 0

        while (true) {
            positions.add(Pair(row, col))
            if (row + movHor < edges || row + movHor >= sizes || col + movVer < edges || col + movVer >= sizes) break
            if (matrix[row + movHor][col + movVer] == "#") {
                val temp = movVer
                movVer = -movHor
                movHor = temp
            } else {
                row += movHor
                col += movVer
            }
        }

        return positions.size
    }


    fun part2(input: List<String>): Int {
        var count = 0
        val (row, col, sizes, edges, matrix) = getMatrixAndEdges(input)

        fun guardLoops (matrix: List<List<String>>, r: Int, c: Int): Boolean {
            var movHor = -1
            var movVer = 0
            var nR = r
            var nC = c
            val positions = mutableSetOf<String>()

            while (true) {
                positions.add("$nR,$nC,$movHor,$movVer")
                if (nR + movHor < edges || nR + movHor >= sizes || nC + movVer < edges || nC + movVer >= sizes) return false
                if (matrix[nR + movHor][nC + movVer] == "#") {
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

        for (nR in 0 until sizes) {
            for (nC in 0 until sizes) {
                if (matrix[nR][nC] != ".") continue
                matrix[nR][nC] = "#"
                if (guardLoops(matrix, row, col)) count += 1
                matrix[nR][nC] = "."
            }
        }

        return count
    }

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}