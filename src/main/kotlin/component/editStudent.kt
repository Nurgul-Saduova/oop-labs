package component

import kotlinx.html.InputType
import kotlinx.html.id
import react.*
import react.dom.input

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
