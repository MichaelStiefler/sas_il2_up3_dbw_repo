// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ActorTarget.java

package com.maddox.il2.builder;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.DrawEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.IconDraw;
import com.maddox.il2.objects.ActorAlign;
import com.maddox.il2.objects.bridges.Bridge;
import com.maddox.il2.objects.bridges.BridgeSegment;
import com.maddox.rts.Message;
import java.util.AbstractCollection;

// Referenced classes of package com.maddox.il2.builder:
//            PathAir, Path, PathChief, PAir, 
//            PPoint, PNodes

public class ActorTarget extends com.maddox.il2.engine.Actor
    implements com.maddox.il2.objects.ActorAlign
{
    static class SelectMoved
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(d <= com.maddox.il2.builder.ActorTarget._maxLen2)
            {
                if(!(actor instanceof com.maddox.il2.builder.PPoint))
                    return true;
                if(com.maddox.il2.builder.ActorTarget._Actor == null || d < com.maddox.il2.builder.ActorTarget._Len2)
                {
                    com.maddox.il2.builder.ActorTarget._Actor = actor;
                    com.maddox.il2.builder.ActorTarget._Len2 = d;
                }
            }
            return true;
        }

        SelectMoved()
        {
        }
    }

    static class SelectChief
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(d <= com.maddox.il2.builder.ActorTarget._maxLen2)
            {
                if(!(actor instanceof com.maddox.il2.builder.PNodes))
                    return true;
                if(com.maddox.il2.builder.ActorTarget._Actor == null || d < com.maddox.il2.builder.ActorTarget._Len2)
                {
                    com.maddox.il2.builder.ActorTarget._Actor = actor;
                    com.maddox.il2.builder.ActorTarget._Len2 = d;
                }
            }
            return true;
        }

        SelectChief()
        {
        }
    }

    static class SelectAir
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(d <= com.maddox.il2.builder.ActorTarget._maxLen2)
            {
                if(!(actor instanceof com.maddox.il2.builder.PAir))
                    return true;
                if(com.maddox.il2.builder.ActorTarget._Actor == null || d < com.maddox.il2.builder.ActorTarget._Len2)
                {
                    com.maddox.il2.builder.ActorTarget._Actor = actor;
                    com.maddox.il2.builder.ActorTarget._Len2 = d;
                }
            }
            return true;
        }

        SelectAir()
        {
        }
    }

    static class SelectBridge
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            if(d <= com.maddox.il2.builder.ActorTarget._maxLen2)
            {
                if(actor instanceof com.maddox.il2.objects.bridges.BridgeSegment)
                    actor = actor.getOwner();
                else
                    return true;
                if(com.maddox.il2.builder.ActorTarget._Actor == null || d < com.maddox.il2.builder.ActorTarget._Len2)
                {
                    com.maddox.il2.builder.ActorTarget._Actor = actor;
                    com.maddox.il2.builder.ActorTarget._Len2 = d;
                }
            }
            return true;
        }

        SelectBridge()
        {
        }
    }


    public com.maddox.il2.engine.Actor getTarget()
    {
        return target;
    }

    public void align()
    {
        alignPosToLand(0.0D, true);
    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public ActorTarget(com.maddox.JGP.Point3d point3d, int i, java.lang.String s, int j)
    {
        target = null;
        destructLevel = 50;
        flags |= 0x2000;
        pos = new ActorPosMove(this);
        pos.setAbs(point3d);
        align();
        drawing(true);
        type = i;
        if(i == 3 || i == 6 || i == 7)
        {
            bTimeout = true;
            timeout = 30;
        }
        if(i != 1 && i != 6)
            if(s == null)
            {
                if(i == 2 || i == 7)
                    target = selectBridge(10000D);
                else
                if(i == 4)
                    target = selectAir(10000D);
                else
                if(i == 5)
                    target = selectChief(10000D);
                else
                if(i == 3)
                    target = selectChief(1000D);
                else
                    target = selectMoved(10000D);
                if(target == null && i != 3)
                    throw new RuntimeException("target NOT found");
            } else
            {
                target = com.maddox.il2.engine.Actor.getByName(s);
                if(target == null)
                    throw new RuntimeException("target NOT found");
                if(i == 2 || i == 7)
                {
                    if(!(target instanceof com.maddox.il2.objects.bridges.Bridge))
                        throw new RuntimeException("target NOT found");
                } else
                if(i == 4)
                {
                    if(!(target instanceof com.maddox.il2.builder.PathAir))
                        throw new RuntimeException("target NOT found");
                    target = ((com.maddox.il2.builder.Path)target).point(j);
                } else
                if(i == 5)
                {
                    if(!(target instanceof com.maddox.il2.builder.PathChief))
                        throw new RuntimeException("target NOT found");
                    target = ((com.maddox.il2.builder.Path)target).point(j);
                } else
                if(i == 3)
                {
                    if(!(target instanceof com.maddox.il2.builder.PathChief))
                        throw new RuntimeException("target NOT found");
                    target = ((com.maddox.il2.builder.Path)target).point(j);
                } else
                {
                    if(!(target instanceof com.maddox.il2.builder.Path))
                        throw new RuntimeException("target NOT found");
                    target = ((com.maddox.il2.builder.Path)target).point(j);
                }
            }
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(target instanceof com.maddox.il2.builder.PAir)
                icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroyair.mat");
            else
                icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroychief.mat");
            break;

        case 1: // '\001'
            icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroyground.mat");
            r = 500;
            break;

        case 2: // '\002'
            icon = com.maddox.il2.engine.IconDraw.get("icons/tdestroybridge.mat");
            break;

        case 3: // '\003'
            icon = com.maddox.il2.engine.IconDraw.get("icons/tinspect.mat");
            r = 500;
            break;

        case 4: // '\004'
            icon = com.maddox.il2.engine.IconDraw.get("icons/tescort.mat");
            break;

        case 5: // '\005'
            icon = com.maddox.il2.engine.IconDraw.get("icons/tdefence.mat");
            break;

        case 6: // '\006'
            icon = com.maddox.il2.engine.IconDraw.get("icons/tdefenceground.mat");
            r = 500;
            break;

        case 7: // '\007'
            icon = com.maddox.il2.engine.IconDraw.get("icons/tdefencebridge.mat");
            break;
        }
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    private com.maddox.il2.engine.Actor selectBridge(double d)
    {
        _Actor = null;
        _maxLen2 = d * d;
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        com.maddox.il2.engine.Engine.drawEnv().getFiltered((java.util.AbstractCollection)null, point3d.x - d, point3d.y - d, point3d.x + d, point3d.y + d, 2, _selectBridge);
        com.maddox.il2.engine.Actor actor = _Actor;
        _Actor = null;
        return actor;
    }

    private com.maddox.il2.engine.Actor selectAir(double d)
    {
        _Actor = null;
        _maxLen2 = d * d;
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        com.maddox.il2.engine.Engine.drawEnv().getFiltered((java.util.AbstractCollection)null, point3d.x - d, point3d.y - d, point3d.x + d, point3d.y + d, 12, _selectAir);
        com.maddox.il2.engine.Actor actor = _Actor;
        _Actor = null;
        return actor;
    }

    private com.maddox.il2.engine.Actor selectChief(double d)
    {
        _Actor = null;
        _maxLen2 = d * d;
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        com.maddox.il2.engine.Engine.drawEnv().getFiltered((java.util.AbstractCollection)null, point3d.x - d, point3d.y - d, point3d.x + d, point3d.y + d, 12, _selectChief);
        com.maddox.il2.engine.Actor actor = _Actor;
        _Actor = null;
        return actor;
    }

    private com.maddox.il2.engine.Actor selectMoved(double d)
    {
        _Actor = null;
        _maxLen2 = d * d;
        com.maddox.JGP.Point3d point3d = pos.getAbsPoint();
        com.maddox.il2.engine.Engine.drawEnv().getFiltered((java.util.AbstractCollection)null, point3d.x - d, point3d.y - d, point3d.x + d, point3d.y + d, 12, _selectMoved);
        com.maddox.il2.engine.Actor actor = _Actor;
        _Actor = null;
        return actor;
    }

    public int importance;
    public int type;
    public com.maddox.il2.engine.Actor target;
    public int timeout;
    public boolean bTimeout;
    public boolean bLanding;
    public int destructLevel;
    public int r;
    private static com.maddox.il2.engine.Actor _Actor = null;
    private static double _Len2;
    private static double _maxLen2;
    private static com.maddox.il2.builder.SelectBridge _selectBridge = new SelectBridge();
    private static com.maddox.il2.builder.SelectAir _selectAir = new SelectAir();
    private static com.maddox.il2.builder.SelectChief _selectChief = new SelectChief();
    private static com.maddox.il2.builder.SelectMoved _selectMoved = new SelectMoved();






}
