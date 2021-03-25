import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat as that
import org.junit.jupiter.api.Test
import testing.TestDataLoader
import testing.TestMunros.assertMatch

internal class MunroFinderShould {

    @Test
    fun `give us a list of all munros converted from a list of string lines`() {
        val collection = MunroFinder.parse(PARSED_TEST_DATA)
        val first = collection.munros.first()
        val last = collection.munros.last()
        that(collection.munros.size, Is(602))
        assertMatch(first, FIRST_MUNRO)
        assertMatch(last, LAST_MUNRO)
    }

    companion object {
        val PARSED_TEST_DATA = TestDataLoader.readFullDateFile()
        val FIRST_MUNRO = Munro(
            runningNo = 1,
            name = "Ben Chonzie",
            doBihNumber = 1,
            streetMapUrl = "\"http://www.streetmap.co.uk/newmap.srf?x=277324&y=730857&z=3&sv=277324,730857&st=4&tl=~&bi=~&lu=N&ar=y\"",
            geographyUrl = "http://www.geograph.org.uk/gridref/NN7732430857",
            hillBaggingUrl = "http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=1",
            smcSection = 1,
            rhbSection = "01A",
            section = 1.1,
            heightInMeters = 931.0,
            heightInFeet = 3054.0,
            map1to50 = "51 52",
            map1to25 = "OL47W 368W 379W",
            gridReference = "NN773308",
            gridReferenceXy = "NN7732430857",
            xCoordinate = 277324,
            yCoordinate = 730857,
            type = Munro.Type.MUNRO
        )
        val LAST_MUNRO = Munro(
            runningNo = 602,
            name = "Ben More",
            doBihNumber = 1301,
            streetMapUrl = "\"http://www.streetmap.co.uk/newmap.srf?x=152576&y=733078&z=3&sv=152576,733078&st=4&tl=~&bi=~&lu=N&ar=y\"",
            geographyUrl = "http://www.geograph.org.uk/gridref/NM5257633078",
            hillBaggingUrl = "http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=1301",
            smcSection = 17,
            rhbSection = "17E",
            section = 17.5,
            heightInMeters = 966.0,
            heightInFeet = 3169.0,
            map1to50 = "47 48",
            map1to25 = "375W",
            gridReference = "NM525330",
            gridReferenceXy = "NM5257633078",
            xCoordinate = 152576,
            yCoordinate = 733078,
            type = Munro.Type.MUNRO
        )
    }
}