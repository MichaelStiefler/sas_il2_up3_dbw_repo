package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public class DXXI_DU extends DXXI
{

    public DXXI_DU()
    {
    }

    protected void moveFan(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            super.moveFan(f);
            float f1 = FM.CT.getAileron();
            float f2 = FM.CT.getElevator();
            hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9F * f1, DXXI_DU.cvt(f2, -1F, 1.0F, -8F, 9.5F));
            hierMesh().chunkSetAngles("pilotarm2_d0", DXXI_DU.cvt(f1, -1F, 1.0F, 14F, -16F), 0.0F, DXXI_DU.cvt(f1, -1F, 1.0F, 6F, -8F) - DXXI_DU.cvt(f2, -1F, 1.0F, -37F, 35F));
            hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, DXXI_DU.cvt(f1, -1F, 1.0F, -16F, 14F) + DXXI_DU.cvt(f2, -1F, 0.0F, -61F, 0.0F) + DXXI_DU.cvt(f2, 0.0F, 1.0F, 0.0F, 43F));
        }
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
        if(hierMesh().isChunkVisible("GearR22_D2") && !hierMesh().isChunkVisible("gearr31_d0"))
        {
            hierMesh().chunkVisible("gearr31_d0", true);
            hierMesh().chunkVisible("gearr32_d0", true);
            com.maddox.il2.objects.Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("GearR22_D1"));
            wreckage.collide(true);
            com.maddox.JGP.Vector3d vector3d = new Vector3d();
            vector3d.set(FM.Vwld);
            wreckage.setSpeed(vector3d);
        }
        if(hierMesh().isChunkVisible("GearL22_D2") && !hierMesh().isChunkVisible("gearl31_d0"))
        {
            hierMesh().chunkVisible("gearl31_d0", true);
            hierMesh().chunkVisible("gearl32_d0", true);
            com.maddox.il2.objects.Wreckage wreckage1 = new Wreckage(this, hierMesh().chunkFind("GearL22_D1"));
            wreckage1.collide(true);
            com.maddox.JGP.Vector3d vector3d1 = new Vector3d();
            vector3d1.set(FM.Vwld);
            wreckage1.setSpeed(vector3d1);
        }
    }

    static 
    {
        java.lang.Class class1 = DXXI_DU.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "D.XXI");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/DXXI_DU/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00DXXI());
        com.maddox.rts.Property.set(class1, "yearService", 1938F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1940F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/FokkerDU.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitDXXI_SARJA4.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8472F);
        com.maddox.rts.Property.set(class1, "originCountry", PaintScheme.countryNetherlands);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        DXXI_DU.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 300", "MGunBrowning303k 300", "MGunBrowning303k 300", "MGunBrowning303k 300"
        });
        DXXI_DU.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
