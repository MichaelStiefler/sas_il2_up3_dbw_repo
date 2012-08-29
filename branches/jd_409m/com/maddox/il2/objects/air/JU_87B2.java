package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class JU_87B2 extends JU_87
{
  private boolean bDynamoOperational = true;
  private float dynamoOrient = 0.0F;
  private boolean bDynamoRotary = false;
  private int pk;

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    if ((paramInt1 == 36) || (paramInt1 == 37) || (paramInt1 == 10))
    {
      hierMesh().chunkVisible("GearR3_D0", false);
      hierMesh().chunkVisible("GearR3Rot_D0", false);
      this.bDynamoOperational = false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveFan(float paramFloat)
  {
    if (this.bDynamoOperational) {
      this.pk = Math.abs((int)(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() / 14.0D));
      if (this.pk >= 1) this.pk = 1;
    }
    if (this.bDynamoRotary != (this.pk == 1)) {
      this.bDynamoRotary = (this.pk == 1);
      hierMesh().chunkVisible("GearR3_D0", !this.bDynamoRotary);
      hierMesh().chunkVisible("GearR3Rot_D0", this.bDynamoRotary);
    }
    this.dynamoOrient = (this.bDynamoRotary ? (this.dynamoOrient - 17.987F) % 360.0F : (float)(this.dynamoOrient - this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() * 1.544401526451111D) % 360.0F);
    hierMesh().chunkSetAngles("GearR3_D0", 0.0F, this.dynamoOrient, 0.0F);
    super.moveFan(paramFloat);
  }

  protected void moveAirBrake(float paramFloat)
  {
    hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 80.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 9; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -15.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = JU_87B2.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "FlightModel", "FlightModels/Ju-87B-2.fmd");
    Property.set(localClass, "meshName", "3do/plane/Ju-87B-2/hier.him");
    Property.set(localClass, "iconFar_shortClassName", "Ju-87");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU_87B2.class, CockpitJU_87B2_Gunner.class });

    Property.set(localClass, "LOSElevation", 0.8499F);

    Property.set(localClass, "yearService", 1939.9F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4xSC50", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null });

    Aircraft.weaponsRegister(localClass, "1xSC500", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xSD500", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunSD500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "1xSC250", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunSC250 1" });

    Aircraft.weaponsRegister(localClass, "1xSC250_4xSC50", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 1050", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC250 1" });

    Aircraft.weaponsRegister(localClass, "1xAB250", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunAB250 1" });

    Aircraft.weaponsRegister(localClass, "1SC1000", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG15t 900", null, null, null, null, "BombGunSC1000 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null });
  }
}