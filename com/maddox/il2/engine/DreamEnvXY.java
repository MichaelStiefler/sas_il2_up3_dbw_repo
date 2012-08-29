// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DreamEnvXY.java

package com.maddox.il2.engine;

import com.maddox.JGP.Point3d;
import com.maddox.rts.Time;
import com.maddox.util.HashMapExt;
import com.maddox.util.HashMapXY16Hash;
import com.maddox.util.IntHashtable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.maddox.il2.engine:
//            DreamEnv, Actor, MsgDream, DreamEnvXY_IntArray, 
//            MsgDreamGlobal, ActorPos, Engine

public class DreamEnvXY extends com.maddox.il2.engine.DreamEnv
{

    public boolean isSleep(double d, double d1)
    {
        return isSleep((int)(d / 200D), (int)(d1 / 200D));
    }

    public boolean isSleep(com.maddox.JGP.Point3d point3d)
    {
        return isSleep(point3d.x, point3d.y);
    }

    public boolean isSleep(int i, int j)
    {
        return fireXY.get(keyXY(i, j)) <= 0;
    }

    private void doSquareLocalListeners(int i, int ai[], boolean flag)
    {
        for(int j = 0; j < i; j++)
        {
            int k = ai[j];
            com.maddox.util.HashMapExt hashmapext = listenerXY.get(k >> 16, (short)(k & 0xffff));
            if(hashmapext != null)
            {
                for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
                {
                    com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)entry.getKey();
                    if(com.maddox.il2.engine.Actor.isValid(actor) && !listenerChanges.containsKey(actor))
                        msgDream.send(flag, actor);
                }

            }
        }

    }

    private void makeArrayIndx(com.maddox.il2.engine.DreamEnvXY_IntArray dreamenvxy_intarray)
    {
        int i = dreamenvxy_intarray.size();
        int ai[] = dreamenvxy_intarray.array();
        xIndx.clear();
        yIndx.clear();
        for(int j = 0; j < i; j++)
        {
            int k = ai[j];
            xIndx.add((short)(k & 0xffff));
            yIndx.add(k >> 16);
        }

    }

    protected void doChanges()
    {
        updateTickCounter--;
        if(updateTickCounter > 0)
        {
            if(globalListener.size() > 0)
            {
                for(int i = 0; i < globalListener.size(); i++)
                {
                    com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)globalListener.get(i);
                    if(com.maddox.il2.engine.Actor.isValid(actor))
                        msgDreamGlobal.sendTick(actor, updateTicks, updateTickCounter);
                }

            }
            return;
        }
        updateTickCounter = updateTicks;
        updateFire();
        int j = squareSleep.size() + squareWakeup.size();
        if(j > 0)
        {
            int k = squareSleep.size();
            if(k > 0)
                doSquareLocalListeners(k, squareSleep.array(), false);
            k = squareWakeup.size();
            if(k > 0)
                doSquareLocalListeners(k, squareWakeup.array(), true);
        }
        if(listenerChanges.size() > 0)
        {
            com.maddox.util.HashMapExt hashmapext = listenerChanges;
            if(hashmapext != null)
            {
                for(java.util.Map.Entry entry = hashmapext.nextEntry(null); entry != null; entry = hashmapext.nextEntry(entry))
                {
                    com.maddox.il2.engine.Actor actor1 = (com.maddox.il2.engine.Actor)entry.getKey();
                    if(com.maddox.il2.engine.Actor.isValid(actor1))
                    {
                        boolean flag = entry.getValue() == wakeuped;
                        int j2 = keyXY(actor1.pos.getCurrentPoint());
                        boolean flag1 = fireXY.get(j2) > 0;
                        if(flag1 != flag)
                            msgDream.send(flag1, actor1);
                    }
                }

            }
            listenerChanges.clear();
        }
        if(j > 0)
        {
            if(globalListener.size() > 0)
            {
                int l = squareSleep.size();
                if(l > 0)
                {
                    makeArrayIndx(squareSleep);
                    for(int i1 = 0; i1 < globalListener.size(); i1++)
                    {
                        com.maddox.il2.engine.Actor actor2 = (com.maddox.il2.engine.Actor)globalListener.get(i1);
                        if(com.maddox.il2.engine.Actor.isValid(actor2))
                            msgDreamGlobal.send(actor2, false, l, xIndx.array(), yIndx.array());
                    }

                }
                l = squareWakeup.size();
                if(l > 0)
                {
                    makeArrayIndx(squareWakeup);
                    for(int j1 = 0; j1 < globalListener.size(); j1++)
                    {
                        com.maddox.il2.engine.Actor actor3 = (com.maddox.il2.engine.Actor)globalListener.get(j1);
                        if(com.maddox.il2.engine.Actor.isValid(actor3))
                            msgDreamGlobal.send(actor3, true, l, xIndx.array(), yIndx.array());
                    }

                }
            }
            squareSleep.clear();
            squareWakeup.clear();
        }
        if(resetGlobalListener.size() > 0)
        {
            int ai[] = fireXY.keyList();
            for(int k1 = 0; k1 < ai.length; k1++)
            {
                int l1 = ai[k1];
                if(l1 > 0x80000001)
                    squareWakeup.add(l1);
            }

            makeArrayIndx(squareWakeup);
            squareWakeup.clear();
            for(int i2 = 0; i2 < resetGlobalListener.size(); i2++)
            {
                com.maddox.il2.engine.Actor actor4 = (com.maddox.il2.engine.Actor)resetGlobalListener.get(i2);
                if(com.maddox.il2.engine.Actor.isValid(actor4))
                {
                    msgDreamGlobal.send(actor4, true, xIndx.size(), xIndx.array(), yIndx.array());
                    globalListener.add(actor4);
                }
            }

            resetGlobalListener.clear();
            xIndx.clear();
            yIndx.clear();
        }
    }

    protected void changedListenerPos(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        int i = (int)(point3d.x / 200D);
        int j = (int)(point3d.y / 200D);
        int k = (int)(point3d1.x / 200D);
        int l = (int)(point3d1.y / 200D);
        if(i != k || j != l)
        {
            listenerXY.remove(j, i, actor);
            listenerXY.put(l, k, actor, null);
            if(!listenerChanges.containsKey(actor))
            {
                int i1 = keyXY(i, j);
                if(fireXY.get(i1) > 0)
                    listenerChanges.put(actor, wakeuped);
                else
                    listenerChanges.put(actor, null);
            }
        }
    }

    protected void addListener(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.JGP.Point3d point3d = actor.pos.getCurrentPoint();
        int i = (int)(point3d.x / 200D);
        int j = (int)(point3d.y / 200D);
        listenerXY.put(j, i, actor, null);
        listenerChanges.put(actor, null);
    }

    protected void removeListener(com.maddox.il2.engine.Actor actor)
    {
        com.maddox.JGP.Point3d point3d = actor.pos.getCurrentPoint();
        int i = (int)(point3d.x / 200D);
        int j = (int)(point3d.y / 200D);
        listenerXY.remove(j, i, actor);
        listenerChanges.remove(actor);
    }

    protected void addGlobalListener(com.maddox.il2.engine.Actor actor)
    {
        resetGlobalListener.add(actor);
    }

    protected void removeGlobalListener(com.maddox.il2.engine.Actor actor)
    {
        int i = globalListener.indexOf(actor);
        if(i >= 0)
            globalListener.remove(i);
        i = resetGlobalListener.indexOf(actor);
        if(i >= 0)
            resetGlobalListener.remove(i);
    }

    public void resetGlobalListener(com.maddox.il2.engine.Actor actor)
    {
        removeGlobalListener(actor);
        addGlobalListener(actor);
    }

    private void updateFire()
    {
        if(!fireCenterXY.isEmpty())
        {
            int ai[] = fireCenterXY.keyList();
            int ai1[] = fireCenterXY.values();
            for(int i = 0; i < ai.length; i++)
            {
                int j = ai[i];
                if(j > 0x80000001)
                {
                    int k = ai1[i];
                    if(k != 0)
                    {
                        int l = (j >> 16) - fireSquares / 2;
                        int j1 = (short)(j & 0xffff) - fireSquares / 2;
                        for(int l1 = 0; l1 < fireSquares; l1++)
                        {
                            for(int j2 = 0; j2 < fireSquares; j2++)
                            {
                                int l2 = keyXY(j2 + j1, l1 + l);
                                int i3 = fireUpdateXY.getIndex(l2);
                                if(i3 >= 0)
                                    fireUpdateXY.setByIndex(i3, fireUpdateXY.getByIndex(i3) + k);
                                else
                                    fireUpdateXY.put(l2, fireXY.get(l2) + k);
                            }

                        }

                    }
                }
            }

            fireCenterXY.clear();
            if(!fireUpdateXY.isEmpty())
            {
                int ai2[] = fireUpdateXY.keyList();
                int ai3[] = fireUpdateXY.values();
                for(int i1 = 0; i1 < ai2.length; i1++)
                {
                    int k1 = ai2[i1];
                    if(k1 > 0x80000001)
                    {
                        int i2 = ai3[i1];
                        int k2 = fireXY.getIndex(k1);
                        if(k2 >= 0)
                        {
                            if(i2 == 0)
                            {
                                fireXY.removeByIndex(k2);
                                fireXY.validate();
                                squareSleep.add(k1);
                            } else
                            {
                                fireXY.setByIndex(k2, i2);
                            }
                        } else
                        if(i2 > 0)
                        {
                            fireXY.put(k1, i2);
                            squareWakeup.add(k1);
                        }
                    }
                }

                fireUpdateXY.clear();
            }
        }
    }

    protected void changedFirePos(com.maddox.il2.engine.Actor actor, com.maddox.JGP.Point3d point3d, com.maddox.JGP.Point3d point3d1)
    {
        int i = keyXY(point3d1);
        if(!fires.containsKey(actor))
        {
            fires.put(actor, null);
            fireCenterXY.put(i, fireCenterXY.get(i) + 1);
            return;
        }
        int j = keyXY(point3d);
        if(j == i)
        {
            return;
        } else
        {
            fireCenterXY.put(j, fireCenterXY.get(j) - 1);
            fireCenterXY.put(i, fireCenterXY.get(i) + 1);
            return;
        }
    }

    protected void addFire(com.maddox.il2.engine.Actor actor)
    {
        if(fires.containsKey(actor))
            return;
        fires.put(actor, null);
        int i = keyXY(actor.pos.getCurrentPoint());
        int j = fireCenterXY.get(i) + 1;
        fireCenterXY.put(i, j);
        if(j == 1)
            updateTickCounter = 0;
    }

    protected void removeFire(com.maddox.il2.engine.Actor actor)
    {
        if(!fires.containsKey(actor))
            return;
        fires.remove(actor);
        int i = keyXY(actor.pos.getCurrentPoint());
        int j = fireCenterXY.get(i) - 1;
        fireCenterXY.put(i, j);
        if(j == 0)
            updateTickCounter = 0;
    }

    public void resetGameClear()
    {
        java.util.ArrayList arraylist = new ArrayList(fires.keySet());
        com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        arraylist.addAll(globalListener);
        com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        arraylist.addAll(resetGlobalListener);
        com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        resetGlobalListener.addAll(globalListener);
        globalListener.clear();
        java.util.ArrayList arraylist1 = new ArrayList();
        listenerXY.allValues(arraylist1);
        for(int i = 0; i < arraylist1.size(); i++)
        {
            com.maddox.util.HashMapExt hashmapext = (com.maddox.util.HashMapExt)arraylist1.get(i);
            arraylist.addAll(hashmapext.keySet());
            com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        }

        arraylist1.clear();
    }

    public void resetGameCreate()
    {
        listenerChanges.clear();
        clearFire();
    }

    protected void clearFire()
    {
        fires.clear();
        fireXY.clear();
        fireCenterXY.clear();
        updateTickCounter = 0;
    }

    private int keyXY(com.maddox.JGP.Point3d point3d)
    {
        return (int)(point3d.x / 200D) & 0xffff | (int)(point3d.y / 200D) << 16;
    }

    private int keyXY(int i, int j)
    {
        return i & 0xffff | j << 16;
    }

    public DreamEnvXY()
    {
        msgDream = new MsgDream();
        xIndx = new DreamEnvXY_IntArray(32);
        yIndx = new DreamEnvXY_IntArray(32);
        msgDreamGlobal = new MsgDreamGlobal();
        wakeuped = new Object();
        listenerChanges = new HashMapExt();
        listenerXY = new HashMapXY16Hash(7);
        fires = new HashMapExt();
        fireXY = new IntHashtable(21, 0);
        fireCenterXY = new IntHashtable(21, 0);
        fireUpdateXY = new IntHashtable(21, 0);
        globalListener = new ArrayList();
        resetGlobalListener = new ArrayList();
        squareSleep = new DreamEnvXY_IntArray(32);
        squareWakeup = new DreamEnvXY_IntArray(32);
        fireSquares = 38;
        if(fireSquares < 3)
            fireSquares = 3;
        fireSquares |= 1;
        updateTicks = (int)(1.0D / (double)com.maddox.rts.Time.tickConstLenFs());
        if(updateTicks < 1)
            updateTicks = 1;
        updateTickCounter = 0;
    }

    private com.maddox.il2.engine.MsgDream msgDream;
    private com.maddox.il2.engine.DreamEnvXY_IntArray xIndx;
    private com.maddox.il2.engine.DreamEnvXY_IntArray yIndx;
    private com.maddox.il2.engine.MsgDreamGlobal msgDreamGlobal;
    private java.lang.Object wakeuped;
    private com.maddox.util.HashMapExt listenerChanges;
    private com.maddox.util.HashMapXY16Hash listenerXY;
    private com.maddox.util.HashMapExt fires;
    private com.maddox.util.IntHashtable fireXY;
    private com.maddox.util.IntHashtable fireCenterXY;
    private com.maddox.util.IntHashtable fireUpdateXY;
    private int fireSquares;
    private int updateTicks;
    private int updateTickCounter;
    private java.util.List globalListener;
    private java.util.List resetGlobalListener;
    private com.maddox.il2.engine.DreamEnvXY_IntArray squareSleep;
    private com.maddox.il2.engine.DreamEnvXY_IntArray squareWakeup;
}
