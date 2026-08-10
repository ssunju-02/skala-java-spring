package com.sk.skala.myapp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AsyncService {

    // 기본 SimpleAsyncTaskExecutor 사용: 반환값 없음
    @Async
    public void asyncMethodWithoutReturn(String message) {
        try {
            Thread.sleep(2000); // 2초 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("비동기 메서드 완료 - Thread: {}, Message: {}",
                Thread.currentThread().getName(), message);
    }

    // 커스텀 ThreadPoolTaskExecutor(taskExecutor) 사용: CompletableFuture 반환
    @Async("taskExecutor")
    public CompletableFuture<String> asyncMethodWithReturn(String message) {
        try {
            Thread.sleep(3000); // 3초 대기
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String result = "처리 완료: " + message;
        log.info("비동기 메서드(반환값) 완료 - Thread: {}, Result: {}",
                Thread.currentThread().getName(), result);
        return CompletableFuture.completedFuture(result);
    }
}
