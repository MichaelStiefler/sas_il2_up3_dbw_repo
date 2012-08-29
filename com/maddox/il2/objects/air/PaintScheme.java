package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.game.I18N;
import com.maddox.rts.HomePath;
import com.maddox.rts.SectFile;
import java.io.File;

public abstract class PaintScheme
{
  public static final String countryGermany = "de".intern();
  public static final String countryFinland = "fi".intern();
  public static final String countryFrance = "fr".intern();
  public static final String countryRSpain = "es".intern();
  public static final String countryNSpain = "sp".intern();
  public static final String countryBrazil = "br".intern();
  public static final String countrySwitz = "ch".intern();
  public static final String countryCzech = "cz".intern();
  public static final String countryBritain = "gb".intern();
  public static final String countryHungary = "hu".intern();
  public static final String countryBulgaria = "bg".intern();
  public static final String countryBulgariaAl = "bl".intern();
  public static final String countryYugoslavia = "yu".intern();
  public static final String countryYugoPar = "yp".intern();
  public static final String countryItaly = "it".intern();
  public static final String countryANR = "an".intern();
  public static final String countryICAF = "il".intern();
  public static final String countryJapan = "ja".intern();
  public static final String countryChina = "cn".intern();
  public static final String countryNetherlands = "du".intern();
  public static final String countryBelgium = "be".intern();
  public static final String countryGreece = "gr".intern();
  public static final String countryNoName = "nn".intern();
  public static final String countryPoland = "pl".intern();
  public static final String countryRomania = "ro".intern();
  public static final String countryRomaniaAl = "rl".intern();
  public static final String countryRussia = "ru".intern();
  public static final String countryNewZealand = "rz".intern();
  public static final String countrySouthAfrica = "sa".intern();
  public static final String countrySlovakia = "sk".intern();
  public static final String countryCroatia = "hr".intern();
  public static final String countryUSA = "us".intern();
  public static final String countryIsrael = "is".intern();
  public static final String countryEgypt = "eg".intern();
  public static final String countrySyria = "sy".intern();
  public static final String countryIraq = "ir".intern();
  public static final String countryNorthKorea = "kp".intern();
  public static final String countrySouthKorea = "kr".intern();
  public static final char[][] psGermanBomberLetter = { { 'H', 'K', 'L', 'B' }, { 'M', 'N', 'P', 'C' }, { 'R', 'S', 'T', 'D' }, { 'U', 'V', 'W', 'E' }, { 'X', 'Y', 'Z', 'F' } };

  public static final float[][] psGermanBomberColor = { { 0.945F, 0.929F, 0.886F }, { 0.584F, 0.122F, 0.122F }, { 0.89F, 0.729F, 0.0F }, { 0.247F, 0.806F, 0.0F } };
  public static String[][] psGermanFighterString;
  public static final char[][] psGermanFighterGruppeChar = { { ' ', '-', '~', '¤' }, { ' ', '-', '|', '±' } };
  public static String[] psRussianBomberString;
  public static final float[][] psRussianBomberColor = { { 0.718F, 0.176F, 0.102F }, { 1.0F, 0.843F, 0.314F }, { 0.247F, 0.733F, 0.937F }, { 0.263F, 0.447F, 0.184F } };
  public static String[][] psFinnishFighterString;
  public static final float[][] psFinnishFighterColor = { { 0.584F, 0.122F, 0.122F }, { 0.945F, 0.929F, 0.886F }, { 0.89F, 0.729F, 0.0F }, { 0.1F, 0.235F, 0.695F } };

  public static final String[][] psFinnishFighterPrefix = { { "German/02", "German/", "German/03", "German/10" }, { "German/02", "German/01", "German/03", "German/10" } };

  public static final float[] psBritishGrayColor = { 0.7255F, 0.8431F, 0.6471F };

  public static final float[] psBritishSkyColor = { 0.5098F, 0.5412F, 0.549F };

  public static final float[] psBritishRedColor = { 0.6824F, 0.2471F, 0.1451F };

  public static final float[] psBritishWhiteColor = { 0.99F, 0.99F, 0.99F };
  protected static final String prefixCachePath = "PaintSchemes/Cache/";
  protected static final String prefixInsigniaPath = "../Decals/";
  protected static final String mat1Path = "PaintSchemes/base1.mat";
  protected static final String mat2Path = "PaintSchemes/base2.mat";

  public String typedName(Aircraft paramAircraft)
  {
    Wing localWing = (Wing)paramAircraft.getOwner();
    Squadron localSquadron = localWing.squadron();
    Regiment localRegiment = localWing.regiment();
    return typedName(paramAircraft.getClass(), localRegiment, localSquadron.indexInRegiment(), localWing.indexInSquadron(), localWing.aircIndex(paramAircraft));
  }

  public String typedNameNum(Aircraft paramAircraft, int paramInt)
  {
    Wing localWing = (Wing)paramAircraft.getOwner();
    Squadron localSquadron = localWing.squadron();
    Regiment localRegiment = localWing.regiment();
    return typedNameNum(paramAircraft.getClass(), localRegiment, localSquadron.indexInRegiment(), localWing.indexInSquadron(), paramInt);
  }

  public String typedName(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    return typedNameNum(paramClass, paramRegiment, paramInt1, paramInt2, paramInt2 * 4 + paramInt3 + 1);
  }

  public void prepare(Aircraft paramAircraft)
  {
    prepare(paramAircraft, true);
  }

  public void prepare(Aircraft paramAircraft, boolean paramBoolean)
  {
    Wing localWing = (Wing)paramAircraft.getOwner();
    Squadron localSquadron = localWing.squadron();
    Regiment localRegiment = localWing.regiment();
    prepare(paramAircraft.getClass(), paramAircraft.hierMesh(), localRegiment, localSquadron.indexInRegiment(), localWing.indexInSquadron(), localWing.aircIndex(paramAircraft), paramBoolean);
  }

  public void prepareNum(Aircraft paramAircraft, int paramInt)
  {
    prepareNum(paramAircraft, paramInt, true);
  }

  public void prepareNum(Aircraft paramAircraft, int paramInt, boolean paramBoolean)
  {
    Wing localWing = (Wing)paramAircraft.getOwner();
    Squadron localSquadron = localWing.squadron();
    Regiment localRegiment = localWing.regiment();
    prepareNum(paramAircraft.getClass(), paramAircraft.hierMesh(), localRegiment, localSquadron.indexInRegiment(), localWing.indexInSquadron(), paramInt, paramBoolean);
  }

  public void prepare(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    prepare(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3, true);
  }

  public void prepare(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt2 * 4 + paramInt3 + 1, paramBoolean);
  }

  public String typedNameNum(Class paramClass, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    return "" + paramInt3;
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    }
    else {
      if ((paramRegiment.country() == countryUSA) || ((paramRegiment.country() == countryBritain) && ("rn".equals(paramRegiment.branch()))) || (paramRegiment.country() == countryJapan))
        changeMat(paramHierMesh, "Overlay5", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      else
        changeMat(paramHierMesh, "Overlay5", paramRegiment.name(), paramRegiment.fileNameTga(), 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      changeMat(paramHierMesh, "Overlay8", "null", "null.tga", 1.0F, 1.0F, 1.0F);
      prepareNumOff(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3);
    }
  }

  public void prepareNumOff(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void prepareNum(Class paramClass, HierMesh paramHierMesh, Regiment paramRegiment, int paramInt1, int paramInt2, int paramInt3)
  {
    prepareNum(paramClass, paramHierMesh, paramRegiment, paramInt1, paramInt2, paramInt3, false);
  }

  public static void init()
  {
    psGermanFighterString = new String[][] { { I18N.color("White") + " ", I18N.color("Red") + " ", I18N.color("Gold") + " ", I18N.color("Green") + " " }, { I18N.color("White") + " ", I18N.color("Black") + " ", I18N.color("Brown") + " ", I18N.color("Blue") + " " } };

    psRussianBomberString = new String[] { I18N.color("Red"), I18N.color("Yellow"), I18N.color("Aqua"), I18N.color("Green") };

    psFinnishFighterString = new String[][] { { I18N.color("Red") + " ", I18N.color("White") + " ", I18N.color("Yellow") + " ", I18N.color("Blue") + " " }, { I18N.color("Red") + " ", I18N.color("White") + " ", I18N.color("Yellow") + " ", I18N.color("Blue") + " " } };
  }

  protected void changeMat(HierMesh paramHierMesh, String paramString1, String paramString2, String paramString3, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!Config.isUSE_RENDER())
      return;
    if ((paramString3 == null) || (paramString3.length() < 4))
      return;
    int i = paramString3.charAt(paramString3.length() - 1);
    if ((i == 92) || (i == 47))
      return;
    int j = paramHierMesh.materialFind(paramString1);
    if (j != -1)
    {
      Mat localMat = makeMat(paramString2, paramString3, paramFloat1, paramFloat2, paramFloat3);
      if (localMat != null)
        paramHierMesh.materialReplace(paramString1, localMat);
    }
  }

  protected void changeMat(HierMesh paramHierMesh, String paramString1, String paramString2, String paramString3, String paramString4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    if (!Config.isUSE_RENDER())
      return;
    int i = paramHierMesh.materialFind(paramString1);
    if (i != -1)
    {
      Mat localMat = makeMat(paramString2, paramString3, paramString4, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
      if (localMat != null)
        paramHierMesh.materialReplace(paramString1, localMat);
    }
  }

  public static Mat makeMatGUI(String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!Config.isUSE_RENDER())
      return null;
    if ((paramString2 == null) || (paramString2.length() < 4))
      return null;
    int i = paramString2.charAt(paramString2.length() - 1);
    if ((i == 92) || (i == 47))
      return null;
    String str = "PaintSchemes/Cache/" + paramString1 + ".mat";
    if (FObj.Exist(str))
      return Mat.New(str);
    if (new File(str).exists())
    {
      return Mat.New(str);
    }

    SectFile localSectFile = new SectFile("PaintSchemes/base1.mat", 0);
    localSectFile.set("Layer0", "TextureName", HomePath.concatNames("../Decals/", paramString2));
    localSectFile.set("Layer0", "ColorScale", "" + paramFloat1 + " " + paramFloat2 + " " + paramFloat3 + " 1.0");
    localSectFile.set("Layer0", "tfTestZ", "0");
    localSectFile.saveFile(str);
    return Mat.New(str);
  }

  public static Mat makeMat(String paramString1, String paramString2, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (!Config.isUSE_RENDER())
      return null;
    if ((paramString2 == null) || (paramString2.length() < 4))
      return null;
    int i = paramString2.charAt(paramString2.length() - 1);
    if ((i == 92) || (i == 47))
      return null;
    String str = "PaintSchemes/Cache/" + paramString1 + ".mat";
    if (FObj.Exist(str))
      return Mat.New(str);
    if (new File(str).exists())
    {
      return Mat.New(str);
    }

    SectFile localSectFile = new SectFile("PaintSchemes/base1.mat", 0);
    localSectFile.set("Layer0", "TextureName", HomePath.concatNames("../Decals/", paramString2));
    localSectFile.set("Layer0", "ColorScale", "" + paramFloat1 + " " + paramFloat2 + " " + paramFloat3 + " 1.0");
    localSectFile.saveFile(str);
    return Mat.New(str);
  }

  public static Mat makeMat(String paramString1, String paramString2, String paramString3, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
  {
    if (!Config.isUSE_RENDER())
      return null;
    String str = "PaintSchemes/Cache/" + paramString1 + ".mat";
    if (FObj.Exist(str))
      return Mat.New(str);
    if (new File(str).exists())
    {
      return Mat.New(str);
    }

    SectFile localSectFile = new SectFile("PaintSchemes/base2.mat", 0);
    localSectFile.set("Layer0", "TextureName", HomePath.concatNames("../Decals/", paramString2));
    localSectFile.set("Layer0", "ColorScale", "" + paramFloat1 + " " + paramFloat2 + " " + paramFloat3 + " 1.0");
    localSectFile.set("Layer1", "TextureName", HomePath.concatNames("../Decals/", paramString3));
    localSectFile.set("Layer1", "ColorScale", "" + paramFloat4 + " " + paramFloat5 + " " + paramFloat6 + " 1.0");
    localSectFile.saveFile(str);
    return Mat.New(str);
  }

  protected String getFAFACCode(Class paramClass, int paramInt)
  {
    if (paramClass.isAssignableFrom(BF_109E4.class))
      return "MT-1" + (paramInt > 1 ? "7" : "6");
    if (paramClass.isAssignableFrom(BF_109E4B.class))
      return "MT-1" + (paramInt > 1 ? "7" : "6");
    if (paramClass.isAssignableFrom(BF_109E7.class))
      return "MT-1" + (paramInt > 1 ? "9" : "8");
    if (paramClass.isAssignableFrom(BF_109E7NZ.class))
      return "MT-1" + (paramInt > 1 ? "9" : "8");
    if (paramClass.isAssignableFrom(BF_109F2.class))
      return "MT-1" + (paramInt > 1 ? "9" : "8");
    if (paramClass.isAssignableFrom(BF_109G2.class))
      return "MT-2" + paramInt;
    if (paramClass.isAssignableFrom(BF_109G6.class))
      return "MT-4" + (paramInt + 4);
    if (paramClass.isAssignableFrom(BF_109G6Late.class))
      return "MT-4" + (paramInt + 4);
    if (paramClass.isAssignableFrom(BF_109G6AS.class))
      return "MT-4" + (paramInt + 4);
    if (paramClass.isAssignableFrom(BF_109K4.class))
      return "MT-52";
    if (paramClass.isAssignableFrom(BLENHEIM1.class))
      return "BL-1" + (paramInt + 4);
    if (paramClass.isAssignableFrom(BLENHEIM4.class))
      return "BL-" + (paramInt == 2 ? "19" : paramInt == 1 ? "13" : paramInt == 0 ? "12" : "20");
    if (paramClass.isAssignableFrom(F2A_B239.class))
      return "BW-3" + (paramInt + 5);
    if (paramClass.isAssignableFrom(FI_156.class))
      return "ST-11";
    if (paramClass.isAssignableFrom(FW_189A2.class))
      return "UH-35";
    if (paramClass.isAssignableFrom(FW_190.class))
      return "FW-1" + paramInt;
    if (paramClass.isAssignableFrom(G50.class))
      return "FA-" + paramInt;
    if (paramClass.isAssignableFrom(GLADIATOR1.class))
      return "GL-2" + (5 + paramInt);
    if (paramClass.isAssignableFrom(GLADIATOR1J8A.class))
      return "GL-2" + (5 + paramInt);
    if (paramClass.isAssignableFrom(GLADIATOR2.class))
      return "GL-2" + (5 + paramInt);
    if ((paramClass.isAssignableFrom(HE_111H2.class)) || (paramClass.isAssignableFrom(HE_111H6.class)))
      return "HE-1" + (paramInt + 1);
    if (paramClass.isAssignableFrom(HE_111Z.class))
      return "HZ-33";
    if (paramClass.isAssignableFrom(HS_129B2.class))
      return "HS-2";
    if (paramClass.isAssignableFrom(HS_129B3Wa.class))
      return "HS-2";
    if (paramClass.isAssignableFrom(HurricaneMkIa.class))
      return "HC45";
    if (paramClass.isAssignableFrom(HurricaneMkIIb.class))
      return "HC46";
    if (paramClass.isAssignableFrom(HurricaneMkIIbMod.class))
      return "HC46";
    if (paramClass.isAssignableFrom(HurricaneMkIIc.class))
      return "HC46";
    if (paramClass.isAssignableFrom(I_153_M62.class))
      return "IT-" + (paramInt + 1);
    if (paramClass.isAssignableFrom(IL_2.class))
      return "IL-";
    if (paramClass.isAssignableFrom(IL_4_DB3B.class))
      return "DB-" + (paramInt > 1 ? "2" : "1");
    if (paramClass.isAssignableFrom(IL_4_DB3F.class))
      return "DB-" + (paramInt > 1 ? "2" : "1");
    if (paramClass.isAssignableFrom(IL_4_DB3M.class))
      return "DB-" + (paramInt > 1 ? "2" : "1");
    if (paramClass.isAssignableFrom(IL_4_DB3T.class))
      return "DB-" + (paramInt > 1 ? "2" : "1");
    if (paramClass.isAssignableFrom(IL_4_IL4.class))
      return "DF-2";
    if (paramClass.isAssignableFrom(JU_52.class))
      return "UJ-" + paramInt;
    if (paramClass.isAssignableFrom(JU_87B2.class))
      return "B-3";
    if (paramClass.isAssignableFrom(JU_87D3.class))
      return "B-4";
    if (paramClass.isAssignableFrom(JU_87G1.class))
      return "B-9";
    if (paramClass.isAssignableFrom(JU_88A4.class))
      return "JK-2" + ((int)(0.5F * paramInt) + 5);
    if (paramClass.isAssignableFrom(LA_5FN.class))
      return "LA-2" + paramInt;
    if (paramClass.isAssignableFrom(LAGG_3IT.class))
      return "LG-";
    if (paramClass.isAssignableFrom(LAGG_3SERIES4.class))
      return "LG-";
    if (paramClass.isAssignableFrom(LAGG_3SERIES66.class))
      return "LG-";
    if (paramClass.isAssignableFrom(LI_2.class))
      return "DO-";
    if (paramClass.isAssignableFrom(MBR_2AM34.class))
      return "VV-18";
    if (paramClass.isAssignableFrom(MIG_3EARLY.class))
      return "MIG-0";
    if (paramClass.isAssignableFrom(MIG_3UD.class))
      return "MIG-1";
    if (paramClass.isAssignableFrom(MIG_3U.class))
      return "MIG-2";
    if (paramClass.isAssignableFrom(MS406.class))
      return "MS-3" + paramInt;
    if (paramClass.isAssignableFrom(MS410.class))
      return "MS-6" + paramInt;
    if (paramClass.isAssignableFrom(MSMORKO.class))
      return "MSv-3" + paramInt;
    if (paramClass.isAssignableFrom(P_36A3.class))
      return "CU-50";
    if (paramClass.isAssignableFrom(P_36A4.class))
      return "CU-5" + (5 + paramInt);
    if (paramClass.isAssignableFrom(P_39N.class))
      return "KN-35";
    if (paramClass.isAssignableFrom(P_39Q1.class))
      return "KU-35";
    if (paramClass.isAssignableFrom(P_39Q10.class))
      return "KU-79";
    if (paramClass.isAssignableFrom(P_40E.class))
      return "CU-50";
    if (paramClass.isAssignableFrom(P_40EM105.class))
      return "CU-51";
    if (paramClass.isAssignableFrom(P_40M.class))
      return "CU-52";
    if (paramClass.isAssignableFrom(PE_3SERIES1.class))
      return "PE-30";
    if (paramClass.isAssignableFrom(PE_2SERIES1.class))
      return "PE-21";
    if (paramClass.isAssignableFrom(PE_2SERIES110.class))
      return "PE-21";
    if (paramClass.isAssignableFrom(PE_2SERIES359.class))
      return "PE-21";
    if (paramClass.isAssignableFrom(PE_2SERIES84.class))
      return "PE-21";
    if (paramClass.isAssignableFrom(PE_3BIS.class))
      return "PE-30";
    if (paramClass.isAssignableFrom(R_10.class))
      return "R-";
    if (paramClass.isAssignableFrom(SB_2M100A.class))
      return "SB-" + paramInt;
    if (paramClass.isAssignableFrom(SB_2M103.class))
      return "SB-" + paramInt;
    if (paramClass.isAssignableFrom(U_2VS.class))
      return "VU-";
    if (paramClass.isAssignableFrom(YAK_1B.class))
      return "YK-12";
    if (paramClass.isAssignableFrom(YAK_3.class))
      return "YK-52";
    if (paramClass.isAssignableFrom(YAK_9U.class)) {
      return "YK-54";
    }
    return "MT-00";
  }

  protected int clampToLiteral(int paramInt)
  {
    if (paramInt < 1)
      return 1;
    if (paramInt > 26) {
      return 26;
    }
    return paramInt;
  }
}