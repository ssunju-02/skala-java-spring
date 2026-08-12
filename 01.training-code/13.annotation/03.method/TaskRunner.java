public class TaskRunner {
    @RunMe(order = 2)
    public void taskB() {
        System.out.println("Task B");
    }

    @RunMe(order = 1)
    public void taskA() {
        System.out.println("Task A");
    }

    public void noAnnotation() {
        System.out.println("Ignore");
    }
}
