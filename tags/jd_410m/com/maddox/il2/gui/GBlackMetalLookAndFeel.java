package com.maddox.il2.gui;

import com.maddox.gwindow.GBevel;
import com.maddox.gwindow.GCanvas;
import com.maddox.gwindow.GCaption;
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
import com.maddox.gwindow.GWindowClient;
import com.maddox.gwindow.GWindowComboControl;
import com.maddox.gwindow.GWindowComboControl.ListArea;
import com.maddox.gwindow.GWindowEditBox;
import com.maddox.gwindow.GWindowEditControl;
import com.maddox.gwindow.GWindowFramed;
import com.maddox.gwindow.GWindowHScrollBar;
import com.maddox.gwindow.GWindowHScrollBar.LButton;
import com.maddox.gwindow.GWindowHScrollBar.RButton;
import com.maddox.gwindow.GWindowHSliderInt;
import com.maddox.gwindow.GWindowLabel;
import com.maddox.gwindow.GWindowMenu;
import com.maddox.gwindow.GWindowMenuBar;
import com.maddox.gwindow.GWindowMenuBarItem;
import com.maddox.gwindow.GWindowMenuItem;
import com.maddox.gwindow.GWindowRoot;
import com.maddox.gwindow.GWindowStatusBar;
import com.maddox.gwindow.GWindowTabDialogClient;
import com.maddox.gwindow.GWindowTabDialogClient.Tab;
import com.maddox.gwindow.GWindowVScrollBar;
import com.maddox.gwindow.GWindowVScrollBar.DButton;
import com.maddox.gwindow.GWindowVScrollBar.UButton;

public class GBlackMetalLookAndFeel extends GWin95LookAndFeel
{
  public GBevel bevelTabOVER = new GBevel();
  public GBevel bevelButtonUP = new GBevel();
  public GBevel bevelButtonOVER = new GBevel();
  public GBevel bevelButtonDOWN = new GBevel();
  public GBevel bevelMenu = new GBevel();

  float spaceMenuBarItem = 0.1666667F;

  float spaceMenuItem = 0.1666667F;

  private GRegion _titleRegion = new GRegion();
  float spaceFramedTitle = 0.25F;

  float spaceComboList = 3.0F;

  float spaceStatusBar = 0.08333334F;

  float spaceTab = 0.08333334F;
  GRegion tabReg = new GRegion();

  public void drawSeparateH(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    paramGWindow.setCanvasColorWHITE();
    paramGWindow.draw(paramFloat1, paramFloat2, paramFloat3, 2.0F, this.elements, 68.0F, 78.0F, 1.0F, 2.0F);
  }
  public void drawSeparateW(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
    paramGWindow.setCanvasColorWHITE();
    paramGWindow.draw(paramFloat1, paramFloat2, 2.0F, paramFloat3, this.elements, 78.0F, 68.0F, 2.0F, 1.0F);
  }

  public void render(GWindowButton paramGWindowButton)
  {
    paramGWindowButton.setCanvasColorWHITE();
    GRegion localGRegion;
    float f1;
    float f2;
    if (!paramGWindowButton.bEnable) {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.win.dx, paramGWindowButton.win.dy, this.bevelButtonUP, this.elementsStreched);
      if (paramGWindowButton.cap != null) {
        localGRegion = paramGWindowButton.getClientRegion();
        f1 = localGRegion.dx;
        f2 = localGRegion.dy;
        if (paramGWindowButton.pushClipRegion(localGRegion, paramGWindowButton.bClip, 0.0F)) {
          renderTextDialogControl(paramGWindowButton, 1.0F, 1.0F, f1, f2, 8355711, false);
          renderTextDialogControl(paramGWindowButton, 0.0F, 0.0F, f1, f2, 6391956, false);
          paramGWindowButton.popClip();
        }
      }
      return;
    }
    if ((paramGWindowButton.bDown) && (!paramGWindowButton.bDrawOnlyUP)) {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.win.dx, paramGWindowButton.win.dy, this.bevelButtonDOWN, this.elementsStreched);
    } else {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.win.dx, paramGWindowButton.win.dy, this.bevelButtonUP, this.elementsStreched);
      if (paramGWindowButton.isMouseOver()) {
        drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.win.dx, paramGWindowButton.win.dy, this.bevelButtonOVER, this.elementsStreched);
      }
    }
    if ((paramGWindowButton.bDrawActive) && (paramGWindowButton.isActivated())) {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.win.dx, paramGWindowButton.win.dy, this.bevelBlack, this.elements);
    }

    if (paramGWindowButton.cap != null) {
      localGRegion = paramGWindowButton.getClientRegion();
      f1 = localGRegion.dx;
      f2 = localGRegion.dy;
      if (paramGWindowButton.pushClipRegion(localGRegion, paramGWindowButton.bClip, 0.0F)) {
        if (paramGWindowButton.bDown) renderTextDialogControl(paramGWindowButton, 1.0F, 2.0F, f1, f2, 5298175, paramGWindowButton.isDefault()); else
          renderTextDialogControl(paramGWindowButton, 0.0F, 0.0F, f1, f2, 5298175, paramGWindowButton.isDefault());
        paramGWindowButton.popClip();
      }
    }
  }

  public void render(GWindowMenuBar paramGWindowMenuBar)
  {
    paramGWindowMenuBar.setCanvasColorWHITE();
    GRegion localGRegion = this.bevelMenu.B;
    paramGWindowMenuBar.draw(0.0F, paramGWindowMenuBar.win.dy - this.bevelMenu.B.dy, paramGWindowMenuBar.win.dx, this.bevelMenu.B.dy, this.elementsStreched, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = this.bevelMenu.Area;
    paramGWindowMenuBar.draw(0.0F, 0.0F, paramGWindowMenuBar.win.dx, paramGWindowMenuBar.win.dy - this.bevelMenu.B.dy, this.elementsStreched, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
  }

  public void render(GWindowMenuBarItem paramGWindowMenuBarItem) {
    if (paramGWindowMenuBarItem == paramGWindowMenuBarItem.menuBar().selected) {
      paramGWindowMenuBarItem.setCanvasColorWHITE();
      drawBevel(paramGWindowMenuBarItem, 0.0F, 0.0F, paramGWindowMenuBarItem.win.dx, paramGWindowMenuBarItem.win.dy, this.bevelDOWNsmall, this.elementsStreched);
    } else if (paramGWindowMenuBarItem == paramGWindowMenuBarItem.menuBar().over) {
      paramGWindowMenuBarItem.setCanvasColorWHITE();
      drawBevel(paramGWindowMenuBarItem, 0.0F, 0.0F, paramGWindowMenuBarItem.win.dx, paramGWindowMenuBarItem.win.dy, this.bevelUPsmall, this.elementsStreched);
    }
    if (paramGWindowMenuBarItem.pushClipRegion(paramGWindowMenuBarItem.getClientRegion(), paramGWindowMenuBarItem.bClip, 0.0F)) {
      paramGWindowMenuBarItem.setCanvasColorBLACK();
      float f1 = metric(this.spaceMenuBarItem);
      float f2 = metric(this.spaceMenuBarItem);
      if (paramGWindowMenuBarItem == paramGWindowMenuBarItem.menuBar().selected) {
        f1 = f2 = metric(this.spaceMenuBarItem) + 1.0F;
        paramGWindowMenuBarItem.root.C.color.set(255, 255, 0);
      } else if (paramGWindowMenuBarItem == paramGWindowMenuBarItem.menuBar().over) {
        f1 = f2 = metric(this.spaceMenuBarItem) - 1.0F;
      }
      paramGWindowMenuBarItem.cap.draw(paramGWindowMenuBarItem, f1, f2, paramGWindowMenuBarItem.root.textFonts[0], (paramGWindowMenuBarItem.menuBar().bAltDown) && (paramGWindowMenuBarItem.menuBar().over == null));

      paramGWindowMenuBarItem.popClip();
    }
  }

  public void render(GWindowMenu paramGWindowMenu) {
    paramGWindowMenu.setCanvasColorWHITE();
    drawBevel(paramGWindowMenu, 0.0F, 0.0F, paramGWindowMenu.win.dx, paramGWindowMenu.win.dy, this.bevelDOWNsmall, this.elementsStreched);
  }

  public void render(GWindowMenuItem paramGWindowMenuItem) {
    if ("-".equals(paramGWindowMenuItem.cap.caption)) {
      drawSeparateH(paramGWindowMenuItem, 0.0F, paramGWindowMenuItem.win.dy / 2.0F, paramGWindowMenuItem.win.dx);
      return;
    }
    paramGWindowMenuItem.setCanvasFont(0);
    if (paramGWindowMenuItem.bEnable) {
      if (paramGWindowMenuItem == paramGWindowMenuItem.menu().selected)
      {
        paramGWindowMenuItem.root.C.color.set(255, 255, 128);
        paramGWindowMenuItem.draw(0.0F, 0.0F, paramGWindowMenuItem.win.dx, paramGWindowMenuItem.win.dy, this.elementsStreched, 65.0F, 81.0F, 14.0F, 14.0F);
        paramGWindowMenuItem.root.C.color.set(255, 255, 0);
      }
      else {
        paramGWindowMenuItem.root.C.color.set(255, 215, 80);
      }
      renderMenuItem(paramGWindowMenuItem, 0.0F, 0.0F);
    }
    else {
      paramGWindowMenuItem.root.C.color.set(127, 127, 127);
      renderMenuItem(paramGWindowMenuItem, 1.0F, 1.0F);
      paramGWindowMenuItem.root.C.color.set(148, 136, 97);
      renderMenuItem(paramGWindowMenuItem, 0.0F, 0.0F);
    }
  }

  private void renderMenuItem(GWindowMenuItem paramGWindowMenuItem, float paramFloat1, float paramFloat2) {
    if (paramGWindowMenuItem.bChecked) {
      paramGWindowMenuItem.draw(paramFloat1, (paramGWindowMenuItem.win.dy - metric()) / 2.0F + paramFloat2, metric(), metric(), this.selectMenuIcon);
    }
    paramGWindowMenuItem.cap.draw(paramGWindowMenuItem, metric() + metric(this.spaceMenuItem) + paramFloat1, paramGWindowMenuItem.win.dy - metric(this.spaceMenuItem) - paramGWindowMenuItem.root.textFonts[0].height + paramFloat2, paramGWindowMenuItem.root.textFonts[0]);

    if (paramGWindowMenuItem.subMenu() != null)
      paramGWindowMenuItem.draw(paramGWindowMenuItem.win.dx - metric() + paramFloat1, (paramGWindowMenuItem.win.dy - metric()) / 2.0F + paramFloat2, metric(), metric(), this.subMenuIcon);
  }

  public void render(GWindowFramed paramGWindowFramed)
  {
    float f = framedTitleHeight(paramGWindowFramed);
    paramGWindowFramed.setCanvasColorWHITE();
    drawBevel(paramGWindowFramed, 0.0F, f, paramGWindowFramed.win.dx, paramGWindowFramed.win.dy - f, this.bevelFW, this.elementsStreched);

    GBevel localGBevel = this.bevelTitleActive;
    if (!paramGWindowFramed.isActivated()) localGBevel = this.bevelTitleInactive;
    paramGWindowFramed.setCanvasColorWHITE();
    drawBevel(paramGWindowFramed, 0.0F, 0.0F, paramGWindowFramed.win.dx, f, localGBevel, this.elementsStreched);
    if (paramGWindowFramed.title != null) {
      paramGWindowFramed.root.C.color.set(255, 255, 0);

      if (!paramGWindowFramed.isActivated()) paramGWindowFramed.setCanvasColor(12632256);
      paramGWindowFramed.setCanvasFont(1);
      this._titleRegion.x = this.bevelTitleActive.L.dx;
      this._titleRegion.y = this.bevelTitleActive.T.dy;
      this._titleRegion.dx = (paramGWindowFramed.win.dx - this.bevelTitleActive.L.dx - this.bevelTitleActive.R.dx - metric());
      this._titleRegion.dy = (f - this.bevelTitleActive.T.dy - this.bevelTitleActive.B.dy);
      if (paramGWindowFramed.pushClipRegion(this._titleRegion, paramGWindowFramed.bClip, metric(this.spaceFramedTitle))) {
        paramGWindowFramed.draw(0.0F, 0.0F, paramGWindowFramed.title);
        paramGWindowFramed.popClip();
      }
    }
  }

  private float framedTitleHeight(GWindowFramed paramGWindowFramed) {
    float f = metric();
    if (f > paramGWindowFramed.root.textFonts[1].height)
      f = paramGWindowFramed.root.textFonts[1].height;
    return f + 2.0F * metric(this.spaceFramedTitle) + this.bevelTitleActive.T.dy + this.bevelTitleActive.B.dy;
  }
  public void render(GWindowClient paramGWindowClient) {
    paramGWindowClient.setCanvasColorWHITE();

    paramGWindowClient.draw(0.0F, 0.0F, paramGWindowClient.win.dx, paramGWindowClient.win.dy, this.elementsStreched, 65.0F, 33.0F, 30.0F, 14.0F);
  }

  public void render(GWindowLabel paramGWindowLabel)
  {
    if (!paramGWindowLabel.bEnable) {
      localGRegion = paramGWindowLabel.getClientRegion();
      f1 = localGRegion.dx;
      f2 = localGRegion.dy;
      if (paramGWindowLabel.pushClipRegion(localGRegion, paramGWindowLabel.bClip, 0.0F)) {
        renderTextDialogControl(paramGWindowLabel, 1.0F, 1.0F, f1, f2, 8355711, false);
        renderTextDialogControl(paramGWindowLabel, 0.0F, 0.0F, f1, f2, 6391956, false);
        paramGWindowLabel.popClip();
      }
      return;
    }
    if (paramGWindowLabel.isActivated()) {
      paramGWindowLabel.setCanvasColorWHITE();
      drawBevel(paramGWindowLabel, 0.0F, 0.0F, paramGWindowLabel.win.dx, paramGWindowLabel.win.dy, this.bevelBlack50, this.elements);
    }
    GRegion localGRegion = paramGWindowLabel.getClientRegion();
    float f1 = localGRegion.dx;
    float f2 = localGRegion.dy;
    if (paramGWindowLabel.pushClipRegion(localGRegion, paramGWindowLabel.bClip, 0.0F)) {
      renderTextDialogControl(paramGWindowLabel, 0.0F, 0.0F, f1, f2, 5298175, paramGWindowLabel.isDefault());
      paramGWindowLabel.popClip();
    }
  }

  public void renderComboList(GWindowComboControl paramGWindowComboControl)
  {
    GWindowComboControl.ListArea localListArea = paramGWindowComboControl.listArea;
    localListArea.setCanvasColorWHITE();
    localListArea.draw(this.bevelDOWNsmall.L.dx, this.bevelDOWNsmall.T.dy, localListArea.win.dx - this.bevelDOWNsmall.R.dx - this.bevelDOWNsmall.L.dx, localListArea.win.dy - this.bevelDOWNsmall.B.dy - this.bevelDOWNsmall.T.dy, this.elementsStreched, 114.0F, 34.0F, 12.0F, 12.0F);

    drawBevel(localListArea, 0.0F, 0.0F, localListArea.win.dx, localListArea.win.dy, this.bevelDOWNsmall, this.elementsStreched);
    GRegion localGRegion = localListArea.getClientRegion();
    localGRegion.x += this.bevelDOWNsmall.L.dx + this.spaceComboList;
    localGRegion.y += this.bevelDOWNsmall.T.dy;
    localGRegion.dx -= this.bevelDOWNsmall.L.dx + this.bevelDOWNsmall.R.dx + 2.0F * this.spaceComboList;
    localGRegion.dy -= this.bevelDOWNsmall.T.dy + this.bevelDOWNsmall.B.dy;
    if (paramGWindowComboControl.scrollBar.isVisible())
      localGRegion.dx -= paramGWindowComboControl.scrollBar.win.dx;
    if (localListArea.pushClipRegion(localGRegion, true, 0.0F)) {
      localListArea.setCanvasColor(5298175);
      localListArea.setCanvasFont(paramGWindowComboControl.font);
      GFont localGFont = paramGWindowComboControl.root.C.font;
      float f = (getComboHline() - localGFont.height) / 2.0F;
      int i = paramGWindowComboControl.listStartLine;
      for (int j = 0; j < paramGWindowComboControl.listCountLines; j++)
      {
        GSize localGSize;
        if (i == paramGWindowComboControl.listSelected) {
          localGSize = localGFont.size(paramGWindowComboControl.get(i));
          localListArea.draw(0.0F, f, localListArea.win.dx, localGSize.dy, this.elementsStreched, 65.0F, 81.0F, 14.0F, 14.0F);
          localListArea.setCanvasColor(65535);
          localListArea.draw(0.0F, f, paramGWindowComboControl.get(i));
          localListArea.setCanvasColor(5298175);
        } else if ((paramGWindowComboControl.posEnable != null) && (paramGWindowComboControl.posEnable[i] == 0)) {
          localGSize = localGFont.size(paramGWindowComboControl.get(i));
          localListArea.setCanvasColor(8355711);

          localListArea.draw(1.0F, f + 1.0F, paramGWindowComboControl.get(i));
          localListArea.setCanvasColor(6391956);
          localListArea.draw(0.0F, f, paramGWindowComboControl.get(i));
          localListArea.setCanvasColor(65535);
        }
        else
        {
          localListArea.draw(0.0F, f, paramGWindowComboControl.get(i));
        }
        i++;
        f += getComboHline();
      }
      localListArea.popClip();
    }
  }

  public void setupComboList(GWindowComboControl paramGWindowComboControl) {
    paramGWindowComboControl.listArea.win.dx = paramGWindowComboControl.win.dx;
    paramGWindowComboControl.listCountLines = paramGWindowComboControl.listVisibleLines;
    if (paramGWindowComboControl.listCountLines > paramGWindowComboControl.size())
      paramGWindowComboControl.listCountLines = paramGWindowComboControl.size();
    paramGWindowComboControl.listArea.win.dy = (paramGWindowComboControl.listCountLines * getComboHline() + this.bevelDOWNsmall.B.dy + this.bevelDOWNsmall.T.dy);

    GPoint localGPoint = paramGWindowComboControl.windowToGlobal(0.0F, paramGWindowComboControl.win.dy);
    if (localGPoint.y + paramGWindowComboControl.listArea.win.dy > paramGWindowComboControl.root.win.dy)
      paramGWindowComboControl.listArea.win.y = (-paramGWindowComboControl.listArea.win.dy);
    else
      paramGWindowComboControl.listArea.win.y = paramGWindowComboControl.win.dy;
    paramGWindowComboControl.listArea.win.x = 0.0F;
    if (paramGWindowComboControl.listCountLines < paramGWindowComboControl.size()) {
      paramGWindowComboControl.scrollBar.win.dx = getVScrollBarW();
      paramGWindowComboControl.scrollBar.win.dy = (paramGWindowComboControl.listCountLines * getComboHline());
      paramGWindowComboControl.scrollBar.win.x = (paramGWindowComboControl.listArea.win.dx - paramGWindowComboControl.scrollBar.win.dx - this.bevelDOWNsmall.R.dx);
      paramGWindowComboControl.scrollBar.win.y = this.bevelDOWNsmall.T.dy;
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
  }
  public void render(GWindowComboControl paramGWindowComboControl) {
    paramGWindowComboControl.setCanvasColorWHITE();
    if (paramGWindowComboControl.bEnable) {
      paramGWindowComboControl.draw(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy, paramGWindowComboControl.win.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowComboControl.win.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy, this.elementsStreched, 100.0F, 52.0F, 8.0F, 8.0F);
    }
    else
    {
      paramGWindowComboControl.draw(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy, paramGWindowComboControl.win.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowComboControl.win.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy, this.elementsStreched, 116.0F, 52.0F, 8.0F, 8.0F);
    }

    drawBevel(paramGWindowComboControl, 0.0F, 0.0F, paramGWindowComboControl.win.dx, paramGWindowComboControl.win.dy, this.bevelDOWN, this.elementsStreched, false);
  }

  public void render(GWindowEditBox paramGWindowEditBox, float paramFloat)
  {
    paramGWindowEditBox.color = 5298175;
    super.render(paramGWindowEditBox, paramFloat);
  }

  public void render(GWindowEditControl paramGWindowEditControl)
  {
    paramGWindowEditControl.setCanvasColorWHITE();
    paramGWindowEditControl.draw(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy, paramGWindowEditControl.win.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowEditControl.win.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy, this.elementsStreched, 100.0F, 52.0F, 8.0F, 8.0F);

    drawBevel(paramGWindowEditControl, 0.0F, 0.0F, paramGWindowEditControl.win.dx, paramGWindowEditControl.win.dy, this.bevelDOWN, this.elementsStreched, false);
    render(paramGWindowEditControl, this.bevelDOWN.L.dx);
  }

  public void render(GWindowHScrollBar paramGWindowHScrollBar)
  {
    float f1 = paramGWindowHScrollBar.xM - paramGWindowHScrollBar.lButton.win.dx;
    float f2 = paramGWindowHScrollBar.rButton.win.x - paramGWindowHScrollBar.xM - paramGWindowHScrollBar.dxM;
    if ((f1 < 0.0F) && (f2 < 0.0F)) return;
    paramGWindowHScrollBar.setCanvasColorWHITE();

    if (f1 > 0.0F) {
      if (paramGWindowHScrollBar.downState == 1)
        paramGWindowHScrollBar.draw(paramGWindowHScrollBar.lButton.win.dx, 0.0F, f1, paramGWindowHScrollBar.win.dy, this.elements, 85.0F, 81.0F, 2.0F, 6.0F);
      else
        paramGWindowHScrollBar.draw(paramGWindowHScrollBar.lButton.win.dx, 0.0F, f1, paramGWindowHScrollBar.win.dy, this.elements, 81.0F, 81.0F, 2.0F, 6.0F);
    }
    if (f2 > 0.0F)
      if (paramGWindowHScrollBar.downState == 2)
        paramGWindowHScrollBar.draw(paramGWindowHScrollBar.xM + paramGWindowHScrollBar.dxM, 0.0F, f2, paramGWindowHScrollBar.win.dy, this.elements, 85.0F, 81.0F, 2.0F, 6.0F);
      else
        paramGWindowHScrollBar.draw(paramGWindowHScrollBar.xM + paramGWindowHScrollBar.dxM, 0.0F, f2, paramGWindowHScrollBar.win.dy, this.elements, 81.0F, 81.0F, 2.0F, 6.0F);
  }

  public void render(GWindowVScrollBar paramGWindowVScrollBar)
  {
    float f1 = paramGWindowVScrollBar.yM - paramGWindowVScrollBar.uButton.win.dy;
    float f2 = paramGWindowVScrollBar.dButton.win.y - paramGWindowVScrollBar.yM - paramGWindowVScrollBar.dyM;
    if ((f1 < 0.0F) && (f2 < 0.0F)) return;
    paramGWindowVScrollBar.setCanvasColorWHITE();

    if (f1 > 0.0F) {
      if (paramGWindowVScrollBar.downState == 1)
        paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.uButton.win.dy, paramGWindowVScrollBar.win.dx, f1, this.elements, 89.0F, 85.0F, 6.0F, 2.0F);
      else
        paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.uButton.win.dy, paramGWindowVScrollBar.win.dx, f1, this.elements, 89.0F, 81.0F, 6.0F, 2.0F);
    }
    if (f2 > 0.0F)
      if (paramGWindowVScrollBar.downState == 2)
        paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.yM + paramGWindowVScrollBar.dyM, paramGWindowVScrollBar.win.dx, f2, this.elements, 89.0F, 85.0F, 6.0F, 2.0F);
      else
        paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.yM + paramGWindowVScrollBar.dyM, paramGWindowVScrollBar.win.dx, f2, this.elements, 89.0F, 81.0F, 6.0F, 2.0F);
  }

  public void render(GWindowHSliderInt paramGWindowHSliderInt)
  {
    paramGWindowHSliderInt.setCanvasColorWHITE();
    drawSeparateH(paramGWindowHSliderInt, 0.0F, paramGWindowHSliderInt.win.dy - (int)(metric() / 2.0F), paramGWindowHSliderInt.win.dx);
    if ((paramGWindowHSliderInt.bEnable) && (paramGWindowHSliderInt.isActivated())) {
      drawBevel(paramGWindowHSliderInt, 0.0F, 0.0F, paramGWindowHSliderInt.win.dx, paramGWindowHSliderInt.win.dy, this.bevelBlack50, this.elementsStreched);
    }
    drawBevel(paramGWindowHSliderInt, paramGWindowHSliderInt.xM, paramGWindowHSliderInt.win.dy - metric(), paramGWindowHSliderInt.dxM, metric(), this.bevelUP, this.elementsStreched);
    float f1 = (int)(metric() / 4.0F) - 1.0F;
    float f2 = (paramGWindowHSliderInt.win.dx - paramGWindowHSliderInt.dxM) / (paramGWindowHSliderInt.posCount - 1);
    if (f2 < 3.0F) return;
    for (int i = 0; i < paramGWindowHSliderInt.posCount; i++) {
      boolean bool = paramGWindowHSliderInt.bEnable;
      int j;
      if ((bool) && (paramGWindowHSliderInt.posEnable != null))
        j = paramGWindowHSliderInt.posEnable[i];
      if (j != 0) paramGWindowHSliderInt.draw(f1, 1.0F, 2.0F, 2.0F, this.elements, 126.0F, 106.0F, 1.0F, 1.0F); else
        paramGWindowHSliderInt.draw(f1, 1.0F, 2.0F, 2.0F, this.elements, 81.0F, 65.0F, 1.0F, 1.0F);
      f1 += f2;
    }
  }

  public void render(GWindowStatusBar paramGWindowStatusBar)
  {
    paramGWindowStatusBar.setCanvasColorWHITE();
    drawBevel(paramGWindowStatusBar, 0.0F, 0.0F, paramGWindowStatusBar.win.dx, paramGWindowStatusBar.win.dy, this.bevelUP, this.elementsStreched);
    drawBevel(paramGWindowStatusBar, this.bevelUP.L.dx + metric(this.spaceStatusBar), this.bevelUP.T.dy + metric(this.spaceStatusBar), paramGWindowStatusBar.win.dx - 2.0F * metric(this.spaceStatusBar) - this.bevelUP.L.dx - this.bevelUP.R.dx, paramGWindowStatusBar.win.dy - 2.0F * metric(this.spaceStatusBar) - this.bevelUP.T.dy - this.bevelUP.B.dy, this.bevelDOWNsmall, this.elementsStreched);

    if (paramGWindowStatusBar.pushClipRegion(paramGWindowStatusBar.getClientRegion(), paramGWindowStatusBar.bClip, 0.0F)) {
      paramGWindowStatusBar.setCanvasColor(5298175);
      paramGWindowStatusBar.setCanvasFont(0);
      if (paramGWindowStatusBar.help != null)
        paramGWindowStatusBar.draw(0.0F, 0.0F, paramGWindowStatusBar.help);
      else
        paramGWindowStatusBar.draw(0.0F, 0.0F, paramGWindowStatusBar.defaultHelp);
      paramGWindowStatusBar.popClip();
    }
  }

  public void render(GWindowTabDialogClient paramGWindowTabDialogClient)
  {
    float f = 0.0F;
    if (paramGWindowTabDialogClient.sizeTabs() > 0) {
      GSize localGSize = paramGWindowTabDialogClient.getTab(0).getMinSize();
      f = localGSize.dy;
    }
    paramGWindowTabDialogClient.setCanvasColorWHITE();
    drawBevel(paramGWindowTabDialogClient, 0.0F, f, paramGWindowTabDialogClient.win.dx, paramGWindowTabDialogClient.win.dy - f, this.bevelTabDialogClient, this.elementsStreched, false);
  }
  public void render(GWindowTabDialogClient.Tab paramTab) {
    GBevel localGBevel = this.bevelTab;
    boolean bool = paramTab.isCurrent();
    if (bool) localGBevel = this.bevelTabCUR;
    if (paramTab.isMouseOver()) localGBevel = this.bevelTabOVER;
    paramTab.setCanvasColorWHITE();
    drawBevel(paramTab, 0.0F, 0.0F, paramTab.win.dx, paramTab.win.dy, localGBevel, this.elementsStreched);
    this.tabReg.x = (localGBevel.L.dx + metric(this.spaceTab));
    this.tabReg.y = (localGBevel.T.dy + metric(this.spaceTab));
    this.tabReg.dx = (paramTab.win.dx - localGBevel.L.dx - localGBevel.R.dx);
    this.tabReg.dy = (paramTab.win.dy - localGBevel.T.dy - localGBevel.B.dy);
    if (paramTab.pushClipRegion(this.tabReg, paramTab.bClip, 0.0F)) {
      if (bool) {
        renderTextDialogControl(paramTab, metric(this.spaceTab), metric(this.spaceTab), this.tabReg.dx - 2.0F * metric(this.spaceTab), this.tabReg.dy - 2.0F * metric(this.spaceTab), 65535, false);
      }
      else
      {
        renderTextDialogControl(paramTab, metric(this.spaceTab), metric(this.spaceTab), this.tabReg.dx - 2.0F * metric(this.spaceTab), this.tabReg.dy - 2.0F * metric(this.spaceTab), 5298175, false);
      }

      paramTab.popClip();
    }
  }

  public void setMessageBoxTextColor(GWindowClient paramGWindowClient) {
    paramGWindowClient.setCanvasColor(5298175);
  }

  public void resolutionChanged(GWindowRoot paramGWindowRoot)
  {
    super.resolutionChanged(paramGWindowRoot);
  }

  public void init(GWindowRoot paramGWindowRoot) {
    super.init(paramGWindowRoot);
    this.elements = GTexture.New("GUI/blackmetal/elements.mat");
    this.elementsStreched = GTexture.New("GUI/blackmetal/elementss.mat");
    this.cursors = GTexture.New("GUI/blackmetal/cursors.mat");
    this.cursorsStreched = GTexture.New("GUI/blackmetal/cursorss.mat");

    this.regionWhite = new GTexRegion(this.elements, 124.0F, 100.0F, 1.0F, 1.0F);

    this.bevelUP.set(new GRegion(96.0F, 64.0F, 16.0F, 16.0F), new GRegion(100.0F, 68.0F, 8.0F, 8.0F));
    this.bevelDOWN.set(new GRegion(112.0F, 64.0F, 16.0F, 16.0F), new GRegion(116.0F, 68.0F, 8.0F, 8.0F));

    this.bevelUPsmall.set(new GRegion(96.0F, 32.0F, 16.0F, 16.0F), new GRegion(98.0F, 34.0F, 12.0F, 12.0F));
    this.bevelDOWNsmall.set(new GRegion(112.0F, 32.0F, 16.0F, 16.0F), new GRegion(114.0F, 34.0F, 12.0F, 12.0F));

    this.bevelButtonUP.set(new GRegion(64.0F, 0.0F, 32.0F, 16.0F), new GRegion(68.0F, 4.0F, 24.0F, 8.0F));
    this.bevelButtonOVER.set(new GRegion(96.0F, 0.0F, 32.0F, 16.0F), new GRegion(100.0F, 4.0F, 24.0F, 8.0F));
    this.bevelButtonDOWN.set(new GRegion(64.0F, 16.0F, 32.0F, 16.0F), new GRegion(68.0F, 20.0F, 24.0F, 8.0F));

    this.bevelMenu.set(new GRegion(64.0F, 48.0F, 32.0F, 16.0F), new GRegion(68.0F, 52.0F, 24.0F, 8.0F));

    this.bevelFW.set(new GRegion(64.0F, 4.0F, 32.0F, 12.0F), new GRegion(68.0F, 4.0F, 24.0F, 8.0F));

    this.bevelTitleActive.set(new GRegion(96.0F, 48.0F, 16.0F, 16.0F), new GRegion(100.0F, 52.0F, 8.0F, 8.0F));
    this.bevelTitleInactive.set(new GRegion(112.0F, 48.0F, 16.0F, 16.0F), new GRegion(116.0F, 52.0F, 8.0F, 8.0F));

    this.bevelBlack.set(new GRegion(64.0F, 64.0F, 16.0F, 16.0F), new GRegion(66.0F, 66.0F, 12.0F, 12.0F));
    this.bevelBlack.Area.dx = 0.0F;
    this.bevelBlack50.set(new GRegion(80.0F, 64.0F, 16.0F, 16.0F), new GRegion(82.0F, 66.0F, 12.0F, 12.0F));
    this.bevelBlack50.Area.dx = 0.0F;
    this.bevelSeparate.set(new GRegion(64.0F, 64.0F, 16.0F, 16.0F), new GRegion(66.0F, 66.0F, 12.0F, 12.0F));
    this.bevelSeparate.Area.dx = 0.0F;

    this.bevelTabDialogClient = new GBevel();
    this.bevelTabDialogClient.set(new GRegion(96.0F, 16.0F, 32.0F, 16.0F), new GRegion(100.0F, 20.0F, 24.0F, 8.0F));

    this.bevelTabCUR.set(new GRegion(64.0F, 96.0F, 16.0F, 16.0F), new GRegion(68.0F, 100.0F, 8.0F, 8.0F));

    this.bevelTabOVER.set(new GRegion(80.0F, 96.0F, 16.0F, 16.0F), new GRegion(84.0F, 100.0F, 8.0F, 8.0F));

    this.bevelTab.set(new GRegion(96.0F, 96.0F, 16.0F, 14.0F), new GRegion(100.0F, 100.0F, 8.0F, 6.0F));

    this.closeBoxUP = new GTexRegion(this.elementsStreched, 0.0F, 16.0F, 16.0F, 16.0F);
    this.closeBoxOVER = new GTexRegion(this.elementsStreched, 16.0F, 16.0F, 16.0F, 16.0F);
    this.closeBoxDOWN = new GTexRegion(this.elementsStreched, 32.0F, 16.0F, 16.0F, 16.0F);
    this.closeBoxDISABLE = new GTexRegion(this.elementsStreched, 48.0F, 16.0F, 16.0F, 16.0F);

    this.SBupButtonUP = new GTexRegion(this.elementsStreched, 0.0F, 64.0F, 16.0F, 16.0F);
    this.SBupButtonOVER = new GTexRegion(this.elementsStreched, 16.0F, 64.0F, 16.0F, 16.0F);
    this.SBupButtonDOWN = new GTexRegion(this.elementsStreched, 32.0F, 64.0F, 16.0F, 16.0F);
    this.SBupButtonDISABLE = new GTexRegion(this.elementsStreched, 48.0F, 64.0F, 16.0F, 16.0F);

    this.SBdownButtonUP = new GTexRegion(this.elementsStreched, 0.0F, 80.0F, 16.0F, 16.0F);
    this.SBdownButtonOVER = new GTexRegion(this.elementsStreched, 16.0F, 80.0F, 16.0F, 16.0F);
    this.SBdownButtonDOWN = new GTexRegion(this.elementsStreched, 32.0F, 80.0F, 16.0F, 16.0F);
    this.SBdownButtonDISABLE = new GTexRegion(this.elementsStreched, 48.0F, 80.0F, 16.0F, 16.0F);

    this.SBleftButtonUP = new GTexRegion(this.elementsStreched, 0.0F, 96.0F, 16.0F, 16.0F);
    this.SBleftButtonOVER = new GTexRegion(this.elementsStreched, 16.0F, 96.0F, 16.0F, 16.0F);
    this.SBleftButtonDOWN = new GTexRegion(this.elementsStreched, 32.0F, 96.0F, 16.0F, 16.0F);
    this.SBleftButtonDISABLE = new GTexRegion(this.elementsStreched, 48.0F, 96.0F, 16.0F, 16.0F);

    this.SBrightButtonUP = new GTexRegion(this.elementsStreched, 0.0F, 112.0F, 16.0F, 16.0F);
    this.SBrightButtonOVER = new GTexRegion(this.elementsStreched, 16.0F, 112.0F, 16.0F, 16.0F);
    this.SBrightButtonDOWN = new GTexRegion(this.elementsStreched, 32.0F, 112.0F, 16.0F, 16.0F);
    this.SBrightButtonDISABLE = new GTexRegion(this.elementsStreched, 48.0F, 112.0F, 16.0F, 16.0F);

    this.checkBoxUnCheckEnable = new GTexRegion(this.elementsStreched, 64.0F, 112.0F, 16.0F, 16.0F);
    this.checkBoxCheckEnable = new GTexRegion(this.elementsStreched, 80.0F, 112.0F, 16.0F, 16.0F);
    this.checkBoxUnCheckDisable = new GTexRegion(this.elementsStreched, 96.0F, 112.0F, 16.0F, 16.0F);
    this.checkBoxCheckDisable = new GTexRegion(this.elementsStreched, 112.0F, 112.0F, 16.0F, 16.0F);

    paramGWindowRoot.textFonts[0] = GFont.New("arial10");
    paramGWindowRoot.textFonts[1] = GFont.New("arialb10");
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

    this.selectMenuIcon = new GTexRegion(this.cursors, 116.0F, 104.0F, 12.0F, 12.0F);
    this.subMenuIcon = new GTexRegion(this.cursors, 116.0F, 116.0F, 12.0F, 12.0F);
  }
}