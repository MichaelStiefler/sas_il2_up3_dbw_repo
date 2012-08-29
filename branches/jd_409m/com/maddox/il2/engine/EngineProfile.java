package com.maddox.il2.engine;

public class EngineProfile
{
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
  private static int[] arr = new int[8];

  public static void get(EngineProfile paramEngineProfile)
  {
    paramEngineProfile.frames = Engine.cur.profile.frames;
    paramEngineProfile.collideLineAll = Engine.cur.profile.collideLineAll;
    paramEngineProfile.collideLineSphere = Engine.cur.profile.collideLineSphere;
    paramEngineProfile.collideLine = Engine.cur.profile.collideLine;
    paramEngineProfile.collideSphereAll = Engine.cur.profile.collideSphereAll;
    paramEngineProfile.collideSphereSphere = Engine.cur.profile.collideSphereSphere;
    paramEngineProfile.collideSphere = Engine.cur.profile.collideSphere;
    paramEngineProfile.drawAll = Engine.cur.profile.drawAll;
    paramEngineProfile.draw = Engine.cur.profile.draw;
    Engine.cur.profile.drawAll = 0;
    Engine.cur.profile.draw = 0;
    getLow(arr);
    paramEngineProfile.effSprites = arr[0];
    paramEngineProfile.effSmokes = arr[1];
    paramEngineProfile.effParticles = arr[2];
    paramEngineProfile.sprites = arr[3];
    paramEngineProfile.trianglesSortable = arr[4];
    paramEngineProfile.trianglesNonSortable = arr[5];
    paramEngineProfile.trianglesShadowsXXX = arr[6];
    paramEngineProfile.trianglesLand = arr[7];
  }

  public static void clrSum() {
    Engine.cur.profile.frames = 0;
    Engine.cur.profile.collideLineAll = 0;
    Engine.cur.profile.collideLineSphere = 0;
    Engine.cur.profile.collideLine = 0;
    Engine.cur.profile.collideSphereAll = 0;
    Engine.cur.profile.collideSphereSphere = 0;
    Engine.cur.profile.collideSphere = 0;
  }

  protected void endFrame() {
    this.frames += 1;
    this.collideLineAll += this.countCollideLineAll;
    this.collideLineSphere += this.countCollideLineSphere;
    this.collideLine += this.countCollideLine;
    this.collideSphereAll += this.countCollideSphereAll;
    this.collideSphereSphere += this.countCollideSphereSphere;
    this.collideSphere += this.countCollideSphere;
    this.countCollideLineAll = 0;
    this.countCollideLineSphere = 0;
    this.countCollideLine = 0;
    this.countCollideSphereAll = 0;
    this.countCollideSphereSphere = 0;
    this.countCollideSphere = 0;
    this.drawAll += this.countDrawAll;
    this.draw += this.countDraw;
    this.countDrawAll = 0;
    this.countDraw = 0;
  }

  private static native void getLow(int[] paramArrayOfInt);
}