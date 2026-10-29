package com.example.compactspeedctrl;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

public class CompactSpeedControllerBlock extends DirectionalKineticBlock implements IBE<CompactSpeedControllerBlockEntity> {

    public CompactSpeedControllerBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        Direction facing = state.getValue(FACING);
        return face == facing || face == facing.getOpposite();
    }

    @Override
    public Class<CompactSpeedControllerBlockEntity> getBlockEntityClass() {
        return CompactSpeedControllerBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends CompactSpeedControllerBlockEntity> getBlockEntityType() {
        return CompactSpeedCtrlBlockEntities.COMPACT_SPEED_CONTROLLER.get();
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (level.isClientSide) return InteractionResult.SUCCESS;
        withBlockEntityDo(level, pos, be -> {
            // Right-click: +1 RPM; Shift + right-click: -1 RPM.
            be.changeTargetSpeed(player.isShiftKeyDown() ? -1 : 1);
            player.displayClientMessage(Component.literal("Target speed: " + be.getTargetSpeed() + " RPM"), true);
        });
        return InteractionResult.SUCCESS;
    }
}