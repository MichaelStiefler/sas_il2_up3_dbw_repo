package com.maddox.il2.objects.vehicles.tanks;

import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.ai.ground.TgtVehicle;

public abstract class Tank
{
  static
  {
    new TankGeneric.SPAWN(Tank.HoRo_MG.class);
    new TankGeneric.SPAWN(Tank.Tatra_OA30.class);
    new TankGeneric.SPAWN(Tank.PzIIF.class);
    new TankGeneric.SPAWN(Tank.HaGo.class);
    new TankGeneric.SPAWN(Tank.HaGoRadio.class);
    new TankGeneric.SPAWN(Tank.ChiHa.class);
    new TankGeneric.SPAWN(Tank.ChiHa_alt.class);
    new TankGeneric.SPAWN(Tank.LT_35.class);
    new TankGeneric.SPAWN(Tank.LT_35Ro.class);
    new TankGeneric.SPAWN(Tank.LT_35Sl.class);
    new TankGeneric.SPAWN(Tank.LT_38.class);
    new TankGeneric.SPAWN(Tank.LT_38Sl.class);
    new TankGeneric.SPAWN(Tank.LT_40.class);
    new TankGeneric.SPAWN(Tank.LT_40MG.class);
    new TankGeneric.SPAWN(Tank.PzIIIG.class);
    new TankGeneric.SPAWN(Tank.PzIIIJ.class);
    new TankGeneric.SPAWN(Tank.PzIIIM.class);
    new TankGeneric.SPAWN(Tank.PzIIIN.class);
    new TankGeneric.SPAWN(Tank.TuranI.class);
    new TankGeneric.SPAWN(Tank.TuranII.class);
    new TankGeneric.SPAWN(Tank.PzIVE.class);
    new TankGeneric.SPAWN(Tank.PzIVF.class);
    new TankGeneric.SPAWN(Tank.PzIVF2.class);
    new TankGeneric.SPAWN(Tank.PzIVJ.class);
    new TankGeneric.SPAWN(Tank.PzVA.class);
    new TankGeneric.SPAWN(Tank.PzV_II.class);
    new TankGeneric.SPAWN(Tank.PzVIE.class);
    new TankGeneric.SPAWN(Tank.PzVIB.class);
    new TankGeneric.SPAWN(Tank.Maus.class);
    new TankGeneric.SPAWN(Tank.ZrinyiII.class);
    new TankGeneric.SPAWN(Tank.Hetzer.class);
    new TankGeneric.SPAWN(Tank.Jagdpanther.class);
    new TankGeneric.SPAWN(Tank.Ferdinand.class);
    new TankGeneric.SPAWN(Tank.StuGIIIG.class);
    new TankGeneric.SPAWN(Tank.StuGIV.class);
    new TankGeneric.SPAWN(Tank.MarderIII.class);
    new TankGeneric.SPAWN(Tank.Karl_600mm.class);
    new TankGeneric.SPAWN(Tank.HoRo.class);
    new TankGeneric.SPAWN(Tank.SdKfz251.class);
    new TankGeneric.SPAWN(Tank.DemagFlak.class);
    new TankGeneric.SPAWN(Tank.SdKfz6Flak37.class);
    new TankGeneric.SPAWN(Tank.OpelBlitzMaultierAA.class);
    new TankGeneric.SPAWN(Tank.Nimrod.class);
    new TankGeneric.SPAWN(Tank.Wirbelwind.class);
    new TankGeneric.SPAWN(Tank.Coelian.class);
    new TankGeneric.SPAWN(Tank.OpelBlitzMaultierRocket.class);
    new TankGeneric.SPAWN(Tank.BA_64.class);
    new TankGeneric.SPAWN(Tank.BA_10.class);
    new TankGeneric.SPAWN(Tank.BA_6.class);
    new TankGeneric.SPAWN(Tank.FAIM.class);
    new TankGeneric.SPAWN(Tank.LVT_2.class);
    new TankGeneric.SPAWN(Tank.M8_Greyhound.class);
    new TankGeneric.SPAWN(Tank.BT7.class);
    new TankGeneric.SPAWN(Tank.T40S.class);
    new TankGeneric.SPAWN(Tank.T60.class);
    new TankGeneric.SPAWN(Tank._7TP.class);
    new TankGeneric.SPAWN(Tank.M3A1Stuart.class);
    new TankGeneric.SPAWN(Tank.M5A1Stuart.class);
    new TankGeneric.SPAWN(Tank.T70M.class);
    new TankGeneric.SPAWN(Tank.T26_Early.class);
    new TankGeneric.SPAWN(Tank.T26_Late.class);
    new TankGeneric.SPAWN(Tank.ValentineII.class);
    new TankGeneric.SPAWN(Tank.MatildaMkII_UK.class);
    new TankGeneric.SPAWN(Tank.MatildaMkII_AU.class);
    new TankGeneric.SPAWN(Tank.M4A2.class);
    new TankGeneric.SPAWN(Tank.M4A2_US.class);
    new TankGeneric.SPAWN(Tank.M4A2_76W.class);
    new TankGeneric.SPAWN(Tank.M4A2_76W_US.class);
    new TankGeneric.SPAWN(Tank.T34.class);
    new TankGeneric.SPAWN(Tank.T44.class);
    new TankGeneric.SPAWN(Tank.T34_85.class);
    new TankGeneric.SPAWN(Tank.T28.class);
    new TankGeneric.SPAWN(Tank.KV1.class);
    new TankGeneric.SPAWN(Tank.IS2.class);
    new TankGeneric.SPAWN(Tank.IS3.class);
    new TankGeneric.SPAWN(Tank.T35.class);
    new TankGeneric.SPAWN(Tank.ZSU37.class);
    new TankGeneric.SPAWN(Tank.SU76M.class);
    new TankGeneric.SPAWN(Tank.SU85.class);
    new TankGeneric.SPAWN(Tank.SU100.class);
    new TankGeneric.SPAWN(Tank.ISU152.class);
    new TankGeneric.SPAWN(Tank.ZIS5_AA.class);
    new TankGeneric.SPAWN(Tank.MaximeGAZ.class);
    new TankGeneric.SPAWN(Tank.M3A1_APC.class);
    new TankGeneric.SPAWN(Tank.M16.class);
    new TankGeneric.SPAWN(Tank.M16_US.class);
    new TankGeneric.SPAWN(Tank.M3_75mm_GMC.class);
    new TankGeneric.SPAWN(M3Lee.class);
    new TankGeneric.SPAWN(Crusader.class);
    new TankGeneric.SPAWN(VickersMKVI.class);
    new TankGeneric.SPAWN(Fiat_M13.class);
    new TankGeneric.SPAWN(Fiat_M15.class);
    new TankGeneric.SPAWN(Fiat_M40.class);
    new TankGeneric.SPAWN(Fiat_M42.class);
    new TankGeneric.SPAWN(CrusaderAAA.class);
    new TankGeneric.SPAWN(HotchkissH35.class);
    new TankGeneric.SPAWN(RenaultR35.class);
    new TankGeneric.SPAWN(SomuaS35.class);
    new TankGeneric.SPAWN(AxisInfantry.class);
    new TankGeneric.SPAWN(AxisInfantryNW.class);
    new TankGeneric.SPAWN(AxisPnzrInfantry.class);
    new TankGeneric.SPAWN(AxisSMGInfantry.class);
    new TankGeneric.SPAWN(JapInfantry.class);
    new TankGeneric.SPAWN(JapInfantryNW.class);
    new TankGeneric.SPAWN(RusInfantry.class);
    new TankGeneric.SPAWN(RusSMGInfantry.class);
    new TankGeneric.SPAWN(RusInfantryNW.class);
    new TankGeneric.SPAWN(USInfantry.class);
    new TankGeneric.SPAWN(USInfantryNW.class);
    new TankGeneric.SPAWN(GBInfantry.class);
    new TankGeneric.SPAWN(GBInfantryNW.class);
    new TankGeneric.SPAWN(USBazooka.class);
    new TankGeneric.SPAWN(GBBazooka.class);
    new TankGeneric.SPAWN(RusBazooka.class);
    new TankGeneric.SPAWN(AxisBazooka.class);
    new TankGeneric.SPAWN(JapBazooka.class);
  }

  public static class CrusaderAAA extends TankGeneric
    implements TgtVehicle, TgtFlak
  {
  }

  public static class Fiat_M40 extends TankGeneric
    implements TgtTank
  {
  }

  public static class Fiat_M42 extends TankGeneric
    implements TgtTank
  {
  }

  public static class Fiat_M13 extends TankGeneric
    implements TgtTank
  {
  }

  public static class Fiat_M15 extends TankGeneric
    implements TgtTank
  {
  }

  public static class VickersMKVI extends TankGeneric
    implements TgtTank
  {
  }

  public static class Crusader extends TankGeneric
    implements TgtTank
  {
  }

  public static class HotchkissH35 extends TankGeneric
    implements TgtTank
  {
  }

  public static class RenaultR35 extends TankGeneric
    implements TgtTank
  {
  }

  public static class SomuaS35 extends TankGeneric
    implements TgtTank
  {
  }

  public static class M3Lee extends TankGeneric
    implements TgtTank
  {
  }

  public static class JapBazooka extends TankGeneric
    implements TgtTank
  {
  }

  public static class AxisBazooka extends TankGeneric
    implements TgtTank
  {
  }

  public static class RusBazooka extends TankGeneric
    implements TgtTank
  {
  }

  public static class GBBazooka extends TankGeneric
    implements TgtTank
  {
  }

  public static class USBazooka extends TankGeneric
    implements TgtTank
  {
  }

  public static class GBInfantryNW extends TankGeneric
    implements TgtTank
  {
  }

  public static class GBInfantry extends TankGeneric
    implements TgtTank
  {
  }

  public static class USInfantryNW extends TankGeneric
    implements TgtTank
  {
  }

  public static class USInfantry extends TankGeneric
    implements TgtTank
  {
  }

  public static class RusInfantryNW extends TankGeneric
    implements TgtTank
  {
  }

  public static class RusSMGInfantry extends TankGeneric
    implements TgtTank
  {
  }

  public static class RusInfantry extends TankGeneric
    implements TgtTank
  {
  }

  public static class JapInfantryNW extends TankGeneric
    implements TgtTank
  {
  }

  public static class JapInfantry extends TankGeneric
    implements TgtTank
  {
  }

  public static class AxisSMGInfantry extends TankGeneric
    implements TgtTank
  {
  }

  public static class AxisPnzrInfantry extends TankGeneric
    implements TgtTank
  {
  }

  public static class AxisInfantryNW extends TankGeneric
    implements TgtTank
  {
  }

  public static class AxisInfantry extends TankGeneric
    implements TgtTank
  {
  }
}