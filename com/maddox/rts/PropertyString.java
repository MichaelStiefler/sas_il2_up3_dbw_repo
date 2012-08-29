// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Property.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            Property, Finger

class PropertyString extends com.maddox.rts.Property
{

    public PropertyString(java.lang.Object obj, java.lang.String s)
    {
        super(obj, s);
        value = "";
        invokeObserver(lastMapInt, lastAction);
    }

    public PropertyString(java.lang.Object obj, java.lang.String s, java.lang.String s1)
    {
        super(obj, s);
        value = "";
        value = s1;
        invokeObserver(lastMapInt, lastAction);
    }

    public java.lang.Class classValue()
    {
        return java.lang.String.class;
    }

    public int intValue()
    {
        return java.lang.Integer.parseInt(value);
        java.lang.Exception exception;
        exception;
        return super.intValue();
    }

    public float floatValue()
    {
        return java.lang.Float.parseFloat(value);
        java.lang.Exception exception;
        exception;
        return super.floatValue();
    }

    public long longValue()
    {
        return java.lang.Long.parseLong(value);
        java.lang.Exception exception;
        exception;
        return super.longValue();
    }

    public double doubleValue()
    {
        return java.lang.Double.parseDouble(value);
        java.lang.Exception exception;
        exception;
        return super.doubleValue();
    }

    public java.lang.Object value()
    {
        return value;
    }

    public java.lang.String stringValue()
    {
        return value;
    }

    public long fingerValue(long l)
    {
        return com.maddox.rts.Finger.incLong(l, value);
    }

    public void set(int i)
    {
        value = java.lang.Integer.toString(i);
    }

    public void set(float f)
    {
        value = java.lang.Float.toString(f);
    }

    public void set(long l)
    {
        value = java.lang.Long.toString(l);
    }

    public void set(double d)
    {
        value = java.lang.Double.toString(d);
    }

    public void set(java.lang.String s)
    {
        value = s;
    }

    private java.lang.String value;
}
