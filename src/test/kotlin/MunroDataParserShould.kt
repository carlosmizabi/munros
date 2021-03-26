import org.junit.jupiter.api.Test
import testing.TestDataLoader
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat as that

internal class MunroDataParserShould {

    data class Case(val info: String, val input: String, val result: List<String>)

    private val cases = listOf(
        Case(info = "No comma produces one entry",
            input = "",
            result = listOf("")
        ),
        Case(info = """
                |One comma produces two entries; 
                |one for before and another for after the comma""",
            input = ",",
            result = listOf("", "")
        ),
        Case(info = """
                | An input with only four commas
                | will produce one entry before the first comma
                | plus one after each comma, so 4 empty entries
                """,
            input = ",,,",
            result = listOf("", "", "", "")
        ),
        Case(info = "No comma produces one entry",
            input = "one",
            result = listOf("one")
        ),
        Case(info = "One entry ending with a double quote will become an identical entry",
            input = "one\"",
            result = listOf("one\"")
        ),
        Case(info = "One entry starting in a double quote will be come a identical entry",
            input = "\"one",
            result = listOf("\"one")
        ),
        Case(info = """
                | A entry with a comma at the end will give us two entries; 
                | one for the word and empty entry for after the comma""",
            input = "one,",
            result = listOf("one", "")
        ),
        Case(
            info = "First and Last are words with two empty entries in the middle",
            input = """one,,,four""",
            result = listOf("one", "", "", "four")
        ),
        Case(info = """
            | Two Double quotes in between two commas will
            | produce an entry with just two double quotes""",
            input = """one,"",1,four,""",
            result = listOf("one", "\"\"", "1", "four", "")
        ),
        Case(info = "All quoted entries will be preserved",
            input = """"one","two","3","four"""",
            result = listOf("\"one\"", "\"two\"", "\"3\"", "\"four\"")
        ),
        Case(info = "A comma in between double quotes will be preserved",
            input = """one,two,"1,2",three""",
            result = listOf("one", "two", "\"1,2\"", "three")
        ),

        Case(info = "A double quote without a another subsequent double will, be preserved",
            input = """one,two,"1,2","three,4""",
            result = listOf("one", "two", "\"1,2\"", "\"three", "4")
        ),
        Case(
            info = "All commas between a pair of double quotes will be preserved",
            input = """"1,1","2,2,2","3,3,3,3","4,4,4,4,4"""",
            result = listOf("\"1,1\"", "\"2,2,2\"", "\"3,3,3,3\"", "\"4,4,4,4,4\"")
        ),
        Case(
            info = """Quotes are preserved""",
            input = """ "","","","" """.trim(),
            result = listOf("\"\"", "\"\"", "\"\"", "\"\"",)
        ),
        Case(
            info = """Spaces are preserved""",
            input = """ " " , "" , " " ,"" """,
            result = listOf(""" " " """, """ "" """, """ " " ""","\"\" ")
        ),
    )

    @Test
    fun `values in quotes should be handled correctly`() {
        cases.forEach { (info, data, expected) ->
            val result = data.intoEntries()
            result.forEach { println(it) }
            println(result)
            that(info, result.size, Is(expected.size))
            that(info, result, Is(expected))
        }
    }

    @Test
    fun `take a list of lines and gives us a collection of munros`() {
        val data = TestDataLoader.readLinesFrom("one_munro.csv")
        MunroDataParser.parse(data).munros.last().apply {
            that("name", name, Is("Beinn an Dothaidh"))
            that(height, Is(1004.0))
            that(gridReference, Is("NN331408"))
            that(type, Is(Munro.Type.MUNRO))
        }
    }
}

