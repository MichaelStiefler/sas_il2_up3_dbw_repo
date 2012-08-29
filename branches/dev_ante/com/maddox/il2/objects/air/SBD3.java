package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Motor;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class SBD3 extends SBD
  implements TypeStormovik, TypeDiveBomber
{
  private static final float[] angles = { 4.0F, 5.5F, 5.5F, 7.0F, 10.5F, 15.5F, 24.0F, 33.0F, 40.0F, 46.0F, 52.5F, 59.0F, 64.5F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 69.0F, 66.5F, 62.5F, 55.0F, 49.5F, -40.0F, -74.5F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F, -77.0F };

  private float flapps = 0.0F;

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -135.0F) f1 = -135.0F;
      if (f1 > 135.0F) f1 = 135.0F;
      if (f2 < -69.0F) {
        f2 = -69.0F;
        bool = false;
      }
      if (f2 > 45.0F) {
        f2 = 45.0F;
        bool = false;
      }
      float f3 = Math.abs(f1);
      while (f3 > 180.0F) {
        f3 -= 180.0F;
      }
      if (f2 >= -floatindex(Aircraft.cvt(f3, 0.0F, 180.0F, 0.0F, 36.0F), paramArrayOfFloat)) break;
      f2 = -floatindex(Aircraft.cvt(f3, 0.0F, 180.0F, 0.0F, 36.0F), paramArrayOfFloat);
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }
  private static final float floatindex(float paramFloat, float[] paramArrayOfFloat) {
    int i = (int)paramFloat;
    if (i >= paramArrayOfFloat.length - 1) return paramArrayOfFloat[(paramArrayOfFloat.length - 1)];
    if (i < 0) return paramArrayOfFloat[0];
    if (i == 0) {
      if (paramFloat > 0.0F) return paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[1] - paramArrayOfFloat[0]);
      return paramArrayOfFloat[0];
    }
    return paramArrayOfFloat[i] + paramFloat % i * (paramArrayOfFloat[(i + 1)] - paramArrayOfFloat[i]);
  }

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

  public void typeDiveBomberAdjVelocityPlus()
  {
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

  public void update(float paramFloat) {
    super.update(paramFloat);
    float f = this.FM.EI.engines[0].getControlRadiator();
    if (Math.abs(this.flapps - f) > 0.01F) {
      this.flapps = f;
      hierMesh().chunkSetAngles("Oil_D0", 0.0F, -22.0F * f, 0.0F);
      for (int i = 1; i < 7; i++)
        hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -22.0F * f, 0.0F);
    }
  }

  static
  {
    Class localClass = SBD3.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "SBD");
    Property.set(localClass, "meshName", "3DO/Plane/SBD-3(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_us", "3DO/Plane/SBD-3(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar01());

    Property.set(localClass, "yearService", 1942.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/SBD-3.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitSBD3.class, CockpitSBD3_TGunner.class });

    Property.set(localClass, "LOSElevation", 1.1058F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 10, 3, 3, 3 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, null });

    Aircraft.weaponsRegister(localClass, "1x250", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun250lbs" });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun250lbs", "BombGun250lbs", null });

    Aircraft.weaponsRegister(localClass, "3x250", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun250lbs", "BombGun250lbs", "BombGun250lbs" });

    Aircraft.weaponsRegister(localClass, "1x500", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun500lbs" });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500lbs", "BombGun500lbs", null });

    Aircraft.weaponsRegister(localClass, "3x500", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", "BombGun500lbs", "BombGun500lbs", "BombGun500lbs" });

    Aircraft.weaponsRegister(localClass, "1x1000", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun1000lbs" });

    Aircraft.weaponsRegister(localClass, "1x1600", new String[] { "MGunBrowning50s 350", "MGunBrowning50s 350", "MGunBrowning303t 500", "MGunBrowning303t 500", null, null, "BombGun1600lbs" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null });
  }
}