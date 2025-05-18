package serie2.point

data class Point(val id: Int, val x: Double, val y: Double) {
    override fun toString(): String = "v $id $x $y"

    override fun hashCode(): Int {
        return id.hashCode() * 31 + x.hashCode() * 17 + y.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Point &&
                other.id == id &&
                other.x == x &&
                other.y == y
    }
}