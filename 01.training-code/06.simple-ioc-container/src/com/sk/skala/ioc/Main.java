package com.sk.skala.ioc;

import com.sk.skala.ioc.container.SimpleIocContainer;
import com.sk.skala.ioc.service.TestService;
import com.sk.skala.ioc.service.UserService;

public class Main {
    public static void main(String[] args) {
        SimpleIocContainer container = new SimpleIocContainer();
        container.register(UserService.class);
        container.register(TestService.class);

        System.out.println();
        UserService userService = container.getBean(UserService.class);
        String result = userService.getUser("홍길동");
        System.out.println("  " + result);

        System.out.println();
        TestService testService = container.getBean(TestService.class);
        String testResult = testService.runTest("홍길동");
        System.out.println("  " + testResult);

        // 같은 타입을 다시 요청해도 동일한 인스턴스(싱글톤)가 반환되는지 확인
        System.out.println();
        System.out.println("싱글톤 확인: " + (userService == container.getBean(UserService.class)));
    }
}
