// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Property.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Property, Finger

class PropertyDouble extends com.maddox.rts.Property
{

    public PropertyDouble(java.lang.Object obj, java.lang.String s)
    {
        super(obj, s);
        value = 0.0D;
        invokeObserver(com.maddox.rts.Property.lastMapInt, com.maddox.rts.Property.lastAction);
    }

    public PropertyDouble(java.lang.Object obj, java.lang.String s, double d)
    {
        super(obj, s);
        value = 0.0D;
        value = d;
        invokeObserver(com.maddox.rts.Property.lastMapInt, com.maddox.rts.Property.lastAction);
    }

    public java.lang.Class classValue()
    {
        return java.lang.Double.class;
    }

    public int intValue()
    {
        return (int)value;
    }

    public float floatValue()
    {
        return (float)value;
    }

    public long longValue()
    {
        return (long)value;
    }

    public double doubleValue()
    {
        return value;
    }

    public java.lang.Object value()
    {
        return new Double(value);
    }

    public java.lang.String stringValue()
    {
        return java.lang.Double.toString(value);
    }

    public long fingerValue(long l)
    {
        return com.maddox.rts.Finger.incLong(l, value);
    }

    public void set(int i)
    {
        value = i;
    }

    public void set(float f)
    {
        value = f;
    }

    public void set(long l)
    {
        value = l;
    }

    public void set(double d)
    {
        value = d;
    }

    public void set(java.lang.Object obj)
    {
        if(obj instanceof java.lang.Number)
            value = ((java.lang.Number)obj).doubleValue();
        else
            super.set(obj);
    }

    public void set(java.lang.String s)
    {
        try
        {
            value = java.lang.Double.parseDouble(s);
        }
        catch(java.lang.Exception exception)
        {
            super.set(s);
        }
    }

    private double value;
}
