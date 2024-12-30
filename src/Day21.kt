fun main() {
    data class Directions(val r: Int, val c: Int, val mov: String)

    val numKeypad = listOf(
        listOf("7", "8", "9"),
        listOf("4", "5", "6"),
        listOf("1", "2", "3"),
        listOf( null, "0", "A")
    )

    val dirKeypad = listOf(
        listOf(null, "^", "A"),
        listOf("<", "v", ">")
    )

    fun cartesianProduct(lists:List<List<String>> ): List<List<String>> =
        lists.fold(listOf(emptyList())) { acc, list ->
            acc.flatMap { comb -> list.map { element -> comb + element } }
        }

    // This code's based on HyperNeutrino's
    fun part1(input: List<String>): Int {
        fun solve(key: String = "", keyPad: List<List<String?>>): List<String> {
            val pos = HashMap<String, Pair<Int, Int>>()
            for (r in keyPad.indices) {
                for (c in keyPad[0].indices) {
                    keyPad[r][c]?.let { pos[it] = r to c }
                }
            }
            val seqs = HashMap<Pair<String, String>, List<String>>()
            for (x in pos.keys) {
                for (y in pos.keys) {
                    if (x == y) { seqs[Pair(x, y)] = listOf("A"); continue }
                    val possibilities = mutableListOf<String>()
                    val queue = mutableListOf(pos[x]!! to  "")
                    var optimal = Int.MAX_VALUE

                    whileLoop@
                    while (queue.isNotEmpty()) {
                        val (pair, moves) = queue.removeFirst()
                        val (cr, cc) = pair
                        val directions = listOf(
                            Directions(cr - 1, cc, "^"),
                            Directions(cr + 1, cc, "v"),
                            Directions(cr, cc - 1, "<"),
                            Directions(cr, cc + 1, ">")
                        )
                        for (dir in directions) {
                            if (dir.r < 0 || dir.c < 0 || dir.r > keyPad.lastIndex || dir.c > keyPad[0].lastIndex) continue
                            if (keyPad[dir.r][dir.c] == null) continue
                            if (keyPad[dir.r][dir.c] == y) {
                                val movesLength = moves.length + 1
                                if(optimal < movesLength) break@whileLoop
                                optimal = movesLength
                                possibilities.add(moves + dir.mov + "A")
                            } else {
                                queue.add(Pair(Pair(dir.r, dir.c), moves + dir.mov))
                            }
                        }
                    }
                    seqs[Pair(x, y)] = possibilities
                }
            }
            val options = ("A$key").zip(key).map { (x, y) -> seqs[Pair(x.toString(), y.toString())]!! }
            return cartesianProduct(options).map { it.joinToString("") }
        }

        var total = 0

        for (line in input) {
            val robot1 = solve(line, numKeypad)
            var next = robot1
            repeat (2) {
                val possibleNext = mutableListOf<String>()
                for(seq in next) {
                    possibleNext += solve(seq, dirKeypad)
                }
                val minLength = possibleNext.minOf { it.length }
                next = possibleNext.filter { it.length == minLength }
            }
            total += next[0].length * line.dropLast(1).toInt()
        }

        return total
    }

    data class LengthCache (val seq: String, val depth: Int)

    // This code's based on HyperNeutrino's
    fun part2(input: List<String>): Long {
        fun computeSeqs(keyPad: List<List<String?>>): HashMap<Pair<String, String>, List<String>> {
            val pos = HashMap<String, Pair<Int, Int>>()
            for (r in keyPad.indices) {
                for (c in keyPad[0].indices) {
                    keyPad[r][c]?.let { pos[it] = r to c }
                }
            }
            val seqs = HashMap<Pair<String, String>, List<String>>()
            for (x in pos.keys) {
                for (y in pos.keys) {
                    if (x == y) { seqs[Pair(x, y)] = listOf("A"); continue }
                    val possibilities = mutableListOf<String>()
                    val queue = mutableListOf(pos[x]!! to  "")
                    var optimal = Int.MAX_VALUE

                    whileLoop@
                    while (queue.isNotEmpty()) {
                        val (pair, moves) = queue.removeFirst()
                        val (cr, cc) = pair
                        val directions = listOf(
                            Directions(cr - 1, cc, "^"),
                            Directions(cr + 1, cc, "v"),
                            Directions(cr, cc - 1, "<"),
                            Directions(cr, cc + 1, ">")
                        )
                        for (dir in directions) {
                            if (dir.r < 0 || dir.c < 0 || dir.r > keyPad.lastIndex || dir.c > keyPad[0].lastIndex) continue
                            if (keyPad[dir.r][dir.c] == null) continue
                            if (keyPad[dir.r][dir.c] == y) {
                                val movesLength = moves.length + 1
                                if(optimal < movesLength) break@whileLoop
                                optimal = movesLength
                                possibilities.add(moves + dir.mov + "A")
                            } else {
                                queue.add(Pair(Pair(dir.r, dir.c), moves + dir.mov))
                            }
                        }
                    }
                    seqs[Pair(x, y)] = possibilities
                }
            }
            return seqs
        }

        fun solve(key: String = "", seqs:  HashMap<Pair<String, String>, List<String>>): List<String> {
            val options = ("A$key").zip(key).map { (x, y) -> seqs[Pair(x.toString(), y.toString())]!! }
            return cartesianProduct(options).map { it.joinToString("") }
        }

        val numSeqs = computeSeqs(numKeypad)
        val dirSeqs = computeSeqs(dirKeypad)
        val cache = HashMap<LengthCache, Long>()

        val dirLengths = dirSeqs.map {(key, value) -> key to value.first().length }.toMap()

        fun computeLength(seq: String, depth: Int = 25): Long {
            if (depth == 1) return ("A$seq").zip(seq).sumOf { dirLengths[it.first.toString() to it.second.toString()]!! }.toLong()
            if (!cache.containsKey(LengthCache(seq, depth))) {
                var length = 0L
                for ((x, y) in ("A$seq").zip(seq)) {
                    length += dirSeqs[x.toString() to y.toString()]!!.minOf { computeLength(it,depth - 1) }
                }
                cache[LengthCache(seq, depth)] = length

            }
            return cache[LengthCache(seq, depth)]!!
        }

        var total = 0L

        for (line in input) {
            val results = solve(line, numSeqs)
            val length = results.minOf { computeLength(it) }
            total += length * line.dropLast(1).toLong()
        }
        return total
    }

    val input = readInput("test")
    part1(input).println()
    part2(input).println()
}