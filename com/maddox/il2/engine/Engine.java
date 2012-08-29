// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Engine.java

package com.maddox.il2.engine;

import com.maddox.il2.ai.World;
import com.maddox.il2.objects.ActorLand;
import com.maddox.rts.Message;
import com.maddox.rts.MessageQueue;
import com.maddox.rts.NetEnv;
import com.maddox.rts.RTSConf;
import com.maddox.sound.Acoustics;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.maddox.il2.engine:
//            ActorDestroyListener, Actor, ActorSoundListener, LightEnvXY, 
//            DrawEnvXY, CollideEnvXY, DreamEnvXY, InterpolateAdapter, 
//            EngineProfile, Renders, Landscape, BulletGeneric, 
//            DrawEnv, CollideEnv, DreamEnv, MeshShared, 
//            GObj, LightEnv

public class Engine
{

    public static boolean isServer()
    {
        return com.maddox.rts.NetEnv.isServer();
    }

    public static com.maddox.il2.engine.Landscape land()
    {
        return cur.land;
    }

    public static com.maddox.il2.engine.Actor actorLand()
    {
        return cur.actorLand;
    }

    public static com.maddox.il2.engine.LightEnv lightEnv()
    {
        return cur.lightEnv;
    }

    public static com.maddox.il2.engine.DrawEnv drawEnv()
    {
        return cur.drawEnv;
    }

    public static com.maddox.il2.engine.CollideEnv collideEnv()
    {
        return cur.collideEnv;
    }

    public static com.maddox.il2.engine.DreamEnv dreamEnv()
    {
        return cur.dreamEnv;
    }

    public static java.util.List targets()
    {
        return cur.targets;
    }

    public static void setWorldAcoustics(java.lang.String s)
    {
        cur.worldAcoustics = new Acoustics(s);
    }

    public static com.maddox.sound.Acoustics worldAcoustics()
    {
        return cur.worldAcoustics;
    }

    public static com.maddox.il2.engine.ActorSoundListener soundListener()
    {
        return cur.soundListener;
    }

    public static com.maddox.il2.engine.Renders rendersMain()
    {
        return cur.rendersMain;
    }

    public static com.maddox.il2.engine.InterpolateAdapter interpolateAdapter()
    {
        return cur.interpolateAdapter;
    }

    protected void actorDestroyed(com.maddox.il2.engine.Actor actor)
    {
        for(int i = 0; i < actorDestroyListeners.size(); i++)
            ((com.maddox.il2.engine.ActorDestroyListener)actorDestroyListeners.get(i)).actorDestroyed(actor);

    }

    public void addActorDestroyListener(com.maddox.il2.engine.ActorDestroyListener actordestroylistener)
    {
        if(!actorDestroyListeners.contains(actordestroylistener))
            actorDestroyListeners.add(actordestroylistener);
    }

    public void removeActorDestroyListener(com.maddox.il2.engine.ActorDestroyListener actordestroylistener)
    {
        int i = actorDestroyListeners.indexOf(actordestroylistener);
        if(i >= 0)
            actorDestroyListeners.remove(i);
    }

    public static void processPostDestroyActors()
    {
        int i = cur.queueDestroyActors.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)cur.queueDestroyActors.get(j);
            if(com.maddox.il2.engine.Actor.isValid(actor))
                actor.destroy();
        }

        cur.queueDestroyActors.clear();
    }

    public static void postDestroyActor(com.maddox.il2.engine.Actor actor)
    {
        cur.queueDestroyActors.add(actor);
    }

    public static com.maddox.util.HashMapExt name2Actor()
    {
        return cur.name2Actor;
    }

    public static void destroyListGameActors(java.util.List list)
    {
        int i = list.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)list.get(j);
            if(!com.maddox.il2.engine.Actor.isValid(actor) || (actor.flags & 0x4000) != 0)
                continue;
            try
            {
                actor.destroy();
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.il2.engine.Engine.printDebug(exception);
            }
        }

        list.clear();
    }

    private void dectroyMsgActors(com.maddox.rts.Message amessage[])
    {
        if(amessage == null)
            return;
        for(int i = 0; i < amessage.length && amessage[i] != null; i++)
        {
            java.lang.Object obj = amessage[i].listener();
            if(obj instanceof com.maddox.il2.engine.Actor)
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)obj;
                if(com.maddox.il2.engine.Actor.isValid(actor) && (actor.flags & 0x4000) == 0)
                    try
                    {
                        actor.destroy();
                    }
                    catch(java.lang.Exception exception)
                    {
                        com.maddox.il2.engine.Engine.printDebug(exception);
                    }
            }
            amessage[i] = null;
        }

    }

    public void resetGameClear()
    {
        while(bulletList != null) 
        {
            com.maddox.il2.engine.BulletGeneric bulletgeneric = bulletList;
            bulletList = bulletgeneric.nextBullet;
            bulletgeneric.destroy();
            bulletgeneric.nextBullet = null;
        }
        targets.clear();
        java.util.ArrayList arraylist = new ArrayList();
        cur.drawEnv.resetGameClear();
        cur.collideEnv.resetGameClear();
        cur.dreamEnv.resetGameClear();
        cur.interpolateAdapter.resetGameClear();
        actorLand.destroy();
        arraylist.addAll(allEff3DActors.keySet());
        com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        arraylist.addAll(name2Actor.values());
        com.maddox.il2.engine.Engine.destroyListGameActors(arraylist);
        com.maddox.il2.engine.MeshShared.clearAll();
        cur.world.resetGameClear();
        dectroyMsgActors(com.maddox.rts.RTSConf.cur.queue.toArray());
        dectroyMsgActors(com.maddox.rts.RTSConf.cur.queueNextTick.toArray());
        com.maddox.il2.engine.Engine.processPostDestroyActors();
        com.maddox.il2.engine.GObj.DeleteCppObjects();
    }

    public void resetGameCreate()
    {
        com.maddox.il2.engine.Actor.resetActorGameHashCodes();
        cur.world.resetGameCreate();
        cur.collideEnv.resetGameCreate();
        cur.drawEnv.resetGameCreate();
        cur.lightEnv.clear();
        cur.dreamEnv.resetGameCreate();
        cur.interpolateAdapter.resetGameCreate();
        actorLand = new ActorLand();
        soundListener = new ActorSoundListener();
        soundListener.initDraw();
    }

    public Engine()
    {
        world = new World();
        lightEnv = new LightEnvXY(world.sun());
        drawEnv = new DrawEnvXY();
        collideEnv = new CollideEnvXY();
        dreamEnv = new DreamEnvXY();
        targets = new ArrayList();
        worldAcoustics = null;
        soundListener = null;
        interpolateAdapter = new InterpolateAdapter();
        actorDestroyListeners = new ArrayList();
        profile = new EngineProfile();
        queueDestroyActors = new ArrayList();
        name2Actor = new HashMapExt();
        allEff3DActors = new HashMapExt();
        allActors = new HashMapExt();
        posChanged = new ArrayList();
        cur = this;
        rendersMain = new Renders();
        land = new Landscape();
        actorLand = new ActorLand();
        soundListener = new ActorSoundListener();
    }

    protected static void printDebug(java.lang.Exception exception)
    {
        java.lang.System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    public static com.maddox.il2.engine.Engine cur;
    public static final boolean CHECK_DESTROY_ACTORS = false;
    public com.maddox.il2.engine.Landscape land;
    public com.maddox.il2.ai.World world;
    public com.maddox.il2.engine.Actor actorLand;
    public com.maddox.il2.engine.BulletGeneric bulletList;
    public com.maddox.il2.engine.LightEnv lightEnv;
    public com.maddox.il2.engine.DrawEnv drawEnv;
    public com.maddox.il2.engine.CollideEnv collideEnv;
    public com.maddox.il2.engine.DreamEnv dreamEnv;
    protected java.util.ArrayList targets;
    private com.maddox.sound.Acoustics worldAcoustics;
    protected com.maddox.il2.engine.ActorSoundListener soundListener;
    private com.maddox.il2.engine.Renders rendersMain;
    protected com.maddox.il2.engine.InterpolateAdapter interpolateAdapter;
    private java.util.ArrayList actorDestroyListeners;
    protected com.maddox.il2.engine.EngineProfile profile;
    private java.util.ArrayList queueDestroyActors;
    protected com.maddox.util.HashMapExt name2Actor;
    protected com.maddox.util.HashMapExt allEff3DActors;
    protected com.maddox.util.HashMapExt allActors;
    protected java.util.ArrayList posChanged;
}
