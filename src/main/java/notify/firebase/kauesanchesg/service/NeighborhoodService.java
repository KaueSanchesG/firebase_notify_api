package notify.firebase.kauesanchesg.service;

import notify.firebase.kauesanchesg.domain.entity.NeighborhoodEntity;
import notify.firebase.kauesanchesg.dto.response.NeighborhoodResponse;
import notify.firebase.kauesanchesg.mapper.NeighborhoodMapper;
import notify.firebase.kauesanchesg.repository.NeighborhoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeighborhoodService {

    @Autowired
    private NeighborhoodRepository repository;

    @Autowired
    private NeighborhoodMapper mapper;

    public List<NeighborhoodResponse> getAllNeighborhood(){
        List<NeighborhoodEntity> neighborhoodEntities = repository.findAll();

        return mapper.toDTOList(neighborhoodEntities);
    }
}
