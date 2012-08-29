// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EngineTowCable.java

package com.maddox.il2.fm;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Orientation;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.fm:
//            Engines, FlightModel, Mass, Gear

public class EngineTowCable extends com.maddox.il2.fm.Engines
{

    public EngineTowCable()
    {
        traktor = null;
        plough = null;
        stringLength = 88.8F;
        stringKx = 88F;
        boostThrust = 0.0F;
        fireOutTime = 0L;
    }

    public float force(float f, float f1)
    {
        a = 0.0F;
        if(com.maddox.rts.Time.current() < fireOutTime)
            a += boostThrust;
        if(traktor == null)
            return plough.M.mass * (plough.Gears.onGround ? 0.0F : 1E-018F);
        tmpv.sub(traktor.Loc, plough.Loc);
        plough.Or.transformInv(tmpv);
        if(tmpv.x > (double)stringLength)
            a += ((float)tmpv.x - stringLength) * ((float)tmpv.x - stringLength) * stringKx;
        return a;
    }

    public float getPower()
    {
        return 1.0F;
    }

    public int getEngineNumber()
    {
        return 0;
    }

    public com.maddox.il2.fm.FlightModel traktor;
    public com.maddox.il2.fm.FlightModel plough;
    public float stringLength;
    public float stringKx;
    public float boostThrust;
    public long fireOutTime;
    private static float a;
    private static com.maddox.JGP.Vector3d tmpv = new Vector3d();

}
