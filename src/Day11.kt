fun main() {
    fun part1(input: List<String>): Long {
        val blocks = input.flatMap { it.split(" ").map { num -> num.toLong() } }

        fun operation(stone: Long, i: Int): Long {
            if ( i == 0) return 1
            var results = 0L
            if (stone == 0L) {
                results = operation(1, i - 1)
            } else if (stone.toString().length % 2 == 0) {
                val stringStone = stone.toString()
                val half = stringStone.length / 2
                val leading = stringStone.take(half)
                val trailing = stringStone.takeLast(half)
                results += operation(leading.toLong(), i - 1) + operation(trailing.toLong(), i - 1)
            } else {
                results = operation(stone * 2024, i - 1)
            }
            return results
        }
        return blocks.sumOf { operation(it, 25) }
    }

    fun part2(input: List<String>): Long {
        val blocks = input.flatMap { it.split(" ").map { num -> num.toLong() } }
        val cache = HashMap<Pair<Long, Int>, Long>()

        fun operation(stone: Long, i: Int): Long {
            if ( i == 0) return 1
            if (!cache.containsKey(Pair(stone, i))) {
                var results = 0L
                if (stone == 0L) {
                    results = operation(1, i - 1)
                } else if (stone.toString().length % 2 == 0) {
                    val stringStone = stone.toString()
                    val half = stringStone.length / 2
                    val leading = stringStone.take(half)
                    val trailing = stringStone.takeLast(half)
                    results += operation(leading.toLong(), i - 1) + operation(trailing.toLong(), i - 1)
                } else {
                    results = operation(stone * 2024, i - 1)
                }
                cache[Pair(stone, i)] = results
            }
            return cache.getOrDefault(Pair(stone, i), 0L)
        }
        return blocks.sumOf { operation(it, 75) }
    }

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}