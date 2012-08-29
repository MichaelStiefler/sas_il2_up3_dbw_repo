package com.maddox.il2.objects.ships;

import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.objects.vehicles.radios.TypeHasBeacon;
import com.maddox.il2.objects.vehicles.radios.TypeHasHayRake;
import com.maddox.rts.SectFile;

public abstract class Ship
{
  static
  {
    new BigshipGeneric.SPAWN(G5.class);

    new BigshipGeneric.SPAWN(MO4.class);

    new BigshipGeneric.SPAWN(BBK_1942.class);

    new BigshipGeneric.SPAWN(BBK1124_1943.class);

    new BigshipGeneric.SPAWN(Destroyer_USSR_Type7.class);

    new BigshipGeneric.SPAWN(Destroyer_USSR_Type7_44.class);

    new BigshipGeneric.SPAWN(Tral.class);

    new ShipGeneric.SPAWN(Shuka.class);

    new BigshipGeneric.SPAWN(ShukaP.class);

    new BigshipGeneric.SPAWN(Aurora.class);

    new BigshipGeneric.SPAWN(Marat.class);

    new BigshipGeneric.SPAWN(Kirov.class);

    new BigshipGeneric.SPAWN(Tashkent.class);

    new BigshipGeneric.SPAWN(Tramp.class);

    new BigshipGeneric.SPAWN(Tanker.class);

    new BigshipGeneric.SPAWN(USSLexingtonCV2.class);

    new BigshipGeneric.SPAWN(USSSaratogaCV3.class);

    new BigshipGeneric.SPAWN(USSCVGeneric.class);

    new BigshipGeneric.SPAWN(USSBBGeneric.class);

    new BigshipGeneric.SPAWN(USSIndianapolisCA35.class);

    new ShipGeneric.SPAWN(LVT_2WAT.class);

    new ShipGeneric.SPAWN(DUKW_WAT.class);

    new ShipGeneric.SPAWN(LCVP.class);

    new BigshipGeneric.SPAWN(USSGreenlingSS213_Srf.class);

    new BigshipGeneric.SPAWN(USSGreenlingSS213_Sub.class);

    new BigshipGeneric.SPAWN(USSGatoSS212_Srf.class);

    new BigshipGeneric.SPAWN(USSGatoSS212_Sub.class);

    new BigshipGeneric.SPAWN(USSWardDD139.class);

    new BigshipGeneric.SPAWN(USSDentDD116.class);

    new BigshipGeneric.SPAWN(USSFletcherDD445.class);

    new BigshipGeneric.SPAWN(USSOBannonDD450.class);

    new BigshipGeneric.SPAWN(USSKiddDD661.class);

    new BigshipGeneric.SPAWN(USSCasablancaCVE55.class);

    new BigshipGeneric.SPAWN(USSKitkunBayCVE71.class);

    new BigshipGeneric.SPAWN(USSShamrockBayCVE84.class);

    new BigshipGeneric.SPAWN(USSEssexCV9.class);

    new BigshipGeneric.SPAWN(USSIntrepidCV11.class);

    new ShipGeneric.SPAWN(PilotWater_US.class);

    new ShipGeneric.SPAWN(PilotBoatWater_US.class);

    new BigshipGeneric.SPAWN(HMSIllustriousCV.class);

    new BigshipGeneric.SPAWN(HMSPoWBB.class);

    new BigshipGeneric.SPAWN(HMSKingGeorgeVBB.class);

    new BigshipGeneric.SPAWN(HMSDukeOfYorkBB.class);

    new BigshipGeneric.SPAWN(S80.class);

    new BigshipGeneric.SPAWN(MFP.class);

    new BigshipGeneric.SPAWN(MFP2.class);

    new BigshipGeneric.SPAWN(MAS501.class);

    new BigshipGeneric.SPAWN(Murgesku.class);

    new BigshipGeneric.SPAWN(MBoat.class);

    new ShipGeneric.SPAWN(Submarine.class);

    new BigshipGeneric.SPAWN(SubmarineP.class);

    new BigshipGeneric.SPAWN(Niobe.class);

    new BigshipGeneric.SPAWN(NiobeWithBeacon.class);

    new BigshipGeneric.SPAWN(SubTypeVIIC_Srf.class);

    new BigshipGeneric.SPAWN(SubTypeVIIC_SrfWithBeacon.class);

    new BigshipGeneric.SPAWN(SubTypeVIIC_Sub.class);

    new BigshipGeneric.SPAWN(Illmarinen.class);

    new BigshipGeneric.SPAWN(Vainamoinen.class);

    new BigshipGeneric.SPAWN(Tirpitz.class);

    new BigshipGeneric.SPAWN(PAM.class);

    new BigshipGeneric.SPAWN(IJNAkagiCV.class);

    new BigshipGeneric.SPAWN(IJNShokakuCV.class);

    new BigshipGeneric.SPAWN(IJNZuikakuCV.class);

    new BigshipGeneric.SPAWN(IJNCVGeneric.class);

    new BigshipGeneric.SPAWN(IJNBBGeneric.class);

    new BigshipGeneric.SPAWN(IJNAkizukiDD42.class);

    new BigshipGeneric.SPAWN(IJNAmatsukazeDD41.class);

    new BigshipGeneric.SPAWN(IJNArashiDD41.class);

    new BigshipGeneric.SPAWN(IJNKageroDD41.class);

    new BigshipGeneric.SPAWN(IJNNowakiDD41.class);

    new BigshipGeneric.SPAWN(IJNYukikazeDD41.class);

    new BigshipGeneric.SPAWN(IJNAmatsukazeDD43.class);

    new BigshipGeneric.SPAWN(IJNAmatsukazeDD43WithBeacon.class);

    new BigshipGeneric.SPAWN(IJNNowakiDD43.class);

    new BigshipGeneric.SPAWN(IJNYukikazeDD43.class);

    new BigshipGeneric.SPAWN(IJNAmatsukazeDD45.class);

    new BigshipGeneric.SPAWN(IJNYukikazeDD45.class);

    new BigshipGeneric.SPAWN(IJNFishJunk.class);

    new BigshipGeneric.SPAWN(IJNFishJunkA.class);

    new ShipGeneric.SPAWN(DaihatsuLC.class);

    new ShipGeneric.SPAWN(PilotWater_JA.class);

    new BigshipGeneric.SPAWN(RwyCon.class);

    new BigshipGeneric.SPAWN(RwySteel.class);

    new BigshipGeneric.SPAWN(RwySteelLow.class);

    new BigshipGeneric.SPAWN(RwyTransp.class);

    new BigshipGeneric.SPAWN(RwyTranspWide.class);

    new BigshipGeneric.SPAWN(RwyTranspSqr.class);
  }

  public static class RwyTranspSqr extends BigshipGeneric
    implements TgtShip, TestRunway
  {
    public RwyTranspSqr()
    {
    }

    public RwyTranspSqr(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class RwyTranspWide extends BigshipGeneric
    implements TgtShip, TestRunway
  {
    public RwyTranspWide()
    {
    }

    public RwyTranspWide(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class RwyTransp extends BigshipGeneric
    implements TgtShip, TestRunway
  {
    public RwyTransp()
    {
    }

    public RwyTransp(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class RwySteelLow extends BigshipGeneric
    implements TgtShip, TestRunway
  {
    public RwySteelLow()
    {
    }

    public RwySteelLow(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class RwySteel extends BigshipGeneric
    implements TgtShip, TestRunway
  {
    public RwySteel()
    {
    }

    public RwySteel(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class RwyCon extends BigshipGeneric
    implements TgtShip, TestRunway
  {
    public RwyCon()
    {
    }

    public RwyCon(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class PilotWater_JA extends ShipGeneric
    implements TgtShip, WeakBody
  {
    public PilotWater_JA()
    {
    }

    public PilotWater_JA(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class DaihatsuLC extends ShipGeneric
    implements TgtShip
  {
    public DaihatsuLC()
    {
    }

    public DaihatsuLC(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNFishJunkA extends BigshipGeneric
    implements TgtShip
  {
    public IJNFishJunkA()
    {
    }

    public IJNFishJunkA(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNFishJunk extends BigshipGeneric
    implements TgtShip
  {
    public IJNFishJunk()
    {
    }

    public IJNFishJunk(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNYukikazeDD45 extends BigshipGeneric
    implements TgtShip
  {
    public IJNYukikazeDD45()
    {
    }

    public IJNYukikazeDD45(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNAmatsukazeDD45 extends BigshipGeneric
    implements TgtShip
  {
    public IJNAmatsukazeDD45()
    {
    }

    public IJNAmatsukazeDD45(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNYukikazeDD43 extends BigshipGeneric
    implements TgtShip
  {
    public IJNYukikazeDD43()
    {
    }

    public IJNYukikazeDD43(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNNowakiDD43 extends BigshipGeneric
    implements TgtShip
  {
    public IJNNowakiDD43()
    {
    }

    public IJNNowakiDD43(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNAmatsukazeDD43WithBeacon extends BigshipGeneric
    implements TgtShip, TypeHasBeacon
  {
    public IJNAmatsukazeDD43WithBeacon()
    {
    }

    public IJNAmatsukazeDD43WithBeacon(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNAmatsukazeDD43 extends BigshipGeneric
    implements TgtShip
  {
    public IJNAmatsukazeDD43()
    {
    }

    public IJNAmatsukazeDD43(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNYukikazeDD41 extends BigshipGeneric
    implements TgtShip
  {
    public IJNYukikazeDD41()
    {
    }

    public IJNYukikazeDD41(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNNowakiDD41 extends BigshipGeneric
    implements TgtShip
  {
    public IJNNowakiDD41()
    {
    }

    public IJNNowakiDD41(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNKageroDD41 extends BigshipGeneric
    implements TgtShip
  {
    public IJNKageroDD41()
    {
    }

    public IJNKageroDD41(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNArashiDD41 extends BigshipGeneric
    implements TgtShip
  {
    public IJNArashiDD41()
    {
    }

    public IJNArashiDD41(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNAmatsukazeDD41 extends BigshipGeneric
    implements TgtShip
  {
    public IJNAmatsukazeDD41()
    {
    }

    public IJNAmatsukazeDD41(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNAkizukiDD42 extends BigshipGeneric
    implements TgtShip
  {
    public IJNAkizukiDD42()
    {
    }

    public IJNAkizukiDD42(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNBBGeneric extends BigshipGeneric
    implements TgtShip
  {
    public IJNBBGeneric()
    {
    }

    public IJNBBGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNCVGeneric extends BigshipGeneric
    implements TgtShip, TypeHasBeacon
  {
    public IJNCVGeneric()
    {
    }

    public IJNCVGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNZuikakuCV extends BigshipGeneric
    implements TgtShip, TypeHasBeacon
  {
    public IJNZuikakuCV()
    {
    }

    public IJNZuikakuCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNShokakuCV extends BigshipGeneric
    implements TgtShip, TypeHasBeacon
  {
    public IJNShokakuCV()
    {
    }

    public IJNShokakuCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNAkagiCV extends BigshipGeneric
    implements TgtShip, TypeHasBeacon
  {
    public IJNAkagiCV()
    {
    }

    public IJNAkagiCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class PAM extends BigshipGeneric
    implements TgtShip
  {
    public PAM()
    {
    }

    public PAM(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Tirpitz extends BigshipGeneric
    implements TgtShip
  {
    public Tirpitz()
    {
    }

    public Tirpitz(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Vainamoinen extends BigshipGeneric
    implements TgtShip
  {
    public Vainamoinen()
    {
    }

    public Vainamoinen(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Illmarinen extends BigshipGeneric
    implements TgtShip
  {
    public Illmarinen()
    {
    }

    public Illmarinen(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class SubTypeVIIC_Sub extends BigshipGeneric
    implements TgtShip
  {
    public SubTypeVIIC_Sub()
    {
    }

    public SubTypeVIIC_Sub(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class SubTypeVIIC_SrfWithBeacon extends BigshipGeneric
    implements TgtShip, TypeHasBeacon
  {
    public SubTypeVIIC_SrfWithBeacon()
    {
    }

    public SubTypeVIIC_SrfWithBeacon(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class SubTypeVIIC_Srf extends BigshipGeneric
    implements TgtShip
  {
    public SubTypeVIIC_Srf()
    {
    }

    public SubTypeVIIC_Srf(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class NiobeWithBeacon extends BigshipGeneric
    implements TgtShip, TypeHasBeacon
  {
    public NiobeWithBeacon()
    {
    }

    public NiobeWithBeacon(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Niobe extends BigshipGeneric
    implements TgtShip
  {
    public Niobe()
    {
    }

    public Niobe(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class SubmarineP extends BigshipGeneric
    implements TgtShip
  {
    public SubmarineP()
    {
    }

    public SubmarineP(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Submarine extends ShipGeneric
    implements TgtShip
  {
    public Submarine()
    {
    }

    public Submarine(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MBoat extends BigshipGeneric
    implements TgtShip
  {
    public MBoat()
    {
    }

    public MBoat(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Murgesku extends BigshipGeneric
    implements TgtShip
  {
    public Murgesku()
    {
    }

    public Murgesku(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MAS501 extends BigshipGeneric
    implements TgtShip
  {
    public MAS501()
    {
    }

    public MAS501(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MFP2 extends BigshipGeneric
    implements TgtShip
  {
    public MFP2()
    {
    }

    public MFP2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MFP extends BigshipGeneric
    implements TgtShip
  {
    public MFP()
    {
    }

    public MFP(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class S80 extends BigshipGeneric
    implements TgtShip
  {
    public S80()
    {
    }

    public S80(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSDukeOfYorkBB extends BigshipGeneric
    implements TgtShip
  {
    public HMSDukeOfYorkBB()
    {
    }

    public HMSDukeOfYorkBB(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSKingGeorgeVBB extends BigshipGeneric
    implements TgtShip
  {
    public HMSKingGeorgeVBB()
    {
    }

    public HMSKingGeorgeVBB(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSPoWBB extends BigshipGeneric
    implements TgtShip
  {
    public HMSPoWBB()
    {
    }

    public HMSPoWBB(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSIllustriousCV extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public HMSIllustriousCV()
    {
    }

    public HMSIllustriousCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class PilotBoatWater_US extends ShipGeneric
    implements TgtShip, WeakBody
  {
    public PilotBoatWater_US()
    {
    }

    public PilotBoatWater_US(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class PilotWater_US extends ShipGeneric
    implements TgtShip
  {
    public PilotWater_US()
    {
    }

    public PilotWater_US(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSIntrepidCV11 extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public USSIntrepidCV11()
    {
    }

    public USSIntrepidCV11(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSEssexCV9 extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public USSEssexCV9()
    {
    }

    public USSEssexCV9(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSShamrockBayCVE84 extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public USSShamrockBayCVE84()
    {
    }

    public USSShamrockBayCVE84(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSKitkunBayCVE71 extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public USSKitkunBayCVE71()
    {
    }

    public USSKitkunBayCVE71(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSCasablancaCVE55 extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public USSCasablancaCVE55()
    {
    }

    public USSCasablancaCVE55(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSKiddDD661 extends BigshipGeneric
    implements TgtShip
  {
    public USSKiddDD661()
    {
    }

    public USSKiddDD661(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSOBannonDD450 extends BigshipGeneric
    implements TgtShip
  {
    public USSOBannonDD450()
    {
    }

    public USSOBannonDD450(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSFletcherDD445 extends BigshipGeneric
    implements TgtShip
  {
    public USSFletcherDD445()
    {
    }

    public USSFletcherDD445(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSDentDD116 extends BigshipGeneric
    implements TgtShip
  {
    public USSDentDD116()
    {
    }

    public USSDentDD116(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSWardDD139 extends BigshipGeneric
    implements TgtShip
  {
    public USSWardDD139()
    {
    }

    public USSWardDD139(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSGatoSS212_Sub extends BigshipGeneric
    implements TgtShip
  {
    public USSGatoSS212_Sub()
    {
    }

    public USSGatoSS212_Sub(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSGatoSS212_Srf extends BigshipGeneric
    implements TgtShip
  {
    public USSGatoSS212_Srf()
    {
    }

    public USSGatoSS212_Srf(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSGreenlingSS213_Sub extends BigshipGeneric
    implements TgtShip
  {
    public USSGreenlingSS213_Sub()
    {
    }

    public USSGreenlingSS213_Sub(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSGreenlingSS213_Srf extends BigshipGeneric
    implements TgtShip
  {
    public USSGreenlingSS213_Srf()
    {
    }

    public USSGreenlingSS213_Srf(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class LCVP extends ShipGeneric
    implements TgtShip
  {
    public LCVP()
    {
    }

    public LCVP(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class DUKW_WAT extends ShipGeneric
    implements TgtShip
  {
    public DUKW_WAT()
    {
    }

    public DUKW_WAT(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class LVT_2WAT extends ShipGeneric
    implements TgtShip
  {
    public LVT_2WAT()
    {
    }

    public LVT_2WAT(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSIndianapolisCA35 extends BigshipGeneric
    implements TgtShip
  {
    public USSIndianapolisCA35()
    {
    }

    public USSIndianapolisCA35(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSBBGeneric extends BigshipGeneric
    implements TgtShip
  {
    public USSBBGeneric()
    {
    }

    public USSBBGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSCVGeneric extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public USSCVGeneric()
    {
    }

    public USSCVGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSSaratogaCV3 extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public USSSaratogaCV3()
    {
    }

    public USSSaratogaCV3(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSLexingtonCV2 extends BigshipGeneric
    implements TgtShip, TypeHasHayRake
  {
    public USSLexingtonCV2()
    {
    }

    public USSLexingtonCV2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Tanker extends BigshipGeneric
    implements TgtShip
  {
    public Tanker()
    {
    }

    public Tanker(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Tramp extends BigshipGeneric
    implements TgtShip
  {
    public Tramp()
    {
    }

    public Tramp(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Tashkent extends BigshipGeneric
    implements TgtShip
  {
    public Tashkent()
    {
    }

    public Tashkent(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Kirov extends BigshipGeneric
    implements TgtShip
  {
    public Kirov()
    {
    }

    public Kirov(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Marat extends BigshipGeneric
    implements TgtShip
  {
    public Marat()
    {
    }

    public Marat(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Aurora extends BigshipGeneric
    implements TgtShip
  {
    public Aurora()
    {
    }

    public Aurora(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class ShukaP extends BigshipGeneric
    implements TgtShip
  {
    public ShukaP()
    {
    }

    public ShukaP(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Shuka extends ShipGeneric
    implements TgtShip
  {
    public Shuka()
    {
    }

    public Shuka(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Tral extends BigshipGeneric
    implements TgtShip
  {
    public Tral()
    {
    }

    public Tral(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Destroyer_USSR_Type7_44 extends BigshipGeneric
    implements TgtShip
  {
    public Destroyer_USSR_Type7_44()
    {
    }

    public Destroyer_USSR_Type7_44(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Destroyer_USSR_Type7 extends BigshipGeneric
    implements TgtShip
  {
    public Destroyer_USSR_Type7()
    {
    }

    public Destroyer_USSR_Type7(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class BBK1124_1943 extends BigshipGeneric
    implements TgtShip
  {
    public BBK1124_1943()
    {
    }

    public BBK1124_1943(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class BBK_1942 extends BigshipGeneric
    implements TgtShip
  {
    public BBK_1942()
    {
    }

    public BBK_1942(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MO4 extends BigshipGeneric
    implements TgtShip
  {
    public MO4()
    {
    }

    public MO4(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class G5 extends BigshipGeneric
    implements TgtShip
  {
    public G5()
    {
    }

    public G5(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }
}