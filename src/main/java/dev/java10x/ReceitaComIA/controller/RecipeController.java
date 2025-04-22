package dev.java10x.ReceitaComIA.controller;

import dev.java10x.ReceitaComIA.model.FoodItem;
import dev.java10x.ReceitaComIA.service.ChatGptService;
import dev.java10x.ReceitaComIA.service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/generate")
public class RecipeController {

    private ChatGptService chatGptService;
    private FoodItemService foodItemService;

    public RecipeController(ChatGptService chatGptService, FoodItemService foodItemService) {
        this.chatGptService = chatGptService;
        this.foodItemService = foodItemService;
    }

    @GetMapping("/food")
    public Mono<ResponseEntity<String>> generateRecipe() {
        List<FoodItem> foodItems = foodItemService.listar();
        return chatGptService.generateRecipe(foodItems)
                .map(recipe -> ResponseEntity.ok(recipe))
                .defaultIfEmpty(ResponseEntity.noContent().build());

    }
}