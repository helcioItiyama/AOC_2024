fun main() {
    fun part1(input: List<String>): Int {
        return input.flatMap { line ->
            val validate = """mul\(\d+,\d+\)""".toRegex()
            validate.findAll(line).map {
                val numbers = """\d+""".toRegex()
                numbers.findAll(it.value).map {number -> number.value.toInt()}.toList().reduce {
                    acc, number -> acc * number
                }
            }.toList()
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val validate = """(mul\(\d+,\d+\)|do(n't)?\(\))""".toRegex()

        val commands = input.flatMap { line -> validate.findAll(line).map{ it.value } }
        var shouldAdd = true
        val filteredMul = commands.fold(mutableListOf<String>()) { acc, str ->
            when (str) {
                "don't()" -> shouldAdd = false
                "do()" -> shouldAdd = true
                else -> if (shouldAdd) acc.add(str)
            }
            acc
        }

        return filteredMul.map {
            val numbers = """\d+""".toRegex()
            numbers.findAll(it)
                .map { number -> number.value.toInt() }
                .toList()
                .reduce { acc, number -> acc * number }
        }.toList().sum()
    }

    val input = readInput("day03")
    part1(input).println()
    part2(input).println()
}