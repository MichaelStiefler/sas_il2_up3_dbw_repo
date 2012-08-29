package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class I_15BIS_SKIS extends I_15xyz
{
  private float skiAngleL;
  private float skiAngleR;
  private float spring;
  private float wireRandomizer1;
  private float wireRandomizer2;
  private float wireRandomizer3;
  private float wireRandomizer4;

  public I_15BIS_SKIS()
  {
    this.skiAngleL = 0.0F;
    this.skiAngleR = 0.0F;
    this.spring = 0.15F;
    this.wireRandomizer1 = 0.0F;
    this.wireRandomizer2 = 0.0F;
    this.wireRandomizer3 = 0.0F;
    this.wireRandomizer4 = 0.0F;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.CT.bHasBrakeControl = false;
    this.wireRandomizer1 = ((float)(Math.random() * 2.0D) - 1.0F);
    this.wireRandomizer2 = ((float)(Math.random() * 2.0D) - 1.0F);
    this.wireRandomizer3 = ((float)(Math.random() * 2.0D) - 1.0F);
    this.wireRandomizer4 = ((float)(Math.random() * 2.0D) - 1.0F);
  }

  public void sfxWheels()
  {
  }

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      int i = 0;
      float f1 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 30.0F, 80.0F, 1.0F, 0.0F);
      float f2 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed(), 0.0F, 30.0F, 0.0F, 0.5F);
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] > 0.0F)
      {
        i = 1;
        this.skiAngleL = (0.5F * this.skiAngleL + 0.5F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());
        if (this.skiAngleL > 20.0F)
          this.skiAngleL -= this.spring;
        hierMesh().chunkSetAngles("SkiL1_D0", World.Rnd().nextFloat(-f2, f2), World.Rnd().nextFloat(-f2, f2) - this.skiAngleL, World.Rnd().nextFloat(f2, f2));
      }
      else {
        if (this.skiAngleL > f1 * -10.0F + 0.01D)
        {
          this.skiAngleL -= this.spring;
          i = 1;
        }
        else if (this.skiAngleL < f1 * -10.0F - 0.01D)
        {
          this.skiAngleL += this.spring;
          i = 1;
        }
        hierMesh().chunkSetAngles("SkiL1_D0", 0.0F, -this.skiAngleL, 0.0F);
      }
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] > 0.0F)
      {
        i = 1;
        this.skiAngleR = (0.5F * this.skiAngleR + 0.5F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage());
        if (this.skiAngleR > 20.0F)
          this.skiAngleR -= this.spring;
        hierMesh().chunkSetAngles("SkiR1_D0", World.Rnd().nextFloat(-f2, f2), World.Rnd().nextFloat(-f2, f2) - this.skiAngleR, World.Rnd().nextFloat(f2, f2));
        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] == 0.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll() < 365.0F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getRoll() > 355.0F))
        {
          this.skiAngleL = this.skiAngleR;
          hierMesh().chunkSetAngles("SkiL1_D0", World.Rnd().nextFloat(-f2, f2), World.Rnd().nextFloat(-f2, f2) - this.skiAngleL, World.Rnd().nextFloat(f2, f2));
        }
      }
      else {
        if (this.skiAngleR > f1 * -10.0F + 0.01D)
        {
          this.skiAngleR -= this.spring;
          i = 1;
        }
        else if (this.skiAngleR < f1 * -10.0F - 0.01D)
        {
          this.skiAngleR += this.spring;
          i = 1;
        }
        hierMesh().chunkSetAngles("SkiR1_D0", 0.0F, -this.skiAngleR, 0.0F);
      }
      if ((i == 0) && (f1 == 0.0F))
      {
        super.moveFan(paramFloat);
        return;
      }
      hierMesh().chunkSetAngles("SkiC_D0", 0.0F, (this.skiAngleL + this.skiAngleR) / 2.0F, 0.0F);
      float f3 = this.skiAngleL / 20.0F;
      if (this.skiAngleL > 0.0F)
      {
        hierMesh().chunkSetAngles("LSkiFrontDownWire1_d0", 0.0F, -f3 * 4.0F, f3 * 12.4F);
        hierMesh().chunkSetAngles("LSkiFrontDownWire2_d0", 0.0F, -f3 * 4.0F, f3 * 12.4F);
      }
      else {
        hierMesh().chunkSetAngles("LSkiFrontDownWire1_d0", 0.0F, -f3 * 8.0F, f3 * 12.4F);
        hierMesh().chunkSetAngles("LSkiFrontDownWire2_d0", 0.0F, -f3 * 8.0F, f3 * 12.4F);
      }
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.xyz[0] = (-0.16F * f3 + this.suspL);
      Aircraft.xyz[1] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      hierMesh().chunkSetLocate("LSkiFrontUpWire_d0", Aircraft.xyz, Aircraft.ypr);
      float f4;
      float f5;
      float f6;
      float f7;
      float f8;
      float f9;
      float f10;
      if (this.skiAngleL < 0.0F)
      {
        hierMesh().chunkSetAngles("LWire1_d0", 0.0F, 0.0F, f3 * 15.0F);
        hierMesh().chunkSetAngles("LWire12_d0", 0.0F, 0.0F, f3 * 15.0F);
        hierMesh().chunkSetAngles("LWire2_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire3_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire4_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire5_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire6_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire7_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire8_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire9_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire10_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire11_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire13_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire14_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire15_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire16_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire17_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire18_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire19_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire20_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire21_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("LWire22_d0", 0.0F, 0.0F, 0.0F);
      }
      else {
        f4 = 1.0F;
        hierMesh().chunkSetAngles("LWire1_d0", 0.0F, 6.5F * f3 + f3 * (-20.0F * f1) * this.wireRandomizer3, f3 * (60.0F * f4));
        hierMesh().chunkSetAngles("LWire12_d0", 0.0F, 6.5F * f3 + f3 * (20.0F * f1) * this.wireRandomizer4, f3 * (70.0F * f4));
        f5 = f3 * -5.0F;
        f6 = f3 * -10.0F;
        f7 = f3 * -15.0F;
        f8 = f3 * (5.0F * f1) * this.wireRandomizer3;
        f9 = f3 * (10.0F * f1) * this.wireRandomizer3;
        f10 = f3 * (-5.0F * f1) * this.wireRandomizer4;
        hierMesh().chunkSetAngles("LWire2_d0", 0.0F, f9, f5);
        hierMesh().chunkSetAngles("LWire3_d0", 0.0F, f8, f6);
        hierMesh().chunkSetAngles("LWire4_d0", 0.0F, f9, f6);
        hierMesh().chunkSetAngles("LWire5_d0", 0.0F, f8, f6);
        hierMesh().chunkSetAngles("LWire6_d0", 0.0F, f9, f7);
        hierMesh().chunkSetAngles("LWire7_d0", 0.0F, f8, f6);
        hierMesh().chunkSetAngles("LWire8_d0", 0.0F, f9, f7);
        hierMesh().chunkSetAngles("LWire9_d0", 0.0F, f8, f5);
        hierMesh().chunkSetAngles("LWire10_d0", 0.0F, f9, f5);
        hierMesh().chunkSetAngles("LWire11_d0", 0.0F, f8, f5);
        hierMesh().chunkSetAngles("LWire13_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("LWire14_d0", 0.0F, f10, f7);
        hierMesh().chunkSetAngles("LWire15_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("LWire16_d0", 0.0F, f10, f7);
        hierMesh().chunkSetAngles("LWire17_d0", 0.0F, 0.0F, f6);
        hierMesh().chunkSetAngles("LWire18_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("LWire19_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("LWire20_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("LWire21_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("LWire22_d0", 0.0F, f10, f6);
      }
      f3 = this.skiAngleR / 20.0F;
      if (this.skiAngleR > 0.0F)
      {
        hierMesh().chunkSetAngles("RSkiFrontDownWire1_d0", 0.0F, f3 * 4.0F, f3 * 12.4F);
        hierMesh().chunkSetAngles("RSkiFrontDownWire2_d0", 0.0F, f3 * 4.0F, f3 * 12.4F);
      }
      else {
        hierMesh().chunkSetAngles("RSkiFrontDownWire1_d0", 0.0F, f3 * 8.0F, f3 * 12.4F);
        hierMesh().chunkSetAngles("RSkiFrontDownWire2_d0", 0.0F, f3 * 8.0F, f3 * 12.4F);
      }
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.ypr[0] = 0.0F;
      Aircraft.xyz[0] = (-0.16F * f3 + this.suspR);
      Aircraft.xyz[1] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      hierMesh().chunkSetLocate("RSkiFrontUpWire_d0", Aircraft.xyz, Aircraft.ypr);
      if (this.skiAngleR < 0.0F)
      {
        hierMesh().chunkSetAngles("RWire1_d0", 0.0F, 0.0F, f3 * 15.0F);
        hierMesh().chunkSetAngles("RWire12_d0", 0.0F, 0.0F, f3 * 15.0F);
        hierMesh().chunkSetAngles("RWire2_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire3_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire4_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire5_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire6_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire7_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire8_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire9_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire10_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire11_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire13_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire14_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire15_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire16_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire17_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire18_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire19_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire20_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire21_d0", 0.0F, 0.0F, 0.0F);
        hierMesh().chunkSetAngles("RWire22_d0", 0.0F, 0.0F, 0.0F);
      }
      else {
        f4 = 1.0F;
        hierMesh().chunkSetAngles("RWire1_d0", 0.0F, -6.5F * f3 + f3 * (-20.0F * f1) * this.wireRandomizer1, f3 * (60.0F * f4));
        hierMesh().chunkSetAngles("RWire12_d0", 0.0F, -6.5F * f3 + f3 * (20.0F * f1) * this.wireRandomizer2, f3 * (70.0F * f4));
        f5 = f3 * -5.0F;
        f6 = f3 * -10.0F;
        f7 = f3 * -15.0F;
        f8 = f3 * (5.0F * f1) * this.wireRandomizer1;
        f9 = f3 * (10.0F * f1) * this.wireRandomizer1;
        f10 = f3 * (-5.0F * f1) * this.wireRandomizer2;
        hierMesh().chunkSetAngles("RWire2_d0", 0.0F, f9, f5);
        hierMesh().chunkSetAngles("RWire3_d0", 0.0F, f8, f6);
        hierMesh().chunkSetAngles("RWire4_d0", 0.0F, f9, f6);
        hierMesh().chunkSetAngles("RWire5_d0", 0.0F, f8, f7);
        hierMesh().chunkSetAngles("RWire6_d0", 0.0F, f9, f6);
        hierMesh().chunkSetAngles("RWire7_d0", 0.0F, f8, f6);
        hierMesh().chunkSetAngles("RWire8_d0", 0.0F, f9, f6);
        hierMesh().chunkSetAngles("RWire9_d0", 0.0F, f8, f5);
        hierMesh().chunkSetAngles("RWire10_d0", 0.0F, f9, f5);
        hierMesh().chunkSetAngles("RWire11_d0", 0.0F, f8, f5);
        hierMesh().chunkSetAngles("RWire13_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("RWire14_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("RWire15_d0", 0.0F, f10, f7);
        hierMesh().chunkSetAngles("RWire16_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("RWire17_d0", 0.0F, 0.0F, f7);
        hierMesh().chunkSetAngles("RWire18_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("RWire19_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("RWire20_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("RWire21_d0", 0.0F, f10, f6);
        hierMesh().chunkSetAngles("RWire22_d0", 0.0F, f10, f6);
      }
    }
    super.moveFan(paramFloat);
  }

  static
  {
    Class localClass = I_15BIS_SKIS.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-15bis");
    Property.set(localClass, "meshName", "3DO/Plane/I-15bis/hierSkis.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar08());
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
    Property.set(localClass, "yearService", 1937.0F);
    Property.set(localClass, "yearExpired", 1942.0F);
    Property.set(localClass, "FlightModel", "FlightModels/I-15bis.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitI_15bis.class });

    Property.set(localClass, "LOSElevation", 0.84305F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xAO10", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xAO10_2xFAB50", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2xFAB50", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xRS82", new String[] { "MGunPV1sipzl 775", "MGunPV1sipzl 775", "MGunPV1sipzl 425", "MGunPV1sipzl 425", null, null, "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, null, null, null, null, null, null, "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", "PylonRO_82_1", null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}