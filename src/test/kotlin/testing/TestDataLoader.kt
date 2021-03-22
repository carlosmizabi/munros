package testing

import java.io.File
import java.lang.Exception

object TestDataLoader {

    const val FULL_DATA_FILE = "munrotab_v6.2.csv"

    fun readLinesFrom(file: String): List<String> {
        var lines = emptyList<String>()
        try {
            lines = javaClass.classLoader.getResource(file)?.let {
                File(it.file).readLines(Charsets.UTF_8)
            } ?: emptyList()
        } catch (error: Exception) {
            print(error)
        }
        return lines
    }
}