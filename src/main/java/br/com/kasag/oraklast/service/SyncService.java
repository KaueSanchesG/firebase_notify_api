package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.OpenMeteoResponseDTO;
import br.com.kasag.oraklast.dto.PointModelDTO;
import br.com.kasag.oraklast.dto.RTDBDailyModelDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.com.kasag.oraklast.utils.RTDBHashMapping.toHash;

@Service
public class SyncService {

    @Autowired
    private RTDBService rtdbService;

    @Autowired
    private FCMService fcmService;

    @Autowired
    private OpenMeteoService meteoService;

    /**
     * / Points around triple border among Brasil, Paraguay, Argentina
     * /
     * / @content pN stands for Paraguay points
     * / @content BN stands for Brasil points
     * / @content aN stands for Argentina points
     */
    private final List<PointModelDTO> points = List.of(
            new PointModelDTO("p1", -25.44873110009506, -54.64848707106452),
            new PointModelDTO("p2", -25.46604073933723, -54.66875376662977),
            new PointModelDTO("p3", -25.45214016036795, -54.67424932091286),
            new PointModelDTO("p4", -25.41529268908153, -54.68116785986069),
            new PointModelDTO("p5", -25.40715364143828, -54.68480172394786),
            new PointModelDTO("p6", -25.41040254753702, -54.69399177757399),
            new PointModelDTO("b1", -25.47766237391046, -54.60401165565593),
            new PointModelDTO("b2", -25.49662535912696, -54.61877065024706),
            new PointModelDTO("b3", -25.53541957371173, -54.62202404347496),
            new PointModelDTO("b4", -25.58028211779522, -54.58896119364636),
            new PointModelDTO("a1", -25.89848504820023, -54.54347205392787),
            new PointModelDTO("a2", -25.6059442462042, -54.58227626429527),
            new PointModelDTO("a3", -25.6562983474533, -54.58097986624939)
    );

    private final String lats = points.stream()
            .map(p -> String.valueOf(p.latitude()))
            .collect(Collectors.joining(","));

    private final String lngs = points.stream()
            .map(p -> String.valueOf(p.longitude()))
            .collect(Collectors.joining(","));

    public Map<String, Object> emitSyncEvent() {
        List<OpenMeteoResponseDTO> meteoResponse = meteoService.doForecast(lats, lngs);

        Map<String, Object> payload = toHash(points, meteoResponse);

        return payload;
    }

}
