data class MunroCollection(val munros: List<Munro>) {

    fun filter(type: Munro.Type? = null): List<Munro> {
        return munros.filter {
            if (type != null) it.type == type
            else it.type != Munro.Type.UNKNOWN
        }
    }
}