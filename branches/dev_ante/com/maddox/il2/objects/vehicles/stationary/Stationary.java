package com.maddox.il2.objects.vehicles.stationary;

import com.maddox.il2.ai.ground.TgtFlak;
import com.maddox.il2.ai.ground.TgtTank;
import com.maddox.il2.ai.ground.TgtVehicle;
import com.maddox.il2.ai.ground.TypeRadar;
import com.maddox.il2.objects.vehicles.artillery.SArtillery;
import com.maddox.il2.objects.vehicles.artillery.STank;

public abstract class Stationary
{
  public static final int CHAIN_HOME_HIGH = 0;
  public static final int CHAIN_HOME_LOW = 1;

  static Class _mthclass$(String paramString)
  {
    try
    {
      return Class.forName(paramString);
    }
    catch (ClassNotFoundException localClassNotFoundException) {
    }
    throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
  }

  static
  {
    new RadarGeneric.SPAWN(RadarChainHomeHigh.class);
    new RadarGeneric.SPAWN(RadarChainHomeLow.class);
    new StationaryGeneric.SPAWN(Stationary.GAZ67.class);
    new StationaryGeneric.SPAWN(Stationary.GAZ67t.class);
    new StationaryGeneric.SPAWN(Stationary.GAZ_M1.class);
    new StationaryGeneric.SPAWN(Stationary.WillisMB.class);
    new StationaryGeneric.SPAWN(Stationary.WillisMB_US.class);
    new StationaryGeneric.SPAWN(Stationary.WillisMBt.class);
    new StationaryGeneric.SPAWN(Stationary.WillisMBt_US.class);
    new StationaryGeneric.SPAWN(Stationary.WillisMBtc_US.class);
    new StationaryGeneric.SPAWN(Stationary.StudebeckerTruck.class);
    new StationaryGeneric.SPAWN(Stationary.ZIS5_PC.class);
    new StationaryGeneric.SPAWN(Stationary.ZIS5_radio.class);
    new StationaryGeneric.SPAWN(Stationary.ZIS5_medic.class);
    new StationaryGeneric.SPAWN(Stationary.ZIS6_fuel.class);
    new StationaryGeneric.SPAWN(Stationary.Chevrolet_flatbed_US.class);
    new StationaryGeneric.SPAWN(Stationary.Chevrolet_medic_US.class);
    new StationaryGeneric.SPAWN(Stationary.Chevrolet_radio_US.class);
    new StationaryGeneric.SPAWN(Stationary.DiamondT_US.class);
    new StationaryGeneric.SPAWN(Stationary.M3_Halftrack.class);
    new StationaryGeneric.SPAWN(Stationary.M3_Halftrack_ppl.class);
    new StationaryGeneric.SPAWN(Stationary.Tractor_US.class);
    new StationaryGeneric.SPAWN(Stationary.DUKW.class);
    new StationaryGeneric.SPAWN(Stationary.Bulldozer_US.class);
    new StationaryGeneric.SPAWN(Stationary.Bus_US.class);
    new StationaryGeneric.SPAWN(Stationary.GAZ_55.class);
    new StationaryGeneric.SPAWN(Stationary.GAZ_Bus.class);
    new StationaryGeneric.SPAWN(Stationary.GAZ_AA.class);
    new StationaryGeneric.SPAWN(Stationary.GAZ_AAA.class);
    new StationaryGeneric.SPAWN(Stationary.Katyusha.class);
    new StationaryGeneric.SPAWN(Stationary.StudebeckerRocket.class);
    new StationaryGeneric.SPAWN(Stationary.BA_64.class);
    new StationaryGeneric.SPAWN(Stationary.BA_10.class);
    new StationaryGeneric.SPAWN(Stationary.BA_6.class);
    new StationaryGeneric.SPAWN(Stationary.FAIM.class);
    new StationaryGeneric.SPAWN(Stationary.LVT_2.class);
    new StationaryGeneric.SPAWN(Stationary.M8_Greyhound.class);
    new StationaryGeneric.SPAWN(Stationary.BT7.class);
    new StationaryGeneric.SPAWN(Stationary.T40S.class);
    new StationaryGeneric.SPAWN(Stationary.T60.class);
    new StationaryGeneric.SPAWN(Stationary._7TP.class);
    new StationaryGeneric.SPAWN(Stationary.M3A1Stuart.class);
    new StationaryGeneric.SPAWN(Stationary.M5A1Stuart.class);
    new StationaryGeneric.SPAWN(Stationary.T70M.class);
    new StationaryGeneric.SPAWN(Stationary.T26_Early.class);
    new StationaryGeneric.SPAWN(Stationary.T26_Late.class);
    new StationaryGeneric.SPAWN(Stationary.ValentineII.class);
    new StationaryGeneric.SPAWN(Stationary.MatildaMkII_UK.class);
    new StationaryGeneric.SPAWN(Stationary.MatildaMkII_AU.class);
    new StationaryGeneric.SPAWN(Stationary.M4A2.class);
    new StationaryGeneric.SPAWN(Stationary.M4A2_US.class);
    new StationaryGeneric.SPAWN(Stationary.M4A2_76W.class);
    new StationaryGeneric.SPAWN(Stationary.M4A2_76W_US.class);
    new StationaryGeneric.SPAWN(Stationary.T34.class);
    new StationaryGeneric.SPAWN(Stationary.T44.class);
    new StationaryGeneric.SPAWN(Stationary.T34_85.class);
    new StationaryGeneric.SPAWN(Stationary.T28.class);
    new StationaryGeneric.SPAWN(Stationary.KV1.class);
    new StationaryGeneric.SPAWN(Stationary.IS2.class);
    new StationaryGeneric.SPAWN(Stationary.IS3.class);
    new StationaryGeneric.SPAWN(Stationary.T35.class);
    new StationaryGeneric.SPAWN(Stationary.ZSU37.class);
    new StationaryGeneric.SPAWN(Stationary.SU76M.class);
    new StationaryGeneric.SPAWN(Stationary.SU85.class);
    new StationaryGeneric.SPAWN(Stationary.SU100.class);
    new StationaryGeneric.SPAWN(Stationary.ISU152.class);
    new StationaryGeneric.SPAWN(Stationary.ZIS5_AA.class);
    new StationaryGeneric.SPAWN(Stationary.MaximeGAZ.class);
    new StationaryGeneric.SPAWN(Stationary.M3A1_APC.class);
    new StationaryGeneric.SPAWN(Stationary.M16.class);
    new StationaryGeneric.SPAWN(Stationary.M16_US.class);
    new StationaryGeneric.SPAWN(Stationary.M3_75mm_GMC.class);
    new StationaryGeneric.SPAWN(Stationary.VW82.class);
    new StationaryGeneric.SPAWN(Stationary.VW82t.class);
    new StationaryGeneric.SPAWN(Stationary.OpelKadett.class);
    new StationaryGeneric.SPAWN(Stationary.Kurogane.class);
    new StationaryGeneric.SPAWN(Stationary.HoHa.class);
    new StationaryGeneric.SPAWN(Stationary.Truck_Type94.class);
    new StationaryGeneric.SPAWN(Stationary.OpelBlitz36S.class);
    new StationaryGeneric.SPAWN(Stationary.OpelBlitz6700A.class);
    new StationaryGeneric.SPAWN(Stationary.OpelBlitz6700A_fuel.class);
    new StationaryGeneric.SPAWN(Stationary.OpelBlitz6700A_medic.class);
    new StationaryGeneric.SPAWN(Stationary.OpelBlitz6700A_radio.class);
    new StationaryGeneric.SPAWN(BikeBMW.class);
    new StationaryGeneric.SPAWN(Stationary.Kettenkrad.class);
    new StationaryGeneric.SPAWN(Stationary.RSO.class);
    new StationaryGeneric.SPAWN(Stationary.OpelBlitzMaultier.class);
    new StationaryGeneric.SPAWN(Stationary.DemagD7.class);
    new StationaryGeneric.SPAWN(Stationary.SdKfz6.class);
    new StationaryGeneric.SPAWN(Stationary.Bicycle.class);
    new StationaryGeneric.SPAWN(Stationary.Motorcycle.class);
    new StationaryGeneric.SPAWN(Stationary.HoRo_MG.class);
    new StationaryGeneric.SPAWN(Stationary.Tatra_OA30.class);
    new StationaryGeneric.SPAWN(Stationary.PzIIF.class);
    new StationaryGeneric.SPAWN(Stationary.HaGo.class);
    new StationaryGeneric.SPAWN(Stationary.HaGoRadio.class);
    new StationaryGeneric.SPAWN(Stationary.ChiHa.class);
    new StationaryGeneric.SPAWN(Stationary.ChiHa_alt.class);
    new StationaryGeneric.SPAWN(Stationary.LT_35.class);
    new StationaryGeneric.SPAWN(Stationary.LT_35Ro.class);
    new StationaryGeneric.SPAWN(Stationary.LT_35Sl.class);
    new StationaryGeneric.SPAWN(Stationary.LT_38.class);
    new StationaryGeneric.SPAWN(Stationary.LT_38Sl.class);
    new StationaryGeneric.SPAWN(Stationary.LT_40.class);
    new StationaryGeneric.SPAWN(Stationary.LT_40MG.class);
    new StationaryGeneric.SPAWN(Stationary.PzIIIG.class);
    new StationaryGeneric.SPAWN(Stationary.PzIIIJ.class);
    new StationaryGeneric.SPAWN(Stationary.PzIIIM.class);
    new StationaryGeneric.SPAWN(Stationary.PzIIIN.class);
    new StationaryGeneric.SPAWN(Stationary.TuranI.class);
    new StationaryGeneric.SPAWN(Stationary.TuranII.class);
    new StationaryGeneric.SPAWN(Stationary.PzIVE.class);
    new StationaryGeneric.SPAWN(Stationary.PzIVF.class);
    new StationaryGeneric.SPAWN(Stationary.PzIVF2.class);
    new StationaryGeneric.SPAWN(Stationary.PzIVJ.class);
    new StationaryGeneric.SPAWN(Stationary.PzVA.class);
    new StationaryGeneric.SPAWN(Stationary.PzV_II.class);
    new StationaryGeneric.SPAWN(Stationary.PzVIE.class);
    new StationaryGeneric.SPAWN(Stationary.PzVIB.class);
    new StationaryGeneric.SPAWN(Stationary.Maus.class);
    new StationaryGeneric.SPAWN(Stationary.ZrinyiII.class);
    new StationaryGeneric.SPAWN(Stationary.Hetzer.class);
    new StationaryGeneric.SPAWN(Stationary.Jagdpanther.class);
    new StationaryGeneric.SPAWN(Stationary.Ferdinand.class);
    new StationaryGeneric.SPAWN(Stationary.StuGIIIG.class);
    new StationaryGeneric.SPAWN(Stationary.StuGIV.class);
    new StationaryGeneric.SPAWN(Stationary.MarderIII.class);
    new StationaryGeneric.SPAWN(Stationary.Karl_600mm.class);
    new StationaryGeneric.SPAWN(Stationary.HoRo.class);
    new StationaryGeneric.SPAWN(Stationary.SdKfz251.class);
    new StationaryGeneric.SPAWN(Stationary.DemagFlak.class);
    new StationaryGeneric.SPAWN(Stationary.SdKfz6Flak37.class);
    new StationaryGeneric.SPAWN(Stationary.OpelBlitzMaultierAA.class);
    new StationaryGeneric.SPAWN(Stationary.Nimrod.class);
    new StationaryGeneric.SPAWN(Stationary.Wirbelwind.class);
    new StationaryGeneric.SPAWN(Stationary.Coelian.class);
    new StationaryGeneric.SPAWN(Stationary.OpelBlitzMaultierRocket.class);
    new StationaryGeneric.SPAWN(Stationary.Zenit3K.class);
    new StationaryGeneric.SPAWN(Stationary.Zenit85mm_1939.class);
    new StationaryGeneric.SPAWN(Stationary.Flak18_88mm.class);
    new StationaryGeneric.SPAWN(Stationary.Type88_75mm_JA.class);
    new StationaryGeneric.SPAWN(Stationary.Bofors_40mm_UK.class);
    new StationaryGeneric.SPAWN(Stationary.Bofors_40mm_US.class);
    new StationaryGeneric.SPAWN(Stationary.StBofors_40mm_UK.class);
    new StationaryGeneric.SPAWN(Stationary.StBofors_40mm_US.class);
    new StationaryGeneric.SPAWN(Stationary.Maxime.class);
    new StationaryGeneric.SPAWN(Stationary.Maxime4.class);
    new StationaryGeneric.SPAWN(Stationary.Zenit25mm_1940.class);
    new StationaryGeneric.SPAWN(Stationary.Zenit61K.class);
    new StationaryGeneric.SPAWN(Stationary._50calMG_water_US.class);
    new StationaryGeneric.SPAWN(Stationary.Flak30_20mm.class);
    new StationaryGeneric.SPAWN(Stationary.Flak38_20mm.class);
    new StationaryGeneric.SPAWN(Stationary.Flak18_37mm.class);
    new StationaryGeneric.SPAWN(Stationary.Type98_20mm_JA.class);
    new StationaryGeneric.SPAWN(Stationary.Twin25mm_JA.class);
    new StationaryGeneric.SPAWN(Stationary.Trip25mm_JA.class);
    new StationaryGeneric.SPAWN(Stationary.DShKAA.class);
    new StationaryGeneric.SPAWN(Stationary.ML20.class);
    new StationaryGeneric.SPAWN(Stationary.M30_122mm.class);
    new StationaryGeneric.SPAWN(Stationary.Howitzer_150mm.class);
    new StationaryGeneric.SPAWN(Stationary.M2A1_105mm.class);
    new StationaryGeneric.SPAWN(Stationary.Type91_105mm.class);
    new StationaryGeneric.SPAWN(Stationary.ATG38_45mm.class);
    new StationaryGeneric.SPAWN(Stationary.ZIS3.class);
    new StationaryGeneric.SPAWN(Stationary.M5_75mm.class);
    new StationaryGeneric.SPAWN(Stationary.PaK35.class);
    new StationaryGeneric.SPAWN(Stationary.PaK38.class);
    new StationaryGeneric.SPAWN(Stationary.PaK40.class);
    new StationaryGeneric.SPAWN(Stationary.Type94_37mm_JA.class);
    new StationaryGeneric.SPAWN(Stationary.Type38_75mm.class);
    new StationaryGeneric.SPAWN(Stationary.BunkerA_gun.class);
    new StationaryGeneric.SPAWN(Stationary.BunkerA2_gun.class);
    new StationaryGeneric.SPAWN(Stationary.BunkerD_gun.class);
    new StationaryGeneric.SPAWN(Stationary.BunkerS2_gun.class);
    new StationaryGeneric.SPAWN(Stationary.MG42.class);
    new StationaryGeneric.SPAWN(Stationary.BunkerG1_gun.class);
    new StationaryGeneric.SPAWN(Stationary.BunkerR1_gun.class);
    new StationaryGeneric.SPAWN(Stationary.BunkerR2_gun.class);
    new StationaryGeneric.SPAWN(Stationary.DShK.class);
    new StationaryGeneric.SPAWN(Stationary._50calMG_US_M2HB.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon1.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon2.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon3.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon4.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon5.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon6.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon7.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon8.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon9.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon10.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon11.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon12.class);
    new StationaryGeneric.SPAWN(Stationary.Wagon13.class);
    new StationaryGeneric.SPAWN(VickersMKVI.class);
    new StationaryGeneric.SPAWN(Crusader.class);
    new StationaryGeneric.SPAWN(M3Lee.class);
    new StationaryGeneric.SPAWN(Fiat_M13.class);
    new StationaryGeneric.SPAWN(Fiat_M15.class);
    new StationaryGeneric.SPAWN(Fiat_M40.class);
    new StationaryGeneric.SPAWN(Fiat_M42.class);
    new StationaryGeneric.SPAWN(HotchkissH35.class);
    new StationaryGeneric.SPAWN(RenaultR35.class);
    new StationaryGeneric.SPAWN(SomuaS35.class);
    new StationaryGeneric.SPAWN(CrusaderAAA.class);
    new StationaryGeneric.SPAWN(Camion_SK.class);
    new StationaryGeneric.SPAWN(Citerne_POMP.class);
    new StationaryGeneric.SPAWN(Fourgon_POMP.class);
    new StationaryGeneric.SPAWN(Voit2P_Beige.class);
    new StationaryGeneric.SPAWN(Voit4P_Noire_R.class);
    new StationaryGeneric.SPAWN(Voit4P_Bleue.class);
    new StationaryGeneric.SPAWN(Bus_NAAFI.class);
    new StationaryGeneric.SPAWN(GuyOtterBl.class);
    new StationaryGeneric.SPAWN(GuyOtterBr.class);
    new StationaryGeneric.SPAWN(RAF_Bus.class);
    new StationaryGeneric.SPAWN(US_Bus.class);
    new StationaryGeneric.SPAWN(CoastBunker_PTO.class);
    new StationaryGeneric.SPAWN(CoastBunker_ETO1.class);
    new StationaryGeneric.SPAWN(CoastBunker_ETO2.class);
  }

  public static class Bus_NAAFI extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class GuyOtterBl extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class GuyOtterBr extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class RAF_Bus extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class US_Bus extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class Voit4P_Noire_R extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class Voit4P_Bleue extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class Voit2P_Beige extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class Fourgon_POMP extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class Citerne_POMP extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class Camion_SK extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class CoastBunker_PTO extends StationaryGeneric
    implements SArtillery
  {
  }

  public static class CoastBunker_ETO1 extends StationaryGeneric
    implements SArtillery
  {
  }

  public static class CoastBunker_ETO2 extends StationaryGeneric
    implements SArtillery
  {
  }

  public static class BikeBMW extends StationaryGeneric
    implements TgtVehicle
  {
  }

  public static class SomuaS35 extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class RenaultR35 extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class HotchkissH35 extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class Fiat_M42 extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class Fiat_M40 extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class Fiat_M15 extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class Fiat_M13 extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class M3Lee extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class Crusader extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class VickersMKVI extends StationaryGeneric
    implements TgtTank, STank
  {
  }

  public static class CrusaderAAA extends StationaryGeneric
    implements TgtTank, TgtFlak, STank
  {
  }

  public static class RadarChainHomeHigh extends RadarGeneric
    implements TypeRadar
  {
    private float Range;
    private int Type;

    public float getRange()
    {
      return this.Range;
    }

    public int getType()
    {
      return this.Type;
    }

    public RadarChainHomeHigh()
    {
      this.Range = 80000.0F;
      this.Type = 0;
    }
  }

  public static class RadarChainHomeLow extends RadarGeneric
    implements TypeRadar
  {
    private float Range;
    private int Type;

    public float getRange()
    {
      return this.Range;
    }

    public int getType()
    {
      return this.Type;
    }

    public RadarChainHomeLow()
    {
      this.Range = 30000.0F;
      this.Type = 1;
    }
  }
}