data class BitBoard (
    val width: Int,
    val height: Int
) {
    var board: Array<IntArray> = Array(width) { IntArray(height) { 0 } }

    fun copy(): BitBoard {
        var newBitBoard = BitBoard(width,height)
        newBitBoard.board = this.board
        return newBitBoard
    }
    fun get(i: Int, j: Int) : Int {
        return board[i][j]
    }

    fun set(i: Int, j: Int, value: Int) : Unit {
        board[i][j] = value
    }

    fun count(): Int {
        return board.map { it -> it.sum() }.sum()
    }

    fun emptySurroundingSquares(i: Int, j: Int) : Boolean {
        var empty = true
        for (delta in deltas) {
            if (i + delta.first in 0 until width && j + delta.second in 0 until height) {
                empty = empty && get(i+delta.first,j+delta.second) == 0
            }
        }
        return empty
    }
}