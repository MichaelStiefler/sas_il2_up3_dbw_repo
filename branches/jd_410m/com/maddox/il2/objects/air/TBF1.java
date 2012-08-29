package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Turret;
import com.maddox.rts.Property;

public class TBF1 extends TBF
{
  protected void moveWingFold(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("WingLMid_D0", 0.0F, -100.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("WingRMid_D0", 0.0F, 100.0F * paramFloat, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -33.0F) { f1 = -33.0F; bool = false; }
      if (f1 > 33.0F) { f1 = 33.0F; bool = false; }
      if (f2 < -3.0F) { f2 = -3.0F; bool = false; }
      if (f2 <= 62.0F) break; f2 = 62.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      this.FM.turret[1].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      break;
    case 2:
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("HMask4_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", hierMesh().isChunkVisible("Pilot3_D0"));
      hierMesh().chunkVisible("Pilot4_D1", hierMesh().isChunkVisible("Pilot4_D0"));
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("Pilot4_D0", false);
    }
  }

  static
  {
    Class localClass = TBF1.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "TBF");
    Property.set(localClass, "meshName", "3DO/Plane/TBF-1(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar01());
    Property.set(localClass, "meshName_us", "3DO/Plane/TBF-1(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeBMPar01());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/TBF-1C.fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 10, 11, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4x100", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1" });

    weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", null, null, null, null });

    weaponsRegister(localClass, "4x250", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, null, null, "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1", "BombGun250lbs 1" });

    weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", null, null, null, null });

    weaponsRegister(localClass, "4x500", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, null, null, "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1", "BombGun500lbs 1" });

    weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, null, "BombGun1000lbs 1", "BombGun1000lbs 1", null, null, null, null });

    weaponsRegister(localClass, "1x1600", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, "BombGun1600lbs", null, null, null, null, null, null });

    weaponsRegister(localClass, "1x2000", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, "BombGun2000lbs", null, null, null, null, null, null });

    weaponsRegister(localClass, "1xmk13", new String[] { "MGunBrowning303si 300", "MGunBrowning50t 400", "MGunBrowning303t 500", null, null, null, null, null, null, null, null, "BombGunTorpMk13", null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}