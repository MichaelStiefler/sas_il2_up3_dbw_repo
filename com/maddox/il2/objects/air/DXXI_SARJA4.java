package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main;
import com.maddox.il2.net.NetFileServerSkin;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.HomePath;
import com.maddox.rts.Property;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;

public class DXXI_SARJA4 extends DXXI
  implements TypeScout
{
  private boolean skisLocked = false;
  private float skiAngleL = 0.0F;
  private float skiAngleR = 0.0F;
  private float spring = 0.15F;

  public void missionStarting()
  {
    super.missionStarting();
    customization();
  }

  private void customization()
  {
    int i = hierMesh().chunkFindCheck("cf_D0");
    int j = hierMesh().materialFindInChunk("Gloss1D0o", i);
    Mat localMat = hierMesh().material(j);
    String str1 = localMat.Name();
    if (str1.startsWith("PaintSchemes/Cache"))
    {
      try
      {
        str1 = str1.substring(19);
        str1 = str1.substring(0, str1.indexOf("/"));
        String str2 = Main.cur().netFileServerSkin.primaryPath();
        File localFile = new File(HomePath.toFileSystemName(str2 + "/DXXI_SARJA4/Customization.ini", 0));
        BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
        String str3 = null;
        int k = 0;
        int m = 0;
        while ((str3 = localBufferedReader.readLine()) != null)
        {
          if (str3.equals("[NoWheelSpats]"))
          {
            k = 1;
            m = 0;
          }
          else if (str3.equals("[NoWingSlots]"))
          {
            k = 0;
            m = 1;
          } else {
            if (!str3.equals(str1))
              continue;
            if ((k != 0) && (World.cur().camouflage == 1))
            {
              removeWheelSpats();
            }
            if (m == 0)
              continue;
            hierMesh().chunkVisible("SlotCoverLMid", true);
            hierMesh().chunkVisible("SlotCoverRMid", true);
            hierMesh().chunkVisible("SlotCoverLOut", true);
            hierMesh().chunkVisible("SlotCoverROut", true);
          }
        }

        localBufferedReader.close();
      }
      catch (Exception localException)
      {
        System.out.println(localException);
      }
    }
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.hasSelfSealingTank = true;
    this.canopyMaxAngle = 0.45F;

    if ((Config.isUSE_RENDER()) && (World.cur().camouflage == 1))
    {
      this.jdField_hasSkis_of_type_Boolean = true;
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

      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasBrakeControl = false;
    }
    else if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.01F) {
      removeWheelSpats();
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    if ((World.cur().camouflage == 1) && (World.Rnd().nextFloat() > 0.1F))
    {
      paramHierMesh.chunkVisible("GearL1_D0", false);
      paramHierMesh.chunkVisible("GearL22_D0", false);
      paramHierMesh.chunkVisible("GearR1_D0", false);
      paramHierMesh.chunkVisible("GearR22_D0", false);
      paramHierMesh.chunkVisible("GearC1_D0", false);
      paramHierMesh.chunkVisible("GearL31_D0", false);
      paramHierMesh.chunkVisible("GearL32_D0", false);
      paramHierMesh.chunkVisible("GearR31_D0", false);
      paramHierMesh.chunkVisible("GearR32_D0", false);

      paramHierMesh.chunkVisible("GearC11_D0", true);
      paramHierMesh.chunkVisible("GearL11_D0", true);
      paramHierMesh.chunkVisible("GearL21_D0", true);
      paramHierMesh.chunkVisible("GearR11_D0", true);
      paramHierMesh.chunkVisible("GearR21_D0", true);

      paramHierMesh.chunkSetAngles("GearL21_D0", 0.0F, 12.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR21_D0", 0.0F, 12.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearC11_D0", 0.0F, 12.0F, 0.0F);
    }
  }

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      super.moveFan(-paramFloat);
      float f1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getAileron();
      float f2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator();

      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9.0F * f1, Aircraft.cvt(f2, -1.0F, 1.0F, -8.0F, 9.5F));
      hierMesh().chunkSetAngles("pilotarm2_d0", Aircraft.cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, Aircraft.cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - Aircraft.cvt(f2, -1.0F, 1.0F, -37.0F, 35.0F));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, Aircraft.cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + Aircraft.cvt(f2, -1.0F, 0.0F, -61.0F, 0.0F) + Aircraft.cvt(f2, 0.0F, 1.0F, 0.0F, 43.0F));

      float f3 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getRadiator() * 30.0F;

      hierMesh().chunkSetAngles("cowlf1_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf2_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf3_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf4_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf5_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf6_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf7_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf8_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf9_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf10_d0", 0.0F, f3, 0.0F);
      hierMesh().chunkSetAngles("cowlf0_d0", 0.0F, f3, 0.0F);

      if (World.cur().camouflage == 1)
      {
        float f4 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 30.0F, 100.0F, 1.0F, 0.0F);
        float f5 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 0.0F, 30.0F, 0.0F, 0.5F);

        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] > 0.0F)
        {
          this.skiAngleL = (0.5F * this.skiAngleL + 0.5F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());

          if (this.skiAngleL > 20.0F)
          {
            this.skiAngleL -= this.spring;
          }

          hierMesh().chunkSetAngles("GearL21_D0", World.Rnd().nextFloat(-f5, f5), World.Rnd().nextFloat(-f5 * 2.0F, f5 * 2.0F) + this.skiAngleL, World.Rnd().nextFloat(f5, f5));

          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] == 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll() < 365.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll() > 355.0F))
          {
            this.skiAngleR = this.skiAngleL;
            hierMesh().chunkSetAngles("GearR21_D0", World.Rnd().nextFloat(-f5, f5), World.Rnd().nextFloat(-f5 * 2.0F, f5 * 2.0F) + this.skiAngleR, World.Rnd().nextFloat(f5, f5));
          }

        }
        else
        {
          if (this.skiAngleL > f4 * -10.0F + 0.01D)
          {
            this.skiAngleL -= this.spring;
          }
          else if (this.skiAngleL < f4 * -10.0F - 0.01D)
          {
            this.skiAngleL += this.spring;
          }

          hierMesh().chunkSetAngles("GearL21_D0", 0.0F, this.skiAngleL, 0.0F);
        }

        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] > 0.0F)
        {
          this.skiAngleR = (0.5F * this.skiAngleR + 0.5F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());

          if (this.skiAngleR > 20.0F)
          {
            this.skiAngleR -= this.spring;
          }

          hierMesh().chunkSetAngles("GearR21_D0", World.Rnd().nextFloat(-f5, f5), World.Rnd().nextFloat(-f5 * 2.0F, f5 * 2.0F) + this.skiAngleR, World.Rnd().nextFloat(f5, f5));
        }
        else
        {
          if (this.skiAngleR > f4 * -10.0F + 0.01D)
          {
            this.skiAngleR -= this.spring;
          }
          else if (this.skiAngleR < f4 * -10.0F - 0.01D)
          {
            this.skiAngleR += this.spring;
          }
          hierMesh().chunkSetAngles("GearR21_D0", 0.0F, this.skiAngleR, 0.0F);
        }

        hierMesh().chunkSetAngles("GearC11_D0", 0.0F, (this.skiAngleL + this.skiAngleR) / 2.0F, 0.0F);
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

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
      this.bChangedPit = true;
    Wreckage localWreckage;
    Vector3d localVector3d;
    if (World.cur().camouflage != 1)
    {
      if (hierMesh().isChunkVisible("GearR22_D2"))
      {
        if (!hierMesh().isChunkVisible("gearr31_d0"))
        {
          hierMesh().chunkVisible("gearr31_d0", true);
          hierMesh().chunkVisible("gearr32_d0", true);
          localWreckage = new Wreckage(this, hierMesh().chunkFind("GearR22_D1"));
          localWreckage.collide(true);
          localVector3d = new Vector3d();
          localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          localWreckage.setSpeed(localVector3d);
        }
      }
      if (hierMesh().isChunkVisible("GearL22_D2"))
      {
        if (!hierMesh().isChunkVisible("gearl31_d0"))
        {
          hierMesh().chunkVisible("gearl31_d0", true);
          hierMesh().chunkVisible("gearl32_d0", true);
          localWreckage = new Wreckage(this, hierMesh().chunkFind("GearL22_D1"));
          localWreckage.collide(true);
          localVector3d = new Vector3d();
          localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          localWreckage.setSpeed(localVector3d);
        }
      }
    }
    else
    {
      if ((hierMesh().isChunkVisible("GearR11_D1")) || (hierMesh().isChunkVisible("GearR21_D2")))
      {
        if (!hierMesh().isChunkVisible("gearr31_d0"))
        {
          hierMesh().chunkVisible("GearR11_D1", true);
          hierMesh().chunkVisible("GearR11_D0", false);
          hierMesh().chunkVisible("gearr31_d0", true);
          hierMesh().chunkVisible("gearr32_d0", true);
          localWreckage = new Wreckage(this, hierMesh().chunkFind("GearR11_D0"));
          localWreckage.collide(true);
          localVector3d = new Vector3d();
          localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          localWreckage.setSpeed(localVector3d);
        }
      }
      if ((hierMesh().isChunkVisible("GearL11_D1")) || (hierMesh().isChunkVisible("GearL21_D2")))
      {
        if (!hierMesh().isChunkVisible("gearl31_d0"))
        {
          hierMesh().chunkVisible("GearL11_D1", true);
          hierMesh().chunkVisible("GearL11_D0", false);
          hierMesh().chunkVisible("gearl31_d0", true);
          hierMesh().chunkVisible("gearl32_d0", true);
          localWreckage = new Wreckage(this, hierMesh().chunkFind("GearL11_D0"));
          localWreckage.collide(true);
          localVector3d = new Vector3d();
          localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
          localWreckage.setSpeed(localVector3d);
        }
      }
    }
  }

  public void sfxWheels() {
    if (!this.jdField_hasSkis_of_type_Boolean)
      super.sfxWheels();
  }

  static
  {
    Class localClass = DXXI_SARJA4.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "D.XXI");
    Property.set(localClass, "meshName_fi", "3DO/Plane/DXXI_SARJA4/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/DXXI_SARJA4/hierMulti.him");
    Property.set(localClass, "PaintScheme_fi", new PaintSchemeFMPar00DXXI());
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00DXXI());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.0F);
    Property.set(localClass, "FlightModel", "FlightModels/FokkerS4.fmd");

    Property.set(localClass, "LOSElevation", 0.8472F);
    Property.set(localClass, "originCountry", PaintScheme.countryFinland);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 300", "MGunBrowning303k 300", "MGunBrowning303k 300", "MGunBrowning303k 300" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}