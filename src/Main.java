public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Проснуться", "Встать с кровати");
        Task task2 = new Task("Помыться", "Сходить в душ");

        Epic epic1 = new Epic("Переезд", "В новый дом");
        Subtask sub1 = new Subtask("Собрать коробки", "Аккуратно сложить вещи");
        Subtask sub2 = new Subtask("Закрыть коробки", "Заклеить коробки и отнести их в машину");
        epic1.createSubtask(sub1);
        epic1.createSubtask(sub2);

        Epic epic2 = new Epic("Попрощаться", "Со старым домом");
        Subtask sub3 = new Subtask("Кошка!", "Хорошо что мяукнула, куда же без тебя!");
        epic2.createSubtask(sub3);

        taskManager.create(task1);
        taskManager.create(task2);
        taskManager.create(epic1);
        taskManager.create(epic2);
        System.out.println("Список задач:");
        System.out.println(taskManager.getTaskHashMap());
        System.out.println("Список эпиков:");
        System.out.println(taskManager.getEpicHashMap());
        System.out.println("Список подзадач:");
        System.out.println(taskManager.getSubtaskHashMap());

        System.out.println("Обновление статуса Task:");
        task1.setStatus(TaskStatus.IN_PROGRESS);
        var taskList = taskManager.getTaskHashMap();
        for (Task task : taskList.values()) {
            System.out.println(task.getName() + " " + task.getStatus() + " " + task.getId());
        }
        System.out.println("Обновление статуса Subtask:");
        sub1.setStatus(TaskStatus.DONE);
        sub2.setStatus(TaskStatus.DONE);
        taskManager.update(sub1);
        taskManager.update(sub2);
        var subtaskList = taskManager.getSubtaskHashMap();
        for (Subtask subtask : subtaskList.values()) {
            System.out.println(subtask.getName() + " " + subtask.getStatus() + " " + subtask.getId());
        }
        System.out.println("Обновление статуса Epic:");
        var epicList = taskManager.getEpicHashMap();
        for (Epic epic : epicList.values()) {
            System.out.println(epic.getName() + " " + epic.getStatus() + " " + epic.getId());
        }

        System.out.println("Удаление Task:");
        taskManager.deleteById(task2.getId());
        for (Task task : taskList.values()) {
            System.out.println(task.getName() + " " + task.getStatus() + " " + task.getId());
        }
        System.out.println("Удаление Epic:");
        taskManager.deleteById(epic2.getId());
        for (Epic epic : epicList.values()) {
            System.out.println(epic.getName() + " " + epic.getStatus() + " " + epic.getId());
        }
        //проверяем что удалились subtask при удалении epic
        for (Subtask subtask : subtaskList.values()) {
            System.out.println(subtask.getName() + " " + subtask.getStatus() + " " + subtask.getId());
        }

        System.out.println("Удаление всех Task, Epic, Subtask");
        taskManager.deleteAll();
        System.out.println("После удаления размер Epic= " + taskManager.getEpicHashMap().size());
        System.out.println("После удаления размер Task= " + taskManager.getTaskHashMap().size());
        System.out.println("После удаления размер Subtask= " + taskManager.getSubtaskHashMap().size());
    }
}
