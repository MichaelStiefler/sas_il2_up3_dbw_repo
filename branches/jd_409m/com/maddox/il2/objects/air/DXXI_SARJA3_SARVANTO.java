package com.maddox.il2.objects.air;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.BulletProperties;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.GunProperties;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.objects.weapons.Gun;
import com.maddox.rts.Property;

public class DXXI_SARJA3_SARVANTO extends DXXI
  implements TypeAcePlane
{
  private float skiAngleL = 0.0F;
  private float skiAngleR = 0.0F;
  private float spring = 0.15F;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Skill = 3;
    JormaSarvanto();
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
  }

  private void JormaSarvanto()
  {
    for (int i = 0; i < this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons.length; i++)
    {
      BulletEmitter[] arrayOfBulletEmitter = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.Weapons[i];
      if (arrayOfBulletEmitter == null)
        continue;
      for (int j = 0; j < arrayOfBulletEmitter.length; j++)
      {
        BulletEmitter localBulletEmitter = arrayOfBulletEmitter[j];
        if (!(localBulletEmitter instanceof Gun))
          continue;
        GunProperties localGunProperties = ((Gun)localBulletEmitter).prop;
        BulletProperties[] arrayOfBulletProperties = localGunProperties.bullet;
        if (arrayOfBulletProperties == null)
          continue;
        for (int k = 0; k < arrayOfBulletProperties.length; k++)
        {
          arrayOfBulletProperties[k].powerType = 3;
          arrayOfBulletProperties[k].massa = 0.02F;
          arrayOfBulletProperties[k].kalibr = 4.442132E-005F;
          arrayOfBulletProperties[k].speed = 835.0F;

          if (arrayOfBulletProperties[k].power == 0.0F)
            continue;
          arrayOfBulletProperties[k].power = 0.002F;
        }
      }
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
      super.moveFan(paramFloat);
      float f1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getAileron();
      float f2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator();

      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9.0F * f1, Aircraft.cvt(f2, -1.0F, 1.0F, -8.0F, 9.5F));
      hierMesh().chunkSetAngles("pilotarm2_d0", Aircraft.cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, Aircraft.cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - Aircraft.cvt(f2, -1.0F, 1.0F, -37.0F, 35.0F));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, Aircraft.cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + Aircraft.cvt(f2, -1.0F, 0.0F, -61.0F, 0.0F) + Aircraft.cvt(f2, 0.0F, 1.0F, 0.0F, 43.0F));

      if (World.cur().camouflage == 1)
      {
        float f3 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 30.0F, 100.0F, 1.0F, 0.0F);
        float f4 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 0.0F, 30.0F, 0.0F, 0.5F);

        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] > 0.0F)
        {
          this.skiAngleL = (0.5F * this.skiAngleL + 0.5F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());

          if (this.skiAngleL > 20.0F)
          {
            this.skiAngleL -= this.spring;
          }

          hierMesh().chunkSetAngles("GearL21_D0", World.Rnd().nextFloat(-f4, f4), World.Rnd().nextFloat(-f4 * 2.0F, f4 * 2.0F) + this.skiAngleL, World.Rnd().nextFloat(f4, f4));

          if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] == 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll() < 365.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll() > 355.0F))
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

        if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] > 0.0F)
        {
          this.skiAngleR = (0.5F * this.skiAngleR + 0.5F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());

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

  public void sfxWheels()
  {
    if (!this.jdField_hasSkis_of_type_Boolean)
      super.sfxWheels();
  }

  static
  {
    Class localClass = DXXI_SARJA3_SARVANTO.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "D.XXI");
    Property.set(localClass, "meshName", "3DO/Plane/DXXI_SARJA3_EARLY(Sarvanto)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00DXXI());
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1940.0F);
    Property.set(localClass, "FlightModel", "FlightModels/FokkerS3Early.fmd");
    Property.set(localClass, "LOSElevation", 0.8472F);
    Property.set(localClass, "originCountry", PaintScheme.countryFinland);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 500", "MGunBrowning303sipzl 500", "MGunBrowning303k 500", "MGunBrowning303k 500" });

    Aircraft.weaponsRegister(localClass, "AlternativeTracers", new String[] { "MGunBrowning303sipzl_fullTracers 500", "MGunBrowning303sipzl_NoTracers 500", "MGunBrowning303k_NoTracers 500", "MGunBrowning303k_NoTracers 500" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}