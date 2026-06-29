package notify.firebase.kauesanchesg.controller;

import notify.firebase.kauesanchesg.dto.request.WarningRequest;
import notify.firebase.kauesanchesg.dto.response.NeighborhoodResponse;
import notify.firebase.kauesanchesg.dto.response.WarningResponse;
import notify.firebase.kauesanchesg.service.NeighborhoodService;
import notify.firebase.kauesanchesg.service.WarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class DefaultController {

    @Autowired
    private NeighborhoodService neighborhoodService;

    @Autowired
    private WarningsService warningsService;

    @GetMapping("/")
    public ResponseEntity<Void> getApi(){
        return ResponseEntity.ok().build();
    }

    @GetMapping("/warnings")
    public ResponseEntity<List<WarningResponse>> getWarnings(){
        return ResponseEntity.ok(warningsService.getAllWarnings());
    }

    @GetMapping("/neighborhood")
    public ResponseEntity<List<NeighborhoodResponse>> getNeighborhood(){
        return ResponseEntity.ok(neighborhoodService.getAllNeighborhood());
    }

    @PostMapping("/warning")
    public ResponseEntity<Void> createWarning(@RequestBody WarningRequest request){
        warningsService.createWarning(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
