// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Regiment.java

package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;
import com.maddox.il2.game.I18N;
import com.maddox.rts.Message;
import com.maddox.rts.SFSInputStream;
import com.maddox.rts.SectFile;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PropertyResourceBundle;

// Referenced classes of package com.maddox.il2.ai:
//            Army

public class Regiment extends com.maddox.il2.engine.Actor
{

    public java.lang.String country()
    {
        return country;
    }

    public java.lang.String branch()
    {
        return branch;
    }

    public java.lang.String fileName()
    {
        return "PaintSchemes/" + shortFileName;
    }

    public java.lang.String fileNameTga()
    {
        return "../" + shortFileName + ".tga";
    }

    public java.lang.String id()
    {
        return sid;
    }

    public char[] aid()
    {
        return id;
    }

    public int gruppeNumber()
    {
        return gruppeNumber;
    }

    public java.lang.String shortInfo()
    {
        return shortInfo;
    }

    public java.lang.String info()
    {
        return info != null ? info : shortInfo;
    }

    public java.lang.String speech()
    {
        return speech;
    }

    public static java.lang.String getCountryFromBranch(java.lang.String s)
    {
        if(branchMap.containsKey(s))
            return (java.lang.String)branchMap.get(s);
        else
            return s;
    }

    public boolean isUserDefined()
    {
        return false;
    }

    public static void resetGame()
    {
        int i = all.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.il2.ai.Regiment regiment = (com.maddox.il2.ai.Regiment)all.get(j);
            regiment.diedBombers = 0;
            regiment.diedAircrafts = 0;
        }

    }

    public java.lang.Object getSwitchListener(com.maddox.rts.Message message)
    {
        return this;
    }

    public Regiment(java.lang.String s, java.lang.String s1, int i)
    {
        diedBombers = 0;
        diedAircrafts = 0;
        id = new char[2];
        gruppeNumber = 1;
        flags |= 0x4000;
        setArmy(i);
        setName(s);
        shortFileName = s1;
        try
        {
            java.util.PropertyResourceBundle propertyresourcebundle = new PropertyResourceBundle(new SFSInputStream(fileName()));
            country = propertyresourcebundle.getString("country");
            country = country.toLowerCase().intern();
            branch = country;
            if(branchMap.containsKey(country))
                country = (java.lang.String)branchMap.get(branch);
            java.lang.String s2 = propertyresourcebundle.getString("id");
            id[0] = s2.charAt(0);
            id[1] = s2.charAt(1);
            if((id[0] < '0' || id[0] > '9') && (id[0] < 'A' || id[0] > 'Z') && id[0] != '_')
                throw new RuntimeException("Bad regiment id[0]");
            if((id[1] < '0' || id[1] > '9') && (id[1] < 'A' || id[1] > 'Z') && id[1] != '_')
                throw new RuntimeException("Bad regiment id[1]");
            sid = new String(id);
            speech = country;
            try
            {
                speech = propertyresourcebundle.getString("speech");
                speech = speech.toLowerCase().intern();
            }
            catch(java.lang.Exception exception1) { }
            shortInfo = com.maddox.il2.game.I18N.regimentShort(s);
            info = com.maddox.il2.game.I18N.regimentInfo(s);
            try
            {
                java.lang.String s3 = propertyresourcebundle.getString("gruppeNumber");
                if(s3 != null)
                {
                    try
                    {
                        gruppeNumber = java.lang.Integer.parseInt(s3);
                    }
                    catch(java.lang.Exception exception3) { }
                    if(gruppeNumber < 1)
                        gruppeNumber = 1;
                    if(gruppeNumber > 5)
                        gruppeNumber = 5;
                }
            }
            catch(java.lang.Exception exception2) { }
        }
        catch(java.lang.Exception exception)
        {
            java.lang.System.out.println("Regiment load failed: " + exception.getMessage());
            exception.printStackTrace();
            destroy();
            return;
        }
        all.add(this);
    }

    public Regiment(java.lang.String s, int i)
    {
        this(s, com.maddox.il2.ai.Regiment.makeShortFileName(s, i), i);
    }

    private static java.lang.String makeShortFileName(java.lang.String s, int i)
    {
        if(i < com.maddox.il2.ai.Army.amountSingle())
            return com.maddox.il2.ai.Army.name(i) + "/" + s;
        else
            return s;
    }

    protected Regiment()
    {
        diedBombers = 0;
        diedAircrafts = 0;
        id = new char[2];
        gruppeNumber = 1;
    }

    protected void createActorHashCode()
    {
        makeActorRealHashCode();
    }

    public static java.util.List getAll()
    {
        return all;
    }

    public static com.maddox.il2.ai.Regiment findFirst(java.lang.String s, int i)
    {
        java.lang.String s1 = null;
        com.maddox.il2.ai.Regiment regiment = null;
        if(s != null)
        {
            s1 = s + "_" + i;
            regiment = (com.maddox.il2.ai.Regiment)firstMap.get(s1);
            if(regiment != null)
                return regiment;
            for(int j = 0; j < all.size(); j++)
            {
                com.maddox.il2.ai.Regiment regiment1 = (com.maddox.il2.ai.Regiment)all.get(j);
                if(!s.equals(regiment1.country()) || i != regiment1.getArmy())
                    continue;
                regiment = regiment1;
                break;
            }

        }
        if(regiment == null)
        {
            java.lang.String s2 = "NoNe";
            switch(i)
            {
            case 1: // '\001'
                s2 = "r01";
                break;

            case 2: // '\002'
                s2 = "g01";
                break;
            }
            regiment = (com.maddox.il2.ai.Regiment)com.maddox.il2.engine.Actor.getByName(s2);
        }
        if(regiment != null && s != null)
            firstMap.put(s1, regiment);
        return regiment;
    }

    public static void loadAll()
    {
        com.maddox.rts.SectFile sectfile = new SectFile("PaintSchemes/regiments.ini", 0);
        int i = sectfile.sectionIndex("branch");
        if(i >= 0)
        {
            int j = sectfile.vars(i);
            for(int l = 0; l < j; l++)
            {
                java.lang.String s = sectfile.var(i, l);
                java.lang.String s1 = sectfile.value(i, l);
                if(s != null && s1 != null)
                    branchMap.put(s.toLowerCase().intern(), s1.toLowerCase().intern());
            }

        }
        for(int k = 1; k < com.maddox.il2.ai.Army.amountSingle(); k++)
            com.maddox.il2.ai.Regiment.loadSection(sectfile, com.maddox.il2.ai.Army.name(k), k);

        com.maddox.il2.ai.Regiment.loadSection(sectfile, "NoNe", 0);
    }

    private static void loadSection(com.maddox.rts.SectFile sectfile, java.lang.String s, int i)
    {
        int j = sectfile.sectionIndex(s);
        if(j < 0)
            return;
        int k = sectfile.vars(j);
        for(int l = 0; l < k; l++)
            new Regiment(sectfile.var(j, l), i);

    }

    public static final java.lang.String prefixPath = "PaintSchemes/";
    public int diedBombers;
    public int diedAircrafts;
    protected java.lang.String country;
    protected java.lang.String branch;
    protected java.lang.String shortFileName;
    protected char id[];
    protected java.lang.String sid;
    protected java.lang.String shortInfo;
    protected java.lang.String info;
    protected int gruppeNumber;
    protected java.lang.String speech;
    private static java.util.ArrayList all = new ArrayList();
    protected static com.maddox.util.HashMapExt branchMap = new HashMapExt();
    private static java.util.HashMap firstMap = new HashMap();

}
