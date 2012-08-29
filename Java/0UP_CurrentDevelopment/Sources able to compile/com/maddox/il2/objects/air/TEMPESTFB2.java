package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class TEMPESTFB2 extends TEMPEST
{

    public TEMPESTFB2()
    {
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        float f1 = (float)java.lang.Math.sin(Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
        hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    static java.lang.Class class$com$maddox$il2$objects$air$TEMPESTFB2;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Tempest");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/TempestFBMkII(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/TempestFBMkII(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/TempestMkII.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitTemp5.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.93655F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 
            9, 9, 2, 2, 2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalDev05", "_ExternalDev06", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xdt", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "FuelTankGun_TankTempest 1", "FuelTankGun_TankTempest 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonTEMPESTPLN1 1", "PylonTEMPESTPLN2 1", "BombGun250lbsE 1", "BombGun250lbsE 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonTEMPESTPLN1 1", "PylonTEMPESTPLN2 1", "BombGun500lbsE 1", "BombGun500lbsE 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2x1000", new java.lang.String[] {
            "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", "MGunHispanoMkIk 200", null, null, "PylonTEMPESTPLN1 1", "PylonTEMPESTPLN2 1", "BombGun1000lbs 1", "BombGun1000lbs 1", 
            null, null, null, null, null, null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
