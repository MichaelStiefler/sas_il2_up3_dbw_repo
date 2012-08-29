package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Squares;

public abstract class Scheme3 extends Aircraft
{
  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -25.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -25.0F * paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat)
  {
    hierMesh().chunkSetAngles("VatorL_D0", 0.0F, -33.0F * paramFloat, 0.0F);
  }

  protected void moveAileron(float paramFloat)
  {
    hierMesh().chunkSetAngles("AroneL_D0", 0.0F, -33.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("AroneR_D0", 0.0F, -33.0F * paramFloat, 0.0F);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -70.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap04_D0", 0.0F, f, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) { case 11:
      super.cutFM(17, paramInt2, paramActor); return super.cutFM(18, paramInt2, paramActor);
    case 12:
      super.cutFM(17, paramInt2, paramActor); return super.cutFM(18, paramInt2, paramActor);
    case 17:
      super.cutFM(18, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(17, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(18, paramInt2, paramActor);
      if (World.Rnd().nextBoolean()) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut *= 0.75F;
      } else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut *= 0.95F;
      }
      break;
    case 18:
      super.cutFM(17, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(17, paramInt2, paramActor);
      this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.cut(18, paramInt2, paramActor);
      if (World.Rnd().nextBoolean()) {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut *= 0.75F;
      } else {
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLOut *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLMid *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingLIn *= 0.75F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRIn *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingRMid *= 0.95F;
        this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Sq_of_type_ComMaddoxIl2FmSquares.liftWingROut *= 0.95F;
      }case 13:
    case 14:
    case 15:
    case 16: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }
}