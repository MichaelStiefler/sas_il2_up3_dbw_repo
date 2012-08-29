package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.rts.Property;

public class P_11F extends P_11
{

    public P_11F()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.cur().camouflage == 1)
        {
            hierMesh().chunkVisible("GearL1_D0", false);
            hierMesh().chunkVisible("GearL11_D0", true);
            hierMesh().chunkVisible("GearR1_D0", false);
            hierMesh().chunkVisible("GearR11_D0", true);
            FM.CT.bHasBrakeControl = false;
        }
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            for(int i = 0; i < aobj.length; i++)
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.Bomb)
                {
                    hierMesh().chunkVisible("RackL_D0", true);
                    hierMesh().chunkVisible("RackR_D0", true);
                }

        }
    }

    protected void moveFan(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            super.moveFan(f);
            float f1 = Aircraft.cvt(FM.Or.getTangage(), -30F, 30F, -30F, 30F);
            if(FM.Gears.onGround() && FM.CT.getGear() > 0.9F && FM.getSpeed() > 5F)
            {
                if(FM.Gears.gWheelSinking[0] > 0.0F)
                    hierMesh().chunkSetAngles("GearL11_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 1.0F), com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 1.0F), com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 1.0F) - f1);
                else
                    hierMesh().chunkSetAngles("GearL11_D0", 0.0F, 0.0F, -f1);
                if(FM.Gears.gWheelSinking[1] > 0.0F)
                    hierMesh().chunkSetAngles("GearR11_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 1.0F), com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 1.0F), com.maddox.il2.ai.World.Rnd().nextFloat(-1F, 1.0F) - f1);
                else
                    hierMesh().chunkSetAngles("GearR11_D0", 0.0F, 0.0F, -f1);
            } else
            {
                hierMesh().chunkSetAngles("GearL11_D0", 0.0F, 0.0F, -f1);
                hierMesh().chunkSetAngles("GearR11_D0", 0.0F, 0.0F, -f1);
            }
        }
    }

    static 
    {
        java.lang.Class class1 = P_11F.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P.11");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-11f(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "meshName_ro", "3DO/Plane/P-11f(Romanian)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_ro", new PaintSchemeFMPar00());
        com.maddox.rts.Property.set(class1, "originCountry", PaintScheme.countryRomania);
        com.maddox.rts.Property.set(class1, "yearService", 1936F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-11f.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", CockpitP_11F.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.7956F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 1, 1, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303sipzl 750", "MGunBrowning303sipzl 750", "MGunBrowning303ki 350", "MGunBrowning303ki 350", null, null
        });
        Aircraft.weaponsRegister(class1, "2puw125", new java.lang.String[] {
            "MGunBrowning303sipzl 750", "MGunBrowning303sipzl 750", "MGunBrowning303ki 350", "MGunBrowning303ki 350", "BombGunPuW125", "BombGunPuW125"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
