// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   Obstacle.java

package com.maddox.il2.ai.ground;


public interface Obstacle
{

    public abstract boolean unmovableInFuture();

    public abstract void collisionDeath();
}
