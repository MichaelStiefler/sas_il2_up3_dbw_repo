// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorFrontMarker.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Front;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import java.util.List;

public class ActorFrontMarker extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.objects.ActorAlign
{

    public void align()
    {
        alignPosToLand(0.0D, true);
        m.x = pos.getAbsPoint().x;
        m.y = pos.getAbsPoint().y;
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public void destroy()
    {
        if(m == null)
            return;
        super.destroy();
        int i = com.maddox.il2.ai.Front.markers().indexOf(m);
        if(i >= 0)
        {
            com.maddox.il2.ai.Front.markers().remove(i);
            com.maddox.il2.ai.Front.setMarkersChanged();
        }
        m = null;
    }

    public ActorFrontMarker(java.lang.String s, int i, com.maddox.JGP.Point3d point3d)
    {
        m = new com.maddox.il2.ai.Front.Marker();
        i18nKey = s;
        setArmy(i);
        m.army = i;
        m._armyMask = 1 << i - 1;
        flags |= 0x2000;
        pos = new ActorPosMove(this);
        com.maddox.il2.engine.IconDraw.create(this);
        if(point3d != null)
        {
            pos.setAbs(point3d);
            align();
        }
        icon = com.maddox.il2.engine.IconDraw.get("icons/front0.mat");
        drawing(true);
        com.maddox.il2.ai.Front.markers().add(m);
        com.maddox.il2.ai.Front.setMarkersChanged();
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public com.maddox.il2.ai.Front.Marker m;
    public java.lang.String i18nKey;
}
