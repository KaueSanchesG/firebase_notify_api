package br.com.kasag.oraklast.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class Firebase {

    @Value("${firebase.credentials.path}")
    private String credentialsPath;

    @PostConstruct
    public void initializeFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream(credentialsPath);

                FirebaseOptions op = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();

                FirebaseApp.initializeApp(op);

                DatabaseReference rf = FirebaseDatabase.getInstance()
                        .getReference("restricted_access/secret_doc");
                rf.addListenerForSingleValueEvent(new ValueEventListener(){
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot){
                        Object doc = dataSnapshot.getValue();
                        System.out.println(doc);
                    }

                    @Override
                    public void onCancelled(DatabaseError error){

                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar a conexão com o firebase", e);
        }
    }
}
