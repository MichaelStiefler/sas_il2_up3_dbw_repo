package com.maddox.sound;

import java.io.PrintStream;
import java.util.HashMap;

public class ControlInfo extends BaseObject
{
  public static final int CC_SPEED = 64;
  public static final int CC_ANGLE = 65;
  public static final int CC_DIST = 66;
  public static final int CC_ENV = 67;
  public static final int CC_RPM = 100;
  public static final int CC_DAMAGE = 101;
  public static final int CC_DIVING = 102;
  public static final int CC_FLATTER = 103;
  public static final int CC_BRAKE = 104;
  public static final int CC_GEAR = 105;
  public static final int CC_START = 106;
  public static final int CC_LOAD = 107;
  public static final int CC_MOTOR_LD = 108;
  public static final int CC_PROP_LD = 109;
  public static final int CC_DOOR = 110;
  public static final int CC_RELSPEED = 111;
  public static final int CC_MOTOR_STAGE = 112;
  public static final int CC_BOMB_SPEED = 200;
  public static final int CC_BOMB_TIME = 201;
  public static final int CC_MASSA = 202;
  public static final int CC_EMIT_RUN = 500;
  public static final int CC_EMIT_SPEED = 501;
  public static final int CC_MOD = 1000;
  protected int cc;
  protected Class cls;
  protected static HashMap map = new HashMap();

  protected ControlInfo(String paramString, int paramInt, Class paramClass)
  {
    this.cc = paramInt;
    this.cls = paramClass;
    map.put(paramString, this);
  }

  protected static SoundControl get(String paramString, int paramInt)
  {
    try {
      if (paramInt == 0) return null;
      ControlInfo localControlInfo = (ControlInfo)map.get(paramString);
      SoundControl localSoundControl = (SoundControl)localControlInfo.cls.newInstance();
      localSoundControl.init(localControlInfo.cc, paramInt);
      return localSoundControl;
    }
    catch (Exception localException) {
      System.out.println("Cannot create sound control " + paramString);
    }
    return null;
  }

  static {
    new ControlInfo("speed", 64, BoundsControl.class);
    new ControlInfo("angle", 65, BoundsControl.class);
    new ControlInfo("dist", 66, BoundsControl.class);
    new ControlInfo("env", 67, EnvControl.class);

    new ControlInfo("rpm", 100, BoundsControl.class);
    new ControlInfo("damage", 101, BoundsControl.class);

    new ControlInfo("diving", 102, BoundsControl.class);
    new ControlInfo("flatter", 103, BoundsControl.class);
    new ControlInfo("brake", 104, BoundsControl.class);
    new ControlInfo("gear", 105, BoundsControl.class);
    new ControlInfo("start", 106, BoundsControl.class);
    new ControlInfo("load", 107, BoundsControl.class);

    new ControlInfo("motorld", 108, BoundsControl.class);
    new ControlInfo("propld", 109, BoundsControl.class);
    new ControlInfo("door", 110, BoundsControl.class);
    new ControlInfo("relspeed", 111, BoundsControl.class);
    new ControlInfo("mostage", 112, BoundsControl.class);

    new ControlInfo("bombspeed", 200, BoundsControl.class);
    new ControlInfo("bombtime", 201, BoundsControl.class);

    new ControlInfo("massa", 202, BoundsControl.class);

    new ControlInfo("mod", 1000, ModControl.class);
  }
}