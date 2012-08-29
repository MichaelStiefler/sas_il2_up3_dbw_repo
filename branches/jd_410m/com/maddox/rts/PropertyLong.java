package com.maddox.rts;

class PropertyLong extends Property
{
  private long value = 0L;

  public PropertyLong(Object paramObject, String paramString) { super(paramObject, paramString);
    invokeObserver(lastMapInt, lastAction); }

  public PropertyLong(Object paramObject, String paramString, long paramLong) {
    super(paramObject, paramString);
    this.value = paramLong;
    invokeObserver(lastMapInt, lastAction);
  }
  public Class classValue() { return Long.class; } 
  public int intValue() { return (int)this.value; } 
  public float floatValue() { return (float)this.value; } 
  public long longValue() { return this.value; } 
  public double doubleValue() { return this.value; } 
  public Object value() { return new Long(this.value); } 
  public String stringValue() { return Long.toString(this.value); } 
  public long fingerValue(long paramLong) { return Finger.incLong(paramLong, this.value); } 
  public void set(int paramInt) { this.value = paramInt; } 
  public void set(float paramFloat) { this.value = ()paramFloat; } 
  public void set(long paramLong) { this.value = paramLong; } 
  public void set(double paramDouble) { this.value = ()paramDouble; } 
  public void set(Object paramObject) { if ((paramObject instanceof Number)) this.value = ((Number)paramObject).longValue(); else super.set(paramObject);  } 
  public void set(String paramString) {
    try { this.value = Long.parseLong(paramString); } catch (Exception localException) { super.set(paramString);
    }
  }
}