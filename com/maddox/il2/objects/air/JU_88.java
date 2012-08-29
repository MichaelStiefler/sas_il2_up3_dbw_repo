// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_88.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, JU_88MSTL, Aircraft, PaintScheme

public abstract class JU_88 extends com.maddox.il2.objects.air.Scheme2
{

    public JU_88()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 1600F, -80F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -90F * f, 0.0F);
        f1 = f >= 0.5F ? java.lang.Math.abs(java.lang.Math.min(1.0F - f, 0.1F)) : java.lang.Math.abs(java.lang.Math.min(f, 0.1F));
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, -450F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, 450F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, 1200F * f1, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -1200F * f1, 0.0F);
        if(f < 0.5F)
        {
            hiermesh.chunkSetAngles("GearR5_D0", 0.0F, 900F * f1, 0.0F);
            hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -900F * f1, 0.0F);
            hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -900F * f1, 0.0F);
            hiermesh.chunkSetAngles("GearL4_D0", 0.0F, 900F * f1, 0.0F);
        }
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, 0.0F, 95F * f);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, 0.0F, 95F * f);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -130F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -130F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.JU_88.moveGear(hierMesh(), f);
    }

    protected void moveFlap(float f)
    {
        float f1 = -70F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
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
            if(f > 35F)
            {
                f = 35F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 35F)
            {
                f1 = 35F;
                flag = false;
            }
            break;

        case 1: // '\001'
            f = 0.0F;
            f1 = 0.0F;
            flag = false;
            break;

        case 2: // '\002'
            if(f < -45F)
            {
                f = -45F;
                flag = false;
            }
            if(f > 25F)
            {
                f = 25F;
                flag = false;
            }
            if(f1 < -10F)
            {
                f1 = -10F;
                flag = false;
            }
            if(f1 > 60F)
            {
                f1 = 60F;
                flag = false;
            }
            if(f < -2F)
            {
                if(f1 < com.maddox.il2.objects.air.Aircraft.cvt(f, -6.8F, -2F, -10F, -2.99F))
                    f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, -6.8F, -2F, -10F, -2.99F);
                break;
            }
            if(f < 0.5F)
            {
                if(f1 < com.maddox.il2.objects.air.Aircraft.cvt(f, -2F, 0.5F, -2.99F, -2.3F))
                    f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, -2F, 0.5F, -2.99F, -2.3F);
                break;
            }
            if(f < 5.3F)
            {
                if(f1 < com.maddox.il2.objects.air.Aircraft.cvt(f, 0.5F, 5.3F, -2.3F, -2.3F))
                    f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.5F, 5.3F, -2.3F, -2.3F);
                break;
            }
            if(f1 < com.maddox.il2.objects.air.Aircraft.cvt(f, 5.3F, 25F, -2.3F, -7.2F))
                f1 = com.maddox.il2.objects.air.Aircraft.cvt(f, 5.3F, 25F, -2.3F, -7.2F);
            break;

        case 3: // '\003'
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
            if(f1 < -35F)
            {
                f1 = -35F;
                flag = false;
            }
            if(f1 > -0.48F)
            {
                f1 = -0.48F;
                flag = false;
            }
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 13: // '\r'
            killPilot(this, 0);
            killPilot(this, 1);
            killPilot(this, 2);
            killPilot(this, 3);
            return false;

        case 33: // '!'
            return super.cutFM(34, j, actor);

        case 36: // '$'
            return super.cutFM(37, j, actor);

        case 3: // '\003'
            FM.AS.hitEngine(this, 0, 99);
            break;

        case 4: // '\004'
            FM.AS.hitEngine(this, 1, 99);
            break;
        }
        return super.cutFM(i, j, actor);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.2F)
        {
            if(FM.AS.astateEngineStates[0] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.hitTank(this, 0, 3);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.hitTank(this, 1, 3);
            }
            if(FM.AS.astateEngineStates[1] > 3)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                    FM.AS.hitTank(this, 2, 3);
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                    FM.AS.hitTank(this, 3, 3);
            }
            if(FM.AS.astateTankStates[0] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                nextDMGLevel(FM.AS.astateEffectChunks[0] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                nextDMGLevel(FM.AS.astateEffectChunks[1] + "0", 0, this);
            if(FM.AS.astateTankStates[1] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                FM.AS.hitTank(this, 2, 3);
            if(FM.AS.astateTankStates[2] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                FM.AS.hitTank(this, 1, 3);
            if(FM.AS.astateTankStates[2] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                nextDMGLevel(FM.AS.astateEffectChunks[2] + "0", 0, this);
            if(FM.AS.astateTankStates[3] > 4 && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.11F)
                nextDMGLevel(FM.AS.astateEffectChunks[3] + "0", 0, this);
        }
        if(!(this instanceof com.maddox.il2.objects.air.JU_88MSTL))
        {
            for(int i = 1; i < 4; i++)
                if(FM.getAltitude() < 3000F)
                    hierMesh().chunkVisible("HMask" + i + "_D0", false);
                else
                    hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

        }
    }

    public boolean typeDiveBomberToggleAutomation()
    {
        return false;
    }

    public void typeDiveBomberAdjAltitudeReset()
    {
    }

    public void typeDiveBomberAdjAltitudePlus()
    {
    }

    public void typeDiveBomberAdjAltitudeMinus()
    {
    }

    public void typeDiveBomberAdjVelocityReset()
    {
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
    }

    public void typeDiveBomberAdjDiveAngleReset()
    {
    }

    public void typeDiveBomberAdjDiveAnglePlus()
    {
    }

    public void typeDiveBomberAdjDiveAngleMinus()
    {
    }

    public void typeDiveBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeDiveBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
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
        java.lang.Class class1 = com.maddox.il2.objects.air.JU_88.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
