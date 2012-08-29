package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class I_16TYPE5_SKIS extends I_16FixedSkis
  implements TypeTNBFighter
{
  private boolean bailingOut = false;
  private boolean canopyForward = false;
  private boolean okToJump = false;
  private float flaperonAngle = 0.0F;
  private float aileronsAngle = 0.0F;
  private boolean oneTimeCheckDone = false;
  private boolean sideDoorOpened = false;

  public void moveCockpitDoor(float paramFloat)
  {
    if ((this.bailingOut) && (paramFloat >= 1.0F) && (!this.canopyForward))
    {
      this.canopyForward = true;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceCockpitDoor(0.0F);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this, 1);
    }
    else if (this.canopyForward)
    {
      hierMesh().chunkSetAngles("Blister2_D0", 0.0F, 160.0F * paramFloat, 0.0F);
      if (paramFloat >= 1.0F)
      {
        this.okToJump = true;
        super.hitDaSilk();
      }
    }
    else
    {
      Aircraft.xyz[0] = 0.0F;
      Aircraft.xyz[2] = 0.0F;
      Aircraft.ypr[0] = 0.0F;
      Aircraft.ypr[1] = 0.0F;
      Aircraft.ypr[2] = 0.0F;
      Aircraft.xyz[1] = (paramFloat * 0.548F);
      hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    }

    if (Config.isUSE_RENDER())
    {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  public void hitDaSilk()
  {
    if (this.okToJump)
    {
      super.hitDaSilk();
    }
    else if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) || (isNetPlayer()))
    {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor() == 1.0D) && (!this.bailingOut))
      {
        this.bailingOut = true;
        this.okToJump = true;
        this.canopyForward = true;
        super.hitDaSilk();
      }
    }
    else if (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(0))
    {
      if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor() < 1.0D) && (!this.bailingOut))
      {
        this.bailingOut = true;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this, 1);
      }
      else if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor() == 1.0D) && (!this.bailingOut))
      {
        this.bailingOut = true;
        this.okToJump = true;
        this.canopyForward = true;
        super.hitDaSilk();
      }

    }

    if ((!this.sideDoorOpened) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.bIsAboutToBailout) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.isPilotDead(0)))
    {
      this.sideDoorOpened = true;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.forceCockpitDoor(0.0F);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this, 1);
    }
  }

  public void missionStarting()
  {
    super.missionStarting();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  public void prepareCamouflage() {
    super.prepareCamouflage();
    hierMesh().chunkVisible("pilotarm2_d0", true);
    hierMesh().chunkVisible("pilotarm1_d0", true);
  }

  public void moveWheelSink()
  {
  }

  public void moveGear(float paramFloat)
  {
  }

  protected void moveAileron(float paramFloat)
  {
    this.aileronsAngle = paramFloat;
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * paramFloat - this.flaperonAngle, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * paramFloat + this.flaperonAngle, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    this.flaperonAngle = (paramFloat * 17.0F);
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -30.0F * this.aileronsAngle - this.flaperonAngle, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -30.0F * this.aileronsAngle + this.flaperonAngle, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((!this.oneTimeCheckDone) && (!this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (!isNetPlayer()))
    {
      if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)
      {
        this.oneTimeCheckDone = true;
        if (World.cur().camouflage == 1)
        {
          if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.25F)
          {
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl = 1.0F;
            this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this, 1);
          }

        }
        else if (World.Rnd().nextFloat(0.0F, 1.0F) < 0.5F)
        {
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.cockpitDoorControl = 1.0F;
          this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.setCockpitDoor(this, 1);
        }
      }
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D1", true);
      hierMesh().chunkVisible("pilotarm2_d0", false);
      hierMesh().chunkVisible("pilotarm1_d0", false);
    }
  }

  public void doRemoveBodyFromPlane(int paramInt)
  {
    super.doRemoveBodyFromPlane(paramInt);
    hierMesh().chunkVisible("pilotarm2_d0", false);
    hierMesh().chunkVisible("pilotarm1_d0", false);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type5(multi)/hier_skis.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar07());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-16type5/hier_skis.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar07());
    Property.set(localClass, "yearService", 1935.0F);
    Property.set(localClass, "yearExpired", 1942.0F);
    Property.set(localClass, "FlightModel", "FlightModels/I-16type5Skis.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitI_16TYPE5.class });

    Property.set(localClass, "LOSElevation", 0.82595F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 3, 3, 9, 9 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev07", "_ExternalDev08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASk 900", "MGunShKASk 900", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x50kg", new String[] { "MGunShKASk 900", "MGunShKASk 900", "BombGunFAB50 1", "BombGunFAB50 1", null, null });

    Aircraft.weaponsRegister(localClass, "2x100kg", new String[] { "MGunShKASk 900", "MGunShKASk 900", "BombGunFAB100 1", "BombGunFAB100 1", null, null });

    Aircraft.weaponsRegister(localClass, "PV-1", new String[] { "MGunPV1 900", "MGunPV1 900", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}