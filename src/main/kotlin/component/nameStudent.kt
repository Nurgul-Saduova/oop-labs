package component

import data.Student
import react.*
import react.dom.li

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