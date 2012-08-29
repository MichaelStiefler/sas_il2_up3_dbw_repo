package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Landscape;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.Property;

public class BF_109Z extends SchemeZ
  implements TypeFighter
{
  private float kangle = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    float f = Aircraft.cvt(paramFloat, 0.05F, 0.49F, 0.0F, 1.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, -77.5F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL2_D0", -33.5F * f, 0.0F, 0.0F);
    f = Aircraft.cvt(paramFloat, 0.12F, 0.95F, 0.0F, 1.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, 77.5F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearL4_D0", 33.5F * f, 0.0F, 0.0F);
    f = Aircraft.cvt(paramFloat, 0.3F, 0.82F, 0.0F, 1.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 77.5F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 33.5F * f, 0.0F, 0.0F);
    f = Aircraft.cvt(paramFloat, 0.34F, 0.78F, 0.0F, 1.0F);
    paramHierMesh.chunkSetAngles("GearR4_D0", 0.0F, -77.5F * f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", -33.5F * f, 0.0F, 0.0F);
  }

  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat) {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
    hierMesh().chunkSetAngles("GearC3_D0", 0.0F, -paramFloat, 0.0F);
  }

  protected void moveElevator(float paramFloat) {
    hierMesh().chunkSetAngles("VatorR_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  protected void moveRudder(float paramFloat) {
    hierMesh().chunkSetAngles("Rudder1_D0", 0.0F, -30.0F * paramFloat, 0.0F);
    hierMesh().chunkSetAngles("Rudder2_D0", 0.0F, -30.0F * paramFloat, 0.0F);
  }

  public void update(float paramFloat) {
    hierMesh().chunkSetAngles("GearL6_D0", 0.0F, -this.FM.Gears.gWheelAngles[0], 0.0F);

    hierMesh().chunkSetAngles("GearR6_D0", 0.0F, -this.FM.Gears.gWheelAngles[1], 0.0F);

    hierMesh().chunkSetAngles("GearC4_D0", 0.0F, -this.FM.Gears.gWheelAngles[2], 0.0F);

    if (this.FM.getSpeed() > 5.0F) {
      hierMesh().chunkSetAngles("SlatL_D0", 0.0F, Aircraft.cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);

      hierMesh().chunkSetAngles("SlatR_D0", 0.0F, Aircraft.cvt(this.FM.getAOA(), 6.8F, 11.0F, 0.0F, 1.5F), 0.0F);
    }

    hierMesh().chunkSetAngles("Flap01L_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01U_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02L_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02U_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01L2_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap01U2_D0", 0.0F, 20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02L2_D0", 0.0F, -20.0F * this.kangle, 0.0F);
    hierMesh().chunkSetAngles("Flap02U2_D0", 0.0F, 20.0F * this.kangle, 0.0F);
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

  public void rareAction(float paramFloat, boolean paramBoolean) {
    super.rareAction(paramFloat, paramBoolean);
    if (paramBoolean) {
      if (this.FM.AS.astateTankStates[2] > 5)
        this.FM.AS.repairTank(2);
      if (this.FM.AS.astateTankStates[3] > 5)
        this.FM.AS.repairTank(3);
    }
    if (this.FM.getAltitude() < 3000.0F)
      hierMesh().chunkVisible("HMask1_D0", false);
    else
      hierMesh().chunkVisible("HMask1_D0", hierMesh().isChunkVisible("Head1_D0"));
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -45.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap03_D0", 0.0F, f, 0.0F);
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1) {
    case 3:
    case 4:
      return false;
    case 18:
      if (World.Rnd().nextFloat() >= 0.5F) break;
      cut("Keel2");
      if (World.Rnd().nextFloat() >= 0.5F) break;
      cut("Tail2"); break;
    case 13:
      cut("WingRIn");
      cut("Engine2");
      cut("Tail2");
      this.FM.cut(36, paramInt2, paramActor);
      this.FM.cut(4, paramInt2, paramActor);
      this.FM.cut(20, paramInt2, paramActor);
      if (World.Rnd().nextFloat() >= 0.5F) break;
      cut("StabR");
      this.FM.cut(18, paramInt2, paramActor);
      this.FM.cut(17, paramInt2, paramActor); break;
    case 19:
      cut("StabR");
      this.FM.cut(18, paramInt2, paramActor);
      this.FM.cut(17, paramInt2, paramActor);
      break;
    case 20:
      cut("StabR");
      this.FM.cut(18, paramInt2, paramActor);
      this.FM.cut(17, paramInt2, paramActor);
      break;
    case 36:
      cut("Engine2");
      cut("Nose");
      cut("Tail2");
      this.FM.cut(4, paramInt2, paramActor);
      this.FM.cut(13, paramInt2, paramActor);
      this.FM.cut(20, paramInt2, paramActor);
      break;
    case 9:
      cut("GearL4");
      break;
    case 10:
      cut("GearR4");
    case 5:
    case 6:
    case 7:
    case 8:
    case 11:
    case 12:
    case 14:
    case 15:
    case 16:
    case 17:
    case 21:
    case 22:
    case 23:
    case 24:
    case 25:
    case 26:
    case 27:
    case 28:
    case 29:
    case 30:
    case 31:
    case 32:
    case 33:
    case 34:
    case 35: } return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void doKillPilot(int paramInt)
  {
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Head1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("HMask1_D0", false);
      if (this.FM.AS.bIsAboutToBailout) break;
      if (hierMesh().isChunkVisible("Blister1_D0"))
        hierMesh().chunkVisible("Gore1_D0", true);
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  static
  {
    Class localClass = BF_109Z.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);
    Property.set(localClass, "iconFar_shortClassName", "Bf109");
    Property.set(localClass, "meshName", "3DO/Plane/Bf-109Z/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1944.5F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Bf-109Z.fmd");
    Property.set(localClass, "cockpitClass", CockpitBF_109Z.class);

    Property.set(localClass, "LOSElevation", 0.7498F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 1, 9, 9, 9, 3, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_CANNON01", "_CANNON02", "_CANNON03", "_CANNON05", "_CANNON04", "_ExternalDev01", "_ExternalDev03", "_ExternalDev02", "_ExternalBomb03", "_ExternalBomb03", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "mk103", new String[] { "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", "MGunMK103ki 35", "MGunMK108ki 35", null, "PylonMk103", null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "sc500", new String[] { "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", null, "PylonETC900", null, "BombGunSC500", null, null, null });

    Aircraft.weaponsRegister(localClass, "sc500sc250", new String[] { "MGunMK108ki 65", "MGunMK108ki 65", "MGunMK108ki 35", null, "MGunMK108ki 35", "PylonETC900", "PylonETC900", "PylonETC900", "BombGunSC500", "BombGunNull", "BombGunSC250", "BombGunSC250" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}