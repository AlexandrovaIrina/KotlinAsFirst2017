@file:Suppress("UNUSED_PARAMETER")

package lesson2.task1

import lesson1.task1.discriminant
import java.lang.Math.*

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    return if (age / 10 == 1 || age / 10 == 11 || age % 10 in 5..9 || age % 10 == 0) age.toString() + " лет"
    else if (age % 10 == 1) age.toString() + " год"
    else age.toString() + " года"
}

/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    val s1 = t1 * v1
    val s2 = t2 * v2
    val s3 = t3 * v3
    val s = (s1 + s2 + s3) / 2
    return when {
        s < s1 -> s / v1
        s == s1 -> t1
        s > s1 && s < s2 + s1 -> t1 + (s - s1) / v2
        s == s2 + s1 -> t2 + t1
        else -> t1 + t2 + (s - s1 - s2) / v3
    }
}

/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    val sameX1 = kingX == rookX1
    val sameY1 = kingY == rookY1
    val sameX2 = kingX == rookX2
    val sameY2 = kingY == rookY2
    return if ((sameX1 || sameY1) && !sameX2 && !sameY2) 1
    else if ((sameX2 || sameY2) && !sameX1 && !sameY1) 2
    else if ((sameX2 || sameY2) && (sameX1 || sameY1)) 3
    else 0
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    val sameDiagonalBishop = abs(kingX - bishopX) == abs(kingY - bishopY)
    val sameLineRook = kingX == rookX || kingY == rookY
    return when {
        sameDiagonalBishop && sameLineRook -> 3
        !sameDiagonalBishop && sameLineRook -> 1
        sameDiagonalBishop && !sameLineRook -> 2
        else -> 0
    }
}


/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun triangleKind(a: Double, b: Double, c: Double): Int {
    var biggestSide = a
    var side2 = b
    var side3 = c
    if (side2 > biggestSide && side2 > side3) {
        var l = biggestSide
        biggestSide = side2
        side2 = l
    } else if (side3 > biggestSide && side3 > side2) {
        var l = biggestSide
        biggestSide = side3
        side3 = l
    }
    if (biggestSide >= side2 + side3) return -1
    val cosAlpha = (side2 * side2 + side3 * side3 - biggestSide * biggestSide )/ (2 * side2 * side3)
    return when {
        cosAlpha > 0 -> 0
        cosAlpha == 0.0 -> 1
        else -> 2
    }
}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    if (c > b || a > d) return -1
    return when {
        a >= c && b <= d -> b - a
        b >= d && a >= c -> d - a
        a <= c && d <= b -> d - c
        else -> b - c
    }
}
