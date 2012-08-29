package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class SU_26M2 extends IAR_8X
    implements TypeFighter, TypeTNBFighter
{

    public SU_26M2()
    {
        kangle = 0.0F;
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

    public void moveCockpitDoor(float f)
    {
        hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 0.0F, 110F * f);
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
    }

    protected void moveGear(float f)
    {
        SU_26M2.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        if(FM.CT.getGear() >= 0.98F)
            hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void update(float f)
    {
        hierMesh().chunkSetAngles("Stvorka1_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka2_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka3_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka4_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka5_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka6_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka7_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka8_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka9_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka10_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka11_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka12_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka13_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka14_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka15_D0", 0.0F, -73F * kangle, 0.0F);
        hierMesh().chunkSetAngles("Stvorka16_D0", 0.0F, -73F * kangle, 0.0F);
        kangle = 0.95F * kangle + 0.05F * FM.EI.engines[0].getControlRadiator();
        super.update(f);
    }

    private float kangle;
    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = SU_26M2.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Su-26");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/SU_26/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1989F);
        com.maddox.rts.Property.set(class1, "yearExpired", 2050);
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitSU26SAS.class
        });
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Su-26.fmd");
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            null, null, null, null
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
