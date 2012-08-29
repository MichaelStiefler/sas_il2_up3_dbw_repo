// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TU_2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2a, TypeTransport, PaintScheme

public abstract class TU_2 extends com.maddox.il2.objects.air.Scheme2a
    implements com.maddox.il2.objects.air.TypeTransport
{

    public TU_2()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 0.777778F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, 0.777778F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -0.777778F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -0.777778F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 70F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -105F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -105F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 135F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 135F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.TU_2.moveGear(hierMesh(), f);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLMid") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingRMid") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 3, 1);
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitTank(shot.initiator, 2, 1);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 1, 1);
        super.msgShot(shot);
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].setHealth(f);
            break;

        case 2: // '\002'
            FM.turret[1].setHealth(f);
            break;

        case 3: // '\003'
            FM.turret[2].setHealth(f);
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("HMask3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
        {
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("HMask3_D0", false);
        } else
        {
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
            hierMesh().chunkVisible("HMask2_D0", hierMesh().isChunkVisible("Pilot2_D0"));
            hierMesh().chunkVisible("HMask3_D0", hierMesh().isChunkVisible("Pilot3_D0"));
        }
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
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 > 30F)
            {
                f1 = 30F;
                flag = false;
            }
            float f2 = java.lang.Math.abs(f);
            if(f2 < 4.5F)
            {
                if(f1 < -0.111F * f2)
                {
                    f1 = -0.111F * f2;
                    flag = false;
                }
            } else
            if(f2 < 14.8F)
            {
                if(f1 < 1.08108F - 0.35135F * f2)
                {
                    f1 = 1.08108F - 0.35135F * f2;
                    flag = false;
                }
                if(f1 < -0.9369F + 0.0971F * f2 && f1 > -2.4369F + 0.0971F * f2)
                    flag = false;
            } else
            if(f2 < 17.2F)
            {
                if(f1 < 1.08108F - 0.35135F * f2)
                {
                    f1 = 1.08108F - 0.35135F * f2;
                    flag = false;
                }
                if(f1 < 8.5F || f1 > -5.5F)
                    flag = false;
            } else
            if(f2 < 23F)
            {
                if(f1 < 1.08108F - 0.35135F * f2)
                {
                    f1 = 1.08108F - 0.35135F * f2;
                    flag = false;
                }
            } else
            if(f1 < -7F)
            {
                f1 = -7F;
                flag = false;
            }
            break;

        case 1: // '\001'
            if(f < -20F)
            {
                f = -20F;
                flag = false;
            }
            if(f > 45F)
            {
                f = 45F;
                flag = false;
            }
            if(f < -15.5F)
            {
                if(f1 < 0.7778F + 0.3889F * f)
                {
                    f1 = 0.7778F + 0.3889F * f;
                    flag = false;
                }
                if(f1 > 23F)
                {
                    f1 = 23F;
                    flag = false;
                }
                if(f1 < -0.2182F - 0.1091F * f || f1 > -7.509F - 0.2545F * f)
                    flag = false;
                break;
            }
            if(f < 2.0F)
            {
                if(f1 < 0.7778F + 0.3889F * f)
                {
                    f1 = 0.7778F + 0.3889F * f;
                    flag = false;
                }
                if(f1 > 40F)
                {
                    f1 = 40F;
                    flag = false;
                }
                if((double)f1 < -0.21820000000000001D - (double)(0.1091F * f) || f1 > -7.509F - 0.2545F * f)
                    flag = false;
                break;
            }
            if(f < 14F)
            {
                if(f1 < 0.186172F * f)
                {
                    f1 = 0.186172F * f;
                    flag = false;
                }
                if(f1 > 40F)
                {
                    f1 = 40F;
                    flag = false;
                }
                if(f1 < 0.2034F + 0.1017F * f || f1 > -6.5254F + 0.2373F * f)
                    flag = false;
                break;
            }
            if(f < 27.5F)
            {
                if(f1 < 0.186172F * f)
                {
                    f1 = 0.186172F * f;
                    flag = false;
                }
                if(f1 > 45.839F - 0.7742F * f)
                {
                    f1 = 45.839F - 0.7742F * f;
                    flag = false;
                }
                if(f1 < 0.2034F + 0.1017F * f || f1 > -6.5254F + 0.2373F * f)
                    flag = false;
                break;
            }
            if(f < 38F)
            {
                if(f1 < -4.2132F + 0.5714F * f)
                {
                    f1 = -4.2132F + 0.5714F * f;
                    flag = false;
                }
                if(f1 > 45.839F - 0.7742F * f)
                {
                    f1 = 45.839F - 0.7742F * f;
                    flag = false;
                }
                break;
            }
            if(f >= 45F)
                break;
            if(f1 < -7.5F)
            {
                f1 = -7.5F;
                flag = false;
            }
            if(f1 > 45.839F - 0.7742F * f)
            {
                f1 = 45.839F - 0.7742F * f;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -30F)
            {
                f = -30F;
                flag = false;
            }
            if(f > 30F)
            {
                f = 30F;
                flag = false;
            }
            if(f1 < -50F)
            {
                f1 = -50F;
                flag = false;
            }
            if(f1 > 1.0F)
            {
                f1 = 1.0F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.TU_2.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
    }
}
