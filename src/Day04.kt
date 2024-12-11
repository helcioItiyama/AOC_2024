fun main() {
    data class Delimiter(val left: Int, val right: Int, val top: Int, val bottom: Int)

    fun getNextChar(char: String): String {
         return when(char) {
            "X" -> "M"
            "M" -> "A"
            "A" -> "S"
            else -> ""
        }
    }

    fun part1(input: List<String>): Int {
        val matrix = input.map { it.split("").drop(1).dropLast(1) }
        var count = 0
        val delimiter = Delimiter(
            left = 0,
            right = matrix[0].lastIndex,
            top = 0,
            bottom = matrix.lastIndex
        )

        fun validate(str: String, l: Int, c: Int): Boolean {
            if (matrix[l][c] != str) return false
            if (str == "S") {
                count++
                return false
            }
            return true
        }

        fun checkTop(str: String, l: Int, c: Int) {
            if (!validate(str, l, c)) return
            if(l > delimiter.top) checkTop(getNextChar(str), l - 1, c)
        }

        fun checkBottom(str: String, l: Int, c: Int) {
            if (!validate(str, l, c)) return
            if(l < delimiter.bottom) checkBottom(getNextChar(str), l + 1, c)
        }

        fun checkLeft(str: String, l: Int, c: Int) {
            if (!validate(str, l, c)) return
            if (c > delimiter.left) checkLeft(getNextChar(str), l, c - 1)
        }

        fun checkRight(str: String, l: Int, c: Int) {
            if (!validate(str, l, c)) return
            if(c < delimiter.right) checkRight(getNextChar(str), l, c + 1)
        }

        fun checkTopLeft(str: String, l: Int, c: Int) {
            if (!validate(str, l, c)) return
            if (l > delimiter.top && c > delimiter.left)
                checkTopLeft(getNextChar(str), l - 1, c -1)
        }

        fun checkTopRight(str: String, l: Int, c: Int) {
            if (!validate(str, l, c)) return
            if (l > delimiter.top && c < delimiter.right)
                checkTopRight(getNextChar(str), l - 1, c + 1)
        }

        fun checkBottomLeft(str: String, l: Int, c: Int) {
            if (!validate(str, l, c)) return
            if (l < delimiter.bottom && c > delimiter.left)
                checkBottomLeft(getNextChar(str), l + 1, c - 1)
        }

        fun checkBottomRight(str: String, l: Int, c: Int) {
            if (!validate(str, l, c)) return
            if (l < delimiter.bottom && c < delimiter.right)
                checkBottomRight(getNextChar(str), l + 1, c + 1)
        }

        for (l in  0 .. matrix.lastIndex) {
            for (c in 0 .. matrix[l].lastIndex) {
                val entry = "X"
                if (matrix[l][c] == entry) {
                    checkTop(entry, l, c)
                    checkBottom(entry, l, c)
                    checkLeft(entry, l, c)
                    checkRight(entry, l, c)
                    checkTopLeft(entry, l, c)
                    checkTopRight(entry, l, c)
                    checkBottomLeft(entry, l, c)
                    checkBottomRight(entry, l, c)
                }
            }
        }
        return count
    }

    fun part2(input: List<String>): Int {
        val matrix = input.map { it.split("").drop(1).dropLast(1) }
        var count = 0
        val delimiter = Delimiter(
            left = 0,
            right = matrix[0].lastIndex,
            top = 0,
            bottom = matrix.lastIndex
        )

        fun checkDiagonalLeft(l: Int, c: Int): Boolean {
            val left = if (l > delimiter.top && c > delimiter.left) {
                matrix[l - 1][c - 1]
            } else ""
            val right = if (l < delimiter.bottom && c < delimiter.right) {
                matrix[l + 1][c + 1]
            } else ""
            return (left == "S" && right == "M") || (left == "M" && right == "S")
        }

        fun checkDiagonalRight(l: Int, c: Int): Boolean {
            val right = if (l > delimiter.top && c < delimiter.right) {
                matrix[l - 1][c + 1]
            } else ""
            val left = if (l < delimiter.bottom && c > delimiter.left) {
                matrix[l + 1][c - 1]
            } else ""
            return (left == "S" && right == "M") || (left == "M" && right == "S")
        }

        for (l in  0 .. matrix.lastIndex) {
            for (c in 0 .. matrix[l].lastIndex) {
                val entry = "A"
                if (matrix[l][c] == entry) {
                    if(checkDiagonalLeft(l, c) && checkDiagonalRight(l, c)) count++
                }
            }
        }
        return count
    }

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}