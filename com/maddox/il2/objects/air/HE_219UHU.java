// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   HE_219UHU.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HE_219, PaintSchemeBMPar05, NetAircraft, Aircraft

public class HE_219UHU extends com.maddox.il2.objects.air.HE_219
{

    public HE_219UHU()
    {
        bKeelUp = true;
    }

    public void update(float f)
    {
        super.update(f);
    }

    protected void hitBone(java.lang.String s, com.maddox.il2.ai.Shot shot, com.maddox.JGP.Point3d point3d)
    {
        if(s.startsWith("xxcannon01"))
        {
            debuggunnery("Armament System: Left Wing Cannon: Disabled..");
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 1);
            getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(6.98F, 24.35F), shot);
            return;
        }
        if(s.startsWith("xxcannon02"))
        {
            debuggunnery("Armament System: Right Wing Cannon: Disabled..");
            ((com.maddox.il2.fm.FlightModelMain) (super.FM)).AS.setJamBullets(1, 2);
            getEnergyPastArmor(com.maddox.il2.ai.World.Rnd().nextFloat(6.98F, 24.35F), shot);
            return;
        } else
        {
            super.hitBone(s, shot, point3d);
            return;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            break;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    private boolean bKeelUp;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "He219UHU");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/He-219UHU/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeBMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1943F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1948F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/He-219.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHE_219.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.00705F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 2, 2, 1, 1, 1, 1, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_CANNON05", "_CANNON06", "_CANNON07", "_CANNON08", "_ExternalDev01", "_ExternalDev02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMK108nt 400", "MGunMK108nt 400", null, null, "MGunMG15120nt 500", "MGunMG15120nt 500", "MGunMG15120nt 500", "MGunMG15120nt 500", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R1: 6 x MG151/20", new java.lang.String[] {
            "MGunMG15120nt 400", "MGunMG15120nt 400", null, null, "MGunMG15120nt 500", "MGunMG15120nt 500", "MGunMG15120nt 500", "MGunMG15120nt 500", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R2: 4 x Mk103+2 x MG151/20", new java.lang.String[] {
            "MGunMG15120nt 400", "MGunMG15120nt 400", null, null, "MGunMK103nt 500", "MGunMK103nt 500", "MGunMK103nt 500", "MGunMK103nt 500", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R3: 4 x Mk108+2 x MG151/20", new java.lang.String[] {
            "MGunMG15120nt 400", "MGunMG15120nt 400", null, null, "MGunMK108nt 500", "MGunMK108nt 500", "MGunMK108nt 500", "MGunMK108nt 500", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "R4: 4 x MG151/20+2 x Mk108(SM)", new java.lang.String[] {
            "MGunMG15120nt 400", "MGunMG15120nt 400", "MGunMK108nt 100", "MGunMK108nt 100", null, null, "MGunMG15120nt 500", "MGunMG15120nt 500", null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
