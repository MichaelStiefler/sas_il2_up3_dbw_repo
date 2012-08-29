package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class LAGG_3SERIES1 extends LAGG_3
    implements TypeTNBFighter
{

    public LAGG_3SERIES1()
    {
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[0] = -Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.58F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.CT.bHasCockpitDoorControl = true;
        FM.CT.dvCockpitDoor = 0.75F;
    }

    static 
    {
        java.lang.Class class1 = LAGG_3SERIES1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "LaGG");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/LaGG-3series1/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1944.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/LaGG-3series4.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitLAGG_3SERIES4.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.69445F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 0, 0, 1, 3, 3, 9, 2, 9, 
            2, 9, 2, 9, 2, 9, 2, 9, 2, 9, 
            2, 9, 2, 9, 9
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_CANNON01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev01", "_ExternalRock01", "_ExternalDev02", 
            "_ExternalRock02", "_ExternalDev03", "_ExternalRock03", "_ExternalDev04", "_ExternalRock04", "_ExternalDev05", "_ExternalRock05", "_ExternalDev06", "_ExternalRock06", "_ExternalDev07", 
            "_ExternalRock07", "_ExternalDev08", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunUBk 220", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xFAB50", new java.lang.String[] {
            "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunUBk 220", "BombGunFAB50", "BombGunFAB50", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2xDROPTANK", new java.lang.String[] {
            "MGunUBs 220", "MGunUBs 220", "MGunShKASs 325", "MGunShKASs 325", "MGunUBk 220", null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "FuelTankGun_Tank80", "FuelTankGun_Tank80"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null
        });
    }
}
