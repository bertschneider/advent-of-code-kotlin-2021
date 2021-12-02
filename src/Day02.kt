// This solution is super over-engineered and a simple loop with a when expression would have been enough.
// Regardless, I wanted to use proper classes and create an object-oriented solution.

data class Position(val x: Int, val y: Int)

sealed interface Command {
    operator fun invoke(position: Position): Position
    companion object {
        fun parse(line: String) : Command {
            val (command, value) = line.split(" ")
            return when (command) {
                "forward" -> Forward(value.toInt())
                "up" -> Up(value.toInt())
                "down" -> Down(value.toInt())
                else -> throw IllegalArgumentException("Unknown command $command")
            }
        }
    }
}
data class Forward(val value: Int): Command {
        override operator fun invoke(position: Position): Position {
            return position.copy(x = position.x + value)
        }
}
data class Up(val value: Int): Command {
    override operator fun invoke(position: Position): Position {
        return position.copy(y = position.y - value)
    }
}
data class Down(val value: Int): Command {
    override operator fun invoke(position: Position): Position {
        return position.copy(y = position.y + value)
    }
}

fun main() {
    val commands: List<Command> = readInput("Day02").map(Command::parse)
    val position = commands.fold(Position(0, 0)) { position, command -> command(position)}
    val multipliedPosition = position.x * position.y
    println("Multiplication of $position is $multipliedPosition")
}
