fun main() {
    fun getGraph(input: List<String>) = input.fold(HashMap<String, MutableSet<String>>()) { acc, curr ->
        val (left, right) = curr.split("-")
        if (left !in acc) acc[left] = mutableSetOf(right)
        if (right !in acc)  acc[right] = mutableSetOf(left)
        acc[left]?.add(right)
        acc[right]?.add(left)
        acc
    }

    fun part1(input: List<String>): Int {
        val graph = getGraph(input)

        fun traverse(key: String): MutableSet<List<String>>? =
            graph[key]?.fold(mutableSetOf()) { acc, curr ->
                val matchedKey = graph.getOrDefault(curr, emptySet()).filter { e -> graph[key]?.contains(e) == true }
                if (matchedKey.isNotEmpty()) {
                    matchedKey.forEach { k ->
                        val kHasT = k.startsWith('t')
                        val hasKeyT = key.startsWith('t')
                        val hasValueT = curr.startsWith('t')
                        val hasAnyKeyT = hasKeyT || hasValueT || kHasT
                        if (hasAnyKeyT) acc.add(listOf(key, curr, k).sorted())
                    }
                }
                acc
            }

        return graph.keys.fold(mutableSetOf<List<String>>()) { acc, curr ->
            val each = traverse(curr)
            if (each !== null) {
                each.forEach { e -> acc.add(e) }
            }
            acc
        }.count()
    }

    fun part2(input: List<String>): String {
        val graph = getGraph(input)
        val connections = mutableSetOf<MutableSet<String>>()

        fun traverse(key: String, set: MutableSet<String>) {
            val setKeys = set.toSortedSet()
            if (setKeys in connections) return
            connections.add(setKeys)
            for (each in graph.getOrDefault(key, emptySet())) {
                when {
                    each in set -> continue
                    !set.all { q -> each in graph.getOrDefault(q, emptySet()) } -> continue
                    else -> {
                        val copy = set.toMutableSet()
                        copy.add(each)
                        traverse(each, copy)
                    }
                }
            }
        }

        graph.keys.forEach { traverse(it, mutableSetOf(it)) }
        return connections.maxBy { it.size }.sorted().joinToString(",")
    }

    val input = readInput("Day23")
    part1(input).println()
    part2(input).println()
}