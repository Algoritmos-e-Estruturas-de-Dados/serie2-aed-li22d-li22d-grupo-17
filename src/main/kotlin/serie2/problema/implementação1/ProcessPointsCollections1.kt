package serie2.problema.implementação1
import serie2.point.Point
import java.io.File
import kotlin.system.measureTimeMillis

fun parsePoint(line: String): Point? {
    val parts = line.trim().split(" ")
    if (parts.size == 4 && parts[0] == "v") {
        val id = parts[1].toIntOrNull()
        val x = parts[2].toDoubleOrNull()
        val y = parts[3].toDoubleOrNull()
        if (id != null && x != null && y != null) return Point(id, x, y)
    }
    return null
}

fun readPointsSet(file: String): Set<Point> {
    val set = mutableSetOf<Point>()
    File(file).forEachLine { parsePoint(it)?.let(set::add) }
    return set
}

fun writePoints(file: String, points: Set<Point>) {
    File(file).printWriter().use { writer ->
        points.forEach { writer.println(it) }
    }
}

fun main() {
    var set1 = emptySet<Point>()
    var set2 = emptySet<Point>()

    println("Versão V1 (Kotlin HashSet)")
    while (true) {
        print("> ")
        val input = readlnOrNull()?.split(" ") ?: continue
        when (input.firstOrNull()) {
            "load" -> {
                if (input.size == 3) {
                    set1 = readPointsSet(input[1])
                    set2 = readPointsSet(input[2])
                    println("Ficheiros carregados.")
                }
            }
            "union" -> {
                val duration = measureTimeMillis {
                    val result = set1 union set2
                    writePoints(input[1], result)
                }
                println("União feita em $duration ms.")
            }
            "intersection" -> {
                val duration = measureTimeMillis {
                    val result = set1 intersect set2
                    writePoints(input[1], result)
                }
                println("Interseção feita em $duration ms.")
            }
            "difference" -> {
                val duration = measureTimeMillis {
                    val result = set1 subtract set2
                    writePoints(input[1], result)
                }
                println("Diferença feita em $duration ms.")
            }
            "exit" -> return
        }
    }
}

