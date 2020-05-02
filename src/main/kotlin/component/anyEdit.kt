package component

import hoc.withDisplayName
import kotlinx.html.InputType
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
    rComponenentname:RBuilder.(O)-> ReactElement,
    rComponenentEdit:RBuilder.()-> ReactElement,
    rComponent: RBuilder.(Array<O>, String, String)->ReactElement
) =
    functionalComponent<AnyEditProps<O>>{props ->
        h3{+"Edit"}
        ul{
            rComponenentEdit()
            input(type = InputType.submit){
                attrs.value = "Add"
                attrs.onClickFunction = props.Add
            }
            props.subObjs.mapIndexed { index, element ->
                li{
                    rComponenentname(element)
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
    rComponenentname: RBuilder.(O) -> ReactElement,
    rComponenentEdit:RBuilder.()-> ReactElement,
    rComponent:RBuilder.(Array<O>, String, String)-> ReactElement,
    subObjs: Array<O>,
    Add: (Event) -> Unit,
    name: String,
    path: String,
    Delete:Array<(Event)->Unit>
)= child(
    withDisplayName("EditAny", fanyEdit<O>(rComponenentname, rComponenentEdit, rComponent))
){
    attrs.subObjs = subObjs
    attrs.Add = Add
    attrs.name = name
    attrs.path = path
    attrs.Delete = Delete
}
