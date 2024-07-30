import managers.TaskManager;
import models.Epic;
import models.Subtask;
import models.Task;
import managers.Managers;
import models.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;



class UnitTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    void comparisonIdTask() {
        Task task1 = new Task("Не заниматься ночью", "План провален");
        taskManager.createTask(task1);
        Assertions.assertEquals(task1, taskManager.getTaskById(task1.getId()));
    }

    @Test
    void comparisonIdTaskHeir() {
        Epic epic = new Epic("Лечь спать", "Опять мимо");
        Subtask subtask1 = new Subtask("Хотелось бы", "Но нет", epic.getId());
        Subtask subtask2 = new Subtask("Может завтра", "Вряд ли", epic.getId());
        Assertions.assertEquals(subtask1, subtask2);
    }

    @Test
    void addEpicToSubtask() {
        Epic epic = new Epic("Первый", "Всегда первый");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("первый", "Всегда первый", epic.getId());
        taskManager.createSubtask(subtask);
        Assertions.assertFalse(taskManager.getEpics().contains(subtask));
    }

    @Test
    void addSubtaskOwnEpic() {
        Epic epic = new Epic("Второй", "Потом первый");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("Второй","Потом первый", epic.getId());
        taskManager.createSubtask(subtask);
        Assertions.assertFalse(taskManager.getSubtasks().contains(epic));
    }

    @Test
    public void GetDefault() {
        Assertions.assertNotNull(Managers.getDefault());
    }

    @Test
    public void GetDefaultHistory() {
        Assertions.assertNotNull(Managers.getDefaultHistory());
    }

    @Test
    void addingAllTypesToInMemoryTaskManager() {
        Task task = new Task("Первый", "Всегда первый");
        taskManager.createTask(task);
        taskManager.getHistoryList().add(task);
        Epic epic = new Epic("Первый", "Всегда первый");
        taskManager.createEpic(epic);
        taskManager.getHistoryList().add(epic);

        Assertions.assertEquals(1, taskManager.getTasks().size());
        Assertions.assertEquals(2, taskManager.getHistoryList().size());
        Assertions.assertNotEquals(taskManager.getHistoryList().getFirst(),taskManager.getHistoryList().get(1));

    }

    @Test
    void doNotConflictIdInMemoryTaskManager() {
        Task task1 = new Task("Первый", "Потом первый");
        taskManager.createTask(task1);;
        int firstId = task1.getId();
        Task task2 = new Task("Первый", "Всегда первый");
        taskManager.createTask(task2);;
        int secondId = task2.getId();
        Assertions.assertEquals(firstId, taskManager.getTaskById(task1.getId()).getId());
        Assertions.assertEquals(secondId, taskManager.getTaskById(task2.getId()).getId());
    }

    @Test
    void theTaskDoesNotChangeWhenAddedToTheManager() {
        Task task = new Task("Первый", "Всегда первый");
        String taskName = task.getName();
        String taskDescription = task.getDescription();
        TaskStatus taskStatus = task.getStatus();

        taskManager.createTask(task);

        Assertions.assertEquals(taskName, taskManager.getTaskById(task.getId()).getName());
        Assertions.assertEquals(taskDescription, taskManager.getTaskById(task.getId()).getDescription());
        Assertions.assertEquals(taskStatus, taskManager.getTaskById(task.getId()).getStatus());
    }

}
