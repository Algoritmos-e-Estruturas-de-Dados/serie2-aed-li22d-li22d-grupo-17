package serie2.problema.implementação2

import serie2.point.Point
import serie2.part4.HashMap
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

fun readPointsToMap(file: String, map: HashMap<Point, Boolean>) {
    File(file).forEachLine { parsePoint(it)?.let { point -> map.put(point, true) } }
}

fun writePoints(file: String, points: List<Point>) {
    File(file).printWriter().use { writer ->
        points.forEach { writer.println(it) }
    }
}

fun getKeys(map: HashMap<Point, Boolean>): List<Point> {
    val result = mutableListOf<Point>()
    for (entry in map) {
        result.add(entry.key)
    }
    return result
}

fun union(map1: HashMap<Point, Boolean>, map2: HashMap<Point, Boolean>): HashMap<Point, Boolean> {
    val result = HashMap<Point, Boolean>()
    for (entry in map1) result.put(entry.key, true)
    for (entry in map2) result.put(entry.key, true)
    return result
}

fun intersection(map1: HashMap<Point, Boolean>, map2: HashMap<Point, Boolean>): HashMap<Point, Boolean> {
    val result = HashMap<Point, Boolean>()
    for (entry in map1) {
        if (map2.get(entry.key) != null) result.put(entry.key, true)
    }
    return result
}

fun difference(map1: HashMap<Point, Boolean>, map2: HashMap<Point, Boolean>): HashMap<Point, Boolean> {
    val result = HashMap<Point, Boolean>()
    for (entry in map1) {
        if (map2.get(entry.key) == null) result.put(entry.key, true)
    }
    return result
}

fun main() {
    val map1 = HashMap<Point, Boolean>()
    val map2 = HashMap<Point, Boolean>()

    println("Versão V2 (HashMap personalizado)")
    while (true) {
        print("> ")
        val input = readlnOrNull()?.split(" ") ?: continue
        when (input.firstOrNull()) {
            "load" -> {
                if (input.size == 3) {
                    map1.clear()
                    map2.clear()
                    readPointsToMap(input[1], map1)
                    readPointsToMap(input[2], map2)
                    println("Ficheiros carregados.")
                }
            }
            "union" -> {
                val duration = measureTimeMillis {
                    val result = union(map1, map2)
                    writePoints(input[1], getKeys(result))
                }
                println("União feita em $duration ms.")
            }
            "intersection" -> {
                val duration = measureTimeMillis {
                    val result = intersection(map1, map2)
                    writePoints(input[1], getKeys(result))
                }
                println("Interseção feita em $duration ms.")
            }
            "difference" -> {
                val duration = measureTimeMillis {
                    val result = difference(map1, map2)
                    writePoints(input[1], getKeys(result))
                }
                println("Diferença feita em $duration ms.")
            }
            "exit" -> return
        }
    }
}