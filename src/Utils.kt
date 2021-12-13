import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Convert list of Ints to continuous number.
 */
fun List<Int>.joinToString() = this.joinToString("").toInt()

/**
 * Transpose key->value map to value->key map.
 */
fun <K, V> Map<K, V>.transpose() =
    emptyMap<V, K>() + this.map { (key, value) -> value to key }


/**
 * Transpose a list of String.
 */
fun transpose(original: List<String>): List<String> {
    val transposed = List(original.first().length) { StringBuilder("") }
    original.forEach { reading ->
        reading.mapIndexed { index, c ->
            transposed[index].append(c)
        }
    }
    return transposed.map { it.toString() }
}

/**
 * Generic Point class.
 */
data class Point(val x: Int, val y: Int) {
    fun straightNeighbours() = setOf(Point(x, y - 1), Point(x + 1, y), Point(x, y + 1), Point(x - 1, y))

    fun neighbours() = setOf(
        Point(x - 1, y - 1), Point(x, y - 1), Point(x + 1, y - 1),
        Point(x - 1, y), Point(x + 1, y),
        Point(x - 1, y + 1), Point(x, y + 1), Point(x + 1, y + 1)
    )
}