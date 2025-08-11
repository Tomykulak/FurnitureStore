package tomykulak.furniturestore.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String category;
    private BigDecimal price;
    private BigDecimal oldPrice;
    private Boolean sellableOnline;
    private String link;
    private Boolean otherColors;
    private String shortDescription;
    private String designer;
    private Integer depth;
    private Integer height;
    private Integer width;
}