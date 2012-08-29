// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AwardsRUbomber.java

package com.maddox.il2.game.campaign;


// Referenced classes of package com.maddox.il2.game.campaign:
//            Awards

public class AwardsRUbomber extends com.maddox.il2.game.campaign.Awards
{

    public AwardsRUbomber()
    {
    }

    public int count(int i)
    {
        if(i < 1000)
            return 0;
        if(i < 5000)
            return 1;
        if(i < 10000)
            return 2;
        if(i < 12500)
            return 4;
        if(i < 15000)
            return 5;
        if(i < 17500)
            return 6;
        return i >= 20000 ? 8 : 7;
    }

    public int[] index(int i)
    {
        if(i < 1000)
            return null;
        if(i < 5000)
            return (new int[] {
                0
            });
        if(i < 10000)
            return (new int[] {
                0, 1
            });
        if(i < 12500)
            return (new int[] {
                0, 1, 2, 3
            });
        if(i < 15000)
            return (new int[] {
                0, 1, 2, 3, 4
            });
        if(i < 17500)
            return (new int[] {
                0, 1, 2, 3, 4, 5
            });
        if(i < 20000)
            return (new int[] {
                0, 1, 2, 3, 4, 5, 2
            });
        else
            return (new int[] {
                0, 1, 2, 3, 4, 5, 2, 6
            });
    }
}
