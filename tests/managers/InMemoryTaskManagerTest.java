package managers;

import models.Epic;
import models.Task;
import models.TaskStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InMemoryTaskManagerTest {

    TaskManager taskManager = Managers.getDefault();
    Task task1;
    Epic epic;
    Task task2;


    @BeforeEach
    void createTaskAndEpic() {
        task1 = new Task("Первый", "Всегда первый");
        epic = new Epic("Первый", "Всегда первый");
        task2 = new Task("Второй", "Потом первый");
    }

    @Test
    void addingAllTypesToInMemoryTaskManager() {
        taskManager.createTask(task1);
        taskManager.getHistoryList().add(task1);
        taskManager.createEpic(epic);
        taskManager.getHistoryList().add(epic);

        Assertions.assertEquals(1, taskManager.getTasks().size());
        Assertions.assertEquals(2, taskManager.getHistoryList().size());
        Assertions.assertNotEquals(taskManager.getHistoryList().getFirst(),taskManager.getHistoryList().get(1));

    }

    @Test
    void doNotConflictIdInMemoryTaskManager() {
        taskManager.createTask(task1);;
        int firstId = task1.getId();
        taskManager.createTask(task2);;
        int secondId = task2.getId();
        Assertions.assertEquals(firstId, taskManager.getTaskById(task1.getId()).getId());
        Assertions.assertEquals(secondId, taskManager.getTaskById(task2.getId()).getId());
    }

    @Test
    void theTaskDoesNotChangeWhenAddedToTheManager() {
        String taskName = task1.getName();
        String taskDescription = task1.getDescription();
        TaskStatus taskStatus = task1.getStatus();

        taskManager.createTask(task1);

        Assertions.assertEquals(taskName, taskManager.getTaskById(task1.getId()).getName());
        Assertions.assertEquals(taskDescription, taskManager.getTaskById(task1.getId()).getDescription());
        Assertions.assertEquals(taskStatus, taskManager.getTaskById(task1.getId()).getStatus());
    }
}
