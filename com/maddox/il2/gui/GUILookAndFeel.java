package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GColor;
import com.maddox.gwindow.GCursorTexRegion;
import com.maddox.gwindow.GFont;
import com.maddox.gwindow.GPoint;
import com.maddox.gwindow.GRegion;
import com.maddox.gwindow.GSize;
import com.maddox.gwindow.GTexRegion;
import com.maddox.gwindow.GTexture;
import com.maddox.gwindow.GWin95LookAndFeel;
import com.maddox.gwindow.GWindow;
import com.maddox.gwindow.GWindowButton;
import com.maddox.gwindow.GWindowButtonTexture;
import com.maddox.gwindow.GWindowClient;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowComboControl.ListArea;
import com.maddox.gwindow.GWindowEditBox;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFrameCloseBox;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHScrollBar;
import com.maddox.gwindow.GWindowHScrollBar.LButton;
import com.maddox.gwindow.GWindowHScrollBar.MButton;
import com.maddox.gwindow.GWindowHScrollBar.RButton;
import com.maddox.gwindow.GWindowMenuBar;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowTable;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.gwindow.GWindowVScrollBar.DButton;
import com.maddox.gwindow.GWindowVScrollBar.MButton;
import com.maddox.gwindow.GWindowVScrollBar.UButton;
import com.maddox.il2.game.I18N;
import com.maddox.sound.Sample;
import com.maddox.sound.SoundFX;
import com.maddox.sound.SoundPreset;
import java.util.HashMap;

public class GUILookAndFeel extends GWin95LookAndFeel
{
  public GTexture buttons;
  public GTexture buttons2;
  public GTexture basicelements;
  public GBevel bevelGreen;
  public GBevel bevelRed;
  public GBevel bevelBlacked;
  public GBevel bevelComboDown;
  public GBevel bevelComboUp;
  public GBevel bevelButtonDOWN;
  public GBevel bevelButtonUP;
  public GBevel bevelAward;
  protected Sample sndKey1;
  protected Sample sndKey2;
  protected SoundFX guiSound;
  public HashMap sounds;
  public GTexRegion CBButtonUP;
  public GTexRegion CBButtonDOWN;
  float spaceComboList;
  float minScrollMSize;
  float spaceButton;
  float spaceFramedTitle;
  private GRegion _titleRegion;

  public GUILookAndFeel()
  {
    this.sndKey1 = new Sample("key_01.wav");
    this.sndKey2 = new Sample("key_02.wav");
    this.guiSound = new SoundFX(new SoundPreset("interface"));

    this.sounds = new HashMap();

    this.sounds.put("comboShow", this.sndKey1);
    this.sounds.put("comboHide", this.sndKey2);
    this.sounds.put("clickCheckBox", this.sndKey2);
    this.sounds.put("clickButton", this.sndKey1);
    this.sounds.put("clickSwitch", this.sndKey2);

    this.spaceComboList = 3.0F;

    this.minScrollMSize = 0.5F;

    this.spaceButton = 0.0F;

    this.spaceFramedTitle = 0.25F;

    this._titleRegion = new GRegion();
  }

  public void soundPlay(String paramString)
  {
    if (this.bSoundEnable)
      this.guiSound.play((Sample)this.sounds.get(paramString));
  }

  public void drawSeparateH(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    GUISeparate.draw(paramGWindow, GColor.Gray, paramFloat1, paramFloat2, paramFloat3, 2.0F);
  }
  public void drawSeparateW(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
    GUISeparate.draw(paramGWindow, GColor.Gray, paramFloat1, paramFloat2, 2.0F, paramFloat3);
  }

  public void render(GWindowTable paramGWindowTable)
  {
    paramGWindowTable.setCanvasColorWHITE();
    drawBevel(paramGWindowTable, 0.0F, 0.0F, paramGWindowTable.win.dx, paramGWindowTable.win.dy, this.bevelComboDown, this.basicelements, true);
    GRegion localGRegion = paramGWindowTable.root.C.clip;
    localGRegion.x += this.bevelComboDown.L.dx;
    localGRegion.y += this.bevelComboDown.T.dy;
    localGRegion.dx -= this.bevelComboDown.L.dx + this.bevelComboDown.R.dx;
    localGRegion.dy -= this.bevelComboDown.T.dy + this.bevelComboDown.B.dy;
  }

  public GRegion getClientRegion(GWindowTable paramGWindowTable, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (this.bevelComboDown.L.dx + paramFloat);
    paramGRegion.y = (this.bevelComboDown.T.dy + paramFloat);
    paramGRegion.dx = (paramGWindowTable.win.dx - paramGRegion.x - this.bevelComboDown.R.dx - paramFloat);
    paramGRegion.dy = (paramGWindowTable.win.dy - paramGRegion.y - this.bevelComboDown.B.dy - paramFloat);
    return paramGRegion;
  }

  public void render(GWindowEditControl paramGWindowEditControl)
  {
    paramGWindowEditControl.setCanvasColorWHITE();
    drawBevel(paramGWindowEditControl, 0.0F, 0.0F, paramGWindowEditControl.win.dx, paramGWindowEditControl.win.dy, this.bevelComboDown, this.basicelements, true);
    render(paramGWindowEditControl, this.bevelComboDown.L.dx);
  }

  public float xComboListItem(int paramInt, float paramFloat1, float paramFloat2)
  {
    if ((paramInt == 0) || (paramFloat2 >= paramFloat1))
      return 0.0F;
    if (paramInt == 2)
      return paramFloat1 - paramFloat2;
    if (paramInt == 1) {
      return (paramFloat1 - paramFloat2) / 2.0F;
    }
    return 0.0F;
  }

  public void renderComboList(GWindowComboControl paramGWindowComboControl)
  {
    GWindowComboControl.ListArea localListArea = paramGWindowComboControl.listArea;
    int i = localListArea.root.C.alpha; localListArea.root.C.alpha = 255;
    localListArea.setCanvasColorWHITE();

    drawBevel(localListArea, 0.0F, 0.0F, localListArea.win.dx, localListArea.win.dy, this.bevelComboUp, this.basicelements);
    GRegion localGRegion = localListArea.getClientRegion();
    localGRegion.x += this.bevelComboUp.L.dx + this.spaceComboList;
    localGRegion.y += this.bevelComboUp.T.dy;
    localGRegion.dx -= this.bevelComboUp.L.dx + this.bevelComboUp.R.dx + 2.0F * this.spaceComboList;
    localGRegion.dy -= this.bevelComboUp.T.dy + this.bevelComboUp.B.dy;

    if (localListArea.pushClipRegion(localGRegion, true, 0.0F)) {
      localListArea.setCanvasColorBLACK();
      localListArea.setCanvasFont(paramGWindowComboControl.font);
      GFont localGFont = paramGWindowComboControl.root.C.font;
      float f = (getComboHline() - localGFont.height) / 2.0F;
      int j = paramGWindowComboControl.listStartLine;
      for (int k = 0; k < paramGWindowComboControl.listCountLines; k++) {
        GSize localGSize = localGFont.size(paramGWindowComboControl.get(j));
        if (j == paramGWindowComboControl.listSelected) {
          localListArea.draw(0.0F, f, localListArea.win.dx, localGSize.dy, this.elements, 4.0F, 98.0F, 1.0F, 1.0F);
          localListArea.setCanvasColorWHITE();
          localListArea.draw(xComboListItem(paramGWindowComboControl.align, localGRegion.dx, localGSize.dx), f, paramGWindowComboControl.get(j));
          localListArea.setCanvasColorBLACK();
        } else if ((paramGWindowComboControl.posEnable != null) && (paramGWindowComboControl.posEnable[j] == 0)) {
          localListArea.setCanvasColorWHITE();
          localListArea.draw(0.0F, f, localListArea.win.dx, localGSize.dy, this.elements, 2.0F, 16.0F, 1.0F, 1.0F);
          localListArea.draw(xComboListItem(paramGWindowComboControl.align, localGRegion.dx, localGSize.dx) + 1.0F, f + 1.0F, paramGWindowComboControl.get(j));
          localListArea.setCanvasColor(8355711);
          localListArea.draw(xComboListItem(paramGWindowComboControl.align, localGRegion.dx, localGSize.dx), f, paramGWindowComboControl.get(j));
          localListArea.setCanvasColorBLACK();
        } else {
          localListArea.draw(xComboListItem(paramGWindowComboControl.align, localGRegion.dx, localGSize.dx), f, paramGWindowComboControl.get(j));
        }
        j++;
        f += getComboHline();
      }
      localListArea.popClip();
    }
    localListArea.root.C.alpha = i;
  }
  public void setupComboList(GWindowComboControl paramGWindowComboControl) {
    paramGWindowComboControl.listArea.win.dx = paramGWindowComboControl.win.dx;
    paramGWindowComboControl.listCountLines = paramGWindowComboControl.listVisibleLines;
    if (paramGWindowComboControl.listCountLines > paramGWindowComboControl.size())
      paramGWindowComboControl.listCountLines = paramGWindowComboControl.size();
    paramGWindowComboControl.listArea.win.dy = (paramGWindowComboControl.listCountLines * getComboHline() + this.bevelComboUp.B.dy + this.bevelComboUp.T.dy);

    GPoint localGPoint = paramGWindowComboControl.windowToGlobal(0.0F, paramGWindowComboControl.win.dy);
    if (localGPoint.y + paramGWindowComboControl.listArea.win.dy > paramGWindowComboControl.root.win.dy)
      paramGWindowComboControl.listArea.win.y = (-paramGWindowComboControl.listArea.win.dy);
    else
      paramGWindowComboControl.listArea.win.y = paramGWindowComboControl.win.dy;
    paramGWindowComboControl.listArea.win.x = 0.0F;
    if (paramGWindowComboControl.listCountLines < paramGWindowComboControl.size()) {
      paramGWindowComboControl.scrollBar.win.dx = getVScrollBarW();
      paramGWindowComboControl.scrollBar.win.dy = paramGWindowComboControl.listArea.win.dy;
      paramGWindowComboControl.scrollBar.win.x = (paramGWindowComboControl.listArea.win.dx - paramGWindowComboControl.scrollBar.win.dx);
      paramGWindowComboControl.scrollBar.win.y = 0.0F;
      if (paramGWindowComboControl.iSelected >= 0) {
        paramGWindowComboControl.listStartLine = paramGWindowComboControl.iSelected;
        if (paramGWindowComboControl.listStartLine + paramGWindowComboControl.listCountLines > paramGWindowComboControl.size())
          paramGWindowComboControl.listStartLine = (paramGWindowComboControl.size() - paramGWindowComboControl.listCountLines);
      } else {
        paramGWindowComboControl.listStartLine = 0;
      }
      paramGWindowComboControl.scrollBar.setRange(0.0F, paramGWindowComboControl.size(), paramGWindowComboControl.listCountLines, 1.0F, paramGWindowComboControl.listStartLine);
      paramGWindowComboControl.scrollBar.showWindow();
    }
    else {
      paramGWindowComboControl.scrollBar.hideWindow();
      paramGWindowComboControl.listStartLine = 0;
    }
    paramGWindowComboControl.listSelected = paramGWindowComboControl.iSelected;

    paramGWindowComboControl.listArea.win.dx -= getVScrollBarW();
  }

  public void render(GWindowComboControl paramGWindowComboControl) {
    int i = paramGWindowComboControl.root.C.alpha; paramGWindowComboControl.root.C.alpha = 255;
    paramGWindowComboControl.setCanvasColorWHITE();

    drawBevel(paramGWindowComboControl, 0.0F, 0.0F, paramGWindowComboControl.win.dx - getVScrollBarW(), paramGWindowComboControl.win.dy, this.bevelComboDown, this.basicelements);
    paramGWindowComboControl.root.C.alpha = i;
  }

  public void setupComboEditBox(GWindowEditBox paramGWindowEditBox) {
    paramGWindowEditBox.win.x = this.bevelComboDown.L.dx;
    paramGWindowEditBox.win.y = this.bevelComboDown.T.dy;
    paramGWindowEditBox.win.dx = (paramGWindowEditBox.parentWindow.win.dx - this.bevelComboDown.L.dx - this.bevelComboDown.R.dx - getVScrollBarW());
    paramGWindowEditBox.win.dy = (paramGWindowEditBox.parentWindow.win.dy - this.bevelComboDown.T.dy - this.bevelComboDown.B.dy);
  }

  public void setupComboButton(GWindowButtonTexture paramGWindowButtonTexture) {
    paramGWindowButtonTexture.texUP = this.CBButtonUP;
    paramGWindowButtonTexture.texDOWN = this.CBButtonDOWN;
    paramGWindowButtonTexture.texDISABLE = this.CBButtonUP;
    paramGWindowButtonTexture.texOVER = this.CBButtonUP;
    paramGWindowButtonTexture.win.dx = getVScrollBarW();
    paramGWindowButtonTexture.win.dy = getVScrollBarH();
    paramGWindowButtonTexture.win.x = (paramGWindowButtonTexture.parentWindow.win.dx - paramGWindowButtonTexture.win.dx);
    paramGWindowButtonTexture.win.y = 0.0F;
  }
  public float getComboH() {
    return getVScrollBarH() + this.bevelComboDown.B.dy + this.bevelComboDown.T.dy;
  }
  public float getComboHmetric() { return getComboH() / metric(); } 
  public float getComboHline() { return metric(1.2F);
  }

  public void render(GWindowVScrollBar paramGWindowVScrollBar)
  {
    float f1 = paramGWindowVScrollBar.yM - paramGWindowVScrollBar.uButton.win.dy;
    float f2 = paramGWindowVScrollBar.dButton.win.y - paramGWindowVScrollBar.yM - paramGWindowVScrollBar.dyM;
    if ((f1 < 0.0F) && (f2 < 0.0F)) return;
    int i = paramGWindowVScrollBar.root.C.alpha; paramGWindowVScrollBar.root.C.alpha = 255;
    paramGWindowVScrollBar.setCanvasColorWHITE();
    paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.uButton.win.dy, paramGWindowVScrollBar.win.dx, paramGWindowVScrollBar.win.dy - paramGWindowVScrollBar.uButton.win.dy - paramGWindowVScrollBar.dButton.win.dy, this.buttons, 0.0F, 143.0F, 32.0F, 2.0F);
    paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.yM, paramGWindowVScrollBar.win.dx, paramGWindowVScrollBar.dyM, this.buttons, 192.0F, 112.0F, 32.0F, 64.0F);
    paramGWindowVScrollBar.root.C.alpha = i;
  }

  public void setupVScrollBarSizes(GWindowVScrollBar paramGWindowVScrollBar) {
    float f1 = paramGWindowVScrollBar.win.dy - 2.0F * getVScrollBarH();
    int i = f1 >= 2.0F * metric(this.minScrollMSize) ? 1 : 0;
    float f2 = getVScrollBarH();
    if (f1 <= 0.0F) {
      f2 = paramGWindowVScrollBar.win.dy / 2.0F;
    }
    paramGWindowVScrollBar.uButton.setSize(getVScrollBarW(), f2);
    paramGWindowVScrollBar.uButton.setPos(0.0F, 0.0F);
    paramGWindowVScrollBar.dButton.setSize(getVScrollBarW(), f2);
    paramGWindowVScrollBar.dButton.setPos(0.0F, paramGWindowVScrollBar.win.dy - f2);

    float f3 = 2.0F * f2;
    if (f3 > f1) {
      paramGWindowVScrollBar.dyM = 0.0F;
      paramGWindowVScrollBar.yM = (paramGWindowVScrollBar.pos / (paramGWindowVScrollBar.posMax - paramGWindowVScrollBar.posMin) * (paramGWindowVScrollBar.win.dy - f2));
      if (paramGWindowVScrollBar.yM < 0.0F) paramGWindowVScrollBar.yM = (-paramGWindowVScrollBar.yM);
      paramGWindowVScrollBar.mButton.hideWindow();
    } else {
      paramGWindowVScrollBar.dyM = (2.0F * f2);
      paramGWindowVScrollBar.yM = (paramGWindowVScrollBar.pos / (paramGWindowVScrollBar.posMax - paramGWindowVScrollBar.posMin) * (paramGWindowVScrollBar.win.dy - 4.0F * f2));
      if (paramGWindowVScrollBar.yM < 0.0F) paramGWindowVScrollBar.yM = (-paramGWindowVScrollBar.yM);
      paramGWindowVScrollBar.yM += f2;
      paramGWindowVScrollBar.mButton.showWindow();
      paramGWindowVScrollBar.mButton.setSize(getVScrollBarW(), 2.0F * f2);
      paramGWindowVScrollBar.mButton.setPos(0.0F, paramGWindowVScrollBar.yM);
      paramGWindowVScrollBar.mButton._bNoRender = true;
    }
  }

  public void render(GWindowHScrollBar paramGWindowHScrollBar) {
    float f1 = paramGWindowHScrollBar.xM - paramGWindowHScrollBar.lButton.win.dx;
    float f2 = paramGWindowHScrollBar.rButton.win.x - paramGWindowHScrollBar.xM - paramGWindowHScrollBar.dxM;
    if ((f1 < 0.0F) && (f2 < 0.0F)) return;
    int i = paramGWindowHScrollBar.root.C.alpha; paramGWindowHScrollBar.root.C.alpha = 255;
    paramGWindowHScrollBar.setCanvasColorWHITE();
    paramGWindowHScrollBar.draw(paramGWindowHScrollBar.lButton.win.dx, 0.0F, paramGWindowHScrollBar.win.dx - paramGWindowHScrollBar.lButton.win.dx - paramGWindowHScrollBar.rButton.win.dx, paramGWindowHScrollBar.win.dy, this.buttons, 95.0F, 144.0F, 1.0F, 32.0F);
    paramGWindowHScrollBar.draw(paramGWindowHScrollBar.xM, 0.0F, paramGWindowHScrollBar.dxM, paramGWindowHScrollBar.win.dy, this.buttons, 128.0F, 112.0F, 64.0F, 32.0F);
    paramGWindowHScrollBar.root.C.alpha = i;
  }

  public void setupHScrollBarSizes(GWindowHScrollBar paramGWindowHScrollBar) {
    float f1 = paramGWindowHScrollBar.win.dx - 2.0F * getHScrollBarW();
    int i = f1 >= 2.0F * metric(this.minScrollMSize) ? 1 : 0;
    float f2 = getHScrollBarW();
    if (f1 <= 0.0F) {
      f2 = paramGWindowHScrollBar.win.dx / 2.0F;
    }
    paramGWindowHScrollBar.lButton.setSize(f2, getHScrollBarH());
    paramGWindowHScrollBar.lButton.setPos(0.0F, 0.0F);
    paramGWindowHScrollBar.rButton.setSize(f2, getHScrollBarH());
    paramGWindowHScrollBar.rButton.setPos(paramGWindowHScrollBar.win.dx - f2, 0.0F);
    paramGWindowHScrollBar.mButton._bNoRender = true;

    paramGWindowHScrollBar.dxM = (2.0F * f2);
    paramGWindowHScrollBar.xM = (paramGWindowHScrollBar.pos / (paramGWindowHScrollBar.posMax - paramGWindowHScrollBar.posMin) * (paramGWindowHScrollBar.win.dx - 4.0F * f2));
    if (paramGWindowHScrollBar.xM < 0.0F) paramGWindowHScrollBar.xM = (-paramGWindowHScrollBar.xM);
    paramGWindowHScrollBar.xM += f2;
    paramGWindowHScrollBar.mButton.setSize(2.0F * f2, getHScrollBarH());
    paramGWindowHScrollBar.mButton.setPos(paramGWindowHScrollBar.xM, 0.0F);
    paramGWindowHScrollBar.mButton._bNoRender = true;
  }
  public float getHScrollBarW() {
    return metric(1.6F); } 
  public float getHScrollBarH() { return metric(1.6F); } 
  public float getVScrollBarW() { return metric(1.6F); } 
  public float getVScrollBarH() { return metric(1.6F); } 
  public void setupScrollButtonUP(GWindowButtonTexture paramGWindowButtonTexture) {
    paramGWindowButtonTexture.texUP = this.SBupButtonUP;
    paramGWindowButtonTexture.texDOWN = this.SBupButtonDOWN;
    paramGWindowButtonTexture.texDISABLE = this.SBupButtonDISABLE;
    paramGWindowButtonTexture.texOVER = this.SBupButtonOVER;
  }
  public void setupScrollButtonDOWN(GWindowButtonTexture paramGWindowButtonTexture) {
    paramGWindowButtonTexture.texUP = this.SBdownButtonUP;
    paramGWindowButtonTexture.texDOWN = this.SBdownButtonDOWN;
    paramGWindowButtonTexture.texDISABLE = this.SBdownButtonDISABLE;
    paramGWindowButtonTexture.texOVER = this.SBdownButtonOVER;
  }
  public void setupScrollButtonLEFT(GWindowButtonTexture paramGWindowButtonTexture) {
    paramGWindowButtonTexture.texUP = this.SBleftButtonUP;
    paramGWindowButtonTexture.texDOWN = this.SBleftButtonDOWN;
    paramGWindowButtonTexture.texDISABLE = this.SBleftButtonDISABLE;
    paramGWindowButtonTexture.texOVER = this.SBleftButtonOVER;
  }
  public void setupScrollButtonRIGHT(GWindowButtonTexture paramGWindowButtonTexture) {
    paramGWindowButtonTexture.texUP = this.SBrightButtonUP;
    paramGWindowButtonTexture.texDOWN = this.SBrightButtonDOWN;
    paramGWindowButtonTexture.texDISABLE = this.SBrightButtonDISABLE;
    paramGWindowButtonTexture.texOVER = this.SBrightButtonOVER;
  }

  public void render(GWindowButton paramGWindowButton)
  {
    int i = paramGWindowButton.root.C.alpha; paramGWindowButton.root.C.alpha = 255;
    paramGWindowButton.setCanvasColorWHITE();
    GRegion localGRegion;
    float f1;
    float f2;
    if (!paramGWindowButton.bEnable) {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.win.dx, paramGWindowButton.win.dy, this.bevelButtonUP, this.basicelements);
      if (paramGWindowButton.cap != null) {
        localGRegion = paramGWindowButton.getClientRegion();
        f1 = localGRegion.dx;
        f2 = localGRegion.dy;
        if (paramGWindowButton.pushClipRegion(localGRegion, paramGWindowButton.bClip, 0.0F)) {
          renderTextDialogControl(paramGWindowButton, 1.0F, 1.0F, f1, f2, 16777215, false);
          renderTextDialogControl(paramGWindowButton, 0.0F, 0.0F, f1, f2, 8355711, false);
          paramGWindowButton.popClip();
        }
      }
      paramGWindowButton.root.C.alpha = i;
      return;
    }
    if ((paramGWindowButton.bDown) && (!paramGWindowButton.bDrawOnlyUP))
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.win.dx, paramGWindowButton.win.dy, this.bevelButtonDOWN, this.basicelements);
    else {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.win.dx, paramGWindowButton.win.dy, this.bevelButtonUP, this.basicelements);
    }

    if (paramGWindowButton.cap != null) {
      localGRegion = paramGWindowButton.getClientRegion();
      f1 = localGRegion.dx;
      f2 = localGRegion.dy;
      if (paramGWindowButton.pushClipRegion(localGRegion, paramGWindowButton.bClip, 0.0F)) {
        if (paramGWindowButton.bDown) renderTextDialogControl(paramGWindowButton, 1.0F, 1.0F, f1, f2, paramGWindowButton.color, paramGWindowButton.isDefault()); else
          renderTextDialogControl(paramGWindowButton, 0.0F, 0.0F, f1, f2, paramGWindowButton.color, paramGWindowButton.isDefault());
        paramGWindowButton.popClip();
      }
    }
    paramGWindowButton.root.C.alpha = i;
  }

  public void render(GWindowButtonTexture paramGWindowButtonTexture) {
    int i = paramGWindowButtonTexture.root.C.alpha; paramGWindowButtonTexture.root.C.alpha = 255;
    super.render(paramGWindowButtonTexture);
    paramGWindowButtonTexture.root.C.alpha = i;
  }

  public GSize getMinSize(GWindowButton paramGWindowButton, GSize paramGSize) {
    paramGSize = getMinSizeDialogControl(paramGWindowButton, paramGSize);
    paramGSize.dx += this.bevelButtonUP.L.dx / 2.0F + this.bevelButtonUP.R.dx / 2.0F + 2.0F * metric(this.spaceButton);
    paramGSize.dy += this.bevelButtonUP.T.dy / 2.0F + this.bevelButtonUP.B.dy / 2.0F + 2.0F * metric(this.spaceButton);
    return paramGSize;
  }

  public GRegion getClientRegion(GWindowButton paramGWindowButton, GRegion paramGRegion, float paramFloat) {
    paramGRegion = getClientRegionDialogControl(paramGWindowButton, paramGRegion, paramFloat);
    paramGRegion.x += this.bevelButtonUP.L.dx / 2.0F;
    paramGRegion.y += this.bevelButtonUP.T.dy / 2.0F;
    paramGRegion.dx -= this.bevelButtonUP.L.dx / 2.0F + this.bevelButtonUP.R.dx / 2.0F;
    paramGRegion.dy -= this.bevelButtonUP.T.dy / 2.0F + this.bevelButtonUP.B.dy / 2.0F;
    return paramGRegion;
  }

  public void render(GWindowClient paramGWindowClient)
  {
    paramGWindowClient.setCanvasColorWHITE();
    paramGWindowClient.draw(0.0F, 0.0F, paramGWindowClient.win.dx, paramGWindowClient.win.dy, this.basicelements, this.bevelComboUp.Area);
  }

  public void setupFrameCloseBox(GWindowFrameCloseBox paramGWindowFrameCloseBox)
  {
    paramGWindowFrameCloseBox.texUP = this.closeBoxUP;
    paramGWindowFrameCloseBox.texDOWN = this.closeBoxDOWN;
    paramGWindowFrameCloseBox.texDISABLE = this.closeBoxDISABLE;
    paramGWindowFrameCloseBox.texOVER = this.closeBoxOVER;
  }

  public void frameSetCloseBoxPos(GWindowFramed paramGWindowFramed) {
    paramGWindowFramed.closeBox.hideWindow();
  }

  public int frameHitTest(GWindowFramed paramGWindowFramed, float paramFloat1, float paramFloat2)
  {
    GRegion localGRegion = paramGWindowFramed.getClientRegion();
    if ((paramFloat1 < 0.0F) || (paramFloat1 > paramGWindowFramed.win.dx) || (paramFloat2 < 0.0F) || (paramFloat2 > paramGWindowFramed.win.dy))
    {
      return 0;
    }if ((paramFloat1 >= localGRegion.x) && (paramFloat1 < localGRegion.x + localGRegion.dx) && (paramFloat2 >= localGRegion.y) && (paramFloat2 < localGRegion.y + localGRegion.dy))
    {
      return 0;
    }if (paramFloat1 < localGRegion.x) {
      if (paramFloat2 < this.bevelButtonUP.T.dy) return 1;
      if (paramFloat2 >= localGRegion.y + localGRegion.dy) return 6;
      return 4;
    }if (paramFloat1 >= localGRegion.x + localGRegion.dx) {
      if (paramFloat2 < this.bevelButtonUP.T.dy) return 3;
      if (paramFloat2 >= localGRegion.y + localGRegion.dy) return 8;
      return 5;
    }
    if (paramFloat2 < this.bevelButtonUP.T.dy) return 2;
    if (paramFloat2 >= localGRegion.y + localGRegion.dy) return 7;
    return 9;
  }

  public void render(GWindowFramed paramGWindowFramed)
  {
    float f = framedTitleHeight(paramGWindowFramed);
    paramGWindowFramed.setCanvasColorWHITE();
    drawBevel(paramGWindowFramed, 0.0F, f, paramGWindowFramed.win.dx, paramGWindowFramed.win.dy - f, this.bevelComboUp, this.basicelements, false);

    GBevel localGBevel = this.bevelButtonUP;

    drawBevel(paramGWindowFramed, 0.0F, 0.0F, paramGWindowFramed.win.dx, f, localGBevel, this.basicelements);
    if (paramGWindowFramed.title != null) {
      if (!paramGWindowFramed.isActivated()) paramGWindowFramed.setCanvasColor(12632256);
      paramGWindowFramed.setCanvasFont(1);
      this._titleRegion.x = this.bevelButtonUP.L.dx;
      this._titleRegion.y = this.bevelButtonUP.T.dy;
      if (paramGWindowFramed.closeBox != null)
        this._titleRegion.dx = (paramGWindowFramed.win.dx - this.bevelButtonUP.L.dx - this.bevelButtonUP.R.dx - metric());
      else
        this._titleRegion.dx = (paramGWindowFramed.win.dx - this.bevelButtonUP.L.dx - this.bevelButtonUP.R.dx);
      this._titleRegion.dy = (f - this.bevelButtonUP.T.dy - this.bevelButtonUP.B.dy);
      if (paramGWindowFramed.pushClipRegion(this._titleRegion, paramGWindowFramed.bClip, metric(this.spaceFramedTitle))) {
        paramGWindowFramed.draw(0.0F, 0.0F, paramGWindowFramed.title);
        paramGWindowFramed.popClip();
      }
    }
  }

  private float framedTitleHeight(GWindowFramed paramGWindowFramed)
  {
    float f = metric();
    if (f > paramGWindowFramed.root.textFonts[1].height)
      f = paramGWindowFramed.root.textFonts[1].height;
    if ((paramGWindowFramed.title != null) && (paramGWindowFramed.title.length() > 0)) {
      return f + 2.0F * metric(this.spaceFramedTitle) + this.bevelButtonUP.T.dy + this.bevelButtonUP.B.dy;
    }
    return this.bevelButtonUP.T.dy + this.bevelButtonUP.B.dy;
  }

  public GSize getMinSize(GWindowFramed paramGWindowFramed, GSize paramGSize) {
    float f1 = 50.0F;
    float f2 = 50.0F;
    GSize localGSize;
    if (paramGWindowFramed.clientWindow != null) {
      localGSize = paramGWindowFramed.clientWindow.getMinSize();
      f1 = localGSize.dx; f2 = localGSize.dy;
    }
    if (paramGWindowFramed.menuBar != null) {
      localGSize = paramGWindowFramed.menuBar.getMinSize();
      f2 += localGSize.dy;
    }
    f1 += this.bevelComboUp.L.dx + this.bevelComboUp.R.dx;
    f2 += this.bevelComboUp.T.dy + this.bevelComboUp.B.dy + framedTitleHeight(paramGWindowFramed);
    paramGSize.set(f1, f2);
    return paramGSize;
  }

  public GRegion getClientRegion(GWindowFramed paramGWindowFramed, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (this.bevelComboUp.L.dx + paramFloat);
    paramGRegion.y = (framedTitleHeight(paramGWindowFramed) + this.bevelFW.T.dy + paramFloat);
    if (paramGWindowFramed.menuBar != null)
      paramGRegion.y += paramGWindowFramed.menuBar.win.dy;
    paramGRegion.dx = (paramGWindowFramed.win.dx - paramGRegion.x - this.bevelComboUp.R.dx - paramFloat);
    paramGRegion.dy = (paramGWindowFramed.win.dy - paramGRegion.y - this.bevelComboUp.B.dy - paramFloat);
    return paramGRegion;
  }

  public String i18n(String paramString)
  {
    return I18N.gwindow(paramString);
  }

  public void init(GWindowRoot paramGWindowRoot) {
    this.buttons = GTexture.New("GUI/game/buttons.mat");
    this.buttons2 = GTexture.New("GUI/game/buttons2.mat");

    this.basicelements = GTexture.New("GUI/game/basicelements.mat");
    this.bevelGreen = new GBevel();
    this.bevelGreen.set(new GRegion(160.0F, 192.0F, 64.0F, 64.0F), new GRegion(191.0F, 223.0F, 2.0F, 2.0F));
    this.bevelRed = new GBevel();
    this.bevelRed.set(new GRegion(64.0F, 192.0F, 32.0F, 32.0F), new GRegion(69.0F, 197.0F, 22.0F, 22.0F));
    this.bevelBlacked = new GBevel();
    this.bevelBlacked.set(new GRegion(32.0F, 192.0F, 32.0F, 32.0F), new GRegion(41.0F, 201.0F, 14.0F, 14.0F));
    this.bevelComboUp = new GBevel();
    this.bevelComboUp.set(new GRegion(32.0F, 224.0F, 16.0F, 16.0F), new GRegion(36.0F, 228.0F, 8.0F, 8.0F));
    this.bevelComboDown = new GBevel();
    this.bevelComboDown.set(new GRegion(32.0F, 240.0F, 16.0F, 16.0F), new GRegion(35.0F, 243.0F, 10.0F, 10.0F));
    this.bevelButtonUP = new GBevel();
    this.bevelButtonUP.set(new GRegion(160.0F, 160.0F, 32.0F, 32.0F), new GRegion(172.0F, 172.0F, 8.0F, 8.0F));
    this.bevelButtonDOWN = new GBevel();
    this.bevelButtonDOWN.set(new GRegion(192.0F, 160.0F, 32.0F, 32.0F), new GRegion(204.0F, 172.0F, 8.0F, 8.0F));
    this.bevelAward = new GBevel();
    this.bevelAward.set(new GRegion(160.0F, 160.0F, 32.0F, 32.0F), new GRegion(165.0F, 166.0F, 21.0F, 20.0F));

    this.elements = GTexture.New("GUI/game/elements.mat");
    this.elementsStreched = GTexture.New("GUI/game/elementss.mat");
    this.cursors = GTexture.New("GUI/game/cursors.mat");
    this.cursorsStreched = GTexture.New("GUI/game/cursorss.mat");
    this.regionWhite = new GTexRegion(this.elements, 5.0F, 17.0F, 1.0F, 1.0F);
    this.bevelUP.set(new GRegion(4.0F, 16.0F, 16.0F, 16.0F), new GRegion(6.0F, 18.0F, 12.0F, 12.0F));

    this.bevelTabDialogClient = this.bevelUP;
    this.bevelDOWN.set(new GRegion(52.0F, 37.0F, 16.0F, 16.0F), new GRegion(54.0F, 39.0F, 12.0F, 12.0F));

    this.bevelUPsmall.set(new GRegion(19.0F, 60.0F, 11.0F, 10.0F), new GRegion(20.0F, 61.0F, 9.0F, 8.0F));
    this.bevelDOWNsmall.set(new GRegion(19.0F, 71.0F, 10.0F, 9.0F), new GRegion(20.0F, 72.0F, 8.0F, 7.0F));
    this.bevelFW.set(new GRegion(0.0F, 16.0F, 128.0F, 112.0F), new GRegion(4.0F, 16.0F, 120.0F, 108.0F));
    this.bevelTitleActive.set(new GRegion(0.0F, 0.0F, 128.0F, 16.0F), new GRegion(4.0F, 4.0F, 120.0F, 11.0F));
    this.bevelTitleInactive.set(new GRegion(70.0F, 18.0F, 53.0F, 16.0F), new GRegion(74.0F, 22.0F, 45.0F, 11.0F));
    this.bevelFW.Area.dx = 0.0F;
    this.bevelBlack.set(new GRegion(4.0F, 98.0F, 4.0F, 4.0F), new GRegion(5.0F, 99.0F, 2.0F, 2.0F));
    this.bevelBlack.Area.dx = 0.0F;
    this.bevelBlack50.set(new GRegion(32.0F, 16.0F, 12.0F, 10.0F), new GRegion(33.0F, 17.0F, 10.0F, 8.0F));
    this.bevelBlack50.Area.dx = 0.0F;
    this.bevelSeparate.set(new GRegion(87.0F, 69.0F, 8.0F, 7.0F), new GRegion(89.0F, 71.0F, 4.0F, 3.0F));
    this.bevelSeparate.Area.dx = 0.0F;

    this.bevelTabCUR.set(new GRegion(4.0F, 82.0F, 53.0F, 15.0F), new GRegion(8.0F, 86.0F, 45.0F, 8.0F));
    this.bevelTabCUR.Area.dx = 0.0F;
    this.bevelTab.set(new GRegion(57.0F, 82.0F, 54.0F, 13.0F), new GRegion(61.0F, 87.0F, 46.0F, 8.0F));
    this.bevelTab.Area.dx = 0.0F;

    this.closeBoxUP = new GTexRegion(this.elementsStreched, 4.0F, 32.0F, 10.0F, 9.0F);
    this.closeBoxDOWN = new GTexRegion(this.elementsStreched, 4.0F, 43.0F, 10.0F, 9.0F);
    this.closeBoxDISABLE = (this.closeBoxOVER = this.closeBoxUP);

    this.SBupButtonUP = new GTexRegion(this.buttons, 0.0F, 112.0F, 32.0F, 32.0F);
    this.SBupButtonDOWN = new GTexRegion(this.buttons, 32.0F, 112.0F, 32.0F, 32.0F);
    this.SBupButtonDISABLE = (this.SBupButtonOVER = this.SBupButtonUP);

    this.SBdownButtonUP = new GTexRegion(this.buttons, 0.0F, 144.0F, 32.0F, 32.0F);
    this.SBdownButtonDOWN = new GTexRegion(this.buttons, 32.0F, 144.0F, 32.0F, 32.0F);
    this.SBdownButtonDISABLE = (this.SBdownButtonOVER = this.SBdownButtonUP);

    this.SBleftButtonUP = new GTexRegion(this.buttons, 64.0F, 144.0F, 32.0F, 32.0F);
    this.SBleftButtonDOWN = new GTexRegion(this.buttons, 96.0F, 144.0F, 32.0F, 32.0F);
    this.SBleftButtonDISABLE = (this.SBleftButtonOVER = this.SBleftButtonUP);

    this.SBrightButtonUP = new GTexRegion(this.buttons, 64.0F, 112.0F, 32.0F, 32.0F);
    this.SBrightButtonDOWN = new GTexRegion(this.buttons, 96.0F, 112.0F, 32.0F, 32.0F);
    this.SBrightButtonDISABLE = (this.SBrightButtonOVER = this.SBrightButtonUP);

    this.CBButtonUP = new GTexRegion(this.buttons, 128.0F, 144.0F, 32.0F, 32.0F);
    this.CBButtonDOWN = new GTexRegion(this.buttons, 160.0F, 144.0F, 32.0F, 32.0F);

    this.checkBoxCheckEnable = new GTexRegion(this.elementsStreched, 32.0F, 64.0F, 13.0F, 13.0F);
    this.checkBoxCheckDisable = new GTexRegion(this.elementsStreched, 45.0F, 64.0F, 13.0F, 13.0F);
    this.checkBoxUnCheckEnable = new GTexRegion(this.elementsStreched, 58.0F, 64.0F, 13.0F, 13.0F);
    this.checkBoxUnCheckDisable = new GTexRegion(this.elementsStreched, 71.0F, 64.0F, 13.0F, 13.0F);

    paramGWindowRoot.textFonts[0] = GFont.New("arial10");
    paramGWindowRoot.textFonts[1] = GFont.New("arialb10");
    this.metric = (int)(paramGWindowRoot.textFonts[0].height + 0.5F);
    paramGWindowRoot.mouseCursors[0] = new GCursorTexRegion(this.cursors, 32.0F, 96.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0);
    paramGWindowRoot.mouseCursors[1] = new GCursorTexRegion(this.cursors, 0.0F, 0.0F, 32.0F, 32.0F, 5.0F, 4.0F, 1);
    paramGWindowRoot.mouseCursors[2] = new GCursorTexRegion(this.cursors, 32.0F, 0.0F, 32.0F, 32.0F, 15.0F, 15.0F, 2);
    paramGWindowRoot.mouseCursors[3] = new GCursorTexRegion(this.cursors, 64.0F, 0.0F, 32.0F, 32.0F, 5.0F, 4.0F, 3);
    paramGWindowRoot.mouseCursors[4] = new GCursorTexRegion(this.cursors, 96.0F, 0.0F, 32.0F, 32.0F, 5.0F, 4.0F, 4);
    paramGWindowRoot.mouseCursors[5] = new GCursorTexRegion(this.cursors, 0.0F, 32.0F, 32.0F, 32.0F, 14.0F, 15.0F, 5);
    paramGWindowRoot.mouseCursors[6] = new GCursorTexRegion(this.cursors, 32.0F, 32.0F, 32.0F, 32.0F, 5.0F, 4.0F, 6);
    paramGWindowRoot.mouseCursors[7] = new GCursorTexRegion(this.cursors, 64.0F, 32.0F, 32.0F, 32.0F, 5.0F, 4.0F, 7);
    paramGWindowRoot.mouseCursors[8] = new GCursorTexRegion(this.cursors, 96.0F, 32.0F, 32.0F, 32.0F, 5.0F, 4.0F, 8);
    paramGWindowRoot.mouseCursors[9] = new GCursorTexRegion(this.cursors, 0.0F, 64.0F, 32.0F, 32.0F, 5.0F, 4.0F, 9);
    paramGWindowRoot.mouseCursors[10] = new GCursorTexRegion(this.cursors, 32.0F, 64.0F, 32.0F, 32.0F, 5.0F, 4.0F, 10);
    paramGWindowRoot.mouseCursors[11] = new GCursorTexRegion(this.cursors, 64.0F, 64.0F, 32.0F, 32.0F, 5.0F, 4.0F, 11);
    paramGWindowRoot.mouseCursors[12] = new GCursorTexRegion(this.cursors, 96.0F, 64.0F, 32.0F, 32.0F, 5.0F, 4.0F, 12);
    paramGWindowRoot.mouseCursors[13] = new GCursorTexRegion(this.cursors, 0.0F, 96.0F, 32.0F, 32.0F, 5.0F, 4.0F, 13);

    this.selectMenuIcon = new GTexRegion(this.cursorsStreched, 116.0F, 104.0F, 12.0F, 12.0F);
    this.subMenuIcon = new GTexRegion(this.cursorsStreched, 116.0F, 116.0F, 12.0F, 12.0F);
  }
}