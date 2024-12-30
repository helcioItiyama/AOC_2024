fun main() {
    fun part1(input: List<String>): Long {
        fun mix(v: Long, secret: Long): Long = v xor secret
        fun prune(v: Long): Long = v % 16777216L
        val result = input.map {
            var i = 1
            var secret = it.toLong()

            while (i <= 2000) {
                secret = prune(mix(secret * 64, secret))
                secret = prune(mix(secret / 32, secret))
                secret = prune(mix(secret * 2048, secret))
                i++
            }
            secret
        }

        return result.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day22")
    part1(input).println()
//    part2(input).println()
}