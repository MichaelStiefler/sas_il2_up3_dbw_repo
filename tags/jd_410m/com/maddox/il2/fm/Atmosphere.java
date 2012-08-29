package com.maddox.il2.fm;

import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Engine;

public class Atmosphere
{
  private float g = 9.8F;
  private float P0 = 101300.0F;
  private float T0 = 288.16F;
  private float ro0 = 1.225F;
  private float Mu0 = 1.825E-006F;

  private static final float[] Density = { 1.0F, -9.59387E-005F, 3.53118E-009F, -5.83556E-014F, 2.28719E-019F };
  private static final float[] Pressure = { 1.0F, -0.00011844F, 5.6763E-009F, -1.3738E-013F, 1.60373E-018F };
  private static final float[] Temperature = { 1.0F, -2.27712E-005F, 2.18069E-010F, -5.71104E-014F, 3.97306E-018F };

  public static final float g()
  {
    return World.cur().Atm.g;
  }
  public static final float P0() {
    return World.cur().Atm.P0;
  }
  public static final float T0() {
    return World.cur().Atm.T0;
  }
  public static final float ro0() {
    return World.cur().Atm.ro0;
  }
  public static final float Mu0() {
    return World.cur().Atm.Mu0;
  }

  private static final float poly(float[] paramArrayOfFloat, float paramFloat)
  {
    return (((paramArrayOfFloat[4] * paramFloat + paramArrayOfFloat[3]) * paramFloat + paramArrayOfFloat[2]) * paramFloat + paramArrayOfFloat[1]) * paramFloat + paramArrayOfFloat[0];
  }

  public static final void set(float paramFloat1, float paramFloat2)
  {
    paramFloat1 *= 133.28947F;
    paramFloat2 += 273.16F;
    if ((Engine.cur == null) || (Engine.cur.world == null)) return;
    World.cur().Atm.P0 = paramFloat1;
    World.cur().Atm.T0 = paramFloat2;
    World.cur().Atm.ro0 = (1.225F * (paramFloat1 / 101300.0F) * (288.16F / paramFloat2));
  }

  public static final float pressure(float paramFloat)
  {
    if (paramFloat > 18300.0F) return 18300.0F / paramFloat * P0() * poly(Pressure, 18300.0F);
    return P0() * poly(Pressure, paramFloat);
  }

  public static final float temperature(float paramFloat)
  {
    if (paramFloat > 18300.0F) paramFloat = 18300.0F;
    return T0() * poly(Temperature, paramFloat);
  }

  public static final float sonicSpeed(float paramFloat)
  {
    return 20.1F * (float)Math.sqrt(temperature(paramFloat));
  }

  public static final float density(float paramFloat)
  {
    if (paramFloat > 18300.0F) return 18300.0F / paramFloat * ro0() * poly(Density, 18300.0F);
    return ro0() * poly(Density, paramFloat);
  }

  public static final float viscosity(float paramFloat)
  {
    return Mu0() * (float)Math.pow(temperature(paramFloat) / T0(), 0.76D);
  }

  public static final float kineticViscosity(float paramFloat)
  {
    return viscosity(paramFloat) / density(paramFloat);
  }
}