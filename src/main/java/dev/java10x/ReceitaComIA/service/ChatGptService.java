package dev.java10x.ReceitaComIA.service;

import dev.java10x.ReceitaComIA.model.FoodItem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatGptService {

    private final WebClient webClient;

    private String apiKey = System.getenv("api_key");

    public ChatGptService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> generateRecipe(List<FoodItem> foodItems) {

        String alimentos = foodItems.stream()
                .map(item -> String.format("%s - Quantidade: %s, Validade: %s",
                        item.getNome(), item.getQuantidade(), item.getValidade()))
                .collect(Collectors.joining("\n"));

        String prompt = "Faça uma receita com os seguintes itens:\n" + alimentos;

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", List.of(
                        Map.of("role", "system", "content", "Você é um assistente que cria receitas."),
                        Map.of("role", "user", "content", prompt)
                )
        );

        return webClient.post()
                .uri("https://api.openai.com/v1/chat/completions")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                    if (choices != null && !choices.isEmpty()) {
                        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                        return message.get("content").toString();
                    }
                    return "Nenhuma receita foi gerada.";
                });
    }
}