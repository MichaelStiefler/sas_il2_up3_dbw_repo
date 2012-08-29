// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames safe 
// Source File Name:   P_51A.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_51, PaintSchemeFMPar05, PaintSchemeFCSPar05, Cockpit, 
//            NetAircraft, Aircraft

public class P_51A extends com.maddox.il2.objects.air.P_51
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

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.P_51A.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "iconFar_shortClassName", "P-51");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshNameDemo", "3DO/Plane/P-51A(GB)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName", "3DO/Plane/P-51A(Multi1)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme", ((java.lang.Object) (new PaintSchemeFMPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "meshName_gb", "3DO/Plane/P-51A(GB)/hier.him");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "PaintScheme_gb", ((java.lang.Object) (new PaintSchemeFCSPar05())));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearService", 1942.8F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "yearExpired", 1947.5F);
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "FlightModel", "FlightModels/P-51A.fmd");
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "cockpitClass", ((java.lang.Object) (new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitP_51B.class
        })));
        com.maddox.rts.Property.set(((java.lang.Object) (class1)), "LOSElevation", 1.03F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 9, 9, 3, 3, 3, 3, 
            9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb02", "_ExternalBomb01", 
            "_ExternalBomb01", "_ExternalBomb02"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 280", "MGunBrowning50k 280", null, null, null, null, null, null, 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x250", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 280", "MGunBrowning50k 280", "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun250lbs 1", "BombGunNull 1", "BombGun250lbs 1", "BombGunNull 1", 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2x500", new java.lang.String[] {
            "MGunBrowning50k 350", "MGunBrowning50k 350", "MGunBrowning50k 280", "MGunBrowning50k 280", "PylonP51PLN2 1", "PylonP51PLN2 1", "BombGun500lbs 1", "BombGunNull 1", "BombGun500lbs 1", "BombGunNull 1", 
            null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
