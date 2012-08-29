package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class ME_210CA1 extends ME_210
  implements TypeFighter, TypeBNZFighter, TypeStormovik, TypeStormovikArmored, TypeDiveBomber
{
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers())
      bChangedPit = true;
  }

  public boolean typeDiveBomberToggleAutomation() {
    return false;
  }

  public void typeDiveBomberAdjAltitudeReset()
  {
  }

  public void typeDiveBomberAdjAltitudePlus()
  {
  }

  public void typeDiveBomberAdjAltitudeMinus()
  {
  }

  public void typeDiveBomberAdjVelocityReset()
  {
  }

  public void typeDiveBomberAdjVelocityPlus()
  {
  }

  public void typeDiveBomberAdjVelocityMinus()
  {
  }

  public void typeDiveBomberAdjDiveAngleReset()
  {
  }

  public void typeDiveBomberAdjDiveAnglePlus()
  {
  }

  public void typeDiveBomberAdjDiveAngleMinus()
  {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted)
    throws IOException
  {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput)
    throws IOException
  {
  }

  static
  {
    Class localClass = ME_210CA1.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Me-210");
    Property.set(localClass, "meshName", "3DO/Plane/Me-210Ca-1/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "yearService", 1943.0F);
    Property.set(localClass, "yearExpired", 1945.5F);
    Property.set(localClass, "FlightModel", "FlightModels/Me-210Ca-1.fmd");
    Property.set(localClass, "cockpitClass", new Class[] { CockpitME_210CA1.class });

    Property.set(localClass, "LOSElevation", 0.66895F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1, 10, 10, 3, 3, 3 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_CANNON01", "_CANNON02", "_MGUN03", "_MGUN04", "_BombSpawn01", "_BombSpawn02", "_BombSpawn03" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", null, null, null });

    Aircraft.weaponsRegister(localClass, "2sc250", new String[] { "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunSC250", "BombGunSC250", null });

    Aircraft.weaponsRegister(localClass, "2ab250", new String[] { "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunAB250", "BombGunAB250", null });

    Aircraft.weaponsRegister(localClass, "2sc500", new String[] { "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunSC500", "BombGunSC500", null });

    Aircraft.weaponsRegister(localClass, "2ab500", new String[] { "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunAB500", "BombGunAB500", null });

    Aircraft.weaponsRegister(localClass, "2sd500", new String[] { "MGunMG17ki 505", "MGunMG17ki 500", "MGunMG15120k 325", "MGunMG15120k 325", "MGunMG131tj 500", "MGunMG131tj 500", "BombGunSD500", "BombGunSD500", null });

    Aircraft.weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null, null, null, null });
  }
}