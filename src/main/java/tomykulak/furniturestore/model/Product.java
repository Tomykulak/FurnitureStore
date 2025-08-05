package tomykulak.furniturestore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @NonNull
    private Integer item_id;
    private String name;
    private String category;
    private BigDecimal price;
    private BigDecimal old_price;
    private Boolean sellable_online;
    private String link;
    private boolean other_colors;
    private String short_description;
    private String designer;
    private Integer depth;
    private Integer height;
    private Integer width;
}