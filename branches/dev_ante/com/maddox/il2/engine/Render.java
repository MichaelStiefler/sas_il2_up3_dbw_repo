package com.maddox.il2.engine;

import com.maddox.JGP.Color4f;
import com.maddox.opengl.gl;
import com.maddox.rts.State;
import com.maddox.rts.States;
import java.util.List;
import java.util.TreeSet;

public class Render extends Actor
{
  public static final int STATE_SHOW = 0;
  public static final int STATE_HIDDEN = 1;
  protected float zOrder;
  protected boolean bSaveAspect;
  public static final int VIEW_PORT_X0 = 0;
  public static final int VIEW_PORT_Y0 = 1;
  public static final int VIEW_PORT_DX = 2;
  public static final int VIEW_PORT_DY = 3;
  protected float[] viewPort;
  protected Camera camera = null;

  protected LightEnv lightEnv = null;
  public static final float CLEAR_DEPTH_MIN = 1.0E-006F;
  public static final float CLEAR_DEPTH_MAX = 0.999999F;
  protected boolean bClearColor = true;
  protected Color4f clearColor = new Color4f(0.0F, 0.0F, 0.0F, 1.0F);
  protected boolean bClearDepth = true;
  protected float clearDepth = 0.999999F;
  protected boolean bClearStencil = false;
  protected boolean bSolidArea = false;
  private Renders renders;
  private static int[] tmpI4 = new int[4];
  private static float[] tmpF4 = new float[4];
  public static final int DRAW_MODE_CAMERA = -1;
  public static final int DRAW_MODE_WORLD_REAL = 0;
  public static final int DRAW_MODE_WORLD_VIRT = 1;
  public static final int LineFlags = Mat.BLEND | Mat.TESTZ | Mat.MODULATE | Mat.NOTEXTURE;
  public static final int lmAntialise = 1;
  public static final int lmDrawStrip = 2;
  public static final int lmDrawLoop = 4;

  public static Render current()
  {
    return Renders.currentRender;
  }

  public static Camera currentCamera()
  {
    return Renders.currentCamera;
  }

  public static LightEnv currentLightEnv()
  {
    return Renders.currentLightEnv;
  }
  public static boolean isPreRendering() {
    return Renders.bPreRendering;
  }
  public static boolean isRendering() {
    return Renders.bRendering;
  }

  public boolean isShow()
  {
    return state() == 0;
  }
  public void setShow(boolean paramBoolean) {
    this.jdField_states_of_type_ComMaddoxRtsStates.setState(paramBoolean ? 0 : 1);
  }

  public float getZOrder()
  {
    return this.zOrder;
  }

  public void setZOrder(float paramFloat)
  {
    if (this.zOrder != paramFloat) {
      this.renders.renderSet.remove(this);
      this.zOrder = paramFloat;
      this.renders.renderSet.add(this);
      this.renders.renderArray = this.renders.renderSet.toArray(this.renders.renderArray);
    }
  }

  public boolean isSaveAspect()
  {
    return this.bSaveAspect;
  }

  public void setSaveAspect(boolean paramBoolean) {
    if (this.bSaveAspect != paramBoolean) {
      this.bSaveAspect = paramBoolean;
      float[] arrayOfFloat = { 0.0F, 0.0F, 1.0F, 1.0F };
      if (this.bSaveAspect)
        getAspectViewPort(arrayOfFloat);
      setViewPort(arrayOfFloat);
      contextResize(contextWidth(), contextHeight());
    }
  }

  protected void clampViewPort(int[] paramArrayOfInt)
  {
  }

  public void getViewPort(float[] paramArrayOfFloat)
  {
    paramArrayOfFloat[0] = this.viewPort[0];
    paramArrayOfFloat[1] = this.viewPort[1];
    paramArrayOfFloat[2] = this.viewPort[2];
    paramArrayOfFloat[3] = this.viewPort[3];
  }

  public void getViewPort(int[] paramArrayOfInt)
  {
    paramArrayOfInt[0] = (int)(this.viewPort[0] * this.renders.width() + 0.5F);
    paramArrayOfInt[1] = (int)(this.viewPort[1] * this.renders.height() + 0.5F);
    paramArrayOfInt[2] = (int)(this.viewPort[2] * this.renders.width() + 0.5F);
    paramArrayOfInt[3] = (int)(this.viewPort[3] * this.renders.height() + 0.5F);
    clampViewPort(paramArrayOfInt);
  }
  public int getViewPortX0() {
    return (int)(this.viewPort[0] * this.renders.width() + 0.5F); } 
  public int getViewPortY0() { return (int)(this.viewPort[1] * this.renders.height() + 0.5F); }

  public int getViewPortWidth() {
    return (int)(this.viewPort[2] * this.renders.width() + 0.5F);
  }
  public int getViewPortHeight() {
    return (int)(this.viewPort[3] * this.renders.height() + 0.5F);
  }

  public void setViewPort(float[] paramArrayOfFloat) {
    this.viewPort[0] = paramArrayOfFloat[0];
    this.viewPort[1] = paramArrayOfFloat[1];
    this.viewPort[2] = paramArrayOfFloat[2];
    this.viewPort[3] = paramArrayOfFloat[3];
    getViewPort(tmpI4);
    setViewPort(tmpI4);
  }

  public void setViewPort(int[] paramArrayOfInt)
  {
    tmpI4[0] = paramArrayOfInt[0];
    tmpI4[1] = paramArrayOfInt[1];
    tmpI4[2] = paramArrayOfInt[2];
    tmpI4[3] = paramArrayOfInt[3];
    clampViewPort(tmpI4);
    this.viewPort[0] = (tmpI4[0] / this.renders.width());
    this.viewPort[1] = (tmpI4[1] / this.renders.height());
    this.viewPort[2] = (tmpI4[2] / this.renders.width());
    this.viewPort[3] = (tmpI4[3] / this.renders.height());
    if ((this.camera != null) && ((this.camera instanceof Camera3D)))
      ((Camera3D)this.camera).setViewPortWidth(tmpI4[2]);
  }

  public void getAspectViewPort(float[] paramArrayOfFloat)
  {
    this.renders.getAspectViewPort(paramArrayOfFloat);
  }
  public void getAspectViewPort(int[] paramArrayOfInt) {
    this.renders.getAspectViewPort(paramArrayOfInt);
  }

  public Camera getCamera()
  {
    return this.camera;
  }
  public void setCamera(Camera paramCamera) { this.camera = paramCamera;
    if ((this.camera != null) && ((this.camera instanceof Camera3D)))
      ((Camera3D)this.camera).setViewPortWidth((int)(this.viewPort[2] * this.renders.width() + 0.5F));
  }

  public LightEnv getLightEnv()
  {
    return this.lightEnv; } 
  public void setLightEnv(LightEnv paramLightEnv) { this.lightEnv = paramLightEnv;
  }

  public boolean isClearColor()
  {
    return this.bClearColor; } 
  public void useClearColor(boolean paramBoolean) { this.bClearColor = paramBoolean; } 
  public Color4f getClearColor() { return this.clearColor; } 
  public void setClearColor(Color4f paramColor4f) {
    this.clearColor.set(paramColor4f);
    this.clearColor.clamp(0.0F, 1.0F);
  }
  public boolean isClearDepth() { return this.bClearDepth; } 
  public void useClearDepth(boolean paramBoolean) { this.bClearDepth = paramBoolean; } 
  public float getClearDepth() { return this.clearDepth; } 
  public void setClearDepth(float paramFloat) {
    this.clearDepth = paramFloat;
    if (this.clearDepth <= 1.0E-006F) this.clearDepth = 1.0E-006F;
    if (this.clearDepth >= 0.999999F) this.clearDepth = 0.999999F; 
  }

  public boolean isClearStencil() {
    return this.bClearStencil; } 
  public void useClearStencil(boolean paramBoolean) { this.bClearStencil = paramBoolean; }

  protected void preRender() {
  }

  protected void render() {
  }

  public int contextWidth() {
    return this.renders.width();
  }
  public int contextHeight() {
    return this.renders.height();
  }

  public void contextResized() {
    contextResize(contextWidth(), contextHeight());
  }

  protected void contextResize(int paramInt1, int paramInt2)
  {
    if (isSaveAspect()) {
      getAspectViewPort(tmpF4);
      setViewPort(tmpF4);
      if ((this.camera != null) && ((this.camera instanceof CameraOrtho2D))) {
        getAspectViewPort(tmpI4);
        ((CameraOrtho2D)this.camera).set(0.0F, tmpI4[2], 0.0F, tmpI4[3]);
      }
    }
    else {
      this.viewPort[0] = 0.0F;
      this.viewPort[1] = 0.0F;
      this.viewPort[2] = 1.0F;
      this.viewPort[3] = 1.0F;
      getViewPort(tmpI4);
      setViewPort(tmpI4);
      if (this.camera != null) {
        if ((this.camera instanceof CameraOrtho2D))
          ((CameraOrtho2D)this.camera).set(0.0F, paramInt1, 0.0F, paramInt2);
        else if ((this.camera instanceof Camera3D)) {
          ((Camera3D)this.camera).set(((Camera3D)this.camera).FOV(), paramInt1 / paramInt2);
        }
      }
    }
    if ((this.camera != null) && ((this.camera instanceof Camera3D)))
      ((Camera3D)this.camera).setViewPortWidth((int)(this.viewPort[2] * paramInt1 + 0.5F));
  }

  private void draw(List paramList, boolean paramBoolean)
  {
    if ((paramList == null) || (paramList.size() == 0)) return;
    int i = paramList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)paramList.get(j);
      localActor.draw.render(localActor);
    }
    if (paramBoolean)
      paramList.clear();
    MeshShared.renderArray(false);
  }
  private void drawShadowProjective(List paramList, boolean paramBoolean) {
    if ((paramList == null) || (paramList.size() == 0)) return;
    int i = paramList.size();

    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)paramList.get(j);
      localActor.draw.renderShadowProjective(localActor);
    }

    if (paramBoolean)
      paramList.clear();
    MeshShared.renderArrayShadowProjective();
  }

  private void drawShadowVolume(List paramList, boolean paramBoolean) {
    if ((paramList == null) || (paramList.size() == 0)) return;
    int i = paramList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)paramList.get(j);
      localActor.draw.renderShadowVolume(localActor);
    }
    if (paramBoolean)
      paramList.clear();
    MeshShared.renderArrayShadowVolume();
  }
  private void drawShadowVolumeHQ(List paramList, boolean paramBoolean) {
    if ((paramList == null) || (paramList.size() == 0)) return;
    int i = paramList.size();
    for (int j = 0; j < i; j++) {
      Actor localActor = (Actor)paramList.get(j);
      localActor.draw.renderShadowVolumeHQ(localActor);
    }
    if (paramBoolean)
      paramList.clear();
    MeshShared.renderArrayShadowVolumeHQ();
  }

  public void drawShadow(List paramList) {
    if (paramList == null) return;
    if ((!RenderContext.bRenderEnable) || (RenderContext.shadowGet() == 0))
    {
      paramList.clear();
      return;
    }
    drawShadowProjective(paramList, true);
  }

  public void draw(List paramList1, List paramList2)
  {
    if (!RenderContext.bRenderEnable) {
      if (paramList1 != null) paramList1.clear();
      if (paramList2 != null) paramList2.clear();
      return;
    }
    draw(paramList1, true);
    draw(paramList2, true);
  }

  public void draw(List paramList1, List paramList2, List paramList3)
  {
    if (!RenderContext.bRenderEnable) {
      if (paramList1 != null) paramList1.clear();
      if (paramList2 != null) paramList2.clear();
      if (paramList3 != null) paramList3.clear();
      return;
    }
    switch (RenderContext.shadowGet()) {
    case 1:
    case 2:
      drawShadowProjective(paramList3, true);
    case 0:
      draw(paramList1, true);
      draw(paramList2, true);
      break;
    case 3:
      draw(paramList1, true);
      draw(paramList2, true);
      drawShadowVolume(paramList3, true);
      break;
    case 4:
      draw(paramList1, false);
      draw(paramList2, false);

      drawShadowVolumeHQ(paramList3, true);

      Sun localSun = currentLightEnv().sun();
      float f1 = localSun.Diffuze;
      localSun.Diffuze = 0.0F;
      float f2 = localSun.Specular;
      localSun.Specular = 0.0F;
      localSun.activate();

      gl.Enable(2960);
      gl.StencilFunc(517, 0, -1);
      gl.StencilOp(7680, 7680, 7680);
      draw(paramList1, true);
      draw(paramList2, true);

      localSun.Diffuze = f1; localSun.Specular = f2;
      localSun.activate();
    }
  }

  public void destroy()
  {
    this.renders.renderSet.remove(this);
    this.renders.renderArray = this.renders.renderSet.toArray(this.renders.renderArray);
    super.destroy();
  }

  public Renders renders() {
    return this.renders;
  }
  public Render(float paramFloat) {
    this(Engine.rendersMain(), paramFloat);
  }

  public Render(Renders paramRenders, float paramFloat) {
    this.flags |= 24576;
    this.jdField_states_of_type_ComMaddoxRtsStates = new States(new Object[] { new State(this), new State(this) });
    this.jdField_states_of_type_ComMaddoxRtsStates.setState(0);
    this.zOrder = paramFloat;
    this.renders = paramRenders;
    paramRenders.renderSet.add(this);
    paramRenders.renderArray = paramRenders.renderSet.toArray(paramRenders.renderArray);

    this.bSaveAspect = paramRenders.isSaveAspect();
    this.viewPort = new float[] { 0.0F, 0.0F, 1.0F, 1.0F };
    if (isSaveAspect())
      getAspectViewPort(this.viewPort); 
  }

  protected void createActorHashCode() {
    makeActorRealHashCode(); } 
  public static native void flush();

  public static native void shadows();

  public static native void enableFog(boolean paramBoolean);

  public static native void transform3f(float[] paramArrayOfFloat, int paramInt);

  public static native void transformVirt3f(float[] paramArrayOfFloat, int paramInt);

  public static native void transform3fInv(float[] paramArrayOfFloat, int paramInt);

  public static native void vertexArraysTransformAndLock(float[] paramArrayOfFloat, int paramInt);

  public static native void vertexArraysUnLock();

  public static boolean drawSetMaterial(Mat paramMat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) { return DrawSetMaterial(paramMat.cppObject(), paramFloat1, paramFloat2, paramFloat3, paramFloat4); }

  public static boolean drawSetMaterial(Mat paramMat)
  {
    return DrawSetMaterial0(paramMat.cppObject());
  }

  public static native void drawBeginSprites(int paramInt);

  public static native void drawPushSprite(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, float paramFloat5);

  public static native void drawPushSprite(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, float paramFloat5, float paramFloat6);

  public static native void drawPushSprite(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9);

  public static native void prepareStates();

  public static native void clearStates();

  public static native void drawBeginTriangleLists(int paramInt);

  public static native void drawTriangleList(float[] paramArrayOfFloat, int[] paramArrayOfInt1, int paramInt1, int[] paramArrayOfInt2, int paramInt2);

  public static native void drawTriangleList(float[] paramArrayOfFloat, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt, int paramInt4);

  public static native void drawEnd();

  public static native void drawBeginLines(int paramInt);

  public static native void drawLines(float[] paramArrayOfFloat, int paramInt1, float paramFloat, int paramInt2, int paramInt3, int paramInt4);

  private static native boolean DrawSetMaterial(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  private static native boolean DrawSetMaterial0(int paramInt);

  public static void drawTile(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, Mat paramMat, int paramInt, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9)
  {
    DrawTile(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramMat.cppObject(), paramInt, paramFloat6, paramFloat7, paramFloat8, paramFloat9);
  }

  public static native void DrawTile(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, int paramInt1, int paramInt2, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9);

  static {
    GObj.loadNative();
  }
}