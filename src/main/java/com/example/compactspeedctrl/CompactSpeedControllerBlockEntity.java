package com.example.compactspeedctrl;

import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class CompactSpeedControllerBlockEntity extends SplitShaftBlockEntity {

    /** Output as a percentage of input speed, -200..200. 100 = passthrough, 0 = stopped. */
    private int percent = 100;

    public CompactSpeedControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public int getPercent() {
        return percent;
    }

    public void changePercent(int delta) {
        percent = Math.max(-200, Math.min(200, percent + delta));
        setChanged();
        sendData();
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        return percent / 100f;
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(tag, registries, clientPacket);
        tag.putInt("Percent", percent);
    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        percent = tag.contains("Percent") ? tag.getInt("Percent") : 100;
    }
}
