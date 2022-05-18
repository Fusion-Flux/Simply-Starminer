package com.fusionflux.starminer.util;

import net.minecraft.util.math.Vec3d;

public interface EntityAttachments {
    int getGravityTimer();

    void setGravityTimer(int gravityTimer);

    int getPlateGravityTimer();

    void setPlateGravityTimer(int gravityTimer);

    int getRemainingGravity();

    Vec3d getLastSSMVel();

    void setRemainingGravity(int gravityTimer);

}
