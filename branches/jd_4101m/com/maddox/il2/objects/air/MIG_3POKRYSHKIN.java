package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.Property;

public class MIG_3POKRYSHKIN extends MIG_3
  implements TypeAcePlane
{
  private float kangle = 0.0F;

  public void update(float paramFloat) { if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 0.9F), 0.0F, 0.0F);
      hierMesh().chunkSetAngles("SlatR_D0", cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 0.9F), 0.0F, 0.0F);
    }
    hierMesh().chunkSetAngles("WaterFlap_D0", 0.0F, 30.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("OilRad1_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("OilRad2_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    this.FM.Skill = 3;
  }

  static
  {
    Class localClass = MIG_3POKRYSHKIN.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "MiG");
    Property.set(localClass, "meshName", "3do/plane/MIG-3(ofPokryshkin)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeSpecial());

    Property.set(localClass, "FlightModel", "FlightModels/MiG-3(ofPokryshkin).fmd");

    weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 1, 3, 3, 3, 3, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06" });

    weaponsRegister(localClass, "default", new String[] { "MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xBK", new String[] { "MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", "MGunUBk 145", "MGunUBk 145", null, null, null, null, "PylonMiG_3_BK", "PylonMiG_3_BK", null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "6xRS-82", new String[] { "MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null, null, null, null, null, null, null, null, "PylonRO_82_3", "PylonRO_82_3", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1", "RocketGunRS82 1" });

    weaponsRegister(localClass, "4xFAB-50", new String[] { "MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null, null, "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", "BombGunFAB50 1", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "2xFAB-100", new String[] { "MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null, null, null, "BombGunFAB100 1", "BombGunFAB100 1", null, null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "4xAO-10", new String[] { "MGunShKASs 1450", "MGunShKASs 1450", "MGunUBk 600", null, null, "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", "BombGunAO10 1", null, null, null, null, null, null, null, null, null, null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}