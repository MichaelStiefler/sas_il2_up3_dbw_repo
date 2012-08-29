package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.rts.Property;

public class IL_2_1941Late extends IL_2
{
  public void doKillPilot(int paramInt)
  {
    if (paramInt == 1)
      this.FM.turret[0].bIsOperable = false;
  }

  public void doMurderPilot(int paramInt)
  {
    if (paramInt == 1) {
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      hierMesh().chunkVisible("Turret1A_D0", false);
      hierMesh().chunkVisible("Turret1A_D1", false);
      hierMesh().chunkVisible("Turret1B_D0", false);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    if (paramArrayOfFloat[0] < -135.0F) { paramArrayOfFloat[0] = -135.0F; bool = false;
    } else if (paramArrayOfFloat[0] > 135.0F) { paramArrayOfFloat[0] = 135.0F; bool = false; }
    float f1 = Math.abs(paramArrayOfFloat[0]);
    if (f1 < 20.0F) {
      if (paramArrayOfFloat[1] < -10.0F) { paramArrayOfFloat[1] = -10.0F; bool = false; }
    }
    else if (paramArrayOfFloat[1] < -15.0F) { paramArrayOfFloat[1] = -15.0F; bool = false;
    }
    if (paramArrayOfFloat[1] > 45.0F) { paramArrayOfFloat[1] = 45.0F; bool = false; }
    if (!bool) return false;

    float f2 = paramArrayOfFloat[1];
    if ((f1 < 2.0F) && (f2 < 17.0F)) return false;
    if (f2 > -5.0F) return true;
    if (f2 > -12.0F) {
      f2 += 12.0F;
      return f1 > 12.0F + f2 * 2.571429F;
    }

    f2 = -f2;
    return f1 > f2;
  }

  static
  {
    Class localClass = IL_2_1941Late.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "IL2");
    Property.set(localClass, "meshName", "3do/plane/Il-2-1941Late(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "meshName_ru", "3do/plane/Il-2-1941Late/hier.him");
    Property.set(localClass, "PaintScheme_ru", new PaintSchemeBCSPar02());

    Property.set(localClass, "yearService", 1941.2F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Il-2-1941.fmd");
    Property.set(localClass, "cockpitClass", CockpitIL_2_1940.class);
    Property.set(localClass, "LOSElevation", 0.81F);
    Property.set(localClass, "Handicap", 1.0F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 9, 9, 9, 9, 9, 9, 3, 3, 10, 10 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_Cannon01", "_Cannon02", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalRock07", "_ExternalRock08", "_ExternalBomb01", "_ExternalBomb02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalDev04", "_ExternalDev05", "_ExternalDev06", "_BombSpawn01", "_BombSpawn02", "_MGUN03", "_MGUN04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "8xRS82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "8xBRS82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", "RocketGunBRS82", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "8xRS132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "8xBRS132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", "RocketGunBRS132", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "8xM13", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", "RocketGunM13", null, null, null, null, null, null, null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "216xAJ-2", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, "BombGunAmpoule", "BombGunAmpoule", "BombGunAmpoule", "BombGunAmpoule", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "30xAO10", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, "BombGunAO10 7", "BombGunAO10 7", "BombGunAO10 8", "BombGunAO10 8", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "50xAO10", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, "BombGunAO10 12", "BombGunAO10 12", "BombGunAO10 13", "BombGunAO10 13", "PylonKMB", "PylonKMB", "PylonKMB", "PylonKMB", null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "4xFAB50", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "4xFAB50_8xRS82", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", "RocketGunRS82", null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "4xFAB50_8xRS132", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", "RocketGunRS132", null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "6xFAB50", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", "BombGunFAB50", null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "4xFAB100", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "6xFAB100", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", "BombGunFAB100", null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "2xFAB250", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, "BombGunFAB250", "BombGunFAB250", null, null, null, null, null, null, null, null, null, null, null, null, "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "2xVAP250", new String[] { "MGunShKASi 750", "MGunShKASi 750", "MGunVYak 300", "MGunVYak 300", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "PylonVAP250", "PylonVAP250", "BombGunPhBall", "BombGunPhBall", "MGunShKASt 500", "MGunShKASt 500" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}