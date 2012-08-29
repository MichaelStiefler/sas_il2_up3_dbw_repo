// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   ZutiAircraft.java

package com.maddox.il2.game;

import com.maddox.il2.fm.FlightModel;
import com.maddox.il2.fm.Gear;
import com.maddox.il2.net.BornPlace;
import com.maddox.il2.net.NetServerParams;
import com.maddox.il2.objects.air.Aircraft;
import com.maddox.rts.Property;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

// Referenced classes of package com.maddox.il2.game:
//            I18N, Main

public class ZutiAircraft
{

    public ZutiAircraft()
    {
        selectedWeaponNames = null;
        weaponNames = null;
        aircraftName = null;
        maxAllowed = -1;
        inUse = 0;
        modifiedRuntime = false;
        acNameFromClass = null;
    }

    public void setAcName(java.lang.String s)
    {
        aircraftName = s;
        fillWeapons();
    }

    public java.lang.String getAcName()
    {
        return aircraftName;
    }

    public void setMaxAllowed(int i)
    {
        maxAllowed = i;
    }

    public int getMaxAllowed()
    {
        return maxAllowed;
    }

    private void fillWeapons()
    {
        weaponNames = new ArrayList();
        java.lang.Class class1 = (java.lang.Class)com.maddox.rts.Property.value(aircraftName, "airClass", null);
        java.lang.String as[] = com.maddox.il2.objects.air.Aircraft.getWeaponsRegistered(class1);
        if(as != null && as.length > 0)
        {
            for(int i = 0; i < as.length; i++)
            {
                java.lang.String s = as[i];
                weaponNames.add(s);
            }

            if(weaponNames.size() == 0)
                weaponNames.add(as[0]);
        }
    }

    public java.lang.String getWeaponI18NName(java.lang.String s)
    {
        return com.maddox.il2.game.I18N.weapons(aircraftName, s);
    }

    public java.lang.String getLoadoutById(int i)
    {
        if(selectedWeaponNames == null || selectedWeaponNames.size() < 1)
            if(i < weaponNames.size())
                return (java.lang.String)weaponNames.get(i);
            else
                return null;
        if(i > selectedWeaponNames.size() - 1)
            return null;
        else
            return (java.lang.String)selectedWeaponNames.get(i);
    }

    public java.util.ArrayList getSelectedWeaponI18NNames()
    {
        java.util.ArrayList arraylist = new ArrayList();
        if(selectedWeaponNames == null)
            return arraylist;
        for(int i = 0; i < selectedWeaponNames.size(); i++)
            arraylist.add(getWeaponI18NName((java.lang.String)selectedWeaponNames.get(i)));

        return arraylist;
    }

    public java.util.ArrayList getWeaponI18NNames()
    {
        java.util.ArrayList arraylist = new ArrayList();
        if(weaponNames == null)
            return arraylist;
        for(int i = 0; i < weaponNames.size(); i++)
            arraylist.add(getWeaponI18NName((java.lang.String)weaponNames.get(i)));

        return arraylist;
    }

    public void listWeapons()
    {
        for(int i = 0; i < weaponNames.size(); i++);
    }

    private java.lang.String getSelectedWeaponsIds()
    {
        if(selectedWeaponNames == null || selectedWeaponNames.size() < 0)
            return "";
        java.lang.String s = "";
        java.util.ArrayList arraylist = getWeaponI18NNames();
        if(selectedWeaponNames.size() == arraylist.size())
        {
            for(int i = 0; i < arraylist.size(); i++)
                s = s + " " + i;

        } else
        {
            java.util.ArrayList arraylist1 = new ArrayList();
label0:
            for(int j = 0; j < selectedWeaponNames.size(); j++)
            {
                int l = 0;
                do
                {
                    if(l >= arraylist.size())
                        continue label0;
                    if(selectedWeaponNames.get(j).equals(arraylist.get(l)))
                    {
                        arraylist1.add(new Integer(l));
                        continue label0;
                    }
                    l++;
                } while(true);
            }

            java.util.Collections.sort(arraylist1);
            for(int k = 0; k < arraylist1.size(); k++)
                s = s + " " + ((java.lang.Integer)arraylist1.get(k)).intValue();

        }
        return s.trim();
    }

    public void setSelectedWeapons(java.util.ArrayList arraylist)
    {
        if(selectedWeaponNames == null)
            selectedWeaponNames = new ArrayList();
        selectedWeaponNames.clear();
        for(int i = 0; i < arraylist.size(); i++)
            selectedWeaponNames.add(arraylist.get(i));

    }

    public void setLoadedWeapons(java.lang.String s, boolean flag)
    {
        if(selectedWeaponNames == null)
            selectedWeaponNames = new ArrayList();
        selectedWeaponNames.clear();
        if(s == null)
            return;
        java.util.ArrayList arraylist = new ArrayList();
        for(java.util.StringTokenizer stringtokenizer = new StringTokenizer(s); stringtokenizer.hasMoreTokens();)
            try
            {
                arraylist.add(java.lang.Integer.valueOf(stringtokenizer.nextToken()));
            }
            catch(java.lang.Exception exception) { }

        java.util.Collections.sort(arraylist);
        for(int i = 0; i < arraylist.size(); i++)
            try
            {
                java.lang.Integer integer = (java.lang.Integer)arraylist.get(i);
                if(flag)
                    selectedWeaponNames.add(getWeaponI18NName((java.lang.String)weaponNames.get(integer.intValue())));
                else
                    selectedWeaponNames.add((java.lang.String)weaponNames.get(integer.intValue()));
            }
            catch(java.lang.Exception exception1) { }

    }

    public java.lang.String getMissionLine(boolean flag)
    {
        return aircraftName + " " + maxAllowed + " " + getSelectedWeaponsIds();
    }

    public java.lang.String getMissionLineNoLoadouts()
    {
        return aircraftName + " " + maxAllowed;
    }

    public void setModifiedRuntime(boolean flag)
    {
        modifiedRuntime = flag;
    }

    public boolean getModifiedRuntime()
    {
        return modifiedRuntime;
    }

    public boolean wasModified()
    {
        return selectedWeaponNames != null && selectedWeaponNames.size() > 0 || maxAllowed >= 0;
    }

    public void decreaseInUse()
    {
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster())
            inUse--;
    }

    public void increaseInUse()
    {
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster())
            inUse++;
    }

    public void decreasePlaneCount()
    {
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            if(maxAllowed <= 0)
                return;
            maxAllowed--;
        }
    }

    public void increasePlaneCount()
    {
        if(com.maddox.il2.game.Main.cur().netServerParams != null && com.maddox.il2.game.Main.cur().netServerParams.isMaster())
        {
            if(maxAllowed == -1)
                return;
            maxAllowed++;
        }
    }

    public boolean isAvailable(boolean flag)
    {
        if(maxAllowed == -1 || !flag)
            return true;
        if(flag)
        {
            if(maxAllowed > 0 && inUse < maxAllowed)
                return true;
        } else
        if(inUse < maxAllowed)
            return true;
        return false;
    }

    public java.lang.String getClassShortAcName()
    {
        if(acNameFromClass == null)
            acNameFromClass = com.maddox.il2.game.ZutiAircraft.getShortAcNameFromClass(((java.lang.Class)com.maddox.rts.Property.value(aircraftName, "airClass", null)).toString());
        return acNameFromClass;
    }

    public static java.lang.String getStaticAcNameFromActor(java.lang.String s)
    {
        return s.substring(s.indexOf("$") + 1, s.indexOf("@"));
        java.lang.Exception exception;
        exception;
        return "";
    }

    public static java.lang.String getShortAcNameFromClass(java.lang.String s)
    {
        return s.substring(s.lastIndexOf(".") + 1, s.length());
        java.lang.Exception exception;
        exception;
        return "";
    }

    public static boolean isPlaneLandedAndDamaged(com.maddox.il2.fm.FlightModel flightmodel)
    {
        return com.maddox.il2.net.BornPlace.isLandedOnHomeBase(flightmodel, null) && (flightmodel.Gears.isAnyDamaged() || !flightmodel.isCapableOfBMP() || flightmodel.isSentControlsOutNote());
    }

    public static boolean isPlaneUsable(com.maddox.il2.fm.FlightModel flightmodel)
    {
        return !flightmodel.Gears.isAnyDamaged() && flightmodel.isCapableOfBMP();
    }

    private java.util.ArrayList selectedWeaponNames;
    private java.util.ArrayList weaponNames;
    private java.lang.String aircraftName;
    private int maxAllowed;
    private int inUse;
    private boolean modifiedRuntime;
    private java.lang.String acNameFromClass;
}
