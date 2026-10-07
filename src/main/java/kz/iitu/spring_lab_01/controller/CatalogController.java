package kz.iitu.spring_lab_01.controller;

import kz.iitu.spring_lab_01.aspect.SlowCallInfo;
import kz.iitu.spring_lab_01.aspect.SlowMethodAspect;
import kz.iitu.spring_lab_01.service.CatalogService;
import org.springframework.aop.support.AopUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/lab4")
public class CatalogController {

    private final CatalogService catalogService;
    private final SlowMethodAspect slowMethodAspect;

    // Один единственный конструктор со всеми зависимостями
    public CatalogController(CatalogService catalogService, SlowMethodAspect slowMethodAspect) {
        this.catalogService = catalogService;
        this.slowMethodAspect = slowMethodAspect;
    }

    @GetMapping("/item/{id}")
    public String getItem(@PathVariable long id) {
        return catalogService.findById(id);
    }

    @GetMapping("/items")
    public List<String> getItems(@RequestParam(defaultValue = "5") int limit) {
        return catalogService.findAll(limit);
    }

    @DeleteMapping("/item/{id}")
    public String deleteItem(@PathVariable long id) {
        return catalogService.remove(id);
    }

    @GetMapping("/proxy")
    public Map<String, String> proxyInfo() {
        return Map.of(
            "className",  catalogService.getClass().getName(),
            "superClass", catalogService.getClass().getSuperclass().getSimpleName(),
            "isAopProxy", String.valueOf(AopUtils.isAopProxy(catalogService)),
            "isCglib",    String.valueOf(AopUtils.isCglibProxy(catalogService))
        );
    }

    @GetMapping("/remove-twice/{id}")
    public String removeTwice(@PathVariable long id) {
        return catalogService.removeTwice(id);
    }

    @GetMapping("/slow-calls")
    public List<SlowCallInfo> getSlowCalls() {
        return slowMethodAspect.getSlowCalls();
    }
}