# oop-labs_8
Задание к лабораторной работе: "Доработайте приложение из видеоуроков. Разработайте компоненты, отвечающий за редактирование названия занятия и имени и фамилии студента. Разработайте компонент, отвечающий за редактирование списка элементов (с возможностью добавить или удалить элемент). В качестве аргументов этому компоненту передаются компоненты для отображения и для редактирования элемента списка. Добавьте в приложение страницы для редактирования списка студентов и списка занятий." 
## Ход работы<br>

 Реализация компонента "editLesson" для редактирования названия занятия<br>
Код editLesson:<br>

    interface EditlessonProps : RProps {
      var lesson: Lesson
    }

    val feditLesson =
    functionalComponent<EditlessonProps> { props ->
       li{
            +props.lesson.name
        }
    }

    fun RBuilder.editlesson(
    lesson: Lesson
    ) = child(feditLesson) {
    attrs.lesson = lesson
    }

 Реализация компонента "editStudent" для редактирования  фамилии и имени студента<br>
Код editStudent:<br>

    interface EditstudentProps : RProps {
     var student: Student
    }

    val feditStudent =
    functionalComponent<EditstudentProps> { props ->
        li{
            +"${props.student.firstname} ${props.student.surname}"
        }
    }

    fun RBuilder.editstudent(
    student: Student
    ) = child(feditStudent) {
     attrs.student = student
    }

 Компонент "anyEdit", отвечающий за редактирование списка элементов (с возможностью добавить или удалить элемент)<br>
Код anyEdit:<br>

    interface AnyEditProps<O>: RProps {
      var subObjs: Array<O>
      var Add:(Event)->Unit
     var Delete: Array<(Event)->Unit>
     var name : String
     var path : String
    }

    fun <O> fanyEdit(
       rComponenentEdit:RBuilder.(O)-> ReactElement,
       rComponent: RBuilder.(Array<O>, String, String)->ReactElement
    ) =
       functionalComponent<AnyEditProps<O>>{props ->
          h3{+"Edit"}
          ul{
            input(type = InputType.text)  {
            attrs.placeholder = "Enter lesson title"
            attrs.id ="LessonAdd"
            }
            input(type = InputType.text) {
                attrs.placeholder = "Enter student Firstname"
                attrs.id ="StudentAddFirstname"
            }
            input(type = InputType.text) {
                attrs.placeholder = "Enter student Surname"
                attrs.id ="StudentAddSurname"
            }
            input(type = InputType.submit){
                attrs.value = "Add"
                attrs.onClickFunction = props.Add
            }
            props.subObjs.mapIndexed { index, element ->
                li{
                 rComponenentEdit(element)
                    input(type = InputType.submit){
                        attrs.value = "Delete"
                        attrs.onClickFunction = props.Delete[index]
                    }
                }
            }
            rComponent( props.subObjs,props.name,props.path)
        }
    }

    fun <O> RBuilder.anyEdit(
     rComponenentEdit:RBuilder.(O)-> ReactElement,
    rComponent:RBuilder.( Array<O>, String, String)-> ReactElement,
    subObjs: Array<O>,
    Add:(Event)->Unit,
    name : String,
    path : String,
    Delete:Array<(Event)->Unit>
    )= child(
    withDisplayName("EditAny", fanyEdit<O>(rComponenentEdit, rComponent))
    ){
    attrs.subObjs = subObjs
    attrs.Add = Add
    attrs.Delete = Delete
    attrs.name = name
    attrs.path = path
    }

Результат добавления урока 
![Результат]()

Результат удаления урока
![Результат]()

Результат добавления студента
![Результат]()

Результат удаления студента
![Результат]()

