fun main() {
    val ground = readInput("Day09")
        .foldIndexed(mutableMapOf<P, Int>()) { x, m, line ->
            line.forEachIndexed { y, c -> m[P(x, y)] = c.digitToInt() }
            m
        }

    val lowestPositions = ground.filter { (point, value) ->
        point.neighbours().mapNotNull { ground[it] }.all { value < it }
    }
    val riskLevel = lowestPositions.values.sumOf { 1 + it }

    val cleanedGround = ground.filter { it.value < 9 }.keys
    val basinProduct = findBasins(cleanedGround.toMutableSet())
        .map { it.count() }
        .sortedDescending()
        .take(3)
        .fold(1) { p, v -> p * v }

    println("Risk Level: $riskLevel")
    println("Basin Product: $basinProduct")
}

data class P(private val x: Int, private val y: Int) {
    fun neighbours(): Set<P> {
        return setOf(P(x, y - 1), P(x + 1, y), P(x, y + 1), P(x - 1, y))
    }
}

// Had to use MutableSet for the next functions due to too much overhead for the immutable variants

fun findNeighbours(current: P, left: MutableSet<P>): Set<P> {
    val leftNeighbours = current.neighbours().filter { left.contains(it) }.toSet()
    left.removeAll(leftNeighbours)
    left.remove(current)
    val neighbours = mutableSetOf(current)
    neighbours.addAll(leftNeighbours.flatMap { findNeighbours(it, left) })
    return neighbours
}

fun findBasins(left: MutableSet<P>): List<Set<P>> {
    return if (left.isEmpty()) {
        emptyList()
    } else {
        val basin = findNeighbours(left.first(), left)
        left.removeAll(basin)
        listOf(basin) + findBasins(left)
    }
}