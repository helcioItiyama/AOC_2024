fun main() {
    fun part1(input: List<String>): Long {
        val voidIndex = input.indexOf("")
        val (left, right) = input.withIndex().partition { it.index < voidIndex }
        val operationHash = HashMap<String, MutableSet<String>>()
        val validOperations = mutableSetOf<List<String>>()

        val valuesHash = left.fold(HashMap<String, Boolean?>()) { acc, curr ->
            val (key, value) = curr.value.split(": ")
            acc[key] = value == "1"
            acc
        }

        val inst = right
            .drop(1)
            .map { it.value.replace("-> ", "").split(" ") }


        inst.forEach {
            val (v1, op, v2, res) = it
            if (!valuesHash.containsKey(v1)) valuesHash[v1] = null
            if (!valuesHash.containsKey(v2)) valuesHash[v2] = null
            if (!valuesHash.containsKey(res)) valuesHash[res] = null
            if(res.startsWith("z")) validOperations.add(it)
            operationHash[res] = mutableSetOf(v1, op, v2)
        }

        fun runOperation(val1: Boolean?, op: String, val2: Boolean?): Boolean? {
            return when {
                val1 == null || val2 == null -> null
                op == "AND" -> val1 and val2
                op == "OR" -> val1 or val2
                op == "XOR" -> val1 xor val2
                else -> null
            }
        }

        fun traverse(val1: String, op: String, val2: String): Boolean? {
            var first: Boolean? = null
            var second: Boolean? = null
            val hashValue1 = valuesHash[val1]
            val hasValue2 = valuesHash[val2]

            if (hashValue1!= null && hasValue2 != null) {
                return runOperation(hashValue1, op, hasValue2)
            }
            if (hashValue1 == null) {
                first = traverse(
                    operationHash[val1]!!.elementAt(0),
                    operationHash[val1]!!.elementAt(1),
                    operationHash[val1]!!.elementAt(2)
                )
            }
            if (hasValue2 == null) {
                second = traverse(
                    operationHash[val2]!!.elementAt(0),
                    operationHash[val2]!!.elementAt(1),
                    operationHash[val2]!!.elementAt(2)
                )
            }
            return runOperation(first, op, second)
        }

        while (validOperations.any { valuesHash[it[3]] == null}) {
            for(each in validOperations) {
                if(valuesHash[each[3]] != null) continue
                valuesHash[each[3]] = traverse(each[0], each[1], each[2])
            }
        }

        return valuesHash
            .entries
            .filter { it.key.startsWith("z") }
            .sortedByDescending { it.key }
            .fold("") { acc, curr -> if (curr.value == true) acc + "1" else acc +  "0" }
            .toLong(2)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }


    val input = readInput("Day24")
    part1(input).println()
//    part2(input).println()
}