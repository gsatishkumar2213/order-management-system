package org.example.kafka.inventoryservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @GetMapping("/{user}")
    public ResponseEntity<String> helloWorld(@PathVariable String user) {
        return ResponseEntity.ok("Welcome to Inventory: " + user);
    }
}
