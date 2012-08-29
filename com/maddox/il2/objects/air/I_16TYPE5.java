package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class I_16TYPE5 extends I_16
  implements TypeTNBFighter
{
  private boolean bailingOut = false;
  private boolean canopyForward = false;
  private boolean okToJump = false;
  private float flaperonAngle = 0.0F;
  private float aileronsAngle = 0.0F;
  private boolean sideDoorOpened = false;
  private boolean oneTimeCheckDone = false;

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if ((paramString.startsWith("xxtank1")) && (getEnergyPastArmor(0.1F, paramShot) > 0.0F) && (World.Rnd().nextFloat() < 0.3F))
    {
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.astateTankStates[0] == 0) {
        Aircraft.debugprintln(this, "*** Fuel Tank: Pierced..");

        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 2);
      }
      if ((paramShot.powerType == 3) && (World.Rnd().nextFloat() < 0.75F))
      {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, 2);
        Aircraft.debugprintln(this, "*** Fuel Tank: Hit..");
      }
    }
    else {
      super.hitBone(paramString, paramShot, paramPoint3d);
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1)
    {
    case 19:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Gears.hitCentreGear();
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

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

  public void moveGear(float paramFloat)
  {
    super.moveGear(paramFloat);

    if (paramFloat > 0.5F)
    {
      hierMesh().chunkSetAngles("GearWireR1_D0", Aircraft.cvt(paramFloat, 0.5F, 1.0F, 14.5F, -8.0F), Aircraft.cvt(paramFloat, 0.5F, 1.0F, 44.0F, 62.5F), 0.0F);
      hierMesh().chunkSetAngles("GearWireL1_D0", Aircraft.cvt(paramFloat, 0.5F, 1.0F, -14.5F, 8.0F), Aircraft.cvt(paramFloat, 0.5F, 1.0F, -44.0F, -62.5F), 0.0F);
    }
    else if (paramFloat > 0.25F)
    {
      hierMesh().chunkSetAngles("GearWireR1_D0", Aircraft.cvt(paramFloat, 0.25F, 0.5F, 33.0F, 14.5F), Aircraft.cvt(paramFloat, 0.25F, 0.5F, 38.0F, 44.0F), 0.0F);
      hierMesh().chunkSetAngles("GearWireL1_D0", Aircraft.cvt(paramFloat, 0.25F, 0.5F, -33.0F, -14.5F), Aircraft.cvt(paramFloat, 0.25F, 0.5F, -38.0F, -44.0F), 0.0F);
    }
    else
    {
      hierMesh().chunkSetAngles("GearWireR1_D0", Aircraft.cvt(paramFloat, 0.0F, 0.25F, 0.0F, 33.0F), Aircraft.cvt(paramFloat, 0.0F, 0.25F, 0.0F, 38.0F), 0.0F);
      hierMesh().chunkSetAngles("GearWireL1_D0", Aircraft.cvt(paramFloat, 0.0F, 0.25F, 0.0F, -33.0F), Aircraft.cvt(paramFloat, 0.0F, 0.25F, 0.0F, -38.0F), 0.0F);
    }
    if (paramFloat > 0.5F)
    {
      hierMesh().chunkVisible("GearWireR2_D0", true);
      hierMesh().chunkVisible("GearWireL2_D0", true);
    }
    else
    {
      hierMesh().chunkVisible("GearWireR2_D0", false);
      hierMesh().chunkVisible("GearWireL2_D0", false);
    }
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

  protected void moveFan(float paramFloat)
  {
    if (Config.isUSE_RENDER())
    {
      super.moveFan(paramFloat);
      float f1 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getAileron();
      float f2 = this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getElevator();
      hierMesh().chunkSetAngles("Stick_D0", 0.0F, 12.0F * f1, Aircraft.cvt(f2, -1.0F, 1.0F, -12.0F, 18.0F));
      hierMesh().chunkSetAngles("pilotarm2_d0", Aircraft.cvt(f1, -1.0F, 1.0F, 14.0F, -16.0F), 0.0F, Aircraft.cvt(f1, -1.0F, 1.0F, 6.0F, -8.0F) - (Aircraft.cvt(f2, -1.0F, 0.0F, -36.0F, 0.0F) + Aircraft.cvt(f2, 0.0F, 1.0F, 0.0F, 32.0F)));
      hierMesh().chunkSetAngles("pilotarm1_d0", 0.0F, 0.0F, Aircraft.cvt(f1, -1.0F, 1.0F, -16.0F, 14.0F) + Aircraft.cvt(f2, -1.0F, 0.0F, -62.0F, 0.0F) + Aircraft.cvt(f2, 0.0F, 1.0F, 0.0F, 44.0F));
    }
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

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    hierMesh().chunkVisible("GearWireR1_D0", true);
    hierMesh().chunkVisible("GearWireL1_D0", true);
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

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "I-16");
    Property.set(localClass, "meshName", "3DO/Plane/I-16type5(multi)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFCSPar07());
    Property.set(localClass, "meshName_ru", "3DO/Plane/I-16type5/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeFCSPar07());
    Property.set(localClass, "yearService", 1935.0F);
    Property.set(localClass, "yearExpired", 1942.0F);
    Property.set(localClass, "FlightModel", "FlightModels/I-16type5.fmd");
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