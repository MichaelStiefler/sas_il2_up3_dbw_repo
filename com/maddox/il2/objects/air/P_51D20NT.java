package com.maddox.il2.objects.air;

import com.maddox.JGP.Vector3d;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.fm.Controls;
import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.game.AircraftHotKeys;
import com.maddox.il2.game.HUD;
import com.maddox.il2.game.Main3D;
import com.maddox.il2.objects.Wreckage;
import com.maddox.rts.Finger;
import com.maddox.rts.NetMsgGuaranted;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.Property;
import com.maddox.util.HashMapInt;
import java.io.IOException;
import java.util.ArrayList;

public class P_51D20NT extends P_51Mustang
  implements TypeFighterAceMaker
{
  public int k14Mode = 0;
  public int k14WingspanType = 0;
  public float k14Distance = 200.0F;

  public boolean bHasBlister = true;
  private float fMaxKMHSpeedForOpenCanopy = 150.0F;

  public void moveCockpitDoor(float paramFloat) {
    resetYPRmodifier();
    Aircraft.xyz[1] = Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 0.55F);
    hierMesh().chunkSetLocate("Blister1_D0", Aircraft.xyz, Aircraft.ypr);
    float f = (float)Math.sin(Aircraft.cvt(paramFloat, 0.01F, 0.99F, 0.0F, 3.141593F));

    hierMesh().chunkSetAngles("Pilot1_D0", 0.0F, 0.0F, 9.0F * f);
    hierMesh().chunkSetAngles("Head1_D0", 12.0F * f, 0.0F, 0.0F);
    if (Config.isUSE_RENDER()) {
      if ((Main3D.cur3D().cockpits != null) && (Main3D.cur3D().cockpits[0] != null))
      {
        Main3D.cur3D().cockpits[0].onDoorMoved(paramFloat);
      }setDoorSnd(paramFloat);
    }
  }

  public void update(float paramFloat)
  {
    super.update(paramFloat);
    if ((this.FM.CT.getCockpitDoor() > 0.2D) && (this.bHasBlister) && (this.FM.getSpeedKMH() > this.fMaxKMHSpeedForOpenCanopy) && (hierMesh().chunkFindCheck("Blister1_D0") != -1))
    {
      try
      {
        if (this == World.getPlayerAircraft())
          ((CockpitP_51D20NTK14)Main3D.cur3D().cockpitCur).removeCanopy();
      } catch (Exception localException) {
      }
      hierMesh().hideSubTrees("Blister1_D0");
      Wreckage localWreckage = new Wreckage(this, hierMesh().chunkFind("Blister1_D0"));
      localWreckage.collide(false);
      Vector3d localVector3d = new Vector3d();
      localVector3d.set(this.FM.Vwld);
      localWreckage.setSpeed(localVector3d);
      this.bHasBlister = false;
      this.FM.CT.bHasCockpitDoorControl = false;
      this.FM.setGCenter(-0.3F);
    }
  }

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
    Class localClass = P_51D20NT.class;

    new NetAircraft.SPAWN(localClass);
    Property.set(localClass, "iconFar_shortClassName", "P-51");
    Property.set(localClass, "meshName", "3DO/Plane/P-51D-20NT(Multi1)/hier.him");

    Property.set(localClass, "PaintScheme", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_gb", "3DO/Plane/P-51D-20NT(GB)/hier.him");

    Property.set(localClass, "PaintScheme_gb", new PaintSchemeFMPar05());
    Property.set(localClass, "meshName_us", "3DO/Plane/P-51D-20NT(USA)/hier.him");

    Property.set(localClass, "PaintScheme_us", new PaintSchemeFMPar06());
    Property.set(localClass, "noseart", 1);
    Property.set(localClass, "yearService", 1944.0F);
    Property.set(localClass, "yearExpired", 1947.5F);
    Property.set(localClass, "FlightModel", "FlightModels/P-51D-20NT.fmd");
    Property.set(localClass, "cockpitClass", CockpitP_51D20NTK14.class);

    Property.set(localClass, "LOSElevation", 1.1188F);
    Aircraft.weaponTriggersRegister(localClass, new int[] { 0, 0, 0, 0, 0, 0, 9, 9, 9, 9, 3, 3, 9, 9, 2, 2, 2, 2, 2, 2, 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 });

    Aircraft.weaponHooksRegister(localClass, new String[] { "_MGUN01", "_MGUN02", "_MGUN03", "_MGUN04", "_MGUN05", "_MGUN06", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalBomb01", "_ExternalBomb02", "_ExternalDev02", "_ExternalDev03", "_ExternalRock01", "_ExternalRock02", "_ExternalRock03", "_ExternalRock04", "_ExternalRock05", "_ExternalRock06", "_ExternalDev05", "_ExternalDev06", "_ExternalDev07", "_ExternalDev08", "_ExternalDev09", "_ExternalDev10", "_ExternalDev11", "_ExternalDev12", "_ExternalDev13", "_ExternalDev14", "_ExternalRock07", "_ExternalRock08", "_ExternalRock09", "_ExternalRock10", "_ExternalRock11", "_ExternalRock12", "_ExternalRock13", "_ExternalRock14", "_ExternalRock15", "_ExternalRock16" });
    try
    {
      ArrayList localArrayList = new ArrayList();
      Property.set(localClass, "weaponsList", localArrayList);
      HashMapInt localHashMapInt = new HashMapInt();
      Property.set(localClass, "weaponsMap", localHashMapInt);
      int i = 40;
      String str = "default";
      Aircraft._WeaponSlot[] arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];

      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (int j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x1000";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun1000lbs", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x250_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);

      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x500_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x75Tank_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2New", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2New", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x110Tank_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank110gal2", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank110gal2", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x75Tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2New", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2New", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x108Tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank108gal2", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank108gal2", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x110Tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank110gal2", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank110gal2", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "6xM2_APIT_2x165Tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[3] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 270);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank165gal2", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank165gal2", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x250";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x500";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = null;
      arrayOf_WeaponSlot[9] = null;
      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x250_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun250lbs", 1);

      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x500_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = null;
      arrayOf_WeaponSlot[7] = null;
      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      arrayOf_WeaponSlot[11] = new Aircraft._WeaponSlot(3, "BombGun500lbs", 1);

      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x75Tank_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2New", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2New", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x110Tank_6x45";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank110gal2", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank110gal2", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[13] = new Aircraft._WeaponSlot(9, "PylonRO_4andHalfInch_3", 1);

      arrayOf_WeaponSlot[14] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[15] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[16] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[17] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[18] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[19] = new Aircraft._WeaponSlot(2, "RocketGun4andHalfInch", 1);

      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x75Tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2New", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank75gal2New", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x108Tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank108gal2", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank108gal2", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x110Tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank110gal2", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank110gal2", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);

      str = "4xM2_APIT_2x165Tank";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      arrayOf_WeaponSlot[0] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[1] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 400);

      arrayOf_WeaponSlot[2] = null;
      arrayOf_WeaponSlot[3] = null;
      arrayOf_WeaponSlot[4] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[5] = new Aircraft._WeaponSlot(0, "MGunBrowning50kAPIT", 500);

      arrayOf_WeaponSlot[6] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank165gal2", 1);

      arrayOf_WeaponSlot[7] = new Aircraft._WeaponSlot(9, "FuelTankGun_Tank165gal2", 1);

      arrayOf_WeaponSlot[8] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[9] = new Aircraft._WeaponSlot(9, "Pylon51Late", 1);

      arrayOf_WeaponSlot[10] = null;
      arrayOf_WeaponSlot[11] = null;
      arrayOf_WeaponSlot[12] = null;
      arrayOf_WeaponSlot[13] = null;
      arrayOf_WeaponSlot[14] = null;
      arrayOf_WeaponSlot[15] = null;
      arrayOf_WeaponSlot[16] = null;
      arrayOf_WeaponSlot[17] = null;
      arrayOf_WeaponSlot[18] = null;
      arrayOf_WeaponSlot[19] = null;
      arrayOf_WeaponSlot[20] = null;
      arrayOf_WeaponSlot[21] = null;
      arrayOf_WeaponSlot[22] = null;
      arrayOf_WeaponSlot[23] = null;
      arrayOf_WeaponSlot[24] = null;
      arrayOf_WeaponSlot[25] = null;
      arrayOf_WeaponSlot[26] = null;
      arrayOf_WeaponSlot[27] = null;
      arrayOf_WeaponSlot[28] = null;
      arrayOf_WeaponSlot[29] = null;
      arrayOf_WeaponSlot[30] = null;
      arrayOf_WeaponSlot[31] = null;
      arrayOf_WeaponSlot[32] = null;
      arrayOf_WeaponSlot[33] = null;
      arrayOf_WeaponSlot[34] = null;
      arrayOf_WeaponSlot[35] = null;
      arrayOf_WeaponSlot[36] = null;
      arrayOf_WeaponSlot[37] = null;
      arrayOf_WeaponSlot[38] = null;
      arrayOf_WeaponSlot[39] = null;
      for (j = 40; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
      str = "none";
      arrayOf_WeaponSlot = new Aircraft._WeaponSlot[i];
      for (j = 0; j < i; j++)
        arrayOf_WeaponSlot[j] = null;
      localArrayList.add(str);
      localHashMapInt.put(Finger.Int(str), arrayOf_WeaponSlot);
    }
    catch (Exception localException)
    {
    }
  }
}