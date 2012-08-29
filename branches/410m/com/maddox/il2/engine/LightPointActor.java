// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LightPointActor.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Destroy;
import com.maddox.rts.ObjState;

// Referenced classes of package com.maddox.il2.engine:
//            LightPoint

public class LightPointActor
    implements com.maddox.rts.Destroy
{

    public boolean isDestroyed()
    {
        return light == null || light.isDestroyed();
    }

    public void destroy()
    {
        com.maddox.rts.ObjState.destroy(light);
    }

    public LightPointActor(com.maddox.il2.engine.LightPoint lightpoint)
    {
        relPos = new Point3d();
        light = lightpoint;
    }

    public LightPointActor(com.maddox.il2.engine.LightPoint lightpoint, com.maddox.JGP.Point3d point3d)
    {
        relPos = new Point3d();
        light = lightpoint;
        relPos.set(point3d);
    }

    public com.maddox.il2.engine.LightPoint light;
    public com.maddox.JGP.Point3d relPos;
}
