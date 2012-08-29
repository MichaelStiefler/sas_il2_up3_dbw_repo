package com.maddox.il2.ai;

import java.util.ArrayList;

public class Scores
{
  public static int enemyAirKill;
  public static int enemyGroundKill;
  public static int[] arrayEnemyGroundKill;
  public static int friendlyKill;
  public static double scoreUp;
  public static double scoreDown;
  public static int score;

  public static void compute()
  {
    ScoreCounter localScoreCounter = World.cur().scoreCounter;
    ArrayList localArrayList1 = localScoreCounter.enemyItems;
    enemyAirKill = 0;
    enemyGroundKill = 0;
    scoreUp = 0.0D;
    ArrayList localArrayList2 = new ArrayList();
    for (int i = 0; i < localArrayList1.size(); i++) {
      ScoreItem localScoreItem1 = (ScoreItem)localArrayList1.get(i);
      if (localScoreItem1.type == 0) {
        enemyAirKill += 1;
      } else {
        enemyGroundKill += 1;
        localArrayList2.add(localScoreItem1);
      }
      scoreUp += localScoreItem1.score;
    }
    if (enemyGroundKill > 0) {
      arrayEnemyGroundKill = new int[enemyGroundKill];
      for (j = 0; j < enemyGroundKill; j++)
        arrayEnemyGroundKill[j] = ((ScoreItem)localArrayList2.get(j)).type;
    } else {
      arrayEnemyGroundKill = null;
    }

    friendlyKill = 0;
    scoreDown = 0.0D;
    localArrayList1 = localScoreCounter.friendItems;
    for (int j = 0; j < localArrayList1.size(); j++) {
      ScoreItem localScoreItem2 = (ScoreItem)localArrayList1.get(j);
      friendlyKill += 1;
      scoreDown += localScoreItem2.score;
    }

    localArrayList1 = localScoreCounter.targetOnItems;
    for (int k = 0; k < localArrayList1.size(); k++) {
      ScoreItem localScoreItem3 = (ScoreItem)localArrayList1.get(k);
      scoreUp += localScoreItem3.score;
    }

    localArrayList1 = localScoreCounter.targetOffItems;
    for (int m = 0; m < localArrayList1.size(); m++) {
      ScoreItem localScoreItem4 = (ScoreItem)localArrayList1.get(m);
      scoreDown += localScoreItem4.score;
    }

    if (localScoreCounter.bPlayerDead)
      scoreUp /= 10.0D;
    else if (localScoreCounter.bPlayerCaptured)
      scoreUp = scoreUp * 2.0D / 10.0D;
    else if (localScoreCounter.bLandedFarAirdrome)
      scoreUp = scoreUp * 7.0D / 10.0D;
    else if (localScoreCounter.bPlayerParatrooper) {
      scoreUp /= 2.0D;
    }

    score = (int)Math.round(scoreUp - scoreDown);
  }
}