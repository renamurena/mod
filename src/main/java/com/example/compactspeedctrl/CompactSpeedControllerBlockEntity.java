package com.example.compactspeedctrl;

import java.util.List;

import com.simibubi.create.content.kinetics.RotationPropagator;
import com.simibubi.create.content.kinetics.motor.KineticScrollValueBehaviour;
import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.utility.CreateLang;

import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class CompactSpeedControllerBlockEntity extends SplitShaftBlockEntity {

    public static final int DEFAULT_SPEED = 16;
    public static final int MAX_SPEED = 256;
    public ScrollValueBehaviour targetSpeed;

    public CompactSpeedControllerBlockEntity(BlockPos pos, BlockState state) {
        super(CompactSpeedCtrlBlockEntities.COMPACT_SPEED_CONTROLLER.get(), pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
        targetSpeed = new KineticScrollValueBehaviour(
                CreateLang.translateDirect("kinetics.speed_controller.rotation_speed"),
                this, new ControllerValueBoxTransform());
        targetSpeed.between(-MAX_SPEED, MAX_SPEED);
        targetSpeed.value = DEFAULT_SPEED;
        targetSpeed.withCallback(value -> updateTargetRotation());
        behaviours.add(targetSpeed);
    }

    private void updateTargetRotation() {
        if (level == null || level.isClientSide)
            return;
        if (hasNetwork())
            getOrCreateNetwork().remove(this);
        RotationPropagator.handleRemoved(level, worldPosition, this);
        removeSource();
        attachKinetics();
        setChanged();
        sendData();
    }

    public int getTargetSpeed() {
        return targetSpeed == null ? DEFAULT_SPEED : targetSpeed.getValue();
    }

    @Override
    public float getRotationSpeedModifier(Direction face) {
        Direction output = getBlockState().getValue(CompactSpeedControllerBlock.FACING);
        if (face != output)
            return 1.0f;
        float inputSpeed = getTheoreticalSpeed();
        int requestedSpeed = getTargetSpeed();
        if (requestedSpeed == 0 || inputSpeed == 0)
            return 0.0f;
        return requestedSpeed / inputSpeed;
    }

    private class ControllerValueBoxTransform extends ValueBoxTransform.Sided {
        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8, 11f, 15.5f);
        }

        @Override
        protected boolean isSideActive(BlockState state, Direction direction) {
            return direction.getAxis().isHorizontal()
                    && direction != state.getValue(CompactSpeedControllerBlock.FACING)
                    && direction != state.getValue(CompactSpeedControllerBlock.FACING).getOpposite();
        }

        @Override
        public float getScale() {
            return 0.5f;
        }
    }
}
