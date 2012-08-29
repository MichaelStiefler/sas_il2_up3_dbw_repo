package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.air.AirGroup;
import com.maddox.il2.ai.air.AirGroupList;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.MsgOwnerListener;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.SectFile;

public class Wing extends Chief
  implements MsgOwnerListener
{
  public Aircraft[] airc = new Aircraft[4];
  public Way way = new Way();

  private static Vector3d tmpSpeed = new Vector3d();
  private static Point3d tmpPoint0 = new Point3d();
  private static Point3d tmpPoint1 = new Point3d();

  private static Vector3d zeroSpeed = new Vector3d();
  private static Point3d pGround = new Point3d();
  private static Orient oGround = new Orient();
  private static Vector3d vGround = new Vector3d();

  public int aircReady()
  {
    int i = 0;
    for (int j = 0; j < this.airc.length; j++)
      if (Actor.isValid(this.airc[j]))
        i++;
    return i;
  }

  public int aircIndex(Aircraft paramAircraft)
  {
    String str = paramAircraft.name();
    int i = str.charAt(str.length() - 1);
    return i - 48;
  }

  public Regiment regiment() {
    return squadron().regiment();
  }

  public Squadron squadron() {
    return (Squadron)getOwner();
  }

  public int indexInSquadron()
  {
    int i = name().charAt(name().length() - 1);
    return i - 48;
  }

  public void msgOwnerDied(Actor paramActor) {
    if (getDiedFlag()) return;
    for (int i = 0; i < this.airc.length; i++)
      if ((Actor.isValid(this.airc[i])) && (this.airc[i].isAlive()))
        return;
    World.onActorDied(this, null);
  }
  public void msgOwnerTaskComplete(Actor paramActor) {
    for (int i = 0; i < this.airc.length; i++)
      if ((Actor.isValid(this.airc[i])) && (!this.airc[i].isTaskComplete()))
        return;
    World.onTaskComplete(this);
  }
  public void msgOwnerAttach(Actor paramActor) {
  }
  public void msgOwnerDetach(Actor paramActor) {
  }
  public void msgOwnerChange(Actor paramActor1, Actor paramActor2) {
  }
  public void destroy() { for (int i = 0; i < this.airc.length; i++)
      this.airc[i] = null;
    super.destroy(); }

  public Object getSwitchListener(Message paramMessage)
  {
    return this;
  }

  public void load(SectFile paramSectFile, String paramString, NetChannel paramNetChannel)
    throws Exception
  {
    int i = paramNetChannel != null ? 1 : 0;
    if (paramSectFile.sectionIndex(paramString) < 0) {
      destroy();
      throw new Exception("Mission: section '" + paramString + "' not found");
    }

    String str1 = paramSectFile.get(paramString, "Class", (String)null);
    if (str1 == null) {
      destroy();
      throw new Exception("Mission: in section '" + paramString + "' class aircraft not defined");
    }

    String str2 = paramString + "_Way";
    if (paramSectFile.sectionIndex(str2) < 0) {
      destroy();
      throw new Exception("Mission: section '" + str2 + "' not found");
    }

    if (Actor.getByName(paramString) != null) {
      destroy();
      throw new Exception("Mission: dublicate wing '" + paramString + "'");
    }

    setName(paramString);
    int j = indexInSquadron();
    if ((j < 0) || (j > 3)) {
      throw new RuntimeException("Wing '" + paramString + "' NOT valid");
    }
    Squadron localSquadron = Squadron.New(paramString.substring(0, paramString.length() - 1));
    setOwner(localSquadron);
    localSquadron.wing[indexInSquadron()] = this;
    setArmy(localSquadron.getArmy());

    Aircraft.loadingCountry = localSquadron.regiment().country();

    int k = paramSectFile.get(paramString, "Planes", 1, 1, 4);
    try
    {
      this.way.load(paramSectFile, str2);

      for (int m = 0; m < k; m++) {
        this.airc[m] = Mission.cur().loadAir(paramSectFile, str1, paramString, paramString + m, m);
        this.airc[m].setArmy(getArmy());
        this.airc[m].checkTurretSkill();
        this.airc[m].setOwner(this);
        if (this.airc[m] == World.getPlayerAircraft())
          World.setPlayerRegiment();
        this.airc[m].preparePaintScheme();
        this.airc[m].prepareCamouflage();
        setPosAndSpeed(this.airc[m], this.way);
      }
      setArmy(this.airc[0].getArmy());
      Formation.generate(this.airc);

      for (m = 0; m < k; m++) {
        this.airc[m].FM.AP.way = new Way(this.way);
      }

      if (((Mission.isSingle()) || (((Mission.isCoop()) || (Mission.isDogfight())) && (Mission.isServer()))) && (!NetMissionTrack.isPlaying()))
      {
        AirGroup localAirGroup = new AirGroup(this.airc[0].getSquadron(), this.way);
        for (int n = 0; n < k; n++) localAirGroup.addAircraft(this.airc[n]);
        AirGroupList.addAirGroup(War.Groups, this.airc[0].getArmy() - 1 & 0x1, localAirGroup);
      }

      Aircraft.loadingCountry = null;
    } catch (Exception localException) {
      Aircraft.loadingCountry = null;
      destroy();
      throw localException;
    }
  }

  private void setPosAndSpeed(Aircraft paramAircraft, Way paramWay)
  {
    WayPoint localWayPoint1;
    if (paramWay.size() == 1) {
      localWayPoint1 = paramWay.get(0);
      localWayPoint1.getP(tmpPoint0);
      paramAircraft.pos.setAbs(tmpPoint0);
      tmpSpeed.set(localWayPoint1.Speed, 0.0D, 0.0D);
      paramAircraft.setSpeed(tmpSpeed);
    } else {
      localWayPoint1 = paramWay.get(0);
      WayPoint localWayPoint2 = paramWay.get(1);
      localWayPoint1.getP(tmpPoint0);
      localWayPoint2.getP(tmpPoint1);
      tmpSpeed.sub(tmpPoint1, tmpPoint0);
      tmpSpeed.normalize();
      _tmpOrient.setAT0(tmpSpeed);
      tmpSpeed.scale(localWayPoint1.Speed);
      paramAircraft.pos.setAbs(tmpPoint0, _tmpOrient);
      paramAircraft.setSpeed(tmpSpeed);
    }
    paramAircraft.pos.reset();
  }

  public void setOnAirport()
  {
    WayPoint localWayPoint = this.way.get(0);
    localWayPoint.getP(tmpPoint0);
    int i;
    if (localWayPoint.Action == 1) {
      i = 0;
      Object localObject;
      if (localWayPoint.sTarget != null) {
        localObject = localWayPoint.getTarget();
        if ((localObject != null) && ((localObject instanceof BigshipGeneric))) {
          AirportCarrier localAirportCarrier = ((BigshipGeneric)localObject).getAirport();
          if (Actor.isValid(localAirportCarrier)) {
            localAirportCarrier.setTakeoff(tmpPoint0, this.airc);
            i = 1;
          }
        }
      }
      if (i == 0) {
        localObject = Airport.nearest(tmpPoint0, getArmy(), 3);
        if (localObject != null) {
          double d = ((Airport)localObject).pos.getAbsPoint().distance(tmpPoint0);
          if (d < 1250.0D)
            ((Airport)localObject).setTakeoff(tmpPoint0, this.airc);
          else
            setonground();
        } else {
          setonground();
        }
      }
    } else {
      for (i = 0; i < 4; i++) {
        if (!Actor.isValid(this.airc[i]))
          continue;
        if (this.airc[i] == World.getPlayerAircraft()) {
          this.airc[i].FM.EI.setCurControlAll(true);
          this.airc[i].FM.EI.setEngineRunning();
          this.airc[i].FM.CT.setPowerControl(0.75F);
        }
        this.airc[i].FM.setStationedOnGround(false);
        this.airc[i].FM.setWasAirborne(true);
      }
    }
  }

  private void setonground()
  {
    for (int i = 0; i < this.airc.length; i++)
      if (this.airc[i] != null) {
        this.airc[i].pos.getAbs(pGround, oGround);
        pGround.z = (World.land().HQ(pGround.x, pGround.y) + this.airc[i].FM.Gears.H);
        Engine.land().N(pGround.x, pGround.y, vGround);
        oGround.orient(vGround);
        oGround.increment(0.0F, this.airc[i].FM.Gears.Pitch, 0.0F);
        this.airc[i].setOnGround(pGround, oGround, zeroSpeed);
      }
  }
}