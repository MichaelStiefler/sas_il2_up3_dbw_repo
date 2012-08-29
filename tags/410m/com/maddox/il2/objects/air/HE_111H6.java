// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HE_111H6.java

package com.maddox.il2.objects.air;

import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.weapons.ToKGUtils;
import com.maddox.il2.objects.weapons.Torpedo;
import com.maddox.rts.Property;

// Referenced classes of package com.maddox.il2.objects.air:
//            HE_111, PaintSchemeBMPar02, TypeHasToKG, NetAircraft

public class HE_111H6 extends com.maddox.il2.objects.air.HE_111
    implements com.maddox.il2.objects.air.TypeHasToKG
{

    public HE_111H6()
    {
        hasToKG = false;
        spreadAngle = 0;
    }

    public void update(float f)
    {
        if(FM.turret[5].tMode == 2)
            FM.turret[5].tMode = 4;
        super.update(f);
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        java.lang.Object aobj[] = pos.getBaseAttached();
        if(aobj != null)
        {
            for(int i = 0; i < aobj.length; i++)
                if(aobj[i] instanceof com.maddox.il2.objects.weapons.Torpedo)
                {
                    hasToKG = true;
                    return;
                }

        }
    }

    public void typeBomberAdjSideslipPlus()
    {
        if(hasToKG)
        {
            fAOB++;
            if(fAOB > 180F)
                fAOB = 180F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGAOB", new java.lang.Object[] {
                new Integer((int)fAOB)
            });
            com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(FM, fAOB, fShipSpeed);
        } else
        {
            super.typeBomberAdjSideslipPlus();
        }
    }

    public void typeBomberAdjSideslipMinus()
    {
        if(hasToKG)
        {
            fAOB--;
            if(fAOB < -180F)
                fAOB = -180F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGAOB", new java.lang.Object[] {
                new Integer((int)fAOB)
            });
            com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(FM, fAOB, fShipSpeed);
        } else
        {
            super.typeBomberAdjSideslipMinus();
        }
    }

    public void typeBomberAdjSpeedPlus()
    {
        if(hasToKG)
        {
            fShipSpeed++;
            if(fShipSpeed > 35F)
                fShipSpeed = 35F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGSpeed", new java.lang.Object[] {
                new Integer((int)fShipSpeed)
            });
            com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(FM, fAOB, fShipSpeed);
        } else
        {
            super.typeBomberAdjSpeedPlus();
        }
    }

    public void typeBomberAdjSpeedMinus()
    {
        if(hasToKG)
        {
            fShipSpeed--;
            if(fShipSpeed < 0.0F)
                fShipSpeed = 0.0F;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGSpeed", new java.lang.Object[] {
                new Integer((int)fShipSpeed)
            });
            com.maddox.il2.objects.weapons.ToKGUtils.setTorpedoGyroAngle(FM, fAOB, fShipSpeed);
        } else
        {
            super.typeBomberAdjSpeedMinus();
        }
    }

    public void typeBomberAdjAltitudeReset()
    {
        if(!hasToKG)
            super.typeBomberAdjAltitudeReset();
    }

    public void typeBomberAdjAltitudePlus()
    {
        if(!hasToKG)
            super.typeBomberAdjAltitudePlus();
    }

    public void typeBomberAdjAltitudeMinus()
    {
        if(!hasToKG)
            super.typeBomberAdjAltitudeMinus();
    }

    public void typeBomberAdjSpeedReset()
    {
        if(!hasToKG)
            super.typeBomberAdjSpeedReset();
    }

    public void typeBomberAdjDistanceReset()
    {
        if(!hasToKG)
            super.typeBomberAdjDistanceReset();
    }

    public void typeBomberAdjDistancePlus()
    {
        if(!hasToKG)
        {
            super.typeBomberAdjDistancePlus();
        } else
        {
            spreadAngle++;
            if(spreadAngle > 30)
                spreadAngle = 30;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGSpread", new java.lang.Object[] {
                new Integer(spreadAngle)
            });
            FM.AS.setSpreadAngle(spreadAngle);
        }
    }

    public void typeBomberAdjDistanceMinus()
    {
        if(!hasToKG)
        {
            super.typeBomberAdjDistanceMinus();
        } else
        {
            spreadAngle--;
            if(spreadAngle < 0)
                spreadAngle = 0;
            com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "TOKGSpread", new java.lang.Object[] {
                new Integer(spreadAngle)
            });
            FM.AS.setSpreadAngle(spreadAngle);
        }
    }

    public void typeBomberAdjSideslipReset()
    {
        if(!hasToKG)
            super.typeBomberAdjSideslipReset();
    }

    public boolean isSalvo()
    {
        return thisWeaponsName.indexOf("spread") == -1;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    protected float fAOB;
    protected float fShipSpeed;
    public boolean hasToKG;
    protected int spreadAngle;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.HE_111H6.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "He-111");
        com.maddox.rts.Property.set(class1, "meshName", "3do/plane/He-111H-6/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "yearService", 1941F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1948F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/He-111H-6.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitHE_111H6.class, com.maddox.il2.objects.air.CockpitHE_111H6_Bombardier.class, com.maddox.il2.objects.air.CockpitHE_111H6_NGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_TGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_BGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_LGunner.class, com.maddox.il2.objects.air.CockpitHE_111H2_RGunner.class
        });
        com.maddox.il2.objects.air.HE_111H6.weaponTriggersRegister(class1, new int[] {
            10, 11, 12, 13, 14, 15, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.HE_111H6.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04"
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", null, null, null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "4xSD250", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSD500", "BombGunSD500", null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "4xSC500", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC500", "BombGunSC500", null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "4xAB500", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunAB500", "BombGunAB500", null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "1SC1000", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC1000", null, null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "2SC1000", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC1000", "BombGunSC1000", null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "2PC1600", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunPC1600", null, null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "1SC1800", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC1800", null, null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "2SC2000", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunSC2000", null, null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "2xTorp", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpF5BheavyL", "BombGunTorpF5BheavyR", null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "2xTorp_spread", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpF5BheavyL", "BombGunNull", "BombGunNull", "BombGunTorpF5BheavyR"
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "2xTorp_LTW", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpFiumeL", "BombGunTorpFiumeR", null, null
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "2xTorp_LTW_spread", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpFiumeL", "BombGunNull", "BombGunNull", "BombGunTorpFiumeR"
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "2xTorp_Practice_spread", new java.lang.String[] {
            "MGunMGFFt 250", "MGunMG15t 1000", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 750", "BombGunTorpLTF5PracticeL", "BombGunNull", "BombGunNull", "BombGunTorpLTF5PracticeR"
        });
        com.maddox.il2.objects.air.HE_111H6.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null
        });
    }
}
