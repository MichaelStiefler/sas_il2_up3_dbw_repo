package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class PBY extends PBYX
  implements TypeBomber, TypeScout, TypeSailPlane, TypeTransport
{
  public float fSightCurAltitude;
  public float fSightCurSpeed;
  public float fSightCurForwardAngle;
  public float fSightSetForwardAngle;
  public float fSightCurSideslip;

  static
  {
    Class class1 = PBY.class;
    new NetAircraft.SPAWN(class1);
    Property.set(class1, "iconFar_shortClassName", "PBY");
    Property.set(class1, "meshName", "3DO/Plane/PBY/hier.him");
    Property.set(class1, "PaintScheme", new PaintSchemeBMPar04());
    Property.set(class1, "yearService", 1941.0F);
    Property.set(class1, "yearExpired", 2048.0F);
    Property.set(class1, "FlightModel", "FlightModels/PBN-1.fmd");
    Property.set(class1, "cockpitClass", new Class[] { 
      CockpitPBY.class, CockpitPBY_Bombardier.class, CockpitPBY_TGunner.class, CockpitPBY_LGunner.class, CockpitPBY_RGunner.class });

    Property.set(class1, "LOSElevation", 0.73425F);
    Aircraft.weaponTriggersRegister(class1, new int[] { 
      10, 11, 12, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(class1, new String[] { 
      "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04" });
    try
    {
      ArrayList arraylist = new ArrayList();
      Property.set(class1, "weaponsList", arraylist);
      HashMapInt hashmapint = new HashMapInt();
      Property.set(class1, "weaponsMap", hashmapint);
      byte byte0 = 7;
      Aircraft._WeaponSlot[] a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      String s = "default";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 1000);
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);

      String s1 = "2x500";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 1000);
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      a_lweaponslot[5] = new Aircraft._WeaponSlot(2, "BombGun500lbs", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arraylist.add(s1);
      hashmapint.put(Finger.Int(s1), a_lweaponslot);

      String s2 = "4x500";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 1000);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(2, "BombGun500lbs", 1);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(2, "BombGun500lbs", 1);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      arraylist.add(s2);
      hashmapint.put(Finger.Int(s2), a_lweaponslot);

      String s3 = "2x1000";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 1000);
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      a_lweaponslot[5] = new Aircraft._WeaponSlot(2, "BombGun1000lbs", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arraylist.add(s3);
      hashmapint.put(Finger.Int(s3), a_lweaponslot);

      String s5 = "4x1000";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 1000);
      a_lweaponslot[3] = new Aircraft._WeaponSlot(2, "BombGun1000lbs", 1);
      a_lweaponslot[4] = new Aircraft._WeaponSlot(2, "BombGun1000lbs", 1);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      arraylist.add(s5);
      hashmapint.put(Finger.Int(s5), a_lweaponslot);

      String s6 = "2xtorp";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 1000);
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "BombGunTorpMk13", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(1, "BombGunTorpMk13", 1);
      arraylist.add(s6);
      hashmapint.put(Finger.Int(s6), a_lweaponslot);

      String s7 = "1xtorp+2x500";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 1000);
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "BombGunTorpMk13", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(2, "BombGun500lbs", 1);
      arraylist.add(s7);
      hashmapint.put(Finger.Int(s7), a_lweaponslot);

      String s8 = "1xtorp+2x1000";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = new Aircraft._WeaponSlot(10, "MGunBrowning50t", 1000);
      a_lweaponslot[1] = new Aircraft._WeaponSlot(11, "MGunBrowning50t", 1000);
      a_lweaponslot[2] = new Aircraft._WeaponSlot(12, "MGunBrowning50t", 1000);
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);
      a_lweaponslot[5] = new Aircraft._WeaponSlot(0, "BombGunTorpMk13", 1);
      a_lweaponslot[6] = new Aircraft._WeaponSlot(2, "BombGun1000lbs", 1);
      arraylist.add(s8);
      hashmapint.put(Finger.Int(s8), a_lweaponslot);

      s = "none";
      a_lweaponslot = new Aircraft._WeaponSlot[byte0];
      a_lweaponslot[0] = null;
      a_lweaponslot[1] = null;
      a_lweaponslot[2] = null;
      a_lweaponslot[3] = null;
      a_lweaponslot[4] = null;
      a_lweaponslot[5] = null;
      a_lweaponslot[6] = null;
      arraylist.add(s);
      hashmapint.put(Finger.Int(s), a_lweaponslot);
    }
    catch (Exception localException)
    {
    }
  }

  public PBY()
  {
    this.fSightCurAltitude = 300.0F;
    this.fSightCurSpeed = 50.0F;
    this.fSightCurForwardAngle = 0.0F;
    this.fSightSetForwardAngle = 0.0F;
    this.fSightCurSideslip = 0.0F;
  }

  public void doKillPilot(int i)
  {
    switch (i)
    {
    case 2:
      this.FM.turret[0].bIsOperable = false;
      break;
    case 5:
      this.FM.turret[1].bIsOperable = false;
      break;
    case 6:
      this.FM.turret[2].bIsOperable = false;
    case 3:
    case 4:
    }
  }

  public void doMurderPilot(int i) {
    switch (i)
    {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("HMask1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("HMask2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      break;
    case 2:
      hierMesh().chunkVisible("Pilot3_D0", false);
      hierMesh().chunkVisible("HMask3_D0", false);
      hierMesh().chunkVisible("Pilot3_D1", true);
      break;
    case 3:
      hierMesh().chunkVisible("Pilot4_D0", false);
      hierMesh().chunkVisible("HMask4_D0", false);
      hierMesh().chunkVisible("Pilot4_D1", true);
      break;
    case 5:
      hierMesh().chunkVisible("Pilot6_D0", false);
      hierMesh().chunkVisible("HMask6_D0", false);
      hierMesh().chunkVisible("Pilot6_D1", true);
      break;
    case 6:
      hierMesh().chunkVisible("Pilot7_D0", false);
      hierMesh().chunkVisible("HMask7_D0", false);
      hierMesh().chunkVisible("Pilot7_D1", true);
    case 4:
    }
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
    this.fSightCurAltitude += 10.0F;
    if (this.fSightCurAltitude > 10000.0F)
    {
      this.fSightCurAltitude = 10000.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "BombsightAltitude", new Object[] { 
      new Integer((int)this.fSightCurAltitude) });
  }

  public void typeBomberAdjAltitudeMinus()
  {
    this.fSightCurAltitude -= 10.0F;
    if (this.fSightCurAltitude < 300.0F)
    {
      this.fSightCurAltitude = 300.0F;
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
    netmsgguaranted.writeFloat(this.fSightCurForwardAngle);
    netmsgguaranted.writeFloat(this.fSightCurSideslip);
  }

  public void typeBomberReplicateFromNet(NetMsgInput netmsginput)
    throws IOException
  {
    this.fSightCurAltitude = netmsginput.readFloat();
    this.fSightCurSpeed = netmsginput.readFloat();
    this.fSightCurForwardAngle = netmsginput.readFloat();
    this.fSightCurSideslip = netmsginput.readFloat();
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
}