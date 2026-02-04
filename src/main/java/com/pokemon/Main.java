package com.pokemon;

import com.pokemon.model.Pokemon;
import com.pokemon.service.PokemonService;

import java.util.Optional;

/**
 * Main: demuestra GET, POST y UPDATE contra PokeAPI y almacenamiento local.
 */
public class Main {

    public static void main(String[] args) {
        PokemonService service = new PokemonService();

        System.out.println("=== GET desde PokeAPI (ditto) ===");
        Optional<Pokemon> ditto = service.getFromApi("ditto");
        ditto.ifPresentOrElse(
                p -> System.out.println("OK: " + p + " | Experiencia base: " + p.getBaseExperience()),
                () -> System.out.println("No encontrado")
        );

        System.out.println("\n=== POST: guardar en local (copia de ditto) ===");
        ditto.ifPresent(p -> {
            Pokemon guardado = service.postFromApi("ditto").orElseThrow();
            System.out.println("Guardado en local con id: " + guardado.getId());
        });

        System.out.println("\n=== POST: crear Pokémon manual y guardar ===");
        Pokemon custom = new Pokemon(0, "CustomMon", 5, 60);
        Pokemon creado = service.post(custom);
        System.out.println("Creado: " + creado);

        System.out.println("\n=== GET (busca en API y en local) ===");
        service.get("ditto").ifPresent(p -> System.out.println("ditto: " + p));
        service.get(String.valueOf(creado.getId())).ifPresent(p -> System.out.println("local: " + p));

        System.out.println("\n=== UPDATE: modificar Pokémon en local ===");
        Pokemon actualizado = new Pokemon(creado.getId(), "CustomMonV2", 10, 120);
        service.update(creado.getId(), actualizado).ifPresent(p ->
                System.out.println("Actualizado: " + p)
        );

        System.out.println("\n=== Listado local ===");
        service.listLocal().forEach(p -> System.out.println("  " + p));
    }
}
