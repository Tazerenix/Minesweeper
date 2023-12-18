import csstype.ClassName
import react.*
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tbody
import react.dom.html.ReactHTML.td
import react.dom.html.ReactHTML.tr


external interface BoardDisplayProps : Props {
    var clickSquareFun: (Int, Int, ClickType) -> Unit
    var gameProp: Game
}

val BoardDisplay = FC<BoardDisplayProps> { props ->
    +"Total mines: ${props.gameProp.minesBoard.count()}. Squares revealed: ${props.gameProp.revealedBoard.count()}. Mines remaining: ${props.gameProp.dims.mines - props.gameProp.flagsBoard.count()}"
    div {
        p {
            +"Width = ${props.gameProp.dims.width}, Height = ${props.gameProp.dims.height}, Mines remaining = ${props.gameProp.dims.mines}"
        }
        table {
            onContextMenu = { it ->
                it.preventDefault()
            }

            id = "mines"
            tbody {
                for (j in 0 until props.gameProp.dims.height) {
                    tr {
                        for (i in 0 until props.gameProp.dims.width) {
                            td {

                                button {
                                    //+"$i,$j"
                                    //+"${props.gameProp.minesBoard.get(i,j)}"
                                    //+"${props.gameProp.minesBoard.get(i, j)},${props.gameProp.revealedBoard.get(i, j)}"
                                    var cl = "sq$i-$j"
                                    if (props.gameProp.revealedBoard.get(i,j) == 1) {
                                        cl += " revealed"
                                        if (props.gameProp.numAdjacent(i,j) > 0) {
                                            cl += " b${props.gameProp.numAdjacent(i,j)}"
                                            +"${props.gameProp.numAdjacent(i,j)}"
                                        }
                                    }
                                    else if (props.gameProp.flagsBoard.get(i,j) == 1) {
                                        cl += " flagged"
                                    }
                                    className = ClassName(cl)

                                    onClick = { it ->
                                        if (it.buttons == 3) { //left right click
                                            props.clickSquareFun(i,j, ClickType.LEFTRIGHT_CLICK)
                                        }
                                        else if (it.button == 1) { //Middle click
                                            props.clickSquareFun(i,j, ClickType.MIDDLE_CLICK)
                                        }
                                        else if (it.button == 0 && it.detail == 2) { //Double left click
                                            props.clickSquareFun(i,j, ClickType.DOUBLE_CLICK)
                                        }
                                        else if (it.button == 0 && it.detail == 1) { //left click
                                            props.clickSquareFun(i, j, ClickType.LEFT_CLICK)
                                        }

                                    }

                                    onMouseDown = { it ->
                                        if (it.button == 2) { //right click
                                            props.clickSquareFun(i,j, ClickType.RIGHT_CLICK)
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
