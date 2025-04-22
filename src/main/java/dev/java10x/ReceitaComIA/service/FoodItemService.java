package dev.java10x.ReceitaComIA.service;

import dev.java10x.ReceitaComIA.model.FoodItem;
import dev.java10x.ReceitaComIA.repository.FoodItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class FoodItemService {

    private FoodItemRepository repository;

    public FoodItemService(FoodItemRepository repository) {
        this.repository = repository;
    }

    // salvar
    public FoodItem salvar(FoodItem food){
        return repository.save(food);
    }

    // listar
    public List<FoodItem> listar(){
        return repository.findAll();
    }

    // listar por id
    public Optional<FoodItem> listarPorId(Long id){
        return repository.findById(id);
    }

    // deletar
    public void deletar(Long id){
        repository.deleteById(id);
    }

    public FoodItem atualizar(Long id, FoodItem novo) {
        FoodItem existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item com ID " + id + " não encontrado."));

        if (novo.getNome() != null) {
            existente.setNome(novo.getNome());
        }
        if (novo.getCategoria() != null) {
            existente.setCategoria(novo.getCategoria());
        }
        if (novo.getQuantidade() != null) {
            existente.setQuantidade(novo.getQuantidade());
        }
        if (novo.getValidade() != null) {
            existente.setValidade(novo.getValidade());
        }

        return repository.save(existente);
    }



}
