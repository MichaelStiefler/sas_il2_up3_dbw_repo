package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Tuple3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.ai.air.Maneuver;
import com.maddox.il2.ai.air.Pilot;
import com.maddox.il2.engine.ActorHMesh;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Orientation;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModelMain;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.fm.RealFlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.objects.sounds.SndAircraft;
import com.maddox.rts.CLASS;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import com.maddox.rts.Property;
import com.maddox.rts.Time;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class TA_152C1 extends FW_190
  implements TypeFighterAceMaker
{
  public boolean bToFire;
  private long tX4Prev;
  public int k14Mode;
  public int k14WingspanType;
  public float k14Distance;
  private float kangle;
  private float deltaAzimuth;
  private float deltaTangage;

  public TA_152C1()
  {
    this.k14Mode = 0;
    this.k14WingspanType = 0;
    this.k14Distance = 200.0F;
    this.bToFire = false;
    this.kangle = 0.0F;
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, -77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, -77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -102.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, -102.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC3_D0", 0.0F, 0.0F, 0.0F);
    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, f, 0.0F);
  }

  protected void moveGear(float paramFloat)
  {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat)
  {
    if (this.FM.CT.getGear() < 0.98F)
    {
      return;
    }

    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveWheelSink()
  {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[0], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearL2a_D0", Aircraft.xyz, Aircraft.ypr);
    Aircraft.xyz[1] = Aircraft.cvt(this.FM.Gears.gWheelSinking[1], 0.0F, 0.44F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("GearR2a_D0", Aircraft.xyz, Aircraft.ypr);
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, -f, 0.0F);
  }

  public void rareAction(float paramFloat, boolean paramBoolean)
  {
    super.rareAction(paramFloat, paramBoolean);
    if ((((this.FM instanceof RealFlightModel)) && (((RealFlightModel)this.FM).isRealMode())) || (!paramBoolean) || (!(this.FM instanceof Pilot)))
      return;
    Pilot localPilot = (Pilot)this.FM;
    if ((localPilot.get_maneuver() == 63) && (localPilot.target != null))
    {
      Point3d localPoint3d = new Point3d(localPilot.target.Loc);
      localPoint3d.sub(this.FM.Loc);
      this.FM.Or.transformInv(localPoint3d);
      if (((localPoint3d.x > 4000.0D) && (localPoint3d.x < 5500.0D)) || ((localPoint3d.x > 100.0D) && (localPoint3d.x < 5000.0D) && (World.Rnd().nextFloat() < 0.33F) && (Time.current() > this.tX4Prev + 10000L)))
      {
        this.bToFire = true;
        this.tX4Prev = Time.current();
      }
    }
  }

  public void update(float paramFloat)
  {
    for (int i = 1; i < 15; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());
    super.update(paramFloat);
  }

  public boolean typeFighterAceMakerToggleAutomation()
  {
    this.k14Mode += 1;
    if (this.k14Mode > 2)
      this.k14Mode = 0;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + this.k14Mode);
    return true;
  }

  public void typeFighterAceMakerAdjDistanceReset()
  {
  }

  public void typeFighterAceMakerAdjDistancePlus()
  {
    this.k14Distance += 10.0F;
    if (this.k14Distance > 800.0F)
      this.k14Distance = 800.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
  }

  public void typeFighterAceMakerAdjDistanceMinus()
  {
    this.k14Distance -= 10.0F;
    if (this.k14Distance < 200.0F)
      this.k14Distance = 200.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
  }

  public void typeFighterAceMakerAdjSideslipReset()
  {
  }

  public void typeFighterAceMakerAdjSideslipPlus()
  {
    this.k14WingspanType -= 1;
    if (this.k14WingspanType < 0)
      this.k14WingspanType = 0;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "AskaniaWing" + this.k14WingspanType);
  }

  public void typeFighterAceMakerAdjSideslipMinus()
  {
    this.k14WingspanType += 1;
    if (this.k14WingspanType > 9)
      this.k14WingspanType = 9;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "AskaniaWing" + this.k14WingspanType);
  }

  public void typeFighterAceMakerReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
    paramNetMsgGuaranted.writeByte(this.k14Mode);
    paramNetMsgGuaranted.writeByte(this.k14WingspanType);
    paramNetMsgGuaranted.writeFloat(this.k14Distance);
  }

  public void typeFighterAceMakerReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
    this.k14Mode = paramNetMsgInput.readByte();
    this.k14WingspanType = paramNetMsgInput.readByte();
    this.k14Distance = paramNetMsgInput.readFloat();
  }

  private static Aircraft._WeaponSlot[] GenerateDefaultConfig(int paramInt)
  {
    Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[paramInt];
    try
    {
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMK108k", 90);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(1, "MGunMG15120s", 175);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG15120s", 175);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120s", 175);
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG15120s", 175);
    } catch (Exception localException) {
    }
    return arrayOf_WeaponSlot;
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Ta.152");
    Property.set(localClass, "meshName", "3DO/Plane/Ta-152C1/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "yearService", 1944.6F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Ta-152C1.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitTA_152C1.class });

    Property.set(localClass, "LOSElevation", 0.755F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 1, 1, 1, 1, 9, 9, 2, 2, 2, 2, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON03", "_CANNON04", "_CANNON05", "_CANNON06", "_ExternalDev01", "_ExternalDev02", "_ExternalRock01", "_ExternalRock01", "_ExternalRock02", "_ExternalRock02", "_ExternalBomb02", "_ExternalBomb03" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 15;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = GenerateDefaultConfig(i);
      for (int j = 5; j < i; j++) {
        arrayOf_WeaponSlot[j] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (int k = 0; k < i; k++) {
        arrayOf_WeaponSlot[k] = null;
      }
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}