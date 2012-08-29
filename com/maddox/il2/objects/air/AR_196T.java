// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   AR_196T.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.Property;
import java.util.Random;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, TypeScout, TypeStormovik, Aircraft, 
//            PaintScheme

public abstract class AR_196T extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeScout, com.maddox.il2.objects.air.TypeStormovik
{

    public AR_196T()
    {
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 0, 6);
            return super.cutFM(34, j, actor);

        case 36: // '$'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(((com.maddox.il2.engine.Actor) (this)), 1, 6);
            return super.cutFM(37, j, actor);

        case 34: // '"'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).cut(9, j, actor);
            break;

        case 37: // '%'
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).cut(10, j, actor);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).setExplosion(explosion);
        if(explosion.chunkName != null)
        {
            if(explosion.chunkName.startsWith("CF"))
            {
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.005F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(explosion.initiator, 0);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.005F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(explosion.initiator, 1);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.005F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(explosion.initiator, 2);
            }
            if(explosion.chunkName.startsWith("Engine") && explosion.power > 0.011F)
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitOil(explosion.initiator, 0);
            if(explosion.chunkName.startsWith("Tail"))
            {
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.01F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(explosion.initiator, 1);
                if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.11F)
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(explosion.initiator, 2);
            }
        }
        ((com.maddox.il2.objects.air.Aircraft)this).msgExplosion(explosion);
    }

    protected void moveFlap(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45F * f, 0.0F);
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45F * f, 0.0F);
    }

    protected void moveElevator(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -30F * f, 0.0F);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.AR_196T.moveGear(((com.maddox.il2.engine.ActorHMesh)this).hierMesh(), f);
    }

    public void moveSteering(float f)
    {
    }

    public void moveWheelSink()
    {
    }

    protected void moveRudder(float f)
    {
        ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30F * f, 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        ((com.maddox.il2.objects.air.Aircraft)this).rareAction(f, flag);
        for(int i = 1; i < 3; i++)
            if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).getAltitude() < 3000F)
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask" + i + "_D0", ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = ((com.maddox.il2.objects.air.Aircraft)this).turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        case 0: // '\0'
            if(f < -35F)
            {
                f = -35F;
                flag = false;
            }
            if(f > 35F)
            {
                f = 35F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            ((com.maddox.il2.objects.sounds.SndAircraft)this).FM.turret[0].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot1_D1", true);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Head1_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask1_D0", false);
            break;

        case 1: // '\001'
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot2_D0", false);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("Pilot2_D1", true);
            ((com.maddox.il2.engine.ActorHMesh)this).hierMesh().chunkVisible("HMask2_D0", false);
            break;
        }
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xx"))
        {
            if(s.startsWith("xxarmor"))
            {
                if(s.endsWith("p1"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(5F, 10F), shot);
                else
                if(s.endsWith("p2"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(5.25D / (java.lang.Math.abs(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y) + 9.9999997473787516E-005D), shot);
                else
                if(s.endsWith("p3"))
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(5F, 10F), shot);
                return;
            }
            if(s.startsWith("xxcontrols"))
            {
                int i = s.charAt(10) - 48;
                switch(i)
                {
                default:
                    break;

                case 1: // '\001'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(2.2F, shot) > 0.0F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 2);
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 0);
                    }
                    break;

                case 2: // '\002'
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setCockpitState(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateCockpitState | 0x40);
                    break;

                case 3: // '\003'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 6);
                    }
                    break;

                case 4: // '\004'
                case 5: // '\005'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.0F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.85F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 0);
                    break;

                case 6: // '\006'
                case 9: // '\t'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.5F, shot) > 0.0F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 1);
                    break;

                case 7: // '\007'
                case 8: // '\b'
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.5F, shot) > 0.0F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setControlsDamage(shot.initiator, 2);
                    break;
                }
                return;
            }
            if(s.startsWith("xxspar"))
            {
                if((s.endsWith("t1") || s.endsWith("t2")) && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(19.9F / (float)java.lang.Math.sqrt(((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).y + ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z * ((com.maddox.JGP.Tuple3d) (com.maddox.il2.objects.air.Aircraft.v1)).z), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("*** Tail1 Spars Broken in Half..");
                    ((com.maddox.il2.objects.air.Aircraft)this).msgCollision(((com.maddox.il2.engine.Actor) (this)), "Tail1_D0", "Tail1_D0");
                }
                if((s.endsWith("li1") || s.endsWith("li2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("*** WingLIn Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLIn_D3", shot.initiator);
                }
                if((s.endsWith("ri1") || s.endsWith("ri2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("*** WingRIn Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRIn_D3", shot.initiator);
                }
                if((s.endsWith("lm1") || s.endsWith("lm2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("*** WingLMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLMid_D3", shot.initiator);
                }
                if((s.endsWith("rm1") || s.endsWith("rm2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("*** WingRMid Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingRMid_D3", shot.initiator);
                }
                if((s.endsWith("lo1") || s.endsWith("lo2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("*** WingLOut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingLOut_D3", shot.initiator);
                }
                if((s.endsWith("ro1") || s.endsWith("ro2")) && ((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") > 2 && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(19.5F * com.maddox.il2.ai.World.Rnd().nextFloat(1.0F, 2.0F), shot) > 0.0F)
                {
                    ((com.maddox.il2.objects.air.Aircraft)this).debuggunnery("*** WingROut Spars Damaged..");
                    ((com.maddox.il2.objects.air.Aircraft)this).nextDMGLevels(1, 2, "WingROut_D3", shot.initiator);
                }
                return;
            }
            if(s.startsWith("xxeng"))
            {
                com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Hit..");
                if(s.endsWith("pipe"))
                {
                    if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.1F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 0 && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.Weapons[1] != null && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).CT.Weapons[1].length != 2)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Nose Nozzle Pipe Bent..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.3F, shot);
                } else
                if(s.endsWith("prop"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.8F)
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Prop Governor Hit, Disabled..");
                        } else
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Prop Governor Hit, Damaged..");
                        }
                } else
                if(s.endsWith("gear"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.6F, shot) > 0.0F)
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setEngineStuck(shot.initiator);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Bullet Jams Reductor Gear..");
                        } else
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 3);
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 4);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Reductor Gear Damaged, Prop Governor Failed..");
                        }
                } else
                if(s.endsWith("supc"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineSpecificDamage(shot.initiator, 0, 0);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Supercharger Disabled..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.5F, shot);
                } else
                if(s.endsWith("feed"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(8.9F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getPowerOutput() > 0.7F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 0)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 100);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Pressurized Fuel Line Pierced, Fuel Flamed..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.0F, shot);
                } else
                if(s.endsWith("fuel"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.1F, shot) > 0.0F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 0)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setEngineStops(shot.initiator);
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Fuel Line Stalled, Engine Stalled..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(1.0F, shot);
                } else
                if(s.endsWith("case"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(4.2F, shot) > 0.0F)
                    {
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 175000F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Bullet Jams Crank Ball Bearing..");
                        }
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 50000F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 0)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 2);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Crank Case Hit, Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() + "..");
                        }
                        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 0)
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setReadyness(shot.initiator, ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() - com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.power / 48000F));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Crank Case Hit, Readyness Reduced to " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getReadyness() + "..");
                    }
                    ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(27.5F, shot);
                } else
                if(s.startsWith("xxeng1cyl"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(2.4F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersRatio() * (((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 0 ? 1.75F : 0.5F))
                    {
                        if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 0)
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                        else
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 19200F)));
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Cylinders Hit, " + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylindersOperable() + "/" + ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getCylinders() + " Left..");
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 96000F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 0)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 3);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Cylinders Hit, Engine Fires..");
                        }
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < shot.power / 96000F && ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].getType() == 1)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitEngine(shot.initiator, 0, 1);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Cylinders Hit, Engine Fires..");
                        }
                        if(((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.01F)
                        {
                            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setEngineStuck(shot.initiator, 0);
                            com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Bullet Jams Piston Head..");
                        }
                        ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(43.6F, shot);
                    }
                } else
                if(s.startsWith("xxeng1mag"))
                {
                    int j = s.charAt(9) - 49;
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).EI.engines[0].setMagnetoKnockOut(shot.initiator, j);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Magneto " + j + " Destroyed..");
                } else
                if(s.endsWith("sync"))
                {
                    if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(2.1F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F)
                        com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Gun Synchronized Hit, Nose Guns Lose Authority..");
                } else
                if(s.endsWith("oil1") && ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(2.4F, shot) > 0.0F)
                {
                    ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitOil(shot.initiator, 0);
                    com.maddox.il2.objects.air.Aircraft.debugprintln(((com.maddox.il2.engine.Actor) (this)), "*** Engine Module: Oil Radiator Hit..");
                }
                return;
            }
            if(s.startsWith("xxmgun"))
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(0, 0);
            if(s.startsWith("xxcannon01"))
            {
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 0);
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
            }
            if(s.startsWith("xxcannon02"))
            {
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 1);
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(3.3F, 24.6F), shot);
            }
            if(s.startsWith("xxammo"))
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat(3800F, 30000F) < shot.power)
                {
                    if(s.endsWith("01"))
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 0);
                    if(s.endsWith("02"))
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(1, 1);
                }
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 28.33F), shot);
            }
            if(s.startsWith("xxoil"))
            {
                ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitOil(shot.initiator, 0);
                ((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.22F, shot);
            }
            if(s.startsWith("xxtank"))
            {
                int k = s.charAt(6) - 49;
                if(((com.maddox.il2.objects.air.Aircraft)this).getEnergyPastArmor(0.1F, shot) > 0.0F && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.25F)
                {
                    if(((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.astateTankStates[k] == 0)
                    {
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, k, 1);
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.doSetTankState(shot.initiator, k, 1);
                    }
                    if(shot.powerType == 3 && ((java.util.Random) (com.maddox.il2.ai.World.Rnd())).nextFloat() < 0.5F)
                        ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.hitTank(shot.initiator, k, 2);
                }
            }
            return;
        }
        if(s.startsWith("xcf"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("CF") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("CF", shot);
        } else
        if(s.startsWith("xtail"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Tail1") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Keel1") < 2)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xrudder"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Rudder1", shot);
        else
        if(s.startsWith("xstabl"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("StabR", shot);
        else
        if(s.startsWith("xvatorl"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("VatorL", shot);
        else
        if(s.startsWith("xwinglin"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLIn") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLIn", shot);
        } else
        if(s.startsWith("xwingrin"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRIn") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRIn", shot);
        } else
        if(s.startsWith("xwinglmid"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLMid") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLMid", shot);
        } else
        if(s.startsWith("xwingrmid"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingRMid") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingRMid", shot);
        } else
        if(s.startsWith("xwinglout"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingLOut") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingLOut", shot);
        } else
        if(s.startsWith("xwingrout"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("WingROut") < 3)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xaronel"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneL", shot);
        else
        if(s.startsWith("xaroner"))
            ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("AroneR", shot);
        else
        if(s.startsWith("xengine"))
        {
            if(((com.maddox.il2.objects.air.Aircraft)this).chunkDamageVisible("Engine1") < 2)
                ((com.maddox.il2.objects.air.Aircraft)this).hitChunk("Engine1", shot);
        } else
        if(s.startsWith("xturret"))
            ((com.maddox.il2.fm.FlightModelMain) (((com.maddox.il2.objects.sounds.SndAircraft)this).FM)).AS.setJamBullets(10, 0);
        else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int l;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                l = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                l = s.charAt(6) - 49;
            } else
            {
                l = s.charAt(5) - 49;
            }
            ((com.maddox.il2.objects.air.Aircraft)this).hitFlesh(l, shot, ((int) (byte0)));
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(((java.lang.Throwable) (classnotfoundexception)).getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.AR_196T.class;
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
