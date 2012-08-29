// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HunterInterface.java

package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Actor;

// Referenced classes of package com.maddox.il2.ai.ground:
//            Aim

public interface HunterInterface
{

    public abstract float getReloadingTime(com.maddox.il2.ai.ground.Aim aim);

    public abstract float chainFireTime(com.maddox.il2.ai.ground.Aim aim);

    public abstract float probabKeepSameEnemy(com.maddox.il2.engine.Actor actor);

    public abstract float minTimeRelaxAfterFight();

    public abstract boolean enterToFireMode(int i, com.maddox.il2.engine.Actor actor, float f, com.maddox.il2.ai.ground.Aim aim);

    public abstract void gunStartParking(com.maddox.il2.ai.ground.Aim aim);

    public abstract void gunInMove(boolean flag, com.maddox.il2.ai.ground.Aim aim);

    public abstract com.maddox.il2.engine.Actor findEnemy(com.maddox.il2.ai.ground.Aim aim);

    public abstract int targetGun(com.maddox.il2.ai.ground.Aim aim, com.maddox.il2.engine.Actor actor, float f, boolean flag);

    public abstract void singleShot(com.maddox.il2.ai.ground.Aim aim);

    public abstract void startFire(com.maddox.il2.ai.ground.Aim aim);

    public abstract void continueFire(com.maddox.il2.ai.ground.Aim aim);

    public abstract void stopFire(com.maddox.il2.ai.ground.Aim aim);
}
