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

    public int getId() {
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
            updateEpicStatus(epic);
        }
    }

    public void deleteEpicAll() {
        subtasks.clear();
        epics.clear();
    }

    public Task getTaskById(int id) {
        boolean isTaskKeyExist = tasks.containsKey(id);
        if (isTaskKeyExist) {
            return tasks.get(id);
        }
        System.out.println("По данному id =" + id + " Task не найден!");
        return null;
    }

    public Epic getEpicById(int id) {
        boolean isEpicKeyExist = epics.containsKey(id);
        if (isEpicKeyExist) {
            return epics.get(id);
        }
        System.out.println("По данному id =" + id + " Epic не найден!");
        return null;
    }

    public Subtask getSubtaskById(int id) {
        boolean isSubtaskKeyExist = subtasks.containsKey(id);
        if (isSubtaskKeyExist) {
            return subtasks.get(id);
        }
        System.out.println("По данному id =" + id + " Subtask не найден!");
        return null;
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
        epic.createSubtask(subtask);
        subtask.setId(getId());
        subtasks.put(subtask.getId(), subtask);
    }


    public void updateTask(Task task) {
        if (task == null) {
            System.out.println("Данная задача отсутствует!");
            return;
        }
        int taskId = task.getId();
        var findTask = getTaskById(taskId);
        if (findTask == null) {
            System.out.println("По данному ID, задача не найдена!");
            return;
        }
        findTask.setStatus(task.getStatus());
        findTask.setDescription(task.getDescription());
        findTask.setName(task.getDescription());
    }

    public void updateEpic(Epic epic) {
        if (epic == null) {
            System.out.println("Данный Epic отсутствует!");
            return;
        }
        var findEpic = getEpicById(epic.getId());
        if (findEpic == null) {
            System.out.println("По данному ID, Epic не найден!");
            return;
        }
        findEpic.setName(epic.getName());
        findEpic.setDescription(epic.getDescription());
    }

    public void updateSubtask(Subtask subtask) {
        if (subtask == null) {
            System.out.println("Данный Subtask отсутствует!");
            return;
        }
        var findSubtask = getSubtaskById(subtask.getId());
        if (findSubtask == null) {
            System.out.println("По данному ID, Epic не найден!");
            return;
        }
        findSubtask.setName(subtask.getName());
        findSubtask.setDescription(subtask.getDescription());
        findSubtask.setStatus(subtask.getStatus());
        var epic = getEpicById(subtask.getEpicId());
        updateEpicStatus(epic);
    }

    public void updateEpicStatus(Epic epic) {
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
        boolean isTaskKeyExist = tasks.containsKey(id);
        if (isTaskKeyExist) {
            tasks.remove(id);
        }
    }

    public void deleteEpicById(int id) {
        boolean isEpicKeyExist = epics.containsKey(id);
        if (isEpicKeyExist) {
            var epic = getEpicById(id);
            for (Subtask task : epic.getSubtasks()) {
                subtasks.remove(task.getId());
            }
            epics.remove(id);
        }
    }

    public void deleteSubtaskById(int id) {
        boolean isSubtaskKeyExist = subtasks.containsKey(id);
        if (isSubtaskKeyExist) {
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
