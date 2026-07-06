package br.com.kasag.oraklast.controller;

import br.com.kasag.oraklast.service.RTDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SyncController {

    @Autowired
    private RTDBService service;

    @GetMapping("/sync")
    public ResponseEntity<List> updateFirebaseRTDB() {
        return ResponseEntity.ok(service.testCall());
    }
}
