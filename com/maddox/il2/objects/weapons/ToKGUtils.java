package com.maddox.il2.objects.weapons;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Property;

public class ToKGUtils
{
  public static void setTorpedoGyroAngle(FlightModel paramFlightModel, float paramFloat1, float paramFloat2)
  {
    double d1 = 0.01745329251994328D;
    float f = 0.0F;
    double d2 = 80.0D;
    double d3 = 40.0D;
    double d4 = 21.0D;
    double d5 = 2000.0D;
    double d6 = paramFloat1 * d1;
    double d7 = paramFloat2 * 0.514444D;

    if (paramFlightModel.CT.Weapons[3] != null) {
      for (int i = 0; i < paramFlightModel.CT.Weapons[3].length; i++)
      {
        if ((paramFlightModel.CT.Weapons[3][i] == null) || ((paramFlightModel.CT.Weapons[3][i] instanceof BombGunNull)) || (paramFlightModel.CT.Weapons[3][i].countBullets() == 0))
          continue;
        Class localClass1 = paramFlightModel.CT.Weapons[3][0].getClass();
        Class localClass2 = (Class)Property.value(localClass1, "bulletClass", null);
        d4 = Property.floatValue(localClass2, "velocity", 21.0F);
        d2 = Property.floatValue(localClass2, "dropSpeed", 200.0F) / 3.6D;
        d3 = Property.floatValue(localClass2, "dropAltitude", 40.0F);
        break;
      }

    }

    double d8 = Math.sqrt(2.0D * d3 / Atmosphere.g());

    double d9 = Math.atan2(d7 * d8 * Math.sin(d6), d5 - (d2 + d7 * Math.cos(d6)) * d8);

    double d10 = d9 + d6;

    double d11 = Math.asin(d7 / d4 * Math.sin(d10));

    f = (float)((d11 + d9) / d1);

    paramFlightModel.AS.setGyroAngle(f);
  }

  private static void mydebug(String paramString)
  {
  }
}