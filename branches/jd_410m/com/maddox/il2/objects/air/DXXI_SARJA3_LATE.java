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

public class DXXI_SARJA3_LATE extends DXXI
  implements TypeScout
{
  private boolean hasRevi = false;
  private CockpitDXXI_SARJA3_LATE pit = null;
  private float skiAngleL = 0.0F;
  private float skiAngleR = 0.0F;
  private float spring = 0.15F;
  public float gyroDelta = 0.0F;

  public void missionStarting()
  {
    super.missionStarting();
    customization();
    if (this.FM.isStationedOnGround())
      this.gyroDelta += (float)Math.random() * 360.0F;
  }

  public void registerPit(CockpitDXXI_SARJA3_LATE paramCockpitDXXI_SARJA3_LATE)
  {
    this.pit = paramCockpitDXXI_SARJA3_LATE;
  }

  public boolean hasRevi()
  {
    return this.hasRevi;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.hasSelfSealingTank = true;
    if ((Config.isUSE_RENDER()) && (World.cur().camouflage == 1))
    {
      this.hasSkis = true;
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

      this.FM.CT.bHasBrakeControl = false;
    }
    else if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.01F) {
      removeWheelSpats();
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((this.FM.Or.getKren() < -10.0F) || (this.FM.Or.getKren() > 10.0F))
    {
      this.gyroDelta = (float)(this.gyroDelta - 0.01D);
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
    Mat localMat = hierMesh().material(j);
    String str1 = localMat.Name();
    if (str1.startsWith("PaintSchemes/Cache"))
    {
      try
      {
        str1 = str1.substring(19);
        str1 = str1.substring(0, str1.indexOf("/"));
        String str2 = Main.cur().netFileServerSkin.primaryPath();
        File localFile = new File(HomePath.toFileSystemName(str2 + "/DXXI_SARJA3_LATE/Customization.ini", 0));
        BufferedReader localBufferedReader = new BufferedReader(new FileReader(localFile));
        String str3 = null;
        int k = 0;
        int m = 0;
        int n = 0;
        while ((str3 = localBufferedReader.readLine()) != null)
        {
          if (str3.equals("[ReflectorSight]"))
          {
            k = 1;
            m = 0;
            n = 0; continue;
          }
          if (str3.equals("[NoWheelSpats]"))
          {
            k = 0;
            m = 1;
            n = 0; continue;
          }
          if (str3.equals("[WingSlots]"))
          {
            k = 0;
            m = 0;
            n = 1; continue;
          }
          if (!str3.equals(str1))
            continue;
          if (k != 0)
          {
            hierMesh().chunkVisible("Revi_D0", true);
            hierMesh().chunkVisible("Goertz_D0", false);
            this.hasRevi = true;
          }
          if ((m != 0) && (World.cur().camouflage != 1))
          {
            removeWheelSpats();
          }
          if (n == 0)
            continue;
          hierMesh().chunkVisible("SlotCoverLMid", false);
          hierMesh().chunkVisible("SlotCoverRMid", false);
          hierMesh().chunkVisible("SlotCoverLOut", false);
          hierMesh().chunkVisible("SlotCoverROut", false);
        }

        localBufferedReader.close();
      }
      catch (Exception localException)
      {
        System.out.println(localException);
      }

    }
    else if (World.Rnd().nextFloat(0.0F, 1.0F) > 0.6F)
    {
      hierMesh().chunkVisible("Revi_D0", true);
      hierMesh().chunkVisible("Goertz_D0", false);
      this.hasRevi = true;
    }

    if ((this.hasRevi) && (this.pit != null))
      this.pit.setRevi();
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
      super.moveFan(paramFloat);
      float f1 = this.FM.CT.getAileron();
      float f2 = this.FM.CT.getElevator();

      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9.0F * f1, cvt(f2, -1.0F, 1.0F, -8.0F, 9.5F));
      hierMesh().chunkSetAngles("pilotarm2_d0", cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - cvt(f2, -1.0F, 1.0F, -37.0F, 35.0F));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(f2, -1.0F, 0.0F, -61.0F, 0.0F) + cvt(f2, 0.0F, 1.0F, 0.0F, 43.0F));

      if (World.cur().camouflage == 1)
      {
        float f3 = Aircraft.cvt(this.FM.getSpeed(), 30.0F, 100.0F, 1.0F, 0.0F);
        float f4 = Aircraft.cvt(this.FM.getSpeed(), 0.0F, 30.0F, 0.0F, 0.5F);

        if (this.FM.Gears.gWheelSinking[0] > 0.0F)
        {
          this.skiAngleL = (0.5F * this.skiAngleL + 0.5F * this.FM.Or.getTangage());

          if (this.skiAngleL > 20.0F)
          {
            this.skiAngleL -= this.spring;
          }

          hierMesh().chunkSetAngles("GearL21_D0", World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) + this.skiAngleL, World.Rnd().nextFloat(f4, f4));

          if ((this.FM.Gears.gWheelSinking[1] == 0.0F) && (this.FM.Or.getRoll() < 365.0F) && (this.FM.Or.getRoll() > 355.0F))
          {
            this.skiAngleR = this.skiAngleL;
            hierMesh().chunkSetAngles("GearR21_D0", World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) + this.skiAngleR, World.Rnd().nextFloat(f4, f4));
          }

        }
        else
        {
          if (this.skiAngleL > f3 * -10.0F + 0.01D)
          {
            this.skiAngleL -= this.spring;
          }
          else if (this.skiAngleL < f3 * -10.0F - 0.01D)
          {
            this.skiAngleL += this.spring;
          }

          hierMesh().chunkSetAngles("GearL21_D0", 0.0F, this.skiAngleL, 0.0F);
        }

        if (this.FM.Gears.gWheelSinking[1] > 0.0F)
        {
          this.skiAngleR = (0.5F * this.skiAngleR + 0.5F * this.FM.Or.getTangage());

          if (this.skiAngleR > 20.0F)
          {
            this.skiAngleR -= this.spring;
          }

          hierMesh().chunkSetAngles("GearR21_D0", World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) + this.skiAngleR, World.Rnd().nextFloat(f4, f4));
        }
        else
        {
          if (this.skiAngleR > f3 * -10.0F + 0.01D)
          {
            this.skiAngleR -= this.spring;
          }
          else if (this.skiAngleR < f3 * -10.0F - 0.01D)
          {
            this.skiAngleR += this.spring;
          }
          hierMesh().chunkSetAngles("GearR21_D0", 0.0F, this.skiAngleR, 0.0F);
        }

        hierMesh().chunkSetAngles("GearC11_D0", 0.0F, (this.skiAngleL + this.skiAngleR) / 2.0F, 0.0F);
      }
    }
  }

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
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
          localVector3d.set(this.FM.Vwld);
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
          localVector3d.set(this.FM.Vwld);
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
          localVector3d.set(this.FM.Vwld);
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
          localVector3d.set(this.FM.Vwld);
          localWreckage.setSpeed(localVector3d);
        }
      }
    }
  }

  public void sfxWheels() {
    if (!this.hasSkis)
      super.sfxWheels();
  }

  public void auxPlus(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      this.gyroDelta += 1.0F;
    }
  }

  public void auxMinus(int paramInt)
  {
    switch (paramInt)
    {
    case 1:
      this.gyroDelta -= 1.0F;
    }
  }

  static
  {
    Class localClass = DXXI_SARJA3_LATE.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "D.XXI");
    Property.set(localClass, "meshName_fi", "3DO/Plane/DXXI_SARJA3_LATE/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/DXXI_SARJA3_LATE/hierMulti.him");
    Property.set(localClass, "PaintScheme_fi", new PaintSchemeFMPar00DXXI());
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00DXXI());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.0F);
    Property.set(localClass, "FlightModel", "FlightModels/FokkerS3LATE.fmd");
    Property.set(localClass, "cockpitClass", CockpitDXXI_SARJA3_LATE.class);
    Property.set(localClass, "LOSElevation", 0.8472F);
    Property.set(localClass, "originCountry", PaintScheme.countryFinland);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 500", "MGunBrowning303sipzl 500", "MGunBrowning303k 500", "MGunBrowning303k 500" });

    weaponsRegister(localClass, "AlternativeTracers", new String[] { "MGunBrowning303sipzl_fullTracers 500", "MGunBrowning303sipzl_NoTracers 500", "MGunBrowning303k_NoTracers 500", "MGunBrowning303k_NoTracers 500" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}