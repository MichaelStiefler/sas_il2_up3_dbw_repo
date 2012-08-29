package com.maddox.il2.game;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.GUIWindowManager;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.hotkey.HookGunner;
import com.maddox.il2.engine.hotkey.HookPilot;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.gui.GUI;
import com.maddox.il2.gui.GUIChatDialog;
import com.maddox.il2.gui.GUIPad;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.air.A6M;
import com.maddox.il2.objects.air.A6M5C;
import com.maddox.il2.objects.air.A6M7_62;
import com.maddox.il2.objects.air.A_20;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.BLENHEIM;
import com.maddox.il2.objects.air.B_17;
import com.maddox.il2.objects.air.B_24;
import com.maddox.il2.objects.air.B_25;
import com.maddox.il2.objects.air.B_29X;
import com.maddox.il2.objects.air.Cockpit;
import com.maddox.il2.objects.air.CockpitGunner;
import com.maddox.il2.objects.air.CockpitPilot;
import com.maddox.il2.objects.air.DO_335;
import com.maddox.il2.objects.air.F84G1;
import com.maddox.il2.objects.air.F9F;
import com.maddox.il2.objects.air.FW_200;
import com.maddox.il2.objects.air.HE_111H2;
import com.maddox.il2.objects.air.Halifax;
import com.maddox.il2.objects.air.Hurricane;
import com.maddox.il2.objects.air.IL_10;
import com.maddox.il2.objects.air.IL_2;
import com.maddox.il2.objects.air.IL_4;
import com.maddox.il2.objects.air.JU_88A4;
import com.maddox.il2.objects.air.KI_21;
import com.maddox.il2.objects.air.ME_210;
import com.maddox.il2.objects.air.ME_210CA1ZSTR;
import com.maddox.il2.objects.air.MOSQUITO;
import com.maddox.il2.objects.air.MOSQUITOS;
import com.maddox.il2.objects.air.P2V;
import com.maddox.il2.objects.air.PE_2;
import com.maddox.il2.objects.air.PE_8;
import com.maddox.il2.objects.air.P_51;
import com.maddox.il2.objects.air.P_51Mustang;
import com.maddox.il2.objects.air.Paratrooper;
import com.maddox.il2.objects.air.R_10;
import com.maddox.il2.objects.air.SB;
import com.maddox.il2.objects.air.SM79;
import com.maddox.il2.objects.air.SPITFIRE;
import com.maddox.il2.objects.air.SPITFIRE12;
import com.maddox.il2.objects.air.SPITFIRE14C;
import com.maddox.il2.objects.air.SPITFIRELF14E;
import com.maddox.il2.objects.air.SU_2;
import com.maddox.il2.objects.air.TBF;
import com.maddox.il2.objects.air.TB_3;
import com.maddox.il2.objects.air.TEMPEST;
import com.maddox.il2.objects.air.TU_2S;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.il2.objects.air.TypeFighterAceMaker;
import com.maddox.il2.objects.air.TypeX4Carrier;
import com.maddox.il2.objects.air.YAK_9B;
import com.maddox.il2.objects.effects.ForceFeedback;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CmdEnv;
import com.maddox.rts.Console;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdMouseMove;
import com.maddox.rts.HotKeyCmdMove;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Joy;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetEnv;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.RTSConf;
import com.maddox.rts.SectFile;
import com.maddox.rts.Time;
import com.maddox.sound.AudioDevice;
import com.maddox.sound.CfgNpFlags;
import com.maddox.sound.CmdMusic;
import com.maddox.sound.RadioChannel;
import com.maddox.util.HashMapExt;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class AircraftHotKeys
{
  public static boolean bFirstHotCmd = false;
  private RealFlightModel FM;
  private boolean bPropAuto;
  private boolean bAfterburner;
  private float lastPower;
  private float lastProp;
  private static final int MOVE_POWER = 1;
  private static final int MOVE_FLAPS = 2;
  private static final int MOVE_AILERON = 3;
  private static final int MOVE_ELEVATOR = 4;
  private static final int MOVE_RUDDER = 5;
  private static final int MOVE_BRAKE = 6;
  private static final int MOVE_STEP = 7;
  private static final int MOVE_TRIMHOR = 8;
  private static final int MOVE_TRIMVER = 9;
  private static final int MOVE_TRIMRUD = 10;
  private int cptdmg;
  private static final int BRAKE = 0;
  private static final int RUDDER_LEFT = 1;
  private static final int RUDDER_RIGHT = 2;
  private static final int ELEVATOR_UP = 3;
  private static final int ELEVATOR_DOWN = 4;
  private static final int AILERON_LEFT = 5;
  private static final int AILERON_RIGHT = 6;
  private static final int RADIATOR = 7;
  private static final int GEAR = 9;
  private static final int GUNPODS = 15;
  private static final int WEAPON0 = 16;
  private static final int WEAPON1 = 17;
  private static final int WEAPON2 = 18;
  private static final int WEAPON3 = 19;
  private static final int WEAPON01 = 64;
  private static final int POWER_0 = 20;
  private static final int POWER_1 = 21;
  private static final int POWER_2 = 22;
  private static final int POWER_3 = 23;
  private static final int POWER_4 = 24;
  private static final int POWER_5 = 25;
  private static final int POWER_6 = 26;
  private static final int POWER_7 = 27;
  private static final int POWER_8 = 28;
  private static final int POWER_9 = 29;
  private static final int POWER_10 = 30;
  private static final int STEP_0 = 31;
  private static final int STEP_1 = 32;
  private static final int STEP_2 = 33;
  private static final int STEP_3 = 34;
  private static final int STEP_4 = 35;
  private static final int STEP_5 = 36;
  private static final int STEP_6 = 37;
  private static final int STEP_7 = 38;
  private static final int STEP_8 = 39;
  private static final int STEP_9 = 40;
  private static final int STEP_10 = 41;
  private static final int STEP_A = 42;
  private static final int AIRCRAFT_TRIM_V_0 = 43;
  private static final int AIRCRAFT_TRIM_V_PLUS = 44;
  private static final int AIRCRAFT_TRIM_V_MINUS = 45;
  private static final int AIRCRAFT_TRIM_H_0 = 46;
  private static final int AIRCRAFT_TRIM_H_PLUS = 47;
  private static final int AIRCRAFT_TRIM_H_MINUS = 48;
  private static final int AIRCRAFT_TRIM_R_0 = 49;
  private static final int AIRCRAFT_TRIM_R_PLUS = 50;
  private static final int AIRCRAFT_TRIM_R_MINUS = 51;
  private static final int FLAPS_NOTCH_UP = 52;
  private static final int FLAPS_NOTCH_DOWN = 53;
  private static final int GEAR_UP_MANUAL = 54;
  private static final int GEAR_DOWN_MANUAL = 55;
  private static final int RUDDER_LEFT_1 = 56;
  private static final int RUDDER_CENTRE = 57;
  private static final int RUDDER_RIGHT_1 = 58;
  private static final int POWER_PLUS_5 = 59;
  private static final int POWER_MINUS_5 = 60;
  private static final int BOOST = 61;
  private static final int AIRCRAFT_DROP_TANKS = 62;
  private static final int AIRCRAFT_TOGGLE_AIRBRAKE = 63;
  private static final int AIRCRAFT_TOGGLE_ENGINE = 70;
  private static final int AIRCRAFT_STABILIZER = 71;
  private static final int AIRCRAFT_TAILWHEELLOCK = 72;
  private static final int STEP_PLUS_5 = 73;
  private static final int STEP_MINUS_5 = 74;
  private static final int MIX_0 = 75;
  private static final int MIX_1 = 76;
  private static final int MIX_2 = 77;
  private static final int MIX_3 = 78;
  private static final int MIX_4 = 79;
  private static final int MIX_5 = 80;
  private static final int MIX_6 = 81;
  private static final int MIX_7 = 82;
  private static final int MIX_8 = 83;
  private static final int MIX_9 = 84;
  private static final int MIX_10 = 85;
  private static final int MIX_PLUS_20 = 86;
  private static final int MIX_MINUS_20 = 87;
  private static final int MAGNETO_PLUS_1 = 88;
  private static final int MAGNETO_MINUS_1 = 89;
  private static final int ENGINE_SELECT_ALL = 90;
  private static final int ENGINE_SELECT_NONE = 91;
  private static final int ENGINE_SELECT_LEFT = 92;
  private static final int ENGINE_SELECT_RIGHT = 93;
  private static final int ENGINE_SELECT_1 = 94;
  private static final int ENGINE_SELECT_2 = 95;
  private static final int ENGINE_SELECT_3 = 96;
  private static final int ENGINE_SELECT_4 = 97;
  private static final int ENGINE_SELECT_5 = 98;
  private static final int ENGINE_SELECT_6 = 99;
  private static final int ENGINE_SELECT_7 = 100;
  private static final int ENGINE_SELECT_8 = 101;
  private static final int ENGINE_TOGGLE_ALL = 102;
  private static final int ENGINE_TOGGLE_LEFT = 103;
  private static final int ENGINE_TOGGLE_RIGHT = 104;
  private static final int ENGINE_TOGGLE_1 = 105;
  private static final int ENGINE_TOGGLE_2 = 106;
  private static final int ENGINE_TOGGLE_3 = 107;
  private static final int ENGINE_TOGGLE_4 = 108;
  private static final int ENGINE_TOGGLE_5 = 109;
  private static final int ENGINE_TOGGLE_6 = 110;
  private static final int ENGINE_TOGGLE_7 = 111;
  private static final int ENGINE_TOGGLE_8 = 112;
  private static final int ENGINE_EXTINGUISHER = 113;
  private static final int ENGINE_FEATHER = 114;
  private static final int COMPRESSORSTEP_PLUS = 115;
  private static final int COMPRESSORSTEP_MINUS = 116;
  private static final int SIGHT_DIST_PLUS = 117;
  private static final int SIGHT_DIST_MINUS = 118;
  private static final int SIGHT_SIDE_RIGHT = 119;
  private static final int SIGHT_SIDE_LEFT = 120;
  private static final int SIGHT_ALT_PLUS = 121;
  private static final int SIGHT_ALT_MINUS = 122;
  private static final int SIGHT_SPD_PLUS = 123;
  private static final int SIGHT_SPD_MINUS = 124;
  private static final int SIGHT_AUTO_ONOFF = 125;
  private static final int AIRCRAFT_DOCK_UNDOCK = 126;
  private static final int WINGFOLD = 127;
  private static final int COCKPITDOOR = 128;
  private static final int AIRCRAFT_CARRIERHOOK = 129;
  private static final int AIRCRAFT_BRAKESHOE = 130;
  public static int hudLogPowerId;
  public static int hudLogWeaponId;
  private boolean bAutoAutopilot;
  private int switchToCockpitRequest;
  private HotKeyCmd[] cmdFov;
  private static Object[] namedAll = new Object[1];
  private static TreeMap namedAircraft = new TreeMap();
  private boolean bMissionModsSet;
  private boolean bSpeedbarTAS;
  private boolean bSeparateGearUpDown;
  private boolean bSeparateHookUpDown;
  private boolean bSeparateRadiatorOpenClose;
  private boolean bToggleMusic;
  private boolean bViewExternalSelf;
  private boolean bViewExternalOnGround;
  private boolean bViewExternalWhenDead;
  private boolean bViewExternalFriendlies;
  private boolean bBombBayDoors;
  private boolean bMusicOn;
  private int iAirShowSmoke;
  private boolean bAirShowSmokeEnhanced;
  private boolean bSideDoor;
  private int COCKPIT_DOOR;
  private int SIDE_DOOR;
  private boolean bAllowDumpFuel;
  private boolean bDumpFuel;
  private boolean bExtViewEnemy;
  private boolean bExtViewFriendly;
  private boolean bExtViewSelf;
  private boolean bExtViewGround;
  private boolean bExtViewDead;
  private int iExtViewGround;
  private int iExtViewDead;
  private boolean bPadlockEnemy;
  private boolean bPadlockFriendly;
  private boolean bExtPadlockEnemy;
  private boolean bExtPadlockFriendly;
  private boolean bMustangCompressorAuto;

  public boolean isAfterburner()
  {
    if (!Actor.isValid(World.getPlayerAircraft()))
      this.bAfterburner = false;
    return this.bAfterburner;
  }

  public void setAfterburner(boolean paramBoolean)
  {
    if (this.FM.EI.isSelectionHasControlAfterburner())
    {
      this.bAfterburner = paramBoolean;
      if (this.bAfterburner)
      {
        int i = 0;
        if (((this.FM.actor instanceof Hurricane)) || (((this.FM.actor instanceof A6M)) && (!(this.FM.actor instanceof A6M7_62)) && (!(this.FM.actor instanceof A6M5C))) || ((this.FM.actor instanceof P_51)) || ((this.FM.actor instanceof SPITFIRE)) || ((this.FM.actor instanceof MOSQUITO)) || ((this.FM.actor instanceof TEMPEST)))
          i = 1;
        try
        {
          if ((this.FM.actor instanceof MOSQUITOS))
            i = 1;
        } catch (Throwable localThrowable) {
        }
        if (i != 0)
          HUD.logRightBottom("BoostWepTP0");
        else
          HUD.logRightBottom("BoostWepTP" + this.FM.EI.getFirstSelected().getAfterburnerType());
      }
      else {
        HUD.logRightBottom(null);
      }
    }
    this.FM.CT.setAfterburnerControl(this.bAfterburner);
  }

  public void setAfterburnerForAutoActivation(boolean paramBoolean)
  {
    this.bAfterburner = paramBoolean;
  }

  public boolean isPropAuto()
  {
    if (!Actor.isValid(World.getPlayerAircraft()))
      this.bPropAuto = false;
    return this.bPropAuto;
  }

  public void setPropAuto(boolean paramBoolean)
  {
    if ((paramBoolean) && (!this.FM.EI.isSelectionAllowsAutoProp()))
    {
      return;
    }

    this.bPropAuto = paramBoolean;
  }

  public void resetGame()
  {
    this.FM = null;
    this.bAfterburner = false;
    this.bPropAuto = true;
    this.lastPower = -0.5F;
    this.lastProp = 1.5F;
    this.bMusicOn = true;
    this.bDumpFuel = false;
    this.bMissionModsSet = false;
    this.bMustangCompressorAuto = true;
  }

  public void resetUser()
  {
    resetGame();
  }

  private boolean setPilot()
  {
    this.FM = null;
    if (!Actor.isAlive(World.getPlayerAircraft()))
      return false;
    if (World.isPlayerParatrooper())
      return false;
    if (World.isPlayerDead())
      return false;
    FlightModel localFlightModel = World.getPlayerFM();
    if (localFlightModel == null)
      return false;
    if ((localFlightModel instanceof RealFlightModel))
    {
      this.FM = ((RealFlightModel)localFlightModel);
      return this.FM.isRealMode();
    }

    return false;
  }

  private void setPowerControl(float paramFloat)
  {
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    if (paramFloat > 1.1F)
      paramFloat = 1.1F;
    this.lastPower = paramFloat;
    this.FM.CT.setPowerControl(paramFloat);
    hudPower(this.FM.CT.PowerControl);
  }

  private void setPropControl(float paramFloat)
  {
    if (!World.cur().diffCur.ComplexEManagement)
      return;
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    if (paramFloat > 1.0F)
      paramFloat = 1.0F;
    this.lastProp = paramFloat;
    if (!this.FM.EI.isSelectionAllowsAutoProp())
      this.bPropAuto = false;
    if (!this.bPropAuto)
    {
      this.FM.CT.setStepControlAuto(false);
      this.FM.CT.setStepControl(paramFloat);
      HUD.log(hudLogPowerId, "PropPitch", new Object[] { new Integer(Math.round(this.FM.CT.getStepControl() * 100.0F)) });
    }
  }

  private void setMixControl(float paramFloat)
  {
    if (!World.cur().diffCur.ComplexEManagement)
      return;
    if (paramFloat < 0.0F)
      paramFloat = 0.0F;
    if (paramFloat > 1.2F)
      paramFloat = 1.2F;
    if (this.FM.EI.getFirstSelected() != null)
    {
      this.FM.EI.setMix(paramFloat);
      paramFloat = this.FM.EI.getFirstSelected().getControlMix();
      this.FM.CT.setMixControl(paramFloat);
      HUD.log(hudLogPowerId, "PropMix", new Object[] { new Integer(Math.round(this.FM.CT.getMixControl() * 100.0F)) });
    }
  }

  private void hudPower(float paramFloat)
  {
    HUD.log(hudLogPowerId, "Power", new Object[] { new Integer(Math.round(paramFloat * 100.0F)) });
  }

  private void hudWeapon(boolean paramBoolean, int paramInt)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    BulletEmitter[] arrayOfBulletEmitter = this.FM.CT.Weapons[paramInt];
    if (arrayOfBulletEmitter == null)
      return;
    for (int n = 0; n < arrayOfBulletEmitter.length; n++)
    {
      if ((arrayOfBulletEmitter[n] == null) || (!arrayOfBulletEmitter[n].haveBullets()))
        continue;
      i = 1;
      break;
    }

    if (!paramBoolean)
    {
      ForceFeedback.fxTriggerShake(paramInt, false);
      return;
    }
    if (i != 0)
      ForceFeedback.fxTriggerShake(paramInt, true);
    else
      HUD.log(hudLogWeaponId, "OutOfAmmo");
  }

  private void setMissionMods()
  {
    this.bExtViewEnemy = false;
    this.bExtViewFriendly = false;
    this.bExtViewSelf = false;
    this.bExtViewGround = false;
    this.bExtViewDead = false;
    this.iExtViewGround = -1;
    this.iExtViewDead = -1;
    this.bPadlockEnemy = false;
    this.bPadlockFriendly = false;
    this.bExtPadlockEnemy = false;
    this.bExtPadlockFriendly = false;
    this.bViewExternalOnGround = false;
    this.bViewExternalWhenDead = false;
    this.bViewExternalFriendlies = false;
    if (Mission.cur().sectFile() == null)
      return;
    if (Mission.cur().sectFile().get("Mods", "ViewExternalSelf", 0) > 0)
      this.bViewExternalSelf = true;
    if (Mission.cur().sectFile().get("Mods", "ViewExternalOnGround", 0) > 0)
      this.bViewExternalOnGround = true;
    if (Mission.cur().sectFile().get("Mods", "ViewExternalWhenDead", 0) > 0)
      this.bViewExternalWhenDead = true;
    if (Mission.cur().sectFile().get("Mods", "ViewExternalFriendlies", 0) > 0)
      this.bViewExternalFriendlies = true;
    int i = Mission.cur().sectFile().get("Mods", "ExternalViewLevel", -1);
    if (i == 2)
    {
      this.bExtViewEnemy = true;
      this.bExtViewFriendly = true;
      this.bExtViewSelf = true;
    }
    else if ((i == 1) || (this.bViewExternalFriendlies))
    {
      this.bExtViewFriendly = true;
      this.bExtViewSelf = true;
    }
    else if ((i == 0) || (this.bViewExternalSelf)) {
      this.bExtViewSelf = true;
    }this.iExtViewGround = Mission.cur().sectFile().get("Mods", "ExternalViewGround", -1);
    if ((this.iExtViewGround == 1) || (this.bViewExternalOnGround))
      this.bExtViewGround = true;
    this.iExtViewDead = Mission.cur().sectFile().get("Mods", "ExternalViewDead", -1);
    if ((this.iExtViewDead == 1) || (this.bViewExternalWhenDead))
      this.bExtViewDead = true;
    int j = Mission.cur().sectFile().get("Mods", "PadlockLevel", -1);
    if (j == 2)
    {
      this.bPadlockEnemy = true;
      this.bPadlockFriendly = true;
    }
    else if (j == 1) {
      this.bPadlockFriendly = true;
    }int k = Mission.cur().sectFile().get("Mods", "ExternalPadlockLevel", -1);
    if (k == 2)
    {
      this.bExtPadlockEnemy = true;
      this.bExtPadlockFriendly = true;
    }
    else if (k == 1) {
      this.bExtPadlockFriendly = true;
    }this.bMissionModsSet = true;
  }

  private boolean viewAllowed(boolean paramBoolean)
  {
    if (!World.cur().diffCur.No_Outside_Views)
      return true;
    if (!paramBoolean)
      return false;
    if ((paramBoolean) && (this.iExtViewGround == -1) && (this.iExtViewDead == -1))
      return true;
    if ((this.bExtViewGround) && (Actor.isValid(World.getPlayerAircraft())) && (this.FM.Gears.onGround())) {
      return true;
    }
    return (this.bExtViewDead) && ((World.isPlayerParatrooper()) || (World.isPlayerDead()) || (this.FM.isReadyToDie()) || (!Actor.isValid(World.getPlayerAircraft())));
  }

  private boolean hasBayDoors()
  {
    int i = 0;
    if ((((Aircraft)this.FM.actor instanceof A_20)) || (((Aircraft)this.FM.actor instanceof B_17)) || (((Aircraft)this.FM.actor instanceof B_24)) || (((Aircraft)this.FM.actor instanceof B_25)) || (((Aircraft)this.FM.actor instanceof B_29X)) || (((Aircraft)this.FM.actor instanceof Halifax)) || (((Aircraft)this.FM.actor instanceof BLENHEIM)) || (((Aircraft)this.FM.actor instanceof DO_335)) || (((Aircraft)this.FM.actor instanceof MOSQUITO)) || (((Aircraft)this.FM.actor instanceof HE_111H2)) || (((Aircraft)this.FM.actor instanceof IL_10)) || (((Aircraft)this.FM.actor instanceof JU_88A4)) || ((((Aircraft)this.FM.actor instanceof ME_210)) && (!((Aircraft)this.FM.actor instanceof ME_210CA1ZSTR))) || (((Aircraft)this.FM.actor instanceof PE_2)) || (((Aircraft)this.FM.actor instanceof PE_8)) || (((Aircraft)this.FM.actor instanceof R_10)) || (((Aircraft)this.FM.actor instanceof SB)) || (((Aircraft)this.FM.actor instanceof SU_2)) || (((Aircraft)this.FM.actor instanceof TB_3)) || (((Aircraft)this.FM.actor instanceof IL_2)) || (((Aircraft)this.FM.actor instanceof IL_4)) || (((Aircraft)this.FM.actor instanceof FW_200)) || (((Aircraft)this.FM.actor instanceof KI_21)) || (((Aircraft)this.FM.actor instanceof YAK_9B)) || (((Aircraft)this.FM.actor instanceof TU_2S)) || (((Aircraft)this.FM.actor instanceof TBF)))
      i = 1;
    try
    {
      if (((Aircraft)this.FM.actor instanceof MOSQUITOS))
        i = 1;
    }
    catch (Throwable localThrowable1) {
    }
    try {
      if (((Aircraft)this.FM.actor instanceof SM79))
        i = 1;
    }
    catch (Throwable localThrowable2) {
    }
    try {
      if (((Aircraft)this.FM.actor instanceof P2V))
        i = 1;
    } catch (Throwable localThrowable3) {
    }
    return i;
  }

  private void doCmdPilot(int paramInt, boolean paramBoolean)
  {
    if (!setPilot())
      return;
    Aircraft localAircraft = (Aircraft)this.FM.actor;
    int i = 0;
    int j = 0;
    switch (paramInt)
    {
    case 16:
      this.FM.CT.WeaponControl[0] = paramBoolean;
      hudWeapon(paramBoolean, 0);
      break;
    case 17:
      this.FM.CT.WeaponControl[1] = paramBoolean;
      hudWeapon(paramBoolean, 1);
      break;
    case 18:
      this.FM.CT.WeaponControl[2] = paramBoolean;
      hudWeapon(paramBoolean, 2);
      break;
    case 19:
      if ((this.bBombBayDoors) && (hasBayDoors()))
        this.FM.CT.bHasBayDoors = true;
      this.FM.CT.WeaponControl[3] = paramBoolean;
      hudWeapon(paramBoolean, 3);
      break;
    case 64:
      this.FM.CT.WeaponControl[0] = paramBoolean;
      hudWeapon(paramBoolean, 0);
      this.FM.CT.WeaponControl[1] = paramBoolean;
      hudWeapon(paramBoolean, 1);
    }

    if (!paramBoolean)
    {
      switch (paramInt)
      {
      default:
        break;
      case 71:
        if (((localAircraft instanceof TypeBomber)) || ((localAircraft instanceof DO_335)) || ((localAircraft instanceof F84G1)) || ((localAircraft instanceof F84G1)))
        {
          this.FM.CT.StabilizerControl = (!this.FM.CT.StabilizerControl);
          HUD.log("Stabilizer" + (this.FM.CT.StabilizerControl ? "On" : "Off"));
        }
        return;
      case 15:
        if (!localAircraft.isGunPodsExist())
          return;
        if (localAircraft.isGunPodsOn())
        {
          localAircraft.setGunPodsOn(false);
          HUD.log("GunPodsOff");
        }
        else {
          localAircraft.setGunPodsOn(true);
          HUD.log("GunPodsOn");
        }
        return;
      case 1:
      case 2:
        if (this.FM.CT.StabilizerControl) break;
        this.FM.CT.RudderControl = 0.0F; break;
      case 0:
        this.FM.CT.BrakeControl = 0.0F;
        break;
      case 3:
      case 4:
        if (this.FM.CT.StabilizerControl) break;
        this.FM.CT.ElevatorControl = 0.0F; break;
      case 5:
      case 6:
        if (this.FM.CT.StabilizerControl) break;
        this.FM.CT.AileronControl = 0.0F; break;
      case 54:
        if ((this.FM.Gears.onGround()) || (this.FM.CT.GearControl <= 0.0F) || (!this.FM.Gears.isOperable()) || (this.FM.Gears.isHydroOperable()))
          break;
        this.FM.CT.GearControl -= 0.02F;
        if (this.FM.CT.GearControl > 0.0F)
          break;
        this.FM.CT.GearControl = 0.0F;
        HUD.log("GearUp"); break;
      case 55:
        if ((this.FM.Gears.onGround()) || (this.FM.CT.GearControl >= 1.0F) || (!this.FM.Gears.isOperable()) || (this.FM.Gears.isHydroOperable()))
          break;
        this.FM.CT.GearControl += 0.02F;
        if (this.FM.CT.GearControl < 1.0F)
          break;
        this.FM.CT.GearControl = 1.0F;
        HUD.log("GearDown"); break;
      case 63:
        if (!this.FM.CT.bHasAirBrakeControl)
          break;
        this.FM.CT.AirBrakeControl = (this.FM.CT.AirBrakeControl <= 0.5F ? 1.0F : 0.0F);
        HUD.log("Divebrake" + (this.FM.CT.AirBrakeControl != 0.0F ? "ON" : "OFF")); break;
      case 70:
        if ((World.cur().diffCur.SeparateEStart) && (this.FM.EI.getNumSelected() > 1) && (this.FM.EI.getFirstSelected().getStage() == 0))
          return;
        this.FM.EI.toggle();
        break;
      case 126:
        if (!(this.FM.actor instanceof TypeDockable))
          break;
        if (((TypeDockable)this.FM.actor).typeDockableIsDocked())
          ((TypeDockable)this.FM.actor).typeDockableAttemptDetach();
        else {
          ((TypeDockable)this.FM.actor).typeDockableAttemptAttach();
        }
      }
      return;
    }
    switch (paramInt)
    {
    case 8:
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
    case 17:
    case 18:
    case 19:
    case 54:
    case 55:
    case 63:
    case 64:
    case 65:
    case 66:
    case 67:
    case 68:
    case 69:
    case 70:
    case 71:
    case 100:
    case 101:
    case 111:
    case 112:
    case 126:
    default:
      break;
    case 7:
      if (this.bSeparateRadiatorOpenClose)
      {
        if (!this.FM.EI.isSelectionHasControlRadiator())
          break;
        if (this.FM.CT.getRadiatorControlAuto())
        {
          this.FM.CT.setRadiatorControlAuto(false, this.FM.EI);
          if (World.cur().diffCur.ComplexEManagement)
          {
            this.FM.CT.setRadiatorControl(0.0F);
            HUD.log("RadiatorControl" + (int)(this.FM.CT.getRadiatorControl() * 10.0F));
          }
          else {
            this.FM.CT.setRadiatorControl(1.0F);
            HUD.log("RadiatorON");
          }

        }
        else if (World.cur().diffCur.ComplexEManagement)
        {
          if (this.FM.CT.getRadiatorControl() == 1.0F)
            break;
          if ((this.FM.actor instanceof MOSQUITO))
            this.FM.CT.setRadiatorControl(1.0F);
          else
            this.FM.CT.setRadiatorControl(this.FM.CT.getRadiatorControl() + 0.2F);
          HUD.log("RadiatorControl" + (int)(this.FM.CT.getRadiatorControl() * 10.0F));
        }
        else {
          this.FM.CT.setRadiatorControlAuto(true, this.FM.EI);
          HUD.log("RadiatorOFF");
        }
      }
      else {
        if (!this.FM.EI.isSelectionHasControlRadiator())
          break;
        if (this.FM.CT.getRadiatorControlAuto())
        {
          this.FM.CT.setRadiatorControlAuto(false, this.FM.EI);
          if (World.cur().diffCur.ComplexEManagement)
          {
            this.FM.CT.setRadiatorControl(0.0F);
            HUD.log("RadiatorControl" + (int)(this.FM.CT.getRadiatorControl() * 10.0F));
          }
          else {
            this.FM.CT.setRadiatorControl(1.0F);
            HUD.log("RadiatorON");
          }

        }
        else if (World.cur().diffCur.ComplexEManagement)
        {
          if (this.FM.CT.getRadiatorControl() == 1.0F)
          {
            if (this.FM.EI.isSelectionAllowsAutoRadiator())
            {
              this.FM.CT.setRadiatorControlAuto(true, this.FM.EI);
              HUD.log("RadiatorOFF");
            }
            else {
              this.FM.CT.setRadiatorControl(0.0F);
              HUD.log("RadiatorControl" + (int)(this.FM.CT.getRadiatorControl() * 10.0F));
            }
          }
          else {
            if ((this.FM.actor instanceof MOSQUITO))
              this.FM.CT.setRadiatorControl(1.0F);
            else
              this.FM.CT.setRadiatorControl(this.FM.CT.getRadiatorControl() + 0.2F);
            HUD.log("RadiatorControl" + (int)(this.FM.CT.getRadiatorControl() * 10.0F));
          }
        } else {
          this.FM.CT.setRadiatorControlAuto(true, this.FM.EI);
          HUD.log("RadiatorOFF");
        }
      }
      break;
    case 0:
      this.FM.CT.BrakeControl = 1.0F;
      break;
    case 3:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.ElevatorControl = -1.0F; break;
    case 4:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.ElevatorControl = 1.0F; break;
    case 5:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.AileronControl = -1.0F; break;
    case 6:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.AileronControl = 1.0F; break;
    case 72:
      if (!this.FM.CT.bHasLockGearControl)
        break;
      this.FM.Gears.bTailwheelLocked = (!this.FM.Gears.bTailwheelLocked);
      HUD.log("TailwheelLock" + (this.FM.Gears.bTailwheelLocked ? "ON" : "OFF")); break;
    case 1:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.RudderControl = -1.0F; break;
    case 56:
      if ((this.FM.CT.StabilizerControl) || (this.FM.CT.RudderControl <= -1.0F)) break;
      this.FM.CT.RudderControl -= 0.1F; break;
    case 57:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.RudderControl = 0.0F; break;
    case 2:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.RudderControl = 1.0F; break;
    case 58:
      if ((this.FM.CT.StabilizerControl) || (this.FM.CT.RudderControl >= 1.0F)) break;
      this.FM.CT.RudderControl += 0.1F; break;
    case 20:
      setPowerControl(0.0F);
      break;
    case 21:
      if (this.bBombBayDoors)
      {
        if (!hasBayDoors())
          break;
        this.FM.CT.bHasBayDoors = true;
        if (this.FM.CT.BayDoorControl != 0.0F)
        {
          this.FM.CT.BayDoorControl = 0.0F;
          HUD.log("BayDoorsClosed");
        }
        else {
          this.FM.CT.BayDoorControl = 1.0F;
          HUD.log("BayDoorsOpen");
        }
      }
      else {
        setPowerControl(0.1F);
      }
      break;
    case 22:
      if (this.bToggleMusic)
      {
        if (this.bMusicOn)
        {
          CmdMusic.toggle(false);
          this.bMusicOn = false;
        }
        else {
          CmdMusic.toggle(true);
          this.bMusicOn = true;
        }
      }
      else {
        setPowerControl(0.2F);
      }
      break;
    case 23:
      if (this.bSeparateRadiatorOpenClose)
      {
        if ((!this.FM.EI.isSelectionHasControlRadiator()) || (this.FM.CT.getRadiatorControlAuto()))
          break;
        if (World.cur().diffCur.ComplexEManagement)
        {
          if (this.FM.CT.getRadiatorControl() == 0.0F)
          {
            if (!this.FM.EI.isSelectionAllowsAutoRadiator())
              break;
            this.FM.CT.setRadiatorControlAuto(true, this.FM.EI);
            HUD.log("RadiatorOFF");
          }
          else
          {
            if ((this.FM.actor instanceof MOSQUITO))
              this.FM.CT.setRadiatorControl(0.0F);
            else
              this.FM.CT.setRadiatorControl(this.FM.CT.getRadiatorControl() - 0.2F);
            HUD.log("RadiatorControl" + (int)(this.FM.CT.getRadiatorControl() * 10.0F));
          }
        } else {
          this.FM.CT.setRadiatorControlAuto(true, this.FM.EI);
          HUD.log("RadiatorOFF");
        }
      }
      else {
        setPowerControl(0.3F);
      }
      break;
    case 24:
      if (this.bSideDoor)
      {
        int k = 0;
        try
        {
          if (((Aircraft)this.FM.actor instanceof SPITFIRELF14E))
            k = 1;
        }
        catch (Throwable localThrowable3) {
        }
        try {
          if (((Aircraft)this.FM.actor instanceof SPITFIRE14C))
            k = 1;
        }
        catch (Throwable localThrowable4) {
        }
        try {
          if (((Aircraft)this.FM.actor instanceof SPITFIRE12))
            k = 1;
        } catch (Throwable localThrowable5) {
        }
        if (k == 0)
          break;
        this.FM.CT.setActiveDoor(this.SIDE_DOOR);
        if ((this.FM.CT.cockpitDoorControl < 0.5F) && (this.FM.CT.getCockpitDoor() < 0.01F))
        {
          this.FM.AS.setCockpitDoor(localAircraft, 1);
        }
        else {
          if ((this.FM.CT.cockpitDoorControl <= 0.5F) || (this.FM.CT.getCockpitDoor() <= 0.99F)) break;
          this.FM.AS.setCockpitDoor(localAircraft, 0);
        }
      } else {
        setPowerControl(0.4F);
      }
      break;
    case 25:
      if (this.bAllowDumpFuel)
      {
        if (!((Aircraft)this.FM.actor instanceof F9F))
          break;
        if (this.bDumpFuel)
          this.bDumpFuel = false;
        else
          this.bDumpFuel = true;
        this.FM.AS.setDumpFuelState(this.bDumpFuel);
      }
      else {
        setPowerControl(0.5F);
      }
      break;
    case 26:
      setPowerControl(0.6F);
      break;
    case 27:
      setPowerControl(0.7F);
      break;
    case 28:
      setPowerControl(0.8F);
      break;
    case 29:
      setPowerControl(0.9F);
      break;
    case 30:
      setPowerControl(1.0F);
      break;
    case 59:
      setPowerControl(this.FM.CT.PowerControl + 0.05F);
      break;
    case 60:
      setPowerControl(this.FM.CT.PowerControl - 0.05F);
      break;
    case 61:
      setAfterburner(!this.bAfterburner);
      break;
    case 31:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.0F); break;
    case 32:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.1F); break;
    case 33:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.2F); break;
    case 34:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.3F); break;
    case 35:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.4F); break;
    case 36:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.5F); break;
    case 37:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.6F); break;
    case 38:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.7F); break;
    case 39:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.8F); break;
    case 40:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(0.9F); break;
    case 41:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(1.0F); break;
    case 73:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(this.lastProp + 0.05F); break;
    case 74:
      if (!this.FM.EI.isSelectionHasControlProp()) break;
      setPropControl(this.lastProp - 0.05F); break;
    case 42:
      if ((!this.FM.EI.isSelectionHasControlProp()) || (!World.cur().diffCur.ComplexEManagement))
        break;
      setPropAuto(!this.bPropAuto);
      if (this.bPropAuto)
      {
        HUD.log("PropAutoPitch");
        this.lastProp = this.FM.CT.getStepControl();
        this.FM.CT.setStepControlAuto(true);
      }
      else {
        this.FM.CT.setStepControlAuto(false);
        setPropControl(this.lastProp);
      }
      break;
    case 114:
      if ((!this.FM.EI.isSelectionHasControlFeather()) || (!World.cur().diffCur.ComplexEManagement) || (this.FM.EI.getFirstSelected() == null)) break;
      this.FM.EI.setFeather(this.FM.EI.getFirstSelected().getControlFeather() != 0 ? 0 : 1); break;
    case 75:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.0F); break;
    case 76:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.1F); break;
    case 77:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.2F); break;
    case 78:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.3F); break;
    case 79:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.4F); break;
    case 80:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.5F); break;
    case 81:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.6F); break;
    case 82:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.7F); break;
    case 83:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.8F); break;
    case 84:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(0.9F); break;
    case 85:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(1.0F); break;
    case 86:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(this.FM.CT.getMixControl() + 0.2F); break;
    case 87:
      if (!this.FM.EI.isSelectionHasControlMix()) break;
      setMixControl(this.FM.CT.getMixControl() - 0.2F); break;
    case 89:
      if (!this.bSeparateGearUpDown)
      {
        if ((!this.FM.EI.isSelectionHasControlMagnetos()) || (this.FM.EI.getFirstSelected() == null) || (this.FM.EI.getFirstSelected().getControlMagnetos() <= 0))
          break;
        this.FM.CT.setMagnetoControl(this.FM.EI.getFirstSelected().getControlMagnetos() - 1);
        HUD.log("MagnetoSetup" + this.FM.CT.getMagnetoControl());
      }
      else
      {
        if ((!this.FM.CT.bHasGearControl) || (this.FM.Gears.onGround()) || (!this.FM.Gears.isHydroOperable()))
          break;
        if ((this.FM.CT.GearControl > 0.5F) && (this.FM.CT.getGear() > 0.99F))
        {
          this.FM.CT.GearControl = 0.0F;
          HUD.log("GearUp");
        }
        if (!this.FM.Gears.isAnyDamaged()) break;
        HUD.log("GearDamaged"); } break;
    case 88:
      if (!this.bSeparateHookUpDown)
      {
        if ((!this.FM.EI.isSelectionHasControlMagnetos()) || (this.FM.EI.getFirstSelected() == null) || (this.FM.EI.getFirstSelected().getControlMagnetos() >= 3))
          break;
        this.FM.CT.setMagnetoControl(this.FM.EI.getFirstSelected().getControlMagnetos() + 1);
        HUD.log("MagnetoSetup" + this.FM.CT.getMagnetoControl());
      }
      else
      {
        if ((!this.FM.CT.bHasArrestorControl) || (this.FM.CT.arrestorControl <= 0.5F))
          break;
        this.FM.AS.setArrestor(this.FM.actor, 0);
        HUD.log("HookUp"); } break;
    case 116:
      try
      {
        if (((this.FM.actor instanceof P_51Mustang)) && (World.cur().diffCur.ComplexEManagement))
        {
          if (!this.bMustangCompressorAuto)
            if (this.FM.CT.getCompressorControl() == 0)
            {
              this.FM.EI.getFirstSelected().bHasCompressorControl = false;
              this.bMustangCompressorAuto = true;
              HUD.log("Supercharger: Auto");
            }
            else {
              this.FM.CT.setCompressorControl(this.FM.EI.getFirstSelected().getControlCompressor() - 1);
              HUD.log("CompressorSetup" + this.FM.CT.getCompressorControl());
            }
          j = 1;
        }
      } catch (Throwable localThrowable1) {
      }
      if ((j != 0) || (!this.FM.EI.isSelectionHasControlCompressor()) || (this.FM.EI.getFirstSelected() == null) || (!World.cur().diffCur.ComplexEManagement))
        break;
      this.FM.CT.setCompressorControl(this.FM.EI.getFirstSelected().getControlCompressor() - 1);
      HUD.log("CompressorSetup" + this.FM.CT.getCompressorControl()); break;
    case 115:
      try
      {
        if (((this.FM.actor instanceof P_51Mustang)) && (World.cur().diffCur.ComplexEManagement))
        {
          if (this.bMustangCompressorAuto)
          {
            this.FM.EI.getFirstSelected().bHasCompressorControl = true;
            this.FM.CT.setCompressorControl(0);
            this.bMustangCompressorAuto = false;
            HUD.log("CompressorSetup0");
          }
          else {
            this.FM.CT.setCompressorControl(this.FM.EI.getFirstSelected().getControlCompressor() + 1);
            HUD.log("CompressorSetup" + this.FM.CT.getCompressorControl());
          }
          j = 1;
        }
      } catch (Throwable localThrowable2) {
      }
      if ((j != 0) || (!this.FM.EI.isSelectionHasControlCompressor()) || (this.FM.EI.getFirstSelected() == null) || (!World.cur().diffCur.ComplexEManagement))
        break;
      this.FM.CT.setCompressorControl(this.FM.EI.getFirstSelected().getControlCompressor() + 1);
      HUD.log("CompressorSetup" + this.FM.CT.getCompressorControl()); break;
    case 90:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControlAll(true);
      HUD.log("EngineSelectAll");
      break;
    case 91:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControlAll(false);
      HUD.log("EngineSelectNone");
      break;
    case 92:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControlAll(false);
      int[] arrayOfInt1 = this.FM.EI.getSublist(this.FM.Scheme, 1);
      for (int m = 0; m < arrayOfInt1.length; m++) {
        this.FM.EI.setCurControl(arrayOfInt1[m], true);
      }
      HUD.log("EngineSelectLeft");
      break;
    case 93:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControlAll(false);
      int[] arrayOfInt2 = this.FM.EI.getSublist(this.FM.Scheme, 2);
      for (int n = 0; n < arrayOfInt2.length; n++) {
        this.FM.EI.setCurControl(arrayOfInt2[n], true);
      }
      HUD.log("EngineSelectRight");
      break;
    case 94:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControlAll(false);
      this.FM.EI.setCurControl(0, true);
      HUD.log("EngineSelect1");
      break;
    case 95:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControlAll(false);
      this.FM.EI.setCurControl(1, true);
      HUD.log("EngineSelect2");
      break;
    case 96:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1) || (this.FM.EI.getNum() < 3))
        return;
      this.FM.EI.setCurControlAll(false);
      this.FM.EI.setCurControl(2, true);
      HUD.log("EngineSelect3");
      break;
    case 97:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1) || (this.FM.EI.getNum() < 4))
        return;
      this.FM.EI.setCurControlAll(false);
      this.FM.EI.setCurControl(3, true);
      HUD.log("EngineSelect4");
      break;
    case 98:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1) || (this.FM.EI.getNum() < 5))
        return;
      this.FM.EI.setCurControlAll(false);
      this.FM.EI.setCurControl(4, true);
      HUD.log("EngineSelect5");
      break;
    case 99:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1) || (this.FM.EI.getNum() < 6))
        return;
      this.FM.EI.setCurControlAll(false);
      this.FM.EI.setCurControl(5, true);
      HUD.log("EngineSelect6");
      break;
    case 102:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      for (int i1 = 0; i1 < this.FM.EI.getNum(); i1++) {
        this.FM.EI.setCurControl(i1, !this.FM.EI.getCurControl(i1));
      }
      HUD.log("EngineToggleAll");
      break;
    case 103:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      int[] arrayOfInt3 = this.FM.EI.getSublist(this.FM.Scheme, 1);
      for (int i2 = 0; i2 < arrayOfInt3.length; i2++) {
        this.FM.EI.setCurControl(arrayOfInt3[i2], !this.FM.EI.getCurControl(arrayOfInt3[i2]));
      }
      HUD.log("EngineToggleLeft");
      break;
    case 104:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      int[] arrayOfInt4 = this.FM.EI.getSublist(this.FM.Scheme, 2);
      for (int i3 = 0; i3 < arrayOfInt4.length; i3++) {
        this.FM.EI.setCurControl(arrayOfInt4[i3], !this.FM.EI.getCurControl(arrayOfInt4[i3]));
      }
      HUD.log("EngineToggleRight");
      break;
    case 105:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControl(0, !this.FM.EI.getCurControl(0));
      HUD.log("EngineSelect1" + (this.FM.EI.getCurControl(0) ? "" : "OFF"));
      break;
    case 106:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControl(1, !this.FM.EI.getCurControl(1));
      HUD.log("EngineSelect2" + (this.FM.EI.getCurControl(1) ? "" : "OFF"));
      break;
    case 107:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControl(2, !this.FM.EI.getCurControl(2));
      HUD.log("EngineSelect3" + (this.FM.EI.getCurControl(2) ? "" : "OFF"));
      break;
    case 108:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControl(3, !this.FM.EI.getCurControl(3));
      HUD.log("EngineSelect4" + (this.FM.EI.getCurControl(3) ? "" : "OFF"));
      break;
    case 109:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControl(4, !this.FM.EI.getCurControl(4));
      HUD.log("EngineSelect5" + (this.FM.EI.getCurControl(4) ? "" : "OFF"));
      break;
    case 110:
      if ((this.FM.Scheme == 0) || (this.FM.Scheme == 1))
        return;
      this.FM.EI.setCurControl(5, !this.FM.EI.getCurControl(5));
      HUD.log("EngineSelect6" + (this.FM.EI.getCurControl(5) ? "" : "OFF"));
      break;
    case 113:
      if (!this.FM.EI.isSelectionHasControlExtinguisher())
        break;
      for (int i4 = 0; i4 < this.FM.EI.getNum(); i4++)
        if (this.FM.EI.getCurControl(i4))
          this.FM.EI.engines[i4].setExtinguisherFire();
    case 53:
      if ((goto 7900) || 
        (!this.FM.CT.bHasFlapsControl))
        break;
      if (!this.FM.CT.bHasFlapsControlRed)
      {
        if (this.FM.CT.FlapsControl < 0.2F)
        {
          this.FM.CT.FlapsControl = 0.2F;
          HUD.log("FlapsCombat");
        }
        else if (this.FM.CT.FlapsControl < 0.33F)
        {
          this.FM.CT.FlapsControl = 0.33F;
          HUD.log("FlapsTakeOff");
        }
        else {
          if (this.FM.CT.FlapsControl >= 1.0F)
            break;
          this.FM.CT.FlapsControl = 1.0F;
          HUD.log("FlapsLanding");
        }
      }
      else {
        if (this.FM.CT.FlapsControl >= 0.5F)
          break;
        this.FM.CT.FlapsControl = 1.0F;
        HUD.log("FlapsLanding"); } break;
    case 52:
      if (!this.FM.CT.bHasFlapsControl)
        break;
      if (!this.FM.CT.bHasFlapsControlRed)
      {
        if (this.FM.CT.FlapsControl > 0.33F)
        {
          this.FM.CT.FlapsControl = 0.33F;
          HUD.log("FlapsTakeOff");
        }
        else if (this.FM.CT.FlapsControl > 0.2F)
        {
          this.FM.CT.FlapsControl = 0.2F;
          HUD.log("FlapsCombat");
        }
        else {
          if (this.FM.CT.FlapsControl <= 0.0F)
            break;
          this.FM.CT.FlapsControl = 0.0F;
          HUD.log("FlapsRaised");
        }
      }
      else {
        if (this.FM.CT.FlapsControl <= 0.5F)
          break;
        this.FM.CT.FlapsControl = 0.0F;
        HUD.log("FlapsRaised"); } break;
    case 9:
      if ((!this.FM.CT.bHasGearControl) || (this.FM.Gears.onGround()) || (!this.FM.Gears.isHydroOperable()))
        break;
      if ((this.FM.CT.GearControl > 0.5F) && (this.FM.CT.getGear() > 0.99F) && (!this.bSeparateGearUpDown))
      {
        this.FM.CT.GearControl = 0.0F;
        HUD.log("GearUp");
      }
      else if ((this.FM.CT.GearControl < 0.5F) && (this.FM.CT.getGear() < 0.01F))
      {
        this.FM.CT.GearControl = 1.0F;
        HUD.log("GearDown");
      }
      if (!this.FM.Gears.isAnyDamaged()) break;
      HUD.log("GearDamaged"); break;
    case 129:
      if (!this.FM.CT.bHasArrestorControl)
        break;
      if ((this.FM.CT.arrestorControl > 0.5F) && (!this.bSeparateHookUpDown))
      {
        this.FM.AS.setArrestor(this.FM.actor, 0);
        HUD.log("HookUp");
      }
      else {
        this.FM.AS.setArrestor(this.FM.actor, 1);
        HUD.log("HookDown");
      }
      break;
    case 130:
      if (!this.FM.canChangeBrakeShoe)
        break;
      if (this.FM.jdField_brakeShoe_of_type_Boolean)
      {
        this.FM.jdField_brakeShoe_of_type_Boolean = false;
        HUD.log("BrakeShoeOff");
      }
      else {
        this.FM.jdField_brakeShoe_of_type_Boolean = true;
        HUD.log("BrakeShoeOn");
      }
      break;
    case 127:
      if (!this.FM.CT.bHasWingControl)
        break;
      if ((this.FM.CT.wingControl < 0.5F) && (this.FM.CT.getWing() < 0.01F))
      {
        this.FM.AS.setWingFold(localAircraft, 1);
        HUD.log("WingFold");
      }
      else {
        if ((this.FM.CT.wingControl <= 0.5F) || (this.FM.CT.getWing() <= 0.99F))
          break;
        this.FM.AS.setWingFold(localAircraft, 0);
        HUD.log("WingExpand"); } break;
    case 128:
      if (!this.FM.CT.bHasCockpitDoorControl)
        break;
      if (this.bSideDoor)
      {
        int i5 = 0;
        try
        {
          if (((Aircraft)this.FM.actor instanceof SPITFIRELF14E))
            i5 = 1;
        }
        catch (Throwable localThrowable6) {
        }
        try {
          if (((Aircraft)this.FM.actor instanceof SPITFIRE14C))
            i5 = 1;
        }
        catch (Throwable localThrowable7) {
        }
        try {
          if (((Aircraft)this.FM.actor instanceof SPITFIRE12))
            i5 = 1;
        } catch (Throwable localThrowable8) {
        }
        if (i5 != 0)
          this.FM.CT.setActiveDoor(this.COCKPIT_DOOR);
      }
      if ((this.FM.CT.cockpitDoorControl < 0.5F) && (this.FM.CT.getCockpitDoor() < 0.01F))
      {
        this.FM.AS.setCockpitDoor(localAircraft, 1);
        HUD.log("CockpitDoorOPN");
      }
      else {
        if ((this.FM.CT.cockpitDoorControl <= 0.5F) || (this.FM.CT.getCockpitDoor() <= 0.99F))
          break;
        this.FM.AS.setCockpitDoor(localAircraft, 0);
        HUD.log("CockpitDoorCLS"); } break;
    case 43:
      if (!this.FM.CT.bHasElevatorTrim) break;
      this.FM.CT.setTrimElevatorControl(0.0F); break;
    case 44:
      doCmdPilotTick(paramInt);
      break;
    case 45:
      doCmdPilotTick(paramInt);
      break;
    case 46:
      if (!this.FM.CT.bHasAileronTrim) break;
      this.FM.CT.setTrimAileronControl(0.0F); break;
    case 47:
      doCmdPilotTick(paramInt);
      break;
    case 48:
      doCmdPilotTick(paramInt);
      break;
    case 49:
      if (!this.FM.CT.bHasRudderTrim) break;
      this.FM.CT.setTrimRudderControl(0.0F); break;
    case 50:
      doCmdPilotTick(paramInt);
      break;
    case 51:
      doCmdPilotTick(paramInt);
      break;
    case 125:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberToggleAutomation();
        toTrackSign(paramInt);
      }
      else if ((localAircraft instanceof TypeDiveBomber))
      {
        ((TypeDiveBomber)localAircraft).typeDiveBomberToggleAutomation();
        toTrackSign(paramInt);
      }
      else {
        if (!(localAircraft instanceof TypeFighterAceMaker))
          break;
        ((TypeFighterAceMaker)localAircraft).typeFighterAceMakerToggleAutomation();
        toTrackSign(paramInt); } break;
    case 117:
      doCmdPilotTick(paramInt);
      break;
    case 118:
      doCmdPilotTick(paramInt);
      break;
    case 119:
      doCmdPilotTick(paramInt);
      break;
    case 120:
      doCmdPilotTick(paramInt);
      break;
    case 121:
      doCmdPilotTick(paramInt);
      break;
    case 122:
      doCmdPilotTick(paramInt);
      break;
    case 123:
      doCmdPilotTick(paramInt);
      break;
    case 124:
      doCmdPilotTick(paramInt);
      break;
    case 62:
      this.FM.CT.dropFuelTanks();
    }
  }

  private void doCmdPilotTick(int paramInt)
  {
    if (!setPilot())
      return;
    Aircraft localAircraft = (Aircraft)this.FM.actor;
    switch (paramInt)
    {
    default:
      break;
    case 44:
      if ((!this.FM.CT.bHasElevatorTrim) || (this.FM.CT.getTrimElevatorControl() >= 0.5F)) break;
      this.FM.CT.setTrimElevatorControl(this.FM.CT.getTrimElevatorControl() + 0.00625F); break;
    case 45:
      if ((!this.FM.CT.bHasElevatorTrim) || (this.FM.CT.getTrimElevatorControl() <= -0.5F)) break;
      this.FM.CT.setTrimElevatorControl(this.FM.CT.getTrimElevatorControl() - 0.00625F); break;
    case 47:
      if ((!this.FM.CT.bHasAileronTrim) || (this.FM.CT.getTrimAileronControl() >= 0.5F)) break;
      this.FM.CT.setTrimAileronControl(this.FM.CT.getTrimAileronControl() + 0.00625F); break;
    case 48:
      if ((!this.FM.CT.bHasAileronTrim) || (this.FM.CT.getTrimAileronControl() <= -0.5F)) break;
      this.FM.CT.setTrimAileronControl(this.FM.CT.getTrimAileronControl() - 0.00625F); break;
    case 50:
      if ((!this.FM.CT.bHasRudderTrim) || (this.FM.CT.getTrimRudderControl() >= 0.5F)) break;
      this.FM.CT.setTrimRudderControl(this.FM.CT.getTrimRudderControl() + 0.00625F); break;
    case 51:
      if ((!this.FM.CT.bHasRudderTrim) || (this.FM.CT.getTrimRudderControl() <= -0.5F)) break;
      this.FM.CT.setTrimRudderControl(this.FM.CT.getTrimRudderControl() - 0.00625F); break;
    case 117:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberAdjDistancePlus();
        toTrackSign(paramInt);
      }
      else if ((localAircraft instanceof TypeFighterAceMaker))
      {
        ((TypeFighterAceMaker)localAircraft).typeFighterAceMakerAdjDistancePlus();
        toTrackSign(paramInt);
      }
      else {
        if (!(localAircraft instanceof TypeDiveBomber))
          break;
        ((TypeDiveBomber)localAircraft).typeDiveBomberAdjDiveAnglePlus();
        toTrackSign(paramInt); } break;
    case 118:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberAdjDistanceMinus();
        toTrackSign(paramInt);
      }
      else if ((localAircraft instanceof TypeFighterAceMaker))
      {
        ((TypeFighterAceMaker)localAircraft).typeFighterAceMakerAdjDistanceMinus();
        toTrackSign(paramInt);
      }
      else {
        if (!(localAircraft instanceof TypeDiveBomber))
          break;
        ((TypeDiveBomber)localAircraft).typeDiveBomberAdjDiveAngleMinus();
        toTrackSign(paramInt); } break;
    case 119:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberAdjSideslipPlus();
        toTrackSign(paramInt);
      }
      if ((localAircraft instanceof TypeFighterAceMaker))
      {
        ((TypeFighterAceMaker)localAircraft).typeFighterAceMakerAdjSideslipPlus();
        toTrackSign(paramInt);
      }
      if (!(localAircraft instanceof TypeX4Carrier))
        break;
      ((TypeX4Carrier)localAircraft).typeX4CAdjSidePlus();
      toTrackSign(paramInt); break;
    case 120:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberAdjSideslipMinus();
        toTrackSign(paramInt);
      }
      if ((localAircraft instanceof TypeFighterAceMaker))
      {
        ((TypeFighterAceMaker)localAircraft).typeFighterAceMakerAdjSideslipMinus();
        toTrackSign(paramInt);
      }
      if (!(localAircraft instanceof TypeX4Carrier))
        break;
      ((TypeX4Carrier)localAircraft).typeX4CAdjSideMinus();
      toTrackSign(paramInt); break;
    case 121:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberAdjAltitudePlus();
        toTrackSign(paramInt);
      }
      else if ((localAircraft instanceof TypeDiveBomber))
      {
        ((TypeDiveBomber)localAircraft).typeDiveBomberAdjAltitudePlus();
        toTrackSign(paramInt);
      }
      else {
        if (!(localAircraft instanceof TypeX4Carrier))
          break;
        ((TypeX4Carrier)localAircraft).typeX4CAdjAttitudePlus();
        toTrackSign(paramInt); } break;
    case 122:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberAdjAltitudeMinus();
        toTrackSign(paramInt);
      }
      else if ((localAircraft instanceof TypeDiveBomber))
      {
        ((TypeDiveBomber)localAircraft).typeDiveBomberAdjAltitudeMinus();
        toTrackSign(paramInt);
      }
      else {
        if (!(localAircraft instanceof TypeX4Carrier))
          break;
        ((TypeX4Carrier)localAircraft).typeX4CAdjAttitudeMinus();
        toTrackSign(paramInt); } break;
    case 123:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberAdjSpeedPlus();
        toTrackSign(paramInt);
      }
      else {
        if (!(localAircraft instanceof TypeDiveBomber))
          break;
        ((TypeDiveBomber)localAircraft).typeDiveBomberAdjVelocityPlus();
        toTrackSign(paramInt); } break;
    case 124:
      if ((localAircraft instanceof TypeBomber))
      {
        ((TypeBomber)localAircraft).typeBomberAdjSpeedMinus();
        toTrackSign(paramInt);
      }
      else {
        if (!(localAircraft instanceof TypeDiveBomber))
          break;
        ((TypeDiveBomber)localAircraft).typeDiveBomberAdjVelocityMinus();
        toTrackSign(paramInt);
      }
    }
  }

  public void fromTrackSign(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    if (!Actor.isAlive(World.getPlayerAircraft()))
      return;
    if (World.isPlayerParatrooper())
      return;
    if (World.isPlayerDead())
      return;
    Object localObject;
    int i;
    if ((World.getPlayerAircraft() instanceof TypeBomber))
    {
      localObject = (TypeBomber)World.getPlayerAircraft();
      i = paramNetMsgInput.readUnsignedShort();
      switch (i)
      {
      case 125:
        ((TypeBomber)localObject).typeBomberToggleAutomation();
        break;
      case 117:
        ((TypeBomber)localObject).typeBomberAdjDistancePlus();
        break;
      case 118:
        ((TypeBomber)localObject).typeBomberAdjDistanceMinus();
        break;
      case 119:
        ((TypeBomber)localObject).typeBomberAdjSideslipPlus();
        break;
      case 120:
        ((TypeBomber)localObject).typeBomberAdjSideslipMinus();
        break;
      case 121:
        ((TypeBomber)localObject).typeBomberAdjAltitudePlus();
        break;
      case 122:
        ((TypeBomber)localObject).typeBomberAdjAltitudeMinus();
        break;
      case 123:
        ((TypeBomber)localObject).typeBomberAdjSpeedPlus();
        break;
      case 124:
        ((TypeBomber)localObject).typeBomberAdjSpeedMinus();
        break;
      default:
        return;
      }
    }
    if ((World.getPlayerAircraft() instanceof TypeDiveBomber))
    {
      localObject = (TypeDiveBomber)World.getPlayerAircraft();
      i = paramNetMsgInput.readUnsignedShort();
      switch (i)
      {
      case 125:
        ((TypeDiveBomber)localObject).typeDiveBomberToggleAutomation();
        break;
      case 121:
        ((TypeDiveBomber)localObject).typeDiveBomberAdjAltitudePlus();
        break;
      case 122:
        ((TypeDiveBomber)localObject).typeDiveBomberAdjAltitudeMinus();
        break;
      case 123:
      case 124:
      default:
        return;
      }
    }
    if ((World.getPlayerAircraft() instanceof TypeFighterAceMaker))
    {
      localObject = (TypeFighterAceMaker)World.getPlayerAircraft();
      i = paramNetMsgInput.readUnsignedShort();
      switch (i)
      {
      case 125:
        ((TypeFighterAceMaker)localObject).typeFighterAceMakerToggleAutomation();
        break;
      case 117:
        ((TypeFighterAceMaker)localObject).typeFighterAceMakerAdjDistancePlus();
        break;
      case 118:
        ((TypeFighterAceMaker)localObject).typeFighterAceMakerAdjDistanceMinus();
        break;
      case 119:
        ((TypeFighterAceMaker)localObject).typeFighterAceMakerAdjSideslipPlus();
        break;
      case 120:
        ((TypeFighterAceMaker)localObject).typeFighterAceMakerAdjSideslipMinus();
        break;
      case 121:
      case 122:
      case 123:
      case 124:
      default:
        return;
      }
    }
  }

  private void toTrackSign(int paramInt)
  {
    if (Main3D.cur3D().gameTrackRecord() != null)
      try
      {
        NetMsgGuaranted localNetMsgGuaranted = new NetMsgGuaranted();
        localNetMsgGuaranted.writeByte(5);
        localNetMsgGuaranted.writeShort(paramInt);
        Main3D.cur3D().gameTrackRecord().postTo(Main3D.cur3D().gameTrackRecord().channel(), localNetMsgGuaranted);
      }
      catch (Exception localException) {
      }
  }

  private void doCmdPilotMove(int paramInt, float paramFloat) {
    if (!setPilot())
      return;
    switch (paramInt)
    {
    case 1:
      float f1 = paramFloat * 0.55F + 0.55F;
      if (Math.abs(f1 - this.lastPower) < 0.01F) break;
      setPowerControl(f1); break;
    case 7:
      float f2 = paramFloat * 0.5F + 0.5F;
      if ((Math.abs(f2 - this.lastProp) < 0.02F) || (!this.FM.EI.isSelectionHasControlProp())) break;
      setPropControl(f2); break;
    case 2:
      this.FM.CT.FlapsControl = (paramFloat * 0.5F + 0.5F);
      break;
    case 3:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.AileronControl = paramFloat; break;
    case 4:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.ElevatorControl = paramFloat; break;
    case 5:
      if (this.FM.CT.StabilizerControl) break;
      this.FM.CT.RudderControl = paramFloat; break;
    case 6:
      this.FM.CT.BrakeControl = (paramFloat * 0.5F + 0.5F);
      break;
    case 8:
      if (!this.FM.CT.bHasAileronTrim) break;
      this.FM.CT.setTrimAileronControl(paramFloat * 0.5F); break;
    case 9:
      if (!this.FM.CT.bHasElevatorTrim) break;
      this.FM.CT.setTrimElevatorControl(paramFloat * 0.5F); break;
    case 10:
      if (!this.FM.CT.bHasRudderTrim) break;
      this.FM.CT.setTrimRudderControl(paramFloat * 0.5F); break;
    default:
      return;
    }
  }

  public void createPilotHotMoves()
  {
    String str = "move";
    HotKeyCmdEnv.setCurrentEnv(str);
    HotKeyEnv.fromIni(str, Config.cur.ini, "HotKey " + str);
    HotKeyCmdEnv localHotKeyCmdEnv1 = HotKeyCmdEnv.currentEnv();
    HotKeyCmdEnv localHotKeyCmdEnv2 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("01", "power", 1, 1));
    HotKeyCmdEnv localHotKeyCmdEnv3 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("02", "flaps", 2, 2));
    HotKeyCmdEnv localHotKeyCmdEnv4 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("03", "aileron", 3, 3));
    HotKeyCmdEnv localHotKeyCmdEnv5 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("04", "elevator", 4, 4));
    HotKeyCmdEnv localHotKeyCmdEnv6 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("05", "rudder", 5, 5));
    HotKeyCmdEnv localHotKeyCmdEnv7 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("06", "brakes", 6, 6));
    HotKeyCmdEnv localHotKeyCmdEnv8 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("07", "pitch", 7, 7));
    HotKeyCmdEnv localHotKeyCmdEnv9 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("08", "trimaileron", 8, 8));
    HotKeyCmdEnv localHotKeyCmdEnv10 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("09", "trimelevator", 9, 9));
    HotKeyCmdEnv localHotKeyCmdEnv11 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove("10", "trimrudder", 10, 10));
    HotKeyCmdEnv localHotKeyCmdEnv12 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-power", 1, 11));
    HotKeyCmdEnv localHotKeyCmdEnv13 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-flaps", 2, 12));
    HotKeyCmdEnv localHotKeyCmdEnv14 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-aileron", 3, 13));
    HotKeyCmdEnv localHotKeyCmdEnv15 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-elevator", 4, 14));
    HotKeyCmdEnv localHotKeyCmdEnv16 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-rudder", 5, 15));
    HotKeyCmdEnv localHotKeyCmdEnv17 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-brakes", 6, 16));
    HotKeyCmdEnv localHotKeyCmdEnv18 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-pitch", 7, 17));
    HotKeyCmdEnv localHotKeyCmdEnv19 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-trimaileron", 8, 18));
    HotKeyCmdEnv localHotKeyCmdEnv20 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-trimelevator", 9, 19));
    HotKeyCmdEnv localHotKeyCmdEnv21 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFireMove(null, "-trimrudder", 10, 20));
  }

  public void createPilotHotKeys()
  {
    String str = "pilot";
    HotKeyCmdEnv.setCurrentEnv(str);
    HotKeyEnv.fromIni(str, Config.cur.ini, "HotKey " + str);
    HotKeyCmdEnv localHotKeyCmdEnv1 = HotKeyCmdEnv.currentEnv();
    HotKeyCmdEnv localHotKeyCmdEnv2 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic01", "ElevatorUp", 3, 103));
    HotKeyCmdEnv localHotKeyCmdEnv3 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic02", "ElevatorDown", 4, 104));
    HotKeyCmdEnv localHotKeyCmdEnv4 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic03", "AileronLeft", 5, 105));
    HotKeyCmdEnv localHotKeyCmdEnv5 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic04", "AileronRight", 6, 106));
    HotKeyCmdEnv localHotKeyCmdEnv6 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic05", "RudderLeft", 1, 101));
    HotKeyCmdEnv localHotKeyCmdEnv7 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic06", "RudderRight", 2, 102));
    HotKeyCmdEnv localHotKeyCmdEnv8 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic07", "Stabilizer", 71, 165));
    HotKeyCmdEnv localHotKeyCmdEnv9 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic08", "AIRCRAFT_RUDDER_LEFT_1", 56, 156));
    HotKeyCmdEnv localHotKeyCmdEnv10 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic09", "AIRCRAFT_RUDDER_CENTRE", 57, 157));
    HotKeyCmdEnv localHotKeyCmdEnv11 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic10", "AIRCRAFT_RUDDER_RIGHT_1", 58, 158));
    HotKeyCmdEnv localHotKeyCmdEnv12 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic11", "AIRCRAFT_TRIM_V_PLUS", 44, 144));
    HotKeyCmdEnv localHotKeyCmdEnv13 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic12", "AIRCRAFT_TRIM_V_0", 43, 143));
    HotKeyCmdEnv localHotKeyCmdEnv14 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic13", "AIRCRAFT_TRIM_V_MINUS", 45, 145));
    HotKeyCmdEnv localHotKeyCmdEnv15 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic14", "AIRCRAFT_TRIM_H_MINUS", 48, 148));
    HotKeyCmdEnv localHotKeyCmdEnv16 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic15", "AIRCRAFT_TRIM_H_0", 46, 146));
    HotKeyCmdEnv localHotKeyCmdEnv17 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic16", "AIRCRAFT_TRIM_H_PLUS", 47, 147));
    HotKeyCmdEnv localHotKeyCmdEnv18 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic17", "AIRCRAFT_TRIM_R_MINUS", 51, 151));
    HotKeyCmdEnv localHotKeyCmdEnv19 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic18", "AIRCRAFT_TRIM_R_0", 49, 149));
    HotKeyCmdEnv localHotKeyCmdEnv20 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("1basic19", "AIRCRAFT_TRIM_R_PLUS", 50, 150));
    HotKeyCmdEnv localHotKeyCmdEnv21 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$1", "1basic20")
    {
    });
    hudLogPowerId = HUD.makeIdLog();
    HotKeyCmdEnv localHotKeyCmdEnv22 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine01", "AIRCRAFT_TOGGLE_ENGINE", 70, 164));
    HotKeyCmdEnv localHotKeyCmdEnv23 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine02", "AIRCRAFT_POWER_PLUS_5", 59, 159));
    HotKeyCmdEnv localHotKeyCmdEnv24 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine03", "AIRCRAFT_POWER_MINUS_5", 60, 160));
    HotKeyCmdEnv localHotKeyCmdEnv25 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine04", "Boost", 61, 161));
    HotKeyCmdEnv localHotKeyCmdEnv26 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine05", "Power0", 20, 120));
    HotKeyCmdEnv localHotKeyCmdEnv27 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine06", "Power10", 21, 121));
    HotKeyCmdEnv localHotKeyCmdEnv28 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine07", "Power20", 22, 122));
    HotKeyCmdEnv localHotKeyCmdEnv29 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine08", "Power30", 23, 123));
    HotKeyCmdEnv localHotKeyCmdEnv30 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine09", "Power40", 24, 124));
    HotKeyCmdEnv localHotKeyCmdEnv31 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine10", "Power50", 25, 125));
    HotKeyCmdEnv localHotKeyCmdEnv32 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine11", "Power60", 26, 126));
    HotKeyCmdEnv localHotKeyCmdEnv33 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine12", "Power70", 27, 127));
    HotKeyCmdEnv localHotKeyCmdEnv34 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine13", "Power80", 28, 128));
    HotKeyCmdEnv localHotKeyCmdEnv35 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine14", "Power90", 29, 129));
    HotKeyCmdEnv localHotKeyCmdEnv36 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine15", "Power100", 30, 130));
    HotKeyCmdEnv localHotKeyCmdEnv37 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$2", "2engine16")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv38 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine17", "Step0", 31, 131));
    HotKeyCmdEnv localHotKeyCmdEnv39 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine18", "Step10", 32, 132));
    HotKeyCmdEnv localHotKeyCmdEnv40 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine19", "Step20", 33, 133));
    HotKeyCmdEnv localHotKeyCmdEnv41 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine20", "Step30", 34, 134));
    HotKeyCmdEnv localHotKeyCmdEnv42 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine21", "Step40", 35, 135));
    HotKeyCmdEnv localHotKeyCmdEnv43 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine22", "Step50", 36, 136));
    HotKeyCmdEnv localHotKeyCmdEnv44 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine23", "Step60", 37, 137));
    HotKeyCmdEnv localHotKeyCmdEnv45 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine24", "Step70", 38, 138));
    HotKeyCmdEnv localHotKeyCmdEnv46 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine25", "Step80", 39, 139));
    HotKeyCmdEnv localHotKeyCmdEnv47 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine26", "Step90", 40, 140));
    HotKeyCmdEnv localHotKeyCmdEnv48 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine27", "Step100", 41, 141));
    HotKeyCmdEnv localHotKeyCmdEnv49 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine28", "StepAuto", 42, 142));
    HotKeyCmdEnv localHotKeyCmdEnv50 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine29", "StepPlus5", 73, 290));
    HotKeyCmdEnv localHotKeyCmdEnv51 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine30", "StepMinus5", 74, 291));
    HotKeyCmdEnv localHotKeyCmdEnv52 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$3", "2engine31")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv53 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine32", "Mix0", 75, 292));
    HotKeyCmdEnv localHotKeyCmdEnv54 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine33", "Mix10", 76, 293));
    HotKeyCmdEnv localHotKeyCmdEnv55 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine34", "Mix20", 77, 294));
    HotKeyCmdEnv localHotKeyCmdEnv56 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine35", "Mix30", 78, 295));
    HotKeyCmdEnv localHotKeyCmdEnv57 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine36", "Mix40", 79, 296));
    HotKeyCmdEnv localHotKeyCmdEnv58 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine37", "Mix50", 80, 297));
    HotKeyCmdEnv localHotKeyCmdEnv59 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine38", "Mix60", 81, 298));
    HotKeyCmdEnv localHotKeyCmdEnv60 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine39", "Mix70", 82, 299));
    HotKeyCmdEnv localHotKeyCmdEnv61 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine40", "Mix80", 83, 300));
    HotKeyCmdEnv localHotKeyCmdEnv62 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine41", "Mix90", 84, 301));
    HotKeyCmdEnv localHotKeyCmdEnv63 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine42", "Mix100", 85, 302));
    HotKeyCmdEnv localHotKeyCmdEnv64 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine43", "MixPlus20", 86, 303));
    HotKeyCmdEnv localHotKeyCmdEnv65 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine44", "MixMinus20", 87, 304));
    HotKeyCmdEnv localHotKeyCmdEnv66 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$4", "2engine45")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv67 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine46", "MagnetoPlus", 88, 305));
    HotKeyCmdEnv localHotKeyCmdEnv68 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine47", "MagnetoMinus", 89, 306));
    HotKeyCmdEnv localHotKeyCmdEnv69 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$5", "2engine48")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv70 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine49", "CompressorPlus", 115, 334));
    HotKeyCmdEnv localHotKeyCmdEnv71 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine50", "CompressorMinus", 116, 335));
    HotKeyCmdEnv localHotKeyCmdEnv72 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$6", "2engine51")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv73 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine52", "EngineSelectAll", 90, 307));
    HotKeyCmdEnv localHotKeyCmdEnv74 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine53", "EngineSelectNone", 91, 318));
    HotKeyCmdEnv localHotKeyCmdEnv75 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine54", "EngineSelectLeft", 92, 316));
    HotKeyCmdEnv localHotKeyCmdEnv76 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine55", "EngineSelectRight", 93, 317));
    HotKeyCmdEnv localHotKeyCmdEnv77 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine56", "EngineSelect1", 94, 308));
    HotKeyCmdEnv localHotKeyCmdEnv78 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine57", "EngineSelect2", 95, 309));
    HotKeyCmdEnv localHotKeyCmdEnv79 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine58", "EngineSelect3", 96, 310));
    HotKeyCmdEnv localHotKeyCmdEnv80 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine59", "EngineSelect4", 97, 311));
    HotKeyCmdEnv localHotKeyCmdEnv81 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine60", "EngineSelect5", 98, 312));
    HotKeyCmdEnv localHotKeyCmdEnv82 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine61", "EngineSelect6", 99, 313));
    HotKeyCmdEnv localHotKeyCmdEnv83 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine62", "EngineSelect7", 100, 314));
    HotKeyCmdEnv localHotKeyCmdEnv84 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine63", "EngineSelect8", 101, 315));
    HotKeyCmdEnv localHotKeyCmdEnv85 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine64", "EngineToggleAll", 102, 319));
    HotKeyCmdEnv localHotKeyCmdEnv86 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine65", "EngineToggleLeft", 103, 328));
    HotKeyCmdEnv localHotKeyCmdEnv87 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine66", "EngineToggleRight", 104, 329));
    HotKeyCmdEnv localHotKeyCmdEnv88 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine67", "EngineToggle1", 105, 320));
    HotKeyCmdEnv localHotKeyCmdEnv89 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine68", "EngineToggle2", 106, 321));
    HotKeyCmdEnv localHotKeyCmdEnv90 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine69", "EngineToggle3", 107, 322));
    HotKeyCmdEnv localHotKeyCmdEnv91 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine70", "EngineToggle4", 108, 323));
    HotKeyCmdEnv localHotKeyCmdEnv92 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine71", "EngineToggle5", 109, 324));
    HotKeyCmdEnv localHotKeyCmdEnv93 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine72", "EngineToggle6", 110, 325));
    HotKeyCmdEnv localHotKeyCmdEnv94 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine73", "EngineToggle7", 111, 326));
    HotKeyCmdEnv localHotKeyCmdEnv95 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine74", "EngineToggle8", 112, 327));
    HotKeyCmdEnv localHotKeyCmdEnv96 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$7", "2engine75")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv97 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine76", "EngineExtinguisher", 113, 330));
    HotKeyCmdEnv localHotKeyCmdEnv98 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("2engine77", "EngineFeather", 114, 333));
    HotKeyCmdEnv localHotKeyCmdEnv99 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$8", "2engine78")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv100 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced01", "AIRCRAFT_FLAPS_NOTCH_UP", 52, 152));
    HotKeyCmdEnv localHotKeyCmdEnv101 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced02", "AIRCRAFT_FLAPS_NOTCH_DOWN", 53, 153));
    HotKeyCmdEnv localHotKeyCmdEnv102 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced03", "Gear", 9, 109));
    HotKeyCmdEnv localHotKeyCmdEnv103 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced04", "AIRCRAFT_GEAR_UP_MANUAL", 54, 154));
    HotKeyCmdEnv localHotKeyCmdEnv104 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced05", "AIRCRAFT_GEAR_DOWN_MANUAL", 55, 155));
    HotKeyCmdEnv localHotKeyCmdEnv105 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced06", "Radiator", 7, 107));
    HotKeyCmdEnv localHotKeyCmdEnv106 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced07", "AIRCRAFT_TOGGLE_AIRBRAKE", 63, 163));
    HotKeyCmdEnv localHotKeyCmdEnv107 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced08", "Brake", 0, 100));
    HotKeyCmdEnv localHotKeyCmdEnv108 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced09", "AIRCRAFT_TAILWHEELLOCK", 72, 166));
    HotKeyCmdEnv localHotKeyCmdEnv109 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced10", "AIRCRAFT_DROP_TANKS", 62, 162));
    HotKeyCmdEnv localHotKeyCmdEnv110 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$9", "3advanced11")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv111 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced12", "AIRCRAFT_DOCK_UNDOCK", 126, 346));
    HotKeyCmdEnv localHotKeyCmdEnv112 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced13", "WINGFOLD", 127, 347));
    HotKeyCmdEnv localHotKeyCmdEnv113 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced14", "AIRCRAFT_CARRIERHOOK", 129, 349));
    HotKeyCmdEnv localHotKeyCmdEnv114 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced15", "AIRCRAFT_BRAKESHOE", 130, 350));
    HotKeyCmdEnv localHotKeyCmdEnv115 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("3advanced16", "COCKPITDOOR", 128, 348));
    HotKeyCmdEnv localHotKeyCmdEnv116 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$$10", "3advanced17")
    {
    });
    hudLogWeaponId = HUD.makeIdLog();
    HotKeyCmdEnv localHotKeyCmdEnv117 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic0", "Weapon0", 16, 116));
    HotKeyCmdEnv localHotKeyCmdEnv118 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic1", "Weapon1", 17, 117));
    HotKeyCmdEnv localHotKeyCmdEnv119 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic2", "Weapon2", 18, 118));
    HotKeyCmdEnv localHotKeyCmdEnv120 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic3", "Weapon3", 19, 119));
    HotKeyCmdEnv localHotKeyCmdEnv121 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic4", "Weapon01", 64, 173));
    HotKeyCmdEnv localHotKeyCmdEnv122 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("4basic5", "GunPods", 15, 115));
    HotKeyCmdEnv localHotKeyCmdEnv123 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmd(false, "$$+SIGHTCONTROLS", "4basic6")
    {
    });
    HotKeyCmdEnv localHotKeyCmdEnv124 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced01", "SIGHT_AUTO_ONOFF", 125, 344));
    HotKeyCmdEnv localHotKeyCmdEnv125 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced02", "SIGHT_DIST_PLUS", 117, 336));
    HotKeyCmdEnv localHotKeyCmdEnv126 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced03", "SIGHT_DIST_MINUS", 118, 337));
    HotKeyCmdEnv localHotKeyCmdEnv127 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced04", "SIGHT_SIDE_RIGHT", 119, 338));
    HotKeyCmdEnv localHotKeyCmdEnv128 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced05", "SIGHT_SIDE_LEFT", 120, 339));
    HotKeyCmdEnv localHotKeyCmdEnv129 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced06", "SIGHT_ALT_PLUS", 121, 340));
    HotKeyCmdEnv localHotKeyCmdEnv130 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced07", "SIGHT_ALT_MINUS", 122, 341));
    HotKeyCmdEnv localHotKeyCmdEnv131 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced08", "SIGHT_SPD_PLUS", 123, 342));
    HotKeyCmdEnv localHotKeyCmdEnv132 = localHotKeyCmdEnv1;
    HotKeyCmdEnv.addCmd(new HotKeyCmdFire("5advanced09", "SIGHT_SPD_MINUS", 124, 343));
  }

  private CockpitGunner getActiveCockpitGuner()
  {
    if (!Actor.isAlive(World.getPlayerAircraft()))
      return null;
    if (World.isPlayerParatrooper())
      return null;
    if (World.isPlayerDead())
      return null;
    if (Main3D.cur3D().cockpits == null)
      return null;
    int i = World.getPlayerAircraft().FM.AS.astatePlayerIndex;
    for (int j = 0; j < Main3D.cur3D().cockpits.length; j++) {
      if (!(Main3D.cur3D().cockpits[j] instanceof CockpitGunner))
        continue;
      CockpitGunner localCockpitGunner = (CockpitGunner)Main3D.cur3D().cockpits[j];
      if ((i != localCockpitGunner.astatePilotIndx()) || (!localCockpitGunner.isRealMode()))
        continue;
      Turret localTurret = localCockpitGunner.aiTurret();
      if (!localTurret.bIsNetMirror) {
        return localCockpitGunner;
      }
    }

    return null;
  }

  public void createGunnerHotKeys()
  {
    String str = "gunner";
    HotKeyCmdEnv.setCurrentEnv(str);
    HotKeyEnv.fromIni(str, Config.cur.ini, "HotKey " + str);
    HotKeyCmdEnv.addCmd(str, new HotKeyCmdMouseMove(true, "Mouse")
    {
      public void created()
      {
        setRecordId(51);
        this.sortingName = null;
      }

      public boolean isDisableIfTimePaused()
      {
        return true;
      }

      public void move(int paramInt1, int paramInt2, int paramInt3)
      {
        CockpitGunner localCockpitGunner = AircraftHotKeys.this.getActiveCockpitGuner();
        if (localCockpitGunner == null)
        {
          return;
        }

        localCockpitGunner.hookGunner().mouseMove(paramInt1, paramInt2, paramInt3);
      }
    });
    HotKeyCmdEnv.addCmd(str, new HotKeyCmd(true, "Fire")
    {
      CockpitGunner coc = null;

      public void created()
      {
        setRecordId(52);
      }

      public boolean isDisableIfTimePaused()
      {
        return true;
      }

      private boolean isExistAmmo(CockpitGunner paramCockpitGunner)
      {
        FlightModel localFlightModel = World.getPlayerFM();
        BulletEmitter[] arrayOfBulletEmitter = localFlightModel.CT.Weapons[paramCockpitGunner.weaponControlNum()];
        if (arrayOfBulletEmitter == null)
          return false;
        for (int i = 0; i < arrayOfBulletEmitter.length; i++) {
          if ((arrayOfBulletEmitter[i] != null) && (arrayOfBulletEmitter[i].haveBullets()))
            return true;
        }
        return false;
      }

      public void begin()
      {
        this.coc = AircraftHotKeys.this.getActiveCockpitGuner();
        if (this.coc == null)
          return;
        if (isExistAmmo(this.coc))
          this.coc.hookGunner().gunFire(true);
        else
          HUD.log(AircraftHotKeys.hudLogWeaponId, "OutOfAmmo");
      }

      public void end()
      {
        if (this.coc == null)
          return;
        if (Actor.isValid(this.coc))
          this.coc.hookGunner().gunFire(false);
        this.coc = null;
      }
    });
  }

  public boolean isAutoAutopilot()
  {
    return this.bAutoAutopilot;
  }

  public void setAutoAutopilot(boolean paramBoolean)
  {
    this.bAutoAutopilot = paramBoolean;
  }

  public static boolean isCockpitRealMode(int paramInt)
  {
    Object localObject;
    if ((Main3D.cur3D().cockpits[paramInt] instanceof CockpitPilot))
    {
      localObject = (RealFlightModel)World.getPlayerFM();
      return ((RealFlightModel)localObject).isRealMode();
    }
    if ((Main3D.cur3D().cockpits[paramInt] instanceof CockpitGunner))
    {
      localObject = (CockpitGunner)Main3D.cur3D().cockpits[paramInt];
      return ((CockpitGunner)localObject).isRealMode();
    }

    return false;
  }

  public static void setCockpitRealMode(int paramInt, boolean paramBoolean)
  {
    Object localObject1;
    if ((Main3D.cur3D().cockpits[paramInt] instanceof CockpitPilot))
    {
      if (Mission.isNet())
        return;
      localObject1 = (RealFlightModel)World.getPlayerFM();
      if (((RealFlightModel)localObject1).get_maneuver() == 44)
        return;
      if (((RealFlightModel)localObject1).isRealMode() == paramBoolean)
        return;
      if (((RealFlightModel)localObject1).isRealMode())
        Main3D.cur3D().aircraftHotKeys.bAfterburner = false;
      ((FlightModelMain)localObject1).CT.resetControl(0);
      ((FlightModelMain)localObject1).CT.resetControl(1);
      ((FlightModelMain)localObject1).CT.resetControl(2);
      ((FlightModelMain)localObject1).EI.setCurControlAll(true);
      ((RealFlightModel)localObject1).setRealMode(paramBoolean);
      HUD.log("PilotAI" + (((RealFlightModel)localObject1).isRealMode() ? "OFF" : "ON"));
    }
    else if ((Main3D.cur3D().cockpits[paramInt] instanceof CockpitGunner))
    {
      localObject1 = (CockpitGunner)Main3D.cur3D().cockpits[paramInt];
      if (((CockpitGunner)localObject1).isRealMode() == paramBoolean)
        return;
      ((CockpitGunner)localObject1).setRealMode(paramBoolean);
      if (!NetMissionTrack.isPlaying())
      {
        localObject2 = World.getPlayerAircraft();
        if (World.isPlayerGunner())
          ((Aircraft)localObject2).netCockpitAuto(World.getPlayerGunner(), paramInt, !((CockpitGunner)localObject1).isRealMode());
        else
          ((Aircraft)localObject2).netCockpitAuto((Actor)localObject2, paramInt, !((CockpitGunner)localObject1).isRealMode());
      }
      Object localObject2 = World.getPlayerFM();
      AircraftState localAircraftState = ((FlightModelMain)localObject2).AS;
      String str = AircraftState.astateHUDPilotHits[localObject2.AS.astatePilotFunctions[localObject1.astatePilotIndx()]];
      HUD.log(str + (((CockpitGunner)localObject1).isRealMode() ? "AIOFF" : "AION"));
    }
  }

  private boolean isMiscValid()
  {
    if (!Actor.isAlive(World.getPlayerAircraft()))
      return false;
    if (World.isPlayerParatrooper())
      return false;
    if (World.isPlayerDead()) {
      return false;
    }
    return Mission.isPlaying();
  }

  public void createMiscHotKeys()
  {
    String str = "misc";
    HotKeyCmdEnv.setCurrentEnv(str);
    HotKeyEnv.fromIni(str, Config.cur.ini, "HotKey " + str);
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "autopilot", "00")
    {
      public void created()
      {
        setRecordId(270);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        if (Main3D.cur3D().isDemoPlaying())
          return;
        if (World.getPlayerFM().AS.isPilotDead(Main3D.cur3D().cockpitCur.astatePilotIndx()))
          return;
        int i = Main3D.cur3D().cockpitCurIndx();
        if (AircraftHotKeys.isCockpitRealMode(i)) {
          new AircraftHotKeys.15(this, true, new Integer(i));
        }
        else
        {
          new AircraftHotKeys.16(this, true, new Integer(i));
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "autopilotAuto", "01")
    {
      public void begin()
      {
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        if (Main3D.cur3D().isDemoPlaying())
        {
          return;
        }

        new AircraftHotKeys.18(this, true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "autopilotAuto_", null)
    {
      public void created()
      {
        setRecordId(271);
        HotKeyEnv.currentEnv().remove(this.sName);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.isMiscValid())
        {
          return;
        }

        AircraftHotKeys.this.setAutoAutopilot(!AircraftHotKeys.this.isAutoAutopilot());
        HUD.log("AutopilotAuto" + (AircraftHotKeys.this.isAutoAutopilot() ? "ON" : "OFF"));
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "target_", null)
    {
      public void created()
      {
        setRecordId(278);
        HotKeyEnv.currentEnv().remove(this.sName);
      }

      public void begin()
      {
        Actor localActor = null;
        if (Main3D.cur3D().isDemoPlaying())
          localActor = Selector._getTrackArg0();
        else
          localActor = HookPilot.cur().getEnemy();
        Selector.setTarget(Selector.setCurRecordArg0(localActor));
      }
    });
    for (int i = 0; i < 10; i++)
    {
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitRealOn" + i, null) {
        int indx;

        public void created() { this.indx = (Character.getNumericValue(name().charAt(name().length() - 1)) - Character.getNumericValue('0'));
          setRecordId(500 + this.indx);
          HotKeyEnv.currentEnv().remove(this.sName);
        }

        public void begin()
        {
          if (!AircraftHotKeys.this.isMiscValid())
          {
            return;
          }

          AircraftHotKeys.setCockpitRealMode(this.indx, true);
        }
      });
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitRealOff" + i, null) {
        int indx;

        public void created() { this.indx = (Character.getNumericValue(name().charAt(name().length() - 1)) - Character.getNumericValue('0'));
          setRecordId(510 + this.indx);
          HotKeyEnv.currentEnv().remove(this.sName);
        }

        public void begin()
        {
          if (!AircraftHotKeys.this.isMiscValid())
          {
            return;
          }

          AircraftHotKeys.setCockpitRealMode(this.indx, false);
        }
      });
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitEnter" + i, null) {
        int indx;

        public void created() { this.indx = (Character.getNumericValue(name().charAt(name().length() - 1)) - Character.getNumericValue('0'));
          setRecordId(520 + this.indx);
          HotKeyEnv.currentEnv().remove(this.sName);
        }

        public void begin()
        {
          if (!AircraftHotKeys.this.isMiscValid())
            return;
          if ((Main3D.cur3D().cockpits != null) && (this.indx < Main3D.cur3D().cockpits.length))
          {
            World.getPlayerAircraft().FM.AS.astatePlayerIndex = Main3D.cur3D().cockpits[this.indx].astatePilotIndx();
            if (!NetMissionTrack.isPlaying())
            {
              Aircraft localAircraft = World.getPlayerAircraft();
              if (World.isPlayerGunner())
                localAircraft.netCockpitEnter(World.getPlayerGunner(), this.indx);
              else
                localAircraft.netCockpitEnter(localAircraft, this.indx);
            }
          }
        }
      });
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitLeave" + i, null) {
        int indx;

        public void created() { this.indx = (Character.getNumericValue(name().charAt(name().length() - 1)) - Character.getNumericValue('0'));
          setRecordId(530 + this.indx);
          HotKeyEnv.currentEnv().remove(this.sName);
        }

        public void begin()
        {
          if (!AircraftHotKeys.this.isMiscValid())
            return;
          if ((Main3D.cur3D().cockpits != null) && (this.indx < Main3D.cur3D().cockpits.length) && ((Main3D.cur3D().cockpits[this.indx] instanceof CockpitGunner)) && (AircraftHotKeys.isCockpitRealMode(this.indx))) {
            ((CockpitGunner)Main3D.cur3D().cockpits[this.indx]).hookGunner().gunFire(false);
          }

        }

      });
    }

    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ejectPilot", "02")
    {
      public void created()
      {
        setRecordId(272);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        if (World.isPlayerGunner())
          return;
        if (!(World.getPlayerFM() instanceof RealFlightModel))
          return;
        RealFlightModel localRealFlightModel = (RealFlightModel)World.getPlayerFM();
        if (!localRealFlightModel.isRealMode())
          return;
        if (localRealFlightModel.AS.bIsAboutToBailout)
          return;
        if (!localRealFlightModel.AS.bIsEnableToBailout)
        {
          return;
        }

        AircraftState.bCheckPlayerAircraft = false;
        ((Aircraft)localRealFlightModel.actor).hitDaSilk();
        AircraftState.bCheckPlayerAircraft = true;
        com.maddox.il2.objects.sounds.Voice.cur().SpeakBailOut[(localRealFlightModel.actor.getArmy() - 1 & 0x1)][((Aircraft)localRealFlightModel.actor).aircIndex()] = ((int)(Time.current() / 60000L) + 1);
        new AircraftHotKeys.26(this, true);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitDim", "03")
    {
      public void created()
      {
        setRecordId(274);
      }

      public void begin()
      {
        if (Main3D.cur3D().isViewOutside())
          return;
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        if (!Actor.isValid(Main3D.cur3D().cockpitCur))
        {
          return;
        }

        Main3D.cur3D().cockpitCur.doToggleDim();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitLight", "04")
    {
      public void created()
      {
        setRecordId(275);
      }

      public void begin()
      {
        if (Main3D.cur3D().isViewOutside())
          return;
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        if (!Actor.isValid(Main3D.cur3D().cockpitCur))
        {
          return;
        }

        Main3D.cur3D().cockpitCur.doToggleLight();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "toggleNavLights", "05")
    {
      public void created()
      {
        setRecordId(331);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        FlightModel localFlightModel = World.getPlayerFM();
        if (localFlightModel == null)
          return;
        boolean bool = localFlightModel.AS.bNavLightsOn;
        localFlightModel.AS.setNavLightsState(!localFlightModel.AS.bNavLightsOn);
        if ((!bool) && (!localFlightModel.AS.bNavLightsOn))
        {
          return;
        }

        HUD.log("NavigationLights" + (localFlightModel.AS.bNavLightsOn ? "ON" : "OFF"));
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "toggleLandingLight", "06")
    {
      public void created()
      {
        setRecordId(345);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        FlightModel localFlightModel = World.getPlayerFM();
        if (localFlightModel == null)
          return;
        boolean bool = localFlightModel.AS.bLandingLightOn;
        localFlightModel.AS.setLandingLightState(!localFlightModel.AS.bLandingLightOn);
        if ((!bool) && (!localFlightModel.AS.bLandingLightOn))
        {
          return;
        }

        HUD.log("LandingLight" + (localFlightModel.AS.bLandingLightOn ? "ON" : "OFF"));
        EventLog.onToggleLandingLight(localFlightModel.actor, localFlightModel.AS.bLandingLightOn);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "toggleSmokes", "07")
    {
      public void created()
      {
        setRecordId(273);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        FlightModel localFlightModel = World.getPlayerFM();
        if (localFlightModel == null)
        {
          return;
        }

        localFlightModel.AS.setAirShowSmokeType(AircraftHotKeys.this.iAirShowSmoke);
        localFlightModel.AS.setAirShowSmokeEnhanced(AircraftHotKeys.this.bAirShowSmokeEnhanced);
        localFlightModel.AS.setAirShowState(!localFlightModel.AS.bShowSmokesOn);
        EventLog.onToggleSmoke(localFlightModel.actor, localFlightModel.AS.bShowSmokesOn);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "pad", "08")
    {
      public void end()
      {
        int i = Main.state().id();
        int j = (i == 5) || (i == 29) || (i == 63) || (i == 49) || (i == 50) || (i == 42) || (i == 43) ? 1 : 0;
        if (GUI.pad.isActive()) {
          GUI.pad.leave(j == 0);
        }
        else if ((j != 0) && (!Main3D.cur3D().guiManager.isMouseActive()))
          GUI.pad.enter();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "chat", "09")
    {
      public void end()
      {
        GUI.chatActivate();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "onlineRating", "10")
    {
      public void begin()
      {
        Main3D.cur3D().hud.startNetStat();
      }

      public void end()
      {
        Main3D.cur3D().hud.stopNetStat();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "onlineRatingPage", "11")
    {
      public void end()
      {
        Main3D.cur3D().hud.pageNetStat();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "showPositionHint", "12")
    {
      public void begin()
      {
        if (!AircraftHotKeys.this.bSpeedbarTAS)
          HUD.setDrawSpeed((HUD.drawSpeed() + 1) % 4);
        else
          HUD.setDrawSpeed((HUD.drawSpeed() + 1) % 7);
      }

      public void created()
      {
        setRecordId(277);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "iconTypes", "13")
    {
      public void end()
      {
        Main3D.cur3D().changeIconTypes();
      }

      public void created()
      {
        setRecordId(279);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "showMirror", "14")
    {
      public void end()
      {
        Main3D.cur3D().viewMirror = ((Main3D.cur3D().viewMirror + 1) % 3);
      }

      public void created()
      {
        setRecordId(280);
      }
    });
  }

  public void create_MiscHotKeys()
  {
    String str = "$$$misc";
    HotKeyCmdEnv.setCurrentEnv(str);
    HotKeyEnv.fromIni(str, Config.cur.ini, "HotKey " + str);
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "quickSaveNetTrack", "01")
    {
      public void end()
      {
        GUIWindowManager localGUIWindowManager = Main3D.cur3D().guiManager;
        if (localGUIWindowManager.isKeyboardActive())
          return;
        if (NetMissionTrack.isQuickRecording())
          NetMissionTrack.stopRecording();
        else
          NetMissionTrack.startQuickRecording();
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "radioMuteKey", "02")
    {
      public void begin()
      {
        AudioDevice.setPTT(true);
      }

      public void end()
      {
        AudioDevice.setPTT(false);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "radioChannelSwitch", "03")
    {
      public void end()
      {
        if (GUI.chatDlg == null)
          return;
        if (Main.cur().chat == null)
          return;
        if (GUI.chatDlg.mode() == 2)
          return;
        if (RadioChannel.tstLoop)
          return;
        if (!AudioDevice.npFlags.get(0))
          return;
        if (NetMissionTrack.isPlaying())
          return;
        NetUser localNetUser = (NetUser)NetEnv.host();
        String str1 = null;
        String str2 = null;
        if (localNetUser.isRadioPrivate())
        {
          str1 = "radio NONE";
          str2 = "radioNone";
        }
        else if (localNetUser.isRadioArmy())
        {
          str1 = "radio NONE";
          str2 = "radioNone";
        }
        else if (localNetUser.isRadioCommon())
        {
          if (localNetUser.getArmy() != 0)
          {
            str1 = "radio ARMY";
            str2 = "radioArmy";
          }
          else {
            str1 = "radio NONE";
            str2 = "radioNone";
          }
        }
        else if (localNetUser.isRadioNone())
        {
          str1 = "radio COMMON";
          str2 = "radioCommon";
        }
        System.out.println(RTSConf.cur.console.getPrompt() + str1);
        RTSConf.cur.console.getEnv().exec(str1);
        RTSConf.cur.console.addHistoryCmd(str1);
        RTSConf.cur.console.curHistoryCmd = -1;
        if (!Time.isPaused())
          HUD.log(str2);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "soundMuteKey", "04")
    {
      public void end()
      {
        AudioDevice.toggleMute();
      }
    });
  }

  private void switchToAIGunner()
  {
    if ((!Main3D.cur3D().isDemoPlaying()) && ((Main3D.cur3D().cockpitCur instanceof CockpitGunner)) && (Main3D.cur3D().isViewOutside()) && (isAutoAutopilot()))
    {
      CockpitGunner localCockpitGunner = (CockpitGunner)Main3D.cur3D().cockpitCur;
      if (localCockpitGunner.isRealMode())
        new MsgAction(true, new Integer(Main3D.cur3D().cockpitCurIndx()))
        {
          public void doAction(Object paramObject)
          {
            int i = ((Integer)paramObject).intValue();
            HotKeyCmd.exec("misc", "cockpitRealOff" + i);
          }
        };
    }
  }

  private boolean isValidCockpit(int paramInt)
  {
    if (!Actor.isValid(World.getPlayerAircraft()))
      return false;
    if (!Mission.isPlaying())
      return false;
    if (World.isPlayerParatrooper())
      return false;
    if (Main3D.cur3D().cockpits == null)
      return false;
    if (paramInt >= Main3D.cur3D().cockpits.length)
      return false;
    if (World.getPlayerAircraft().isUnderWater())
      return false;
    Cockpit localCockpit = Main3D.cur3D().cockpits[paramInt];
    if (!localCockpit.isEnableFocusing())
      return false;
    int i = localCockpit.astatePilotIndx();
    if (World.getPlayerFM().AS.isPilotParatrooper(i))
      return false;
    if (World.getPlayerFM().AS.isPilotDead(i))
      return false;
    if (Mission.isNet())
    {
      if (Mission.isCoop())
      {
        if (World.isPlayerGunner())
        {
          if ((localCockpit instanceof CockpitPilot))
            return false;
        }
        else if ((localCockpit instanceof CockpitPilot))
          return true;
        if (Time.current() == 0L)
          return false;
        if (Main3D.cur3D().isDemoPlaying()) {
          return true;
        }
        return (!Actor.isValid(World.getPlayerAircraft().netCockpitGetDriver(paramInt))) || (World.isPlayerDead());
      }

      return Mission.isDogfight();
    }

    return true;
  }

  private void switchToCockpit(int paramInt)
  {
    if ((Mission.isCoop()) && ((Main3D.cur3D().cockpits[paramInt] instanceof CockpitGunner)) && (!Main3D.cur3D().isDemoPlaying()) && (!World.isPlayerDead()))
    {
      Object localObject = World.getPlayerAircraft();
      if (World.isPlayerGunner())
        localObject = World.getPlayerGunner();
      Actor localActor = World.getPlayerAircraft().netCockpitGetDriver(paramInt);
      if (localObject != localActor) {
        if (Actor.isValid(localActor))
        {
          return;
        }

        this.switchToCockpitRequest = paramInt;
        World.getPlayerAircraft().netCockpitDriverRequest((Actor)localObject, paramInt);
        return;
      }
    }
    doSwitchToCockpit(paramInt);
  }

  public void netSwitchToCockpit(int paramInt)
  {
    if (Main3D.cur3D().isDemoPlaying())
      return;
    if (paramInt == this.switchToCockpitRequest)
      new MsgAction(true, new Integer(paramInt))
      {
        public void doAction(Object paramObject)
        {
          int i = ((Integer)paramObject).intValue();
          HotKeyCmd.exec("aircraftView", "cockpitSwitch" + i);
        }
      };
  }

  private void doSwitchToCockpit(int paramInt)
  {
    Selector.setCurRecordArg0(World.getPlayerAircraft());
    if ((!World.isPlayerDead()) && (!World.isPlayerParatrooper()) && (!Main3D.cur3D().isDemoPlaying()))
    {
      int i = 1;
      if (((Main3D.cur3D().cockpitCur instanceof CockpitPilot)) && ((Main3D.cur3D().cockpits[paramInt] instanceof CockpitPilot)))
        i = 0;
      if ((i != 0) && (isAutoAutopilot())) {
        new MsgAction(true, new Integer(Main3D.cur3D().cockpitCurIndx()))
        {
          public void doAction(Object paramObject)
          {
            int i = ((Integer)paramObject).intValue();
            HotKeyCmd.exec("misc", "cockpitRealOff" + i);
          }
        };
      }
      new MsgAction(true, new Integer(Main3D.cur3D().cockpitCurIndx()))
      {
        public void doAction(Object paramObject)
        {
          int i = ((Integer)paramObject).intValue();
          HotKeyCmd.exec("misc", "cockpitLeave" + i);
        }
      };
      new MsgAction(true, new Integer(paramInt))
      {
        public void doAction(Object paramObject)
        {
          int i = ((Integer)paramObject).intValue();
          HotKeyCmd.exec("misc", "cockpitEnter" + i);
        }
      };
      if ((i != 0) && (isAutoAutopilot())) {
        new MsgAction(true, new Integer(paramInt))
        {
          public void doAction(Object paramObject)
          {
            int i = ((Integer)paramObject).intValue();
            HotKeyCmd.exec("misc", "cockpitRealOn" + i);
          }
        };
      }
    }
    Main3D.cur3D().cockpitCur.focusLeave();
    Main3D.cur3D().cockpitCur = Main3D.cur3D().cockpits[paramInt];
    Main3D.cur3D().cockpitCur.focusEnter();
  }

  private int nextValidCockpit()
  {
    int i = Main3D.cur3D().cockpitCurIndx();
    if (i < 0)
      return -1;
    int j = Main3D.cur3D().cockpits.length;
    if (j < 2)
      return -1;
    for (int k = 0; k < j - 1; k++)
    {
      int m = (i + k + 1) % j;
      if (isValidCockpit(m)) {
        return m;
      }
    }
    return -1;
  }

  public void setEnableChangeFov(boolean paramBoolean)
  {
    for (int i = 0; i < this.cmdFov.length; i++)
      this.cmdFov[i].enable(paramBoolean);
  }

  public void createViewHotKeys()
  {
    String str = "aircraftView";
    HotKeyCmdEnv.setCurrentEnv(str);
    HotKeyEnv.fromIni(str, Config.cur.ini, "HotKey " + str);
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "changeCockpit", "0")
    {
      public void begin()
      {
        int i = AircraftHotKeys.this.nextValidCockpit();
        if (i < 0)
        {
          return;
        }

        new AircraftHotKeys.50(this, true, new Integer(i));
      }
    });
    for (int i = 0; i < 10; i++)
    {
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitView" + i, "0" + i) {
        int indx;

        public void created() { this.indx = (Character.getNumericValue(name().charAt(name().length() - 1)) - Character.getNumericValue('0'));
        }

        public void begin()
        {
          if (!AircraftHotKeys.this.isValidCockpit(this.indx))
          {
            return;
          }

          new AircraftHotKeys.52(this, true, new Integer(this.indx));
        }
      });
      HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitSwitch" + i, null) {
        int indx;

        public void created() { this.indx = (Character.getNumericValue(name().charAt(name().length() - 1)) - Character.getNumericValue('0'));
          setRecordId(230 + this.indx);
          HotKeyEnv.currentEnv().remove(this.sName);
        }

        public void begin()
        {
          if ((Main3D.cur3D().cockpitCurIndx() == this.indx) && (!Main3D.cur3D().isViewOutside()))
          {
            return;
          }

          AircraftHotKeys.this.switchToCockpit(this.indx);
        }

      });
    }

    HotKeyCmdEnv.addCmd(this.cmdFov[0] =  = new HotKeyCmd(true, "fov90", "11")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 90");
      }

      public void created()
      {
        setRecordId(216);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[1] =  = new HotKeyCmd(true, "fov85", "12")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 85");
      }

      public void created()
      {
        setRecordId(244);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[2] =  = new HotKeyCmd(true, "fov80", "13")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 80");
      }

      public void created()
      {
        setRecordId(243);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[3] =  = new HotKeyCmd(true, "fov75", "14")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 75");
      }

      public void created()
      {
        setRecordId(242);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[4] =  = new HotKeyCmd(true, "fov70", "15")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 70");
      }

      public void created()
      {
        setRecordId(215);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[5] =  = new HotKeyCmd(true, "fov65", "16")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 65");
      }

      public void created()
      {
        setRecordId(241);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[6] =  = new HotKeyCmd(true, "fov60", "17")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 60");
      }

      public void created()
      {
        setRecordId(240);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[7] =  = new HotKeyCmd(true, "fov55", "18")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 55");
      }

      public void created()
      {
        setRecordId(229);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[8] =  = new HotKeyCmd(true, "fov50", "19")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 50");
      }

      public void created()
      {
        setRecordId(228);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[9] =  = new HotKeyCmd(true, "fov45", "20")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 45");
      }

      public void created()
      {
        setRecordId(227);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[10] =  = new HotKeyCmd(true, "fov40", "21")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 40");
      }

      public void created()
      {
        setRecordId(226);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[11] =  = new HotKeyCmd(true, "fov35", "22")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 35");
      }

      public void created()
      {
        setRecordId(225);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[12] =  = new HotKeyCmd(true, "fov30", "23")
    {
      public void begin()
      {
        CmdEnv.top().exec("fov 30");
      }

      public void created()
      {
        setRecordId(214);
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[13] =  = new HotKeyCmd(true, "fovSwitch", "24")
    {
      public void begin()
      {
        float f1 = (Main3D.FOVX - 30.0F) * (Main3D.FOVX - 30.0F);
        float f2 = (Main3D.FOVX - 70.0F) * (Main3D.FOVX - 70.0F);
        float f3 = (Main3D.FOVX - 90.0F) * (Main3D.FOVX - 90.0F);
        int i = 0;
        if (f1 <= f2) {
          i = 70;
        }
        else if (f2 <= f3)
          i = 90;
        else
          i = 30;
        new AircraftHotKeys.68(this, true, new Integer(i));
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[14] =  = new HotKeyCmd(true, "fovInc", "25")
    {
      public void begin()
      {
        int i = (int)(Main3D.FOVX + 2.5D) / 5 * 5;
        if (i < 30)
          i = 30;
        if (i > 85)
          i = 85;
        i += 5;
        new AircraftHotKeys.70(this, true, new Integer(i));
      }
    });
    HotKeyCmdEnv.addCmd(this.cmdFov[15] =  = new HotKeyCmd(true, "fovDec", "26")
    {
      public void begin()
      {
        int i = (int)(Main3D.FOVX + 2.5D) / 5 * 5;
        if (i < 35)
          i = 35;
        if (i > 90)
          i = 90;
        i -= 5;
        new AircraftHotKeys.72(this, true, new Integer(i));
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "CockpitView", "27")
    {
      public void begin()
      {
        if (!Actor.isValid(World.getPlayerAircraft()))
          return;
        if (World.isPlayerParatrooper())
          return;
        if (World.getPlayerAircraft().isUnderWater())
          return;
        Main3D.cur3D().setViewInside();
        Selector.setCurRecordArg0(World.getPlayerAircraft());
        if ((!Main3D.cur3D().isDemoPlaying()) && (World.getPlayerAircraft().netCockpitGetDriver(Main3D.cur3D().cockpitCurIndx()) == null)) {
          new AircraftHotKeys.74(this, true, new Integer(Main3D.cur3D().cockpitCurIndx()));
        }

        if ((!Main3D.cur3D().isDemoPlaying()) && (!Main3D.cur3D().isViewOutside()) && (AircraftHotKeys.this.isAutoAutopilot()) && (!AircraftHotKeys.isCockpitRealMode(Main3D.cur3D().cockpitCurIndx())))
          new AircraftHotKeys.75(this, true, new Integer(Main3D.cur3D().cockpitCurIndx()));
      }

      public void created()
      {
        setRecordId(212);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "CockpitShow", "28")
    {
      public void created()
      {
        setRecordId(213);
      }

      public void begin()
      {
        if (World.cur().diffCur.Cockpit_Always_On)
          return;
        if (Main3D.cur3D().isViewOutside())
          return;
        if (!(Main3D.cur3D().cockpitCur instanceof CockpitPilot))
          return;
        if (Main3D.cur3D().isViewInsideShow())
        {
          Main3D.cur3D().hud.bDrawDashBoard = true;
          Main3D.cur3D().setViewInsideShow(false);
          Main3D.cur3D().cockpitCur.setEnableRenderingBall(true);
        }
        else if ((Main3D.cur3D().hud.bDrawDashBoard) && (Main3D.cur3D().cockpitCur.isEnableRenderingBall())) {
          Main3D.cur3D().cockpitCur.setEnableRenderingBall(false);
        }
        else if ((Main3D.cur3D().hud.bDrawDashBoard) && (!Main3D.cur3D().cockpitCur.isEnableRenderingBall()))
        {
          Main3D.cur3D().hud.bDrawDashBoard = false;
          Main3D.cur3D().cockpitCur.setEnableRenderingBall(true);
        }
        else if ((Main3D.cur3D().isEnableRenderingCockpit()) && (Main3D.cur3D().cockpitCur.isEnableRenderingBall())) {
          Main3D.cur3D().cockpitCur.setEnableRenderingBall(false);
        }
        else if ((Main3D.cur3D().isEnableRenderingCockpit()) && (!Main3D.cur3D().cockpitCur.isEnableRenderingBall()))
        {
          Main3D.cur3D().setEnableRenderingCockpit(false);
        }
        else {
          Main3D.cur3D().setEnableRenderingCockpit(true);
          Main3D.cur3D().setViewInsideShow(true);
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "OutsideView", "29")
    {
      public void created()
      {
        setRecordId(205);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if (AircraftHotKeys.this.viewAllowed(AircraftHotKeys.this.bExtViewSelf))
        {
          Object localObject = World.getPlayerAircraft();
          Selector.setCurRecordArg0((Actor)localObject);
          if (!Actor.isValid((Actor)localObject))
            localObject = AircraftHotKeys.this.getViewActor();
          if (Actor.isValid((Actor)localObject))
          {
            int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
            Main3D.cur3D().setViewFlow10((Actor)localObject, false);
            if (i != 0)
              AircraftHotKeys.this.switchToAIGunner();
          }
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "NextView", "30")
    {
      public void created()
      {
        setRecordId(206);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if (AircraftHotKeys.this.viewAllowed(AircraftHotKeys.this.bExtViewFriendly))
        {
          Actor localActor = AircraftHotKeys.this.nextViewActor(false);
          if (Actor.isValid(localActor))
          {
            int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
            Main3D.cur3D().setViewFlow10(localActor, false);
            if (i != 0)
              AircraftHotKeys.this.switchToAIGunner();
          }
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "NextViewEnemy", "31")
    {
      public void created()
      {
        setRecordId(207);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if (AircraftHotKeys.this.viewAllowed(AircraftHotKeys.this.bExtViewEnemy))
        {
          Actor localActor = AircraftHotKeys.this.nextViewActor(true);
          if (Actor.isValid(localActor))
          {
            int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
            Main3D.cur3D().setViewFlow10(localActor, false);
            if (i != 0)
              AircraftHotKeys.this.switchToAIGunner();
          }
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "OutsideViewFly", "32")
    {
      public void created()
      {
        setRecordId(200);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if (AircraftHotKeys.this.viewAllowed(AircraftHotKeys.this.bExtViewSelf))
        {
          Actor localActor = AircraftHotKeys.this.getViewActor();
          if ((Actor.isValid(localActor)) && (!(localActor instanceof ActorViewPoint)) && (!(localActor instanceof BigshipGeneric)))
          {
            int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
            Main3D.cur3D().setViewFly(localActor);
            if (i != 0)
              AircraftHotKeys.this.switchToAIGunner();
          }
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "PadlockView", "33")
    {
      public void created()
      {
        setRecordId(217);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Padlock) && (!AircraftHotKeys.this.bPadlockEnemy))
          return;
        Aircraft localAircraft = World.getPlayerAircraft();
        if ((!Actor.isValid(localAircraft)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
          return;
        if (Main3D.cur3D().isViewPadlock())
        {
          Main3D.cur3D().setViewEndPadlock();
          Selector.setCurRecordArg1(localAircraft);
        }
        else {
          if ((AircraftHotKeys.bFirstHotCmd) && (Actor.isValid(World.getPlayerAircraft())) && (!World.isPlayerParatrooper()))
            Main3D.cur3D().setViewInside();
          Main3D.cur3D().setViewPadlock(false, false);
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "PadlockViewFriend", "34")
    {
      public void created()
      {
        setRecordId(218);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Padlock) && (!AircraftHotKeys.this.bPadlockFriendly))
          return;
        Aircraft localAircraft = World.getPlayerAircraft();
        if ((!Actor.isValid(localAircraft)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
          return;
        if (Main3D.cur3D().isViewPadlock())
        {
          Main3D.cur3D().setViewEndPadlock();
          Selector.setCurRecordArg1(localAircraft);
        }
        else {
          if ((AircraftHotKeys.bFirstHotCmd) && (Actor.isValid(World.getPlayerAircraft())) && (!World.isPlayerParatrooper()))
            Main3D.cur3D().setViewInside();
          Main3D.cur3D().setViewPadlock(true, false);
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "PadlockViewGround", "35")
    {
      public void created()
      {
        setRecordId(221);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Padlock) && (!AircraftHotKeys.this.bPadlockEnemy))
          return;
        Aircraft localAircraft = World.getPlayerAircraft();
        if ((!Actor.isValid(localAircraft)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
          return;
        if (Main3D.cur3D().isViewPadlock())
        {
          Main3D.cur3D().setViewEndPadlock();
          Selector.setCurRecordArg1(localAircraft);
        }
        else {
          if ((AircraftHotKeys.bFirstHotCmd) && (Actor.isValid(World.getPlayerAircraft())) && (!World.isPlayerParatrooper()))
            Main3D.cur3D().setViewInside();
          Main3D.cur3D().setViewPadlock(false, true);
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "PadlockViewFriendGround", "36")
    {
      public void created()
      {
        setRecordId(222);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Padlock) && (!AircraftHotKeys.this.bPadlockFriendly))
          return;
        Aircraft localAircraft = World.getPlayerAircraft();
        if ((!Actor.isValid(localAircraft)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
          return;
        if (Main3D.cur3D().isViewPadlock())
        {
          Main3D.cur3D().setViewEndPadlock();
          Selector.setCurRecordArg1(localAircraft);
        }
        else {
          if ((AircraftHotKeys.bFirstHotCmd) && (Actor.isValid(World.getPlayerAircraft())) && (!World.isPlayerParatrooper()))
            Main3D.cur3D().setViewInside();
          Main3D.cur3D().setViewPadlock(true, true);
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "PadlockViewNext", "37")
    {
      public void created()
      {
        setRecordId(223);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Padlock) && (!AircraftHotKeys.this.bPadlockFriendly) && (!AircraftHotKeys.this.bExtPadlockFriendly))
          return;
        Aircraft localAircraft = World.getPlayerAircraft();
        if ((!Actor.isValid(localAircraft)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
          return;
        if (AircraftHotKeys.bFirstHotCmd)
        {
          Main3D.cur3D().setViewInside();
          if ((Actor.isValid(Main3D.cur3D().cockpitCur)) && (Main3D.cur3D().cockpitCur.existPadlock()))
            Main3D.cur3D().cockpitCur.startPadlock(Selector._getTrackArg1());
        }
        else if (Main3D.cur3D().isViewPadlock()) {
          Main3D.cur3D().setViewNextPadlock(true);
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "PadlockViewPrev", "38")
    {
      public void created()
      {
        setRecordId(224);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Padlock) && (!AircraftHotKeys.this.bPadlockFriendly) && (!AircraftHotKeys.this.bExtPadlockFriendly))
          return;
        Aircraft localAircraft = World.getPlayerAircraft();
        if ((!Actor.isValid(localAircraft)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
          return;
        if (AircraftHotKeys.bFirstHotCmd)
        {
          Main3D.cur3D().setViewInside();
          if ((Actor.isValid(Main3D.cur3D().cockpitCur)) && (Main3D.cur3D().cockpitCur.existPadlock()))
            Main3D.cur3D().cockpitCur.startPadlock(Selector._getTrackArg1());
        }
        else if (Main3D.cur3D().isViewPadlock()) {
          Main3D.cur3D().setViewNextPadlock(false);
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "PadlockViewForward", "39")
    {
      public void created()
      {
        setRecordId(220);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Padlock) && (!AircraftHotKeys.this.bPadlockFriendly) && (!AircraftHotKeys.this.bExtPadlockFriendly))
          return;
        Aircraft localAircraft = World.getPlayerAircraft();
        if ((!Actor.isValid(localAircraft)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
        {
          return;
        }

        Main3D.cur3D().setViewPadlockForward(true);
      }

      public void end()
      {
        if (World.cur().diffCur.No_Padlock)
          return;
        Aircraft localAircraft = World.getPlayerAircraft();
        if ((!Actor.isValid(localAircraft)) || (World.isPlayerDead()) || (World.isPlayerParatrooper()))
        {
          return;
        }

        Main3D.cur3D().setViewPadlockForward(false);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ViewEnemyAir", "40")
    {
      public void created()
      {
        setRecordId(203);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Outside_Views) && (!AircraftHotKeys.this.bExtPadlockEnemy))
          return;
        Actor localActor = AircraftHotKeys.this.getViewActor();
        if ((Actor.isValid(localActor)) && (!(localActor instanceof BigshipGeneric)))
        {
          int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
          Main3D.cur3D().setViewEnemy(localActor, false, false);
          if (i != 0)
            AircraftHotKeys.this.switchToAIGunner();
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ViewFriendAir", "41")
    {
      public void created()
      {
        setRecordId(198);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Outside_Views) && (!AircraftHotKeys.this.bExtPadlockFriendly))
          return;
        Actor localActor = AircraftHotKeys.this.getViewActor();
        if ((Actor.isValid(localActor)) && (!(localActor instanceof BigshipGeneric)))
        {
          int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
          Main3D.cur3D().setViewFriend(localActor, false, false);
          if (i != 0)
            AircraftHotKeys.this.switchToAIGunner();
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ViewEnemyDirectAir", "42")
    {
      public void created()
      {
        setRecordId(201);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Outside_Views) && (!AircraftHotKeys.this.bExtPadlockEnemy))
          return;
        Actor localActor = AircraftHotKeys.this.getViewActor();
        if ((Actor.isValid(localActor)) && (!(localActor instanceof BigshipGeneric)))
        {
          int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
          Main3D.cur3D().setViewEnemy(localActor, true, false);
          if (i != 0)
            AircraftHotKeys.this.switchToAIGunner();
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ViewEnemyGround", "43")
    {
      public void created()
      {
        setRecordId(204);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Outside_Views) && (!AircraftHotKeys.this.bExtPadlockEnemy))
          return;
        Actor localActor = AircraftHotKeys.this.getViewActor();
        if ((Actor.isValid(localActor)) && (!(localActor instanceof BigshipGeneric)))
        {
          int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
          Main3D.cur3D().setViewEnemy(localActor, false, true);
          if (i != 0)
            AircraftHotKeys.this.switchToAIGunner();
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ViewFriendGround", "44")
    {
      public void created()
      {
        setRecordId(199);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Outside_Views) && (!AircraftHotKeys.this.bExtPadlockFriendly))
          return;
        Actor localActor = AircraftHotKeys.this.getViewActor();
        if ((Actor.isValid(localActor)) && (!(localActor instanceof BigshipGeneric)))
        {
          int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
          Main3D.cur3D().setViewFriend(localActor, false, true);
          if (i != 0)
            AircraftHotKeys.this.switchToAIGunner();
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "ViewEnemyDirectGround", "45")
    {
      public void created()
      {
        setRecordId(202);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if ((World.cur().diffCur.No_Outside_Views) && (!AircraftHotKeys.this.bExtPadlockEnemy))
          return;
        Actor localActor = AircraftHotKeys.this.getViewActor();
        if ((Actor.isValid(localActor)) && (!(localActor instanceof BigshipGeneric)))
        {
          int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
          Main3D.cur3D().setViewEnemy(localActor, true, true);
          if (i != 0)
            AircraftHotKeys.this.switchToAIGunner();
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "OutsideViewFollow", "46")
    {
      public void created()
      {
        setRecordId(208);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if (AircraftHotKeys.this.viewAllowed(AircraftHotKeys.this.bExtViewSelf))
        {
          Object localObject = World.getPlayerAircraft();
          Selector.setCurRecordArg0((Actor)localObject);
          if (!Actor.isValid((Actor)localObject))
            localObject = AircraftHotKeys.this.getViewActor();
          if (Actor.isValid((Actor)localObject))
          {
            int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
            Main3D.cur3D().setViewFlow10((Actor)localObject, true);
            if (i != 0)
              AircraftHotKeys.this.switchToAIGunner();
          }
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "NextViewFollow", "47")
    {
      public void created()
      {
        setRecordId(209);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if (AircraftHotKeys.this.viewAllowed(AircraftHotKeys.this.bExtViewFriendly))
        {
          Actor localActor = AircraftHotKeys.this.nextViewActor(false);
          if (Actor.isValid(localActor))
          {
            int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
            Main3D.cur3D().setViewFlow10(localActor, true);
            if (i != 0)
              AircraftHotKeys.this.switchToAIGunner();
          }
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "NextViewEnemyFollow", "48")
    {
      public void created()
      {
        setRecordId(210);
      }

      public void begin()
      {
        if (!AircraftHotKeys.this.bMissionModsSet)
          AircraftHotKeys.this.setMissionMods();
        if (AircraftHotKeys.this.viewAllowed(AircraftHotKeys.this.bExtViewEnemy))
        {
          Actor localActor = AircraftHotKeys.this.nextViewActor(true);
          if (Actor.isValid(localActor))
          {
            int i = !Main3D.cur3D().isViewOutside() ? 1 : 0;
            Main3D.cur3D().setViewFlow10(localActor, true);
            if (i != 0)
              AircraftHotKeys.this.switchToAIGunner();
          }
        }
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitAim", "49")
    {
      public void created()
      {
        setRecordId(276);
      }

      public void begin()
      {
        if (Main3D.cur3D().isViewOutside())
          return;
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        if (!Actor.isValid(Main3D.cur3D().cockpitCur))
          return;
        if (Main3D.cur3D().cockpitCur.isToggleUp())
        {
          return;
        }

        Main3D.cur3D().cockpitCur.doToggleAim(!Main3D.cur3D().cockpitCur.isToggleAim());
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "cockpitUp", "50")
    {
      public void created()
      {
        setRecordId(281);
      }

      public void begin()
      {
        if (Main3D.cur3D().isViewOutside())
          return;
        if (!AircraftHotKeys.this.isMiscValid())
          return;
        if (!Actor.isValid(Main3D.cur3D().cockpitCur))
          return;
        if (Main3D.cur3D().cockpitCur.isToggleAim())
          return;
        if (!World.getPlayerFM().CT.bHasCockpitDoorControl)
          return;
        if ((!Main3D.cur3D().cockpitCur.isToggleUp()) && ((World.getPlayerFM().CT.cockpitDoorControl < 0.5F) || (World.getPlayerFM().CT.getCockpitDoor() < 0.99F)))
        {
          return;
        }

        Main3D.cur3D().cockpitCur.doToggleUp(!Main3D.cur3D().cockpitCur.isToggleUp());
      }
    });
  }

  public void createTimeHotKeys()
  {
    String str = "timeCompression";
    HotKeyCmdEnv.setCurrentEnv(str);
    HotKeyEnv.fromIni(str, Config.cur.ini, "HotKey " + str);
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "timeSpeedUp", "0")
    {
      public void begin()
      {
        if (TimeSkip.isDo())
          return;
        if (Time.isEnableChangeSpeed())
        {
          float f = Time.nextSpeed() * 2.0F;
          if (f <= 8.0F)
          {
            Time.setSpeed(f);
            AircraftHotKeys.this.showTimeSpeed(f);
          }
        }
      }

      public void created()
      {
        setRecordId(25);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "timeSpeedNormal", "1")
    {
      public void begin()
      {
        if (TimeSkip.isDo())
          return;
        if (Time.isEnableChangeSpeed())
        {
          Time.setSpeed(1.0F);
          AircraftHotKeys.this.showTimeSpeed(1.0F);
        }
      }

      public void created()
      {
        setRecordId(24);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "timeSpeedDown", "2")
    {
      public void begin()
      {
        if (TimeSkip.isDo())
          return;
        if (Time.isEnableChangeSpeed())
        {
          float f = Time.nextSpeed() / 2.0F;
          if (f >= 0.25F)
          {
            Time.setSpeed(f);
            AircraftHotKeys.this.showTimeSpeed(f);
          }
        }
      }

      public void created()
      {
        setRecordId(26);
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "timeSpeedPause", "3")
    {
      public void begin()
      {
      }
    });
    HotKeyCmdEnv.addCmd(new HotKeyCmd(true, "timeSkip", "4")
    {
      public void begin()
      {
        if (TimeSkip.isDo())
          Main3D.cur3D().timeSkip.stop();
        else
          Main3D.cur3D().timeSkip.start();
      }
    });
  }

  private void showTimeSpeed(float paramFloat)
  {
    int i = Math.round(paramFloat * 4.0F);
    switch (i)
    {
    case 4:
      Main3D.cur3D().hud._log(0, "TimeSpeedNormal");
      break;
    case 8:
      Main3D.cur3D().hud._log(0, "TimeSpeedUp2");
      break;
    case 16:
      Main3D.cur3D().hud._log(0, "TimeSpeedUp4");
      break;
    case 32:
      Main3D.cur3D().hud._log(0, "TimeSpeedUp8");
      break;
    case 2:
      Main3D.cur3D().hud._log(0, "TimeSpeedDown2");
      break;
    case 1:
      Main3D.cur3D().hud._log(0, "TimeSpeedDown4");
    }
  }

  public AircraftHotKeys()
  {
    this.bMissionModsSet = false;
    this.bSpeedbarTAS = false;
    this.bSeparateGearUpDown = false;
    this.bSeparateHookUpDown = false;
    this.bSeparateRadiatorOpenClose = false;
    this.bToggleMusic = false;
    this.bViewExternalSelf = false;
    this.bViewExternalOnGround = false;
    this.bViewExternalWhenDead = false;
    this.bViewExternalFriendlies = false;
    this.bBombBayDoors = true;
    this.bMusicOn = true;
    this.iAirShowSmoke = 0;
    this.bAirShowSmokeEnhanced = false;
    this.bSideDoor = true;
    this.COCKPIT_DOOR = 1;
    this.SIDE_DOOR = 2;
    this.bAllowDumpFuel = false;
    this.bDumpFuel = false;
    this.bExtViewEnemy = false;
    this.bExtViewFriendly = false;
    this.bExtViewSelf = false;
    this.bExtViewGround = false;
    this.bExtViewDead = false;
    this.iExtViewGround = -1;
    this.iExtViewDead = -1;
    this.bPadlockEnemy = false;
    this.bPadlockFriendly = false;
    this.bExtPadlockEnemy = false;
    this.bExtPadlockFriendly = false;
    this.bMustangCompressorAuto = true;
    this.bPropAuto = true;
    this.bAfterburner = false;
    this.lastPower = -0.5F;
    this.lastProp = 1.5F;
    this.cptdmg = 1;
    this.bAutoAutopilot = false;
    this.switchToCockpitRequest = -1;
    this.cmdFov = new HotKeyCmd[16];
    createPilotHotKeys();
    createPilotHotMoves();
    createGunnerHotKeys();
    createMiscHotKeys();
    create_MiscHotKeys();
    createViewHotKeys();
    createTimeHotKeys();
    if (Config.cur.ini.get("Mods", "SpeedbarTAS", 0) > 0)
      this.bSpeedbarTAS = true;
    if (Config.cur.ini.get("Mods", "SeparateGearUpDown", 0) > 0)
      this.bSeparateGearUpDown = true;
    if (Config.cur.ini.get("Mods", "SeparateHookUpDown", 0) > 0)
      this.bSeparateHookUpDown = true;
    if (Config.cur.ini.get("Mods", "SeparateRadiatorOpenClose", 0) > 0)
      this.bSeparateRadiatorOpenClose = true;
    if (Config.cur.ini.get("Mods", "ToggleMusic", 0) > 0)
      this.bToggleMusic = true;
    if (Config.cur.ini.get("Mods", "BombBayDoors", 1) == 0)
      this.bBombBayDoors = false;
    if (Config.cur.ini.get("Mods", "SideDoor", 1) == 0)
      this.bSideDoor = false;
    this.iAirShowSmoke = Config.cur.ini.get("Mods", "AirShowSmoke", 0);
    if ((this.iAirShowSmoke < 1) || (this.iAirShowSmoke > 3))
      this.iAirShowSmoke = 0;
    if (Config.cur.ini.get("Mods", "AirShowSmokeEnhanced", 0) > 0)
      this.bAirShowSmokeEnhanced = true;
    if (Config.cur.ini.get("Mods", "DumpFuel", 0) > 0)
      this.bAllowDumpFuel = true;
  }

  private Actor getViewActor()
  {
    if (Selector.isEnableTrackArgs())
      return Selector.setCurRecordArg0(Selector.getTrackArg0());
    Actor localActor = Main3D.cur3D().viewActor();
    if (isViewed(localActor)) {
      return Selector.setCurRecordArg0(localActor);
    }
    return Selector.setCurRecordArg0(World.getPlayerAircraft());
  }

  private Actor nextViewActor(boolean paramBoolean)
  {
    if (Selector.isEnableTrackArgs())
      return Selector.setCurRecordArg0(Selector.getTrackArg0());
    int i = World.getPlayerArmy();
    namedAircraft.clear();
    Actor localActor1 = Main3D.cur3D().viewActor();
    if (isViewed(localActor1))
      namedAircraft.put(localActor1.name(), null);
    for (Map.Entry localEntry = Engine.name2Actor().nextEntry(null); localEntry != null; localEntry = Engine.name2Actor().nextEntry(localEntry))
    {
      Actor localActor2 = (Actor)localEntry.getValue();
      if ((isViewed(localActor2)) && (localActor2 != localActor1)) {
        if (paramBoolean)
        {
          if (localActor2.getArmy() != i)
            namedAircraft.put(localActor2.name(), null);
        }
        else if (localActor2.getArmy() == i)
          namedAircraft.put(localActor2.name(), null);
      }
    }
    if (namedAircraft.size() == 0)
      return Selector.setCurRecordArg0(null);
    if (!isViewed(localActor1))
      return Selector.setCurRecordArg0((Actor)Engine.name2Actor().get((String)namedAircraft.firstKey()));
    if ((namedAircraft.size() == 1) && (isViewed(localActor1)))
      return Selector.setCurRecordArg0(null);
    namedAll = namedAircraft.keySet().toArray(namedAll);
    int j = 0;
    String str = localActor1.name();
    for (; namedAll[j] != null; j++) {
      if (str.equals(namedAll[j]))
        break;
    }
    if (namedAll[j] == null)
      return Selector.setCurRecordArg0(null);
    j++;
    if ((namedAll.length == j) || (namedAll[j] == null))
      j = 0;
    return Selector.setCurRecordArg0((Actor)Engine.name2Actor().get((String)namedAll[j]));
  }

  private boolean isViewed(Actor paramActor)
  {
    if (!Actor.isValid(paramActor)) {
      return false;
    }
    return ((paramActor instanceof Aircraft)) || ((paramActor instanceof Paratrooper)) || ((paramActor instanceof ActorViewPoint)) || (((paramActor instanceof BigshipGeneric)) && (((BigshipGeneric)paramActor).getAirport() != null));
  }

  class HotKeyCmdFire extends HotKeyCmd
  {
    int cmd;
    long time;

    public void begin()
    {
      AircraftHotKeys.this.doCmdPilot(this.cmd, true);
      this.time = Time.tick();
    }

    public void tick()
    {
      if (Time.tick() > this.time + 500L)
        AircraftHotKeys.this.doCmdPilotTick(this.cmd);
    }

    public boolean isTickInTime(boolean paramBoolean)
    {
      return !paramBoolean;
    }

    public void end()
    {
      AircraftHotKeys.this.doCmdPilot(this.cmd, false);
    }

    public boolean isDisableIfTimePaused()
    {
      return true;
    }

    public HotKeyCmdFire(String paramString1, String paramInt1, int paramInt2, int arg5)
    {
      super(paramInt1, paramString1);
      this.cmd = paramInt2;
      int i;
      setRecordId(i);
    }
  }

  class HotKeyCmdFireMove extends HotKeyCmdMove
  {
    int cmd;

    public void begin()
    {
      int i = name().charAt(0) != '-' ? 1 : -1;
      AircraftHotKeys.this.doCmdPilotMove(this.cmd, Joy.normal(i * move()));
    }

    public boolean isDisableIfTimePaused()
    {
      return true;
    }

    public HotKeyCmdFireMove(String paramString1, String paramInt1, int paramInt2, int arg5)
    {
      super(paramInt1, paramString1);
      this.cmd = paramInt2;
      int i;
      setRecordId(i);
    }
  }
}