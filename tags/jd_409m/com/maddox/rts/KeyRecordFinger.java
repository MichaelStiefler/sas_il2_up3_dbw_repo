package com.maddox.rts;

public abstract interface KeyRecordFinger
{
  public abstract int checkPeriod();

  public abstract int countSaveFingers();

  public abstract int[] calculateFingers();

  public abstract void checkFingers(int[] paramArrayOfInt);
}