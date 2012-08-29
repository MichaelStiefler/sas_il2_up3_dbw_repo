// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GFileFilter.java

package com.maddox.gwindow;

import java.io.File;

public interface GFileFilter
{

    public abstract boolean accept(java.io.File file);

    public abstract java.lang.String getDescription();
}
