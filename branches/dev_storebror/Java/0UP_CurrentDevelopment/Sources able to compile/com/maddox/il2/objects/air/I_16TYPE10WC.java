package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class I_16TYPE10WC extends I_16
    implements TypeFighter, TypeTNBFighter
{

    public I_16TYPE10WC()
    {
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void moveFlap(float f)
    {
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -55F * f, 0.0F);
        hierMesh().chunkSetAngles("Flap03_D0", 0.0F, -55F * f, 0.0F);
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = I_16TYPE10WC.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "I-16");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/I-16type10WC/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitI_16TYPE_Early.class
        });
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/I-16type10WC.fmd");
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 0, 0, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASk 750", "MGunShKASk 750", "MGunShKASk 650", "MGunShKASk 650", null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
