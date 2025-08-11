package tomykulak.furniturestore.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
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
