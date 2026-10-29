package com.example.compactspeedctrl;

import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class CompactSpeedControllerBlockEntity extends SplitShaftBlockEntity {

    /** Target output RPM, matching Create's speed-controller style numeric setting. */
    private int targetSpeed = 16;

    public CompactSpeedControllerBlockEntity(BlockPos pos, BlockState state) {
        super(CompactSpeedCtrlBlockEntities.COMPACT_SPEED_CONTROLLER.get(), pos, state);
    }

    public int getTargetSpeed() {
        return targetSpeed;
    }

    public void changeTargetSpeed(int delta) {
        int next = Math.max(-256, Math.min(256, targetSpeed + delta));
        if (next == targetSpeed) return;
        targetSpeed = next;
        setChanged();
        sendData();
        if (level != null && !level.isClientSide) {
            // Re-propagate the kinetic network so connected machinery receives the new ratio.
            level.invalidateCapabilities(worldPosition);
            level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        }
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        Direction output = getBlockState().getValue(CompactSpeedControllerBlock.FACING);
        if (face != output) return 1.0f;
        float inputSpeed = getTheoreticalSpeed();
        if (targetSpeed == 0 || inputSpeed == 0) return 0.0f;
        return targetSpeed / inputSpeed;
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("TargetSpeed", targetSpeed);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        targetSpeed = tag.contains("TargetSpeed") ? Math.max(-256, Math.min(256, tag.getInt("TargetSpeed"))) : 16;
    }
}