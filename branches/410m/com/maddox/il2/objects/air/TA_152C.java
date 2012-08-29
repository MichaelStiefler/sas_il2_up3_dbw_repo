// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TA_152C.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;
import com.maddox.rts.Time;

// Referenced classes of package com.maddox.il2.objects.air:
//            FW_190, PaintSchemeFMPar06, TypeX4Carrier, NetAircraft

public class TA_152C extends com.maddox.il2.objects.air.FW_190
    implements com.maddox.il2.objects.air.TypeX4Carrier
{

    public TA_152C()
    {
        bToFire = false;
        tX4Prev = 0L;
        kangle = 0.0F;
        deltaAzimuth = 0.0F;
        deltaTangage = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -77F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, -102F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, -102F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 20F * f, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -94F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.TA_152C.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() < 0.98F)
        {
            return;
        } else
        {
            hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
            return;
        }
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.TA_152C.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.44F, 0.0F, 0.44F);
        hierMesh().chunkSetLocate("GearL2a_D0", xyz, ypr);
        xyz[1] = com.maddox.il2.objects.air.TA_152C.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.44F, 0.0F, 0.44F);
        hierMesh().chunkSetLocate("GearR2a_D0", xyz, ypr);
    }

    protected void moveFlap(float f)
    {
        float f1 = -50F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -f1, 0.0F);
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if((FM instanceof com.maddox.il2.fm.RealFlightModel) && ((com.maddox.il2.fm.RealFlightModel)FM).isRealMode() || !flag || !(FM instanceof com.maddox.il2.ai.air.Pilot))
            return;
        com.maddox.il2.ai.air.Pilot pilot = (com.maddox.il2.ai.air.Pilot)FM;
        if(pilot.get_maneuver() == 63 && pilot.target != null)
        {
            com.maddox.JGP.Point3d point3d = new Point3d(pilot.target.Loc);
            point3d.sub(FM.Loc);
            FM.Or.transformInv(point3d);
            if((point3d.x > 4000D && point3d.x < 5500D || point3d.x > 100D && point3d.x < 5000D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.33F) && com.maddox.rts.Time.current() > tX4Prev + 10000L)
            {
                bToFire = true;
                tX4Prev = com.maddox.rts.Time.current();
            }
        }
    }

    public void update(float f)
    {
        for(int i = 1; i < 15; i++)
            hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10F * kangle, 0.0F);

        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        super.update(f);
    }

    public void typeX4CAdjSidePlus()
    {
        deltaAzimuth = 1.0F;
    }

    public void typeX4CAdjSideMinus()
    {
        deltaAzimuth = -1F;
    }

    public void typeX4CAdjAttitudePlus()
    {
        deltaTangage = 1.0F;
    }

    public void typeX4CAdjAttitudeMinus()
    {
        deltaTangage = -1F;
    }

    public void typeX4CResetControls()
    {
        deltaAzimuth = deltaTangage = 0.0F;
    }

    public float typeX4CgetdeltaAzimuth()
    {
        return deltaAzimuth;
    }

    public float typeX4CgetdeltaTangage()
    {
        return deltaTangage;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public boolean bToFire;
    private long tX4Prev;
    private float kangle;
    private float deltaAzimuth;
    private float deltaTangage;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ta.152");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ta-152C/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1944.6F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ta-152C.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitTA_152C.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.755F);
        com.maddox.il2.objects.air.TA_152C.weaponTriggersRegister(class1, new int[] {
            0, 1, 1, 1, 1, 9, 9, 2, 2, 2, 
            2, 3, 3
        });
        com.maddox.il2.objects.air.TA_152C.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON03", "_CANNON04", "_CANNON05", "_CANNON06", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", 
            "_ExternalRock02", "_ExternalBomb02", "_ExternalBomb03"
        });
        com.maddox.il2.objects.air.TA_152C.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108k 90", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.TA_152C.weaponsRegister(class1, "2x4", new java.lang.String[] {
            "MGunMK108k 90", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "PylonETC250", "PylonETC250", "RocketGunX4 1", "BombGunNull 1", "RocketGunX4 1", 
            "BombGunNull 1", null, null
        });
        com.maddox.il2.objects.air.TA_152C.weaponsRegister(class1, "2xSC250", new java.lang.String[] {
            "MGunMK108k 90", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "MGunMG15120s 175", "PylonETC250", "PylonETC250", null, null, null, 
            null, "BombGunSC250 1", "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.TA_152C.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
