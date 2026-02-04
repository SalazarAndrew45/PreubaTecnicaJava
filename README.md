# Prueba Técnica Java – Consumo PokeAPI

Proyecto Java que consume [PokeAPI](https://pokeapi.co/api/v2/pokemon/ditto) con estructura **modelo**, **servicio** y **main**, implementando **GET**, **POST** y **UPDATE**.

## Estructura

```
src/main/java/com/pokemon/
├── Main.java                 # Punto de entrada, ejemplos GET/POST/UPDATE
├── model/
│   └── Pokemon.java          # DTO según respuesta de PokeAPI
└── service/
    └── PokemonService.java   # GET (API), POST y UPDATE (local)
```

## Requisitos

- **Java 17+**
- **Maven 3.6+**


## API y operaciones

| Operación | Descripción |
|-----------|-------------|
| **GET** | Obtiene un Pokémon desde PokeAPI por nombre o id (ej. `ditto`, `132`). |
| **POST** | Añade un Pokémon al almacenamiento **local** (la API es de solo lectura). Puedes crear uno manual o guardar una copia de uno obtenido por GET. |
| **UPDATE** | Actualiza un Pokémon en el almacenamiento **local** por id o nombre. |

La API pública de PokeAPI no permite POST ni UPDATE, por eso el servicio mantiene un almacenamiento local en memoria para esas operaciones.

## Uso desde código

```java
PokemonService service = new PokemonService();

// GET desde API
Optional<Pokemon> p = service.getFromApi("ditto");

// POST (guardar en local)
Pokemon nuevo = new Pokemon(0, "MiPokemon", 5, 60);
service.post(nuevo);
// o guardar una copia de uno de la API:
service.postFromApi("ditto");

// UPDATE (solo en local)
service.update(id, pokemonActualizado);
```
