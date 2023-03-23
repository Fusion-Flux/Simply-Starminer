package com.fusionflux.starminer.duck;

import com.fusionflux.starminer.block.entity.AbstractStarCoreBlockEntity;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface EntityAttachments {
    int getPlateGravityTimer();

    void setPlateGravityTimer(int gravityTimer);

    Object2IntMap<AbstractStarCoreBlockEntity> getNearbyStarCores();
}
