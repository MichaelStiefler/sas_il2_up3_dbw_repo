package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class JU_87G2RUDEL extends JU_87
  implements TypeStormovik, TypeAcePlane
{
  private boolean bDynamoLOperational = true;
  private boolean bDynamoROperational = true;
  private float dynamoOrient = 0.0F;
  private boolean bDynamoRotary = false;
  private int pk;

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.Skill = 3;
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 5; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, 15.0F - 30.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = JU_87G2RUDEL.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Ju-87");
    Property.set(localClass, "meshName", "3do/plane/Ju-87G-2(ofRudel)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeSpecial());

    Property.set(localClass, "FlightModel", "FlightModels/Ju-87G-2(ofRudel).fmd");

    weaponTriggersRegister(localClass, new int[] { 1, 1, 10, 10 });
    weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBK37JU87 12", "MGunBK37JU87 12", "MGunMG81t 250", "MGunMG81t 250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}