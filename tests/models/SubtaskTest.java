package models;

import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SubtaskTest {

    TaskManager taskManager = Managers.getDefault();

    @Test
    void containsSubtaskToEpic() {
        Epic epic = new Epic("Первый", "Всегда первый");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask("первый", "Всегда первый", epic.getId());
        taskManager.createSubtask(subtask);
        Assertions.assertFalse(taskManager.getSubtasks().contains(epic));
    }

    @Test
    void subtaskEqualByEpicId() {
        Epic epic = new Epic("Лечь спать", "Опять мимо");
        Subtask subtask1 = new Subtask("Хотелось бы", "Но нет", epic.getId());
        Subtask subtask2 = new Subtask("Может завтра", "Вряд ли", epic.getId());

        Assertions.assertEquals(subtask1.getEpicId(), subtask2.getEpicId());
    }

}
