package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class JU_87G1 extends JU_87
  implements TypeStormovik
{
  private boolean bDynamoLOperational = true;
  private boolean bDynamoROperational = true;
  private float dynamoOrient = 0.0F;
  private boolean bDynamoRotary = false;
  private int pk;

  public void update(float paramFloat)
  {
    for (int i = 1; i < 5; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, 15.0F - 30.0F * this.FM.EI.engines[0].getControlRadiator(), 0.0F);
    }
    super.update(paramFloat);
  }

  static
  {
    Class localClass = JU_87G1.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "FlightModel", "FlightModels/Ju-87G-1.fmd");
    Property.set(localClass, "meshName", "3do/plane/Ju-87G-1/hier.him");
    Property.set(localClass, "iconFar_shortClassName", "Ju-87");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "cockpitClass", new Class[] { CockpitJU_87D3.class, CockpitJU_87G1_Gunner.class });

    Property.set(localClass, "LOSElevation", 0.8499F);

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 1, 1, 10, 10 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_MGUN01", "_MGUN02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBK37JU87 12", "MGunBK37JU87 12", "MGunMG81t 750", "MGunMG81t 750" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}