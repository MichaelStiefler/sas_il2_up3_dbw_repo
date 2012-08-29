// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   JU_87D8.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.GunEmpty;
import com.maddox.il2.objects.weapons.PylonWB151;
import com.maddox.il2.objects.weapons.PylonWB20;
import com.maddox.il2.objects.weapons.PylonWB81A;
import com.maddox.il2.objects.weapons.PylonWB81B;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            JU_87D5, PaintSchemeBMPar02, TypeStormovik, NetAircraft, 
//            Aircraft

public class JU_87D8 extends com.maddox.il2.objects.air.JU_87D5
    implements com.maddox.il2.objects.air.TypeStormovik
{

    public JU_87D8()
    {
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            int i = 0;
            do
            {
                if(i >= aobj.length)
                    break;
                if((aobj[i] instanceof com.maddox.il2.objects.weapons.PylonWB81B) || (aobj[i] instanceof com.maddox.il2.objects.weapons.PylonWB81A) || (aobj[i] instanceof com.maddox.il2.objects.weapons.PylonWB20) || (aobj[i] instanceof com.maddox.il2.objects.weapons.PylonWB151))
                {
                    FM.M.massEmpty += 190F;
                    FM.CT.bHasAirBrakeControl = false;
                    hierMesh().chunkVisible("Brake01_D0", false);
                    hierMesh().chunkVisible("Brake02_D0", false);
                    hierMesh().chunkVisible("WingLMid_D0", false);
                    hierMesh().chunkVisible("WingRMid_D0", false);
                    hierMesh().chunkVisible("WingLMid_D0_D8WOB", true);
                    hierMesh().chunkVisible("WingRMid_D0_D8WOB", true);
                    break;
                }
                i++;
            } while(true);
        }
    }

    protected void moveAirBrake(float f)
    {
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            int i = 0;
            do
            {
                if(i >= aobj.length)
                    break;
                if(!(aobj[i] instanceof com.maddox.il2.objects.weapons.PylonWB81B) && !(aobj[i] instanceof com.maddox.il2.objects.weapons.PylonWB81A) && !(aobj[i] instanceof com.maddox.il2.objects.weapons.PylonWB20) && !(aobj[i] instanceof com.maddox.il2.objects.weapons.PylonWB151))
                {
                    hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 80F * f, 0.0F);
                    hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 80F * f, 0.0F);
                    break;
                }
                i++;
            } while(true);
        }
    }

    public void typeDiveBomberAdjAltitudePlus()
    {
        if((getGunByHookName("_MGUN05") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_MGUN17") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_CANNON07") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            fDiveRecoveryAlt += 25F;
            if(fDiveRecoveryAlt > 6000F)
                fDiveRecoveryAlt = 6000F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
                new Integer((int)fDiveRecoveryAlt)
            });
        }
    }

    public void typeDiveBomberAdjAltitudeMinus()
    {
        if((getGunByHookName("_MGUN05") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_MGUN17") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_CANNON07") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            fDiveRecoveryAlt -= 25F;
            if(fDiveRecoveryAlt < 0.0F)
                fDiveRecoveryAlt = 0.0F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
                new Integer((int)fDiveRecoveryAlt)
            });
        }
    }

    public void typeDiveBomberAdjVelocityPlus()
    {
        if((getGunByHookName("_MGUN05") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_MGUN17") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_CANNON07") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            fDiveVelocity += 10F;
            if(fDiveVelocity > 700F)
                fDiveVelocity = 700F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
                new Integer((int)fDiveVelocity)
            });
        }
    }

    public void typeDiveBomberAdjVelocityMinus()
    {
        if((getGunByHookName("_MGUN05") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_MGUN17") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_CANNON03") instanceof com.maddox.il2.objects.weapons.GunEmpty) && (getGunByHookName("_CANNON07") instanceof com.maddox.il2.objects.weapons.GunEmpty))
        {
            fDiveVelocity -= 10F;
            if(fDiveVelocity < 150F)
                fDiveVelocity = 150F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
                new Integer((int)fDiveVelocity)
            });
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        try
        {
            return java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
    }

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.JU_87D8.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Ju-87D-8.fmd");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/Ju-87D-5/hier_D8.him");
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Ju-87");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitJU_87D5.class, com.maddox.il2.objects.air.CockpitJU_87D3_Gunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8499F);
        com.maddox.rts.Property.set(class1, "yearService", 1943F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 10, 10, 3, 3, 3, 3, 3, 3, 
            3, 3, 3, 3, 3, 3, 3, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
            1, 1, 1, 1, 1, 1, 1, 1, 1, 9, 
            9, 9, 9
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb01", "_ExternalBomb02", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_MGUN05", "_MGUN06", "_MGUN07", 
            "_MGUN08", "_MGUN09", "_MGUN10", "_MGUN11", "_MGUN12", "_MGUN13", "_MGUN14", "_MGUN15", "_MGUN16", "_CANNON03", 
            "_CANNON04", "_CANNON05", "_CANNON06", "_MGUN17", "_MGUN18", "_MGUN19", "_MGUN20", "_MGUN21", "_MGUN22", "_MGUN23", 
            "_MGUN24", "_MGUN25", "_MGUN26", "_MGUN27", "_MGUN28", "_CANNON07", "_CANNON08", "_CANNON09", "_CANNON10", "_ExternalDev01", 
            "_ExternalDev02", "_ExternalDev03", "_ExternalDev04"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xSC70", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC70 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunSC70 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunSC250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunAB250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC250_4xSC70", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC70 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunSC70 1", null, null, null, null, "BombGunSC250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSD500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunSD500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunAB500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunSC500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500_4xSC70", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunSC70 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunSC70 1", null, null, null, null, "BombGunSC500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xSC250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, "BombGunSC250 1", null, null, "BombGunSC250 1", "BombGunSC250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "3xAB250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, "BombGunAB250 1", null, null, "BombGunAB250 1", "BombGunAB250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500_2xSC250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, "BombGunSC250 1", null, null, "BombGunSC250 1", "BombGunSC500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB500_2xAB250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, "BombGunAB250 1", null, null, "BombGunAB250 1", "BombGunAB500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xAB1000", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunAB1000 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC1000", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunSC1000 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81B", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81B 1", "PylonWB81B 1", null, "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81B_1xAB250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81B 1", "PylonWB81B 1", "BombGunAB250 1", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81B_1xSC250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81B 1", "PylonWB81B 1", "BombGunSC250 1", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81B_1xAB500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81B 1", "PylonWB81B 1", "BombGunAB500 1", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81B_1xSC500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81B 1", "PylonWB81B 1", "BombGunSC500 1", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_20", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB20 1", "PylonWB20 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, "MGunMGFFk 90", 
            "MGunMGFFk 90", "MGunMGFFk 90", "MGunMGFFk 90", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_20_1xAB250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB20 1", "PylonWB20 1", "BombGunAB250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, "MGunMGFFk 90", 
            "MGunMGFFk 90", "MGunMGFFk 90", "MGunMGFFk 90", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_20_1xSC250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB20 1", "PylonWB20 1", "BombGunSC250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, "MGunMGFFk 90", 
            "MGunMGFFk 90", "MGunMGFFk 90", "MGunMGFFk 90", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_20_1xAB500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB20 1", "PylonWB20 1", "BombGunAB500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, "MGunMGFFk 90", 
            "MGunMGFFk 90", "MGunMGFFk 90", "MGunMGFFk 90", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_20_1xSC500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB20 1", "PylonWB20 1", "BombGunSC500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, "MGunMGFFk 90", 
            "MGunMGFFk 90", "MGunMGFFk 90", "MGunMGFFk 90", null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81A", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81A 1", "PylonWB81A 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81A_1xAB250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81A 1", "PylonWB81A 1", "BombGunAB250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81A_1xSC250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81A 1", "PylonWB81A 1", "BombGunSC250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81A_1xAB500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81A 1", "PylonWB81A 1", "BombGunAB500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_81A_1xSC500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB81A 1", "PylonWB81A 1", "BombGunSC500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", 
            "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", "MGunMG81k 250", null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_151", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB151 1", "PylonWB151 1", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_151_1xAB250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB151 1", "PylonWB151 1", "BombGunAB250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_151_1xSC250", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB151 1", "PylonWB151 1", "BombGunSC250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_151_1xAB500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB151 1", "PylonWB151 1", "BombGunAB500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "2xWB_151_1xSC500", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, "PylonWB151 1", "PylonWB151 1", "BombGunSC500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", "MGunMG15120kh 200", null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC250_2xTank", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunSC250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "FuelTankGun_Ju87 1", "FuelTankGun_Ju87 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC500_2xTank", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunSC500 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "FuelTankGun_Ju87 1", "FuelTankGun_Ju87 1"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xPC1600", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunPC1600 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xParaFlare", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunParaFlare 1", "BombGunNull 1", "BombGunNull 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunSC70 1", null, null, null, null, "BombGunSC250 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4xParaFlare", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", "BombGunParaFlare 1", "BombGunNull 1", "BombGunNull 1", "BombGunParaFlare 1", "BombGunParaFlare 1", "BombGunNull 1", 
            "BombGunNull 1", "BombGunParaFlare 1", null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1xSC1800", new java.lang.String[] {
            "MGunMG15120sn 250", "MGunMG15120sn 250", "MGunMG81t 1000", "MGunMG81t 1000", null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunSC1800 1", null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null
        });
    }
}
