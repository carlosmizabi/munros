data class MunroCollection(val munros: List<Munro>) {

    fun filter(type: Munro.Type? = null): List<Munro> {
        return munros.filter{ it.type == type }
    }
}