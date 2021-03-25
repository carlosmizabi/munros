object MunroDataParser {

    fun parse(data: List<String>): MunroCollection {
        val munros = data.mapNotNull { parseLine(it) }.filter { it.isValid }
        return MunroCollection(munros)
    }

    private fun parseLine(it: String): Munro? {
        if (it.isEmpty()) return null
        val values = it.intoEntries()
        return Munro(
            runningNo = values.elementAtOrNull(0)?.toIntOrNull(),
            doBihNumber = values.elementAtOrNull(1)?.toIntOrNull(),
            streetMapUrl = values.elementAtOrNull(2) ?: "",
            geographyUrl = values.elementAtOrNull(3) ?: "",
            hillBaggingUrl = values.elementAtOrNull(4) ?: "",
            name = values.elementAtOrNull(5) ?: "",
            smcSection = values.elementAtOrNull(6)?.toIntOrNull(),
            rhbSection = values.elementAtOrNull(7) ?: "",
            section = values.elementAtOrNull(8)?.toDoubleOrNull(),
            heightInMeters = values.elementAtOrNull(9)?.toIntOrNull(),
            heightInFeet = values.elementAtOrNull(10)?.toIntOrNull(),
            map1to50 = values.elementAtOrNull(11) ?: "",
            map1to25 = values.elementAtOrNull(12) ?: "",
            gridReference = values.elementAtOrNull(13) ?: "",
            gridReferenceXy = values.elementAtOrNull(14) ?: "",
            xCoordinate = values.elementAtOrNull(15)?.toIntOrNull(),
            yCoordinate = values.elementAtOrNull(16)?.toIntOrNull(),
            type = values.munro
        )
    }
}

const val ESCAPED_COMMA = "ʭ"
const val COMMA = ","
val QUOTED_TEXT_SELECTOR_REGEX = """ "([^"]*([^"]*)*)" """.trim().toRegex()

internal fun String.intoEntries(): List<String> {
    var escaped = this
    val quoted = QUOTED_TEXT_SELECTOR_REGEX.findAll(this)
    var replacementsCount = 0
    quoted.toList().let {
        it.forEach { mr: MatchResult ->
            if (mr.value.contains(",")) {
                val newText = mr.value.replace(COMMA, ESCAPED_COMMA)
                escaped = escaped.replaceRange(mr.range, newText)
                replacementsCount++
            }
        }
    }
    return escaped.split(COMMA).map { it.replace(ESCAPED_COMMA, COMMA) }
}

private val Munro?.isValid: Boolean
    get() = this?.name?.isNotEmpty() == true && this.xCoordinate != null && this.yCoordinate != null

private val List<String>.munro: Munro.Type
    get() {
        val value = elementAtOrNull(27) ?: ""
        return when {
            value.equals("mun", ignoreCase = true) -> Munro.Type.MUNRO
            value.equals("top", ignoreCase = true) -> Munro.Type.TOP
            else -> Munro.Type.UNKNOWN
        }
    }
