public class UserController {
    public void createUser(@ParamName("userName") String name, @ParamName("userAge") int age) {
        System.out.println(name + ", " + age + "세 사용자를 생성합니다.");
    }
}
