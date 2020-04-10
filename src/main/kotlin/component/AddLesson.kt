package component

import react.*
import react.dom.*
import kotlinx.html.js.*
import data.Lesson
import data.lessonsList
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import kotlinx.html.select
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.child
import react.dom.button
import react.dom.h3
import react.dom.input
import react.functionalComponent
import kotlin.browser.document

interface AddLessonProps : RProps {
    var onClick: (Event) -> Unit
   // var lesson: String
}

val fAddLesson =
    functionalComponent<AddLessonProps> {
            props ->
        h3 { +"Add Lesson"}
            input(type = InputType.text){}
            input(type = InputType.submit) {
                attrs.value = "Add"
                attrs.onClickFunction = props.onClick
                }
            }

fun RBuilder.addLesson(
    onClick: (Event) -> Unit
) = child(fAddLesson) {
    attrs.onClick = onClick
}