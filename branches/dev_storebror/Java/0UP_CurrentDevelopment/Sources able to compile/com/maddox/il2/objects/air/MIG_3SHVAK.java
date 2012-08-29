package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class MIG_3SHVAK extends MIG_3
{

    public MIG_3SHVAK()
    {
        kangle = 0.0F;
    }

    public void update(float f)
    {
        if(FM.getSpeed() > 5F)
        {
            hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 0.9F), 0.0F);
            hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(FM.getAOA(), 6.8F, 11F, 0.0F, 0.9F), 0.0F);
        }
        hierMesh().chunkSetAngles("WaterFlap_D0", 0.0F, 30F * kangle, 0.0F);
        hierMesh().chunkSetAngles("OilRad1_D0", 0.0F, -20F * kangle, 0.0F);
        hierMesh().chunkSetAngles("OilRad2_D0", 0.0F, -20F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        super.update(f);
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[0] = -Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
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

    private float kangle;

    static 
    {
        java.lang.Class class1 = MIG_3SHVAK.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "MiG");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MiG-3ShVAK(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MiG-3ud.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitMIG_3UB.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.906F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShVAKs 250", "MGunShVAKs 250"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
