// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   MC_202.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme1, PaintSchemeFCSPar01, PaintSchemeFMPar01, TypeFighter, 
//            NetAircraft, PaintScheme, Aircraft

public class MC_202 extends com.maddox.il2.objects.air.Scheme1
    implements com.maddox.il2.objects.air.TypeFighter
{

    public MC_202()
    {
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("Head1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            break;
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.getAltitude() < 3000F)
            hierMesh().chunkVisible("HMask1_D0", false);
        else
            hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Pilot1_D0"));
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, -88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, -88F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -100F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL6_D0", 0.0F, -114F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR6_D0", 0.0F, -114F * f, 0.0F);
        float f1 = java.lang.Math.max(-f * 1500F, -80F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, f1, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.MC_202.moveGear(hierMesh(), f);
    }

    protected void moveFlap(float f)
    {
        float f1 = -45F * f;
        hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f1, 0.0F);
        hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f1, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.MC_202.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "M.C.202");
        com.maddox.rts.Property.set(class1, "meshNameDemo", "3DO/Plane/MC-202/hier.him");
        com.maddox.rts.Property.set(class1, "meshName_it", "3DO/Plane/MC-202/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_it", new PaintSchemeFCSPar01());
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/MC-202(Multi1)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar01());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryItaly);
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/MC-202.fmd");
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBredaSAFAT127si 370", "MGunBredaSAFAT127si 370", "MGunBredaSAFAT77k 500", "MGunBredaSAFAT77k 500"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
