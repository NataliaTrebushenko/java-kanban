import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {

    private final HashMap<Integer, Task> taskHashMap = new HashMap<>();
    private final HashMap<Integer, Epic> epicHashMap = new HashMap<>();
    private final HashMap<Integer, Subtask> subtaskHashMap = new HashMap<>();

    public HashMap<Integer, Task> getTaskHashMap() {
        return taskHashMap;
    }

    public HashMap<Integer, Epic> getEpicHashMap() {
        return epicHashMap;
    }

    public HashMap<Integer, Subtask> getSubtaskHashMap() {
        return subtaskHashMap;
    }

    public void deleteAll() {
        taskHashMap.clear();
        subtaskHashMap.clear();
        epicHashMap.clear();
    }

    public Task getById(int id) {
        boolean isTaskKeyExist = taskHashMap.containsKey(id);
        boolean isEpicKeyExist = epicHashMap.containsKey(id);
        boolean isSubtaskKeyExist = subtaskHashMap.containsKey(id);
        if (isTaskKeyExist) {
            return taskHashMap.get(id);
        }
        if (isEpicKeyExist) {
            return epicHashMap.get(id);
        }
        if (isSubtaskKeyExist) {
            return subtaskHashMap.get(id);
        }
        System.out.println("По данному id =" + id + " задача не найдена!");
        return null;
    }

    public void create(Task task) {
        if (task == null) {
            System.out.println("Данная задача отсутствует!");
            return;
        }
        if (task instanceof Epic) {
            var subtasks = ((Epic) task).getSubtasks();
            for (Subtask subtask : subtasks.values()) {
                subtaskHashMap.put(subtask.getId(), subtask);
            }
            epicHashMap.put(task.getId(), (Epic) task);
            return;
        }
        if (task instanceof Subtask) {
            int epicId = ((Subtask) task).getEpicId();
            if (epicHashMap.containsKey(epicId)) {
                Epic epic = epicHashMap.get(epicId);
                updateEpicStatus(epic);
                subtaskHashMap.put(task.getId(), (Subtask) task);
            }
            return;
        }
        taskHashMap.put(task.getId(), task);
    }

    public void update(Task task) {
        if (task == null) {
            System.out.println("Данная задача отсутствует!");
            return;
        }
        int taskId = task.getId();
        Task findTask = getById(taskId);
        if (findTask == null) {
            System.out.println("По данному ID, задача не найдена!");
            return;
        }
        create(task);
    }

    public void updateEpicStatus(Epic epic) {
        if (epic == null) {
            System.out.println("Не передано.");
            return;
        }
        if (!epicHashMap.containsKey(epic.getId())) {
            System.out.println("С таким ID эпик отсутствует!");
            return;
        }
        var subtasks = getSubtaskEpic(epic);
        if (subtasks.isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            int countDone = 0;
            int countNew = 0;
            for (Subtask subtask : subtasks.values()) {
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

    public void deleteById(int id) {
        boolean isTaskKeyExist = taskHashMap.containsKey(id);
        boolean isEpicKeyExist = epicHashMap.containsKey(id);
        boolean isSubtaskKeyExist = subtaskHashMap.containsKey(id);
        if (isTaskKeyExist) {
            taskHashMap.remove(id);
            return;
        }
        if (isEpicKeyExist) {
            ArrayList<Integer> subtaskIdList = new ArrayList<>();
            for (Subtask task : subtaskHashMap.values()) {
                if (task.getEpicId() == id) {
                    subtaskIdList.add(task.getId());
                }
            }
            subtaskIdList.forEach(subtaskHashMap::remove);
            epicHashMap.remove(id);
        }
        if (isSubtaskKeyExist) {
            Subtask task = (Subtask) getById(id);
            int epicId = task.getEpicId();
            Epic epic = epicHashMap.get(epicId);
            epic.deleteSubtask(id);
            subtaskHashMap.remove(id);
        }
    }

    public HashMap<Integer, Subtask> getSubtaskEpic(Epic epic) {
        if (epicHashMap.containsKey(epic.getId())) {
            return epic.getSubtasks();
        }
        return null;
    }
}
