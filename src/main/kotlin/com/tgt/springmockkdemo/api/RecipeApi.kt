package com.tgt.springmockkdemo.api

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class RecipeApi(@Qualifier(value = "recipeApi") val webClient: WebClient) {
    fun getRecipe(name: String): Recipe {
        return webClient.get()
                .uri("/recipe")
                .retrieve()
                .bodyToMono(Recipe::class.java)
                .block()!!
    }
}

data class Recipe(val name: String, val ingredients: List<Ingredient>)

data class Ingredient(val name: String, val quantity: Int)