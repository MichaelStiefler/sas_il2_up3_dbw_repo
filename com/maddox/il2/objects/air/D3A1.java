package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.Main3D;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class D3A1 extends D3A
  implements TypeDiveBomber
{
  public static boolean bChangedPit = false;

  protected void nextDMGLevel(String paramString, int paramInt, Actor paramActor)
  {
    super.nextDMGLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  protected void nextCUTLevel(String paramString, int paramInt, Actor paramActor) {
    super.nextCUTLevel(paramString, paramInt, paramActor);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  public void doWoundPilot(int paramInt, float paramFloat) {
    super.doWoundPilot(paramInt, paramFloat);
    if (this.FM.isPlayers()) bChangedPit = true; 
  }

  public void doMurderPilot(int paramInt) {
    super.doMurderPilot(paramInt);
    if (this.FM.isPlayers()) bChangedPit = true;
  }

  public void moveCockpitDoor(float paramFloat)
  {
    resetYPRmodifier();
    xyz[1] = cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.44F);
    hierMesh().chunkSetLocate("Blister1_D0", xyz, ypr);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null)) Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      setDoorSnd(paramFloat);
    }
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -33.0F) { f1 = -33.0F; bool = false; }
      if (f1 > 33.0F) { f1 = 33.0F; bool = false; }
      if (f2 < -3.0F) { f2 = -3.0F; bool = false; }
      if (f2 <= 62.0F) break; f2 = 62.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  public boolean typeDiveBomberToggleAutomation()
  {
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

  public void typeDiveBomberAdjVelocityPlus() {
  }

  public void typeDiveBomberAdjVelocityMinus() {
  }

  public void typeDiveBomberAdjDiveAngleReset() {
  }

  public void typeDiveBomberAdjDiveAnglePlus() {
  }

  public void typeDiveBomberAdjDiveAngleMinus() {
  }

  public void typeDiveBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeDiveBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = D3A1.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "D3A");
    Property.set(localClass, "meshName", "3DO/Plane/D3A1(Multi1)/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar01());
    Property.set(localClass, "meshName_ja", "3DO/Plane/D3A1(ja)/hier.him");
    Property.set(localClass, "PaintScheme_ja", new PaintSchemeBCSPar01());

    Property.set(localClass, "yearService", 1940.0F);
    Property.set(localClass, "yearExpired", 1946.5F);

    Property.set(localClass, "FlightModel", "FlightModels/D3A1.fmd");
    Property.set(localClass, "cockpitClass", CockpitD3A1.class);
    Property.set(localClass, "cockpitClass", new Class[] { CockpitD3A1.class, CockpitD3A1_TGunner.class });

    Property.set(localClass, "LOSElevation", 0.87195F);

    weaponTriggersRegister(localClass, new int[] { 0, 0, 10, 3, 3, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_ExternalBomb02", "_ExternalBomb03", "_ExternalBomb01" });

    weaponsRegister(localClass, "default", new String[] { "MGunVikkersKsi 600", "MGunVikkersKsi 600", "MGunVikkersKt 600", null, null, null });

    weaponsRegister(localClass, "1x250", new String[] { "MGunVikkersKsi 600", "MGunVikkersKsi 600", "MGunVikkersKt 600", null, null, "BombGun250kgJ 1" });

    weaponsRegister(localClass, "1x2502x30", new String[] { "MGunVikkersKsi 600", "MGunVikkersKsi 600", "MGunVikkersKt 600", "BombGun30kgJ 1", "BombGun30kgJ 1", "BombGun250kgJ 1" });

    weaponsRegister(localClass, "1x2502x60", new String[] { "MGunVikkersKsi 600", "MGunVikkersKsi 600", "MGunVikkersKt 600", "BombGun60kgJ 1", "BombGun60kgJ 1", "BombGun250kgJ 1" });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null, null });
  }
}