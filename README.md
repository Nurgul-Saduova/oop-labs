# oop-labs_9
Задание к лабораторной работе: "Используя код приложения из лекций переделайте приложения из последнего задания предыдущего модуля с использованием redux. Реализовать хранилище нужно простым способом, без использования функций комбинирования reducer'ов (их мы рассмотрим далее)." 
## Ход работы<br>

 Прописываем actions<br>

    class ChangePresent(val lesson: Int, val student: Int) : RAction
    class AddLesson(val DopLesson: Lesson): RAction
    class AddStudent (val DopStudent: Student): RAction
    class DeleteLesson(var index:Int): RAction
    class DeleteStudent(var index:Int): RAction

Прописываем reducers<br>

    fun changeReducer(state: State, action: RAction) =
    when (action) {
        is ChangePresent -> State(
            state.presents.mapIndexed { indexLesson, lesson ->
                if (indexLesson == action.lesson)
                    lesson.mapIndexed { indexStudent, student ->
                        if (indexStudent == action.student)
                            !student
                        else student
                    }.toTypedArray()
                else
                    lesson
            }.toTypedArray(),
            state.lessons,
            state.students
        )
        is AddLesson -> State(
            state.presents.plus(arrayOf(Array(state.students.size){false })),
            state.lessons.plus(action.DopLesson),
            state.students
        )
        is AddStudent -> State(
            state.presents.mapIndexed {index,_ ->
                state.presents[index].plus(arrayOf(false))
            }.toTypedArray(),
            state.lessons,
            state.students.plus(action.DopStudent)
        )
        is DeleteLesson -> State(
            state.presents.mapIndexedNotNull { index, booleans ->
                if(index==action.index)
                    null
                else
                    booleans
            }.toTypedArray(),
            state.lessons.mapIndexedNotNull { index, lesson ->
                if(index!=action.index)
                    lesson
                else {
                    null
                }
            }.toTypedArray(),
            state.students
        )

        is DeleteStudent -> State(
            state.presents.mapIndexedNotNull { index, booleans ->
                if(index==action.index)
                    null
                else
                    booleans
            }.toTypedArray(),
            state.lessons,
            state.students.mapIndexedNotNull { index, student ->
                if(index!=action.index)
                    student
                else {
                    null
                }
            }.toTypedArray()
        )
        else -> state
    }
 Прописываем функции удаления/добавления в app<br>

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
    

Результат добавления студента:
![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_9/Screenshots/доб%20студента.PNG?raw=true)

Результат удаления студента:
![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_9/Screenshots/уд%20студента.PNG?raw=true)

Результат добавления урока:

![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_9/Screenshots/доб%20урока.PNG?raw=true)

Результат удаления урока:

![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_9/Screenshots/уд%20урока.PNG?raw=true)
