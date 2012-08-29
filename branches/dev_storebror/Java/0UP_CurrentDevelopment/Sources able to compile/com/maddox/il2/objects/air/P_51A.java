package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class P_51A extends P_51
{

    public P_51A()
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
        java.lang.Class class1 = P_51A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-51");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/P-51A(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-51A(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        com.maddox.rts.Property.set(class1, "meshName_gb", "3DO/Plane/P-51A(GB)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_gb", new PaintSchemeFCSPar05());
        com.maddox.rts.Property.set(class1, "yearService", 1942.8F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1947.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-51A.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitP_51B.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.03F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 9, 9, 3, 3, 3, 3, 
            9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", "_ExternalBomb01", 
            "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 280", "MGunBrowning50k 280", null, null, null, null, null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 280", "MGunBrowning50k 280", "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun250lbs 1", "BombGunNull 1", "BombGun250lbs 1", "BombGunNull 1", 
            null, null
        });
        Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 280", "MGunBrowning50k 280", "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun500lbs 1", "BombGunNull 1", "BombGun500lbs 1", "BombGunNull 1", 
            null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
