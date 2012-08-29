// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HookPilot.java

package com.maddox.il2.engine.hotkey;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.DifficultySettings;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Camera3D;
import com.maddox.il2.engine.HookRender;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.VisibilityChecker;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.effects.SpritesFog;
import com.maddox.rts.HotKeyCmd;
import com.maddox.rts.MsgAction;
import com.maddox.rts.Time;
import java.io.BufferedReader;
import java.io.PrintWriter;

// Referenced classes of package com.maddox.il2.engine.hotkey:
//            HookView

public class HookPilot extends com.maddox.il2.engine.HookRender
{

    public void resetGame()
    {
        enemy = null;
        bUp = false;
    }

    public com.maddox.JGP.Point3d pCamera()
    {
        if(bAim)
            return pAim;
        if(bUp)
            return pUp;
        else
            return pCenter;
    }

    public void setSimpleUse(boolean flag)
    {
        bSimpleUse = flag;
    }

    public void setSimpleAimOrient(float f, float f1, float f2)
    {
        o.set(f, f1, f2);
        le.set(pCamera(), o);
    }

    public void setCenter(com.maddox.JGP.Point3d point3d)
    {
        pCenter.set(point3d);
    }

    public void setAim(com.maddox.JGP.Point3d point3d)
    {
        pAim.set(pCenter);
        if(point3d != null)
            pAim.set(point3d);
        setUp(point3d);
    }

    public void setUp(com.maddox.JGP.Point3d point3d)
    {
        pUp.set(pCenter);
        if(point3d != null)
            pUp.set(point3d);
    }

    public void setSteps(float f, float f1)
    {
        stepAzimut = f;
        stepTangage = f1;
    }

    public void setMinMax(float f, float f1, float f2)
    {
        maxAzimut = f;
        minTangage = f1;
        maxTangage = f2;
    }

    public void setForward(boolean flag)
    {
        bForward = flag;
    }

    public void endPadlock()
    {
        bPadlockEnd = true;
    }

    private void _reset()
    {
        if(!com.maddox.il2.game.AircraftHotKeys.bFirstHotCmd)
        {
            _Azimut = Azimut = 0.0F;
            _Tangage = Tangage = 0.0F;
            o.set(0.0F, 0.0F, 0.0F);
            le.set(pCamera(), o);
        }
        Px = Py = Pz = 0.0F;
        azimPadlock = 0.0F;
        tangPadlock = 0.0F;
        timeKoof = 1.0F;
        prevTime = -1L;
        roolTime = -1L;
        enemy = null;
        bPadlock = false;
        tPadlockEnd = -1L;
        tPadlockEndLen = 0L;
        bPadlockEnd = false;
        bForward = false;
        if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
            new com.maddox.rts.MsgAction(64, 0.0D) {

                public void doAction()
                {
                    com.maddox.rts.HotKeyCmd.exec("misc", "target_");
                }

            }
;
        timeViewSet = -2000L;
        headShift.set(0.0D, 0.0D, 0.0D);
        counterForce.set(0.0D, 0.0D, 0.0D);
        oldHeadTime = -1L;
        oldWx = 0.0D;
        oldWy = 0.0D;
    }

    public void saveRecordedStates(java.io.PrintWriter printwriter)
        throws java.lang.Exception
    {
        printwriter.println(Azimut);
        printwriter.println(_Azimut);
        printwriter.println(Tangage);
        printwriter.println(_Tangage);
        printwriter.println(o.azimut());
        printwriter.println(o.tangage());
    }

    public void loadRecordedStates(java.io.BufferedReader bufferedreader)
        throws java.lang.Exception
    {
        Azimut = java.lang.Float.parseFloat(bufferedreader.readLine());
        _Azimut = java.lang.Float.parseFloat(bufferedreader.readLine());
        Tangage = java.lang.Float.parseFloat(bufferedreader.readLine());
        _Tangage = java.lang.Float.parseFloat(bufferedreader.readLine());
        o.set(java.lang.Float.parseFloat(bufferedreader.readLine()), java.lang.Float.parseFloat(bufferedreader.readLine()), 0.0F);
        le.set(pCamera(), o);
    }

    public void reset()
    {
        stamp = -1L;
        _reset();
    }

    private void setTimeKoof()
    {
        long l = com.maddox.rts.Time.current();
        if(prevTime == -1L)
            timeKoof = 1.0F;
        else
            timeKoof = (float)(l - prevTime) / 30F;
        prevTime = l;
    }

    private void headRoll(com.maddox.il2.objects.air.Aircraft aircraft)
    {
        if(!(aircraft.FM instanceof com.maddox.il2.fm.RealFlightModel))
            return;
        long l = roolTime - stamp;
        if(l >= 0L && l < 50L)
            return;
        roolTime = stamp;
        shakeLVL = ((com.maddox.il2.fm.RealFlightModel)(com.maddox.il2.fm.RealFlightModel)aircraft.FM).shakeLevel;
        float f;
        float f1;
        float f2;
        if(com.maddox.il2.ai.World.cur().diffCur.Head_Shake)
        {
            long l1 = com.maddox.rts.Time.current();
            if(oldHeadTime == -1L)
            {
                oldHeadTime = com.maddox.rts.Time.current();
                oldWx = aircraft.FM.getW().x;
                oldWy = aircraft.FM.getW().y;
            }
            long l2 = l1 - oldHeadTime;
            oldHeadTime = l1;
            if(l2 > 200L)
                l2 = 200L;
            double d1 = 0.0030000000000000001D * (double)l2;
            double d2 = aircraft.FM.getW().x - oldWx;
            double d3 = aircraft.FM.getW().y - oldWy;
            oldWx = aircraft.FM.getW().x;
            if(d1 < 0.001D)
                d2 = 0.0D;
            else
                d2 /= d1;
            oldWy = aircraft.FM.getW().y;
            if(d1 < 0.001D)
                d3 = 0.0D;
            else
                d3 /= d1;
            if(aircraft.FM.Gears.onGround())
            {
                tmpA.set(0.0D, 0.0D, 0.0D);
                headShift.scale(1.0D - d1);
                tmpA.scale(d1);
                headShift.add(tmpA);
                f1 = (float)headShift.y;
                f = (float)(headShift.x + (double)(0.03F * shakeLVL * (0.5F - rnd.nextFloat())));
                f2 = (float)(headShift.z + (double)(1.2F * shakeLVL * (0.5F - rnd.nextFloat())));
            } else
            {
                tmpB.set(0.0D, 0.0D, 0.0D);
                tmpA.set(aircraft.FM.getAccel());
                aircraft.FM.Or.transformInv(tmpA);
                tmpA.scale(-0.59999999999999998D);
                if(tmpA.z > 0.0D)
                    tmpA.z *= 0.80000000000000004D;
                tmpB.add(tmpA);
                counterForce.scale(1.0D - 0.20000000000000001D * d1);
                tmpA.scale(0.20000000000000001D * d1);
                counterForce.add(tmpA);
                tmpB.sub(counterForce);
                counterForce.scale(1.0D - 0.050000000000000003D * d1);
                if(counterForce.z > 0.0D)
                    counterForce.z *= 1.0D - 0.080000000000000002D * d1;
                tmpB.scale(0.080000000000000002D);
                tmpA.set(-0.69999999999999996D * d3, d2, 0.0D);
                tmpA.add(tmpB);
                headShift.scale(1.0D - d1);
                tmpA.scale(d1);
                headShift.add(tmpA);
                f1 = (float)headShift.y;
                f = (float)(headShift.x + (double)(0.3F * shakeLVL * (0.5F - rnd.nextFloat())));
                f2 = (float)(headShift.z + (double)(0.4F * shakeLVL * (0.5F - rnd.nextFloat())));
            }
        } else
        {
            f1 = 0.0F;
            f = 0.0F;
            f2 = 0.0F;
        }
        if(com.maddox.il2.ai.World.cur().diffCur.Wind_N_Turbulence)
        {
            float f3 = com.maddox.il2.objects.effects.SpritesFog.dynamicFogAlpha;
            double d = aircraft.pos.getAbsPoint().z;
            if(f3 > 0.01F && d > 300D && d < 2500D)
            {
                float f4 = aircraft.FM.getSpeed();
                if(f4 > 138.8889F)
                    f4 = 138.8889F;
                f4 -= 55.55556F;
                if(f4 < 0.0F)
                    f4 = 0.0F;
                f4 /= 83.33334F;
                f1 += f4 * 0.05F * f3 * (0.5F - rnd.nextFloat());
                f2 += f4 * 0.3F * f3 * (0.5F - rnd.nextFloat());
            }
        }
        if(f >= 1.0F || f <= -1F)
            if(f < -1F)
                f = -1F;
            else
            if(f > 1.0F)
                f = 1.0F;
            else
                f = 0.0F;
        if(f1 >= 1.0F || f1 <= -1F)
            if(f1 < -1F)
                f1 = -1F;
            else
            if(f1 > 1.0F)
                f1 = 1.0F;
            else
                f1 = 0.0F;
        if(f2 >= 1.0F || f2 <= -1F)
            if(f2 < -1F)
                f2 = -1F;
            else
            if(f2 > 1.0F)
                f2 = 1.0F;
            else
                f2 = 0.0F;
        P.set(Px += (f * (bAim ? 0.01F : 0.03F) - Px) * 0.4F, Py += (f1 * (bAim ? 0.01F : 0.03F) - Py) * 0.4F, Pz += (f2 * (bAim ? 0.01F : 0.03F) - Pz) * 0.4F);
        oTmp.set((float)(6D * P.y), (float)(6D * P.z), (float)(60D * P.y));
        oTmp.increment(0.31F * rnd.nextFloat(-shakeLVL, shakeLVL), 0.31F * rnd.nextFloat(-shakeLVL, shakeLVL), 0.54F * rnd.nextFloat(-shakeLVL, shakeLVL));
    }

    public boolean isPadlock()
    {
        return bPadlock;
    }

    public com.maddox.il2.engine.Actor getEnemy()
    {
        return enemy;
    }

    public void stopPadlock()
    {
        if(!bPadlock)
        {
            return;
        } else
        {
            stamp = -1L;
            _reset();
            return;
        }
    }

    public boolean startPadlock(com.maddox.il2.engine.Actor actor)
    {
        if(!bUse || bSimpleUse)
            return false;
        if(!com.maddox.il2.engine.Actor.isValid(actor))
        {
            bPadlock = false;
            return false;
        }
        com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
        if(!com.maddox.il2.engine.Actor.isValid(aircraft))
        {
            bPadlock = false;
            return false;
        }
        enemy = actor;
        Azimut = _Azimut;
        Tangage = _Tangage;
        bPadlock = true;
        bPadlockEnd = false;
        bVisibleEnemy = true;
        aircraft.pos.getAbs(pAbs, oAbs);
        com.maddox.il2.engine.Camera3D camera3d = (com.maddox.il2.engine.Camera3D)target2;
        camera3d.pos.getAbs(o);
        o.sub(oAbs);
        azimPadlock = o.getAzimut();
        tangPadlock = o.getTangage();
        azimPadlock = (azimPadlock + 3600F) % 360F;
        if(azimPadlock > 180F)
            azimPadlock -= 360F;
        stamp = -1L;
        if(!com.maddox.il2.game.Main3D.cur3D().isDemoPlaying())
            new com.maddox.rts.MsgAction(64, 0.0D) {

                public void doAction()
                {
                    com.maddox.rts.HotKeyCmd.exec("misc", "target_");
                }

            }
;
        return true;
    }

    public boolean isAim()
    {
        return bAim;
    }

    public void doAim(boolean flag)
    {
        if(bAim == flag)
        {
            return;
        } else
        {
            bAim = flag;
            return;
        }
    }

    public boolean isUp()
    {
        return bUp;
    }

    public void doUp(boolean flag)
    {
        if(bUp == flag)
        {
            return;
        } else
        {
            bUp = flag;
            return;
        }
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
        if(bUse)
        {
            if(bPadlock)
            {
                com.maddox.il2.objects.air.Aircraft aircraft = com.maddox.il2.ai.World.getPlayerAircraft();
                if(!com.maddox.il2.engine.Actor.isValid(aircraft))
                {
                    reset();
                    loc1.add(le, loc);
                    return true;
                }
                long l2 = com.maddox.rts.Time.current();
                if(l2 != stamp && enemy.pos != null && aircraft.pos != null)
                {
                    stamp = l2;
                    setTimeKoof();
                    enemy.pos.getRender(pe);
                    pEnemyAbs.set(pe);
                    aircraft.pos.getRender(pAbs, oAbs);
                    Ve.sub(pe, pAbs);
                    o.setAT0(Ve);
                    if(com.maddox.il2.ai.World.cur().diffCur.Head_Shake || com.maddox.il2.ai.World.cur().diffCur.Wind_N_Turbulence)
                    {
                        headRoll(aircraft);
                        pe.add(pCamera(), P);
                        le.set(pe);
                    } else
                    {
                        le.set(pCamera());
                    }
                    o.sub(oAbs);
                    padlockSet(o);
                    op.set(o);
                    op.add(oAbs);
                }
                loc1.add(le, loc);
                loc1.set(op);
                return true;
            }
            long l = com.maddox.rts.Time.currentReal();
            if(l != rprevTime && !bSimpleUse)
            {
                long l3 = l - rprevTime;
                rprevTime = l;
                if(_Azimut != Azimut || _Tangage != Tangage)
                {
                    Azimut = bvalue(_Azimut, Azimut, l3);
                    Tangage = bvalue(_Tangage, Tangage, l3);
                    o.set(Azimut, Tangage, 0.0F);
                }
            }
            if((com.maddox.il2.ai.World.cur().diffCur.Head_Shake || com.maddox.il2.ai.World.cur().diffCur.Wind_N_Turbulence) && !bSimpleUse)
            {
                com.maddox.il2.objects.air.Aircraft aircraft1 = com.maddox.il2.ai.World.getPlayerAircraft();
                if(com.maddox.il2.engine.Actor.isValid(aircraft1))
                {
                    long l1 = com.maddox.rts.Time.current();
                    if(l1 != stamp)
                    {
                        stamp = l1;
                        headRoll(aircraft1);
                    }
                }
                pe.add(pCamera(), P);
                oTmp2.set(o);
                oTmp2.increment(oTmp);
                le.set(pe, oTmp2);
            } else
            {
                le.set(pCamera(), o);
            }
            loc1.add(le, loc);
        } else
        {
            loc1.set(loc);
        }
        return true;
    }

    public void computePos(com.maddox.il2.engine.Actor actor, com.maddox.il2.engine.Loc loc, com.maddox.il2.engine.Loc loc1)
    {
        if(bUse)
        {
            if(com.maddox.rts.Time.isPaused() && !bPadlock)
            {
                if(com.maddox.il2.ai.World.cur().diffCur.Head_Shake)
                {
                    pe.add(pCamera(), P);
                    le.set(pe, o);
                } else
                {
                    le.set(pCamera(), o);
                }
                loc1.add(le, loc);
                return;
            }
            loc1.add(le, loc);
            if(bPadlock)
                loc1.set(op);
        } else
        {
            loc1.set(loc);
        }
    }

    private float avalue(float f, float f1)
    {
        if(f >= 0.0F)
            if(f <= f1)
                return 0.0F;
            else
                return f - f1;
        if(f >= -f1)
            return 0.0F;
        else
            return f + f1;
    }

    private float bvalue(float f, float f1)
    {
        float f2 = ((com.maddox.il2.engine.hotkey.HookView.koofSpeed * 4F) / 6F) * timeKoof;
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

    private void padlockSet(com.maddox.il2.engine.Orient orient)
    {
        float f = orient.getAzimut();
        float f1 = orient.getTangage();
        if(bPadlockEnd || bForward)
        {
            f = f1 = 0.0F;
            tPadlockEnd = -1L;
        } else
        {
            com.maddox.il2.engine.Camera3D camera3d = (com.maddox.il2.engine.Camera3D)target2;
            float f2 = camera3d.FOV() * 0.3F;
            float f3 = f2 / camera3d.aspect();
            f = (f + 3600F) % 360F;
            if(f > 180F)
                f -= 360F;
            f = avalue(f, f2);
            f1 = avalue(f1, f3);
            boolean flag = false;
            if(f < -maxAzimut)
            {
                f = -maxAzimut;
                flag = true;
            }
            if(f > maxAzimut)
            {
                f = maxAzimut;
                flag = true;
            }
            if(f1 < minTangage)
            {
                f1 = minTangage;
                flag = true;
            }
            if(flag || !bVisibleEnemy || !com.maddox.il2.engine.Actor.isAlive(enemy))
            {
                if(tPadlockEnd != -1L)
                    tPadlockEndLen += com.maddox.rts.Time.current() - tPadlockEnd;
                tPadlockEnd = com.maddox.rts.Time.current();
                if(tPadlockEndLen > 4000L)
                {
                    bPadlockEnd = true;
                    tPadlockEnd = -1L;
                    tPadlockEndLen = 0L;
                }
            } else
            {
                tPadlockEnd = -1L;
                tPadlockEndLen = 0L;
            }
        }
        f = bvalue(f, azimPadlock);
        f1 = bvalue(f1, tangPadlock);
        orient.set(f, f1, 0.0F);
        azimPadlock = f;
        tangPadlock = f1;
        if(bPadlockEnd && -1F < azimPadlock && azimPadlock < 1.0F && -1F < tangPadlock && tangPadlock < 1.0F)
        {
            stamp = -1L;
            _reset();
        }
    }

    public void checkPadlockState()
    {
        if(!bPadlock)
            return;
        if(!com.maddox.il2.engine.Actor.isAlive(enemy))
        {
            return;
        } else
        {
            com.maddox.il2.game.VisibilityChecker.checkLandObstacle = true;
            com.maddox.il2.game.VisibilityChecker.checkCabinObstacle = true;
            com.maddox.il2.game.VisibilityChecker.checkPlaneObstacle = true;
            com.maddox.il2.game.VisibilityChecker.checkObjObstacle = true;
            bVisibleEnemy = com.maddox.il2.game.VisibilityChecker.computeVisibility(null, enemy) > 0.0F;
            return;
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
        return flag1;
    }

    public boolean useMouse(boolean flag)
    {
        boolean flag1 = bUseMouse;
        bUseMouse = flag;
        return flag1;
    }

    public void mouseMove(int i, int j, int k)
    {
        if(!bUse || bPadlock || bSimpleUse)
            return;
        if(bUseMouse && com.maddox.rts.Time.real() > timeViewSet + 1000L)
        {
            float f = (o.azimut() + (float)i * com.maddox.il2.engine.hotkey.HookView.koofAzimut) % 360F;
            if(f > 180F)
                f -= 360F;
            else
            if(f < -180F)
                f += 360F;
            if(f < -maxAzimut)
            {
                if(i <= 0)
                    f = -maxAzimut;
                else
                    f = maxAzimut;
            } else
            if(f > maxAzimut)
                if(i >= 0)
                    f = maxAzimut;
                else
                    f = -maxAzimut;
            float f1 = (o.tangage() + (float)j * com.maddox.il2.engine.hotkey.HookView.koofTangage) % 360F;
            if(f1 > 180F)
                f1 -= 360F;
            else
            if(f1 < -180F)
                f1 += 360F;
            if(f1 < minTangage)
            {
                if(j <= 0)
                    f1 = minTangage;
                else
                    f1 = maxTangage;
            } else
            if(f1 > maxTangage)
                if(j >= 0)
                    f1 = maxTangage;
                else
                    f1 = minTangage;
            o.set(f, f1, 0.0F);
            if(com.maddox.il2.engine.Actor.isValid(target))
                target.pos.inValidate(true);
            if(com.maddox.il2.engine.Actor.isValid(target2))
                target2.pos.inValidate(true);
            Azimut = _Azimut;
            Tangage = _Tangage;
        }
    }

    public void viewSet(float f, float f1)
    {
        if(!bUse || bPadlock || bSimpleUse)
            return;
        if(bUseMouse)
        {
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
            o.set(f, f1, 0.0F);
            if(com.maddox.il2.engine.Actor.isValid(target))
                target.pos.inValidate(true);
            if(com.maddox.il2.engine.Actor.isValid(target2))
                target2.pos.inValidate(true);
        }
    }

    public void snapSet(float f, float f1)
    {
        if(!bUse || bPadlock || bSimpleUse)
            return;
        _Azimut = 45F * f;
        _Tangage = 44F * f1;
        Azimut = o.azimut() % 360F;
        if(Azimut > 180F)
            Azimut -= 360F;
        else
        if(Azimut < -180F)
            Azimut += 360F;
        Tangage = o.tangage() % 360F;
        if(Tangage > 180F)
            Tangage -= 360F;
        else
        if(Tangage < -180F)
            Tangage += 360F;
        if(com.maddox.il2.engine.Actor.isValid(target))
            target.pos.inValidate(true);
        if(com.maddox.il2.engine.Actor.isValid(target2))
            target2.pos.inValidate(true);
    }

    public void panSet(int i, int j)
    {
        if(!bUse || bPadlock || bSimpleUse)
            return;
        if(i == 0 && j == 0)
        {
            _Azimut = 0.0F;
            _Tangage = 0.0F;
        }
        if(_Azimut == -maxAzimut)
        {
            int k = (int)(_Azimut / stepAzimut);
            if(-_Azimut % stepAzimut > 0.01F * stepAzimut)
                k--;
            _Azimut = (float)k * stepAzimut;
        } else
        if(_Azimut == maxAzimut)
        {
            int l = (int)(_Azimut / stepAzimut);
            if(_Azimut % stepAzimut > 0.01F * stepAzimut)
                l++;
            _Azimut = (float)l * stepAzimut;
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
        Azimut = o.azimut() % 360F;
        if(Azimut > 180F)
            Azimut -= 360F;
        else
        if(Azimut < -180F)
            Azimut += 360F;
        Tangage = o.tangage() % 360F;
        if(Tangage > 180F)
            Tangage -= 360F;
        else
        if(Tangage < -180F)
            Tangage += 360F;
        if(com.maddox.il2.engine.Actor.isValid(target))
            target.pos.inValidate(true);
        if(com.maddox.il2.engine.Actor.isValid(target2))
            target2.pos.inValidate(true);
    }

    private HookPilot()
    {
        stepAzimut = 45F;
        stepTangage = 30F;
        maxAzimut = 155F;
        maxTangage = 89F;
        minTangage = -60F;
        Azimut = 0.0F;
        Tangage = 0.0F;
        _Azimut = 0.0F;
        _Tangage = 0.0F;
        rprevTime = 0L;
        timeKoof = 1.0F;
        o = new Orient();
        op = new Orient();
        le = new Loc();
        pe = new Point3d();
        pEnemyAbs = new Point3d();
        Ve = new Vector3d();
        pAbs = new Point3d();
        oAbs = new Orient();
        target = null;
        target2 = null;
        enemy = null;
        stamp = -1L;
        prevTime = -1L;
        roolTime = -1L;
        bUse = false;
        bPadlock = true;
        tPadlockEnd = -1L;
        tPadlockEndLen = 0L;
        bPadlockEnd = false;
        bForward = false;
        bAim = false;
        bUp = false;
        bVisibleEnemy = true;
        bSimpleUse = false;
        pCenter = new Point3d();
        pAim = new Point3d();
        pUp = new Point3d();
        bUseMouse = true;
        timeViewSet = -2000L;
    }

    public static com.maddox.il2.engine.hotkey.HookPilot New()
    {
        if(current == null)
            current = new HookPilot();
        return current;
    }

    public static com.maddox.il2.engine.hotkey.HookPilot cur()
    {
        return com.maddox.il2.engine.hotkey.HookPilot.New();
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
    private long rprevTime;
    private float Px;
    private float Py;
    private float Pz;
    private float azimPadlock;
    private float tangPadlock;
    private float timeKoof;
    private com.maddox.il2.engine.Orient o;
    private com.maddox.il2.engine.Orient op;
    private com.maddox.il2.engine.Loc le;
    private com.maddox.JGP.Point3d pe;
    private com.maddox.JGP.Point3d pEnemyAbs;
    private com.maddox.JGP.Vector3d Ve;
    private com.maddox.JGP.Point3d pAbs;
    private com.maddox.il2.engine.Orient oAbs;
    private com.maddox.il2.engine.Actor target;
    private com.maddox.il2.engine.Actor target2;
    private com.maddox.il2.engine.Actor enemy;
    private long stamp;
    private long prevTime;
    private long roolTime;
    private boolean bUse;
    private boolean bPadlock;
    private long tPadlockEnd;
    private long tPadlockEndLen;
    private boolean bPadlockEnd;
    private boolean bForward;
    private boolean bAim;
    private boolean bUp;
    private boolean bVisibleEnemy;
    private boolean bSimpleUse;
    private com.maddox.JGP.Point3d pCenter;
    private com.maddox.JGP.Point3d pAim;
    private com.maddox.JGP.Point3d pUp;
    private static com.maddox.il2.ai.RangeRandom rnd = new RangeRandom();
    private static com.maddox.JGP.Point3d P = new Point3d();
    private static com.maddox.JGP.Vector3d tmpA = new Vector3d();
    private static com.maddox.JGP.Vector3d tmpB = new Vector3d();
    private static com.maddox.JGP.Vector3d headShift = new Vector3d();
    private static com.maddox.JGP.Vector3d counterForce = new Vector3d();
    private static long oldHeadTime = 0L;
    private static double oldWx = 0.0D;
    private static double oldWy = 0.0D;
    private boolean bUseMouse;
    private long timeViewSet;
    public static com.maddox.il2.engine.hotkey.HookPilot current;
    private static com.maddox.il2.engine.Orient oTmp = new Orient();
    private static com.maddox.il2.engine.Orient oTmp2 = new Orient();
    private static float shakeLVL;

}
