package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class MSMORKO extends MS400X
{

    public MSMORKO()
    {
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("OilRad_D0", 0.0F, -20F * FM.EI.engines[0].getControlRadiator(), 0.0F);
        super.update(f);
    }

    static 
    {
        java.lang.Class class1 = MSMORKO.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Morane");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/MSMorko(fi)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName_fi", "3DO/Plane/MSMorko(fi)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fi", new PaintSchemeFCSPar02());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MSMorko(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1944F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1951.8F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MSMorko.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitMorane.class
        });
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
