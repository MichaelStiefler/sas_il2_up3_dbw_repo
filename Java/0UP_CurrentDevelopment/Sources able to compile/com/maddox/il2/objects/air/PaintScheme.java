package com.maddox.il2.objects.air;

import com.maddox.il2.ai.Regiment;
import com.maddox.il2.ai.Squadron;
import com.maddox.il2.ai.Wing;
import com.maddox.il2.ai.World;
import com.maddox.il2.engine.Config;
import com.maddox.il2.engine.FObj;
import com.maddox.il2.engine.HierMesh;
import com.maddox.il2.engine.Mat;
import com.maddox.il2.engine.Mesh;
import com.maddox.il2.game.I18N;
import com.maddox.il2.game.Mission;
import com.maddox.rts.HomePath;
import com.maddox.rts.SectFile;
import java.io.File;

public abstract class PaintScheme
{

    public PaintScheme()
    {
    }

    public java.lang.String typedName(Aircraft aircraft)
    {
        com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)aircraft.getOwner();
        com.maddox.il2.ai.Squadron squadron = wing.squadron();
        com.maddox.il2.ai.Regiment regiment = wing.regiment();
        return typedName(aircraft.getClass(), regiment, squadron.indexInRegiment(), wing.indexInSquadron(), wing.aircIndex(aircraft));
    }

    public java.lang.String typedNameNum(Aircraft aircraft, int i)
    {
        com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)aircraft.getOwner();
        com.maddox.il2.ai.Squadron squadron = wing.squadron();
        com.maddox.il2.ai.Regiment regiment = wing.regiment();
        return typedNameNum(aircraft.getClass(), regiment, squadron.indexInRegiment(), wing.indexInSquadron(), i);
    }

    public java.lang.String typedName(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        return typedNameNum(class1, regiment, i, j, j * 4 + k + 1);
    }

    public void prepare(Aircraft aircraft)
    {
        prepare(aircraft, true);
    }

    public void prepare(Aircraft aircraft, boolean flag)
    {
        com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)aircraft.getOwner();
        com.maddox.il2.ai.Squadron squadron = wing.squadron();
        com.maddox.il2.ai.Regiment regiment = wing.regiment();
        prepare(aircraft.getClass(), aircraft.hierMesh(), regiment, squadron.indexInRegiment(), wing.indexInSquadron(), wing.aircIndex(aircraft), flag);
    }

    public void prepareNum(Aircraft aircraft, int i)
    {
        prepareNum(aircraft, i, true);
    }

    public void prepareNum(Aircraft aircraft, int i, boolean flag)
    {
        com.maddox.il2.ai.Wing wing = (com.maddox.il2.ai.Wing)aircraft.getOwner();
        com.maddox.il2.ai.Squadron squadron = wing.squadron();
        com.maddox.il2.ai.Regiment regiment = wing.regiment();
        prepareNum(aircraft.getClass(), aircraft.hierMesh(), regiment, squadron.indexInRegiment(), wing.indexInSquadron(), i, flag);
    }

    public void prepare(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        prepare(class1, hiermesh, regiment, i, j, k, true);
    }

    public void prepare(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k, boolean flag)
    {
        prepareNum(class1, hiermesh, regiment, i, j, j * 4 + k + 1, flag);
    }

    public java.lang.String typedNameNum(java.lang.Class class1, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        return "" + k;
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k, boolean flag)
    {
        if(flag)
        {
            prepareNum(class1, hiermesh, regiment, i, j, k);
        } else
        {
            if(regiment.country() == countryUSA || regiment.country() == countryBritain && "rn".equals(regiment.branch()) || regiment.country() == countryJapan || regiment.country() == countryUSABlue)
                changeMat(null, hiermesh, "Overlay5", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            else
                changeMat(null, hiermesh, "Overlay5", regiment.name(), regiment.fileNameTga(), 1.0F, 1.0F, 1.0F);
            changeMat(null, hiermesh, "Overlay1", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(null, hiermesh, "Overlay2", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(null, hiermesh, "Overlay3", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(null, hiermesh, "Overlay4", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(null, hiermesh, "Overlay6", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(null, hiermesh, "Overlay7", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            changeMat(null, hiermesh, "Overlay8", "null", "null.tga", 1.0F, 1.0F, 1.0F);
            prepareNumOff(class1, hiermesh, regiment, i, j, k);
        }
    }

    public void prepareNumOff(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
    }

    public void prepareNum(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, com.maddox.il2.ai.Regiment regiment, int i, int j, int k)
    {
        prepareNum(class1, hiermesh, regiment, i, j, k, false);
    }

    public static void init()
    {
        psGermanFighterString = (new java.lang.String[][] {
            new java.lang.String[] {
                com.maddox.il2.game.I18N.color("White") + " ", com.maddox.il2.game.I18N.color("Red") + " ", com.maddox.il2.game.I18N.color("Gold") + " ", com.maddox.il2.game.I18N.color("Green") + " "
            }, new java.lang.String[] {
                com.maddox.il2.game.I18N.color("White") + " ", com.maddox.il2.game.I18N.color("Black") + " ", com.maddox.il2.game.I18N.color("Brown") + " ", com.maddox.il2.game.I18N.color("Blue") + " "
            }
        });
        psRussianBomberString = (new java.lang.String[] {
            com.maddox.il2.game.I18N.color("Red"), com.maddox.il2.game.I18N.color("Yellow"), com.maddox.il2.game.I18N.color("Aqua"), com.maddox.il2.game.I18N.color("Green")
        });
        psFinnishFighterString = (new java.lang.String[][] {
            new java.lang.String[] {
                com.maddox.il2.game.I18N.color("Red") + " ", com.maddox.il2.game.I18N.color("White") + " ", com.maddox.il2.game.I18N.color("Yellow") + " ", com.maddox.il2.game.I18N.color("Blue") + " "
            }, new java.lang.String[] {
                com.maddox.il2.game.I18N.color("Red") + " ", com.maddox.il2.game.I18N.color("White") + " ", com.maddox.il2.game.I18N.color("Yellow") + " ", com.maddox.il2.game.I18N.color("Blue") + " "
            }
        });
    }

    protected void changeMat(java.lang.Class class1, com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s, java.lang.String s1, java.lang.String s2, float f, float f1, 
            float f2)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        if(s2 == null || s2.length() < 4)
            return;
        char c = s2.charAt(s2.length() - 1);
        if(c == '\\' || c == '/')
            return;
        java.lang.String as[] = getCustomMarkings(class1, s1, s2, s);
        if(as != null)
        {
            s1 = as[0];
            s2 = as[1];
        }
        java.lang.String s3 = null;
        if(class1 != null)
        {
            s3 = getPlaneSpecificMaterialID(class1);
            if(s3 != null)
                s1 = s1 + "_" + s3;
        }
        int i = hiermesh.materialFind(s);
        if(i != -1)
        {
            com.maddox.il2.engine.Mat mat = PaintScheme.makeMat(s1, s2, f, f1, f2);
            if(mat != null)
            {
                hiermesh.materialReplace(s, mat);
                if(s3 != null)
                    changeMatAppearance(hiermesh, s);
            }
        }
    }

    protected void changeMat(com.maddox.il2.engine.HierMesh hiermesh, java.lang.String s, java.lang.String s1, java.lang.String s2, java.lang.String s3, float f, float f1, 
            float f2, float f3, float f4, float f5)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return;
        int i = hiermesh.materialFind(s);
        if(i != -1)
        {
            com.maddox.il2.engine.Mat mat = PaintScheme.makeMat(s1, s2, s3, f, f1, f2, f3, f4, f5);
            if(mat != null)
                hiermesh.materialReplace(s, mat);
        }
    }

    public static com.maddox.il2.engine.Mat makeMatGUI(java.lang.String s, java.lang.String s1, float f, float f1, float f2)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return null;
        if(s1 == null || s1.length() < 4)
            return null;
        char c = s1.charAt(s1.length() - 1);
        if(c == '\\' || c == '/')
            return null;
        java.lang.String s2 = "PaintSchemes/Cache/" + s + ".mat";
        if(com.maddox.il2.engine.FObj.Exist(s2))
            return com.maddox.il2.engine.Mat.New(s2);
        if((new File(s2)).exists())
        {
            return com.maddox.il2.engine.Mat.New(s2);
        } else
        {
            com.maddox.rts.SectFile sectfile = new SectFile("PaintSchemes/base1.mat", 0);
            sectfile.set("Layer0", "TextureName", com.maddox.rts.HomePath.concatNames("../Decals/", s1));
            sectfile.set("Layer0", "ColorScale", "" + f + " " + f1 + " " + f2 + " 1.0");
            sectfile.set("Layer0", "tfTestZ", "0");
            sectfile.saveFile(s2);
            return com.maddox.il2.engine.Mat.New(s2);
        }
    }

    public static com.maddox.il2.engine.Mat makeMat(java.lang.String s, java.lang.String s1, float f, float f1, float f2)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return null;
        if(s1 == null || s1.length() < 4)
            return null;
        char c = s1.charAt(s1.length() - 1);
        if(c == '\\' || c == '/')
            return null;
        java.lang.String s2 = "PaintSchemes/Cache/" + s + ".mat";
        if(com.maddox.il2.engine.FObj.Exist(s2))
            return com.maddox.il2.engine.Mat.New(s2);
        if((new File(s2)).exists())
        {
            return com.maddox.il2.engine.Mat.New(s2);
        } else
        {
            com.maddox.rts.SectFile sectfile = new SectFile("PaintSchemes/base1.mat", 0);
            sectfile.set("Layer0", "TextureName", com.maddox.rts.HomePath.concatNames("../Decals/", s1));
            sectfile.set("Layer0", "ColorScale", "" + f + " " + f1 + " " + f2 + " 1.0");
            sectfile.saveFile(s2);
            return com.maddox.il2.engine.Mat.New(s2);
        }
    }

    public static com.maddox.il2.engine.Mat makeMat(java.lang.String s, java.lang.String s1, java.lang.String s2, float f, float f1, float f2, float f3, float f4, 
            float f5)
    {
        if(!com.maddox.il2.engine.Config.isUSE_RENDER())
            return null;
        java.lang.String s3 = "PaintSchemes/Cache/" + s + ".mat";
        if(com.maddox.il2.engine.FObj.Exist(s3))
            return com.maddox.il2.engine.Mat.New(s3);
        if((new File(s3)).exists())
        {
            return com.maddox.il2.engine.Mat.New(s3);
        } else
        {
            com.maddox.rts.SectFile sectfile = new SectFile("PaintSchemes/base2.mat", 0);
            sectfile.set("Layer0", "TextureName", com.maddox.rts.HomePath.concatNames("../Decals/", s1));
            sectfile.set("Layer0", "ColorScale", "" + f + " " + f1 + " " + f2 + " 1.0");
            sectfile.set("Layer1", "TextureName", com.maddox.rts.HomePath.concatNames("../Decals/", s2));
            sectfile.set("Layer1", "ColorScale", "" + f3 + " " + f4 + " " + f5 + " 1.0");
            sectfile.saveFile(s3);
            return com.maddox.il2.engine.Mat.New(s3);
        }
    }

    protected java.lang.String getFAFACCode(java.lang.Class class1, int i)
    {
        if(class1.isAssignableFrom(BF_109E4.class))
            return "MT-1" + (i > 1 ? "7" : "6");
        if(class1.isAssignableFrom(BF_109E4B.class))
            return "MT-1" + (i > 1 ? "7" : "6");
        if(class1.isAssignableFrom(BF_109E7.class))
            return "MT-1" + (i > 1 ? "9" : "8");
        if(class1.isAssignableFrom(BF_109E7NZ.class))
            return "MT-1" + (i > 1 ? "9" : "8");
        if(class1.isAssignableFrom(BF_109F2.class))
            return "MT-1" + (i > 1 ? "9" : "8");
        if(class1.isAssignableFrom(BF_109G2.class))
            return "MT-2" + i;
        if(class1.isAssignableFrom(BF_109G6.class))
            return "MT-4" + (i + 4);
        if(class1.isAssignableFrom(BF_109G6Late.class))
            return "MT-4" + (i + 4);
        if(class1.isAssignableFrom(BF_109G6AS.class))
            return "MT-4" + (i + 4);
        if(class1.isAssignableFrom(BF_109K4.class))
            return "MT-52";
        if(class1.isAssignableFrom(BLENHEIM1.class))
            return "BL-1" + (i + 4);
        if(class1.isAssignableFrom(BLENHEIM4.class))
            return "BL-" + (i == 0 ? "12" : i == 1 ? "13" : i == 2 ? "19" : "20");
        if(class1.isAssignableFrom(F2A_B239.class))
            return "BW-3" + (i + 5);
        if(class1.isAssignableFrom(FI_156.class))
            return "ST-11";
        if(class1.isAssignableFrom(FW_189A2.class))
            return "UH-35";
        if(class1.isAssignableFrom(FW_190.class))
            return "FW-1" + i;
        if(class1.isAssignableFrom(G50.class))
            return "FA-" + i;
        if(class1.isAssignableFrom(GLADIATOR1.class))
            return "GL-2" + (5 + i);
        if(class1.isAssignableFrom(GLADIATOR1J8A.class))
            return "GL-2" + (5 + i);
        if(class1.isAssignableFrom(GLADIATOR2.class))
            return "GL-2" + (5 + i);
        if(class1.isAssignableFrom(HE_111H2.class) || class1.isAssignableFrom(HE_111H6.class))
            return "HE-1" + (i + 1);
        if(class1.isAssignableFrom(HE_111Z.class))
            return "HZ-33";
        if(class1.isAssignableFrom(HS_129B2.class))
            return "HS-2";
        if(class1.isAssignableFrom(HS_129B3Wa.class))
            return "HS-2";
        if(class1.isAssignableFrom(HurricaneMkIa.class))
            return "HC45";
        if(class1.isAssignableFrom(HurricaneMkIIb.class))
            return "HC46";
        if(class1.isAssignableFrom(HurricaneMkIIbMod.class))
            return "HC46";
        if(class1.isAssignableFrom(HurricaneMkIIc.class))
            return "HC46";
        if(class1.isAssignableFrom(I_153_M62.class))
            return "IT-" + (i + 1);
        if(class1.isAssignableFrom(IL_2.class))
            return "IL-";
        if(class1.isAssignableFrom(IL_4_DB3B.class))
            return "DB-" + (i > 1 ? "2" : "1");
        if(class1.isAssignableFrom(IL_4_DB3F.class))
            return "DB-" + (i > 1 ? "2" : "1");
        if(class1.isAssignableFrom(IL_4_DB3M.class))
            return "DB-" + (i > 1 ? "2" : "1");
        if(class1.isAssignableFrom(IL_4_DB3T.class))
            return "DB-" + (i > 1 ? "2" : "1");
        if(class1.isAssignableFrom(IL_4_IL4.class))
            return "DF-2";
        if(class1.isAssignableFrom(JU_52.class))
            return "UJ-" + i;
        if(class1.isAssignableFrom(JU_87B2.class))
            return "B-3";
        if(class1.isAssignableFrom(JU_87D3.class))
            return "B-4";
        if(class1.isAssignableFrom(JU_87G1.class))
            return "B-9";
        if(class1.isAssignableFrom(JU_88A4.class))
            return "JK-2" + ((int)(0.5F * (float)i) + 5);
        if(class1.isAssignableFrom(LA_5FN.class))
            return "LA-2" + i;
        if(class1.isAssignableFrom(LAGG_3IT.class))
            return "LG-";
        if(class1.isAssignableFrom(LAGG_3SERIES4.class))
            return "LG-";
        if(class1.isAssignableFrom(LAGG_3SERIES66.class))
            return "LG-";
        if(class1.isAssignableFrom(LI_2.class))
            return "DO-";
        if(class1.isAssignableFrom(MBR_2AM34.class))
            return "VV-18";
        if(class1.isAssignableFrom(MIG_3EARLY.class))
            return "MIG-0";
        if(class1.isAssignableFrom(MIG_3UD.class))
            return "MIG-1";
        if(class1.isAssignableFrom(MIG_3U.class))
            return "MIG-2";
        if(class1.isAssignableFrom(MS406.class))
            return "MS-3" + i;
        if(class1.isAssignableFrom(MS410.class))
            return "MS-6" + i;
        if(class1.isAssignableFrom(MSMORKO.class))
            return "MSv-3" + i;
        if(class1.isAssignableFrom(P_36A3.class))
            return "CU-50";
        if(class1.isAssignableFrom(P_36A4.class))
            return "CU-5" + (5 + i);
        if(class1.isAssignableFrom(P_39N.class))
            return "KN-35";
        if(class1.isAssignableFrom(P_39Q1.class))
            return "KU-35";
        if(class1.isAssignableFrom(P_39Q10.class))
            return "KU-79";
        if(class1.isAssignableFrom(P_40E.class))
            return "CU-50";
        if(class1.isAssignableFrom(P_40EM105.class))
            return "CU-51";
        if(class1.isAssignableFrom(P_40M.class))
            return "CU-52";
        if(class1.isAssignableFrom(PE_3SERIES1.class))
            return "PE-30";
        if(class1.isAssignableFrom(PE_2SERIES1.class))
            return "PE-21";
        if(class1.isAssignableFrom(PE_2SERIES110.class))
            return "PE-21";
        if(class1.isAssignableFrom(PE_2SERIES359.class))
            return "PE-21";
        if(class1.isAssignableFrom(PE_2SERIES84.class))
            return "PE-21";
        if(class1.isAssignableFrom(PE_3BIS.class))
            return "PE-30";
        if(class1.isAssignableFrom(R_10.class))
            return "R-";
        if(class1.isAssignableFrom(SB_2M100A.class))
            return "SB-" + i;
        if(class1.isAssignableFrom(SB_2M103.class))
            return "SB-" + i;
        if(class1.isAssignableFrom(U_2VS.class))
            return "VU-";
        if(class1.isAssignableFrom(YAK_1B.class))
            return "YK-12";
        if(class1.isAssignableFrom(YAK_3.class))
            return "YK-52";
        if(class1.isAssignableFrom(YAK_9U.class))
            return "YK-54";
        else
            return "MT-00";
    }

    protected java.lang.String getCHFMCode(java.lang.Class class1)
    {
        if(class1.isAssignableFrom(BF_109E4.class))
            return "J-2";
        if(class1.isAssignableFrom(BF_109E4B.class))
            return "J-2";
        if(class1.isAssignableFrom(BF_109E7.class))
            return "J-2";
        if(class1.isAssignableFrom(BF_109E7NZ.class))
            return "J-2";
        if(class1.isAssignableFrom(BF_109F2.class))
            return "J-4";
        if(class1.isAssignableFrom(BF_109G2.class))
            return "J-6";
        if(class1.isAssignableFrom(BF_109G6.class))
            return "J-6";
        if(class1.isAssignableFrom(BF_109G6Late.class))
            return "J-8";
        if(class1.isAssignableFrom(BF_109G6AS.class))
            return "J-8";
        if(class1.isAssignableFrom(BF_109K4.class))
            return "J-8";
        if(class1.isAssignableFrom(MS406.class))
            return "J-0";
        if(class1.isAssignableFrom(MS410.class))
            return "J-2";
        else
            return "J-4";
    }

    protected java.lang.String getCHBMCode(java.lang.Class class1)
    {
        if(class1.isAssignableFrom(C_47.class))
            return "A-7";
        if(class1.isAssignableFrom(JU_52_3MG4E.class))
            return "A-7";
        if(class1.isAssignableFrom(JU_52_3MG5E.class))
            return "A-7";
        else
            return "B-0";
    }

    protected int clampToLiteral(int i)
    {
        if(i < 1)
            return 1;
        if(i > 26)
            return 26;
        else
            return i;
    }

    private java.lang.String getPlaneSpecificMaterialID(java.lang.Class class1)
    {
        if(class1.isAssignableFrom(IL_2I.class) || class1.isAssignableFrom(IL_2MLate.class) || class1.isAssignableFrom(IL_2Type3.class) || class1.isAssignableFrom(IL_2Type3M.class))
            return "IL-2";
        if(class1.isAssignableFrom(MIG_3EARLY.class) || class1.isAssignableFrom(MIG_3AM38.class) || class1.isAssignableFrom(MIG_3POKRYSHKIN.class) || class1.isAssignableFrom(MIG_3SHVAK.class) || class1.isAssignableFrom(MIG_3UB.class) || class1.isAssignableFrom(MIG_3UD.class))
            return "MIG-3";
        if(class1.isAssignableFrom(U_2VS.class))
            return "U-2";
        if(class1.isAssignableFrom(R_10.class))
            return "R-10";
        else
            return null;
    }

    private java.lang.String[] getCustomMarkings(java.lang.Class class1, java.lang.String s, java.lang.String s1, java.lang.String s2)
    {
        if(class1 == null)
            return null;
        if(s.equals("whitestar1"))
        {
            int i = com.maddox.il2.game.Mission.getMissionDate(true);
            if(i > 0)
            {
                if(i < 0x1285563)
                {
                    s1 = "States/whitestar1_early.tga";
                    s = "whitestar1_early";
                }
                if(i > 0x12857b4 && i < 0x1287ce4 && s2.equals("Overlay7") && (com.maddox.il2.ai.World.cur().camouflage == 2 || com.maddox.il2.ai.World.cur().camouflage == 5))
                {
                    s1 = "States/whitestar1_OT.tga";
                    s = "whitestar1_OT";
                }
                return (new java.lang.String[] {
                    s, s1
                });
            }
        } else
        {
            if(s.equals("redstar0") || s.equals("redstar1") || s.equals("redstar2") || s.equals("redstar3"))
            {
                int j = com.maddox.il2.game.Mission.getMissionDate(true);
                if(j > 0)
                {
                    if((PE_8.class).isAssignableFrom(class1))
                    {
                        if(j < 0x128582f)
                        {
                            s1 = "Russian/redstar1.tga";
                            s = "redstar1";
                        } else
                        {
                            s1 = "Russian/redstar3.tga";
                            s = "redstar3";
                        }
                        return (new java.lang.String[] {
                            s, s1
                        });
                    }
                    if(j < 0x127e218 && !(SB_2M100A.class).isAssignableFrom(class1) && !(SB_2M103.class).isAssignableFrom(class1) && !(IL_4_DB3B.class).isAssignableFrom(class1) && !(IL_4_DB3F.class).isAssignableFrom(class1) && !(IL_4_DB3M.class).isAssignableFrom(class1) && !(IL_4_DB3T.class).isAssignableFrom(class1) && !(IL_4_IL4.class).isAssignableFrom(class1))
                    {
                        s1 = "Russian/redstar0.tga";
                        s = "redstar0";
                    } else
                    if(j < 0x1285554)
                    {
                        s1 = "Russian/redstar1_border.tga";
                        s = "redstar1_border";
                    } else
                    if(j < 0x1287d2c)
                    {
                        s1 = "Russian/redstar1.tga";
                        s = "redstar1";
                    } else
                    if(j < 0x1287d2c)
                    {
                        s1 = "Russian/redstar2.tga";
                        s = "redstar2";
                    } else
                    {
                        s1 = "Russian/redstar3.tga";
                        s = "redstar3";
                    }
                    return (new java.lang.String[] {
                        s, s1
                    });
                } else
                {
                    return null;
                }
            }
            if((DO_335A0.class).isAssignableFrom(class1) || (DO_335V13.class).isAssignableFrom(class1))
            {
                if(s.equals("balken2"))
                    return (new java.lang.String[] {
                        "balken3", "German/balken3.tga"
                    });
            } else
            if((BF_109G6AS.class).isAssignableFrom(class1) || (BF_109G6Late.class).isAssignableFrom(class1) || (BF_109G10.class).isAssignableFrom(class1) || (FW_190F8.class).isAssignableFrom(class1) || (FW_190A8.class).isAssignableFrom(class1) || (FW_190A6.class).isAssignableFrom(class1))
            {
                if(s.equals("balken0"))
                    return (new java.lang.String[] {
                        "balken4", "German/balken4.tga"
                    });
                if(s.equals("balken1"))
                    return (new java.lang.String[] {
                        "balken2", "German/balken2.tga"
                    });
            } else
            if((FW_190A9.class).isAssignableFrom(class1))
            {
                if(s.equals("balken0"))
                    return (new java.lang.String[] {
                        "balken4", "German/balken4.tga"
                    });
                if(s.equals("balken1"))
                    return (new java.lang.String[] {
                        "balken5", "German/balken5.tga"
                    });
            }
        }
        return null;
    }

    private void changeMatAppearance(com.maddox.il2.engine.Mesh mesh, java.lang.String s)
    {
        int i = mesh.materialFind("Matt1D0o");
        int j = mesh.materialFind(s);
        if(i == -1 || j == -1)
            return;
        com.maddox.il2.engine.Mat mat = mesh.material(i);
        com.maddox.il2.engine.Mat mat1 = mesh.material(j);
        if(mat1 == null || mat == null)
            return;
        try
        {
            mat1.set((byte)22, mat.get((byte)22));
            mat1.set((byte)23, mat.get((byte)23));
            mat1.set((byte)24, mat.get((byte)24));
            mat1.set((byte)20, mat.get((byte)20));
            mat1.set((byte)21, mat.get((byte)21));
        }
        catch(java.lang.Exception exception) { }
    }

    public static final java.lang.String countryGermany = "de".intern();
    public static final java.lang.String countryFinland = "fi".intern();
    public static final java.lang.String countryFrance = "fr".intern();
    public static final java.lang.String countryBritain = "gb".intern();
    public static final java.lang.String countryHungary = "hu".intern();
    public static final java.lang.String countryItaly = "it".intern();
    public static final java.lang.String countryJapan = "ja".intern();
    public static final java.lang.String countryNetherlands = "du".intern();
    public static final java.lang.String countryNoName = "nn".intern();
    public static final java.lang.String countryPoland = "pl".intern();
    public static final java.lang.String countryRomania = "ro".intern();
    public static final java.lang.String countryRussia = "ru".intern();
    public static final java.lang.String countryNewZealand = "rz".intern();
    public static final java.lang.String countrySlovakia = "sk".intern();
    public static final java.lang.String countryUSA = "us".intern();
    public static final java.lang.String countrySwitzerland = "ch".intern();
    public static final java.lang.String countryChina = "cn".intern();
    public static final java.lang.String countrySpainRep = "es".intern();
    public static final java.lang.String countrySpainNat = "sp".intern();
    public static final java.lang.String countryIsrael = "is".intern();
    public static final java.lang.String countryItalyAllied = "il".intern();
    public static final java.lang.String countryRomaniaAllied = "rl".intern();
    public static final java.lang.String countryFinlandAllied = "fl".intern();
    public static final java.lang.String countryBelgium = "be".intern();
    public static final java.lang.String countryBulgaria = "bg".intern();
    public static final java.lang.String countryNorway = "no".intern();
    public static final java.lang.String countryGreece = "gr".intern();
    public static final java.lang.String countryBrazil = "br".intern();
    public static final java.lang.String countrySweden = "sw".intern();
    public static final java.lang.String countrySouthAfrica = "sa".intern();
    public static final java.lang.String countryYugoslavia = "yu".intern();
    public static final java.lang.String countryYugoPar = "yp".intern();
    public static final java.lang.String countryDenmark = "dk".intern();
    public static final java.lang.String countryPhilippines = "ph".intern();
    public static final java.lang.String countryCroatia = "hr".intern();
    public static final java.lang.String countryBulgariaAllied = "bl".intern();
    public static final java.lang.String countryThailand = "th".intern();
    public static final java.lang.String countryGDR = "dd".intern();
    public static final java.lang.String countryHungaryAllied = "hy".intern();
    public static final java.lang.String countryEgypt = "eg".intern();
    public static final java.lang.String countryCzechoslovakia = "cz".intern();
    public static final java.lang.String countryIran = "ir".intern();
    public static final java.lang.String countryNorthKorea = "kp".intern();
    public static final java.lang.String countrySouthKorea = "kr".intern();
    public static final java.lang.String countrySyria = "sy".intern();
    public static final java.lang.String countryItalyANR = "an".intern();
    public static final java.lang.String countryVichyFrance = "vi".intern();
    public static final java.lang.String countryChinaRed = "cx".intern();
    public static final java.lang.String countryNorthKoreaRed = "kx".intern();
    public static final java.lang.String countrySyriaRed = "sx".intern();
    public static final java.lang.String countryEgyptRed = "ex".intern();
    public static final java.lang.String countryIndonesia = "do".intern();
    public static final java.lang.String countryIsraelBlue = "iq".intern();
    public static final java.lang.String countrySouthKoreaBlue = "kq".intern();
    public static final java.lang.String countryThailandBlue = "ty".intern();
    public static final java.lang.String countryBritainBlue = "vb".intern();
    public static final java.lang.String countryUSABlue = "na".intern();
    public static final java.lang.String countryCanadaBlue = "cq".intern();
    public static final java.lang.String countryMalaysia = "my".intern();
    public static final java.lang.String countrySingapore = "si".intern();
    public static final java.lang.String countryAustralia = "as".intern();
    public static final java.lang.String countryNewZeeland = "nz".intern();
    public static final java.lang.String countryGermanyBlue = "gp".intern();
    public static final java.lang.String countryVietnamNorth = "nv".intern();
    public static final java.lang.String countryVietnamSouth = "vn".intern();
    public static final char psGermanBomberLetter[][] = {
        {
            'H', 'K', 'L', 'B'
        }, {
            'M', 'N', 'P', 'C'
        }, {
            'R', 'S', 'T', 'D'
        }, {
            'U', 'V', 'W', 'E'
        }, {
            'X', 'Y', 'Z', 'F'
        }
    };
    public static final float psGermanBomberColor[][] = {
        {
            0.945F, 0.929F, 0.886F
        }, {
            0.584F, 0.122F, 0.122F
        }, {
            0.89F, 0.729F, 0.0F
        }, {
            0.247F, 0.806F, 0.0F
        }
    };
    public static java.lang.String psGermanFighterString[][];
    public static final char psGermanFighterGruppeChar[][] = {
        {
            ' ', '-', '~', '\244'
        }, {
            ' ', '-', '|', '\261'
        }
    };
    public static java.lang.String psRussianBomberString[];
    public static final float psRussianBomberColor[][] = {
        {
            0.718F, 0.176F, 0.102F
        }, {
            1.0F, 0.843F, 0.314F
        }, {
            0.247F, 0.733F, 0.937F
        }, {
            0.263F, 0.447F, 0.184F
        }
    };
    public static java.lang.String psFinnishFighterString[][];
    public static final float psFinnishFighterColor[][] = {
        {
            0.584F, 0.122F, 0.122F
        }, {
            0.945F, 0.929F, 0.886F
        }, {
            0.89F, 0.729F, 0.0F
        }, {
            0.1F, 0.235F, 0.695F
        }
    };
    public static final java.lang.String psFinnishFighterPrefix[][] = {
        {
            "German/02", "German/", "German/03", "German/10"
        }, {
            "German/02", "German/01", "German/03", "German/10"
        }
    };
    public static final float psBritishGrayColor[] = {
        0.7255F, 0.8431F, 0.6471F
    };
    public static final float psBritishSkyColor[] = {
        0.7255F, 0.8431F, 0.6471F
    };
    public static final float psBritishOrangeColor[] = {
        0.988F, 0.8F, 0.08F
    };
    public static final float psBritishBlackColor[] = {
        0.0F, 0.0F, 0.0F
    };
    public static final float psBritishRedColor[] = {
        0.6824F, 0.2471F, 0.1451F
    };
    public static final float psBritishWhiteColor[] = {
        0.99F, 0.99F, 0.99F
    };
    protected static final java.lang.String prefixCachePath = "PaintSchemes/Cache/";
    protected static final java.lang.String prefixInsigniaPath = "../Decals/";
    protected static final java.lang.String mat1Path = "PaintSchemes/base1.mat";
    protected static final java.lang.String mat2Path = "PaintSchemes/base2.mat";

}