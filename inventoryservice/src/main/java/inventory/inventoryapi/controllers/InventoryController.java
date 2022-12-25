package inventory.inventoryapi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import inventory.inventoryapi.dtos.InventoryCheckDto;
import inventory.inventoryapi.dtos.InventoryItemDto;
import inventory.inventoryapi.repositories.InventoryRepository;
import inventory.inventoryapi.services.InventoryService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;
private  final ObjectMapper mapper;
    @PostMapping("/check")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryCheckDto> checkInventory(@RequestBody List<InventoryItemDto> inventoryItemDtos) throws JsonProcessingException {
      log.info("checking inventory for items:"+ mapper.writeValueAsString(inventoryItemDtos));
        return inventoryService.checkInventory(inventoryItemDtos);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody InventoryItemDto inventoryItemDto) {

        return inventoryService.create(inventoryItemDto);
    }

    @PutMapping
    public String update(@RequestBody InventoryItemDto inventoryItemDto) {
        return inventoryService.updateInventory(inventoryItemDto) ? "inventory updated successfully" : "inventory update failed , sku code not present.create inventory item and then update";
    }
}
