import java.util.HashMap;

public class Epic extends Task {

    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void deleteSubtask(int id) {
        subtasks.remove(id);
    }

    public Epic(String name, String description) {
        super(name, description);
    }

    public void createSubtask(Subtask subtask) {
        subtask.setEpicId(getId());
        subtasks.put(subtask.getId(), subtask);
    }


    @Override
    public String toString() {
        return "Epic{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}

