package com.maddox.il2.objects.air;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.engine.Eff3DActor;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.rts.Finger;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.util.ArrayList;

public class SEAGLADIATOR2 extends SEAGLADIATOR
{
  private float arrestor = 0.0F;

  public void msgCollision(Actor paramActor, String paramString1, String paramString2)
  {
    if (((!isNet()) || (!isNetMirror())) && (!paramString1.startsWith("Hook")))
      super.msgCollision(paramActor, paramString1, paramString2);
  }

  public void moveArrestorHook(float paramFloat) {
    hierMesh().chunkSetAngles("Hook1_D0", 0.0F, -57.0F * paramFloat, 0.0F);
    resetYPRmodifier();
    Aircraft.xyz[2] = (0.1385F * paramFloat);
    this.arrestor = paramFloat;
  }

  public void update(float paramFloat) {
    super.update(paramFloat);
    if (this.FM.CT.getArrestor() > 0.2F)
    {
      float f;
      if (this.FM.Gears.arrestorVAngle != 0.0F) {
        f = Aircraft.cvt(this.FM.Gears.arrestorVAngle, -50.0F, 7.0F, 1.0F, 0.0F);

        this.arrestor = (0.8F * this.arrestor + 0.2F * f);
        moveArrestorHook(this.arrestor);
      } else {
        f = -33.0F * this.FM.Gears.arrestorVSink / 57.0F;
        if ((f < 0.0F) && (this.FM.getSpeedKMH() > 60.0F)) {
          Eff3DActor.New(this, this.FM.Gears.arrestorHook, null, 1.0F, "3DO/Effects/Fireworks/04_Sparks.eff", 0.1F);
        }

        if ((f > 0.0F) && (this.FM.CT.getArrestor() < 0.95F))
          f = 0.0F;
        if (f > 0.2F)
          f = 0.2F;
        if (f > 0.0F)
          this.arrestor = (0.7F * this.arrestor + 0.3F * (this.arrestor + f));
        else
          this.arrestor = (0.3F * this.arrestor + 0.7F * (this.arrestor + f));
        if (this.arrestor < 0.0F)
          this.arrestor = 0.0F;
        else if (this.arrestor > 1.0F)
          this.arrestor = 1.0F;
        moveArrestorHook(this.arrestor);
      }
    }
  }

  protected boolean cutFM(int paramInt1, int paramInt2, Actor paramActor) {
    switch (paramInt1) {
    case 19:
      this.FM.CT.bHasArrestorControl = false;
    }

    return super.cutFM(paramInt1, paramInt2, paramActor);
  }

  static
  {
    Class localClass = SEAGLADIATOR2.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "Gladiator");
    Property.set(localClass, "meshName", "3DO/Plane/SeaGladiatorMkII(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeSEAGLAD());
    Property.set(localClass, "meshName_gb", "3DO/Plane/SeaGladiatorMkII(GB)/hier.him");

    Property.set(localClass, "PaintScheme_gb", new PaintSchemeSEAGLAD());
    Property.set(localClass, "yearService", 1939.0F);
    Property.set(localClass, "yearExpired", 1943.0F);
    Property.set(localClass, "FlightModel", "FlightModels/SeaGladiatorMkII.fmd");

    Property.set(localClass, "cockpitClass", CockpitSEAGLADIATOR.class);

    Property.set(localClass, "LOSElevation", 0.8472F);
    Property.set(localClass, "originCountry", PaintScheme.countryBritain);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 1, 1 });
    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04" });

    Aircraft.weaponsRegister(localClass, "default", new String[] { "MGunBrowning303sipzl 600", "MGunBrowning303sipzl 600", "MGunBrowning303k 400", "MGunBrowning303k 400" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 4;
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      String str = "default";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning303sipzl", 600);
      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning303sipzl", 600);
      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(1, "MGunBrowning303k", 400);
      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(1, "MGunBrowning303k", 400);
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = null;
      arrayOf_WeaponSlot[1] = null;
      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}