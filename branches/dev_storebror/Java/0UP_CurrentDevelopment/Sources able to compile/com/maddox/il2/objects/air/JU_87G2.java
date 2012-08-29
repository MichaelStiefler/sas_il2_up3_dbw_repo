package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.rts.Property;

public class JU_87G2 extends JU_87G1
    implements TypeStormovik
{

    public JU_87G2()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(!(getGunByHookName("_MGUN01") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            hierMesh().chunkVisible("20mmL_D0", true);
        if(!(getGunByHookName("_MGUN02") instanceof com.maddox.il2.objects.weapons.GunEmpty))
            hierMesh().chunkVisible("20mmR_D0", true);
    }

    static 
    {
        java.lang.Class class1 = JU_87G2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-87G-2.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Ju-87D-5/hier_G2.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-87");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitJU_87D3.class, CockpitJU_87G1_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8499F);
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 10, 10, 0, 0, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBK37JU87 24", "MGunBK37JU87 24", "MGunMG81t 750", "MGunMG81t 750", null, null, "PylonJu87BK37 1", "PylonJu87BK37 1"
        });
        Aircraft.weaponsRegister(class1, "mg151-20", new java.lang.String[] {
            "MGunBK37JU87 24", "MGunBK37JU87 24", "MGunMG81t 750", "MGunMG81t 750", "MGunMG15120si 250", "MGunMG15120si 250", "PylonJu87BK37 1", "PylonJu87BK37 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
