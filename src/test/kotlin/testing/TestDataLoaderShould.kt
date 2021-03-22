package testing
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.MatcherAssert.assertThat as that
import org.junit.jupiter.api.Test

class TestDataLoaderShould {

    @Test
    fun `be able to load files` () {
        val lines = TestDataLoader.readLinesFrom(TestDataLoader.FULL_DATA_FILE)
        that(lines.isNotEmpty(), Is(true))
        that(lines[0], Is(FULL_DATA_FIRST_LINE))
    }

    companion object {
        const val FULL_DATA_FIRST_LINE = "Running No,DoBIH Number,Streetmap,Geograph,Hill-bagging,Name,SMC Section,RHB Section,_Section,Height (m),Height (ft),Map 1:50,Map 1:25,Grid Ref,GridRefXY,xcoord,ycoord,1891,1921,1933,1953,1969,1974,1981,1984,1990,1997,Post 1997,Comments"
    }
}