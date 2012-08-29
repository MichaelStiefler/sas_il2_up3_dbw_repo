package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class JU_87B1 extends JU_87
{

    public JU_87B1()
    {
        bDynamoOperational = true;
        dynamoOrient = 0.0F;
        bDynamoRotary = false;
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        if(i == 36 || i == 37 || i == 10)
        {
            hierMesh().chunkVisible("GearR3_D0", false);
            hierMesh().chunkVisible("GearR3Rot_D0", false);
            bDynamoOperational = false;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveFan(float f)
    {
        if(bDynamoOperational)
        {
            pk = java.lang.Math.abs((int)(FM.Vwld.length() / 14D));
            if(pk >= 1)
                pk = 1;
        }
        if(bDynamoRotary != (pk == 1))
        {
            bDynamoRotary = pk == 1;
            hierMesh().chunkVisible("GearR3_D0", !bDynamoRotary);
            hierMesh().chunkVisible("GearR3Rot_D0", bDynamoRotary);
        }
        dynamoOrient = bDynamoRotary ? (dynamoOrient - 17.987F) % 360F : (float)((double)dynamoOrient - FM.Vwld.length() * 1.5444015264511108D) % 360F;
        hierMesh().chunkSetAngles("GearR3_D0", 0.0F, dynamoOrient, 0.0F);
        super.moveFan(f);
    }

    protected void moveAirBrake(float f)
    {
        hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 80F * f, 0.0F);
        hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 80F * f, 0.0F);
    }

    public void update(float f)
    {
        for(int i = 1; i < 9; i++)
            hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, 0.0F * FM.EI.engines[0].getControlRadiator(), 0.0F);

        super.update(f);
    }

    private boolean bDynamoOperational;
    private float dynamoOrient;
    private boolean bDynamoRotary;
    private int pk;

    static 
    {
        java.lang.Class class1 = JU_87B1.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-87B-1.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Ju-87B-1/hier.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-87");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            CockpitJU_87B2.class, CockpitJU_87B2_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8499F);
        com.maddox.rts.Property.set(class1, "yearService", 1938.9F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 3, 3, 3, 3, 3, 3, 3, 
            3, 3
        });
        Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb03", 
            "_ExternalBomb04", "_ExternalBomb05"
        });
        Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, null, null, null, 
            null, null
        });
        Aircraft.weaponsRegister(class1, "4xSC50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", "BombGunSC50 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunNull 1", "BombGunNull 1", 
            "BombGunSC50 1", null
        });
        Aircraft.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, null, null, null, 
            null, "BombGunSC250 1"
        });
        Aircraft.weaponsRegister(class1, "1xAB250", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, null, null, null, 
            null, "BombGunAB250 1"
        });
        Aircraft.weaponsRegister(class1, "1xSC250_4xSC50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", "BombGunSC50 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunNull 1", "BombGunNull 1", 
            "BombGunSC50 1", "BombGunSC250 1"
        });
        Aircraft.weaponsRegister(class1, "1xAB250_4xSC50", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", "BombGunSC50 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunNull 1", "BombGunNull 1", 
            "BombGunSC50 1", "BombGunAB250 1"
        });
        Aircraft.weaponsRegister(class1, "1xSC500", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, null, null, null, 
            null, "BombGunSC500 1"
        });
        Aircraft.weaponsRegister(class1, "1xAB500", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, null, null, null, 
            null, "BombGunAB500 1"
        });
        Aircraft.weaponsRegister(class1, "1xSD500", new java.lang.String[] {
            "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, null, null, null, 
            null, "BombGunSD500 1"
        });
        Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null
        });
    }
}
