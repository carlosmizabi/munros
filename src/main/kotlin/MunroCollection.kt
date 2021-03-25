typealias Range = ClosedFloatingPointRange<Double>

data class MunroCollection(val munros: List<Munro>) {

    fun filter(type: Munro.Type? = null, heightRange: Range? = null): List<Munro> {
        return munros.filter {
            (if (type != null) it.type == type else it.type != Munro.Type.UNKNOWN)
                    && heightRange?.contains(it.heightInMeters ?: 0.0) ?: true
        }
    }
}

