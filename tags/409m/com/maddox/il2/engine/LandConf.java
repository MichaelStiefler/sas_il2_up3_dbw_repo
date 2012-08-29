// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   LandConf.java

package com.maddox.il2.engine;

import com.maddox.il2.fm.Atmosphere;
import com.maddox.rts.IniFile;

// Referenced classes of package com.maddox.il2.engine:
//            CollideEnvXY

public class LandConf
{

    public LandConf()
    {
        camouflage = "SUMMER";
        declin = 45;
        month = 6;
        bBig = false;
        outsideMapCell = 2;
        Fields = new java.lang.String[32];
    }

    public void set(java.lang.String s)
    {
        com.maddox.rts.IniFile inifile = new IniFile(s, 0);
        heightMap = inifile.getValue("MAP", "HeightMap");
        typeMap = inifile.getValue("MAP", "TypeMap");
        camouflage = inifile.get("WORLDPOS", "CAMOUFLAGE", "SUMMER");
        declin = inifile.get("WORLDPOS", "DECLIN", 45);
        month = inifile.get("WORLDPOS", "MONTH", 6);
        int i = inifile.get("WORLDPOS", "PRESSURE", 760, 680, 800);
        int j = inifile.get("WORLDPOS", "TEMPERATURE", 15, -50, 50);
        com.maddox.il2.fm.Atmosphere.set(i, j);
        rail = inifile.getValue("ROADS", "Rail");
        road = inifile.getValue("ROADS", "Road");
        highway = inifile.getValue("ROADS", "Highway");
        country = inifile.getValue("OBJECTS", "Country");
        city = inifile.getValue("OBJECTS", "City");
        HeightMap = inifile.getValue("MAP", "HeightMap");
        Fields[0] = inifile.getValue("FIELDS", "LowLand0");
        Fields[1] = inifile.getValue("FIELDS", "LowLand1");
        Fields[2] = inifile.getValue("FIELDS", "LowLand2");
        Fields[3] = inifile.getValue("FIELDS", "LowLand3");
        Fields[4] = inifile.getValue("FIELDS", "MidLand0");
        Fields[5] = inifile.getValue("FIELDS", "MidLand1");
        Fields[6] = inifile.getValue("FIELDS", "MidLand2");
        Fields[7] = inifile.getValue("FIELDS", "MidLand3");
        Fields[8] = inifile.getValue("FIELDS", "Mount0");
        Fields[9] = inifile.getValue("FIELDS", "Mount1");
        Fields[10] = inifile.getValue("FIELDS", "Mount2");
        Fields[11] = inifile.getValue("FIELDS", "Mount3");
        Fields[12] = inifile.getValue("FIELDS", "Country0");
        Fields[13] = inifile.getValue("FIELDS", "Country1");
        Fields[14] = inifile.getValue("FIELDS", "Country2");
        Fields[15] = inifile.getValue("FIELDS", "Country3");
        Fields[16] = inifile.getValue("FIELDS", "City0");
        Fields[17] = inifile.getValue("FIELDS", "City1");
        Fields[18] = inifile.getValue("FIELDS", "City2");
        Fields[19] = inifile.getValue("FIELDS", "City3");
        Fields[20] = inifile.getValue("FIELDS", "AirField0");
        Fields[21] = inifile.getValue("FIELDS", "AirField1");
        Fields[22] = inifile.getValue("FIELDS", "AirField2");
        Fields[23] = inifile.getValue("FIELDS", "AirField3");
        Fields[24] = inifile.getValue("FIELDS", "Wood0");
        Fields[25] = inifile.getValue("FIELDS", "Wood1");
        Fields[26] = inifile.getValue("FIELDS", "Wood2");
        Fields[27] = inifile.getValue("FIELDS", "Wood3");
        Fields[28] = inifile.getValue("FIELDS", "Water0");
        Fields[29] = inifile.getValue("FIELDS", "Water1");
        Fields[30] = inifile.getValue("FIELDS", "Water2");
        Fields[31] = inifile.getValue("FIELDS", "Water3");
        int k = inifile.get("MAP2D_BIG", "OutsideMapCell", -1);
        if(k == 8 || k == 28)
        {
            bBig = true;
            outsideMapCell = k;
        } else
        {
            bBig = false;
            outsideMapCell = 2;
        }
        com.maddox.il2.engine.CollideEnvXY.STATIC_HMAX = inifile.get("MAP", "STATIC_HMAX", 50F, 50F, 3000F);
    }

    public java.lang.String heightMap;
    public java.lang.String typeMap;
    public java.lang.String camouflage;
    public int declin;
    public int month;
    public java.lang.String country;
    public java.lang.String city;
    public java.lang.String rail;
    public java.lang.String road;
    public java.lang.String highway;
    public boolean bBig;
    public int outsideMapCell;
    public java.lang.String HeightMap;
    public java.lang.String Fields[];
}
