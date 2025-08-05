package tomykulak.furniturestore.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Product {
    @NonNull
    Integer item_id;
    String name;
    String category;
    Float price;
    Float old_price;
    boolean sellable_online;
    String link;
    boolean other_colors;
    String short_description;
    String designer;
    Integer depth;
    Integer height;
    Integer width;
}