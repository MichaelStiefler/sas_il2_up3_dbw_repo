package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.util.NumberTokenizer;

public class Target extends Actor
{
  public static final int PRIMARY = 0;
  public static final int SECONDARY = 1;
  public static final int SECRET = 2;
  public static final int DESTROY = 0;
  public static final int DESTROY_GROUND = 1;
  public static final int DESTROY_BRIDGE = 2;
  public static final int INSPECT = 3;
  public static final int ESCORT = 4;
  public static final int DEFENCE = 5;
  public static final int DEFENCE_GROUND = 6;
  public static final int DEFENCE_BRIDGE = 7;
  protected int importance;
  protected long timeout;
  private static int _counterStaticActors;
  private static StaticActorsFilter staticActorsFilter = new StaticActorsFilter();

  public int importance()
  {
    return this.importance;
  }
  protected boolean checkActorDied(Actor paramActor) { return false; } 
  protected boolean checkTaskComplete(Actor paramActor) { return false; } 
  protected boolean checkPeriodic() { return false; } 
  protected boolean checkTimeoutOff() { return false; }

  protected Target(int paramInt1, int paramInt2) {
    this.importance = paramInt1;
    if (paramInt2 >= 0) this.timeout = (paramInt2 * 60L * 1000L); else {
      this.timeout = -1L;
    }
    World.cur().targetsGuard.addTarget(this);
  }

  public static final void create(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return;
    NumberTokenizer localNumberTokenizer = new NumberTokenizer(paramString);
    int i = localNumberTokenizer.next(0, 0, 7);
    int j = localNumberTokenizer.next(0, 0, 2);
    int k = localNumberTokenizer.next(0) == 1 ? 1 : 0;
    int m = localNumberTokenizer.next(0, 0, 720);
    if (k == 0) m = -1;
    int n = localNumberTokenizer.next(0);
    boolean bool = (n & 0x1) == 1;
    n /= 10;
    if (n < 0) n = 0;
    if (n > 100) n = 100;
    int i1 = localNumberTokenizer.next(0);
    int i2 = localNumberTokenizer.next(0);
    int i3 = localNumberTokenizer.next(1000, 2, 3000);
    int i4 = localNumberTokenizer.next(0);
    String str = localNumberTokenizer.next(null);
    if ((str != null) && (str.startsWith("Bridge")))
      str = " " + str;
    switch (i) {
    case 0:
      new TDestroy(j, m, str, n);
      break;
    case 1:
      new TDestroyGround(j, m, i1, i2, i3, n);
      break;
    case 2:
      new TDestroyBridge(j, m, str);
      break;
    case 3:
      new TInspect(j, m, i1, i2, i3, bool, str);
      break;
    case 4:
    case 5:
      new TEscort(j, m, str, n);
      break;
    case 6:
      new TDefenceGround(j, m, i1, i2, i3, n);
      break;
    case 7:
      new TDefenceBridge(j, m, str);
    }
  }

  protected static int countStaticActors(Point3d paramPoint3d, double paramDouble)
  {
    _counterStaticActors = 0;
    Engine.cur.collideEnv.getFiltered(null, paramPoint3d, paramDouble, staticActorsFilter);
    return _counterStaticActors;
  }

  protected static boolean isStaticActor(Actor paramActor) {
    if (paramActor.getArmy() == 0) return false;
    if ((paramActor instanceof Aircraft)) return false;
    if ((paramActor instanceof Chief)) return false;
    if ((paramActor instanceof Wing)) return false;
    return (!Actor.isValid(paramActor.getOwner())) || (!(paramActor.getOwner() instanceof Chief));
  }

  static class StaticActorsFilter implements ActorFilter
  {
    public boolean isUse(Actor paramActor, double paramDouble)
    {
      if (!paramActor.isAlive()) return true;
      if (!Target.isStaticActor(paramActor)) return true;
      Target.access$008();
      return true;
    }
  }
}