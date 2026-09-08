package dashboard.backend;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class Portfolio {
  String name;

  @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
  List<Holding> holdings;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
