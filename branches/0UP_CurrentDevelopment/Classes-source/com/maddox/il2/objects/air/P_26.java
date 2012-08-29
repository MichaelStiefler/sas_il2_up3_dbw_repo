// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   P_26.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_26x, PaintSchemeFMPar01, NetAircraft, Aircraft

public class P_26 extends com.maddox.il2.objects.air.P_26x
{

    public P_26()
    {
        bChangedExts = false;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.CT.setTrimRudderControl(0.007F);
        FM.CT.setTrimAileronControl(0.007F);
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        bChangedExts = true;
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        bChangedExts = true;
        if(FM.isPlayers())
            bChangedPit = true;
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

    public boolean bChangedExts;
    public static boolean bChangedPit = false;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "P-26");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/P-26(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1932F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1946F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/P-26.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitP_26.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.032F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 9, 9, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.P_26.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_ExternalDev01", "_ExternalDev02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.P_26.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303s 500", "MGunBrowning303s 500", null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.P_26.weaponsRegister(class1, "4x25", new java.lang.String[] {
            "MGunBrowning303s 500", "MGunBrowning303s 500", "PylonF4FPLN2 1", "PylonF4FPLN2 1", "BombGunPuW125 1", "BombGunPuW125 1", "BombGunPuW125 1", "BombGunPuW125 1"
        });
        com.maddox.il2.objects.air.P_26.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
