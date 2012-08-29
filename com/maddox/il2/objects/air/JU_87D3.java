package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class JU_87D3 extends JU_87
  implements TypeStormovik
{
  private boolean bDynamoLOperational = true;
  private boolean bDynamoROperational = true;
  private float dynamoOrient = 0.0F;
  private boolean bDynamoRotary = false;
  private int pk;

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    if ((paramInt1 == 33) || (paramInt1 == 34) || (paramInt1 == 9))
    {
      hierMesh().chunkVisible("GearL3_D0", false);
      hierMesh().chunkVisible("GearL3Rot_D0", false);
      this.bDynamoLOperational = false;
    }
    if ((paramInt1 == 36) || (paramInt1 == 37) || (paramInt1 == 10))
    {
      hierMesh().chunkVisible("GearR3_D0", false);
      hierMesh().chunkVisible("GearR3Rot_D0", false);
      this.bDynamoROperational = false;
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  protected void moveFan(float paramFloat)
  {
    this.pk = Math.abs((int)(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() / 14.0D));
    if (this.pk >= 1) this.pk = 1;
    if (this.bDynamoRotary != (this.pk == 1)) {
      this.bDynamoRotary = (this.pk == 1);
      if (this.bDynamoLOperational) {
        hierMesh().chunkVisible("GearL3_D0", !this.bDynamoRotary);
        hierMesh().chunkVisible("GearL3Rot_D0", this.bDynamoRotary);
      }
      if (this.bDynamoROperational) {
        hierMesh().chunkVisible("GearR3_D0", !this.bDynamoRotary);
        hierMesh().chunkVisible("GearR3Rot_D0", this.bDynamoRotary);
      }
    }

    this.dynamoOrient = (this.bDynamoRotary ? (this.dynamoOrient - 17.987F) % 360.0F : (float)(this.dynamoOrient - this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Vwld_of_type_ComMaddoxJGPVector3d.length() * 1.544401526451111D) % 360.0F);
    hierMesh().chunkSetAngles("GearL3_D0", 0.0F, this.dynamoOrient, 0.0F);
    hierMesh().chunkSetAngles("GearR3_D0", 0.0F, this.dynamoOrient + 12.5F, 0.0F);
    super.moveFan(paramFloat);
  }

  protected void moveAirBrake(float paramFloat)
  {
    hierMesh().chunkSetAngles("Brake01_D0", 0.0F, 80.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Brake02_D0", 0.0F, 80.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 5; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, 15.0F - 30.0F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = JU_87D3.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "FlightModel", "FlightModels/Ju-87D-3.fmd");
    Property.set(localClass, "meshName", "3do/plane/Ju-87D-3/hier.him");
    Property.set(localClass, "iconFar_shortClassName", "Ju-87");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU_87D3.class, CockpitJU_87D3_Gunner.class });

    Property.set(localClass, "LOSElevation", 0.8499F);

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 10, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1xSC250", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunSC250 1" });

    Aircraft.weaponsRegister(localClass, "1xSC500", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xSC500_4xSC50", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xSC500_4xSC70", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", null, null, "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xSC500_2xSC250", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500_4xSC50", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500_4xSC70", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", null, null, "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500_2xSC250", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "3xSC250", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1" });

    Aircraft.weaponsRegister(localClass, "1xSC1000", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunSC1000 1" });

    Aircraft.weaponsRegister(localClass, "1xAB1000", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunAB1000 1" });

    Aircraft.weaponsRegister(localClass, "1xPC1600", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunPC1600 1" });

    Aircraft.weaponsRegister(localClass, "1xSC1800", new String[] { "MGunMG17si 500", "MGunMG17si 500", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunSC1800 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}