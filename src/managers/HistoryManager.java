package managers;

import models.Task;

import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {

    List<Task> getHistoryList();

    void addTask(Task task);
}
