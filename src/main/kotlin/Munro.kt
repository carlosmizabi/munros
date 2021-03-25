
data class Munro(
    val runningNo: Int? = null,
    val name: String = "",
    val doBihNumber: Int? = null,
    val streetMapUrl: String = "",
    val hillBaggingUrl: String = "",
    val geographyUrl: String = "",
    val smcSection: Int? = null,
    val rhbSection: String = "",
    val section: Double? = null,
    val heightInMeters: Double? = null,
    val heightInFeet: Double? = null,
    val map1to50: String = "",
    val map1to25: String = "",
    val gridReference: String = "",
    val gridReferenceXy: String = "",
    val xCoordinate: Int? = null,
    val yCoordinate: Int? = null,
    val type: Type = Type.UNKNOWN
) {

    fun isTypeMatch(type: Type? = null): Boolean
        = (if (type != null) this.type == type else this.type != Type.UNKNOWN)

    fun isInRange(heightRange: Range? = null): Boolean
        = heightRange?.contains(heightInMeters ?: 0.0) ?: true

    enum class Type {
        MUNRO,
        TOP,
        UNKNOWN
    }
}
