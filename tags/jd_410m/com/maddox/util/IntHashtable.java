package com.maddox.util;

import java.io.PrintStream;

public final class IntHashtable
{
  public static final int EMPTY = -2147483648;
  public static final int DELETED = -2147483647;
  public static final int MAX_UNUSED = -2147483647;
  private int defaultValue = -1;
  private int primeIndex;
  private static final float highWaterFactor = 0.4F;
  private int highWaterMark;
  private static final float lowWaterFactor = 0.0F;
  private int lowWaterMark;
  private static final float delWaterFactor = 0.4F;
  private int delWaterMark;
  private int count;
  private int countDeletings;
  private int[] values;
  private int[] keyList;
  private static final int[] PRIMES = { 11, 37, 71, 127, 179, 257, 359, 491, 661, 887, 1181, 1553, 2053, 2683, 3517, 4591, 6007, 7817, 10193, 13291, 17291, 22481, 29251, 38053, 49499, 64373, 83701, 108863, 141511, 184003, 239231, 310997, 404321, 525649, 683377, 888397, 1154947, 1501447, 1951949, 2537501, 3298807, 4288439, 5575001, 7247533, 9421793, 12248389, 15922903, 20699753, 26909713, 34982639, 45477503, 59120749, 76856959, 99914123, 129888349, 168854831, 219511301, 285364721, 370974151, 482266423, 626946367, 815030309, 1059539417, 1377401287, 1790621681, 2147483647 };

  public IntHashtable()
  {
    initialize(3);
  }

  public IntHashtable(int paramInt) {
    initialize(leastGreaterPrimeIndex((int)(paramInt / 0.4F)));
  }

  public IntHashtable(int paramInt1, int paramInt2) {
    this.defaultValue = paramInt2;
    initialize(leastGreaterPrimeIndex((int)(paramInt1 / 0.4F)));
  }

  public int size() {
    return this.count;
  }

  public boolean isEmpty() {
    return this.count == 0;
  }

  public void put(int paramInt1, int paramInt2) {
    if (this.count > this.highWaterMark) {
      rehash();
    }
    if (this.countDeletings > this.delWaterMark) {
      rehashSameSize();
    }
    int i = find(paramInt1);
    if (this.keyList[i] <= -2147483647) {
      this.keyList[i] = paramInt1;
      this.count += 1;
    }
    this.values[i] = paramInt2;
  }

  public int get(int paramInt) {
    return this.values[find(paramInt)];
  }

  public void remove(int paramInt) {
    int i = find(paramInt);
    if (this.keyList[i] > -2147483647) {
      this.keyList[i] = -2147483647;
      this.values[i] = this.defaultValue;
      this.count -= 1;
      this.countDeletings += 1;
      if (this.count < this.lowWaterMark) {
        rehash();
      }
      if (this.countDeletings > this.delWaterMark)
        rehashSameSize();
    }
  }

  public void clear()
  {
    if (this.count != 0)
      clearAll();
  }

  public int getDefaultValue() {
    return this.defaultValue;
  }

  public void setDefaultValue(int paramInt) {
    this.defaultValue = paramInt;
    rehash();
  }

  public boolean equals(Object paramObject) {
    if (paramObject.getClass() != getClass()) return false;

    IntHashtable localIntHashtable = (IntHashtable)paramObject;
    if ((localIntHashtable.size() != this.count) || (localIntHashtable.defaultValue != this.defaultValue)) {
      return false;
    }
    for (int i = 0; i < this.keyList.length; i++) {
      int j = this.keyList[i];
      if ((j > -2147483647) && (localIntHashtable.get(j) != this.values[i]))
        return false;
    }
    return true;
  }

  public static boolean isValidKey(int paramInt)
  {
    return paramInt > -2147483647;
  }
  public int[] values() {
    return this.values;
  }
  public int[] keyList() { return this.keyList; } 
  public int getByIndex(int paramInt) {
    if (this.keyList[paramInt] > -2147483647) {
      return this.values[paramInt];
    }
    return this.defaultValue;
  }
  public void setByIndex(int paramInt1, int paramInt2) {
    if (this.keyList[paramInt1] > -2147483647)
      this.values[paramInt1] = paramInt2;
  }

  public void removeByIndex(int paramInt)
  {
    if (this.keyList[paramInt] > -2147483647) {
      this.keyList[paramInt] = -2147483647;
      this.values[paramInt] = this.defaultValue;
      this.count -= 1;
      this.countDeletings += 1;
    }
  }

  public int getIndex(int paramInt) {
    int i = find(paramInt);
    if (this.keyList[i] > -2147483647)
      return i;
    return -1;
  }
  public void validate() {
    if (this.countDeletings > this.delWaterMark)
      rehashSameSize();
  }

  private void initialize(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    } else if (paramInt >= PRIMES.length) {
      System.out.println("IntHashtable TOO BIG");
      paramInt = PRIMES.length - 1;
    }

    this.primeIndex = paramInt;
    int i = PRIMES[paramInt];
    this.values = new int[i];
    this.keyList = new int[i];
    clearAll();
    this.lowWaterMark = (int)(i * 0.0F);
    this.highWaterMark = (int)(i * 0.4F);
    this.delWaterMark = (int)(i * 0.4F);
  }

  private void clearAll() {
    for (int i = 0; i < this.keyList.length; i++) {
      this.keyList[i] = -2147483648;
      this.values[i] = this.defaultValue;
    }
    this.count = 0;
    this.countDeletings = 0;
  }

  private void rehash() {
    int[] arrayOfInt1 = this.values;
    int[] arrayOfInt2 = this.keyList;
    int i = this.primeIndex;
    if (this.count > this.highWaterMark)
      i++;
    else if (this.count < this.lowWaterMark) {
      i -= 2;
    }
    initialize(i);
    for (int j = arrayOfInt1.length - 1; j >= 0; j--) {
      int k = arrayOfInt2[j];
      if (k > -2147483647)
        putInternal(k, arrayOfInt1[j]);
    }
  }

  private void rehashSameSize()
  {
    int[] arrayOfInt1 = this.values;
    int[] arrayOfInt2 = this.keyList;
    initialize(this.primeIndex);
    for (int i = arrayOfInt1.length - 1; i >= 0; i--) {
      int j = arrayOfInt2[i];
      if (j > -2147483647)
        putInternal(j, arrayOfInt1[i]);
    }
  }

  private void putInternal(int paramInt1, int paramInt2)
  {
    int i = find(paramInt1);
    if (this.keyList[i] <= -2147483647) {
      this.keyList[i] = paramInt1;
      this.count += 1;
    }
    this.values[i] = paramInt2;
  }

  private int find(int paramInt) {
    if (paramInt <= -2147483647)
      throw new IllegalArgumentException("key can't be less than 0x80000001");
    int i = -1;
    int j = (paramInt ^ 0x4000000) % this.keyList.length;
    if (j < 0) j = -j;
    int k = 0;
    while (true) {
      int m = this.keyList[j];
      if (m == paramInt)
        return j;
      if (m <= -2147483647)
      {
        if (m == -2147483648) {
          if (i >= 0) {
            j = i;
          }
          return j;
        }if (i < 0) {
          i = j;
        }
      }
      if (k == 0) {
        k = paramInt % (this.keyList.length - 1);
        if (k < 0) k = -k;
        k++;
      }

      j = (j + k) % this.keyList.length;
    }
  }

  private static int leastGreaterPrimeIndex(int paramInt)
  {
    for (int i = 0; i < PRIMES.length; i++) {
      if (paramInt < PRIMES[i]) {
        return i;
      }
    }
    return i - 1;
  }
}