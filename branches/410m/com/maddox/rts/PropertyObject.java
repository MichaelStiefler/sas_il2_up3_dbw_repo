// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Property.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Property

class PropertyObject extends com.maddox.rts.Property
{

    public PropertyObject(java.lang.Object obj, java.lang.String s)
    {
        super(obj, s);
        value = null;
        invokeObserver(lastMapInt, lastAction);
    }

    public PropertyObject(java.lang.Object obj, java.lang.String s, java.lang.Object obj1)
    {
        super(obj, s);
        value = null;
        value = obj1;
        invokeObserver(lastMapInt, lastAction);
    }

    public java.lang.Class classValue()
    {
        if(value == null)
            return java.lang.Object.class;
        else
            return value.getClass();
    }

    public int intValue()
    {
        if(value instanceof java.lang.Number)
            return ((java.lang.Number)value).intValue();
        else
            return super.intValue();
    }

    public float floatValue()
    {
        if(value instanceof java.lang.Number)
            return ((java.lang.Number)value).floatValue();
        else
            return super.floatValue();
    }

    public long longValue()
    {
        if(value instanceof java.lang.Number)
            return ((java.lang.Number)value).longValue();
        else
            return super.longValue();
    }

    public double doubleValue()
    {
        if(value instanceof java.lang.Number)
            return ((java.lang.Number)value).doubleValue();
        else
            return super.doubleValue();
    }

    public java.lang.Object value()
    {
        return value;
    }

    public java.lang.String stringValue()
    {
        return value != null ? value.toString() : null;
    }

    public void set(int i)
    {
        value = new Integer(i);
    }

    public void set(float f)
    {
        value = new Float(f);
    }

    public void set(long l)
    {
        value = new Long(l);
    }

    public void set(double d)
    {
        value = new Double(d);
    }

    public void set(java.lang.Object obj)
    {
        value = obj;
    }

    private java.lang.Object value;
}
