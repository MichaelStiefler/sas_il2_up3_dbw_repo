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
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

public abstract class LAGG_3 extends Scheme1
  implements TypeFighter
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { if (!(this instanceof LAGG_3RD)) {
      hierMesh().chunkSetAngles("Water_luk", 0.0F, -17.5F + 17.5F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
    }
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);

    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -80.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 100.0F * paramFloat, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("Engine")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < paramShot.mass)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
    }

    if ((paramShot.chunkName.startsWith("WingLMid")) && 
      (World.Rnd().nextFloat() < 0.25F) && (paramShot.power > 7650.0F) && (paramShot.powerType == 3)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, World.Rnd().nextInt(1, (int)(paramShot.mass * 100.0F)));
    }

    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat() < 0.25F) && (paramShot.power > 7650.0F) && (paramShot.powerType == 3)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 1, World.Rnd().nextInt(1, (int)(paramShot.mass * 100.0F)));
    }

    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat() < 0.25F) && (paramShot.power > 7650.0F) && (paramShot.powerType == 3)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 2, World.Rnd().nextInt(1, (int)(paramShot.mass * 100.0F)));
    }

    if ((paramShot.chunkName.startsWith("WingRMid")) && 
      (World.Rnd().nextFloat() < 0.25F) && (paramShot.power > 7650.0F) && (paramShot.powerType == 3)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 3, World.Rnd().nextInt(1, (int)(paramShot.mass * 100.0F)));
    }

    if (paramShot.chunkName.startsWith("Pilot")) {
      if (Aircraft.v1.jdField_x_of_type_Double > 0.8600000143051148D) {
        if (paramShot.power * Aircraft.v1.jdField_x_of_type_Double > 19200.0D)
          killPilot(paramShot.initiator, 0);
      } else if (World.Rnd().nextFloat(0.0F, 4600.0F) < paramShot.power) {
        killPilot(paramShot.initiator, 0);
        if ((Aircraft.Pd.z > 0.5D) && 
          (paramShot.initiator == World.getPlayerAircraft()) && (World.cur().isArcade())) HUD.logCenter("H E A D S H O T");
      }

      paramShot.chunkName = ("CF_D" + chunkDamageVisible("CF"));
    }

    super.msgShot(paramShot);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) {
    case 33:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, World.Rnd().nextInt(3, 6));
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, World.Rnd().nextInt(3, 6));
      return super.cutFM(37, paramInt2, paramActor);
    case 19:
      if (World.Rnd().nextFloat() >= 0.25F) break;
      int i = World.Rnd().nextInt(3, 6);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 1, i);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(this, 2, i);
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      int i = 0;
      int j;
      if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  static
  {
    Class localClass = LAGG_3.class;
    Property.set(localClass, "originCountry", PaintScheme.countryRussia);
  }
}