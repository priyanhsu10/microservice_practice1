package inventory.inventoryapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(schema = "public",name = "tbl_inventoryitem")
public class InventoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id ;
    private  String skuCode;
    private  int quantity;
}
