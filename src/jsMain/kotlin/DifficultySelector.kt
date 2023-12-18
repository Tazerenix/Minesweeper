import csstype.ClassName
import kotlinx.browser.window
import react.*
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.input

external interface DifficultySelectorProps : Props {
    var resetGameFun: (DifficultyDimensions) -> Unit
}

val DifficultySelector = FC<DifficultySelectorProps> { props ->
    div {
        className = ClassName("selector")
        h3 {
            +"Difficulty:"
        }
        button {
            + "Beginner"
            onClick = {
                props.resetGameFun(DifficultyDimensions(9,9,10))
            }
        }
        button {
            +"Intermediate"
            onClick = {
                props.resetGameFun(DifficultyDimensions(16,16,40))
            }
        }
        button {
            +"Expert"
            onClick = {
                props.resetGameFun(DifficultyDimensions(30,16,99))
            }
        }

        var customWidth: Int by useState(15)
        var customHeight: Int by useState(15)
        var customMines: Int by useState(50)

        +" or "
        input {
            type = InputType.number
            min = 1
            max = 100
            value = customWidth
            onChange = { it ->
                customWidth = it.target.value.toInt()
            }
        }
        +"×"
        input {
            type = InputType.number
            min = 1
            max = 100
            value = customHeight
            onChange = { it ->
                customHeight = it.target.value.toInt()
            }
        }
        +" with "
        input {
            type = InputType.number
            min = 1
            max = 10000
            value = customMines
            onChange = { it ->
                customMines = it.target.value.toInt()
            }
        }
        +" mines"

        button {
            +"Custom"
            onClick = {
                props.resetGameFun(DifficultyDimensions(customWidth, customHeight, customMines))
            }
        }
    }
}