typealias Callback = (target: Long, operators: List<Long>) -> Boolean

fun main() {
    fun getData(input: List<String>): List<Pair<String, List<Long>>> =
        input.map {
            val (testValue, operator) = it.split(":")
            testValue to operator.split(" ").drop(1). map { it.toLong() }
        }

    fun getTotal(data:List<Pair<String, List<Long>>>, callback: Callback): Long {
        var total = 0L
        for ((testValue, operators) in data) {
            val target = testValue.toLong()
            if (callback(target, operators)) total += target
        }
        return total
    }

    fun part1(input: List<String>): Long {
        val data = getData(input)
        fun evaluate(target: Long, list: List<Long>): Boolean {
            return when {
                list.size == 1 -> target == list.last()
                target % list.last() == 0L && evaluate(target / list.last(), list.dropLast(1)) -> true
                target > list[1] && evaluate(target - list.last(), list.dropLast(1)) -> true
                else -> false
            }
        }
        return getTotal(data,::evaluate)
    }

    fun part2(input: List<String>): Long {
        val data = getData(input)
        fun evaluate(target: Long, list: List<Long>): Boolean {
            val targetString = target.toString()
            val lastString = list.last().toString()
            return when {
                list.size == 1 -> target == list.last()
                target % list.last() == 0L && evaluate(target / list.last(), list.dropLast(1)) -> true
                target > list[1] && evaluate(target - list.last(), list.dropLast(1)) -> true
                targetString.length > lastString.length
                        && targetString.endsWith(lastString)
                        && evaluate(targetString.dropLast(lastString.length).toLong(),list.dropLast(1)) -> true
                else -> false
            }
        }
        return getTotal(data, ::evaluate)
    }

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}