package com.maddox.rts;

class PropertyObject extends Property
{
  private Object value = null;

  public PropertyObject(Object paramObject, String paramString) { super(paramObject, paramString);
    invokeObserver(Property.lastMapInt, Property.lastAction); }

  public PropertyObject(Object paramObject1, String paramString, Object paramObject2) {
    super(paramObject1, paramString);
    this.value = paramObject2;
    invokeObserver(Property.lastMapInt, Property.lastAction);
  }
  public Class classValue() { if (this.value == null) return Object.class; return this.value.getClass(); } 
  public int intValue() { if ((this.value instanceof Number)) return ((Number)this.value).intValue(); return super.intValue(); } 
  public float floatValue() { if ((this.value instanceof Number)) return ((Number)this.value).floatValue(); return super.floatValue(); } 
  public long longValue() { if ((this.value instanceof Number)) return ((Number)this.value).longValue(); return super.longValue(); } 
  public double doubleValue() { if ((this.value instanceof Number)) return ((Number)this.value).doubleValue(); return super.doubleValue(); } 
  public Object value() { return this.value; } 
  public String stringValue() { return this.value == null ? null : this.value.toString(); } 
  public void set(int paramInt) { this.value = new Integer(paramInt); } 
  public void set(float paramFloat) { this.value = new Float(paramFloat); } 
  public void set(long paramLong) { this.value = new Long(paramLong); } 
  public void set(double paramDouble) { this.value = new Double(paramDouble); } 
  public void set(Object paramObject) { this.value = paramObject;
  }
}