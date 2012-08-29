package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.vehicles.stationary.SmokeGeneric;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetChannelInStream;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetObj;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class AirportGround extends AirportStatic
{
  private ArrayList runwayLights = null;
  private boolean lightsOn = false;
  private Aircraft acThatRequestedLights = null;
  private boolean aircraftIsTakingOff = false;
  private boolean canTurnOffLights = false;
  private boolean canTurnOnLights = false;
  private int randomDelay = 0;
  private long timeOfLightsOn = 0L;
  private final int maxLightsOnTimeMs = 90000;

  public AirportGround()
  {
    this.net = null;
    if ((Mission.cur() != null) && (
      (!NetMissionTrack.isPlaying()) || (NetMissionTrack.playingOriginalVersion() > 102))) {
      int i = Mission.cur().getUnitNetIdRemote(this);
      NetChannel localNetChannel = Mission.cur().getNetMasterChannel();
      if (localNetChannel == null) {
        this.net = new Master(this);
      }
      else if (i != 0)
        this.net = new Mirror(this, (NetChannel)localNetChannel, i);
    }
  }

  protected void update()
  {
    super.update();

    if (Mission.cur() == null)
      return;
    if ((NetMissionTrack.isPlaying()) && (NetMissionTrack.playingOriginalVersion() <= 102))
      return;
    if (this.net.isMirror())
      return;
    if (this.lightsOn)
    {
      if (this.canTurnOffLights)
      {
        if (this.randomDelay < 0)
          turnOnLights(false);
        else
          this.randomDelay -= 1;
      }
      else if ((this.acThatRequestedLights != null) && (this.aircraftIsTakingOff))
      {
        if ((!this.acThatRequestedLights.FM.Gears.onGround()) || (!this.acThatRequestedLights.isAlive()))
        {
          this.randomDelay = (300 + World.Rnd().nextInt(800));
          this.canTurnOffLights = true;
        }
        else if (Time.current() > this.timeOfLightsOn + 90000L)
        {
          this.canTurnOffLights = true;
          turnOnLights(false);
        }
      }
      else if ((this.acThatRequestedLights != null) && (!this.aircraftIsTakingOff))
      {
        if ((this.acThatRequestedLights.FM.Gears.onGround()) || (!this.acThatRequestedLights.isAlive()))
        {
          this.randomDelay = (300 + World.Rnd().nextInt(800));
          this.canTurnOffLights = true;
        }
        else if (Time.current() > this.timeOfLightsOn + 90000L)
        {
          this.canTurnOffLights = true;
          turnOnLights(false);
        }
      }
      else if (Time.current() > this.timeOfLightsOn + 90000L)
      {
        this.canTurnOffLights = true;
        turnOnLights(false);
      }

    }
    else if (this.canTurnOnLights)
    {
      if (this.randomDelay < 0)
      {
        this.canTurnOnLights = false;
        turnOnLights(true);
        this.timeOfLightsOn = Time.current();
      }
      else {
        this.randomDelay -= 1;
      }
    }
  }

  public boolean hasLights()
  {
    return (this.runwayLights != null) && (this.runwayLights.size() > 0);
  }

  public void addLights(SmokeGeneric paramSmokeGeneric)
  {
    if (this.runwayLights == null)
      this.runwayLights = new ArrayList();
    this.runwayLights.add(paramSmokeGeneric);
    paramSmokeGeneric.setArmy(0);
  }

  private void turnOnLights(boolean paramBoolean)
  {
    this.lightsOn = paramBoolean;
    if (!this.net.isMirror())
      master_sendLights(this.lightsOn);
    if (this.runwayLights != null)
    {
      for (int i = 0; i < this.runwayLights.size(); i++)
      {
        SmokeGeneric localSmokeGeneric = (SmokeGeneric)this.runwayLights.get(i);
        localSmokeGeneric.setVisible(paramBoolean);
      }
    }
  }

  public void turnOnLights(Aircraft paramAircraft)
  {
    if (this.net.isMirror()) {
      mirror_sendLights(paramAircraft);
      return;
    }
    this.canTurnOnLights = true;
    this.canTurnOffLights = false;
    this.acThatRequestedLights = paramAircraft;

    if ((this.acThatRequestedLights != null) && (this.acThatRequestedLights.FM.Gears.onGround()))
      this.aircraftIsTakingOff = true;
    else {
      this.aircraftIsTakingOff = false;
    }
    this.randomDelay = (200 + World.Rnd().nextInt(200));
  }

  public void netFirstUpdate(NetChannel paramNetChannel)
    throws IOException
  {
    try
    {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(80);
      localNetMsgGuaranted.writeBoolean(this.lightsOn);
      this.net.postTo(paramNetChannel, localNetMsgGuaranted);
    }
    catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
      throw new RuntimeException("Airport lights: NetFirstUpdate failed");
    }
  }

  private boolean master_sendLights(boolean paramBoolean) {
    if (this.net.isMirror())
      return false;
    try {
      NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
      localNetMsgGuaranted.writeByte(80);
      localNetMsgGuaranted.writeBoolean(paramBoolean);
      this.net.post(localNetMsgGuaranted);
      return true;
    }
    catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }return false;
  }

  private boolean mirror_sendLights(Aircraft paramAircraft)
  {
    if ((!this.net.isMirror()) || ((this.net.masterChannel() instanceof NetChannelInStream)))
      return false;
    try {
      NetMsgFiltered localNetMsgFiltered = null;
      localNetMsgFiltered = new NetMsgFiltered();
      localNetMsgFiltered.writeByte(81);
      localNetMsgFiltered.writeNetObj(paramAircraft == null ? null : paramAircraft.net);
      localNetMsgFiltered.setIncludeTime(false);
      this.net.postTo(NetServerParams.getServerTime(), this.net.masterChannel(), localNetMsgFiltered);
      return true;
    }
    catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }return false;
  }

  class Mirror extends ActorNet
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4)
    {
      super(paramInt, i);
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        int i = paramNetMsgInput.readUnsignedByte();

        if (i == 80) {
          boolean bool = paramNetMsgInput.readBoolean();
          AirportGround.this.turnOnLights(bool);
        }
        return true;
      }

      return true;
    }
  }

  class Master extends ActorNet
  {
    public Master(Actor arg2)
    {
      super();
    }
    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        return true;
      }
      if (paramNetMsgInput.readUnsignedByte() == 81) {
        NetObj localNetObj = paramNetMsgInput.readNetObj();
        AirportGround.access$002(AirportGround.this, localNetObj == null ? null : (Aircraft)(Aircraft)((ActorNet)localNetObj).actor());
        AirportGround.this.turnOnLights(AirportGround.this.acThatRequestedLights);
        return true;
      }
      return false;
    }
  }
}