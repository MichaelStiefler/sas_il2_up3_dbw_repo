package com.maddox.gwindow;

public abstract class GWindowLookAndFeel
{
  public GBevel bevelTabDialogClient;
  public GTexRegion regionWhite;
  public boolean bSoundEnable;
  public static final float metricConst = 12.0F;

  public float metric()
  {
    return 12.0F;
  }

  public float metric(float paramFloat)
  {
    return metric() * paramFloat;
  }
  public void drawBevel(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GBevel paramGBevel, GTexture paramGTexture) {
    drawBevel(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramGBevel, paramGTexture, true);
  }

  public void drawBevel(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, GBevel paramGBevel, GTexture paramGTexture, boolean paramBoolean)
  {
    GRegion localGRegion = paramGBevel.TL;
    paramGWindow.draw(paramFloat1, paramFloat2, localGRegion.dx, localGRegion.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = paramGBevel.T;
    paramGWindow.draw(paramFloat1 + paramGBevel.TL.dx, paramFloat2, paramFloat3 - paramGBevel.TL.dx - paramGBevel.TR.dx, localGRegion.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = paramGBevel.TR;
    paramGWindow.draw(paramFloat1 + paramFloat3 - localGRegion.dx, paramFloat2, localGRegion.dx, localGRegion.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = paramGBevel.L;
    paramGWindow.draw(paramFloat1, paramFloat2 + paramGBevel.TL.dy, localGRegion.dx, paramFloat4 - paramGBevel.TL.dy - paramGBevel.BL.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = paramGBevel.R;
    paramGWindow.draw(paramFloat1 + paramFloat3 - localGRegion.dx, paramFloat2 + paramGBevel.TL.dy, localGRegion.dx, paramFloat4 - paramGBevel.TL.dy - paramGBevel.BL.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = paramGBevel.BL;
    paramGWindow.draw(paramFloat1, paramFloat2 + paramFloat4 - localGRegion.dy, localGRegion.dx, localGRegion.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = paramGBevel.B;
    paramGWindow.draw(paramFloat1 + paramGBevel.BL.dx, paramFloat2 + paramFloat4 - localGRegion.dy, paramFloat3 - paramGBevel.BL.dx - paramGBevel.BR.dx, localGRegion.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = paramGBevel.BR;
    paramGWindow.draw(paramFloat1 + paramFloat3 - localGRegion.dx, paramFloat2 + paramFloat4 - localGRegion.dy, localGRegion.dx, localGRegion.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    if (paramBoolean) {
      localGRegion = paramGBevel.Area;
      paramGWindow.draw(paramFloat1 + paramGBevel.TL.dx, paramFloat2 + paramGBevel.TL.dy, paramFloat3 - paramGBevel.BL.dx - paramGBevel.BR.dx, paramFloat4 - paramGBevel.TL.dy - paramGBevel.BL.dy, paramGTexture, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    }
  }

  public void fillRegion(GWindow paramGWindow, GColor paramGColor, GRegion paramGRegion)
  {
    fillRegion(paramGWindow, paramGColor.color, paramGRegion.x, paramGRegion.y, paramGRegion.dx, paramGRegion.dy);
  }
  public void fillRegion(GWindow paramGWindow, int paramInt, GRegion paramGRegion) {
    fillRegion(paramGWindow, paramInt, paramGRegion.x, paramGRegion.y, paramGRegion.dx, paramGRegion.dy);
  }

  public void fillRegion(GWindow paramGWindow, GColor paramGColor, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
    fillRegion(paramGWindow, paramGColor.color, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  public void fillRegion(GWindow paramGWindow, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
  }
  public void drawSeparateH(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
  }
  public void drawSeparateW(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
  }
  public void soundPlay(String paramString) {
  }
  public void renderClient(GWindowTree paramGWindowTree) {  }

  public void render(GWindowTree paramGWindowTree) {  }

  public GRegion getClientRegion(GWindowTree paramGWindowTree, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowTable paramGWindowTable) {
  }
  public GRegion getClientRegion(GWindowTable paramGWindowTable, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowTabDialogClient paramGWindowTabDialogClient) {
  }
  public void render(GWindowTabDialogClient.Tab paramTab) {  }

  public GSize getMinSize(GWindowTabDialogClient.Tab paramTab, GSize paramGSize) { return paramGSize; } 
  public void render(GWindowComboControl paramGWindowComboControl) {
  }
  public void renderComboList(GWindowComboControl paramGWindowComboControl) {  }

  public void setupComboList(GWindowComboControl paramGWindowComboControl) {  }

  public void setupComboEditBox(GWindowEditBox paramGWindowEditBox) {  }

  public void setupComboButton(GWindowButtonTexture paramGWindowButtonTexture) {  }

  public float getComboH() { return 14.400001F; } 
  public float getComboHmetric() { return 1.2F; } 
  public float getComboHline() { return 14.400001F; } 
  public void render(GWindowBoxSeparate paramGWindowBoxSeparate) {
  }
  public void render(GWindowVSeparate paramGWindowVSeparate) {
  }
  public float getVSeparateW() { return 2.0F; } 
  public void render(GWindowHSeparate paramGWindowHSeparate) {
  }
  public float getHSeparateH() { return 2.0F; } 
  public void render(GWindowVSliderInt paramGWindowVSliderInt) {
  }
  public void setupVSliderIntSizes(GWindowVSliderInt paramGWindowVSliderInt) {  }

  public float getVSliderIntW() { return 12.0F; } 
  public float getVSliderIntWmetric() { return 1.0F; } 
  public void render(GWindowHSliderInt paramGWindowHSliderInt) {
  }
  public void setupHSliderIntSizes(GWindowHSliderInt paramGWindowHSliderInt) {  }

  public float getHSliderIntH() { return 12.0F; } 
  public float getHSliderIntHmetric() { return 1.0F; } 
  public void render(GWindowEditNumber paramGWindowEditNumber) {
  }
  public void setupEditNumber(GWindowEditNumber paramGWindowEditNumber) {
  }
  public void render(GWindowEditControl paramGWindowEditControl) {
  }
  public void render(GWindowEditBox paramGWindowEditBox, float paramFloat) {  }

  public void render(GWindowEditText paramGWindowEditText) {  }

  public void render(GWindowEditTextControl paramGWindowEditTextControl) {  }

  public float getBorderSizeEditTextControl() { return 0.0F; } 
  public void render(GWindowCheckBox paramGWindowCheckBox) {
  }
  public GSize getMinSize(GWindowCheckBox paramGWindowCheckBox, GSize paramGSize) { return paramGSize; } 
  public void render(GWindowVScrollBar paramGWindowVScrollBar) {
  }
  public void setupVScrollBarSizes(GWindowVScrollBar paramGWindowVScrollBar) {
  }
  public void render(GWindowHScrollBar paramGWindowHScrollBar) {
  }
  public void setupHScrollBarSizes(GWindowHScrollBar paramGWindowHScrollBar) {  }

  public float getHScrollBarH() { return 12.0F; } 
  public float getHScrollBarW() { return 12.0F; } 
  public float getVScrollBarH() { return 12.0F; } 
  public float getVScrollBarW() { return 12.0F; } 
  public void setupScrollButtonUP(GWindowButtonTexture paramGWindowButtonTexture) {
  }
  public void setupScrollButtonDOWN(GWindowButtonTexture paramGWindowButtonTexture) {  }

  public void setupScrollButtonLEFT(GWindowButtonTexture paramGWindowButtonTexture) {  }

  public void setupScrollButtonRIGHT(GWindowButtonTexture paramGWindowButtonTexture) {  }

  public void render(GWindowLabel paramGWindowLabel) {  }

  public GSize getMinSize(GWindowLabel paramGWindowLabel, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegion(GWindowLabel paramGWindowLabel, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowButton paramGWindowButton) {
  }
  public void render(GWindowButtonTexture paramGWindowButtonTexture) {  }

  public GSize getMinSize(GWindowButton paramGWindowButton, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegion(GWindowButton paramGWindowButton, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void renderTextDialogControl(GWindowDialogControl paramGWindowDialogControl, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, boolean paramBoolean) {
  }
  public GSize getMinSizeDialogControl(GWindowDialogControl paramGWindowDialogControl, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegionDialogControl(GWindowDialogControl paramGWindowDialogControl, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowClient paramGWindowClient) {
  }
  public void setMessageBoxTextColor(GWindowClient paramGWindowClient) {
    paramGWindowClient.setCanvasColorBLACK();
  }
  public void setupFrameCloseBox(GWindowFrameCloseBox paramGWindowFrameCloseBox) {  }

  public void frameSetCloseBoxPos(GWindowFramed paramGWindowFramed) {  }

  public int frameHitTest(GWindowFramed paramGWindowFramed, float paramFloat1, float paramFloat2) { return 0; } 
  public void render(GWindowFramed paramGWindowFramed) {  }

  public GSize getMinSize(GWindowFramed paramGWindowFramed, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegion(GWindowFramed paramGWindowFramed, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowMenuItem paramGWindowMenuItem) {
  }
  public GSize getMinSize(GWindowMenuItem paramGWindowMenuItem, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegion(GWindowMenuItem paramGWindowMenuItem, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowMenu paramGWindowMenu) {
  }
  public GSize getMinSize(GWindowMenu paramGWindowMenu, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegion(GWindowMenu paramGWindowMenu, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowMenuBarItem paramGWindowMenuBarItem) {
  }
  public GSize getMinSize(GWindowMenuBarItem paramGWindowMenuBarItem, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegion(GWindowMenuBarItem paramGWindowMenuBarItem, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowMenuBar paramGWindowMenuBar) {
  }
  public GSize getMinSize(GWindowMenuBar paramGWindowMenuBar, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegion(GWindowMenuBar paramGWindowMenuBar, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void render(GWindowStatusBar paramGWindowStatusBar) {
  }
  public GSize getMinSize(GWindowStatusBar paramGWindowStatusBar, GSize paramGSize) { return paramGSize; } 
  public GRegion getClientRegion(GWindowStatusBar paramGWindowStatusBar, GRegion paramGRegion, float paramFloat) { return paramGRegion; } 
  public void resolutionChanged(GWindowRoot paramGWindowRoot) {
  }
  public void init(GWindowRoot paramGWindowRoot) {
  }
  public String i18n(String paramString) {
    if (paramString == null) return paramString;
    if (paramString.indexOf('_') < 0) return paramString;
    return paramString.replace('_', ' ');
  }
}