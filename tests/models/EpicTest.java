package models;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EpicTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    void containsEpicToSubtask() {
        Epic epic = new Epic("Первый", "Всегда первый");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("первый", "Всегда первый", epic.getId());
        taskManager.createSubtask(subtask);
        Assertions.assertFalse(taskManager.getEpics().contains(subtask));
    }

}
