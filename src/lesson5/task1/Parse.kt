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
        }
        else {
            println("Прошло секунд с начала суток: $seconds")
        }
    }
    else {
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
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    val months31 = listOf(0, 2, 4, 6, 7, 9, 11)
    val months30 = listOf(3, 5, 8, 10)
    try {
        var ansDay = parts[0].toInt()
        if (parts[1] !in months) return ""
        var rightFormat = parts[0].toInt() < 31 && months.indexOf(parts[1]) in months30
        if (!rightFormat) rightFormat = parts[0].toInt() < 32 && months.indexOf(parts[1]) in months31
        if (!rightFormat) rightFormat = parts[0].toInt() < 29 && months.indexOf(parts[1]) == 1
        var leapYear = parts[2].toInt() % 4 == 0 && parts[2].toInt() % 100 != 0 || parts[2].toInt() % 400 == 0
        if (!rightFormat && leapYear) rightFormat = parts[0].toInt() == 29 && months.indexOf(parts[1]) == 1
        var ansMonth: Int
        if (!rightFormat) return ""
        else ansMonth = months.indexOf(parts[1]) + 1
        var ansYear = parts[2].toInt()
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
    val months = listOf("января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    val parts = digital.split(".")
    var ans = listOf<String>()
    val months31 = listOf(0, 2, 4, 6, 7, 9, 11)
    val months30 = listOf(3, 5, 8, 10)
    if (parts.size != 3) return ""
    try {
        ans += (parts[0].toInt()).toString()
        if (parts[1].toInt() in 1..months.size) ans += months[parts[1].toInt() - 1]
        else return ""
        ans += parts[2]
        var rightDate = parts[1].toInt() - 1 in months30 && parts[0].toInt() <= 30
        if (!rightDate) rightDate = parts[1].toInt() - 1 in months31 && parts[0].toInt() <= 31
        if (!rightDate) rightDate = parts[1].toInt() == 2 && parts[0].toInt() < 29
        var leapYear = parts[2].toInt() % 4 == 0 && parts[2].toInt() % 100 != 0 || parts[2].toInt() % 400 == 0
        if (!rightDate) rightDate = leapYear && parts[0].toInt() == 2 && parts[1].toInt() == 2
        if (!rightDate) return ""
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
    val legalChar = listOf('(', ')', '-', '+', ' ')
    var ans = StringBuilder()
    for (i in 0 until phone.length) {
        if (phone[i] !in legalChar && phone[i].toInt() !in 48..58) {
            return ""
        } else {
            if (phone[i].toInt() in 48..58) ans.append(phone[i])
            if (phone[i] == '+' && i == 0) ans.append(phone[i])
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
            var succesfulJump = false
            for (j in 0 until parts[i + 1].length) {
                succesfulJump = parts[i + 1][j] == '+'
                if (parts[i + 1][j] !in legalChars) return -1
            }
            if (succesfulJump && ans < parts[i].toInt()) ans = parts[i].toInt()
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
    val error = IllegalArgumentException("Description")
    var ans = 0
    if (parts.size % 2 == 0) throw error
    try {
        ans += parts[0].toInt()
        for (i in 1 until parts.size step 2) {
            if (parts[i] !in legalChars) throw error
            if (parts[i] == "+") ans += parts[i + 1].toInt()
            if (parts[i] == "-") ans -= parts[i + 1].toInt()
        }
        return ans
    } catch (e: NumberFormatException) {
        throw error
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
    var ans = -1
    var index = 0
    for (i in 1 until parts.size) {
        if (parts[i - 1].toLowerCase() == parts[i].toLowerCase()) {
            ans = index
        }
        index += parts[i - 1].length + 1
    }
    return ans
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
            if (productAndPrice[1].toDouble() < 0) return ""
            if (productAndPrice[1].toDouble() > maxPrice) {
                maxPrice = productAndPrice[1].toDouble()
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
fun fromRoman(roman: String): Int {
    val romanNumbers = listOf('I', 'V', 'X', 'L', 'C', 'D', 'M')
    var lastNumber = 0
    var currentNumber = 0
    if (roman.length == 0) return -1
    if (roman[0] !in romanNumbers) return -1
    if (romanNumbers.indexOf(roman[0]) % 2 == 0) {
        lastNumber = pow(10.0, (romanNumbers.indexOf(roman[0]) / 2).toDouble()).toInt()
    } else {
        lastNumber = 5 * pow(10.0, (romanNumbers.indexOf(roman[0]) / 2).toDouble()).toInt()
    }
    var ans = lastNumber
    for (i in 1 until roman.length) {
        if (roman[i] !in romanNumbers) return -1
        if (romanNumbers.indexOf(roman[i]) % 2 == 0) {
            currentNumber = pow(10.0, (romanNumbers.indexOf(roman[i]) / 2).toDouble()).toInt()
        } else {
            currentNumber = 5 * pow(10.0, (romanNumbers.indexOf(roman[i]) / 2).toDouble()).toInt()
        }
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
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    var ans = mutableListOf<Int>()
    var ind = 0
    var iterator = cells / 2
    var amountBrackets = 0
    val errorState = IllegalStateException("computeDeviceCells")
    val errorArgument = IllegalArgumentException("computeDeviceCells")
    val legalChars = listOf('+', '-', '>', '<', '[', ']', ' ')
    for (i in 0 until cells) ans.add(0)
    var step = 0
    while (iterator in 0 until cells && ind in 0 until commands.length && step < limit) {
        step ++
        if (commands[ind] !in legalChars) throw errorArgument
        when (commands[ind]) {
            '+' -> ans[iterator]++
            '-' -> ans[iterator]--
            '>' -> iterator++
            '<' -> iterator--
            '[' -> {
                amountBrackets++
                if (ans[iterator] == 0) {
                    ind++
                    val count = amountBrackets - 1
                    while (ind < commands.length && amountBrackets != count) {
                        if (commands[ind] == ']') amountBrackets--
                        if (commands[ind] == '[') amountBrackets++
                        ind++
                    }
                    ind--
                    if(ind == commands.length && commands[ind - 1] != ']') throw errorArgument
                }
            }
            ']' -> {
                amountBrackets --
                if (ans[iterator] != 0) {
                    amountBrackets --
                    ind--
                    val count = amountBrackets + 1
                    while (ind >= 0 && amountBrackets != count) {
                        if (commands[ind] == ']') amountBrackets--
                        if (commands[ind] == '[') amountBrackets++
                        ind--
                    }
                    ind++
                    if (ind == -1 && commands[0] != '[') throw errorArgument
                    amountBrackets ++
                }
            }
        }
        ind++
    }
    if (limit == step) return ans
    if (amountBrackets != 0) throw errorArgument
    if (iterator !in 0 until cells && ind != commands.length - 1) throw errorState
    return ans
}
