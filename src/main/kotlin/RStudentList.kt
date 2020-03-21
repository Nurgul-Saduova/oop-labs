import data.Student
import org.w3c.dom.events.Event
import react.*
import react.dom.li
import react.dom.ol

interface RStudentListProps : RProps {
    var students: Array<Student>
    var present: Array<Boolean>
    var onClick: List < (Event) -> Unit >
}

val RFstudentlist =
    functionalComponent<RStudentListProps> { props ->
        ol {
            props.students.mapIndexed { index, student ->
                li {
                    rstudent(student, props.present[index], props.onClick[index])
                }
            }
        }
    }

fun RBuilder.rstudentlist(students: Array<Student>, present: Array<Boolean>, onClick: List < (Event) -> Unit >) =
    child(RFstudentlist) {
        attrs.students = students
        attrs.present = present
        attrs.onClick = onClick
    }