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
    if (n == 1 || n == 2) return 1
    var lastNumberFibonacci1 = 1
    var lastNumberFibonacci2 = 1
    var ans = 1
    for (i in 3..n) {
        ans = lastNumberFibonacci1 + lastNumberFibonacci2
        lastNumberFibonacci1 = lastNumberFibonacci2
        lastNumberFibonacci2 = ans
    }
    return ans
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
    while (divisor <= sqrt(n.toDouble()).toInt()) {
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
    var divisor = n / 2
    while(divisor >= sqrt(n.toDouble()).toInt()){
        if(n % divisor == 0) return divisor
        divisor --
    }
    return 1
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
    var ans = false
    val left = sqrt(m * 1.0)
    val right = sqrt(n * 1.0)
    var number = round(left)
    while(!ans && number in round(left)..round(right)){
        ans = number * number in m..n
        number++
    }
    return ans
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
    while (ans > 2 * PI) ans -= 2 * PI
    while (ans < -2 * PI) ans += 2 * PI
    val number = ans
    var power = 3.0
    var factorial = 6.0
    var term = ans
    while (abs(term) > eps) {
        term = pow(number, power) / factorial
        if (power.toInt() % 4 == 3) ans -= term
        else ans += term
        power += 2
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
    var number = x
    while (number > 2 * PI) number -= 2 * PI
    while (number < -2 * PI) number += 2 * PI
    var power = 2.0
    var factorial = 2.0
    var term = ans
    while (term >= eps) {
        term = pow(number, power) / factorial
        if (power.toInt() % 4 == 2) ans -= term
        else ans += term
        power += 2
        factorial *= power * (power - 1)
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
fun isPalindrome(n: Int): Boolean = revert(n) == n

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
        square = number * number
        var sizeOfNumber = digitNumber(square)
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
    var ans = 2
    var lastFib1 = 1
    while (n >= i) {
        var sizeOfNumber = digitNumber(ans)
        if (i + sizeOfNumber <= n) {
            i += sizeOfNumber
            lastFib1 = lastFib2
            lastFib2 = ans
            ans = lastFib1 + lastFib2
        }
        else {
            while (i <= n) {
                i++
                ans %= pow(10.0, sizeOfNumber.toDouble()).toInt()
                sizeOfNumber--
            }
            return ans / pow(10.0, sizeOfNumber.toDouble()).toInt()
        }
    }
    return 1
}
