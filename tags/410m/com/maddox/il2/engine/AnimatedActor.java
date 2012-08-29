// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   AnimatedActor.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Animator, Mesh

public interface AnimatedActor
{

    public abstract com.maddox.il2.engine.Animator getAnimator();

    public abstract com.maddox.il2.engine.Mesh getAnimatedMesh();
}
