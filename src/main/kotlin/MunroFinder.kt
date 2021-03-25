object MunroFinder {
    fun parse(lines: List<String>): MunroCollection {
        return MunroDataParser.parse(lines)
    }
}