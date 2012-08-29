// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   SPITFIRE12.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            SPITFIRE, PaintSchemeFMPar04, Aircraft, Cockpit, 
//            NetAircraft

public class SPITFIRE12 extends com.maddox.il2.objects.air.SPITFIRE
{

    public SPITFIRE12()
    {
        flapps = 0.0F;
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[1] = com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        float f1 = (float)java.lang.Math.sin(com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
        hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.0F, 0.6F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.2F, 1.0F, 0.0F, -95F), 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, -75F), 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.09F, 0.0F, -75F), 0.0F);
        hiermesh.chunkSetAngles("GearC5_D0", 0.0F, com.maddox.il2.objects.air.Aircraft.cvt(f, 0.01F, 0.09F, 0.0F, -75F), 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.SPITFIRE12.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -f, 0.0F);
    }

    public void moveWheelSink()
    {
        resetYPRmodifier();
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[0], 0.0F, 0.247F, 0.0F, -0.247F);
        hierMesh().chunkSetLocate("GearL3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
        com.maddox.il2.objects.air.Aircraft.xyz[2] = com.maddox.il2.objects.air.Aircraft.cvt(FM.Gears.gWheelSinking[1], 0.0F, 0.247F, 0.0F, 0.247F);
        hierMesh().chunkSetLocate("GearR3_D0", com.maddox.il2.objects.air.Aircraft.xyz, com.maddox.il2.objects.air.Aircraft.ypr);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private float flapps;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.SPITFIRE12.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "Spit");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/SpitfireMkXII(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar04())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/Spitfire-F-XII-G3-CW.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitSpit12.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 0.5926F);
        com.maddox.il2.objects.air.SPITFIRE12.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 9, 9, 3
        });
        com.maddox.il2.objects.air.SPITFIRE12.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_CANNON02", "_ExternalDev08", "_ExternalDev01", "_ExternalBomb01"
        });
        com.maddox.il2.objects.air.SPITFIRE12.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, null, null
        });
        com.maddox.il2.objects.air.SPITFIRE12.weaponsRegister(class1, "30gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit30New 1", null, null
        });
        com.maddox.il2.objects.air.SPITFIRE12.weaponsRegister(class1, "45gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit45New 1", null, null
        });
        com.maddox.il2.objects.air.SPITFIRE12.weaponsRegister(class1, "90gal", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", "FuelTankGun_TankSpit90New 1", null, null
        });
        com.maddox.il2.objects.air.SPITFIRE12.weaponsRegister(class1, "250lb", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitC", "BombGun250lbsE 1"
        });
        com.maddox.il2.objects.air.SPITFIRE12.weaponsRegister(class1, "500lb", new java.lang.String[] {
            "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunBrowning303k 350", "MGunHispanoMkIki 120", "MGunHispanoMkIki 120", null, "PylonSpitC", "BombGun500lbsE 1"
        });
        com.maddox.il2.objects.air.SPITFIRE12.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null
        });
    }
}
