// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TB_3_4M_17.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Explosion;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.EnginesInterface;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            TB_3, PaintSchemeBMPar00, NetAircraft

public class TB_3_4M_17 extends com.maddox.il2.objects.air.TB_3
{

    public TB_3_4M_17()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        float f2 = java.lang.Math.abs(f);
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f1 < -47F)
            {
                f1 = -47F;
                flag = false;
            }
            if(f1 > 47F)
            {
                f1 = 47F;
                flag = false;
            }
            if(f2 < 147F)
            {
                if(f1 < 0.5964912F * f2 - 117.6842F)
                {
                    f1 = 0.5964912F * f2 - 117.6842F;
                    flag = false;
                }
            } else
            if(f2 < 157F)
            {
                if(f1 < 0.3F * f2 - 74.1F)
                {
                    f1 = 0.3F * f2 - 74.1F;
                    flag = false;
                }
            } else
            if(f1 < 0.2173913F * f2 - 61.13044F)
            {
                f1 = 0.2173913F * f2 - 61.13044F;
                flag = false;
            }
            if(f2 >= 110F)
                if(f2 < 115F)
                {
                    if(f1 < -5F && f1 > -20F)
                        flag = false;
                } else
                if(f2 < 160F)
                {
                    if(f1 < -5F)
                        flag = false;
                } else
                if(f1 < 15F)
                    flag = false;
            break;

        case 1: // '\001'
            if(f1 < -47F)
            {
                f1 = -47F;
                flag = false;
            }
            if(f1 > 47F)
            {
                f1 = 47F;
                flag = false;
            }
            if(f < -38F)
            {
                if(f1 < -32F)
                {
                    f1 = -32F;
                    flag = false;
                }
            } else
            if(f < -16F)
            {
                if(f1 < 0.5909091F * f - 9.545455F)
                {
                    f1 = 0.5909091F * f - 9.545455F;
                    flag = false;
                }
            } else
            if(f < 35F)
            {
                if(f1 < -19F)
                {
                    f1 = -19F;
                    flag = false;
                }
            } else
            if(f < 44F)
            {
                if(f1 < -3.111111F * f + 89.88889F)
                {
                    f1 = -3.111111F * f + 89.88889F;
                    flag = false;
                }
            } else
            if(f < 139F)
            {
                if(f1 < -47F)
                {
                    f1 = -47F;
                    flag = false;
                }
            } else
            if(f < 150F)
            {
                if(f1 < 1.363636F * f - 236.5455F)
                {
                    f1 = 1.363636F * f - 236.5455F;
                    flag = false;
                }
            } else
            if(f1 < -32F)
            {
                f1 = -32F;
                flag = false;
            }
            if(f < -175.7F)
            {
                if(f1 < 80.8F)
                    flag = false;
                break;
            }
            if(f < -167F)
            {
                if(f1 < 0.0F)
                    flag = false;
                break;
            }
            if(f < -124.8F)
            {
                if(f1 < -22.8F)
                    flag = false;
                break;
            }
            if(f < -82F)
            {
                if(f1 < -16F)
                    flag = false;
                break;
            }
            if(f < 24F)
            {
                if(f1 < 0.0F)
                    flag = false;
                break;
            }
            if(f < 32F)
            {
                if(f1 < -8.3F)
                    flag = false;
                break;
            }
            if(f < 80F)
            {
                if(f1 < 0.0F)
                    flag = false;
                break;
            }
            if(f < 174F)
            {
                if(f1 < 0.5F * f - 87F)
                    flag = false;
                break;
            }
            if(f < 178.7F)
            {
                if(f1 < 0.0F)
                    flag = false;
                break;
            }
            if(f1 < 80.8F)
                flag = false;
            break;

        case 2: // '\002'
            if(f1 < -47F)
            {
                f1 = -47F;
                flag = false;
            }
            if(f1 > 47F)
            {
                f1 = 47F;
                flag = false;
            }
            if(f < -68F)
            {
                if(f1 < -32F)
                {
                    f1 = -32F;
                    flag = false;
                }
            } else
            if(f < -22F)
            {
                if(f1 < 0.5347826F * f + 4.365217F)
                {
                    f1 = 0.5347826F * f + 4.365217F;
                    flag = false;
                }
            } else
            if(f < 27F)
            {
                if(f1 < -0.3387755F * f - 14.85306F)
                {
                    f1 = -0.3387755F * f - 14.85306F;
                    flag = false;
                }
            } else
            if(f < 40F)
            {
                if(f1 < -1.769231F * f + 23.76923F)
                {
                    f1 = -1.769231F * f + 23.76923F;
                    flag = false;
                }
            } else
            if(f < 137F)
            {
                if(f1 < -47F)
                {
                    f1 = -47F;
                    flag = false;
                }
            } else
            if(f < 152F)
            {
                if(f1 < 1.0F * f - 184F)
                {
                    f1 = 1.0F * f - 184F;
                    flag = false;
                }
            } else
            if(f1 < -32F)
            {
                f1 = -32F;
                flag = false;
            }
            if(f < -172F)
            {
                if(f1 < 2.0F)
                    flag = false;
                break;
            }
            if(f < -123F)
            {
                if(f1 < 30F)
                    flag = false;
                break;
            }
            if(f < -102F)
            {
                if(f1 < 0.0F)
                    flag = false;
                break;
            }
            if(f < -36F)
            {
                if(f1 < -9F)
                    flag = false;
                break;
            }
            if(f < -5.1F)
            {
                if(f1 < 0.0F)
                    flag = false;
                break;
            }
            if(f < -1.2F)
            {
                if(f1 < 24.5F)
                    flag = false;
                break;
            }
            if(f < 62F)
            {
                if(f1 < -0.7436709F * f - 0.892496F)
                {
                    f1 = -0.7436709F * f - 0.892496F;
                    flag = false;
                }
                break;
            }
            if(f < 103F)
            {
                if(f1 < -47F)
                    flag = false;
                break;
            }
            if(f1 < 0.0F)
                flag = false;
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(flag)
        {
            for(int i = 0; i < 4; i++)
                if(FM.AS.astateEngineStates[i] > 3 && FM.EI.engines[i].getReadyness() < 0.1F)
                    FM.AS.repairEngine(i);

            for(int j = 0; j < 4; j++)
                if(FM.AS.astateTankStates[j] > 3 && (float)FM.AS.astatePilotStates[4] < 50F && (float)FM.AS.astatePilotStates[7] < 50F && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.1F)
                    FM.AS.repairTank(j);

        }
    }

    public void update(float f)
    {
        super.update(f);
        hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -FM.Gears.gWheelAngles[0], 0.0F);
        hierMesh().chunkSetAngles("GearR3_D0", 0.0F, -FM.Gears.gWheelAngles[1], 0.0F);
    }

    public void msgExplosion(com.maddox.il2.ai.Explosion explosion)
    {
        setExplosion(explosion);
        if(explosion.chunkName != null && (explosion.chunkName.startsWith("Wing") || explosion.chunkName.startsWith("Tail")) && explosion.chunkName.endsWith("D3") && explosion.power < 0.014F)
        {
            return;
        } else
        {
            super.msgExplosion(explosion);
            return;
        }
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
    }

    public void typeBomberAdjDistancePlus()
    {
    }

    public void typeBomberAdjDistanceMinus()
    {
    }

    public void typeBomberAdjSideslipReset()
    {
    }

    public void typeBomberAdjSideslipPlus()
    {
    }

    public void typeBomberAdjSideslipMinus()
    {
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 300F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 50F;
        if(fSightCurAltitude > 5000F)
            fSightCurAltitude = 5000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 50F;
        if(fSightCurAltitude < 300F)
            fSightCurAltitude = 300F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjSpeedReset()
    {
        fSightCurSpeed = 50F;
    }

    public void typeBomberAdjSpeedPlus()
    {
        fSightCurSpeed += 5F;
        if(fSightCurSpeed > 350F)
            fSightCurSpeed = 350F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberAdjSpeedMinus()
    {
        fSightCurSpeed -= 5F;
        if(fSightCurSpeed < 50F)
            fSightCurSpeed = 50F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new java.lang.Object[] {
            new Integer((int)fSightCurSpeed)
        });
    }

    public void typeBomberUpdate(float f)
    {
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    public float fSightCurAltitude;
    public float fSightCurSpeed;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.TB_3_4M_17.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "TB-3");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/TB-3-4M-17/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar00());
        com.maddox.rts.Property.set(class1, "yearService", 1932F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/TB-3-4M-17.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitTB_3.class, com.maddox.il2.objects.air.CockpitTB_3_Bombardier.class, com.maddox.il2.objects.air.CockpitTB_3_NGunner.class, com.maddox.il2.objects.air.CockpitTB_3_TGunner1.class, com.maddox.il2.objects.air.CockpitTB_3_TGunner2.class
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponTriggersRegister(class1, new int[] {
            10, 10, 11, 11, 12, 12, 9, 9, 9, 9, 
            3, 3, 3, 3, 3, 3, 3, 3, 9, 9, 
            9, 9, 3, 3, 3, 3, 3, 3
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", 
            "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07", "_ExternalBomb08", "_ExternalDev05", "_ExternalDev06", 
            "_ExternalDev07", "_ExternalDev08", "_ExternalBomb09", "_ExternalBomb10", "_ExternalBomb11", "_ExternalBomb12", "_BombSpawn01", "_BombSpawn02"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "14fab50", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunFAB50 7", "BombGunFAB50 7"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "28fab50", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunFAB50 14", "BombGunFAB50 14"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "14fab100", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunFAB100 7", "BombGunFAB100 7"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "28fab100", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, "BombGunFAB100 14", "BombGunFAB100 14"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "8fab250", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", 
            "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "4fab500", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "4fab50028fab100", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB100 14", "BombGunFAB100 14"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "4fab5004fab250", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", 
            "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "8fab500", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", "PylonPE8_FAB250", 
            "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", null, null, 
            null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "2fab10002fab500", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB1000", "BombGunFAB1000", null, null
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "2fab10002fab50028fab50", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB50 14", "BombGunFAB50 14"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "2fab10002fab50014fab100", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB100 7", "BombGunFAB100 7"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "2fab10002fab50020fab100", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB500", "BombGunFAB500", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB100 10", "BombGunFAB100 10"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "2fab10004fab50014fab50", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, "PylonPE8_FAB250", "PylonPE8_FAB250", 
            null, null, null, null, "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "BombGunFAB500", "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            null, null, "BombGunFAB1000", "BombGunFAB1000", null, null, "BombGunFAB50 7", "BombGunFAB50 7"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "2fab10004fab25020fab100", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, "PylonPE8_FAB250", "PylonPE8_FAB250", 
            null, null, null, null, "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "BombGunFAB250", "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            null, null, "BombGunFAB1000", "BombGunFAB1000", null, null, "BombGunFAB100 10", "BombGunFAB100 10"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "4fab1000", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", null, null
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "4fab100014fab50", new java.lang.String[] {
            "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", "MGunDA762t 1196", "MGunDA762t4d 1156", null, null, null, null, 
            null, null, null, null, null, null, null, null, "PylonDer16TB3Fake", "PylonDer16TB3Fake", 
            "PylonDer16TB3Fake", "PylonDer16TB3Fake", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB50 7", "BombGunFAB50 7"
        });
        com.maddox.il2.objects.air.TB_3_4M_17.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null
        });
    }
}
