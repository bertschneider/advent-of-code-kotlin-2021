fun main() {

    // This so super inefficient but quite nice from an API perspective
    fun countWindowsLargerThanPrevious(input: List<String>, windowSize: Int = 3): Int =
            input.map { x -> x.toInt() }.windowed(windowSize).windowed(2).count { (a, b) -> b.sum() > a.sum() }

    fun countNumbersLargerThanPrevious(inputs: List<String>) =
            countWindowsLargerThanPrevious(inputs, windowSize = 1)

    val measurements = readInput("Day01")
    println(countNumbersLargerThanPrevious(measurements))
    println(countWindowsLargerThanPrevious(measurements))
}
