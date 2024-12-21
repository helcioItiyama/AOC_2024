typealias Antenna = MutableList<Pair<Int, Int>>

fun main() {
    class AntennaCoords (input:List<String>) {
        val height = input.lastIndex
        val width = input[0].lastIndex
        val data = input.foldIndexed(HashMap<String, Antenna>()) { i, acc, r ->
            val antennas = r
                .split("")
                .drop(1)
                .dropLast(1)
                .foldIndexed (HashMap<String, Pair<Int, Int>>()) { j, cAcc, c ->
                    if (c != ".") {
                        cAcc[c] = Pair(i, j)
                    }
                    cAcc
                }
            antennas.keys.forEach { key ->
                if (!acc.containsKey(key)) acc[key] = mutableListOf()
                acc[key]?.apply { add(antennas[key]!!) }
            }
            acc
        }

        fun checkBoundaries(r: Int, c: Int) = r in 0..height && c in 0..width
    }

    fun part1(input: List<String>): Int {
        val antennaCoords = AntennaCoords(input)
        val antiNodes = mutableSetOf<Pair<Int, Int>>()

        antennaCoords.data.values.forEach {
            val lastAntenna = it.lastIndex
            var curr = 0
            var next = 1

            while (curr <= lastAntenna && next <= lastAntenna) {
                val currRow = it[curr].first
                val currCol = it[curr].second

                val nextRow = it[next].first
                val nextCol = it[next].second
                val rowDiff = nextRow - currRow
                val colDiff = nextCol - currCol

                val prevAntiNodeRow = currRow - rowDiff
                val prevAntiNodeCol = currCol - colDiff

                val nextAntiNodeRow = nextRow + rowDiff
                val nextAntiNodeCol = nextCol + colDiff

                if (antennaCoords.checkBoundaries(prevAntiNodeRow, prevAntiNodeCol)) {
                    antiNodes.add(Pair(prevAntiNodeRow, prevAntiNodeCol))
                }

                if(antennaCoords.checkBoundaries(nextAntiNodeRow, nextAntiNodeCol)) {
                    antiNodes.add(Pair(nextAntiNodeRow, nextAntiNodeCol))
                }

                next++

                if (next > lastAntenna && curr < lastAntenna - 1) {
                    curr++
                    next = curr + 1
                }
            }
        }
        return antiNodes.size
    }

    fun part2(input: List<String>): Int {
        val antennaCoords = AntennaCoords(input)
        val antiNodes = mutableSetOf<Pair<Int, Int>>()

        antennaCoords.data.values.forEach {
            for (curr in 0..it.lastIndex) {
                for (next in 0 .. it.lastIndex) {
                    if (curr == next) continue

                    val currRow = it[curr].first
                    val currCol = it[curr].second

                    val nextRow = it[next].first
                    val nextCol = it[next].second

                    val rowDiff = nextRow - currRow
                    val colDiff = nextCol - currCol

                    for (a in 0 until antennaCoords.height * antennaCoords.width ) {
                        val prevAntiNodeRow = currRow - rowDiff * a
                        val prevAntiNodeCol = currCol - colDiff * a

                        if (antennaCoords.checkBoundaries(prevAntiNodeRow, prevAntiNodeCol)) {
                            antiNodes.add(Pair(prevAntiNodeRow, prevAntiNodeCol))
                        }
                    }
                }
            }
        }
        return antiNodes.size
    }

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}