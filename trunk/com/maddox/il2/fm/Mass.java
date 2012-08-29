package com.maddox.il2.fm;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.BulletEmitter;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.GunGeneric;
import com.maddox.il2.objects.weapons.BombGun;
import com.maddox.il2.objects.weapons.FuelTank;
import com.maddox.il2.objects.weapons.Pylon;
import com.maddox.il2.objects.weapons.PylonA5MPLN1;
import com.maddox.il2.objects.weapons.PylonA6MPLN1;
import com.maddox.il2.objects.weapons.PylonA6MPLN2;
import com.maddox.il2.objects.weapons.PylonB25PLN2;
import com.maddox.il2.objects.weapons.PylonB25RAIL;
import com.maddox.il2.objects.weapons.PylonB5NPLN0;
import com.maddox.il2.objects.weapons.PylonB5NPLN1;
import com.maddox.il2.objects.weapons.PylonB5NPLN2;
import com.maddox.il2.objects.weapons.PylonB5NPLN3;
import com.maddox.il2.objects.weapons.PylonB6NPLN1;
import com.maddox.il2.objects.weapons.PylonBEAUPLN1;
import com.maddox.il2.objects.weapons.PylonBEAUPLN2;
import com.maddox.il2.objects.weapons.PylonBEAUPLN3;
import com.maddox.il2.objects.weapons.PylonBEAUPLN4;
import com.maddox.il2.objects.weapons.PylonBF110R3;
import com.maddox.il2.objects.weapons.PylonBF110R4;
import com.maddox.il2.objects.weapons.PylonDer16TB3Fake;
import com.maddox.il2.objects.weapons.PylonETC250;
import com.maddox.il2.objects.weapons.PylonETC50;
import com.maddox.il2.objects.weapons.PylonETC501FW190;
import com.maddox.il2.objects.weapons.PylonETC50Bf109;
import com.maddox.il2.objects.weapons.PylonETC71;
import com.maddox.il2.objects.weapons.PylonETC900;
import com.maddox.il2.objects.weapons.PylonF4FPLN1;
import com.maddox.il2.objects.weapons.PylonF4FPLN2;
import com.maddox.il2.objects.weapons.PylonF4UPLN2;
import com.maddox.il2.objects.weapons.PylonF4UPLN3;
import com.maddox.il2.objects.weapons.PylonF6FPLN1;
import com.maddox.il2.objects.weapons.PylonF6FPLN2;
import com.maddox.il2.objects.weapons.PylonHS129BK37;
import com.maddox.il2.objects.weapons.PylonHS129BK75;
import com.maddox.il2.objects.weapons.PylonHS129MG17S;
import com.maddox.il2.objects.weapons.PylonHS129MK101;
import com.maddox.il2.objects.weapons.PylonHS129MK103;
import com.maddox.il2.objects.weapons.PylonHs129BombRackC250;
import com.maddox.il2.objects.weapons.PylonHs129BombRackC4x50;
import com.maddox.il2.objects.weapons.PylonHs129BombRackL;
import com.maddox.il2.objects.weapons.PylonHs129BombRackR;
import com.maddox.il2.objects.weapons.PylonKI43PLN1;
import com.maddox.il2.objects.weapons.PylonKI84PLN2;
import com.maddox.il2.objects.weapons.PylonKMB;
import com.maddox.il2.objects.weapons.PylonMG15120;
import com.maddox.il2.objects.weapons.PylonMG15120Internal;
import com.maddox.il2.objects.weapons.PylonMG15120x2;
import com.maddox.il2.objects.weapons.PylonMe262_R4M_Left;
import com.maddox.il2.objects.weapons.PylonMe262_R4M_Right;
import com.maddox.il2.objects.weapons.PylonMiG_3_BK;
import com.maddox.il2.objects.weapons.PylonMk103;
import com.maddox.il2.objects.weapons.PylonMk108;
import com.maddox.il2.objects.weapons.PylonN1K1PLN1;
import com.maddox.il2.objects.weapons.PylonP38GUNPOD;
import com.maddox.il2.objects.weapons.PylonP38RAIL3FL;
import com.maddox.il2.objects.weapons.PylonP38RAIL3FR;
import com.maddox.il2.objects.weapons.PylonP38RAIL3WL;
import com.maddox.il2.objects.weapons.PylonP38RAIL3WR;
import com.maddox.il2.objects.weapons.PylonP38RAIL5;
import com.maddox.il2.objects.weapons.PylonP39PLN1;
import com.maddox.il2.objects.weapons.PylonP51PLN2;
import com.maddox.il2.objects.weapons.PylonP63CGUNPOD;
import com.maddox.il2.objects.weapons.PylonP63CPLN2;
import com.maddox.il2.objects.weapons.PylonPE8_FAB100;
import com.maddox.il2.objects.weapons.PylonPE8_FAB250;
import com.maddox.il2.objects.weapons.PylonR5BombRackC;
import com.maddox.il2.objects.weapons.PylonR5BombRackL;
import com.maddox.il2.objects.weapons.PylonRO_4andHalfInch_3;
import com.maddox.il2.objects.weapons.PylonRO_82_1;
import com.maddox.il2.objects.weapons.PylonRO_82_3;
import com.maddox.il2.objects.weapons.PylonRO_WfrGr21;
import com.maddox.il2.objects.weapons.PylonRO_WfrGr21Dual;
import com.maddox.il2.objects.weapons.PylonS328;
import com.maddox.il2.objects.weapons.PylonSpitC;
import com.maddox.il2.objects.weapons.PylonSpitL;
import com.maddox.il2.objects.weapons.PylonSpitR;
import com.maddox.il2.objects.weapons.PylonSpitROCK;
import com.maddox.il2.objects.weapons.PylonTEMPESTPLN1;
import com.maddox.il2.objects.weapons.PylonTEMPESTPLN2;
import com.maddox.il2.objects.weapons.PylonTEMPESTPLN3;
import com.maddox.il2.objects.weapons.PylonTEMPESTPLN4;
import com.maddox.il2.objects.weapons.PylonVAP250;
import com.maddox.il2.objects.weapons.RocketBombGun;
import com.maddox.il2.objects.weapons.RocketGun;
import com.maddox.rts.SectFile;

public class Mass
{
  private FlightModelMain FM;
  public float massEmpty;
  public float mass;
  public float maxWeight;
  private float parasiteMass;
  private float parasiteJx;
  public float fuel;
  public float maxFuel;
  private FuelTank[] fuelTanks;
  private boolean bFuelTanksLoaded = false;
  public float nitro = 0.0F;
  public float maxNitro = 1.0F;
  public float referenceWeight;
  public float pylonCoeff = 0.0F;
  private float pylonCoeffB = 0.0F;
  private float pylonCoeffR = 0.0F;
  public float fuelCoeff = 0.0F;

  public void load(SectFile paramSectFile, FlightModelMain paramFlightModelMain)
  {
    String str1 = "Mass"; String str2 = "Critical Mass in " + paramSectFile.toString();

    this.FM = paramFlightModelMain;

    float f = paramSectFile.get(str1, "Empty", 0.0F); if (f == 0.0F) throw new RuntimeException(str2);
    this.massEmpty = f;
    f = paramSectFile.get(str1, "Oil", -1.0F); if (f == -1.0F) throw new RuntimeException(str2);
    this.massEmpty += f;
    f = paramSectFile.get("Aircraft", "Crew", 0.0F); if (f == 0.0F) throw new RuntimeException(str2);
    this.massEmpty += f * 90.0F;

    this.referenceWeight = this.massEmpty;

    f = paramSectFile.get(str1, "TakeOff", 0.0F); if (f == 0.0F) throw new RuntimeException(str2);
    this.maxWeight = f;
    f = paramSectFile.get(str1, "Fuel", 0.0F); if (f == 0.0F) throw new RuntimeException(str2);
    this.maxFuel = f;

    this.mass = this.massEmpty;
    this.fuel = this.maxFuel;

    f = paramSectFile.get(str1, "Nitro", 0.0F);
    this.maxNitro = (this.nitro = f);

    this.referenceWeight += this.fuel + this.maxNitro;
  }

  public void onFuelTanksChanged()
  {
    this.bFuelTanksLoaded = true;
    this.fuelTanks = new FuelTank[0];
  }

  public boolean requestFuel(float paramFloat)
  {
    this.mass = (this.massEmpty + this.fuel + this.nitro + this.parasiteMass);

    if (!this.bFuelTanksLoaded) {
      this.fuelTanks = this.FM.CT.getFuelTanks();
      this.bFuelTanksLoaded = true;
    }

    if (this.fuelTanks.length != 0)
    {
      this.fuelCoeff = 1.0F;
      float f = 0.0F;
      for (int i = 0; i < this.fuelTanks.length; i++) f += this.fuelTanks[i].getFuel(paramFloat / this.fuelTanks.length);
      if (f > 0.0F) return true; 
    }
    else {
      this.fuelCoeff = 0.0F;
    }
    this.fuel -= paramFloat;
    if (this.fuel < 0.0F) {
      this.fuel = 0.0F;
      return false;
    }
    return true;
  }

  public boolean requestNitro(float paramFloat)
  {
    this.mass = (this.massEmpty + this.fuel + this.nitro + this.parasiteMass);
    this.nitro -= paramFloat;
    if (this.nitro < 0.0F) {
      this.nitro = 0.0F;
      return false;
    }
    return true;
  }

  public float getFullMass()
  {
    return this.mass;
  }

  public void computeParasiteMass(BulletEmitter[][] paramArrayOfBulletEmitter)
  {
    this.parasiteMass = 0.0F; this.parasiteJx = 0.0F;

    for (int i = 0; i < paramArrayOfBulletEmitter.length; i++) {
      if ((paramArrayOfBulletEmitter[i] == null) || (paramArrayOfBulletEmitter[i].length <= 0)) continue; for (int j = 0; j < paramArrayOfBulletEmitter[i].length; j++)
      {
        int k;
        float f1;
        float f2;
        float f3;
        if ((paramArrayOfBulletEmitter[i][j] instanceof GunGeneric)) {
          k = paramArrayOfBulletEmitter[i][j].countBullets();
          f1 = paramArrayOfBulletEmitter[i][j].bulletMassa() * (k >= 0 ? k : 50) * 3.0F;
          f2 = (float)((Actor)paramArrayOfBulletEmitter[i][j]).pos.getRelPoint().z;
          f3 = (float)((Actor)paramArrayOfBulletEmitter[i][j]).pos.getRelPoint().y;
          this.parasiteJx += (f2 * f2 + f3 * f3) * f1;
          this.parasiteMass += f1;
        }

        if (((paramArrayOfBulletEmitter[i][j] instanceof BombGun)) || ((paramArrayOfBulletEmitter[i][j] instanceof RocketGun)) || ((paramArrayOfBulletEmitter[i][j] instanceof RocketBombGun)))
        {
          k = paramArrayOfBulletEmitter[i][j].countBullets();
          f1 = paramArrayOfBulletEmitter[i][j].bulletMassa() * (k >= 0 ? k : 1);
          f2 = 0.0F;
          f3 = 2.0F;

          if (k > 0)
          {
            if ((((paramArrayOfBulletEmitter[i][j] instanceof BombGun)) || ((paramArrayOfBulletEmitter[i][j] instanceof RocketBombGun))) && (paramArrayOfBulletEmitter[i][j].getHookName().startsWith("_External")))
              this.pylonCoeffB = 1.5F;
            else {
              this.pylonCoeffR = 1.0F;
            }
          }
          this.pylonCoeff = (this.pylonCoeffB + this.pylonCoeffR);

          this.parasiteJx += (f2 * f2 + f3 * f3) * f1;
          this.parasiteMass += f1;
        }

        if ((paramArrayOfBulletEmitter[i][j] instanceof Pylon)) {
          if (((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_82_1)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_82_3)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonPE8_FAB100)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonPE8_FAB250)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonMG15120Internal)))
          {
            f1 = 0.0F;
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonB25RAIL))
            f1 = 5.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonBEAUPLN2))
            f1 = 108.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonBEAUPLN3))
            f1 = 108.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonF4FPLN1))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonF6FPLN2))
            f1 = 5.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL3FL))
            f1 = 36.299999F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL3FR))
            f1 = 36.299999F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL3WL))
            f1 = 36.299999F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL3WR))
            f1 = 36.299999F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38RAIL5))
            f1 = 50.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_4andHalfInch_3)) {
            f1 = 36.299999F;
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_WfrGr21))
            f1 = 37.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_WfrGr21Dual))
            f1 = 70.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonSpitL))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonSpitR))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonSpitROCK))
            f1 = 5.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonTEMPESTPLN3))
            f1 = 108.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonTEMPESTPLN4))
            f1 = 108.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonA5MPLN1))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonA6MPLN2))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonETC71))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonF4FPLN2))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonS328))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHs129BombRackL))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHs129BombRackR))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonR5BombRackC))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonR5BombRackL))
            f1 = 10.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonR5BombRackL)) {
            f1 = 10.0F;
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonETC50))
            f1 = 40.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonETC50Bf109))
            f1 = 40.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonETC501FW190))
            f1 = 60.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP51PLN2))
            f1 = 9.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP63CPLN2))
            f1 = 7.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonSpitC))
            f1 = 23.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonA6MPLN1))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonB25PLN2))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonBEAUPLN1))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonETC250))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonETC900))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonF6FPLN1))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonKI43PLN1))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonKI84PLN2))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonKMB))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonMe262_R4M_Left))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonMe262_R4M_Right))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonN1K1PLN1))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP39PLN1))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonTEMPESTPLN1))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonTEMPESTPLN2))
            f1 = 30.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHs129BombRackC250))
            f1 = 50.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHs129BombRackC4x50)) {
            f1 = 50.0F;
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonB5NPLN1))
            f1 = 70.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonB5NPLN2))
            f1 = 70.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonB5NPLN3))
            f1 = 70.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonB6NPLN1))
            f1 = 70.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonF4UPLN2))
            f1 = 70.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonF4UPLN3)) {
            f1 = 70.0F;
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonB5NPLN0))
            f1 = 150.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonBEAUPLN4))
            f1 = 150.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonDer16TB3Fake)) {
            f1 = 150.0F;
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonVAP250)) {
            f1 = 150.0F;
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonBF110R3))
            f1 = 150.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonBF110R4)) {
            f1 = 350.0F;
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonMG15120x2))
            f1 = 100.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHS129BK37))
            f1 = 350.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHS129MG17S))
            f1 = 80.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHS129MK101))
            f1 = 250.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHS129MK103))
            f1 = 196.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonHS129BK75))
          {
            if ((this.FM.isPlayers()) && (((RealFlightModel)this.FM).isRealMode()))
            {
              f1 = 905.0F;
            }
            else
            {
              f1 = 300.0F;
            }
          }
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonMG15120))
            f1 = 61.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonMiG_3_BK))
            f1 = 35.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonMk103))
            f1 = 140.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonMk108))
            f1 = 90.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP38GUNPOD))
            f1 = 90.0F;
          else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonP63CGUNPOD)) {
            f1 = 45.0F;
          }
          else
          {
            f1 = 150.0F;
          }
          f2 = (float)((Actor)paramArrayOfBulletEmitter[i][j]).pos.getRelPoint().z;
          f3 = (float)((Actor)paramArrayOfBulletEmitter[i][j]).pos.getRelPoint().y;
          this.parasiteJx += (f2 * f2 + f3 * f3) * f1;
          this.parasiteMass += f1;
        }
      }
    }

    this.pylonCoeffB = 0.0F;
    this.pylonCoeffR = 0.0F;
  }

  public void computeFullJ(Vector3d paramVector3d1, Vector3d paramVector3d2)
  {
    paramVector3d1.scale(this.massEmpty, paramVector3d2);

    paramVector3d1.x += this.parasiteJx;
  }
}