// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AwardsRUfighter.java

package com.maddox.il2.game.campaign;


// Referenced classes of package com.maddox.il2.game.campaign:
//            Awards

public class AwardsRUfighter extends com.maddox.il2.game.campaign.Awards
{

    public AwardsRUfighter()
    {
    }

    public int count(int i)
    {
        if(i < 1000)
            return 0;
        if(i < 3000)
            return 1;
        if(i < 5000)
            return 2;
        if(i < 10000)
            return 4;
        if(i < 12500)
            return 5;
        if(i < 15000)
            return 6;
        return i >= 20000 ? 8 : 7;
    }

    public int[] index(int i)
    {
        if(i < 1000)
            return null;
        if(i < 3000)
            return (new int[] {
                0
            });
        if(i < 5000)
            return (new int[] {
                0, 1
            });
        if(i < 10000)
            return (new int[] {
                0, 1, 2, 3
            });
        if(i < 12500)
            return (new int[] {
                0, 1, 2, 3, 2
            });
        if(i < 15000)
            return (new int[] {
                0, 1, 2, 3, 2, 4
            });
        if(i < 20000)
            return (new int[] {
                0, 1, 2, 3, 2, 4, 2
            });
        else
            return (new int[] {
                0, 1, 2, 3, 2, 4, 2, 6
            });
    }
}
