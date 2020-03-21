
import data.Student
import data.studentList
import kotlinx.html.ARel.index
import org.w3c.dom.events.Event
import react.*
import react.dom.h1
import react.dom.h2
import react.dom.li
import react.dom.ol

interface RLessonProps : RProps {
    var lesson: String
    var students: Array<Student>
}

interface RLessonState : RState {
    var present: Array<Boolean>
}

class RLesson : RComponent<RLessonProps, RLessonState>() {

    override fun componentWillMount() {
        state.apply {
            present = Array(props.students.size){false}
        }
    }

    override fun RBuilder.render() {
        h1 {
            +props.lesson
                ol {
                   val click =  props.students.mapIndexed { index, student -> onClick(index) }
                       rstudentlist(props.students, state.present, click)
                }
            }
        }

    fun RBuilder.onClick(index: Int): (Event) -> Unit = {
        setState {
            present[index] = !present[index]
        }
    }
}

fun RBuilder.lesson(students: Array<Student>) =
    child(RLesson::class) {
        attrs.lesson = "PHYSICS"
        attrs.students  = students
    }

