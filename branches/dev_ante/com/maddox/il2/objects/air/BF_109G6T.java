package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class BF_109G6T extends BF_109
  implements TypeBNZFighter
{
  public float cockpitDoor_ = 0.0F; private float EpsVerySmooth_ = 0.0005F; private float fMaxKMHSpeedForOpenCanopy = 40.0F;

  private float kangle = 0.0F; public boolean bHasBlister = true;

  public void update(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 5.0F) { hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F); hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F); } hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -16.0F * this.kangle, 0.0F); hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 16.0F * this.kangle, 0.0F); hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -16.0F * this.kangle, 0.0F); hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 16.0F * this.kangle, 0.0F); this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.EI.engines[0].getControlRadiator()); if (this.kangle > 1.0F) this.kangle = 1.0F; super.update(paramFloat);
    Object localObject;
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getCockpitDoor() > 0.2D) && (this.bHasBlister) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeedKMH() > this.fMaxKMHSpeedForOpenCanopy) && (hierMesh().chunkFindCheck("Blister1_D0") != -1)) { try { if (this == World.getPlayerAircraft()) ((CockpitBF_109G6)Main3D.cur3D().cockpitCur).removeCanopy();  } catch (Exception localException) {
      }hierMesh().hideSubTrees("Blister1_D0"); localObject = new Wreckage(this, hierMesh().chunkFind("Blister1_D0")); ((Wreckage)localObject).collide(true); Vector3d localVector3d = new Vector3d(); localVector3d.set(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.Vwld); ((Wreckage)localObject).setSpeed(localVector3d); this.bHasBlister = false; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.bHasCockpitDoorControl = false; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.setGCenter(-0.5F); } if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) if (!Main3D.cur3D().isViewOutside()) hierMesh().chunkVisible("CF_D0", false); else hierMesh().chunkVisible("CF_D0", true); 
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) { if (!Main3D.cur3D().isViewOutside()) hierMesh().chunkVisible("CF_D1", false); hierMesh().chunkVisible("CF_D2", false); hierMesh().chunkVisible("CF_D3", false); } if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) { if (!Main3D.cur3D().isViewOutside()) hierMesh().chunkVisible("Blister1_D0", false); else if (this.bHasBlister)
      {
        hierMesh().chunkVisible("Blister1_D0", true); } localObject = World.getPlayerAircraft().pos.getAbsPoint(); if (((Point3d)localObject).z - World.land().HQ(((Point3d)localObject).x, ((Point3d)localObject).y) < 0.009999999776482582D) hierMesh().chunkVisible("CF_D0", true); if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.bIsAboutToBailout) hierMesh().chunkVisible("Blister1_D0", false);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f1 = 0.8F; float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F; if ((paramFloat <= f1) || (paramFloat == 1.0F))
    {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F); paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F); } f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F; if (paramFloat >= 1.0F - f1) { paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F); paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F); } paramHierMesh.chunkSetAngles("GearC3_D0", 70.0F * paramFloat, 0.0F, 0.0F); if (paramFloat > 0.99F) { paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F); paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F); paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F); paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F); } if (paramFloat < 0.01F) { paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F); paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F); paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);

      paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
    }
  }

  protected void moveGear(float paramFloat)
  {
    float f1 = 0.9F - ((Wing)getOwner()).aircIndex(this) * 0.1F; float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F; if ((paramFloat <= f1) || (paramFloat == 1.0F)) { hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F); hierMesh().chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F); } f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;

    if (paramFloat >= 1.0F - f1) { hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F); hierMesh().chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F); } hierMesh().chunkSetAngles("GearC3_D0", 70.0F * paramFloat, 0.0F, 0.0F); if (paramFloat > 0.99F) { hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F); hierMesh().chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F); hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F); hierMesh().chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
    }
  }

  public void moveSteering(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_CT_of_type_ComMaddoxIl2FmControls.getGear() < 0.98F) return;

    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier(); hierMesh().chunkSetAngles("Blister1_D0", 0.0F, 100.0F * paramFloat, 0.0F);

    if (Config.isUSE_RENDER()) { if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat); setDoorSnd(paramFloat);
    }
  }

  static
  {
    Class localClass = BF_109G6T.class; new NetAircraft.SPAWN(localClass); Property.set(localClass, "iconFar_shortClassName", "Bf109"); Property.set(localClass, "meshName", "3DO/Plane/Bf-109G-6EarlyT/hier.him"); Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04()); Property.set(localClass, "yearService", 1943.0F); Property.set(localClass, "yearExpired", 1945.5F); Property.set(localClass, "FlightModel", "FlightModels/bf-109g-6_1_32Ata_oc.fmd"); Property.set(localClass, "cockpitClass", new Class[] { CockpitBF_109G6.class }); Property.set(localClass, "LOSElevation", 0.7498F); Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 1, 1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 3, 3, 3 }); Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev01", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });
    try { ArrayList localArrayList = new ArrayList(); Property.set(localClass, "weaponsList", localArrayList); HashMapInt localHashMapInt = new HashMapInt(); Property.set(localClass, "weaponsMap", localHashMapInt); int i = 17; Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; String str = "default"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200); for (int j = 4; j < i; j++) arrayOf_WeaponSlot[j] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "R1-SC250"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = null; arrayOf_WeaponSlot[6] = null;

      arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = null; arrayOf_WeaponSlot[11] = null; arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1); for (int k = 13; k < i; k++) arrayOf_WeaponSlot[k] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "R1-AB250"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = null; arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = null; arrayOf_WeaponSlot[11] = null; arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunAB250", 1); for (int m = 13; m < i; m++) arrayOf_WeaponSlot[m] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "R1-SC500"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = null; arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = null; arrayOf_WeaponSlot[11] = null; arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunSC500", 1); for (int n = 13; n < i; n++) arrayOf_WeaponSlot[n] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "R1-AB500"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = null; arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = null; arrayOf_WeaponSlot[11] = null; arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(3, "BombGunAB500", 1); for (int i1 = 13; i1 < i; i1++) arrayOf_WeaponSlot[i1] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "R2-SC50"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = null; arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonETC50", 1); arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = null; arrayOf_WeaponSlot[11] = null; arrayOf_WeaponSlot[12] = null; arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1); arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1); arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1); arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1); localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "R3-DROPTANK"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = null; arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1); for (int i2 = 10; i2 < i; i2++) arrayOf_WeaponSlot[i2] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "R6-MG151-20"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunMG15120MGki", 200); arrayOf_WeaponSlot[3] = null; arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 140);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 140); arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = null; arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "PylonMG15120", 1); arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(1, "PylonMG15120", 1); for (int i3 = 12; i3 < i; i3++) arrayOf_WeaponSlot[i3] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "R3R6-MG151-20"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG131si", 300); arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG15120MGki", 200); arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135); arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(1, "MGunMG15120kh", 135); arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "FuelTankGun_Type_D", 1); arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(1, "PylonMG15120", 1); arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(1, "PylonMG15120", 1); for (int i4 = 12; i4 < i; i4++) arrayOf_WeaponSlot[i4] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "none"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; for (int i5 = 0; i5 < i; i5++) arrayOf_WeaponSlot[i5] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}