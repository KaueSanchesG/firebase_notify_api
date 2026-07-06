package br.com.kasag.oraklast.service;

import br.com.kasag.oraklast.dto.OpenMeteoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RTDBService {

    @Autowired
    private OpenMeteoService meteoService;

    private String lats = "-25.45848579898471,-25.4600668475067";
    private String lngs = "-54.6645158906254,-54.61555417489907";

    public List<OpenMeteoResponseDTO> testCall() {
        return meteoService.doForecast(lats, lngs);
    }
}
