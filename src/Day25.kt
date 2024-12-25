fun main() {
    fun getHeights(list: List<MutableList<String>>) = list.map {
        val invertList = MutableList(list[0][0].length) { MutableList(list[0].size) { "" } }
        for (r in it.indices) {
            for (c in it[0].indices) {
                invertList[c][r] = it[r][c].toString()
            }
        }
        invertList.map { it.count { c -> c == "#" } - 1 }
    }

    fun part1(input: List<String>): Int {
        val list = mutableListOf<MutableList<String>>()
        var tempList = mutableListOf<String>()
        for (i in input.indices) {
            if (input[i] == "" || i == input.lastIndex) {
                if (i == input.lastIndex) tempList.add(input[i])
                list.add(tempList)
                tempList = mutableListOf()
            } else {
                tempList.add(input[i])
            }
        }

        val (locks, keys) = list.partition { it.first().count { first -> first.toString() == "#"} == it.first().length }

        val locksCoords = getHeights(locks)
        val keysCoords = getHeights(keys)
        var count = 0

        for (lock in locksCoords) {
            for (key in keysCoords) {
                val isTrue = mutableListOf<Boolean>()
                for(i in lock.indices) {
                    isTrue.add(lock[i] + key[i] <= locksCoords[0].size)
                }
                if (isTrue.all { it }) count++
            }
        }
        return count
    }

    val input = readInput("Day25")
    part1(input).println()
}