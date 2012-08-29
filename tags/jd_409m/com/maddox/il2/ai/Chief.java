package com.maddox.il2.ai;

import com.maddox.il2.engine.Actor;

public class Chief extends Actor
{
  public static float new_DELAY_WAKEUP = 0.0F;
  public static int new_SKILL_IDX = 2;

  public static float new_SLOWFIRE_K = 1.0F;
  public int weaponsMask;
  public int hitbyMask;
  public float attackMaxDistance;
  protected Chief enemy;

  public void packUnits()
  {
  }

  public void unpackUnits()
  {
  }

  public void fightStatistically(Chief paramChief)
  {
  }
}