package inventory.inventoryapi.repositories;

import inventory.inventoryapi.models.InventoryItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends CrudRepository<InventoryItem,Integer> {
    Optional<InventoryItem> findAllBySkuCode(String skuCode);

    List<InventoryItem> findAllBySkuCodeIn(List<String> skucodes);
}
