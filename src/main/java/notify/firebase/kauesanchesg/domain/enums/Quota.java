package notify.firebase.kauesanchesg.domain.enums;

import lombok.Getter;

@Getter
public enum Quota {
    MINOR("Alerta"),
    MODERATE("Enchente"),
    MAJOR("Inundação");

    private final String description;

    Quota(String description) {
        this.description = description;
    }
}
