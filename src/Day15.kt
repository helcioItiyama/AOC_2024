fun main() {
    fun part1(input: List<String>): Int {
        val (screen, instructions) = input.partition { it.contains("#") }
        val matrix = screen.map {
            it.split("").drop(1).dropLast(1).toMutableList()
        }.toMutableList()
        val lineOfInstructions = instructions.joinToString("")
        var initL = 0
        var initC = 0
        var sum = 0

        for (l in matrix.indices) {
            for (c in matrix[0].indices) {
                if (matrix[l][c] == "@") {
                    initL = l
                    initC = c
                }
            }
        }

       for (i in lineOfInstructions.indices) {
           val inst = lineOfInstructions[i]
           val nextL = if (inst == '^') -1 else if (inst == 'v') 1 else 0
           val nextC = if (inst == '<') -1 else if (inst == '>') 1 else 0
           val boxes = mutableListOf<Pair<Int, Int>>()
           var shouldMove = true
           var currentL = initL
           var currentC = initC
           while (true) {
               currentL += nextL
               currentC += nextC
               val char = matrix[currentL][currentC]
               if (char == "#") {
                   shouldMove = false
                   break
               }
               if (char == "O") boxes.add(Pair(currentL, currentC))
               if (char == ".") break
           }
           if (!shouldMove) continue
           matrix[initL][initC] = "."
           matrix[initL + nextL][initC + nextC] = "@"
           for ((boxL, boxC) in boxes) {
               matrix[boxL + nextL][boxC + nextC] = "O"
           }
           initL += nextL
           initC += nextC
        }

        for(i in matrix.indices) {
            for(j in matrix[0].indices) {
                if (matrix[i][j] == "O") sum += 100 * i + j
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("test")
    part1(input).println()
//    part2(input).println()
}
