import kotlin.math.absoluteValue

data class Spreadsheet(private val rows: List<Row>) {
    companion object {
        fun from(s: String): Spreadsheet = Spreadsheet(
                s.split("\n")
                        .filter { it.isNotBlank() }
                        .map { Row.from(it) }
        )
    }

    fun checksum(rowCalculation: (Row) -> Int) = rows.map(rowCalculation).sum()
}

data class Row(val cells: List<Cell>) {
    companion object {
        fun from(s: String): Row = Row(
                s.split(" ")
                        .map(Integer::parseInt)
                        .sorted()
                        .map { Cell(it) }
        )
    }

    val difference: Int
        get() = (cells.first().value - cells.last().value).absoluteValue
    val division: Int
        get() = permutations().filter { (a, b) -> a.value.rem(b.value) == 0 }.first().run { first.value / second.value }
}

data class Cell(val value: Int)

fun Row.permutations(): List<Pair<Cell, Cell>> = cells
        .associate { key -> key to cells.filter { it != key } }
        .map { (cell, others) -> others.map { Pair(cell, it) } }
        .flatten()
