// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   TypeX4Carrier.java

package com.maddox.il2.objects.air;


public interface TypeX4Carrier
{

    public abstract void typeX4CAdjSidePlus();

    public abstract void typeX4CAdjSideMinus();

    public abstract void typeX4CAdjAttitudePlus();

    public abstract void typeX4CAdjAttitudeMinus();

    public abstract void typeX4CResetControls();

    public abstract float typeX4CgetdeltaAzimuth();

    public abstract float typeX4CgetdeltaTangage();
}
