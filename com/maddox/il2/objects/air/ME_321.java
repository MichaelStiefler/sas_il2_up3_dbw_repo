package com.maddox.il2.objects.air;

import com.maddox.JGP.Point3d;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.ActorPos;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Turret;
import com.maddox.il2.objects.ActorLand;
import com.maddox.il2.objects.weapons.Bomb;
import com.maddox.il2.objects.weapons.BombWalterStarthilferakete;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class ME_321 extends Scheme0
  implements TypeGlider, TypeTransport, TypeBomber
{
  private Bomb[] booster = { null, null, null, null };

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (((paramActor instanceof ActorLand)) && (this.FM.getVertSpeed() > -10.0F)) return;
    super.msgCollision(paramActor, paramString1, paramString2);
  }

  public void doWoundPilot(int paramInt, float paramFloat)
  {
    switch (paramInt) {
    case 0:
      break;
    case 1:
      break;
    case 2:
      this.FM.turret[0].setHealth(paramFloat);
      break;
    case 3:
      this.FM.turret[1].setHealth(paramFloat);
      break;
    case 4:
      this.FM.turret[2].setHealth(paramFloat);
      break;
    case 5:
      this.FM.turret[3].setHealth(paramFloat);
    }
  }

  public void doMurderPilot(int paramInt) {
    switch (paramInt) {
    case 0:
      hierMesh().chunkVisible("Pilot1_D0", false);
      hierMesh().chunkVisible("Pilot1_D1", true);
      hierMesh().chunkVisible("Head1_D0", false);
      if (!hierMesh().isChunkVisible("Blister1_D0")) break;
      hierMesh().chunkVisible("Gore1_D0", true); break;
    case 1:
      hierMesh().chunkVisible("Pilot2_D0", false);
      hierMesh().chunkVisible("Pilot2_D1", true);
      if (!hierMesh().isChunkVisible("Blister1_D0")) break;
      hierMesh().chunkVisible("Gore2_D0", true);
    }
  }

  protected void hitBone(String paramString, Shot paramShot, Point3d paramPoint3d)
  {
    if ((paramString.startsWith("xpilot")) || (paramString.startsWith("xhead"))) {
      int i = 0;
      int j;
      if (paramString.endsWith("a")) {
        i = 1;
        j = paramString.charAt(6) - '1';
      } else if (paramString.endsWith("b")) {
        i = 2;
        j = paramString.charAt(6) - '1';
      } else {
        j = paramString.charAt(5) - '1';
      }
      hitFlesh(j, paramShot, i);
    }
  }

  public void destroy()
  {
    doCutBoosters();
    super.destroy();
  }

  protected void moveAileron(float paramFloat)
  {
  }

  protected void moveFlap(float paramFloat)
  {
    float f = -50.0F * paramFloat;
    hierMesh().chunkSetAngles("Flap01_D0", 0.0F, f, 0.0F);
    hierMesh().chunkSetAngles("Flap02_D0", 0.0F, f, 0.0F);
  }

  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    float f1 = -paramArrayOfFloat[0]; float f2 = paramArrayOfFloat[1];
    switch (paramInt) {
    case 0:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 1:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 30.0F) break; f2 = 30.0F; bool = false; break;
    case 2:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false; break;
    case 3:
      if (f1 < -45.0F) { f1 = -45.0F; bool = false; }
      if (f1 > 45.0F) { f1 = 45.0F; bool = false; }
      if (f2 < -30.0F) { f2 = -30.0F; bool = false; }
      if (f2 <= 60.0F) break; f2 = 60.0F; bool = false;
    }

    paramArrayOfFloat[0] = (-f1); paramArrayOfFloat[1] = f2;
    return bool;
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor)
  {
    switch (paramInt1) { case 33:
      return super.cutFM(34, paramInt2, paramActor);
    case 36:
      return super.cutFM(37, paramInt2, paramActor);
    }
    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  public void update(float paramFloat)
  {
    this.FM.CT.GearControl = 1.0F;
    this.FM.GearCX = 0.0F;
    this.FM.Gears.lgear = true;
    this.FM.Gears.rgear = true;
    super.update(paramFloat);
  }

  public void doCutCart()
  {
    hierMesh().chunkSetAngles("GearC2_D0", 0.0F, 0.0F, 2.0F);
    hierMesh().chunkSetAngles("GearL2_D0", 0.0F, 0.0F, 2.0F);
    hierMesh().chunkSetAngles("GearR2_D0", 0.0F, 0.0F, 2.0F);
    cut("Cart");
  }

  public void doFireBoosters()
  {
    Eff3DActor.New(this, findHook("_Booster1"), null, 1.0F, "3DO/Effects/Tracers/HydrogenRocket/rocket.eff", 30.0F);
    Eff3DActor.New(this, findHook("_Booster2"), null, 1.0F, "3DO/Effects/Tracers/HydrogenRocket/rocket.eff", 30.0F);
    Eff3DActor.New(this, findHook("_Booster3"), null, 1.0F, "3DO/Effects/Tracers/HydrogenRocket/rocket.eff", 30.0F);
    Eff3DActor.New(this, findHook("_Booster4"), null, 1.0F, "3DO/Effects/Tracers/HydrogenRocket/rocket.eff", 30.0F);
  }

  public void onAircraftLoaded()
  {
    super.onAircraftLoaded();
    for (int i = 0; i < 4; i++)
      try {
        this.booster[i] = new BombWalterStarthilferakete();
        this.booster[i].pos.setBase(this, findHook("_BoosterH" + (i + 1)), false);

        this.booster[i].pos.resetAsBase();

        this.booster[i].drawing(true);
      }
      catch (Exception localException)
      {
        debugprintln("Structure corrupt - can't hang Walter-Starthilferakete..");
      }
  }

  public void doCutBoosters()
  {
    for (int i = 0; i < 4; i++)
      if (this.booster[i] != null) {
        this.booster[i].start();
        this.booster[i] = null;
      }
  }

  public boolean typeBomberToggleAutomation()
  {
    return false;
  }

  public void typeBomberAdjDistanceReset()
  {
  }

  public void typeBomberAdjDistancePlus()
  {
  }

  public void typeBomberAdjDistanceMinus()
  {
  }

  public void typeBomberAdjSideslipReset()
  {
  }

  public void typeBomberAdjSideslipPlus() {
  }

  public void typeBomberAdjSideslipMinus() {
  }

  public void typeBomberAdjAltitudeReset() {
  }

  public void typeBomberAdjAltitudePlus() {
  }

  public void typeBomberAdjAltitudeMinus() {
  }

  public void typeBomberAdjSpeedReset() {
  }

  public void typeBomberAdjSpeedPlus() {
  }

  public void typeBomberAdjSpeedMinus() {
  }

  public void typeBomberUpdate(float paramFloat) {
  }

  public void typeBomberReplicateToNet(NetMsgGuaranted paramNetMsgGuaranted) throws IOException {
  }

  public void typeBomberReplicateFromNet(NetMsgInput paramNetMsgInput) throws IOException {
  }

  static {
    Class localClass = ME_321.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "iconFar_shortClassName", "Me-321");
    Property.set(localClass, "meshName", "3do/plane/Me-321/hier.him");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());
    Property.set(localClass, "originCountry", PaintScheme.countryGermany);

    Property.set(localClass, "yearService", 1941.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    Property.set(localClass, "FlightModel", "FlightModels/Me-321.fmd");

    Property.set(localClass, "gliderString", "3DO/Arms/TowCable/mono.sim");
    Property.set(localClass, "gliderBoostThrust", 1960.0F);
    Property.set(localClass, "gliderStringLength", 140.0F);
    Property.set(localClass, "gliderStringKx", 100.0F);
    Property.set(localClass, "gliderStringFactor", 1.89F);
    Property.set(localClass, "gliderCart", 1);
    Property.set(localClass, "gliderBoosters", 1);
    Property.set(localClass, "gliderFireOut", 30.0F);

    weaponTriggersRegister(localClass, new int[] { 10, 11, 12, 13, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_ExternalBomb01" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15t 750", "MGunMG15t 750", "MGunMG15t 1500", "MGunMG15t 1550", null });

    weaponsRegister(localClass, "none", new String[] { null, null, null, null, null });
  }
}