// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   Missile.java

package com.maddox.il2.objects.weapons;

import com.maddox.JGP.Geom;
import com.maddox.JGP.Point3d;
import com.maddox.JGP.Point3f;
import com.maddox.JGP.Tuple3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.EventLog;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorMesh;
import com.maddox.il2.engine.ActorNet;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.Interpolate;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.LightPoint;
import com.maddox.il2.engine.LightPointActor;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.game.NetSafeLog;
import com.maddox.il2.game.Selector;
import com.maddox.il2.objects.ActorSimpleMesh;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeGuidedMissileCarrier;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Message;
import com.maddox.rts.NetChannel;
import com.maddox.rts.NetMsgFiltered;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.NetMsgSpawn;
import com.maddox.rts.NetObj;
import com.maddox.rts.NetSpawn;
import com.maddox.rts.NetUpdate;
import com.maddox.rts.ObjState;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import java.io.IOException;
import java.io.PrintStream;
import java.security.SecureRandom;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.weapons:
//            Rocket, MissilePhysics, GuidedMissileUtils

public class Missile extends com.maddox.il2.objects.weapons.Rocket
{
    class Master extends com.maddox.il2.engine.ActorNet
        implements com.maddox.rts.NetUpdate
    {

        public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
        {
            if(!com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.ActorNet)this).actor()))
                return;
            if(netchannel.isMirrored(((com.maddox.rts.NetObj) (this))))
                return;
            try
            {
                if(netchannel.userState == 0)
                {
                    com.maddox.rts.NetMsgSpawn netmsgspawn = ((com.maddox.il2.engine.ActorNet)this).actor().netReplicate(netchannel);
                    if(netmsgspawn != null)
                    {
                        ((com.maddox.rts.NetObj)this).postTo(netchannel, ((com.maddox.rts.NetMsgGuaranted) (netmsgspawn)));
                        ((com.maddox.il2.engine.ActorNet)this).actor().netFirstUpdate(netchannel);
                    }
                }
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
            return;
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            return false;
        }

        public void netUpdate()
        {
            try
            {
                out.unLockAndClear();
                ((com.maddox.il2.engine.ActorNet)this).actor().pos.getAbs(theMissilePoint3d, theMissileOrient);
                ((com.maddox.rts.NetMsgOutput) (out)).writeFloat((float)((com.maddox.JGP.Tuple3d) (theMissilePoint3d)).x);
                ((com.maddox.rts.NetMsgOutput) (out)).writeFloat((float)((com.maddox.JGP.Tuple3d) (theMissilePoint3d)).y);
                ((com.maddox.rts.NetMsgOutput) (out)).writeFloat((float)((com.maddox.JGP.Tuple3d) (theMissilePoint3d)).z);
                theMissileOrient.wrap();
                int i = (int)((theMissileOrient.getYaw() * 32000F) / 180F);
                int j = (int)((theMissileOrient.tangage() * 32000F) / 90F);
                ((com.maddox.rts.NetMsgOutput) (out)).writeShort(i);
                ((com.maddox.rts.NetMsgOutput) (out)).writeShort(j);
                ((com.maddox.rts.NetObj)this).post(com.maddox.rts.Time.current(), out);
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        com.maddox.rts.NetMsgFiltered out;
        com.maddox.JGP.Point3d theMissilePoint3d;
        com.maddox.il2.engine.Orient theMissileOrient;

        public Master(com.maddox.il2.engine.Actor actor)
        {
            super(actor);
            theMissilePoint3d = new Point3d();
            theMissileOrient = new Orient();
            out = new NetMsgFiltered();
        }
    }

    class Mirror extends com.maddox.il2.engine.ActorNet
    {

        public void msgNetNewChannel(com.maddox.rts.NetChannel netchannel)
        {
            if(!com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.ActorNet)this).actor()))
                return;
            if(netchannel.isMirrored(((com.maddox.rts.NetObj) (this))))
                return;
            try
            {
                if(netchannel.userState == 0)
                {
                    com.maddox.rts.NetMsgSpawn netmsgspawn = ((com.maddox.il2.engine.ActorNet)this).actor().netReplicate(netchannel);
                    if(netmsgspawn != null)
                    {
                        ((com.maddox.rts.NetObj)this).postTo(netchannel, ((com.maddox.rts.NetMsgGuaranted) (netmsgspawn)));
                        ((com.maddox.il2.engine.ActorNet)this).actor().netFirstUpdate(netchannel);
                    }
                }
            }
            catch(java.lang.Exception exception)
            {
                com.maddox.rts.NetObj.printDebug(exception);
            }
        }

        public boolean netInput(com.maddox.rts.NetMsgInput netmsginput)
            throws java.io.IOException
        {
            if(netmsginput.isGuaranted())
                return false;
            if(((com.maddox.rts.NetObj)this).isMirrored())
            {
                out.unLockAndSet(netmsginput, 0);
                ((com.maddox.rts.NetObj)this).postReal(com.maddox.rts.Message.currentTime(true), out);
            }
            ((com.maddox.JGP.Tuple3d) (theMissilePoint3d)).set(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
            int i = ((int) (netmsginput.readShort()));
            int j = ((int) (netmsginput.readShort()));
            float f = -(((float)i * 180F) / 32000F);
            float f1 = ((float)j * 90F) / 32000F;
            theMissileOrient.set(f, f1, 0.0F);
            ((com.maddox.il2.engine.ActorNet)this).actor().pos.setAbs(theMissilePoint3d, theMissileOrient);
            return true;
        }

        com.maddox.rts.NetMsgFiltered out;
        com.maddox.JGP.Point3d theMissilePoint3d;
        com.maddox.il2.engine.Orient theMissileOrient;

        public Mirror(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i)
        {
            super(actor, netchannel, i);
            theMissilePoint3d = new Point3d();
            theMissileOrient = new Orient();
            out = new NetMsgFiltered();
        }
    }

    static class SPAWN
        implements com.maddox.rts.NetSpawn
    {

        public void netSpawn(int i, com.maddox.rts.NetMsgInput netmsginput)
        {
            com.maddox.rts.NetObj netobj = netmsginput.readNetObj();
            if(netobj == null)
                return;
            try
            {
                com.maddox.il2.engine.Actor actor = (com.maddox.il2.engine.Actor)netobj.superObj();
                com.maddox.JGP.Point3d point3d = new Point3d(netmsginput.readFloat(), netmsginput.readFloat(), netmsginput.readFloat());
                com.maddox.il2.engine.Orient orient = new Orient(netmsginput.readFloat(), netmsginput.readFloat(), 0.0F);
                float f = netmsginput.readFloat();
                doSpawn(actor, netmsginput.channel(), i, point3d, orient, f);
                if(actor instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier)
                    ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)actor).shotMissile();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(((java.lang.Throwable) (exception)).getMessage());
                ((java.lang.Throwable) (exception)).printStackTrace();
            }
        }

        public void doSpawn(com.maddox.il2.engine.Actor actor1, com.maddox.rts.NetChannel netchannel1, int j, com.maddox.JGP.Point3d point3d1, com.maddox.il2.engine.Orient orient1, float f1)
        {
        }

        SPAWN()
        {
        }
    }

    private class MissileNavLight
    {

        public com.maddox.il2.engine.Eff3DActor theNavLight;
        public com.maddox.il2.objects.weapons.MissileNavLight nextNavLight;

        public MissileNavLight(com.maddox.il2.engine.Eff3DActor theEff3DActor)
        {
            theNavLight = theEff3DActor;
            nextNavLight = null;
        }
    }


    public double getLaunchTimeFactor()
    {
        return ((double)(com.maddox.rts.Time.current() - tStart) / 1000D) * 6D;
    }

    public double getLaunchYaw()
    {
        double launchYawTimeFactor = getLaunchTimeFactor();
        double theLaunchYaw = 2D * ((java.lang.Math.cos(launchYawTimeFactor + 0.40000000000000002D) - 1.02D) + launchYawTimeFactor / 5D);
        theLaunchYaw *= java.lang.Math.sin(launchKren);
        theLaunchYaw += launchYaw;
        return theLaunchYaw;
    }

    public double getLaunchPitch()
    {
        double launchPitchTimeFactor = getLaunchTimeFactor();
        double theLaunchPitch = 2D * ((java.lang.Math.cos(launchPitchTimeFactor + 0.40000000000000002D) - 1.02D) + launchPitchTimeFactor / 5D);
        theLaunchPitch *= java.lang.Math.cos(launchKren);
        theLaunchPitch += launchPitch;
        return theLaunchPitch;
    }

    private void endAllSmoke()
    {
        if(iExhausts < 2)
        {
            com.maddox.il2.engine.Eff3DActor.finish(super.smoke);
        } else
        {
            for(int i = 0; i < iExhausts; i++)
                if(smokes[i] != null)
                    com.maddox.il2.engine.Eff3DActor.finish(smokes[i]);

        }
        endedSmoke = true;
    }

    private void endAllFlame()
    {
        if(iExhausts < 2)
        {
            com.maddox.rts.ObjState.destroy(((com.maddox.rts.Destroy) (super.flame)));
        } else
        {
            for(int i = 0; i < iExhausts; i++)
                if(flames[i] != null)
                    com.maddox.rts.ObjState.destroy(((com.maddox.rts.Destroy) (flames[i])));

        }
        if(super.light != null)
            super.light.light.setEmit(0.0F, 1.0F);
        ((com.maddox.il2.engine.Actor)this).stopSounds();
        endedFlame = true;
    }

    private void endAllSprites()
    {
        if(iExhausts < 2)
        {
            com.maddox.il2.engine.Eff3DActor.finish(super.sprite);
        } else
        {
            for(int i = 0; i < iExhausts; i++)
                if(sprites[i] != null)
                    com.maddox.il2.engine.Eff3DActor.finish(sprites[i]);

        }
        endedSprite = true;
    }

    protected void endSmoke()
    {
        if(!smokeActive && !endedSmoke)
            endAllSmoke();
        if(!spriteActive && !endedSprite)
            endAllSprites();
        if(!flameActive && !endedFlame)
            endAllFlame();
    }

    public boolean stepTargetHoming()
    {
        float fTick = com.maddox.rts.Time.tickLenFs();
        int theFailState = getFailState();
        float fSpeed = (float)((com.maddox.il2.objects.weapons.Rocket)this).getSpeed((com.maddox.JGP.Vector3d)null);
        if(theFailState == 3)
            fTimeFire = 0.0F;
        ((com.maddox.il2.engine.Actor)this).pos.getAbs(p, or);
        or.wrap();
        float theForce = fForce;
        float millisecondsFromStart = com.maddox.rts.Time.current() - tStart;
        if(millisecondsFromStart > fTimeFire)
        {
            flameActive = false;
            smokeActive = false;
            endSmoke();
            fMassa = fMassaEnd;
            fForce = 0.0F;
        } else
        {
            if(fT1 > 0.001F && millisecondsFromStart < fT1)
                theForce *= (fP1 + ((100F - fP1) * millisecondsFromStart) / fT1) / 100F;
            float millisecondsToEnd = fTimeFire - millisecondsFromStart;
            if(fT2 > 0.001F && millisecondsToEnd < fT2)
                theForce *= (fP2 + ((100F - fP2) * (fT2 - millisecondsToEnd)) / fT2) / 100F;
            fMassa -= fDiffM;
        }
        float fForceAzimuth = com.maddox.il2.objects.weapons.MissilePhysics.getGForce(fSpeed, oldDeltaAzimuth / fTick);
        float fForceTangage = com.maddox.il2.objects.weapons.MissilePhysics.getGForce(fSpeed, oldDeltaTangage / fTick);
        float fTurnForce = (float)java.lang.Math.sqrt(fForceAzimuth * fForceAzimuth + fForceTangage * fForceTangage) * 9.80665F * fMassa;
        fTurnForce *= fCw;
        float fResForce = (float)java.lang.Math.sqrt(java.lang.Math.abs(theForce * theForce - fTurnForce * fTurnForce));
        if(fTurnForce > theForce)
            fResForce *= -1F;
        float fAccelForce = fResForce - com.maddox.il2.objects.weapons.MissilePhysics.getDragInGravity(fSquare, fCw, (float)((com.maddox.JGP.Tuple3d) (p)).z, fSpeed, or.getTangage(), fMassa);
        float fAccel = fAccelForce / fMassa;
        fSpeed += fAccel * fTick;
        if(fSpeed < 3F)
            theFailState = 7;
        ((com.maddox.JGP.Tuple3d) (v)).set(1.0D, 0.0D, 0.0D);
        or.transform(((com.maddox.JGP.Tuple3d) (v)));
        ((com.maddox.JGP.Tuple3d) (v)).scale(fSpeed);
        ((com.maddox.il2.objects.weapons.Rocket)this).setSpeed(v);
        p.x += ((com.maddox.JGP.Tuple3d) (v)).x * (double)fTick;
        p.y += ((com.maddox.JGP.Tuple3d) (v)).y * (double)fTick;
        p.z += ((com.maddox.JGP.Tuple3d) (v)).z * (double)fTick;
        if(((com.maddox.il2.engine.Actor)this).isNet() && ((com.maddox.il2.engine.Actor)this).isNetMirror())
        {
            ((com.maddox.il2.engine.Actor)this).pos.setAbs(p, or);
            return false;
        }
        if(theFailState == 7)
        {
            ((com.maddox.il2.objects.weapons.Rocket)this).doExplosionAir();
            ((com.maddox.il2.engine.Actor)this).postDestroy();
            ((com.maddox.il2.engine.Actor)this).collide(false);
            ((com.maddox.il2.engine.Actor)this).drawing(false);
            return false;
        }
        if(victim != null)
        {
            victim.pos.getAbs(pT, orVictimOffset);
            victim.getSpeed(victimSpeed);
            double victimDistance = com.maddox.il2.objects.weapons.GuidedMissileUtils.distanceBetween(((com.maddox.il2.engine.Actor) (this)), victim);
            double theVictimSpeed = victimSpeed.length();
            double speedRel = (double)fSpeed / theVictimSpeed;
            double gamma = com.maddox.il2.objects.weapons.GuidedMissileUtils.angleActorBetween(victim, ((com.maddox.il2.engine.Actor) (this)));
            double alpha = com.maddox.JGP.Geom.RAD2DEG((float)java.lang.Math.asin(java.lang.Math.sin(com.maddox.JGP.Geom.DEG2RAD((float)gamma)) / speedRel));
            double beta = 180D - gamma - alpha;
            double victimAdvance = (victimDistance * java.lang.Math.sin(com.maddox.JGP.Geom.DEG2RAD((float)alpha))) / java.lang.Math.sin(com.maddox.JGP.Geom.DEG2RAD((float)beta));
            victimAdvance -= 5D;
            double timeToTarget = victimAdvance / theVictimSpeed;
            ((com.maddox.JGP.Tuple3d) (victimSpeed)).scale(timeToTarget * (double)(fLeadPercent / 100F));
            ((com.maddox.JGP.Tuple3d) (pT)).add(((com.maddox.JGP.Tuple3d) (victimSpeed)));
            com.maddox.il2.engine.Orient orientTarget = new Orient();
            orientTarget.set(victim.pos.getAbsOrient());
            ((com.maddox.JGP.Tuple3d) (v)).set(((com.maddox.JGP.Tuple3f) (pVictimOffset)));
            orientTarget.transform(((com.maddox.JGP.Tuple3d) (v)));
            ((com.maddox.JGP.Tuple3d) (pT)).add(((com.maddox.JGP.Tuple3d) (v)));
            ((com.maddox.JGP.Tuple3d) (pT)).sub(((com.maddox.JGP.Tuple3d) (p)));
            or.transformInv(((com.maddox.JGP.Tuple3d) (pT)));
            double angleAzimuth = java.lang.Math.toDegrees(java.lang.Math.atan(((com.maddox.JGP.Tuple3d) (pT)).y / ((com.maddox.JGP.Tuple3d) (pT)).x));
            double angleTangage = java.lang.Math.toDegrees(java.lang.Math.atan(((com.maddox.JGP.Tuple3d) (pT)).z / ((com.maddox.JGP.Tuple3d) (pT)).x));
            if(theFailState == 4)
            {
                angleAzimuth += 180D;
                angleTangage += 180D;
                if(angleAzimuth > 180D)
                    angleAzimuth = 180D - angleAzimuth;
                if(angleTangage > 180D)
                    angleTangage = 180D - angleTangage;
            }
            if(com.maddox.rts.Time.current() > tStart + lTrackDelay)
            {
                float turnStepMax = com.maddox.il2.objects.weapons.MissilePhysics.getDegPerSec(fSpeed, fMaxG) * fTick * com.maddox.il2.objects.weapons.MissilePhysics.getAirDensityFactor((float)((com.maddox.JGP.Tuple3d) (p)).z);
                float turnDiffMax = turnStepMax / fStepsForFullTurn;
                if(theFailState == 5)
                {
                    if(fIvanTimeLeft < fTick)
                    {
                        if(nextRandomFloat() < 0.5F)
                        {
                            if(nextRandomFloat() < 0.5F)
                                deltaAzimuth = turnStepMax;
                            else
                                deltaAzimuth = -turnStepMax;
                            deltaTangage = nextRandomFloat(-turnStepMax, turnStepMax);
                        } else
                        {
                            if(nextRandomFloat() < 0.5F)
                                deltaTangage = turnStepMax;
                            else
                                deltaTangage = -turnStepMax;
                            deltaAzimuth = nextRandomFloat(-turnStepMax, turnStepMax);
                        }
                        fIvanTimeLeft = nextRandomFloat(1.0F, 2.0F);
                    } else
                    {
                        deltaAzimuth = oldDeltaAzimuth;
                        deltaTangage = oldDeltaTangage;
                        fIvanTimeLeft -= fTick;
                        if(fIvanTimeLeft < fTick)
                        {
                            iFailState = 0;
                            fIvanTimeLeft = 0.0F;
                        }
                    }
                } else
                if(theFailState == 2)
                {
                    if(nextRandomFloat() < 0.5F)
                        deltaAzimuth = turnStepMax;
                    else
                        deltaAzimuth = -turnStepMax;
                    if(nextRandomFloat() < 0.5F)
                        deltaTangage = turnStepMax;
                    else
                        deltaTangage = -turnStepMax;
                } else
                if(theFailState == 6)
                {
                    deltaAzimuth = oldDeltaAzimuth;
                    deltaTangage = oldDeltaTangage;
                } else
                {
                    if(((com.maddox.JGP.Tuple3d) (pT)).x > -10D)
                    {
                        deltaAzimuth = (float)(-angleAzimuth);
                        if(deltaAzimuth > turnStepMax)
                            deltaAzimuth = turnStepMax;
                        if(deltaAzimuth < -turnStepMax)
                            deltaAzimuth = -turnStepMax;
                        deltaTangage = (float)angleTangage;
                        if(deltaTangage > turnStepMax)
                            deltaTangage = turnStepMax;
                        if(deltaTangage < -turnStepMax)
                            deltaTangage = -turnStepMax;
                    }
                    if(java.lang.Math.abs(oldDeltaAzimuth - deltaAzimuth) > turnDiffMax)
                        if(oldDeltaAzimuth < deltaAzimuth)
                            deltaAzimuth = oldDeltaAzimuth + turnDiffMax;
                        else
                            deltaAzimuth = oldDeltaAzimuth - turnDiffMax;
                    if(java.lang.Math.abs(oldDeltaTangage - deltaTangage) > turnDiffMax)
                        if(oldDeltaTangage < deltaTangage)
                            deltaTangage = oldDeltaTangage + turnDiffMax;
                        else
                            deltaTangage = oldDeltaTangage - turnDiffMax;
                    oldDeltaAzimuth = deltaAzimuth;
                    oldDeltaTangage = deltaTangage;
                }
                or.increment(deltaAzimuth, deltaTangage, 0.0F);
                or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
            } else
            if(iLaunchType == 2)
                or.setYPR((float)getLaunchYaw(), (float)getLaunchPitch(), 0.0F);
            else
                or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
        } else
        if(com.maddox.rts.Time.current() < tStart + lTrackDelay && iLaunchType == 2)
            or.setYPR((float)getLaunchYaw(), (float)getLaunchPitch(), 0.0F);
        else
            or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
        deltaAzimuth = deltaTangage = 0.0F;
        ((com.maddox.il2.engine.Actor)this).pos.setAbs(p, or);
        if(com.maddox.rts.Time.current() > tStart + 2L * lTrackDelay)
            if(com.maddox.il2.engine.Actor.isValid(victim))
            {
                float f2 = (float)p.distance(victim.pos.getAbsPoint());
                if((victim instanceof com.maddox.il2.objects.air.Aircraft) && f2 > prevd && prevd != 1000F)
                {
                    if(f2 < 30F)
                    {
                        ((com.maddox.il2.objects.weapons.Rocket)this).doExplosionAir();
                        ((com.maddox.il2.engine.Actor)this).postDestroy();
                        ((com.maddox.il2.engine.Actor)this).collide(false);
                        ((com.maddox.il2.engine.Actor)this).drawing(false);
                    }
                    if(((com.maddox.il2.objects.weapons.Rocket)this).getSpeed((com.maddox.JGP.Vector3d)null) > victim.getSpeed((com.maddox.JGP.Vector3d)null))
                        victim = null;
                }
                prevd = f2;
            } else
            {
                prevd = 1000F;
            }
        if(!com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor)this).getOwner()) || !(((com.maddox.il2.engine.Actor)this).getOwner() instanceof com.maddox.il2.objects.air.Aircraft))
        {
            ((com.maddox.il2.objects.weapons.Rocket)this).doExplosionAir();
            ((com.maddox.il2.engine.Actor)this).postDestroy();
            ((com.maddox.il2.engine.Actor)this).collide(false);
            ((com.maddox.il2.engine.Actor)this).drawing(false);
            return false;
        } else
        {
            return false;
        }
    }

    public boolean stepBeamRider()
    {
        float fTick = com.maddox.rts.Time.tickLenFs();
        float fSpeed = (float)((com.maddox.il2.objects.weapons.Rocket)this).getSpeed(((com.maddox.JGP.Vector3d) (null)));
        int theFailState = getFailState();
        if(theFailState == 3)
            fTimeFire = 0.0F;
        ((com.maddox.il2.engine.Actor)this).pos.getAbs(p, or);
        or.wrap();
        float theForce = fForce;
        float millisecondsFromStart = com.maddox.rts.Time.current() - tStart;
        if(millisecondsFromStart > fTimeFire)
        {
            flameActive = false;
            smokeActive = false;
            endSmoke();
            fMassa = fMassaEnd;
            fForce = 0.0F;
        } else
        {
            if(fT1 > 0.001F && millisecondsFromStart < fT1)
                theForce *= (fP1 + ((100F - fP1) * millisecondsFromStart) / fT1) / 100F;
            float millisecondsToEnd = fTimeFire - millisecondsFromStart;
            if(fT2 > 0.001F && millisecondsToEnd < fT2)
                theForce *= (fP2 + ((100F - fP2) * (fT2 - millisecondsToEnd)) / fT2) / 100F;
            fMassa -= fDiffM;
        }
        float fForceAzimuth = com.maddox.il2.objects.weapons.MissilePhysics.getGForce(fSpeed, oldDeltaAzimuth / fTick);
        float fForceTangage = com.maddox.il2.objects.weapons.MissilePhysics.getGForce(fSpeed, oldDeltaTangage / fTick);
        float fTurnForce = (float)java.lang.Math.sqrt(fForceAzimuth * fForceAzimuth + fForceTangage * fForceTangage) * 9.80665F * fMassa;
        fTurnForce *= fCw;
        float fResForce = (float)java.lang.Math.sqrt(java.lang.Math.abs(theForce * theForce - fTurnForce * fTurnForce));
        if(fTurnForce > theForce)
            fResForce *= -1F;
        float fAccelForce = fResForce - com.maddox.il2.objects.weapons.MissilePhysics.getDragInGravity(fSquare, fCw, (float)((com.maddox.JGP.Tuple3d) (p)).z, fSpeed, or.getTangage(), fMassa);
        float fAccel = fAccelForce / fMassa;
        fSpeed += fAccel * fTick;
        if(fSpeed < 3F)
            theFailState = 7;
        ((com.maddox.JGP.Tuple3d) (v)).set(1.0D, 0.0D, 0.0D);
        or.transform(((com.maddox.JGP.Tuple3d) (v)));
        ((com.maddox.JGP.Tuple3d) (v)).scale(fSpeed);
        ((com.maddox.il2.objects.weapons.Rocket)this).setSpeed(v);
        p.x += ((com.maddox.JGP.Tuple3d) (v)).x * (double)fTick;
        p.y += ((com.maddox.JGP.Tuple3d) (v)).y * (double)fTick;
        p.z += ((com.maddox.JGP.Tuple3d) (v)).z * (double)fTick;
        if(((com.maddox.il2.engine.Actor)this).isNet() && ((com.maddox.il2.engine.Actor)this).isNetMirror())
        {
            ((com.maddox.il2.engine.Actor)this).pos.setAbs(p, or);
            return false;
        }
        if(theFailState == 7)
        {
            ((com.maddox.il2.objects.weapons.Rocket)this).doExplosionAir();
            ((com.maddox.il2.engine.Actor)this).postDestroy();
            ((com.maddox.il2.engine.Actor)this).collide(false);
            ((com.maddox.il2.engine.Actor)this).drawing(false);
            return false;
        }
        com.maddox.il2.engine.Actor myOwner = ((com.maddox.il2.engine.Actor)this).getOwner();
        if(victim != null && com.maddox.il2.objects.weapons.GuidedMissileUtils.angleBetween(myOwner, victim) > fMaxFOVfrom)
            victim = null;
        if(victim != null)
        {
            if(myOwner != null)
            {
                float hTurn = 0.0F;
                float vTurn = 0.0F;
                com.maddox.JGP.Point3d pointAC = new Point3d();
                ((com.maddox.JGP.Tuple3d) (pointAC)).set(((com.maddox.JGP.Tuple3d) (myOwner.pos.getAbsPoint())));
                com.maddox.il2.engine.Orient orientAC = new Orient();
                orientAC.set(myOwner.pos.getAbsOrient());
                com.maddox.JGP.Point3d pointTarget = new Point3d();
                ((com.maddox.JGP.Tuple3d) (pointTarget)).set(((com.maddox.JGP.Tuple3d) (victim.pos.getAbsPoint())));
                com.maddox.JGP.Vector3d vectorOffset = new Vector3d();
                com.maddox.il2.engine.Orient orientTarget = new Orient();
                orientTarget.set(victim.pos.getAbsOrient());
                ((com.maddox.JGP.Tuple3d) (vectorOffset)).set(((com.maddox.JGP.Tuple3f) (pVictimOffset)));
                orientTarget.transform(((com.maddox.JGP.Tuple3d) (vectorOffset)));
                ((com.maddox.JGP.Tuple3d) (pointTarget)).add(((com.maddox.JGP.Tuple3d) (vectorOffset)));
                com.maddox.JGP.Point3d pointMissile = new Point3d();
                ((com.maddox.JGP.Tuple3d) (pointMissile)).set(((com.maddox.JGP.Tuple3d) (((com.maddox.il2.engine.Actor)this).pos.getAbsPoint())));
                com.maddox.JGP.Point3d pointMissileAft = new Point3d();
                ((com.maddox.JGP.Tuple3d) (pointMissileAft)).set(((com.maddox.JGP.Tuple3d) (pointMissile)));
                com.maddox.il2.engine.Orient orientMissileAft = new Orient();
                orientMissileAft.set(((com.maddox.il2.engine.Actor)this).pos.getAbsOrient());
                com.maddox.JGP.Point3d pointACAft = new Point3d();
                ((com.maddox.JGP.Tuple3d) (pointACAft)).set(((com.maddox.JGP.Tuple3d) (pointAC)));
                ((com.maddox.JGP.Tuple3d) (pointTarget)).sub(((com.maddox.JGP.Tuple3d) (pointAC)));
                orientAC.transformInv(((com.maddox.JGP.Tuple3d) (pointTarget)));
                float targetAzimuth = (float)java.lang.Math.toDegrees(java.lang.Math.atan(-((com.maddox.JGP.Tuple3d) (pointTarget)).y / ((com.maddox.JGP.Tuple3d) (pointTarget)).x));
                float targetElevation = (float)java.lang.Math.toDegrees(java.lang.Math.atan(((com.maddox.JGP.Tuple3d) (pointTarget)).z / ((com.maddox.JGP.Tuple3d) (pointTarget)).x));
                if(theFailState == 4)
                {
                    targetAzimuth += 180F;
                    targetElevation += 180F;
                    if(targetAzimuth > 180F)
                        targetAzimuth = 180F - targetAzimuth;
                    if(targetElevation > 180F)
                        targetElevation = 180F - targetElevation;
                }
                ((com.maddox.JGP.Tuple3d) (pointMissile)).sub(((com.maddox.JGP.Tuple3d) (pointAC)));
                orientAC.transformInv(((com.maddox.JGP.Tuple3d) (pointMissile)));
                float missileAzimuth = (float)java.lang.Math.toDegrees(java.lang.Math.atan(-((com.maddox.JGP.Tuple3d) (pointMissile)).y / ((com.maddox.JGP.Tuple3d) (pointMissile)).x));
                float missileElevation = (float)java.lang.Math.toDegrees(java.lang.Math.atan(((com.maddox.JGP.Tuple3d) (pointMissile)).z / ((com.maddox.JGP.Tuple3d) (pointMissile)).x));
                float missileOffsetAzimuth = missileAzimuth - targetAzimuth;
                float missileOffsetElevation = missileElevation - targetElevation;
                ((com.maddox.JGP.Tuple3d) (pointACAft)).sub(((com.maddox.JGP.Tuple3d) (pointMissileAft)));
                orientMissileAft.transformInv(((com.maddox.JGP.Tuple3d) (pointACAft)));
                float missileAzimuthAft = (float)java.lang.Math.toDegrees(java.lang.Math.atan(-((com.maddox.JGP.Tuple3d) (pointACAft)).y / ((com.maddox.JGP.Tuple3d) (pointACAft)).x));
                float missileElevationAft = (float)java.lang.Math.toDegrees(java.lang.Math.atan(((com.maddox.JGP.Tuple3d) (pointACAft)).z / ((com.maddox.JGP.Tuple3d) (pointACAft)).x));
                float missileTrackOffsetAzimuth = missileOffsetAzimuth - missileAzimuthAft;
                float missileTrackOffsetElevation = missileOffsetElevation - missileElevationAft;
                float closingFactor = -5F;
                float maxClosing = 60F;
                float fastClosingMax = 3F;
                float turnNormal = 1.0F;
                float turnQuick = 1.5F;
                float turnSharp = 2.0F;
                if(missileOffsetAzimuth < 0.0F)
                {
                    if(missileTrackOffsetAzimuth < 0.0F)
                        hTurn = turnSharp;
                    else
                    if(missileTrackOffsetAzimuth > maxClosing)
                        hTurn = -turnNormal;
                    else
                    if(missileTrackOffsetAzimuth > fastClosingMax && missileTrackOffsetAzimuth > closingFactor * missileOffsetAzimuth)
                        hTurn = -turnQuick;
                    else
                        hTurn = turnNormal;
                } else
                if(missileTrackOffsetAzimuth > 0.0F)
                    hTurn = -turnSharp;
                else
                if(missileTrackOffsetAzimuth < -maxClosing)
                    hTurn = turnNormal;
                else
                if(missileTrackOffsetAzimuth < -fastClosingMax && missileTrackOffsetAzimuth < closingFactor * missileOffsetAzimuth)
                    hTurn = turnQuick;
                else
                    hTurn = -turnNormal;
                if(missileOffsetElevation < 0.0F)
                {
                    if(missileTrackOffsetElevation < 0.0F)
                        vTurn = turnSharp;
                    else
                    if(missileTrackOffsetElevation > maxClosing)
                        vTurn = -turnNormal;
                    else
                    if(missileTrackOffsetElevation > fastClosingMax && missileTrackOffsetElevation > closingFactor * missileOffsetElevation)
                        vTurn = -turnQuick;
                    else
                        vTurn = turnNormal;
                } else
                if(missileTrackOffsetElevation > 0.0F)
                    vTurn = -turnSharp;
                else
                if(missileTrackOffsetElevation < -maxClosing)
                    vTurn = turnNormal;
                else
                if(missileTrackOffsetElevation < -fastClosingMax && missileTrackOffsetElevation < closingFactor * missileOffsetElevation)
                    vTurn = turnQuick;
                else
                    vTurn = -turnNormal;
                iCounter++;
                if(com.maddox.rts.Time.current() > tStart + lTrackDelay)
                {
                    float turnStepMax = com.maddox.il2.objects.weapons.MissilePhysics.getDegPerSec(fSpeed, fMaxG) * fTick * com.maddox.il2.objects.weapons.MissilePhysics.getAirDensityFactor((float)((com.maddox.JGP.Tuple3d) (p)).z);
                    float turnDiffMax = turnStepMax / fStepsForFullTurn;
                    if(theFailState == 5)
                    {
                        if(fIvanTimeLeft < fTick)
                        {
                            if(nextRandomFloat() < 0.5F)
                            {
                                if(nextRandomFloat() < 0.5F)
                                    deltaAzimuth = turnStepMax;
                                else
                                    deltaAzimuth = -turnStepMax;
                                deltaTangage = nextRandomFloat(-turnStepMax, turnStepMax);
                            } else
                            {
                                if(nextRandomFloat() < 0.5F)
                                    deltaTangage = turnStepMax;
                                else
                                    deltaTangage = -turnStepMax;
                                deltaAzimuth = nextRandomFloat(-turnStepMax, turnStepMax);
                            }
                            fIvanTimeLeft = nextRandomFloat(1.0F, 2.0F);
                        } else
                        {
                            deltaAzimuth = oldDeltaAzimuth;
                            deltaTangage = oldDeltaTangage;
                            fIvanTimeLeft -= fTick;
                            if(fIvanTimeLeft < fTick)
                            {
                                iFailState = 0;
                                fIvanTimeLeft = 0.0F;
                            }
                        }
                    } else
                    if(theFailState == 2)
                    {
                        if(nextRandomFloat() < 0.5F)
                            deltaAzimuth = turnStepMax;
                        else
                            deltaAzimuth = -turnStepMax;
                        if(nextRandomFloat() < 0.5F)
                            deltaTangage = turnStepMax;
                        else
                            deltaTangage = -turnStepMax;
                    } else
                    if(theFailState == 6)
                    {
                        deltaAzimuth = oldDeltaAzimuth;
                        deltaTangage = oldDeltaTangage;
                    } else
                    {
                        if(java.lang.Math.abs(targetAzimuth) <= 90F && java.lang.Math.abs(targetElevation) <= 90F)
                        {
                            if(hTurn * oldDeltaAzimuth < 0.0F)
                            {
                                deltaAzimuth = hTurn * turnDiffMax;
                            } else
                            {
                                deltaAzimuth = oldDeltaAzimuth + hTurn * turnDiffMax;
                                if(deltaAzimuth < -turnStepMax)
                                    deltaAzimuth = -turnStepMax;
                                if(deltaAzimuth > turnStepMax)
                                    deltaAzimuth = turnStepMax;
                            }
                            if(vTurn * oldDeltaTangage < 0.0F)
                            {
                                deltaTangage = vTurn * turnDiffMax;
                            } else
                            {
                                deltaTangage = oldDeltaTangage + vTurn * turnDiffMax;
                                if(deltaTangage < -turnStepMax)
                                    deltaTangage = -turnStepMax;
                                if(deltaTangage > turnStepMax)
                                    deltaTangage = turnStepMax;
                            }
                        }
                        oldDeltaAzimuth = deltaAzimuth;
                        oldDeltaTangage = deltaTangage;
                    }
                    or.increment(deltaAzimuth, deltaTangage, 0.0F);
                    or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
                } else
                if(iLaunchType == 2)
                    or.setYPR((float)getLaunchYaw(), (float)getLaunchPitch(), 0.0F);
                else
                    or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
            }
        } else
        if(com.maddox.rts.Time.current() < tStart + lTrackDelay && iLaunchType == 2)
            or.setYPR((float)getLaunchYaw(), (float)getLaunchPitch(), 0.0F);
        else
            or.setYPR(or.getYaw(), or.getPitch(), 0.0F);
        deltaAzimuth = deltaTangage = 0.0F;
        ((com.maddox.il2.engine.Actor)this).pos.setAbs(p, or);
        if(com.maddox.rts.Time.current() > tStart + 2L * lTrackDelay)
            if(com.maddox.il2.engine.Actor.isValid(victim))
            {
                float f2 = (float)p.distance(victim.pos.getAbsPoint());
                if((victim instanceof com.maddox.il2.objects.air.Aircraft) && f2 > prevd && prevd != 1000F)
                {
                    if(f2 < 30F)
                    {
                        ((com.maddox.il2.objects.weapons.Rocket)this).doExplosionAir();
                        ((com.maddox.il2.engine.Actor)this).postDestroy();
                        ((com.maddox.il2.engine.Actor)this).collide(false);
                        ((com.maddox.il2.engine.Actor)this).drawing(false);
                    }
                    if(((com.maddox.il2.objects.weapons.Rocket)this).getSpeed((com.maddox.JGP.Vector3d)null) > victim.getSpeed((com.maddox.JGP.Vector3d)null))
                        victim = null;
                }
                prevd = f2;
            } else
            {
                prevd = 1000F;
            }
        if(!com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor)this).getOwner()) || !(((com.maddox.il2.engine.Actor)this).getOwner() instanceof com.maddox.il2.objects.air.Aircraft))
        {
            ((com.maddox.il2.objects.weapons.Rocket)this).doExplosionAir();
            ((com.maddox.il2.engine.Actor)this).postDestroy();
            ((com.maddox.il2.engine.Actor)this).collide(false);
            ((com.maddox.il2.engine.Actor)this).drawing(false);
            return false;
        } else
        {
            return false;
        }
    }

    public boolean interpolateStep()
    {
        if(com.maddox.rts.Time.current() > tStart + lTrackDelay && (isSunTracking() || isGroundTracking()))
            victim = null;
        switch(iStepMode)
        {
        case 0: // '\0'
            return stepTargetHoming();

        case 1: // '\001'
            return stepBeamRider();
        }
        return true;
    }

    private int getFailState()
    {
        if(lTimeToFailure == 0L)
            return 0;
        long millisecondsFromStart = com.maddox.rts.Time.current() - tStart;
        if(millisecondsFromStart < lTimeToFailure)
            return 0;
        if(iFailState == 1)
        {
            float fRand = nextRandomFloat();
            if((double)fRand < 0.01D)
                return 7;
            if((double)fRand < 0.02D)
                return 4;
            if((double)fRand < 0.20000000000000001D)
                return 2;
            return (double)fRand >= 0.5D ? 5 : 6;
        } else
        {
            return iFailState;
        }
    }

    private void setFailState()
    {
        if(iFailState == 0)
            iFailState = nextRandomInt(1, 7);
    }

    public Missile()
    {
        fm = null;
        or = new Orient();
        p = new Point3d();
        orVictimOffset = null;
        pVictimOffset = null;
        pT = null;
        v = null;
        victimSpeed = null;
        tStart = 0L;
        prevd = 0.0F;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        d = 0.0D;
        victim = null;
        fMissileBaseSpeed = 0.0F;
        launchKren = 0.0D;
        launchPitch = 0.0D;
        launchYaw = 0.0D;
        endedFlame = false;
        endedSmoke = false;
        endedSprite = false;
        flameActive = true;
        smokeActive = true;
        spriteActive = true;
        iCounter = 0;
        iStepMode = 0;
        fMaxFOVfrom = 0.0F;
        fLeadPercent = 0.0F;
        fMaxG = 12F;
        fStepsForFullTurn = 10F;
        fTimeLife = 30F;
        fTimeFire = 2.2F;
        fForce = 18712F;
        lTimeToFailure = 0L;
        fSunRayAngle = 0.0F;
        fGroundTrackFactor = 0.0F;
        fMaxLaunchG = 2.0F;
        iLaunchType = 0;
        fKalibr = 0.2F;
        fMassa = 86.2F;
        fMassaEnd = 86.2F;
        fSquare = 1.0F;
        fCw = 0.3F;
        lTrackDelay = 1000L;
        lLastTimeNoFlare = 0L;
        lFlareLockTime = 1000L;
        fDiffM = 0.0F;
        fT1 = 0.0F;
        fP1 = 0.0F;
        fT2 = 0.0F;
        fP2 = 0.0F;
        iFailState = 0;
        fIvanTimeLeft = 0.0F;
        smokes = null;
        sprites = null;
        flames = null;
        sEffSmoke = null;
        sEffSprite = null;
        sSimFlame = null;
        iExhausts = 1;
        firstNavLight = null;
        lastNavLight = null;
        pT = new Point3d();
        v = new Vector3d();
        victimSpeed = new Vector3d();
        orVictimOffset = new Orient();
        pVictimOffset = new Point3f();
        MissileInit();
    }

    public final void MissileInit()
    {
        d = 0.10000000000000001D;
        victim = null;
        fm = null;
        tStart = 0L;
        prevd = 1000F;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        oldDeltaAzimuth = 0.0F;
        oldDeltaTangage = 0.0F;
        endedFlame = false;
        endedSmoke = false;
        endedSprite = false;
        flameActive = true;
        smokeActive = true;
        spriteActive = true;
    }

    public Missile(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
    {
        fm = null;
        or = new Orient();
        p = new Point3d();
        orVictimOffset = null;
        pVictimOffset = null;
        pT = null;
        v = null;
        victimSpeed = null;
        tStart = 0L;
        prevd = 0.0F;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
        d = 0.0D;
        victim = null;
        fMissileBaseSpeed = 0.0F;
        launchKren = 0.0D;
        launchPitch = 0.0D;
        launchYaw = 0.0D;
        endedFlame = false;
        endedSmoke = false;
        endedSprite = false;
        flameActive = true;
        smokeActive = true;
        spriteActive = true;
        iCounter = 0;
        iStepMode = 0;
        fMaxFOVfrom = 0.0F;
        fLeadPercent = 0.0F;
        fMaxG = 12F;
        fStepsForFullTurn = 10F;
        fTimeLife = 30F;
        fTimeFire = 2.2F;
        fForce = 18712F;
        lTimeToFailure = 0L;
        fSunRayAngle = 0.0F;
        fGroundTrackFactor = 0.0F;
        fMaxLaunchG = 2.0F;
        iLaunchType = 0;
        fKalibr = 0.2F;
        fMassa = 86.2F;
        fMassaEnd = 86.2F;
        fSquare = 1.0F;
        fCw = 0.3F;
        lTrackDelay = 1000L;
        lLastTimeNoFlare = 0L;
        lFlareLockTime = 1000L;
        fDiffM = 0.0F;
        fT1 = 0.0F;
        fP1 = 0.0F;
        fT2 = 0.0F;
        fP2 = 0.0F;
        iFailState = 0;
        fIvanTimeLeft = 0.0F;
        smokes = null;
        sprites = null;
        flames = null;
        sEffSmoke = null;
        sEffSprite = null;
        sSimFlame = null;
        iExhausts = 1;
        firstNavLight = null;
        lastNavLight = null;
        MissileInit(actor, netchannel, i, point3d, orient, f);
    }

    public final void MissileInit(com.maddox.il2.engine.Actor actor, com.maddox.rts.NetChannel netchannel, int i, com.maddox.JGP.Point3d point3d, com.maddox.il2.engine.Orient orient, float f)
    {
        if(com.maddox.il2.engine.Actor.isValid(actor) && !com.maddox.il2.engine.Actor.isValid(((com.maddox.il2.engine.Actor)this).getOwner()))
            ((com.maddox.il2.engine.Actor)this).setOwner(actor);
        d = 0.10000000000000001D;
        victim = null;
        fm = null;
        tStart = 0L;
        prevd = 1000F;
        oldDeltaAzimuth = 0.0F;
        oldDeltaTangage = 0.0F;
        endedFlame = false;
        endedSmoke = false;
        endedSprite = false;
        flameActive = true;
        smokeActive = true;
        spriteActive = true;
        ((com.maddox.il2.engine.Actor)this).net = ((com.maddox.il2.engine.ActorNet) (new Mirror(((com.maddox.il2.engine.Actor) (this)), netchannel, i)));
        ((com.maddox.il2.engine.Actor)this).pos.setAbs(point3d, orient);
        ((com.maddox.il2.engine.Actor)this).pos.reset();
        ((com.maddox.il2.engine.Actor)this).pos.setBase(actor, ((com.maddox.il2.engine.Hook) (null)), true);
        doStart(-1F);
        ((com.maddox.JGP.Tuple3d) (v)).set(1.0D, 0.0D, 0.0D);
        orient.transform(((com.maddox.JGP.Tuple3d) (v)));
        ((com.maddox.JGP.Tuple3d) (v)).scale(f);
        ((com.maddox.il2.objects.weapons.Rocket)this).setSpeed(v);
        ((com.maddox.il2.engine.Actor)this).collide(false);
    }

    private void initRandom()
    {
        if(theRangeRandom != null)
        {
            return;
        } else
        {
            long lTime = java.lang.System.currentTimeMillis();
            java.security.SecureRandom secRandom = new SecureRandom();
            secRandom.setSeed(lTime);
            long lSeed1 = ((java.util.Random) (secRandom)).nextInt();
            long lSeed2 = ((java.util.Random) (secRandom)).nextInt();
            long lSeed = (lSeed1 << 32) + lSeed2;
            theRangeRandom = new RangeRandom(lSeed);
            return;
        }
    }

    private float nextRandomFloat(float fMin, float fMax)
    {
        initRandom();
        return theRangeRandom.nextFloat(fMin, fMax);
    }

    private float nextRandomFloat()
    {
        initRandom();
        return ((java.util.Random) (theRangeRandom)).nextFloat();
    }

    private int nextRandomInt(int iMin, int iMax)
    {
        initRandom();
        return theRangeRandom.nextInt(iMin, iMax);
    }

    public void start(float f)
    {
        start(f, 0);
    }

    public void start(float f, int paramInt)
    {
        com.maddox.il2.engine.Actor actor = ((com.maddox.il2.engine.Actor)this).pos.base();
        if(!com.maddox.il2.engine.Actor.isValid(actor) || !(actor instanceof com.maddox.il2.objects.air.Aircraft))
            break MISSING_BLOCK_LABEL_86;
        if(actor.isNetMirror())
        {
            destroy();
            return;
        }
        try
        {
            ((com.maddox.il2.engine.Actor)this).net = ((com.maddox.il2.engine.ActorNet) (new Master(((com.maddox.il2.engine.Actor) (this)))));
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.game.NetSafeLog.log(((com.maddox.il2.engine.Actor)this).getOwner(), "Missile launch cancelled (system error):" + ((java.lang.Throwable) (exception)).getMessage());
            destroy();
        }
        doStart(f);
        return;
    }

    private void getMissileProperties()
    {
        java.lang.Class localClass = ((java.lang.Object)this).getClass();
        float f = com.maddox.rts.Time.tickLenFs();
        iLaunchType = com.maddox.rts.Property.intValue(localClass, "launchType", 0);
        iStepMode = com.maddox.rts.Property.intValue(localClass, "stepMode", 0);
        fMaxFOVfrom = com.maddox.rts.Property.floatValue(localClass, "maxFOVfrom", 180F);
        fMaxLaunchG = com.maddox.rts.Property.floatValue(localClass, "maxLockGForce", 99.9F);
        fMaxG = com.maddox.rts.Property.floatValue(localClass, "maxGForce", 12F);
        fStepsForFullTurn = com.maddox.rts.Property.floatValue(localClass, "stepsForFullTurn", 10F);
        fTimeFire = com.maddox.rts.Property.floatValue(localClass, "timeFire", 2.2F) * 1000F;
        fTimeLife = com.maddox.rts.Property.floatValue(localClass, "timeLife", 30F) * 1000F;
        super.timeFire = (long)fTimeFire;
        super.timeLife = (long)fTimeLife;
        fForce = com.maddox.rts.Property.floatValue(localClass, "force", 18712F);
        fLeadPercent = com.maddox.rts.Property.floatValue(localClass, "leadPercent", 0.0F);
        fKalibr = com.maddox.rts.Property.floatValue(localClass, "kalibr", 0.2F);
        fMassa = com.maddox.rts.Property.floatValue(localClass, "massa", 86.2F);
        fMassaEnd = com.maddox.rts.Property.floatValue(localClass, "massaEnd", 80F);
        fSunRayAngle = com.maddox.rts.Property.floatValue(localClass, "sunRayAngle", 0.0F);
        fGroundTrackFactor = com.maddox.rts.Property.floatValue(localClass, "groundTrackFactor", 0.0F);
        lFlareLockTime = com.maddox.rts.Property.longValue(localClass, "flareLockTime", 1000L);
        lTrackDelay = com.maddox.rts.Property.longValue(localClass, "trackDelay", 1000L);
        fSquare = (3.141593F * fKalibr * fKalibr) / 4F;
        fT1 = com.maddox.rts.Property.floatValue(localClass, "forceT1", 0.0F) * 1000F;
        fP1 = com.maddox.rts.Property.floatValue(localClass, "forceP1", 0.0F);
        fT2 = com.maddox.rts.Property.floatValue(localClass, "forceT2", 0.0F) * 1000F;
        fP2 = com.maddox.rts.Property.floatValue(localClass, "forceP2", 0.0F);
        fCw = com.maddox.rts.Property.floatValue(localClass, "dragCoefficient", 0.3F);
        iExhausts = getNumExhausts();
        sEffSmoke = com.maddox.rts.Property.stringValue(localClass, "smoke", ((java.lang.String) (null)));
        sEffSprite = com.maddox.rts.Property.stringValue(localClass, "sprite", ((java.lang.String) (null)));
        sSimFlame = com.maddox.rts.Property.stringValue(localClass, "flame", ((java.lang.String) (null)));
        float fFailureRate = com.maddox.rts.Property.floatValue(localClass, "failureRate", 10F);
        if(nextRandomFloat(0.0F, 100F) < fFailureRate)
        {
            setFailState();
            float fRand = nextRandomFloat();
            fRand = fRand * fRand * fRand * fRand;
            long lBaseFailTime = lTrackDelay;
            if(iFailState == 7)
                lBaseFailTime += lBaseFailTime;
            lTimeToFailure = lBaseFailTime + (long)((fTimeLife - (float)lBaseFailTime) * fRand);
        } else
        {
            iFailState = 0;
            lTimeToFailure = 0L;
        }
        if(fTimeFire > 0.0F)
            fDiffM = (fMassa - fMassaEnd) / (fTimeFire / 1000F / com.maddox.rts.Time.tickConstLenFs());
        else
            fDiffM = 0.0F;
    }

    private void createAdditionalSmokes()
    {
        smokes = new com.maddox.il2.engine.Eff3DActor[iExhausts];
        smokes[0] = super.smoke;
        if(sEffSmoke == null)
            return;
        com.maddox.il2.engine.Hook theHook = null;
        for(int i = 1; i < iExhausts; i++)
        {
            theHook = ((com.maddox.il2.engine.ActorMesh)this).findHook(((java.lang.Object) ("_SMOKE" + i)));
            if(theHook == null)
            {
                smokes[i] = null;
                continue;
            }
            smokes[i] = com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), theHook, ((com.maddox.il2.engine.Loc) (null)), 1.0F, sEffSmoke, -1F);
            if(smokes[i] != null)
                ((com.maddox.il2.engine.Actor) (smokes[i])).pos.changeHookToRel();
        }

    }

    private void createAdditionalSprites()
    {
        sprites = new com.maddox.il2.engine.Eff3DActor[iExhausts];
        sprites[0] = super.sprite;
        if(sEffSprite == null)
            return;
        com.maddox.il2.engine.Hook theHook = null;
        for(int i = 1; i < iExhausts; i++)
        {
            theHook = ((com.maddox.il2.engine.ActorMesh)this).findHook(((java.lang.Object) ("_SMOKE" + i)));
            if(theHook == null)
            {
                sprites[i] = null;
                continue;
            }
            sprites[i] = com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), theHook, ((com.maddox.il2.engine.Loc) (null)), fKalibr, sEffSprite, -1F);
            if(sprites[i] != null)
                ((com.maddox.il2.engine.Actor) (sprites[i])).pos.changeHookToRel();
        }

    }

    private void createAdditionalFlames()
    {
        flames = new com.maddox.il2.engine.Actor[iExhausts];
        flames[0] = super.flame;
        if(sSimFlame == null)
            return;
        com.maddox.il2.engine.Hook theHook = null;
        for(int i = 1; i < iExhausts; i++)
        {
            theHook = ((com.maddox.il2.engine.ActorMesh)this).findHook(((java.lang.Object) ("_SMOKE" + i)));
            flames[i] = ((com.maddox.il2.engine.Actor) (new ActorSimpleMesh(sSimFlame)));
            if(flames[i] != null)
            {
                ((com.maddox.il2.engine.ActorMesh) ((com.maddox.il2.objects.ActorSimpleMesh)flames[i])).mesh().setScale(1.0F);
                flames[i].pos.setBase(((com.maddox.il2.engine.Actor) (this)), theHook, false);
                flames[i].pos.changeHookToRel();
                flames[i].pos.resetAsBase();
            }
        }

    }

    private void endNavLights()
    {
        for(com.maddox.il2.objects.weapons.MissileNavLight theNavLight = firstNavLight; theNavLight != null; theNavLight = theNavLight.nextNavLight)
            com.maddox.il2.engine.Eff3DActor.finish(theNavLight.theNavLight);

    }

    private void createNavLights()
    {
        createNamedNavLights("_NavLightR", "3DO/Effects/Fireworks/FlareRed.eff");
        createNamedNavLights("_NavLightG", "3DO/Effects/Fireworks/FlareGreen.eff");
        createNamedNavLights("_NavLightW", "3DO/Effects/Fireworks/FlareWhite.eff");
        createNamedNavLights("_NavLightP", "3DO/Effects/Fireworks/PhosfourousBall.eff");
    }

    private void createNamedNavLights(java.lang.String theNavLightHookName, java.lang.String theEffectName)
    {
        int numNavLights = getNumNavLights(theNavLightHookName);
        if(numNavLights == 0)
            return;
        com.maddox.il2.engine.Hook theHook = null;
        for(int i = 0; i < numNavLights; i++)
        {
            if(i == 0)
                theHook = ((com.maddox.il2.engine.ActorMesh)this).findHook(((java.lang.Object) (theNavLightHookName)));
            else
                theHook = ((com.maddox.il2.engine.ActorMesh)this).findHook(((java.lang.Object) (theNavLightHookName + i)));
            com.maddox.il2.objects.weapons.MissileNavLight theNavLight = new MissileNavLight(com.maddox.il2.engine.Eff3DActor.New(((com.maddox.il2.engine.Actor) (this)), theHook, ((com.maddox.il2.engine.Loc) (null)), 1.0F, theEffectName, -1F));
            if(firstNavLight == null)
            {
                firstNavLight = theNavLight;
                lastNavLight = theNavLight;
            } else
            {
                lastNavLight.nextNavLight = theNavLight;
                lastNavLight = theNavLight;
            }
        }

    }

    private int getNumNavLights(java.lang.String theNavLightHookName)
    {
        if(((com.maddox.il2.engine.ActorMesh)this).mesh.hookFind(theNavLightHookName) == -1)
            return 0;
        int retVal;
        for(retVal = 1; ((com.maddox.il2.engine.ActorMesh)this).mesh.hookFind(theNavLightHookName + retVal) != -1; retVal++);
        return retVal;
    }

    private int getNumExhausts()
    {
        if(((com.maddox.il2.engine.ActorMesh)this).mesh.hookFind("_SMOKE") == -1)
            return 0;
        int retVal;
        for(retVal = 1; ((com.maddox.il2.engine.ActorMesh)this).mesh.hookFind("_SMOKE" + retVal) != -1; retVal++);
        return retVal;
    }

    private void doStart(float f)
    {
        super.start(-1F, 0);
        getMissileProperties();
        if(iExhausts > 1)
        {
            createAdditionalSmokes();
            createAdditionalSprites();
            createAdditionalFlames();
        }
        firstNavLight = null;
        lastNavLight = null;
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            createNavLights();
        ((com.maddox.il2.engine.ActorMesh) ((com.maddox.il2.objects.ActorSimpleMesh)super.flame)).mesh().setScale(1.0F);
        prevd = 1000F;
        ((com.maddox.il2.engine.Actor)this).pos.getRelOrient().transformInv(((com.maddox.JGP.Tuple3d) (super.speed)));
        super.speed.y *= 3D;
        super.speed.z *= 3D;
        super.speed.x -= 198D;
        ((com.maddox.il2.engine.Actor)this).pos.getRelOrient().transform(((com.maddox.JGP.Tuple3d) (super.speed)));
        tStart = com.maddox.rts.Time.current();
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
            super.flame.drawing(true);
        ((com.maddox.il2.engine.Actor)this).pos.getAbs(p, or);
        fMissileBaseSpeed = (float)((com.maddox.il2.objects.weapons.Rocket)this).getSpeed((com.maddox.JGP.Vector3d)null);
        if(((com.maddox.il2.engine.Actor)this).isNet() && ((com.maddox.il2.engine.Actor)this).isNetMirror())
            return;
        fm = ((com.maddox.il2.objects.sounds.SndAircraft) ((com.maddox.il2.objects.air.Aircraft)((com.maddox.il2.engine.Actor)this).getOwner())).FM;
        switch(iLaunchType)
        {
        case 1: // '\001'
            fMissileBaseSpeed += 20F;
            ((com.maddox.JGP.Tuple3d) (v)).set(1.0D, 0.0D, 0.0D);
            or.transform(((com.maddox.JGP.Tuple3d) (v)));
            ((com.maddox.JGP.Tuple3d) (v)).scale(fMissileBaseSpeed);
            ((com.maddox.il2.objects.weapons.Rocket)this).setSpeed(v);
            launchKren = java.lang.Math.toRadians(or.getKren());
            launchYaw = or.getYaw();
            launchPitch = or.getPitch();
            or.setYPR((float)launchYaw, (float)launchPitch, 0.0F);
            ((com.maddox.il2.engine.Actor)this).pos.setAbs(p, or);
            break;

        case 2: // '\002'
            launchKren = java.lang.Math.toRadians(or.getKren());
            launchYaw = or.getYaw() + ((com.maddox.il2.fm.FlightModelMain) (fm)).getAOA() * (float)java.lang.Math.sin(launchKren);
            launchPitch = or.getPitch() - ((com.maddox.il2.fm.FlightModelMain) (fm)).getAOA() * (float)java.lang.Math.cos(launchKren);
            or.setYPR((float)launchYaw + 0.5F * (float)java.lang.Math.sin(launchKren), (float)launchPitch - 0.5F * (float)java.lang.Math.cos(launchKren), 0.0F);
            ((com.maddox.il2.engine.Actor)this).pos.setAbs(p, or);
            break;
        }
        victim = null;
        if((((com.maddox.il2.engine.Actor)this).getOwner() != com.maddox.il2.ai.World.getPlayerAircraft() || !((com.maddox.il2.fm.RealFlightModel)fm).isRealMode()) && (fm instanceof com.maddox.il2.ai.air.Pilot))
        {
            if(((com.maddox.il2.engine.Actor)this).getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier)
            {
                victim = ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Actor)this).getOwner()).getMissileTarget();
                pVictimOffset = ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Actor)this).getOwner()).getMissileTargetOffset();
            } else
            if(((com.maddox.il2.engine.Actor)this).getOwner() instanceof com.maddox.il2.objects.air.TypeFighter)
            {
                com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)fm;
                victim = ((com.maddox.il2.engine.Interpolate) (((com.maddox.il2.ai.air.Maneuver) (pilot)).target)).actor;
            }
            break MISSING_BLOCK_LABEL_774;
        }
        if(((com.maddox.il2.fm.FlightModelMain) (fm)).getOverload() > fMaxLaunchG)
        {
            victim = null;
            return;
        }
        try
        {
            if(((com.maddox.il2.engine.Actor)this).getOwner() instanceof com.maddox.il2.objects.air.TypeGuidedMissileCarrier)
            {
                victim = ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Actor)this).getOwner()).getMissileTarget();
                pVictimOffset = ((com.maddox.il2.objects.air.TypeGuidedMissileCarrier)((com.maddox.il2.engine.Actor)this).getOwner()).getMissileTargetOffset();
            } else
            {
                victim = com.maddox.il2.game.Selector.look(true, false, com.maddox.il2.game.Main3D.cur3D()._camera3D[com.maddox.il2.game.Main3D.cur3D().getRenderIndx()], ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft())).getArmy(), -1, ((com.maddox.il2.engine.Actor) (com.maddox.il2.ai.World.getPlayerAircraft())), false);
                if(victim == null)
                    victim = com.maddox.il2.game.Main3D.cur3D().getViewPadlockEnemy();
            }
        }
        catch(java.lang.Exception exception)
        {
            com.maddox.il2.ai.EventLog.type("Missile doStart Exception: " + ((java.lang.Throwable) (exception)).getMessage());
        }
    }

    public void destroy()
    {
        if(((com.maddox.il2.engine.Actor)this).isNet() && ((com.maddox.il2.engine.Actor)this).isNetMirror())
            ((com.maddox.il2.objects.weapons.Rocket)this).doExplosionAir();
        endNavLights();
        flameActive = false;
        smokeActive = false;
        spriteActive = false;
        endSmoke();
        victim = null;
        fm = null;
        tStart = 0L;
        prevd = 1000F;
        super.destroy();
    }

    private boolean isSunTracking()
    {
        if(fSunRayAngle == 0.0F)
            return false;
        float sunAngle = com.maddox.il2.objects.weapons.GuidedMissileUtils.angleBetween(((com.maddox.il2.engine.Actor) (this)), com.maddox.il2.ai.World.Sun().ToSun);
        return sunAngle < fSunRayAngle;
    }

    private boolean isGroundTracking()
    {
        if(fGroundTrackFactor == 0.0F)
            return false;
        ((com.maddox.il2.engine.Actor)this).pos.getAbs(p, or);
        or.wrap();
        float ttG = or.getTangage() * -1F;
        float missileAlt = (float)(((com.maddox.JGP.Tuple3d) (p)).z - com.maddox.il2.engine.Engine.land().HQ_Air(((com.maddox.JGP.Tuple3d) (p)).x, ((com.maddox.JGP.Tuple3d) (p)).y));
        missileAlt /= 1000F;
        float groundFactor = ttG / (missileAlt * missileAlt);
        long lTimeCurrent = com.maddox.rts.Time.current();
        if(lLastTimeNoFlare == 0L)
            lLastTimeNoFlare = lTimeCurrent;
        if(groundFactor > fGroundTrackFactor)
        {
            return lTimeCurrent >= lLastTimeNoFlare + lFlareLockTime;
        } else
        {
            lLastTimeNoFlare = lTimeCurrent;
            return false;
        }
    }

    public com.maddox.rts.NetMsgSpawn netReplicate(com.maddox.rts.NetChannel netchannel)
        throws java.io.IOException
    {
        com.maddox.rts.NetMsgSpawn netmsgspawn = ((com.maddox.il2.engine.Actor)this).netReplicate(netchannel);
        ((com.maddox.rts.NetMsgOutput) (netmsgspawn)).writeNetObj(((com.maddox.rts.NetObj) (((com.maddox.il2.engine.Actor)this).getOwner().net)));
        com.maddox.JGP.Point3d point3d = ((com.maddox.il2.engine.Actor)this).pos.getAbsPoint();
        ((com.maddox.rts.NetMsgOutput) (netmsgspawn)).writeFloat((float)((com.maddox.JGP.Tuple3d) (point3d)).x);
        ((com.maddox.rts.NetMsgOutput) (netmsgspawn)).writeFloat((float)((com.maddox.JGP.Tuple3d) (point3d)).y);
        ((com.maddox.rts.NetMsgOutput) (netmsgspawn)).writeFloat((float)((com.maddox.JGP.Tuple3d) (point3d)).z);
        com.maddox.il2.engine.Orient orient = ((com.maddox.il2.engine.Actor)this).pos.getAbsOrient();
        ((com.maddox.rts.NetMsgOutput) (netmsgspawn)).writeFloat(orient.azimut());
        ((com.maddox.rts.NetMsgOutput) (netmsgspawn)).writeFloat(orient.tangage());
        float f = (float)((com.maddox.il2.objects.weapons.Rocket)this).getSpeed(((com.maddox.JGP.Vector3d) (null)));
        ((com.maddox.rts.NetMsgOutput) (netmsgspawn)).writeFloat(f);
        return netmsgspawn;
    }

    private com.maddox.il2.fm.FlightModel fm;
    private com.maddox.il2.engine.Orient or;
    private com.maddox.JGP.Point3d p;
    private com.maddox.il2.engine.Orient orVictimOffset;
    private com.maddox.JGP.Point3f pVictimOffset;
    private com.maddox.JGP.Point3d pT;
    private com.maddox.JGP.Vector3d v;
    private com.maddox.JGP.Vector3d victimSpeed;
    private long tStart;
    private float prevd;
    private float deltaAzimuth;
    private float deltaTangage;
    private double d;
    private com.maddox.il2.engine.Actor victim;
    private float fMissileBaseSpeed;
    private double launchKren;
    private double launchPitch;
    private double launchYaw;
    private float oldDeltaAzimuth;
    private float oldDeltaTangage;
    private boolean endedFlame;
    private boolean endedSmoke;
    private boolean endedSprite;
    private boolean flameActive;
    private boolean smokeActive;
    private boolean spriteActive;
    private int iCounter;
    private int iStepMode;
    private float fMaxFOVfrom;
    private float fLeadPercent;
    private float fMaxG;
    private float fStepsForFullTurn;
    private float fTimeLife;
    private float fTimeFire;
    private float fForce;
    private long lTimeToFailure;
    private float fSunRayAngle;
    private float fGroundTrackFactor;
    private float fMaxLaunchG;
    private int iLaunchType;
    private float fKalibr;
    private float fMassa;
    private float fMassaEnd;
    private float fSquare;
    private float fCw;
    private long lTrackDelay;
    private long lLastTimeNoFlare;
    private long lFlareLockTime;
    private float fDiffM;
    private float fT1;
    private float fP1;
    private float fT2;
    private float fP2;
    private int iFailState;
    private float fIvanTimeLeft;
    private com.maddox.il2.engine.Eff3DActor smokes[];
    private com.maddox.il2.engine.Eff3DActor sprites[];
    private com.maddox.il2.engine.Actor flames[];
    private java.lang.String sEffSmoke;
    private java.lang.String sEffSprite;
    private java.lang.String sSimFlame;
    private int iExhausts;
    private com.maddox.il2.objects.weapons.MissileNavLight firstNavLight;
    private com.maddox.il2.objects.weapons.MissileNavLight lastNavLight;
    protected static final int LAUNCH_TYPE_STRAIGHT = 0;
    protected static final int LAUNCH_TYPE_QUICK = 1;
    protected static final int LAUNCH_TYPE_DROP = 2;
    protected static final int STEP_MODE_HOMING = 0;
    protected static final int STEP_MODE_BEAMRIDER = 1;
    protected static final int DETECTOR_TYPE_MANUAL = 0;
    protected static final int DETECTOR_TYPE_INFRARED = 1;
    protected static final int DETECTOR_TYPE_RADAR_HOMING = 2;
    protected static final int DETECTOR_TYPE_RADAR_BEAMRIDING = 3;
    protected static final int DETECTOR_TYPE_RADAR_TRACK_VIA_MISSILE = 4;
    protected static final int FAIL_TYPE_NONE = 0;
    protected static final int FAIL_TYPE_ELECTRONICS = 1;
    protected static final int FAIL_TYPE_MIRROR = 2;
    protected static final int FAIL_TYPE_ENGINE = 3;
    protected static final int FAIL_TYPE_REFLEX = 4;
    protected static final int FAIL_TYPE_IVAN = 5;
    protected static final int FAIL_TYPE_CONTROL_BLOCKED = 6;
    protected static final int FAIL_TYPE_WARHEAD = 7;
    protected static final int FAIL_TYPE_NUMBER = 7;
    private static final float IVAN_TIME_MIN = 1F;
    private static final float IVAN_TIME_MAX = 2F;
    private static com.maddox.il2.ai.RangeRandom theRangeRandom;
}
