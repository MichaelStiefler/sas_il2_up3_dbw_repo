package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.fm.Mass;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.Squares;
import com.maddox.il2.game.HUD;
import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class Centauro_late extends Centauroxyz
  implements TypeFighter, TypeBNZFighter
{
  public static boolean bChangedPit = false;
  private float G;
  private float limite;
  private float Gneg;
  private float limiteneg;
  private float temp;
  private float reduc;

  static
  {
    Class class1 = CLASS.THIS();
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "iconFar_shortClassName", "G.55");
    Property.set(class1, "meshName_it", "3DO/Plane/G-55(it)/hier.him");
    Property.set(class1, "PaintScheme_it", new PaintSchemeBMPar09());
    Property.set(class1, "meshName", "3DO/Plane/G-55(multi)/hier.him");
    Property.set(class1, "PaintScheme", new PaintSchemeFMPar02());
    Property.set(class1, "yearService", 1943.0F);
    Property.set(class1, "yearExpired", 1948.5F);
    Property.set(class1, "FlightModel", "FlightModels/Centauro_late.fmd");
    Property.set(class1, "cockpitClass", new Class[] { CockpitMC_205.class });
    Property.set(class1, "LOSElevation", 0.9119F);
    Aircraft.weaponTriggersRegister(class1, new int[] { 
      0, 0, 1, 1, 1 });

    Aircraft.weaponHooksRegister(class1, new String[] { 
      "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03" });
    try
    {
      ArrayList arraylist = new ArrayList();
      Property.set(class1, "weaponsList", arraylist);
      HashMapInt hashmapint = new HashMapInt();
      Property.set(class1, "weaponsMap", hashmapint);
      byte byte0 = 5;
      Aircraft._WeaponSlot[] a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      String s = "default";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(0, "MGunBredaSAFAT127g55", 300);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(0, "MGunBredaSAFAT127g55", 300);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120t", 200);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 250);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 250);
      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);

      s = "none";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = null;
      a_lweaponslot[2] = null;
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
    }
    catch (Exception localException)
    {
    }
  }

  public Centauro_late()
  {
    this.temp = World.Rnd().nextFloat(106.4F, 111.3F);
    this.reduc = World.Rnd().nextFloat(0.7824F, 0.7903F);
  }

  protected void nextDMGLevel(String s, int i, Actor actor)
  {
    super.nextDMGLevel(s, i, actor);
    if (this.FM.isPlayers())
    {
      bChangedPit = true;
    }
  }

  protected void nextCUTLevel(String s, int i, Actor actor)
  {
    super.nextCUTLevel(s, i, actor);
    if (this.FM.isPlayers())
    {
      bChangedPit = true;
    }
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();

    this.FM.SensPitch = 0.634F;
    this.FM.SensRoll = 0.37F;
    this.FM.SensYaw = 0.49F;
    this.FM.EI.engines[0].tOilOutMaxRPM = this.temp;
    this.FM.setGCenter(0.18F);
    this.FM.EI.engines[0].setPropReductorValue(this.reduc);
    this.FM.EI.engines[0].setControlPropAuto(true);
    if (this.FM.isPlayers())
    {
      this.FM.CT.BrakeControl = 1.0F;
      this.FM.CT.setMagnetoControl(0);
    }
    if (!this.FM.isPlayers())
      this.FM.CT.bHasCockpitDoorControl = true;
  }

  public void update(float f) {
    super.update(f);

    if (!this.FM.Gears.onGround())
    {
      this.FM.EI.engines[0].addVside *= 1.04167D;
      this.FM.EI.engines[0].addVflow *= 1.00765D;
    }

    if (this.FM.CT.getTrimElevatorControl() > 0.361F) {
      this.FM.CT.setTrimElevatorControl(0.361F);
    }
    if (this.FM.CT.getTrimElevatorControl() < -0.261F) {
      this.FM.CT.setTrimElevatorControl(-0.261F);
    }
    if ((this.FM.Gears.onGround()) && (this.FM.EI.engines[0].getRPM() < 2300.0F))
      this.FM.SensYaw = 0.91F;
    else {
      this.FM.SensYaw = 0.49F;
    }
    if ((this.FM.isPlayers()) && (this.FM.Gears.onGround())) {
      this.FM.VmaxAllowed = this.FM.getSpeed();
    }
    if (this.FM.VmaxAllowed > 237.0F) {
      this.FM.VmaxAllowed = 237.0F;
    }
    if (this.FM.CT.getElevator() < 0.0F)
      this.FM.SensPitch = 0.484F;
    else {
      this.FM.SensPitch = 0.61F;
    }
    if ((!this.FM.isPlayers()) && (this.FM.Gears.onGround()))
    {
      if (this.FM.EI.engines[0].getRPM() < 1400.0F)
      {
        this.FM.CT.cockpitDoorControl = 1.0F;
      }
      else this.FM.CT.cockpitDoorControl = 0.0F;
    }

    if ((this.FM.EI.engines[0].getControlThrottle() <= 1.0F) && (this.FM.EI.engines[0].getControlAfterburner()))
      this.FM.EI.engines[0].tOilOutMaxRPM = 125.0F;
    else {
      this.FM.EI.engines[0].tOilOutMaxRPM = this.temp;
    }
    if ((this.FM.BarometerZ > 5800.0F) && (this.FM.EI.engines[0].getControlThrottle() > 1.0F)) {
      this.FM.EI.engines[0].setPropReductorValue(this.reduc * 0.94F);
    }
    if (this.FM.EI.engines[0].getControlThrottle() > 1.0F) {
      HUD.logRightBottom("Decollo e emergenza");
    }
    if ((!this.FM.isPlayers()) && (this.FM.Gears.onGround()))
    {
      if (this.FM.EI.engines[0].getRPM() < 1400.0F)
      {
        this.FM.CT.cockpitDoorControl = 1.0F;
      }
      else this.FM.CT.cockpitDoorControl = 0.0F;
    }
    this.FM.Sq.dragProducedCx *= 0.92F;

    if (this.FM.getAltitude() > 5600.0F)
    {
      float i = 0.18F;
      i -= (this.FM.getAltitude() - 5599.0F) / 217000.0F;
      this.FM.setGCenter(i);
    }
    else
    {
      this.FM.setGCenter(0.18F);
    }

    calcg();
    calcgneg();
  }

  static Class _mthclass$(String s)
  {
    try
    {
      class1 = Class.forName(s);
    }
    catch (ClassNotFoundException classnotfoundexception)
    {
      Class class1;
      throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }
    Class class1;
    return class1;
  }

  private void calcg()
  {
    this.G = this.FM.getOverload();
    this.limite = (29200.0F / this.FM.M.mass);

    if (this.G > this.limite * 1.104D)
    {
      this.FM.VmaxAllowed = (this.FM.getSpeed() - 30.0F);
    }
    else if ((this.FM.EI.engines[0].getRPM() > 2500.0F) && (this.FM.EI.engines[0].getRPM() <= 2600.0F)) {
      this.FM.VmaxAllowed = (this.FM.getSpeed() + 16.5F);
    }
    else if (this.FM.EI.engines[0].getRPM() > 2600.0F) {
      this.FM.VmaxAllowed = (this.FM.getSpeed() + 15.0F);
    }
    else
      this.FM.VmaxAllowed = 251.3F;
  }

  private void calcgneg()
  {
    this.Gneg = this.FM.getOverload();
    this.limiteneg = (-11550.0F / this.FM.M.mass);

    if (this.Gneg < this.limiteneg)
    {
      this.FM.VmaxAllowed = (this.FM.getSpeed() - 30.0F);
    }
  }
}