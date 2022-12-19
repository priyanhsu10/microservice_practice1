package inventory.inventoryapi.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryItemDtoResponse {
    private int id;
    private String skuCode;
    private int quantity;

}
