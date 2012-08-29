// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_87D3.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            JU_87, PaintSchemeBMPar02, TypeStormovik, NetAircraft

public class JU_87D3 extends com.maddox.il2.objects.air.JU_87
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public JU_87D3()
    {
        bDynamoLOperational = true;
        bDynamoROperational = true;
        dynamoOrient = 0.0F;
        bDynamoRotary = false;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(i == 33 || i == 34 || i == 9)
        {
            hierMesh().chunkVisible("GearL3_D0", false);
            hierMesh().chunkVisible("GearL3Rot_D0", false);
            bDynamoLOperational = false;
        }
        if(i == 36 || i == 37 || i == 10)
        {
            hierMesh().chunkVisible("GearR3_D0", false);
            hierMesh().chunkVisible("GearR3Rot_D0", false);
            bDynamoROperational = false;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveFan(float f)
    {
        pk = java.lang.Math.abs((int)(FM.Vwld.length() / 14D));
        if(pk >= 1)
            pk = 1;
        if(bDynamoRotary != (pk == 1))
        {
            bDynamoRotary = pk == 1;
            if(bDynamoLOperational)
            {
                hierMesh().chunkVisible("GearL3_D0", !bDynamoRotary);
                hierMesh().chunkVisible("GearL3Rot_D0", bDynamoRotary);
            }
            if(bDynamoROperational)
            {
                hierMesh().chunkVisible("GearR3_D0", !bDynamoRotary);
                hierMesh().chunkVisible("GearR3Rot_D0", bDynamoRotary);
            }
        }
        dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F : (float)((double)dynamoOrient - FM.Vwld.length() * 1.5444015264511108D) % 360F;
        hierMesh().chunkSetAngles("GearL3_D0", 0.0F, dynamoOrient, 0.0F);
        hierMesh().chunkSetAngles("GearR3_D0", 0.0F, dynamoOrient + 12.5F, 0.0F);
        super.moveFan(f);
    }

    protected void moveAirBrake(float f)
    {
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 80F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 80F * f, 0.0F);
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
        java.lang.Class class1 = com.maddox.il2.objects.air.JU_87D3.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-87D-3.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Ju-87D-3/hier.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-87");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitJU_87D3.class, com.maddox.il2.objects.air.CockpitJU_87D3_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8499F);
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.il2.objects.air.JU_87D3.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 10, 3, 3, 3, 3, 3, 3, 
            3
        });
        com.maddox.il2.objects.air.JU_87D3.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", 
            "_ExternalBomb07"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xSC500", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xSC500_4xSC50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xSC500_4xAB23", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", null, null, 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xSC500_4xSC70", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", null, null, 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xSC500_2xSC250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xAB500", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xAB500_4xSC50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xAB500_4xAB23", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", "BombGunAB23 1", null, null, 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xAB500_4xSC70", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", null, null, 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xAB500_2xSC250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "3xSC250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xSC1000", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunSC1000 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xAB1000", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunAB1000 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xPC1600", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunPC1600 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "1xSC1800", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunSC1800 1"
        });
        com.maddox.il2.objects.air.JU_87D3.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
