package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.*;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.objects.air:
//            ME_262, PaintSchemeBMPar03, TypeStormovik, NetAircraft, 
//            Aircraft

public class ME_262A extends ME_262
    implements com.maddox.il2.objects.air.TypeStormovik, com.maddox.il2.objects.air.TypeBNZFighter
{

    public ME_262A()
    {
        cockpitDoor_ = 0.0F;
        fMaxKMHSpeedForOpenCanopy = 200F;
        bHasBlister = true;
    }

    
    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(getBulletEmitterByHookName("_ExternalBomb01") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        	{
        		if(getBulletEmitterByHookName("_ExternalDev03") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        			{
        				hierMesh().chunkVisible("Pylon_D0", false);
        			}
        	}
        if(getBulletEmitterByHookName("_CANNON07") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        	{
        		hierMesh().chunkVisible("MK103", false);	
        	}
        else
        	{
        		hierMesh().chunkVisible("MK103", true);	
        	}
        if(getBulletEmitterByHookName("_CANNON09") instanceof com.maddox.il2.objects.weapons.GunEmpty)
        	{
    			hierMesh().chunkVisible("MK108", false);	
        	}
        else
    		{
        		hierMesh().chunkVisible("MK108", true);	
    		}
        super.onAircraftLoaded();
        if(FM.isPlayers())
        {
           //   FM.CT.bHasCockpitDoorControl = true;
           //   FM.CT.dvCockpitDoor = 1.0F;       
        }
    }
        
    public void update(float f)
    {        
        if((double)FM.CT.getCockpitDoor() > 0.20000000000000001D && bHasBlister && FM.getSpeedKMH() > fMaxKMHSpeedForOpenCanopy && hierMesh().chunkFindCheck("Blister1_D0") != -1)
        {
            try
            {
                if(this == World.getPlayerAircraft())
                    ((CockpitME_262)Main3D.cur3D().cockpitCur).removeCanopy();
            }
            catch(Exception exception) { }
            hierMesh().hideSubTrees("Blister1_D0");
            Wreckage wreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
            wreckage.collide(true);
            Vector3d vector3d = new Vector3d();
            vector3d.set(FM.Vwld);
            wreckage.setSpeed(vector3d);
            bHasBlister = false;
            FM.CT.bHasCockpitDoorControl = false;
            FM.setGCenter(-0.50F);
        }
    }
    
    
    public void moveCockpitDoor(float f)
    {
        if(bHasBlister)
        {
            resetYPRmodifier();
            hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100F * f, 0.0F);
            if(com.maddox.il2.engine.Config.isUSE_RENDER())
            {
                if(com.maddox.il2.game.Main3D.cur3D().cockpits != null && com.maddox.il2.game.Main3D.cur3D().cockpits[0] != null)
                    com.maddox.il2.game.Main3D.cur3D().cockpits[0].onDoorMoved(f);
                setDoorSnd(f);
            }
        }
    }
    

        public float cockpitDoor_;
        private float fMaxKMHSpeedForOpenCanopy;
        public boolean bHasBlister;
        
    static 
    {
        Class class1 = com.maddox.il2.objects.air.ME_262A.class;
        new NetAircraft.SPAWN(class1);
        Property.set(class1, "iconFar_shortClassName", "Me 262");
        Property.set(class1, "meshName", "3DO/Plane/Me-262A-1a/hier_262A.him");
        Property.set(class1, "PaintScheme", new PaintSchemeFMPar05());
        Property.set(class1, "yearService", 1944.1F);
        Property.set(class1, "yearExpired", 1945.5F);
        Property.set(class1, "FlightModel", "FlightModels/Me-262A-1a.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
                com.maddox.il2.objects.air.CockpitME_262.class
            });
        Property.set(class1, "LOSElevation", 0.74615F);
        Aircraft.weaponTriggersRegister(class1, new int[] {
                0, 0, 1, 1, 3, 3, 2, 2, 9, 9, 
                9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 
                2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 
                2, 2, 2, 2, 2, 2, 0, 0, 1, 1, 
                1, 1
            });
            Aircraft.weaponHooksRegister(class1, new String[] {
                "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalRock25", "_ExternalRock26", "_ExternalDev03", "_ExternalDev04", 
                "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", 
                "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16", "_ExternalRock17", "_ExternalRock18", 
                "_ExternalRock19", "_ExternalRock20", "_ExternalRock21", "_ExternalRock22", "_ExternalRock23", "_ExternalRock24", "_CANNON05", "_CANNON06", "_CANNON07", "_CANNON08", 
                "_CANNON09", "_CANNON10"
            });
            try
            {
                ArrayList arraylist = new ArrayList();
                Property.set(class1, "weaponsList", arraylist);
                HashMapInt hashmapint = new HashMapInt();
                Property.set(class1, "weaponsMap", hashmapint);
                byte byte0 = 42;
                String s = "default";
                Aircraft._WeaponSlot a_lweaponslot[] = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                for(int i = 4; i < byte0; i++)
                    a_lweaponslot[i] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "2xSC250";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
                a_lweaponslot[5] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
                for(int j = 6; j < byte0; j++)
                    a_lweaponslot[j] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "2xSC500";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
                a_lweaponslot[5] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
                for(int k = 6; k < byte0; k++)
                    a_lweaponslot[k] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "2xWfrGr21";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[6] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);
                a_lweaponslot[7] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);
                a_lweaponslot[8] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21_262", 1);
                a_lweaponslot[9] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21_262", 1);
                for(int l = 10; l < byte0; l++)
                    a_lweaponslot[l] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "24r4m";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                for(int i1 = 4; i1 < 9; i1++)
                    a_lweaponslot[i1] = null;

                a_lweaponslot[10] = new Aircraft._WeaponSlot(9, "PylonMe262_R4M_Left", 1);
                a_lweaponslot[11] = new Aircraft._WeaponSlot(9, "PylonMe262_R4M_Right", 1);
                a_lweaponslot[12] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[13] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[20] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[21] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[22] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[23] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[24] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[25] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[26] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[27] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[28] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[29] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[30] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[31] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[32] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[33] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[34] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[35] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                for(int i1 = 36; i1 < byte0; i1++)
                    a_lweaponslot[i1] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "2xSC250_2xMK108";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = null;
                a_lweaponslot[3] = null;
                a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
                a_lweaponslot[5] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1);
                for(int j1 = 6; j1 < byte0; j1++)
                    a_lweaponslot[j1] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "2xSC500_2xMK108";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = null;
                a_lweaponslot[3] = null;
                a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
                a_lweaponslot[5] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1);
                for(int k1 = 6; k1 < byte0; k1++)
                    a_lweaponslot[k1] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "2xWfrGr21_2xMK108";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = null;
                a_lweaponslot[3] = null;
                a_lweaponslot[6] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);
                a_lweaponslot[7] = new Aircraft._WeaponSlot(2, "RocketGunWfrGr21", 1);
                a_lweaponslot[8] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21_262", 1);
                a_lweaponslot[9] = new Aircraft._WeaponSlot(9, "PylonRO_WfrGr21_262", 1);
                for(int l1 = 10; l1 < byte0; l1++)
                    a_lweaponslot[l1] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "24r4m_2xMK108";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                for(int i2 = 2; i2 < 9; i2++)
                    a_lweaponslot[i2] = null;

                a_lweaponslot[10] = new Aircraft._WeaponSlot(9, "PylonMe262_R4M_Left", 1);
                a_lweaponslot[11] = new Aircraft._WeaponSlot(9, "PylonMe262_R4M_Right", 1);
                a_lweaponslot[12] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[13] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[14] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[15] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[16] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[17] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[18] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[19] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[20] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[21] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[22] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[23] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[24] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[25] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[26] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[27] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[28] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[29] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[30] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[31] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[32] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[33] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[34] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                a_lweaponslot[35] = new Aircraft._WeaponSlot(2, "RocketGunR4M", 1);
                for(int i2 = 36; i2 < byte0; i2++)
                    a_lweaponslot[i2] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "U1";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(9, "PylonMG15120Internal", 1);
                for(int j2 = 2; j2 < 35; j2++)
                    a_lweaponslot[j2] = null;

                a_lweaponslot[36] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 146);
                a_lweaponslot[37] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 146);
                a_lweaponslot[38] = new Aircraft._WeaponSlot(1, "MGunMK103k", 72);
                a_lweaponslot[39] = new Aircraft._WeaponSlot(1, "MGunMK103k", 72);
                a_lweaponslot[40] = new Aircraft._WeaponSlot(1, "MGunMK108k", 66);
                a_lweaponslot[41] = new Aircraft._WeaponSlot(1, "MGunMK108k", 66);
                for(int j2 = 42; j2 < byte0; j2++)
                    a_lweaponslot[j2] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "U5";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunMK108k", 100);
                a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMK108k", 80);
                for(int k2 = 4; k2 < 39; k2++)
                    a_lweaponslot[k2] = null;

                a_lweaponslot[40] = new Aircraft._WeaponSlot(1, "MGunMK108k", 66);
                a_lweaponslot[41] = new Aircraft._WeaponSlot(1, "MGunMK108k", 66);
                for(int k2 = 42; k2 < byte0; k2++)
                    a_lweaponslot[k2] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
                s = "none";
                a_lweaponslot = new Aircraft._WeaponSlot[byte0];
                for(int l2 = 0; l2 < byte0; l2++)
                    a_lweaponslot[l2] = null;

                arraylist.add(s);
                hashmapint.put(Finger.Int(s), a_lweaponslot);
            }
            catch(Exception exception) { }
        }
    }