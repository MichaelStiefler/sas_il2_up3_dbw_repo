package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.CLASS;
import com.maddox.rts.Property;

public class BF_109K4C3 extends BF_109
  implements TypeBNZFighter
{
  private float kangle = 0.0F;

  protected void moveRudder(float paramFloat)
  {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Flettner1_D0", 0.0F, -45.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat) {
    if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);

      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
    }

    hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());

    if (this.kangle > 1.0F)
      this.kangle = 1.0F;
    super.update(paramFloat);
    if (this.FM.isPlayers()) {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("CF_D0", false);
      else {
        hierMesh().chunkVisible("CF_D0", true);
      }
    }
    if (this.FM.isPlayers()) {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("CF_D1", false);
      hierMesh().chunkVisible("CF_D2", false);
      hierMesh().chunkVisible("CF_D3", false);
    }

    if (this.FM.isPlayers()) {
      if (!Main3D.cur3D().isViewOutside())
        hierMesh().chunkVisible("Blister1_D0", false);
      else
        hierMesh().chunkVisible("Blister1_D0", true);
      Point3d localPoint3d = World.getPlayerAircraft().pos.getAbsPoint();
      if (localPoint3d.z - World.land().HQ(localPoint3d.x, localPoint3d.y) < 0.009999999776482582D)
        hierMesh().chunkVisible("CF_D0", true);
      if (this.FM.AS.bIsAboutToBailout)
        hierMesh().chunkVisible("Blister1_D0", false);
    }
  }

  public static void moveGear(HierMesh paramHierMesh, float paramFloat) {
    float f1 = 0.8F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;

    if ((paramFloat <= f1) || (paramFloat == 1.0F)) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;

    if (paramFloat >= 1.0F - f1) {
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
    }
    f2 = Math.max(-paramFloat * 1500.0F, -90.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 0.0F, -f2, 0.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, f2, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 90.0F * paramFloat, 0.0F, 0.0F);
    if (paramFloat > 0.99F) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
    }
    if (paramFloat < 0.01F) {
      paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 0.0F, 0.0F);
      paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 0.0F);
    }
  }

  protected void moveGear(float paramFloat) {
    float f1 = 0.9F - ((Wing)getOwner()).aircIndex(this) * 0.1F;
    float f2 = -0.5F * (float)Math.cos(paramFloat / f1 * 3.141592653589793D) + 0.5F;

    if ((paramFloat <= f1) || (paramFloat == 1.0F)) {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -33.5F * f2, 0.0F, 0.0F);
    }
    f2 = -0.5F * (float)Math.cos((paramFloat - (1.0F - f1)) / f1 * 3.141592653589793D) + 0.5F;

    if (paramFloat >= 1.0F - f1) {
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f2, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 33.5F * f2, 0.0F, 0.0F);
    }
    f2 = Math.max(-paramFloat * 1500.0F, -90.0F);
    hierMesh().chunkSetAngles("GearL4_D0", 0.0F, -f2, 0.0F);
    hierMesh().chunkSetAngles("GearR4_D0", 0.0F, f2, 0.0F);
    hierMesh().chunkSetAngles("GearC2_D0", 90.0F * paramFloat, 0.0F, 0.0F);
    if (paramFloat > 0.99F) {
      hierMesh().chunkSetAngles("GearL3_D0", 0.0F, -77.5F, 0.0F);
      hierMesh().chunkSetAngles("GearL2_D0", -33.5F, 0.0F, 0.0F);
      hierMesh().chunkSetAngles("GearR3_D0", 0.0F, 77.5F, 0.0F);
      hierMesh().chunkSetAngles("GearR2_D0", 33.5F, 0.0F, 0.0F);
    }
  }

  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() >= 0.98F)
      hierMesh().chunkSetAngles("GearC2_D0", 90.0F, -paramFloat, 0.0F);
  }

  static
  {
    Class localClass = CLASS.THIS();
    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Bf109");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-109K-4/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar06());
    Property.set(localClass, "yearService", 1945.0F);
    Property.set(localClass, "yearExpired", 1955.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Bf-109K-4-C3.fmd");

    Property.set(localClass, "cockpitClass", CockpitBF_109K4.class);

    Property.set(localClass, "LOSElevation", 0.7498F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 1, 1, 1, 1, 1, 9, 9, 9, 9, 3, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON04", "_CANNON05", "_ExternalDev01", "_ExternalDev01", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb04", "_ExternalBomb05" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", null, null, null, null, null, null, null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "R1-SC250", new String[] { "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", null, null, null, null, "PylonETC900", null, null, null, "BombGunSC250 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "R1-SC500", new String[] { "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", null, null, null, null, "PylonETC900", null, null, null, "BombGunSC500 1", null, null, null, null });

    Aircraft.weaponsRegister(localClass, "R3-DROPTANK", new String[] { "MGunMG131si 300", "MGunMG131si 300", null, "MGunMK108ki 65", null, null, null, null, "PylonETC900", "FuelTankGun_Type_D", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "R5-MK108", new String[] { "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", null, null, null, "MGunMK108kh 60", "MGunMK108kh 60", null, null, "PylonMk108", "PylonMk108", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "R6-MG151-20", new String[] { "MGunMG131si 300", "MGunMG131si 300", "MGunMK108ki 65", null, "MGunMG15120MGkh 135", "MGunMG15120MGkh 135", null, null, null, null, "PylonMG15120", "PylonMG15120", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null });
  }
}