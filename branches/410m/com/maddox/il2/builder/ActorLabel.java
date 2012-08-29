// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorLabel.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import com.maddox.rts.Property;

public class ActorLabel extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.objects.ActorAlign
{

    public void align()
    {
        alignPosToLand(0.0D, true);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorLabel(com.maddox.JGP.Point3d point3d)
    {
        flags |= 0x2000;
        pos = new ActorPosMove(this);
        com.maddox.il2.engine.IconDraw.create(this);
        if(point3d != null)
        {
            pos.setAbs(point3d);
            align();
        }
        drawing(true);
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.ActorLabel.class, "iconName", "icons/label.mat");
    }
}
