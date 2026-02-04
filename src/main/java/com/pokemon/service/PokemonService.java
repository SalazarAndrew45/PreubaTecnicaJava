package com.pokemon.service;

import com.google.gson.Gson;
import com.pokemon.model.Pokemon;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Servicio para consumir PokeAPI (GET) y gestionar Pokémon en local (POST, UPDATE).
 * La API pública es de solo lectura, por eso POST y UPDATE operan sobre un almacenamiento local.
 */
public class PokemonService {

    private static final String POKEAPI_BASE = "https://pokeapi.co/api/v2/pokemon/";
    private static final int TIMEOUT_SECONDS = 10;

    private final HttpClient httpClient;
    private final Gson gson;

    /** Almacenamiento local para POST y UPDATE (la API no permite escritura) */
    private final ConcurrentHashMap<Integer, Pokemon> localStore = new ConcurrentHashMap<>();
    private final AtomicInteger nextLocalId = new AtomicInteger(10000);

    public PokemonService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
        this.gson = new Gson();
    }

    /**
     * GET: Obtiene un Pokémon desde PokeAPI por nombre o id.
     */
    public Optional<Pokemon> getFromApi(String nameOrId) {
        String url = POKEAPI_BASE + nameOrId.trim().toLowerCase();
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return Optional.empty();
            }
            Pokemon pokemon = gson.fromJson(response.body(), Pokemon.class);
            return Optional.of(pokemon);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener Pokémon desde API: " + e.getMessage(), e);
        }
    }

    /**
     * GET: Busca primero en la API; si no existe, busca en el almacenamiento local.
     */
    public Optional<Pokemon> get(String nameOrId) {
        Optional<Pokemon> fromApi = getFromApi(nameOrId);
        if (fromApi.isPresent()) {
            return fromApi;
        }
        try {
            int id = Integer.parseInt(nameOrId);
            return Optional.ofNullable(localStore.get(id));
        } catch (NumberFormatException e) {
            return localStore.values().stream()
                    .filter(p -> nameOrId.equalsIgnoreCase(p.getName()))
                    .findFirst();
        }
    }

    /**
     * POST: Añade un Pokémon al almacenamiento local.
     * Si se pasa un Pokémon obtenido de la API, se guarda una copia con id local.
     */
    public Pokemon post(Pokemon pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon no puede ser null");
        }
        int id = nextLocalId.getAndIncrement();
        pokemon.setId(id);
        localStore.put(id, pokemon);
        return pokemon;
    }

    /**
     * POST: Obtiene un Pokémon de la API por nombre y lo añade al almacenamiento local.
     */
    public Optional<Pokemon> postFromApi(String nameOrId) {
        return getFromApi(nameOrId).map(this::post);
    }

    /**
     * UPDATE: Actualiza un Pokémon en el almacenamiento local por id.
     */
    public Optional<Pokemon> update(int id, Pokemon pokemon) {
        if (pokemon == null) {
            throw new IllegalArgumentException("Pokemon no puede ser null");
        }
        if (!localStore.containsKey(id)) {
            return Optional.empty();
        }
        pokemon.setId(id);
        localStore.put(id, pokemon);
        return Optional.of(pokemon);
    }

    /**
     * UPDATE: Actualiza por nombre (busca en local y actualiza).
     */
    public Optional<Pokemon> updateByName(String name, Pokemon pokemon) {
        Optional<Integer> id = localStore.values().stream()
                .filter(p -> name.equalsIgnoreCase(p.getName()))
                .map(Pokemon::getId)
                .findFirst();
        return id.flatMap(i -> update(i, pokemon));
    }

    /**
     * Lista todos los Pokémon guardados localmente.
     */
    public List<Pokemon> listLocal() {
        return new ArrayList<>(localStore.values());
    }
}
