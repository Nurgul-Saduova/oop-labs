package component

import hoc.withDisplayName
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.h3
import react.dom.input
import react.dom.li
import react.dom.ul

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
