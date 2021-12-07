import kotlin.math.absoluteValue

fun main() {
    val input = readInput("Day05")
    val lines = input.map { line ->
        val coordinates = line.split(" -> ")
        val splitFrom = coordinates[0].split(",")
        val from = Point(splitFrom.first().toInt(), splitFrom.last().toInt())
        val splitTo = coordinates[1].split(",")
        val to = Point(splitTo.first().toInt(), splitTo.last().toInt())
        from to to
    }

    val world = lines.fold(mutableMapOf<Point, Int>()) { world, (from, to) ->
        world.increasePoints(from.toPoint(to))
    }

    val dangerousPoints = world.filterValues { it > 1 }
    println("Number of dangerous points: ${dangerousPoints.size}")
}

data class Point(val x: Int, val y: Int) {
    fun toPoint(other: Point): List<Point> {
        fun range(from: Int, to: Int): IntRange = if (from < to) from..to else to..from
        return if (this.x == other.x) {
            range(this.y, other.y).map { Point(this.x, it) }
        } else if (this.y == other.y) {
            range(this.x, other.x).map { Point(it, this.y) }
        } else {
            val fn: (Point) -> Point = if (this.x < other.x) {
                if (this.y < other.y) {
                    { p -> Point(p.x + 1, p.y + 1) }
                } else {
                    { p -> Point(p.x + 1, p.y - 1) }
                }
            } else {
                if (this.y < other.y) {
                    { p -> Point(p.x - 1, p.y + 1) }
                } else {
                    { p -> Point(p.x - 1, p.y - 1) }
                }
            }
            generateSequence(this, fn).take((this.x - other.x).absoluteValue).toList()
        }
    }
}

typealias World = MutableMap<Point, Int>

fun World.increasePoints(points: List<Point>): World {
    points.forEach { this.increasePoint(it) }
    return this
}

fun World.increasePoint(point: Point) {
    val value = this.getOrDefault(point, 0)
    this[point] = value + 1
}

