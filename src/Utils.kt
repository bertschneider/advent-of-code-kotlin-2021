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