package component

import data.Lesson
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
  //  lesson: Lesson
  //  addL:(Event)->Unit,
   // deleteL:(Event)->Unit
) = child(feditLesson) {
    //attrs.lesson = lesson
   // attrs.addL = addL
   // attrs.deleteL = deleteL
}