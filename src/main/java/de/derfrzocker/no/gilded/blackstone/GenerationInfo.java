package de.derfrzocker.no.gilded.blackstone;

import org.bukkit.NamespacedKey;
import org.bukkit.generator.structure.Structure;
import org.bukkit.util.BlockTransformer;

import java.util.Set;

public record GenerationInfo(NamespacedKey transformerKey, BlockTransformer transformer, Set<Structure> structures,
                             ListType listType, Set<String> worlds) {
}
