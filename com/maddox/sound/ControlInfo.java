// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ControlInfo.java

package com.maddox.sound;

import java.io.PrintStream;
import java.util.HashMap;

// Referenced classes of package com.maddox.sound:
//            BaseObject, SoundControl

public class ControlInfo extends com.maddox.sound.BaseObject
{

    protected ControlInfo(java.lang.String s, int i, java.lang.Class class1)
    {
        cc = i;
        cls = class1;
        map.put(s, this);
    }

    protected static com.maddox.sound.SoundControl get(java.lang.String s, int i)
    {
        if(i == 0)
            return null;
        com.maddox.sound.SoundControl soundcontrol;
        com.maddox.sound.ControlInfo controlinfo = (com.maddox.sound.ControlInfo)map.get(s);
        soundcontrol = (com.maddox.sound.SoundControl)controlinfo.cls.newInstance();
        soundcontrol.init(controlinfo.cc, i);
        return soundcontrol;
        java.lang.Exception exception;
        exception;
        java.lang.System.out.println("Cannot create sound control " + s);
        return null;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

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
    protected java.lang.Class cls;
    protected static java.util.HashMap map = new HashMap();

    static 
    {
        new ControlInfo("speed", 64, com.maddox.sound.BoundsControl.class);
        new ControlInfo("angle", 65, com.maddox.sound.BoundsControl.class);
        new ControlInfo("dist", 66, com.maddox.sound.BoundsControl.class);
        new ControlInfo("env", 67, com.maddox.sound.EnvControl.class);
        new ControlInfo("rpm", 100, com.maddox.sound.BoundsControl.class);
        new ControlInfo("damage", 101, com.maddox.sound.BoundsControl.class);
        new ControlInfo("diving", 102, com.maddox.sound.BoundsControl.class);
        new ControlInfo("flatter", 103, com.maddox.sound.BoundsControl.class);
        new ControlInfo("brake", 104, com.maddox.sound.BoundsControl.class);
        new ControlInfo("gear", 105, com.maddox.sound.BoundsControl.class);
        new ControlInfo("start", 106, com.maddox.sound.BoundsControl.class);
        new ControlInfo("load", 107, com.maddox.sound.BoundsControl.class);
        new ControlInfo("motorld", 108, com.maddox.sound.BoundsControl.class);
        new ControlInfo("propld", 109, com.maddox.sound.BoundsControl.class);
        new ControlInfo("door", 110, com.maddox.sound.BoundsControl.class);
        new ControlInfo("relspeed", 111, com.maddox.sound.BoundsControl.class);
        new ControlInfo("mostage", 112, com.maddox.sound.BoundsControl.class);
        new ControlInfo("bombspeed", 200, com.maddox.sound.BoundsControl.class);
        new ControlInfo("bombtime", 201, com.maddox.sound.BoundsControl.class);
        new ControlInfo("massa", 202, com.maddox.sound.BoundsControl.class);
        new ControlInfo("mod", 1000, com.maddox.sound.ModControl.class);
    }
}
