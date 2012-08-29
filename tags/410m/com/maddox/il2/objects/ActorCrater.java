// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorCrater.java

package com.maddox.il2.objects;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.MsgCollisionRequestListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Message;
import com.maddox.rts.NetObj;
import com.maddox.rts.Time;

public class ActorCrater extends com.maddox.il2.engine.ActorMesh
    implements com.maddox.il2.engine.MsgCollisionRequestListener
{

    public void msgCollisionRequest(com.maddox.il2.engine.Actor actor, boolean aflag[])
    {
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
            aflag[0] = true;
        else
            aflag[0] = false;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorCrater(java.lang.String s, com.maddox.il2.engine.Loc loc, float f)
    {
        super(s, loc);
        pos.getAbs(p, o);
        p.z = com.maddox.il2.engine.Engine.land().HQ(p.x, p.y);
        com.maddox.il2.engine.Engine.land().N(p.x, p.y, normal);
        o.orient(normal);
        pos.setAbs(p, o);
        pos.reset();
        drawing(true);
        collide(true);
        postDestroy(com.maddox.rts.Time.current() + (long)(f * 1000F));
        if(com.maddox.il2.engine.Actor.isAlive(initOwner) && (initOwner instanceof com.maddox.il2.objects.air.Aircraft))
            netOwner = ((com.maddox.il2.objects.air.Aircraft)initOwner).netUser();
    }

    public com.maddox.rts.NetObj netOwner;
    public static com.maddox.il2.engine.Actor initOwner = null;
    private static com.maddox.JGP.Vector3f normal = new Vector3f();
    private static com.maddox.JGP.Point3d p = new Point3d();
    private static com.maddox.il2.engine.Orient o = new Orient();

}
