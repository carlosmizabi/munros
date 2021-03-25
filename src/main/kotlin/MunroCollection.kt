typealias Range = ClosedFloatingPointRange<Double>

data class MunroCollection(val munros: List<Munro>) {

    fun filter(type: Munro.Type? = null, heightRange: Range? = null): List<Munro> {
        return munros.filter { it.filter(type, heightRange) }
    }

    private fun Munro.filter(type: Munro.Type? = null, heightRange: Range? = null): Boolean
        = (if (type != null) type == this.type else type != Munro.Type.UNKNOWN)
            && heightRange?.contains(heightInMeters ?: 0.0) ?: true
}

