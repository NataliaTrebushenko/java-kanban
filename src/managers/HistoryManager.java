package managers;

import models.Task;

import java.util.ArrayList;

public interface HistoryManager {

    ArrayList<Task> getHistoryList();

    void addTask(Task task);
}
