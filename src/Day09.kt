fun main() {
    fun part1(input: List<String>): Long {
        var id = 0
        val list = input.fold(mutableListOf<String>()) { acc, curr ->
            curr.split("").drop(1).dropLast(1).forEachIndexed { i, num ->
                if (i % 2 == 0) {
                    var e = 1
                    while (e <= num.toInt()) {
                        acc.add(id.toString())
                        e++
                    }
                    id++
                } else {
                    var e = 1
                    while (e  <= num.toInt()) {
                        acc.add(".")
                        e++
                    }
                }
            }
            acc
        }

        var leftIndex = 0
        var rightIndex = list.lastIndex

        while (leftIndex < rightIndex) {
            if (list[leftIndex] != ".") {
                leftIndex++
            } else if (list[rightIndex] != ".") {
                val temp = list[leftIndex]
                list[leftIndex] = list[rightIndex]
                list[rightIndex] = temp
                leftIndex++
                rightIndex--
            } else {
                rightIndex--
            }
        }

        return list.foldIndexed(0) { i, acc, curr ->
            if (curr != ".") {
                acc + i * curr.toInt()
            } else acc
        }
    }

    fun part2(input: List<String>): Long {
        var id = 0
        var pos = 0
        var sum = 0L
        val (block, list) = input.fold(Pair(HashMap<Int, MutableList<Int>>(), mutableListOf<MutableList<Int>>())) { acc, curr ->
            curr.split("").drop(1).dropLast(1).forEachIndexed { i, num ->
                if (i % 2 == 0) {
                    acc.first[id] = mutableListOf(pos, num.toInt())
                    pos += num.toInt()
                    id++
                } else {
                    if (num.toInt() != 0) {
                        acc.second.add(mutableListOf(pos, num.toInt()))
                        pos += num.toInt()
                    }
                }
            }
            acc
        }

        for (i in block.size - 1 downTo 0) {
            for (j in 0 until list.size) {
                if (block[i]!![0] > list[j][0]) {
                    if (block[i]!![1] <= list[j][1]) {
                        block[i]!![0] = list[j][0]
                        list[j][0] += block[i]!![1]
                        list[j][1] -= block[i]!![1]
                        if (list[j][1] == 0) list.removeAt(j)
                        break
                    }
                }
            }
        }

        block.entries.forEach {(key, value) ->
            for (i in value[0] until (value[1] + value[0])) {
                sum += key * i
            }
        }

        return sum
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}