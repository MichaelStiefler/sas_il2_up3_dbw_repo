// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PBYX.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, TypeBomber, TypeScout, TypeSailPlane, 
//            TypeTransport, PaintScheme

public abstract class PBYX extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeScout, com.maddox.il2.objects.air.TypeSailPlane, com.maddox.il2.objects.air.TypeTransport
{

    public PBYX()
    {
    }

    private static float floatindex(float f, float af[])
    {
        int i = (int)f;
        if(i >= af.length - 1)
            return af[af.length - 1];
        if(i < 0)
            return af[0];
        if(i == 0)
        {
            if(f > 0.0F)
                return af[0] + f * (af[1] - af[0]);
            else
                return af[0];
        } else
        {
            return af[i] + (f % (float)i) * (af[i + 1] - af[i]);
        }
    }

    public void update(float f)
    {
        super.update(f);
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 2; j++)
                if(FM.Gears.clpGearEff[i][j] != null)
                {
                    tmpp.set(FM.Gears.clpGearEff[i][j].pos.getAbsPoint());
                    tmpp.z = 0.01D;
                    FM.Gears.clpGearEff[i][j].pos.setAbs(tmpp);
                    FM.Gears.clpGearEff[i][j].pos.reset();
                }

        }

    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -com.maddox.il2.objects.air.PBYX.floatindex(10F * f, gear3), 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, com.maddox.il2.objects.air.PBYX.floatindex(10F * f, gear4), 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, com.maddox.il2.objects.air.PBYX.floatindex(10F * f, gear5), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -com.maddox.il2.objects.air.PBYX.floatindex(10F * f, gear3), 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, com.maddox.il2.objects.air.PBYX.floatindex(10F * f, gear4), 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -com.maddox.il2.objects.air.PBYX.floatindex(10F * f, gear5), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.PBYX.moveGear(hierMesh(), f);
    }

    protected void moveFlap(float f)
    {
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f < -90F)
            {
                f = -90F;
                flag = false;
            }
            if(f > 90F)
            {
                f = 90F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 45F)
            {
                f1 = 45F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -90F)
            {
                f = -90F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 90F)
            {
                f = 90F;
                flag = false;
            }
            if(f1 < -30F)
            {
                f1 = -30F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xcf"))
        {
            if(chunkDamageVisible("CF") < 3)
                hitChunk("CF", shot);
        } else
        if(s.startsWith("xtail"))
        {
            if(chunkDamageVisible("Tail1") < 3)
                hitChunk("Tail1", shot);
        } else
        if(s.startsWith("xkeel"))
        {
            if(chunkDamageVisible("Keel1") < 2)
                hitChunk("Keel1", shot);
        } else
        if(s.startsWith("xrudder"))
            hitChunk("Rudder1", shot);
        else
        if(s.startsWith("xstabl"))
            hitChunk("StabL", shot);
        else
        if(s.startsWith("xstabr"))
            hitChunk("StabR", shot);
        else
        if(s.startsWith("xvatorl"))
            hitChunk("VatorL", shot);
        else
        if(s.startsWith("xvatorr"))
            hitChunk("VatorR", shot);
        else
        if(s.startsWith("xwinglin"))
        {
            if(chunkDamageVisible("WingLIn") < 3)
                hitChunk("WingLIn", shot);
            if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.hitTank(shot.initiator, 0, 1);
            if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.hitTank(shot.initiator, 1, 1);
        } else
        if(s.startsWith("xwingrin"))
        {
            if(chunkDamageVisible("WingRIn") < 3)
                hitChunk("WingRIn", shot);
            if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.hitTank(shot.initiator, 2, 1);
            if(shot.powerType == 3 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                FM.AS.hitTank(shot.initiator, 3, 1);
        } else
        if(s.startsWith("xwinglmid"))
        {
            if(chunkDamageVisible("WingLMid") < 3)
                hitChunk("WingLMid", shot);
        } else
        if(s.startsWith("xwingrmid"))
        {
            if(chunkDamageVisible("WingRMid") < 3)
                hitChunk("WingRMid", shot);
        } else
        if(s.startsWith("xwinglout"))
        {
            if(chunkDamageVisible("WingLOut") < 3)
                hitChunk("WingLOut", shot);
        } else
        if(s.startsWith("xwingrout"))
        {
            if(chunkDamageVisible("WingROut") < 3)
                hitChunk("WingROut", shot);
        } else
        if(s.startsWith("xaronel"))
            hitChunk("AroneL", shot);
        else
        if(s.startsWith("xaroner"))
            hitChunk("AroneR", shot);
        else
        if(s.startsWith("xengine1"))
        {
            if(chunkDamageVisible("Engine1") < 2)
                hitChunk("Engine1", shot);
            if(getEnergyPastArmor(1.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[0].getCylindersRatio() * 0.5F)
            {
                FM.EI.engines[0].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                if(FM.AS.astateEngineStates[0] < 1)
                {
                    FM.AS.hitEngine(shot.initiator, 0, 1);
                    FM.AS.doSetEngineState(shot.initiator, 0, 1);
                }
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 960000F)
                    FM.AS.hitEngine(shot.initiator, 0, 3);
                getEnergyPastArmor(25F, shot);
            }
        } else
        if(s.startsWith("xengine2"))
        {
            if(chunkDamageVisible("Engine2") < 2)
                hitChunk("Engine2", shot);
            if(getEnergyPastArmor(1.45F, shot) > 0.0F && com.maddox.il2.ai.World.Rnd().nextFloat() < FM.EI.engines[1].getCylindersRatio() * 0.5F)
            {
                FM.EI.engines[1].setCyliderKnockOut(shot.initiator, com.maddox.il2.ai.World.Rnd().nextInt(1, (int)(shot.power / 4800F)));
                if(FM.AS.astateEngineStates[1] < 1)
                {
                    FM.AS.hitEngine(shot.initiator, 1, 1);
                    FM.AS.doSetEngineState(shot.initiator, 1, 1);
                }
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < shot.power / 960000F)
                    FM.AS.hitEngine(shot.initiator, 1, 3);
                getEnergyPastArmor(25F, shot);
            }
        } else
        if(s.startsWith("xgearl"))
            hitChunk("GearL2", shot);
        else
        if(s.startsWith("xgearr"))
            hitChunk("GearR2", shot);
        else
        if(s.startsWith("xturret"))
        {
            if(s.startsWith("xturret1"))
                FM.AS.setJamBullets(10, 0);
            if(s.startsWith("xturret2"))
                FM.AS.setJamBullets(11, 0);
            if(s.startsWith("xturret3"))
                FM.AS.setJamBullets(12, 0);
        } else
        if(s.startsWith("xpilot") || s.startsWith("xhead"))
        {
            byte byte0 = 0;
            int i;
            if(s.endsWith("a"))
            {
                byte0 = 1;
                i = s.charAt(6) - 49;
            } else
            if(s.endsWith("b"))
            {
                byte0 = 2;
                i = s.charAt(6) - 49;
            } else
            {
                i = s.charAt(5) - 49;
            }
            hitFlesh(i, shot, byte0);
        }
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 19: // '\023'
            killPilot(this, 5);
            killPilot(this, 6);
            break;

        case 33: // '!'
            hierMesh().chunkVisible("Wire01_D0", false);
            hitProp(0, j, actor);
            hitProp(1, j, actor);
            super.cutFM(36, j, actor);
            // fall through

        case 34: // '"'
        case 35: // '#'
            hierMesh().chunkVisible("Wire02_D0", false);
            break;

        case 36: // '$'
            hierMesh().chunkVisible("Wire02_D0", false);
            hitProp(0, j, actor);
            hitProp(1, j, actor);
            super.cutFM(33, j, actor);
            // fall through

        case 37: // '%'
        case 38: // '&'
            hierMesh().chunkVisible("Wire01_D0", false);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 7; i++)
        {
            if(i == 5)
                continue;
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));
        }

    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static final float gear3[] = {
        0.0F, 0.75F, 1.5F, 2.25F, 3.49F, 5F, 7.75F, 12F, 17.75F, 25.01F, 
        45F
    };
    private static final float gear4[] = {
        0.0F, 7.25F, 14.25F, 21.5F, 28.75F, 36.25F, 44F, 54.6F, 64F, 72.75F, 
        85F
    };
    private static final float gear5[] = {
        0.0F, 3.5F, 7F, 10.6F, 13.9F, 17F, 19.25F, 20.75F, 24.25F, 30.95F, 
        50F
    };
    private static com.maddox.JGP.Point3d tmpp = new Point3d();

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.PBYX.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryUSA);
    }
}
