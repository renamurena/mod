package com.example.compactspeedctrl;

import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(CompactSpeedCtrlMod.MOD_ID)
public class CompactSpeedCtrlMod {
    public static final String MOD_ID = "compactspeedctrl";

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredBlock<CompactSpeedControllerBlock> COMPACT_SPEED_CONTROLLER =
            BLOCKS.registerBlock("compact_speed_controller", CompactSpeedControllerBlock::new,
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.COLOR_ORANGE)
                            .strength(2.5f)
                            .sound(SoundType.COPPER)
                            .noOcclusion());

    public static final DeferredItem<BlockItem> COMPACT_SPEED_CONTROLLER_ITEM =
            ITEMS.registerSimpleBlockItem("compact_speed_controller", COMPACT_SPEED_CONTROLLER);

    public CompactSpeedCtrlMod(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CompactSpeedCtrlBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        modEventBus.addListener(this::addToCreateTab);
    }

    private void addToCreateTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey()) {
            event.accept(COMPACT_SPEED_CONTROLLER_ITEM);
        }
    }
}
