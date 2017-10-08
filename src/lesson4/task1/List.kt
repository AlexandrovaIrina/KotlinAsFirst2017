@file:Suppress("UNUSED_PARAMETER")
package lesson4.task1

import lesson1.task1.discriminant
import java.lang.Math.*

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = Math.sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var ans = 0.0
    for (i in 0 until v.size) {
        ans += v[i] * v[i]
    }
    return sqrt(ans)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double{
    var ans = 0.0
    if(list.isEmpty()) return 0.0
    for(element in list){
        ans += element
    }
    return ans / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double>{
    var meanOfList = mean(list)
    for(i in 0 until list.size){
        list[i] -= meanOfList
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double {
    var ans = 0.0
    for(i in 0 until a.size){
        ans += a[i] * b[i]
    }
    return ans
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var ans = 0.0
    var powOfX = 1.0
    for(i in 0 until p.size){
        ans += p[i] * powOfX
        powOfX *= x
    }
    return ans
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double>{
    for(i in 1 until list.size){
        list[i] += list[i - 1]
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun isPrime(n: Int): Boolean{
    if(n < 2) return false
    for(i in 2..sqrt(n.toDouble()).toInt())
        if(n % i == 0) return false
    return true
}
fun factorize(n: Int): List<Int> {
    var number = n
    var ans = listOf<Int>()
    var fact = 2
    while (number != 1) {
        while (!isPrime(fact)) fact++
        while (number % fact == 0) {
            ans += fact
            number /= fact
        }
        fact++
    }
    return ans
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 */
fun factorizeToString(n: Int): String{
    if(isPrime(n)) return n.toString()
    return factorize(n).joinToString(separator = "*")
}

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int>{
    var ans = mutableListOf<Int>()
    var number = n
    while (number >= base) {
        ans.add(number % base)
        number /= base
    }
    ans.add(number)
    for (i in 0..(ans.size - 1) / 2) {
        var element = ans[i]
        ans[i] = ans[ans.size - 1 - i]
        ans[ans.size - 1 - i] = element
    }
    return ans
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    var list = convert(n, base)
    var ans = mutableListOf<Any>()
    for (i in 0 until list.size) {
        when (list[i]) {
            10 -> ans.add('a')
            11 -> ans.add('b')
            12 -> ans.add('c')
            13 -> ans.add('d')
            14 -> ans.add('e')
            15 -> ans.add('f')
            16 -> ans.add('g')
            17 -> ans.add('h')
            18 -> ans.add('i')
            19 -> ans.add('j')
            20 -> ans.add('k')
            21 -> ans.add('l')
            22 -> ans.add('m')
            23 -> ans.add('n')
            24 -> ans.add('o')
            25 -> ans.add('p')
            26 -> ans.add('q')
            27 -> ans.add('r')
            28 -> ans.add('s')
            29 -> ans.add('t')
            30 -> ans.add('u')
            31 -> ans.add('v')
            32 -> ans.add('w')
            33 -> ans.add('x')
            34 -> ans.add('y')
            35 -> ans.add('z')
            else -> ans.add(list[i])
        }
    }
    return ans.joinToString(separator = "")
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var ans = 0
    var power = (digits.size - 1).toDouble()
    for (element in digits) {
        ans += element * pow(base.toDouble(), power).toInt()
        power--
    }
    return ans
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int{
    var ans = 0
    var power = pow(base.toDouble(), (str.length - 1).toDouble()).toInt()
    for (i in 0 until str.length) {
        when {
            str[i] == '1' -> ans += power
            str[i] == '2' -> ans += 2 * power
            str[i] == '3' -> ans += 3 * power
            str[i] == '4' -> ans += 4 * power
            str[i] == '5' -> ans += 5 * power
            str[i] == '6' -> ans += 6 * power
            str[i] == '7' -> ans += 7 * power
            str[i] == '8' -> ans += 8 * power
            str[i] == '9' -> ans += 9 * power
            str[i] == 'a' -> ans += 10 * power
            str[i] == 'b' -> ans += 11 * power
            str[i] == 'c' -> ans += 12 * power
            str[i] == 'd' -> ans += 13 * power
            str[i] == 'e' -> ans += 14 * power
            str[i] == 'f' -> ans += 15 * power
            str[i] == 'g' -> ans += 16 * power
            str[i] == 'h' -> ans += 17 * power
            str[i] == 'i' -> ans += 18 * power
            str[i] == 'j' -> ans += 19 * power
            str[i] == 'k' -> ans += 20 * power
            str[i] == 'l' -> ans += 21 * power
            str[i] == 'm' -> ans += 22 * power
            str[i] == 'n' -> ans += 23 * power
            str[i] == 'o' -> ans += 24 * power
            str[i] == 'p' -> ans += 25 * power
            str[i] == 'q' -> ans += 26 * power
            str[i] == 'r' -> ans += 27 * power
            str[i] == 's' -> ans += 28 * power
            str[i] == 't' -> ans += 29 * power
            str[i] == 'u' -> ans += 30 * power
            str[i] == 'v' -> ans += 31 * power
            str[i] == 'w' -> ans += 32 * power
            str[i] == 'x' -> ans += 33 * power
            str[i] == 'y' -> ans += 34 * power
            str[i] == 'z' -> ans += 35 * power
        }
        power /= base
    }
    return ans
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var ans = ""
    var number = n
    while(number > 0){
        when {
            number >= 1000 -> {
                ans += "M"
                number -= 1000
            }
            number in 900..999 -> {
                ans += "CM"
                number -= 900
            }
            number in 400..499 -> {
                ans += "CD"
                number -= 400
            }
            number in 500..899 -> {
                ans += "D"
                number -= 500
            }
            number in 100..399 -> {
                ans += "C"
                number -= 100
            }
            number in 90..99 -> {
                ans += "XC"
                number -= 90
            }
            number in 50..89 -> {
                ans += "L"
                number -= 50
            }
            number in 40..99 -> {
                ans += "XL"
                number -= 40
            }
            number in 10..39 -> {
                ans += "X"
                number -= 10
            }
            number == 9 -> {
                ans += "IX"
                number -= 9
            }
            number in 5..8 -> {
                ans += "V"
                number -= 5
            }
            number == 4 -> {
                ans += "IV"
                number -= 4
            }
            number in 1..3 -> {
                ans += "I"
                number -= 1
            }
        }
    }
    return ans
}
/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun russian(n: Int): String = TODO()