@file:Suppress("UNUSED_PARAMETER", "unused")

package lesson7.task1

/**
 * Ячейка матрицы: row = ряд, column = колонка
 */
data class Cell(val row: Int, val column: Int)

/**
 * Интерфейс, описывающий возможности матрицы. E = тип элемента матрицы
 */
interface Matrix<E> {
    /** Высота */
    val height: Int

    /** Ширина */
    val width: Int

    /**
     * Доступ к ячейке.
     * Методы могут бросить исключение, если ячейка не существует или пуста
     */
    operator fun get(row: Int, column: Int): E

    operator fun get(cell: Cell): E

    /**
     * Запись в ячейку.
     * Методы могут бросить исключение, если ячейка не существует
     */
    operator fun set(row: Int, column: Int, value: E)

    operator fun set(cell: Cell, value: E)
}

/**
 * Простая
 *
 * Метод для создания матрицы, должен вернуть РЕАЛИЗАЦИЮ Matrix<E>.
 * height = высота, width = ширина, e = чем заполнить элементы.
 * Бросить исключение IllegalArgumentException, если height или width <= 0.
 */
fun <E> createMatrix(height: Int, width: Int, e: E): Matrix<E> {
    if (height <= 0 || width <= 0) throw IllegalArgumentException("createMatrix")
    val res = MatrixImpl<E>(height, width)
    for (i in 0 until height)
        for (j in 0 until width)
            res.set(i, j, e)
    return res
}

/**
 * Средняя сложность
 *
 * Реализация интерфейса "матрица"
 */
class MatrixImpl<E>(override val height: Int, override val width: Int) : Matrix<E> {
    var mat = mutableMapOf<Cell, E>()

    override fun get(row: Int, column: Int): E = mat[Cell(row, column)] ?: throw IllegalArgumentException("get")


    override fun get(cell: Cell): E = get(cell.row, cell.column)

    override fun set(row: Int, column: Int, value: E) {
        mat[Cell(row, column)] = value
    }

    override fun set(cell: Cell, value: E) = set(cell.row, cell.column, value)


    override fun equals(other: Any?): Boolean {
        if(other is MatrixImpl<*>
                && height == other.height && width == other.width){
            for(i in 0 until height){
                for(j in 0 until width){
                    if(mat[Cell(i, j)] != other[Cell(i, j)]) return false
                }
            }
            return true
        }
        else return false
    }

    override fun toString(): String {
        val ans = StringBuilder()
        ans.append('[')
        for (i in 0 until height) {
            ans.append("[")
            for (j in 0 until width) {
                ans.append(mat[Cell(i, j)].toString())
                ans.append(", ")
            }
            ans.append("], ")
        }
        ans.append("]")
        return ans.toString()
    }

    override fun hashCode(): Int {
        var result = height
        result = 31 * result + width
        result = 31 * result + mat.hashCode()
        return result
    }
}

