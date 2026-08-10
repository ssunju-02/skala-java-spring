package com.sk.skala.ioc.container;

import com.sk.skala.ioc.annotation.Service;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

// 순수 Reflection만으로 Spring IoC 컨테이너의 "생성자 주입 + 싱글톤 관리" 핵심 원리를 재현한다.
public class SimpleIocContainer {

    private final Map<Class<?>, Object> singletons = new HashMap<>();

    // @Service가 붙은 클래스만 Bean 후보로 등록 (실제 인스턴스 생성은 getBean 호출 시점에 지연)
    public void register(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Service.class)) {
            throw new IllegalArgumentException(clazz.getName() + "에는 @Service가 없습니다.");
        }
        System.out.println("[Bean 등록] " + clazz.getSimpleName());
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        if (singletons.containsKey(clazz)) {
            return (T) singletons.get(clazz);
        }
        try {
            Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Object[] args = new Object[parameterTypes.length];
            for (int i = 0; i < parameterTypes.length; i++) {
                // 생성자 파라미터 타입만 보고, 개발자 대신 컨테이너가 의존성을 재귀적으로 만들어 주입한다 (제어의 역전)
                args[i] = getBean(parameterTypes[i]);
            }
            Object instance = constructor.newInstance(args);
            singletons.put(clazz, instance);
            System.out.println("[Bean 생성] " + clazz.getSimpleName() + " (싱글톤으로 캐시)");
            return (T) instance;
        } catch (Exception e) {
            throw new RuntimeException("Bean 생성 실패: " + clazz.getName(), e);
        }
    }
}
