// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I_185M82A.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.objects.weapons.BombFAB100;
import com.maddox.il2.objects.weapons.BombFAB250;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            I_185, PaintSchemeFMPar01, NetAircraft

public class I_185M82A extends com.maddox.il2.objects.air.I_185
{

    public I_185M82A()
    {
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("Water1_D0", 0.0F, -20F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        for(int i = 1; i < 5; i++)
            hierMesh().chunkSetAngles("Oil" + i + "_D0", 0.0F, -15F * FM.EI.engines[0].getControlRadiator(), 0.0F);

        super.update(f);
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, -65F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC3_D0", 0.0F, com.maddox.il2.objects.air.I_185M82A.cvt(f, 0.02F, 0.1F, 0.0F, -65F), 0.0F);
        hiermesh.chunkSetAngles("GearC4_D0", 0.0F, com.maddox.il2.objects.air.I_185M82A.cvt(f, 0.02F, 0.1F, 0.0F, -65F), 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 87F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL7_D0", 0.0F, com.maddox.il2.objects.air.I_185M82A.cvt(f, 0.02F, 0.1F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearL8_D0", 0.0F, -90F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 87F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR7_D0", 0.0F, com.maddox.il2.objects.air.I_185M82A.cvt(f, 0.02F, 0.1F, 0.0F, -85F), 0.0F);
        hiermesh.chunkSetAngles("GearR8_D0", 0.0F, -90F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.I_185M82A.moveGear(hierMesh(), f);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj == null)
            return;
        for(int i = 0; i < aobj.length; i++)
        {
            if(aobj[i] instanceof com.maddox.il2.objects.weapons.BombFAB100)
            {
                hierMesh().chunkVisible("RackL1_D0", true);
                hierMesh().chunkVisible("RackR1_D0", true);
                return;
            }
            if(aobj[i] instanceof com.maddox.il2.objects.weapons.BombFAB250)
            {
                hierMesh().chunkVisible("RackL2_D0", true);
                hierMesh().chunkVisible("RackR2_D0", true);
                return;
            }
        }

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
        java.lang.Class class1 = com.maddox.il2.objects.air.I_185M82A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-185");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-185(M-82A)(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-185M-82A.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitI_185M82.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.89135F);
        com.maddox.il2.objects.air.I_185M82A.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 3, 3, 3, 3, 9, 9, 2, 
            2, 2, 2, 2, 2
        });
        com.maddox.il2.objects.air.I_185M82A.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", 
            "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06"
        });
        com.maddox.il2.objects.air.I_185M82A.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220", null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.I_185M82A.weaponsRegister(class1, "4fab100", new java.lang.String[] {
            "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.I_185M82A.weaponsRegister(class1, "2fab250", new java.lang.String[] {
            "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220", "BombGunFAB250 1", "BombGunFAB250 1", null, null, null, null, null, 
            null, null, null, null, null
        });
        com.maddox.il2.objects.air.I_185M82A.weaponsRegister(class1, "6rs82", new java.lang.String[] {
            "MGunShVAKsi 220", "MGunShVAKsi 220", "MGunShVAKsi 220", null, null, null, null, "PylonRO_82_3", "PylonRO_82_3", "RocketGunRS82 1", 
            "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1"
        });
        com.maddox.il2.objects.air.I_185M82A.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
