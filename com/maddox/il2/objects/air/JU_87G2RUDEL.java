// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_87G2RUDEL.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            JU_87, PaintSchemeSpecial, TypeStormovik, TypeAcePlane, 
//            NetAircraft

public class JU_87G2RUDEL extends com.maddox.il2.objects.air.JU_87
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeAcePlane
{

    public JU_87G2RUDEL()
    {
        bDynamoLOperational = true;
        bDynamoROperational = true;
        dynamoOrient = 0.0F;
        bDynamoRotary = false;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        FM.Skill = 3;
    }

    public void update(float f)
    {
        for(int i = 1; i < 5; i++)
            hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, 15F - 30F * FM.EI.engines[0].getControlRadiator(), 0.0F);

        super.update(f);
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean bDynamoLOperational;
    private boolean bDynamoROperational;
    private float dynamoOrient;
    private boolean bDynamoRotary;
    private int pk;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.JU_87G2RUDEL.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-87");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Ju-87G-2(ofRudel)/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeSpecial());
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-87G-2(ofRudel).fmd");
        com.maddox.il2.objects.air.JU_87G2RUDEL.weaponTriggersRegister(class1, new int[] {
            1, 1, 10, 10
        });
        com.maddox.il2.objects.air.JU_87G2RUDEL.weaponHooksRegister(class1, new java.lang.String[] {
            "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02"
        });
        com.maddox.il2.objects.air.JU_87G2RUDEL.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBK37JU87 12", "MGunBK37JU87 12", "MGunMG81t 250", "MGunMG81t 250"
        });
        com.maddox.il2.objects.air.JU_87G2RUDEL.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}