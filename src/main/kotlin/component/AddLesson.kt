package component

import kotlinx.html.InputType
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.child
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
                 attrs{
                    value = "Add"
                    onClickFunction = props.onClick
                }
                }
            }

fun RBuilder.addLesson(
    onClick: (Event) -> Unit
) = child(fAddLesson) {
    attrs.onClick = onClick
}
