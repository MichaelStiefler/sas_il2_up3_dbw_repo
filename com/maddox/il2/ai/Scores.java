// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Scores.java

package com.maddox.il2.ai;

import java.util.ArrayList;

// Referenced classes of package com.maddox.il2.ai:
//            ScoreItem, World, ScoreCounter

public class Scores
{

    public Scores()
    {
    }

    public static void compute()
    {
        com.maddox.il2.ai.ScoreCounter scorecounter = com.maddox.il2.ai.World.cur().scoreCounter;
        java.util.ArrayList arraylist = scorecounter.enemyItems;
        enemyAirKill = 0;
        enemyGroundKill = 0;
        scoreUp = 0.0D;
        java.util.ArrayList arraylist1 = new ArrayList();
        for(int i = 0; i < arraylist.size(); i++)
        {
            com.maddox.il2.ai.ScoreItem scoreitem = (com.maddox.il2.ai.ScoreItem)arraylist.get(i);
            if(scoreitem.type == 0)
            {
                enemyAirKill++;
            } else
            {
                enemyGroundKill++;
                arraylist1.add(scoreitem);
            }
            scoreUp += scoreitem.score;
        }

        if(enemyGroundKill > 0)
        {
            arrayEnemyGroundKill = new int[enemyGroundKill];
            for(int j = 0; j < enemyGroundKill; j++)
                arrayEnemyGroundKill[j] = ((com.maddox.il2.ai.ScoreItem)arraylist1.get(j)).type;

        } else
        {
            arrayEnemyGroundKill = null;
        }
        friendlyKill = 0;
        scoreDown = 0.0D;
        arraylist = scorecounter.friendItems;
        for(int k = 0; k < arraylist.size(); k++)
        {
            com.maddox.il2.ai.ScoreItem scoreitem1 = (com.maddox.il2.ai.ScoreItem)arraylist.get(k);
            friendlyKill++;
            scoreDown += scoreitem1.score;
        }

        arraylist = scorecounter.targetOnItems;
        for(int l = 0; l < arraylist.size(); l++)
        {
            com.maddox.il2.ai.ScoreItem scoreitem2 = (com.maddox.il2.ai.ScoreItem)arraylist.get(l);
            scoreUp += scoreitem2.score;
        }

        arraylist = scorecounter.targetOffItems;
        for(int i1 = 0; i1 < arraylist.size(); i1++)
        {
            com.maddox.il2.ai.ScoreItem scoreitem3 = (com.maddox.il2.ai.ScoreItem)arraylist.get(i1);
            scoreDown += scoreitem3.score;
        }

        if(scorecounter.bPlayerDead)
            scoreUp /= 10D;
        else
        if(scorecounter.bPlayerCaptured)
            scoreUp = (scoreUp * 2D) / 10D;
        else
        if(scorecounter.bLandedFarAirdrome)
            scoreUp = (scoreUp * 7D) / 10D;
        else
        if(scorecounter.bPlayerParatrooper)
            scoreUp /= 2D;
        score = (int)java.lang.Math.round(scoreUp - scoreDown);
    }

    public static int enemyAirKill;
    public static int enemyGroundKill;
    public static int arrayEnemyGroundKill[];
    public static int friendlyKill;
    public static double scoreUp;
    public static double scoreDown;
    public static int score;
}
