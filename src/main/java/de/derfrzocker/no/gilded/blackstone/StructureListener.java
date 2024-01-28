package de.derfrzocker.no.gilded.blackstone;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.AsyncStructureGenerateEvent;

public class StructureListener implements Listener {

    private final GenerationInfo generationInfo;

    public StructureListener(GenerationInfo generationInfo) {
        this.generationInfo = generationInfo;
    }

    @EventHandler
    public void onStructureGenerate(AsyncStructureGenerateEvent event) {
        if (!generationInfo.structures().contains(event.getStructure())) {
            return;
        }

        if (generationInfo.worlds().contains(event.getWorld().getName())) {
            if (generationInfo.listType() == ListType.DENY) {
                return;
            }
        } else {
            if (generationInfo.listType() == ListType.ALLOW) {
                return;
            }
        }

        event.setBlockTransformer(generationInfo.transformerKey(), generationInfo.transformer());
    }
}
