import kotlin.math.absoluteValue

fun main() {
    val input = readInput("Day07")
    val crabPositions = input.first().split(",").map { it.toInt() }
    println(crabPositions)

    val min = crabPositions.minOf { it }
    val max = crabPositions.maxOf { it }
    val positions = min .. max

    val costsPerPosition = positions.fold(mutableMapOf<Int, Int>()) { costs, target ->
        costs[target] = crabPositions.sumOf { (it - target).absoluteValue }
        costs
    }
    val minCosts = costsPerPosition.minByOrNull { it.value }

    println("Positional costs: $costsPerPosition")
    println("Min. costs at position ${minCosts?.key}: ${minCosts?.value}")
}

