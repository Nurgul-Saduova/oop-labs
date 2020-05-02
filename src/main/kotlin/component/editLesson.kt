package component

import kotlinx.html.InputType
import kotlinx.html.id
import react.*
import react.dom.input


interface EditlessonProps : RProps {
}

val feditLesson =
    functionalComponent<EditlessonProps> { props ->
        input(type = InputType.text)  {
            attrs.placeholder = "Enter lesson title"
            attrs.id ="LessonAdd"
        }
    }

fun RBuilder.editlesson(
) = child(feditLesson) {
}