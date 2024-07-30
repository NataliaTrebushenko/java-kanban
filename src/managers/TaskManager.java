package managers;

import models.Epic;
import models.Subtask;
import models.Task;

import java.util.ArrayList;
import java.util.Collection;

public interface TaskManager {
    int getId();

    Collection<Task> getTasks();

    Collection<Epic> getEpics();

    Collection<Subtask> getSubtasks();

    void deleteTaskAll();

    void deleteSubtaskAll();

    void deleteEpicAll();

    Task getTaskById(int id);

    Epic getEpicById(int id);

    Subtask getSubtaskById(int id);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void updateTask(Task task);

    void updateEpic(Epic epic);

    void updateSubtask(Subtask subtask);

    void updateEpicStatus(Epic epic);

    void deleteTaskById(int id);

    void deleteEpicById(int id);

    void deleteSubtaskById(int id);

    Collection<Subtask> getEpicSubtasks(int epicId);

    ArrayList<Task> getHistoryList();

}
