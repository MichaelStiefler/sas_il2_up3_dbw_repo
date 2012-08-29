package com.maddox.il2.objects.air;

import com.maddox.il2.ai.RangeRandom;
import com.maddox.il2.ai.Shot;
import com.maddox.il2.ai.World;
import com.maddox.il2.fm.AircraftState;
import com.maddox.il2.fm.FlightModel;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import java.io.IOException;

public class JU_52_3MG4E extends JU_52
  implements TypeBomber
{
  public boolean turretAngles(int paramInt, float[] paramArrayOfFloat)
  {
    boolean bool = super.turretAngles(paramInt, paramArrayOfFloat);

    if (paramArrayOfFloat[0] < -50.0F) { paramArrayOfFloat[0] = -50.0F; bool = false;
    } else if (paramArrayOfFloat[0] > 50.0F) { paramArrayOfFloat[0] = 50.0F; bool = false; }
    float f = Math.abs(paramArrayOfFloat[0]);
    if (f < 20.0F) {
      if (paramArrayOfFloat[1] < -1.0F) { paramArrayOfFloat[1] = -1.0F; bool = false; }
    }
    else if (paramArrayOfFloat[1] < -5.0F) { paramArrayOfFloat[1] = -5.0F; bool = false;
    }
    if (paramArrayOfFloat[1] > 45.0F) { paramArrayOfFloat[1] = 45.0F; bool = false; }
    return bool;
  }

  public void msgShot(Shot paramShot)
  {
    setShot(paramShot);

    if ((paramShot.chunkName.startsWith("WingLIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitTank(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("WingRIn")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitTank(paramShot.initiator, 1, 1);
    if ((paramShot.chunkName.startsWith("Engine1")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F))
      this.FM.AS.hitEngine(paramShot.initiator, 0, 1);
    if ((paramShot.chunkName.startsWith("Engine2")) && 
      (World.Rnd().nextFloat(0.0F, 1.0F) < 0.1F)) {
      this.FM.AS.hitEngine(paramShot.initiator, 1, 1);
    }
    if ((this.FM.AS.astateEngineStates[0] > 2) && (this.FM.AS.astateEngineStates[1] > 2)) {
      this.FM.setCapableOfBMP(false, paramShot.initiator);
    }

    super.msgShot(paramShot);
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

  public void typeBomberAdjSideslipReset() {
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
    Class localClass = JU_52_3MG4E.class;
    new NetAircraft.SPAWN(localClass);

    Property.set(localClass, "FlightModel", "FlightModels/Ju-52_3mg4e.fmd");
    Property.set(localClass, "meshName", "3do/plane/Ju-52_3mg4e/hier.him");
    Property.set(localClass, "iconFar_shortClassName", "Ju-52");
    Property.set(localClass, "PaintScheme", new PaintSchemeBMPar02());

    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1945.5F);

    weaponTriggersRegister(localClass, new int[] { 10, 3 });
    weaponHooksRegister(localClass, new String[] { "_MGUN01", "_BombSpawn01" });

    weaponsRegister(localClass, "default", new String[] { "MGunMG15t 250", null });

    weaponsRegister(localClass, "18xPara", new String[] { "MGunMG15t 250", "BombGunPara 18" });

    weaponsRegister(localClass, "none", new String[] { null, null });
  }
}