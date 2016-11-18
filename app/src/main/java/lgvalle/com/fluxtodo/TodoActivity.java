package lgvalle.com.fluxtodo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lgvalle.com.fluxtodo.actions.ActionsCreator;
import lgvalle.com.fluxtodo.dispatcher.Dispatcher;
import lgvalle.com.fluxtodo.stores.TodoState;
import lgvalle.com.fluxtodo.stores.TodoStore;
import rx.Subscriber;

public class TodoActivity extends AppCompatActivity {

    private static final String TAG = "TodoActivity";

    @BindView(R.id.main_input)
    public EditText mainInput;

    @BindView(R.id.main_layout)
    public ViewGroup mainLayout;

    @BindView(R.id.main_checkbox)
    public CheckBox mainCheck;

    private TodoRecyclerAdapter listAdapter;

    private Dispatcher dispatcher;
    private ActionsCreator actionsCreator;
    private TodoStore todoStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDependencies();
        setupView();
    }

    private void initDependencies() {
        dispatcher = Dispatcher.get();
        actionsCreator = ActionsCreator.get(dispatcher);
        todoStore = TodoStore.get(dispatcher);
        todoStore.subscribe(new Subscriber<TodoState>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "[onCompleted]");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "[onError] Error: " + e);
            }

            @Override
            public void onNext(TodoState todoState) {
                Log.d(TAG, "[onNext] State: " + todoState);
                updateUI(todoState);
            }
        });
    }

    private void setupView() {

        ButterKnife.bind(this);

        RecyclerView mainList = (RecyclerView) findViewById(R.id.main_list);
        mainList.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new TodoRecyclerAdapter(actionsCreator);
        mainList.setAdapter(listAdapter);
    }

    @OnClick(R.id.main_clear_completed)
    public void onClearCompleteButtonClick() {
        clearCompleted();
        resetMainCheck();
    }

    @OnClick(R.id.main_checkbox)
    public void onCheckBoxClick() {
        checkAll();
    }

    @OnClick(R.id.main_add)
    public void onAddButtonClick() {
        addTodo();
        resetMainInput();
    }

    private void updateUI(TodoState state) {

        Log.d(TAG, "[updateUI] New state: " + state);

        listAdapter.setItems(state.todos);

        if (todoStore.canUndo()) {
            Snackbar snackbar = Snackbar.make(mainLayout, "Element deleted", Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    actionsCreator.undoDestroy();
                }
            });
            snackbar.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void addTodo() {
        Log.d("TODO", "[addTodo]");
        if (validateInput()) {
            actionsCreator.create(getInputText());
        }
    }

    private void checkAll() {
        actionsCreator.toggleCompleteAll();
    }

    private void clearCompleted() {
        actionsCreator.destroyCompleted();
    }

    private void resetMainInput() {
        mainInput.setText("");
    }

    private void resetMainCheck() {
        if (mainCheck.isChecked()) {
            mainCheck.setChecked(false);
        }
    }

    private boolean validateInput() {
        return !TextUtils.isEmpty(getInputText());
    }

    private String getInputText() {
        return mainInput.getText().toString();
    }

}
