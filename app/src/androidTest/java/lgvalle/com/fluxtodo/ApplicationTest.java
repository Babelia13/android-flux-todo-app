package lgvalle.com.fluxtodo;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.ViewGroup;
import android.widget.CheckBox;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import lgvalle.com.fluxtodo.actions.CustomViewActions;
import lgvalle.com.fluxtodo.model.Todo;
import lgvalle.com.fluxtodo.recyclerview.RecyclerViewInteraction;
import lgvalle.com.fluxtodo.recyclerview.RecyclerViewItemCountAssertion;
import lgvalle.com.fluxtodo.stores.TodoState;
import lgvalle.com.fluxtodo.util.FluxUtil;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ApplicationTest {

    private final String TODO_TASK_TEXT = "Todo task";

    @Rule
    public ActivityTestRule<TodoActivity> activityRule = new ActivityTestRule<>(
            TodoActivity.class);

    @Test
    public void add_task() {

        // Type the text of the task in the edit text
        onView(withId(R.id.main_input)).perform(typeText(TODO_TASK_TEXT), closeSoftKeyboard());

        // Click the add task button
        onView(withId(R.id.main_add)).perform(click());

        // Check that the task has been included in the list
        onView(withId(R.id.main_list))
                .check(matches(hasDescendant(withText(TODO_TASK_TEXT))));

    }

    @Test
    public void remove_task() {

        // Fill task list with ten items
        fillTodoList();

        // Remove first task of the list
        onView(withId(R.id.main_list))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(TODO_TASK_TEXT)),
                        CustomViewActions.clickChildViewWithId(R.id.row_delete)));

        // Check that the task has been deleted from the list
        onView(withId(R.id.main_list))
                .check(matches(not(hasDescendant(withText(TODO_TASK_TEXT)))));

    }

    @Test
    public void remove_task_and_undo() {

        // Fill task list with ten items
        fillTodoList();

        // Remove first task of the list
        onView(withId(R.id.main_list))
                .perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(TODO_TASK_TEXT)),
                        CustomViewActions.clickChildViewWithId(R.id.row_delete)));

        // Click the UNDO snack bar
        onView(allOf(withId(android.support.design.R.id.snackbar_action), withText(R.string.undo_delete_item)))
                .perform(click());

        // Check that the task is in the list again
        onView(withId(R.id.main_list))
                .check(matches(hasDescendant(withText(TODO_TASK_TEXT))));

    }

    @Test
    public void complete_first_task() {

        // Fill task list with ten items
        fillTodoList();

        // Complete the first task
        onView(withId(R.id.main_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        CustomViewActions.clickChildViewWithId(R.id.row_checkbox)));

        // Get list updated
        List<Todo> todoList = FluxUtil.findStore(activityRule.getActivity()).state().todos;

        // Check that the state of all the tasks is the correct one
        RecyclerViewInteraction.<Todo>onRecyclerView(withId(R.id.main_list))
                .withItems(todoList)
                .check((item, view, e) -> {
                    CheckBox checkbox = ((CheckBox)((ViewGroup) view).getChildAt(0));
                    assertThat(checkbox.isChecked(), is(item.isComplete()));
                });

        // Another way to implement the assertion is the following:
//        onView(withId(R.id.main_list)).check(new ViewAssertion() {
//            @Override public void check(View view, NoMatchingViewException noViewFoundException) {
//                RecyclerView.ViewHolder viewHolder = ((RecyclerView) view).findViewHolderForAdapterPosition(0);
//                CheckBox checkBox = (CheckBox) viewHolder.itemView.findViewById(R.id.row_checkbox);
//                assertThat(checkBox.isChecked(), is(true));
//            }
//        });
    }

    @Test
    public void complete_all_tasks() {

        // Fill task list with ten items
        fillTodoList();

        // Perform check
        onView(withId(R.id.main_checkbox)).perform(click());

        // Get list updated
        List<Todo> todoList = FluxUtil.findStore(activityRule.getActivity()).state().todos;

        // Check that all the task have been completed
        RecyclerViewInteraction.<Todo>onRecyclerView(withId(R.id.main_list))
                .withItems(todoList)
                .check((item, view, e) -> {
                    CheckBox checkbox = ((CheckBox)((ViewGroup) view).getChildAt(0));
                    assertThat(checkbox.isChecked(), is(true));
                });

    }

    @Test
    public void complete_and_clear_first_task() {

        // Fill task list with ten items
        fillTodoList();

        // Complete the first task
        onView(withId(R.id.main_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0,
                        CustomViewActions.clickChildViewWithId(R.id.row_checkbox)));

        // Click the clear completed button
        onView(withId(R.id.main_clear_completed)).perform(click());

        // Check that the task has been deleted from the list
        onView(withId(R.id.main_list))
                .check(matches(not(hasDescendant(withText(TODO_TASK_TEXT)))));
    }

    @Test
    public void complete_and_clear_all_tasks() {

        // Fill task list with ten items and complete all of them
        fillAndCompleteTodoList();

        // Click the clear completed button
        onView(withId(R.id.main_clear_completed)).perform(click());

        // Check the recycler view is empty after deleting all the tasks
        onView(withId(R.id.main_list)).check(new RecyclerViewItemCountAssertion(0));
    }

    /**
     * Create a tasks list with ten item.
     */
    public void fillTodoList() {

        ArrayList<Todo> todoList = new ArrayList<Todo>();
        for (int i = 0; i < 10; i++) {
            String taskText = TODO_TASK_TEXT + " " + i;
            if (i == 0) taskText = TODO_TASK_TEXT;
            todoList.add(new Todo(i, taskText));        }

        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FluxUtil.findStore(activityRule.getActivity()).setState(new TodoState(todoList, null));
            }
        });
    }

    /**
     * Create a tasks list with ten item.
     */
    public void fillAndCompleteTodoList() {

        ArrayList<Todo> todoList = new ArrayList<Todo>();
        for (int i = 0; i < 10; i++) {
            String taskText = TODO_TASK_TEXT + " " + i;
            if (i == 0) taskText = TODO_TASK_TEXT;
            Todo todo = new Todo(i, taskText);
            todo.setComplete(true);
            todoList.add(todo);
        }

        activityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FluxUtil.findStore(activityRule.getActivity()).setState(new TodoState(todoList, null));
            }
        });
    }
}