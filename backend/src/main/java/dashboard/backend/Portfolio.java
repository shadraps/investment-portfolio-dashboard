package dashboard.backend;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String name;
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    List<Holding> holdings;
}
