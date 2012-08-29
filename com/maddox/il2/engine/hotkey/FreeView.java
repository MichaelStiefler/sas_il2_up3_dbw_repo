// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FreeView.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
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
import com.maddox.rts.Mouse;
import com.maddox.rts.MsgAddListener;
import com.maddox.rts.MsgMouseListener;
import com.maddox.rts.Time;

public class FreeView
    implements com.maddox.rts.MsgMouseListener
{
    class FreeInterpolate extends com.maddox.il2.engine.Interpolate
    {

        public void begin()
        {
            reset();
        }

        public boolean tick()
        {
            if(!bActive)
                return false;
            if(com.maddox.il2.engine.Actor.isValid(target))
            {
                target.pos.getAbs(pAbs);
                p.set(-len, 0.0D, 0.0D);
                o.transform(p);
                pAbs.add(p);
                double d = com.maddox.il2.engine.Engine.land().HQ(pAbs.x, pAbs.y) + 1.0D;
                if(pAbs.z < d)
                    pAbs.z = d;
                actor.pos.setAbs(pAbs, o);
            } else
            {
                actor.pos.setAbs(o);
            }
            return true;
        }

        public boolean bActive;

        FreeInterpolate()
        {
            bActive = true;
        }
    }


    public void resetGame()
    {
        target = null;
    }

    private void reset()
    {
        if(com.maddox.il2.engine.Actor.isValid(target))
        {
            target.pos.getAbs(oAbs);
            o.set(oAbs.azimut(), oAbs.tangage(), 0.0F);
        } else
        {
            o.set(0.0F, 0.0F, 0.0F);
        }
        len = defaultLen;
    }

    public void msgMouseButton(int i, boolean flag)
    {
    }

    public void msgMouseAbsMove(int i, int j, int k)
    {
    }

    public void msgMouseMove(int i, int j, int k)
    {
        if(bUseMouse)
            if(bChangeLen)
            {
                len += (float)j * koofLen;
                if(len < minLen)
                    len = minLen;
                if(len > maxLen)
                    len = maxLen;
            } else
            {
                o.set(o.azimut() + (float)i * koofAzimut, o.tangage() + (float)j * koofTangage, 0.0F);
            }
    }

    private void initHotKeys()
    {
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "Reset") {

            public void begin()
            {
                reset();
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(envName, new com.maddox.rts.HotKeyCmd(false, "Len") {

            public void begin()
            {
                bChangeLen = true;
            }

            public void end()
            {
                bChangeLen = false;
            }

        }
);
    }

    public static float len()
    {
        return adapter._len();
    }

    private float _len()
    {
        return len;
    }

    public static void setTarget(com.maddox.il2.engine.Actor actor)
    {
        adapter._setTarget(actor);
    }

    private void _setTarget(com.maddox.il2.engine.Actor actor)
    {
        target = actor;
    }

    public static com.maddox.il2.engine.Actor getTarget()
    {
        return adapter._getTarget();
    }

    private com.maddox.il2.engine.Actor _getTarget()
    {
        return target;
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

    public static com.maddox.il2.engine.Actor getActor()
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

    public static boolean useMouse(boolean flag)
    {
        return adapter._useMouse(flag);
    }

    private boolean _useMouse(boolean flag)
    {
        boolean flag1 = bUseMouse;
        bUseMouse = flag;
        return flag1;
    }

    public static java.lang.String environment()
    {
        return adapter._environment();
    }

    private java.lang.String _environment()
    {
        return envName;
    }

    private FreeView(java.lang.String s)
    {
        interpolator = null;
        koofAzimut = 0.25F;
        koofTangage = 0.25F;
        koofLen = 0.25F;
        minLen = 2.0F;
        defaultLen = 10F;
        maxLen = 500F;
        len = defaultLen;
        o = new Orientation();
        p = new Point3d();
        target = null;
        bUseMouse = true;
        pAbs = new Point3d();
        oAbs = new Orient();
        bChangeLen = false;
        adapter = this;
        com.maddox.rts.MsgAddListener.post(0, com.maddox.rts.Mouse.adapter(), this, null);
        envName = s;
        com.maddox.rts.HotKeyEnv.fromIni(envName, com.maddox.il2.engine.Config.cur.ini, envName);
        java.lang.String s1 = envName + " Config";
        koofAzimut = com.maddox.il2.engine.Config.cur.ini.get(s1, "AzimutSpeed", koofAzimut);
        koofTangage = com.maddox.il2.engine.Config.cur.ini.get(s1, "TangageSpeed", koofTangage);
        koofLen = com.maddox.il2.engine.Config.cur.ini.get(s1, "LenSpeed", koofLen);
        minLen = com.maddox.il2.engine.Config.cur.ini.get(s1, "MinLen", minLen);
        defaultLen = com.maddox.il2.engine.Config.cur.ini.get(s1, "DefaultLen", defaultLen);
        maxLen = com.maddox.il2.engine.Config.cur.ini.get(s1, "MaxLen", maxLen);
        initHotKeys();
    }

    public static void initSave()
    {
        adapter._initSave();
    }

    private void _initSave()
    {
        java.lang.String s = envName + " Config";
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "AzimutSpeed", java.lang.Float.toString(koofAzimut));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "TangageSpeed", java.lang.Float.toString(koofTangage));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "LenSpeed", java.lang.Float.toString(koofLen));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "MinLen", java.lang.Float.toString(minLen));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "DefaultLen", java.lang.Float.toString(defaultLen));
        com.maddox.il2.engine.Config.cur.ini.setValue(s, "MaxLen", java.lang.Float.toString(maxLen));
    }

    public static com.maddox.il2.engine.hotkey.FreeView adapter()
    {
        return adapter;
    }

    public static void init(java.lang.String s)
    {
        if(adapter == null)
            new FreeView(s);
    }

    private com.maddox.il2.engine.hotkey.FreeInterpolate interpolator;
    private java.lang.String envName;
    private float koofAzimut;
    private float koofTangage;
    private float koofLen;
    private float minLen;
    private float defaultLen;
    private float maxLen;
    private float len;
    private com.maddox.il2.engine.Orient o;
    private com.maddox.JGP.Point3d p;
    private com.maddox.il2.engine.Actor target;
    private boolean bUseMouse;
    private com.maddox.JGP.Point3d pAbs;
    private com.maddox.il2.engine.Orient oAbs;
    private boolean bChangeLen;
    private static com.maddox.il2.engine.hotkey.FreeView adapter = null;








}
