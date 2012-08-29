// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Path.java

package com.maddox.il2.builder;

import com.maddox.il2.engine.Actor;
import com.maddox.rts.Message;
import java.util.List;

// Referenced classes of package com.maddox.il2.builder:
//            PPoint, Plugin, Builder, BldConfig, 
//            Pathes

public class Path extends com.maddox.il2.engine.Actor
{

    public boolean isDrawing()
    {
        if((flags & 1) == 0)
        {
            return false;
        } else
        {
            int i = getArmy();
            return com.maddox.il2.builder.Plugin.builder.conf.bShowArmy[i];
        }
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Path(com.maddox.il2.builder.Pathes pathes)
    {
        startTime = 0.0D;
        renderPoints = 0;
        moveType = -1;
        points = new java.lang.Object[1];
        flags |= 0x2000;
        setOwner(pathes);
        drawing(true);
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public com.maddox.il2.builder.PPoint selectPrev(com.maddox.il2.builder.PPoint ppoint)
    {
        points = getOwnerAttached(points);
        for(int i = 0; i < points.length; i++)
        {
            com.maddox.il2.builder.PPoint ppoint1 = (com.maddox.il2.builder.PPoint)points[i];
            if(ppoint1 == ppoint)
            {
                if(i > 0)
                    return (com.maddox.il2.builder.PPoint)points[i - 1];
                if(i + 1 < points.length)
                    return (com.maddox.il2.builder.PPoint)points[i + 1];
                else
                    return null;
            }
        }

        return null;
    }

    public int pointIndx(com.maddox.il2.builder.PPoint ppoint)
    {
        points = getOwnerAttached(points);
        for(int i = 0; i < points.length; i++)
        {
            com.maddox.il2.builder.PPoint ppoint1 = (com.maddox.il2.builder.PPoint)points[i];
            if(ppoint1 == ppoint)
                return i;
        }

        return -1;
    }

    public int points()
    {
        return ownerAttached.size();
    }

    public com.maddox.il2.builder.PPoint point(int i)
    {
        return (com.maddox.il2.builder.PPoint)ownerAttached.get(i);
    }

    public void computeTimes()
    {
    }

    public void pointMoved(com.maddox.il2.builder.PPoint ppoint)
    {
        computeTimes();
    }

    public void destroy()
    {
        if(isDestroyed())
            return;
        if(this == player)
        {
            player = null;
            playerNum = 0;
        }
        java.lang.Object aobj[] = getOwnerAttached();
        for(int i = 0; i < aobj.length; i++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)aobj[i];
            if(actor == null)
                break;
            actor.destroy();
            aobj[i] = null;
        }

        super.destroy();
    }

    public static com.maddox.il2.builder.Path player;
    public static int playerNum;
    public double startTime;
    public int renderPoints;
    public int moveType;
    private java.lang.Object points[];
}
