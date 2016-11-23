package lgvalle.com.fluxtodo.model;

/**
 * Created by lgvalle on 02/08/15.
 */
public class Todo implements Cloneable, Comparable<Todo> {

    long id;
    boolean complete;
    String text;

    public Todo(long id, String text) {
        this.id = id;
        this.text = text;
        this.complete = false;
    }

    public Todo(long id, String text, boolean complete) {
        this.id = id;
        this.text = text;
        this.complete = complete;
    }

    public Todo(Todo todo) {
        this.id = todo.id;
        this.text = todo.text;
        this.complete = todo.isComplete();
    }

    public long getId() {
        return id;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getText() {
        return text;
    }

    @Override
    public Todo clone() {
        return new Todo(id, text, complete);
    }

    @Override
    public int compareTo(Todo todo) {
        if (id == todo.getId()) {
            return 0;
        } else if (id < todo.getId()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", complete=" + complete +
                ", text='" + text + '\'' +
                '}';
    }
}
