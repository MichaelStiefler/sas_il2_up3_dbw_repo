// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   CampaignDGen.java

package com.maddox.il2.game.campaign;

import java.io.PrintStream;

// Referenced classes of package com.maddox.il2.game.campaign:
//            Campaign

public class CampaignDGen extends com.maddox.il2.game.campaign.Campaign
{

    public java.lang.String dgenFileName()
    {
        return dgenFileName;
    }

    public java.lang.String prefix()
    {
        return prefix;
    }

    public void doExternalCampaignGenerator(java.lang.String s)
    {
        java.lang.String s1 = "DGen.exe";
        try
        {
            java.lang.String s2 = branch() + " " + " " + rank();
            java.lang.Runtime runtime = java.lang.Runtime.getRuntime();
            java.lang.Process process = runtime.exec(s1 + " " + s2);
            process.waitFor();
        }
        catch(java.lang.Throwable throwable)
        {
            java.lang.System.out.println(throwable.getMessage());
            throwable.printStackTrace();
        }
    }

    public CampaignDGen(java.lang.String s, java.lang.String s1, int i, int j, java.lang.String s2)
    {
        dgenFileName = s;
        _country = s1;
        _difficulty = i;
        _rank = j;
        prefix = s2;
    }

    private java.lang.String dgenFileName;
    private java.lang.String prefix;
}
