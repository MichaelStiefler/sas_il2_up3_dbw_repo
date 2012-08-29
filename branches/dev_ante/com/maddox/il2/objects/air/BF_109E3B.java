package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class BF_109E3B extends BF_109
{
  private float kangle;

  public BF_109E3B()
  {
    this.kangle = 0.0F;
  }

  public void update(float paramFloat)
  {
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getSpeed() > 5.0F) { hierMesh().chunkSetAngles("SlatL_D0", 0.0F, 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F)); hierMesh().chunkSetAngles("SlatR_D0", 0.0F, 0.0F, Aircraft.cvt(this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F));
    }

    hierMesh().chunkSetAngles("WaterL_D0", 0.0F, -38.0F * this.kangle, 0.0F); hierMesh().chunkSetAngles("WaterR_D0", 0.0F, -38.0F * this.kangle, 0.0F); this.kangle = (0.95F * this.kangle + 0.05F * this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlRadiator()); if (this.kangle > 1.0F) this.kangle = 1.0F; super.update(paramFloat); if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) if (!Main3D.cur3D().isViewOutside()) hierMesh().chunkVisible("CF_D0", false); else hierMesh().chunkVisible("CF_D0", true); 
    if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers())
    {
      if (!Main3D.cur3D().isViewOutside()) hierMesh().chunkVisible("CF_D1", false); hierMesh().chunkVisible("CF_D2", false); hierMesh().chunkVisible("CF_D3", false); } if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) { if (!Main3D.cur3D().isViewOutside()) hierMesh().chunkVisible("Blister1_D0", false);
      else
      {
        hierMesh().chunkVisible("Blister1_D0", true); } Point3d localPoint3d = World.getPlayerAircraft().pos.getAbsPoint(); if (localPoint3d.z - World.land().HQ(localPoint3d.x, localPoint3d.y) < 0.009999999776482582D) hierMesh().chunkVisible("CF_D0", true); if (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.AS.bIsAboutToBailout) hierMesh().chunkVisible("Blister1_D0", false);  }
    afterburnerhud(); super.update(paramFloat);
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f1 = 0.8F; float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F; if (paramFloat <= f1) { paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -78.0F * f2, 0.0F); paramHierMesh.chunkSetAngles("GearL2_D0", -24.0F * f2, 0.0F, 0.0F); } f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F; if (paramFloat >= 1.0F - f1) { paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 78.0F * f2, 0.0F); paramHierMesh.chunkSetAngles("GearR2_D0", 24.0F * f2, 0.0F, 0.0F); } if (paramFloat > 0.99F) { paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -78.0F, 0.0F); paramHierMesh.chunkSetAngles("GearL2_D0", -24.0F, 0.0F, 0.0F); paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 78.0F, 0.0F); paramHierMesh.chunkSetAngles("GearR2_D0", 24.0F, 0.0F, 0.0F); } if (paramFloat < 0.01F) { paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);

      paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F); paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F); paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
    }
  }

  protected void afterburnerhud()
  {
    if ((this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.isPlayers()) && (this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_EI_of_type_ComMaddoxIl2FmEnginesInterface.engines[0].getControlAfterburner())) HUD.logRightBottom("Start- und Notleistung ENABLED!");
  }

  protected void moveGear(float paramFloat)
  {
    float f1 = 0.9F - ((Wing)getOwner()).aircIndex(this) * 0.1F; float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F; if (paramFloat <= f1) { hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78.0F * f2, 0.0F); hierMesh().chunkSetAngles("GearL2_D0", -24.0F * f2, 0.0F, 0.0F); } f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F; if (paramFloat >= 1.0F - f1) { hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78.0F * f2, 0.0F); hierMesh().chunkSetAngles("GearR2_D0", 24.0F * f2, 0.0F, 0.0F); } if (paramFloat > 0.99F) { hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -78.0F, 0.0F); hierMesh().chunkSetAngles("GearL2_D0", -24.0F, 0.0F, 0.0F);

      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 78.0F, 0.0F); hierMesh().chunkSetAngles("GearR2_D0", 24.0F, 0.0F, 0.0F);
    }
  }

  public void moveSteering(float paramFloat)
  {
    if (paramFloat > 77.5F) { paramFloat = 77.5F; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.steerAngle = paramFloat; } if (paramFloat < -77.5F) { paramFloat = -77.5F; this.jdField_FM_of_type_ComMaddoxIl2FmFlightModel.jdField_Gears_of_type_ComMaddoxIl2FmGear.steerAngle = paramFloat; } hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = BF_109E3B.class; new NetAircraft.SPAWN(localClass); Property.set(localClass, "iconFar_shortClassName", "Bf109"); Property.set(localClass, "meshName", "3DO/Plane/Bf-109E-3/Bf109E3B.him"); Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01()); Property.set(localClass, "yearService", 1940.0F); Property.set(localClass, "yearExpired", 1944.0F); Property.set(localClass, "cockpitClass", new Class[] { CockpitBF_109Ee.class }); Property.set(localClass, "FlightModel", "FlightModels/Bf-109E-3B.fmd"); Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 1, 9, 3, 3, 3, 3, 3 }); Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_CANNON03", "_ExternalDev01", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });
    try { ArrayList localArrayList = new ArrayList(); Property.set(localClass, "weaponsList", localArrayList); HashMapInt localHashMapInt = new HashMapInt(); Property.set(localClass, "weaponsMap", localHashMapInt);

      int i = 11; Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; String str = "default"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 1000); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 1000); arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMGFFs", 60); arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMGFFs", 60); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = null; arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "1sc250"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 1000); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 1000); arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG17k", 750); arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG17k", 750); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(3, "BombGunSC250", 1); arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = null; arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "4sc50"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunMG17si", 1000); arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunMG17si", 1000); arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunMG17k", 750); arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunMG17k", 750); arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonETC50", 1); arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1); arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1); arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1); arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGunSC50", 1); localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot); str = "none"; arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i]; arrayOf_WeaponSlot[0] = null; arrayOf_WeaponSlot[1] = null; arrayOf_WeaponSlot[2] = null; arrayOf_WeaponSlot[3] = null; arrayOf_WeaponSlot[4] = null; arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(9, "PylonETC900", 1); arrayOf_WeaponSlot[6] = null; arrayOf_WeaponSlot[7] = null; arrayOf_WeaponSlot[8] = null; arrayOf_WeaponSlot[9] = null; arrayOf_WeaponSlot[10] = null; localArrayList.add(str); localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}