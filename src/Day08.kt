fun main() {
    val readings = parseInputFile("Day08")

    val digits = listOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")
    val mappings = buildAllPossibleMappings()

    val results = readings.map { (patterns, numbers) ->
        mappings.filter { mapping ->
            patterns.all { pattern ->
                digits.any { digit ->
                    digit.length == pattern.length
                            && digit.all { segment ->
                        pattern.contains(mapping.getOrDefault(segment, 'X'))
                    }
                }
            }
        }.firstNotNullOf { mapping ->
            val transposed = mapping.transpose()
            numbers.map { number ->
                val decoded = number.map { transposed[it] }.sortedBy { it }.joinToString("")
                digits.indexOf(decoded)
            }
        }
    }

    val easy = results.flatten().count { listOf(1, 4, 7, 8).contains(it) }
    val hard = results.sumOf { it.joinToString() }

    println("Part 1: $easy")
    println("Part 2: $hard")
}

fun parseInputFile(name: String): List<Pair<List<String>, List<String>>> {
    val lines = readInput(name)
    val outputs = lines.map { line ->
        val parts = line.split(" | ")
        val patterns = parts.first().split(" ")
        val results = parts.last().split(" ")
        patterns to results
    }
    return outputs
}

fun buildAllPossibleMappings(): Sequence<Map<Char, Char>> {
    fun permute(value: String, result: String = ""): List<String> {
        return if (value.isEmpty()) {
            listOf(result)
        } else {
            value.flatMapIndexed { i, c ->
                permute(value.removeRange(i, i + 1), result + c)
            }
        }
    }

    val segments = "abcdefg"
    return permute(segments).asSequence().map { permutation ->
        segments.zip(permutation).toMap()
    }
}

fun <K, V> Map<K, V>.transpose() =
    emptyMap<V, K>() + this.map { (key, value) -> value to key }

fun List<Int>.joinToString() = this.joinToString("").toInt()
