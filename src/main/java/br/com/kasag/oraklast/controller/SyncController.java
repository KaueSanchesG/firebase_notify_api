package br.com.kasag.oraklast.controller;

import br.com.kasag.oraklast.dto.OpenMeteoResponseDTO;
import br.com.kasag.oraklast.service.SyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class SyncController {

    @Autowired
    private SyncService service;

    @GetMapping("/sync")
    public ResponseEntity<List<OpenMeteoResponseDTO>> updateFirebaseRTDB() {
        return ResponseEntity.ok(service.emitSyncEvent());
    }

    /**
     * Used 4 history data metrics only (and once while developing)
     */
//    @GetMapping("/history")
//    public ResponseEntity<?> historyData(){
//        service.calcHistMetrics();
//        return ResponseEntity.ok("guess it works D:");
//    }
}
