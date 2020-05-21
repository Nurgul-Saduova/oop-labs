package component

import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.button


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

