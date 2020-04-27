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

interface namestudentProps : RProps {
    var student: Student
}

val fnameStudent =
    functionalComponent<namestudentProps> { props ->
        li{
            +"${props.student.firstname} ${props.student.surname}"
        }
    }

fun RBuilder.namestudent(
    student: Student
) = child(fnameStudent) {
    attrs.student = student
}