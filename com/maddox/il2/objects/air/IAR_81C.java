// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   IAR_81C.java

package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            IAR_8X, PaintSchemeFMPar01, TypeFighter, TypeStormovik, 
//            TypeDiveBomber, NetAircraft

public class IAR_81C extends com.maddox.il2.objects.air.IAR_8X
    implements com.maddox.il2.objects.air.TypeFighter, com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeDiveBomber
{

    public IAR_81C()
    {
    }

    public boolean typeDiveBomberToggleAutomation()
    {
        return false;
    }

    public void typeDiveBomberAdjAltitudeReset()
    {
    }

    public void typeDiveBomberAdjAltitudePlus()
    {
    }

    public void typeDiveBomberAdjAltitudeMinus()
    {
    }

    public void typeDiveBomberAdjVelocityReset()
    {
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
    }

    public void typeDiveBomberAdjDiveAngleReset()
    {
    }

    public void typeDiveBomberAdjDiveAnglePlus()
    {
    }

    public void typeDiveBomberAdjDiveAngleMinus()
    {
    }

    public void typeDiveBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
    }

    public void typeDiveBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.IAR_81C.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "IAR 80");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/IAR-81a/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/IAR-81a.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitIAR81.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8323F);
        com.maddox.il2.objects.air.IAR_81C.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 1, 1, 3, 3, 3, 3, 
            3
        });
        com.maddox.il2.objects.air.IAR_81C.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", 
            "_ExternalBomb05"
        });
        com.maddox.il2.objects.air.IAR_81C.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303k 1000", "MGunBrowning303k 1000", "MGunBrowning303ki 1000", "MGunBrowning303ki 1000", "MGunMG15120k 250", "MGunMG15120k 250", null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.IAR_81C.weaponsRegister(class1, "2xSC50", new java.lang.String[] {
            "MGunBrowning303k 1000", "MGunBrowning303k 1000", "MGunBrowning303ki 1000", "MGunBrowning303ki 1000", "MGunMG15120k 250", "MGunMG15120k 250", "BombGunSC50", "BombGunSC50", null, null, 
            null
        });
        com.maddox.il2.objects.air.IAR_81C.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunBrowning303k 1000", "MGunBrowning303k 1000", "MGunBrowning303ki 1000", "MGunBrowning303ki 1000", "MGunMG15120k 250", "MGunMG15120k 250", null, null, null, null, 
            "BombGunSC250"
        });
        com.maddox.il2.objects.air.IAR_81C.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
