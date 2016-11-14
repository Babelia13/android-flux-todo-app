package lgvalle.com.fluxtodo.stores;

import android.util.Log;

import java.util.Collections;
import java.util.Iterator;

import lgvalle.com.fluxtodo.actions.Action;
import lgvalle.com.fluxtodo.actions.TodoActions;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;
import lgvalle.com.fluxtodo.model.Todo;
import rx.Subscriber;

/**
 * Created by lgvalle on 02/08/15.
 */
public class TodoStore extends Store<TodoState> {

    private static final String TAG = "TodoStore";

    private static TodoStore instance;

    protected TodoStore(Dispatcher dispatcher) {
        super(dispatcher);

        Log.d(TAG, "[TodoStore] Subscribe all actions");

        //This code could be simplified
        dispatcher.subscribe(TodoActions.TODO_CREATE, new Subscriber<Action>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Action action) {
                Log.d(TAG, "[onNext|" + action.getType() + "] State: " + state());
                String text = ((String) action.getData().get(TodoActions.KEY_TEXT));
                TodoState newState = create(text);
                setState(newState);
            }
        });

        dispatcher.subscribe(TodoActions.TODO_DESTROY, new Subscriber<Action>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Action action) {
                Log.d(TAG, "[onNext|" + action.getType() + "] State: " + state());
                long id = ((long) action.getData().get(TodoActions.KEY_ID));
                setState(destroy(id));
            }
        });

        dispatcher.subscribe(TodoActions.TODO_UNDO_DESTROY, new Subscriber<Action>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Action action) {
                setState(undoDestroy());
            }
        });

        dispatcher.subscribe(TodoActions.TODO_COMPLETE, new Subscriber<Action>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Action action) {
                long id = ((long) action.getData().get(TodoActions.KEY_ID));
                setState(updateComplete(id, true));
            }
        });

        dispatcher.subscribe(TodoActions.TODO_UNDO_COMPLETE, new Subscriber<Action>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Action action) {
                long id = ((long) action.getData().get(TodoActions.KEY_ID));
                setState(updateComplete(id, false));
            }
        });

        dispatcher.subscribe(TodoActions.TODO_DESTROY_COMPLETED, new Subscriber<Action>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Action action) {
                setState(destroyCompleted());
            }
        });

        dispatcher.subscribe(TodoActions.TODO_TOGGLE_COMPLETE_ALL, new Subscriber<Action>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Action action) {
                setState(updateCompleteAll());
            }
        });
    }

    @Override
    protected TodoState initialState() {
        return new TodoState();
    }

    public static TodoStore get(Dispatcher dispatcher) {
        if (instance == null) {
            instance = new TodoStore(dispatcher);
        }
        return instance;
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
