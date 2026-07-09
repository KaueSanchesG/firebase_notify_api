package br.com.kasag.oraklast.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class Firebase {

    private final String dbId = "oraklast";

    @Bean
    public Firestore firestore() throws IOException {

        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();

        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);
        }

        FirestoreOptions firestoreOptions = FirestoreOptions.newBuilder()
                .setCredentials(credentials)
                .setDatabaseId(dbId)
                .build();

        return firestoreOptions.getService();
    }
}
