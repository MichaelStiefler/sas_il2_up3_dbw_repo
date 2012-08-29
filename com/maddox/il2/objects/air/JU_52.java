package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public abstract class JU_52 extends Scheme6
  implements TypeTransport
{
  private boolean bDynamoOperational = true;
  private float dynamoOrient = 0.0F;
  private boolean bDynamoRotary = false;
  private int pk;

  public void doKillPilot(int paramInt)
  {
    switch (paramInt) {
    case 2:
      if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret.length <= 0) break;
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
    }
  }

  public void doMurderPilot(int paramInt)
  {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
    }
  }

  protected void moveFan(float paramFloat)
  {
    if (this.bDynamoOperational) {
      this.pk = Math.abs((int)(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() / 14.0D));
      if (this.pk >= 1) this.pk = 1;
    }
    if (this.bDynamoRotary != (this.pk == 1)) {
      this.bDynamoRotary = (this.pk == 1);
      hierMesh().chunkVisible("Cart_D0", !this.bDynamoRotary);
      hierMesh().chunkVisible("CartRot_D0", this.bDynamoRotary);
    }
    this.dynamoOrient = (this.bDynamoRotary ? (this.dynamoOrient - 17.987F) % 360.0F : (float)(this.dynamoOrient - this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() * 1.544401526451111D) % 360.0F);
    hierMesh().chunkSetAngles("Cart_D0", 0.0F, this.dynamoOrient, 0.0F);
    super.moveFan(paramFloat);
  }

  protected void moveFlap(float paramFloat)
  {
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, -45.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -45.0F * paramFloat, 0.0F);
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 0.5F) < paramShot.mass)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 0, 1);
    }
    if ((paramShot.chunkName.startsWith("Engine2")) && 
      (World.Rnd().nextFloat(0.0F, 0.5F) < paramShot.mass)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 1, 1);
    }
    if ((paramShot.chunkName.startsWith("Engine3")) && 
      (World.Rnd().nextFloat(0.0F, 0.5F) < paramShot.mass)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitEngine(paramShot.initiator, 2, 1);
    }

    if (paramShot.chunkName.startsWith("Turret")) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.turret[0].bIsOperable = false;
    }

    if ((paramShot.chunkName.startsWith("Tail1")) && 
      (Aircraft.Pd.jdField_z_of_type_Double > 0.5D) && (Aircraft.Pd.jdField_x_of_type_Double > -6.0D) && (Aircraft.Pd.jdField_x_of_type_Double < -4.949999809265137D) && (World.Rnd().nextFloat() < 0.5F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, 2, (int)(paramShot.mass * 1000.0F * 0.5F));
    }

    if ((paramShot.chunkName.startsWith("CF")) && 
      (Aircraft.v1.jdField_x_of_type_Double < -0.2000000029802322D) && (Aircraft.Pd.jdField_x_of_type_Double > 2.599999904632568D) && (Aircraft.Pd.jdField_z_of_type_Double > 0.7350000143051148D) && 
      (World.Rnd().nextFloat() < 0.178F)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitPilot(paramShot.initiator, Aircraft.Pd.jdField_y_of_type_Double > 0.0D ? 0 : 1, (int)(paramShot.mass * 900.0F));
    }

    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (Math.abs(Aircraft.Pd.jdField_y_of_type_Double) < 2.099999904632568D)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, World.Rnd().nextInt(0, (int)(paramShot.mass * 30.0F)));
    }

    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (Math.abs(Aircraft.Pd.jdField_y_of_type_Double) < 2.099999904632568D)) {
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_AS_of_type_ComMaddoxIl2FmAircraftState.hitTank(paramShot.initiator, 0, World.Rnd().nextInt(1, (int)(paramShot.mass * 30.0F)));
    }

    super.msgShot(paramShot);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  static
  {
    Class localClass = JU_52.class;
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
  }
}