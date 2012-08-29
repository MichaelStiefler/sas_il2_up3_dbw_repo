package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class U_2VS_shkas extends U_2VS
{

    public U_2VS_shkas()
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

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            if(!FM.AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore1_D0", true);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            if(!FM.AS.bIsAboutToBailout)
                hierMesh().chunkVisible("Gore2_D0", true);
            break;
        }
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = U_2VS_shkas.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "U-2");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/U-2VS_new/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1967F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitU2VSs.class, CockpitU2Gunner.class
        });
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/U-2LSH.fmd");
        Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 3, 3, 3, 3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_BombSpawn01"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASt 750", null, null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "2ao10", new java.lang.String[] {
            "MGunShKASt 750", "BombGunAO10 1", "BombGunAO10 1", null, null, null
        });
        Aircraft.weaponsRegister(class1, "4ao10", new java.lang.String[] {
            "MGunShKASt 750", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1"
        });
        Aircraft.weaponsRegister(class1, "2fab50", new java.lang.String[] {
            "MGunShKASt 750", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null
        });
        Aircraft.weaponsRegister(class1, "4fab50", new java.lang.String[] {
            "MGunShKASt 750", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", null
        });
        Aircraft.weaponsRegister(class1, "2fab100", new java.lang.String[] {
            "MGunShKASt 750", "BombGunFAB100 1", "BombGunFAB100 1", null, null, null
        });
        Aircraft.weaponsRegister(class1, "2fab50_2fab100", new java.lang.String[] {
            "MGunShKASt 750", "BombGunFAB100 1", "BombGunFAB100 1", "BombGunFAB50 1", "BombGunFAB50 1", null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null
        });
    }
}
