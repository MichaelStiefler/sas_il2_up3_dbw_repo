// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   EngineProfile.java

package com.maddox.il2.engine;


// Referenced classes of package com.maddox.il2.engine:
//            Engine

public class EngineProfile
{

    public EngineProfile()
    {
    }

    public static void get(com.maddox.il2.engine.EngineProfile engineprofile)
    {
        engineprofile.frames = com.maddox.il2.engine.Engine.cur.profile.frames;
        engineprofile.collideLineAll = com.maddox.il2.engine.Engine.cur.profile.collideLineAll;
        engineprofile.collideLineSphere = com.maddox.il2.engine.Engine.cur.profile.collideLineSphere;
        engineprofile.collideLine = com.maddox.il2.engine.Engine.cur.profile.collideLine;
        engineprofile.collideSphereAll = com.maddox.il2.engine.Engine.cur.profile.collideSphereAll;
        engineprofile.collideSphereSphere = com.maddox.il2.engine.Engine.cur.profile.collideSphereSphere;
        engineprofile.collideSphere = com.maddox.il2.engine.Engine.cur.profile.collideSphere;
        engineprofile.drawAll = com.maddox.il2.engine.Engine.cur.profile.drawAll;
        engineprofile.draw = com.maddox.il2.engine.Engine.cur.profile.draw;
        com.maddox.il2.engine.Engine.cur.profile.drawAll = 0;
        com.maddox.il2.engine.Engine.cur.profile.draw = 0;
        com.maddox.il2.engine.EngineProfile.getLow(arr);
        engineprofile.effSprites = arr[0];
        engineprofile.effSmokes = arr[1];
        engineprofile.effParticles = arr[2];
        engineprofile.sprites = arr[3];
        engineprofile.trianglesSortable = arr[4];
        engineprofile.trianglesNonSortable = arr[5];
        engineprofile.trianglesShadowsXXX = arr[6];
        engineprofile.trianglesLand = arr[7];
    }

    public static void clrSum()
    {
        com.maddox.il2.engine.Engine.cur.profile.frames = 0;
        com.maddox.il2.engine.Engine.cur.profile.collideLineAll = 0;
        com.maddox.il2.engine.Engine.cur.profile.collideLineSphere = 0;
        com.maddox.il2.engine.Engine.cur.profile.collideLine = 0;
        com.maddox.il2.engine.Engine.cur.profile.collideSphereAll = 0;
        com.maddox.il2.engine.Engine.cur.profile.collideSphereSphere = 0;
        com.maddox.il2.engine.Engine.cur.profile.collideSphere = 0;
    }

    protected void endFrame()
    {
        frames++;
        collideLineAll += countCollideLineAll;
        collideLineSphere += countCollideLineSphere;
        collideLine += countCollideLine;
        collideSphereAll += countCollideSphereAll;
        collideSphereSphere += countCollideSphereSphere;
        collideSphere += countCollideSphere;
        countCollideLineAll = 0;
        countCollideLineSphere = 0;
        countCollideLine = 0;
        countCollideSphereAll = 0;
        countCollideSphereSphere = 0;
        countCollideSphere = 0;
        drawAll += countDrawAll;
        draw += countDraw;
        countDrawAll = 0;
        countDraw = 0;
    }

    private static native void getLow(int ai[]);

    public int frames;
    public int collideLineAll;
    public int collideLineSphere;
    public int collideLine;
    public int collideSphereAll;
    public int collideSphereSphere;
    public int collideSphere;
    public int drawAll;
    public int draw;
    public int effSprites;
    public int effSmokes;
    public int effParticles;
    public int sprites;
    public int trianglesSortable;
    public int trianglesNonSortable;
    public int trianglesShadowsXXX;
    public int trianglesLand;
    protected int countCollideLineAll;
    protected int countCollideLineSphere;
    protected int countCollideLine;
    protected int countCollideSphereAll;
    protected int countCollideSphereSphere;
    protected int countCollideSphere;
    protected int countDrawAll;
    protected int countDraw;
    private static int arr[] = new int[8];

}
