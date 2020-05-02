package component

import data.*
import hoc.withDisplayName
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*
import react.router.dom.*
import redux.*
import kotlin.browser.document

interface AppProps : RProps {
    var store: Store<State, RAction, WrapperAction>
}

interface RouteNumberResult : RProps {
    var number: String
}

fun fApp() =
    functionalComponent<AppProps> { props ->
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
                    anyList(props.store.getState().lessons, "Lessons", "/lessons")
                }
            )
            route("/students",
                exact = true,
                render = {
                    anyList(props.store.getState().students, "Students", "/students")
                }
            )
            route("/lessons/:number",
                render = renderLesson(props)
            )
            route("/students/:number",
                render = renderStudent(props)
            )
            route("/editLesson",
                render = renderEditLesson(props)
            )
            route("/editStudent",
                render = renderEditStudent(props)
            )
        }
    }

fun AppProps.onClickStudent(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(index, num))
        }
    }

fun AppProps.onClickLesson(num: Int): (Int) -> (Event) -> Unit =
    { index ->
        {
            store.dispatch(ChangePresent(num, index))
        }
    }

fun AppProps.addLesson(): (Event) -> Unit =
    { _: Event ->
        val addL = document.getElementById("LessonAdd")!!  as HTMLInputElement
        val DopLesson = Lesson("${addL.value}")
        store.dispatch(AddLesson(DopLesson))
    }

fun AppProps.addStudent(): (Event) -> Unit=
    { _: Event ->
        val addStudF = document.getElementById("StudentAddFirstname") as HTMLInputElement
        val addStudS = document.getElementById("StudentAddSurname") as HTMLInputElement
        val DopStudent = Student("${addStudF.value}", "${addStudS.value}")
        store.dispatch(AddStudent(DopStudent))
    }

fun AppProps.deleteLesson(index: Int): (Event) -> Unit = { _: Event ->
    store.dispatch(DeleteLesson(index))
}

fun AppProps.deleteStudent(index: Int): (Event) -> Unit = { _: Event ->
    store.dispatch(DeleteStudent(index))
}

fun RBuilder.renderEditLesson(props: AppProps) = {
    anyEdit(
        RBuilder::namelesson,
        RBuilder::editlesson,
        RBuilder::anyList,
        props.store.getState().lessons,
        props.addLesson(),
        "Lessons",
        "/lessons" ,
        props.store.getState().lessons.mapIndexed{index, lesson ->
            props.deleteLesson(index)
        }.toTypedArray()
    )
}

fun RBuilder.renderEditStudent(props: AppProps) = {
    anyEdit(
        RBuilder::namestudent,
        RBuilder::editstudent,
        RBuilder::anyList,
        props.store.getState().students,
        props.addStudent(),
        "Students",
        "/students",
        props.store.getState().students.mapIndexed { index, student ->
            props.deleteStudent(index)
        }.toTypedArray()
    )
}

fun RBuilder.renderLesson(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val lesson = props.store.getState().lessons.getOrNull(num)
        if (lesson != null)
            anyFull(
                RBuilder::student,
                lesson,
                props.store.getState().students,
                props.store.getState().presents[num],
                props.onClickLesson(num)
            )
        else
            p { +"No such lesson" }
    }

fun RBuilder.renderStudent(props: AppProps) =
    { route_props: RouteResultProps<RouteNumberResult> ->
        val num = route_props.match.params.number.toIntOrNull() ?: -1
        val student = props.store.getState().students.getOrNull(num)
        if (student != null)
            anyFull(
                RBuilder::lesson,
                student,
                props.store.getState().lessons,
                props.store.getState().presents.map {
                    it[num]
                }.toTypedArray(),
                props.onClickStudent(num)
            )
        else
            p { +"No such student" }
    }


fun RBuilder.app(
    store: Store<State, RAction, WrapperAction>
) =
    child(
        withDisplayName("App", fApp())
    ) {
        attrs.store = store
    }





