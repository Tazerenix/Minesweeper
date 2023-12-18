import dom.html.HTMLButtonElement
import kotlinx.browser.window
import react.*
import react.dom.html.ButtonHTMLAttributes
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h2

enum class Difficulty {
    BEGINNER, INTERMEDIATE, EXPERT, CUSTOM
}

enum class ClickType {
    LEFT_CLICK,
    RIGHT_CLICK,
    MIDDLE_CLICK,
    DOUBLE_CLICK,
    LEFTRIGHT_CLICK
}

val deltas: List<Pair<Int,Int>> = listOf (
    Pair(-1,-1),
    Pair(-1,0),
    Pair(-1,1),
    Pair(0,-1),
    Pair(0,1),
    Pair(1,-1),
    Pair(1,0),
    Pair(1,1)
)

data class DifficultyDimensions (
    val width: Int,
    val height: Int,
    val mines: Int
)

val App = FC<Props> {
    var game: Game by useState(Game(DifficultyDimensions(9,9,10)))


    h1 {
        +"Minesweeper"
    }


    fun resetGame (customDims: DifficultyDimensions) {
        game = Game(customDims)
    }



    DifficultySelector {
        resetGameFun = ::resetGame
    }

    fun gameOver() {
        window.alert("You clicked a mine! Game over!")
        resetGame(game.dims)
    }


    h2 {
        +when(game.dims) {
            DifficultyDimensions(9,9,10) -> "Beginner"
            DifficultyDimensions(16,16,40) -> "Intermediate"
            DifficultyDimensions(30,16,99) -> "Expert"
            else -> "Custom ${game.dims.width} x ${game.dims.height} with ${game.dims.mines} mines."
        }
    }



    fun revealSquare (i: Int, j: Int) {

    }

    fun clickSquare(i: Int, j: Int, clickType: ClickType) {
        when(clickType) {
            ClickType.LEFT_CLICK -> {
                if (game.revealedBoard.count() == 0) {
                    game = game.startGame(i, j)
                }

                if (game.revealedBoard.get(i, j) == 0) {
                    if (game.minesBoard.get(i, j) == 1) {
                        gameOver()
                        return
                    }

                    game = game.reveal(i, j)
                }
            }
            ClickType.RIGHT_CLICK -> {
                game = game.flag(i,j)
            }
            ClickType.DOUBLE_CLICK, ClickType.MIDDLE_CLICK, ClickType.LEFTRIGHT_CLICK -> {
                if (game.revealed(i,j)) {
                    try {
                        game = game.revealDoubleClick(i, j)
                    }
                    catch (e: RevealedMineException) {
                        gameOver()
                        return
                    }
                }
            }
        }

        if (game.checkWin()) {
            window.alert("You won!")
            resetGame(game.dims)
        }
    }

    BoardDisplay {
        clickSquareFun = ::clickSquare
        gameProp = game
    }
}



