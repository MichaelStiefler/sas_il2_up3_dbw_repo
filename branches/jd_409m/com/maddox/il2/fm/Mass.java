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
import com.maddox.il2.objects.weapons.PylonMG15120Internal;
import com.maddox.il2.objects.weapons.PylonMG15120x2;
import com.maddox.il2.objects.weapons.PylonPE8_FAB100;
import com.maddox.il2.objects.weapons.PylonPE8_FAB250;
import com.maddox.il2.objects.weapons.PylonRO_82_1;
import com.maddox.il2.objects.weapons.PylonRO_82_3;
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
    f = paramSectFile.get(str1, "TakeOff", 0.0F); if (f == 0.0F) throw new RuntimeException(str2);
    this.maxWeight = f;
    f = paramSectFile.get(str1, "Fuel", 0.0F); if (f == 0.0F) throw new RuntimeException(str2);
    this.maxFuel = f;

    this.mass = this.massEmpty;
    this.fuel = this.maxFuel;

    f = paramSectFile.get(str1, "Nitro", 0.0F);
    this.maxNitro = (this.nitro = f);
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

    if (this.fuelTanks.length != 0) {
      float f = 0.0F;
      for (int i = 0; i < this.fuelTanks.length; i++) f += this.fuelTanks[i].getFuel(paramFloat / this.fuelTanks.length);
      if (f > 0.0F) return true;
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
          f2 = (float)((Actor)paramArrayOfBulletEmitter[i][j]).pos.getRelPoint().jdField_z_of_type_Double;
          f3 = (float)((Actor)paramArrayOfBulletEmitter[i][j]).pos.getRelPoint().jdField_y_of_type_Double;
          this.parasiteJx += (f2 * f2 + f3 * f3) * f1;
          this.parasiteMass += f1;
        }

        if (((paramArrayOfBulletEmitter[i][j] instanceof BombGun)) || ((paramArrayOfBulletEmitter[i][j] instanceof RocketGun))) {
          k = paramArrayOfBulletEmitter[i][j].countBullets();
          f1 = paramArrayOfBulletEmitter[i][j].bulletMassa() * (k >= 0 ? k : 1);
          f2 = 0.0F;
          f3 = 2.0F;
          this.parasiteJx += (f2 * f2 + f3 * f3) * f1;
          this.parasiteMass += f1;
        }

        if ((paramArrayOfBulletEmitter[i][j] instanceof Pylon)) {
          if (((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_82_1)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonRO_82_3)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonPE8_FAB100)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonPE8_FAB250)) || ((paramArrayOfBulletEmitter[i][j] instanceof PylonMG15120Internal)))
          {
            f1 = 0.0F;
          } else if ((paramArrayOfBulletEmitter[i][j] instanceof PylonMG15120x2))
            f1 = 450.0F;
          else {
            f1 = 15.0F;
          }
          f2 = (float)((Actor)paramArrayOfBulletEmitter[i][j]).pos.getRelPoint().jdField_z_of_type_Double;
          f3 = (float)((Actor)paramArrayOfBulletEmitter[i][j]).pos.getRelPoint().jdField_y_of_type_Double;
          this.parasiteJx += (f2 * f2 + f3 * f3) * f1;
          this.parasiteMass += f1;
        }
      }
    }
  }

  public void computeFullJ(Vector3d paramVector3d1, Vector3d paramVector3d2)
  {
    paramVector3d1.scale(this.massEmpty, paramVector3d2);

    paramVector3d1.x += this.parasiteJx;
  }
}