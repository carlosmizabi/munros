package testing

import Munro
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.CoreMatchers.`is` as Is

object TestMunros {
    val PARSED_TEST_DATA by lazy {  TestDataLoader.readFullDateFile() }

    fun assertMatch(result: Munro, expected: Munro) {
        result.apply {
            assertThat("runningNo", runningNo, Is(expected.runningNo))
            assertThat("doBihNumber", doBihNumber, Is(expected.doBihNumber))
            assertThat("streetMapUrl", streetMapUrl, Is(expected.streetMapUrl))
            assertThat("geographyUrl", geographyUrl, Is(expected.geographyUrl))
            assertThat("hillBaggingUrl", hillBaggingUrl, Is(expected.hillBaggingUrl))
            assertThat("name", name, Is(expected.name))
            assertThat("smcSection", smcSection, Is(expected.smcSection))
            assertThat("section", section, Is(expected.section))
            assertThat("rhbSection", rhbSection, Is(expected.rhbSection))
            assertThat("heightInMeters", heightInMeters, Is(expected.heightInMeters))
            assertThat("heightInFeet", heightInFeet, Is(expected.heightInFeet))
            assertThat("map1to25", map1to25, Is(expected.map1to25))
            assertThat("map1to50", map1to50, Is(expected.map1to50))
            assertThat("gridReference", gridReference, Is(expected.gridReference))
            assertThat("gridReferenceXy", gridReferenceXy, Is(expected.gridReferenceXy))
            assertThat("xCoordinate", xCoordinate, Is(expected.xCoordinate))
            assertThat("yCoordinate", yCoordinate, Is(expected.yCoordinate))
            assertThat("type", type, Is(expected.type))
        }
    }
}