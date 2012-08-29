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

public class Engine
{
  public static Engine cur;
  public static final boolean CHECK_DESTROY_ACTORS = false;
  public Landscape land;
  public World world = new World();
  public Actor actorLand;
  public BulletGeneric bulletList;
  public LightEnv lightEnv = new LightEnvXY(this.world.sun());

  public DrawEnv drawEnv = new DrawEnvXY();

  public CollideEnv collideEnv = new CollideEnvXY();

  public DreamEnv dreamEnv = new DreamEnvXY();

  protected ArrayList targets = new ArrayList();

  private Acoustics worldAcoustics = null;

  protected ActorSoundListener soundListener = null;
  private Renders rendersMain;
  protected InterpolateAdapter interpolateAdapter = new InterpolateAdapter();

  private ArrayList actorDestroyListeners = new ArrayList();

  protected EngineProfile profile = new EngineProfile();

  private ArrayList queueDestroyActors = new ArrayList();

  protected HashMapExt name2Actor = new HashMapExt();

  protected HashMapExt allEff3DActors = new HashMapExt();

  protected HashMapExt allActors = new HashMapExt();

  protected ArrayList posChanged = new ArrayList();

  public static boolean isServer()
  {
    return NetEnv.isServer();
  }

  public static Landscape land()
  {
    return cur.land;
  }

  public static Actor actorLand()
  {
    return cur.actorLand;
  }

  public static LightEnv lightEnv()
  {
    return cur.lightEnv;
  }

  public static DrawEnv drawEnv()
  {
    return cur.drawEnv;
  }

  public static CollideEnv collideEnv()
  {
    return cur.collideEnv;
  }

  public static DreamEnv dreamEnv()
  {
    return cur.dreamEnv;
  }

  public static List targets()
  {
    return cur.targets;
  }

  public static void setWorldAcoustics(String paramString) {
    cur.worldAcoustics = new Acoustics(paramString);
  }
  public static Acoustics worldAcoustics() {
    return cur.worldAcoustics;
  }

  public static ActorSoundListener soundListener()
  {
    return cur.soundListener;
  }

  public static Renders rendersMain()
  {
    return cur.rendersMain;
  }

  public static InterpolateAdapter interpolateAdapter()
  {
    return cur.interpolateAdapter;
  }

  protected void actorDestroyed(Actor paramActor)
  {
    for (int i = 0; i < this.actorDestroyListeners.size(); i++)
      ((ActorDestroyListener)this.actorDestroyListeners.get(i)).actorDestroyed(paramActor);
  }

  public void addActorDestroyListener(ActorDestroyListener paramActorDestroyListener) {
    if (!this.actorDestroyListeners.contains(paramActorDestroyListener))
      this.actorDestroyListeners.add(paramActorDestroyListener); 
  }

  public void removeActorDestroyListener(ActorDestroyListener paramActorDestroyListener) {
    int i = this.actorDestroyListeners.indexOf(paramActorDestroyListener);
    if (i >= 0)
      this.actorDestroyListeners.remove(i);
  }

  public static void processPostDestroyActors()
  {
    int i = cur.queueDestroyActors.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)cur.queueDestroyActors.get(j);
      if (Actor.isValid(localActor))
        localActor.destroy();
    }
    cur.queueDestroyActors.clear();
  }

  public static void postDestroyActor(Actor paramActor)
  {
    cur.queueDestroyActors.add(paramActor);
  }

  public static HashMapExt name2Actor()
  {
    return cur.name2Actor;
  }

  public static void destroyListGameActors(List paramList)
  {
    int i = paramList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)paramList.get(j);
      if ((!Actor.isValid(localActor)) || ((localActor.flags & 0x4000) != 0)) continue;
      try {
        localActor.destroy();
      } catch (Exception localException) {
        printDebug(localException);
      }
    }

    paramList.clear();
  }

  private void dectroyMsgActors(Message[] paramArrayOfMessage) {
    if (paramArrayOfMessage == null) return;
    for (int i = 0; i < paramArrayOfMessage.length; i++) {
      if (paramArrayOfMessage[i] == null) break;
      Object localObject = paramArrayOfMessage[i].listener();
      if ((localObject instanceof Actor)) {
        Actor localActor = (Actor)localObject;
        if ((Actor.isValid(localActor)) && ((localActor.flags & 0x4000) == 0)) {
          try {
            localActor.destroy();
          } catch (Exception localException) {
            printDebug(localException);
          }
        }
      }
      paramArrayOfMessage[i] = null;
    }
  }

  public void resetGameClear() {
    while (this.bulletList != null) {
      localObject = this.bulletList;
      this.bulletList = ((BulletGeneric)localObject).nextBullet;
      ((BulletGeneric)localObject).destroy();
      ((BulletGeneric)localObject).nextBullet = null;
    }
    this.targets.clear();
    Object localObject = new ArrayList();
    cur.drawEnv.resetGameClear();
    cur.collideEnv.resetGameClear();
    cur.dreamEnv.resetGameClear();
    cur.interpolateAdapter.resetGameClear();
    this.actorLand.destroy();
    ((ArrayList)localObject).addAll(this.allEff3DActors.keySet());
    destroyListGameActors((List)localObject);
    ((ArrayList)localObject).addAll(this.name2Actor.values());
    destroyListGameActors((List)localObject);
    MeshShared.clearAll();
    cur.world.resetGameClear();

    dectroyMsgActors(RTSConf.cur.queue.toArray());
    dectroyMsgActors(RTSConf.cur.queueNextTick.toArray());
    processPostDestroyActors();

    GObj.DeleteCppObjects();
  }

  public void resetGameCreate() {
    Actor.resetActorGameHashCodes();
    cur.world.resetGameCreate();
    cur.collideEnv.resetGameCreate();
    cur.drawEnv.resetGameCreate();
    cur.lightEnv.clear();
    cur.dreamEnv.resetGameCreate();
    cur.interpolateAdapter.resetGameCreate();

    this.actorLand = new ActorLand();
    this.soundListener = new ActorSoundListener();
    this.soundListener.initDraw();
  }

  public Engine() {
    cur = this;
    this.rendersMain = new Renders();
    this.land = new Landscape();
    this.actorLand = new ActorLand();
    this.soundListener = new ActorSoundListener();
  }

  protected static void printDebug(Exception paramException) {
    System.out.println(paramException.getMessage());
    paramException.printStackTrace();
  }
}