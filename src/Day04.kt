fun main() {
    val lines = readInput("Day04")
    val numbers = lines[0].split(",").map { it.toInt() }
    val initial = Game.parse(lines.drop(1))

    val boards = generateSequence(initial to numbers) { (game, numbers) ->
        game.filter { board -> !board.hasWon() }.draw(numbers.first()) to numbers.drop(1)
    }
    val winnerScore = boards.firstNotNullOf { (game, _) ->
        game.score()
    }
    println("Winner Score: $winnerScore")

    val lastScore = boards.firstNotNullOf { (game, _) ->
        if (game.boards.size == 1) {
            game.score()
        } else {
            null
        }
    }
    println("Last Score: $lastScore")
}

data class Game(val boards: List<Board>, val number: Int?, val round: Int = 0) {

    companion object {
        fun parse(input: List<String>): Game {
            val groups = input.fold(mutableListOf<MutableList<String>>()) { boards, line ->
                if (line.isBlank()) {
                    boards.add(mutableListOf())
                } else {
                    boards.last().add(line)
                }
                boards
            }
            return Game(groups.map(Board.Companion::parse), null)
        }
    }

    fun draw(number: Int): Game {
        return Game(boards.map { it.draw(number) }, number, round + 1)
    }

    fun score(): Int? {
        val winner = winner()
        if (winner != null && number != null) {
            return winner.unmarkedSum() * number
        }
        return null
    }

    private fun winner(): Board? {
        return boards.find { it.hasWon() }
    }

    private fun hasWinner(): Boolean {
        return winner() != null
    }

    fun filter(predicate: (Board) -> Boolean): Game {
        return Game(boards.filter(predicate), number, round)
    }

    override fun toString(): String {
        return """
Round: $round
Number: $number
Won: ${hasWinner()}
Boards: 
${boards.joinToString("\n\n")}
""".trimIndent()
    }
}

data class Board(val positions: List<List<Pair<Int, Boolean>>>) {
    companion object {
        fun parse(lines: List<String>): Board {
            return Board(lines.map { line ->
                line.split(" ").filter(String::isNotBlank).map {
                    it.toInt() to false
                }
            })
        }
    }

    fun draw(number: Int): Board {
        return Board(positions.map { line ->
            line.map { position ->
                if (position.first == number) {
                    position.first to true
                } else {
                    position
                }
            }
        })
    }

    fun hasWon(): Boolean {
        val candidates =
            positions.plus(
                (positions.indices).map { index ->
                    positions.map { it[index] }
                })
        return candidates.any { candidate ->
            candidate.all { it.second }
        }
    }

    fun unmarkedSum(): Int {
        return positions.sumOf { row -> row.sumOf { if (it.second) 0 else it.first } }
    }

    override fun toString(): String {
        return positions.joinToString("\n") { line ->
            line.joinToString(",") { (position, drawn) ->
                if (drawn) {
                    "[%3s]".format(position)
                } else {
                    "%5s".format(position)
                }
            }
        }
    }
}

