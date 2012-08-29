package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class MOSQUITO16 extends MOSQUITOS
  implements TypeBomber
{
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurForwardAngle;
  public float fSightSetForwardAngle;
  public float fSightCurSideslip;

  static
  {
    Class class1 = MOSQUITO16.class;
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "iconFar_shortClassName", "Mosquito");
    Property.set(class1, "meshName", "3DO/Plane/Mosquito_B_MkXVI(Multi1)/hier.him");
    Property.set(class1, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(class1, "meshName_gb", "3DO/Plane/Mosquito_B_MkXVI(GB)/hier.him");
    Property.set(class1, "PaintScheme_gb", new PaintSchemeFMPar06());
    Property.set(class1, "yearService", 1941.0F);
    Property.set(class1, "yearExpired", 1946.5F);
    Property.set(class1, "FlightModel", "FlightModels/Mosquito-BMkXVI.fmd");
    Property.set(class1, "cockpitClass", new Class[] { 
      CockpitMosquito16.class, CockpitMOSQUITO16_Bombardier.class });

    Property.set(class1, "LOSElevation", 0.76315F);
    Aircraft.weaponTriggersRegister(class1, new int[] { 
      0, 3, 3, 3, 3, 9, 9 });

    Aircraft.weaponHooksRegister(class1, new String[] { 
      "_Clip04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03", "_BombSpawn04", "_ExternalBomb02", "_ExternalBomb03" });
    try
    {
      ArrayList arraylist = new ArrayList();
      Property.set(class1, "weaponsList", arraylist);
      HashMapInt hashmapint = new HashMapInt();
      Property.set(class1, "weaponsMap", hashmapint);
      byte byte0 = 7;
      String s = "default";
      Aircraft._WeaponSlot[] a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = null;
      a_lweaponslot[2] = null;
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int i = 7; i < byte0; i++)
      {
        a_lweaponslot[i] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiRedND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIRed", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIRed", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIRed", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIRed", 2);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int j = 7; j < byte0; j++)
      {
        a_lweaponslot[j] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiGreenND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIGreen", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIGreen", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIGreen", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIGreen", 2);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int k = 7; k < byte0; k++)
      {
        a_lweaponslot[k] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiBlueND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIBlue", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIBlue", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIBlue", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIBlue", 2);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int l = 7; l < byte0; l++)
      {
        a_lweaponslot[l] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiYellowND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIYellow", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIYellow", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIYellow", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIYellow", 2);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int i1 = 7; i1 < byte0; i1++)
      {
        a_lweaponslot[i1] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiOrangeND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIOrange", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIOrange", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIOrange", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIOrange", 2);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int j1 = 7; j1 < byte0; j1++)
      {
        a_lweaponslot[j1] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiPinkND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIPink", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIPink", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIPink", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIPink", 2);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int k1 = 7; k1 < byte0; k1++)
      {
        a_lweaponslot[k1] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiRed";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIRed", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIRed", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIRed", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIRed", 2);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int l1 = 7; l1 < byte0; l1++)
      {
        a_lweaponslot[l1] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiGreen";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIGreen", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIGreen", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIGreen", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIGreen", 2);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int i2 = 7; i2 < byte0; i2++)
      {
        a_lweaponslot[i2] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiBlue";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIBlue", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIBlue", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIBlue", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIBlue", 2);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int j2 = 7; j2 < byte0; j2++)
      {
        a_lweaponslot[j2] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiYellow";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIYellow", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIYellow", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIYellow", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIYellow", 2);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int k2 = 7; k2 < byte0; k2++)
      {
        a_lweaponslot[k2] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiOrange";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIOrange", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIOrange", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIOrange", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIOrange", 2);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int l2 = 7; l2 < byte0; l2++)
      {
        a_lweaponslot[l2] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "TiPink";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunTIPink", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGunTIPink", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGunTIPink", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGunTIPink", 2);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int i3 = 7; i3 < byte0; i3++)
      {
        a_lweaponslot[i3] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "4x250ND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int j3 = 7; j3 < byte0; j3++)
      {
        a_lweaponslot[j3] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "6x250ND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 2);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int k3 = 7; k3 < byte0; k3++)
      {
        a_lweaponslot[k3] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "4x500ND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int l3 = 7; l3 < byte0; l3++)
      {
        a_lweaponslot[l3] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "6x500ND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 2);
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int i4 = 7; i4 < byte0; i4++)
      {
        a_lweaponslot[i4] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "1xhc4000ND";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunHC4000", 1);
      a_lweaponslot[2] = null;
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      for (int j4 = 7; j4 < byte0; j4++)
      {
        a_lweaponslot[j4] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "4x250";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int k4 = 7; k4 < byte0; k4++)
      {
        a_lweaponslot[k4] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "6x250";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun250lbsE", 2);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int l4 = 7; l4 < byte0; l4++)
      {
        a_lweaponslot[l4] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "4x500";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int i5 = 7; i5 < byte0; i5++)
      {
        a_lweaponslot[i5] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "6x500";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 1);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 2);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbsE", 2);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int j5 = 7; j5 < byte0; j5++)
      {
        a_lweaponslot[j5] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "1xhc4000";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = new Aircraft._WeaponSlot(3, "BombGunHC4000", 1);
      a_lweaponslot[2] = null;
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      a_lweaponslot[5] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank100gal", 1);
      for (int k5 = 7; k5 < byte0; k5++)
      {
        a_lweaponslot[k5] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
      s = "None";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      for (int l5 = 0; l5 < byte0; l5++)
      {
        a_lweaponslot[l5] = null;
      }

      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
    }
    catch (Exception localException)
    {
    }
  }

  public MOSQUITO16()
  {
    this.fSightCurAltitude = 300.0F;
    this.fSightCurSpeed = 50.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightSetForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
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

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
    this.fSightCurForwardAngle = 0.0F;
  }

  public void typeBomberAdjDistancePlus()
  {
    this.fSightCurForwardAngle += 0.2F;
    if (this.fSightCurForwardAngle > 75.0F)
    {
      this.fSightCurForwardAngle = 75.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { 
      new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjDistanceMinus()
  {
    this.fSightCurForwardAngle -= 0.2F;
    if (this.fSightCurForwardAngle < -15.0F)
    {
      this.fSightCurForwardAngle = -15.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightElevation", new Object[] { 
      new Integer((int)(this.fSightCurForwardAngle * 1.0F)) });
  }

  public void typeBomberAdjSideslipReset()
  {
    this.fSightCurSideslip = 0.0F;
  }

  public void typeBomberAdjSideslipPlus()
  {
    this.fSightCurSideslip += 1.0F;
    if (this.fSightCurSideslip > 45.0F)
    {
      this.fSightCurSideslip = 45.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { 
      new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjSideslipMinus()
  {
    this.fSightCurSideslip -= 1.0F;
    if (this.fSightCurSideslip < -45.0F)
    {
      this.fSightCurSideslip = -45.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSlip", new Object[] { 
      new Integer((int)(this.fSightCurSideslip * 1.0F)) });
  }

  public void typeBomberAdjAltitudeReset()
  {
    this.fSightCurAltitude = 300.0F;
  }

  public void typeBomberAdjAltitudePlus()
  {
    this.fSightCurAltitude += 50.0F;
    if (this.fSightCurAltitude > 50000.0F)
    {
      this.fSightCurAltitude = 50000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { 
      new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 50.0F;
    if (this.fSightCurAltitude < 100.0F)
    {
      this.fSightCurAltitude = 100.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { 
      new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjSpeedReset()
  {
    this.fSightCurSpeed = 50.0F;
  }

  public void typeBomberAdjSpeedPlus()
  {
    this.fSightCurSpeed += 5.0F;
    if (this.fSightCurSpeed > 520.0F)
    {
      this.fSightCurSpeed = 520.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { 
      new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberAdjSpeedMinus()
  {
    this.fSightCurSpeed -= 5.0F;
    if (this.fSightCurSpeed < 50.0F)
    {
      this.fSightCurSpeed = 50.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightSpeed", new Object[] { 
      new Integer((int)this.fSightCurSpeed) });
  }

  public void typeBomberUpdate(float f)
  {
    double d = this.fSightCurSpeed / 3.6D * Math.sqrt(this.fSightCurAltitude * 0.203873598D);
    d -= this.fSightCurAltitude * this.fSightCurAltitude * 1.419E-005D;
    this.fSightSetForwardAngle = (float)Math.toDegrees(Math.atan(d / this.fSightCurAltitude));
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted netmsgguaranted)
    throws IOException
  {
    netmsgguaranted.writeFloat(this.fSightCurAltitude);
    netmsgguaranted.writeFloat(this.fSightCurSpeed);
  }

  public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
    throws IOException
  {
    this.fSightCurAltitude = netmsginput.readFloat();
    this.fSightCurSpeed = netmsginput.readFloat();
  }
}