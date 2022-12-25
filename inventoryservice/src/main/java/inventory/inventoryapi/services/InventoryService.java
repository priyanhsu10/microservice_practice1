package inventory.inventoryapi.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inventory.inventoryapi.dtos.InventoryCheckDto;
import inventory.inventoryapi.dtos.InventoryItemDto;
import inventory.inventoryapi.models.InventoryItem;
import inventory.inventoryapi.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;
    private final ObjectMapper mapper;

    @Transactional
    public String create(InventoryItemDto inventoryItemDto) {

        var item = InventoryItem.builder()
                .skuCode(inventoryItemDto.getSkuCode())
                .quantity(inventoryItemDto.getQuantity())
                .build();

        inventoryRepository.save(item);

        return "inventory item create id :" + item.getId();
    }

    public boolean updateInventory(InventoryItemDto inventoryItemDto) {

        var optItem = inventoryRepository.findAllBySkuCode(inventoryItemDto.getSkuCode());

        if (optItem.isEmpty()) {
            return false;
        }
        InventoryItem inventoryItem = optItem.get();

        //this function will add or subtract quantity
        inventoryItem.setQuantity(inventoryItem.getQuantity() + inventoryItemDto.getQuantity());
        inventoryRepository.save(inventoryItem);
        return true;
    }

    public List<InventoryCheckDto> checkInventory(List<InventoryItemDto> inventoryItems) throws JsonProcessingException {

        List<String> skuCodes = inventoryItems.stream().map(x -> x.getSkuCode()).collect(Collectors.toList());
        List<InventoryItem> items = inventoryRepository.findAllBySkuCodeIn(skuCodes);
        var result = new ArrayList<InventoryCheckDto>();
        for (InventoryItemDto item : inventoryItems) {
            Optional<InventoryItem> envt = items.stream().filter(x -> x.getSkuCode().equals(item.getSkuCode())).findFirst();
            if (envt.isEmpty()) {
                //create enventory or throw exception
                log.warn("inventory skucode  not found " + item.getSkuCode());
                result.add(  new InventoryCheckDto(item.getSkuCode(),false));
            } else {

                InventoryItem inventoryItem = envt.get();
                result.add(new InventoryCheckDto(inventoryItem.getSkuCode(), inventoryItem.getQuantity() >= item.getQuantity()));
            }

        }
        log.info("inventory check items :" +mapper.writeValueAsString(result));
        return result;
    }
}
