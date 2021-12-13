fun main() {
    val readings = loadReadings("Day08")

    val digits = listOf("abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg")
    val mappings = buildAllPossibleMappings("abcdefg")

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

fun loadReadings(name: String): List<Pair<List<String>, List<String>>> {
    val lines = readInput(name)
    return lines.map { line ->
        line.split(" | ").let { (p, r) ->
            val patterns = p.split(" ")
            val results = r.split(" ")
            patterns to results
        }
    }
}

fun buildAllPossibleMappings(segments: String): Sequence<Map<Char, Char>> {
    fun permute(value: String, result: String = ""): List<String> {
        return if (value.isEmpty()) {
            listOf(result)
        } else {
            value.flatMapIndexed { i, c ->
                permute(value.removeRange(i, i + 1), result + c)
            }
        }
    }

    return permute(segments).asSequence().map { permutation ->
        segments.zip(permutation).toMap()
    }
}
