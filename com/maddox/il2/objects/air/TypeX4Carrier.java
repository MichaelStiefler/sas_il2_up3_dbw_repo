package com.maddox.il2.objects.air;

public abstract interface TypeX4Carrier
{
  public abstract void typeX4CAdjSidePlus();

  public abstract void typeX4CAdjSideMinus();

  public abstract void typeX4CAdjAttitudePlus();

  public abstract void typeX4CAdjAttitudeMinus();

  public abstract void typeX4CResetControls();

  public abstract float typeX4CgetdeltaAzimuth();

  public abstract float typeX4CgetdeltaTangage();
}