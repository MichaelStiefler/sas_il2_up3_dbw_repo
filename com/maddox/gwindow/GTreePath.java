// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GTreePath.java

package com.maddox.gwindow;


public class GTreePath
{

    public GTreePath(java.lang.Object aobj[])
    {
        if(aobj == null || aobj.length == 0)
            throw new IllegalArgumentException("path in GTreePath must be non null and not empty.");
        for(int i = 0; i < aobj.length; i++)
            if(aobj[i] == null)
            {
                if(i == 0)
                    throw new IllegalArgumentException("path in GTreePath must be not empty.");
                lastPathComponent = aobj[i - 1];
                if(i > 1)
                    parentPath = new GTreePath(aobj, i - 1);
                return;
            }

        lastPathComponent = aobj[aobj.length - 1];
        if(aobj.length > 1)
            parentPath = new GTreePath(aobj, aobj.length - 1);
    }

    public GTreePath(java.lang.Object obj)
    {
        if(obj == null)
        {
            throw new IllegalArgumentException("path in GTreePath must be non null.");
        } else
        {
            lastPathComponent = obj;
            parentPath = null;
            return;
        }
    }

    protected GTreePath(com.maddox.gwindow.GTreePath gtreepath, java.lang.Object obj)
    {
        if(obj == null)
        {
            throw new IllegalArgumentException("path in GTreePath must be non null.");
        } else
        {
            parentPath = gtreepath;
            lastPathComponent = obj;
            return;
        }
    }

    protected GTreePath(java.lang.Object aobj[], int i)
    {
        lastPathComponent = aobj[i - 1];
        if(i > 1)
            parentPath = new GTreePath(aobj, i - 1);
    }

    protected GTreePath()
    {
    }

    public java.lang.Object[] getPath()
    {
        int i = getPathCount();
        java.lang.Object aobj[] = new java.lang.Object[i--];
        for(com.maddox.gwindow.GTreePath gtreepath = this; gtreepath != null; gtreepath = gtreepath.parentPath)
            aobj[i--] = gtreepath.lastPathComponent;

        return aobj;
    }

    public java.lang.Object[] getPath(java.lang.Object aobj[])
    {
        int i = getPathCount();
        if(aobj == null || aobj.length < i)
            aobj = new java.lang.Object[i];
        i--;
        for(com.maddox.gwindow.GTreePath gtreepath = this; gtreepath != null; gtreepath = gtreepath.parentPath)
            aobj[i--] = gtreepath.lastPathComponent;

        return aobj;
    }

    public java.lang.Object getLastPathComponent()
    {
        return lastPathComponent;
    }

    public int getPathCount()
    {
        int i = 0;
        for(com.maddox.gwindow.GTreePath gtreepath = this; gtreepath != null; gtreepath = gtreepath.parentPath)
            i++;

        return i;
    }

    public java.lang.Object getPathComponent(int i)
    {
        int j = getPathCount();
        if(i < 0 || i >= j)
            throw new IllegalArgumentException("Index " + i + " is out of the specified range");
        com.maddox.gwindow.GTreePath gtreepath = this;
        for(int k = j - 1; k != i; k--)
            gtreepath = gtreepath.parentPath;

        return gtreepath.lastPathComponent;
    }

    public boolean equals(java.lang.Object obj)
    {
        if(obj == this)
            return true;
        if(obj instanceof com.maddox.gwindow.GTreePath)
        {
            com.maddox.gwindow.GTreePath gtreepath = (com.maddox.gwindow.GTreePath)obj;
            if(getPathCount() != gtreepath.getPathCount())
                return false;
            for(com.maddox.gwindow.GTreePath gtreepath1 = this; gtreepath1 != null; gtreepath1 = gtreepath1.parentPath)
            {
                if(!gtreepath1.lastPathComponent.equals(gtreepath.lastPathComponent))
                    return false;
                gtreepath = gtreepath.parentPath;
            }

            return true;
        } else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return lastPathComponent.hashCode();
    }

    public boolean isDescendant(com.maddox.gwindow.GTreePath gtreepath)
    {
        if(gtreepath == this)
            return true;
        if(gtreepath != null)
        {
            int i = getPathCount();
            int j = gtreepath.getPathCount();
            if(j < i)
                return false;
            while(j-- > i) 
                gtreepath = gtreepath.getParentPath();
            return equals(gtreepath);
        } else
        {
            return false;
        }
    }

    public com.maddox.gwindow.GTreePath pathByAddingChild(java.lang.Object obj)
    {
        if(obj == null)
            throw new NullPointerException("Null child not allowed");
        else
            return new GTreePath(this, obj);
    }

    public com.maddox.gwindow.GTreePath getParentPath()
    {
        return parentPath;
    }

    public java.lang.String toString()
    {
        java.lang.StringBuffer stringbuffer = new StringBuffer("[");
        int i = 0;
        for(int j = getPathCount(); i < j; i++)
        {
            if(i > 0)
                stringbuffer.append(", ");
            stringbuffer.append(getPathComponent(i));
        }

        stringbuffer.append("]");
        return stringbuffer.toString();
    }

    private com.maddox.gwindow.GTreePath parentPath;
    private java.lang.Object lastPathComponent;
}
