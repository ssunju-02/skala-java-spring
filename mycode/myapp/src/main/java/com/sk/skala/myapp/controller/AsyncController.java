package com.sk.skala.myapp.controller;

import com.sk.skala.myapp.aspect.Metrics;
import com.sk.skala.myapp.service.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api")
public class AsyncController {

    private final AsyncService asyncService;

    public AsyncController(AsyncService asyncService) {
        this.asyncService = asyncService;
    }

    // Non-Blocking: 즉시 CompletableFuture를 반환, 완료되면 응답
    @GetMapping("/future")
    @Metrics
    public CompletableFuture<String> callAsyncFuture(@RequestParam String message) {
        return asyncService.asyncMethodWithReturn(message);
    }

    // 반환값 없는 비동기 호출: 요청 스레드는 바로 응답
    @GetMapping("/void")
    @Metrics
    public String callAsyncVoid(@RequestParam String message) {
        asyncService.asyncMethodWithoutReturn(message);
        return "비동기 작업이 시작되었습니다. (반환값 없음)";
    }
}
