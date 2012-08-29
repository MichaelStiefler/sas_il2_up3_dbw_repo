// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_88A17.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Pitot;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.ToKGUtils;
import com.maddox.rts.CLASS;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            JU_88NEW, PaintSchemeBMPar02, TypeStormovik, TypeBomber, 
//            TypeScout, TypeHasToKG, NetAircraft, Aircraft

public class JU_88A17 extends com.maddox.il2.objects.air.JU_88NEW
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeBomber, com.maddox.il2.objects.air.TypeScout, com.maddox.il2.objects.air.TypeHasToKG
{

    public JU_88A17()
    {
        diveMechStage = 0;
        bNDives = false;
        bSightAutomation = false;
        bSightBombDump = false;
        fSightCurDistance = 0.0F;
        fSightCurForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
        fSightCurAltitude = 850F;
        fSightCurSpeed = 150F;
        fSightCurReadyness = 0.0F;
        fDiveRecoveryAlt = 850F;
        fDiveVelocity = 150F;
        fDiveAngle = 70F;
        fAOB = 0.0F;
        fShipSpeed = 15F;
        spreadAngle = 0;
    }

    protected void nextDMGLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextDMGLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    protected void nextCUTLevel(java.lang.String s, int i, com.maddox.il2.engine.Actor actor)
    {
        super.nextCUTLevel(s, i, actor);
        if(FM.isPlayers())
            bChangedPit = true;
    }

    public void doWoundPilot(int i, float f)
    {
        switch(i)
        {
        case 1: // '\001'
            FM.turret[0].setHealth(f);
            break;

        case 2: // '\002'
            FM.turret[1].setHealth(f);
            FM.turret[2].setHealth(f);
            break;
        }
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

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("HMask2_D0", false);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            hierMesh().chunkVisible("HMask3_D0", false);
            break;
        }
    }

    public void typeBomberUpdate(float f)
    {
        if((double)java.lang.Math.abs(FM.Or.getKren()) > 4.5D)
        {
            fSightCurReadyness -= 0.0666666F * f;
            if(fSightCurReadyness < 0.0F)
                fSightCurReadyness = 0.0F;
        }
        if(fSightCurReadyness < 1.0F)
            fSightCurReadyness += 0.0333333F * f;
        else
        if(bSightAutomation)
            return;
    }

    public boolean typeBomberToggleAutomation()
    {
        bSightAutomation = false;
        bSightBombDump = false;
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
        spreadAngle++;
        if(spreadAngle > 30)
            spreadAngle = 30;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGSpread", new java.lang.Object[] {
            new Integer(spreadAngle)
        });
        FM.AS.setSpreadAngle(spreadAngle);
    }

    public void typeBomberAdjDistanceMinus()
    {
        spreadAngle--;
        if(spreadAngle < 0)
            spreadAngle = 0;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGSpread", new java.lang.Object[] {
            new Integer(spreadAngle)
        });
        FM.AS.setSpreadAngle(spreadAngle);
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
        fAOB++;
        if(fAOB > 180F)
            fAOB = 180F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGAOB", new java.lang.Object[] {
            new Integer((int)fAOB)
        });
        com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(FM, fAOB, fShipSpeed);
    }

    public void typeBomberAdjSideslipMinus()
    {
        fAOB--;
        if(fAOB < -180F)
            fAOB = -180F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGAOB", new java.lang.Object[] {
            new Integer((int)fAOB)
        });
        com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(FM, fAOB, fShipSpeed);
    }

    public void typeBomberAdjAltitudeReset()
    {
    }

    public void typeBomberAdjAltitudePlus()
    {
    }

    public void typeBomberAdjAltitudeMinus()
    {
    }

    public void typeBomberAdjSpeedReset()
    {
    }

    public void typeBomberAdjSpeedPlus()
    {
        fShipSpeed++;
        if(fShipSpeed > 35F)
            fShipSpeed = 35F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGSpeed", new java.lang.Object[] {
            new Integer((int)fShipSpeed)
        });
        com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(FM, fAOB, fShipSpeed);
    }

    public void typeBomberAdjSpeedMinus()
    {
        fShipSpeed--;
        if(fShipSpeed < 0.0F)
            fShipSpeed = 0.0F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGSpeed", new java.lang.Object[] {
            new Integer((int)fShipSpeed)
        });
        com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(FM, fAOB, fShipSpeed);
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeByte((bSightAutomation ? 1 : 0) | (bSightBombDump ? 2 : 0));
        netmsgguaranted.writeFloat(fSightCurDistance);
        netmsgguaranted.writeByte((int)fSightCurForwardAngle);
        netmsgguaranted.writeByte((int)((fSightCurSideslip + 3F) * 33.33333F));
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeByte((int)(fSightCurSpeed / 2.5F));
        netmsgguaranted.writeByte((int)(fSightCurReadyness * 200F));
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        int i = netmsginput.readUnsignedByte();
        bSightAutomation = (i & 1) != 0;
        bSightBombDump = (i & 2) != 0;
        fSightCurDistance = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readUnsignedByte();
        fSightCurSideslip = -3F + (float)netmsginput.readUnsignedByte() / 33.33333F;
        fSightCurAltitude = fDiveRecoveryAlt = netmsginput.readFloat();
        fSightCurSpeed = fDiveVelocity = (float)netmsginput.readUnsignedByte() * 2.5F;
        fSightCurReadyness = (float)netmsginput.readUnsignedByte() / 200F;
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -f, 0.0F);
    }

    public void update(float f)
    {
        super.update(f);
        if(com.maddox.il2.fm.Pitot.Indicator((float)FM.Loc.z, FM.getSpeed()) > 70F && (double)FM.CT.getFlap() > 0.01D && FM.CT.FlapsControl != 0.0F)
        {
            FM.CT.FlapsControl = 0.0F;
            com.maddox.il2.ai.World.cur();
            if(FM.actor == com.maddox.il2.ai.World.getPlayerAircraft())
                com.maddox.il2.game.HUD.log("FlapsRaised");
        }
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        for(int i = 1; i < 3; i++)
            if(FM.getAltitude() < 3000F)
                hierMesh().chunkVisible("HMask" + i + "_D0", false);
            else
                hierMesh().chunkVisible("HMask" + i + "_D0", hierMesh().isChunkVisible("Pilot" + i + "_D0"));

    }

    public boolean isSalvo()
    {
        return thisWeaponsName.indexOf("salvo") != -1;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public static boolean bChangedPit = false;
    public int diveMechStage;
    public boolean bNDives;
    private boolean bSightAutomation;
    private boolean bSightBombDump;
    private float fSightCurDistance;
    public float fSightCurForwardAngle;
    public float fSightCurSideslip;
    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurReadyness;
    public float fDiveRecoveryAlt;
    public float fDiveVelocity;
    public float fDiveAngle;
    protected float fAOB;
    protected float fShipSpeed;
    protected int spreadAngle;

    static 
    {
        java.lang.Class class1 = com.maddox.rts.CLASS.THIS();
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-88");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Ju-88A-17/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1942F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-88A-17.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitJU_88A17.class, com.maddox.il2.objects.air.CockpitJU_88A17_Bombardier.class, com.maddox.il2.objects.air.CockpitJU_88A17_NGunner.class, com.maddox.il2.objects.air.CockpitJU_88A17_RGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 1.0976F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 3, 3, 3, 3, 1
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_CANNON01"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", null, null, null, null, "MGunMGFFt 180"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "1xLTW_Torp", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", null, "BombGunTorpFiume 1", null, null, "MGunMGFFt 180"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "2xLTW_Torp_salvo", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "BombGunTorpFiumeL 1", "BombGunTorpFiumeR 1", null, null, "MGunMGFFt 180"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "2xLTW_Torp_spread", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "BombGunTorpFiumeL 1", "BombGunNull 1", "BombGunNull 1", "BombGunTorpFiumeR 1", "MGunMGFFt 180"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "1xLTF5Bh_Torp", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", null, "BombGunTorpF5Bheavy 1", null, null, "MGunMGFFt 180"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "2xLTF5Bh_Torp_salvo", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "BombGunTorpF5BheavyL 1", "BombGunTorpF5BheavyR 1", null, null, "MGunMGFFt 180"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "2xLTF5Bh_Torp_spread", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "BombGunTorpF5BheavyL 1", "BombGunNull 1", "BombGunNull 1", "BombGunTorpF5BheavyR 1", "MGunMGFFt 180"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "2xPractice_Torp_spread", new java.lang.String[] {
            "MGunMG81t 375", "MGunMG81t 1200", "MGunMG81t 1200", "BombGunTorpLTF5PracticeL 1", "BombGunNull 1", "BombGunNull 1", "BombGunTorpLTF5PracticeR 1", "MGunMGFFt 180"
        });
        com.maddox.il2.objects.air.JU_88A17.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null
        });
    }
}
