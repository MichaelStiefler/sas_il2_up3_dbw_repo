// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PPoint.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.rts.Message;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.builder:
//            Path

public class PPoint extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.objects.ActorAlign
{

    public com.maddox.il2.engine.Actor getTarget()
    {
        return null;
    }

    public void moveTo(com.maddox.JGP.Point3d point3d)
    {
        pos.setAbs(point3d);
        align();
    }

    public void align()
    {
        alignPosToLand(0.0D, true);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public PPoint(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, com.maddox.il2.engine.Mat mat, com.maddox.JGP.Point3d point3d)
    {
        time = 0.0D;
        flags |= 0x2000;
        pos = new ActorPosMove(this);
        if(mat != null)
            icon = mat;
        else
            com.maddox.il2.engine.IconDraw.create(this);
        setOwnerAfter(path, ppoint, true, true, true);
        if(point3d != null)
        {
            pos.setAbs(point3d);
            align();
        }
        drawing(true);
    }

    public PPoint(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, com.maddox.JGP.Point3d point3d)
    {
        this(path, ppoint, (com.maddox.il2.engine.Mat)null, point3d);
    }

    public PPoint(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, java.lang.String s, com.maddox.JGP.Point3d point3d)
    {
        this(path, ppoint, com.maddox.il2.engine.IconDraw.get(s), point3d);
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

    public double time;
    public double screenX;
    public double screenY;
    public int screenQuad;

    static 
    {
        com.maddox.rts.Property.set(com.maddox.il2.builder.PPoint.class, "iconName", "icons/SelectIcon.mat");
    }
}
