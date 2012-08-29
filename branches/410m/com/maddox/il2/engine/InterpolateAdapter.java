// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   InterpolateAdapter.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.War;
import com.maddox.rts.Message;
import com.maddox.rts.MsgTimeOut;
import com.maddox.rts.MsgTimeOutListener;
import com.maddox.rts.Time;
import java.io.PrintStream;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.engine:
//            Actor, Engine, ActorPos, DreamEnv, 
//            GObj, EngineProfile, CollideEnv, ActorFilter, 
//            Accumulator

public final class InterpolateAdapter
    implements com.maddox.rts.MsgTimeOutListener
{

    public static void getSphere(java.util.AbstractCollection abstractcollection, com.maddox.JGP.Point3d point3d, double d)
    {
        com.maddox.il2.engine.InterpolateAdapter.adapter()._getSphere(abstractcollection, point3d, d);
    }

    public static void getFiltered(java.util.AbstractCollection abstractcollection, com.maddox.JGP.Point3d point3d, double d, com.maddox.il2.engine.ActorFilter actorfilter)
    {
        com.maddox.il2.engine.InterpolateAdapter.adapter()._getFiltered(abstractcollection, point3d, d, actorfilter);
    }

    public static void getNearestEnemies(com.maddox.JGP.Point3d point3d, double d, int i, com.maddox.il2.engine.Accumulator accumulator)
    {
        com.maddox.il2.engine.InterpolateAdapter.adapter()._getNearestEnemies(point3d, d, i, accumulator);
    }

    public static void getNearestEnemiesCyl(com.maddox.JGP.Point3d point3d, double d, double d1, double d2, int i, 
            com.maddox.il2.engine.Accumulator accumulator)
    {
        com.maddox.il2.engine.InterpolateAdapter.adapter()._getNearestEnemiesCyl(point3d, d, d1, d2, i, accumulator);
    }

    public static com.maddox.il2.engine.InterpolateAdapter adapter()
    {
        return com.maddox.il2.engine.Engine.cur.interpolateAdapter;
    }

    public static int step()
    {
        return com.maddox.il2.engine.Engine.cur.interpolateAdapter.stepStamp;
    }

    public static boolean isActive()
    {
        return com.maddox.il2.engine.Engine.cur.interpolateAdapter.bActive;
    }

    public static void active(boolean flag)
    {
        com.maddox.il2.engine.Engine.cur.interpolateAdapter.bActive = flag;
    }

    public static boolean isProcess()
    {
        return com.maddox.il2.engine.Engine.cur.interpolateAdapter.bProcess;
    }

    public static boolean containsListener(com.maddox.il2.engine.Actor actor)
    {
        return com.maddox.il2.engine.Engine.cur.interpolateAdapter.listeners.contains(actor);
    }

    public static void forceInterpolate(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.Engine.cur.interpolateAdapter._forceInterpolate(actor);
    }

    private void _forceInterpolate(com.maddox.il2.engine.Actor actor)
    {
        if(!bProcess)
            return;
        if(currentListener == actor || stackListeners.contains(actor))
        {
            java.lang.System.err.println("ERROR: Cycle reference interpolate position");
            int i = 0;
            if(currentListener != actor)
                i = stackListeners.indexOf(actor) + 1;
            int j = stackListeners.size();
            java.lang.System.err.println("  " + actor);
            for(; i < j; i++)
                java.lang.System.err.println("  " + stackListeners.get(i));

            return;
        }
        if(curListListeners.contains(actor))
        {
            stackListeners.add(actor);
            actor.interpolateTick();
            stackListeners.remove(stackListeners.size() - 1);
        }
    }

    private void updatePos()
    {
        java.util.ArrayList arraylist = com.maddox.il2.engine.Engine.cur.posChanged;
        int i = arraylist.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)arraylist.get(j);
            if(com.maddox.il2.engine.Actor.isValid(actor))
                actor.pos.updateCurrent();
        }

        arraylist.clear();
        com.maddox.il2.engine.Engine.cur.dreamEnv.doChanges();
    }

    public void msgTimeOut(java.lang.Object obj)
    {
        boolean flag = com.maddox.rts.Message.current().isRealTime();
        if(flag)
            realTicker.post();
        else
            ticker.post();
        curListListeners = flag ? realListeners : listeners;
        com.maddox.il2.engine.Engine.processPostDestroyActors();
        com.maddox.il2.engine.GObj.DeleteCppObjects();
        if(bActive)
        {
            bProcess = true;
            if(!flag)
                com.maddox.il2.ai.War.cur().interpolateTick();
            if(!flag)
            {
                updatePos();
            } else
            {
                if(com.maddox.rts.Time.isRealOnly())
                    updatePos();
                com.maddox.il2.engine.Engine.cur.profile.endFrame();
            }
            for(iCur = 0; iCur < curListListeners.size(); iCur++)
            {
                currentListener = (com.maddox.il2.engine.Actor)curListListeners.get(iCur);
                currentListener.interpolateTick();
            }

            currentListener = null;
            bProcess = false;
            if(!flag)
            {
                com.maddox.il2.engine.Engine.cur.collideEnv.doCollision(com.maddox.il2.engine.Engine.cur.posChanged);
                com.maddox.il2.engine.Engine.cur.collideEnv.doBulletMoveAndCollision();
            }
            com.maddox.il2.engine.Engine.processPostDestroyActors();
            stepStamp++;
        }
    }

    public void addListener(com.maddox.il2.engine.Actor actor)
    {
        java.util.ArrayList arraylist = actor.isRealTime() ? realListeners : listeners;
        if(!arraylist.contains(actor))
        {
            if(!com.maddox.il2.engine.Actor.isValid(actor))
                return;
            arraylist.add(actor);
        }
    }

    public void removeListener(com.maddox.il2.engine.Actor actor)
    {
        java.util.ArrayList arraylist = actor.isRealTime() ? realListeners : listeners;
        int i = arraylist.indexOf(actor);
        if(i >= 0)
        {
            arraylist.remove(i);
            if(bProcess && i <= iCur)
                iCur--;
        }
    }

    public static void forceListener(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.il2.engine.Engine.cur.interpolateAdapter._forceListener(actor);
    }

    private void _forceListener(com.maddox.il2.engine.Actor actor)
    {
        java.util.ArrayList arraylist = actor.isRealTime() ? realListeners : listeners;
        int i = arraylist.indexOf(actor);
        if(i >= 0)
        {
            arraylist.remove(i);
            arraylist.add(0, actor);
        }
    }

    public java.util.List listeners()
    {
        return listeners;
    }

    public java.util.List realListeners()
    {
        return realListeners;
    }

    private void clearDestroyedListeners(java.util.List list)
    {
        int i = list.size();
        for(int j = 0; j < list.size(); j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            if(!com.maddox.il2.engine.Actor.isValid(actor))
                list.remove(j);
        }

    }

    protected void resetGameClear()
    {
        java.util.ArrayList arraylist = new ArrayList(realListeners);
        com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        arraylist.addAll(listeners);
        com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        clearDestroyedListeners(listeners);
        clearDestroyedListeners(realListeners);
    }

    protected void resetGameCreate()
    {
        ticker.post();
    }

    protected InterpolateAdapter()
    {
        stackListeners = new ArrayList();
        iCur = 0;
        stepStamp = 0;
        ticker = new MsgTimeOut(null);
        ticker.setTickPos(-1000);
        ticker.setNotCleanAfterSend();
        ticker.setFlags(8);
        ticker.setListener(this);
        ticker.post();
        realTicker = new MsgTimeOut(null);
        realTicker.setTickPos(-1000);
        realTicker.setNotCleanAfterSend();
        realTicker.setFlags(72);
        realTicker.setListener(this);
        realTicker.post();
        listeners = new ArrayList();
        realListeners = new ArrayList();
        bProcess = false;
        bActive = true;
    }

    private void _getSphere(java.util.AbstractCollection abstractcollection, com.maddox.JGP.Point3d point3d, double d)
    {
        double d1 = d * d;
        int i = listeners.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)listeners.get(j);
            if(actor.pos == null)
                continue;
            com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
            double d2 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
            if(d2 <= d1)
                abstractcollection.add(actor);
        }

    }

    private void _getFiltered(java.util.AbstractCollection abstractcollection, com.maddox.JGP.Point3d point3d, double d, com.maddox.il2.engine.ActorFilter actorfilter)
    {
        double d1 = d * d;
        int i = listeners.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)listeners.get(j);
            if(actor.pos == null)
                continue;
            com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
            double d2 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
            if(d2 <= d1 && actorfilter.isUse(actor, d2) && abstractcollection != null)
                abstractcollection.add(actor);
        }

    }

    private void _getNearestEnemies(com.maddox.JGP.Point3d point3d, double d, int i, com.maddox.il2.engine.Accumulator accumulator)
    {
        double d1 = d * d;
        int j = listeners.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)listeners.get(k);
            if(actor.pos == null)
                continue;
            int l = actor.getArmy();
            if(l == 0 || l == i)
                continue;
            com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
            double d2 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y) + (point3d.z - point3d1.z) * (point3d.z - point3d1.z);
            if(d2 <= d1)
                accumulator.add(actor, d2);
        }

    }

    private void _getNearestEnemiesCyl(com.maddox.JGP.Point3d point3d, double d, double d1, double d2, 
            int i, com.maddox.il2.engine.Accumulator accumulator)
    {
        double d3 = d * d;
        int j = listeners.size();
        for(int k = 0; k < j; k++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)listeners.get(k);
            if(actor.pos == null)
                continue;
            int l = actor.getArmy();
            if(l == 0 || l == i)
                continue;
            com.maddox.JGP.Point3d point3d1 = actor.pos.getAbsPoint();
            double d4 = (point3d.x - point3d1.x) * (point3d.x - point3d1.x) + (point3d.y - point3d1.y) * (point3d.y - point3d1.y);
            if(d4 > d3)
                continue;
            double d5 = point3d1.z - point3d.z;
            if(d5 <= d2 && d5 >= d1)
                accumulator.add(actor, d4 + d5 * d5);
        }

    }

    public static final int TICK_POS = -1000;
    private com.maddox.il2.engine.Actor currentListener;
    private java.util.ArrayList stackListeners;
    private java.util.ArrayList curListListeners;
    private int iCur;
    private com.maddox.rts.MsgTimeOut ticker;
    private com.maddox.rts.MsgTimeOut realTicker;
    private java.util.ArrayList listeners;
    private java.util.ArrayList realListeners;
    private boolean bProcess;
    private boolean bActive;
    private int stepStamp;
}
