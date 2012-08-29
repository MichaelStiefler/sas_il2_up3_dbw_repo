// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookView.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorFilter;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.objects.ActorViewPoint;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.HotKeyCmdEnv;
import com.maddox.rts.HotKeyCmdMouseMove;
import com.maddox.rts.HotKeyEnv;
import com.maddox.rts.IniFile;
import com.maddox.rts.Time;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class HookView extends com.maddox.il2.engine.HookRender
{
    static class ClipFilter
        implements com.maddox.il2.engine.ActorFilter
    {

        public boolean isUse(com.maddox.il2.engine.Actor actor, double d)
        {
            return actor instanceof com.maddox.il2.objects.ships.BigshipGeneric;
        }

        ClipFilter()
        {
        }
    }


    public boolean getFollow()
    {
        return bFollow;
    }

    public void setFollow(boolean flag)
    {
        bFollow = flag;
    }

    protected boolean isUseCommon()
    {
        return useFlags != 0;
    }

    protected void useCommon(int i, boolean flag)
    {
        if(bUse)
            useFlags |= i;
        else
            useFlags &= ~i;
    }

    protected static float minLen()
    {
        if(_visibleR > 0.0F)
            return _visibleR + 1.5F;
        else
            return _minLen;
    }

    public static float defaultLen()
    {
        if(_visibleR > 0.0F)
            return _visibleR * 3F;
        else
            return _defaultLen;
    }

    protected static float maxLen()
    {
        return _maxLen;
    }

    public float len()
    {
        return len;
    }

    public void resetGame()
    {
        lastBaseActor = null;
        _visibleR = -1F;
        _Azimut = Azimut = 0.0F;
        _Tangage = Tangage = 0.0F;
        timeViewSet = -2000L;
    }

    public void saveRecordedStates(java.io.PrintWriter printwriter)
        throws java.lang.Exception
    {
        printwriter.println(len);
        printwriter.println(Azimut);
        printwriter.println(_Azimut);
        printwriter.println(Tangage);
        printwriter.println(_Tangage);
        printwriter.println(o.azimut());
        printwriter.println(o.tangage());
        printwriter.println(koofAzimut);
        printwriter.println(koofTangage);
        printwriter.println(koofLen);
        printwriter.println(_minLen);
        printwriter.println(_defaultLen);
        printwriter.println(_maxLen);
        printwriter.println(koofSpeed);
    }

    public void loadRecordedStates(java.io.BufferedReader bufferedreader)
        throws java.lang.Exception
    {
        len = java.lang.Float.parseFloat(bufferedreader.readLine());
        Azimut = java.lang.Float.parseFloat(bufferedreader.readLine());
        _Azimut = java.lang.Float.parseFloat(bufferedreader.readLine());
        Tangage = java.lang.Float.parseFloat(bufferedreader.readLine());
        _Tangage = java.lang.Float.parseFloat(bufferedreader.readLine());
        o.set(java.lang.Float.parseFloat(bufferedreader.readLine()), java.lang.Float.parseFloat(bufferedreader.readLine()), 0.0F);
        koofAzimut = java.lang.Float.parseFloat(bufferedreader.readLine());
        koofTangage = java.lang.Float.parseFloat(bufferedreader.readLine());
        koofLen = java.lang.Float.parseFloat(bufferedreader.readLine());
        _minLen = java.lang.Float.parseFloat(bufferedreader.readLine());
        _defaultLen = java.lang.Float.parseFloat(bufferedreader.readLine());
        _maxLen = java.lang.Float.parseFloat(bufferedreader.readLine());
        koofSpeed = java.lang.Float.parseFloat(bufferedreader.readLine());
    }

    public void reset()
    {
        timeViewSet = -2000L;
        if(com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd)
        {
            return;
        } else
        {
            set(lastBaseActor, com.maddox.il2.engine.hotkey.HookView.defaultLen(), 0.0F, 0.0F);
            return;
        }
    }

    public void set(com.maddox.il2.engine.Actor actor, float f, float f1)
    {
        set(actor, com.maddox.il2.engine.hotkey.HookView.defaultLen(), f, f1);
    }

    public void set(com.maddox.il2.engine.Actor actor, float f, float f1, float f2)
    {
        lastBaseActor = actor;
        _visibleR = -1F;
        if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.engine.ActorMesh))
            _visibleR = ((com.maddox.il2.engine.ActorMesh)actor).mesh().visibilityR();
        o.set(f1, f2, 0.0F);
        _Azimut = Azimut = 0.0F;
        _Tangage = Tangage = 0.0F;
        prevTime = com.maddox.rts.Time.real();
        com.maddox.il2.engine.hotkey.HookView _tmp = this;
        len = f;
        if(camera != null)
        {
            com.maddox.il2.engine.Actor actor1 = camera.pos.base();
            if(actor1 != null)
            {
                actor1.pos.getAbs(o);
                o.increment(f1, f2, 0.0F);
                o.set(o.azimut(), o.tangage(), 0.0F);
            }
            if(bUse)
                camera.pos.inValidate(true);
        }
        o.wrap();
    }

    private float bvalue(float f, float f1, long l)
    {
        float f2 = (koofSpeed * (float)l) / 30F;
        if(f == f1)
            return f;
        if(f > f1)
            if(f < f1 + f2)
                return f;
            else
                return f1 + f2;
        if(f > f1 - f2)
            return f;
        else
            return f1 - f2;
    }

    public boolean computeRenderPos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        long l = com.maddox.rts.Time.currentReal();
        if(l != prevTime)
        {
            long l1 = l - prevTime;
            prevTime = l;
            if(_Azimut != Azimut || _Tangage != Tangage)
            {
                Azimut = bvalue(_Azimut, Azimut, l1);
                Tangage = bvalue(_Tangage, Tangage, l1);
                loc.get(o);
                float f = o.azimut() + Azimut;
                o.set(f, Tangage, 0.0F);
                o.wrap360();
            }
            if(_Azimut == Azimut && (Azimut > 180F || Azimut < -180F))
            {
                Azimut %= 360F;
                if(Azimut > 180F)
                    Azimut -= 360F;
                else
                if(Azimut < -180F)
                    Azimut += 360F;
                _Azimut = Azimut;
            }
            if(_Tangage == Tangage && (Tangage > 180F || Tangage < -180F))
            {
                Tangage %= 360F;
                if(Tangage > 180F)
                    Tangage -= 360F;
                else
                if(Tangage < -180F)
                    Tangage += 360F;
                _Tangage = Tangage;
            }
        }
        computePos(actor, loc, loc1);
        return true;
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        loc.get(pAbs, oAbs);
        if(bUse)
        {
            if(lastBaseActor != actor)
            {
                lastBaseActor = actor;
                _visibleR = -1F;
                if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.engine.ActorMesh))
                    _visibleR = ((com.maddox.il2.engine.ActorMesh)actor).mesh().visibilityR();
            }
            p.set(-len, 0.0D, 0.0D);
            if(bFollow)
                o.set(oAbs.getAzimut(), o.getTangage(), o.getKren());
            o.transform(p);
            pAbs.add(p);
            if(bClipOnLand)
            {
                double d = com.maddox.il2.engine.Engine.land().HQ_Air(pAbs.x, pAbs.y) + 2D;
                if(pAbs.z < d)
                    pAbs.z = d;
                pClipZ1.set(pAbs);
                pClipZ2.set(pAbs);
                pClipZ1.z -= 2D;
                pClipZ2.z += 42D;
                com.maddox.il2.engine.Actor actor1 = com.maddox.il2.engine.Engine.collideEnv().getLine(pClipZ2, pClipZ1, false, clipFilter, pClipRes);
                if(com.maddox.il2.engine.Actor.isValid(actor1) && pAbs.z < pClipRes.z + 2D)
                    pAbs.z = pClipRes.z + 2D;
            }
            loc1.set(pAbs, o);
        } else
        {
            loc1.set(pAbs, oAbs);
        }
    }

    public void setCamera(com.maddox.il2.engine.Actor actor)
    {
        camera = actor;
    }

    public void use(boolean flag)
    {
        bUse = flag;
        if(camera != null)
            camera.pos.inValidate(true);
        useCommon(1, bUse);
    }

    public boolean isUse()
    {
        return bUse;
    }

    public static void useMouse(boolean flag)
    {
        bUseMouse = flag;
    }

    public static boolean isUseMouse()
    {
        return bUseMouse;
    }

    public boolean clipOnLand(boolean flag)
    {
        boolean flag1 = bClipOnLand;
        bClipOnLand = flag;
        return flag1;
    }

    protected void clipLen(com.maddox.il2.engine.Actor actor)
    {
        if(len < com.maddox.il2.engine.hotkey.HookView.minLen())
            if(com.maddox.il2.engine.Actor.isValid(actor) && (actor instanceof com.maddox.il2.objects.ActorViewPoint))
            {
                if(len < -com.maddox.il2.engine.hotkey.HookView.maxLen())
                    len = -com.maddox.il2.engine.hotkey.HookView.maxLen();
            } else
            {
                len = com.maddox.il2.engine.hotkey.HookView.minLen();
            }
        if(len > com.maddox.il2.engine.hotkey.HookView.maxLen())
            len = com.maddox.il2.engine.hotkey.HookView.maxLen();
    }

    protected void mouseMove(int i, int j, int k)
    {
        if(bUseMouse && isUseCommon())
        {
            if(bChangeLen)
            {
                len += (float)j * koofLen;
                clipLen(lastBaseActor);
            } else
            {
                if(com.maddox.rts.Time.real() < timeViewSet + 1000L)
                    return;
                if(bFollow)
                    i = 0;
                if(bUse)
                {
                    o.set(o.azimut() + (float)i * koofAzimut, o.tangage() + (float)j * koofTangage, 0.0F);
                    o.wrap360();
                }
            }
            if(com.maddox.il2.engine.Actor.isValid(camera))
                camera.pos.inValidate(true);
            Azimut = _Azimut;
            Tangage = _Tangage;
        }
    }

    public void viewSet(float f, float f1)
    {
        if(!bUseMouse && !isUseCommon())
            return;
        timeViewSet = com.maddox.rts.Time.real();
        f %= 360F;
        if(f > 180F)
            f -= 360F;
        else
        if(f < -180F)
            f += 360F;
        f1 %= 360F;
        if(f1 > 180F)
            f1 -= 360F;
        else
        if(f1 < -180F)
            f1 += 360F;
        if(bFollow)
        {
            o.set(o.azimut(), f1, 0.0F);
            o.wrap360();
        } else
        {
            Azimut = _Azimut = f;
            Tangage = _Tangage = f1;
            o.set(Azimut, Tangage, 0.0F);
        }
        if(com.maddox.il2.engine.Actor.isValid(camera))
            camera.pos.inValidate(true);
    }

    public void snapSet(float f, float f1)
    {
        if(!bUse || !bUseMouse || !isUseCommon())
            return;
        if(bFollow)
            f = 0.0F;
        _Azimut = 45F * f;
        _Tangage = 44F * f1;
        if(camera != null)
        {
            com.maddox.il2.engine.Actor actor = camera.pos.base();
            if(actor != null)
            {
                Azimut = (o.azimut() - actor.pos.getAbsOrient().azimut()) % 360F;
                if(Azimut > _Azimut)
                    for(; java.lang.Math.abs(Azimut - _Azimut) > 180F; Azimut -= 360F);
                else
                if(Azimut < _Azimut)
                    for(; java.lang.Math.abs(Azimut - _Azimut) > 180F; Azimut += 360F);
                Tangage = o.tangage() % 360F;
                if(Tangage > _Tangage)
                    for(; java.lang.Math.abs(Tangage - _Tangage) > 180F; Tangage -= 360F);
                else
                if(Tangage < _Tangage)
                    for(; java.lang.Math.abs(Tangage - _Tangage) > 180F; Tangage += 360F);
                camera.pos.inValidate(true);
            }
        }
    }

    public void panSet(int i, int j)
    {
        if(!bUse || !bUseMouse || !isUseCommon())
            return;
        if(i == 0 && j == 0)
        {
            _Azimut = 0.0F;
            _Tangage = 0.0F;
        }
        _Azimut = (float)i * stepAzimut + _Azimut;
        if(bFollow)
            _Azimut = 0.0F;
        _Tangage = (float)j * stepTangage + _Tangage;
        if(camera != null)
        {
            com.maddox.il2.engine.Actor actor = camera.pos.base();
            if(actor != null)
            {
                Azimut = (o.azimut() - actor.pos.getAbsOrient().azimut()) % 360F;
                if(Azimut > _Azimut)
                    for(; java.lang.Math.abs(Azimut - _Azimut) > 180F; Azimut -= 360F);
                else
                if(Azimut < _Azimut)
                    for(; java.lang.Math.abs(Azimut - _Azimut) > 180F; Azimut += 360F);
                Tangage = o.tangage() % 360F;
                if(Tangage > _Tangage)
                    for(; java.lang.Math.abs(Tangage - _Tangage) > 180F; Tangage -= 360F);
                else
                if(Tangage < _Tangage)
                    for(; java.lang.Math.abs(Tangage - _Tangage) > 180F; Tangage += 360F);
                camera.pos.inValidate(true);
            }
        }
    }

    private void initHotKeys(java.lang.String s)
    {
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmdMouseMove(true, "Move") {

            public void move(int i, int j, int k)
            {
                mouseMove(i, j, k);
            }

            public void created()
            {
                setRecordId(27);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "Reset") {

            public void begin()
            {
                if(isUseCommon())
                    reset();
            }

            public void created()
            {
                setRecordId(28);
            }

        }
);
        com.maddox.rts.HotKeyCmdEnv.addCmd(s, new com.maddox.rts.HotKeyCmd(true, "Len") {

            public void begin()
            {
                if(isUseCommon())
                    com.maddox.il2.engine.hotkey.HookView.bChangeLen = true;
            }

            public void end()
            {
                if(isUseCommon())
                    com.maddox.il2.engine.hotkey.HookView.bChangeLen = false;
            }

            public void created()
            {
                setRecordId(29);
            }

        }
);
    }

    public static void loadConfig()
    {
        koofAzimut = com.maddox.il2.engine.Config.cur.ini.get(sectConf, "AzimutSpeed", koofAzimut, 0.01F, 1.0F);
        koofTangage = com.maddox.il2.engine.Config.cur.ini.get(sectConf, "TangageSpeed", koofTangage, 0.01F, 1.0F);
        koofLen = com.maddox.il2.engine.Config.cur.ini.get(sectConf, "LenSpeed", koofLen, 0.1F, 10F);
        _minLen = com.maddox.il2.engine.Config.cur.ini.get(sectConf, "MinLen", _minLen, 1.0F, 20F);
        _defaultLen = com.maddox.il2.engine.Config.cur.ini.get(sectConf, "DefaultLen", _defaultLen, 1.0F, 100F);
        _maxLen = com.maddox.il2.engine.Config.cur.ini.get(sectConf, "MaxLen", _maxLen, 1.0F, 50000F);
        if(_defaultLen < _minLen)
            _defaultLen = _minLen;
        if(_maxLen < _defaultLen)
            _maxLen = _defaultLen;
        koofSpeed = com.maddox.il2.engine.Config.cur.ini.get(sectConf, "Speed", koofSpeed, 0.1F, 100F);
    }

    public HookView(java.lang.String s)
    {
        camera = null;
        bUse = false;
        bClipOnLand = true;
        o = new Orientation();
        p = new Point3d();
        pAbs = new Point3d();
        oAbs = new Orient();
        bFollow = false;
        timeViewSet = -2000L;
        com.maddox.rts.HotKeyEnv.fromIni(s, com.maddox.il2.engine.Config.cur.ini, s);
        sectConf = s + " Config";
        com.maddox.il2.engine.hotkey.HookView.loadConfig();
        initHotKeys(s);
        current = this;
    }

    protected HookView()
    {
        camera = null;
        bUse = false;
        bClipOnLand = true;
        o = new Orientation();
        p = new Point3d();
        pAbs = new Point3d();
        oAbs = new Orient();
        bFollow = false;
        timeViewSet = -2000L;
    }

    public static com.maddox.il2.engine.hotkey.HookView cur()
    {
        return current;
    }

    protected static com.maddox.il2.engine.Actor lastBaseActor = null;
    protected com.maddox.il2.engine.Actor camera;
    protected boolean bUse;
    protected boolean bClipOnLand;
    protected static final int EXT = 1;
    protected static boolean bUseMouse = true;
    protected static boolean bChangeLen = false;
    protected static int useFlags = 0;
    private static float _minLen = 2.0F;
    private static float _defaultLen;
    private static float _maxLen = 500F;
    protected static float _visibleR = -1F;
    protected static float len;
    protected static float stepAzimut = 45F;
    protected static float stepTangage = 30F;
    protected static float maxAzimut = 179F;
    protected static float maxTangage = 89F;
    protected static float minTangage = -89F;
    protected static float Azimut = 0.0F;
    protected static float Tangage = 0.0F;
    protected static float _Azimut = 0.0F;
    protected static float _Tangage = 0.0F;
    protected static long prevTime = 0L;
    protected static float koofAzimut = 0.1F;
    protected static float koofTangage = 0.1F;
    protected static float koofLen = 1.0F;
    protected static float koofSpeed = 6F;
    protected com.maddox.il2.engine.Orient o;
    protected com.maddox.JGP.Point3d p;
    protected com.maddox.JGP.Point3d pAbs;
    protected com.maddox.il2.engine.Orient oAbs;
    private boolean bFollow;
    private static com.maddox.JGP.Point3d pClipZ1 = new Point3d();
    private static com.maddox.JGP.Point3d pClipZ2 = new Point3d();
    private static com.maddox.JGP.Point3d pClipRes = new Point3d();
    static com.maddox.il2.engine.hotkey.ClipFilter clipFilter = new ClipFilter();
    private static final float Circ = 360F;
    private long timeViewSet;
    private static java.lang.String sectConf;
    public static com.maddox.il2.engine.hotkey.HookView current = null;

    static 
    {
        _defaultLen = 20F;
        len = _defaultLen;
    }
}
