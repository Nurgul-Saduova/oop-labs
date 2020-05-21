package component

import container.filterLink
import data.VisibilityFilter
import react.RBuilder
import react.dom.div
import react.dom.span


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

