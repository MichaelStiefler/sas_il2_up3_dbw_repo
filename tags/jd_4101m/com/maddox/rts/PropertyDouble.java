package com.maddox.rts;

class PropertyDouble extends Property
{
  private double value = 0.0D;

  public PropertyDouble(Object paramObject, String paramString) { super(paramObject, paramString);
    invokeObserver(lastMapInt, lastAction); }

  public PropertyDouble(Object paramObject, String paramString, double paramDouble) {
    super(paramObject, paramString);
    this.value = paramDouble;
    invokeObserver(lastMapInt, lastAction);
  }
  public Class classValue() { return Double.class; } 
  public int intValue() { return (int)this.value; } 
  public float floatValue() { return (float)this.value; } 
  public long longValue() { return ()this.value; } 
  public double doubleValue() { return this.value; } 
  public Object value() { return new Double(this.value); } 
  public String stringValue() { return Double.toString(this.value); } 
  public long fingerValue(long paramLong) { return Finger.incLong(paramLong, this.value); } 
  public void set(int paramInt) { this.value = paramInt; } 
  public void set(float paramFloat) { this.value = paramFloat; } 
  public void set(long paramLong) { this.value = paramLong; } 
  public void set(double paramDouble) { this.value = paramDouble; } 
  public void set(Object paramObject) { if ((paramObject instanceof Number)) this.value = ((Number)paramObject).doubleValue(); else super.set(paramObject);  } 
  public void set(String paramString) {
    try { this.value = Double.parseDouble(paramString); } catch (Exception localException) { super.set(paramString);
    }
  }
}