package de.derfrzocker.no.gilded.blackstone;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.structure.Structure;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

public class NoGildedBlackstone extends JavaPlugin {

    private static final NoGildedBlackstoneTransformer TRANSFORMER = new NoGildedBlackstoneTransformer();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        Bukkit.getPluginManager().registerEvents(new StructureListener(createGenerationInfo()), this);
    }

    private GenerationInfo createGenerationInfo() {
        ConfigurationSection section = createConfigurationSection();

        NamespacedKey key = getNamespaceKey(section);
        Set<Structure> structures = getStructures(section);
        ListType listType = getListType(section);
        Set<String> worlds = new HashSet<>(section.getStringList("worlds"));

        return new GenerationInfo(key, TRANSFORMER, structures, listType, worlds);
    }

    private ConfigurationSection createConfigurationSection() {
        return YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
    }

    private NamespacedKey getNamespaceKey(ConfigurationSection section) {
        String key = section.getString("transformer-key", "remove-gilded-blackstone");
        NamespacedKey namespacedKey = NamespacedKey.fromString(key, this);

        if (namespacedKey == null) {
            throw new IllegalArgumentException(String.format("Value '%s' in config for 'transformer-key' is not a valid namespace key", key));
        }

        return namespacedKey;
    }

    private Set<Structure> getStructures(ConfigurationSection section) {
        List<String> strings = section.getStringList("structures");

        if (strings.isEmpty()) {
            return Collections.emptySet();
        }

        return strings
                .stream()
                .map(this::getStructure)
                .collect(Collectors.toSet());
    }

    private Structure getStructure(String key) {
        NamespacedKey namespacedKey = NamespacedKey.fromString(key);

        if (namespacedKey == null) {
            throw new IllegalArgumentException(String.format("Value '%s' in config for 'structures' is not a valid namespace", key));
        }

        Structure structure = Registry.STRUCTURE.get(namespacedKey);

        if (structure == null) {
            throw new IllegalArgumentException(String.format("No structure found for value '%s'", namespacedKey));
        }

        return structure;
    }

    private ListType getListType(ConfigurationSection section) {
        String listType = section.getString("worlds-list-type", "null");
        try {
            return ListType.valueOf(listType.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException(String.format("Value '%s' in config for 'worlds-list-type' is not valid", listType));
        }
    }
}
