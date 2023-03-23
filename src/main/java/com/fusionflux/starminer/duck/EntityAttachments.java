package com.fusionflux.starminer.duck;

import com.fusionflux.starminer.block.entity.AbstractStarCoreBlockEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.util.math.Vec3d;

public interface EntityAttachments {
    int getPlateGravityTimer();

    void setPlateGravityTimer(int gravityTimer);

    int getStarGravityTimer();

    void setStarGravityTimer(int gravityTimer);

    Vec3d getLastSSMVel();

    Object2IntMap<AbstractStarCoreBlockEntity> getNearbyStarCores();
}
