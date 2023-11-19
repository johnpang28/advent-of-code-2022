import Day10.input
import Day10.parseInstructions
import Day10.toCycles
import Day10.toRegisterValues

fun main() {
    val registerValues = parseInstructions(input).toCycles().toRegisterValues()

    val answer1 = (19..registerValues.size step 40).sumOf { i ->
        (i + 1) * registerValues[i]
    }
    println(answer1) // 15880

    val answer2 = registerValues.mapIndexed { i, v ->
        val sprite = (v - 1)..(v + 1)
        if (i % 40 in sprite) '#' else '.'
    }

    answer2.chunked(40).forEach {
        println(it.joinToString(""))
    } // PLGFKAZG
}

object Day10 {

    sealed interface Op {
        fun doOp(v: Int): Int
    }

    data object NoOp: Op {
        override fun doOp(v: Int): Int = v
    }

    data class AddXOp(private val x: Int): Op {
        override fun doOp(v: Int): Int = v + x
    }

    data class Cycle(val op: Op) {
        companion object {
            val noOp = Cycle(NoOp)
            fun addX(x: Int) = Cycle(AddXOp(x))
        }
    }

    data class Instruction(val cycles: List<Cycle>) {
        companion object {
            val noOp = Instruction(listOf(Cycle.noOp))
            fun addX(x: Int) = Instruction(listOf(Cycle.noOp, Cycle.addX(x)))
        }
    }

    fun parseInstructions(s: String): List<Instruction> =
        s.lines().map { line ->
            if (line.startsWith("addx")) Instruction.addX(line.split(" ")[1].toInt())
            else Instruction.noOp
        }

    fun List<Instruction>.toCycles(): List<Cycle> = flatMap { it.cycles }

    fun List<Cycle>.toRegisterValues(): List<Int> = fold(listOf(1)) { acc, next ->
        acc + next.op.doOp(acc.last())
    }

    val input = """
        noop
        noop
        noop
        addx 3
        addx 20
        noop
        addx -12
        noop
        addx 4
        noop
        noop
        noop
        addx 1
        addx 2
        addx 5
        addx 16
        addx -14
        addx -25
        addx 30
        addx 1
        noop
        addx 5
        noop
        addx -38
        noop
        noop
        noop
        addx 3
        addx 2
        noop
        noop
        noop
        addx 5
        addx 5
        addx 2
        addx 13
        addx 6
        addx -16
        addx 2
        addx 5
        addx -15
        addx 16
        addx 7
        noop
        addx -2
        addx 2
        addx 5
        addx -39
        addx 4
        addx -2
        addx 2
        addx 7
        noop
        addx -2
        addx 17
        addx -10
        noop
        noop
        addx 5
        addx -1
        addx 6
        noop
        addx -2
        addx 5
        addx -8
        addx 12
        addx 3
        addx -2
        addx -19
        addx -16
        addx 2
        addx 5
        noop
        addx 25
        addx 7
        addx -29
        addx 3
        addx 4
        addx -4
        addx 9
        noop
        addx 2
        addx -20
        addx 23
        addx 1
        noop
        addx 5
        addx -10
        addx 14
        addx 2
        addx -1
        addx -38
        noop
        addx 20
        addx -15
        noop
        addx 7
        noop
        addx 26
        addx -25
        addx 2
        addx 7
        noop
        noop
        addx 2
        addx -5
        addx 6
        addx 5
        addx 2
        addx 8
        addx -3
        noop
        addx 3
        addx -2
        addx -38
        addx 13
        addx -6
        noop
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        addx 2
        noop
        noop
        addx 7
        addx 3
        addx -2
        addx 2
        addx 5
        addx 2
        noop
        addx 1
        addx 5
        noop
        noop
        noop
        noop
        noop
        noop
    """.trimIndent()
}
