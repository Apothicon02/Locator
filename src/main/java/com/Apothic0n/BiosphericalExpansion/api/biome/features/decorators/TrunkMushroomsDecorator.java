package com.Apothic0n.BiosphericalExpansion.api.biome.features.decorators;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.RegistryObject;

import static com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks.*;

public class TrunkMushroomsDecorator extends TreeDecorator {
    public static final Codec<TrunkMushroomsDecorator> CODEC = Codec.floatRange(0.0f, 1.0f).fieldOf("probability").xmap(TrunkMushroomsDecorator::new, trunkMushroomsDecorator -> Float.valueOf(trunkMushroomsDecorator.probability)).codec();
    private final float probability;

    public TrunkMushroomsDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return BioxTreeDecoratorType.TRUNK_MUSHROOMS.get();
    }

    @Override
    public void place(Context context) {
        LevelSimulatedReader level = context.level();
        RandomSource randomSource = context.random();
        ObjectArrayList<BlockPos> list = context.roots();
        if (randomSource.nextFloat() >= this.probability) {
            return;
        }
        if (list.isEmpty()) {
            list = ObjectArrayList.of(context.logs().get(0), context.logs().get(1), context.logs().get(2), context.logs().get(3), context.logs().get(4), context.logs().get(5), context.logs().get(6), context.logs().get(7), context.logs().get(8));
        }
        list.forEach(blockPos -> {
            int randomNumber = (int)(Math.random()*(42)+1);
            if (randomNumber < 2) {
                if (level.isStateAtPosition(blockPos.north(),  BlockBehaviour.BlockStateBase::canBeReplaced)) {
                    context.setBlock(blockPos.north(), randomMushroom());
                }
            } else if (randomNumber < 3) {
                if (level.isStateAtPosition(blockPos.east(),  BlockBehaviour.BlockStateBase::canBeReplaced)) {
                    context.setBlock(blockPos.east(), randomMushroom());
                }
            } else if (randomNumber < 4) {
                if (level.isStateAtPosition(blockPos.south(),  BlockBehaviour.BlockStateBase::canBeReplaced)) {
                    context.setBlock(blockPos.south(), randomMushroom());
                }
            } else if (randomNumber < 5) {
                if (level.isStateAtPosition(blockPos.west(),  BlockBehaviour.BlockStateBase::canBeReplaced)) {
                    context.setBlock(blockPos.west(), randomMushroom());
                }
            }
        });
    }

    private BlockState randomMushroom() {
        int randomNumber = (int)(Math.random()*(3)+1);
        if (randomNumber < 2) {
            for (int o = 0; o < slabBlocks.size(); o++) {
                RegistryObject<Block> block = slabBlocks.get(o).get(Blocks.RED_MUSHROOM_BLOCK);
                if (block != null) {
                    return block.get().defaultBlockState();
                }
            }
        }
        for (int o = 0; o < slabBlocks.size(); o++) {
            RegistryObject<Block> block = slabBlocks.get(o).get(Blocks.BROWN_MUSHROOM_BLOCK);
            if (block != null) {
                return block.get().defaultBlockState();
            }
        }
        return Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState(); //this means it failed
    }
}