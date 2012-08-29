// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LightPointWorld.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;

// Referenced classes of package com.maddox.il2.engine:
//            LightPoint, Engine, LightEnv

public class LightPointWorld extends com.maddox.il2.engine.LightPoint
{

    public LightPointWorld()
    {
        com.maddox.il2.engine.Engine.cur.lightEnv.add(this);
    }

    public void setPos(double d, double d1, double d2)
    {
        com.maddox.il2.engine.Engine.cur.lightEnv.changedPos(this, d, d1, d2);
        super.setPos(d, d1, d2);
    }

    public void setPos(double ad[])
    {
        setPos(ad[0], ad[1], ad[2]);
    }

    public void setPos(com.maddox.JGP.Point3d point3d)
    {
        setPos(point3d.x, point3d.y, point3d.z);
    }

    public void setEmit(float f, float f1)
    {
        com.maddox.il2.engine.Engine.cur.lightEnv.changedEmit(this, f, f1);
        super.setEmit(f, f1);
    }

    public void destroy()
    {
        if(isDestroyed())
        {
            return;
        } else
        {
            com.maddox.il2.engine.Engine.cur.lightEnv.remove(this);
            super.destroy();
            return;
        }
    }
}
