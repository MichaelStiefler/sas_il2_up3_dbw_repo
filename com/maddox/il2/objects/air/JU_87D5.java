// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_87D5.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            JU_87, PaintSchemeBMPar02, TypeStormovik, NetAircraft, 
//            Aircraft

public class JU_87D5 extends com.maddox.il2.objects.air.JU_87
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public JU_87D5()
    {
        fDiveVelocity = 500F;
        fDiveAngle = 70F;
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

        fDiveAngle = -FM.Or.getTangage();
        if(fDiveAngle > 89F)
            fDiveAngle = 89F;
        if(fDiveAngle < 10F)
            fDiveAngle = 10F;
        super.update(f);
    }

    public void typeDiveBomberAdjVelocityReset()
    {
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
        fDiveVelocity += 10F;
        if(fDiveVelocity > 700F)
            fDiveVelocity = 700F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fDiveVelocity)
        });
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
        fDiveVelocity -= 10F;
        if(fDiveVelocity < 150F)
            fDiveVelocity = 150F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fDiveVelocity)
        });
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

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public float fDiveVelocity;
    public float fDiveAngle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.JU_87D5.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-87D-5.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ju-87D-5/hier.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-87");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitJU_87D5.class, com.maddox.il2.objects.air.CockpitJU_87D3_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8499F);
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            1, 1, 10, 10, 3, 3, 3, 3, 3, 3, 
            3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", 
            "_ExternalBomb07"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500_4xSC50", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500_4xSC70", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", null, null, 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500_2xSC250", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunSC500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB500", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB500_4xSC50", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB500_4xSC70", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", null, null, 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB500_2xSC250", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunAB500 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xSC250", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", 
            "BombGunSC250 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC1000", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunSC1000 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB1000", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunAB1000 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xPC1600", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunPC1600 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC1800", new java.lang.String[] {
            "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, 
            "BombGunSC1800 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null
        });
    }
}
