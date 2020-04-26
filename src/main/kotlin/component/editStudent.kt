package component

import data.Lesson
import data.Student
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.h3
import react.dom.input
import react.dom.li
import react.dom.span

interface EditstudentProps : RProps {
}

val feditStudent =
    functionalComponent<EditstudentProps> { props ->
        input(type = InputType.text) {
            attrs.placeholder = "Enter student Firstname"
            attrs.id ="StudentAddFirstname"
        }
        input(type = InputType.text) {
            attrs.placeholder = "Enter student Surname"
            attrs.id ="StudentAddSurname"
        }
    }

fun RBuilder.editstudent(
) = child(feditStudent) {
}