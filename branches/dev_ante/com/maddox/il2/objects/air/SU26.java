package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class SU26 extends IAR_8X
  implements TypeScout
{
  public static boolean bChangedPit = false;

  static
  {
    Class class1 = SU26.class;
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "iconFar_shortClassName", "SU26");
    Property.set(class1, "meshName", "3DO/Plane/IAR-80/hier.him");
    Property.set(class1, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(class1, "originCountry", PaintScheme.countryRussia);
    Property.set(class1, "yearService", 1989.0F);
    Property.set(class1, "yearExpired", 3000.0F);
    Property.set(class1, "FlightModel", "FlightModels/Su-26.fmd");
    Property.set(class1, "cockpitClass", new Class[] { 
      CockpitIAR81.class });

    Property.set(class1, "FlightModel", "FlightModels/Su-26.fmd");
    Property.set(class1, "LOSElevation", 0.8323F);
    Aircraft.weaponTriggersRegister(class1, new int[4]);

    Aircraft.weaponHooksRegister(class1, new String[] { 
      "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });
    try
    {
      ArrayList arraylist = new ArrayList();
      Property.set(class1, "weaponsList", arraylist);
      HashMapInt hashmapint = new HashMapInt();
      Property.set(class1, "weaponsMap", hashmapint);
      byte byte0 = 4;
      String s = "default";
      Aircraft._WeaponSlot[] a_lweaponslot = GenerateDefaultConfig(byte0);
      for (int i = 4; i < byte0; i++)
      {
        a_lweaponslot[i] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "none";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      for (int j = 0; j < byte0; j++)
      {
        a_lweaponslot[j] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
    }
    catch (Exception localException)
    {
    }
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

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int i)
  {
    Aircraft._WeaponSlot[] a_lweaponslot = new Aircraft._WeaponSlot[i];
    try
    {
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = null;
      a_lweaponslot[2] = null;
      a_lweaponslot[3] = null;
    }
    catch (Exception localException) {
    }
    return a_lweaponslot;
  }
}