package com.maddox.il2.game;

import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18N
{
  static Res army;
  static Res color;
  static Res map;
  static Res gui;
  static Res bld;
  static Res plane;
  static Res weapons;
  static Res technic;
  static Res regimentShort;
  static Res regimentInfo;
  static Res gwindow;
  static Res hud_log;
  static Res credits;
  private static final String[] COUNTRY_KEYS = { "de", "fi", "fr", "gb", "hu", "it", "ja", "nn", "pl", "ro", "sk", "ru", "us", "un", "um", "ra", "rz", "rn", "in", "du" };

  public static String army(String paramString)
  {
    if (army == null) army = new Res("i18n/army");
    return army.get(paramString);
  }
  public static String color(String paramString) {
    if (color == null) color = new Res("i18n/color");
    return color.get(paramString);
  }
  public static String map(String paramString) {
    if (map == null) map = new Res("i18n/maps");
    return map.get(paramString);
  }
  public static String gui(String paramString) {
    if (gui == null) gui = new Res("i18n/gui");
    return gui.get(paramString);
  }
  public static boolean isGuiExist(String paramString) {
    if (gui == null) gui = new Res("i18n/gui");
    return gui.isExist(paramString);
  }
  public static String bld(String paramString) {
    if (bld == null) bld = new Res("i18n/bld");
    return bld.get(paramString);
  }
  public static String plane(String paramString) {
    if (plane == null) plane = new Res("i18n/plane");
    return plane.get(paramString);
  }
  public static String weapons(String paramString1, String paramString2) {
    if (weapons == null) weapons = new Res("i18n/weapons");
    String str = paramString1 + "." + paramString2;
    if (weapons.isExist(str))
      return weapons.get(str);
    return paramString2;
  }
  public static String technic(String paramString) {
    if (technic == null) technic = new Res("i18n/technics");
    return technic.get(paramString);
  }
  public static String regimentShort(String paramString) {
    if (regimentShort == null) regimentShort = new Res("i18n/regShort");
    return regimentShort.get(paramString);
  }
  public static String regimentInfo(String paramString) {
    if (regimentInfo == null) regimentInfo = new Res("i18n/regInfo");
    return regimentInfo.get(paramString);
  }
  public static String gwindow(String paramString) {
    if (paramString == null) return paramString;
    if (gwindow == null) gwindow = new Res("i18n/gwindow");
    if (gwindow.isExist(paramString))
      return gwindow.get(paramString);
    if (paramString.indexOf('_') < 0) return paramString;
    return paramString.replace('_', ' ');
  }
  public static String hud_log(String paramString) {
    if (hud_log == null) hud_log = new Res("i18n/hud_log");
    return hud_log.get(paramString);
  }
  public static String credits(String paramString) {
    if (credits == null) credits = new Res("i18n/credits");
    return credits.get(paramString);
  }

  public static String getCountryKey(String paramString)
  {
    ResourceBundle localResourceBundle = ResourceBundle.getBundle("i18n/country", RTSConf.cur.locale, LDRres.loader());
    for (int i = 0; i < COUNTRY_KEYS.length; i++)
    {
      String str = COUNTRY_KEYS[i];
      try
      {
        if (localResourceBundle.getString(str).equals(paramString)) {
          return str;
        }
      }
      catch (MissingResourceException localMissingResourceException)
      {
      }
    }
    return null;
  }

  static class Res
  {
    private String myName;
    ResourceBundle res;

    String get(String paramString)
    {
      if (this.res == null) return paramString; try
      {
        return this.res.getString(paramString);
      }
      catch (Exception localException)
      {
      }

      return paramString;
    }
    boolean isExist(String paramString) {
      if (this.res == null) return false; try
      {
        this.res.getString(paramString);
        return true;
      }
      catch (Exception localException)
      {
      }

      return false;
    }

    Res(String paramString) {
      try {
        this.res = ResourceBundle.getBundle(paramString, RTSConf.cur.locale, LDRres.loader());
        this.myName = paramString;
      }
      catch (Exception localException)
      {
      }
    }
  }
}