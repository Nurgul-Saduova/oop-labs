import kotlin.browser.document
import data.Student
import data.studentList
import kotlinx.html.*
import kotlinx.html.attributes.enumEncode
import kotlinx.html.dom.append
import kotlinx.html.js.*
import kotlinx.html.js.li
import kotlinx.html.js.option
import org.w3c.dom.*
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.dom.clear

var ascending = true

fun main() {
    document.getElementById("root")!!
        .append {
            h1 {
                +"Students"
                onClickFunction = onCLickFunction()
            }
            ol {
                attributes += "id" to "listStudents"
                studentList.map {
                    li {
                        +"${it.firstname} ${it.surname}"
                        // attributes += "style" to "color:black"
                        //attributes += "id" to it.firstname
                        onClickFunction = click(it)
                    }
                }
            }

            input(options = arrayListOf("blue"))
            label{+"Blue"}
            input(options = arrayListOf("green"))
            label{+"Green"}
            input(options = arrayListOf("purple"))
            label{+"Purple"}
        }
}



private fun changeColor(selectColor: String): (Event) -> Unit {
    return {
        val root = document.getElementById("root")!!
        root.setAttribute("style", "color:${selectColor}")
    }
}


fun TagConsumer<HTMLElement>.input(
    options: List<String>,
    block : INPUT.() -> Unit = {}
) : HTMLInputElement = input(
    type = InputType.radio
) {
    options.forEach {
        attributes += "id" to "selectColor"
        attributes += "value" to it
        +it
        onClickFunction = changeColor(it)
    }
    block ()
}


private fun LI.click(student: Student): (Event) -> Unit {
    return {
        val presenceStudent = document.getElementById(student.firstname)!!
        if (student.absence) {
            presenceStudent.setAttribute("style", "color:pink")
            student.absence = false
        }
        else {
            presenceStudent.setAttribute("style", "color:black")
            student.absence = true
        }
    }
}


private fun H1.onCLickFunction(): (Event) -> Unit {
    return {
        val listStudents = document.getElementById("listStudents")!!
        listStudents.clear()
        listStudents.append {
            if (ascending)
                studentList.sortBy {
                    it.firstname
                }
            else
                studentList.sortByDescending { it.firstname }
            ascending = !ascending
            studentList.map {
                li {
                    +"${it.firstname} ${it.surname}"
                    attributes += "style" to "color:black"
                }
            }
        }
    }
}