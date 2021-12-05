fun main() {
    val readings = readInput("Day03")

    val gammaRate = transpose(readings).map(::mostCommonCharIn).joinToString("")
    val gammaRateInt = gammaRate.toInt(2)
    val epsilonRate = gammaRate.map { if (it == '0') '1' else '0' }.joinToString("")
    val epsilonRateInt = epsilonRate.toInt(2)
    val powerConsumption = gammaRateInt * epsilonRateInt

    val oxygenGeneratorRating = filterByCharBasedOnSelector(readings, ::mostCommonCharIn)
    val oxygenGeneratorRatingInt = oxygenGeneratorRating.toInt(2)
    val co2ScrubberRating = filterByCharBasedOnSelector(readings, ::leastCommonCharIn)
    val co2ScrubberRatingInt = co2ScrubberRating.toInt(2)
    val lifeSupportRating = oxygenGeneratorRatingInt * co2ScrubberRatingInt

    println("             Gamma rate: $gammaRate ($gammaRateInt)")
    println("           Epsilon rate: $epsilonRate ($epsilonRateInt)")
    println("      Power consumption: $powerConsumption")
    println("-----------------------")
    println("Oxygen Generator Rating: $oxygenGeneratorRating ($oxygenGeneratorRatingInt)")
    println("    CO2 Scrubber Rating: $co2ScrubberRating ($co2ScrubberRatingInt)")
    println("    Life Support Rating: $lifeSupportRating")
}

private fun charWithDistribution(string: String, selector: (Map<Char, Int>) -> Int, preference: Char = '1'): Char {
    val charDistribution = string.groupBy { it }.mapValues { it.value.size }
    val selectedValue = selector(charDistribution)
    val selectedChars = charDistribution.filter { it.value == selectedValue }
    return if (selectedChars.size > 1 && selectedChars.containsKey(preference)) {
        preference
    } else {
        selectedChars.firstNotNullOf { it.key }
    }
}

private fun mostCommonCharIn(string: String): Char {
    return charWithDistribution(string, { distribution ->
        distribution.maxOf { it.value }
    }, '1')
}

private fun leastCommonCharIn(string: String): Char {
    return charWithDistribution(string, { distribution ->
        distribution.minOf { it.value }
    }, '0')
}

private tailrec fun filterByCharBasedOnSelector(
    readings: List<String>,
    selector: (String) -> Char,
    index: Int = 0
): String {
    if (readings.size == 1) {
        return readings[0]
    }
    val column = transpose(readings)[index]
    val selectedChar = selector(column)
    val matched = readings.filter {
        it[index] == selectedChar
    }
    return filterByCharBasedOnSelector(matched, selector, index + 1)
}

