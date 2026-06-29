package notify.firebase.kauesanchesg.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import notify.firebase.kauesanchesg.domain.enums.Quota;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "warnings")
public class WarningsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "neighborhood_id", nullable = false)
    private NeighborhoodEntity neighborhood;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(name = "quota", nullable = false)
    private Quota quota;
}
