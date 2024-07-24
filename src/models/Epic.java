package models;
import java.util.Collection;
import java.util.HashMap;

public class Epic extends Task {

    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public Collection<Subtask> getSubtasks() {
        return subtasks.values();
    }

    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }


    @Override
    public String toString() {
        return "tasks.Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}

