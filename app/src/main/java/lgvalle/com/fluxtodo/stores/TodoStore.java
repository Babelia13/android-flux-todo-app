package lgvalle.com.fluxtodo.stores;

import android.util.Log;

import java.util.Collections;
import java.util.Iterator;

import lgvalle.com.fluxtodo.actions.TodoActions;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;
import lgvalle.com.fluxtodo.model.Todo;

/**
 * Created by lgvalle on 02/08/15.
 */
public class TodoStore extends Store<TodoState> {

    private static final String TAG = "TodoStore";

    public TodoStore() {

        Log.d(TAG, "[TodoStore] Subscribe all actions");

        Dispatcher.subscribe(TodoActions.TODO_CREATE, action->{
            Log.d(TAG, "[onNext|" + action.getType() + "] State: " + state());
            String text = ((String) action.getData().get(TodoActions.KEY_TEXT));
            TodoState newState = create(text);
            setState(newState);
        });

        Dispatcher.subscribe(TodoActions.TODO_DESTROY, action -> {
            Log.d(TAG, "[onNext|" + action.getType() + "] State: " + state());
            long id = ((long) action.getData().get(TodoActions.KEY_ID));
            setState(destroy(id));
        });

        Dispatcher.subscribe(TodoActions.TODO_UNDO_DESTROY, action -> setState(undoDestroy()));

        Dispatcher.subscribe(TodoActions.TODO_COMPLETE, action -> {
            long id = ((long) action.getData().get(TodoActions.KEY_ID));
            setState(updateComplete(id, true));
        });

        Dispatcher.subscribe(TodoActions.TODO_UNDO_COMPLETE, action -> {
            long id = ((long) action.getData().get(TodoActions.KEY_ID));
            setState(updateComplete(id, false));
        });


        Dispatcher.subscribe(TodoActions.TODO_DESTROY_COMPLETED, action -> setState(destroyCompleted()));

        Dispatcher.subscribe(TodoActions.TODO_TOGGLE_COMPLETE_ALL, action -> setState(updateCompleteAll()));
    }

    @Override
    protected TodoState initialState() {
        return new TodoState();
    }

    public boolean canUndo() {
        return state().lastDeleted != null;
    }

    private TodoState destroyCompleted() {
        TodoState newState = new TodoState(state());
        Iterator<Todo> iter = newState.todos.iterator();
        while (iter.hasNext()) {
            Todo todo = iter.next();
            if (todo.isComplete()) {
                iter.remove();
            }
        }
        return newState;
    }

    private TodoState updateCompleteAll() {
        return updateAllComplete(!areAllComplete());
    }

    private boolean areAllComplete() {
        for (Todo todo : state().todos) {
            if (!todo.isComplete()) {
                return false;
            }
        }
        return true;
    }

    private TodoState updateAllComplete(boolean complete) {
        TodoState newState = new TodoState(state());
        for (Todo todo : newState.todos) {
            todo.setComplete(complete);
        }
        return newState;
    }

    private TodoState updateComplete(long id, boolean complete) {
        TodoState newState = new TodoState(state());
        Todo todo = getById(newState, id);
        if (todo != null) {
            todo.setComplete(complete);
        }
        return newState;
    }

    private TodoState undoDestroy() {
        TodoState newState = new TodoState(state());
        if (newState.lastDeleted != null) {
            addElement(newState, newState.lastDeleted.clone());
            newState.lastDeleted = null;
        }
        return newState;
    }

    private TodoState create(String text) {
        Log.d(TAG, "[create] Text: " + text);
        long id = System.currentTimeMillis();
        Todo todo = new Todo(id, text);
        TodoState newState = new TodoState(state());
        newState = addElement(newState, todo);
        Collections.sort(newState.todos);
        return newState;
    }

    private TodoState destroy(long id) {
        TodoState newState = new TodoState(state());
        Iterator<Todo> iter = newState.todos.iterator();
        while (iter.hasNext()) {
            Todo todo = iter.next();
            if (todo.getId() == id) {
                newState.lastDeleted = todo.clone();
                iter.remove();
                break;
            }
        }
        return newState;
    }

    private Todo getById(TodoState todoState, long id) {
        Iterator<Todo> iter = todoState.todos.iterator();
        while (iter.hasNext()) {
            Todo todo = iter.next();
            if (todo.getId() == id) {
                return todo;
            }
        }
        return null;
    }


    private TodoState addElement(TodoState todoState, Todo clone) {
        todoState.todos.add(clone);
        Collections.sort(todoState.todos);
        return todoState;
    }

}
