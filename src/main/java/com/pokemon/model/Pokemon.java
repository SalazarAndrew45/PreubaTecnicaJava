package com.pokemon.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Modelo que representa un Pokémon según la respuesta de PokeAPI.
 */
public class Pokemon {

    private int id;
    private String name;
    private int height;
    private int weight;
    @SerializedName("base_experience")
    private int baseExperience;
    private List<AbilitySlot> abilities;
    private List<TypeSlot> types;
    private List<StatValue> stats;
    private Sprites sprites;

    public Pokemon() {}

    public Pokemon(int id, String name, int height, int weight) {
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    // --- Getters y Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }

    public int getWeight() { return weight; }
    public void setWeight(int weight) { this.weight = weight; }

    public int getBaseExperience() { return baseExperience; }
    public void setBaseExperience(int baseExperience) { this.baseExperience = baseExperience; }

    public List<AbilitySlot> getAbilities() { return abilities; }
    public void setAbilities(List<AbilitySlot> abilities) { this.abilities = abilities; }

    public List<TypeSlot> getTypes() { return types; }
    public void setTypes(List<TypeSlot> types) { this.types = types; }

    public List<StatValue> getStats() { return stats; }
    public void setStats(List<StatValue> stats) { this.stats = stats; }

    public Sprites getSprites() { return sprites; }
    public void setSprites(Sprites sprites) { this.sprites = sprites; }

    @Override
    public String toString() {
        return "Pokemon{id=" + id + ", name='" + name + "', height=" + height + ", weight=" + weight + "}";
    }

    // --- Clases anidadas para la respuesta JSON ---

    public static class AbilitySlot {
        private Ability ability;
        @SerializedName("is_hidden")
        private boolean hidden;
        private int slot;

        public Ability getAbility() { return ability; }
        public void setAbility(Ability ability) { this.ability = ability; }
        public boolean isHidden() { return hidden; }
        public void setHidden(boolean hidden) { this.hidden = hidden; }
        public int getSlot() { return slot; }
        public void setSlot(int slot) { this.slot = slot; }
    }

    public static class Ability {
        private String name;
        private String url;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    public static class TypeSlot {
        private int slot;
        private Type type;
        public int getSlot() { return slot; }
        public void setSlot(int slot) { this.slot = slot; }
        public Type getType() { return type; }
        public void setType(Type type) { this.type = type; }
    }

    public static class Type {
        private String name;
        private String url;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    public static class StatValue {
        @SerializedName("base_stat")
        private int baseStat;
        private int effort;
        private Stat stat;
        public int getBaseStat() { return baseStat; }
        public void setBaseStat(int baseStat) { this.baseStat = baseStat; }
        public int getEffort() { return effort; }
        public void setEffort(int effort) { this.effort = effort; }
        public Stat getStat() { return stat; }
        public void setStat(Stat stat) { this.stat = stat; }
    }

    public static class Stat {
        private String name;
        private String url;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }

    public static class Sprites {
        @SerializedName("front_default")
        private String frontDefault;
        @SerializedName("front_shiny")
        private String frontShiny;
        public String getFrontDefault() { return frontDefault; }
        public void setFrontDefault(String frontDefault) { this.frontDefault = frontDefault; }
        public String getFrontShiny() { return frontShiny; }
        public void setFrontShiny(String frontShiny) { this.frontShiny = frontShiny; }
    }
}
