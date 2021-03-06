@file:Suppress("UNUSED_PARAMETER")

package lesson5.task1

import java.lang.Math.*

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку
 */
val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня",
        "июля", "августа", "сентября", "октября", "ноября", "декабря")
val months31 = listOf(0, 2, 4, 6, 7, 9, 11)
val months30 = listOf(3, 5, 8, 10)
fun correctDate(parts: List<String>): Boolean {
    val month = months.indexOf(parts[1])
    if (month == -1) return false
    val year = parts[2].toInt()
    val day = parts[0].toInt()
    val leapYear = year % 4 == 0 && year % 100 != 0 || year % 400 == 0
    return when {
        month in months31 && day < 32 -> true
        month == 1 && day < 29 -> true
        month in months30 && day < 31 -> true
        day == 29 && month == 1 && leapYear -> true
        else -> false
    }
}

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    try {
        val ansDay = parts[0].toInt()
        if (!correctDate(parts)) return ""
        val ansMonth = months.indexOf(parts[1]) + 1
        val ansYear = parts[2].toInt()
        return String.format("%02d.%02d.%d", ansDay, ansMonth, ansYear)
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 */
fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    var ans = listOf<String>()
    if (parts.size != 3) return ""
    try {
        ans += (parts[0].toInt()).toString()
        val month = parts[1].toInt()
        if (month in 1..months.size) ans += months[month - 1]
        else return ""
        ans += parts[2]
        if (!correctDate(ans)) return ""
        return ans.joinToString(separator = " ")
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    if (phone.isEmpty()) return ""
    val legalChar = listOf('(', ')', '-', '+', ' ')
    var ans = StringBuilder()
    if (phone[0] == '+' && phone.length != 1) ans.append(phone[0])
    for (char in phone) {
        if (char !in legalChar && char !in '0'..'9') {
            return ""
        } else {
            if (char in '0'..'9') ans.append(char)
        }
    }
    return ans.toString()
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    var ans = -1
    val parts = jumps.split(' ', '-', '%')
    try {
        for (part in parts) {
            if (part != "" && part.toInt() > ans) ans = part.toInt()
        }
        return ans
    } catch (e: NumberFormatException) {
        return -1
    }
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val parts = jumps.split(' ')
    val legalChars = listOf('+', '-', '%')
    try {
        var ans = -1
        if (parts.size % 2 != 0) return -1
        for (i in 0 until parts.size step 2) {
            val successfulJump = parts[i + 1].indexOf('+', 0) != -1
            if (!(parts[i + 1]).all{it in legalChars}) return -1
            if (successfulJump && ans < parts[i].toInt()) ans = parts[i].toInt()
        }
        return ans
    } catch (e: NumberFormatException) {
        return -1
    }
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    val parts = expression.split(' ')
    val legalChars = listOf("+", "-")
    var ans = 0
    if (parts.size % 2 == 0) throw IllegalArgumentException("plusMinus")
    try {
        ans += parts[0].toInt()
        for (i in 1 until parts.size step 2) {
            if (parts[i] !in legalChars) throw IllegalArgumentException("plusMinus")
            if (parts[i] == "+") ans += parts[i + 1].toInt()
            if (parts[i] == "-") ans -= parts[i + 1].toInt()
        }
        return ans
    } catch (e: NumberFormatException) {
        throw IllegalArgumentException("plusMinus")
    }
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = str.split(" ")
    var index = 0
    var i = 1
    while (i < parts.size) {
        if (parts[i - 1].toLowerCase() == parts[i].toLowerCase()) {
            return index
        }
        index += parts[i - 1].length + 1
        i++
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62.5; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть положительными
 */
fun mostExpensive(description: String): String {
    val products = description.split("; ")
    var ans = ""
    var maxPrice = 0.0
    try {
        for (element in products) {
            val productAndPrice = element.split(' ')
            if (productAndPrice.size != 2) return ""
            val price = productAndPrice[1].toDouble()
            if (price < 0) return ""
            if (price >= maxPrice) {
                maxPrice = price
                ans = productAndPrice[0]
            }

        }
        return ans
    } catch (e: NumberFormatException) {
        return ""
    }
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
val romanNumbers = listOf('I', 'V', 'X', 'L', 'C', 'D', 'M')

fun romanToInt(roman: String, ind: Int): Int {
    return (pow(5.0, (romanNumbers.indexOf(roman[ind]) % 2).toDouble()) *
            pow(10.0, (romanNumbers.indexOf(roman[ind]) / 2).toDouble())).toInt()
}

fun correctNumber(romanNumber: Char): Boolean = romanNumber in romanNumbers
fun fromRoman(roman: String): Int {
    var currentNumber: Int
    if (roman.isEmpty() || !correctNumber(roman[0])) return -1
    var lastNumber = romanToInt(roman, 0)
    var ans = lastNumber
    for (i in 1 until roman.length) {
        if (!correctNumber(roman[i])) return -1
        currentNumber = romanToInt(roman, i)
        if (lastNumber < currentNumber) ans += currentNumber - 2 * lastNumber
        else ans += currentNumber
        lastNumber = currentNumber
    }
    return ans
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun moveBecauseOfBrackets(commands: String, ind: Int, count: Int,
                          amountBrackets: Int, bracketType: Int): Int{
    var index = ind
    var amount = amountBrackets
    while (index in 0 until commands.length && amount != count) {
        if (commands[index] == ']') amount--
        if (commands[index] == '[') amount++
        index += bracketType
    }
    return index
}
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var ans = MutableList(cells){0}
    var ind = 0
    var iterator = cells / 2
    var amountBrackets = 0
    val legalChars = listOf('+', '-', '>', '<', '[', ']', ' ')
    var step = 0
    for (i in 0 until commands.length) {
        if (commands[i] !in legalChars) throw IllegalArgumentException("computeDeviceCells")
        if (commands[i] == ']' && amountBrackets == 0) throw IllegalArgumentException("computeDeviceCells")
        if (commands[i] == '[') amountBrackets++
        if (commands[i] == ']') amountBrackets--
    }
    if (amountBrackets != 0) throw IllegalArgumentException("computeDeviceCells")
    while (iterator in 0 until cells && ind in 0 until commands.length && step < limit) {
        step++
        when (commands[ind]) {
            '+' -> ans[iterator]++
            '-' -> ans[iterator]--
            '>' -> iterator++
            '<' -> iterator--
            '[' -> {
                if (ans[iterator] == 0) {
                    ind++
                    ind = moveBecauseOfBrackets(commands, ind, 0, 1, 1) - 1
                }
            }
            ']' -> {
                if (ans[iterator] != 0) {
                    ind--
                    ind = moveBecauseOfBrackets(commands, ind, 0, -1, -1) + 1
                }
            }
        }
        ind++
    }
    if (iterator !in 0 until cells) throw IllegalStateException("computeDeviceCells")
    return ans
}



fun correctCoin(coin: String): Boolean {
    try{
        val correctNumber = listOf(5000.0, 1000.0, 500.0, 100.0, 50.0, 10.0, 5.0, 2.0, 1.0, 0.5, 0.1, 0.05, 0.01)
        if(coin.toDouble() !in correctNumber) return false
        return true
    }
    catch(e : NumberFormatException){
        throw IllegalArgumentException("myFun")
    }
}


fun myFun(sum: Double, coins: String): List< String > {
    val parts = coins.split(", ")
    val ans = mutableMapOf<String, Int>()
    for(part in parts){
        ans.put(part, 0)
    }
    var number = sum
    var ind = 0
    try{
        while(number > 0){
            val part = parts[ind]
            if(!correctCoin(part)) throw IllegalArgumentException("myFun")
            val currentNumber = part.toDouble()
            while(number - currentNumber >= 0){
                var value = ans[part] ?: 0
                value++
                ans[part] = value
                number -= currentNumber
            }
            ind++
        }
        var answer = mutableListOf<String>()
        for(part in parts){
            if(ans[part] != 0){
                var elem = StringBuilder()
                elem.append(ans[part].toString())
                elem.append(" x ")
                elem.append(part)
                answer.add(elem.toString())
            }
        }
        return answer
    }
    catch(e: NumberFormatException){
        throw IllegalArgumentException("myFun")
    }
}