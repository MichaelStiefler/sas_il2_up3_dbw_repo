package com.maddox.il2.objects.ships;

import com.maddox.il2.ai.ground.TgtShip;
import com.maddox.il2.ai.ground.TypeRadar;
import com.maddox.rts.SectFile;

public abstract class Ship
{
  public static final int AIR_WARNING = 0;
  public static final int FIRE_CONTROL = 1;

  static
  {
    new ShipGeneric.SPAWN(Ship.G5.class);
    new ShipGeneric.SPAWN(Ship.MO4.class);
    new ShipGeneric.SPAWN(Ship.BBK_1942.class);
    new ShipGeneric.SPAWN(Ship.BBK1124_1943.class);
    new ShipGeneric.SPAWN(Ship.Destroyer_USSR_Type7.class);
    new ShipGeneric.SPAWN(Destroyer_USSR_Type7_44.class);
    new ShipGeneric.SPAWN(Ship.Tral.class);
    new ShipGeneric.SPAWN(Ship.Shuka.class);
    new ShipGeneric.SPAWN(Ship.ShukaP.class);
    new BigshipGeneric.SPAWN(Aurora.class);
    new BigshipGeneric.SPAWN(Marat.class);
    new BigshipGeneric.SPAWN(Kirov.class);
    new BigshipGeneric.SPAWN(Tashkent.class);
    new BigshipGeneric.SPAWN(Ship.Tramp.class);
    new BigshipGeneric.SPAWN(Ship.Tanker.class);
    new BigshipGeneric.SPAWN(USSLexingtonCV2.class);
    new BigshipGeneric.SPAWN(USSSaratogaCV3.class);
    new BigshipGeneric.SPAWN(USSCVGeneric.class);
    new BigshipGeneric.SPAWN(USSBBGeneric.class);
    new BigshipGeneric.SPAWN(USSIndianapolisCA35.class);
    new ShipGeneric.SPAWN(Ship.LVT_2WAT.class);
    new ShipGeneric.SPAWN(Ship.DUKW_WAT.class);
    new ShipGeneric.SPAWN(Ship.LCVP.class);
    new BigshipGeneric.SPAWN(USSGreenlingSS213_Srf.class);
    new ShipGeneric.SPAWN(USSGreenlingSS213_Sub.class);
    new BigshipGeneric.SPAWN(USSGatoSS212_Srf.class);
    new ShipGeneric.SPAWN(USSGatoSS212_Sub.class);
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
    new ShipGeneric.SPAWN(Ship.PilotWater_US.class);
    new ShipGeneric.SPAWN(Ship.PilotBoatWater_US.class);
    new BigshipGeneric.SPAWN(HMSIllustriousCV.class);
    new BigshipGeneric.SPAWN(HMSPoWBB.class);
    new BigshipGeneric.SPAWN(HMSKingGeorgeVBB.class);
    new BigshipGeneric.SPAWN(HMSDukeOfYorkBB.class);
    new ShipGeneric.SPAWN(Ship.S80.class);
    new ShipGeneric.SPAWN(Ship.MFP.class);
    new ShipGeneric.SPAWN(Ship.MFP2.class);
    new ShipGeneric.SPAWN(Ship.MAS501.class);
    new ShipGeneric.SPAWN(Murgesku.class);
    new ShipGeneric.SPAWN(Ship.MBoat.class);
    new ShipGeneric.SPAWN(Ship.Submarine.class);
    new ShipGeneric.SPAWN(Ship.SubmarineP.class);
    new BigshipGeneric.SPAWN(Niobe.class);
    new BigshipGeneric.SPAWN(Ship.SubTypeVIIC_Srf.class);
    new ShipGeneric.SPAWN(Ship.SubTypeVIIC_Sub.class);
    new BigshipGeneric.SPAWN(Illmarinen.class);
    new BigshipGeneric.SPAWN(Vainamoinen.class);
    new BigshipGeneric.SPAWN(Tirpitz.class);
    new BigshipGeneric.SPAWN(Ship.PAM.class);
    new BigshipGeneric.SPAWN(IJNAkagiCV.class);
    new BigshipGeneric.SPAWN(IJNHiryuCV.class);
    new BigshipGeneric.SPAWN(IJNKagaCV.class);
    new BigshipGeneric.SPAWN(IJNSoryuCV.class);
    new BigshipGeneric.SPAWN(IJNShokakuCV.class);
    new BigshipGeneric.SPAWN(IJNZuikakuCV.class);
    new BigshipGeneric.SPAWN(IJNCVGeneric.class);
    new BigshipGeneric.SPAWN(IJNCVLGeneric.class);
    new BigshipGeneric.SPAWN(IJNBBGeneric.class);
    new BigshipGeneric.SPAWN(Ship.IJNAkizukiDD42.class);
    new BigshipGeneric.SPAWN(Ship.IJNAmatsukazeDD41.class);
    new BigshipGeneric.SPAWN(Ship.IJNArashiDD41.class);
    new BigshipGeneric.SPAWN(Ship.IJNKageroDD41.class);
    new BigshipGeneric.SPAWN(Ship.IJNNowakiDD41.class);
    new BigshipGeneric.SPAWN(Ship.IJNYukikazeDD41.class);
    new BigshipGeneric.SPAWN(Ship.IJNAmatsukazeDD43.class);
    new BigshipGeneric.SPAWN(Ship.IJNNowakiDD43.class);
    new BigshipGeneric.SPAWN(Ship.IJNYukikazeDD43.class);
    new BigshipGeneric.SPAWN(IJNAmatsukazeDD45.class);
    new BigshipGeneric.SPAWN(IJNYukikazeDD45.class);
    new BigshipGeneric.SPAWN(Ship.IJNFishJunk.class);
    new BigshipGeneric.SPAWN(Ship.IJNFishJunkA.class);
    new ShipGeneric.SPAWN(Ship.DaihatsuLC.class);
    new ShipGeneric.SPAWN(Ship.PilotWater_JA.class);
    new BigshipGeneric.SPAWN(Ship.RwyCon.class);
    new BigshipGeneric.SPAWN(Ship.RwySteel.class);
    new BigshipGeneric.SPAWN(Ship.RwySteelLow.class);
    new BigshipGeneric.SPAWN(Ship.RwyTransp.class);
    new BigshipGeneric.SPAWN(USSPrincetonCVL23.class);
    new BigshipGeneric.SPAWN(USSBelleauWoodCVL24.class);
    new BigshipGeneric.SPAWN(USSSanJacintoCVL30.class);
    new BigshipGeneric.SPAWN(Italia0.class);
    new BigshipGeneric.SPAWN(Italia1.class);
    new BigshipGeneric.SPAWN(Soldati.class);
    new BigshipGeneric.SPAWN(Littorio.class);
    new BigshipGeneric.SPAWN(HMSFiji.class);
    new BigshipGeneric.SPAWN(HMSTribal.class);
    new BigshipGeneric.SPAWN(TroopTrans0.class);
    new BigshipGeneric.SPAWN(TroopTrans1.class);
    new BigshipGeneric.SPAWN(TroopTrans2.class);
    new BigshipGeneric.SPAWN(TroopTrans3.class);
    new BigshipGeneric.SPAWN(Transport4.class);
    new BigshipGeneric.SPAWN(Transport5.class);
    new BigshipGeneric.SPAWN(Transport6.class);
    new BigshipGeneric.SPAWN(Transport7.class);
    new BigshipGeneric.SPAWN(USSMcKean.class);
    new ShipGeneric.SPAWN(MFPT.class);
    new ShipGeneric.SPAWN(MFPIT.class);
    new ShipGeneric.SPAWN(Fisherman1.class);
    new ShipGeneric.SPAWN(Fisherman2.class);
    new ShipGeneric.SPAWN(Boat1.class);
    new BigshipGeneric.SPAWN(Ship0.class);
    new BigshipGeneric.SPAWN(Ship1.class);
    new BigshipGeneric.SPAWN(Ship2.class);
    new BigshipGeneric.SPAWN(Ship3.class);
    new BigshipGeneric.SPAWN(Ship4.class);
    new BigshipGeneric.SPAWN(Tanker0.class);
    new BigshipGeneric.SPAWN(Tanker1.class);
    new BigshipGeneric.SPAWN(Tanker2.class);
    new BigshipGeneric.SPAWN(TankerDmg.class);
    new BigshipGeneric.SPAWN(Destroyer0.class);
    new BigshipGeneric.SPAWN(Destroyer1.class);
    new BigshipGeneric.SPAWN(Destroyer2.class);
    new BigshipGeneric.SPAWN(DestroyerDmg.class);
    new BigshipGeneric.SPAWN(DestroyerWreck.class);
    new ShipGeneric.SPAWN(LCVPWreck.class);
    new ShipGeneric.SPAWN(DLCWreck.class);
    new ShipGeneric.SPAWN(Boat.class);
    new BigshipGeneric.SPAWN(Carrier0.class);
    new BigshipGeneric.SPAWN(Carrier1.class);
    new BigshipGeneric.SPAWN(HMSFormidableCV.class);
    new BigshipGeneric.SPAWN(HMSIndomitableCV.class);
    new ShipGeneric.SPAWN(Barge0.class);
    new ShipGeneric.SPAWN(Barge1.class);
    new BigshipGeneric.SPAWN(HospitalShip.class);
    new BigshipGeneric.SPAWN(Transport1.class);
    new BigshipGeneric.SPAWN(Transport2.class);
    new BigshipGeneric.SPAWN(Transport3.class);
    new BigshipGeneric.SPAWN(TransWreck.class);
    new BigshipGeneric.SPAWN(TransDmg.class);
    new ShipGeneric.SPAWN(SmallWreck.class);
    new ShipGeneric.SPAWN(Fisherman.class);
    new ShipGeneric.SPAWN(MAS501JP.class);
    new ShipGeneric.SPAWN(MAS501UNE.class);
    new ShipGeneric.SPAWN(MAS501UNP.class);
    new ShipGeneric.SPAWN(MAS501RN.class);
    new ShipGeneric.SPAWN(DestroyerRN.class);
    new ShipGeneric.SPAWN(DestroyerKM.class);
  }

  public static class DestroyerKM extends ShipGeneric
    implements TgtShip
  {
    public DestroyerKM()
    {
    }

    public DestroyerKM(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class DestroyerRN extends ShipGeneric
    implements TgtShip
  {
    public DestroyerRN()
    {
    }

    public DestroyerRN(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MAS501RN extends ShipGeneric
    implements TgtShip
  {
    public MAS501RN()
    {
    }

    public MAS501RN(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MAS501UNP extends ShipGeneric
    implements TgtShip
  {
    public MAS501UNP()
    {
    }

    public MAS501UNP(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MAS501UNE extends ShipGeneric
    implements TgtShip
  {
    public MAS501UNE()
    {
    }

    public MAS501UNE(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MAS501JP extends ShipGeneric
    implements TgtShip
  {
    public MAS501JP()
    {
    }

    public MAS501JP(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Fisherman2 extends ShipGeneric
    implements TgtShip
  {
    public Fisherman2()
    {
    }

    public Fisherman2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Fisherman1 extends ShipGeneric
    implements TgtShip
  {
    public Fisherman1()
    {
    }

    public Fisherman1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Fisherman extends ShipGeneric
    implements TgtShip
  {
    public Fisherman()
    {
    }

    public Fisherman(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class SmallWreck extends ShipGeneric
    implements TgtShip
  {
    public SmallWreck()
    {
    }

    public SmallWreck(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class TransDmg extends BigshipGeneric
    implements TgtShip
  {
    public TransDmg()
    {
    }

    public TransDmg(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class TransWreck extends BigshipGeneric
    implements TgtShip
  {
    public TransWreck()
    {
    }

    public TransWreck(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3); }  } 
  public static class Transport7 extends BigshipGeneric implements TgtShip { public Transport7() {  } 
    public Transport7(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3) { super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Transport3 extends BigshipGeneric
    implements TgtShip
  {
    public Transport3()
    {
    }

    public Transport3(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Transport2 extends BigshipGeneric
    implements TgtShip
  {
    public Transport2()
    {
    }

    public Transport2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Transport1 extends BigshipGeneric
    implements TgtShip
  {
    public Transport1()
    {
    }

    public Transport1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HospitalShip extends BigshipGeneric
    implements TgtShip
  {
    public HospitalShip()
    {
    }

    public HospitalShip(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Barge1 extends ShipGeneric
    implements TgtShip
  {
    public Barge1()
    {
    }

    public Barge1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Barge0 extends ShipGeneric
    implements TgtShip
  {
    public Barge0()
    {
    }

    public Barge0(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Carrier1 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Carrier1()
    {
    }

    public Carrier1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Carrier0 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Carrier0(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSIndomitableCV extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public HMSIndomitableCV()
    {
    }

    public HMSIndomitableCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSFormidableCV extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public HMSFormidableCV()
    {
    }

    public HMSFormidableCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Boat extends ShipGeneric
    implements TgtShip
  {
    public Boat()
    {
    }

    public Boat(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class DLCWreck extends ShipGeneric
    implements TgtShip
  {
    public DLCWreck()
    {
    }

    public DLCWreck(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class LCVPWreck extends ShipGeneric
    implements TgtShip
  {
    public LCVPWreck()
    {
    }

    public LCVPWreck(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class DestroyerWreck extends BigshipGeneric
    implements TgtShip
  {
    public DestroyerWreck()
    {
    }

    public DestroyerWreck(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class DestroyerDmg extends BigshipGeneric
    implements TgtShip
  {
    public DestroyerDmg()
    {
    }

    public DestroyerDmg(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Destroyer2 extends BigshipGeneric
    implements TgtShip
  {
    public Destroyer2()
    {
    }

    public Destroyer2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Destroyer1 extends BigshipGeneric
    implements TgtShip
  {
    public Destroyer1()
    {
    }

    public Destroyer1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Destroyer0 extends BigshipGeneric
    implements TgtShip
  {
    public Destroyer0()
    {
    }

    public Destroyer0(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class TankerDmg extends BigshipGeneric
    implements TgtShip
  {
    public TankerDmg()
    {
    }

    public TankerDmg(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Tanker2 extends BigshipGeneric
    implements TgtShip
  {
    public Tanker2()
    {
    }

    public Tanker2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Tanker1 extends BigshipGeneric
    implements TgtShip
  {
    public Tanker1()
    {
    }

    public Tanker1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Tanker0 extends BigshipGeneric
    implements TgtShip
  {
    public Tanker0()
    {
    }

    public Tanker0(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Ship4 extends BigshipGeneric
    implements TgtShip
  {
    public Ship4()
    {
    }

    public Ship4(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Ship3 extends BigshipGeneric
    implements TgtShip
  {
    public Ship3()
    {
    }

    public Ship3(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Ship2 extends BigshipGeneric
    implements TgtShip
  {
    public Ship2()
    {
    }

    public Ship2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Ship1 extends BigshipGeneric
    implements TgtShip
  {
    public Ship1()
    {
    }

    public Ship1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Ship0 extends BigshipGeneric
    implements TgtShip
  {
    public Ship0()
    {
    }

    public Ship0(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Boat1 extends ShipGeneric
    implements TgtShip
  {
    public Boat1()
    {
    }

    public Boat1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3); }  } 
  public static class MFPIT extends ShipGeneric implements TgtShip { public MFPIT() {  } 
    public MFPIT(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3) { super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class MFPT extends ShipGeneric
    implements TgtShip
  {
    public MFPT()
    {
    }

    public MFPT(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSMcKean extends BigshipGeneric
    implements TgtShip
  {
    public USSMcKean()
    {
    }

    public USSMcKean(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Transport6 extends BigshipGeneric
    implements TgtShip
  {
    public Transport6()
    {
    }

    public Transport6(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Transport5 extends BigshipGeneric
    implements TgtShip
  {
    public Transport5()
    {
    }

    public Transport5(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Transport4 extends BigshipGeneric
    implements TgtShip
  {
    public Transport4()
    {
    }

    public Transport4(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class TroopTrans3 extends BigshipGeneric
    implements TgtShip
  {
    public TroopTrans3()
    {
    }

    public TroopTrans3(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class TroopTrans2 extends BigshipGeneric
    implements TgtShip
  {
    public TroopTrans2()
    {
    }

    public TroopTrans2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class TroopTrans1 extends BigshipGeneric
    implements TgtShip
  {
    public TroopTrans1()
    {
    }

    public TroopTrans1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class TroopTrans0 extends BigshipGeneric
    implements TgtShip
  {
    public TroopTrans0()
    {
    }

    public TroopTrans0(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Italia1 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Italia1()
    {
    }

    public Italia1(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Italia0 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Italia0()
    {
    }

    public Italia0(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Soldati extends BigshipGeneric
    implements TgtShip
  {
    public Soldati()
    {
    }

    public Soldati(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Littorio extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Littorio()
    {
    }

    public Littorio(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSFiji extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public HMSFiji()
    {
    }

    public HMSFiji(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSTribal extends BigshipGeneric
    implements TgtShip
  {
    public HMSTribal()
    {
    }

    public HMSTribal(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSSanJacintoCVL30 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSSanJacintoCVL30()
    {
    }

    public USSSanJacintoCVL30(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSBelleauWoodCVL24 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSBelleauWoodCVL24()
    {
    }

    public USSBelleauWoodCVL24(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSPrincetonCVL23 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSPrincetonCVL23()
    {
    }

    public USSPrincetonCVL23(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Destroyer_USSR_Type7_44 extends ShipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Destroyer_USSR_Type7_44()
    {
    }

    public Destroyer_USSR_Type7_44(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Aurora extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Aurora()
    {
    }

    public Aurora(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Marat extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Marat()
    {
    }

    public Marat(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Kirov extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Kirov()
    {
    }

    public Kirov(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
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

  public static class USSLexingtonCV2 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSLexingtonCV2()
    {
    }

    public USSLexingtonCV2(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSSaratogaCV3 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSSaratogaCV3()
    {
    }

    public USSSaratogaCV3(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSCVGeneric extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSCVGeneric()
    {
    }

    public USSCVGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSBBGeneric extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSBBGeneric()
    {
    }

    public USSBBGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSIndianapolisCA35 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSIndianapolisCA35()
    {
    }

    public USSIndianapolisCA35(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
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

  public static class USSGreenlingSS213_Sub extends ShipGeneric
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

  public static class USSGatoSS212_Sub extends ShipGeneric
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

  public static class USSWardDD139 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSWardDD139()
    {
    }

    public USSWardDD139(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
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

  public static class USSFletcherDD445 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSFletcherDD445()
    {
    }

    public USSFletcherDD445(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSOBannonDD450 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSOBannonDD450()
    {
    }

    public USSOBannonDD450(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSKiddDD661 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSKiddDD661()
    {
    }

    public USSKiddDD661(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSCasablancaCVE55 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSCasablancaCVE55()
    {
    }

    public USSCasablancaCVE55(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSKitkunBayCVE71 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSKitkunBayCVE71()
    {
    }

    public USSKitkunBayCVE71(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSShamrockBayCVE84 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSShamrockBayCVE84()
    {
    }

    public USSShamrockBayCVE84(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSEssexCV9 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSEssexCV9()
    {
    }

    public USSEssexCV9(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class USSIntrepidCV11 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public USSIntrepidCV11()
    {
    }

    public USSIntrepidCV11(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSIllustriousCV extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public HMSIllustriousCV()
    {
    }

    public HMSIllustriousCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSPoWBB extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public HMSPoWBB()
    {
    }

    public HMSPoWBB(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSKingGeorgeVBB extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public HMSKingGeorgeVBB()
    {
    }

    public HMSKingGeorgeVBB(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class HMSDukeOfYorkBB extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public HMSDukeOfYorkBB()
    {
    }

    public HMSDukeOfYorkBB(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class Murgesku extends ShipGeneric
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

  public static class Niobe extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Niobe()
    {
    }

    public Niobe(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
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

  public static class Tirpitz extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public Tirpitz()
    {
    }

    public Tirpitz(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNSoryuCV extends BigshipGeneric
    implements TgtShip, TypeRadar
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

    public IJNSoryuCV()
    {
      this.Range = 100000.0F;
      this.Type = 0;
    }

    public IJNSoryuCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
      this.Range = 100000.0F;
      this.Type = 0;
    }
  }

  public static class IJNKagaCV extends BigshipGeneric
    implements TgtShip, TypeRadar
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

    public IJNKagaCV()
    {
      this.Range = 100000.0F;
      this.Type = 0;
    }

    public IJNKagaCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
      this.Range = 100000.0F;
      this.Type = 0;
    }
  }

  public static class IJNHiryuCV extends BigshipGeneric
    implements TgtShip, TypeRadar
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

    public IJNHiryuCV()
    {
      this.Range = 100000.0F;
      this.Type = 0;
    }

    public IJNHiryuCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
      this.Range = 100000.0F;
      this.Type = 0;
    }
  }

  public static class IJNAkagiCV extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public IJNAkagiCV()
    {
    }

    public IJNAkagiCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNShokakuCV extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public IJNShokakuCV()
    {
    }

    public IJNShokakuCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNZuikakuCV extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public IJNZuikakuCV()
    {
    }

    public IJNZuikakuCV(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNCVLGeneric extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public IJNCVLGeneric()
    {
    }

    public IJNCVLGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNCVGeneric extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 100000.0F;
    private int Type = 0;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public IJNCVGeneric()
    {
    }

    public IJNCVGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNBBGeneric extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public IJNBBGeneric()
    {
    }

    public IJNBBGeneric(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNAmatsukazeDD45 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public IJNAmatsukazeDD45()
    {
    }

    public IJNAmatsukazeDD45(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }

  public static class IJNYukikazeDD45 extends BigshipGeneric
    implements TgtShip, TypeRadar
  {
    private float Range = 25000.0F;
    private int Type = 1;

    public float getRange()
    {
      return this.Range;
    }

    public int getType() {
      return this.Type;
    }

    public IJNYukikazeDD45()
    {
    }

    public IJNYukikazeDD45(String paramString1, int paramInt, SectFile paramSectFile1, String paramString2, SectFile paramSectFile2, String paramString3)
    {
      super(paramInt, paramSectFile1, paramString2, paramSectFile2, paramString3);
    }
  }
}