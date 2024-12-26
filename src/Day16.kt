import java.util.PriorityQueue

fun main() {
    // this code was based on HyperNeutrino's
    fun part1(input: List<String>): Int {
        val grid = input.map { it.split("").drop(1).dropLast(1) }
        var c = 0
        var r = 0
        val comparable = compareBy<List<Int>> { it.first() }
        val priorityQueue = PriorityQueue(comparable)
        var totalCost = 0
        val forwardPoint = 1
        val rotationPoint = 1000

        outerLoop@
        for (row in grid.indices) {
            for (col in grid[0].indices) {
                if (grid[row][col] == "S") {
                    r = row
                    c = col
                    break@outerLoop
                }
            }
        }

        val seen = mutableSetOf(listOf(r, c, 0, 1))
        priorityQueue.add(listOf(0, r, c, 0, 1))

        while (priorityQueue.isNotEmpty()) {
            val (cost, currR, currC, directionR, directionC) = priorityQueue.poll()
            seen.add(listOf(currR, currC, directionR, directionC))

            if (grid[currR][currC] == "E") {
                totalCost = cost
                break
            }

            val paths = listOf(
                listOf(cost + forwardPoint, currR + directionR, currC + directionC, directionR, directionC),
                listOf(cost + rotationPoint, currR, currC, directionC, -directionR),
                listOf(cost + rotationPoint, currR, currC, -directionC, directionR),
            )

            for ((newCost, newR, newC, newDirectionR, newDirectionC) in paths) {
                if (grid[newR][newC] == "#") continue
                if (listOf(newR, newC, newDirectionR, newDirectionC) in seen) continue
                priorityQueue.add(listOf(newCost, newR, newC, newDirectionR, newDirectionC))
            }
        }

        return totalCost
    }

    fun part2(input: List<String>): Int {

        return input.size
    }

    val input = readInput("Day16")
    part1(input).println()
//    part2(input).println()
}
