// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   L2D.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme2, PaintSchemeBMPar03, PaintSchemeBCSPar01, TypeTransport, 
//            Aircraft, NetAircraft, PaintScheme

public class L2D extends com.maddox.il2.objects.air.Scheme2
    implements com.maddox.il2.objects.air.TypeTransport
{

    public L2D()
    {
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -45F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 20F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 20F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -120F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -120F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.L2D.moveGear(hierMesh(), f);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("WingLOut") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 6D)
            FM.AS.hitTank(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("WingROut") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 6D)
            FM.AS.hitTank(shot.initiator, 3, 1);
        if(shot.chunkName.startsWith("WingLIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 1.940000057220459D)
            FM.AS.hitTank(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("WingRIn") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F && java.lang.Math.abs(com.maddox.il2.objects.air.Aircraft.Pd.y) < 1.940000057220459D)
            FM.AS.hitTank(shot.initiator, 2, 1);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Nose") && com.maddox.il2.objects.air.Aircraft.Pd.x > 4.9000000953674316D && com.maddox.il2.objects.air.Aircraft.Pd.z > -0.090000003576278687D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
            if(com.maddox.il2.objects.air.Aircraft.Pd.y > 0.0D)
            {
                killPilot(shot.initiator, 0);
                FM.setCapableOfBMP(false, shot.initiator);
            } else
            {
                killPilot(shot.initiator, 1);
            }
        if(FM.AS.astateEngineStates[0] > 2 && FM.AS.astateEngineStates[1] > 2 && com.maddox.il2.ai.World.Rnd().nextInt(0, 99) < 33)
            FM.setCapableOfBMP(false, shot.initiator);
        super.msgShot(shot);
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
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 3; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        default:
            break;

        case 13: // '\r'
            killPilot(this, 0);
            killPilot(this, 1);
            break;

        case 35: // '#'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                FM.AS.hitTank(this, 1, com.maddox.il2.ai.World.Rnd().nextInt(2, 6));
            break;

        case 38: // '&'
            if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.25F)
                FM.AS.hitTank(this, 2, com.maddox.il2.ai.World.Rnd().nextInt(2, 6));
            break;
        }
        return super.cutFM(i, j, actor);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.L2D.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "L2D");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/L2D(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar03());
        com.maddox.rts.Property.set(class1, "meshName_ja", "3DO/Plane/L2D(ja)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ja", new PaintSchemeBCSPar01());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryJapan);
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 2999.9F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/DC-3.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_BombSpawn01"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "5xCargoA", new java.lang.String[] {
            "BombGunCargoA 5"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
