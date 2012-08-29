// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   I18N.java

package com.maddox.il2.game;

import com.maddox.rts.LDRres;
import com.maddox.rts.RTSConf;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class I18N
{
    static class Res
    {

        java.lang.String get(java.lang.String s)
        {
            if(res == null)
                return s;
            return res.getString(s);
            java.lang.Exception exception;
            exception;
            return s;
        }

        boolean isExist(java.lang.String s)
        {
            if(res == null)
                return false;
            res.getString(s);
            return true;
            java.lang.Exception exception;
            exception;
            return false;
        }

        private java.lang.String myName;
        java.util.ResourceBundle res;

        Res(java.lang.String s)
        {
            try
            {
                res = java.util.ResourceBundle.getBundle(s, com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
                myName = s;
            }
            catch(java.lang.Exception exception) { }
        }
    }


    public static java.lang.String army(java.lang.String s)
    {
        if(army == null)
            army = new Res("i18n/army");
        return army.get(s);
    }

    public static java.lang.String color(java.lang.String s)
    {
        if(color == null)
            color = new Res("i18n/color");
        return color.get(s);
    }

    public static java.lang.String map(java.lang.String s)
    {
        if(map == null)
            map = new Res("i18n/maps");
        return map.get(s);
    }

    public static java.lang.String gui(java.lang.String s)
    {
        if(gui == null)
            gui = new Res("i18n/gui");
        return gui.get(s);
    }

    public static boolean isGuiExist(java.lang.String s)
    {
        if(gui == null)
            gui = new Res("i18n/gui");
        return gui.isExist(s);
    }

    public static java.lang.String bld(java.lang.String s)
    {
        if(bld == null)
            bld = new Res("i18n/bld");
        return bld.get(s);
    }

    public static java.lang.String plane(java.lang.String s)
    {
        if(plane == null)
            plane = new Res("i18n/plane");
        return plane.get(s);
    }

    public static java.lang.String weapons(java.lang.String s, java.lang.String s1)
    {
        if(weapons == null)
            weapons = new Res("i18n/weapons");
        java.lang.String s2 = s + "." + s1;
        if(weapons.isExist(s2))
            return weapons.get(s2);
        else
            return s1;
    }

    public static java.lang.String technic(java.lang.String s)
    {
        if(technic == null)
            technic = new Res("i18n/technics");
        return technic.get(s);
    }

    public static java.lang.String regimentShort(java.lang.String s)
    {
        if(regimentShort == null)
            regimentShort = new Res("i18n/regShort");
        return regimentShort.get(s);
    }

    public static java.lang.String regimentInfo(java.lang.String s)
    {
        if(regimentInfo == null)
            regimentInfo = new Res("i18n/regInfo");
        return regimentInfo.get(s);
    }

    public static java.lang.String gwindow(java.lang.String s)
    {
        if(s == null)
            return s;
        if(gwindow == null)
            gwindow = new Res("i18n/gwindow");
        if(gwindow.isExist(s))
            return gwindow.get(s);
        if(s.indexOf('_') < 0)
            return s;
        else
            return s.replace('_', ' ');
    }

    public static java.lang.String hud_log(java.lang.String s)
    {
        if(hud_log == null)
            hud_log = new Res("i18n/hud_log");
        return hud_log.get(s);
    }

    public static java.lang.String credits(java.lang.String s)
    {
        if(credits == null)
            credits = new Res("i18n/credits");
        return credits.get(s);
    }

    public static java.lang.String getCountryKey(java.lang.String s)
    {
        java.util.ResourceBundle resourcebundle;
        int i;
        resourcebundle = java.util.ResourceBundle.getBundle("i18n/country", com.maddox.rts.RTSConf.cur.locale, com.maddox.rts.LDRres.loader());
        i = 0;
_L3:
        if(i >= COUNTRY_KEYS.length) goto _L2; else goto _L1
_L1:
        java.lang.String s1 = COUNTRY_KEYS[i];
        if(resourcebundle.getString(s1).equals(s))
            return s1;
        continue; /* Loop/switch isn't completed */
        java.util.MissingResourceException missingresourceexception;
        missingresourceexception;
        i++;
          goto _L3
_L2:
        return null;
    }

    private I18N()
    {
    }

    static com.maddox.il2.game.Res army;
    static com.maddox.il2.game.Res color;
    static com.maddox.il2.game.Res map;
    static com.maddox.il2.game.Res gui;
    static com.maddox.il2.game.Res bld;
    static com.maddox.il2.game.Res plane;
    static com.maddox.il2.game.Res weapons;
    static com.maddox.il2.game.Res technic;
    static com.maddox.il2.game.Res regimentShort;
    static com.maddox.il2.game.Res regimentInfo;
    static com.maddox.il2.game.Res gwindow;
    static com.maddox.il2.game.Res hud_log;
    static com.maddox.il2.game.Res credits;
    private static final java.lang.String COUNTRY_KEYS[] = {
        "de", "fi", "fr", "gb", "hu", "it", "ja", "nn", "pl", "ro", 
        "sk", "ru", "us", "un", "um", "ra", "rz", "rn", "in", "du"
    };

}
