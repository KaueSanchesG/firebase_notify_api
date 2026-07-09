package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.ForecastUnitedDataDTO;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {
    public FirestoreService(Firestore db) {
        this.db = db;
    }

    Firestore db;

    public void update(List<ForecastUnitedDataDTO> payload) throws ExecutionException, InterruptedException {
        for (ForecastUnitedDataDTO pointData : payload) {
            db.collection("points")
                    .document(pointData.pointId())
                    .set(pointData)
                    .get();
        }
    }
}
