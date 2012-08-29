package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class YAK_7V extends YAK_7A
    implements TypeFighter, TypeTNBFighter, TypeScout
{

    public YAK_7V()
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

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
        YAK_7V.moveGear(hierMesh(), f);
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = YAK_7V.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Yak");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/YAK-7V/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945F);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitYAK_7Vper.class, CockpitYAK_7Vzad.class
        });
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Yak-7v.fmd");
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASsi 500"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null
        });
    }
}
