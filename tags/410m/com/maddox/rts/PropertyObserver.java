// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Property.java

package com.maddox.rts;

import java.util.ArrayList;

// Referenced classes of package com.maddox.rts:
//            Property, PropertyListener, MsgProperty

class PropertyObserver extends com.maddox.rts.Property
{

    protected void addListener(java.lang.Object obj, boolean flag, boolean flag1)
    {
        checkListeners();
        int i = findListener(obj);
        if(i < 0)
            listeners.add(new PropertyListener(obj, flag, flag1));
    }

    protected void removeListener(java.lang.Object obj)
    {
        checkListeners();
        int i = findListener(obj);
        if(i >= 0)
        {
            com.maddox.rts.PropertyListener propertylistener = (com.maddox.rts.PropertyListener)listeners.get(i);
            listeners.remove(i);
            propertylistener.clear();
        }
    }

    protected void invokeListeners(int i, com.maddox.rts.Property property)
    {
        if(listeners.size() == 0)
            return;
        checkListeners();
        if(listeners.size() == 0)
            return;
        invokeStackPtr++;
        java.lang.Object aobj[] = null;
        if(invokeStackPtr == invokeStack.size())
        {
            aobj = listeners.toArray();
            invokeStack.add(((java.lang.Object) (aobj)));
        } else
        {
            java.lang.Object aobj1[] = (java.lang.Object[])(java.lang.Object[])invokeStack.get(invokeStackPtr);
            aobj = listeners.toArray(aobj1);
            if(aobj != aobj1)
                listeners.set(invokeStackPtr, ((java.lang.Object) (aobj)));
        }
        for(int j = 0; j < aobj.length; j++)
        {
            com.maddox.rts.PropertyListener propertylistener = (com.maddox.rts.PropertyListener)aobj[j];
            if(propertylistener == null)
                break;
            java.lang.Object obj = propertylistener.listener();
            if(obj != null)
                com.maddox.rts.MsgProperty.post(propertylistener.isRealTime(), propertylistener.isSend(), obj, property, i);
            aobj[j] = null;
        }

        invokeStackPtr--;
    }

    protected void checkListeners()
    {
        for(int i = 0; i < listeners.size(); i++)
        {
            com.maddox.rts.PropertyListener propertylistener = (com.maddox.rts.PropertyListener)listeners.get(i);
            if(propertylistener.listener() == null)
            {
                listeners.remove(i);
                i--;
            }
        }

    }

    protected int findListener(java.lang.Object obj)
    {
        for(int i = 0; i < listeners.size(); i++)
        {
            com.maddox.rts.PropertyListener propertylistener = (com.maddox.rts.PropertyListener)listeners.get(i);
            if(propertylistener.listener() == obj)
                return i;
        }

        return -1;
    }

    protected PropertyObserver(java.lang.Object obj)
    {
        super(obj, "OBSERVER");
        listeners = new ArrayList();
    }

    private static java.util.ArrayList invokeStack = new ArrayList();
    private static int invokeStackPtr = -1;
    private java.util.ArrayList listeners;

}
