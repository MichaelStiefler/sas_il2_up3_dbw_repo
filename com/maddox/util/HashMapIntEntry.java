// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   HashMapIntEntry.java

package com.maddox.util;


public interface HashMapIntEntry
{

    public abstract int getKey();

    public abstract java.lang.Object getValue();

    public abstract java.lang.Object setValue(java.lang.Object obj);

    public abstract boolean equals(java.lang.Object obj);

    public abstract int hashCode();
}
