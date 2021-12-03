fun main() {
    val readings = readInput("Day03")
    val transpose = transpose(readings)
    val threshold = readings.size / 2

    val gammaRate = transpose.map { line ->
        line.map { it.digitToInt() }.sum()
    }.joinToString("") {
        if (it > threshold) "1" else "0"
    }
    val gammaRateInt = gammaRate.toInt(2)
    val epsilonRate = gammaRate.map { if (it == '0') '1' else '0' }.joinToString("")
    val epsilonRateInt = epsilonRate.toInt(2)
    val powerConsumption = gammaRateInt * epsilonRateInt

    println("  Gamma rate: $gammaRate ($gammaRateInt)")
    println("Epsilon rate: $epsilonRate ($epsilonRateInt)")
    println("Power consumption: $powerConsumption")
}

