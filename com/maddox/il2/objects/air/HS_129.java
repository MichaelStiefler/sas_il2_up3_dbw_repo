// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HS_129.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, TypeStormovik, Aircraft, PaintScheme

public abstract class HS_129 extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public HS_129()
    {
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            if(!FM.AS.bIsAboutToBailout)
            {
                if(hierMesh().isChunkVisible("Blister1_D0"))
                    hierMesh().chunkVisible("Gore1_D0", true);
                hierMesh().chunkVisible("Gore2_D0", true);
            }
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
    }

    protected void moveFlap(float f)
    {
        float f1 = -45F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 90F * f, 0.0F);
        hiermesh.chunkSetAngles("Step_D0", 0.0F, 0.0F, -5F * f);
        float f1 = java.lang.Math.max(-f * 1500F, -90F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.HS_129.moveGear(hierMesh(), f);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            return super.cutFM(34, j, actor);

        case 36: // '$'
            return super.cutFM(37, j, actor);
        }
        return super.cutFM(i, j, actor);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("CF"))
        {
            if(java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z) > 0.13899999856948853D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F)
                FM.AS.hitTank(shot.initiator, 2, (int)com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, shot.mass * 9.21F));
            if(com.maddox.il2.objects.air.Aircraft.v1.x < -0.97399997711181641D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.3F && (double)(-shot.power) * com.maddox.il2.objects.air.Aircraft.v1.x > 19200D)
                {
                    killPilot(shot.initiator, 0);
                    shot.chunkName = "CF_D0";
                }
            } else
            if(com.maddox.il2.objects.air.Aircraft.v1.x < 0.76599997282028198D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.12F && (double)shot.power * java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z) > (double)(1600F * (com.maddox.il2.objects.air.Aircraft.v1.z <= -0.5D ? 1.0F : 6F)))
                {
                    killPilot(shot.initiator, 0);
                    shot.chunkName = "CF_D0";
                }
            } else
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.06F && (double)shot.power * com.maddox.il2.objects.air.Aircraft.v1.x > 12800D)
            {
                killPilot(shot.initiator, 0);
                shot.chunkName = "CF_D0";
            }
        }
        if(shot.chunkName.startsWith("Pilot"))
        {
            killPilot(shot.initiator, 0);
            shot.chunkName = "CF_D0";
        }
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.25F)
            FM.AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.25F)
            FM.AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Engine1"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3F) < shot.mass)
                FM.AS.hitEngine(shot.initiator, 0, 1);
            if(com.maddox.il2.objects.air.Aircraft.v1.y > 0.89999997615814209D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.067F)
                FM.AS.hitEngine(shot.initiator, 0, 5);
        }
        if(shot.chunkName.startsWith("Engine2"))
        {
            if(com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 3F) < shot.mass)
                FM.AS.hitEngine(shot.initiator, 1, 1);
            if(com.maddox.il2.objects.air.Aircraft.v1.y < -0.89999997615814209D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.067F)
                FM.AS.hitEngine(shot.initiator, 1, 5);
        }
        super.msgShot(shot);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.HS_129.class;
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryGermany);
    }
}
