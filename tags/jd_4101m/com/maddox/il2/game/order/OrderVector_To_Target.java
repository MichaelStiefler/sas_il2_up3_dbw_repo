package com.maddox.il2.game.order;

import com.maddox.JGP.Point2d;
import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.ZutiSupportMethods;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.vehicles.radios.BeaconGeneric;
import java.util.Timer;
import java.util.TimerTask;

public class OrderVector_To_Target extends Order
{
  private static Point3d V1 = new Point3d();
  private static Point3d V2 = new Point3d();
  private double dist = 0.0D;

  public OrderVector_To_Target()
  {
    super("Vector_To_Target");
  }

  public void run()
  {
    if (Main.cur().mission.zutiRadar_DisableVectoring) {
      return;
    }
    if ((World.cur().diffCur.RealisticNavigationInstruments) && (!isLosToHomeBase())) {
      return;
    }
    if (Main.cur().netServerParams.isDogfight())
    {
      return;
    }

    Way localWay = Player().FM.AP.way;
    WayPoint localWayPoint1 = localWay.curr();
    localWayPoint1.getP(V2);
    for (int i = localWay.Cur(); i < localWay.size(); i++)
    {
      WayPoint localWayPoint2 = localWay.get(i);
      if ((localWayPoint2.Action == 3) || ((localWayPoint2.getTarget() != null) && (localWayPoint2.Action != 2))) {
        localWayPoint2.getP(V2);
      }
    }

    Player().FM.actor.pos.getAbs(V1);
    V1.sub(V2);

    if (isEnableVoice())
    {
      if (World.cur().diffCur.RealisticNavigationInstruments)
        new DelayedOrder(Player());
      else
        Voice.speakHeadingToTarget(Player(), V1);
    }
  }

  private boolean isLosToHomeBase()
  {
    Player().FM.moveCarrier();
    Point3d localPoint3d = Player().FM.actor.pos.getAbsPoint();
    Object localObject;
    if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isDogfight()))
    {
      localObject = ZutiSupportMethods.getPlayerBornPlace(localPoint3d, Player().getArmy());
      if (localObject != null)
        V2 = new Point3d(((BornPlace)localObject).place.x, ((BornPlace)localObject).place.y, 0.0D);
      else {
        return false;
      }
    }
    else
    {
      localObject = Player().FM.AP.way;
      WayPoint localWayPoint = ((Way)localObject).get(((Way)localObject).size() - 1);
      localWayPoint.getP(V2);
    }
    Player().FM.actor.pos.getAbs(V1);
    V1.sub(V2);

    this.dist = Math.sqrt(V1.x * V1.x + V1.y * V1.y);
    double d = BeaconGeneric.lineOfSightDelta(Player().FM.getAltitude(), V2.z, this.dist);

    return (d >= 0.0D) && (this.dist <= 90000.0D);
  }

  private class DelayedOrder extends TimerTask
  {
    private Timer timer = null;
    private Aircraft ac = null;

    public DelayedOrder(Aircraft arg2)
    {
      Object localObject;
      this.ac = localObject;
      this.timer = new Timer();
      long l = ()Math.random() * ()(OrderVector_To_Target.this.dist / 6.0D);
      this.timer.schedule(this, 10000L + l);
    }

    public void run()
    {
      Voice.speakHeadingToTarget(this.ac, OrderVector_To_Target.V1);
    }
  }
}