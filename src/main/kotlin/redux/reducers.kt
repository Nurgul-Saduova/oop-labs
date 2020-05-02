package redux

import data.State


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

