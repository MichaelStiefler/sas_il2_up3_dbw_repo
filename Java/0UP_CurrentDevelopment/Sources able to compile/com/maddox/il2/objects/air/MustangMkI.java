package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class MustangMkI extends P_51
{

    public MustangMkI()
    {
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        if((double)f <= 0.5D)
        {
            hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f * 2.0F, 0.0F);
        } else
        {
            float f1 = (f - 0.5F) * 2.0F;
            hierMesh().chunkSetAngles("Blister2_D0", 0.0F, -185F * f1, 0.0F);
        }
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    static 
    {
        java.lang.Class class1 = MustangMkI.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-51");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MustangMkI(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/MustangMkI(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1943.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-51A.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitP_51B.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.03F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 0, 0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 500", "MGunBrowning303k 500", null, null, "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning50k 400", "MGunBrowning50k 400"
        });
        Aircraft.weaponsRegister(class1, "2x50cal", new java.lang.String[] {
            "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning303k 500", "MGunBrowning50k 400", "MGunBrowning50k 400"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
