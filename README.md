# oop-labs_8
Задание к лабораторной работе: "Доработайте приложение из видеоуроков. Разработайте компоненты, отвечающий за редактирование названия занятия и имени и фамилии студента. Разработайте компонент, отвечающий за редактирование списка элементов (с возможностью добавить или удалить элемент). В качестве аргументов этому компоненту передаются компоненты для отображения и для редактирования элемента списка. Добавьте в приложение страницы для редактирования списка студентов и списка занятий." 
## Ход работы<br>
Реализация компонента "editLesson" для редактирования названия занятия<br>
Код editLesson:<br>
interface EditlessonProps : RProps {
}

val feditLesson =
    functionalComponent<EditlessonProps> { props ->
       input(type = InputType.text)  {
            attrs.placeholder = "Enter lesson title"
            attrs.id ="LessonAdd"
        }
    }

fun RBuilder.editlesson(
) = child(feditLesson) {
}

Реализация компонента "editStudent" для редактирования  фамилии и имени студента<br>
Код editStudent:<br>
interface EditstudentProps : RProps {
}

val feditStudent =
    functionalComponent<EditstudentProps> { props ->
        input(type = InputType.text) {
            attrs.placeholder = "Enter student Firstname"
            attrs.id ="StudentAddFirstname"
        }
        input(type = InputType.text) {
            attrs.placeholder = "Enter student Surname"
            attrs.id ="StudentAddSurname"
        }
    }

fun RBuilder.editstudent(
) = child(feditStudent) {
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
    rComponenentEdit:RBuilder.()-> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String)->ReactElement
) =
    functionalComponent<AnyEditProps<O>>{props ->
        h3{+"Edit"}
        ul{
            rComponenentEdit()
            props.subObjs.mapIndexed { index, element ->
                li{
                    input(type = InputType.submit){
                        attrs.value = "Delete"
                        attrs.onClickFunction = props.Delete[index]
                    }
                }
            }
            input(type = InputType.submit){
                attrs.value = "Add"
                attrs.onClickFunction = props.Add
            }
            rComponent( props.subObjs,props.name,props.path)
        }
    }

fun <O> RBuilder.anyEdit(
    rComponenentEdit:RBuilder.()-> ReactElement,
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
![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_8/Screenshots/добавление%20урока.PNG?raw=true)

Результат удаления урока
![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_8/Screenshots/удаление%20урока.PNG?raw=true)

Результат добавления студента
![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_8/Screenshots/добавление%20студента.PNG?raw=true)

Результат удаления студента
![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_8/Screenshots/удаление%20студента.PNG?raw=true)

