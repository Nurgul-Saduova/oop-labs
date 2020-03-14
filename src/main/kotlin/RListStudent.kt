import data.Student
import react.*
import react.dom.li

interface RListStudentProps : RProps {
    var students: Array<Student>
}

class RListStudent: RComponent<RListStudentProps, RState>() {
    override fun RBuilder.render() {
            props.students.forEach {
                li {
                    rstudent(it)
                }
        }
    }
}

fun RBuilder.rliststudent(students: Array<Student>) =
    child(RListStudent::class){
        attrs.students = students
    }