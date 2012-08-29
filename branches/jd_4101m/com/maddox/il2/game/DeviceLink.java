package com.maddox.il2.game;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Autopilotage;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.net.NetMissionTrack;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeBomber;
import com.maddox.il2.objects.air.TypeDockable;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdMouseMove;
import com.maddox.rts.HotKeyCmdMove;
import com.maddox.rts.HotKeyCmdTrackIRAngles;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.MsgAction;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.rts.RTSConf;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class DeviceLink
{
  private static int PACKET_SIZE = 2048;
  private static final int VERSION = 1;
  private static final int GET_ACCESSIBLE = 2;
  private static final int SET_ACCESSIBLE = 3;
  private static final int TRACKIR = 5;
  private static final int TIME_OF_DAY = 10;
  private static final int PLANE = 11;
  private static final int COCKPITS = 12;
  private static final int COCKPIT_CUR = 13;
  private static final int ENGINES = 14;
  private static final int SPEEDOMETER = 15;
  private static final int VARIOMETER = 16;
  private static final int SLIP = 17;
  private static final int TURN = 18;
  private static final int ANGULAR_SPEED = 19;
  private static final int ALTIMETER = 20;
  private static final int AZIMUT = 21;
  private static final int BEACON_AZIMUT = 22;
  private static final int ROLL = 23;
  private static final int PITCH = 24;
  private static final int FUEL = 25;
  private static final int OVERLOAD = 26;
  private static final int SHAKE_LEVEL = 27;
  private static final int GEAR_POS_L = 28;
  private static final int GEAR_POS_R = 29;
  private static final int GEAR_POS_C = 30;
  private static final int MAGNETO = 31;
  private static final int RPM = 32;
  private static final int MANIFOLD = 33;
  private static final int TEMP_OILIN = 34;
  private static final int TEMP_OILOUT = 35;
  private static final int TEMP_WATER = 36;
  private static final int TEMP_CYLINDERS = 37;
  private static final int M_POWER = 40;
  private static final int M_FLAPS = 41;
  private static final int M_AILERON = 42;
  private static final int M_ELEVATOR = 43;
  private static final int M_RUDDER = 44;
  private static final int M_BRAKES = 45;
  private static final int M_PITCH = 46;
  private static final int M_TRIMAILERON = 47;
  private static final int M_TRIMELEVATOR = 48;
  private static final int M_TRIMRUDDER = 49;
  private static final int STABILIZER = 50;
  private static final int TOGGLE_ENGINE = 51;
  private static final int BOOST = 52;
  private static final int MAGNETO_PLUS = 53;
  private static final int MAGNETO_MINUS = 54;
  private static final int COMPRESSOR_PLUS = 55;
  private static final int COMPRESSOR_MINUS = 56;
  private static final int ENGINE_SELECT_ALL = 57;
  private static final int ENGINE_SELECT_NONE = 58;
  private static final int ENGINE_SELECT_LEFT = 59;
  private static final int ENGINE_SELECT_RIGHT = 60;
  private static final int ENGINE_SELECT_1 = 61;
  private static final int ENGINE_SELECT_2 = 62;
  private static final int ENGINE_SELECT_3 = 63;
  private static final int ENGINE_SELECT_4 = 64;
  private static final int ENGINE_SELECT_5 = 65;
  private static final int ENGINE_SELECT_6 = 66;
  private static final int ENGINE_SELECT_7 = 67;
  private static final int ENGINE_SELECT_8 = 68;
  private static final int ENGINE_TOGGLE_ALL = 69;
  private static final int ENGINE_TOGGLE_LEFT = 70;
  private static final int ENGINE_TOGGLE_RIGHT = 71;
  private static final int ENGINE_TOGGLE_1 = 72;
  private static final int ENGINE_TOGGLE_2 = 73;
  private static final int ENGINE_TOGGLE_3 = 74;
  private static final int ENGINE_TOGGLE_4 = 75;
  private static final int ENGINE_TOGGLE_5 = 76;
  private static final int ENGINE_TOGGLE_6 = 77;
  private static final int ENGINE_TOGGLE_7 = 78;
  private static final int ENGINE_TOGGLE_8 = 79;
  private static final int ENGINE_EXTINGUISHER = 80;
  private static final int ENGINE_FEATHER = 81;
  private static final int GEAR = 82;
  private static final int GEAR_UP_MANUAL = 83;
  private static final int GEAR_DOWN_MANUAL = 84;
  private static final int RADIATOR = 85;
  private static final int TOGGLE_AIRBRAKE = 86;
  private static final int TAILWHEELLOCK = 87;
  private static final int DROP_TANKS = 88;
  private static final int DOCK_UNDOCK = 89;
  private static final int WEAPON0 = 90;
  private static final int WEAPON1 = 91;
  private static final int WEAPON2 = 92;
  private static final int WEAPON3 = 93;
  private static final int WEAPON01 = 94;
  private static final int GUNPODS = 95;
  private static final int SIGHT_AUTO_ONOFF = 96;
  private static final int SIGHT_DIST_PLUS = 97;
  private static final int SIGHT_DIST_MINUS = 98;
  private static final int SIGHT_SIDE_RIGHT = 99;
  private static final int SIGHT_SIDE_LEFT = 100;
  private static final int SIGHT_ALT_PLUS = 101;
  private static final int SIGHT_ALT_MINUS = 102;
  private static final int SIGHT_SPD_PLUS = 103;
  private static final int SIGHT_SPD_MINUS = 104;
  private static final int WINGFOLD = 105;
  private static final int COCKPITDOOR = 106;
  private static final int CARRIERHOOK = 107;
  private static final int BRAKESHOE = 108;
  private static final int GUNNER_FIRE = 110;
  private static final int GUNNER_MOVE = 111;
  private static final int VchangeCockpit = 150;
  private static final int VcockpitView0 = 151;
  private static final int VcockpitView1 = 152;
  private static final int VcockpitView2 = 153;
  private static final int VcockpitView3 = 154;
  private static final int VcockpitView4 = 155;
  private static final int VcockpitView5 = 156;
  private static final int VcockpitView6 = 157;
  private static final int VcockpitView7 = 158;
  private static final int VcockpitView8 = 159;
  private static final int VcockpitView9 = 160;
  private static final int Vfov90 = 161;
  private static final int Vfov85 = 162;
  private static final int Vfov80 = 163;
  private static final int Vfov75 = 164;
  private static final int Vfov70 = 165;
  private static final int Vfov65 = 166;
  private static final int Vfov60 = 167;
  private static final int Vfov55 = 168;
  private static final int Vfov50 = 169;
  private static final int Vfov45 = 170;
  private static final int Vfov40 = 171;
  private static final int Vfov35 = 172;
  private static final int Vfov30 = 173;
  private static final int VfovSwitch = 174;
  private static final int VfovInc = 175;
  private static final int VfovDec = 176;
  private static final int VCockpitView = 177;
  private static final int VCockpitShow = 178;
  private static final int VOutsideView = 179;
  private static final int VNextView = 180;
  private static final int VNextViewEnemy = 181;
  private static final int VOutsideViewFly = 182;
  private static final int VPadlockView = 183;
  private static final int VPadlockViewFriend = 184;
  private static final int VPadlockViewGround = 185;
  private static final int VPadlockViewFriendGround = 186;
  private static final int VPadlockViewNext = 187;
  private static final int VPadlockViewPrev = 188;
  private static final int VPadlockViewForward = 189;
  private static final int VViewEnemyAir = 190;
  private static final int VViewFriendAir = 191;
  private static final int VViewEnemyDirectAir = 192;
  private static final int VViewEnemyGround = 193;
  private static final int VViewFriendGround = 194;
  private static final int VViewEnemyDirectGround = 195;
  private static final int VOutsideViewFollow = 196;
  private static final int VNextViewFollow = 197;
  private static final int VNextViewEnemyFollow = 198;
  private static final int VcockpitAim = 199;
  private static final int AUTOPILOT = 200;
  private static final int AUTOPILOT_AUTO = 201;
  private static final int EJECTPILOT = 202;
  private static final int COCKPIT_DIM = 203;
  private static final int COCKPIT_LIGHT = 204;
  private static final int TORGLE_NAV_LIGHTS = 205;
  private static final int TORGLE_LANDING_LIGHTS = 206;
  private static final int TORGLE_SMOKES = 207;
  private static final int PAD = 208;
  private static final int CHAT = 209;
  private static final int ONLINE_RATING = 210;
  private static final int SHOW_POSITION_HINT = 211;
  private static final int ICON_TYPES = 212;
  private static final int SHOW_MIRROR = 213;
  private static final int QUICK_SAVE_NET_TRACK = 214;
  private static final int RADIO_MUTE_KEY = 215;
  private static final int RADIO_CHANNEL_SWITCH = 216;
  private static final int TIME_SPEED_UP = 217;
  private static final int TIME_SPEED_NORMAL = 218;
  private static final int TIME_SPEED_DOWN = 219;
  private static final int TIME_SPEED_PAUSE = 220;
  private static final int ONLINE_RATING_PAGE = 221;
  private static final int SOUND_MUTE_KEY = 222;
  private static final int COCKPIT_UP = 223;
  private static final int TIME_SKIP = 224;
  private static final int POSITION = 300;
  private static final int ORIENTATION = 301;
  private static final int SPEED = 302;
  private static final int ACCEL = 303;
  private static final int COWL_FLAPS_POS = 310;
  private static final int FLAPS_POS = 311;
  private static final int BRAKES_POS = 312;
  private static final int MACHINE_GUNS = 320;
  private static final int FUEL_TANKS = 321;
  private static final int ENGINE_STATUS_FIRE = 322;
  private static final int ENGINE_STATUS_TEMPERATURE = 323;
  private static final int ENGINE_STATUS_IGNITION = 324;
  private static final int ENGINE_STATUS_SUPERCHARGER = 325;
  private static final int ENGINE_STATUS_FUEL = 326;
  private static final int CONTROLS_STATUS = 327;
  private static final int COWL_FLAPS = 330;
  private static final int CANOPY = 331;
  private static final int ENGINE_MIX = 332;
  private static final int MAGNETO_SET = 333;
  private HashMapInt paramMap = new HashMapInt();
  private DatagramSocket serverSocket;
  private ArrayList enableClientAdr;
  private Listener listener;
  private List inputPackets;
  private DatagramPacket outPacket = new DatagramPacket(new byte[PACKET_SIZE], PACKET_SIZE);
  private ActionReceivedPacket inputAction;
  private NetMsgInput inputMsg = new NetMsgInput();
  private ArrayList inArg = new ArrayList();
  private StringBuffer inOutBuf = new StringBuffer();
  private LinkedList outList = new LinkedList();
  private static DeviceLink deviceLink;

  private void registerParams()
  {
    new Parameter(1) {
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("1.00");
      }
    };
    new Parameter(2) {
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = 0;
        DeviceLink.Parameter localParameter = null;
        try {
          i = Integer.parseInt((String)paramList.get(0)) & 0xFFFFFFFE;
          localParameter = (DeviceLink.Parameter)DeviceLink.this.paramMap.get(i);
        } catch (Exception localException) {
          return;
        }
        if (localParameter != null)
          answer(new String[] { "" + i, localParameter.get_accessible() ? "1" : "0" });
      }
    };
    new Parameter(3) {
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = 0;
        DeviceLink.Parameter localParameter = null;
        try {
          i = Integer.parseInt((String)paramList.get(0)) & 0xFFFFFFFE;
          localParameter = (DeviceLink.Parameter)DeviceLink.this.paramMap.get(i);
        } catch (Exception localException) {
          return;
        }
        if (localParameter != null)
          answer(new String[] { "" + (i + 1), localParameter.set_accessible() ? "1" : "0" });
      }
    };
    new Parameter(10) {
      public boolean get_accessible() { return isMissinValid(); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + World.getTimeofDay());
      }
    };
    new Parameter(11) {
      public boolean get_accessible() { return isAircraftValid(); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer(Property.stringValue(World.getPlayerAircraft().getClass(), "keyName", ""));
      }
    };
    new Parameter(12) {
      public boolean get_accessible() { return isAircraftValid(); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + Main3D.cur3D().cockpits.length);
      }
    };
    new Parameter(13) {
      public boolean get_accessible() { return isAircraftValid(); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = Main3D.cur3D().cockpitCurIndx();
        if (Main3D.cur3D().isViewOutside())
          i = -1;
        answer("" + i);
      }
    };
    new Parameter(14) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + World.getPlayerFM().EI.getNum());
      }
    };
    new Parameter(15) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + fmt(Pitot.Indicator((float)World.getPlayerFM().Loc.z, World.getPlayerFM().getSpeedKMH())));
      }
    };
    new Parameter(16) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + fmt((float)World.getPlayerFM().Vwld.z));
      }
    };
    new Parameter(17) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        float f = World.getPlayerFM().getSpeedKMH() > 10.0F ? World.getPlayerFM().getAOS() : 0.0F;
        if (f < -45.0F) f = -45.0F;
        if (f > 45.0F) f = 45.0F;
        answer("" + fmt(f));
      }
    };
    new Parameter(18) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        Vector3f localVector3f = new Vector3f();
        localVector3f.set(World.getPlayerFM().getW());
        World.getPlayerFM().Or.transform(localVector3f);
        float f = localVector3f.z / 6.0F;
        if (f < -1.0F) f = -1.0F;
        if (f > 1.0F) f = 1.0F;
        answer("" + fmt(f));
      }
    };
    new Parameter(19) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        Vector3f localVector3f = new Vector3f();
        localVector3f.set(World.getPlayerFM().getW());
        World.getPlayerFM().Or.transform(localVector3f);
        float f = localVector3f.z;
        answer("" + fmt(f));
      }
    };
    new Parameter(20) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + fmt((float)World.getPlayerFM().Loc.z));
      }
    };
    new Parameter(21) {
      Orient o = new Orient();

      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        Orientation localOrientation = World.getPlayerFM().Or;
        this.o.set(localOrientation.azimut(), localOrientation.tangage(), localOrientation.kren());
        this.o.wrap();
        float f = 90.0F - this.o.getYaw();
        while (f < 0.0F)
          f += 360.0F;
        while (f > 360.0F)
          f -= 360.0F;
        answer("" + fmt(f));
      }
    };
    new Parameter(22) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        Point3d localPoint3d = new Point3d();
        Vector3d localVector3d = new Vector3d();
        WayPoint localWayPoint = World.getPlayerFM().AP.way.curr();
        float f = 0.0F;
        if (localWayPoint != null) {
          localWayPoint.getP(localPoint3d);
          localVector3d.sub(localPoint3d, World.getPlayerFM().Loc);
          f = (float)(57.295779513082323D * Math.atan2(localVector3d.y, localVector3d.x));
        }
        answer("" + fmt(f));
      }
    };
    new Parameter(23) {
      Orient o = new Orient();

      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        Orientation localOrientation = World.getPlayerFM().Or;
        this.o.set(localOrientation.azimut(), localOrientation.tangage(), localOrientation.kren());
        this.o.wrap();
        answer("" + fmt(-this.o.getKren()));
      }
    };
    new Parameter(24) {
      Orient o = new Orient();

      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        Orientation localOrientation = World.getPlayerFM().Or;
        this.o.set(localOrientation.azimut(), localOrientation.tangage(), localOrientation.kren());
        this.o.wrap();
        answer("" + fmt(this.o.getTangage()));
      }
    };
    new Parameter(25) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().M.fuel));
      }
    };
    new Parameter(26) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().getOverload()));
      }
    };
    new Parameter(27) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        answer("" + fmt(((RealFlightModel)World.getPlayerFM()).shakeLevel));
      }
    };
    new Parameter(28) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        float f = 0.0F;
        if (World.getPlayerFM().Gears.lgear)
          f = World.getPlayerFM().CT.getGear();
        answer("" + fmt(f));
      }
    };
    new Parameter(29) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        float f = 0.0F;
        if (World.getPlayerFM().Gears.rgear)
          f = World.getPlayerFM().CT.getGear();
        answer("" + fmt(f));
      }
    };
    new Parameter(30) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        float f = World.getPlayerFM().CT.getGear();
        answer("" + fmt(f));
      }
    };
    new Parameter(31) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = engineNum(paramList);
        if (i < 0) return;
        answer(new String[] { "" + i, "" + World.getPlayerFM().EI.engines[i].getControlMagnetos() });
      }
    };
    new Parameter(32) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = engineNum(paramList);
        if (i < 0) return;
        answer(new String[] { "" + i, "" + fmt(World.getPlayerFM().EI.engines[i].getRPM()) });
      }
    };
    new Parameter(33) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = engineNum(paramList);
        if (i < 0) return;
        answer(new String[] { "" + i, "" + fmt(World.getPlayerFM().EI.engines[i].getManifoldPressure()) });
      }
    };
    new Parameter(34) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = engineNum(paramList);
        if (i < 0) return;
        answer(new String[] { "" + i, "" + fmt(World.getPlayerFM().EI.engines[i].tOilIn) });
      }
    };
    new Parameter(35) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = engineNum(paramList);
        if (i < 0) return;
        answer(new String[] { "" + i, "" + fmt(World.getPlayerFM().EI.engines[i].tOilOut) });
      }
    };
    new Parameter(36) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = engineNum(paramList);
        if (i < 0) return;
        answer(new String[] { "" + i, "" + fmt(World.getPlayerFM().EI.engines[i].tWaterOut) });
      }
    };
    new Parameter(37) {
      public boolean get_accessible() { return (isFMValid()) && (isNetAccessible()); } 
      public boolean set_accessible() { return false; } 
      public void get(List paramList) {
        int i = engineNum(paramList);
        if (i < 0) return;
        answer(new String[] { "" + i, "" + fmt(World.getPlayerFM().EI.engines[i].tWaterOut) });
      }
    };
    new ParameterPilot(50, "pilot", "Stabilizer") {
      public boolean set_accessible() { return (super.set_accessible()) && ((World.getPlayerAircraft() instanceof TypeBomber)); } 
      public void get(List paramList) {
        answer(World.getPlayerFM().CT.StabilizerControl ? "1" : "0");
      }
    };
    new ParameterPilot(51, "pilot", "AIRCRAFT_TOGGLE_ENGINE") {
      public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(52, "pilot", "Boost") {
      public boolean set_accessible() { return (super.set_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlAfterburner()); } 
      public void get(List paramList) {
        answer(Main3D.cur3D().aircraftHotKeys.isAfterburner() ? "1" : "0");
      }
    };
    new ParameterPilot(53, "pilot", "MagnetoPlus") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlMagnetos()) && (World.getPlayerFM().EI.getFirstSelected() != null) && (World.getPlayerFM().EI.getFirstSelected().getControlMagnetos() < 3);
      }

      public boolean get_accessible()
      {
        return (super.get_accessible()) && (World.getPlayerFM().EI.getFirstSelected() != null);
      }

      public void get(List paramList) {
        answer("" + World.getPlayerFM().EI.getFirstSelected().getControlMagnetos());
      }
    };
    new ParameterPilot(54, "pilot", "MagnetoMinus") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlMagnetos()) && (World.getPlayerFM().EI.getFirstSelected() != null) && (World.getPlayerFM().EI.getFirstSelected().getControlMagnetos() > 0);
      }

      public boolean get_accessible()
      {
        return (super.get_accessible()) && (World.getPlayerFM().EI.getFirstSelected() != null);
      }

      public void get(List paramList) {
        answer("" + World.getPlayerFM().EI.getFirstSelected().getControlMagnetos());
      }
    };
    new ParameterPilot(55, "pilot", "CompressorPlus") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlCompressor()) && (World.getPlayerFM().EI.getFirstSelected() != null) && (World.cur().diffCur.ComplexEManagement);
      }

      public void get(List paramList)
      {
        answer("" + World.getPlayerFM().CT.getCompressorControl());
      }
    };
    new ParameterPilot(56, "pilot", "CompressorMinus") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlCompressor()) && (World.getPlayerFM().EI.getFirstSelected() != null) && (World.cur().diffCur.ComplexEManagement);
      }

      public void get(List paramList)
      {
        answer("" + World.getPlayerFM().CT.getCompressorControl());
      }
    };
    new ParameterPilot(57, "pilot", "EngineSelectAll") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(58, "pilot", "EngineSelectNone") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(59, "pilot", "EngineSelectLeft") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(60, "pilot", "EngineSelectRight") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(61, "pilot", "EngineSelect1") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(62, "pilot", "EngineSelect2") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(63, "pilot", "EngineSelect3") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(64, "pilot", "EngineSelect4") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(65, "pilot", "EngineSelect5") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(66, "pilot", "EngineSelect6") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(67, "pilot", "EngineSelect7") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(68, "pilot", "EngineSelect8") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(69, "pilot", "EngineToggleAll") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(70, "pilot", "EngineToggleLeft") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(71, "pilot", "EngineToggleRight") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(72, "pilot", "EngineToggle1") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(73, "pilot", "EngineToggle2") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(74, "pilot", "EngineToggle3") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(75, "pilot", "EngineToggle4") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(76, "pilot", "EngineToggle5") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(77, "pilot", "EngineToggle6") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(78, "pilot", "EngineToggle7") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(79, "pilot", "EngineToggle8") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(80, "pilot", "EngineExtinguisher") {
      public boolean get_accessible() { return false; } 
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlExtinguisher());
      }
    };
    new ParameterPilot(81, "pilot", "EngineFeather") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlFeather()) && (World.getPlayerFM().EI.getFirstSelected() != null) && (World.cur().diffCur.ComplexEManagement);
      }

      public boolean get_accessible()
      {
        return (super.get_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlFeather()) && (World.getPlayerFM().EI.getFirstSelected() != null);
      }

      public void get(List paramList)
      {
        answer("" + World.getPlayerFM().EI.getFirstSelected().getControlFeather());
      }
    };
    new ParameterPilot(82, "pilot", "Gear") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasGearControl) && (!World.getPlayerFM().Gears.onGround()) && (World.getPlayerFM().Gears.isHydroOperable());
      }

      public void get(List paramList)
      {
        answer("" + fmt(World.getPlayerFM().CT.GearControl));
      }
    };
    new ParameterPilot(83, "pilot", "AIRCRAFT_GEAR_UP_MANUAL") {
      public boolean get_accessible() { return false; } 
      public boolean set_accessible() {
        return (super.set_accessible()) && (!World.getPlayerFM().Gears.onGround()) && (World.getPlayerFM().CT.GearControl > 0.0F) && (World.getPlayerFM().Gears.isOperable()) && (!World.getPlayerFM().Gears.isHydroOperable());
      }
    };
    new ParameterPilot(84, "pilot", "AIRCRAFT_GEAR_DOWN_MANUAL") {
      public boolean get_accessible() { return false; } 
      public boolean set_accessible() {
        return (super.set_accessible()) && (!World.getPlayerFM().Gears.onGround()) && (World.getPlayerFM().CT.GearControl < 1.0F) && (World.getPlayerFM().Gears.isOperable()) && (!World.getPlayerFM().Gears.isHydroOperable());
      }
    };
    new ParameterPilot(85, "pilot", "Radiator") {
      public boolean get_accessible() { return false; } 
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().EI.isSelectionHasControlRadiator());
      }
    };
    new ParameterPilot(86, "pilot", "AIRCRAFT_TOGGLE_AIRBRAKE") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasAirBrakeControl);
      }

      public void get(List paramList) {
        answer(World.getPlayerFM().CT.AirBrakeControl == 0.0F ? "0" : "1");
      }
    };
    new ParameterPilot(87, "pilot", "AIRCRAFT_TAILWHEELLOCK") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasLockGearControl);
      }

      public void get(List paramList) {
        answer(World.getPlayerFM().Gears.bTailwheelLocked ? "1" : "0");
      }
    };
    new ParameterPilot(88, "pilot", "AIRCRAFT_DROP_TANKS") {
      public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(89, "pilot", "AIRCRAFT_DOCK_UNDOCK") {
      public boolean get_accessible() { return false; } 
      public boolean set_accessible() {
        return (super.set_accessible()) && ((World.getPlayerFM().actor instanceof TypeDockable));
      }
    };
    new ParameterPilot(90, "pilot", "Weapon0") {
      public boolean set_accessible() { return set_accessible0(); } 
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
      public void get(List paramList) {
        answer(World.getPlayerFM().CT.WeaponControl[0] != 0 ? "1" : "0");
      }
    };
    new ParameterPilot(91, "pilot", "Weapon1") {
      public boolean set_accessible() { return set_accessible0(); } 
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
      public void get(List paramList) {
        answer(World.getPlayerFM().CT.WeaponControl[1] != 0 ? "1" : "0");
      }
    };
    new ParameterPilot(92, "pilot", "Weapon2") {
      public boolean set_accessible() { return set_accessible0(); } 
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
      public void get(List paramList) {
        answer(World.getPlayerFM().CT.WeaponControl[2] != 0 ? "1" : "0");
      }
    };
    new ParameterPilot(93, "pilot", "Weapon3") {
      public boolean set_accessible() { return set_accessible0(); } 
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
      public void get(List paramList) {
        answer(World.getPlayerFM().CT.WeaponControl[3] != 0 ? "1" : "0");
      }
    };
    new ParameterPilot(94, "pilot", "Weapon01") {
      public boolean set_accessible() { return set_accessible0(); } 
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
      public void get(List paramList) {
        answer((World.getPlayerFM().CT.WeaponControl[0] != 0) && (World.getPlayerFM().CT.WeaponControl[1] != 0) ? "1" : "0");
      }
    };
    new ParameterPilot(95, "pilot", "GunPods") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerAircraft().isGunPodsExist());
      }
      public void get(List paramList) {
        answer(World.getPlayerAircraft().isGunPodsOn() ? "1" : "0");
      }
    };
    new ParameterPilot(96, "pilot", "SIGHT_AUTO_ONOFF") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(97, "pilot", "SIGHT_DIST_PLUS") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(98, "pilot", "SIGHT_DIST_MINUS") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(99, "pilot", "SIGHT_SIDE_RIGHT") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(100, "pilot", "SIGHT_SIDE_LEFT") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(101, "pilot", "SIGHT_ALT_PLUS") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(102, "pilot", "SIGHT_ALT_MINUS") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(103, "pilot", "SIGHT_SPD_PLUS") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(104, "pilot", "SIGHT_SPD_MINUS") { public boolean get_accessible() { return false;
      }
    };
    new ParameterPilot(95, "pilot", "WINGFOLD") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasWingControl);
      }
      public void get(List paramList) {
        answer(World.getPlayerFM().CT.getWing() > 0.99F ? "1" : "0");
      }
    };
    new ParameterPilot(106, "pilot", "COCKPITDOOR") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasCockpitDoorControl);
      }
      public void get(List paramList) {
        answer(World.getPlayerFM().CT.getCockpitDoor() > 0.99F ? "1" : "0");
      }
    };
    new ParameterPilot(107, "pilot", "AIRCRAFT_CARRIERHOOK") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasArrestorControl);
      }
      public void get(List paramList) {
        answer(World.getPlayerFM().CT.arrestorControl > 0.5F ? "1" : "0");
      }
    };
    new ParameterPilot(108, "pilot", "AIRCRAFT_BRAKESHOE") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().canChangeBrakeShoe);
      }
      public void get(List paramList) {
        answer(World.getPlayerFM().brakeShoe ? "1" : "0");
      }
    };
    new ParameterGunner(110, "gunner", "Fire") {
      public boolean set_accessible() { return set_accessible0(); } 
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
      public void get(List paramList) {
        answer(this.cmd.isActive() ? "1" : "0");
      }
    };
    new ParameterMouseMove(111, "gunner", "Mouse") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (isAircraftValid()) && (!World.isPlayerParatrooper()) && (!World.isPlayerDead());
      }
    };
    new ParameterMove(40, "move", "power") {
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.PowerControl / 0.55F - 1.0F));
      }
    };
    new ParameterMove(41, "move", "flaps") {
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.FlapsControl / 0.5F - 1.0F));
      }
    };
    new ParameterMove(42, "move", "aileron") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (!World.getPlayerFM().CT.StabilizerControl);
      }
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.AileronControl));
      }
    };
    new ParameterMove(43, "move", "elevator") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (!World.getPlayerFM().CT.StabilizerControl);
      }
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.ElevatorControl));
      }
    };
    new ParameterMove(44, "move", "rudder") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (!World.getPlayerFM().CT.StabilizerControl);
      }
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.RudderControl));
      }
    };
    new ParameterMove(45, "move", "brakes") {
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.BrakeControl / 0.5F - 1.0F));
      }
    };
    new ParameterMove(46, "move", "pitch") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.cur().diffCur.ComplexEManagement) && (World.getPlayerFM().EI.isSelectionHasControlProp());
      }

      public void get(List paramList)
      {
        answer("" + fmt(World.getPlayerFM().CT.getStepControl() / 0.5F - 1.0F));
      }
    };
    new ParameterMove(47, "move", "trimaileron") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasAileronTrim);
      }
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.getTrimAileronControl() * 2.0F));
      }
    };
    new ParameterMove(48, "move", "trimelevator") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasElevatorTrim);
      }
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.getTrimElevatorControl() * 2.0F));
      }
    };
    new ParameterMove(49, "move", "trimrudder") {
      public boolean set_accessible() {
        return (super.set_accessible()) && (World.getPlayerFM().CT.bHasRudderTrim);
      }
      public void get(List paramList) {
        answer("" + fmt(World.getPlayerFM().CT.getTrimRudderControl() * 2.0F));
      }
    };
    new ParameterView(150, "aircraftView", "changeCockpit") {
      public boolean get_accessible() { return Main3D.cur3D().cockpitCurIndx() >= 0; } 
      public void get(List paramList) {
        answer("" + Main3D.cur3D().cockpitCurIndx());
      }
    };
    new ParameterView(151, "aircraftView", "cockpitView0");
    new ParameterView(152, "aircraftView", "cockpitView1");
    new ParameterView(153, "aircraftView", "cockpitView2");
    new ParameterView(154, "aircraftView", "cockpitView3");
    new ParameterView(155, "aircraftView", "cockpitView4");
    new ParameterView(156, "aircraftView", "cockpitView5");
    new ParameterView(157, "aircraftView", "cockpitView6");
    new ParameterView(158, "aircraftView", "cockpitView7");
    new ParameterView(159, "aircraftView", "cockpitView8");
    new ParameterView(160, "aircraftView", "cockpitView9");
    new ParameterView(161, "aircraftView", "fov90");
    new ParameterView(162, "aircraftView", "fov85");
    new ParameterView(163, "aircraftView", "fov80");
    new ParameterView(164, "aircraftView", "fov75");
    new ParameterView(165, "aircraftView", "fov70");
    new ParameterView(166, "aircraftView", "fov65");
    new ParameterView(167, "aircraftView", "fov60");
    new ParameterView(168, "aircraftView", "fov55");
    new ParameterView(168, "aircraftView", "fov50");
    new ParameterView(170, "aircraftView", "fov45");
    new ParameterView(171, "aircraftView", "fov40");
    new ParameterView(172, "aircraftView", "fov35");
    new ParameterView(173, "aircraftView", "fov30");
    new ParameterView(174, "aircraftView", "fovSwitch") {
      public boolean get_accessible() { return true; } 
      public void get(List paramList) {
        answer("" + fmt(Main3D.FOVX));
      }
    };
    new ParameterView(175, "aircraftView", "fovInc");
    new ParameterView(176, "aircraftView", "fovDec");
    new ParameterView(177, "aircraftView", "CockpitView");
    new ParameterView(178, "aircraftView", "CockpitShow");
    new ParameterView(179, "aircraftView", "OutsideView");
    new ParameterView(180, "aircraftView", "NextView");
    new ParameterView(181, "aircraftView", "NextViewEnemy");
    new ParameterView(182, "aircraftView", "OutsideViewFly");
    new ParameterView(183, "aircraftView", "PadlockView");
    new ParameterView(184, "aircraftView", "PadlockViewFriend");
    new ParameterView(185, "aircraftView", "PadlockViewGround");
    new ParameterView(186, "aircraftView", "PadlockViewFriendGround");
    new ParameterView(187, "aircraftView", "PadlockViewNext");
    new ParameterView(188, "aircraftView", "PadlockViewPrev");
    new ParameterView(189, "aircraftView", "PadlockViewForward") {
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
    };
    new ParameterView(190, "aircraftView", "ViewEnemyAir");
    new ParameterView(191, "aircraftView", "ViewFriendAir");
    new ParameterView(192, "aircraftView", "ViewEnemyDirectAir");
    new ParameterView(193, "aircraftView", "ViewEnemyGround");
    new ParameterView(194, "aircraftView", "ViewFriendGround");
    new ParameterView(195, "aircraftView", "ViewEnemyDirectGround");
    new ParameterView(196, "aircraftView", "OutsideViewFollow");
    new ParameterView(197, "aircraftView", "NextViewFollow");
    new ParameterView(198, "aircraftView", "NextViewEnemyFollow");
    new ParameterView(199, "aircraftView", "cockpitAim");
    new ParameterTrackIR(5, "PanView", "TrackIR");

    new ParameterMisc(200, "misc", "autopilot") { public boolean set_accessible() { return (super.set_accessible()) && (isFMValid());
      }
    };
    new ParameterMisc(203, "misc", "cockpitDim") { public boolean set_accessible() { return (super.set_accessible()) && (isFMValid()) && (!Main3D.cur3D().isViewOutside());
      }
    };
    new ParameterMisc(204, "misc", "cockpitLight") { public boolean set_accessible() { return (super.set_accessible()) && (isFMValid()) && (!Main3D.cur3D().isViewOutside());
      }
    };
    new ParameterMisc(205, "misc", "toggleNavLights") { public boolean set_accessible() { return (super.set_accessible()) && (isFMValid());
      }
    };
    new ParameterMisc(206, "misc", "toggleLandingLight") { public boolean set_accessible() { return (super.set_accessible()) && (isFMValid());
      }
    };
    new ParameterMisc(207, "misc", "toggleSmokes") { public boolean set_accessible() { return (super.set_accessible()) && (isFMValid());
      }
    };
    new ParameterMisc(201, "misc", "autopilotAuto") { public boolean set_accessible() { return (super.set_accessible()) && (isFMValid());
      }
    };
    new ParameterMisc(202, "misc", "ejectPilot") { public boolean set_accessible() { return (super.set_accessible()) && (isFMValid()) && (!World.isPlayerGunner());
      }
    };
    new ParameterMisc(208, "misc", "pad");
    new ParameterMisc(209, "misc", "chat");
    new ParameterMisc(211, "misc", "showPositionHint");
    new ParameterMisc(212, "misc", "iconTypes");
    new ParameterMisc(213, "misc", "showMirror");
    new ParameterMisc(214, "$$$misc", "quickSaveNetTrack");
    new ParameterMisc(216, "$$$misc", "radioChannelSwitch");
    new ParameterMisc(210, "misc", "onlineRating") {
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
    };
    new ParameterMisc(215, "$$$misc", "radioMuteKey") {
      public void set(List paramList) {
        if ((paramList == null) || (paramList.size() != 1)) return;
        boolean bool = "1".equals(paramList.get(0));
        HotKeyCmd.exec(bool, this.cmd.hotKeyCmdEnv().name(), this.cmd.name());
      }
    };
    new ParameterMisc(217, "timeCompression", "timeSpeedUp") { public boolean set_accessible() { return (super.set_accessible()) && (Time.isEnableChangeSpeed());
      }
    };
    new ParameterMisc(218, "timeCompression", "timeSpeedNormal") { public boolean set_accessible() { return (super.set_accessible()) && (Time.isEnableChangeSpeed());
      }
    };
    new ParameterMisc(219, "timeCompression", "timeSpeedDown") { public boolean set_accessible() { return (super.set_accessible()) && (Time.isEnableChangeSpeed());
      }
    };
    new ParameterMisc(220, "hotkeys", "pause") { public boolean set_accessible() { return (super.set_accessible()) && (Time.isEnableChangeSpeed());
      }
    };
    new ParameterMisc(221, "misc", "onlineRatingPage");
    new ParameterMisc(222, "misc", "soundMuteKey");
    new ParameterView(223, "aircraftView", "cockpitUp");
    new ParameterMisc(224, "timeCompression", "timeSkip") { public boolean set_accessible() { return (super.set_accessible()) && (Time.isEnableChangeSpeed());
      }
    };
  }

  private void receiveParam(int paramInt, List paramList)
  {
    try
    {
      Parameter localParameter = (Parameter)this.paramMap.get(paramInt & 0xFFFFFFFE);
      if (localParameter != null)
        if ((paramInt & 0x1) == 0) {
          if (localParameter.get_accessible())
            localParameter.get(paramList);
        }
        else if (localParameter.set_accessible())
          localParameter.set(paramList);
    }
    catch (Throwable localThrowable)
    {
      System.out.println(localThrowable.getMessage());
      localThrowable.printStackTrace();
    }
  }

  private void serverReceivePacket(DatagramPacket paramDatagramPacket) throws IOException {
    if (paramDatagramPacket.getLength() < 1) return;
    this.inputMsg.setData(null, false, paramDatagramPacket.getData(), paramDatagramPacket.getOffset(), paramDatagramPacket.getLength());

    int i = this.inputMsg.readUnsignedByte();
    if (i != 82) return; this.inputMsg.fixed();
    this.outList.clear();
    int j;
    Object localObject;
    while (true) { j = receiveKey();
      if (j == 0)
        break;
      this.inArg.clear();
      while (true) {
        localObject = receiveArg();
        if (localObject == null)
          break;
        this.inArg.add(localObject);
      }
      receiveParam(j, this.inArg);
    }

    while (this.outList.size() > 0) {
      j = PACKET_SIZE - 1;
      localObject = this.outPacket.getData();
      int k = 0;
      localObject[(k++)] = 65;
      while (this.outList.size() > 0) {
        String str = (String)this.outList.get(0);
        int m = str.length();
        if (m > j)
          break;
        this.outList.remove(0);
        for (int n = 0; n < m; n++)
          localObject[(k++)] = (byte)(str.charAt(n) & 0x7F);
      }
      this.outPacket.setAddress(paramDatagramPacket.getAddress());
      this.outPacket.setPort(paramDatagramPacket.getPort());
      this.outPacket.setLength(k);
      this.serverSocket.send(this.outPacket);
    }
  }

  private void do_answer(int paramInt, String paramString) {
    if (this.inOutBuf.length() > 0)
      this.inOutBuf.delete(0, this.inOutBuf.length());
    this.inOutBuf.append("/" + paramInt);
    if (paramString != null) {
      this.inOutBuf.append('\\');
      int i = paramString.length();
      for (int j = 0; j < i; j++) {
        char c = paramString.charAt(j);
        if ((c == '\\') || (c == '/'))
          this.inOutBuf.append('\\');
        this.inOutBuf.append(c);
      }
    }
    this.outList.add(this.inOutBuf.toString());
  }
  private void do_answer(int paramInt, String[] paramArrayOfString) {
    if (this.inOutBuf.length() > 0)
      this.inOutBuf.delete(0, this.inOutBuf.length());
    this.inOutBuf.append("/" + paramInt);
    if (paramArrayOfString != null) {
      int i = paramArrayOfString.length;
      for (int j = 0; j < i; j++) {
        String str = paramArrayOfString[j];
        this.inOutBuf.append('\\');
        int k = str.length();
        for (int m = 0; m < k; m++) {
          char c = str.charAt(m);
          if ((c == '\\') || (c == '/'))
            this.inOutBuf.append('\\');
          this.inOutBuf.append(c);
        }
      }
    }
    this.outList.add(this.inOutBuf.toString());
  }
  private void do_answer(int paramInt, List paramList) {
    if (this.inOutBuf.length() > 0)
      this.inOutBuf.delete(0, this.inOutBuf.length());
    this.inOutBuf.append("/" + paramInt);
    if (paramList != null) {
      int i = paramList.size();
      for (int j = 0; j < i; j++) {
        String str = (String)paramList.get(j);
        this.inOutBuf.append('\\');
        int k = str.length();
        for (int m = 0; m < k; m++) {
          char c = str.charAt(m);
          if ((c == '\\') || (c == '/'))
            this.inOutBuf.append('\\');
          this.inOutBuf.append(c);
        }
      }
    }
    this.outList.add(this.inOutBuf.toString());
  }

  private int receiveKey() throws IOException {
    if (receiveChar() != '/') return 0;
    int i = 0;
    while (this.inputMsg.available() > 0) {
      int j = receiveChar();
      if ((j == 47) || (j == 92)) {
        this.inputMsg.reset();
        return i;
      }
      if ((j < 48) || (j > 57))
        return 0;
      i = i * 10 + (j - 48);
    }
    return i;
  }
  private String receiveArg() throws IOException {
    char c1 = receiveChar();
    if (c1 == '/') {
      this.inputMsg.reset();
      return null;
    }if (c1 != '\\') {
      this.inputMsg.reset();
      return null;
    }
    if (this.inOutBuf.length() > 0)
      this.inOutBuf.delete(0, this.inOutBuf.length());
    while (true)
    {
      c1 = receiveChar(false);
      if (c1 == 0) {
        this.inputMsg.fixed();
        break;
      }if (c1 == '/') {
        this.inputMsg.reset();
        break;
      }if (c1 == '\\') {
        if (this.inputMsg.available() > 0) {
          char c2 = receiveChar(false);
          if ((c2 == '\\') || (c2 == '/')) {
            this.inputMsg.fixed();
            this.inOutBuf.append(c2);
          } else {
            this.inputMsg.reset();
            break;
          }
          continue;
        }this.inputMsg.reset();
        break;
      }

      this.inputMsg.fixed();
      this.inOutBuf.append(c1);
    }

    if (this.inOutBuf.length() > 0) {
      return this.inOutBuf.toString();
    }
    return null;
  }

  private char receiveChar() throws IOException {
    return receiveChar(true);
  }
  private char receiveChar(boolean paramBoolean) throws IOException {
    while (this.inputMsg.available() > 0) {
      if (paramBoolean)
        this.inputMsg.fixed();
      int i = this.inputMsg.readUnsignedByte();
      if ((i >= 33) && (i <= 126))
      {
        return (char)i;
      }
    }
    return '\000';
  }

  private DeviceLink(int paramInt, ArrayList paramArrayList)
  {
    this.enableClientAdr = paramArrayList;
    try {
      InetAddress localInetAddress = null;
      String str = Config.cur.ini.get("DeviceLink", "host", (String)null);
      if ((str != null) && (str.length() > 0))
        localInetAddress = InetAddress.getByName(str);
      else {
        localInetAddress = InetAddress.getLocalHost();
      }
      this.serverSocket = new DatagramSocket(paramInt, localInetAddress);
      this.serverSocket.setSoTimeout(0);
      this.serverSocket.setSendBufferSize(PACKET_SIZE);
      this.serverSocket.setReceiveBufferSize(PACKET_SIZE);
      this.inputPackets = new LinkedList();
      this.inputAction = new ActionReceivedPacket(null);
      registerParams();
      this.listener = new Listener(null);
      this.listener.setPriority(Thread.currentThread().getPriority() + 1);
      this.listener.start();
    } catch (Throwable localThrowable) {
      System.out.println(localThrowable.getMessage());
      localThrowable.printStackTrace();
    }
  }

  public static void start() {
    if (deviceLink != null) return;
    int i = Config.cur.ini.get("DeviceLink", "port", 0, 0, 65000);
    if (i == 0) return;
    ArrayList localArrayList = new ArrayList();
    String str1 = Config.cur.ini.get("DeviceLink", "IPS", (String)null);
    if (str1 != null) {
      StringTokenizer localStringTokenizer = new StringTokenizer(str1);
      while (localStringTokenizer.hasMoreTokens()) {
        String str2 = localStringTokenizer.nextToken();
        localArrayList.add(str2);
      }
    }

    deviceLink = new DeviceLink(i, localArrayList);
  }

  private class Listener extends Thread
  {
    private final DeviceLink this$0;

    private Listener()
    {
      this.this$0 = this$1;
    }
    public void run() { while ((RTSConf.cur != null) && (!RTSConf.isRequestExitApp()))
        try {
          DatagramPacket localDatagramPacket = new DatagramPacket(new byte[DeviceLink.PACKET_SIZE], DeviceLink.PACKET_SIZE);
          this.this$0.serverSocket.receive(localDatagramPacket);
          if (localDatagramPacket.getLength() >= 1) {
            if (this.this$0.enableClientAdr.size() > 0) {
              String str = localDatagramPacket.getAddress().getHostAddress();
              int i = this.this$0.enableClientAdr.size();
              int j = 0;
              for (int k = 0; k < i; k++) {
                if (str.equals(this.this$0.enableClientAdr.get(k))) {
                  j = 1;
                  break;
                }
              }
              if (j == 0) continue;
            }
            synchronized (this.this$0.inputAction) {
              this.this$0.inputPackets.add(localDatagramPacket);
              this.this$0.inputAction.activate();
            }
          }
        }
        catch (Throwable localThrowable)
        {
        }
    }

    Listener(DeviceLink.1 arg2)
    {
      this();
    }
  }

  private class ActionReceivedPacket extends MsgAction
  {
    private final DeviceLink this$0;

    private ActionReceivedPacket()
    {
      this.this$0 = this$1;
    }
    public void doAction() { while (this.this$0.inputPackets.size() > 0) {
        DatagramPacket localDatagramPacket = (DatagramPacket)this.this$0.inputPackets.get(0);
        this.this$0.inputPackets.remove(0);
        try {
          this.this$0.serverReceivePacket(localDatagramPacket);
        }
        catch (Throwable localThrowable)
        {
        }
      } }

    public void activate() {
      if (!busy())
        post(64, this, 0.0D);
    }

    ActionReceivedPacket(DeviceLink.1 arg2)
    {
      this();
    }
  }

  private class Parameter
  {
    public int id;

    public void set(List paramList)
    {
    }

    public void get(List paramList)
    {
    }

    public boolean get_accessible()
    {
      return true; } 
    public boolean set_accessible() { return true; } 
    protected boolean isMissinValid() {
      return Mission.isPlaying();
    }
    protected boolean isAircraftValid() {
      if (!isMissinValid()) return false;
      return Actor.isAlive(World.getPlayerAircraft());
    }
    protected boolean isFMValid() {
      return (isAircraftValid()) && (!World.isPlayerGunner()) && (!World.isPlayerParatrooper()) && (!World.isPlayerDead());
    }

    protected boolean isNetAccessible()
    {
      return (Mission.isSingle()) || (NetMissionTrack.isPlaying());
    }

    protected int engineNum(List paramList)
    {
      if ((paramList == null) || (paramList.size() == 0)) return 0; try
      {
        return Integer.parseInt((String)paramList.get(0)); } catch (Exception localException) {
      }
      return -1;
    }

    protected Actor actorForName(List paramList) {
      if ((paramList == null) || (paramList.size() == 0)) return null;
      String str = (String)paramList.get(0);
      return Actor.getByName(str);
    }
    protected String fmt(float paramFloat) {
      int i = paramFloat < 0.0F ? 1 : 0;
      if (i != 0) paramFloat = -paramFloat;
      float f = paramFloat + 0.005F - (int)paramFloat;
      if (f >= 0.1F) return (i != 0 ? "-" : "") + (int)paramFloat + "." + (int)(f * 100.0F);
      return (i != 0 ? "-" : "") + (int)paramFloat + ".0" + (int)(f * 100.0F);
    }
    public void answer(String paramString) {
      DeviceLink.this.do_answer(this.id, paramString);
    }
    public void answer(String[] paramArrayOfString) {
      DeviceLink.this.do_answer(this.id, paramArrayOfString);
    }
    public void answer(List paramList) {
      DeviceLink.this.do_answer(this.id, paramList);
    }

    public Parameter(int arg2)
    {
      int i;
      i *= 2;
      DeviceLink.this.paramMap.put(i, this);
      this.id = i;
    }
  }

  private class ParameterMisc extends DeviceLink.Parameter
  {
    HotKeyCmd cmd;

    public boolean get_accessible()
    {
      return false;
    }
    protected boolean isFMValid() { return (isAircraftValid()) && (!World.isPlayerParatrooper()) && (!World.isPlayerDead());
    }

    public boolean set_accessible()
    {
      if (!this.cmd.isEnabled()) return false;
      HotKeyCmdEnv localHotKeyCmdEnv = this.cmd.hotKeyCmdEnv();
      if (!localHotKeyCmdEnv.isEnabled()) return false;
      return HotKeyEnv.isEnabled(localHotKeyCmdEnv.name());
    }

    public void set(List paramList) {
      this.cmd._exec(true);
      this.cmd._exec(false);
    }
    public ParameterMisc(int paramString1, String paramString2, String arg4) {
      super(paramString1);
      String str;
      this.cmd = HotKeyCmdEnv.env(paramString2).get(str);
    }
  }

  private class ParameterTrackIR extends DeviceLink.Parameter
  {
    HotKeyCmdTrackIRAngles cmd;

    public boolean get_accessible()
    {
      return false;
    }
    public boolean set_accessible() { if (!this.cmd.isEnabled()) return false;
      HotKeyCmdEnv localHotKeyCmdEnv = this.cmd.hotKeyCmdEnv();
      if (!localHotKeyCmdEnv.isEnabled()) return false;
      return HotKeyEnv.isEnabled(localHotKeyCmdEnv.name()); } 
    public void set(List paramList) { float f1;
      float f2;
      float f3;
      try { f1 = Float.parseFloat((String)paramList.get(0));
        f2 = Float.parseFloat((String)paramList.get(1));
        f3 = Float.parseFloat((String)paramList.get(2));
      } catch (Exception localException) {
        return;
      }
      this.cmd._exec(f1, f2, f3); }

    public ParameterTrackIR(int paramString1, String paramString2, String arg4) {
      super(paramString1);
      String str;
      this.cmd = ((HotKeyCmdTrackIRAngles)HotKeyCmdEnv.env(paramString2).get(str));
    }
  }

  private class ParameterView extends DeviceLink.Parameter
  {
    HotKeyCmd cmd;

    public boolean get_accessible()
    {
      return false;
    }
    public boolean set_accessible() { if (!this.cmd.isEnabled()) return false;
      HotKeyCmdEnv localHotKeyCmdEnv = this.cmd.hotKeyCmdEnv();
      if (!localHotKeyCmdEnv.isEnabled()) return false;
      return HotKeyEnv.isEnabled(localHotKeyCmdEnv.name()); }

    public void set(List paramList)
    {
      this.cmd._exec(true);
      this.cmd._exec(false);
    }
    public ParameterView(int paramString1, String paramString2, String arg4) {
      super(paramString1);
      String str;
      this.cmd = HotKeyCmdEnv.env(paramString2).get(str);
    }
  }

  private class ParameterPilot extends DeviceLink.Parameter
  {
    HotKeyCmd cmd;

    public boolean get_accessible()
    {
      return isFMValid();
    }
    public boolean set_accessible() {
      if (!this.cmd.isEnabled()) return false;
      HotKeyCmdEnv localHotKeyCmdEnv = this.cmd.hotKeyCmdEnv();
      if (!localHotKeyCmdEnv.isEnabled()) return false;
      if (!HotKeyEnv.isEnabled(localHotKeyCmdEnv.name())) return false;
      return (isFMValid()) && (((RealFlightModel)World.getPlayerFM()).isRealMode()) && (!this.cmd.isActive());
    }

    protected boolean set_accessible0()
    {
      if (!this.cmd.isEnabled()) return false;
      HotKeyCmdEnv localHotKeyCmdEnv = this.cmd.hotKeyCmdEnv();
      if (!localHotKeyCmdEnv.isEnabled()) return false;
      if (!HotKeyEnv.isEnabled(localHotKeyCmdEnv.name())) return false;
      return (isFMValid()) && (((RealFlightModel)World.getPlayerFM()).isRealMode());
    }

    public void set(List paramList) {
      this.cmd._exec(true);
      this.cmd._exec(false);
    }
    public ParameterPilot(int paramString1, String paramString2, String arg4) {
      super(paramString1);
      String str;
      this.cmd = HotKeyCmdEnv.env(paramString2).get(str);
    }
  }

  private class ParameterGunner extends DeviceLink.ParameterPilot
  {
    protected boolean isFMValid()
    {
      return (isAircraftValid()) && (!World.isPlayerParatrooper()) && (!World.isPlayerDead());
    }

    public boolean set_accessible()
    {
      if (!this.cmd.isEnabled()) return false;
      HotKeyCmdEnv localHotKeyCmdEnv = this.cmd.hotKeyCmdEnv();
      if (!localHotKeyCmdEnv.isEnabled()) return false;
      if (!HotKeyEnv.isEnabled(localHotKeyCmdEnv.name())) return false;
      return (isFMValid()) && (!this.cmd.isActive());
    }

    public ParameterGunner(int paramString1, String paramString2, String arg4) {
      super(paramString1, paramString2, str);
    }
  }

  private class ParameterMove extends DeviceLink.Parameter
  {
    HotKeyCmdMove cmd;

    public boolean get_accessible()
    {
      return isFMValid();
    }
    public boolean set_accessible() {
      if (!this.cmd.isEnabled()) return false;
      HotKeyCmdEnv localHotKeyCmdEnv = this.cmd.hotKeyCmdEnv();
      if (!localHotKeyCmdEnv.isEnabled()) return false;
      if (!HotKeyEnv.isEnabled(localHotKeyCmdEnv.name())) return false;
      return (isFMValid()) && (((RealFlightModel)World.getPlayerFM()).isRealMode()) && (!this.cmd.isActive());
    }

    public void set(List paramList)
    {
      float f = 0.0F;
      try {
        f = Float.parseFloat((String)paramList.get(0));
      } catch (Exception localException) {
        return;
      }
      if (f < -1.0F) f = -1.0F;
      if (f > 1.0F) f = 1.0F;
      this.cmd._exec(Math.round(f * 125.0F));
    }
    public ParameterMove(int paramString1, String paramString2, String arg4) {
      super(paramString1);
      String str;
      this.cmd = ((HotKeyCmdMove)HotKeyCmdEnv.env(paramString2).get(str));
    }
  }

  private class ParameterMouseMove extends DeviceLink.Parameter
  {
    HotKeyCmdMouseMove cmd;

    public boolean get_accessible()
    {
      return false;
    }
    public boolean set_accessible() { if (!this.cmd.isEnabled()) return false;
      HotKeyCmdEnv localHotKeyCmdEnv = this.cmd.hotKeyCmdEnv();
      if (!localHotKeyCmdEnv.isEnabled()) return false;
      return HotKeyEnv.isEnabled(localHotKeyCmdEnv.name()); } 
    public void set(List paramList) { int i;
      int j;
      int k;
      try { i = Integer.parseInt((String)paramList.get(0));
        j = Integer.parseInt((String)paramList.get(1));
        k = Integer.parseInt((String)paramList.get(2));
      } catch (Exception localException) {
        return;
      }
      this.cmd._exec(i, j, k); }

    public ParameterMouseMove(int paramString1, String paramString2, String arg4) {
      super(paramString1);
      String str;
      this.cmd = ((HotKeyCmdMouseMove)HotKeyCmdEnv.env(paramString2).get(str));
    }
  }
}