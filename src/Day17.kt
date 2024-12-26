import kotlin.math.floor
import kotlin.math.pow

fun main() {
    fun part1(input: List<String>): String {
        val validate = """\d+""".toRegex()
        val data = input.map { validate.findAll(it).map { num -> num.value }.toList() }
        var registerA = data[0][0].toInt()
        var registerB = data[1][0].toInt()
        var registerC = data[2][0].toInt()
        val program = data[4].map { it.toInt() }

        val output = mutableListOf<Int>()
        var instructionPointer = 0

        fun runCombo(op: Int): Int {
            return when (op) {
                in 0..3 -> op
                4 -> registerA
                5 -> registerB
                6 -> registerC
                else -> throw Error("not valid")
            }
        }

        while (instructionPointer < program.size) {
            val instruction = program[instructionPointer]
            val operand = program[instructionPointer + 1]
            val combo = runCombo(operand)
            val adv = floor((registerA / 2.0.pow(combo))).toInt()
            when (instruction) {
                0 -> registerA = adv
                1 -> registerB = registerB xor operand
                2 -> registerB = combo % 8
                3 -> if (registerA != 0) { instructionPointer = operand; continue }
                4 -> registerB = registerB xor registerC
                5 -> output.add(combo % 8)
                6 -> registerB =  adv
                7 -> registerC =  adv
            }
            instructionPointer += 2
        }

        return output.joinToString(",")
    }

    fun part2(input: List<String>): Int {

        return input.size
    }

    val input = readInput("test")
    part1(input).println()
//    part2(input).println()
}