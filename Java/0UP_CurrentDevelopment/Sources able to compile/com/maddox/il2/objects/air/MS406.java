package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class MS406 extends MS400X
{

    public MS406()
    {
    }

    public void update(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(FM.EI.engines[0].getControlRadiator(), 0.0F, 1.0F, 0.2F, 0.0F);
        hierMesh().chunkSetLocate("OilRad_D0", Aircraft.xyz, Aircraft.ypr);
        super.update(f);
    }

    static 
    {
        java.lang.Class class1 = MS406.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Morane");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/MS406(fi)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName_fi", "3DO/Plane/MS406(fi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MS406(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1951.8F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MS406.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitMorane.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.9119F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15k 300", "MGunMG15k 300", "MGunHispanoMkIki 60"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null
        });
    }
}
