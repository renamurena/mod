package com.example.compactspeedctrl;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CompactSpeedCtrlBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, CompactSpeedCtrlMod.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CompactSpeedControllerBlockEntity>> COMPACT_SPEED_CONTROLLER =
            BLOCK_ENTITIES.register("compact_speed_controller", () ->
                    BlockEntityType.Builder.of(CompactSpeedControllerBlockEntity::new,
                            CompactSpeedCtrlMod.COMPACT_SPEED_CONTROLLER.get()).build(null));
}
