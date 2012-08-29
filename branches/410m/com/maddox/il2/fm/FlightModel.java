// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   FlightModel.java

package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.JGP.Vector3f;
import com.maddox.il2.ai.Airport;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.War;
import com.maddox.il2.ai.Way;
import com.maddox.il2.ai.WayPoint;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.ground.ScareEnemies;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.CollideEnv;
import com.maddox.il2.engine.Engine;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.engine.Loc;
import com.maddox.il2.engine.Orient;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.engine.Sun;
import com.maddox.il2.game.Mission;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.NetAircraft;
import com.maddox.il2.objects.air.TypeDiveBomber;
import com.maddox.il2.objects.air.TypeFighter;
import com.maddox.il2.objects.air.TypeStormovik;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.fm:
//            FlightModelMain, Turret, Gear, AircraftState, 
//            Controls, Autopilotage

public class FlightModel extends com.maddox.il2.fm.FlightModelMain
{

    public FlightModel(java.lang.String s)
    {
        super(s);
        turnOffCollisions = false;
        brakeShoe = false;
        brakeShoeLoc = new Loc();
        brakeShoeLastCarrier = null;
        canChangeBrakeShoe = false;
        dryFriction = 1.0F;
    }

    public void setSkill(int i)
    {
        if(i < 0)
            i = 0;
        if(i > 3)
            i = 3;
        Skill = i;
        turretSkill = i;
        com.maddox.il2.ai.World.cur();
        if(actor != com.maddox.il2.ai.World.getPlayerAircraft() && !((com.maddox.il2.objects.air.Aircraft)actor).isNetPlayer())
            switch(i)
            {
            case 0: // '\0'
                SensPitch *= 0.75F;
                SensRoll *= 0.5F;
                SensYaw *= 0.5F;
                break;

            case 1: // '\001'
                SensRoll *= 0.7F;
                SensPitch *= 0.75F;
                SensYaw *= 0.7F;
                break;

            case 2: // '\002'
                SensRoll *= 0.88F;
                SensPitch *= 0.92F;
                SensYaw *= 0.9F;
                break;

            case 3: // '\003'
                SensPitch *= 1.0F;
                SensRoll *= 1.0F;
                SensYaw *= 1.0F;
                break;
            }
        else
            com.maddox.il2.objects.air.Aircraft.debugprintln(actor, "Skill adjustment rejected on the Player AI parameters..");
    }

    public void set(com.maddox.il2.engine.HierMesh hiermesh)
    {
        HM = hiermesh;
        am = (com.maddox.il2.engine.ActorHMesh)actor;
        int j;
        for(j = 1; j <= 9 && HM.chunkFindCheck("Turret" + j + "A_D0") >= 0 && HM.chunkFindCheck("Turret" + j + "B_D0") >= 0; j++);
        j--;
        turret = new com.maddox.il2.fm.Turret[j];
        for(int i = 0; i < j; i++)
        {
            turret[i] = new Turret();
            turret[i].indexA = HM.chunkFind("Turret" + (i + 1) + "A_D0");
            turret[i].indexB = HM.chunkFind("Turret" + (i + 1) + "B_D0");
            tu[0] = tu[1] = tu[2] = 0.0F;
            HM.setCurChunk(turret[i].indexA);
            am.hierMesh().chunkSetAngles(tu);
            HM.setCurChunk(turret[i].indexB);
            am.hierMesh().chunkSetAngles(tu);
            am.getChunkLoc(turret[i].Lstart);
        }

        Gears.set(hiermesh);
    }

    private void updateRotation(com.maddox.il2.fm.Turret turret1, float f)
    {
        tu[0] = turret1.tuLim[0];
        tu[1] = turret1.tuLim[1];
        float f1 = 10F * f;
        float f2 = tu[0] - turret1.tu[0];
        if(f2 < -f1)
            f2 = -f1;
        else
        if(f2 > f1)
            f2 = f1;
        tu[0] = turret1.tu[0] + f2;
        turret1.tu[0] = tu[0];
        float f3 = tu[1] - turret1.tu[1];
        if(f3 < -f1)
            f3 = -f1;
        else
        if(f3 > f1)
            f3 = f1;
        tu[1] = turret1.tu[1] + f3;
        turret1.tu[1] = tu[1];
        if(f2 == 0.0F && f3 == 0.0F)
        {
            return;
        } else
        {
            float f4 = tu[0];
            tu[0] = 0.0F;
            HM.setCurChunk(turret1.indexB);
            am.hierMesh().chunkSetAngles(tu);
            tu[1] = f4;
            HM.setCurChunk(turret1.indexA);
            am.hierMesh().chunkSetAngles(tu);
            return;
        }
    }

    private boolean isNightTargetVisible(com.maddox.il2.engine.Actor actor, float f)
    {
        float f1 = com.maddox.il2.ai.World.Sun().sunMultiplier;
        int i = com.maddox.il2.game.Mission.curCloudsType();
        float f2 = com.maddox.il2.game.Mission.curCloudsHeight();
        float f3 = 500F;
        if(am.pos.getAbs().getZ() < actor.pos.getAbs().getZ())
        {
            if(actor.pos.getAbs().getZ() < (double)(f2 + f3))
                if(i > 2)
                    f1 *= 1.2F;
                else
                if(i > 3)
                    f1 *= 1.3F;
            f1 *= 1.1F;
        } else
        if(actor.pos.getAbs().getZ() > (double)(f2 + f3))
            if(i > 2)
                f1 *= 1.2F;
            else
            if(i > 3)
                f1 *= 1.3F;
        v3.sub(am.pos.getAbsPoint(), actor.pos.getAbsPoint());
        float f4 = com.maddox.il2.fm.FlightModel.cvt(f, 0.0F, 3F, 0.75F, 1.2F);
        float f5 = com.maddox.il2.fm.FlightModel.cvt((float)v3.length(), 0.0F, 800F, 1.0F, 0.1F);
        float f6 = com.maddox.il2.fm.FlightModel.cvt(f1, 0.095F, 1.0F, 1E-005F, 0.05F) * f5 * f4;
        float f7 = com.maddox.il2.ai.World.Rnd().nextFloat();
        return f7 < f6;
    }

    private boolean isComingFromTheSun(com.maddox.il2.engine.Actor actor)
    {
        if((actor instanceof com.maddox.il2.objects.air.Aircraft) && com.maddox.il2.ai.World.Sun().ToSun.z > 0.0F)
        {
            if(com.maddox.il2.game.Mission.curCloudsType() > 3 && actor.pos.getAbs().getZ() < (double)(com.maddox.il2.game.Mission.curCloudsHeight() + 200F))
                return false;
            v3.set(com.maddox.il2.ai.World.Sun().ToSun.x, com.maddox.il2.ai.World.Sun().ToSun.y, com.maddox.il2.ai.World.Sun().ToSun.z);
            v2.sub(am.pos.getAbsPoint(), actor.pos.getAbsPoint());
            float f = (float)v2.length();
            v2.normalize();
            double d = v3.angle(v2);
            float f1 = com.maddox.il2.fm.FlightModel.cvt(f, 100F, 3000F, 2.9F, 3F);
            if(d > (double)f1)
                return true;
        }
        return false;
    }

    private boolean isTargetExposed(com.maddox.il2.engine.Actor actor)
    {
        if(actor instanceof com.maddox.il2.objects.air.Aircraft)
        {
            com.maddox.il2.objects.air.Aircraft aircraft = (com.maddox.il2.objects.air.Aircraft)actor;
            if(aircraft.FM.AS.bLandingLightOn || aircraft.FM.AS.bNavLightsOn || aircraft.FM.CT.WeaponControl[0] || aircraft.FM.CT.WeaponControl[1])
                return true;
            if(com.maddox.il2.ai.World.Sun().ToMoon.z > 0.0F)
            {
                if(com.maddox.il2.game.Mission.curCloudsType() > 3 && actor.pos.getAbs().getZ() < (double)(com.maddox.il2.game.Mission.curCloudsHeight() + 200F))
                    return false;
                v3.set(com.maddox.il2.ai.World.Sun().ToMoon.x, com.maddox.il2.ai.World.Sun().ToMoon.y, com.maddox.il2.ai.World.Sun().ToMoon.z);
                v2.sub(am.pos.getAbsPoint(), actor.pos.getAbsPoint());
                float f = (float)v2.length();
                v2.normalize();
                double d = v3.angle(v2);
                float f1 = com.maddox.il2.fm.FlightModel.cvt(f, 100F, 3000F, 2.9F, 3F);
                if(d > (double)f1)
                    return true;
            }
            return false;
        } else
        {
            return false;
        }
    }

    private void updateTurret(com.maddox.il2.fm.Turret turret1, int i, float f)
    {
        if(!turret1.bIsOperable)
        {
            CT.WeaponControl[i + 10] = false;
            return;
        }
        if(!turret1.bIsAIControlled)
        {
            tu[0] = tu[1] = tu[2] = 0.0F;
            tu[1] = turret1.tu[0];
            HM.setCurChunk(turret1.indexA);
            am.hierMesh().chunkSetAngles(tu);
            tu[1] = turret1.tu[1];
            HM.setCurChunk(turret1.indexB);
            am.hierMesh().chunkSetAngles(tu);
            return;
        }
        if(turret1.indexA == -1 || turret1.indexB == -1)
            return;
        am = (com.maddox.il2.engine.ActorHMesh)actor;
        float f1 = 0.0F;
        float f3 = (float)turretSkill * turret1.health;
        if(W.lengthSquared() > 0.25D)
            f3 *= 1.0F - (float)java.lang.Math.sqrt(W.length() - 0.5D);
        if(getOverload() > 0.5F)
            f3 *= com.maddox.il2.objects.air.Aircraft.cvt(getOverload(), 0.0F, 5F, 1.0F, 0.0F);
        else
        if(getOverload() < -0.25F)
            f3 *= com.maddox.il2.objects.air.Aircraft.cvt(getOverload(), -1F, 0.0F, 0.0F, 1.0F);
        if(turret1.target != null && (turret1.target instanceof com.maddox.il2.objects.air.Aircraft) && ((com.maddox.il2.objects.air.Aircraft)turret1.target).FM.isTakenMortalDamage())
            turret1.target = null;
        if(turret1.target == null)
        {
            if(turret1.tMode != 0)
            {
                turret1.tMode = 0;
                turret1.timeNext = com.maddox.rts.Time.current();
            }
        } else
        {
            turret1.target.pos.getAbs(Pt);
            turret1.target.getSpeed(Vt);
            actor.getSpeed(Ve);
            Vt.sub(Ve);
            HM.setCurChunk(turret1.indexA);
            com.maddox.il2.engine.Actor _tmp = actor;
            am.getChunkLocAbs(com.maddox.il2.engine.Actor._tmpLoc);
            com.maddox.il2.engine.Actor _tmp1 = actor;
            Ve.sub(Pt, com.maddox.il2.engine.Actor._tmpLoc.getPoint());
            f1 = (float)Ve.length();
            float f2 = (14F - 3F * f3) * (float)java.lang.Math.sin((float)(com.maddox.rts.Time.current() & 65535L) * 0.003F);
            Vt.scale((f1 + f2) * 0.001492537F);
            Ve.add(Vt);
            Or.transformInv(Ve);
            turret1.Lstart.transformInv(Ve);
            Ve.y = -Ve.y;
            HM.setCurChunk(turret1.indexB);
            turret1.Lstart.get(Oo);
            Oo.setAT0(Ve);
            Oo.get(tu);
            Or.transformInv(Vt);
            turret1.Lstart.transformInv(Vt);
            Vt.normalize();
            shoot = ((com.maddox.il2.objects.air.Aircraft)actor).turretAngles(i, tu);
        }
        switch(turret1.tMode)
        {
        default:
            break;

        case 0: // '\0'
            if(com.maddox.il2.ai.World.Sun().ToSun.z < -0.22F)
            {
                turret1.bIsShooting = false;
                turret1.tuLim[0] = turret1.tuLim[1] = 0.0F;
                if(com.maddox.rts.Time.current() > turret1.timeNext)
                {
                    turret1.target = com.maddox.il2.ai.War.GetNearestEnemyAircraft(actor, 1000F, 9);
                    if(turret1.target == null)
                    {
                        turret1.target = com.maddox.il2.ai.War.GetNearestEnemyAircraft(actor, 3000F, 9);
                        if(turret1.target == null)
                            turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(3200L, 10000L);
                        else
                        if(isTargetExposed(turret1.target))
                        {
                            turret1.tMode = 1;
                            turret1.timeNext = 0L;
                        } else
                        {
                            turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(1500L, 4000L);
                        }
                    } else
                    {
                        turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(100L, 500L);
                        if(isNightTargetVisible(turret1.target, f3) || isTargetExposed(turret1.target))
                        {
                            turret1.tMode = 1;
                            turret1.timeNext = 0L;
                        }
                    }
                }
                break;
            }
            turret1.bIsShooting = false;
            turret1.tuLim[0] = turret1.tuLim[1] = 0.0F;
            if(com.maddox.rts.Time.current() <= turret1.timeNext)
                break;
            turret1.target = com.maddox.il2.ai.War.GetNearestEnemyAircraft(actor, 3619F, 9);
            if(turret1.target == null)
            {
                turret1.target = com.maddox.il2.ai.War.GetNearestEnemyAircraft(actor, 6822F, 9);
                if(turret1.target == null)
                    turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(1000L, 10000L);
                else
                    turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(100L, 3000L);
            } else
            {
                turret1.tMode = 1;
                turret1.timeNext = 0L;
            }
            break;

        case 1: // '\001'
            turret1.bIsShooting = false;
            turret1.tuLim[0] = tu[0];
            turret1.tuLim[1] = tu[1];
            if(!isTick(39, 16))
                break;
            if(!shoot && f1 > 550F || com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            {
                turret1.tMode = 0;
                turret1.timeNext = com.maddox.rts.Time.current();
            }
            if((float)(com.maddox.il2.ai.World.Rnd().nextInt() & 0xff) >= 32F * (f3 + 1.0F) && f1 >= 148F + 27F * f3)
                break;
            if(f1 < 450F + 66.6F * f3)
            {
                if(f3 <= 0.0F)
                {
                    if(Vt.x < -0.95999997854232788D)
                    {
                        switch(com.maddox.il2.ai.World.Rnd().nextInt(1, 3))
                        {
                        case 1: // '\001'
                            turret1.tMode = 5;
                            turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(500L, 1200L);
                            break;

                        case 2: // '\002'
                            turret1.tuLim[0] += com.maddox.il2.ai.World.Rnd().nextFloat(-15F, 15F);
                            turret1.tuLim[1] += com.maddox.il2.ai.World.Rnd().nextFloat(-10F, 10F);
                            // fall through

                        case 3: // '\003'
                            turret1.tMode = 3;
                            turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(500L, 10000L);
                            break;
                        }
                        break;
                    }
                    if(Vt.x < -0.33000001311302185D)
                    {
                        turret1.tMode = 3;
                        turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(1000L, 5000L);
                    }
                    break;
                }
                if(f3 <= 2.0F)
                {
                    if(Vt.x < -0.9100000262260437D)
                    {
                        if(com.maddox.il2.ai.World.Rnd().nextBoolean())
                            turret1.tMode = 3;
                        else
                            turret1.tMode = 2;
                        turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(500L, 2200L);
                        break;
                    }
                    if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.5F)
                        turret1.tMode = 2;
                    else
                        turret1.tMode = 3;
                    turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(1500L, 7500L);
                } else
                {
                    turret1.tMode = 2;
                    turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(500L, 7500L);
                }
                break;
            }
            if(f1 < 902F + 88F * f3)
            {
                turret1.tMode = 3;
                turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(100L, 1000L);
            }
            break;

        case 5: // '\005'
            turret1.bIsShooting = false;
            if(com.maddox.rts.Time.current() > turret1.timeNext)
            {
                turret1.tMode = 0;
                turret1.timeNext = 0L;
            }
            break;

        case 3: // '\003'
            turret1.bIsShooting = true;
            turret1.tuLim[0] = tu[0] * com.maddox.il2.ai.World.Rnd().nextFloat(0.85F + 0.05F * f3, 1.15F - 0.05F * f3) + com.maddox.il2.ai.World.Rnd().nextFloat(-8F + 2.666667F * f3, 8F - 2.666667F * f3);
            turret1.tuLim[1] = tu[1] * com.maddox.il2.ai.World.Rnd().nextFloat(0.85F + 0.05F * f3, 1.15F - 0.05F * f3) + com.maddox.il2.ai.World.Rnd().nextFloat(-8F + 2.666667F * f3, 8F - 2.666667F * f3);
            if(com.maddox.rts.Time.current() > turret1.timeNext)
                turret1.tMode = 1;
            break;

        case 2: // '\002'
            turret1.bIsShooting = true;
            turret1.tuLim[0] = tu[0] * com.maddox.il2.ai.World.Rnd().nextFloat(0.85F + 0.05F * f3, 1.15F - 0.05F * f3) + com.maddox.il2.ai.World.Rnd().nextFloat(-8F + 2.666667F * f3, 8F - 2.666667F * f3);
            turret1.tuLim[1] = tu[1] * com.maddox.il2.ai.World.Rnd().nextFloat(0.85F + 0.05F * f3, 1.15F - 0.05F * f3) + com.maddox.il2.ai.World.Rnd().nextFloat(-8F + 2.666667F * f3, 8F - 2.666667F * f3);
            if(com.maddox.rts.Time.current() <= turret1.timeNext)
                break;
            turret1.tMode = 1;
            if(f3 <= 1.0F)
            {
                turret1.tMode = 0;
                turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(100L, (long)((f3 + 1.0F) * 700F));
            }
            break;

        case 4: // '\004'
            turret1.bIsShooting = true;
            shoot = true;
            ((com.maddox.il2.objects.air.Aircraft)actor).turretAngles(i, turret1.tuLim);
            if(isTick(20, 0))
            {
                turret1.tuLim[0] += com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F);
                turret1.tuLim[1] += com.maddox.il2.ai.World.Rnd().nextFloat(-50F, 50F);
            }
            if(com.maddox.rts.Time.current() > turret1.timeNext)
            {
                turret1.tMode = 5;
                turret1.timeNext = com.maddox.rts.Time.current() + com.maddox.il2.ai.World.Rnd().nextLong(100L, 1500L);
            }
            break;
        }
        shoot &= turret1.bIsShooting;
        if(shoot)
            shoot = !isComingFromTheSun(turret1.target);
        CT.WeaponControl[i + 10] = shoot;
        updateRotation(turret1, f);
    }

    public void hit(int i)
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return;
        if(actor.isNetMirror())
        {
            return;
        } else
        {
            super.hit(i);
            return;
        }
    }

    public float getSpeed()
    {
        if(!com.maddox.il2.engine.Actor.isValid(actor))
            return 0.0F;
        if(actor.isNetMirror())
            return (float)Vwld.length();
        else
            return super.getSpeed();
    }

    public void update(float f)
    {
        if(actor.isNetMirror())
            ((com.maddox.il2.objects.air.NetAircraft.Mirror)actor.net).fmUpdate(f);
        else
            FMupdate(f);
    }

    public final void FMupdate(float f)
    {
        if(turret != null)
        {
            int j = turret.length;
            for(int i = 0; i < j; i++)
                updateTurret(turret[i], i, f);

        }
        super.update(f);
    }

    protected void putScareShpere()
    {
        v1.set(1.0D, 0.0D, 0.0D);
        Or.transform(v1);
        v1.scale(2000D);
        p2.set(Loc);
        p2.add(v1);
        com.maddox.il2.engine.Engine.land();
        if(com.maddox.il2.engine.Landscape.rayHitHQ(Loc, p2, p))
        {
            float f = (float)((double)getAltitude() - com.maddox.il2.engine.Engine.land().HQ_Air(Loc.x, Loc.y));
            if((actor instanceof com.maddox.il2.objects.air.TypeDiveBomber) && f > 780F && Or.getTangage() < -70F)
            {
                com.maddox.il2.ai.ground.ScareEnemies.set(16);
                com.maddox.il2.engine.Engine.collideEnv().getNearestEnemies(p, 75D, actor.getArmy(), com.maddox.il2.ai.ground.ScareEnemies.enemies());
            }
            if(actor instanceof com.maddox.il2.objects.air.TypeStormovik)
            {
                if(f < 600F && Or.getTangage() < -15F)
                {
                    com.maddox.il2.ai.ground.ScareEnemies.set(2);
                    com.maddox.il2.engine.Engine.collideEnv().getNearestEnemies(p, 45D, actor.getArmy(), com.maddox.il2.ai.ground.ScareEnemies.enemies());
                }
            } else
            if((actor instanceof com.maddox.il2.objects.air.TypeFighter) && f < 500F && Or.getTangage() < -15F)
            {
                com.maddox.il2.ai.ground.ScareEnemies.set(2);
                com.maddox.il2.engine.Engine.collideEnv().getNearestEnemies(p, 45D, actor.getArmy(), com.maddox.il2.ai.ground.ScareEnemies.enemies());
            }
        }
    }

    public void moveCarrier()
    {
        if(AP.way.isLandingOnShip())
        {
            if(AP.way.landingAirport == null)
            {
                int i = AP.way.Cur();
                AP.way.last();
                if(AP.way.curr().Action == 2)
                {
                    com.maddox.il2.engine.Actor actor = AP.way.curr().getTarget();
                    if(actor != null && (actor instanceof com.maddox.il2.objects.ships.BigshipGeneric))
                        AP.way.landingAirport = ((com.maddox.il2.objects.ships.BigshipGeneric)actor).getAirport();
                }
                AP.way.setCur(i);
            }
            if(com.maddox.il2.engine.Actor.isAlive(AP.way.landingAirport) && !AP.way.isLanding())
                AP.way.landingAirport.rebuildLastPoint(this);
        }
    }

    protected static float cvt(float f, float f1, float f2, float f3, float f4)
    {
        f = java.lang.Math.min(java.lang.Math.max(f, f1), f2);
        return f3 + ((f4 - f3) * (f - f1)) / (f2 - f1);
    }

    public com.maddox.il2.fm.Turret turret[];
    protected com.maddox.il2.engine.HierMesh HM;
    com.maddox.il2.engine.ActorHMesh am;
    private boolean shoot;
    public boolean turnOffCollisions;
    public boolean brakeShoe;
    public com.maddox.il2.engine.Loc brakeShoeLoc;
    public com.maddox.il2.engine.Actor brakeShoeLastCarrier;
    public boolean canChangeBrakeShoe;
    public static final int _FIRST_TURRET = 10;
    private final float tAcc1 = 0.05F;
    private final float tAcc2 = 8F;
    private final float tAcc3 = 2.666667F;
    private static com.maddox.JGP.Vector3d v2 = new Vector3d();
    private static com.maddox.JGP.Vector3d v3 = new Vector3d();
    private static com.maddox.JGP.Point3d Pt = new Point3d();
    private static com.maddox.JGP.Vector3d Ve = new Vector3d();
    private static com.maddox.JGP.Vector3d Vt = new Vector3d();
    private static com.maddox.il2.engine.Orient Oo = new Orient();
    private static float tu[] = new float[3];
    public float dryFriction;
    static com.maddox.JGP.Point3d p = new Point3d();
    static com.maddox.JGP.Vector3d v1 = new Vector3d();
    static com.maddox.JGP.Point3d p2 = new Point3d();

}
