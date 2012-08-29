// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FreeFlyXYZ.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Orient;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Time;

public class FreeFlyXYZ
{
    class FreeInterpolate extends com.maddox.il2.engine.Interpolate
    {

        public void begin()
        {
        }

        public boolean tick()
        {
            if(!bActive)
            {
                return false;
            } else
            {
                float f = com.maddox.rts.Time.tickLenFs();
                actor.pos.getAbs(P, O);
                float f1 = O.azimut() + vMul * vAzimut * speedAzimut * f;
                P.x += (double)vMul * v.x * speedXYZ * (double)f;
                P.y += (double)vMul * v.y * speedXYZ * (double)f;
                P.z += (double)vMul * v.z * speedXYZ * (double)f;
                O.set(f1, O.tangage(), O.kren());
                O.wrap();
                actor.pos.setAbs(P, O);
                return true;
            }
        }

        public boolean bActive;

        FreeInterpolate()
        {
            bActive = true;
        }
    }


    float DEG2RAD(float f)
    {
        return f * 0.01745329F;
    }

    private void initHotKeys()
    {
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "SpeedMul") {

            public void begin()
            {
                vMul = speedMul;
            }

            public void end()
            {
                vMul = 1.0F;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "A-") {

            public void begin()
            {
                vAzimut+= = -1F;
            }

            public void end()
            {
                vAzimut-= = -1F;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "A+") {

            public void begin()
            {
                vAzimut+= = 1.0F;
            }

            public void end()
            {
                vAzimut-= = 1.0F;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "X-") {

            public void begin()
            {
                v.x += -1D;
            }

            public void end()
            {
                v.x -= -1D;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "X+") {

            public void begin()
            {
                v.x++;
            }

            public void end()
            {
                v.x--;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "Y-") {

            public void begin()
            {
                v.y += -1D;
            }

            public void end()
            {
                v.y -= -1D;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "Y+") {

            public void begin()
            {
                v.y++;
            }

            public void end()
            {
                v.y--;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "Z-") {

            public void begin()
            {
                v.z += -1D;
            }

            public void end()
            {
                v.z -= -1D;
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "Z+") {

            public void begin()
            {
                v.z++;
            }

            public void end()
            {
                v.z--;
            }

        }
);
    }

    public static void setActor(com.maddox.il2.engine.Actor actor)
    {
        adapter._setActor(actor);
    }

    private void _setActor(com.maddox.il2.engine.Actor actor)
    {
        if(interpolator != null)
        {
            if(interpolator.actor == actor)
            {
                interpolator.bActive = true;
                return;
            }
            interpolator.bActive = false;
            interpolator = null;
        }
        if(com.maddox.il2.engine.Actor.isValid(actor))
        {
            interpolator = new FreeInterpolate();
            actor.interpPut(interpolator, null, com.maddox.rts.Time.current(), null);
        }
    }

    public static com.maddox.il2.engine.Actor getActor(com.maddox.il2.engine.Actor actor)
    {
        return adapter._getActor();
    }

    private com.maddox.il2.engine.Actor _getActor()
    {
        if(interpolator != null && interpolator.bActive)
            return interpolator.actor;
        else
            return null;
    }

    public static java.lang.String environment()
    {
        return adapter._environment();
    }

    private java.lang.String _environment()
    {
        return envName;
    }

    private FreeFlyXYZ(java.lang.String s)
    {
        interpolator = null;
        speedXYZ = 10D;
        speedAzimut = 10F;
        speedMul = 2.0F;
        v = new Point3d();
        vAzimut = 0.0F;
        vMul = 1.0F;
        P = new Point3d();
        O = new Orient();
        adapter = this;
        envName = s;
        com.maddox.rts.HotKeyEnv.fromIni(envName, com.maddox.il2.engine.Config.cur.ini, envName);
        java.lang.String s1 = envName + " Config";
        speedXYZ = com.maddox.il2.engine.Config.cur.ini.get(s1, "SpeedXYZ", (float)speedXYZ);
        speedAzimut = com.maddox.il2.engine.Config.cur.ini.get(s1, "SpeedAzimut", speedAzimut);
        speedMul = com.maddox.il2.engine.Config.cur.ini.get(s1, "SpeedMul", speedMul);
        initHotKeys();
    }

    public static void initSave()
    {
        adapter._initSave();
    }

    private void _initSave()
    {
        java.lang.String s = envName + " Config";
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "SpeedXYZ", java.lang.Float.toString((float)speedXYZ));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "SpeedAzimut", java.lang.Float.toString(speedAzimut));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "SpeedMul", java.lang.Float.toString(speedMul));
    }

    public static com.maddox.il2.engine.hotkey.FreeFlyXYZ adapter()
    {
        return adapter;
    }

    public static void init(java.lang.String s)
    {
        if(adapter == null)
            new FreeFlyXYZ(s);
    }

    private com.maddox.il2.engine.hotkey.FreeInterpolate interpolator;
    private java.lang.String envName;
    public double speedXYZ;
    public float speedAzimut;
    public float speedMul;
    private com.maddox.JGP.Point3d v;
    private float vAzimut;
    private float vMul;
    private com.maddox.JGP.Point3d P;
    private com.maddox.il2.engine.Orient O;
    private static com.maddox.il2.engine.hotkey.FreeFlyXYZ adapter = null;









}
