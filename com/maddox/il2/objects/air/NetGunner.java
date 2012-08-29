package com.maddox.il2.objects.air;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.ActorPosMove;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetUser;
import com.maddox.rts.Message;
import com.maddox.rts.MsgInvokeMethod_Object;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.Spawn;
import com.maddox.rts.Time;
import com.maddox.util.IntHashtable;
import java.io.IOException;
import java.io.PrintStream;

public class NetGunner extends Actor
{
  private NetUser user;
  private String aircraftName;
  private Aircraft aircraft;
  private IntHashtable filterTable;
  protected int netCockpitIndxPilot = 0;
  protected int netCockpitWeaponControlNum = 0;
  protected int netCockpitTuretNum = -1;
  protected boolean netCockpitValid = false;
  protected NetMsgGuaranted netCockpitMsg = null;
  private boolean bFirstAirCheck = true;

  public IntHashtable getFilterTable() {
    if (this.filterTable == null)
      this.filterTable = new IntHashtable();
    return this.filterTable;
  }

  public String getAircraftName() {
    return this.aircraftName;
  }

  public NetUser getUser() {
    return this.user;
  }

  public Aircraft getAircraft() {
    checkAircraft();
    return this.aircraft;
  }

  public int getCockpitNum() {
    return this.netCockpitIndxPilot;
  }

  private boolean isMirroredAsAir() {
    if (this.aircraft.net == null) return false;
    int i = this.aircraft.net.countMirrors();
    if (this.aircraft.net.isMirror()) i++;
    int j = this.net.countMirrors();
    if (this.net.isMirror()) j++;
    return i == j;
  }

  private boolean checkAircraft() {
    if (Actor.isValid(this.aircraft)) {
      return isMirroredAsAir();
    }
    this.aircraft = ((Aircraft)Actor.getByName(this.aircraftName));
    if (!Actor.isValid(this.aircraft)) return false;
    if (!isMirroredAsAir()) {
      this.aircraft = null;
      return false;
    }
    this.pos.setBase(this.aircraft, null, false);
    this.pos.resetAsBase();
    setArmy(this.aircraft.getArmy());
    this.user.setArmy(getArmy());
    setOwner(this.aircraft);

    if ((isNetMaster()) || (this.user.isTrackWriter())) {
      World.cur().resetUser();
      World.setPlayerAircraft(this.aircraft);
      World.setPlayerFM();
      World.setPlayerRegiment();

      this.aircraft.createCockpits();
      CockpitGunner localCockpitGunner = (CockpitGunner)Main3D.cur3D().cockpits[getCockpitNum()];
      Main3D.cur3D().cockpitCur = localCockpitGunner;

      this.aircraft.FM.AS.astatePlayerIndex = localCockpitGunner.astatePilotIndx();
      if (!this.user.isTrackWriter())
        this.aircraft.netCockpitEnter(this, getCockpitNum(), this.bFirstAirCheck);
      this.bFirstAirCheck = false;
      Main3D.cur3D().cockpitCur.focusEnter();

      if (!this.user.isTrackWriter()) {
        localCockpitGunner.setRealMode(true);
        this.aircraft.netCockpitAuto(this, getCockpitNum(), false);
      }
    }
    this.user.tryPreparePilot(this.aircraft, this.aircraft.netCockpitAstatePilotIndx(getCockpitNum()));
    return true;
  }

  public void netFirstUpdate(NetChannel paramNetChannel) throws IOException {
    doNetFirstUpdate(paramNetChannel);
  }

  public void doNetFirstUpdate(Object paramObject) {
    if (isDestroyed()) return;
    NetChannel localNetChannel = (NetChannel)paramObject;
    if (localNetChannel.isDestroyed()) return;
    if ((!checkAircraft()) || (!localNetChannel.isMirrored(this.aircraft.net))) {
      if ((Actor.isValid(this.aircraft)) && (this.aircraft.net.masterChannel() == localNetChannel))
        return;
      new MsgInvokeMethod_Object("doNetFirstUpdate", localNetChannel).post(72, this, 0.0D);
      return;
    }
    try {
      this.aircraft.netCockpitFirstUpdate(this, localNetChannel);
    } catch (Exception localException) {
      System.out.println(localException.getMessage());
      localException.printStackTrace();
    }
  }

  int packSY(float paramFloat)
  {
    return 0xFFFF & (int)((paramFloat % 360.0D + 180.0D) * 65000.0D / 360.0D);
  }
  int packSP(float paramFloat) {
    return 0x7FFF & (int)((paramFloat % 360.0D + 180.0D) * 32000.0D / 360.0D);
  }

  float unpackSY(int paramInt) {
    return (float)(paramInt * 360.0D / 65000.0D - 180.0D);
  }
  float unpackSP(int paramInt) {
    return (float)(paramInt * 360.0D / 32000.0D - 180.0D);
  }

  public NetGunner(String paramString, NetUser paramNetUser, int paramInt1, int paramInt2)
  {
    this.aircraftName = paramString;
    this.user = paramNetUser;
    this.netCockpitIndxPilot = paramInt2;
    String str = " " + paramString + "(" + paramInt2 + ")";
    Actor.destroy(Actor.getByName(str));
    setName(str);
    this.pos = new ActorPosMove(this);
    if (paramNetUser.isMaster())
      this.net = new Master(this);
    else {
      this.net = new Mirror(this, paramNetUser.masterChannel(), paramInt1);
    }

    if ((paramNetUser.isMaster()) || (paramNetUser.isTrackWriter()))
      World.setPlayerGunner(this); 
  }

  public NetMsgSpawn netReplicate(NetChannel paramNetChannel) throws IOException {
    NetMsgSpawn localNetMsgSpawn = super.netReplicate(paramNetChannel);
    localNetMsgSpawn.write255(this.aircraftName);
    localNetMsgSpawn.writeNetObj(this.user);
    localNetMsgSpawn.writeByte(getCockpitNum());
    return localNetMsgSpawn;
  }

  static
  {
    Spawn.add(NetGunner.class, new SPAWN());
  }

  static class SPAWN
    implements NetSpawn
  {
    public void netSpawn(int paramInt, NetMsgInput paramNetMsgInput)
    {
      try
      {
        String str = paramNetMsgInput.read255();
        NetUser localNetUser = (NetUser)paramNetMsgInput.readNetObj();
        int i = paramNetMsgInput.readUnsignedByte();
        if (localNetUser != null)
          new NetGunner(str, localNetUser, paramInt, i);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
    }
  }

  class Mirror extends ActorNet
    implements NetUpdate
  {
    NetMsgFiltered out = new NetMsgFiltered();
    long lastUpdateTime = Time.current();

    public void netUpdate() {
      if ((Actor.isValid(NetGunner.this.aircraft)) && (NetGunner.this.netCockpitTuretNum >= 0) && (Time.current() - this.lastUpdateTime > 2000L))
        NetGunner.this.aircraft.FM.CT.WeaponControl[NetGunner.this.netCockpitWeaponControlNum] = false;
    }

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      if (paramNetMsgInput.isGuaranted()) {
        return false;
      }
      if (isMirrored()) {
        this.out.unLockAndSet(paramNetMsgInput, 0);
        postReal(Message.currentTime(true), this.out);
      }
      if ((NetGunner.this.checkAircraft()) && (NetGunner.this.netCockpitTuretNum >= 0)) {
        int i = paramNetMsgInput.readUnsignedShort();
        int j = paramNetMsgInput.readUnsignedShort();
        float f1 = NetGunner.this.unpackSY(i);
        float f2 = NetGunner.this.unpackSP(j & 0x7FFF);
        NetGunner.this.aircraft.FM.CT.WeaponControl[NetGunner.this.netCockpitWeaponControlNum] = ((j & 0x8000) != 0 ? 1 : false);
        if ((NetMissionTrack.isPlaying()) && (NetGunner.this.aircraft == World.getPlayerAircraft())) {
          Actor._tmpOrient.set(f1, f2, 0.0F);
          ((CockpitGunner)Main3D.cur3D().cockpits[NetGunner.this.getCockpitNum()]).moveGun(Actor._tmpOrient);
        } else {
          Turret localTurret = NetGunner.this.aircraft.FM.turret[NetGunner.this.netCockpitTuretNum];
          localTurret.tu[0] = f1;
          localTurret.tu[1] = f2;
        }
        this.lastUpdateTime = Time.current();
      }
      return true;
    }
    public Mirror(Actor paramNetChannel, NetChannel paramInt, int arg4) {
      super(paramInt, i);
      try { this.out.setFilterArg(paramNetChannel);
      }
      catch (Exception localException)
      {
      }
    }
  }

  class Master extends ActorNet
    implements NetUpdate
  {
    NetMsgFiltered out = new NetMsgFiltered();

    public boolean netInput(NetMsgInput paramNetMsgInput) throws IOException {
      return false;
    }

    public void netUpdate() {
      if (!Actor.isValid(NetGunner.this.aircraft)) {
        NetGunner.this.checkAircraft();
        return;
      }
      if ((NetGunner.this.netCockpitValid) && (NetGunner.this.netCockpitTuretNum >= 0))
        try {
          Turret localTurret = NetGunner.this.aircraft.FM.turret[NetGunner.this.netCockpitTuretNum];
          int i = NetGunner.this.aircraft.FM.CT.WeaponControl[NetGunner.this.netCockpitWeaponControlNum];
          this.out.unLockAndClear();
          this.out.writeShort(NetGunner.this.packSY(localTurret.tu[0]));
          this.out.writeShort(NetGunner.this.packSP(localTurret.tu[1]) | (i != 0 ? 32768 : 0));
          post(Time.current(), this.out); } catch (Exception localException) {
          printDebug(localException);
        }
    }

    public Master(Actor arg2) {
      super();
      try { this.out.setFilterArg(localActor);
      }
      catch (Exception localException)
      {
      }
    }
  }
}