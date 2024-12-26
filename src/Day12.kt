fun main() {
    class BlocksGraph (input: List<String>) {
        private val blocks = input.map { it.split("").drop(1).dropLast(1) }
        private val left = 0
        private val right = blocks[0].lastIndex
        private val top = 0
        private val bottom = blocks.lastIndex
        val regions = HashMap<Pair<Int, Int>, MutableSet<Pair<Int, Int>>>()

        init {
            for (row in blocks.indices) {
                for (col in blocks[0].indices) {
                    populateRegions(row, col)
                }
            }
        }

        fun addNode(node: Pair<Int, Int>) {
            regions[node] = mutableSetOf()
        }

        fun addEdge(root: Pair<Int, Int>, edge: Pair<Int, Int>) {
            regions[root]?.apply { add(edge) }
            if (edge !in regions) addNode(edge)
            regions[edge]?.apply { add(root) }
        }

        private fun populateRegions(r: Int, c: Int) {
            val curr = blocks[r][c]
            val currPair = Pair(r, c)

            if (currPair !in regions) {
                addNode(currPair)
            }
            if (r < bottom && curr == blocks[r + 1][c]) {
                addEdge(currPair, Pair(r + 1, c))
            }
            if (r > top && curr == blocks[r - 1][c]) {
                addEdge(currPair, Pair(r - 1, c))
            }
            if (c > left && curr == blocks[r][c -1]) {
                addEdge(currPair, Pair(r, c - 1))
            }
            if (c < right && curr == blocks[r][c + 1]) {
                addEdge(currPair, Pair(r, c + 1))
            }
        }
    }

    fun part1(input: List<String>): Int {
        val graph = BlocksGraph(input)
        val hasVisited = mutableSetOf<Pair<Int, Int>>()
        val sequence = mutableListOf<Int>()
        var area = 0
        var perimeter = 0

        fun traverseGraph(node: Pair<Int, Int>) {
            if (node in graph.regions && node !in hasVisited) {
                area++
                perimeter += (4 - graph.regions.getOrDefault(node, emptySet()).size)
                hasVisited.add(node)
                graph.regions[node]?.forEach(::traverseGraph)
            }
        }

        graph.regions.entries.forEach { (key, value) ->
            if (!hasVisited.contains(key)) {
                hasVisited.add(key)
                perimeter += (4 - value.size)
                area++
                value.forEach(::traverseGraph)
                sequence.add(area * perimeter)
                area = 0
                perimeter = 0
            }
        }
        println(hasVisited)

        return sequence.sum()
    }

    // this code was based on HyperNeutrino's
    fun part2(input: List<String>): Int {
        val grid = input.map { it.split("").drop(1).dropLast(1) }
        val regions = mutableSetOf<MutableSet<Pair<Int, Int>>>()
        val visited = mutableSetOf<Pair<Int, Int>>()

        for (r in grid.indices) {
            for (c in grid[0].indices) {
                if (r to c in visited) continue
                visited.add(r to c)
                val region = mutableSetOf(r to c)
                val queue = mutableListOf(r to c)
                val plant = grid[r][c]

                while (queue.isNotEmpty()) {
                    val (curR, currC) = queue.removeFirst()
                    val directions = listOf(curR - 1 to currC, curR + 1 to currC, curR to currC - 1, curR to currC + 1)
                    for ((newR , newC) in directions) {
                        if (newR < 0 || newC < 0 || newR > grid.lastIndex || newC > grid.lastIndex) continue
                        if (grid[newR][newC] != plant) continue
                        if (newR to newC in region) continue
                        region.add(newR to newC)
                        visited.add(newR to newC)
                        queue.add(newR to newC)
                    }
                }
                regions.add(region)
            }
        }

        fun getDirections(r: Double, c: Double) = listOf(
            r - 0.5 to c - 0.5,
            r + 0.5 to c - 0.5,
            r + 0.5 to c + 0.5,
            r - 0.5 to c + 0.5
        )

        fun sides(region: List<Pair<Double, Double>>): Int {
            val possibleCorners = mutableSetOf<Pair<Double, Double>>()
            for ((r, c) in region) {
                for ((cr, cc) in getDirections(r, c)) {
                    possibleCorners.add(cr to cc)
                }
            }
            var corners = 0
            for ((currR, currC) in possibleCorners) {
                val arrangement = getDirections(currR, currC).map { (r, c) -> r to c in region }
                val number = arrangement.count { it }
                val isOpposing =
                    arrangement == listOf(true, false, true, false) || arrangement == listOf(false, true, false, true)
                when (number) {
                    1 -> corners++
                    2 -> if (isOpposing) corners += 2
                    3 -> corners++
                }
            }
            return corners
        }

        return regions.sumOf { it.size * sides(it.map { (r, c) ->  r.toDouble() to c.toDouble()}) }
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}