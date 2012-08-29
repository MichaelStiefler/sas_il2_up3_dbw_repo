package com.maddox.il2.engine;

class DreamEnvXY_IntArray
{
  private int size = 0;
  private int[] arr;

  public int[] array()
  {
    return this.arr; } 
  public int size() { return this.size; } 
  public void clear() { this.size = 0; } 
  public void add(int paramInt) {
    if (this.size + 1 > this.arr.length) {
      int[] arrayOfInt = new int[(this.size + 1) * 2];
      for (int i = 0; i < this.size; i++) arrayOfInt[i] = this.arr[i];
      this.arr = arrayOfInt;
    }
    this.arr[(this.size++)] = paramInt;
  }

  public DreamEnvXY_IntArray(int paramInt) {
    this.arr = new int[paramInt];
  }
}