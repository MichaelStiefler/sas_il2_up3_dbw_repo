// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowEditNumber.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GWindowEditBox, GWindowVScrollBar, GWindowCellEdit, 
//            GWindowLookAndFeel, GWindow

public class GWindowEditNumber extends com.maddox.gwindow.GWindowDialogControl
    implements com.maddox.gwindow.GWindowCellEdit
{

    public void setRange(java.lang.Number number, java.lang.Number number1, java.lang.Number number2)
    {
        min = number;
        max = number1;
        if(number == null || number1 == null)
        {
            bar.hideWindow();
        } else
        {
            if(number2 != null)
                step = number2.doubleValue();
            else
                step = 1.0D;
            bar.setRange(-1F, 1.0F, 0.0F, -1F, 0.0F);
            bar.showWindow();
        }
        resized();
    }

    private java.lang.Number getNumberValue(java.lang.Object obj)
    {
        if(obj instanceof java.lang.Number)
            return (java.lang.Number)obj;
        if(obj instanceof java.lang.String)
        {
            if(type == (java.lang.Byte.class))
                return java.lang.Byte.valueOf((java.lang.String)obj);
            if(type == (java.lang.Short.class))
                return java.lang.Short.valueOf((java.lang.String)obj);
            if(type == (java.lang.Integer.class))
                return java.lang.Integer.valueOf((java.lang.String)obj);
            if(type == (java.lang.Long.class))
                return java.lang.Long.valueOf((java.lang.String)obj);
            if(type == (java.lang.Float.class))
                return java.lang.Float.valueOf((java.lang.String)obj);
            if(type == (java.lang.Double.class))
                return java.lang.Double.valueOf((java.lang.String)obj);
        }
        return null;
    }

    public void setCellEditValue(java.lang.Object obj)
    {
        box.setValue(obj.toString(), false);
    }

    public java.lang.Object getCellEditValue()
    {
        return box.getValue();
    }

    public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
    {
        if(gwindow == bar && i == 2)
        {
            float f = bar.pos();
            java.lang.Number number = getNumberValue(getCellEditValue());
            double d = number.doubleValue();
            if(f < 0.0F)
                d -= step;
            else
                d += step;
            if(step > 0.0D)
            {
                d = (long)(d / step);
                d *= step;
            }
            double d1 = min.doubleValue();
            if(d < d1)
                d = d1;
            double d2 = max.doubleValue();
            if(d > d2)
                d = d2;
            if(type == (java.lang.Float.class) || type == (java.lang.Double.class))
            {
                box.setValue(java.lang.Double.toString(d), false);
            } else
            {
                long l = (long)d;
                box.setValue(java.lang.Long.toString(l), false);
            }
            bar.setPos(0.0F, false);
            return true;
        } else
        {
            return false;
        }
    }

    public void resized()
    {
        super.resized();
        lookAndFeel().setupEditNumber(this);
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public void afterCreated()
    {
        super.afterCreated();
        create(box = new GWindowEditBox());
        bar = new GWindowVScrollBar(this);
        if(type.isAssignableFrom(java.lang.Byte.class) || type.isAssignableFrom(java.lang.Short.class) || type.isAssignableFrom(java.lang.Integer.class) || type.isAssignableFrom(java.lang.Long.class))
            box.bNumericOnly = true;
        bar.hideWindow();
        resized();
    }

    public GWindowEditNumber(java.lang.Class class1)
    {
        step = 1.0D;
        type = class1;
    }

    public GWindowEditNumber(com.maddox.gwindow.GWindow gwindow, java.lang.Class class1, float f, float f1, float f2, float f3, java.lang.String s)
    {
        step = 1.0D;
        toolTip = s;
        align = 0;
        type = class1;
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public java.lang.Class type;
    public java.lang.Number min;
    public java.lang.Number max;
    public double step;
    public com.maddox.gwindow.GWindowEditBox box;
    public com.maddox.gwindow.GWindowVScrollBar bar;
}
