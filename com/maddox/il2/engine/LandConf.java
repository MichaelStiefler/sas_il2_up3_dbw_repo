package com.maddox.il2.engine;

import com.maddox.il2.fm.Atmosphere;
import com.maddox.rts.IniFile;

public class LandConf
{
  public String heightMap;
  public String typeMap;
  public String camouflage = "SUMMER";
  public int declin = 45;
  public int month = 6;
  public String country;
  public String city;
  public String rail;
  public String road;
  public String highway;
  public boolean bBig = false;
  public int outsideMapCell = 2;
  public String HeightMap;
  public String[] Fields = new String[32];

  public void set(String paramString) {
    IniFile localIniFile = new IniFile(paramString, 0);
    this.heightMap = localIniFile.getValue("MAP", "HeightMap");
    this.typeMap = localIniFile.getValue("MAP", "TypeMap");

    this.camouflage = localIniFile.get("WORLDPOS", "CAMOUFLAGE", "SUMMER");
    this.declin = localIniFile.get("WORLDPOS", "DECLIN", 45);
    this.month = localIniFile.get("WORLDPOS", "MONTH", 6);

    int i = localIniFile.get("WORLDPOS", "PRESSURE", 760, 680, 800);
    int j = localIniFile.get("WORLDPOS", "TEMPERATURE", 15, -50, 50);
    Atmosphere.set(i, j);

    this.rail = localIniFile.getValue("ROADS", "Rail");
    this.road = localIniFile.getValue("ROADS", "Road");
    this.highway = localIniFile.getValue("ROADS", "Highway");

    this.country = localIniFile.getValue("OBJECTS", "Country");
    this.city = localIniFile.getValue("OBJECTS", "City");

    this.HeightMap = localIniFile.getValue("MAP", "HeightMap");

    this.Fields[0] = localIniFile.getValue("FIELDS", "LowLand0");
    this.Fields[1] = localIniFile.getValue("FIELDS", "LowLand1");
    this.Fields[2] = localIniFile.getValue("FIELDS", "LowLand2");
    this.Fields[3] = localIniFile.getValue("FIELDS", "LowLand3");
    this.Fields[4] = localIniFile.getValue("FIELDS", "MidLand0");
    this.Fields[5] = localIniFile.getValue("FIELDS", "MidLand1");
    this.Fields[6] = localIniFile.getValue("FIELDS", "MidLand2");
    this.Fields[7] = localIniFile.getValue("FIELDS", "MidLand3");
    this.Fields[8] = localIniFile.getValue("FIELDS", "Mount0");
    this.Fields[9] = localIniFile.getValue("FIELDS", "Mount1");
    this.Fields[10] = localIniFile.getValue("FIELDS", "Mount2");
    this.Fields[11] = localIniFile.getValue("FIELDS", "Mount3");
    this.Fields[12] = localIniFile.getValue("FIELDS", "Country0");
    this.Fields[13] = localIniFile.getValue("FIELDS", "Country1");
    this.Fields[14] = localIniFile.getValue("FIELDS", "Country2");
    this.Fields[15] = localIniFile.getValue("FIELDS", "Country3");
    this.Fields[16] = localIniFile.getValue("FIELDS", "City0");
    this.Fields[17] = localIniFile.getValue("FIELDS", "City1");
    this.Fields[18] = localIniFile.getValue("FIELDS", "City2");
    this.Fields[19] = localIniFile.getValue("FIELDS", "City3");
    this.Fields[20] = localIniFile.getValue("FIELDS", "AirField0");
    this.Fields[21] = localIniFile.getValue("FIELDS", "AirField1");
    this.Fields[22] = localIniFile.getValue("FIELDS", "AirField2");
    this.Fields[23] = localIniFile.getValue("FIELDS", "AirField3");
    this.Fields[24] = localIniFile.getValue("FIELDS", "Wood0");
    this.Fields[25] = localIniFile.getValue("FIELDS", "Wood1");
    this.Fields[26] = localIniFile.getValue("FIELDS", "Wood2");
    this.Fields[27] = localIniFile.getValue("FIELDS", "Wood3");
    this.Fields[28] = localIniFile.getValue("FIELDS", "Water0");
    this.Fields[29] = localIniFile.getValue("FIELDS", "Water1");
    this.Fields[30] = localIniFile.getValue("FIELDS", "Water2");
    this.Fields[31] = localIniFile.getValue("FIELDS", "Water3");

    int k = localIniFile.get("MAP2D_BIG", "OutsideMapCell", -1);
    if ((k == 8) || (k == 28)) {
      this.bBig = true;
      this.outsideMapCell = k;
    } else {
      this.bBig = false;
      this.outsideMapCell = 2;
    }
    CollideEnvXY.STATIC_HMAX = localIniFile.get("MAP", "STATIC_HMAX", 50.0F, 50.0F, 3000.0F);
  }
}