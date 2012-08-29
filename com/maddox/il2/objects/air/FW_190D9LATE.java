package com.maddox.il2.objects.air;

import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Motor;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class FW_190D9LATE extends FW_190
  implements TypeFighterAceMaker
{
  public int k14Mode = 0;
  public int k14WingspanType = 0;
  public float k14Distance = 200.0F;
  private float kangle = 0.0F;

  public static void moveGear(HierMesh paramHierMesh, float paramFloat)
  {
    paramHierMesh.chunkSetAngles("GearL2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR2_D0", 0.0F, 77.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearL3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearR3_D0", 0.0F, 157.0F * paramFloat, 0.0F);
    paramHierMesh.chunkSetAngles("GearC99_D0", 20.0F * paramFloat, 0.0F, 0.0F);
    paramHierMesh.chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 0.0F);
    float f = Math.max(-paramFloat * 1500.0F, -94.0F);
    paramHierMesh.chunkSetAngles("GearL5_D0", 0.0F, -f, 0.0F);
    paramHierMesh.chunkSetAngles("GearR5_D0", 0.0F, -f, 0.0F);
  }

  protected void moveGear(float paramFloat) {
    moveGear(hierMesh(), paramFloat);
  }

  public void moveSteering(float paramFloat) {
    if (this.FM.CT.getGear() >= 0.98F)
      hierMesh().chunkSetAngles("GearC2_D0", 0.0F, -paramFloat, 0.0F);
  }

  public void update(float paramFloat) {
    for (int i = 1; i < 13; i++) {
      hierMesh().chunkSetAngles("Water" + i + "_D0", 0.0F, -10.0F * this.kangle, 0.0F);
    }
    this.kangle = (0.95F * this.kangle + 0.05F * this.FM.EI.engines[0].getControlRadiator());

    super.update(paramFloat);
  }

  public boolean typeFighterAceMakerToggleAutomation() {
    this.k14Mode += 1;
    if (this.k14Mode > 2)
      this.k14Mode = 0;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + this.k14Mode);
    return true;
  }

  public void typeFighterAceMakerAdjDistanceReset()
  {
  }

  public void typeFighterAceMakerAdjDistancePlus() {
    this.k14Distance += 10.0F;
    if (this.k14Distance > 800.0F)
      this.k14Distance = 800.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
  }

  public void typeFighterAceMakerAdjDistanceMinus() {
    this.k14Distance -= 10.0F;
    if (this.k14Distance < 200.0F)
      this.k14Distance = 200.0F;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
  }

  public void typeFighterAceMakerAdjSideslipReset()
  {
  }

  public void typeFighterAceMakerAdjSideslipPlus() {
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

  public void typeFighterAceMakerReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException
  {
    this.k14Mode = paramNetMsgInput.readByte();
    this.k14WingspanType = paramNetMsgInput.readByte();
    this.k14Distance = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = FW_190D9LATE.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "FW190");
    Property.set(localClass, "meshName", "3DO/Plane/Fw-190D-9(Beta)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1944.6F);
    Property.set(localClass, "yearExpired", 1948.0F);
    Property.set(localClass, "FlightModel", "FlightModels/Fw-190D-9Late.fmd");

    Property.set(localClass, "cockpitClass", CockpitFW_190D9LATE.class);

    Property.set(localClass, "LOSElevation", 0.764106F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON03", "_CANNON04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG131si 750", "MGunMG131si 750", "MGunMG15120MGs 250", "MGunMG15120MGs 250" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null });
  }
}