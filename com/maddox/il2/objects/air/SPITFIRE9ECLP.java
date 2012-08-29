package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class SPITFIRE9ECLP extends SPITFIRE9
  implements TypeFighterAceMaker
{
  public int k14Mode = 0;
  public int k14WingspanType = 0;
  public float k14Distance = 200.0F;

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
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + this.k14WingspanType);
  }

  public void typeFighterAceMakerAdjSideslipMinus()
  {
    this.k14WingspanType += 1;
    if (this.k14WingspanType > 9)
      this.k14WingspanType = 9;
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + this.k14WingspanType);
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
    Class localClass = SPITFIRE9ECLP.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Spit");
    Property.set(localClass, "meshName", "3DO/Plane/SpitfireMkIXeCLP(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar04());
    Property.set(localClass, "meshName_gb", "3DO/Plane/SpitfireMkIXeCLP(GB)/hier.him");

    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar04());
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1946.5F);
    Property.set(localClass, "FlightModel", "FlightModels/SpitfireLF_IXCclipped.fmd");

    Property.set(localClass, "cockpitClass", CockpitSpit9ECLP.class);

    Property.set(localClass, "LOSElevation", 0.5926F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 9, 9, 9, 3, 3, 9, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_ExternalDev08", "_ExternalDev02", "_ExternalDev03", "_ExternalBomb02", "_ExternalBomb03", "_ExternalDev01", "_ExternalBomb01" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "30gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit30", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "45gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit45", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "90gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit90", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "250lb", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null });

    Aircraft.weaponsRegister(localClass, "250lb30gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit30", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null });

    Aircraft.weaponsRegister(localClass, "250lb45gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit45", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null });

    Aircraft.weaponsRegister(localClass, "250lb90gal", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", "FuelTankGun_TankSpit90", "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", null, null });

    Aircraft.weaponsRegister(localClass, "500lb", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, null, null, null, null, "PylonSpitC", "BombGun500lbsE 1" });

    Aircraft.weaponsRegister(localClass, "500lb250lb", new String[] { "MGunBrowning50k 250", "MGunBrowning50k 250", "MGunHispanoMkIki 140", "MGunHispanoMkIki 140", null, "PylonSpitL", "PylonSpitR", "BombGun250lbsE 1", "BombGun250lbsE 1", "PylonSpitC", "BombGun500lbsE 1" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null });
  }
}