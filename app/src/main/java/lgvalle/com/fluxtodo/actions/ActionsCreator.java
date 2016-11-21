package lgvalle.com.fluxtodo.actions;

import android.util.Log;

import lgvalle.com.fluxtodo.dispatcher.Dispatcher;
import lgvalle.com.fluxtodo.model.Todo;

/**
 * Created by lgvalle on 02/08/15.
 */
public class ActionsCreator {

    public void create(String text) {
        Log.d("TODO", "[create]");
        Dispatcher.dispatch(
                TodoActions.TODO_CREATE,
                TodoActions.KEY_TEXT, text
        );

    }

    public void destroy(long id) {
        Dispatcher.dispatch(
                TodoActions.TODO_DESTROY,
                TodoActions.KEY_ID, id
        );
    }

    public void undoDestroy() {
        Dispatcher.dispatch(
                TodoActions.TODO_UNDO_DESTROY
        );
    }

    public void toggleComplete(Todo todo) {
        long id = todo.getId();
        String actionType = todo.isComplete() ? TodoActions.TODO_UNDO_COMPLETE : TodoActions.TODO_COMPLETE;

        Dispatcher.dispatch(
                actionType,
                TodoActions.KEY_ID, id
        );
    }

    public void toggleCompleteAll() {
        Dispatcher.dispatch(TodoActions.TODO_TOGGLE_COMPLETE_ALL);
    }

    public void destroyCompleted() {
        Dispatcher.dispatch(TodoActions.TODO_DESTROY_COMPLETED);
    }
}
