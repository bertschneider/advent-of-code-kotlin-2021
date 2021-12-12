import Chunk.*

fun main() {
    val lines = readInput("Day10")
    val syntaxErrorScore = lines.mapNotNull(::check).sumOf {
        when (it) {
            PARENTHESIS -> 3L
            BRACKETS -> 57
            CURLY_BRACKETS -> 1197
            ANGLE_BRACKETS -> 25137
        }
    }
    println("Syntax Error Score: $syntaxErrorScore")
}

tailrec fun check(rest: String, expected: ArrayDeque<Chunk> = ArrayDeque()): Chunk? {
    if (rest.isEmpty()) {
        return null
    }
    val current = rest.first()
    val open = Chunk.forOpen(current)
    if (open != null) {
        expected.addFirst(open)
        return check(rest.drop(1), expected)
    }
    val close = Chunk.forClose(current)
    if (close != null) {
        val exp = expected.removeFirst()
        return if (exp == close) {
            check(rest.drop(1), expected)
        } else {
            close
        }
    }
    throw IllegalStateException("Invalid character $current")
}

enum class Chunk(val open: Char, val close: Char) {
    PARENTHESIS('(', ')'),
    BRACKETS('[', ']'),
    CURLY_BRACKETS('{', '}'),
    ANGLE_BRACKETS('<', '>');

    companion object {
        fun forOpen(open: Char): Chunk? {
            return values().find { it.open == open }
        }

        fun forClose(close: Char): Chunk? {
            return values().find { it.close == close }
        }
    }
}