// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   States.java

package com.maddox.rts;


// Referenced classes of package com.maddox.rts:
//            State, RTSException, MessageProxy, Destroy, 
//            Message

public class States
    implements com.maddox.rts.MessageProxy, com.maddox.rts.Destroy
{

    public int getState()
    {
        return indxState;
    }

    public int getPrevState()
    {
        return indxPrevState;
    }

    public java.lang.Object getListener()
    {
        return state;
    }

    public boolean isDestroyed()
    {
        return indxState == -2;
    }

    public void destroy()
    {
        for(int i = 0; i < states.length; i++)
            if(states[i] != null)
            {
                ((com.maddox.rts.State)states[i]).setParentListener(null);
                states[i] = null;
            }

        states = null;
        state = null;
    }

    public java.lang.Object getListener(com.maddox.rts.Message message)
    {
        return state;
    }

    public java.lang.Object getListener(int i)
    {
        if(i < 0)
            return null;
        else
            return states[i];
    }

    public void setListener(int i, java.lang.Object obj)
    {
        if(i >= 0)
            if(i == indxState)
            {
                int j = indxPrevState;
                setState(-1);
                states[i] = obj;
                setState(i);
                indxPrevState = j;
            } else
            {
                states[i] = obj;
            }
    }

    public void setState(int i)
    {
        if(indxState == -2)
            throw new RTSException("Destroyd object try change state");
        if(indxState == i)
            return;
        java.lang.Object obj;
        if(i >= 0)
            obj = states[i];
        else
            obj = null;
        java.lang.Object obj1 = getListener();
        if(obj1 != null && (obj1 instanceof com.maddox.rts.State))
            ((com.maddox.rts.State)obj1).end(i);
        indxPrevState = indxState;
        indxState = i;
        state = obj;
        if(state != null && (state instanceof com.maddox.rts.State))
            ((com.maddox.rts.State)state).begin(indxPrevState);
    }

    public java.lang.String getStateName()
    {
        if(state != null)
            return state.getClass().getName();
        if(indxState == -2)
            return "DESTROYED";
        else
            return "UNKNOWN";
    }

    public States(java.lang.Object aobj[])
    {
        states = aobj;
        state = null;
        indxState = -1;
        indxPrevState = -1;
    }

    public States()
    {
    }

    private int indxState;
    private int indxPrevState;
    private java.lang.Object states[];
    private java.lang.Object state;
}
