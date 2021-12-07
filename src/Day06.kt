fun main() {
    val input = readInput("Day06")
    val initialFishPopulation = input.first().split(",").map { it.toInt() }
    println(initialFishPopulation)

    val evolution = generateSequence(initialFishPopulation) { population ->
        population.flatMap { fish ->
            if (fish == 0) {
                listOf(6, 8)
            } else {
                listOf(fish - 1)
            }
        }
    }

    evolution.take(81).forEachIndexed { day, population ->
        println("$day: ${population.size}")
    }
}

