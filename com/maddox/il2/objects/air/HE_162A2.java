// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_162A2.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HE_162, PaintSchemeFMPar06, NetAircraft

public class HE_162A2 extends com.maddox.il2.objects.air.HE_162
{

    public HE_162A2()
    {
    }

    protected void moveRudder(float f)
    {
        hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -50F * f, 0.0F);
        hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -50F * f, 0.0F);
        resetYPRmodifier();
        xyz[1] = com.maddox.il2.objects.air.HE_162A2.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 0.0632F);
        if(FM.CT.getGear() > 0.99F)
            ypr[1] = 40F * FM.CT.getRudder();
        hierMesh().chunkSetLocate("GearC25_D0", xyz, ypr);
        hierMesh().chunkSetAngles("GearC27_D0", 0.0F, com.maddox.il2.objects.air.HE_162A2.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, -15F), 0.0F);
        hierMesh().chunkSetAngles("GearC28_D0", 0.0F, com.maddox.il2.objects.air.HE_162A2.cvt(FM.Gears.gWheelSinking[2], 0.0F, 0.0632F, 0.0F, 30F), 0.0F);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 33: // '!'
            return super.cutFM(34, j, actor);

        case 36: // '$'
            return super.cutFM(37, j, actor);

        case 17: // '\021'
            return super.cutFM(11, j, actor);

        case 18: // '\022'
            return super.cutFM(12, j, actor);
        }
        return super.cutFM(i, j, actor);
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
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He-162");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/He-162A-2/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar06());
        com.maddox.rts.Property.set(class1, "yearService", 1944.2F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-162A-2.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitHE_162A2.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.5099F);
        com.maddox.il2.objects.air.HE_162A2.weaponTriggersRegister(class1, new int[] {
            0, 0
        });
        com.maddox.il2.objects.air.HE_162A2.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02"
        });
        com.maddox.il2.objects.air.HE_162A2.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15120k 120", "MGunMG15120k 120"
        });
        com.maddox.il2.objects.air.HE_162A2.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null
        });
    }
}
