@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(val points: Set<Point>) {

    val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */

    fun distance(other: Circle): Double {
        val distanceBetweenCenters = center.distance(other.center)
        val circlesIntersect = distanceBetweenCenters <= other.radius + radius
        if (circlesIntersect) return 0.0
        else {
            return distanceBetweenCenters - other.radius - radius
        }
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean {
        val distanceBetweenPointAndCenter = center.distance(p)
        return distanceBetweenPointAndCenter - radius <= 10e-14
    }
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */

fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException("diameter")
    var maxDistance = 0.0
    var ansPoints = Pair(Point(0.0, 0.0), Point(0.0, 0.0))
    for (i in 0 until points.size - 1) {
        for (j in i + 1 until points.size) {
            val distance = points[i].distance(points[j])
            if (distance > maxDistance) {
                maxDistance = distance
                ansPoints = Pair(points[i], points[j])
            }
        }
    }
    return Segment(ansPoints.first, ansPoints.second)
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val centerOfCircle = Point((diameter.end.x + diameter.begin.x) / 2, (diameter.end.y + diameter.begin.y) / 2)
    val radiusOfCenter = diameter.begin.distance(diameter.end) / 2
    return Circle(centerOfCircle, radiusOfCenter)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        var xPoint: Double
        var yPoint: Double
        if (angle == PI / 2) {
            yPoint = -b * sin(other.angle) / cos(other.angle) + other.b / cos(other.angle)
            return Point(-b, yPoint)
        }
        if (other.angle == PI / 2) {
            yPoint = -other.b * sin(angle) / cos(angle) + b / cos(angle)
            return Point(-other.b, yPoint)
        }
        xPoint = (other.b / cos(other.angle) - b / cos(angle)) / (sin(angle) / cos(angle) - sin(other.angle) / cos(other.angle))
        yPoint = xPoint * sin(angle) / cos(angle) + b / cos(angle)
        return Point(xPoint, yPoint)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun lineBySegment(s: Segment): Line {
    if (s.begin.x == s.end.x) return Line(s.begin, PI / 2)
    var alpha = (s.end.y - s.begin.y) / (s.end.x - s.begin.x)
    alpha = atan(alpha)
    if (alpha < 0) alpha += PI
    return Line(s.begin, alpha)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))

/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val bisectorX = (a.x + b.x) / 2
    val bisectorY = (a.y + b.y) / 2
    if (a.x == b.x) return Line(Point(bisectorX, bisectorY), 0.0)
    var alpha = lineByPoints(a, b).angle + PI / 2
    if (alpha > PI) alpha -= PI
    return Line(Point(bisectorX, bisectorY), alpha)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException("findNearestCirclePair")
    var ans = Pair(circles[0], circles[1])
    var distanceBetweenCircles = circles[0].distance(circles[1])
    for (i in 0 until circles.size - 1) {
        for (j in i + 1 until circles.size) {
            if (distanceBetweenCircles > circles[i].distance(circles[j])) {
                ans = Pair(circles[i], circles[j])
                distanceBetweenCircles = circles[i].distance(circles[j])
            }
        }
    }
    return ans
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val bisectorSideAB = bisectorByPoints(a, b)
    val bisectorSideBC = bisectorByPoints(b, c)
    val centerOfCircle = bisectorSideAB.crossPoint(bisectorSideBC)
    val radiusOfCircle = centerOfCircle.distance(a)
    return Circle(centerOfCircle, radiusOfCircle)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle {
    when (points.size) {
        1 -> return Circle(points[0], 0.0)
        0 -> throw IllegalArgumentException("minContainingCircle")
    }
    val ansPoints = diameter(*points)
    var ans = circleByDiameter(ansPoints)
    var flag = true
    var ind = 0
    while (flag && ind < points.size) {
        flag = ans.contains(points[ind])
        ind++
    }
    if (!flag) {
        var farthestPoint = points[0]
        var maxDistance = farthestPoint.distance(ans.center)
        for (i in 1 until points.size) {
            val currentDistance = points[i].distance(ans.center)
            if (currentDistance > maxDistance) {
                maxDistance = currentDistance
                farthestPoint = points[i]
            }
        }

        ans = circleByThreePoints(ansPoints.begin, ansPoints.end, farthestPoint)
    }
    return ans
}

