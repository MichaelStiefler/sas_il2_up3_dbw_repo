package com.maddox.il2.engine;

import com.maddox.il2.fm.Atmosphere;
import com.maddox.il2.game.ZutiAirfieldPoint;
import com.maddox.rts.IniFile;
import java.io.PrintStream;
import java.util.ArrayList;

public class LandConf
{
  public String heightMap;
  public String typeMap;
  public String camouflage;
  public int declin;
  public int month;
  public String country;
  public String city;
  public String rail;
  public String road;
  public String highway;
  public boolean bBig;
  public int outsideMapCell;
  public String HeightMap;
  public String[] Fields;
  public ArrayList ZutiAirports = null;

  public ArrayList ZutiReloadPlaces = null;
  public String zutiWaterState;

  public LandConf()
  {
    this.camouflage = "SUMMER";
    this.declin = 45;
    this.month = 6;
    this.bBig = false;
    this.outsideMapCell = 2;
    this.Fields = new String[32];
  }

  public void set(String paramString)
  {
    IniFile localIniFile = new IniFile(paramString, 0);
    this.heightMap = localIniFile.getValue("MAP", "HeightMap");
    this.typeMap = localIniFile.getValue("MAP", "TypeMap");
    this.camouflage = localIniFile.get("WORLDPOS", "CAMOUFLAGE", "SUMMER");

    this.zutiWaterState = localIniFile.get("WORLDPOS", "WATER_STATE", "");
    if ((this.zutiWaterState == null) || (this.zutiWaterState.trim().length() == 0))
    {
      if ("WINTER".equals(this.camouflage))
        this.zutiWaterState = "ICE";
      else {
        this.zutiWaterState = "LIQUID";
      }
    }

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

    System.out.println("Loading map.ini defined airfields:");
    if (this.ZutiAirports == null)
      this.ZutiAirports = new ArrayList();
    else {
      this.ZutiAirports.clear();
    }
    for (int m = 0; m < 999; m++)
    {
      String str1 = "";
      if (m < 10)
        str1 = "00" + new Integer(m).toString();
      else if ((m > 9) && (m < 100))
        str1 = "0" + new Integer(m).toString();
      else if (m > 99) {
        str1 = new Integer(m).toString();
      }
      String str2 = localIniFile.getValue("AIRFIELDS", "Airfield_" + str1);
      if (str2.trim().length() <= 0)
        continue;
      AddZutiAirfieldPoint_MapIni(str2);
    }

    if ((k == 8) || (k == 28))
    {
      this.bBig = true;
      this.outsideMapCell = k;
    }
    else
    {
      this.bBig = false;
      this.outsideMapCell = 2;
    }
    CollideEnvXY.STATIC_HMAX = localIniFile.get("MAP", "STATIC_HMAX", 150.0F, 50.0F, 3000.0F);

    if (this.ZutiAirports != null)
      System.out.println("Total number of airports for loaded mission: " + this.ZutiAirports.size());
  }

  public void AddZutiAirfieldPoint_MisIni(String paramString)
  {
    String str1 = paramString;
    try
    {
      double d1 = 0.0D;
      double d2 = 0.0D;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 3.8D;

      int i = 0;

      while (i <= 3)
      {
        String str2 = paramString.substring(0, paramString.indexOf(" "));
        paramString = paramString.substring(paramString.indexOf(" "), paramString.length()).trim();

        switch (i)
        {
        case 0:
          d1 = Double.parseDouble(str2);
          break;
        case 1:
          d2 = Double.parseDouble(str2);
          break;
        case 2:
          d3 = Double.parseDouble(str2);
          break;
        case 3:
          d4 = Double.parseDouble(str2);
          d5 = Double.parseDouble(paramString);
        }

        i++;
      }
      if (this.ZutiAirports != null)
        this.ZutiAirports.add(new ZutiAirfieldPoint(d1, d2, d3, d4, d5));
      else {
        System.out.println("ZutiAirports table is null!");
      }

      System.out.println("Mis file: line <" + str1 + "> is valid!");
    }
    catch (Exception localException)
    {
      System.out.println("Mis file: line <" + str1 + "> is INVALID!");
    }
  }

  private void AddZutiAirfieldPoint_MapIni(String paramString)
  {
    String str1 = paramString;
    try
    {
      double d1 = 0.0D;
      double d2 = 0.0D;
      double d3 = 0.0D;
      double d4 = 0.0D;
      double d5 = 3.8D;

      int i = 0;

      while (i <= 3)
      {
        String str2 = paramString.substring(0, paramString.indexOf(" "));
        paramString = paramString.substring(paramString.indexOf(" "), paramString.length()).trim();

        switch (i)
        {
        case 0:
          d1 = Double.parseDouble(str2);
          break;
        case 1:
          d2 = Double.parseDouble(str2);
          break;
        case 2:
          d3 = Double.parseDouble(str2);
          break;
        case 3:
          d4 = Double.parseDouble(str2);
          d5 = Double.parseDouble(paramString);
        }

        i++;
      }

      if (this.ZutiAirports != null)
        this.ZutiAirports.add(new ZutiAirfieldPoint(d1, d2, d3, d4, d5));
      else {
        System.out.println("ZutiAirports table is null!");
      }
      System.out.println("Map file: line <" + str1 + "> is valid!");
    }
    catch (Exception localException)
    {
      System.out.println("Map file: line <" + str1 + "> is INVALID!");
    }
  }
}