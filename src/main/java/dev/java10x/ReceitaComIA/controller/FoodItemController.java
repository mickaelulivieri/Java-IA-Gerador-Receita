package dev.java10x.ReceitaComIA.controller;

import dev.java10x.ReceitaComIA.model.FoodItem;
import dev.java10x.ReceitaComIA.service.FoodItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/food")
public class FoodItemController {

    private final FoodItemService service;

    public FoodItemController(FoodItemService service) {
        this.service = service;
    }

    // Criar comida
    @PostMapping
    public ResponseEntity<FoodItem> criar(@RequestBody FoodItem foodItem) {
        FoodItem salvo = service.salvar(foodItem);
        return ResponseEntity.ok(salvo);  // Correção aqui
    }

    // Listar todas as comidas
    @GetMapping("/listar")
    public ResponseEntity<List<FoodItem>> foodList() {
        List<FoodItem> lista = service.listar();
        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();  // Adicionando verificação para lista vazia
        }
        return ResponseEntity.ok(lista);
    }

    // Procurar por id
    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> procurarPorId(@PathVariable Long id) {
        Optional<FoodItem> foodItem = service.listarPorId(id);
        return foodItem.map(item -> ResponseEntity.ok(item))
                .orElseGet(() -> ResponseEntity.notFound().build());  // Adicionando resposta para item não encontrado
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Atualizar
    @PutMapping("/atualizar/{id}")  // Alterado para PUT caso você queira substituir completamente o recurso
    public ResponseEntity<FoodItem> atualizar(@PathVariable Long id, @RequestBody FoodItem newFood) {
        FoodItem atualizado = service.atualizar(id, newFood);
        return ResponseEntity.ok(atualizado);
    }
}