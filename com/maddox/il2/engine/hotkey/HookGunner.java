// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookGunner.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Hook;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Time;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.engine.hotkey:
//            HookView

public class HookGunner extends com.maddox.il2.engine.HookRender
{
    public static interface Move
    {

        public abstract void moveGun(com.maddox.il2.engine.Orient orient);

        public abstract void clipAnglesGun(com.maddox.il2.engine.Orient orient);

        public abstract com.maddox.il2.engine.Hook getHookCameraGun();

        public abstract void doGunFire(boolean flag);
    }


    public static void resetGame()
    {
        for(int i = 0; i < all.size(); i++)
        {
            com.maddox.il2.engine.hotkey.HookGunner hookgunner = (com.maddox.il2.engine.hotkey.HookGunner)all.get(i);
            hookgunner.mover = null;
            hookgunner.target = null;
            hookgunner.target2 = null;
            hookgunner.bUse = false;
            hookgunner.oGunMove.set(0.0F, 0.0F, 0.0F);
        }

        all.clear();
        current = null;
    }

    public com.maddox.il2.engine.Orient getGunMove()
    {
        return oGunMove;
    }

    public void resetMove(float f, float f1)
    {
        oGunMove.set(f, f1, 0.0F);
        if(mover != null)
        {
            mover.clipAnglesGun(oGunMove);
            mover.moveGun(oGunMove);
        }
    }

    public void setMover(com.maddox.il2.engine.hotkey.Move move)
    {
        mover = move;
    }

    private void _reset()
    {
        if(!com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd)
        {
            _Azimut = Azimut = 0.0F;
            _Tangage = Tangage = 0.0F;
        }
        Px = Py = Pz = 0.0F;
        L.set(0.0D, 0.0D, 0.0D, 0.0F, 0.0F, 0.0F);
        tstamp = -1L;
        roolTime = -1L;
    }

    public void reset()
    {
        _reset();
    }

    private void headRoll(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        long l = roolTime - tstamp;
        if(l >= 0L && l < 50L)
            return;
        roolTime = tstamp;
        float f = (float)(-(aircraft.FM.getAccel().y + (double)aircraft.FM.getRollAcceleration()) * 0.05000000074505806D);
        float f1 = (float)(-aircraft.FM.getAccel().x * 0.10000000149011612D);
        float f2 = (float)(-aircraft.FM.getAccel().z * 0.019999999552965164D);
        if(f1 >= 1.0F || f1 <= -1F)
            if(f1 < -1F)
                f1 = -1F;
            else
            if(f1 > 1.0F)
                f1 = 1.0F;
            else
                f1 = 0.0F;
        if(f >= 1.0F || f <= -1F)
            if(f < -1F)
                f = -1F;
            else
            if(f > 1.0F)
                f = 1.0F;
            else
                f = 0.0F;
        if(f2 >= 1.0F || f2 <= -1F)
            if(f2 < -1F)
                f2 = -1F;
            else
            if(f2 > 1.0F)
                f2 = 1.0F;
            else
                f2 = 0.0F;
        L.set(Px += (f1 * 0.015F - Px) * 0.4F, Py += (f * 0.015F - Py) * 0.4F, Pz += (f2 * 0.015F - Pz) * 0.4F, 0.0F, 0.0F, 0.0F);
    }

    private float bvalue(float f, float f1, long l)
    {
        float f2 = (com.maddox.il2.engine.hotkey.HookView.koofSpeed * (float)l) / 30F;
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
            }
        }
        computePos(actor, loc, loc1);
        return true;
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        if(bUse && mover != null)
        {
            if(com.maddox.il2.ai.World.cur().diffCur.Head_Shake)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(com.maddox.il2.engine.Actor.isValid(aircraft))
                {
                    long l = com.maddox.rts.Time.current();
                    if(l != tstamp && !aircraft.FM.Gears.onGround())
                    {
                        tstamp = l;
                        headRoll(aircraft);
                    }
                }
            }
            loc1.add(L, loc1);
            o.set(Azimut, Tangage, 0.0F);
            loc1.getOrient().add(o);
            mover.getHookCameraGun().computePos(actor, loc, loc1);
        } else
        {
            loc1.set(loc);
        }
    }

    public void setTarget(com.maddox.il2.engine.Actor actor)
    {
        target = actor;
    }

    public void setTarget2(com.maddox.il2.engine.Actor actor)
    {
        target2 = actor;
    }

    public boolean use(boolean flag)
    {
        boolean flag1 = bUse;
        bUse = flag;
        if(com.maddox.il2.engine.Actor.isValid(target))
            target.pos.inValidate(true);
        if(com.maddox.il2.engine.Actor.isValid(target2))
            target2.pos.inValidate(true);
        if(bUse)
            current = this;
        else
            current = null;
        return flag1;
    }

    public void gunFire(boolean flag)
    {
        if(mover == null)
        {
            return;
        } else
        {
            mover.doGunFire(flag);
            return;
        }
    }

    public void mouseMove(int i, int j, int k)
    {
        if(mover == null)
            return;
        oGunMove.set(oGunMove.azimut() - (float)i * com.maddox.il2.engine.hotkey.HookView.koofAzimut, oGunMove.tangage() + (float)j * com.maddox.il2.engine.hotkey.HookView.koofTangage, 0.0F);
        oGunMove.wrap();
        mover.clipAnglesGun(oGunMove);
        mover.moveGun(oGunMove);
        if(com.maddox.il2.engine.Actor.isValid(target))
            target.pos.inValidate(true);
        if(com.maddox.il2.engine.Actor.isValid(target2))
            target2.pos.inValidate(true);
    }

    public static void doSnapSet(float f, float f1)
    {
        for(int i = 0; i < all.size(); i++)
        {
            com.maddox.il2.engine.hotkey.HookGunner hookgunner = (com.maddox.il2.engine.hotkey.HookGunner)all.get(i);
            hookgunner.snapSet(f, f1);
        }

    }

    public void snapSet(float f, float f1)
    {
        if(!bUse)
            return;
        _Azimut = 45F * f;
        _Tangage = 44F * f1;
        if(com.maddox.il2.engine.Actor.isValid(target))
            target.pos.inValidate(true);
        if(com.maddox.il2.engine.Actor.isValid(target2))
            target2.pos.inValidate(true);
    }

    public static void doPanSet(int i, int j)
    {
        for(int k = 0; k < all.size(); k++)
        {
            com.maddox.il2.engine.hotkey.HookGunner hookgunner = (com.maddox.il2.engine.hotkey.HookGunner)all.get(k);
            hookgunner.panSet(i, j);
        }

    }

    public void panSet(int i, int j)
    {
        if(!bUse)
            return;
        if(i == 0 && j == 0)
        {
            _Azimut = 0.0F;
            _Tangage = 0.0F;
        }
        _Azimut = (float)i * stepAzimut + _Azimut;
        if(_Azimut < -maxAzimut)
            _Azimut = -maxAzimut;
        if(_Azimut > maxAzimut)
            _Azimut = maxAzimut;
        _Tangage = (float)j * stepTangage + _Tangage;
        if(_Tangage < minTangage)
            _Tangage = minTangage;
        if(_Tangage > maxTangage)
            _Tangage = maxTangage;
        if(com.maddox.il2.engine.Actor.isValid(target))
            target.pos.inValidate(true);
        if(com.maddox.il2.engine.Actor.isValid(target2))
            target2.pos.inValidate(true);
    }

    public void viewSet(float f, float f1)
    {
        if(!bUse)
            return;
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
        if(f < -maxAzimut)
            f = -maxAzimut;
        else
        if(f > maxAzimut)
            f = maxAzimut;
        if(f1 > maxTangage)
            f1 = maxTangage;
        else
        if(f1 < minTangage)
            f1 = minTangage;
        _Azimut = Azimut = f;
        _Tangage = Tangage = f1;
        if(com.maddox.il2.engine.Actor.isValid(target))
            target.pos.inValidate(true);
        if(com.maddox.il2.engine.Actor.isValid(target2))
            target2.pos.inValidate(true);
    }

    public static void saveRecordedStates(java.io.PrintWriter printwriter)
        throws java.lang.Exception
    {
        if(current == null)
        {
            printwriter.println(0);
            printwriter.println(0);
            printwriter.println(0);
            printwriter.println(0);
        } else
        {
            printwriter.println(current.Azimut);
            printwriter.println(current._Azimut);
            printwriter.println(current.Tangage);
            printwriter.println(current._Tangage);
        }
    }

    public static void loadRecordedStates(java.io.BufferedReader bufferedreader)
        throws java.lang.Exception
    {
        save_Azimut = java.lang.Float.parseFloat(bufferedreader.readLine());
        save__Azimut = java.lang.Float.parseFloat(bufferedreader.readLine());
        save_Tangage = java.lang.Float.parseFloat(bufferedreader.readLine());
        save__Tangage = java.lang.Float.parseFloat(bufferedreader.readLine());
    }

    public HookGunner(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Actor actor1)
    {
        stepAzimut = 45F;
        stepTangage = 30F;
        maxAzimut = 135F;
        maxTangage = 89F;
        minTangage = -60F;
        Azimut = 0.0F;
        Tangage = 0.0F;
        _Azimut = 0.0F;
        _Tangage = 0.0F;
        prevTime = 0L;
        tstamp = -1L;
        roolTime = -1L;
        bUse = false;
        oGunMove = new Orient();
        L = new Loc();
        o = new Orient();
        Azimut = save_Azimut;
        Tangage = save_Tangage;
        _Azimut = save__Azimut;
        _Tangage = save__Tangage;
        setTarget(actor);
        setTarget2(actor1);
        all.add(this);
    }

    public static com.maddox.il2.engine.hotkey.HookGunner current()
    {
        return current;
    }

    private float stepAzimut;
    private float stepTangage;
    private float maxAzimut;
    private float maxTangage;
    private float minTangage;
    private float Azimut;
    private float Tangage;
    private float _Azimut;
    private float _Tangage;
    private long prevTime;
    private float Px;
    private float Py;
    private float Pz;
    private long tstamp;
    private long roolTime;
    private com.maddox.il2.engine.hotkey.Move mover;
    private com.maddox.il2.engine.Actor target;
    private com.maddox.il2.engine.Actor target2;
    private boolean bUse;
    private com.maddox.il2.engine.Orient oGunMove;
    private com.maddox.il2.engine.Loc L;
    private com.maddox.il2.engine.Orient o;
    private static float save_Azimut = 0.0F;
    private static float save_Tangage = 0.0F;
    private static float save__Azimut = 0.0F;
    private static float save__Tangage = 0.0F;
    private static com.maddox.il2.engine.hotkey.HookGunner current;
    private static java.util.ArrayList all = new ArrayList();

}
