package com.maddox.il2.game.campaign;

public class AwardsRUfighter extends Awards
{
  public int count(int paramInt)
  {
    if (paramInt < 1000)
      return 0;
    if (paramInt < 3000)
      return 1;
    if (paramInt < 5000)
      return 2;
    if (paramInt < 10000)
      return 4;
    if (paramInt < 12500)
      return 5;
    if (paramInt < 15000)
      return 6;
    if (paramInt < 20000) {
      return 7;
    }
    return 8;
  }

  public int[] index(int paramInt) {
    if (paramInt < 1000)
      return null;
    if (paramInt < 3000)
      return new int[] { 0 };
    if (paramInt < 5000)
      return new int[] { 0, 1 };
    if (paramInt < 10000)
      return new int[] { 0, 1, 2, 3 };
    if (paramInt < 12500)
      return new int[] { 0, 1, 2, 3, 2 };
    if (paramInt < 15000)
      return new int[] { 0, 1, 2, 3, 2, 4 };
    if (paramInt < 20000) {
      return new int[] { 0, 1, 2, 3, 2, 4, 2 };
    }
    return new int[] { 0, 1, 2, 3, 2, 4, 2, 6 };
  }
}