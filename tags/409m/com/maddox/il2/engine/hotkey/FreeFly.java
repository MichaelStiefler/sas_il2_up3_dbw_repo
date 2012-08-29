// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FreeFly.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Message;
import com.maddox.rts.Mouse;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgMouseListener;
import com.maddox.rts.Time;

public class FreeFly
    implements com.maddox.rts.MsgMouseListener
{
    class ActorInterpolate extends com.maddox.il2.engine.Actor
    {

        public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
        {
            return this;
        }

        protected void createActorHashCode()
        {
            makeActorRealHashCode();
        }

        public ActorInterpolate()
        {
            if(bRealTime)
                flags |= 0x2000;
            flags |= 0x4000;
        }
    }

    class FreeInterpolate extends com.maddox.il2.engine.Interpolate
    {

        public void begin()
        {
            view.set(0.0F, 0.0F, 0.0F);
            speed = 0.0D;
            prevTime = bRealTime ? com.maddox.rts.Time.currentReal() : com.maddox.rts.Time.current();
        }

        public boolean tick()
        {
            long l = bRealTime ? com.maddox.rts.Time.currentReal() : com.maddox.rts.Time.current();
            if(l < prevTime)
                prevTime = l;
            if(l == prevTime)
                return true;
            float f = (float)(l - prevTime) * 0.001F;
            prevTime = l;
            if(!com.maddox.il2.engine.Actor.isValid(target))
                return true;
            speed+= = doSpeed * aSpeed * (double)f;
            if(speed < 0.0D)
                speed = 0.0D;
            if(speed > maxSpeed)
                speed = maxSpeed;
            target.pos.getAbs(aP, aO);
            double d = speed * (double)f;
            double d1 = java.lang.Math.cos(DEG2RAD(aO.tangage())) * d;
            aP.y -= java.lang.Math.sin(DEG2RAD(aO.azimut())) * d1;
            aP.x += java.lang.Math.cos(DEG2RAD(aO.azimut())) * d1;
            aP.z += java.lang.Math.sin(DEG2RAD(aO.tangage())) * d;
            if(bClipOnLand)
            {
                double d2 = com.maddox.il2.engine.Engine.land().HQ(aP.x, aP.y) + 1.0D;
                if(aP.z < d2)
                    aP.z = d2;
            }
            aO.increment((float)(vO.x * speedO.x * (double)f), (float)(vO.y * speedO.y * (double)f), (float)(vO.z * speedO.z * (double)f));
            o.set(aO);
            if(bViewReset)
            {
                view.set(0.0F, 0.0F, 0.0F);
                bViewReset = false;
            } else
            if(target instanceof com.maddox.il2.engine.Camera)
                o.set(view.x * koofAzimutView, view.y * koofTangageView, 0.0F);
            target.pos.setAbs(aP, o);
            return true;
        }

        private long prevTime;

        FreeInterpolate()
        {
        }
    }


    float DEG2RAD(float f)
    {
        return f * 0.01745329F;
    }

    public void resetGame()
    {
        target = null;
    }

    public void msgMouseButton(int i, boolean flag)
    {
    }

    public void msgMouseAbsMove(int i, int j, int k)
    {
    }

    public void msgMouseMove(int i, int j, int k)
    {
        if(com.maddox.il2.engine.Actor.isValid(target) && (target instanceof com.maddox.il2.engine.Camera))
        {
            view.x += i;
            view.y += j;
        }
    }

    private void initHotKeys()
    {
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "ResetView") {

            public void begin()
            {
                bViewReset = true;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "Azimut-") {

            public void begin()
            {
                vO.x += -1D;
            }

            public void end()
            {
                vO.x -= -1D;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "Azimut+") {

            public void begin()
            {
                vO.x++;
            }

            public void end()
            {
                vO.x--;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "Tangage-") {

            public void begin()
            {
                vO.y += -1D;
            }

            public void end()
            {
                vO.y -= -1D;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "Tangage+") {

            public void begin()
            {
                vO.y++;
            }

            public void end()
            {
                vO.y--;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "Kren-") {

            public void begin()
            {
                vO.z += -1D;
            }

            public void end()
            {
                vO.z -= -1D;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "Kren+") {

            public void begin()
            {
                vO.z++;
            }

            public void end()
            {
                vO.z--;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "Speed-") {

            public void begin()
            {
                doSpeed+= = -1D;
            }

            public void end()
            {
                doSpeed-= = -1D;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(bRealTime, "Speed+") {

            public void begin()
            {
                doSpeed+= = 1.0D;
            }

            public void end()
            {
                doSpeed-= = 1.0D;
            }

        }
);
    }

    public static double speed()
    {
        return adapter._speed();
    }

    private double _speed()
    {
        return speed;
    }

    public static boolean clipOnLand(boolean flag)
    {
        return adapter._clipOnLand(flag);
    }

    private boolean _clipOnLand(boolean flag)
    {
        boolean flag1 = bClipOnLand;
        bClipOnLand = flag;
        return flag1;
    }

    public static void setActor(com.maddox.il2.engine.Actor actor)
    {
        adapter._setActor(actor);
    }

    private void _setActor(com.maddox.il2.engine.Actor actor)
    {
        target = actor;
    }

    public static com.maddox.il2.engine.Actor getActor(com.maddox.il2.engine.Actor actor)
    {
        return adapter._getActor();
    }

    private com.maddox.il2.engine.Actor _getActor()
    {
        return target;
    }

    public static java.lang.String environment()
    {
        return adapter._environment();
    }

    private java.lang.String _environment()
    {
        return envName;
    }

    private FreeFly(java.lang.String s)
    {
        bRealTime = false;
        vO = new Point3d(0.0D, 0.0D, 0.0D);
        speedO = new Point3d(10D, 10D, 10D);
        maxSpeed = 10D;
        aSpeed = 3D;
        doSpeed = 0.0D;
        speed = 0.0D;
        koofAzimutView = 0.25F;
        koofTangageView = 0.25F;
        bViewReset = false;
        view = new Point3f();
        bClipOnLand = true;
        aP = new Point3d();
        aO = new Orientation();
        o = new Orient();
        adapter = this;
        envName = s;
        com.maddox.rts.HotKeyEnv.fromIni(envName, com.maddox.il2.engine.Config.cur.ini, envName);
        java.lang.String s1 = envName + " Config";
        bRealTime = com.maddox.il2.engine.Config.cur.ini.get(s1, "RealTime", bRealTime);
        koofAzimutView = com.maddox.il2.engine.Config.cur.ini.get(s1, "AzimutView", koofAzimutView);
        koofTangageView = com.maddox.il2.engine.Config.cur.ini.get(s1, "TangageView", koofTangageView);
        speedO.x = com.maddox.il2.engine.Config.cur.ini.get(s1, "SpeedAzimut", (float)speedO.x);
        speedO.y = com.maddox.il2.engine.Config.cur.ini.get(s1, "SpeedTangage", (float)speedO.y);
        speedO.z = com.maddox.il2.engine.Config.cur.ini.get(s1, "SpeedKren", (float)speedO.z);
        maxSpeed = com.maddox.il2.engine.Config.cur.ini.get(s1, "MaxSpeed", (float)maxSpeed);
        aSpeed = com.maddox.il2.engine.Config.cur.ini.get(s1, "Acselerate", (float)aSpeed);
        com.maddox.rts.MsgAddListener.post(bRealTime ? 64 : 0, com.maddox.rts.Mouse.adapter(), this, null);
        initHotKeys();
        aInterpolator = new ActorInterpolate();
        aInterpolator.interpPut(new FreeInterpolate(), null, bRealTime ? com.maddox.rts.Time.currentReal() : com.maddox.rts.Time.current(), null);
    }

    public static void initSave()
    {
        adapter._initSave();
    }

    private void _initSave()
    {
        java.lang.String s = envName + " Config";
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "AzimutView", java.lang.Float.toString(koofAzimutView));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "TangageView", java.lang.Float.toString(koofTangageView));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "SpeedAzimut", java.lang.Float.toString((float)speedO.x));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "SpeedTangage", java.lang.Float.toString((float)speedO.y));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "SpeedKren", java.lang.Float.toString((float)speedO.z));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "MaxSpeed", java.lang.Float.toString((float)maxSpeed));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "Acselerate", java.lang.Float.toString((float)aSpeed));
    }

    public static com.maddox.il2.engine.hotkey.FreeFly adapter()
    {
        return adapter;
    }

    public static void init(java.lang.String s)
    {
        if(adapter == null)
            new FreeFly(s);
    }

    private com.maddox.il2.engine.hotkey.ActorInterpolate aInterpolator;
    private com.maddox.il2.engine.Actor target;
    private java.lang.String envName;
    private boolean bRealTime;
    private com.maddox.JGP.Point3d vO;
    public com.maddox.JGP.Point3d speedO;
    public double maxSpeed;
    public double aSpeed;
    private double doSpeed;
    private double speed;
    public float koofAzimutView;
    public float koofTangageView;
    public boolean bViewReset;
    private com.maddox.JGP.Point3f view;
    private boolean bClipOnLand;
    private com.maddox.JGP.Point3d aP;
    private com.maddox.il2.engine.Orient aO;
    private com.maddox.il2.engine.Orient o;
    private static com.maddox.il2.engine.hotkey.FreeFly adapter = null;















}
