package com.maddox.il2.fm;

import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.ai.World;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.BombGunSC50;
import com.maddox.il2.objects.weapons.BombGunSC70;
import com.maddox.il2.objects.weapons.FuelTankGun;
import com.maddox.il2.objects.weapons.Pylon;
import com.maddox.il2.objects.weapons.PylonMG15120Internal;
import com.maddox.il2.objects.weapons.PylonP38RAIL3FL;
import com.maddox.il2.objects.weapons.PylonP38RAIL3FR;
import com.maddox.il2.objects.weapons.PylonP38RAIL3WL;
import com.maddox.il2.objects.weapons.PylonP38RAIL3WR;
import com.maddox.il2.objects.weapons.PylonP38RAIL5;
import com.maddox.il2.objects.weapons.PylonP38RAILS;
import com.maddox.il2.objects.weapons.PylonPE8_FAB100;
import com.maddox.il2.objects.weapons.PylonPE8_FAB250;
import com.maddox.il2.objects.weapons.PylonRO_82_1;
import com.maddox.il2.objects.weapons.PylonRO_82_3;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.il2.objects.weapons.RocketGunR4M;
import com.maddox.rts.SectFile;
import java.io.PrintStream;

public class Squares
{
  public float squareWing;
  public float squareAilerons;
  public float squareElevators;
  public float squareRudders;
  public float squareFlaps;
  public float liftWingLIn;
  public float liftWingLMid;
  public float liftWingLOut;
  public float liftWingRIn;
  public float liftWingRMid;
  public float liftWingROut;
  public float liftStab;
  public float liftKeel;
  public float[] dragEngineCx = { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F };
  public float dragParasiteCx = 0.0F;
  public float dragAirbrakeCx = 0.0F;
  public float dragFuselageCx = 0.0F;
  public float dragProducedCx = 0.0F;
  float spinCxloss;
  float spinCyloss;
  public float[] toughness = new float[44];
  public float[] eAbsorber = new float[44];

  public final float dragSmallHole = 0.06F;
  public final float dragBigHole = 0.12F;
  public final float wingSmallHole = 0.4F;
  public final float wingBigHole = 0.8F;

  public void load(SectFile paramSectFile)
  {
    String str2 = "Zero Square processed from " + paramSectFile.toString();

    String str1 = "Squares";

    float f1 = paramSectFile.get(str1, "Wing", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.squareWing = f1;
    f1 = paramSectFile.get(str1, "Aileron", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.squareAilerons = f1;
    f1 = paramSectFile.get(str1, "Flap", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.squareFlaps = f1;
    f1 = paramSectFile.get(str1, "Stabilizer", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.liftStab = f1;
    f1 = paramSectFile.get(str1, "Elevator", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.squareElevators = f1;
    f1 = paramSectFile.get(str1, "Keel", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.liftKeel = f1;
    f1 = paramSectFile.get(str1, "Rudder", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.squareRudders = f1;
    f1 = paramSectFile.get(str1, "Wing_In", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.liftWingLIn = (this.liftWingRIn = f1);
    f1 = paramSectFile.get(str1, "Wing_Mid", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.liftWingLMid = (this.liftWingRMid = f1);
    f1 = paramSectFile.get(str1, "Wing_Out", 0.0F); if (f1 == 0.0F) throw new RuntimeException(str2);
    this.liftWingLOut = (this.liftWingROut = f1);
    f1 = paramSectFile.get(str1, "AirbrakeCxS", -1.0F); if (f1 == -1.0F) throw new RuntimeException(str2);
    this.dragAirbrakeCx = f1;
    f1 = paramSectFile.get("Params", "SpinCxLoss", -1.0F); if (f1 == -1.0F) throw new RuntimeException(str2);
    this.spinCxloss = f1;
    f1 = paramSectFile.get("Params", "SpinCyLoss", -1.0F); if (f1 == -1.0F) throw new RuntimeException(str2);
    this.spinCyloss = f1;

    for (int i = 0; i < 8; i++) this.dragEngineCx[i] = 0.0F;

    str1 = "Toughness";

    for (int j = 0; j < 44; j++) {
      this.toughness[j] = (paramSectFile.get(str1, com.maddox.il2.objects.air.Aircraft.partNames()[j], 100) * 1.0E-004F);
    }

    this.toughness[43] = 3.4028235E+38F;

    float f2 = 2.0F * (this.liftWingLIn + this.liftWingLMid + this.liftWingLOut) / (this.squareWing + 0.01F);
    if ((f2 < 0.9F) || (f2 > 1.1F)) {
      if (World.cur().isDebugFM()) System.out.println("Error in flightmodel " + paramSectFile.toString() + ": (wing square) != (sum of squares*2)");
      if (f2 > 1.0F)
        this.squareWing = (2.0F * (this.liftWingLIn + this.liftWingLMid + this.liftWingLOut));
      else
        this.liftWingLIn = (this.liftWingLMid = this.liftWingLOut = this.liftWingRIn = this.liftWingRMid = this.liftWingROut = 0.166667F * this.squareWing);
    }
  }

  public float getToughness(int paramInt)
  {
    return this.toughness[paramInt];
  }

  public void computeParasiteDrag(Controls paramControls, BulletEmitter[][] paramArrayOfBulletEmitter)
  {
    this.dragParasiteCx = 0.0F;

    for (int i = 0; i < paramArrayOfBulletEmitter.length; i++) {
      if ((paramArrayOfBulletEmitter[i] == null) || (paramArrayOfBulletEmitter[i].length <= 0)) continue; for (int j = 0; j < paramArrayOfBulletEmitter[i].length; j++) {
        if (((paramArrayOfBulletEmitter[i][j] instanceof BombGun)) && (paramArrayOfBulletEmitter[i][j].haveBullets()) && 
          (paramArrayOfBulletEmitter[i][j].getHookName().startsWith("_External")) && 
          (this.dragParasiteCx < 0.704F)) {
          if (((paramArrayOfBulletEmitter[i][j] instanceof BombGunSC50)) || ((paramArrayOfBulletEmitter[i][j] instanceof BombGunSC70)) || ((paramArrayOfBulletEmitter[i][j] instanceof FuelTankGun)))
            this.dragParasiteCx += 0.02F;
          else {
            this.dragParasiteCx += 0.06F;
          }

        }

        if (((paramArrayOfBulletEmitter[i][j] instanceof RocketGun)) && (paramArrayOfBulletEmitter[i][j].haveBullets()) && (!(paramArrayOfBulletEmitter[i][j] instanceof RocketGunR4M)))
        {
          this.dragParasiteCx += 0.02F;
        }

        if ((!(paramArrayOfBulletEmitter[i][j] instanceof Pylon)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_82_1)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_82_3)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonPE8_FAB100)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonPE8_FAB250)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL3FL)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL3FR)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL3WL)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL3WR)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL5)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAILS)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonMG15120Internal)))
        {
          continue;
        }

        this.dragParasiteCx += 0.035F;
      }

    }

    this.dragParasiteCx += 0.02F * paramControls.getCockpitDoor();
  }
}