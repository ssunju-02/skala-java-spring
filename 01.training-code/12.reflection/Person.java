public class Person {
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Note("인사를 담당하는 메서드")
    public void sayHello(String name) {
        System.out.println("Hello, " + name + "! My name is " + this.name);
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
