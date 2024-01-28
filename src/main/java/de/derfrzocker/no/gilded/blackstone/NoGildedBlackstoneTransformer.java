package de.derfrzocker.no.gilded.blackstone;

import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.util.BlockTransformer;

public class NoGildedBlackstoneTransformer implements BlockTransformer {

    @Override
    public BlockState transform(LimitedRegion limitedRegion, int x, int y, int z, BlockState blockState, TransformationState transformationState) {
        if (blockState.getType() != Material.GILDED_BLACKSTONE) {
            return blockState;
        }

        return Material.BLACKSTONE.createBlockData().createBlockState();
    }
}
