// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Property.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Property, Finger

class PropertyLong extends com.maddox.rts.Property
{

    public PropertyLong(java.lang.Object obj, java.lang.String s)
    {
        super(obj, s);
        value = 0L;
        invokeObserver(lastMapInt, lastAction);
    }

    public PropertyLong(java.lang.Object obj, java.lang.String s, long l)
    {
        super(obj, s);
        value = 0L;
        value = l;
        invokeObserver(lastMapInt, lastAction);
    }

    public java.lang.Class classValue()
    {
        return java.lang.Long.class;
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
        return value;
    }

    public double doubleValue()
    {
        return (double)value;
    }

    public java.lang.Object value()
    {
        return new Long(value);
    }

    public java.lang.String stringValue()
    {
        return java.lang.Long.toString(value);
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
        value = (long)f;
    }

    public void set(long l)
    {
        value = l;
    }

    public void set(double d)
    {
        value = (long)d;
    }

    public void set(java.lang.Object obj)
    {
        if(obj instanceof java.lang.Number)
            value = ((java.lang.Number)obj).longValue();
        else
            super.set(obj);
    }

    public void set(java.lang.String s)
    {
        try
        {
            value = java.lang.Long.parseLong(s);
        }
        catch(java.lang.Exception exception)
        {
            super.set(s);
        }
    }

    private long value;
}
