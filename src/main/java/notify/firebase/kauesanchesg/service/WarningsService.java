package notify.firebase.kauesanchesg.service;

import notify.firebase.kauesanchesg.domain.entity.NeighborhoodEntity;
import notify.firebase.kauesanchesg.domain.entity.WarningsEntity;
import notify.firebase.kauesanchesg.dto.request.WarningRequest;
import notify.firebase.kauesanchesg.dto.response.WarningResponse;
import notify.firebase.kauesanchesg.mapper.WarningMapper;
import notify.firebase.kauesanchesg.repository.NeighborhoodRepository;
import notify.firebase.kauesanchesg.repository.WarningsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WarningsService {

    @Autowired
    private WarningsRepository repository;

    @Autowired
    private NeighborhoodRepository neighborhoodRepository;

    @Autowired
    private WarningMapper mapper;

    public List<WarningResponse> getAllWarnings(){
        List<WarningsEntity> warningsEntities = repository.findAll();

        return warningsEntities.stream()
                .map(e -> {
                    String message = "Notificação de " + e.getQuota().name() + " registrado para o bairro " + e.getNeighborhood().getName();
                    return new WarningResponse(
                            e.getId(),
                            message,
                            e.getTimestamp(),
                            e.getQuota().name()
                    );
                }).toList();
    }

    @Transactional
    public void createWarning(WarningRequest request){
        WarningsEntity entity = mapper.toEntity(request);
        NeighborhoodEntity neighborhood = neighborhoodRepository.getReferenceById(request.neighborhood());
        entity.setNeighborhood(neighborhood);

        repository.save(entity);
    }
}
