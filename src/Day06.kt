fun main() {
    val input = readInput("Day06")
    val initialFishPopulation = input.first().split(",").map { it.toInt() }.groupBy { it }.mapValues { it.value.count().toLong() }
    println(initialFishPopulation)

    val days = (0 .. 8).reversed()
    val evolution = generateSequence(initialFishPopulation) { dailyPopulation ->
        days.fold(mutableMapOf()) { nextPopulation, day ->
            if (day == 0) {
                val reproductionCount = dailyPopulation.getOrDefault(day, 0)
                nextPopulation[8] = reproductionCount
                nextPopulation[6] = nextPopulation.getOrDefault(6, 0) + reproductionCount
            } else {
                nextPopulation[day - 1] = dailyPopulation.getOrDefault(day, 0)
            }
            nextPopulation
        }
    }

    evolution.take(257).forEachIndexed { day, population ->
        println("$day: ${population.values.sum()}")
    }
}

