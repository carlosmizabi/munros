import org.junit.jupiter.api.Test
import testing.TestDataLoader
import testing.TestMunros.assertMatch
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
            that("runningNo", runningNo, Is(47))
            that("doBihNumber", doBihNumber, Is(112))
            that(
                "streetMapUrl",
                streetMapUrl,
                Is("\"http://www.streetmap.co.uk/newmap.srf?x=233178&y=740868&z=3&sv=233178,740868&st=4&tl=~&bi=~&lu=N&ar=y\"")
            )
            that("geographyUrl", geographyUrl, Is("http://www.geograph.org.uk/gridref/NN3317840868"))
            that("hillBaggingUrl", hillBaggingUrl, Is("http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=112"))
            that("name", name, Is("Beinn an Dothaidh"))
            that("smcSection", smcSection, Is(2))
            that(section, Is(2.1))
            that(rhbSection, Is("02A"))
            that(heightInMeters, Is(1004))
            that(heightInFeet, Is(3294))
            that(map1to25, Is("377E"))
            that(map1to50, Is("50"))
            that(gridReference, Is("NN331408"))
            that(gridReferenceXy, Is("NN3317840868"))
            that(xCoordinate, Is(233178))
            that(yCoordinate, Is(740868))
            that(type, Is(Munro.Type.MUNRO))
        }
    }
}

