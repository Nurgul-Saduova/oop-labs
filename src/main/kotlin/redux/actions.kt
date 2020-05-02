package redux

import data.Lesson
import data.Student

class ChangePresent(val lesson: Int, val student: Int) : RAction
class AddLesson(val DopLesson: Lesson): RAction
class AddStudent (val DopStudent: Student): RAction
class DeleteLesson(var index:Int): RAction
class DeleteStudent(var index:Int): RAction