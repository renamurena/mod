package com.example.compactspeedctrl;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

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
}
