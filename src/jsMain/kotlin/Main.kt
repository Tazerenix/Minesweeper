import kotlinx.browser.document
import react.*
import emotion.react.css
import csstype.Position
import csstype.px
import kotlinx.browser.window
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.img
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.table
import react.dom.html.ReactHTML.tr
import react.dom.html.ReactHTML.td
import react.dom.client.createRoot
import react.dom.html.ReactHTML.a



fun main() {
    val container = document.getElementById("root") ?: error("Couldn't find root container!")
    createRoot(container).render(App.create())
}