public class Main {
    public static void main(String[] args) {
        User user = new User.Builder()
                .setName("홍길동")
                .setAge(20)
                .setEmail("hong@skala.com")
                .setPhoneNumber("010-1234-5678")
                .build();

        System.out.println(user);
    }
}
