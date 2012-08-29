package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class JEEP extends CAR
{

    public JEEP()
    {
    }

    public void update(float f)
    {
        if(((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.bIsAboutToBailout)
            hierMesh().chunkVisible("Helm_D0", false);
        ((com.maddox.il2.fm.FlightModelMain) (super.FM)).EI.engines[0].addVside *= 9.9999999999999995E-008D;
        super.update(f);
    }

    static 
    {
        java.lang.Class class1 = JEEP.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Jeep");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Jeep/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Jeep.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitJeep.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8941F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
