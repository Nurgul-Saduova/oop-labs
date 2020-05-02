package component

import data.Lesson
import react.*
import react.dom.li

interface namelessonProps : RProps {
    var lesson: Lesson
}

val fnameLesson =
    functionalComponent<namelessonProps> { props ->
        li{
            +props.lesson.name
        }
    }

fun RBuilder.namelesson(
    lesson: Lesson
) = child(fnameLesson) {
    attrs.lesson = lesson
}