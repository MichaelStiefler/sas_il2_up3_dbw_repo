// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CampaignRed.java

package com.maddox.il2.game.campaign;

import com.maddox.rts.ObjIO;

// Referenced classes of package com.maddox.il2.game.campaign:
//            Campaign

public class CampaignRed extends com.maddox.il2.game.campaign.Campaign
{

    public CampaignRed()
    {
    }

    protected int rankStep()
    {
        return 2000;
    }

    public int army()
    {
        return 1;
    }

    static java.lang.Class _mthclass$(java.lang.String s)
    {
        return java.lang.Class.forName(s);
        java.lang.ClassNotFoundException classnotfoundexception;
        classnotfoundexception;
        throw new NoClassDefFoundError(classnotfoundexception.getMessage());
    }

    private static java.lang.Class _cls;

    static 
    {
        _cls = com.maddox.il2.game.campaign.CampaignRed.class;
        com.maddox.rts.ObjIO.fieldsAllSuperclasses(_cls);
    }
}
