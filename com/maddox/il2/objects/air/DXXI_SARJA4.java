package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Property;

public class DXXI_SARJA4 extends DXXI
{
  private boolean skisLocked;

  public DXXI_SARJA4()
  {
    this.skisLocked = false;
  }

  public void missionStarting()
  {
    super.missionStarting();
    if ((World.cur().camouflage == 1) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isWasAirborne()))
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bTailwheelLocked = true;
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.hasSelfSealingTank = true;
    this.canopyMaxAngle = 0.45F;
    if ((Config.isUSE_RENDER()) && (World.cur().camouflage == 1))
    {
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
    else if (Math.random() < 0.009999999776482582D) {
      removeWheelSpats();
    }
  }

  protected void moveFan(float paramFloat) {
    if (Config.isUSE_RENDER())
    {
      super.moveFan(-paramFloat);
      float f1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getAileron();
      float f2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator();
      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 9.0F * f1, cvt(f2, -1.0F, 1.0F, -8.0F, 9.5F));
      hierMesh().chunkSetAngles("pilotarm2_d0", cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - cvt(f2, -1.0F, 1.0F, -37.0F, 35.0F));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + cvt(f2, -1.0F, 0.0F, -61.0F, 0.0F) + cvt(f2, 0.0F, 1.0F, 0.0F, 43.0F));
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
        float f4 = Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -20.0F, 20.0F, -20.0F, 20.0F);
        if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.onGround()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() > 0.9F) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 5.0F))
        {
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[0] > 0.0F)
            hierMesh().chunkSetAngles("GearL21_D0", World.Rnd().nextFloat(-0.5F, 0.5F), World.Rnd().nextFloat(-1.0F, 1.0F) + f4, World.Rnd().nextFloat(-0.5F, 0.5F));
          else
            hierMesh().chunkSetAngles("GearL21_D0", 0.0F, f4, 0.0F);
          if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.gWheelSinking[1] > 0.0F)
            hierMesh().chunkSetAngles("GearR21_D0", World.Rnd().nextFloat(-1.0F, 1.0F), World.Rnd().nextFloat(-1.0F, 1.0F) + f4, World.Rnd().nextFloat(-1.0F, 1.0F));
          else
            hierMesh().chunkSetAngles("GearR21_D0", 0.0F, f4, 0.0F);
          hierMesh().chunkSetAngles("GearC11_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Or_of_type_ComMaddoxIl2EngineOrientation.getTangage(), -20.0F, 20.0F, -20.0F, 20.0F), 0.0F);
        }
        else if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.bTailwheelLocked)
        {
          if (this.skisLocked)
          {
            hierMesh().chunkSetAngles("GearL21_D0", 0.0F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearR21_D0", 0.0F, 0.0F, 0.0F);
            hierMesh().chunkSetAngles("GearC11_D0", 0.0F, 0.0F, 0.0F);
          }
          else {
            hierMesh().chunkSetAngles("GearL21_D0", 0.0F, f4, 0.0F);
            hierMesh().chunkSetAngles("GearR21_D0", 0.0F, f4, 0.0F);
            hierMesh().chunkSetAngles("GearC11_D0", 0.0F, f4, 0.0F);
            if ((f4 < 0.1F) && (f4 > -0.1F))
              this.skisLocked = true;
          }
        }
        else {
          this.skisLocked = false;
          hierMesh().chunkSetAngles("GearL21_D0", 0.0F, f4, 0.0F);
          hierMesh().chunkSetAngles("GearR21_D0", 0.0F, f4, 0.0F);
          hierMesh().chunkSetAngles("GearC11_D0", 0.0F, f4, 0.0F);
        }
      }
      else {
        hierMesh().chunkSetAngles("GearL21_D0", 0.0F, -100.0F, 0.0F);
        hierMesh().chunkSetAngles("GearR21_D0", 0.0F, -100.0F, 0.0F);
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
      if ((hierMesh().isChunkVisible("GearR22_D2")) && (!hierMesh().isChunkVisible("gearr31_d0")))
      {
        hierMesh().chunkVisible("gearr31_d0", true);
        hierMesh().chunkVisible("gearr32_d0", true);
        localWreckage = new Wreckage(this, hierMesh().chunkFind("GearR22_D1"));
        localWreckage.collide(true);
        localVector3d = new Vector3d();
        localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d);
        localWreckage.setSpeed(localVector3d);
      }
      if ((hierMesh().isChunkVisible("GearL22_D2")) && (!hierMesh().isChunkVisible("gearl31_d0")))
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
    else {
      if (((hierMesh().isChunkVisible("GearR11_D1")) || (hierMesh().isChunkVisible("GearR21_D2"))) && (!hierMesh().isChunkVisible("gearr31_d0")))
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
      if (((hierMesh().isChunkVisible("GearL11_D1")) || (hierMesh().isChunkVisible("GearL21_D2"))) && (!hierMesh().isChunkVisible("gearl31_d0")))
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

  static
  {
    Class localClass = DXXI_SARJA4.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "D.XXI");
    Property.set(localClass, "meshName", "3DO/Plane/DXXI_SARJA4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar00());
    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.0F);
    Property.set(localClass, "FlightModel", "FlightModels/FokkerS4.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitDXXI_SARJA4.class });

    Property.set(localClass, "LOSElevation", 0.8472F);
    Property.set(localClass, "originCountry", PaintScheme.countryFinland);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 300", "MGunBrowning303k 300", "MGunBrowning303k 300", "MGunBrowning303k 300" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}