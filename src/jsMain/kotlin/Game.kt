import kotlinx.browser.window

class RevealedMineException (e: String) : Exception()
class Game (
    val dims: DifficultyDimensions,
    val minesBoard: BitBoard = BitBoard(dims.width, dims.height),
    val flagsBoard: BitBoard = BitBoard(dims.width, dims.height),
    val unsureBoard: BitBoard = BitBoard(dims.width, dims.height),
    val adjacentBoard: BitBoard = BitBoard(dims.width, dims.height),
    val revealedBoard: BitBoard = BitBoard(dims.width, dims.height)
) {


    fun copy() : Game {
        return Game(dims.copy(), this.minesBoard.copy(), this.flagsBoard.copy(),this.unsureBoard.copy(),this.adjacentBoard.copy(),this.revealedBoard.copy())
    }

    fun startGame(startingX: Int, startingY: Int) : Game {
        var count = minOf(dims.mines, dims.width*dims.height)

        val squareList: MutableList<Pair<Int,Int>> = (0 until dims.width).toList().map { a -> List(dims.height) { b -> Pair(a, b) } }.flatten().toMutableList()

        val surroundingSquares = deltas.map { it -> Pair(startingX + it.first, startingY + it.second) }

        squareList.remove(Pair(startingX,startingY))
        squareList.removeAll(surroundingSquares)

        var newGame = this.copy()

        while (count > 0 && squareList.isNotEmpty()) {
            val sq = squareList.random()
            newGame.minesBoard.set(sq.first,sq.second,1)
            squareList.remove(sq)
            count -= 1
        }

        newGame.createAdjacentBoard()


        return newGame
    }

    fun reveal(i: Int, j: Int) : Game {
        if (minesBoard.get(i,j) == 1) {
            throw RevealedMineException("Revealed mine i=$i, j=$j")
        }

        if (revealedBoard.get(i,j) == 1) {
            return this
        }

        var newGame = this.copy()
        newGame.revealedBoard.set(i,j,1)
        if (adjacentBoard.get(i,j) == 0) {
            for (delta in deltas) {
                if (i+delta.first in 0 until dims.width && j+delta.second in 0 until dims.height) {
                    newGame = newGame.reveal(i+delta.first,j+delta.second)
                }
            }
        }
        return newGame
    }

    fun revealDoubleClick(i: Int, j: Int) : Game {
        var newGame = this.copy()
        for (delta in deltas) {
            if (i + delta.first in 0 until newGame.dims.width && j + delta.second in 0 until newGame.dims.height) {
                if (!flagged(i+delta.first,j+delta.second) && !revealed(i+delta.first,j+delta.second)) {
                    newGame = newGame.reveal(i + delta.first, j + delta.second)
                }
            }
        }
        return newGame
    }

    fun numAdjacent(i: Int, j: Int): Int {
        return adjacentBoard.get(i,j)
    }

    fun flag(i: Int, j: Int): Game {
        if (revealedBoard.get(i,j) == 0) {
            var newGame = this.copy()
            newGame.flagsBoard.set(i,j,if (newGame.flagsBoard.get(i,j) == 1) { 0 } else { 1 })
            return newGame
        }
        return this
    }

    fun flagged(i: Int, j: Int): Boolean {
        return flagsBoard.get(i,j) == 1
    }

    fun revealed(i: Int, j: Int): Boolean {
        return revealedBoard.get(i,j) == 1
    }

    fun checkWin(): Boolean {
        var equal = true
        for (j in 0 until dims.height) {
            for (i in 0 until dims.width) {
                if (revealedBoard.get(i,j) == minesBoard.get(i,j)) {
                    equal = false
                    break
                }
            }
        }
        return equal
    }

    fun createAdjacentBoard() {
        for (j in 0 until dims.height) {
            for (i in 0 until dims.width) {
                if (minesBoard.get(i,j) == 0) {
                    var count = 0
                    for (delta in deltas) {
                        if (i + delta.first in 0 until dims.width && j + delta.second in 0 until dims.height && minesBoard.get(i+delta.first,j+delta.second) == 1) {
                            count += 1
                        }
                    }
                    adjacentBoard.set(i,j,count)
                }
            }
        }
    }
}