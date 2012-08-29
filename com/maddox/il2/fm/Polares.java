package com.maddox.il2.fm;

import com.maddox.rts.HomePath;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

public class Polares extends FMMath
{
  protected float lastAOA = -1.0E+012F;
  protected float lastCx;
  protected float lastCy;
  protected float Flaps;
  float[] normP = new float['ú'];
  float[] maxP = new float['ú'];

  public float AOA_crit = 17.0F;
  public float V_max = 153.0F;
  public float V_min = 35.0F;
  public float P_Vmax = 18000.0F;
  public float G = 26000.0F;
  public float S = 18.0F;
  public float K_max = 14.0F;
  public float Cy0_max = 0.18F;
  public float Tfac = 1.0F;
  public float Vyfac = 1.0F;
  public float FlapsMult = 0.16F;
  public float FlapsAngSh = 4.0F;

  public float Vz_climb = 21.0F;
  public float V_climb = 72.0F;
  public float T_turn = 19.0F;
  public float V_turn = 310.0F;
  public float V_maxFlaps = 180.0F;
  public float V_land = 144.0F;
  public float AOA_land = 12.0F;
  private float Ro = Atmosphere.ro0();
  private float R1000 = Atmosphere.density(1000.0F);
  private float sign = 1.0F;

  private float AOALineH = 13.0F;
  private float AOALineL = -13.0F;
  public float AOACritH = 16.0F;
  public float AOACritL = -16.0F;
  private float parabCyCoeffH = 0.01F;
  private float parabCyCoeffL = 0.01F;

  private float Cy0 = 0.0F;
  public float lineCyCoeff = 0.85F;
  public float declineCoeff = 0.007F;
  public float maxDistAng = 30.0F;
  public float parabAngle = 5.0F;
  private float CyCritH = 1.0F;
  private float CyCritL = -1.0F;

  public float AOAMinCx = -1.0F;
  private float AOAParabH = 6.0F;
  private float AOAParabL = -6.0F;
  private float CxMin = 0.02F;
  private float parabCxCoeff = 0.0008F;

  public float AOACritH_0 = 16.0F;
  public float AOACritL_0 = -16.0F;
  public float Cy0_0 = 0.0F;
  public float CyCritH_0 = 1.0F;
  public float CyCritL_0 = -1.0F;

  public float AOAMinCx_Shift = 0.0F;
  public float CxMin_0 = 0.02F;
  public float parabCxCoeff_0 = 0.0008F;

  public float AOACritH_1 = 16.0F;
  public float AOACritL_1 = -16.0F;
  public float Cy0_1 = 0.0F;
  public float CyCritH_1 = 1.0F;
  public float CyCritL_1 = -1.0F;

  public float CxMin_1 = 0.02F;
  public float parabCxCoeff_1 = 0.0008F;

  public final float new_Cy(float paramFloat)
  {
    if (paramFloat == this.lastAOA) return this.lastCy;
    polares2(paramFloat);
    return this.lastCy;
  }

  public final float new_Cx(float paramFloat) {
    if (paramFloat == this.lastAOA) return this.lastCx;
    polares2(paramFloat);
    return this.lastCx;
  }

  protected final void polares2(float paramFloat)
  {
    float f1 = new_Cxa(paramFloat);
    float f2 = new_Cya(paramFloat);
    float f3 = (float)Math.cos(DEG2RAD(paramFloat));
    float f4 = (float)Math.sin(DEG2RAD(paramFloat));
    this.lastAOA = paramFloat;
    this.lastCx = (f1 * f3 - f2 * f4);
    this.lastCy = (f2 * f3 + f1 * f4);
  }

  public float get_AOA_CRYT()
  {
    return this.AOACritH;
  }

  public final void setFlaps(float paramFloat) {
    float f = 0.25F * paramFloat + 0.75F * (float)Math.sqrt(paramFloat);
    this.Cy0 = (this.Cy0_0 + (this.Cy0_1 - this.Cy0_0) * f);
    this.CyCritH = (this.CyCritH_0 + (this.CyCritH_1 - this.CyCritH_0) * f);
    this.CyCritL = (this.CyCritL_0 + (this.CyCritL_1 - this.CyCritL_0) * f);
    this.AOACritH = (this.AOACritH_0 + (this.AOACritH_1 - this.AOACritH_0) * f);
    this.AOACritL = (this.AOACritL_0 + (this.AOACritL_1 - this.AOACritL_0) * f);
    this.AOALineH = (2.0F * (this.CyCritH - this.Cy0) / this.lineCyCoeff - this.AOACritH);
    this.parabCyCoeffH = (0.5F * this.lineCyCoeff / (this.AOACritH - this.AOALineH));
    this.AOALineL = (2.0F * (this.CyCritL - this.Cy0) / this.lineCyCoeff - this.AOACritL);
    this.parabCyCoeffL = (0.5F * this.lineCyCoeff / (this.AOALineL - this.AOACritL));

    this.CxMin = (this.CxMin_0 + (this.CxMin_1 - this.CxMin_0) * paramFloat);
    this.parabCxCoeff = (this.parabCxCoeff_0 + (this.parabCxCoeff_1 - this.parabCxCoeff_0) * paramFloat);
    this.AOAMinCx = (this.AOAMinCx_Shift - this.Cy0 / this.lineCyCoeff);
  }

  public final void setCxMin_0()
  {
    this.AOAMinCx = (this.AOAMinCx_Shift - this.Cy0_0 / this.lineCyCoeff);
    float f1 = this.S * this.Ro * this.V_max * this.V_max;
    float f2 = 2.0F * this.G / f1;
    float f3 = 2.0F * this.P_Vmax / f1;
    float f4 = (f2 - this.Cy0_0) / this.lineCyCoeff;
    float f5 = f4 - this.AOAMinCx;
    this.CxMin_0 = (f3 - this.parabCxCoeff * f5 * f5);
  }

  public final void setCoeffs(float paramFloat1, float paramFloat2)
  {
    this.lineCyCoeff = ((this.CyCritH * paramFloat1 - this.Cy0) / this.AOACritH);
    this.CyCritL = ((this.Cy0 + this.lineCyCoeff * this.AOACritL) / paramFloat1);
    this.AOAMinCx = (-paramFloat2 * this.Cy0 / this.lineCyCoeff);
    float f1 = this.S * this.Ro * this.V_max * this.V_max;
    float f2 = 2.0F * this.G / f1;
    float f3 = 2.0F * this.P_Vmax / f1;
    float f4 = (f2 - this.Cy0) / this.lineCyCoeff;
    if (this.AOAMinCx > f4) this.AOAMinCx = f4;
    float f5 = f4 - this.AOAMinCx;
    this.AOAMinCx_Shift = (this.AOAMinCx - -this.Cy0 / this.lineCyCoeff);
    this.CxMin = (f3 - this.parabCxCoeff * f5 * f5);

    this.AOALineH = (2.0F * (this.CyCritH - this.Cy0) / this.lineCyCoeff - this.AOACritH);
    this.parabCyCoeffH = (0.5F * this.lineCyCoeff / (this.AOACritH - this.AOALineH));
    this.AOALineL = (2.0F * (this.CyCritL - this.Cy0) / this.lineCyCoeff - this.AOACritL);
    this.parabCyCoeffL = (0.5F * this.lineCyCoeff / (this.AOALineL - this.AOACritL));
  }

  public final void calcPolares()
  {
    this.CyCritH = (2.0F * this.G / (this.S * this.Ro * this.V_min * this.V_min));
    this.AOACritH = this.AOA_crit;

    int i = 0;
    float f1 = 10000.0F;

    float f9 = 0.0F;
    float f10 = 0.0F;
    float f11 = 0.0F;
    float f12 = 0.0F;

    for (int j = 0; j <= 25; j++) {
      this.Cy0 = (0.05F + j * 0.01F);
      if (this.Cy0 <= this.Cy0_max)
        for (int k = 0; k < 200; k++) {
          float f7 = 1.0F + k * 0.006F;
          for (int m = 0; m < 100; m++) {
            this.parabCxCoeff = (0.0003F + m * 2.0E-005F);
            for (int n = 0; n <= 20; n++) {
              float f8 = 1.0F - n * 0.05F;
              setCoeffs(f7, f8);
              if ((this.lineCyCoeff > 0.12F) || 
                (this.CxMin < 0.005F) || 
                (this.AOAMinCx > 0.0F)) continue;
              i = 1;

              float f2 = -10000.0F;
              float f14;
              float f15;
              for (int i1 = 0; i1 < 20; i1++) {
                f14 = 0.5F * i1;
                f15 = new_Cya(f14) / new_Cxa(f14);
                if (f2 >= f15) break; f2 = f15;
              }

              if ((f2 <= 1.3F * this.K_max) && (f2 >= 0.6F * this.K_max)) {
                i = 2;

                float f3 = -10000.0F;
                float f4 = 300.0F;
                float f16;
                float f17;
                float f18;
                float f19;
                for (i1 = 25; i1 < 200; i1++) {
                  f14 = this.S * this.Ro * i1 * i1;
                  f15 = 2.0F * this.G / f14;
                  f16 = (f15 - this.Cy0) / this.lineCyCoeff;
                  f17 = f16 - this.AOAMinCx;
                  f18 = 0.5F * (this.CxMin + this.parabCxCoeff * f17 * f17) * f14;
                  f19 = i1 * (this.normP[i1] - f18) / this.G;
                  if (f3 >= f19) break;
                  f3 = f19;
                  f4 = i1;
                }

                if ((f3 <= f3 + 3.0F) && (f3 >= f3 - 3.0F)) {
                  i = 3;
                  if ((f4 <= 1.3F * this.V_climb) && (f4 >= 0.7F * this.V_climb)) {
                    i = 4;

                    float f5 = 10000.0F;
                    float f6 = 300.0F;
                    for (i1 = 125; i1 > 40; i1--) {
                      f14 = this.S * this.R1000 * i1 * i1;
                      f15 = 2.0F * this.maxP[i1] / f14;
                      if (f15 >= this.CxMin) {
                        f16 = (float)Math.sqrt((f15 - this.CxMin) / this.parabCxCoeff);
                        f17 = this.AOAMinCx + f16;
                        if (f17 > 12.5F) f17 = 12.5F;
                        f18 = 0.5F * new_Cya(f17) * f14 / this.G;
                        f19 = (float)Math.sqrt(f18 * f18 - 1.0F);
                        float f20 = 6.283186F * i1 / (9.8F * f19);
                        if (f20 <= 60.0F) {
                          if (f5 <= f20) break;
                          f5 = f20;
                          f6 = i1;
                        }
                      }
                    }

                    i = 5;

                    i = 6;

                    float f13 = this.K_max - f2;
                    f13 = Math.abs(f13);
                    f14 = this.Vz_climb - f3;
                    f14 = Math.abs(f14);
                    f15 = this.V_climb - f4;
                    f15 = Math.abs(f15);
                    f16 = this.T_turn - f5;
                    f16 = Math.abs(f16);
                    f17 = this.V_turn - f6;
                    f17 = Math.abs(f17);
                    f18 = 2.0F * f13 + 12.0F * f14 + 5.0F * f14 + 15.0F * f16 + 2.0F * f17;
                    if (f1 > f18) {
                      f1 = f18;
                      f9 = f7;
                      f10 = f8;
                      f11 = this.parabCxCoeff;
                      f12 = this.Cy0;
                    }
                  }
                }
              }
            }
          }
        }
    }
    this.Cy0 = f12;
    this.parabCxCoeff = f11;
    setCoeffs(f9, f10);
    this.Cy0_0 = this.Cy0;
    this.AOACritH_0 = this.AOACritH;
    this.AOACritL_0 = this.AOACritL;
    this.CyCritH_0 = this.CyCritH;
    this.CyCritL_0 = this.CyCritL;
    this.CxMin_0 = this.CxMin;
    this.parabCxCoeff_0 = this.parabCxCoeff;
    calcFlaps();
    this.Cy0_1 = this.Cy0;
    this.AOACritH_1 = this.AOACritH;
    this.AOACritL_1 = this.AOACritL;
    this.CyCritH_1 = this.CyCritH;
    this.CyCritL_1 = this.CyCritL;
    this.CxMin_1 = this.CxMin;
    this.parabCxCoeff_1 = this.parabCxCoeff;
  }

  public final void calcFlaps()
  {
    float f1 = this.S * this.Ro * this.V_land * this.V_land;
    float f2 = 2.0F * this.G / f1;
    this.Cy0 = (f2 - this.AOA_land * this.lineCyCoeff);
    this.AOAMinCx = (-this.Cy0 / this.lineCyCoeff + this.AOAMinCx_Shift);
    this.parabCxCoeff *= this.FlapsMult;

    f1 = this.S * this.Ro * this.V_maxFlaps * this.V_maxFlaps;
    f2 = 2.0F * this.G / f1;
    float f3 = 2.0F * this.P_Vmax / f1;
    float f4 = (f2 - this.Cy0) / this.lineCyCoeff;
    float f5 = f4 - this.AOAMinCx;
    this.CxMin = (f3 - this.parabCxCoeff * f5);

    this.AOACritH = (this.AOACritH_0 - 4.0F);
    this.CyCritH = (0.85F * (this.Cy0 + this.AOACritH * this.lineCyCoeff));
    this.AOACritL = (this.AOACritL_0 - this.FlapsAngSh);
    float f6 = -0.9F * (float)Math.sin(-0.03926991F * this.AOACritL);
    this.CyCritL = Math.min(-0.7F, f6);

    this.AOALineH = (2.0F * (this.CyCritH - this.Cy0) / this.lineCyCoeff - this.AOACritH);
    this.parabCyCoeffH = (0.5F * this.lineCyCoeff / (this.AOACritH - this.AOALineH));
    this.AOALineL = (2.0F * (this.CyCritL - this.Cy0) / this.lineCyCoeff - this.AOACritL);
    this.parabCyCoeffL = (0.5F * this.lineCyCoeff / (this.AOALineL - this.AOACritL));
  }

  public float getClimb(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    float f1 = Atmosphere.density(paramFloat2);
    float f2 = this.S * f1 * paramFloat1 * paramFloat1;

    float f3 = 2.0F * this.G / f2;
    float f4 = (f3 - this.Cy0) / this.lineCyCoeff;
    float f5 = f4 - this.AOAMinCx;
    float f6 = 0.5F * (this.CxMin + this.parabCxCoeff * f5 * f5) * f2;
    float f7 = paramFloat1 * (paramFloat3 - f6) / this.G;

    return f7;
  }

  public final void saveCoeffs()
  {
    try {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName("Coeffs.txt", 0))));
      localPrintWriter.println("; ==============================================================================");
      localPrintWriter.println("[Polares]");
      localPrintWriter.println("; ==============================================================================");
      localPrintWriter.println("lineCyCoeff        " + this.lineCyCoeff);
      localPrintWriter.println("AOAMinCx_Shift     " + this.AOAMinCx_Shift);
      localPrintWriter.println("Cy0_0              " + this.Cy0_0);
      localPrintWriter.println("AOACritH_0         " + this.AOACritH_0);
      localPrintWriter.println("AOACritL_0         " + this.AOACritL_0);
      localPrintWriter.println("CyCritH_0          " + this.CyCritH_0);
      localPrintWriter.println("CyCritL_0          " + this.CyCritL_0);
      localPrintWriter.println("CxMin_0            " + this.CxMin_0);
      localPrintWriter.println("parabCxCoeff_0     " + this.parabCxCoeff_0);
      localPrintWriter.println("Cy0_1              " + this.Cy0_1);
      localPrintWriter.println("AOACritH_1         " + this.AOACritH_1);
      localPrintWriter.println("AOACritL_1         " + this.AOACritL_1);
      localPrintWriter.println("CyCritH_1          " + this.CyCritH_1);
      localPrintWriter.println("CyCritL_1          " + this.CyCritL_1);
      localPrintWriter.println("CxMin_1            " + this.CxMin_1);
      localPrintWriter.println("parabCxCoeff_1     " + this.parabCxCoeff_1);
      localPrintWriter.close();
    } catch (IOException localIOException) {
      System.out.println("File save failed: " + localIOException.getMessage());
      localIOException.printStackTrace(); }  } 
  public final void drawGraphs(String paramString) { float f1 = -10000.0F;
    float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F; float f5 = 0.0F;
    int n;
    float f6;
    int m;
    try { PrintWriter localPrintWriter1 = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName("Polar.txt", 0))));
      for (int i = -90; i < 90; i++) localPrintWriter1.print(i + "\t");
      localPrintWriter1.println();
      for (n = 0; n <= 5; n++) {
        setFlaps(n * 0.2F);
        for (i = -90; i < 90; i++) localPrintWriter1.print(new_Cya(i) + "\t");
        localPrintWriter1.println();
        for (int j = -90; j < 90; j++) localPrintWriter1.print(new_Cxa(j) + "\t");
        localPrintWriter1.println();
        if (n == 0) {
          for (int k = -90; k < 90; k++) {
            f6 = new_Cya(k) / new_Cxa(k);
            localPrintWriter1.print(f6 * 0.1F + "\t");
            if (f1 < f6) {
              f1 = f6;
            }
          }
          localPrintWriter1.println();
        }

        for (m = -90; m < 90; m++) {
          f6 = this.Cy0 + this.lineCyCoeff * m;
          if ((f6 < 2.0D) && (f6 > -2.0D)) localPrintWriter1.print(f6 + "\t"); else
            localPrintWriter1.print("\t");
        }
        localPrintWriter1.println();
      }
      localPrintWriter1.close();
    } catch (IOException localIOException1) {
      System.out.println("File save failed: " + localIOException1.getMessage());
      localIOException1.printStackTrace();
    }

    try
    {
      PrintWriter localPrintWriter2 = new PrintWriter(new BufferedWriter(new FileWriter(HomePath.toFileSystemName(paramString, 0))));
      for (m = 120; m < 620; m += 2)
        localPrintWriter2.print(m + "\t");
      localPrintWriter2.println();
      f2 = -10000.0F;
      f3 = 300.0F;
      f4 = 10000.0F;
      f5 = 300.0F;
      for (n = 0; n <= 3; n++) {
        switch (n) { case 0:
          setFlaps(0.0F); break;
        case 1:
          setFlaps(0.2F); break;
        case 2:
          setFlaps(0.33F); break;
        case 3:
          setFlaps(1.0F);
        }
        float f7;
        float f8;
        float f9;
        float f10;
        float f11;
        float f12;
        if (n == 0) {
          for (m = 120; m < 620; m += 2) {
            f6 = m * 0.27778F;
            f7 = this.S * this.Ro * f6 * f6;

            f8 = 2.0F * this.G / f7;
            f9 = getAoAbyCy(f8);

            f10 = f9 - this.AOAMinCx;
            f11 = 0.5F * (this.CxMin + this.parabCxCoeff * f10 * f10) * f7;
            f12 = f6 * (this.normP[(int)f6] - f11) / this.G;
            if ((n == 0) && (f2 < f12)) {
              f2 = f12;
              f3 = f6;
            }
            if (f12 < -10.0F) localPrintWriter2.print("\t"); else
              localPrintWriter2.print(f12 * this.Vyfac + "\t");
          }
          localPrintWriter2.println();
        }

        for (m = 120; m < 620; m += 2) {
          f6 = m * 0.27778F;
          f7 = this.S * this.R1000 * f6 * f6;

          f8 = 2.0F * this.maxP[(int)f6] / f7;
          f9 = (float)Math.sqrt((f8 - this.CxMin) / this.parabCxCoeff);
          f10 = this.AOAMinCx + f9;
          if (f10 > this.AOACritH) f10 = this.AOACritH;
          f11 = 0.5F * new_Cya(f10) * f7 / this.G;
          f12 = (float)Math.sqrt(f11 * f11 - 1.0F);
          float f13 = 0.0F;
          if (f12 > 0.2F) f13 = 6.283186F * f6 / (9.8F * f12);
          if (f13 > 40.0F) f13 = 0.0F;
          if (f13 == 0.0F) localPrintWriter2.print("\t"); else
            localPrintWriter2.print(f13 * this.Tfac + "\t");
          if ((n == 0) && (f13 > 0.0F) && (f4 > f13)) {
            f4 = f13;
            f5 = f6;
          }
        }
        localPrintWriter2.println();
      }

      localPrintWriter2.println("M_takeoff:\t" + this.G / 9.8F);
      localPrintWriter2.println("K_max:\t" + f1);
      localPrintWriter2.println("T_turn:\t" + f4 * this.Tfac);
      localPrintWriter2.println("V_turn:\t" + f5 * 3.6F);
      localPrintWriter2.println("Vz_climb:\t" + f2 * this.Vyfac);
      localPrintWriter2.println("V_climb:\t" + f3 * 3.6F);

      localPrintWriter2.close();
    } catch (IOException localIOException2) {
      System.out.println("File save failed: " + localIOException2.getMessage());
      localIOException2.printStackTrace();
    } }

  public final float getAoAbyCy(float paramFloat)
  {
    if (paramFloat > this.CyCritH) return 90.0F;
    float f1 = (paramFloat - this.Cy0) / this.lineCyCoeff;
    if (f1 <= this.AOALineH) return f1;
    if (f1 >= this.AOACritH) return 90.0F;
    float f2 = this.AOALineH;
    float f3 = this.AOACritH;
    for (int i = 0; i < 1000; i++) {
      float f4 = new_Cya(f1);
      if (Math.abs(f4 - paramFloat) < 0.0001D) {
        return f1;
      }
      if (f4 < paramFloat)
        f2 = f1;
      else {
        f3 = f1;
      }
      f1 = 0.5F * (f2 + f3);
    }
    return f1;
  }

  public final float new_Cya(float paramFloat)
  {
    if ((paramFloat <= this.AOALineH) && (paramFloat >= this.AOALineL))
      return this.Cy0 + this.lineCyCoeff * paramFloat;
    float f2;
    float f3;
    if (paramFloat > 0.0F) {
      if (paramFloat <= this.AOACritH) {
        f1 = this.AOACritH - paramFloat;
        f1 *= f1;
        return this.CyCritH - this.parabCyCoeffH * f1;
      }
      if (paramFloat <= 40.0F) {
        if (paramFloat <= this.maxDistAng) {
          f1 = paramFloat - this.AOACritH;
          if (f1 < this.parabAngle) {
            return this.CyCritH - this.declineCoeff * f1 * f1;
          }
          f2 = 0.9F * (float)Math.sin(0.03926991F * this.maxDistAng);
          f3 = this.maxDistAng - this.parabAngle - this.AOACritH;
          float f4 = this.CyCritH - this.declineCoeff * this.parabAngle * this.parabAngle - f2;
          float f5 = f4 / (f3 * f3);
          f1 = this.maxDistAng - paramFloat;
          return f2 + f5 * f1 * f1;
        }

        return 0.9F * (float)Math.sin(0.03926991F * paramFloat);
      }

      if (paramFloat <= 140.0F) {
        this.sign = 1.0F;
        if (paramFloat > 90.0F) { this.sign = -1.0F; paramFloat = 40.0F + (140.0F - paramFloat); }
        f1 = 0.9F * (float)Math.sin(1.570796F + 0.03141593F * (paramFloat - 40.0F));
        return f1 * this.sign;
      }
      this.sign = -1.0F;
      paramFloat = 180.0F - paramFloat;
      f1 = 0.9F * (float)Math.sin(0.03926991F * paramFloat);
      return f1 * this.sign;
    }

    if (paramFloat >= this.AOACritL) {
      f1 = paramFloat - this.AOACritL;
      f1 *= f1;
      return this.CyCritL + this.parabCyCoeffL * f1;
    }
    if (paramFloat >= -40.0F) {
      f1 = this.AOACritL - paramFloat;
      f2 = this.CyCritL + 0.007F * f1 * f1;
      f3 = -0.9F * (float)Math.sin(-0.03926991F * paramFloat);
      return Math.min(f2, f3);
    }
    paramFloat = -paramFloat;
    if (paramFloat <= 140.0F) {
      this.sign = -1.0F;
      if (paramFloat > 90.0F) { this.sign = 1.0F; paramFloat = 40.0F + (140.0F - paramFloat); }
      f1 = 0.9F * (float)Math.sin(1.570796F + 0.03141593F * (paramFloat - 40.0F));
      return f1 * this.sign;
    }
    this.sign = 1.0F;
    paramFloat = 180.0F - paramFloat;
    float f1 = 0.9F * (float)Math.sin(0.03926991F * paramFloat);
    return f1 * this.sign;
  }

  public final float new_Cxa(float paramFloat)
  {
    float f1 = paramFloat - this.AOAMinCx;
    float f2 = this.CxMin + this.parabCxCoeff * f1 * f1;
    if ((paramFloat <= this.AOAParabH) && (paramFloat >= this.AOAParabL))
      return f2;
    if (paramFloat >= this.AOACritH)
      f2 += 0.03F * (paramFloat - this.AOACritH);
    else if (paramFloat <= this.AOACritL) {
      f2 += 0.03F * (this.AOACritL - paramFloat);
    }
    float f3 = 0.2F + 1.2F * (float)Math.abs(Math.sin(DEG2RAD(paramFloat)));
    return Math.min(f2, f3);
  }

  public final float new_Cz(float paramFloat)
  {
    return 0.7F * (float)Math.sin(DEG2RAD(paramFloat));
  }

  public final float getFlaps()
  {
    return this.Flaps;
  }
}