package managers;
import models.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private static final int HISTORY_MAX_SIZE = 10;

    private final List<Task> historyList = new ArrayList<>();

    @Override
    public List<Task> getHistoryList() {
        return historyList;
    }

    @Override
    public void addTask(Task task) {
        if (task == null) {
            return;
        }
        if (historyList.size() == HISTORY_MAX_SIZE) {
            historyList.removeFirst();
        }
        historyList.add(task);
    }

}

