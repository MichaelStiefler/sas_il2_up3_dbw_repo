// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   PE_8.java

package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

// Referenced classes of package com.maddox.il2.objects.air:
//            Scheme4, PaintSchemeBMPar02, TypeTransport, TypeBomber, 
//            Aircraft, NetAircraft, PaintScheme

public class PE_8 extends com.maddox.il2.objects.air.Scheme4
    implements com.maddox.il2.objects.air.TypeTransport, com.maddox.il2.objects.air.TypeBomber
{

    public PE_8()
    {
        fSightCurAltitude = 300F;
        fSightCurSpeed = 50F;
        fSightCurForwardAngle = 0.0F;
        fSightSetForwardAngle = 0.0F;
        fSightCurSideslip = 0.0F;
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        float f1 = java.lang.Math.max(-f * 800F, -50F);
        hiermesh.chunkSetAngles("GearL4_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR4_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearL5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearR5_D0", 0.0F, -f1, 0.0F);
        hiermesh.chunkSetAngles("GearC99_D0", 0.0F, 14F * f, 0.0F);
        hiermesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
        hiermesh.chunkSetAngles("GearL2_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR2_D0", 0.0F, 80F * f, 0.0F);
        hiermesh.chunkSetAngles("GearL3_D0", 0.0F, 55F * f, 0.0F);
        hiermesh.chunkSetAngles("GearR3_D0", 0.0F, 55F * f, 0.0F);
    }

    protected void moveGear(float f)
    {
        com.maddox.il2.objects.air.PE_8.moveGear(hierMesh(), f);
    }

    public void moveSteering(float f)
    {
        hierMesh().chunkSetAngles("GearC2_D0", -f, 0.0F, 0.0F);
    }

    protected boolean cutFM(int i, int j, com.maddox.il2.engine.Actor actor)
    {
        switch(i)
        {
        case 25: // '\031'
            FM.turret[0].bIsOperable = false;
            break;

        case 26: // '\032'
            FM.turret[1].bIsOperable = false;
            break;

        case 27: // '\033'
            FM.turret[2].bIsOperable = false;
            break;

        case 28: // '\034'
            FM.turret[3].bIsOperable = false;
            break;

        case 29: // '\035'
            FM.turret[4].bIsOperable = false;
            break;
        }
        return super.cutFM(i, j, actor);
    }

    protected void moveBayDoor(float f)
    {
        hierMesh().chunkSetAngles("Bay01_D0", 0.0F, 65F * f, 0.0F);
        hierMesh().chunkSetAngles("Bay02_D0", 0.0F, 65F * f, 0.0F);
    }

    public void msgShot(com.maddox.il2.ai.Shot shot)
    {
        setShot(shot);
        if(shot.chunkName.startsWith("Engine1") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 0, 1);
        if(shot.chunkName.startsWith("Engine2") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 1, 1);
        if(shot.chunkName.startsWith("Engine3") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 2, 1);
        if(shot.chunkName.startsWith("Engine4") && com.maddox.il2.ai.World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
            FM.AS.hitEngine(shot.initiator, 3, 1);
        if(shot.chunkName.startsWith("CF"))
        {
            if(com.maddox.il2.objects.air.Aircraft.Pd.x > 4.5500001907348633D && com.maddox.il2.objects.air.Aircraft.Pd.x < 7.1500000953674316D && com.maddox.il2.objects.air.Aircraft.Pd.z > 0.57999998331069946D)
            {
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.233F)
                    if(com.maddox.il2.objects.air.Aircraft.Pd.z > 1.2100000381469727D)
                    {
                        killPilot(shot.initiator, 0);
                        if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                            com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
                    } else
                    {
                        FM.AS.hitPilot(shot.initiator, 0, (int)(shot.power * 0.004F));
                    }
                if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.233F)
                    if(com.maddox.il2.objects.air.Aircraft.Pd.z > 1.2100000381469727D)
                    {
                        killPilot(shot.initiator, 1);
                        if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                            com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
                    } else
                    {
                        FM.AS.hitPilot(shot.initiator, 1, (int)(shot.power * 0.004F));
                    }
            }
            if(com.maddox.il2.objects.air.Aircraft.Pd.x > 9.5299997329711914D && com.maddox.il2.objects.air.Aircraft.Pd.z < 0.14000000059604645D && com.maddox.il2.objects.air.Aircraft.Pd.z > -0.62999999523162842D)
                FM.AS.hitPilot(shot.initiator, 2, (int)(shot.power * 0.002F));
            if(com.maddox.il2.objects.air.Aircraft.Pd.x > 2.4749999046325684D && com.maddox.il2.objects.air.Aircraft.Pd.x < 4.4899997711181641D && com.maddox.il2.objects.air.Aircraft.Pd.z > 0.61000001430511475D && (double)shot.power * java.lang.Math.sqrt(com.maddox.il2.objects.air.Aircraft.v1.y * com.maddox.il2.objects.air.Aircraft.v1.y + com.maddox.il2.objects.air.Aircraft.v1.z * com.maddox.il2.objects.air.Aircraft.v1.z) > 11900D && com.maddox.il2.ai.World.Rnd().nextFloat() < 0.45F)
            {
                for(int i = 0; i < 4; i++)
                    FM.AS.setEngineSpecificDamage(shot.initiator, i, 0);

            }
        }
        if(shot.chunkName.startsWith("Turret1"))
        {
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > 0.033449999988079071D)
            {
                killPilot(shot.initiator, 2);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            } else
            {
                FM.AS.hitPilot(shot.initiator, 2, (int)(shot.power * 0.004F));
            }
            shot.chunkName = "CF_D" + chunkDamageVisible("CF");
        }
        if(shot.chunkName.startsWith("Turret2"))
            if(com.maddox.il2.ai.World.Rnd().nextBoolean())
                FM.AS.hitPilot(shot.initiator, 4, (int)(shot.power * 0.004F));
            else
                FM.turret[1].bIsOperable = false;
        if(shot.chunkName.startsWith("Turret3"))
        {
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > 0.30445000529289246D)
            {
                killPilot(shot.initiator, 7);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            } else
            {
                FM.AS.hitPilot(shot.initiator, 7, (int)(shot.power * 0.002F));
            }
            shot.chunkName = "Tail1_D" + chunkDamageVisible("Tail1");
        }
        if(shot.chunkName.startsWith("Turret4"))
        {
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > -0.99540001153945923D)
            {
                killPilot(shot.initiator, 5);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            } else
            {
                FM.AS.hitPilot(shot.initiator, 5, (int)(shot.power * 0.002F));
            }
        } else
        if(shot.chunkName.startsWith("Turret5"))
        {
            if(com.maddox.il2.objects.air.Aircraft.Pd.z > -0.99540001153945923D)
            {
                killPilot(shot.initiator, 6);
                if(shot.initiator == com.maddox.il2.ai.World.getPlayerAircraft() && com.maddox.il2.ai.World.cur().isArcade())
                    com.maddox.il2.game.HUD.logCenter("H E A D S H O T");
            } else
            {
                FM.AS.hitPilot(shot.initiator, 6, (int)(shot.power * 0.002F));
            }
        } else
        {
            super.msgShot(shot);
        }
    }

    public void doKillPilot(int i)
    {
        switch(i)
        {
        case 2: // '\002'
            FM.turret[0].bIsOperable = false;
            break;

        case 4: // '\004'
            FM.turret[1].bIsOperable = false;
            break;

        case 5: // '\005'
            FM.turret[3].bIsOperable = false;
            break;

        case 6: // '\006'
            FM.turret[4].bIsOperable = false;
            break;

        case 7: // '\007'
            FM.turret[2].bIsOperable = false;
            break;
        }
    }

    public void doMurderPilot(int i)
    {
        switch(i)
        {
        case 0: // '\0'
            hierMesh().chunkVisible("Pilot1_D0", false);
            hierMesh().chunkVisible("HMask1_D0", false);
            hierMesh().chunkVisible("Pilot1_D1", true);
            hierMesh().chunkVisible("Head1_D0", false);
            break;

        case 1: // '\001'
            hierMesh().chunkVisible("Pilot2_D0", false);
            hierMesh().chunkVisible("HMask2_D0", false);
            hierMesh().chunkVisible("Pilot2_D1", true);
            hierMesh().chunkVisible("Head2_D0", false);
            break;

        case 2: // '\002'
            hierMesh().chunkVisible("Pilot3_D0", false);
            hierMesh().chunkVisible("HMask3_D0", false);
            hierMesh().chunkVisible("Pilot3_D1", true);
            hierMesh().chunkVisible("Head3_D0", false);
            break;

        case 3: // '\003'
            hierMesh().chunkVisible("Pilot4_D0", false);
            hierMesh().chunkVisible("HMask4_D0", false);
            hierMesh().chunkVisible("Pilot4_D1", true);
            hierMesh().chunkVisible("Head4_D0", false);
            break;

        case 5: // '\005'
            hierMesh().chunkVisible("Pilot6_D0", false);
            hierMesh().chunkVisible("HMask6_D0", false);
            hierMesh().chunkVisible("Pilot6_D1", true);
            hierMesh().chunkVisible("Head5_D0", false);
            break;

        case 6: // '\006'
            hierMesh().chunkVisible("Pilot7_D0", false);
            hierMesh().chunkVisible("HMask7_D0", false);
            hierMesh().chunkVisible("Pilot7_D1", true);
            hierMesh().chunkVisible("Head6_D0", false);
            break;

        case 7: // '\007'
            hierMesh().chunkVisible("Pilot8_D0", false);
            hierMesh().chunkVisible("HMask8_D0", false);
            hierMesh().chunkVisible("Pilot8_D1", true);
            hierMesh().chunkVisible("Head7_D0", false);
            break;
        }
    }

    public boolean turretAngles(int i, float af[])
    {
        boolean flag = super.turretAngles(i, af);
        float f = -af[0];
        float f1 = af[1];
        switch(i)
        {
        default:
            break;

        case 0: // '\0'
            if(f < -76F)
            {
                f = -76F;
                flag = false;
            }
            if(f > 76F)
            {
                f = 76F;
                flag = false;
            }
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
            break;

        case 1: // '\001'
            float f2 = java.lang.Math.abs(f);
            if(f1 > 50F)
            {
                f1 = 50F;
                flag = false;
            }
            if(f2 < 1.0F)
            {
                if(f1 < 17F)
                {
                    f1 = 17F;
                    flag = false;
                }
                break;
            }
            if(f2 < 4.5F)
            {
                if(f1 < 0.71429F - 0.71429F * f2)
                {
                    f1 = 0.71429F - 0.71429F * f2;
                    flag = false;
                }
                break;
            }
            if(f2 < 29.5F)
            {
                if(f1 < -2.5F)
                {
                    f1 = -2.5F;
                    flag = false;
                }
                break;
            }
            if(f2 < 46F)
            {
                if(f1 < 52.0303F - 1.84848F * f2)
                {
                    f1 = 52.0303F - 1.84848F * f2;
                    flag = false;
                }
                break;
            }
            if(f2 < 89F)
            {
                if(f1 < -70.73518F + 0.80232F * f2)
                {
                    f1 = -70.73518F + 0.80232F * f2;
                    flag = false;
                }
                break;
            }
            if(f2 < 147F)
            {
                if(f1 < 1.5F)
                {
                    f1 = 1.5F;
                    flag = false;
                }
                break;
            }
            if(f2 < 162F)
            {
                if(f1 < -292.5F + 2.0F * f2)
                {
                    f1 = -292.5F + 2.0F * f2;
                    flag = false;
                }
                break;
            }
            if(f1 < 31.5F)
            {
                f1 = 31.5F;
                flag = false;
            }
            break;

        case 2: // '\002'
            if(f < -87F)
            {
                f = -87F;
                flag = false;
            }
            if(f > 87F)
            {
                f = 87F;
                flag = false;
            }
            if(f1 < -78F)
            {
                f1 = -78F;
                flag = false;
            }
            if(f1 > 67F)
            {
                f1 = 67F;
                flag = false;
            }
            break;

        case 3: // '\003'
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 16F)
            {
                f1 = 16F;
                flag = false;
            }
            if(f < -60F)
            {
                f = -60F;
                flag = false;
                if(f1 > -11.5F)
                    f1 = -11.5F;
                break;
            }
            if(f < -13.5F)
            {
                if(f1 > 3.9836F + 0.25806F * f)
                {
                    f1 = 3.9836F + 0.25806F * f;
                    flag = false;
                }
                break;
            }
            if(f < -10.5F)
            {
                if(f1 > 16.25005F + 1.16667F * f)
                {
                    f1 = 16.25005F + 1.16667F * f;
                    flag = false;
                }
                break;
            }
            if(f < 14F)
            {
                if(f1 > 5F)
                    flag = false;
                break;
            }
            if(f < 80F)
            {
                if(f1 > 8F)
                    flag = false;
            } else
            {
                f = 80F;
                flag = false;
            }
            break;

        case 4: // '\004'
            f = -f;
            if(f1 < -45F)
            {
                f1 = -45F;
                flag = false;
            }
            if(f1 > 16F)
            {
                f1 = 16F;
                flag = false;
            }
            if(f < -60F)
            {
                f = -60F;
                flag = false;
                if(f1 > -11.5F)
                    f1 = -11.5F;
            } else
            if(f < -13.5F)
            {
                if(f1 > 3.9836F + 0.25806F * f)
                {
                    f1 = 3.9836F + 0.25806F * f;
                    flag = false;
                }
            } else
            if(f < -10.5F)
            {
                if(f1 > 16.25005F + 1.16667F * f)
                {
                    f1 = 16.25005F + 1.16667F * f;
                    flag = false;
                }
            } else
            if(f < 14F)
            {
                if(f1 > 5F)
                    flag = false;
            } else
            if(f < 80F)
            {
                if(f1 > 8F)
                    flag = false;
            } else
            {
                f = 80F;
                flag = false;
            }
            f = -f;
            break;
        }
        af[0] = -f;
        af[1] = f1;
        return flag;
    }

    public boolean typeBomberToggleAutomation()
    {
        return false;
    }

    public void typeBomberAdjDistanceReset()
    {
        fSightCurForwardAngle = 0.0F;
    }

    public void typeBomberAdjDistancePlus()
    {
        fSightCurForwardAngle += 0.2F;
        if(fSightCurForwardAngle > 75F)
            fSightCurForwardAngle = 75F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjDistanceMinus()
    {
        fSightCurForwardAngle -= 0.2F;
        if(fSightCurForwardAngle < -15F)
            fSightCurForwardAngle = -15F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new java.lang.Object[] {
            new Integer((int)(fSightCurForwardAngle * 1.0F))
        });
    }

    public void typeBomberAdjSideslipReset()
    {
        fSightCurSideslip = 0.0F;
    }

    public void typeBomberAdjSideslipPlus()
    {
        fSightCurSideslip++;
        if(fSightCurSideslip > 45F)
            fSightCurSideslip = 45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjSideslipMinus()
    {
        fSightCurSideslip--;
        if(fSightCurSideslip < -45F)
            fSightCurSideslip = -45F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new java.lang.Object[] {
            new Integer((int)(fSightCurSideslip * 1.0F))
        });
    }

    public void typeBomberAdjAltitudeReset()
    {
        fSightCurAltitude = 300F;
    }

    public void typeBomberAdjAltitudePlus()
    {
        fSightCurAltitude += 10F;
        if(fSightCurAltitude > 6000F)
            fSightCurAltitude = 6000F;
        com.maddox.il2.game.HUD.log(com.maddox.il2.game.AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new java.lang.Object[] {
            new Integer((int)fSightCurAltitude)
        });
    }

    public void typeBomberAdjAltitudeMinus()
    {
        fSightCurAltitude -= 10F;
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
        if(fSightCurSpeed > 650F)
            fSightCurSpeed = 650F;
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
        double d = ((double)fSightCurSpeed / 3.6000000000000001D) * java.lang.Math.sqrt((double)fSightCurAltitude * 0.20387359799999999D);
        d -= (double)(fSightCurAltitude * fSightCurAltitude) * 1.419E-005D;
        fSightSetForwardAngle = (float)java.lang.Math.toDegrees(java.lang.Math.atan(d / (double)fSightCurAltitude));
    }

    public void typeBomberReplicateToNet(com.maddox.rts.NetMsgGuaranted netmsgguaranted)
        throws java.io.IOException
    {
        netmsgguaranted.writeFloat(fSightCurAltitude);
        netmsgguaranted.writeFloat(fSightCurSpeed);
        netmsgguaranted.writeFloat(fSightCurForwardAngle);
        netmsgguaranted.writeFloat(fSightCurSideslip);
    }

    public void typeBomberReplicateFromNet(com.maddox.rts.NetMsgInput netmsginput)
        throws java.io.IOException
    {
        fSightCurAltitude = netmsginput.readFloat();
        fSightCurSpeed = netmsginput.readFloat();
        fSightCurForwardAngle = netmsginput.readFloat();
        fSightCurSideslip = netmsginput.readFloat();
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        java.lang.Class class1;
        try
        {
            class1 = java.lang.Class.forName(s);
        }
        catch(java.lang.ClassNotFoundException classnotfoundexception)
        {
            throw new NoClassDefFoundError(classnotfoundexception.getMessage());
        }
        return class1;
    }

    public float fSightCurAltitude;
    public float fSightCurSpeed;
    public float fSightCurForwardAngle;
    public float fSightSetForwardAngle;
    public float fSightCurSideslip;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.PE_8.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "Pe-8");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/Pe-8/hier.him");
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryRussia);
        com.maddox.rts.Property.set(class1, "yearService", 1940F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1945.5F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/Pe-8.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", new java.lang.Class[] {
            com.maddox.il2.objects.air.CockpitPE8.class, com.maddox.il2.objects.air.CockpitPE8_Bombardier.class, com.maddox.il2.objects.air.CockpitPE8_FGunner.class, com.maddox.il2.objects.air.CockpitPE8_TGunner.class, com.maddox.il2.objects.air.CockpitPE8_AGunner.class, com.maddox.il2.objects.air.CockpitPE8_RGunner.class, com.maddox.il2.objects.air.CockpitPE8_LGunner.class
        });
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.73425F);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            10, 10, 11, 12, 13, 14, 3, 3, 3, 3, 
            3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 
            3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 
            3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 
            3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 
            3, 9, 3, 3, 3, 3, 9, 3, 3, 3, 
            3, 9, 3, 3, 9, 3, 3, 9, 3, 3, 
            9, 3, 3, 9, 3, 3, 9, 3, 3
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn01", "_BombSpawn02", 
            "_BombSpawn03", "_ExternalDev01", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalDev02", "_ExternalBomb07", "_ExternalBomb08", "_ExternalBomb09", 
            "_ExternalBomb10", "_ExternalDev03", "_ExternalBomb11", "_ExternalBomb12", "_ExternalBomb13", "_ExternalBomb14", "_ExternalDev04", "_ExternalBomb15", "_ExternalBomb16", "_ExternalBomb17", 
            "_ExternalBomb18", "_ExternalDev05", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_ExternalDev06", "_BombSpawn08", "_BombSpawn09", "_BombSpawn10", 
            "_BombSpawn11", "_ExternalDev07", "_BombSpawn12", "_BombSpawn13", "_BombSpawn14", "_BombSpawn15", "_ExternalDev08", "_BombSpawn16", "_BombSpawn17", "_BombSpawn18", 
            "_BombSpawn19", "_ExternalDev09", "_BombSpawn20", "_BombSpawn21", "_BombSpawn22", "_BombSpawn23", "_ExternalDev10", "_BombSpawn24", "_BombSpawn25", "_BombSpawn26", 
            "_BombSpawn27", "_ExternalDev11", "_ExternalBomb19", "_ExternalBomb20", "_ExternalDev12", "_ExternalBomb21", "_ExternalBomb22", "_ExternalDev13", "_BombSpawn28", "_BombSpawn29", 
            "_ExternalDev14", "_BombSpawn30", "_BombSpawn31", "_ExternalDev15", "_BombSpawn32", "_BombSpawn33", "_ExternalDev16", "_BombSpawn34", "_BombSpawn35"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "40fab100", new java.lang.String[] {
            "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, null, null, 
            null, "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "PylonPE8_FAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", 
            "BombGunFAB100", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "12fab250", new java.lang.String[] {
            "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", 
            "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250", "PylonPE8_FAB250", "BombGunFAB250", "BombGunFAB250"
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "6fab500", new java.lang.String[] {
            "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, null, "BombGunFAB500", 
            "BombGunFAB500", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, "PylonPE8_FAB250", "BombGunFAB500", "BombGunFAB500", "PylonPE8_FAB250", "BombGunFAB500", "BombGunFAB500", null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "4fab1000", new java.lang.String[] {
            "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", "BombGunFAB1000", "BombGunFAB1000", null, "BombGunFAB1000", 
            "BombGunFAB1000", null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab2000", new java.lang.String[] {
            "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, "BombGunFAB2000", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab20002fab1000", new java.lang.String[] {
            "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", "BombGunFAB1000", "BombGunFAB1000", "BombGunFAB2000", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "1fab5000", new java.lang.String[] {
            "MGunShKASt 1500", "MGunShKASt 1500", "MGunShVAKt 750", "MGunShVAKt 750", "MGunUBt 1200", "MGunUBt 1200", null, null, "BombGunFAB5000", null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
        com.maddox.il2.objects.air.Aircraft.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null, null, 
            null, null, null, null, null, null, null, null, null
        });
    }
}
