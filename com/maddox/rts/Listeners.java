// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Listeners.java

package com.maddox.rts;

import java.util.Vector;

public class Listeners
{

    public java.lang.Object[] get()
    {
        return listeners;
    }

    public void insListener(java.lang.Object obj)
    {
        if(obj != null && -1 == vlisteners.indexOf(obj))
        {
            vlisteners.insertElementAt(obj, 0);
            java.lang.Object aobj[] = new java.lang.Object[vlisteners.size()];
            vlisteners.copyInto(aobj);
            listeners = aobj;
        }
    }

    public void addListener(java.lang.Object obj)
    {
        if(obj != null && -1 == vlisteners.indexOf(obj))
        {
            vlisteners.addElement(obj);
            java.lang.Object aobj[] = new java.lang.Object[vlisteners.size()];
            vlisteners.copyInto(aobj);
            listeners = aobj;
        }
    }

    public void removeListener(java.lang.Object obj)
    {
        if(obj != null)
        {
            vlisteners.removeElement(obj);
            if(vlisteners.size() > 0)
            {
                java.lang.Object aobj[] = new java.lang.Object[vlisteners.size()];
                vlisteners.copyInto(aobj);
                listeners = aobj;
            } else
            {
                listeners = null;
            }
        }
    }

    public Listeners()
    {
        vlisteners = new Vector();
        listeners = null;
    }

    private java.util.Vector vlisteners;
    private java.lang.Object listeners[];
}
