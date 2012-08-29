package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class IAR_81A extends IAR_8X
  implements TypeFighter, TypeStormovik, TypeDiveBomber
{
  public boolean typeDiveBomberToggleAutomation()
  {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset()
  {
  }

  public void typeDiveBomberAdjAltitudePlus()
  {
  }

  public void typeDiveBomberAdjAltitudeMinus()
  {
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus() {
  }

  public void typeDiveBomberAdjVelocityMinus() {
  }

  public void typeDiveBomberAdjDiveAngleReset() {
  }

  public void typeDiveBomberAdjDiveAnglePlus() {
  }

  public void typeDiveBomberAdjDiveAngleMinus() {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = IAR_81A.class;
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "IAR 80");
    Property.set(localClass, "meshName", "3DO/Plane/IAR-81a/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/IAR-81a.fmd");
    Property.set(localClass, "cockpitClass", CockpitIAR81.class);
    Property.set(localClass, "LOSElevation", 0.8323F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 1, 1, 3, 3, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });

    weaponsRegister(localClass, "default", new String[] { "MGunBrowning303k 1000", "MGunBrowning303k 1000", "MGunBrowning303ki 1000", "MGunBrowning303ki 1000", "MGunMG131k 350", "MGunMG131k 350", null, null, null, null, null });

    weaponsRegister(localClass, "2xSC50", new String[] { "MGunBrowning303k 1000", "MGunBrowning303k 1000", "MGunBrowning303ki 1000", "MGunBrowning303ki 1000", "MGunMG131k 350", "MGunMG131k 350", "BombGunSC50", "BombGunSC50", null, null, null });

    weaponsRegister(localClass, "1xSC250", new String[] { "MGunBrowning303k 1000", "MGunBrowning303k 1000", "MGunBrowning303ki 1000", "MGunBrowning303ki 1000", "MGunMG131k 350", "MGunMG131k 350", null, null, null, null, "BombGunSC250" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}