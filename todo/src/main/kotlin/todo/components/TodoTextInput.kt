package todo.components

import com.github.andrewoma.react.*

class TodoTextInputProperties(
        val className: String? = null,
        val id: String? = null,
        val placeHolder: String? = null,
        val value: String? = null,
        val onSave: (String) -> Unit
)

class TodoTextInput : ComponentSpec<TodoTextInputProperties, String>() {
    companion object {
        val enterKeyCode = 13
        val factory = react.createFactory(TodoTextInput())
    }

    override fun initialState(): String? {
        return props.value ?: ""
    }

    override fun Component.render() {
        log.debug("TodoTextInput.render", props, state)

        input({
            className = props.className
            id = props.id
            placeholder = props.placeHolder
            onBlur = { save() }
            onChange = { onChange(it) }
            onKeyDown = { onKeyDown(it) }
            value = state
            autoFocus = true
        })
    }

    fun save() {
        props.onSave(state)
        state = ""
    }

    fun onChange(event: FormEvent) {
        state = event.currentTarget.value
    }

    fun onKeyDown(event: KeyboardEvent) {
        if (event.keyCode == enterKeyCode) {
            save()
        }
    }
}

fun Component.todoTextInput(props: TodoTextInputProperties): Component {
    return constructAndInsert(Component({ TodoTextInput.factory(Ref(props)) }))
}
