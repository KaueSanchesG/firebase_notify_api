package br.com.kasag.oraklast.service;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;


import java.util.Map;

@Service
public class RTDBService {

    private final ObjectMapper objectMapper;

    public RTDBService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static DatabaseReference getRef(){
        return FirebaseDatabase.getInstance()
                .getReference("oraklast_db");
    }

    public void updateRTDB(Map<String, Object> payload){
        Map<String, Object> trustedPayload = objectMapper.convertValue(
                payload,
                new TypeReference<Map<String, Object>>() {
                }
        );

        getRef().updateChildrenAsync(trustedPayload);
    }
}
