package com.maddox.rts;

class PropertyString extends Property
{
  private String value = "";

  public PropertyString(Object paramObject, String paramString) { super(paramObject, paramString);
    invokeObserver(Property.lastMapInt, Property.lastAction); }

  public PropertyString(Object paramObject, String paramString1, String paramString2) {
    super(paramObject, paramString1);
    this.value = paramString2;
    invokeObserver(Property.lastMapInt, Property.lastAction);
  }
  public Class classValue() { return String.class; } 
  public int intValue() { try { return Integer.parseInt(this.value); } catch (Exception localException) {  }
    return super.intValue(); } 
  public float floatValue() { try { return Float.parseFloat(this.value); } catch (Exception localException) {  }
    return super.floatValue(); } 
  public long longValue() { try { return Long.parseLong(this.value); } catch (Exception localException) {  }
    return super.longValue(); } 
  public double doubleValue() { try { return Double.parseDouble(this.value); } catch (Exception localException) {  }
    return super.doubleValue(); } 
  public Object value() { return this.value; } 
  public String stringValue() { return this.value; } 
  public long fingerValue(long paramLong) { return Finger.incLong(paramLong, this.value); } 
  public void set(int paramInt) { this.value = Integer.toString(paramInt); } 
  public void set(float paramFloat) { this.value = Float.toString(paramFloat); } 
  public void set(long paramLong) { this.value = Long.toString(paramLong); } 
  public void set(double paramDouble) { this.value = Double.toString(paramDouble); } 
  public void set(String paramString) {
    this.value = paramString;
  }
}