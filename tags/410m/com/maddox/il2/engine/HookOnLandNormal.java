// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookOnLandNormal.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;

// Referenced classes of package com.maddox.il2.engine:
//            Hook, Orient, Loc, Engine, 
//            Landscape, Actor

public class HookOnLandNormal extends com.maddox.il2.engine.Hook
{

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        loc1.get(p, o);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y) + dz;
        com.maddox.il2.engine.Engine.land().N(p.x, p.y, normal);
        o.orient(normal);
        loc1.set(p, o);
    }

    public HookOnLandNormal(double d)
    {
        dz = 0.10000000000000001D;
        dz = d;
    }

    public double dz;
    private static com.maddox.JGP.Vector3f normal = new Vector3f();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();

}
