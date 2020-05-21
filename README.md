# oop-labs_9
Задание к лабораторной работе: "Доработайте приложение, добавив в списки фильтры, которые выводят либо всех студентов (уроки), либо только присутствующих, либо только отсутствующих." 
## Ход работы<br>

Прописываем enum class VisibilityFilter<br>

    enum class VisibilityFilter {
       SHOW_ALL,
       SHOW_ABSENT,
        SHOW_PRESENT
    }

 Прописываем actions<br>

    class ChangePresent(val lessonID: Int, val studentID: Int) : RAction
    class AddStudent(val student: Student) : RAction
    class RemoveStudent(val id: Int) : RAction
     class ChangeStudent(val id: Int, val newStudent: Student) : RAction
     class AddLesson(val lesson: Lesson) : RAction
    class RemoveLesson(val id: Int) : RAction
    class ChangeLesson(val id: Int, val newLesson: Lesson) : RAction
    class SetVisibilityFilter(val filter: VisibilityFilter) : RAction

Прописываем reducers<br>

    fun visibilityReducer(state: VisibilityFilter, action: RAction) =
    when (action) {
        is SetVisibilityFilter -> action.filter
    else -> state
    }
 Создаем новый компонент link, который будет отвечать за отображение и изменении фильтра<br>

    interface LinkProps : RProps {
    var title: String
    var onClick: () -> Unit
    }

    class Link(props: LinkProps) : RComponent<LinkProps, RState>(props) {
    override fun RBuilder.render() {
        button {
            attrs.onClickFunction = { props.onClick() }
            +props.title
           children()
        }
      }
    }
    
Создаем контейнер для компонента link<br>

    interface FilterLinkProps : RProps {
        var title: String
        var filter: VisibilityFilter
    }

    private interface LinkStateProps : RProps {
        var title: String
        var active: Boolean
    }

    private interface LinkDispatchProps : RProps {
       var onClick: () -> Unit
    }

    val filterLink: RClass<FilterLinkProps> =
    rConnect<
            State,
            SetVisibilityFilter,
            WrapperAction,
            FilterLinkProps,
            LinkStateProps,
            LinkDispatchProps,
            LinkProps>(
        { state, ownProps ->
            title = ownProps.title
            active = VisibilityFilter.SHOW_ALL == ownProps.filter
        },
        { dispatch, ownProps ->
            onClick = { dispatch(SetVisibilityFilter(ownProps.filter)) }
        }
    )(Link::class.js.unsafeCast<RClass<LinkProps>>())

Создаем компонент для вывода кнопок, footer.kt<br>

    fun RBuilder.footer() =
    div {
        span { +"Show: " }
        filterLink {
            attrs.filter = VisibilityFilter.SHOW_ALL
            attrs.title = "All"
        }
        filterLink {
            attrs.filter = VisibilityFilter.SHOW_ABSENT
            attrs.title = "Absent"
        }
        filterLink {
            attrs.filter = VisibilityFilter.SHOW_PRESENT
           attrs.title = "Present"
         }
        }
        
В компонент anyFullContainer.kt добавляем функцию фильтрации<br>

    private fun <O> getVisibleAnyFull(
        objects: Map<Int, O>,
        presents: Map<Int, Boolean>?,
        filter: VisibilityFilter
    ): Map<Int, O> = when (filter) {
    VisibilityFilter.SHOW_ALL -> objects
    VisibilityFilter.SHOW_ABSENT ->  {
        val absentObj = objects.toMutableMap()
        absentObj.clear()
        if (presents != null) {
            presents.filter { !it.value }.map{
                absentObj[it.key] = objects.getValue(it.key)
            }
        }
        absentObj
    }
    VisibilityFilter.SHOW_PRESENT ->  {
        val presentObj = objects.toMutableMap()
        presentObj.clear()
        if (presents != null) {
            presents.filter { it.value }.map{
                presentObj[it.key] = objects.getValue(it.key)
            }
        }
        presentObj
      }
    }

Фильтрация занятия по студенту:

![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_10/Screenshots/allLes.PNG?raw=true)

Фильтрация занятия по отсутствию студента:

![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_10/Screenshots/absentLes.PNG?raw=true)

Фильтрация занятия по присутствию студента:

![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_10/Screenshots/presentLes.PNG?raw=true)

Все студенты:

![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_10/Screenshots/allStud.PNG?raw=true)

Студенты, отсутствующие на занятии:

![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_10/Screenshots/absentStud.PNG?raw=true)

Студенты, присутствующие на занятии:

![Результат](https://github.com/Nurgul-Saduova/oop-labs/blob/lab_10/Screenshots/presentStud.PNG?raw=true)
