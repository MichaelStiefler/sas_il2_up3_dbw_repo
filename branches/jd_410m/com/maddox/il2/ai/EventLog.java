package com.maddox.il2.ai;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.game.DServer;
import com.maddox.il2.game.Main;
import com.maddox.il2.game.Mission;
import com.maddox.il2.game.campaign.Campaign;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.net.NetUser;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.buildings.House;
import com.maddox.rts.HomePath;
import com.maddox.rts.IniFile;
import com.maddox.rts.NetEnv;
import com.maddox.rts.Property;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class EventLog
{
  public static ArrayList actions = new ArrayList();

  public static Action lastFly = null;
  public static final int OCCUPIED = 0;
  public static final int TRYOCCUPIED = 1;
  public static final int CRASHED = 2;
  public static final int SHOTDOWN = 3;
  public static final int DESTROYED = 4;
  public static final int BAILEDOUT = 5;
  public static final int KILLED = 6;
  public static final int LANDED = 7;
  public static final int CAPTURED = 8;
  public static final int WOUNDED = 9;
  public static final int HEAVILYWOUNDED = 10;
  public static final int FLY = 11;
  public static final int PARAKILLED = 12;
  public static final int CHUTEKILLED = 13;
  public static final int SMOKEON = 14;
  public static final int SMOKEOFF = 15;
  public static final int LANDINGLIGHTON = 16;
  public static final int LANDINGLIGHTOFF = 17;
  public static final int WEAPONSLOAD = 18;
  public static final int PARALANDED = 19;
  public static final int DAMAGEDGROUND = 20;
  public static final int DAMAGED = 21;
  public static final int DISCONNECTED = 22;
  public static final int CONNECTED = 23;
  public static final int INFLIGHT = 24;
  public static final int REFLY = 25;
  public static final int REMOVED = 26;
  private static PrintStream file = null;
  private static boolean bBuffering = true;
  private static String fileName = null;
  private static boolean logKeep = false;
  private static DateFormat longDate = null;
  private static DateFormat shortDate = null;
  private static boolean bInited = false;

  public static void flyPlayer(Point3d paramPoint3d)
  {
    if (Mission.isDogfight()) return;
    if (lastFly != null) {
      double d = (lastFly.x - paramPoint3d.x) * (lastFly.x - paramPoint3d.x) + (lastFly.y - paramPoint3d.y) * (lastFly.y - paramPoint3d.y);

      if (d < 250000.0D)
        return;
    }
    lastFly = new Action(11, null, -1, null, -1, 0, (float)paramPoint3d.x, (float)paramPoint3d.y);
  }

  public static void resetGameClear() {
    actions.clear();
    lastFly = null;
  }

  public static void resetGameCreate()
  {
  }

  private static void checkInited()
  {
    if (!bInited) {
      logKeep = Config.cur.ini.get("game", "eventlogkeep", 1, 0, 1) == 1;
      fileName = Config.cur.ini.get("game", "eventlog", (String)null);
      bInited = true;
    }
    if (longDate == null) {
      longDate = DateFormat.getDateTimeInstance(2, 2);
      shortDate = DateFormat.getTimeInstance(2);
    }
  }

  public static void checkState() {
    checkInited();
    if ((fileName == null) && (Main.cur().campaign != null) && (Main.cur().campaign.isDGen())) {
      fileName = "eventlog.lst";
      Config.cur.ini.set("game", "eventlog", "eventlog.lst");
      Config.cur.ini.saveFile();
    }
    if (logKeep) {
      checkBuffering();
    } else {
      if (file != null) {
        try { file.close(); } catch (Exception localException1) {
        }file = null;
      }
      if (fileName != null)
        try {
          File localFile = new File(HomePath.toFileSystemName(fileName, 0));
          localFile.delete(); } catch (Exception localException2) {
        }
    }
  }

  private static void checkBuffering() {
    if (file == null) return;
    int i = 1;
    if (((Main.cur() instanceof DServer)) || ((Main.cur().netServerParams != null) && (!Main.cur().netServerParams.isSingle()) && (Main.cur().netServerParams.isMaster())))
    {
      i = 0;
    }if (i != bBuffering) {
      close();
      open();
    }
  }

  public static boolean open() {
    if (file != null)
      return true;
    checkInited();
    if (fileName == null)
      return false;
    try {
      bBuffering = true;
      if (((Main.cur() instanceof DServer)) || ((Main.cur().netServerParams != null) && (!Main.cur().netServerParams.isSingle()) && (Main.cur().netServerParams.isMaster())))
      {
        bBuffering = false;
      }if (bBuffering) {
        file = new PrintStream(new BufferedOutputStream(new FileOutputStream(HomePath.toFileSystemName(fileName, 0), true), 2048));
      }
      else
        file = new PrintStream(new FileOutputStream(HomePath.toFileSystemName(fileName, 0), true));
    }
    catch (Exception localException) {
      return false;
    }
    return true;
  }
  public static void close() {
    if (file != null) {
      file.flush();
      file.close();
      file = null;
    }
  }

  public static StringBuffer logOnTime(float paramFloat) {
    if (Mission.isDogfight()) {
      Calendar localCalendar = Calendar.getInstance();
      StringBuffer localStringBuffer1 = new StringBuffer();
      localStringBuffer1.append("[");
      localStringBuffer1.append(shortDate.format(localCalendar.getTime()));
      localStringBuffer1.append("] ");
      return localStringBuffer1;
    }
    int i = (int)paramFloat;
    int j = (int)((paramFloat - i) * 60.0F % 60.0F);
    int k = (int)((paramFloat - i - j / 60) * 3600.0F % 60.0F);
    StringBuffer localStringBuffer2 = new StringBuffer();
    if (i < 10)
      localStringBuffer2.append('0');
    localStringBuffer2.append(i);
    localStringBuffer2.append(':');
    if (j < 10)
      localStringBuffer2.append('0');
    localStringBuffer2.append(j);
    localStringBuffer2.append(':');
    if (k < 10)
      localStringBuffer2.append('0');
    localStringBuffer2.append(k);
    localStringBuffer2.append(' ');
    return localStringBuffer2;
  }

  public static String name(Actor paramActor)
  {
    if (!Actor.isValid(paramActor)) {
      return "";
    }
    if ((Mission.isNet()) && (Mission.isDogfight()) && ((paramActor instanceof Aircraft))) {
      Aircraft localAircraft = (Aircraft)paramActor;
      NetUser localNetUser = localAircraft.netUser();
      if (localNetUser != null) {
        return localNetUser.shortName() + ":" + Property.stringValue(localAircraft.getClass(), "keyName", "");
      }
      return Property.stringValue(localAircraft.getClass(), "keyName", "");
    }

    return paramActor.name();
  }

  private static boolean isTyping(int paramInt) {
    if (Main.cur().netServerParams == null)
      return true;
    if (Main.cur().netServerParams.isMaster())
      return true;
    return (Main.cur().netServerParams.eventlogClient() & 1 << paramInt) != 0;
  }

  public static void type(int paramInt1, float paramFloat1, String paramString1, String paramString2, int paramInt2, float paramFloat2, float paramFloat3, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = logOnTime(paramFloat1);
    switch (paramInt1) {
    case 0:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(") seat occupied by ");
      localStringBuffer.append(paramString2);
      localStringBuffer.append(" at ");
      break;
    case 1:
      localStringBuffer.append(paramString2);
      localStringBuffer.append(" is trying to occupy seat ");
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(")");
      break;
    case 2:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" crashed at ");
      break;
    case 3:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" shot down by ");
      localStringBuffer.append(paramString2);
      localStringBuffer.append(" at ");
      break;
    case 4:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" destroyed by ");
      localStringBuffer.append(paramString2);
      localStringBuffer.append(" at ");
      break;
    case 5:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(") bailed out at ");
      break;
    case 6:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      if ((paramString2 == null) || ("".equals(paramString2))) {
        localStringBuffer.append(") was killed at ");
      } else {
        localStringBuffer.append(") was killed by ");
        localStringBuffer.append(paramString2);
        localStringBuffer.append(" at ");
      }
      break;
    case 7:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" landed at ");
      break;
    case 24:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" in flight at ");
      break;
    case 8:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(") was captured at ");
      break;
    case 9:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(") was wounded at ");
      break;
    case 10:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(") was heavily wounded at ");
      break;
    case 12:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(") was killed in his chute by ");
      localStringBuffer.append(paramString2);
      localStringBuffer.append(" at ");
      break;
    case 13:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(") has chute destroyed by ");
      localStringBuffer.append(paramString2);
      localStringBuffer.append(" at ");
      break;
    case 14:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" turned wingtip smokes on at ");
      break;
    case 15:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" turned wingtip smokes off at ");
      break;
    case 16:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" turned landing lights on at ");
      break;
    case 17:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" turned landing lights off at ");
      break;
    case 18:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" loaded weapons '");
      localStringBuffer.append(paramString2);
      localStringBuffer.append("' fuel ");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append("%");
      break;
    case 19:
      localStringBuffer.append(paramString1);
      localStringBuffer.append("(");
      localStringBuffer.append(paramInt2);
      localStringBuffer.append(") successfully bailed out at ");
      break;
    case 20:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" damaged on the ground at ");
      break;
    case 21:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" damaged by ");
      localStringBuffer.append(paramString2);
      localStringBuffer.append(" at ");
      break;
    case 22:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" has disconnected");
      break;
    case 23:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" has connected");
      break;
    case 25:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" entered refly menu");
      break;
    case 26:
      localStringBuffer.append(paramString1);
      localStringBuffer.append(" removed at ");
      break;
    case 11:
    default:
      return;
    }
    if ((paramInt1 != 1) && (paramInt1 != 18) && (paramInt1 != 22) && (paramInt1 != 23) && (paramInt1 != 25)) {
      localStringBuffer.append(paramFloat2);
      localStringBuffer.append(" ");
      localStringBuffer.append(paramFloat3);
    }
    if ((open()) && (isTyping(paramInt1)))
      file.println(localStringBuffer);
    if (paramBoolean)
      ((NetUser)NetEnv.host()).replicateEventLog(paramInt1, paramFloat1, paramString1, paramString2, paramInt2, paramFloat2, paramFloat3);
  }

  public static void type(boolean paramBoolean, String paramString)
  {
    if (open()) {
      if (paramBoolean) {
        Calendar localCalendar = Calendar.getInstance();
        file.println("[" + longDate.format(localCalendar.getTime()) + "] " + paramString);
      } else {
        file.println(paramString);
      }
      file.flush();
    }
  }

  public static void type(String paramString)
  {
    StringBuffer localStringBuffer = logOnTime(World.getTimeofDay());
    localStringBuffer.append(paramString);
    if (open()) {
      file.println(localStringBuffer);
      file.flush();
    }
  }

  public static void onOccupied(Aircraft paramAircraft, NetUser paramNetUser, int paramInt) {
    if (!Actor.isValid(paramAircraft)) return;

    String str = null;
    if (paramNetUser != null)
      str = paramNetUser.uniqueName();
    else if (Mission.isSingle())
      str = "Player";
    if (str == null) return;
    type(0, World.getTimeofDay(), name(paramAircraft), str, paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y, true);

    if (!Mission.isDogfight())
      new Action(0, name(paramAircraft), 0, str, -1, paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y);
  }

  public static void onTryOccupied(String paramString, NetUser paramNetUser, int paramInt)
  {
    String str = null;
    if (paramNetUser != null)
      str = paramNetUser.uniqueName();
    else if (Mission.isSingle())
      str = "Player";
    if (str == null) return;
    type(1, World.getTimeofDay(), paramString, str, paramInt, 0.0F, 0.0F, true);
  }

  public static void onActorDied(Actor paramActor1, Actor paramActor2) {
    if (!Mission.isPlaying()) return;
    if (!Actor.isValid(paramActor1)) return;
    if (((paramActor1 instanceof House)) && 
      (Main.cur().netServerParams != null) && (Main.cur().netServerParams.eventlogHouse())) {
      House localHouse = (House)paramActor1;
      type(4, World.getTimeofDay(), localHouse.getMeshLiveName(), name(paramActor2), 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y, false);

      return;
    }

    if (!paramActor1.isNamed()) return;
    if (paramActor1.isNetMirror()) return;
    if (World.cur().scoreCounter.getRegisteredType(paramActor1) < 0) return;

    if (paramActor2 == World.remover) {
      type(26, World.getTimeofDay(), name(paramActor1), "", 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y, true);
    }
    else if ((paramActor1 == paramActor2) || (!Actor.isValid(paramActor2)) || (!paramActor2.isNamed())) {
      type(2, World.getTimeofDay(), name(paramActor1), "", 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y, true);

      if (!Mission.isDogfight()) {
        new Action(2, name(paramActor1), World.cur().scoreCounter.getRegisteredType(paramActor1), null, -1, 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y);
      }
    }
    else if ((paramActor1 instanceof Aircraft)) {
      type(3, World.getTimeofDay(), name(paramActor1), name(paramActor2), 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y, true);

      if (!Mission.isDogfight())
        new Action(3, name(paramActor1), 0, name(paramActor2), World.cur().scoreCounter.getRegisteredType(paramActor2), 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y);
    }
    else
    {
      type(4, World.getTimeofDay(), name(paramActor1), name(paramActor2), 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y, true);

      if (!Mission.isDogfight())
        new Action(4, name(paramActor1), World.cur().scoreCounter.getRegisteredType(paramActor1), name(paramActor2), World.cur().scoreCounter.getRegisteredType(paramActor2), 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y);
    }
  }

  public static void onBailedOut(Aircraft paramAircraft, int paramInt)
  {
    if (!Mission.isPlaying()) return;
    if (paramAircraft.isNetMirror()) return;
    type(5, World.getTimeofDay(), name(paramAircraft), "", paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y, true);

    if (!Mission.isDogfight())
      new Action(5, name(paramAircraft), 0, null, -1, paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y);
  }

  public static void onPilotKilled(Aircraft paramAircraft, int paramInt, Actor paramActor)
  {
    if (!Mission.isPlaying()) return;
    if (paramAircraft.isNetMirror()) return;
    String str = null;
    if (Actor.isValid(paramActor))
      str = name(paramActor);
    type(6, World.getTimeofDay(), name(paramAircraft), str == null ? "" : str, paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y, true);

    if (!Mission.isDogfight())
      new Action(6, name(paramAircraft), 0, str, -1, paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y);
  }

  public static void onPilotKilled(Actor paramActor, String paramString, int paramInt)
  {
    if (!Mission.isPlaying()) return;

    type(6, World.getTimeofDay(), paramString, "", paramInt, (float)paramActor.pos.getAbsPoint().x, (float)paramActor.pos.getAbsPoint().y, true);

    if (!Mission.isDogfight())
      new Action(6, paramString, 0, null, -1, paramInt, (float)paramActor.pos.getAbsPoint().x, (float)paramActor.pos.getAbsPoint().y);
  }

  public static void onAirLanded(Aircraft paramAircraft)
  {
    if (!Mission.isPlaying()) return;
    if (paramAircraft.isNetMirror()) return;
    type(7, World.getTimeofDay(), name(paramAircraft), "", 0, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y, true);

    if (!Mission.isDogfight())
      new Action(7, name(paramAircraft), 0, null, -1, 0, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y);
  }

  public static void onAirInflight(Aircraft paramAircraft)
  {
    if (!Mission.isPlaying()) return;
    if (paramAircraft.isNetMirror()) return;
    type(24, World.getTimeofDay(), name(paramAircraft), "", 0, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y, true);
  }

  public static void onCaptured(Actor paramActor, String paramString, int paramInt)
  {
    if (!Mission.isPlaying()) return;
    if (paramActor.isNetMirror()) return;
    type(8, World.getTimeofDay(), paramString, "", paramInt, (float)paramActor.pos.getAbsPoint().x, (float)paramActor.pos.getAbsPoint().y, true);
  }

  public static void onCaptured(Aircraft paramAircraft, int paramInt) {
    if (!Mission.isPlaying()) return;
    if (paramAircraft.isNetMirror()) return;
    type(8, World.getTimeofDay(), name(paramAircraft), "", paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y, true);
  }

  public static void onPilotWounded(Aircraft paramAircraft, int paramInt) {
    if (!Mission.isPlaying()) return;
    if (paramAircraft.isNetMirror()) return;
    type(9, World.getTimeofDay(), name(paramAircraft), "", paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y, true);
  }

  public static void onPilotHeavilyWounded(Aircraft paramAircraft, int paramInt) {
    if (!Mission.isPlaying()) return;
    if (paramAircraft.isNetMirror()) return;
    type(10, World.getTimeofDay(), name(paramAircraft), "", paramInt, (float)paramAircraft.pos.getAbsPoint().x, (float)paramAircraft.pos.getAbsPoint().y, true);
  }

  public static void onParaKilled(Actor paramActor1, String paramString, int paramInt, Actor paramActor2) {
    if (!Mission.isPlaying()) return;
    if (paramActor1.isNetMirror()) return;
    type(12, World.getTimeofDay(), paramString, name(paramActor2), paramInt, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y, true);

    if (!Mission.isDogfight())
      new Action(6, paramString, 0, null, -1, paramInt, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y);
  }

  public static void onChuteKilled(Actor paramActor1, String paramString, int paramInt, Actor paramActor2) {
    if (!Mission.isPlaying()) return;
    if (paramActor1.isNetMirror()) return;
    type(13, World.getTimeofDay(), paramString, name(paramActor2), paramInt, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y, true);
  }

  public static void onToggleSmoke(Actor paramActor, boolean paramBoolean) {
    if (!Mission.isPlaying()) return;
    if (paramActor.isNetMirror()) return;
    type(paramBoolean ? 14 : 15, World.getTimeofDay(), name(paramActor), "", 0, (float)paramActor.pos.getAbsPoint().x, (float)paramActor.pos.getAbsPoint().y, true);
  }

  public static void onToggleLandingLight(Actor paramActor, boolean paramBoolean) {
    if (!Mission.isPlaying()) return;
    if (paramActor.isNetMirror()) return;
    type(paramBoolean ? 16 : 17, World.getTimeofDay(), name(paramActor), "", 0, (float)paramActor.pos.getAbsPoint().x, (float)paramActor.pos.getAbsPoint().y, true);
  }

  public static void onWeaponsLoad(Actor paramActor, String paramString, int paramInt) {
    if (paramActor.isNetMirror()) return;
    type(18, World.getTimeofDay(), name(paramActor), paramString, paramInt, 0.0F, 0.0F, true);
  }
  public static void onParaLanded(Actor paramActor, String paramString, int paramInt) {
    if (!Mission.isPlaying()) return;
    if (paramActor.isNetMirror()) return;
    type(19, World.getTimeofDay(), paramString, "", paramInt, (float)paramActor.pos.getAbsPoint().x, (float)paramActor.pos.getAbsPoint().y, true);
  }

  public static void onDamagedGround(Actor paramActor) {
    if (!Actor.isValid(paramActor)) return;
    if (!Mission.isPlaying()) return;
    if (paramActor.isNetMirror()) return;
    type(20, World.getTimeofDay(), name(paramActor), "", 0, (float)paramActor.pos.getAbsPoint().x, (float)paramActor.pos.getAbsPoint().y, true);
  }

  public static void onDamaged(Actor paramActor1, Actor paramActor2) {
    if (!Actor.isValid(paramActor1)) return;
    if (!Mission.isPlaying()) return;
    if (paramActor1.isNetMirror()) return;
    type(21, World.getTimeofDay(), name(paramActor1), name(paramActor2), 0, (float)paramActor1.pos.getAbsPoint().x, (float)paramActor1.pos.getAbsPoint().y, true);
  }

  public static void onDisconnected(String paramString) {
    if (!Mission.isPlaying()) return;

    type(22, World.getTimeofDay(), paramString, "", 0, 0.0F, 0.0F, false);
  }

  public static void onConnected(String paramString) {
    if (!Mission.isPlaying()) return;

    type(23, World.getTimeofDay(), paramString, "", 0, 0.0F, 0.0F, false);
  }

  public static void onRefly(String paramString) {
    if (!Mission.isPlaying()) return;
    type(25, World.getTimeofDay(), paramString, "", 0, 0.0F, 0.0F, true);
  }

  public static class Action
  {
    public int event;
    public float time;
    public String arg0;
    public int scoreItem0;
    public int army0;
    public String arg1;
    public int scoreItem1;
    public int argi;
    public float x;
    public float y;

    public Action()
    {
    }

    public Action(int paramInt1, String paramString1, int paramInt2, String paramString2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2)
    {
      this.event = paramInt1;
      this.time = World.getTimeofDay();
      this.arg0 = paramString1;
      this.scoreItem0 = paramInt2;
      this.army0 = 0;
      if (paramString1 != null) {
        Actor localActor = Actor.getByName(paramString1);
        if (localActor != null)
          this.army0 = localActor.getArmy();
      }
      this.arg1 = paramString2;
      this.scoreItem1 = paramInt3;
      this.argi = paramInt4;
      this.x = paramFloat1;
      this.y = paramFloat2;
      if ((Main.cur().netServerParams != null) && (Main.cur().netServerParams.isMaster()) && (Main.cur().netServerParams.isDedicated()))
      {
        return;
      }EventLog.actions.add(this);
    }
  }
}