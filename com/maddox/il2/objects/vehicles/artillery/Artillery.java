package com.maddox.il2.objects.vehicles.artillery;

import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.ai.ground.TgtTank;

public abstract class Artillery
{
  static
  {
    new ArtilleryGeneric.SPAWN(Artillery.Zenit3K.class);
    new ArtilleryGeneric.SPAWN(Artillery.Zenit85mm_1939.class);
    new ArtilleryGeneric.SPAWN(Artillery.Flak18_88mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.Type88_75mm_JA.class);
    new ArtilleryGeneric.SPAWN(Artillery.Bofors_40mm_UK.class);
    new ArtilleryGeneric.SPAWN(Artillery.Bofors_40mm_US.class);
    new ArtilleryGeneric.SPAWN(Artillery.StBofors_40mm_UK.class);
    new ArtilleryGeneric.SPAWN(Artillery.StBofors_40mm_US.class);
    new ArtilleryGeneric.SPAWN(Artillery.Maxime.class);
    new ArtilleryGeneric.SPAWN(Artillery.Maxime4.class);
    new ArtilleryGeneric.SPAWN(Artillery.Zenit25mm_1940.class);
    new ArtilleryGeneric.SPAWN(Artillery.Zenit61K.class);
    new ArtilleryGeneric.SPAWN(Artillery._50calMG_water_US.class);
    new ArtilleryGeneric.SPAWN(Artillery.Flak30_20mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.Flak38_20mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.Flak18_37mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.Type98_20mm_JA.class);
    new ArtilleryGeneric.SPAWN(Artillery.Twin25mm_JA.class);
    new ArtilleryGeneric.SPAWN(Artillery.Trip25mm_JA.class);
    new ArtilleryGeneric.SPAWN(Artillery.DShKAA.class);
    new ArtilleryGeneric.SPAWN(Artillery.ML20.class);
    new ArtilleryGeneric.SPAWN(Artillery.M30_122mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.Howitzer_150mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.M2A1_105mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.Type91_105mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.ATG38_45mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.ZIS3.class);
    new ArtilleryGeneric.SPAWN(Artillery.M5_75mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.PaK35.class);
    new ArtilleryGeneric.SPAWN(Artillery.PaK38.class);
    new ArtilleryGeneric.SPAWN(Artillery.PaK40.class);
    new ArtilleryGeneric.SPAWN(Artillery.Type94_37mm_JA.class);
    new ArtilleryGeneric.SPAWN(Artillery.Type38_75mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.BunkerA_gun.class);
    new ArtilleryGeneric.SPAWN(Artillery.BunkerA2_gun.class);
    new ArtilleryGeneric.SPAWN(Artillery.BunkerD_gun.class);
    new ArtilleryGeneric.SPAWN(Artillery.BunkerS2_gun.class);
    new ArtilleryGeneric.SPAWN(Artillery.MG42.class);
    new ArtilleryGeneric.SPAWN(Artillery.BunkerG1_gun.class);
    new ArtilleryGeneric.SPAWN(Artillery.BunkerR1_gun.class);
    new ArtilleryGeneric.SPAWN(Artillery.BunkerR2_gun.class);
    new ArtilleryGeneric.SPAWN(Artillery.DShK.class);
    new ArtilleryGeneric.SPAWN(Artillery._50calMG_US_M2HB.class);
    new ArtilleryGeneric.SPAWN(Artillery.HoRo_MG.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIIF.class);
    new ArtilleryGeneric.SPAWN(Artillery.LT_35.class);
    new ArtilleryGeneric.SPAWN(Artillery.LT_35Ro.class);
    new ArtilleryGeneric.SPAWN(Artillery.LT_35Sl.class);
    new ArtilleryGeneric.SPAWN(Artillery.LT_38.class);
    new ArtilleryGeneric.SPAWN(Artillery.LT_38Sl.class);
    new ArtilleryGeneric.SPAWN(Artillery.LT_40.class);
    new ArtilleryGeneric.SPAWN(Artillery.LT_40MG.class);
    new ArtilleryGeneric.SPAWN(Artillery.HaGo.class);
    new ArtilleryGeneric.SPAWN(Artillery.HaGoRadio.class);
    new ArtilleryGeneric.SPAWN(Artillery.ChiHa.class);
    new ArtilleryGeneric.SPAWN(Artillery.ChiHa_alt.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIIIG.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIIIJ.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIIIM.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIIIN.class);
    new ArtilleryGeneric.SPAWN(Artillery.TuranI.class);
    new ArtilleryGeneric.SPAWN(Artillery.TuranII.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIVE.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIVF.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIVF2.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzIVJ.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzVA.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzV_II.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzVIE.class);
    new ArtilleryGeneric.SPAWN(Artillery.PzVIB.class);
    new ArtilleryGeneric.SPAWN(Artillery.Maus.class);
    new ArtilleryGeneric.SPAWN(Artillery.ZrinyiII.class);
    new ArtilleryGeneric.SPAWN(Artillery.Hetzer.class);
    new ArtilleryGeneric.SPAWN(Artillery.Jagdpanther.class);
    new ArtilleryGeneric.SPAWN(Artillery.Ferdinand.class);
    new ArtilleryGeneric.SPAWN(Artillery.StuGIIIG.class);
    new ArtilleryGeneric.SPAWN(Artillery.StuGIV.class);
    new ArtilleryGeneric.SPAWN(Artillery.MarderIII.class);
    new ArtilleryGeneric.SPAWN(Artillery.Karl_600mm.class);
    new ArtilleryGeneric.SPAWN(Artillery.HoRo.class);
    new ArtilleryGeneric.SPAWN(Artillery.SdKfz251.class);
    new ArtilleryGeneric.SPAWN(Artillery.DemagFlak.class);
    new ArtilleryGeneric.SPAWN(Artillery.SdKfz6Flak37.class);
    new ArtilleryGeneric.SPAWN(Artillery.OpelBlitzMaultierAA.class);
    new ArtilleryGeneric.SPAWN(Artillery.Nimrod.class);
    new ArtilleryGeneric.SPAWN(Artillery.Wirbelwind.class);
    new ArtilleryGeneric.SPAWN(Artillery.Coelian.class);
    new ArtilleryGeneric.SPAWN(Artillery.Tatra_OA30.class);
    new ArtilleryGeneric.SPAWN(Artillery.OpelBlitzMaultierRocket.class);
    new ArtilleryGeneric.SPAWN(Artillery.BA_64.class);
    new ArtilleryGeneric.SPAWN(Artillery.BA_10.class);
    new ArtilleryGeneric.SPAWN(Artillery.BA_6.class);
    new ArtilleryGeneric.SPAWN(Artillery.FAIM.class);
    new ArtilleryGeneric.SPAWN(Artillery.LVT_2.class);
    new ArtilleryGeneric.SPAWN(Artillery.M8_Greyhound.class);
    new ArtilleryGeneric.SPAWN(Artillery.BT7.class);
    new ArtilleryGeneric.SPAWN(Artillery.T40S.class);
    new ArtilleryGeneric.SPAWN(Artillery.T60.class);
    new ArtilleryGeneric.SPAWN(Artillery._7TP.class);
    new ArtilleryGeneric.SPAWN(Artillery.M3A1Stuart.class);
    new ArtilleryGeneric.SPAWN(Artillery.M5A1Stuart.class);
    new ArtilleryGeneric.SPAWN(Artillery.T70M.class);
    new ArtilleryGeneric.SPAWN(Artillery.T26_Early.class);
    new ArtilleryGeneric.SPAWN(Artillery.T26_Late.class);
    new ArtilleryGeneric.SPAWN(Artillery.ValentineII.class);
    new ArtilleryGeneric.SPAWN(Artillery.MatildaMkII_UK.class);
    new ArtilleryGeneric.SPAWN(Artillery.MatildaMkII_AU.class);
    new ArtilleryGeneric.SPAWN(Artillery.M4A2.class);
    new ArtilleryGeneric.SPAWN(Artillery.M4A2_US.class);
    new ArtilleryGeneric.SPAWN(Artillery.M4A2_76W.class);
    new ArtilleryGeneric.SPAWN(Artillery.M4A2_76W_US.class);
    new ArtilleryGeneric.SPAWN(Artillery.T34.class);
    new ArtilleryGeneric.SPAWN(Artillery.T44.class);
    new ArtilleryGeneric.SPAWN(Artillery.T34_85.class);
    new ArtilleryGeneric.SPAWN(Artillery.T28.class);
    new ArtilleryGeneric.SPAWN(Artillery.KV1.class);
    new ArtilleryGeneric.SPAWN(Artillery.IS2.class);
    new ArtilleryGeneric.SPAWN(Artillery.IS3.class);
    new ArtilleryGeneric.SPAWN(Artillery.T35.class);
    new ArtilleryGeneric.SPAWN(Artillery.ZSU37.class);
    new ArtilleryGeneric.SPAWN(Artillery.SU76M.class);
    new ArtilleryGeneric.SPAWN(Artillery.SU85.class);
    new ArtilleryGeneric.SPAWN(Artillery.SU100.class);
    new ArtilleryGeneric.SPAWN(Artillery.ISU152.class);
    new ArtilleryGeneric.SPAWN(Artillery.Katyusha.class);
    new ArtilleryGeneric.SPAWN(Artillery.StudebeckerRocket.class);
    new ArtilleryGeneric.SPAWN(Artillery.ZIS5_AA.class);
    new ArtilleryGeneric.SPAWN(Artillery.MaximeGAZ.class);
    new ArtilleryGeneric.SPAWN(Artillery.M3A1_APC.class);
    new ArtilleryGeneric.SPAWN(Artillery.M16.class);
    new ArtilleryGeneric.SPAWN(Artillery.M16_US.class);
    new ArtilleryGeneric.SPAWN(Artillery.M3_75mm_GMC.class);
    new ArtilleryGeneric.SPAWN(M3Lee.class);
    new ArtilleryGeneric.SPAWN(VickersMKVI.class);
    new ArtilleryGeneric.SPAWN(Crusader.class);
    new ArtilleryGeneric.SPAWN(Fiat_M13.class);
    new ArtilleryGeneric.SPAWN(Fiat_M15.class);
    new ArtilleryGeneric.SPAWN(Fiat_M40.class);
    new ArtilleryGeneric.SPAWN(Fiat_M42.class);
    new ArtilleryGeneric.SPAWN(HotchkissH35.class);
    new ArtilleryGeneric.SPAWN(RenaultR35.class);
    new ArtilleryGeneric.SPAWN(SomuaS35.class);
    new ArtilleryGeneric.SPAWN(CrusaderAAA.class);
    new ArtilleryGeneric.SPAWN(AxisInfantry.class);
    new ArtilleryGeneric.SPAWN(AxisPnzrInfantry.class);
    new ArtilleryGeneric.SPAWN(RusInfantry.class);
    new ArtilleryGeneric.SPAWN(JapInfantry.class);
    new ArtilleryGeneric.SPAWN(USInfantry.class);
    new ArtilleryGeneric.SPAWN(GBInfantry.class);
    new ArtilleryGeneric.SPAWN(AxisBazooka.class);
    new ArtilleryGeneric.SPAWN(JapBazooka.class);
    new ArtilleryGeneric.SPAWN(USBazooka.class);
    new ArtilleryGeneric.SPAWN(GBBazooka.class);
    new ArtilleryGeneric.SPAWN(RusBazooka.class);
    new ArtilleryGeneric.SPAWN(CoastBunker_PTO.class);
    new ArtilleryGeneric.SPAWN(CoastBunker_ETO1.class);
    new ArtilleryGeneric.SPAWN(CoastBunker_ETO2.class);
  }

  public static class AxisInfantry extends ArtilleryGeneric
  {
  }

  public static class AxisPnzrInfantry extends ArtilleryGeneric
  {
  }

  public static class AxisBazooka extends ArtilleryGeneric
  {
  }

  public static class JapBazooka extends ArtilleryGeneric
  {
  }

  public static class JapInfantry extends ArtilleryGeneric
  {
  }

  public static class RusInfantry extends ArtilleryGeneric
  {
  }

  public static class USInfantry extends ArtilleryGeneric
  {
  }

  public static class GBInfantry extends ArtilleryGeneric
  {
  }

  public static class CoastBunker_PTO extends ArtilleryGeneric
  {
  }

  public static class CoastBunker_ETO1 extends ArtilleryGeneric
  {
  }

  public static class CoastBunker_ETO2 extends ArtilleryGeneric
  {
  }

  public static class RusBazooka extends ArtilleryGeneric
  {
  }

  public static class GBBazooka extends ArtilleryGeneric
  {
  }

  public static class USBazooka extends ArtilleryGeneric
  {
  }

  public static class M3Lee extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class VickersMKVI extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class Crusader extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class Fiat_M13 extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class Fiat_M15 extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class Fiat_M40 extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class Fiat_M42 extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class HotchkissH35 extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class RenaultR35 extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class SomuaS35 extends ArtilleryGeneric
    implements TgtTank, STank
  {
  }

  public static class CrusaderAAA extends ArtilleryGeneric
    implements TgtTank, TgtFlak, STank
  {
  }
}