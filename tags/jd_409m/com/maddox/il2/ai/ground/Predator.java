package com.maddox.il2.ai.ground;

public abstract interface Predator extends Prey
{
  public abstract int WeaponsMask();

  public abstract float AttackMaxDistance();
}