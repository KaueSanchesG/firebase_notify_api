package notify.firebase.kauesanchesg.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "quota", nullable = false)
    private Quota quota;
}
