import managers.TaskManager;
import models.Epic;
import models.Subtask;
import models.Task;
import models.TaskStatus;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Проснуться", "Встать с кровати");
        taskManager.createTask(task1);
        Task task2 = new Task("Помыться", "Сходить в душ");
        taskManager.createTask(task2);

        Epic epic1 = new Epic("Переезд", "В новый дом");
        taskManager.createEpic(epic1);
        Subtask sub1 = new Subtask("Собрать коробки", "Аккуратно сложить вещи", epic1.getId());
        Subtask sub2 = new Subtask("Закрыть коробки", "Заклеить коробки и отнести их в машину", epic1.getId());
        epic1.addSubtask(sub1);
        taskManager.createSubtask(sub1);
        epic1.addSubtask(sub2);
        taskManager.createSubtask(sub2);

        Epic epic2 = new Epic("Попрощаться", "Со старым домом");
        taskManager.createEpic(epic2);
        Subtask sub3 = new Subtask("Кошка!", "Хорошо что мяукнула, куда же без тебя!", epic2.getId());
        epic2.addSubtask(sub3);
        taskManager.createSubtask(sub3);

        System.out.println("Список задач:");
        System.out.println(taskManager.getTasks());
        System.out.println("Список эпиков:");
        System.out.println(taskManager.getEpics());
        System.out.println("Список подзадач:");
        System.out.println(taskManager.getSubtasks());

        System.out.println("Обновление статуса tasks.Task:");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        var taskList = taskManager.getTasks();
        for (Task task : taskList) {
            System.out.println(task.getName() + " " + task.getStatus() + " " + task.getId());
        }
        System.out.println("Обновление статуса tasks.Subtask:");
        sub1.setStatus(TaskStatus.DONE);
        sub2.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(sub1);
        taskManager.updateSubtask(sub2);
        var subtaskList = taskManager.getSubtasks();
        for (Subtask subtask : subtaskList) {
            System.out.println(subtask.getName() + " " + subtask.getStatus() + " " + subtask.getId());
        }
        System.out.println("Обновление статуса tasks.Epic:");
        var epicList = taskManager.getEpics();
        for (Epic epic : epicList) {
            System.out.println(epic.getName() + " " + epic.getStatus() + " " + epic.getId());
        }

        System.out.println("Удаление tasks.Task:");
        taskManager.deleteTaskById(task2.getId());
        for (Task task : taskList) {
            System.out.println(task.getName() + " " + task.getStatus() + " " + task.getId());
        }
        System.out.println("Удаление tasks.Epic:");
        taskManager.deleteEpicById(epic2.getId());
        for (Epic epic : epicList) {
            System.out.println(epic.getName() + " " + epic.getStatus() + " " + epic.getId());
        }
        //проверяем что удалились subtask при удалении epic
        for (Subtask subtask : subtaskList) {
            System.out.println(subtask.getName() + " " + subtask.getStatus() + " " + subtask.getId());
        }

        System.out.println("Удаление всех tasks.Task, tasks.Epic, tasks.Subtask");

        taskManager.deleteTaskAll();
        System.out.println("После удаления размер tasks.Task= " + taskManager.getTasks().size());

        taskManager.deleteEpicAll();
        System.out.println("После удаления размер tasks.Epic= " + taskManager.getEpics().size());

        taskManager.deleteSubtaskAll();
        System.out.println("После удаления размер tasks.Subtask= " + taskManager.getSubtasks().size());
    }
}
