import Chunk.*

fun main() {
    val lines = readInput("Day10")
    val syntaxCheck = lines.map(::check)

    val syntaxErrorScore = syntaxCheck.mapNotNull { it.first }.sumOf {
        when (it) {
            PARENTHESIS -> 3L
            BRACKETS -> 57
            CURLY_BRACKETS -> 1197
            ANGLE_BRACKETS -> 25137
        }
    }

    val completionScores = syntaxCheck.filter { it.first == null }
        .map { (_, expected) ->
            expected.fold(0L) { sum, chunk ->
                sum * 5 +
                        when (chunk) {
                            PARENTHESIS -> 1
                            BRACKETS -> 2
                            CURLY_BRACKETS -> 3
                            ANGLE_BRACKETS -> 4
                        }
            }
        }.sorted()
    val medianCompletionScore = completionScores[completionScores.size / 2]

    println("Syntax Error Score: $syntaxErrorScore")
    println("Median Completing Score: $medianCompletionScore")
}

tailrec fun check(rest: String, expected: ArrayDeque<Chunk> = ArrayDeque()): Pair<Chunk?, List<Chunk>> {
    if (rest.isEmpty()) {
        return null to expected
    }
    val current = rest.first()
    val open = Chunk.forOpen(current)
    if (open != null) {
        expected.addFirst(open)
        return check(rest.drop(1), expected)
    }
    val close = Chunk.forClose(current)
    if (close != null) {
        val exp = expected.firstOrNull()
        return if (exp == close) {
            expected.removeFirst()
            check(rest.drop(1), expected)
        } else {
            close to expected
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