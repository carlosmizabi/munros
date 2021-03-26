
data class Munro(
    val name: String = "",
    val height: Double? = null,
    val gridReference: String = "",
    val type: Type = Type.UNKNOWN
) {

    fun isTypeMatch(type: Type? = null): Boolean
        = (if (type != null) this.type == type else this.type != Type.UNKNOWN)

    fun isInRange(heightRange: Range? = null): Boolean
        = heightRange?.contains(height ?: 0.0) ?: true

    enum class Type {
        MUNRO,
        TOP,
        UNKNOWN
    }
}
