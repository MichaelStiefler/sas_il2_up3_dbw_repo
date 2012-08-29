package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Property;

public class JU_87D5 extends JU_87
  implements TypeStormovik
{
  public float fDiveVelocity = 500.0F;
  public float fDiveAngle = 70.0F;

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

    this.fDiveAngle = (-this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Or.getTangage());
    if (this.fDiveAngle > 89.0F) {
      this.fDiveAngle = 89.0F;
    }
    if (this.fDiveAngle < 10.0F) {
      this.fDiveAngle = 10.0F;
    }

    super.update(paramFloat);
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus()
  {
    this.fDiveVelocity += 10.0F;
    if (this.fDiveVelocity > 700.0F) {
      this.fDiveVelocity = 700.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fDiveVelocity) });
  }

  public void typeDiveBomberAdjVelocityMinus() {
    this.fDiveVelocity -= 10.0F;
    if (this.fDiveVelocity < 150.0F) {
      this.fDiveVelocity = 150.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { new Integer((int)this.fDiveVelocity) });
  }

  public void typeDiveBomberAdjDiveAngleReset()
  {
  }

  public void typeDiveBomberAdjDiveAnglePlus()
  {
  }

  public void typeDiveBomberAdjDiveAngleMinus()
  {
  }

  static
  {
    Class localClass = JU_87D5.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "FlightModel", "FlightModels/Ju-87D-5.fmd");
    Property.set(localClass, "meshName", "3DO/Plane/Ju-87D-5/hier.him");
    Property.set(localClass, "iconFar_shortClassName", "Ju-87");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU_87D5.class, CockpitJU_87D3_Gunner.class });

    Property.set(localClass, "LOSElevation", 0.8499F);

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 10, 10, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05", "_ExternalBomb06", "_ExternalBomb07" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "1xSC250", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunSC250 1" });

    Aircraft.weaponsRegister(localClass, "1xSC500", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xSC500_4xSC50", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xSC500_4xSC70", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", null, null, "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xSC500_2xSC250", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500_4xSC50", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", "BombGunSC50 1", null, null, "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500_4xSC70", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", "BombGunSC70 1", null, null, "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "1xAB500_2xSC250", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunAB500 1" });

    Aircraft.weaponsRegister(localClass, "3xSC250", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, "BombGunSC250 1", "BombGunSC250 1", "BombGunSC250 1" });

    Aircraft.weaponsRegister(localClass, "1xSC1000", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunSC1000 1" });

    Aircraft.weaponsRegister(localClass, "1xAB1000", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunAB1000 1" });

    Aircraft.weaponsRegister(localClass, "1xPC1600", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunPC1600 1" });

    Aircraft.weaponsRegister(localClass, "1xSC1800", new String[] { "MGunMG15120si 250", "MGunMG15120si 250", "MGunMG81t 750", "MGunMG81t 750", null, null, null, null, null, null, "BombGunSC1800 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}