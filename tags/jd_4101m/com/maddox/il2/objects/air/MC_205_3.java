package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class MC_205_3 extends MC_202xyz
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -88.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -88.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -100.0F * paramFloat, 0.0F);

    paramHierMesh.chunkSetAngles("GearL6_D0", 0.0F, -114.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR6_D0", 0.0F, -114.0F * paramFloat, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -80.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f, 0.0F);

    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, cvt(paramFloat, 0.11F, 0.67F, 0.0F, -38.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC4_D0", 0.0F, cvt(paramFloat, 0.01F, 0.09F, 0.0F, -80.0F), 0.0F);
    paramHierMesh.chunkSetAngles("GearC5_D0", 0.0F, cvt(paramFloat, 0.01F, 0.09F, 0.0F, -80.0F), 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() < 0.65F) return;
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, cvt(paramFloat, -30.0F, 30.0F, 30.0F, -30.0F), 0.0F);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "M.C.205");

    Property.set(localClass, "meshName_it", "3DO/Plane/MC-205_III(it)/hier.him");
    Property.set(localClass, "PaintScheme_it", new PaintSchemeFCSPar02());
    Property.set(localClass, "meshName", "3DO/Plane/MC-205_III(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1948.5F);

    Property.set(localClass, "FlightModel", "FlightModels/MC-205.fmd");
    Property.set(localClass, "cockpitClass", CockpitMC_205.class);
    Property.set(localClass, "LOSElevation", 0.7898F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02" });

    weaponsRegister(localClass, "default", new String[] { "MGunBredaSAFAT127siMC205 370", "MGunBredaSAFAT127siMC205 370", "MGunMG15120k 250", "MGunMG15120k 250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}