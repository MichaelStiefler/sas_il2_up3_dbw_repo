package com.maddox.il2.objects.air;

import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class B_24J100 extends B_24
  implements TypeBomber
{
  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -85.0F) { f1 = -85.0F; bool = false; }
      if (f1 > 85.0F) { f1 = 85.0F; bool = false; }
      if (f2 < -32.0F) { f2 = -32.0F; bool = false; }
      if (f2 <= 46.0F) break; f2 = 46.0F; bool = false; break;
    case 1:
      if (f2 < -0.0F) { f2 = -0.0F; bool = false; }
      if (f2 <= 20.0F) break; f2 = 20.0F; bool = false; break;
    case 2:
      if (f2 < -70.0F) { f2 = -70.0F; bool = false; }
      if (f2 <= 7.0F) break; f2 = 7.0F; bool = false; break;
    case 3:
      if (f1 < -35.0F) { f1 = -35.0F; bool = false; }
      if (f1 > 64.0F) { f1 = 64.0F; bool = false; }
      if (f2 < -37.0F) { f2 = -37.0F; bool = false; }
      if (f2 <= 50.0F) break; f2 = 50.0F; bool = false; break;
    case 4:
      if (f1 < -67.0F) { f1 = -67.0F; bool = false; }
      if (f1 > 34.0F) { f1 = 34.0F; bool = false; }
      if (f2 < -37.0F) { f2 = -37.0F; bool = false; }
      if (f2 <= 50.0F) break; f2 = 50.0F; bool = false; break;
    case 5:
      if (f1 < -85.0F) { f1 = -85.0F; bool = false; }
      if (f1 > 85.0F) { f1 = 85.0F; bool = false; }
      if (f2 < -32.0F) { f2 = -32.0F; bool = false; }
      if (f2 <= 46.0F) break; f2 = 46.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus() {
  }

  public void typeBomberAdjSideslipMinus() {
  }

  public void typeBomberAdjAltitudeReset() {
  }

  public void typeBomberAdjAltitudePlus() {
  }

  public void typeBomberAdjAltitudeMinus() {
  }

  public void typeBomberAdjSpeedReset() {
  }

  public void typeBomberAdjSpeedPlus() {
  }

  public void typeBomberAdjSpeedMinus() {
  }

  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = B_24J100.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "B-24");
    Property.set(localClass, "meshName", "3DO/Plane/B-24J-100-CF(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/B-24J-100-CF(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1943.5F);
    Property.set(localClass, "yearExpired", 2800.8999F);

    Property.set(localClass, "FlightModel", "FlightModels/B-24J.fmd");

    Aircraft.weaponTriggersRegister(localClass, new int[] { 10, 10, 11, 11, 12, 12, 13, 14, 15, 15, 3, 3, 3, 3, 3, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_MGUN07", "_MGUN08", "_MGUN09", "_MGUN10", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_BombSpawn05", "_BombSpawn06", "_BombSpawn07", "_BombSpawn08" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "16x500", new String[] { "MGunBrowning50t 365", "MGunBrowning50t 365", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 610", "MGunBrowning50t 375", "MGunBrowning50t 375", "MGunBrowning50t 500", "MGunBrowning50t 500", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2", "BombGun500lbs 2" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}