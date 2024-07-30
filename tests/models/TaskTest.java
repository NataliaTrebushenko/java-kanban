package models;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TaskTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    void comparisonIdTask() {
        Task task1 = new Task("Не заниматься ночью", "План провален");
        taskManager.createTask(task1);
        Assertions.assertEquals(task1, taskManager.getTaskById(task1.getId()));
    }

}
