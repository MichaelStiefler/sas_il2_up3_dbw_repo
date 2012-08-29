package com.maddox.il2.game.campaign;

public class AwardsDEfighter extends Awards
{
  public int count(int paramInt)
  {
    if (paramInt < 200)
      return 0;
    if (paramInt < 800)
      return 1;
    if (paramInt < 3200)
      return 2;
    if (paramInt < 4500)
      return 3;
    if (paramInt < 10000)
      return 4;
    if (paramInt < 20000)
      return 5;
    if (paramInt < 25000)
      return 6;
    if (paramInt < 30000) {
      return 7;
    }
    return 8;
  }

  public int[] index(int paramInt) {
    if (paramInt < 200)
      return null;
    if (paramInt < 800)
      return new int[] { 0 };
    if (paramInt < 3200)
      return new int[] { 0, 1 };
    if (paramInt < 4500)
      return new int[] { 0, 1, 2 };
    if (paramInt < 10000)
      return new int[] { 0, 1, 2, 3 };
    if (paramInt < 20000)
      return new int[] { 0, 1, 2, 3, 4 };
    if (paramInt < 25000)
      return new int[] { 0, 1, 2, 3, 4, 5 };
    if (paramInt < 30000) {
      return new int[] { 0, 1, 2, 3, 4, 5, 6 };
    }
    return new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
  }
}