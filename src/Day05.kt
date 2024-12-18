import kotlin.math.floor

typealias RulesAndOrders = Pair<HashMap<Int, MutableList<Int>>, List<List<Int>>>

fun getRulesAndOrders(input: List<String>): RulesAndOrders {
    val validation = """\d+\|\d+""".toRegex()
    val (left, right) = input.partition { validation.matches(it) }
    val rules = left.fold(HashMap<Int, MutableList<Int>>()) { acc, e ->
        val (l, r) = e.split("|").map { it.toInt() }
        if(acc[l] == null) acc[l] = mutableListOf()
        acc[l]?.apply { add(r) }
        acc
    }
    val orders = right.drop(1).map { it.split(",").map { it.toInt()} }
    return rules to orders
}

fun checkIfIsCorrect(list: List<Int>, rules: HashMap<Int, MutableList<Int>>): Boolean {
    return list.withIndex().all { (i, curr) ->
        if(i == 0) true else rules[curr]?.contains(list[i - 1]) == true
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val (rules, orders) = getRulesAndOrders(input)
        return orders.filter {
            val reversed = it.reversed()
            checkIfIsCorrect(reversed, rules)
        }.sumOf {
            val index = floor((it.size / 2).toFloat() ).toInt()
            it[index]
        }
    }

    fun part2(input: List<String>): Int {
        val (rules, orders) = getRulesAndOrders(input)
        return orders.sumOf {
            val reversed = it.reversed().toMutableList()
            val isTrue = checkIfIsCorrect(reversed, rules)
            var sum = 0
            if (!isTrue) {
                for (i in 1..reversed.lastIndex) {
                    var j = i - 1
                    val curr = reversed[i]
                    while (j >= 0 && rules[curr]?.contains(reversed[j]) == false) {
                        reversed[j + 1] = reversed[j]
                        j--
                    }
                    reversed[j + 1] = curr
                }
                sum += reversed[floor((reversed.size / 2).toFloat()).toInt()]
            }
            sum
        }
    }

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}