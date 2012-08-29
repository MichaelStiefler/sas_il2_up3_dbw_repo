package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3f;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.ai.air.AutopilotAI;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FMMath;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Mission;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.il2.objects.sounds.Voice;
import com.maddox.il2.objects.weapons.GuidedMissileUtils;
import com.maddox.il2.objects.weapons.GunNull;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.il2.objects.weapons.RocketGunWithDelay;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;
import java.util.ArrayList;

public class TU_4 extends B_29X
  implements TypeBomber, TypeGuidedMissileCarrier, TypeX4Carrier
{
  public static boolean bChangedPit = false;
  private boolean bSightAutomation;
  private boolean bSightBombDump;
  private float fSightCurDistance;
  public float fSightCurForwardAngle;
  public float fSightCurSideslip;
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurReadyness;
  private ArrayList rocketsList;
  private GuidedMissileUtils guidedMissileUtils = null;
  private float deltaAzimuth;
  private float deltaTangage;
  private float trgtPk = 0.0F;
  private Actor trgtAI = null;
  public boolean bToFire;
  private long tX4Prev;
  private boolean aiLaunchMissile = false;

  static
  {
    Class class1 = TU_4.class;
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "iconFar_shortClassName", "TU-4");
    Property.set(class1, "meshName", "3DO/Plane/Tu-4/hier.him");
    Property.set(class1, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(class1, "yearService", 1947.0F);
    Property.set(class1, "yearExpired", 1960.0F);
    Property.set(class1, "cockpitClass", new Class[] { 
      CockpitTU4.class, CockpitTU4_Bombardier.class, CockpitTU4_TGunner.class, CockpitTU4_T2Gunner.class, CockpitTU4_FGunner.class, CockpitTU4_AGunner.class, CockpitTU4_RGunner.class });

    Property.set(class1, "FlightModel", "FlightModels/B-29.fmd");
    Property.set(class1, "MultiTrackingCapable", 0);
    Aircraft.weaponTriggersRegister(class1, new int[] { 
      10, 10, 11, 11, 12, 12, 13, 13, 14, 14, 
      3, 3, 3, 3, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(class1, new String[] { 
      "_MGUN01", "_MGUN02", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_MGUN11", "_MGUN12", 
      "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02" });

    Aircraft.weaponsRegister(class1, "default", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315" });

    Aircraft.weaponsRegister(class1, "48XFAB-100", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
      "BombGunFAB100 12", "BombGunFAB100 12", "BombGunFAB100 12", "BombGunFAB100 12" });

    Aircraft.weaponsRegister(class1, "48XFAB-50", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
      "BombGunFAB50 12", "BombGunFAB50 12", "BombGunFAB50 12", "BombGunFAB50 12" });

    Aircraft.weaponsRegister(class1, "24XFAB-250M-46", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
      "BombGunFAB250m46 6", "BombGunFAB250m46 6", "BombGunFAB250m46 6", "BombGunFAB250m46 6" });

    Aircraft.weaponsRegister(class1, "24XFAB-250", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
      "BombGunFAB250int 6", "BombGunFAB250int 6", "BombGunFAB250int 6", "BombGunFAB250int 6" });

    Aircraft.weaponsRegister(class1, "14XFAB-500", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
      "BombGunFAB500int 4", "BombGunFAB500int 4", "BombGunFAB500int 3", "BombGunFAB500int 3" });

    Aircraft.weaponsRegister(class1, "8XFAB-1000", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
      "BombGunFAB1000int 2", "BombGunFAB1000int 2", "BombGunFAB1000int 2", "BombGunFAB1000int 2" });

    Aircraft.weaponsRegister(class1, "1xRDS-4T", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
      "BombGunRDS4T 1" });

    Aircraft.weaponsRegister(class1, "2xKS-1", new String[] { 
      "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", "MGunVYakNS23 315", 
      null, null, null, null, "RocketGunKS1 1", "RocketGunNull 1", "RocketGunKS1 1", "RocketGunNull 1" });

    Aircraft.weaponsRegister(class1, "none", new String[17]);
  }

  public TU_4()
  {
    this.bSightAutomation = false;
    this.bSightBombDump = false;
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
    this.fSightCurAltitude = 850.0F;
    this.fSightCurSpeed = 200.0F;
    this.fSightCurReadyness = 0.0F;
    this.rocketsList = new ArrayList();
    this.guidedMissileUtils = new GuidedMissileUtils(this);
    this.deltaAzimuth = 0.0F;
    this.deltaTangage = 0.0F;
    this.bToFire = false;
    this.tX4Prev = 0L;
  }

  protected boolean cutFM(int i, int j, Actor actor)
  {
    switch (i)
    {
    case 19:
      killPilot(this, 4);
    }

    return super.cutFM(i, j, actor);
  }

  public void rareAction(float f, boolean flag)
  {
    super.rareAction(f, flag);
    for (int i = 1; i < 7; i++)
      if (this.FM.getAltitude() < 3000.0F)
        hierMesh().chunkVisible("HMask" + i + "_D0", false);
      else
        hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
//    ((com.maddox.il2.objects.weapons.RocketGunKS1)this.rocketsList.get(0)).getMissile().createSmokeTest();
  }

  protected void nextDMGLevel(String s, int i, Actor actor)
  {
    super.nextDMGLevel(s, i, actor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String s, int i, Actor actor)
  {
    super.nextCUTLevel(s, i, actor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  public boolean turretAngles(int i, float[] af)
  {
    boolean flag = super.turretAngles(i, af);
    float f = -af[0];
    float f1 = af[1];
    switch (i)
    {
    default:
      break;
    case 0:
      if (f < -23.0F)
      {
        f = -23.0F;
        flag = false;
      }
      if (f > 23.0F)
      {
        f = 23.0F;
        flag = false;
      }
      if (f1 < -25.0F)
      {
        f1 = -25.0F;
        flag = false;
      }
      if (f1 <= 15.0F)
        break;
      f1 = 15.0F;
      flag = false;

      break;
    case 1:
      if (f1 < 0.0F)
      {
        f1 = 0.0F;
        flag = false;
      }
      if (f1 <= 73.0F)
        break;
      f1 = 73.0F;
      flag = false;

      break;
    case 2:
      if (f < -38.0F)
      {
        f = -38.0F;
        flag = false;
      }
      if (f > 38.0F)
      {
        f = 38.0F;
        flag = false;
      }
      if (f1 < -41.0F)
      {
        f1 = -41.0F;
        flag = false;
      }
      if (f1 <= 43.0F)
        break;
      f1 = 43.0F;
      flag = false;

      break;
    case 3:
      if (f < -85.0F)
      {
        f = -85.0F;
        flag = false;
      }
      if (f > 22.0F)
      {
        f = 22.0F;
        flag = false;
      }
      if (f1 < -40.0F)
      {
        f1 = -40.0F;
        flag = false;
      }
      if (f1 <= 32.0F)
        break;
      f1 = 32.0F;
      flag = false;

      break;
    case 4:
      if (f < -34.0F)
      {
        f = -34.0F;
        flag = false;
      }
      if (f > 30.0F)
      {
        f = 30.0F;
        flag = false;
      }
      if (f1 < -30.0F)
      {
        f1 = -30.0F;
        flag = false;
      }
      if (f1 <= 32.0F)
        break;
      f1 = 32.0F;
      flag = false;
    }

    af[0] = (-f);
    af[1] = f1;
    return flag;
  }

  public void doKillPilot(int i)
  {
    switch (i)
    {
    case 2:
      this.FM.turret[0].bIsOperable = false;
      break;
    case 3:
      this.FM.turret[1].bIsOperable = false;
      break;
    case 4:
      this.FM.turret[2].bIsOperable = false;
      break;
    case 5:
      this.FM.turret[3].bIsOperable = false;
      this.FM.turret[4].bIsOperable = false;
    }
  }

  public boolean typeBomberToggleAutomation()
  {
    this.bSightAutomation = (!this.bSightAutomation);
    this.bSightBombDump = false;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAutomation" + (this.bSightAutomation ? "ON" : "OFF"));
    return this.bSightAutomation;
  }

  public void typeBomberAdjDistanceReset()
  {
    this.fSightCurDistance = 0.0F;
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus()
  {
    this.fSightCurForwardAngle += 1.0F;
    if (this.fSightCurForwardAngle > 85.0F)
      this.fSightCurForwardAngle = 85.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { 
      new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 1.0F;
    if (this.fSightCurForwardAngle < -15.0F)
      this.fSightCurForwardAngle = -15.0F;
    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { 
      new Integer((int)this.fSightCurForwardAngle) });

    if (this.bSightAutomation)
      typeBomberToggleAutomation();
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus()
  {
    this.fSightCurSideslip += 0.1F;
    if (this.fSightCurSideslip > 3.0F)
      this.fSightCurSideslip = 3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { 
      new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 0.1F;
    if (this.fSightCurSideslip < -3.0F)
      this.fSightCurSideslip = -3.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { 
      new Integer((int)(this.fSightCurSideslip * 10.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 850.0F;
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F)
      this.fSightCurAltitude = 10000.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { 
      new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 850.0F)
      this.fSightCurAltitude = 850.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { 
      new Integer((int)this.fSightCurAltitude) });

    this.fSightCurDistance = (this.fSightCurAltitude * (float)Math.tan(Math.toRadians(this.fSightCurForwardAngle)));
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 150.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 10.0F;
    if (this.fSightCurSpeed > 800.0F)
      this.fSightCurSpeed = 800.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { 
      new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 10.0F;
    if (this.fSightCurSpeed < 150.0F)
      this.fSightCurSpeed = 150.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { 
      new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float f)
  {
    if (Math.abs(this.FM.Or.getKren()) > 4.5D)
    {
      this.fSightCurReadyness -= 0.0666666F * f;
      if (this.fSightCurReadyness < 0.0F)
        this.fSightCurReadyness = 0.0F;
    }
    if (this.fSightCurReadyness < 1.0F) {
      this.fSightCurReadyness += 0.0333333F * f;
    }
    else if (this.bSightAutomation)
    {
      this.fSightCurDistance -= this.fSightCurSpeed / 3.6F * f;
      if (this.fSightCurDistance < 0.0F)
      {
        this.fSightCurDistance = 0.0F;
        typeBomberToggleAutomation();
      }
      this.fSightCurForwardAngle = (float)Math.toDegrees(Math.atan(this.fSightCurDistance / this.fSightCurAltitude));
      if (this.fSightCurDistance < this.fSightCurSpeed / 3.6F * Math.sqrt(this.fSightCurAltitude * 0.203874F))
        this.bSightBombDump = true;
      if (this.bSightBombDump)
        if (this.FM.isTick(3, 0))
        {
          if ((this.FM.CT.Weapons[3] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)] != null) && (this.FM.CT.Weapons[3][(this.FM.CT.Weapons[3].length - 1)].haveBullets()))
          {
            this.FM.CT.WeaponControl[3] = true;
            HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightBombdrop");
          }
        }
        else
          this.FM.CT.WeaponControl[3] = false;
    }
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
    throws IOException
  {
    netmsgguaranted.writeByte((this.bSightAutomation ? 1 : 0) | (this.bSightBombDump ? 2 : 0));
    netmsgguaranted.writeFloat(this.fSightCurDistance);
    netmsgguaranted.writeByte((int)this.fSightCurForwardAngle);
    netmsgguaranted.writeByte((int)((this.fSightCurSideslip + 3.0F) * 33.333328F));
    netmsgguaranted.writeFloat(this.fSightCurAltitude);
    netmsgguaranted.writeByte((int)(this.fSightCurSpeed / 2.5F));
    netmsgguaranted.writeByte((int)(this.fSightCurReadyness * 200.0F));
  }

  public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
    throws IOException
  {
    int i = netmsginput.readUnsignedByte();
    this.bSightAutomation = ((i & 0x1) != 0);
    this.bSightBombDump = ((i & 0x2) != 0);
    this.fSightCurDistance = netmsginput.readFloat();
    this.fSightCurForwardAngle = netmsginput.readUnsignedByte();
    this.fSightCurSideslip = (-3.0F + netmsginput.readUnsignedByte() / 33.333328F);
    this.fSightCurAltitude = netmsginput.readFloat();
    this.fSightCurSpeed = (netmsginput.readUnsignedByte() * 2.5F);
    this.fSightCurReadyness = (netmsginput.readUnsignedByte() / 200.0F);
  }

  private void setGunNullOwner()
  {
    try {
      for (int l = 0; l < this.FM.CT.Weapons.length; l++)
        if (this.FM.CT.Weapons[l] != null)
          for (int l1 = 0; l1 < this.FM.CT.Weapons[l].length; l1++) {
            if ((this.FM.CT.Weapons[l][l1] == null) || 
              (!(this.FM.CT.Weapons[l][l1] instanceof GunNull))) continue;
            ((GunNull)this.FM.CT.Weapons[l][l1]).setOwner(this);
          }
    }
    catch (Exception localException)
    {
    }
  }

  public Actor getMissileTarget()
  {
    return this.guidedMissileUtils.getMissileTarget();
  }

  public Point3f getMissileTargetOffset() {
    return this.guidedMissileUtils.getSelectedActorOffset();
  }

  public boolean hasMissiles()
  {
    return !this.rocketsList.isEmpty();
  }

  public void shotMissile()
  {
    if (hasMissiles()) {
      if (NetMissionTrack.isPlaying() || Mission.isNet()) {
//        EventLog.type("shotMissile 1");
        if ((!(this.FM instanceof RealFlightModel)) || (!((RealFlightModel)this.FM).isRealMode())) {
          ((RocketGun)this.rocketsList.get(0)).loadBullets(0);
//          EventLog.type("shotMissile 11");
        } else if (World.cur().diffCur.Limited_Ammo) {
          ((RocketGun)this.rocketsList.get(0)).loadBullets(0);
//          EventLog.type("shotMissile 12,list size = " + this.rocketsList.size());
        }
      }
      
      if ((World.cur().diffCur.Limited_Ammo) || (this != World.getPlayerAircraft())) {
//        ((RocketGun)this.rocketsList.get(0)).loadBullets(0);
        this.rocketsList.remove(0);
//        EventLog.type("shotMissile 2");
      }
      if (this != World.getPlayerAircraft()) {
        Voice.speakAttackByRockets(this);
//        EventLog.type("shotMissile 3");
      }
//      com.maddox.il2.game.HUD.log("Remains: " + this.rocketsList.size());
    }
  }

  public int getMissileLockState()
  {
    return this.guidedMissileUtils.getMissileLockState();
  }

  private float getMissilePk()
  {
    float thePk = 0.0F;
    this.guidedMissileUtils.setMissileTarget(this.guidedMissileUtils.lookForGuidedMissileTargetShip(this.FM.actor, this.guidedMissileUtils.getMaxPOVfrom(), this.guidedMissileUtils.getMaxPOVto(), 
      this.guidedMissileUtils.getPkMaxDist()));
    if (Actor.isValid(this.guidedMissileUtils.getMissileTarget())) {
      thePk = this.guidedMissileUtils.Pk(this.FM.actor, this.guidedMissileUtils.getMissileTarget());
    }

    return thePk;
  }

  private void checkAIlaunchMissile() {
    if ((((this.FM instanceof RealFlightModel)) && 
      (((RealFlightModel)this.FM).isRealMode())) || 
      (!(this.FM instanceof Pilot))) {
      return;
    }
    if (this.rocketsList.isEmpty()) {
      return;
    }
    Pilot pilot = (Pilot)this.FM;
//    if (((pilot.get_maneuver() == 27) || (pilot.get_maneuver() == 62) || (pilot.get_maneuver() == 63)) && 
//      (pilot.target != null))
//    {
//      this.trgtAI = pilot.target.actor;
//      if ((!Actor.isValid(this.trgtAI)) || (!(this.trgtAI instanceof Aircraft))) {
//        return;
//      }
      this.bToFire = false;
//      ((Maneuver)this.FM).overrideMissileControl = false;
      if (this.FM.AP instanceof AutopilotAI) {
        ((AutopilotAI)this.FM.AP).setOverrideMissileControl(this.FM.CT, false);
      }

//      com.maddox.il2.game.HUD.training("trgtPk = " + this.trgtPk);
      if ((this.trgtPk > 25.0F) && 
        (Actor.isValid(this.guidedMissileUtils.getMissileTarget())) && 
        (this.guidedMissileUtils.getMissileTarget().getArmy() != this.FM.actor.getArmy()) && 
        (Time.current() > this.tX4Prev + 10000L))
      {
        this.bToFire = true;
        this.tX4Prev = Time.current();
        this.FM.CT.WeaponControl[2] = true;
//        ((Maneuver)this.FM).overrideMissileControl = true;
      if (this.FM.AP instanceof AutopilotAI) {
        ((AutopilotAI)this.FM.AP).setOverrideMissileControl(this.FM.CT, true);
      }
        this.aiLaunchMissile = true;
//        HUD.log("AI missile launch");
//        shootRocket();
//        this.bToFire = false;
      }
//    }
  }

  public void shootRocket()
  {
    if (this.rocketsList.isEmpty()) {
      return;
    }
//    com.maddox.il2.game.HUD.log("Shoot!");
    ((RocketGun)this.rocketsList.get(0)).shots(1);
//    this.rocketsList.remove(0);
  }
  
  private void checkPendingMissiles() {
    if (this.rocketsList.isEmpty()) return;
    if (this.rocketsList.get(0) instanceof RocketGunWithDelay) {
      ((RocketGunWithDelay)this.rocketsList.get(0)).checkPendingWeaponRelease();
    }
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.rocketsList.clear();
    this.guidedMissileUtils.createMissileList(this.rocketsList);
    setGunNullOwner();
  }

  public void update(float f1) {
    this.checkAIlaunchMissile();
    super.update(f1);
//    if (this.aiLaunchMissile) {
//      this.FM.CT.WeaponControl[2] = true;
//      this.aiLaunchMissile = false;
//    }
    this.checkPendingMissiles();
    this.trgtPk = getMissilePk();
    this.guidedMissileUtils.checkLockStatus();
  }

  public void typeX4CAdjSidePlus()
  {
    this.deltaAzimuth = 1.0F;
  }

  public void typeX4CAdjSideMinus()
  {
    this.deltaAzimuth = -1.0F;
  }

  public void typeX4CAdjAttitudePlus()
  {
    this.deltaTangage = 1.0F;
  }

  public void typeX4CAdjAttitudeMinus()
  {
    this.deltaTangage = -1.0F;
  }

  public void typeX4CResetControls()
  {
    this.deltaAzimuth = (this.deltaTangage = 0.0F);
  }

  public float typeX4CgetdeltaAzimuth()
  {
    return this.deltaAzimuth;
  }

  public float typeX4CgetdeltaTangage()
  {
    return this.deltaTangage;
  }
}

/* Location:           U:\IL2\Classes_1956_12\
 * Qualified Name:     com.maddox.il2.objects.air.TU_4
 * JD-Core Version:    0.6.0
 */