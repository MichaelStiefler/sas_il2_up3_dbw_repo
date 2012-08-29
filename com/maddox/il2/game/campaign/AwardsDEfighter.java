// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AwardsDEfighter.java

package com.maddox.il2.game.campaign;


// Referenced classes of package com.maddox.il2.game.campaign:
//            Awards

public class AwardsDEfighter extends com.maddox.il2.game.campaign.Awards
{

    public AwardsDEfighter()
    {
    }

    public int count(int i)
    {
        if(i < 200)
            return 0;
        if(i < 800)
            return 1;
        if(i < 3200)
            return 2;
        if(i < 4500)
            return 3;
        if(i < 10000)
            return 4;
        if(i < 20000)
            return 5;
        if(i < 25000)
            return 6;
        return i >= 30000 ? 8 : 7;
    }

    public int[] index(int i)
    {
        if(i < 200)
            return null;
        if(i < 800)
            return (new int[] {
                0
            });
        if(i < 3200)
            return (new int[] {
                0, 1
            });
        if(i < 4500)
            return (new int[] {
                0, 1, 2
            });
        if(i < 10000)
            return (new int[] {
                0, 1, 2, 3
            });
        if(i < 20000)
            return (new int[] {
                0, 1, 2, 3, 4
            });
        if(i < 25000)
            return (new int[] {
                0, 1, 2, 3, 4, 5
            });
        if(i < 30000)
            return (new int[] {
                0, 1, 2, 3, 4, 5, 6
            });
        else
            return (new int[] {
                0, 1, 2, 3, 4, 5, 6, 7
            });
    }
}
