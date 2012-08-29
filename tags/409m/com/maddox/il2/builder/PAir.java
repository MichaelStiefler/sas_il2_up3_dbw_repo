// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PAir.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.engine.Mat;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.builder:
//            PPoint, Path, PathChief, PlMisChief

public class PAir extends com.maddox.il2.builder.PPoint
{

    public void setType(int i)
    {
        type = i;
        setIcon();
    }

    public int type()
    {
        return type;
    }

    public com.maddox.il2.engine.Actor getTarget()
    {
        if(com.maddox.il2.engine.Actor.isValid(target))
            return target;
        if(sTarget != null)
        {
            com.maddox.il2.engine.Actor actor = com.maddox.il2.engine.Actor.getByName(sTarget);
            if(com.maddox.il2.engine.Actor.isValid(actor))
            {
                if(actor instanceof com.maddox.il2.builder.Path)
                {
                    com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)actor;
                    if(iTarget >= path.points())
                        iTarget = path.points() - 1;
                    if(iTarget < 0)
                    {
                        iTarget = 0;
                        sTarget = null;
                        target = null;
                    } else
                    {
                        com.maddox.il2.builder.PPoint ppoint = path.point(iTarget);
                        target = ppoint;
                    }
                } else
                {
                    target = actor;
                }
                return target;
            }
            sTarget = null;
        }
        target = null;
        return null;
    }

    public void setTarget(com.maddox.il2.engine.Actor actor)
    {
        target = actor;
        if(target != null)
            if(type != 1 && type != 2)
            {
                if(target instanceof com.maddox.il2.builder.PAir)
                    setType(0);
                else
                    setType(3);
            } else
            if(target instanceof com.maddox.il2.builder.PPoint)
            {
                if(target.getOwner() instanceof com.maddox.il2.builder.PathChief)
                {
                    com.maddox.il2.builder.PathChief pathchief = (com.maddox.il2.builder.PathChief)target.getOwner();
                    if(!com.maddox.il2.builder.PlMisChief.isAirport(pathchief._iType, pathchief._iItem))
                        target = null;
                } else
                {
                    target = null;
                }
            } else
            if(!"true".equals(com.maddox.rts.Property.stringValue(target.getClass(), "IsAirport", (java.lang.String)null)))
                target = null;
    }

    public void destroy()
    {
        target = null;
        sTarget = null;
        com.maddox.il2.builder.Path path = (com.maddox.il2.builder.Path)getOwner();
        super.destroy();
        if(path != null)
            path.computeTimes();
    }

    public PAir(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, com.maddox.il2.engine.Mat mat, com.maddox.JGP.Point3d point3d, int i, double d, 
            double d1)
    {
        super(path, ppoint, mat, point3d);
        target = null;
        sTarget = null;
        iTarget = 0;
        bRadioSilence = false;
        setType(i);
        height = d;
        speed = d1;
        path.computeTimes();
    }

    public PAir(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, com.maddox.JGP.Point3d point3d, int i, double d, double d1)
    {
        this(path, ppoint, (com.maddox.il2.engine.Mat)null, point3d, i, d, d1);
    }

    public PAir(com.maddox.il2.builder.Path path, com.maddox.il2.builder.PPoint ppoint, java.lang.String s, com.maddox.JGP.Point3d point3d, int i, double d, 
            double d1)
    {
        this(path, ppoint, com.maddox.il2.engine.IconDraw.get(s), point3d, i, d, d1);
    }

    private void setIcon()
    {
        java.lang.String s = null;
        switch(type)
        {
        case 0: // '\0'
            s = "normfly";
            break;

        case 1: // '\001'
            s = "takeoff";
            break;

        case 2: // '\002'
            s = "landing";
            break;

        case 3: // '\003'
            s = "gattack";
            break;

        default:
            return;
        }
        icon = com.maddox.il2.engine.IconDraw.get("icons/" + s + ".mat");
    }

    public static final int NORMFLY = 0;
    public static final int TAKEOFF = 1;
    public static final int LANDING = 2;
    public static final int GATTACK = 3;
    public static final java.lang.String types[] = {
        "NORMFLY", "TAKEOFF", "LANDING", "GATTACK"
    };
    private int type;
    public double height;
    public double speed;
    private com.maddox.il2.engine.Actor target;
    public java.lang.String sTarget;
    public int iTarget;
    public boolean bRadioSilence;

}
