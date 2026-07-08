package br.com.kasag.oraklast.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
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
                        .setDatabaseUrl("https://fcm-flood-monitoring-default-rtdb.firebaseio.com/")
                        .build();

                FirebaseApp.initializeApp(op);

            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar a conexão com o firebase", e);
        }
    }
}
