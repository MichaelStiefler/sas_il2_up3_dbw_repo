package com.maddox.il2.objects.air;

import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class P_51D20NA extends P_51
  implements TypeFighterAceMaker
{
  public int k14Mode = 0;
  public int k14WingspanType = 0;
  public float k14Distance = 200.0F;

  public boolean typeFighterAceMakerToggleAutomation()
  {
    this.k14Mode += 1;
    if (this.k14Mode > 2) {
      this.k14Mode = 0;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerMode" + this.k14Mode);
    return true;
  }

  public void typeFighterAceMakerAdjDistanceReset() {
  }

  public void typeFighterAceMakerAdjDistancePlus() {
    this.k14Distance += 10.0F;
    if (this.k14Distance > 800.0F) {
      this.k14Distance = 800.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerInc");
  }

  public void typeFighterAceMakerAdjDistanceMinus() {
    this.k14Distance -= 10.0F;
    if (this.k14Distance < 200.0F) {
      this.k14Distance = 200.0F;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerDec");
  }

  public void typeFighterAceMakerAdjSideslipReset() {
  }

  public void typeFighterAceMakerAdjSideslipPlus() {
    this.k14WingspanType -= 1;
    if (this.k14WingspanType < 0) {
      this.k14WingspanType = 0;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + this.k14WingspanType);
  }

  public void typeFighterAceMakerAdjSideslipMinus() {
    this.k14WingspanType += 1;
    if (this.k14WingspanType > 9) {
      this.k14WingspanType = 9;
    }
    HUD.log(AircraftHotKeys.hudLogWeaponId, "K14AceMakerWing" + this.k14WingspanType);
  }

  public void typeFighterAceMakerReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
    paramNetMsgGuaranted.writeByte(this.k14Mode);
    paramNetMsgGuaranted.writeByte(this.k14WingspanType);
    paramNetMsgGuaranted.writeFloat(this.k14Distance);
  }

  public void typeFighterAceMakerReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
    this.k14Mode = paramNetMsgInput.readByte();
    this.k14WingspanType = paramNetMsgInput.readByte();
    this.k14Distance = paramNetMsgInput.readFloat();
  }

  static
  {
    Class localClass = P_51D20NA.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "P-51");
    Property.set(localClass, "meshNameDemo", "3DO/Plane/P-51D-20NA(USA)/hier.him");
    Property.set(localClass, "meshName", "3DO/Plane/P-51D-20NA(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-51D-20NA(USA)/hier.him");
    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());

    Property.set(localClass, "noseart", 1);

    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1947.5F);

    Property.set(localClass, "FlightModel", "FlightModels/P-51D-20.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_51D20K14.class);
    Property.set(localClass, "LOSElevation", 1.06935F);

    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 9, 9, 3, 3, 9, 9 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", null, null, null, null, null, null });

    Aircraft.weaponsRegister(localClass, "2x250", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun250lbs 1", "BombGun250lbs 1", null, null });

    Aircraft.weaponsRegister(localClass, "2x500", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun500lbs 1", "BombGun500lbs 1", null, null });

    Aircraft.weaponsRegister(localClass, "2x1000", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", "BombGun1000lbs 1", "BombGun1000lbs 1", null, null });

    Aircraft.weaponsRegister(localClass, "2xTank", new String[] { "MGunBrowning50k 400", "MGunBrowning50k 400", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "MGunBrowning50k 270", "PylonP51PLN2", "PylonP51PLN2", null, null, "FuelTankGun_Tank75gal2", "FuelTankGun_Tank75gal2" });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null, null, null, null });
  }
}