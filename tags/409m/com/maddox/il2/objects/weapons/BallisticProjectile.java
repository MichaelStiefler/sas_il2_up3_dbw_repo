// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   BallisticProjectile.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.rts.Message;
import com.maddox.rts.Time;

public class BallisticProjectile extends com.maddox.il2.engine.Actor
{
    class Interpolater extends com.maddox.il2.engine.Interpolate
    {

        public boolean tick()
        {
            interpolateStep();
            return true;
        }

        Interpolater()
        {
        }
    }


    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public BallisticProjectile(com.maddox.JGP.Point3d point3d, com.maddox.JGP.Vector3d vector3d, float f)
    {
        pos = new ActorPosMove(this);
        pos.setAbs(point3d);
        pos.reset();
        v = new Vector3d(vector3d);
        v.scale(com.maddox.rts.Time.tickConstLenFs());
        ttl = com.maddox.rts.Time.current() + (long)(f * 1000F);
        interpPut(new Interpolater(), null, com.maddox.rts.Time.current(), null);
    }

    public void interpolateStep()
    {
        pos.getAbs(tmpp);
        tmpp.add(v);
        pos.setAbs(tmpp);
        v.z -= com.maddox.il2.fm.Atmosphere.g() * com.maddox.rts.Time.tickLenFs() * com.maddox.rts.Time.tickLenFs();
        if(com.maddox.rts.Time.current() > ttl)
            postDestroy();
    }

    private com.maddox.JGP.Vector3d v;
    private long ttl;
    private static com.maddox.JGP.Point3d tmpp = new Point3d();

}
