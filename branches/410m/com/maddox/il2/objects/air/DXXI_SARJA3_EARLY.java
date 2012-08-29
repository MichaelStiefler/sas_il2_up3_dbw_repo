// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   DXXI_SARJA3_EARLY.java

package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.UserCfg;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.objects.air:
//            DXXI, PaintSchemeFMPar00DXXI, CockpitDXXI_SARJA3_EARLY, Aircraft, 
//            NetAircraft, PaintScheme

public class DXXI_SARJA3_EARLY extends com.maddox.il2.objects.air.DXXI
{

    public DXXI_SARJA3_EARLY()
    {
        hasRevi = false;
        pit = null;
        skiAngleL = 0.0F;
        skiAngleR = 0.0F;
        spring = 0.15F;
        gyroDelta = 0.0F;
    }

    public void missionStarting()
    {
        super.missionStarting();
        customization();
        if(FM.isStationedOnGround())
            gyroDelta += (float)java.lang.Math.random() * 360F;
    }

    public void registerPit(com.maddox.il2.objects.air.CockpitDXXI_SARJA3_EARLY cockpitdxxi_sarja3_early)
    {
        pit = cockpitdxxi_sarja3_early;
    }

    public boolean hasRevi()
    {
        return hasRevi;
    }

    public void onAircraftLoaded()
    {
        super.onAircraftLoaded();
        if(com.maddox.il2.engine.Config.isUSE_RENDER() && com.maddox.il2.ai.World.cur().camouflage == 1)
        {
            hasSkis = true;
            hierMesh().chunkVisible("GearL1_D0", false);
            hierMesh().chunkVisible("GearL22_D0", false);
            hierMesh().chunkVisible("GearR1_D0", false);
            hierMesh().chunkVisible("GearR22_D0", false);
            hierMesh().chunkVisible("GearC1_D0", false);
            hierMesh().chunkVisible("GearL31_D0", false);
            hierMesh().chunkVisible("GearL32_D0", false);
            hierMesh().chunkVisible("GearR31_D0", false);
            hierMesh().chunkVisible("GearR32_D0", false);
            hierMesh().chunkVisible("GearC11_D0", true);
            hierMesh().chunkVisible("GearL11_D0", true);
            hierMesh().chunkVisible("GearL21_D0", true);
            hierMesh().chunkVisible("GearR11_D0", true);
            hierMesh().chunkVisible("GearR21_D0", true);
            FM.CT.bHasBrakeControl = false;
            if(FM.isPlayers() && !isNetPlayer())
                areYouJormaSarvanto();
        } else
        if(com.maddox.il2.ai.World.Rnd().nextFloat() < 0.01F)
            removeWheelSpats();
    }

    public void rareAction(float f, boolean flag)
    {
        super.rareAction(f, flag);
        if(FM.Or.getKren() < -10F || FM.Or.getKren() > 10F)
            gyroDelta -= 0.01D;
    }

    private void areYouJormaSarvanto()
    {
        com.maddox.il2.ai.UserCfg usercfg = com.maddox.il2.ai.World.cur().userCfg;
        if(usercfg.callsign.trim().equals("Zamba") && usercfg.name.trim().equals("Jorma") && usercfg.surname.trim().equals("Sarvanto"))
        {
            java.lang.System.out.println("Herra luutnantti. Laitoin teille hieman v\344kev\344mp\344\344 kuulaa v\366ihin.");
            for(int i = 0; i < FM.CT.Weapons.length; i++)
            {
                com.maddox.il2.ai.BulletEmitter abulletemitter[] = FM.CT.Weapons[i];
                if(abulletemitter == null)
                    continue;
                for(int j = 0; j < abulletemitter.length; j++)
                {
                    com.maddox.il2.ai.BulletEmitter bulletemitter = abulletemitter[j];
                    if(!(bulletemitter instanceof com.maddox.il2.objects.weapons.Gun))
                        continue;
                    com.maddox.il2.engine.GunProperties gunproperties = ((com.maddox.il2.objects.weapons.Gun)bulletemitter).prop;
                    com.maddox.il2.engine.BulletProperties abulletproperties[] = gunproperties.bullet;
                    if(abulletproperties == null)
                        continue;
                    for(int k = 0; k < abulletproperties.length; k++)
                    {
                        abulletproperties[k].powerType = 3;
                        abulletproperties[k].massa = 0.02F;
                        abulletproperties[k].kalibr = 4.442131E-005F;
                        abulletproperties[k].speed = 835F;
                        if(abulletproperties[k].power != 0.0F)
                            abulletproperties[k].power = 0.002F;
                    }

                }

            }

        }
    }

    private void removeWheelSpats()
    {
        hierMesh().chunkVisible("GearR22_D0", false);
        hierMesh().chunkVisible("GearL22_D0", false);
        hierMesh().chunkVisible("GearR22_D2", true);
        hierMesh().chunkVisible("GearL22_D2", true);
        hierMesh().chunkVisible("gearl31_d0", true);
        hierMesh().chunkVisible("gearl32_d0", true);
        hierMesh().chunkVisible("gearr31_d0", true);
        hierMesh().chunkVisible("gearr32_d0", true);
    }

    private void customization()
    {
        int i = hierMesh().chunkFindCheck("cf_D0");
        int j = hierMesh().materialFindInChunk("Gloss1D0o", i);
        com.maddox.il2.engine.Mat mat = hierMesh().material(j);
        java.lang.String s = mat.Name();
        if(s.startsWith("PaintSchemes/Cache"))
            try
            {
                s = s.substring(19);
                s = s.substring(0, s.indexOf("/"));
                java.lang.String s1 = com.maddox.il2.game.Main.cur().netFileServerSkin.primaryPath();
                java.io.File file = new File(com.maddox.rts.HomePath.toFileSystemName(s1 + "/DXXI_SARJA3_EARLY/Customization.ini", 0));
                java.io.BufferedReader bufferedreader = new BufferedReader(new FileReader(file));
                Object obj = null;
                boolean flag = false;
                boolean flag1 = false;
                do
                {
                    java.lang.String s2;
                    if((s2 = bufferedreader.readLine()) == null)
                        break;
                    if(s2.equals("[ReflectorSight]"))
                    {
                        flag = true;
                        flag1 = false;
                    } else
                    if(s2.equals("[NoWheelSpats]"))
                    {
                        flag = false;
                        flag1 = true;
                    } else
                    if(s2.equals(s))
                    {
                        if(flag)
                        {
                            hierMesh().chunkVisible("Revi_D0", true);
                            hierMesh().chunkVisible("Goertz_D0", false);
                            hasRevi = true;
                        }
                        if(flag1 && com.maddox.il2.ai.World.cur().camouflage != 1)
                            removeWheelSpats();
                    }
                } while(true);
                bufferedreader.close();
            }
            catch(java.lang.Exception exception)
            {
                java.lang.System.out.println(exception);
            }
        else
        if(com.maddox.il2.ai.World.Rnd().nextFloat() > 0.6F)
        {
            hierMesh().chunkVisible("Revi_D0", true);
            hierMesh().chunkVisible("Goertz_D0", false);
            hasRevi = true;
        }
        if(hasRevi && pit != null)
            pit.setRevi();
    }

    public static void moveGear(com.maddox.il2.engine.HierMesh hiermesh, float f)
    {
        if(com.maddox.il2.ai.World.cur().camouflage == 1 && com.maddox.il2.ai.World.Rnd().nextFloat() > 0.1F)
        {
            hiermesh.chunkVisible("GearL1_D0", false);
            hiermesh.chunkVisible("GearL22_D0", false);
            hiermesh.chunkVisible("GearR1_D0", false);
            hiermesh.chunkVisible("GearR22_D0", false);
            hiermesh.chunkVisible("GearC1_D0", false);
            hiermesh.chunkVisible("GearL31_D0", false);
            hiermesh.chunkVisible("GearL32_D0", false);
            hiermesh.chunkVisible("GearR31_D0", false);
            hiermesh.chunkVisible("GearR32_D0", false);
            hiermesh.chunkVisible("GearC11_D0", true);
            hiermesh.chunkVisible("GearL11_D0", true);
            hiermesh.chunkVisible("GearL21_D0", true);
            hiermesh.chunkVisible("GearR11_D0", true);
            hiermesh.chunkVisible("GearR21_D0", true);
            hiermesh.chunkSetAngles("GearL21_D0", 0.0F, 12F, 0.0F);
            hiermesh.chunkSetAngles("GearR21_D0", 0.0F, 12F, 0.0F);
            hiermesh.chunkSetAngles("GearC11_D0", 0.0F, 12F, 0.0F);
        }
    }

    protected void moveFan(float f)
    {
        if(com.maddox.il2.engine.Config.isUSE_RENDER())
        {
            super.moveFan(f);
            float f1 = FM.CT.getAileron();
            float f2 = FM.CT.getElevator();
            hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9F * f1, com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.cvt(f2, -1F, 1.0F, -8F, 9.5F));
            hierMesh().chunkSetAngles("pilotarm2_d0", com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.cvt(f1, -1F, 1.0F, 14F, -16F), 0.0F, com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.cvt(f1, -1F, 1.0F, 6F, -8F) - com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.cvt(f2, -1F, 1.0F, -37F, 35F));
            hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.cvt(f1, -1F, 1.0F, -16F, 14F) + com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.cvt(f2, -1F, 0.0F, -61F, 0.0F) + com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.cvt(f2, 0.0F, 1.0F, 0.0F, 43F));
            if(com.maddox.il2.ai.World.cur().camouflage == 1)
            {
                float f3 = com.maddox.il2.objects.air.Aircraft.cvt(FM.getSpeed(), 30F, 100F, 1.0F, 0.0F);
                float f4 = com.maddox.il2.objects.air.Aircraft.cvt(FM.getSpeed(), 0.0F, 30F, 0.0F, 0.5F);
                if(FM.Gears.gWheelSinking[0] > 0.0F)
                {
                    skiAngleL = 0.5F * skiAngleL + 0.5F * FM.Or.getTangage();
                    if(skiAngleL > 20F)
                        skiAngleL = skiAngleL - spring;
                    hierMesh().chunkSetAngles("GearL21_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-f4, f4), com.maddox.il2.ai.World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) + skiAngleL, com.maddox.il2.ai.World.Rnd().nextFloat(f4, f4));
                    if(FM.Gears.gWheelSinking[1] == 0.0F && FM.Or.getRoll() < 365F && FM.Or.getRoll() > 355F)
                    {
                        skiAngleR = skiAngleL;
                        hierMesh().chunkSetAngles("GearR21_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-f4, f4), com.maddox.il2.ai.World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) + skiAngleR, com.maddox.il2.ai.World.Rnd().nextFloat(f4, f4));
                    }
                } else
                {
                    if((double)skiAngleL > (double)(f3 * -10F) + 0.01D)
                        skiAngleL = skiAngleL - spring;
                    else
                    if((double)skiAngleL < (double)(f3 * -10F) - 0.01D)
                        skiAngleL = skiAngleL + spring;
                    hierMesh().chunkSetAngles("GearL21_D0", 0.0F, skiAngleL, 0.0F);
                }
                if(FM.Gears.gWheelSinking[1] > 0.0F)
                {
                    skiAngleR = 0.5F * skiAngleR + 0.5F * FM.Or.getTangage();
                    if(skiAngleR > 20F)
                        skiAngleR = skiAngleR - spring;
                    hierMesh().chunkSetAngles("GearR21_D0", com.maddox.il2.ai.World.Rnd().nextFloat(-f4, f4), com.maddox.il2.ai.World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) + skiAngleR, com.maddox.il2.ai.World.Rnd().nextFloat(f4, f4));
                } else
                {
                    if((double)skiAngleR > (double)(f3 * -10F) + 0.01D)
                        skiAngleR = skiAngleR - spring;
                    else
                    if((double)skiAngleR < (double)(f3 * -10F) - 0.01D)
                        skiAngleR = skiAngleR + spring;
                    hierMesh().chunkSetAngles("GearR21_D0", 0.0F, skiAngleR, 0.0F);
                }
                hierMesh().chunkSetAngles("GearC11_D0", 0.0F, (skiAngleL + skiAngleR) / 2.0F, 0.0F);
            }
        }
    }

    public void sfxWheels()
    {
        if(!hasSkis)
            super.sfxWheels();
    }

    public void auxPlus(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            gyroDelta++;
            break;
        }
    }

    public void auxMinus(int i)
    {
        switch(i)
        {
        case 1: // '\001'
            gyroDelta--;
            break;
        }
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private boolean hasRevi;
    private com.maddox.il2.objects.air.CockpitDXXI_SARJA3_EARLY pit;
    private float skiAngleL;
    private float skiAngleR;
    private float spring;
    public float gyroDelta;

    static 
    {
        java.lang.Class class1 = com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.class;
        new NetAircraft.SPAWN(class1);
        com.maddox.rts.Property.set(class1, "iconFar_shortClassName", "D.XXI");
        com.maddox.rts.Property.set(class1, "meshName_fi", "3DO/Plane/DXXI_SARJA3_EARLY/hier.him");
        com.maddox.rts.Property.set(class1, "meshName", "3DO/Plane/DXXI_SARJA3_EARLY/hierMulti.him");
        com.maddox.rts.Property.set(class1, "PaintScheme_fi", new PaintSchemeFMPar00DXXI());
        com.maddox.rts.Property.set(class1, "PaintScheme", new PaintSchemeFMPar00DXXI());
        com.maddox.rts.Property.set(class1, "yearService", 1939F);
        com.maddox.rts.Property.set(class1, "yearExpired", 1940F);
        com.maddox.rts.Property.set(class1, "FlightModel", "FlightModels/FokkerS3Early.fmd");
        com.maddox.rts.Property.set(class1, "cockpitClass", com.maddox.il2.objects.air.CockpitDXXI_SARJA3_EARLY.class);
        com.maddox.rts.Property.set(class1, "LOSElevation", 0.8472F);
        com.maddox.rts.Property.set(class1, "originCountry", com.maddox.il2.objects.air.PaintScheme.countryFinland);
        com.maddox.il2.objects.air.Aircraft.weaponTriggersRegister(class1, new int[] {
            0, 0, 0, 0
        });
        com.maddox.il2.objects.air.Aircraft.weaponHooksRegister(class1, new java.lang.String[] {
            "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04"
        });
        com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.weaponsRegister(class1, "default", new java.lang.String[] {
            "MGunBrowning303sipzl 500", "MGunBrowning303sipzl 500", "MGunBrowning303k 500", "MGunBrowning303k 500"
        });
        com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.weaponsRegister(class1, "AlternativeTracers", new java.lang.String[] {
            "MGunBrowning303sipzl_fullTracers 500", "MGunBrowning303sipzl_NoTracers 500", "MGunBrowning303k_NoTracers 500", "MGunBrowning303k_NoTracers 500"
        });
        com.maddox.il2.objects.air.DXXI_SARJA3_EARLY.weaponsRegister(class1, "none", new java.lang.String[] {
            null, null, null, null
        });
    }
}
