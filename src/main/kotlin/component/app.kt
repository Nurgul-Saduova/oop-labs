package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import kotlin.browser.document
import kotlin.reflect.KClass

interface AppProps : RProps {
}

interface AppState : RState {
    var lessons: Array<Lesson>
    var students: Array<Student>
    var presents: Array<Array<Boolean>>
}

interface RouteNumberResult : RProps {
    var number: String
}

class App : RComponent<AppProps, AppState>() {
    override fun componentWillMount() {
        state.lessons = lessonsList
        state.students = studentList
        state.presents = Array(state.lessons.size) {
            Array(state.students.size) { false }
        }

    }

    override fun RBuilder.render() {
        header {
            h1 { +"App" }
            nav {
                ul {
                    li { navLink("/lessons") { +"Lessons" } }
                    li { navLink("/students") { +"Students" } }
                    li {navLink("/editStudent"){+"Edit Student"} }
                    li {navLink("/editLesson"){+"Edit Lesson"} }
                }
            }
        }

        switch {
            route("/lessons",
                exact = true,
                render = {
                    anyList(state.lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(state.students, "Students", "/students")
                }
            )
            route("/editStudent",
                exact = true,
                render = {
                    anyEdit(
                        RBuilder::editstudent,
                        RBuilder::anyList,
                        state.students,
                        addStudent(),
                        "Students",
                        "/students",
                        state.students.mapIndexed { index, student ->
                            deleteStudent(index)
                        }.toTypedArray()
                    )
                })
            route("/editLesson",
                exact = true,
                render = {
                    anyEdit(
                        RBuilder::editlesson,
                        RBuilder::anyList,
                        state.lessons,
                        addLesson(),
                        "Lessons",
                        "/lessons" ,
                        state.lessons.mapIndexed{index, lesson ->
                            deleteLesson(index)
                        }.toTypedArray()
                    )
                })
            route("/lessons/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val lesson = state.lessons.getOrNull(num)
                    if (lesson != null)
                        anyFull(
                            RBuilder::student,
                            lesson,
                            state.students,
                            state.presents[num]
                        ) { onClick(num, it) }
                    else
                        p { +"No such lesson" }
                }
            )
            route("/students/:number",
                render = { route_props: RouteResultProps<RouteNumberResult> ->
                    val num = route_props.match.params.number.toIntOrNull() ?: -1
                    val student = state.students.getOrNull(num)
                    if (student != null)
                        anyFull(
                            RBuilder::lesson,
                            student,
                            state.lessons,
                            state.presents.map {
                                it[num]
                            }.toTypedArray()
                        ) { onClick(it, num) }
                    else
                        p { +"No such student" }
                }
            )
        }
    }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }

    fun addLesson() =
        { _: Event ->
            val addL = document.getElementById("LessonAdd")!!  as HTMLInputElement
            val DopLesson = Lesson("${addL.value}")
            setState {
                lessons += DopLesson
                presents += arrayOf(Array(state.students.size) { false })
            }
        }

    fun addStudent()=
        {_: Event ->
        val addStudF = document.getElementById("StudentAddFirstname") as HTMLInputElement
        val addStudS = document.getElementById("StudentAddSurname") as HTMLInputElement
        val DopStudent = Student("${addStudF.value}", "${addStudS.value}")
        setState {
            students += DopStudent
            presents += arrayOf(Array(state.students.size){false})
        }
    }

    fun deleteStudent(index: Int) = { _: Event ->
        var delStud = state.students.slice(0 until index).toTypedArray()
        delStud += state.students.slice(index+1 until state.students.size).toTypedArray()
        var newPresents = state.presents[index].slice(0 until index).toTypedArray()
        newPresents += state.presents[index].slice(index+1 until state.students.size).toTypedArray()
        setState{
            students = delStud
            presents[index] = newPresents
        }
    }

    fun deleteLesson( index: Int) = {_: Event ->
        var delLes = state.lessons.slice(0 until index).toTypedArray()
        delLes += state.lessons.slice(index+1 until state.lessons.size).toTypedArray()
        var newPresents = state.presents[index].slice(0 until index).toTypedArray()
        newPresents += state.presents[index].slice(index+1 until state.students.size).toTypedArray()
        setState{
            lessons = delLes
            presents[index] = newPresents
        }
    }

}

fun RBuilder.app(
) =
    child(
        withDisplayName("AppHoc", App::class)
    ) {}





