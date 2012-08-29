// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AirGroupList.java

package com.maddox.il2.ai.air;


// Referenced classes of package com.maddox.il2.ai.air:
//            AirGroup

public class AirGroupList
{

    public AirGroupList()
    {
        G = null;
        next = null;
    }

    public AirGroupList(com.maddox.il2.ai.air.AirGroup airgroup)
    {
        G = airgroup;
        next = null;
    }

    public void release()
    {
        for(com.maddox.il2.ai.air.AirGroupList airgrouplist = this; airgrouplist != null;)
        {
            airgrouplist.G = null;
            if(airgrouplist.next != null)
                airgrouplist = airgrouplist.next;
            else
                airgrouplist = null;
        }

    }

    public static void addAirGroup(com.maddox.il2.ai.air.AirGroupList aairgrouplist[], int i, com.maddox.il2.ai.air.AirGroup airgroup)
    {
        if(aairgrouplist[i] == null)
        {
            aairgrouplist[i] = new AirGroupList(airgroup);
            return;
        }
        com.maddox.il2.ai.air.AirGroupList airgrouplist;
        for(airgrouplist = aairgrouplist[i]; airgrouplist.next != null; airgrouplist = airgrouplist.next);
        airgrouplist.next = new AirGroupList(airgroup);
    }

    public static void delAirGroup(com.maddox.il2.ai.air.AirGroupList aairgrouplist[], int i, com.maddox.il2.ai.air.AirGroup airgroup)
    {
        com.maddox.il2.ai.air.AirGroupList airgrouplist = aairgrouplist[i];
        com.maddox.il2.ai.air.AirGroupList airgrouplist1 = null;
        for(; airgrouplist != null; airgrouplist = airgrouplist.next)
        {
            if(airgrouplist.G == airgroup)
            {
                airgrouplist.G = null;
                if(airgrouplist1 == null)
                    aairgrouplist[i] = airgrouplist.next;
                else
                    airgrouplist1.next = airgrouplist.next;
                airgrouplist.next = null;
                return;
            }
            airgrouplist1 = airgrouplist;
        }

    }

    public static int length(com.maddox.il2.ai.air.AirGroupList airgrouplist)
    {
        if(airgrouplist == null)
            return 0;
        com.maddox.il2.ai.air.AirGroupList airgrouplist1 = airgrouplist;
        int i;
        for(i = 0; airgrouplist1 != null; i++)
            airgrouplist1 = airgrouplist1.next;

        return i;
    }

    public static com.maddox.il2.ai.air.AirGroup getGroup(com.maddox.il2.ai.air.AirGroupList airgrouplist, int i)
    {
        if(airgrouplist == null)
            return null;
        com.maddox.il2.ai.air.AirGroupList airgrouplist1 = airgrouplist;
        for(int j = 0; airgrouplist1 != null; j++)
        {
            if(j == i)
                return airgrouplist1.G;
            airgrouplist1 = airgrouplist1.next;
        }

        return null;
    }

    public static boolean groupInList(com.maddox.il2.ai.air.AirGroupList airgrouplist, com.maddox.il2.ai.air.AirGroup airgroup)
    {
        if(airgrouplist == null)
            return false;
        for(com.maddox.il2.ai.air.AirGroupList airgrouplist1 = airgrouplist; airgrouplist1 != null; airgrouplist1 = airgrouplist1.next)
            if(airgrouplist1.G == airgroup)
                return true;

        return false;
    }

    public static void update(com.maddox.il2.ai.air.AirGroupList airgrouplist)
    {
        for(com.maddox.il2.ai.air.AirGroupList airgrouplist1 = airgrouplist; airgrouplist1 != null; airgrouplist1 = airgrouplist1.next)
            airgrouplist1.G.update();

    }

    public com.maddox.il2.ai.air.AirGroup G;
    com.maddox.il2.ai.air.AirGroupList next;
}
