// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   UnitInterface.java

package com.maddox.il2.ai.ground;

import com.maddox.il2.engine.Actor;

// Referenced classes of package com.maddox.il2.ai.ground:
//            UnitInPackedForm, UnitData

public interface UnitInterface
{

    public abstract void startMove();

    public abstract void forceReaskMove();

    public abstract com.maddox.il2.ai.ground.UnitInPackedForm Pack();

    public abstract com.maddox.il2.ai.ground.UnitData GetUnitData();

    public abstract float HeightAboveLandSurface();

    public abstract float SpeedAverage();

    public abstract float BestSpace();

    public abstract float CommandInterval();

    public abstract float StayInterval();

    public abstract void absoluteDeath(com.maddox.il2.engine.Actor actor);
}
