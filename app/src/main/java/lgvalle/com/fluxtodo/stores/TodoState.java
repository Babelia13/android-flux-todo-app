package lgvalle.com.fluxtodo.stores;

import java.util.ArrayList;
import java.util.List;

import lgvalle.com.fluxtodo.model.Todo;

/**
 * Created by sara.perez on 15/11/16.
 */

public class TodoState {

    public List<Todo> todos;
    public Todo lastDeleted;

    public TodoState() {
        todos = new ArrayList<>();
    }

    public TodoState(List<Todo> todos, Todo lastDeleted) {
        this.todos = todos;
        this.lastDeleted = lastDeleted;
    }

    public TodoState(TodoState todoState) {
        this.todos = null;
        if (todoState.todos != null) {
            this.todos = new ArrayList<>();
            for (Todo todo : todoState.todos) {
                this.todos.add(new Todo(todo));
            }
        }
        this.lastDeleted = todoState.lastDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TodoState state = (TodoState) o;

        if (todos != null ? !todos.equals(state.todos) : state.todos != null) return false;
        return lastDeleted != null ? lastDeleted.equals(state.lastDeleted) : state.lastDeleted == null;

    }

    @Override
    public String toString() {
        return "TodoState{" +
                "todos=" + todos +
                ", lastDeleted=" + lastDeleted +
                '}';
    }
}
