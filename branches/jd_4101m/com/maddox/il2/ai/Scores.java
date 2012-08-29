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
    ScoreItem localScoreItem;
    for (int i = 0; i < localArrayList1.size(); i++) {
      localScoreItem = (ScoreItem)localArrayList1.get(i);
      if (localScoreItem.type == 0) {
        enemyAirKill += 1;
      } else {
        enemyGroundKill += 1;
        localArrayList2.add(localScoreItem);
      }
      scoreUp += localScoreItem.score;
    }
    if (enemyGroundKill > 0) {
      arrayEnemyGroundKill = new int[enemyGroundKill];
      for (i = 0; i < enemyGroundKill; i++)
        arrayEnemyGroundKill[i] = ((ScoreItem)localArrayList2.get(i)).type;
    } else {
      arrayEnemyGroundKill = null;
    }

    friendlyKill = 0;
    scoreDown = 0.0D;
    localArrayList1 = localScoreCounter.friendItems;
    for (i = 0; i < localArrayList1.size(); i++) {
      localScoreItem = (ScoreItem)localArrayList1.get(i);
      friendlyKill += 1;
      scoreDown += localScoreItem.score;
    }

    localArrayList1 = localScoreCounter.targetOnItems;
    for (i = 0; i < localArrayList1.size(); i++) {
      localScoreItem = (ScoreItem)localArrayList1.get(i);
      scoreUp += localScoreItem.score;
    }

    localArrayList1 = localScoreCounter.targetOffItems;
    for (i = 0; i < localArrayList1.size(); i++) {
      localScoreItem = (ScoreItem)localArrayList1.get(i);
      scoreDown += localScoreItem.score;
    }

    if (localScoreCounter.bPlayerDroppedExternalStores) {
      scoreDown += localScoreCounter.externalStoresValue;
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