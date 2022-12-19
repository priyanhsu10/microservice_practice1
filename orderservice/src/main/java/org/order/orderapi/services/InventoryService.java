package org.order.orderapi.services;

import lombok.RequiredArgsConstructor;
import org.order.orderapi.dtos.InventoryCheckDto;
import org.order.orderapi.dtos.InventoryItemDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final WebClient.Builder webClientBuilder;
    private String baseUri = "http://inventory-service/api/inventory";

    public List<InventoryCheckDto> checkInventory(List<InventoryItemDto> inventoryItemDtos) {

        List<InventoryCheckDto> items = webClientBuilder.build().post()
                .uri(baseUri + "/check")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(inventoryItemDtos), new ParameterizedTypeReference<List<InventoryItemDto>>() {
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<InventoryCheckDto>>() {
                })
                .block();
        return items;

    }
}
