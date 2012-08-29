package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class FW_190F8 extends FW_190
{
  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);

    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);
  }
  protected void moveGear(float paramFloat) { moveGear(hierMesh(), paramFloat); } 
  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() < 0.98F) return;
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = FW_190F8.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190F-8(Beta)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar03());

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1948.0F);

    Property.set(localClass, "FlightModel", "FlightModels/Fw-190F-8.fmd");
    Property.set(localClass, "cockpitClass", CockpitFW_190F8.class);
    Property.set(localClass, "LOSElevation", 0.764106F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "4sc50", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", null });

    Aircraft.weaponsRegister(localClass, "1sc250", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunSC250" });

    Aircraft.weaponsRegister(localClass, "1sc2504sc50", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC250" });

    Aircraft.weaponsRegister(localClass, "1ab250", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunAB250" });

    Aircraft.weaponsRegister(localClass, "1sc500", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunSC500" });

    Aircraft.weaponsRegister(localClass, "1sc5004sc50", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC50", "BombGunSC500" });

    Aircraft.weaponsRegister(localClass, "1ab500", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunAB500" });

    Aircraft.weaponsRegister(localClass, "1sd500", new String[] { "MGunMG131si 400", "MGunMG131si 400", "MGunMG15120MGs 200", "MGunMG15120MGs 200", null, null, null, null, "BombGunSD500" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}