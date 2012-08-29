// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 25/07/2011 12:50:20 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   PA48_Enforcer.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.*;
import com.maddox.il2.game.*;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.*;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            P_51, CockpitPA48_Enforcer, PaintSchemeFMPar05, PaintSchemeFMPar06, 
//            TypeFighterAceMaker, Aircraft, Cockpit, NetAircraft

public class PA48_Enforcer extends P_51
    implements TypeFighterAceMaker
{

    public PA48_Enforcer()
    {
        k14Mode = 0;
        k14WingspanType = 0;
        k14Distance = 200F;
        bHasBlister = true;
        fMaxKMHSpeedForOpenCanopy = 420F;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(FM.CT.Weapons[3] != null && FM.CT.Weapons[3][0] != null && FM.CT.Weapons[3][FM.CT.Weapons[3].length - 1].haveBullets() || FM.CT.Weapons[2] != null && FM.CT.Weapons[2][0] != null && FM.CT.Weapons[2][FM.CT.Weapons[2].length - 1].haveBullets())
        {
            hierMesh().chunkVisible("Pylon1_D0", true);
            hierMesh().chunkVisible("Pylon2_D0", true);
            hierMesh().chunkVisible("Pylon3_D0", true);
            hierMesh().chunkVisible("Pylon4_D0", true);
            hierMesh().chunkVisible("Pylon5_D0", true);
            hierMesh().chunkVisible("Pylon6_D0", true);
            hierMesh().chunkVisible("Pylon7_D0", true);
            hierMesh().chunkVisible("Pylon8_D0", true);
            hierMesh().chunkVisible("Pylon9_D0", true);
            hierMesh().chunkVisible("Pylon10_D0", true);
            hierMesh().chunkVisible("Pylon11_D0", true);
            hierMesh().chunkVisible("Pylon12_D0", true);
        } else
        {
            hierMesh().chunkVisible("Pylon1_D0", false);
            hierMesh().chunkVisible("Pylon2_D0", false);
            hierMesh().chunkVisible("Pylon3_D0", false);
            hierMesh().chunkVisible("Pylon4_D0", false);
            hierMesh().chunkVisible("Pylon5_D0", false);
            hierMesh().chunkVisible("Pylon6_D0", false);
            hierMesh().chunkVisible("Pylon7_D0", false);
            hierMesh().chunkVisible("Pylon8_D0", false);
            hierMesh().chunkVisible("Pylon9_D0", false);
            hierMesh().chunkVisible("Pylon10_D0", false);
            hierMesh().chunkVisible("Pylon11_D0", false);
            hierMesh().chunkVisible("Pylon12_D0", false);
        }
    }

    public void moveCockpitDoor(float f)
    {
        resetYPRmodifier();
        Aircraft.xyz[1] = Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 0.55F);
        hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
        float f1 = (float)Math.sin(Aircraft.cvt(f, 0.01F, 0.99F, 0.0F, 3.141593F));
        hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9F * f1);
        hierMesh().chunkSetAngles("Head1_D0", 12F * f1, 0.0F, 0.0F);
        if(Config.isUSE_RENDER())
        {
            if(Main3D.cur3D().cockpits != null && Main3D.cur3D().cockpits[0] != null)
                Main3D.cur3D().cockpits[0].onDoorMoved(f);
            setDoorSnd(f);
        }
    }

    public void moveArrestorHook(float f)
    {
        hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -73.5F * arrestor, 0.0F);
    }

    protected void moveWingFold(HierMesh hiermesh, float f)
    {
        hiermesh.chunkSetAngles("WingLOut_D0", 0.0F, 110F * f, 0.0F);
        hiermesh.chunkSetAngles("WingROut_D0", 0.0F, -110F * f, 0.0F);
    }

    public void moveWingFold(float f)
    {
        if(f < 0.001F)
        {
            setGunPodsOn(true);
            hideWingWeapons(false);
        } else
        {
            setGunPodsOn(false);
            FM.CT.WeaponControl[0] = false;
            hideWingWeapons(true);
        }
        moveWingFold(hierMesh(), f);
    }

    public void update(float f)
    {
        super.update(f);
        if((double)FM.CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == World.getPlayerAircraft())
                    ((CockpitPA48_Enforcer)Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(false);
            Vector3d vector3d = new Vector3d();
            vector3d.set(FM.Vwld);
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            FM.CT.bHasCockpitDoorControl = false;
            FM.setGCenter(-0.3F);
        }
        super.update(f);
        if(FM.CT.getArrestor() > 0.001F)
            if(FM.Gears.arrestorVAngle != 0.0F)
            {
                float f1 = Aircraft.cvt(FM.Gears.arrestorVAngle, -73.5F, 3.5F, 1.0F, 0.0F);
                arrestor = 0.8F * arrestor + 0.2F * f1;
                moveArrestorHook(arrestor);
            } else
            {
                float f2 = -1.224F * FM.Gears.arrestorVSink;
                if(f2 < 0.0F && FM.getSpeedKMH() > 60F && f2 > 0.2F)
                    f2 = 0.2F;
                if(f2 > 0.0F)
                    arrestor = 0.7F * arrestor + 0.3F * (arrestor + f2);
                else
                    arrestor = 0.3F * arrestor + 0.7F * (arrestor + f2);
                if(arrestor < 0.0F)
                    arrestor = 0.0F;
                else
                if(arrestor > FM.CT.getArrestor())
                    arrestor = FM.CT.getArrestor();
                moveArrestorHook(arrestor);
            }
    }

    public boolean typeFighterAceMakerToggleAutomation()
    {
        k14Mode++;
        if(k14Mode > 2)
            k14Mode = 0;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + k14Mode);
        return true;
    }

    public void typeFighterAceMakerAdjDistanceReset()
    {
    }

    public void typeFighterAceMakerAdjDistancePlus()
    {
        k14Distance += 10F;
        if(k14Distance > 800F)
            k14Distance = 800F;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
    }

    public void typeFighterAceMakerAdjDistanceMinus()
    {
        k14Distance -= 10F;
        if(k14Distance < 200F)
            k14Distance = 200F;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
    }

    public void typeFighterAceMakerAdjSideslipReset()
    {
    }

    public void typeFighterAceMakerAdjSideslipPlus()
    {
        k14WingspanType--;
        if(k14WingspanType < 0)
            k14WingspanType = 0;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerAdjSideslipMinus()
    {
        k14WingspanType++;
        if(k14WingspanType > 9)
            k14WingspanType = 9;
        HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + k14WingspanType);
    }

    public void typeFighterAceMakerReplicateToNet(NetMsgGuaranted netmsgguaranted)
        throws IOException
    {
        netmsgguaranted.writeByte(k14Mode);
        netmsgguaranted.writeByte(k14WingspanType);
        netmsgguaranted.writeFloat(k14Distance);
    }

    public void typeFighterAceMakerReplicateFromNet(NetMsgInput netmsginput)
        throws IOException
    {
        k14Mode = netmsginput.readByte();
        k14WingspanType = netmsginput.readByte();
        k14Distance = netmsginput.readFloat();
    }

    public int k14Mode;
    public int k14WingspanType;
    public float k14Distance;
    public boolean bHasBlister;
    private float fMaxKMHSpeedForOpenCanopy;
    private float arrestor;

    static 
    {
        Class class1 = com.maddox.il2.objects.air.PA48_Enforcer.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "PA48_Enforcer");
        Property.set(class1, "meshName", "3DO/Plane/PA48_Enforcer(Multi1)/hier.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        Property.set(class1, "meshName_us", "3DO/Plane/PA48_Enforcer(USA)/hier.him");
        Property.set(class1, "PaintScheme_us", new PaintSchemeFMPar06());
        Property.set(class1, "yearService", 1945F);
        Property.set(class1, "yearExpired", 2011.5F);
        Property.set(class1, "FlightModel", "FlightModels/Enforcer.fmd:Enforcer");
        Property.set(class1, "cockpitClass", new Class[] {
            com.maddox.il2.objects.air.CockpitPA48_Enforcer.class
        });
        Property.set(class1, "LOSElevation", 1.03F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 
            3, 3, 3, 3, 2, 2, 2, 2, 2, 2
        });
        Aircraft.weaponHooksRegister(class1, new String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDEV01", "_ExternalDEV02", "_ExternalDEV03", "_ExternalDEV04", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06"
        });
        try
        {
            ArrayList arraylist = new ArrayList();
            Property.set(class1, "weaponsList", arraylist);
            HashMapInt hashmapint = new HashMapInt();
            Property.set(class1, "weaponsMap", hashmapint);
            byte byte0 = 20;
            Aircraft._WeaponSlot a_lweaponslot[] = new Aircraft._WeaponSlot[byte0];
            String s = "default";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
            String s1 = "4x.50";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = null;
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s1);
            hashmapint.put(Finger.Int(s1), a_lweaponslot);
            String s2 = "droptanks";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s2);
            hashmapint.put(Finger.Int(s2), a_lweaponslot);
            String s3 = "droptanks+4x.50";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s3);
            hashmapint.put(Finger.Int(s3), a_lweaponslot);
            String s4 = "HVAR";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            arraylist.add(s4);
            hashmapint.put(Finger.Int(s4), a_lweaponslot);
            String s5 = "droptanks+HVAR";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2", 1);
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            arraylist.add(s5);
            hashmapint.put(Finger.Int(s5), a_lweaponslot);
            String s6 = "500lb bomb";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s6);
            hashmapint.put(Finger.Int(s6), a_lweaponslot);
            String s7 = "500lb bomb+4x.50";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = null;
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s7);
            hashmapint.put(Finger.Int(s7), a_lweaponslot);
            String s8 = "500lb bomb+HVAR";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            arraylist.add(s8);
            hashmapint.put(Finger.Int(s8), a_lweaponslot);
            String s9 = "500lb bomb+HVAR+4x.50";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = null;
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            arraylist.add(s9);
            hashmapint.put(Finger.Int(s9), a_lweaponslot);
            String s10 = "1000lb bomb";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s10);
            hashmapint.put(Finger.Int(s10), a_lweaponslot);
            String s11 = "1000lb bomb+4x.50";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = null;
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s11);
            hashmapint.put(Finger.Int(s11), a_lweaponslot);
            String s12 = "napalm";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun75Napalm", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun75Napalm", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s12);
            hashmapint.put(Finger.Int(s12), a_lweaponslot);
            String s13 = "napalm+HVAR";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 270);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun75Napalm", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun75Napalm", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            arraylist.add(s13);
            hashmapint.put(Finger.Int(s13), a_lweaponslot);
            String s14 = "napalm+HVAR+4x.50";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 400);
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = null;
            a_lweaponslot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50k", 500);
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = new Aircraft._WeaponSlot(3, "BombGun75Napalm", 1);
            a_lweaponslot[11] = new Aircraft._WeaponSlot(3, "BombGunNull", 1);
            a_lweaponslot[12] = new Aircraft._WeaponSlot(3, "BombGun75Napalm", 1);
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunHVAR5", 1);
            arraylist.add(s14);
            hashmapint.put(Finger.Int(s14), a_lweaponslot);
            s = "none";
            a_lweaponslot = new Aircraft._WeaponSlot[byte0];
            a_lweaponslot[0] = null;
            a_lweaponslot[1] = null;
            a_lweaponslot[2] = null;
            a_lweaponslot[3] = null;
            a_lweaponslot[4] = null;
            a_lweaponslot[5] = null;
            a_lweaponslot[6] = null;
            a_lweaponslot[7] = null;
            a_lweaponslot[8] = null;
            a_lweaponslot[9] = null;
            a_lweaponslot[10] = null;
            a_lweaponslot[11] = null;
            a_lweaponslot[12] = null;
            a_lweaponslot[13] = null;
            a_lweaponslot[14] = null;
            a_lweaponslot[15] = null;
            a_lweaponslot[16] = null;
            a_lweaponslot[17] = null;
            a_lweaponslot[18] = null;
            a_lweaponslot[19] = null;
            arraylist.add(s);
            hashmapint.put(Finger.Int(s), a_lweaponslot);
        }
        catch(Exception exception) { }
    }
}