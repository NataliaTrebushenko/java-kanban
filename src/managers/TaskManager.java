package managers;

import models.Epic;
import models.Subtask;
import models.Task;
import models.TaskStatus;

import java.util.Collection;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int id = 0;

    private int getId() {
        id++;
        return id;
    }

    public Collection<Task> getTasks() {
        return tasks.values();
    }

    public Collection<Epic> getEpics() {
        return epics.values();
    }

    public Collection<Subtask> getSubtasks() {
        return subtasks.values();
    }

    public void deleteTaskAll() {
        tasks.clear();
    }

    public void deleteSubtaskAll() {
        subtasks.clear();
        for (Epic epic : getEpics()) {
            epic.getSubtasks().clear();
            updateEpicStatus(epic);
        }
    }

    public void deleteEpicAll() {
        subtasks.clear();
        epics.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }


    public void createTask(Task task) {
        if (task == null) {
            System.out.println("Передан null!");
            return;
        }
        task.setId(getId());
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        if (epic == null) {
            System.out.println("Передан null!");
            return;
        }
        epic.setId(getId());
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        if (!epics.containsKey(epicId)) {
            return;
        }
        Epic epic = epics.get(epicId);
        epic.addSubtask(subtask);
        updateEpicStatus(epic);
        subtask.setId(getId());
        subtasks.put(subtask.getId(), subtask);
    }


    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            System.out.println("Неверная задача");
            return;
        }
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        if (epic == null || !epics.containsKey(epic.getId())) {
            System.out.println("Неверная задача");
            return;
        }
        var findEpic = getEpicById(epic.getId());
        findEpic.setName(epic.getName());
        findEpic.setDescription(epic.getDescription());
    }

    public void updateSubtask(Subtask subtask) {
        if (subtask == null || !subtasks.containsKey(subtask.getId())) {
            System.out.println("Неверная задача");
            return;
        }
        subtasks.put(subtask.getId(), subtask);
        var epic = getEpicById(subtask.getEpicId());
        epic.addSubtask(subtask);
        updateEpicStatus(epic);
    }

    private void updateEpicStatus(Epic epic) {
        if (epic == null) {
            System.out.println("Не передано.");
            return;
        }
        if (!epics.containsKey(epic.getId())) {
            System.out.println("С таким ID эпик отсутствует!");
            return;
        }
        var subtasks = epic.getSubtasks();
        if (subtasks.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            int countDone = 0;
            int countNew = 0;
            for (Subtask subtask : subtasks) {
                if (subtask.getStatus().equals(TaskStatus.DONE)) {
                    countDone++;
                }
                if (subtask.getStatus().equals(TaskStatus.NEW)) {
                    countNew++;
                }
            }
            int size = subtasks.size();
            if (countDone == size) {
                epic.setStatus(TaskStatus.DONE);
            } else if (countNew == size) {
                epic.setStatus(TaskStatus.NEW);
            } else {
                epic.setStatus(TaskStatus.IN_PROGRESS);
            }
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        if (epics.containsKey(id)) {
            var epic = getEpicById(id);
            for (Subtask task : epic.getSubtasks()) {
                subtasks.remove(task.getId());
            }
            epics.remove(id);
        }
    }

    public void deleteSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            Subtask task = getSubtaskById(id);
            int epicId = task.getEpicId();
            Epic epic = epics.get(epicId);
            epic.deleteSubtask(id);
            subtasks.remove(id);
            updateEpicStatus(epic);
        }
    }

    public Collection<Subtask> getEpicSubtasks(int epicId) {
        if (epics.containsKey(epicId)) {
            return epics.get(epicId).getSubtasks();
        }
        return null;
    }
}
