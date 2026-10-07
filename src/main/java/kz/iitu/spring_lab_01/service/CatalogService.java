package kz.iitu.spring_lab_01.service;

import kz.iitu.spring_lab_01.audit.Audited;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {

    public String findById(long id) {
        return "Item #" + id;
    }

    @Audited(action = "CATALOG_LIST", logArguments = true)
    public List<String> findAll(int limit) {
        // Искусственная задержка для срабатывания SlowMethodAspect
        try {
            Thread.sleep(400); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<String> items = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            items.add("Item #" + i);
        }
        return items;
    }

    @Audited(action = "CATALOG_REMOVE")
    public String remove(long id) {
        return "Item #" + id + " deleted";
    }

    @Autowired
    @Lazy
    private CatalogService self; 

    public String removeTwice(long id) {
        String first  = self.remove(id);     // вызов ЧЕРЕЗ ПРОКСИ self
        String second = self.remove(id + 1); // вызов ЧЕРЕЗ ПРОКСИ self
        return first + "; " + second;
    }
}