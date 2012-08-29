package com.maddox.il2.objects;

import com.maddox.il2.ai.ScoreCounter;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.il2.objects.air.BF_109G6GRAF;
import com.maddox.il2.objects.air.BF_109G6HARTMANN;
import com.maddox.il2.objects.air.I_16TYPE24SAFONOV;
import com.maddox.il2.objects.air.ME_262A1ANOWOTNY;
import com.maddox.il2.objects.air.MIG_3POKRYSHKIN;
import com.maddox.il2.objects.air.P_39NPOKRYSHKIN;
import com.maddox.il2.objects.air.P_39Q15RECHKALOV;
import com.maddox.il2.objects.air.Scheme1;
import com.maddox.il2.objects.air.Scheme2;
import com.maddox.il2.objects.air.Scheme3;
import com.maddox.il2.objects.air.Scheme4;
import com.maddox.il2.objects.air.Scheme5;
import com.maddox.il2.objects.air.Scheme6;
import com.maddox.il2.objects.air.Scheme7;
import com.maddox.il2.objects.air.YAK_9TALBERT;
import com.maddox.il2.objects.bridges.LongBridge;
import com.maddox.il2.objects.ships.BigshipGeneric;
import com.maddox.il2.objects.ships.Ship.Aurora;
import com.maddox.il2.objects.ships.Ship.DUKW_WAT;
import com.maddox.il2.objects.ships.Ship.DaihatsuLC;
import com.maddox.il2.objects.ships.Ship.Destroyer_USSR_Type7;
import com.maddox.il2.objects.ships.Ship.Destroyer_USSR_Type7_44;
import com.maddox.il2.objects.ships.Ship.HMSDukeOfYorkBB;
import com.maddox.il2.objects.ships.Ship.HMSIllustriousCV;
import com.maddox.il2.objects.ships.Ship.HMSKingGeorgeVBB;
import com.maddox.il2.objects.ships.Ship.HMSPoWBB;
import com.maddox.il2.objects.ships.Ship.IJNAkagiCV;
import com.maddox.il2.objects.ships.Ship.IJNAkizukiDD42;
import com.maddox.il2.objects.ships.Ship.IJNAmatsukazeDD41;
import com.maddox.il2.objects.ships.Ship.IJNAmatsukazeDD43;
import com.maddox.il2.objects.ships.Ship.IJNAmatsukazeDD45;
import com.maddox.il2.objects.ships.Ship.IJNArashiDD41;
import com.maddox.il2.objects.ships.Ship.IJNBBGeneric;
import com.maddox.il2.objects.ships.Ship.IJNCVGeneric;
import com.maddox.il2.objects.ships.Ship.IJNFishJunk;
import com.maddox.il2.objects.ships.Ship.IJNFishJunkA;
import com.maddox.il2.objects.ships.Ship.IJNKageroDD41;
import com.maddox.il2.objects.ships.Ship.IJNNowakiDD41;
import com.maddox.il2.objects.ships.Ship.IJNNowakiDD43;
import com.maddox.il2.objects.ships.Ship.IJNShokakuCV;
import com.maddox.il2.objects.ships.Ship.IJNYukikazeDD41;
import com.maddox.il2.objects.ships.Ship.IJNYukikazeDD43;
import com.maddox.il2.objects.ships.Ship.IJNYukikazeDD45;
import com.maddox.il2.objects.ships.Ship.IJNZuikakuCV;
import com.maddox.il2.objects.ships.Ship.Illmarinen;
import com.maddox.il2.objects.ships.Ship.Kirov;
import com.maddox.il2.objects.ships.Ship.LCVP;
import com.maddox.il2.objects.ships.Ship.LVT_2WAT;
import com.maddox.il2.objects.ships.Ship.MBoat;
import com.maddox.il2.objects.ships.Ship.Marat;
import com.maddox.il2.objects.ships.Ship.Murgesku;
import com.maddox.il2.objects.ships.Ship.Niobe;
import com.maddox.il2.objects.ships.Ship.Tanker;
import com.maddox.il2.objects.ships.Ship.Tral;
import com.maddox.il2.objects.ships.Ship.Tramp;
import com.maddox.il2.objects.ships.Ship.USSBBGeneric;
import com.maddox.il2.objects.ships.Ship.USSCVGeneric;
import com.maddox.il2.objects.ships.Ship.USSCasablancaCVE55;
import com.maddox.il2.objects.ships.Ship.USSDentDD116;
import com.maddox.il2.objects.ships.Ship.USSEssexCV9;
import com.maddox.il2.objects.ships.Ship.USSFletcherDD445;
import com.maddox.il2.objects.ships.Ship.USSGatoSS212_Srf;
import com.maddox.il2.objects.ships.Ship.USSGatoSS212_Sub;
import com.maddox.il2.objects.ships.Ship.USSGreenlingSS213_Srf;
import com.maddox.il2.objects.ships.Ship.USSGreenlingSS213_Sub;
import com.maddox.il2.objects.ships.Ship.USSIndianapolisCA35;
import com.maddox.il2.objects.ships.Ship.USSIntrepidCV11;
import com.maddox.il2.objects.ships.Ship.USSKiddDD661;
import com.maddox.il2.objects.ships.Ship.USSKitkunBayCVE71;
import com.maddox.il2.objects.ships.Ship.USSLexingtonCV2;
import com.maddox.il2.objects.ships.Ship.USSOBannonDD450;
import com.maddox.il2.objects.ships.Ship.USSSaratogaCV3;
import com.maddox.il2.objects.ships.Ship.USSShamrockBayCVE84;
import com.maddox.il2.objects.ships.Ship.USSWardDD139;
import com.maddox.il2.objects.ships.Ship.Vainamoinen;
import com.maddox.il2.objects.ships.ShipGeneric;
import com.maddox.il2.objects.trains.Wagon;
import com.maddox.il2.objects.vehicles.artillery.AAA;
import com.maddox.il2.objects.vehicles.artillery.Artillery.IS2;
import com.maddox.il2.objects.vehicles.artillery.Artillery.KV1;
import com.maddox.il2.objects.vehicles.artillery.Artillery.PzVA;
import com.maddox.il2.objects.vehicles.artillery.Artillery.PzVIB;
import com.maddox.il2.objects.vehicles.artillery.Artillery.PzVIE;
import com.maddox.il2.objects.vehicles.artillery.Artillery.T34;
import com.maddox.il2.objects.vehicles.artillery.Artillery.T34_85;
import com.maddox.il2.objects.vehicles.artillery.Artillery.Wirbelwind;
import com.maddox.il2.objects.vehicles.artillery.ArtilleryGeneric;
import com.maddox.il2.objects.vehicles.artillery.SArtillery;
import com.maddox.il2.objects.vehicles.artillery.STank;
import com.maddox.il2.objects.vehicles.artillery.SWagon;
import com.maddox.il2.objects.vehicles.cars.CarGeneric;
import com.maddox.il2.objects.vehicles.planes.PlaneGeneric;
import com.maddox.il2.objects.vehicles.stationary.Stationary.IS2;
import com.maddox.il2.objects.vehicles.stationary.Stationary.KV1;
import com.maddox.il2.objects.vehicles.stationary.Stationary.PzVA;
import com.maddox.il2.objects.vehicles.stationary.Stationary.PzVIB;
import com.maddox.il2.objects.vehicles.stationary.Stationary.PzVIE;
import com.maddox.il2.objects.vehicles.stationary.Stationary.T34;
import com.maddox.il2.objects.vehicles.stationary.Stationary.T34_85;
import com.maddox.il2.objects.vehicles.stationary.Stationary.Wirbelwind;
import com.maddox.il2.objects.vehicles.stationary.StationaryGeneric;
import com.maddox.il2.objects.vehicles.tanks.Tank.IS2;
import com.maddox.il2.objects.vehicles.tanks.Tank.KV1;
import com.maddox.il2.objects.vehicles.tanks.Tank.PzVA;
import com.maddox.il2.objects.vehicles.tanks.Tank.PzVIB;
import com.maddox.il2.objects.vehicles.tanks.Tank.PzVIE;
import com.maddox.il2.objects.vehicles.tanks.Tank.T34;
import com.maddox.il2.objects.vehicles.tanks.Tank.T34_85;
import com.maddox.il2.objects.vehicles.tanks.Tank.Wirbelwind;
import com.maddox.il2.objects.vehicles.tanks.TankGeneric;

public class ScoreRegister
{
  public static void load()
  {
  }

  static
  {
    ScoreCounter.register(Scheme1.class, 0, 100.0D, 300.0D);
    ScoreCounter.register(Scheme2.class, 0, 200.0D, 400.0D);
    ScoreCounter.register(Scheme3.class, 0, 200.0D, 400.0D);
    ScoreCounter.register(Scheme4.class, 0, 400.0D, 400.0D);
    ScoreCounter.register(Scheme5.class, 0, 200.0D, 400.0D);
    ScoreCounter.register(Scheme6.class, 0, 300.0D, 400.0D);
    ScoreCounter.register(Scheme7.class, 0, 600.0D, 400.0D);

    ScoreCounter.register(BF_109G6HARTMANN.class, 0, 150.0D, 500.0D);
    ScoreCounter.register(BF_109G6GRAF.class, 0, 150.0D, 500.0D);
    ScoreCounter.register(I_16TYPE24SAFONOV.class, 0, 150.0D, 500.0D);
    ScoreCounter.register(ME_262A1ANOWOTNY.class, 0, 150.0D, 500.0D);
    ScoreCounter.register(MIG_3POKRYSHKIN.class, 0, 150.0D, 500.0D);
    ScoreCounter.register(P_39NPOKRYSHKIN.class, 0, 150.0D, 500.0D);
    ScoreCounter.register(P_39Q15RECHKALOV.class, 0, 150.0D, 500.0D);
    ScoreCounter.register(YAK_9TALBERT.class, 0, 150.0D, 500.0D);

    ScoreCounter.register(Aircraft.class, 0, 100.0D, 400.0D);

    ScoreCounter.register(PlaneGeneric.class, 8, 20.0D, 400.0D);

    ScoreCounter.register(TankGeneric.class, 1, 40.0D, 400.0D);

    ScoreCounter.register(Tank.Wirbelwind.class, 1, 130.0D, 400.0D);
    ScoreCounter.register(Tank.T34.class, 1, 50.0D, 400.0D);
    ScoreCounter.register(Tank.T34_85.class, 1, 50.0D, 400.0D);
    ScoreCounter.register(Tank.KV1.class, 1, 55.0D, 400.0D);
    ScoreCounter.register(Tank.IS2.class, 1, 55.0D, 400.0D);
    ScoreCounter.register(Tank.PzVA.class, 1, 50.0D, 400.0D);
    ScoreCounter.register(Tank.PzVIE.class, 1, 55.0D, 400.0D);
    ScoreCounter.register(Tank.PzVIB.class, 1, 55.0D, 500.0D);

    ScoreCounter.register(STank.class, 1, 40.0D, 400.0D);
    ScoreCounter.register(Artillery.Wirbelwind.class, 1, 70.0D, 400.0D);
    ScoreCounter.register(Artillery.T34.class, 1, 50.0D, 400.0D);
    ScoreCounter.register(Artillery.T34_85.class, 1, 50.0D, 400.0D);
    ScoreCounter.register(Artillery.KV1.class, 1, 55.0D, 400.0D);
    ScoreCounter.register(Artillery.IS2.class, 1, 55.0D, 400.0D);
    ScoreCounter.register(Artillery.PzVA.class, 1, 50.0D, 400.0D);
    ScoreCounter.register(Artillery.PzVIE.class, 1, 55.0D, 400.0D);
    ScoreCounter.register(Artillery.PzVIB.class, 1, 55.0D, 500.0D);

    ScoreCounter.register(Stationary.Wirbelwind.class, 1, 30.0D, 400.0D);
    ScoreCounter.register(Stationary.T34.class, 1, 30.0D, 400.0D);
    ScoreCounter.register(Stationary.T34_85.class, 1, 30.0D, 400.0D);
    ScoreCounter.register(Stationary.KV1.class, 1, 40.0D, 400.0D);
    ScoreCounter.register(Stationary.IS2.class, 1, 40.0D, 400.0D);
    ScoreCounter.register(Stationary.PzVA.class, 1, 30.0D, 400.0D);
    ScoreCounter.register(Stationary.PzVIE.class, 1, 40.0D, 400.0D);
    ScoreCounter.register(Stationary.PzVIB.class, 1, 40.0D, 500.0D);

    ScoreCounter.register(AAA.class, 4, 60.0D, 150.0D);

    ScoreCounter.register(SArtillery.class, 3, 30.0D, 100.0D);
    ScoreCounter.register(ArtilleryGeneric.class, 3, 30.0D, 100.0D);

    ScoreCounter.register(CarGeneric.class, 2, 25.0D, 100.0D);

    ScoreCounter.register(StationaryGeneric.class, 2, 25.0D, 100.0D);

    ScoreCounter.register(SWagon.class, 6, 30.0D, 100.0D);
    ScoreCounter.register(Wagon.class, 6, 30.0D, 100.0D);

    ScoreCounter.register(ShipGeneric.class, 7, 100.0D, 400.0D);
    ScoreCounter.register(BigshipGeneric.class, 7, 100.0D, 400.0D);

    ScoreCounter.register(Ship.Destroyer_USSR_Type7.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.Destroyer_USSR_Type7_44.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.Murgesku.class, 7, 250.0D, 600.0D);
    ScoreCounter.register(Ship.MBoat.class, 7, 250.0D, 600.0D);
    ScoreCounter.register(Ship.Tral.class, 7, 200.0D, 500.0D);

    ScoreCounter.register(Ship.Aurora.class, 7, 500.0D, 1000.0D);
    ScoreCounter.register(Ship.Marat.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.Kirov.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.Tramp.class, 7, 200.0D, 400.0D);
    ScoreCounter.register(Ship.Niobe.class, 7, 600.0D, 1200.0D);

    ScoreCounter.register(Ship.Tanker.class, 7, 200.0D, 400.0D);
    ScoreCounter.register(Ship.Illmarinen.class, 7, 500.0D, 1000.0D);
    ScoreCounter.register(Ship.Vainamoinen.class, 7, 500.0D, 1000.0D);

    ScoreCounter.register(Ship.USSLexingtonCV2.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.USSSaratogaCV3.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.USSCVGeneric.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.USSBBGeneric.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.USSIndianapolisCA35.class, 7, 600.0D, 1200.0D);
    ScoreCounter.register(Ship.USSGreenlingSS213_Srf.class, 7, 200.0D, 500.0D);
    ScoreCounter.register(Ship.USSGreenlingSS213_Sub.class, 7, 200.0D, 500.0D);
    ScoreCounter.register(Ship.USSGatoSS212_Srf.class, 7, 200.0D, 500.0D);
    ScoreCounter.register(Ship.USSGatoSS212_Sub.class, 7, 200.0D, 500.0D);
    ScoreCounter.register(Ship.USSWardDD139.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.USSDentDD116.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.USSFletcherDD445.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.USSOBannonDD450.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.USSKiddDD661.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.USSCasablancaCVE55.class, 7, 500.0D, 1000.0D);
    ScoreCounter.register(Ship.USSKitkunBayCVE71.class, 7, 500.0D, 1000.0D);
    ScoreCounter.register(Ship.USSShamrockBayCVE84.class, 7, 500.0D, 1000.0D);
    ScoreCounter.register(Ship.USSEssexCV9.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.USSIntrepidCV11.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.LVT_2WAT.class, 7, 30.0D, 100.0D);
    ScoreCounter.register(Ship.DUKW_WAT.class, 7, 30.0D, 100.0D);
    ScoreCounter.register(Ship.LCVP.class, 7, 30.0D, 100.0D);

    ScoreCounter.register(Ship.HMSIllustriousCV.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.HMSPoWBB.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.HMSKingGeorgeVBB.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.HMSDukeOfYorkBB.class, 7, 700.0D, 1400.0D);

    ScoreCounter.register(Ship.IJNAkagiCV.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.IJNShokakuCV.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.IJNZuikakuCV.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.IJNCVGeneric.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.IJNBBGeneric.class, 7, 700.0D, 1400.0D);
    ScoreCounter.register(Ship.IJNAkizukiDD42.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNAmatsukazeDD41.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNArashiDD41.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNKageroDD41.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNNowakiDD41.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNYukikazeDD41.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNAmatsukazeDD43.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNNowakiDD43.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNYukikazeDD43.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNAmatsukazeDD45.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNYukikazeDD45.class, 7, 300.0D, 800.0D);
    ScoreCounter.register(Ship.IJNFishJunk.class, 7, 200.0D, 400.0D);
    ScoreCounter.register(Ship.IJNFishJunkA.class, 7, 250.0D, 600.0D);
    ScoreCounter.register(Ship.DaihatsuLC.class, 7, 30.0D, 100.0D);

    ScoreCounter.register(LongBridge.class, 5, 100.0D, 500.0D);
  }
}