package br.com.kasag.oraklast.controller;

import br.com.kasag.oraklast.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class SyncController {

    @Autowired
    private SyncService service;

    @GetMapping("/sync")
    public ResponseEntity<Map<String, Object>> updateFirebaseRTDB() {
        return ResponseEntity.ok(service.emitSyncEvent());
    }
}
