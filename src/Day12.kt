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

        return sequence.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}