package com.example.compactspeedctrl;

import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class CompactSpeedControllerBlockEntity extends SplitShaftBlockEntity {

    /** Output speed as a percentage of input speed. 100 = passthrough, 0 = stopped. */
    private int percent = 100;

    public CompactSpeedControllerBlockEntity(BlockPos pos, BlockState state) {
        super(CompactSpeedCtrlBlockEntities.COMPACT_SPEED_CONTROLLER.get(), pos, state);
    }

    public int getPercent() {
        return percent;
    }

    public void changePercent(int delta) {
        int next = Math.max(0, Math.min(200, percent + delta));
        if (next == percent) return;
        percent = next;
        setChanged();
        sendData();
        if (level != null && !level.isClientSide) {
            level.updateNeighbourForOutputSignal(worldPosition, getBlockState().getBlock());
        }
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        Direction output = getBlockState().getValue(CompactSpeedControllerBlock.FACING);
        // Keep the input side at full speed; apply the selected ratio only to the output shaft.
        return face == output ? percent / 100f : 1.0f;
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Percent", percent);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        percent = tag.contains("Percent") ? Math.max(0, Math.min(200, tag.getInt("Percent"))) : 100;
    }
}