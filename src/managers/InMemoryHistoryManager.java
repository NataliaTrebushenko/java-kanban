package managers;
import models.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int HISTORY_MAX_SIZE = 10;

    private final ArrayList<Task> historyList = new ArrayList<>();

    @Override
    public ArrayList<Task> getHistoryList() {
        return historyList;
    }

    @Override
    public void addTask(Task task) {
        if (historyList.size() == HISTORY_MAX_SIZE) {
            historyList.removeFirst();
        }
        historyList.add(task);
    }
}

