@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1
import lesson1.task1.numberRevert
import java.lang.Math.*
/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    for (m in 2..Math.sqrt(n.toDouble()).toInt()) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int {
    var ans = 1
    var number = n
    while (number / 10 != 0) {
        ans++
        number /= 10
    }
    return ans
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n == 1) return 1
    if (n == 2) return 1
    return fib(n - 1) + fib(n - 2)
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun gcd(a: Int, b: Int): Int {
    if (a == 0) return b
    else return gcd(b % a, a)
}

fun lcm(m: Int, n: Int): Int {
    val k = m * n / gcd(m, n)
    return k
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    var divisor = 2
    while (divisor < n) {
        if(n % divisor == 0) return divisor
        divisor++
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int {
    var ans = 1
    var i = 2
    while (i < n) {
        if (n % i == 0) ans = i
        i++
    }
    return ans
}

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = gcd(m, n) == 1

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    var f = false
    val l = sqrt(m * 1.0)
    val r = sqrt(n * 1.0)
    var k = l.toInt()
    while(!f && k in l.toInt()..r.toInt()){
        f = k * k in m..n
        k++
    }
    return f
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var ans = x
    while (abs(ans) > 2 * PI) ans -= 2 * PI
    val const = ans
    var power = 3.0
    var factorial = 6.0
    var term = ans
    var i = 1
    while (abs(term) > eps) {
        term = pow(const, power) / factorial
        if (i % 2 == 1) ans -= term
        else ans += term
        power += 2
        i++
        factorial *= power * (power - 1)
    }
    return ans
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var ans = 1.0
    var const = x
    while (abs(const) >= 2 * PI) const -= 2 * PI
    var power = 2.0
    var factorial = 2.0
    var i = 1
    var term = ans
    while (term >= eps) {
        term = pow(const, power) / factorial
        if (i % 2 == 1) ans -= term
        else ans += term
        power += 2
        factorial *= power * (power - 1)
        i++
    }
    return ans
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var ans = n % 10
    var number = n / 10
    while (number > 0) {
        ans = ans * 10 + number % 10
        number /= 10
    }
    return ans
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean {
    var number = n
    var tenSize = 1
    while (number > 9) {
        tenSize *= 10
        number /= 10
    }
    number = n
    while (number > 9) {
        if (number % 10 == number / tenSize) {
            number %= tenSize
            tenSize /= 100
            number /= 10
        } else return false
    }
    return true
}

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var number = n / 10
    var lastDigit = n % 10
    while (number > 0) {
        if (lastDigit != number % 10) return true
        lastDigit = number % 10
        number /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
    var number = 2
    var i = 1
    var square = 1
    while (n > i) {
        var sizeOfNumber = 1
        square = number * number
        while (square / 10 > 0) {
            sizeOfNumber++
            square /= 10
        }
        square = number * number
        if (i + sizeOfNumber < n) {
            i += sizeOfNumber
            number++
        } else {
            while (i < n) {
                i++
                square %= pow(10.0, sizeOfNumber.toDouble()).toInt()
                sizeOfNumber--
            }
            return square / pow(10.0, sizeOfNumber.toDouble()).toInt()
        }
    }
    return square
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var lastFib2 = 1
    var i = 3
    var fib = 2
    var lastFib1 = 1
    while (n > i) {
        var sizeOfNumber = 1
        val l = fib
        fib = lastFib1 + lastFib2
        lastFib1 = lastFib2
        lastFib2 = l
        while (fib / 10 > 0) {
            sizeOfNumber++
            fib /= 10
        }
        fib = lastFib1 + lastFib2
        if (i + sizeOfNumber < n) {
            i += sizeOfNumber
            fib = lastFib1 + lastFib2
            lastFib1 = lastFib2
            lastFib2 = l
        } else {
            while (i < n) {
                i++
                fib %= pow(10.0, sizeOfNumber.toDouble()).toInt()
                sizeOfNumber--
            }
            return fib / pow(10.0, sizeOfNumber.toDouble()).toInt()
        }
    }
    return 1
}
