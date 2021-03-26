package testing

import Munro
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is

object TestMunros {
    val PARSED_TEST_DATA by lazy {  TestDataLoader.readFullDateFile() }

    fun assertMatch(result: Munro, expected: Munro) {
        result.apply {
            assertThat("name", name, Is(expected.name))
            assertThat("heightInMeters", height, Is(expected.height))
            assertThat("gridReference", gridReference, Is(expected.gridReference))
            assertThat("type", type, Is(expected.type))
        }
    }
}