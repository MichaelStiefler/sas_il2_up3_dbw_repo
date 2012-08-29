package com.maddox.gwindow;

import com.maddox.il2.game.I18N;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class GWin95LookAndFeel extends GWindowLookAndFeel
{
  public float metric = 12.0F;

  public GBevel bevelUP = new GBevel();
  public GBevel bevelDOWN = new GBevel();
  public GBevel bevelUPsmall = new GBevel();
  public GBevel bevelDOWNsmall = new GBevel();
  public GBevel bevelFW = new GBevel();
  public GBevel bevelTitleActive = new GBevel();
  public GBevel bevelTitleInactive = new GBevel();
  public GBevel bevelBlack = new GBevel();
  public GBevel bevelBlack50 = new GBevel();
  public GBevel bevelSeparate = new GBevel();

  public GBevel bevelTabCUR = new GBevel();
  public GBevel bevelTab = new GBevel();
  public GTexture elements;
  public GTexture elementsStreched;
  public GTexture cursors;
  public GTexture cursorsStreched;
  public GTexRegion selectMenuIcon;
  public GTexRegion subMenuIcon;
  public GTexRegion closeBoxUP;
  public GTexRegion closeBoxDOWN;
  public GTexRegion closeBoxDISABLE;
  public GTexRegion closeBoxOVER;
  public GTexRegion SBupButtonUP;
  public GTexRegion SBupButtonDOWN;
  public GTexRegion SBupButtonDISABLE;
  public GTexRegion SBupButtonOVER;
  public GTexRegion SBdownButtonUP;
  public GTexRegion SBdownButtonDOWN;
  public GTexRegion SBdownButtonDISABLE;
  public GTexRegion SBdownButtonOVER;
  public GTexRegion SBleftButtonUP;
  public GTexRegion SBleftButtonDOWN;
  public GTexRegion SBleftButtonDISABLE;
  public GTexRegion SBleftButtonOVER;
  public GTexRegion SBrightButtonUP;
  public GTexRegion SBrightButtonDOWN;
  public GTexRegion SBrightButtonDISABLE;
  public GTexRegion SBrightButtonOVER;
  public GTexRegion checkBoxCheckEnable;
  public GTexRegion checkBoxCheckDisable;
  public GTexRegion checkBoxUnCheckEnable;
  public GTexRegion checkBoxUnCheckDisable;
  float spaceTab = 0.08333334F;

  GRegion tabReg = new GRegion();

  float spaceComboList = 3.0F;

  private static GRegion _editBoxReg = new GRegion();

  float minScrollMSize = 0.5F;

  float spaceLabel = 0.25F;

  float spaceButton = 0.1666667F;

  float spaceFramedTitle = 0.25F;

  private GRegion _titleRegion = new GRegion();

  float spaceMenuItem = 0.1666667F;

  float spaceMenu = 0.25F;

  float spaceMenuBarItem = 0.1666667F;

  float spaceMenuBar = 0.1666667F;

  float spaceStatusBar = 0.08333334F;

  public float metric(float paramFloat)
  {
    return (int)(this.metric * paramFloat + 0.5F); } 
  public float metric() { return this.metric;
  }

  public void soundPlay(String paramString)
  {
    if (this.bSoundEnable)
      System.out.println("LF.playSound: " + paramString);
  }

  public void fillRegion(GWindow paramGWindow, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    paramGWindow.setCanvasColor(paramInt);
    paramGWindow.draw(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.elements, 16.0F, 69.0F, 1.0F, 1.0F);
  }

  public void drawSeparateH(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
    paramGWindow.setCanvasColorWHITE();
    paramGWindow.draw(paramFloat1, paramFloat2, paramFloat3, 2.0F, this.elements, 19.0F, 69.0F, 1.0F, 2.0F);
  }
  public void drawSeparateW(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3) {
    paramGWindow.setCanvasColorWHITE();
    paramGWindow.draw(paramFloat1, paramFloat2, 2.0F, paramFloat3, this.elements, 27.0F, 71.0F, 2.0F, 1.0F);
  }

  public void renderClient(GWindowTree paramGWindowTree)
  {
    int i = paramGWindowTree.rows.size();
    if ((i == 0) || (paramGWindowTree.model == null)) return;
    GTreePath localGTreePath1 = paramGWindowTree.model.getRoot();
    int j = localGTreePath1.getPathCount();
    float f1 = paramGWindowTree.getTabStep();
    float f2 = paramGWindowTree.getBorderSpace();
    if (paramGWindowTree.bDrawTreeLines) {
      k = GColor.Gray.jdField_color_of_type_Int;
      for (int m = 0; m < i; m++) {
        localGTreePath1 = (GTreePath)paramGWindowTree.rows.get(m);
        int n = localGTreePath1.getPathCount();
        float f4 = paramGWindowTree.computeHeight(localGTreePath1);
        if (n == j) {
          f2 += f4;
        }
        else {
          float f5 = paramGWindowTree.getBorderSpace();
          f5 += (n - 1 - j) * f1;
          int i1 = 1;
          int i2 = 1;
          float f6 = 0.0F;
          int i3 = m + 1;
          for (; i3 < i; i3++) {
            GTreePath localGTreePath2 = (GTreePath)paramGWindowTree.rows.get(i3);
            int i4 = localGTreePath2.getPathCount();
            if (i3 == m + 1)
              i2 = i4 != n ? 1 : 0;
            if (i4 == n) break;
            if (i4 < n) {
              i1 = 1;
              break;
            }
            i1 = 0;
            f6 += paramGWindowTree.computeHeight(localGTreePath2);
          }
          if (i3 == i) i1 = 1;
          if (i1 == 0) i2 = 0;
          if (paramGWindowTree.model.isLeaf(localGTreePath1)) {
            if (i2 != 0) fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 2.0F, f2, 1.0F, f4 / 2.0F); else
              fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 2.0F, f2, 1.0F, f4);
            fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 2.0F, f2 + f4 / 2.0F, f1 / 2.0F, 1.0F);
          } else {
            fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 4.0F, f2 + f4 / 4.0F, f1 / 2.0F, 1.0F);
            fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 4.0F, f2 + 3.0F * f4 / 4.0F, f1 / 2.0F + 1.0F, 1.0F);
            fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 4.0F, f2 + f4 / 4.0F, 1.0F, f4 / 2.0F);
            fillRegion(paramGWindowTree.wClient, k, f5 + 3.0F * f1 / 4.0F, f2 + f4 / 4.0F, 1.0F, f4 / 2.0F);

            fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 4.0F + 2.0F, f2 + f4 / 2.0F, f1 / 2.0F - 3.0F, 1.0F);
            if (!paramGWindowTree.isExpanded(localGTreePath1)) {
              fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 2.0F, f2 + f4 / 4.0F + 2.0F, 1.0F, f4 / 2.0F - 3.0F);
            }
            if (m != 0)
              fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 2.0F, f2, 1.0F, f4 / 4.0F);
            fillRegion(paramGWindowTree.wClient, k, f5 + 3.0F * f1 / 4.0F, f2 + f4 / 2.0F, f1 / 4.0F, 1.0F);
            if (i2 == 0) {
              fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 2.0F, f2 + 3.0F * f4 / 4.0F, 1.0F, f4 / 4.0F);
            }
          }
          f2 += f4;
          if (i1 == 0)
            fillRegion(paramGWindowTree.wClient, k, f5 + f1 / 2.0F, f2, 1.0F, f6);
        }
      }
      f2 = paramGWindowTree.getBorderSpace();
    }
    for (int k = 0; k < i; k++) {
      localGTreePath1 = (GTreePath)paramGWindowTree.rows.get(k);
      paramGWindowTree.computeSize(localGTreePath1);
      GPoint localGPoint = paramGWindowTree.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.org;
      float f3 = paramGWindowTree.getBorderSpace();
      f3 += (localGTreePath1.getPathCount() - j) * f1;
      localGPoint.add(f3, f2);
      Object localObject;
      if (paramGWindowTree.bDrawIcons) {
        localObject = paramGWindowTree.model.getIcon(localGTreePath1, paramGWindowTree.isSelect(localGTreePath1), paramGWindowTree.isExpanded(localGTreePath1));
        if (localObject != null) {
          paramGWindowTree.setCanvasColorWHITE();
          paramGWindowTree.draw(0.0F, 0.0F, paramGWindowTree.getTabStep(), paramGWindowTree._sizePathDy, (GTexRegion)localObject);
        }
        f3 += f1;
        localGPoint.add(f1, 0.0F);
      }
      if ((!localGTreePath1.equals(paramGWindowTree.selectPath)) || (paramGWindowTree.editor == null))
      {
        if ((paramGWindowTree._sizePathDx > 0.0F) && 
          (!paramGWindowTree.model.render(localGTreePath1, localGTreePath1.equals(paramGWindowTree.selectPath), paramGWindowTree.isExpanded(localGTreePath1), paramGWindowTree._sizePathDx, paramGWindowTree._sizePathDy)))
        {
          localObject = paramGWindowTree.model.getString(localGTreePath1, paramGWindowTree.isSelect(localGTreePath1), paramGWindowTree.isExpanded(localGTreePath1));
          if (localObject != null) {
            paramGWindowTree.setCanvasFont(paramGWindowTree.jdField_font_of_type_Int);
            if (localGTreePath1.equals(paramGWindowTree.selectPath)) {
              paramGWindowTree.setCanvasColorBLACK();
              paramGWindowTree.wClient.draw(0.0F, 0.0F, paramGWindowTree._sizePathDx, paramGWindowTree._sizePathDy, this.jdField_regionWhite_of_type_ComMaddoxGwindowGTexRegion);
              paramGWindowTree.setCanvasColorWHITE();
            } else {
              paramGWindowTree.setCanvasColorBLACK();
            }
            paramGWindowTree.wClient.draw(0.0F, 0.0F, paramGWindowTree._sizePathDx, paramGWindowTree._sizePathDy, 0, (String)localObject);
          }
        }
      }
      localGPoint.sub(f3, f2);
      f2 += paramGWindowTree._sizePathDy;
    }
  }

  public void render(GWindowTree paramGWindowTree) {
    fillRegion(paramGWindowTree, -1, 0.0F, 0.0F, paramGWindowTree.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowTree.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
    paramGWindowTree.setCanvasColorWHITE();
    drawBevel(paramGWindowTree, 0.0F, 0.0F, paramGWindowTree.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowTree.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelDOWN, this.elements, false);
    GRegion localGRegion = paramGWindowTree.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.clip;
    localGRegion.x += this.bevelDOWN.L.dx;
    localGRegion.y += this.bevelDOWN.T.dy;
    localGRegion.dx -= this.bevelDOWN.L.dx + this.bevelDOWN.R.dx;
    localGRegion.dy -= this.bevelDOWN.T.dy + this.bevelDOWN.B.dy;
  }

  public GRegion getClientRegion(GWindowTree paramGWindowTree, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (this.bevelDOWN.L.dx + paramFloat);
    paramGRegion.y = (this.bevelDOWN.T.dy + paramFloat);
    paramGRegion.dx = (paramGWindowTree.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGRegion.x - this.bevelDOWN.R.dx - paramFloat);
    paramGRegion.dy = (paramGWindowTree.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGRegion.y - this.bevelDOWN.B.dy - paramFloat);
    return paramGRegion;
  }

  public void render(GWindowTable paramGWindowTable)
  {
    paramGWindowTable.setCanvasColorWHITE();
    drawBevel(paramGWindowTable, 0.0F, 0.0F, paramGWindowTable.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowTable.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelDOWN, this.elements, true);
    GRegion localGRegion = paramGWindowTable.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.clip;
    localGRegion.x += this.bevelDOWN.L.dx;
    localGRegion.y += this.bevelDOWN.T.dy;
    localGRegion.dx -= this.bevelDOWN.L.dx + this.bevelDOWN.R.dx;
    localGRegion.dy -= this.bevelDOWN.T.dy + this.bevelDOWN.B.dy;
  }

  public GRegion getClientRegion(GWindowTable paramGWindowTable, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (this.bevelDOWN.L.dx + paramFloat);
    paramGRegion.y = (this.bevelDOWN.T.dy + paramFloat);
    paramGRegion.dx = (paramGWindowTable.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGRegion.x - this.bevelDOWN.R.dx - paramFloat);
    paramGRegion.dy = (paramGWindowTable.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGRegion.y - this.bevelDOWN.B.dy - paramFloat);
    return paramGRegion;
  }

  public void render(GWindowTabDialogClient paramGWindowTabDialogClient)
  {
    float f = 0.0F;
    if (paramGWindowTabDialogClient.sizeTabs() > 0) {
      GSize localGSize = paramGWindowTabDialogClient.getTab(0).getMinSize();
      f = localGSize.dy;
    }
    paramGWindowTabDialogClient.setCanvasColorWHITE();
    drawBevel(paramGWindowTabDialogClient, 0.0F, f, paramGWindowTabDialogClient.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowTabDialogClient.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f, this.jdField_bevelTabDialogClient_of_type_ComMaddoxGwindowGBevel, this.elements, false);
  }

  public void render(GWindowTabDialogClient.Tab paramTab) {
    boolean bool = paramTab.isCurrent();
    GBevel localGBevel = bool ? this.bevelTabCUR : this.bevelTab;
    paramTab.setCanvasColorWHITE();
    drawBevel(paramTab, 0.0F, 0.0F, paramTab.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramTab.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, localGBevel, this.elements, false);
    this.tabReg.x = (localGBevel.L.dx + metric(this.spaceTab));
    this.tabReg.y = (localGBevel.T.dy + metric(this.spaceTab));
    this.tabReg.dx = (paramTab.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGBevel.L.dx - localGBevel.R.dx);
    this.tabReg.dy = (paramTab.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGBevel.T.dy - localGBevel.B.dy);
    if (paramTab.pushClipRegion(this.tabReg, paramTab.jdField_bClip_of_type_Boolean, 0.0F)) {
      if (bool) {
        renderTextDialogControl(paramTab, metric(this.spaceTab), metric(this.spaceTab), this.tabReg.dx - 2.0F * metric(this.spaceTab), this.tabReg.dy - 2.0F * metric(this.spaceTab), 16777215, false);
      }
      else
      {
        renderTextDialogControl(paramTab, metric(this.spaceTab), metric(this.spaceTab), this.tabReg.dx - 2.0F * metric(this.spaceTab), this.tabReg.dy - 2.0F * metric(this.spaceTab), 0, false);
      }

      paramTab.popClip();
    }
  }

  public GSize getMinSize(GWindowTabDialogClient.Tab paramTab, GSize paramGSize)
  {
    paramGSize.dx = (paramTab.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[paramTab.jdField_font_of_type_Int].size(paramTab.jdField_cap_of_type_ComMaddoxGwindowGCaption.caption).dx + this.bevelTab.L.dx + this.bevelTab.R.dx + 2.0F * metric(this.spaceTab));

    paramGSize.dy = (paramTab.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[paramTab.jdField_font_of_type_Int].height + this.bevelTab.T.dy + this.bevelTab.B.dy + 2.0F * metric(this.spaceTab));

    return paramGSize;
  }

  public void renderComboList(GWindowComboControl paramGWindowComboControl)
  {
    GWindowComboControl.ListArea localListArea = paramGWindowComboControl.listArea;
    localListArea.setCanvasColorWHITE();
    localListArea.draw(this.bevelBlack.L.dx, this.bevelBlack.T.dy, localListArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelBlack.R.dx - this.bevelBlack.L.dx, localListArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelBlack.B.dy - this.bevelBlack.T.dy, this.elements, 5.0F, 17.0F, 1.0F, 1.0F);

    drawBevel(localListArea, 0.0F, 0.0F, localListArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, localListArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelBlack, this.elements);
    GRegion localGRegion = localListArea.getClientRegion();
    localGRegion.x += this.bevelBlack.L.dx + this.spaceComboList;
    localGRegion.y += this.bevelBlack.T.dy;
    localGRegion.dx -= this.bevelBlack.L.dx + this.bevelBlack.R.dx + 2.0F * this.spaceComboList;
    localGRegion.dy -= this.bevelBlack.T.dy + this.bevelBlack.B.dy;
    if (paramGWindowComboControl.scrollBar.isVisible())
      localGRegion.dx -= paramGWindowComboControl.scrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    if (localListArea.pushClipRegion(localGRegion, true, 0.0F)) {
      localListArea.setCanvasColorBLACK();
      localListArea.setCanvasFont(paramGWindowComboControl.jdField_font_of_type_Int);
      GFont localGFont = paramGWindowComboControl.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font;
      float f = (getComboHline() - localGFont.height) / 2.0F;
      int i = paramGWindowComboControl.listStartLine;
      for (int j = 0; j < paramGWindowComboControl.listCountLines; j++)
      {
        GSize localGSize;
        if (i == paramGWindowComboControl.listSelected) {
          localGSize = localGFont.size(paramGWindowComboControl.get(i));
          localListArea.draw(0.0F, f, localListArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, localGSize.dy, this.elements, 4.0F, 98.0F, 1.0F, 1.0F);
          localListArea.setCanvasColorWHITE();
          localListArea.draw(0.0F, f, paramGWindowComboControl.get(i));
          localListArea.setCanvasColorBLACK();
        } else if ((paramGWindowComboControl.posEnable != null) && (paramGWindowComboControl.posEnable[i] == 0)) {
          localGSize = localGFont.size(paramGWindowComboControl.get(i));
          localListArea.setCanvasColorWHITE();
          localListArea.draw(0.0F, f, localListArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, localGSize.dy, this.elements, 2.0F, 16.0F, 1.0F, 1.0F);
          localListArea.draw(1.0F, f + 1.0F, paramGWindowComboControl.get(i));
          localListArea.setCanvasColor(8355711);
          localListArea.draw(0.0F, f, paramGWindowComboControl.get(i));
          localListArea.setCanvasColorBLACK();
        } else {
          localListArea.draw(0.0F, f, paramGWindowComboControl.get(i));
        }
        i++;
        f += getComboHline();
      }
      localListArea.popClip();
    }
  }

  public void setupComboList(GWindowComboControl paramGWindowComboControl) {
    paramGWindowComboControl.listArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    paramGWindowComboControl.listCountLines = paramGWindowComboControl.listVisibleLines;
    if (paramGWindowComboControl.listCountLines > paramGWindowComboControl.size())
      paramGWindowComboControl.listCountLines = paramGWindowComboControl.size();
    paramGWindowComboControl.listArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = (paramGWindowComboControl.listCountLines * getComboHline() + this.bevelBlack.B.dy + this.bevelBlack.T.dy);

    GPoint localGPoint = paramGWindowComboControl.windowToGlobal(0.0F, paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
    if (localGPoint.y + paramGWindowComboControl.listArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dy > paramGWindowComboControl.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dy)
      paramGWindowComboControl.listArea.jdField_win_of_type_ComMaddoxGwindowGRegion.y = (-paramGWindowComboControl.listArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
    else
      paramGWindowComboControl.listArea.jdField_win_of_type_ComMaddoxGwindowGRegion.y = paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    paramGWindowComboControl.listArea.jdField_win_of_type_ComMaddoxGwindowGRegion.x = 0.0F;
    if (paramGWindowComboControl.listCountLines < paramGWindowComboControl.size()) {
      paramGWindowComboControl.scrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = getVScrollBarW();
      paramGWindowComboControl.scrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = (paramGWindowComboControl.listCountLines * getComboHline());
      paramGWindowComboControl.scrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.x = (paramGWindowComboControl.listArea.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGWindowComboControl.scrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelBlack.R.dx);
      paramGWindowComboControl.scrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.y = this.bevelBlack.T.dy;
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
    if (paramGWindowComboControl.jdField_bEnable_of_type_Boolean) {
      paramGWindowComboControl.draw(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy, paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy, this.elements, 5.0F, 17.0F, 1.0F, 1.0F);
    }
    else
    {
      paramGWindowComboControl.draw(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy, paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy, this.elements, 2.0F, 16.0F, 1.0F, 1.0F);
    }

    drawBevel(paramGWindowComboControl, 0.0F, 0.0F, paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowComboControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelDOWN, this.elements, false);
  }

  public void setupComboEditBox(GWindowEditBox paramGWindowEditBox) {
    paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.x = this.bevelDOWN.L.dx;
    paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.y = this.bevelDOWN.T.dy;
    paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = (paramGWindowEditBox.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelDOWN.L.dx - this.bevelDOWN.R.dx - getVScrollBarW());
    paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = (paramGWindowEditBox.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelDOWN.T.dy - this.bevelDOWN.B.dy);
  }

  public void setupComboButton(GWindowButtonTexture paramGWindowButtonTexture) {
    paramGWindowButtonTexture.texUP = this.SBdownButtonUP;
    paramGWindowButtonTexture.texDOWN = this.SBdownButtonDOWN;
    paramGWindowButtonTexture.texDISABLE = this.SBdownButtonDISABLE;
    paramGWindowButtonTexture.texOVER = this.SBdownButtonOVER;
    paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = getVScrollBarW();
    paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = getVScrollBarH();
    paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.x = (paramGWindowButtonTexture.jdField_parentWindow_of_type_ComMaddoxGwindowGWindow.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelDOWN.R.dx);
    paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.y = this.bevelDOWN.T.dy;
  }
  public float getComboH() {
    return getVScrollBarH() + this.bevelDOWN.B.dy + this.bevelDOWN.T.dy;
  }
  public float getComboHmetric() { return getComboH() / metric(); } 
  public float getComboHline() { return metric(1.2F);
  }

  public void render(GWindowBoxSeparate paramGWindowBoxSeparate)
  {
    paramGWindowBoxSeparate.setCanvasColorWHITE();
    if (paramGWindowBoxSeparate.exclude != null) {
      int i = (paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.y >= paramGWindowBoxSeparate.exclude.jdField_win_of_type_ComMaddoxGwindowGRegion.y) && (paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.y < paramGWindowBoxSeparate.exclude.jdField_win_of_type_ComMaddoxGwindowGRegion.y + paramGWindowBoxSeparate.exclude.jdField_win_of_type_ComMaddoxGwindowGRegion.dy) ? 1 : 0;
      float f1 = 0.0F;
      if (i != 0) {
        f2 = this.bevelSeparate.T.dx;
        this.bevelSeparate.T.dx = 0.0F;
        drawBevel(paramGWindowBoxSeparate, 0.0F, 0.0F, paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelSeparate, this.elements);
        this.bevelSeparate.T.dx = f2;
      } else {
        f2 = this.bevelSeparate.B.dx;
        this.bevelSeparate.B.dx = 0.0F;
        drawBevel(paramGWindowBoxSeparate, 0.0F, 0.0F, paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelSeparate, this.elements);
        this.bevelSeparate.B.dx = f2;
        f1 = paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 2.0F;
      }
      float f2 = paramGWindowBoxSeparate.exclude.jdField_win_of_type_ComMaddoxGwindowGRegion.x - paramGWindowBoxSeparate.border;
      if (f2 > paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.x + 2.0F)
        drawSeparateH(paramGWindowBoxSeparate, 2.0F, f1, f2 - paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.x - 2.0F);
      f2 = paramGWindowBoxSeparate.exclude.jdField_win_of_type_ComMaddoxGwindowGRegion.x + paramGWindowBoxSeparate.exclude.jdField_win_of_type_ComMaddoxGwindowGRegion.dx + paramGWindowBoxSeparate.border;
      if (f2 < paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.x + paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F)
        drawSeparateH(paramGWindowBoxSeparate, f2 - paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.x, f1, paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.x + paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F - f2);
    }
    else {
      drawBevel(paramGWindowBoxSeparate, 0.0F, 0.0F, paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowBoxSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelSeparate, this.elements);
    }
  }

  public void render(GWindowVSeparate paramGWindowVSeparate)
  {
    drawSeparateW(paramGWindowVSeparate, 0.0F, 0.0F, paramGWindowVSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
  }

  public void render(GWindowHSeparate paramGWindowHSeparate)
  {
    drawSeparateH(paramGWindowHSeparate, 0.0F, 0.0F, paramGWindowHSeparate.jdField_win_of_type_ComMaddoxGwindowGRegion.dx);
  }

  public void render(GWindowVSliderInt paramGWindowVSliderInt)
  {
    paramGWindowVSliderInt.setCanvasColorWHITE();
    paramGWindowVSliderInt.draw(0.0F, 0.0F, paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.elements, this.bevelUP.Area);
    drawSeparateW(paramGWindowVSliderInt, (int)(metric() / 2.0F), 0.0F, paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
    if ((paramGWindowVSliderInt.jdField_bEnable_of_type_Boolean) && (paramGWindowVSliderInt.isActivated())) {
      drawBevel(paramGWindowVSliderInt, 0.0F, 0.0F, paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelBlack50, this.elements);
    }
    drawBevel(paramGWindowVSliderInt, 0.0F, paramGWindowVSliderInt.yM, metric(), paramGWindowVSliderInt.dyM, this.bevelUP, this.elements);
    float f1 = (int)(metric() / 4.0F) - 1.0F;
    float f2 = (paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGWindowVSliderInt.dyM) / (paramGWindowVSliderInt.posCount - 1);
    if (f2 < 3.0F) return;
    float f3 = getVSliderIntW() - 3.0F;
    for (int i = 0; i < paramGWindowVSliderInt.posCount; i++) {
      boolean bool = paramGWindowVSliderInt.jdField_bEnable_of_type_Boolean;
      int j;
      if ((bool) && (paramGWindowVSliderInt.posEnable != null))
        j = paramGWindowVSliderInt.posEnable[i];
      if (j != 0) paramGWindowVSliderInt.draw(f3, f1, 2.0F, 2.0F, this.elements, 53.0F, 38.0F, 1.0F, 1.0F); else
        paramGWindowVSliderInt.draw(f3, f1, 2.0F, 2.0F, this.elements, 44.0F, 42.0F, 2.0F, 2.0F);
      f1 += f2;
    }
  }

  public void setupVSliderIntSizes(GWindowVSliderInt paramGWindowVSliderInt) {
    paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dx = getVSliderIntW();
    paramGWindowVSliderInt.dyM = ((int)(metric() / 4.0F) * 2);
    paramGWindowVSliderInt.yM = (paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGWindowVSliderInt.dyM - (paramGWindowVSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGWindowVSliderInt.dyM) / (paramGWindowVSliderInt.posCount - 1) * (paramGWindowVSliderInt.pos - paramGWindowVSliderInt.posStart));
  }
  public float getVSliderIntW() { return metric(1.3F); } 
  public float getVSliderIntWmetric() { return 1.3F;
  }

  public void render(GWindowHSliderInt paramGWindowHSliderInt)
  {
    paramGWindowHSliderInt.setCanvasColorWHITE();
    paramGWindowHSliderInt.draw(0.0F, 0.0F, paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.elements, this.bevelUP.Area);
    drawSeparateH(paramGWindowHSliderInt, 0.0F, paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - (int)(metric() / 2.0F), paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dx);
    if ((paramGWindowHSliderInt.jdField_bEnable_of_type_Boolean) && (paramGWindowHSliderInt.isActivated())) {
      drawBevel(paramGWindowHSliderInt, 0.0F, 0.0F, paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelBlack50, this.elements);
    }
    drawBevel(paramGWindowHSliderInt, paramGWindowHSliderInt.xM, paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - metric(), paramGWindowHSliderInt.dxM, metric(), this.bevelUP, this.elements);
    float f1 = (int)(metric() / 4.0F) - 1.0F;
    float f2 = (paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGWindowHSliderInt.dxM) / (paramGWindowHSliderInt.posCount - 1);
    if (f2 < 3.0F) return;
    for (int i = 0; i < paramGWindowHSliderInt.posCount; i++) {
      boolean bool = paramGWindowHSliderInt.jdField_bEnable_of_type_Boolean;
      int j;
      if ((bool) && (paramGWindowHSliderInt.posEnable != null))
        j = paramGWindowHSliderInt.posEnable[i];
      if (j != 0) paramGWindowHSliderInt.draw(f1, 1.0F, 2.0F, 2.0F, this.elements, 53.0F, 38.0F, 1.0F, 1.0F); else
        paramGWindowHSliderInt.draw(f1, 1.0F, 2.0F, 2.0F, this.elements, 44.0F, 42.0F, 2.0F, 2.0F);
      f1 += f2;
    }
  }

  public void setupHSliderIntSizes(GWindowHSliderInt paramGWindowHSliderInt) {
    paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dy = getHSliderIntH();
    paramGWindowHSliderInt.dxM = ((int)(metric() / 4.0F) * 2);
    paramGWindowHSliderInt.xM = ((paramGWindowHSliderInt.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGWindowHSliderInt.dxM) / (paramGWindowHSliderInt.posCount - 1) * (paramGWindowHSliderInt.pos - paramGWindowHSliderInt.posStart));
  }

  public float getHSliderIntH() {
    return metric(1.3F);
  }
  public float getHSliderIntHmetric() {
    return 1.3F;
  }

  public void render(GWindowEditNumber paramGWindowEditNumber)
  {
    if (paramGWindowEditNumber.jdField_bEnable_of_type_Boolean) paramGWindowEditNumber.setCanvasColor(paramGWindowEditNumber.jdField_color_of_type_Int ^ 0xFFFFFFFF); else
      paramGWindowEditNumber.setCanvasColorWHITE();
    float f = paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    if (paramGWindowEditNumber.bar.isVisible())
      f -= getVScrollBarW();
    paramGWindowEditNumber.draw(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy, f - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy, this.elements, 5.0F, 17.0F, 1.0F, 1.0F);

    paramGWindowEditNumber.setCanvasColorWHITE();
    drawBevel(paramGWindowEditNumber, 0.0F, 0.0F, f, paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelDOWN, this.elements, false);
  }

  public void setupEditNumber(GWindowEditNumber paramGWindowEditNumber) {
    if (paramGWindowEditNumber.bar.isVisible()) {
      paramGWindowEditNumber.box.setPos(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy);
      paramGWindowEditNumber.box.setSize(paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx - getVScrollBarW(), paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy);

      paramGWindowEditNumber.bar.setPos(paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - getVScrollBarW(), 0.0F);
      paramGWindowEditNumber.bar.setSize(getVScrollBarW(), paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dy);
    }
    else {
      paramGWindowEditNumber.box.setPos(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy);
      paramGWindowEditNumber.box.setSize(paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowEditNumber.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy);
    }
  }

  public void render(GWindowEditControl paramGWindowEditControl)
  {
    if (paramGWindowEditControl.jdField_bEnable_of_type_Boolean) paramGWindowEditControl.setCanvasColor(paramGWindowEditControl.jdField_color_of_type_Int ^ 0xFFFFFFFF); else
      paramGWindowEditControl.setCanvasColorWHITE();
    paramGWindowEditControl.draw(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy, paramGWindowEditControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowEditControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy, this.elements, 5.0F, 17.0F, 1.0F, 1.0F);

    paramGWindowEditControl.setCanvasColorWHITE();
    drawBevel(paramGWindowEditControl, 0.0F, 0.0F, paramGWindowEditControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowEditControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelDOWN, this.elements, false);
    render(paramGWindowEditControl, this.bevelDOWN.L.dx);
  }

  public void render(GWindowEditBox paramGWindowEditBox, float paramFloat)
  {
    if ((paramGWindowEditBox.value == null) || (paramGWindowEditBox.value.length() == 0)) {
      if ((paramGWindowEditBox.jdField_bEnable_of_type_Boolean) && (paramGWindowEditBox.bCanEdit) && (paramGWindowEditBox.bShowCaret)) {
        paramGWindowEditBox.setCanvasFont(paramGWindowEditBox.jdField_font_of_type_Int);
        paramGWindowEditBox.setCanvasColor(paramGWindowEditBox.jdField_color_of_type_Int);
        paramGWindowEditBox.draw(paramFloat, (paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGWindowEditBox.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font.height) / 2.0F, "|");
      }
      return;
    }
    String str = paramGWindowEditBox.value.toString();
    if (paramGWindowEditBox.bPassword) {
      localObject = new StringBuffer(str.length());
      for (int i = 0; i < str.length(); i++)
        ((StringBuffer)localObject).append('*');
      str = ((StringBuffer)localObject).toString();
    }
    paramGWindowEditBox.setCanvasFont(paramGWindowEditBox.jdField_font_of_type_Int);
    Object localObject = paramGWindowEditBox.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font;
    GSize localGSize = ((GFont)localObject).size("|");
    float f1 = localGSize.dx;
    localGSize = ((GFont)localObject).size(str);
    float f2 = (paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - localGSize.dy) / 2.0F;
    float f3 = paramFloat;
    float f4 = 0.0F;
    if ((localGSize.dx + 2.0F * f1 >= paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * paramFloat) && (paramGWindowEditBox.jdField_bEnable_of_type_Boolean) && (paramGWindowEditBox.bCanEdit)) {
      if (paramGWindowEditBox.caretOffset > 0) {
        localGSize = ((GFont)localObject).size(str, 0, paramGWindowEditBox.caretOffset);
        f4 = localGSize.dx + 2.0F * f1;
      }
      if (f4 > paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * paramFloat)
        f3 -= f4 - (paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * paramFloat);
    }
    else if (paramGWindowEditBox.jdField_align_of_type_Int == 2) {
      f3 = paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramFloat - localGSize.dx;
    } else if (paramGWindowEditBox.jdField_align_of_type_Int == 1) {
      f3 = (paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - localGSize.dx) / 2.0F;
    }

    _editBoxReg.x = paramFloat;
    _editBoxReg.y = 0.0F;
    _editBoxReg.dx = (paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * paramFloat);
    _editBoxReg.dy = paramGWindowEditBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    f3 -= paramFloat;
    if (paramGWindowEditBox.pushClipRegion(_editBoxReg, paramGWindowEditBox.jdField_bClip_of_type_Boolean, 0.0F)) {
      if (paramGWindowEditBox.jdField_bEnable_of_type_Boolean) {
        if ((paramGWindowEditBox.bAllSelected) || ((!paramGWindowEditBox.bCanEdit) && (paramGWindowEditBox.isKeyFocus()))) {
          paramGWindowEditBox.setCanvasColor(paramGWindowEditBox.jdField_color_of_type_Int);
          localGSize = ((GFont)localObject).size(str);
          paramGWindowEditBox.draw(f3, f2, localGSize.dx + f1, localGSize.dy, this.elements, 4.0F, 98.0F, 1.0F, 1.0F);
          paramGWindowEditBox.setCanvasColor(0xFFFFFF ^ paramGWindowEditBox.jdField_color_of_type_Int);
        } else {
          paramGWindowEditBox.setCanvasColor(paramGWindowEditBox.jdField_color_of_type_Int);
        }
        paramGWindowEditBox.draw(f3, f2, str);
        if (paramGWindowEditBox.bShowCaret) {
          localGSize = ((GFont)localObject).size(str, 0, paramGWindowEditBox.caretOffset);
          paramGWindowEditBox.draw(f3 + localGSize.dx, f2, "|");
        }
      }
      else {
        paramGWindowEditBox.setCanvasColor(16777215);
        paramGWindowEditBox.draw(f3 + 1.0F, f2 + 1.0F, str);

        paramGWindowEditBox.setCanvasColor(8355711);
        paramGWindowEditBox.draw(f3, f2, str);
      }
      paramGWindowEditBox.popClip();
    }
  }

  public void render(GWindowEditText paramGWindowEditText)
  {
    paramGWindowEditText.setCanvasFont(paramGWindowEditText.jdField_font_of_type_Int);
    GFont localGFont = paramGWindowEditText.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.font;
    GSize localGSize = localGFont.size("|");
    float f1 = -localGSize.dx / 2.0F;

    if (paramGWindowEditText.text.size() == 0) {
      if (paramGWindowEditText.bShowCaret) {
        paramGWindowEditText.setCanvasFont(paramGWindowEditText.jdField_font_of_type_Int);
        paramGWindowEditText.setCanvasColor(paramGWindowEditText.jdField_color_of_type_Int);
        paramGWindowEditText.draw(0.0F, 0.0F, "|");
      }
      return;
    }

    paramGWindowEditText.setCanvasColor(paramGWindowEditText.jdField_color_of_type_Int);
    float f2 = localGFont.height;
    int i = paramGWindowEditText.textPos.size();
    int j = 0;
    Object localObject2;
    Object localObject4;
    if ((paramGWindowEditText.isSelected()) && (!paramGWindowEditText.posCaret.isEqual(paramGWindowEditText.posSelect))) {
      Object localObject1 = paramGWindowEditText.posSelect;
      localObject2 = paramGWindowEditText.posCaret;
      if (((GWindowEditText.Pos)localObject2).isEqual((GWindowEditText.Pos)localObject1)) return;
      if (((GWindowEditText.Pos)localObject2).isLess((GWindowEditText.Pos)localObject1)) { Object localObject3 = localObject1; localObject1 = localObject2; localObject2 = localObject3; }
      for (int m = 0; m < i; m++) {
        localObject4 = paramGWindowEditText.itemPos(m);
        GWindowEditText.Pos localPos = GWindowEditText._tmpPos;
        localPos.set(((GWindowEditText.PosLen)localObject4).jdField_item_of_type_Int, ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int + ((GWindowEditText.PosLen)localObject4).len);
        StringBuffer localStringBuffer2 = paramGWindowEditText.item(((GWindowEditText.PosLen)localObject4).jdField_item_of_type_Int);
        char[] arrayOfChar = GWindowEditText._getArrayRenderBuffer(((GWindowEditText.PosLen)localObject4).len);
        localStringBuffer2.getChars(((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int, ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int + ((GWindowEditText.PosLen)localObject4).len, arrayOfChar, 0);
        float f4 = 0.0F;
        float f5 = m * f2;
        if ((((GWindowEditText.PosLen)localObject4).isLess((GWindowEditText.Pos)localObject2)) && (((GWindowEditText.Pos)localObject1).isLess(localPos))) {
          int n = ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int;
          int i1 = localPos.jdField_ofs_of_type_Int;
          if (((GWindowEditText.PosLen)localObject4).isLess((GWindowEditText.Pos)localObject1)) {
            n = ((GWindowEditText.Pos)localObject1).jdField_ofs_of_type_Int;
          }
          if (((GWindowEditText.Pos)localObject2).isLess(localPos)) {
            i1 = ((GWindowEditText.Pos)localObject2).jdField_ofs_of_type_Int;
          }
          if (n != ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int) {
            localGSize = localGFont.size(arrayOfChar, 0, n - ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int);
            paramGWindowEditText.draw(f4, f5, localGSize.dx, f2, 0, arrayOfChar, 0, n - ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int);
            f4 += localGSize.dx;
          }

          localGSize = localGFont.size(arrayOfChar, n - ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int, i1 - n);
          paramGWindowEditText.draw(f4, f5, localGSize.dx, f2, this.elements, 4.0F, 98.0F, 1.0F, 1.0F);
          paramGWindowEditText.setCanvasColor(0xFFFFFF ^ paramGWindowEditText.jdField_color_of_type_Int);
          paramGWindowEditText.draw(f4, f5, localGSize.dx, f2, 0, arrayOfChar, n - ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int, i1 - n);
          paramGWindowEditText.setCanvasColor(paramGWindowEditText.jdField_color_of_type_Int);
          f4 += localGSize.dx;

          if (i1 != localPos.jdField_ofs_of_type_Int) {
            localGSize = localGFont.size(arrayOfChar, i1 - ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int, localPos.jdField_ofs_of_type_Int - i1);
            paramGWindowEditText.draw(f4, f5, localGSize.dx, f2, 0, arrayOfChar, i1 - ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int, localPos.jdField_ofs_of_type_Int - i1);
          }
        }
        else {
          paramGWindowEditText.draw(f4, f5, paramGWindowEditText.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f2, 0, arrayOfChar, 0, ((GWindowEditText.PosLen)localObject4).len);
        }

        if ((j != 0) || (!paramGWindowEditText.bShowCaret) || (((GWindowEditText.PosLen)localObject4).jdField_item_of_type_Int != paramGWindowEditText.posCaret.jdField_item_of_type_Int) || (((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int > paramGWindowEditText.posCaret.jdField_ofs_of_type_Int) || (paramGWindowEditText.posCaret.jdField_ofs_of_type_Int > ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int + ((GWindowEditText.PosLen)localObject4).len))
          continue;
        j = 1;
        localGSize = localGFont.size(arrayOfChar, 0, paramGWindowEditText.posCaret.jdField_ofs_of_type_Int - ((GWindowEditText.PosLen)localObject4).jdField_ofs_of_type_Int);
        paramGWindowEditText.draw(localGSize.dx + f1, f5, "|");
      }
    }
    else
    {
      for (int k = 0; k < i; k++) {
        localObject2 = paramGWindowEditText.itemPos(k);
        StringBuffer localStringBuffer1 = paramGWindowEditText.item(((GWindowEditText.PosLen)localObject2).jdField_item_of_type_Int);
        localObject4 = GWindowEditText._getArrayRenderBuffer(((GWindowEditText.PosLen)localObject2).len);
        localStringBuffer1.getChars(((GWindowEditText.PosLen)localObject2).jdField_ofs_of_type_Int, ((GWindowEditText.PosLen)localObject2).jdField_ofs_of_type_Int + ((GWindowEditText.PosLen)localObject2).len, localObject4, 0);

        float f3 = k * f2;
        paramGWindowEditText.draw(0.0F, f3, paramGWindowEditText.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f2, 0, localObject4, 0, ((GWindowEditText.PosLen)localObject2).len);

        if ((j != 0) || (!paramGWindowEditText.bShowCaret) || (((GWindowEditText.PosLen)localObject2).jdField_item_of_type_Int != paramGWindowEditText.posCaret.jdField_item_of_type_Int) || (((GWindowEditText.PosLen)localObject2).jdField_ofs_of_type_Int > paramGWindowEditText.posCaret.jdField_ofs_of_type_Int) || (paramGWindowEditText.posCaret.jdField_ofs_of_type_Int > ((GWindowEditText.PosLen)localObject2).jdField_ofs_of_type_Int + ((GWindowEditText.PosLen)localObject2).len))
          continue;
        j = 1;
        localGSize = localGFont.size(localObject4, 0, paramGWindowEditText.posCaret.jdField_ofs_of_type_Int - ((GWindowEditText.PosLen)localObject2).jdField_ofs_of_type_Int);
        paramGWindowEditText.draw(localGSize.dx + f1, f3, "|");
      }
    }
  }

  public void render(GWindowEditTextControl paramGWindowEditTextControl)
  {
    if (paramGWindowEditTextControl.jdField_bEnable_of_type_Boolean) paramGWindowEditTextControl.setCanvasColor(paramGWindowEditTextControl.jdField_color_of_type_Int ^ 0xFFFFFFFF); else
      paramGWindowEditTextControl.setCanvasColorWHITE();
    paramGWindowEditTextControl.draw(this.bevelDOWN.L.dx, this.bevelDOWN.T.dy, paramGWindowEditTextControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelDOWN.R.dx - this.bevelDOWN.L.dx, paramGWindowEditTextControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelDOWN.B.dy - this.bevelDOWN.T.dy, this.elements, 5.0F, 17.0F, 1.0F, 1.0F);

    paramGWindowEditTextControl.setCanvasColorWHITE();
    drawBevel(paramGWindowEditTextControl, 0.0F, 0.0F, paramGWindowEditTextControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowEditTextControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelDOWN, this.elements, false);
  }
  public float getBorderSizeEditTextControl() {
    return this.bevelDOWN.L.dx;
  }

  public void render(GWindowCheckBox paramGWindowCheckBox)
  {
    paramGWindowCheckBox.setCanvasColorWHITE();
    if (paramGWindowCheckBox.bChecked) {
      if (paramGWindowCheckBox.jdField_bEnable_of_type_Boolean) paramGWindowCheckBox.draw(0.0F, 0.0F, paramGWindowCheckBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowCheckBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.checkBoxCheckEnable); else
        paramGWindowCheckBox.draw(0.0F, 0.0F, paramGWindowCheckBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowCheckBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.checkBoxCheckDisable);
    }
    else if (paramGWindowCheckBox.jdField_bEnable_of_type_Boolean) paramGWindowCheckBox.draw(0.0F, 0.0F, paramGWindowCheckBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowCheckBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.checkBoxUnCheckEnable); else
      paramGWindowCheckBox.draw(0.0F, 0.0F, paramGWindowCheckBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowCheckBox.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.checkBoxUnCheckDisable);
  }

  public GSize getMinSize(GWindowCheckBox paramGWindowCheckBox, GSize paramGSize) {
    paramGSize.dx = metric();
    paramGSize.dy = metric();
    return paramGSize;
  }

  public void render(GWindowVScrollBar paramGWindowVScrollBar)
  {
    float f1 = paramGWindowVScrollBar.yM - paramGWindowVScrollBar.uButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    float f2 = paramGWindowVScrollBar.dButton.jdField_win_of_type_ComMaddoxGwindowGRegion.y - paramGWindowVScrollBar.yM - paramGWindowVScrollBar.dyM;
    if ((f1 < 0.0F) && (f2 < 0.0F)) return;
    paramGWindowVScrollBar.setCanvasColorWHITE();

    if (f1 > 0.0F) {
      if (paramGWindowVScrollBar.downState == 1)
        paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.uButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, paramGWindowVScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, this.elements, 4.0F, 98.0F, 1.0F, 1.0F);
      else
        paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.uButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, paramGWindowVScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f1, this.elements, 19.0F, 64.0F, 11.0F, 1.0F);
    }
    if (f2 > 0.0F)
      if (paramGWindowVScrollBar.downState == 2)
        paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.yM + paramGWindowVScrollBar.dyM, paramGWindowVScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f2, this.elements, 4.0F, 98.0F, 1.0F, 1.0F);
      else
        paramGWindowVScrollBar.draw(0.0F, paramGWindowVScrollBar.yM + paramGWindowVScrollBar.dyM, paramGWindowVScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f2, this.elements, 19.0F, 64.0F, 11.0F, 1.0F);
  }

  public void setupVScrollBarSizes(GWindowVScrollBar paramGWindowVScrollBar)
  {
    float f1 = paramGWindowVScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 2.0F * getVScrollBarH();
    int i = f1 >= 2.0F * metric(this.minScrollMSize) ? 1 : 0;
    float f2 = getVScrollBarH();
    if (f1 <= 0.0F) {
      f2 = paramGWindowVScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F;
    }
    paramGWindowVScrollBar.uButton.setSize(getVScrollBarW(), f2);
    paramGWindowVScrollBar.uButton.setPos(0.0F, 0.0F);
    paramGWindowVScrollBar.dButton.setSize(getVScrollBarW(), f2);
    paramGWindowVScrollBar.dButton.setPos(0.0F, paramGWindowVScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f2);

    if (paramGWindowVScrollBar.posMin >= paramGWindowVScrollBar.posMax) {
      if (i != 0) {
        paramGWindowVScrollBar.mButton.setSize(getVScrollBarW(), f1);
        paramGWindowVScrollBar.mButton.setPos(0.0F, f2);
        if (!paramGWindowVScrollBar.mButton.isVisible())
          paramGWindowVScrollBar.mButton.showWindow();
        paramGWindowVScrollBar.yM = f2;
        paramGWindowVScrollBar.dyM = f1;
      } else {
        paramGWindowVScrollBar.mButton.hideWindow();
        paramGWindowVScrollBar.yM = f2;
        paramGWindowVScrollBar.dyM = 0.0F;
      }
    } else {
      paramGWindowVScrollBar.yM = (paramGWindowVScrollBar.pos / (paramGWindowVScrollBar.posMax + paramGWindowVScrollBar.posVisible - paramGWindowVScrollBar.posMin) * f1);
      paramGWindowVScrollBar.dyM = (paramGWindowVScrollBar.posVisible / (paramGWindowVScrollBar.posMax + paramGWindowVScrollBar.posVisible - paramGWindowVScrollBar.posMin) * f1);
      if (paramGWindowVScrollBar.yM + paramGWindowVScrollBar.dyM > f1) {
        paramGWindowVScrollBar.yM = (f1 - paramGWindowVScrollBar.dyM);
      }
      if (paramGWindowVScrollBar.yM < 0.0F) {
        paramGWindowVScrollBar.dyM += paramGWindowVScrollBar.yM;
        if (paramGWindowVScrollBar.dyM < 0.0F) paramGWindowVScrollBar.dyM = 0.0F;
        paramGWindowVScrollBar.yM = 0.0F;
      }
      if (i != 0) {
        if (paramGWindowVScrollBar.dyM < metric(this.minScrollMSize)) {
          float f3 = metric(this.minScrollMSize) - paramGWindowVScrollBar.dyM;
          paramGWindowVScrollBar.yM -= f3;
          paramGWindowVScrollBar.dyM = metric(this.minScrollMSize);
          if (paramGWindowVScrollBar.yM < 0.0F) paramGWindowVScrollBar.yM = 0.0F;
        }
        paramGWindowVScrollBar.mButton.setSize(getVScrollBarW(), paramGWindowVScrollBar.dyM);
        paramGWindowVScrollBar.mButton.setPos(0.0F, paramGWindowVScrollBar.yM + f2);
        if (!paramGWindowVScrollBar.mButton.isVisible())
          paramGWindowVScrollBar.mButton.showWindow();
      } else {
        paramGWindowVScrollBar.mButton.hideWindow();
      }
      paramGWindowVScrollBar.yM += f2;
    }
  }

  public void render(GWindowHScrollBar paramGWindowHScrollBar) {
    float f1 = paramGWindowHScrollBar.xM - paramGWindowHScrollBar.lButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx;
    float f2 = paramGWindowHScrollBar.rButton.jdField_win_of_type_ComMaddoxGwindowGRegion.x - paramGWindowHScrollBar.xM - paramGWindowHScrollBar.dxM;
    if ((f1 < 0.0F) && (f2 < 0.0F)) return;
    paramGWindowHScrollBar.setCanvasColorWHITE();

    if (f1 > 0.0F) {
      if (paramGWindowHScrollBar.downState == 1)
        paramGWindowHScrollBar.draw(paramGWindowHScrollBar.lButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, 0.0F, f1, paramGWindowHScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.elements, 4.0F, 98.0F, 1.0F, 1.0F);
      else
        paramGWindowHScrollBar.draw(paramGWindowHScrollBar.lButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, 0.0F, f1, paramGWindowHScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.elements, 22.0F, 60.0F, 1.0F, 10.0F);
    }
    if (f2 > 0.0F)
      if (paramGWindowHScrollBar.downState == 2)
        paramGWindowHScrollBar.draw(paramGWindowHScrollBar.xM + paramGWindowHScrollBar.dxM, 0.0F, f2, paramGWindowHScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.elements, 4.0F, 98.0F, 1.0F, 1.0F);
      else
        paramGWindowHScrollBar.draw(paramGWindowHScrollBar.xM + paramGWindowHScrollBar.dxM, 0.0F, f2, paramGWindowHScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.elements, 22.0F, 60.0F, 1.0F, 10.0F);
  }

  public void setupHScrollBarSizes(GWindowHScrollBar paramGWindowHScrollBar)
  {
    float f1 = paramGWindowHScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * getHScrollBarW();
    int i = f1 >= 2.0F * metric(this.minScrollMSize) ? 1 : 0;
    float f2 = getHScrollBarW();
    if (f1 <= 0.0F) {
      f2 = paramGWindowHScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / 2.0F;
    }
    paramGWindowHScrollBar.lButton.setSize(f2, getHScrollBarH());
    paramGWindowHScrollBar.lButton.setPos(0.0F, 0.0F);
    paramGWindowHScrollBar.rButton.setSize(f2, getHScrollBarH());
    paramGWindowHScrollBar.rButton.setPos(paramGWindowHScrollBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - f2, 0.0F);

    if (paramGWindowHScrollBar.posMin >= paramGWindowHScrollBar.posMax) {
      if (i != 0) {
        paramGWindowHScrollBar.mButton.setSize(f1, getHScrollBarH());
        paramGWindowHScrollBar.mButton.setPos(f2, 0.0F);
        if (!paramGWindowHScrollBar.mButton.isVisible())
          paramGWindowHScrollBar.mButton.showWindow();
        paramGWindowHScrollBar.xM = f2;
        paramGWindowHScrollBar.dxM = f1;
      } else {
        paramGWindowHScrollBar.mButton.hideWindow();
        paramGWindowHScrollBar.xM = f2;
        paramGWindowHScrollBar.dxM = 0.0F;
      }
    } else {
      paramGWindowHScrollBar.xM = (paramGWindowHScrollBar.pos / (paramGWindowHScrollBar.posMax + paramGWindowHScrollBar.posVisible - paramGWindowHScrollBar.posMin) * f1);
      paramGWindowHScrollBar.dxM = (paramGWindowHScrollBar.posVisible / (paramGWindowHScrollBar.posMax + paramGWindowHScrollBar.posVisible - paramGWindowHScrollBar.posMin) * f1);
      if (paramGWindowHScrollBar.xM + paramGWindowHScrollBar.dxM > f1) {
        paramGWindowHScrollBar.xM = (f1 - paramGWindowHScrollBar.dxM);
      }
      if (paramGWindowHScrollBar.xM < 0.0F) {
        paramGWindowHScrollBar.dxM += paramGWindowHScrollBar.xM;
        if (paramGWindowHScrollBar.dxM < 0.0F) paramGWindowHScrollBar.dxM = 0.0F;
        paramGWindowHScrollBar.xM = 0.0F;
      }
      if (i != 0) {
        if (paramGWindowHScrollBar.dxM < metric(this.minScrollMSize)) {
          float f3 = metric(this.minScrollMSize) - paramGWindowHScrollBar.dxM;
          paramGWindowHScrollBar.xM -= f3;
          paramGWindowHScrollBar.dxM = metric(this.minScrollMSize);
          if (paramGWindowHScrollBar.xM < 0.0F) paramGWindowHScrollBar.xM = 0.0F;
        }
        paramGWindowHScrollBar.mButton.setSize(paramGWindowHScrollBar.dxM, getHScrollBarH());
        paramGWindowHScrollBar.mButton.setPos(paramGWindowHScrollBar.xM + f2, 0.0F);
        if (!paramGWindowHScrollBar.mButton.isVisible())
          paramGWindowHScrollBar.mButton.showWindow();
      } else {
        paramGWindowHScrollBar.mButton.hideWindow();
      }
      paramGWindowHScrollBar.xM += f2;
    }
  }

  public float getHScrollBarW() {
    return metric(1.2F); } 
  public float getHScrollBarH() { return metric(1.2F); } 
  public float getVScrollBarW() { return metric(1.2F); } 
  public float getVScrollBarH() { return metric(); } 
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

  public void render(GWindowLabel paramGWindowLabel)
  {
    if (!paramGWindowLabel.jdField_bEnable_of_type_Boolean) {
      localGRegion = paramGWindowLabel.getClientRegion();
      f1 = localGRegion.dx;
      f2 = localGRegion.dy;
      if (paramGWindowLabel.pushClipRegion(localGRegion, paramGWindowLabel.jdField_bClip_of_type_Boolean, 0.0F)) {
        renderTextDialogControl(paramGWindowLabel, 1.0F, 1.0F, f1, f2, 16777215, false);
        renderTextDialogControl(paramGWindowLabel, 0.0F, 0.0F, f1, f2, 8355711, false);
        paramGWindowLabel.popClip();
      }
      return;
    }
    if (paramGWindowLabel.isActivated()) {
      paramGWindowLabel.setCanvasColorWHITE();
      drawBevel(paramGWindowLabel, 0.0F, 0.0F, paramGWindowLabel.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowLabel.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelBlack50, this.elements);
    }
    GRegion localGRegion = paramGWindowLabel.getClientRegion();
    float f1 = localGRegion.dx;
    float f2 = localGRegion.dy;
    if (paramGWindowLabel.pushClipRegion(localGRegion, paramGWindowLabel.jdField_bClip_of_type_Boolean, 0.0F)) {
      renderTextDialogControl(paramGWindowLabel, 0.0F, 0.0F, f1, f2, paramGWindowLabel.jdField_color_of_type_Int, paramGWindowLabel.isDefault());
      paramGWindowLabel.popClip();
    }
  }

  public GSize getMinSize(GWindowLabel paramGWindowLabel, GSize paramGSize) {
    paramGSize = getMinSizeDialogControl(paramGWindowLabel, paramGSize);
    paramGSize.dy += 2.0F * metric(this.spaceLabel);
    return paramGSize;
  }
  public GRegion getClientRegion(GWindowLabel paramGWindowLabel, GRegion paramGRegion, float paramFloat) {
    return getClientRegionDialogControl(paramGWindowLabel, paramGRegion, paramFloat);
  }

  public void render(GWindowButton paramGWindowButton)
  {
    paramGWindowButton.setCanvasColorWHITE();
    GRegion localGRegion;
    float f1;
    float f2;
    if (!paramGWindowButton.jdField_bEnable_of_type_Boolean) {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelUP, this.elements);
      if (paramGWindowButton.jdField_cap_of_type_ComMaddoxGwindowGCaption != null) {
        localGRegion = paramGWindowButton.getClientRegion();
        f1 = localGRegion.dx;
        f2 = localGRegion.dy;
        if (paramGWindowButton.pushClipRegion(localGRegion, paramGWindowButton.jdField_bClip_of_type_Boolean, 0.0F)) {
          renderTextDialogControl(paramGWindowButton, 1.0F, 1.0F, f1, f2, 16777215, false);
          renderTextDialogControl(paramGWindowButton, 0.0F, 0.0F, f1, f2, 8355711, false);
          paramGWindowButton.popClip();
        }
      }
      return;
    }
    if ((paramGWindowButton.jdField_bDown_of_type_Boolean) && (!paramGWindowButton.bDrawOnlyUP))
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelDOWN, this.elements);
    else {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelUP, this.elements);
    }
    if ((paramGWindowButton.bDrawActive) && (paramGWindowButton.isActivated())) {
      drawBevel(paramGWindowButton, 0.0F, 0.0F, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelBlack, this.elements);
      if (paramGWindowButton.jdField_bDown_of_type_Boolean)
        drawBevel(paramGWindowButton, 4.0F, 4.0F, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 6.0F, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 6.0F, this.bevelBlack50, this.elements);
      else {
        drawBevel(paramGWindowButton, 3.0F, 3.0F, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 6.0F, paramGWindowButton.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 6.0F, this.bevelBlack50, this.elements);
      }
    }
    if (paramGWindowButton.jdField_cap_of_type_ComMaddoxGwindowGCaption != null) {
      localGRegion = paramGWindowButton.getClientRegion();
      f1 = localGRegion.dx;
      f2 = localGRegion.dy;
      if (paramGWindowButton.pushClipRegion(localGRegion, paramGWindowButton.jdField_bClip_of_type_Boolean, 0.0F)) {
        if (paramGWindowButton.jdField_bDown_of_type_Boolean) renderTextDialogControl(paramGWindowButton, 1.0F, 1.0F, f1, f2, paramGWindowButton.jdField_color_of_type_Int, paramGWindowButton.isDefault()); else
          renderTextDialogControl(paramGWindowButton, 0.0F, 0.0F, f1, f2, paramGWindowButton.jdField_color_of_type_Int, paramGWindowButton.isDefault());
        paramGWindowButton.popClip();
      }
    }
  }

  public void render(GWindowButtonTexture paramGWindowButtonTexture) {
    paramGWindowButtonTexture.setCanvasColorWHITE();
    if (paramGWindowButtonTexture.jdField_bEnable_of_type_Boolean) {
      if (paramGWindowButtonTexture.isMouseDown(0)) {
        if (paramGWindowButtonTexture.bStrech) paramGWindowButtonTexture.draw(0.0F, 0.0F, paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, paramGWindowButtonTexture.texDOWN); else
          paramGWindowButtonTexture.draw(0.0F, 0.0F, paramGWindowButtonTexture.texDOWN);
      } else if (paramGWindowButtonTexture.isMouseOver()) {
        if (paramGWindowButtonTexture.bStrech) paramGWindowButtonTexture.draw(0.0F, 0.0F, paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, paramGWindowButtonTexture.texOVER); else
          paramGWindowButtonTexture.draw(0.0F, 0.0F, paramGWindowButtonTexture.texOVER);
      }
      else if (paramGWindowButtonTexture.bStrech) paramGWindowButtonTexture.draw(0.0F, 0.0F, paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, paramGWindowButtonTexture.texUP); else {
        paramGWindowButtonTexture.draw(0.0F, 0.0F, paramGWindowButtonTexture.texUP);
      }
    }
    else if (paramGWindowButtonTexture.bStrech) paramGWindowButtonTexture.draw(0.0F, 0.0F, paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowButtonTexture.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, paramGWindowButtonTexture.texDISABLE); else
      paramGWindowButtonTexture.draw(0.0F, 0.0F, paramGWindowButtonTexture.texDISABLE);
  }

  public GSize getMinSize(GWindowButton paramGWindowButton, GSize paramGSize)
  {
    paramGSize = getMinSizeDialogControl(paramGWindowButton, paramGSize);
    paramGSize.dx += this.bevelUP.L.dx + this.bevelUP.R.dx + 2.0F * metric(this.spaceButton);
    paramGSize.dy += this.bevelUP.T.dy + this.bevelUP.B.dy + 2.0F * metric(this.spaceButton);
    return paramGSize;
  }

  public GRegion getClientRegion(GWindowButton paramGWindowButton, GRegion paramGRegion, float paramFloat) {
    paramGRegion = getClientRegionDialogControl(paramGWindowButton, paramGRegion, paramFloat);
    paramGRegion.x += this.bevelUP.L.dx;
    paramGRegion.y += this.bevelUP.T.dy;
    paramGRegion.dx -= this.bevelUP.L.dx + this.bevelUP.R.dx;
    paramGRegion.dy -= this.bevelUP.T.dy + this.bevelUP.B.dy;
    return paramGRegion;
  }

  public void renderTextDialogControl(GWindowDialogControl paramGWindowDialogControl, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt, boolean paramBoolean)
  {
    int i = paramGWindowDialogControl.jdField_font_of_type_Int;
    if ((paramBoolean) && ((i & 0x1) == 0)) i++;
    GFont localGFont = paramGWindowDialogControl.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[i];
    paramGWindowDialogControl.setCanvasColor(paramInt);
    GSize localGSize = localGFont.size(paramGWindowDialogControl.jdField_cap_of_type_ComMaddoxGwindowGCaption.caption);
    paramFloat2 += (paramFloat4 - localGSize.dy) / 2.0F;
    if (paramGWindowDialogControl.jdField_align_of_type_Int == 2)
      paramFloat1 += paramFloat3 - localGSize.dx;
    else if (paramGWindowDialogControl.jdField_align_of_type_Int == 1) {
      paramFloat1 += (paramFloat3 - localGSize.dx) / 2.0F;
    }
    paramGWindowDialogControl.jdField_cap_of_type_ComMaddoxGwindowGCaption.draw(paramGWindowDialogControl, paramFloat1, paramFloat2, localGFont);
  }
  public GSize getMinSizeDialogControl(GWindowDialogControl paramGWindowDialogControl, GSize paramGSize) {
    if (paramGWindowDialogControl.jdField_cap_of_type_ComMaddoxGwindowGCaption != null) {
      paramGSize.dx = paramGWindowDialogControl.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[paramGWindowDialogControl.jdField_font_of_type_Int].size(paramGWindowDialogControl.jdField_cap_of_type_ComMaddoxGwindowGCaption.caption).dx;
      paramGSize.dy = paramGWindowDialogControl.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[paramGWindowDialogControl.jdField_font_of_type_Int].height;
    } else {
      paramGSize.dx = 1.0F;
      paramGSize.dy = 1.0F;
    }
    return paramGSize;
  }
  public GRegion getClientRegionDialogControl(GWindowDialogControl paramGWindowDialogControl, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = paramFloat;
    paramGRegion.y = paramFloat;
    paramGRegion.dx = (paramGWindowDialogControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * paramFloat);
    paramGRegion.dy = (paramGWindowDialogControl.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 2.0F * paramFloat);
    return paramGRegion;
  }

  public void render(GWindowClient paramGWindowClient)
  {
    paramGWindowClient.setCanvasColorWHITE();
    paramGWindowClient.draw(0.0F, 0.0F, paramGWindowClient.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowClient.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.elements, this.bevelUP.Area);
  }

  public void setupFrameCloseBox(GWindowFrameCloseBox paramGWindowFrameCloseBox)
  {
    paramGWindowFrameCloseBox.texUP = this.closeBoxUP;
    paramGWindowFrameCloseBox.texDOWN = this.closeBoxDOWN;
    paramGWindowFrameCloseBox.texDISABLE = this.closeBoxDISABLE;
    paramGWindowFrameCloseBox.texOVER = this.closeBoxOVER;
  }

  public void frameSetCloseBoxPos(GWindowFramed paramGWindowFramed) {
    float f = metric();
    if (f > paramGWindowFramed.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[1].height)
      f = paramGWindowFramed.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[1].height;
    paramGWindowFramed.closeBox.setPos(paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelTitleActive.R.dx - f - metric(this.spaceFramedTitle), this.bevelTitleActive.T.dy + metric(this.spaceFramedTitle));

    paramGWindowFramed.closeBox.setSize(f, f);
  }

  public int frameHitTest(GWindowFramed paramGWindowFramed, float paramFloat1, float paramFloat2) {
    GRegion localGRegion = paramGWindowFramed.getClientRegion();
    if ((paramFloat1 < 0.0F) || (paramFloat1 > paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dx) || (paramFloat2 < 0.0F) || (paramFloat2 > paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dy))
    {
      return 0;
    }if ((paramFloat1 >= localGRegion.x) && (paramFloat1 < localGRegion.x + localGRegion.dx) && (paramFloat2 >= localGRegion.y) && (paramFloat2 < localGRegion.y + localGRegion.dy))
    {
      return 0;
    }if (paramFloat1 < localGRegion.x) {
      if (paramFloat2 < this.bevelTitleActive.T.dy) return 1;
      if (paramFloat2 >= localGRegion.y + localGRegion.dy) return 6;
      return 4;
    }if (paramFloat1 >= localGRegion.x + localGRegion.dx) {
      if (paramFloat2 < this.bevelTitleActive.T.dy) return 3;
      if (paramFloat2 >= localGRegion.y + localGRegion.dy) return 8;
      return 5;
    }
    if (paramFloat2 < this.bevelTitleActive.T.dy) return 2;
    if (paramFloat2 >= localGRegion.y + localGRegion.dy) return 7;
    return 9;
  }

  public void render(GWindowFramed paramGWindowFramed)
  {
    float f = framedTitleHeight(paramGWindowFramed);
    paramGWindowFramed.setCanvasColorWHITE();
    drawBevel(paramGWindowFramed, 0.0F, f, paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - f, this.bevelFW, this.elements);

    GBevel localGBevel = this.bevelTitleActive;
    if (!paramGWindowFramed.isActivated()) localGBevel = this.bevelTitleInactive;
    drawBevel(paramGWindowFramed, 0.0F, 0.0F, paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, f, localGBevel, this.elements);
    if (paramGWindowFramed.title != null) {
      if (!paramGWindowFramed.isActivated()) paramGWindowFramed.setCanvasColor(12632256);
      paramGWindowFramed.setCanvasFont(1);
      this._titleRegion.x = this.bevelTitleActive.L.dx;
      this._titleRegion.y = this.bevelTitleActive.T.dy;
      this._titleRegion.dx = (paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - this.bevelTitleActive.L.dx - this.bevelTitleActive.R.dx - metric());
      this._titleRegion.dy = (f - this.bevelTitleActive.T.dy - this.bevelTitleActive.B.dy);
      if (paramGWindowFramed.pushClipRegion(this._titleRegion, paramGWindowFramed.jdField_bClip_of_type_Boolean, metric(this.spaceFramedTitle))) {
        paramGWindowFramed.draw(0.0F, 0.0F, paramGWindowFramed.title);
        paramGWindowFramed.popClip();
      }
    }
  }

  private float framedTitleHeight(GWindowFramed paramGWindowFramed)
  {
    float f = metric();
    if (f > paramGWindowFramed.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[1].height)
      f = paramGWindowFramed.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[1].height;
    return f + 2.0F * metric(this.spaceFramedTitle) + this.bevelTitleActive.T.dy + this.bevelTitleActive.B.dy;
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
    f1 += this.bevelFW.L.dx + this.bevelFW.R.dx;
    f2 += this.bevelFW.T.dy + this.bevelFW.B.dy + framedTitleHeight(paramGWindowFramed);
    paramGSize.set(f1, f2);
    return paramGSize;
  }

  public GRegion getClientRegion(GWindowFramed paramGWindowFramed, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (this.bevelFW.L.dx + paramFloat);
    paramGRegion.y = (framedTitleHeight(paramGWindowFramed) + this.bevelFW.T.dy + paramFloat);
    if (paramGWindowFramed.menuBar != null)
      paramGRegion.y += paramGWindowFramed.menuBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy;
    paramGRegion.dx = (paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGRegion.x - this.bevelFW.R.dx - paramFloat);
    paramGRegion.dy = (paramGWindowFramed.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGRegion.y - this.bevelFW.B.dy - paramFloat);
    return paramGRegion;
  }

  public void render(GWindowMenuItem paramGWindowMenuItem)
  {
    if ("-".equals(paramGWindowMenuItem.jdField_cap_of_type_ComMaddoxGwindowGCaption.caption)) {
      drawSeparateH(paramGWindowMenuItem, 0.0F, paramGWindowMenuItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dy / 2.0F, paramGWindowMenuItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dx);
      return;
    }
    paramGWindowMenuItem.setCanvasFont(0);
    if (paramGWindowMenuItem.jdField_bEnable_of_type_Boolean) {
      if (paramGWindowMenuItem == paramGWindowMenuItem.menu().selected) {
        paramGWindowMenuItem.setCanvasColorWHITE();
        paramGWindowMenuItem.draw(0.0F, 0.0F, paramGWindowMenuItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowMenuItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.elements, 4.0F, 4.0F, 2.0F, 2.0F);
      } else {
        paramGWindowMenuItem.setCanvasColorBLACK();
      }
      renderMenuItem(paramGWindowMenuItem, 0.0F, 0.0F);
    } else {
      paramGWindowMenuItem.setCanvasColorWHITE();
      renderMenuItem(paramGWindowMenuItem, 1.0F, 1.0F);
      paramGWindowMenuItem.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.C.color.set(127, 127, 127);
      renderMenuItem(paramGWindowMenuItem, 0.0F, 0.0F);
    }
  }

  private void renderMenuItem(GWindowMenuItem paramGWindowMenuItem, float paramFloat1, float paramFloat2) {
    if (paramGWindowMenuItem.bChecked) {
      paramGWindowMenuItem.draw(paramFloat1, (paramGWindowMenuItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - metric()) / 2.0F + paramFloat2, metric(), metric(), this.selectMenuIcon);
    }
    paramGWindowMenuItem.jdField_cap_of_type_ComMaddoxGwindowGCaption.draw(paramGWindowMenuItem, metric() + metric(this.spaceMenuItem) + paramFloat1, paramGWindowMenuItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - metric(this.spaceMenuItem) - paramGWindowMenuItem.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0].height + paramFloat2, paramGWindowMenuItem.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0]);

    if (paramGWindowMenuItem.subMenu() != null)
      paramGWindowMenuItem.draw(paramGWindowMenuItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - metric() + paramFloat1, (paramGWindowMenuItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - metric()) / 2.0F + paramFloat2, metric(), metric(), this.subMenuIcon);
  }

  public GSize getMinSize(GWindowMenuItem paramGWindowMenuItem, GSize paramGSize) {
    paramGSize.dx = (metric() + 2.0F * metric(this.spaceMenuItem) + paramGWindowMenuItem.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0].size(paramGWindowMenuItem.jdField_cap_of_type_ComMaddoxGwindowGCaption.caption).dx + metric());

    paramGSize.dy = (2.0F * metric(this.spaceMenuItem) + paramGWindowMenuItem.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0].height);

    if (paramGSize.dy < metric())
      paramGSize.dy = metric();
    return paramGSize;
  }
  public GRegion getClientRegion(GWindowMenuItem paramGWindowMenuItem, GRegion paramGRegion, float paramFloat) { return paramGRegion;
  }

  public void render(GWindowMenu paramGWindowMenu)
  {
    paramGWindowMenu.setCanvasColorWHITE();
    float f = paramGWindowMenu.jdField_win_of_type_ComMaddoxGwindowGRegion.dx / paramGWindowMenu.columns;
    for (int i = 0; i < paramGWindowMenu.columns; i++)
      drawBevel(paramGWindowMenu, f * i, 0.0F, f, paramGWindowMenu.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelUP, this.elements);
  }

  public GSize getMinSize(GWindowMenu paramGWindowMenu, GSize paramGSize) {
    paramGWindowMenu.columns = 1;
    float f1 = paramGWindowMenu.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - (this.bevelUP.T.dy + 2.0F * metric(this.spaceMenu) + this.bevelUP.B.dy);

    float f2 = 0.0F;
    float f3 = 0.0F;
    int i = paramGWindowMenu.items.size();
    for (int j = 0; j < i; j++) {
      GWindowMenuItem localGWindowMenuItem = (GWindowMenuItem)paramGWindowMenu.items.get(j);
      GSize localGSize = localGWindowMenuItem.getMinSize();
      if (f2 < localGSize.dx) f2 = localGSize.dx;
      if (f3 + localGSize.dy > f1) {
        if (paramGWindowMenu.columns == 1)
          f1 = f3;
        paramGWindowMenu.columns += 1;
        f3 = 0.0F;
      } else {
        f3 += localGSize.dy;
      }
    }
    paramGSize.dx = (paramGWindowMenu.columns * (f2 + this.bevelUP.L.dx + 2.0F * metric(this.spaceMenu) + this.bevelUP.R.dx));
    if (paramGWindowMenu.columns > 1)
      f3 = f1;
    paramGSize.dy = (f3 + this.bevelUP.T.dy + 2.0F * metric(this.spaceMenu) + this.bevelUP.B.dy);
    return paramGSize;
  }
  public GRegion getClientRegion(GWindowMenu paramGWindowMenu, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (this.bevelUP.L.dx + metric(this.spaceMenu) + paramFloat);
    paramGRegion.y = (this.bevelUP.T.dy + metric(this.spaceMenu) + paramFloat);
    paramGRegion.dx = (paramGWindowMenu.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGRegion.x - this.bevelUP.R.dx - metric(this.spaceMenu) - paramFloat);
    paramGRegion.dy = (paramGWindowMenu.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGRegion.y - this.bevelUP.B.dy - metric(this.spaceMenu) - paramFloat);
    return paramGRegion;
  }

  public void render(GWindowMenuBarItem paramGWindowMenuBarItem)
  {
    if (paramGWindowMenuBarItem == paramGWindowMenuBarItem.menuBar().selected) {
      paramGWindowMenuBarItem.setCanvasColorWHITE();
      drawBevel(paramGWindowMenuBarItem, 0.0F, 0.0F, paramGWindowMenuBarItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowMenuBarItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelDOWNsmall, this.elements);
    } else if (paramGWindowMenuBarItem == paramGWindowMenuBarItem.menuBar().over) {
      paramGWindowMenuBarItem.setCanvasColorWHITE();
      drawBevel(paramGWindowMenuBarItem, 0.0F, 0.0F, paramGWindowMenuBarItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowMenuBarItem.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelUPsmall, this.elements);
    }
    if (paramGWindowMenuBarItem.pushClipRegion(paramGWindowMenuBarItem.getClientRegion(), paramGWindowMenuBarItem.jdField_bClip_of_type_Boolean, 0.0F)) {
      paramGWindowMenuBarItem.setCanvasColorBLACK();
      float f1 = metric(this.spaceMenuBarItem);
      float f2 = metric(this.spaceMenuBarItem);
      if (paramGWindowMenuBarItem == paramGWindowMenuBarItem.menuBar().selected)
        f1 = f2 = metric(this.spaceMenuBarItem) + 1.0F;
      else if (paramGWindowMenuBarItem == paramGWindowMenuBarItem.menuBar().over) {
        f1 = f2 = metric(this.spaceMenuBarItem) - 1.0F;
      }
      paramGWindowMenuBarItem.jdField_cap_of_type_ComMaddoxGwindowGCaption.draw(paramGWindowMenuBarItem, f1, f2, paramGWindowMenuBarItem.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0], (paramGWindowMenuBarItem.menuBar().bAltDown) && (paramGWindowMenuBarItem.menuBar().over == null));

      paramGWindowMenuBarItem.popClip();
    }
  }

  public GSize getMinSize(GWindowMenuBarItem paramGWindowMenuBarItem, GSize paramGSize) {
    paramGSize.dx = (this.bevelUPsmall.L.dx + this.bevelUPsmall.R.dx + 2.0F * metric(this.spaceMenuBarItem) + paramGWindowMenuBarItem.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0].size(paramGWindowMenuBarItem.jdField_cap_of_type_ComMaddoxGwindowGCaption.caption).dx);

    paramGSize.dy = getMenuBarItemHeight(paramGWindowMenuBarItem);
    return paramGSize;
  }
  protected float getMenuBarItemHeight(GWindow paramGWindow) {
    return this.bevelUPsmall.T.dy + this.bevelUPsmall.B.dy + 2.0F * metric(this.spaceMenuBarItem) + paramGWindow.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0].height;
  }

  public GRegion getClientRegion(GWindowMenuBarItem paramGWindowMenuBarItem, GRegion paramGRegion, float paramFloat)
  {
    paramGRegion.x = (this.bevelUPsmall.L.dx + paramFloat);
    paramGRegion.y = (this.bevelUPsmall.T.dy + paramFloat);
    GSize localGSize = paramGWindowMenuBarItem.getMinSize();
    paramGRegion.dx = (localGSize.dx - paramGRegion.x - this.bevelUPsmall.R.dx - this.bevelUPsmall.R.dx - paramFloat);
    paramGRegion.dy = (localGSize.dy - paramGRegion.y - this.bevelUPsmall.B.dy - this.bevelUPsmall.B.dy - paramFloat);
    return paramGRegion;
  }

  public void render(GWindowMenuBar paramGWindowMenuBar)
  {
    paramGWindowMenuBar.setCanvasColorWHITE();
    GRegion localGRegion = this.bevelUP.B;
    paramGWindowMenuBar.draw(0.0F, paramGWindowMenuBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelUP.B.dy, paramGWindowMenuBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, this.bevelUP.B.dy, this.elements, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
    localGRegion = this.bevelUP.Area;
    paramGWindowMenuBar.draw(0.0F, 0.0F, paramGWindowMenuBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowMenuBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - this.bevelUP.B.dy, this.elements, localGRegion.x, localGRegion.y, localGRegion.dx, localGRegion.dy);
  }
  public GSize getMinSize(GWindowMenuBar paramGWindowMenuBar, GSize paramGSize) {
    paramGSize.dx = (2.0F * metric(this.spaceMenuBar));
    paramGSize.dy = (2.0F * metric(this.spaceMenuBar) + getMenuBarItemHeight(paramGWindowMenuBar) + this.bevelUP.B.dy);

    return paramGSize;
  }
  public GRegion getClientRegion(GWindowMenuBar paramGWindowMenuBar, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (metric(this.spaceMenuBar) + paramFloat);
    paramGRegion.y = (metric(this.spaceMenuBar) + paramFloat);
    GSize localGSize = paramGWindowMenuBar.getMinSize();
    paramGRegion.dx = (localGSize.dx - 2.0F * metric(this.spaceMenuBar) - 2.0F * paramFloat);
    paramGRegion.dy = (localGSize.dy - 2.0F * metric(this.spaceMenuBar) - this.bevelUP.B.dy - 2.0F * paramFloat);

    return paramGRegion;
  }

  public void render(GWindowStatusBar paramGWindowStatusBar)
  {
    paramGWindowStatusBar.setCanvasColorWHITE();
    drawBevel(paramGWindowStatusBar, 0.0F, 0.0F, paramGWindowStatusBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx, paramGWindowStatusBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy, this.bevelUP, this.elements);
    drawBevel(paramGWindowStatusBar, this.bevelUP.L.dx + metric(this.spaceStatusBar), this.bevelUP.T.dy + metric(this.spaceStatusBar), paramGWindowStatusBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - 2.0F * metric(this.spaceStatusBar) - this.bevelUP.L.dx - this.bevelUP.R.dx, paramGWindowStatusBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - 2.0F * metric(this.spaceStatusBar) - this.bevelUP.T.dy - this.bevelUP.B.dy, this.bevelDOWNsmall, this.elements);

    if (paramGWindowStatusBar.pushClipRegion(paramGWindowStatusBar.getClientRegion(), paramGWindowStatusBar.jdField_bClip_of_type_Boolean, 0.0F)) {
      paramGWindowStatusBar.setCanvasColorBLACK();
      paramGWindowStatusBar.setCanvasFont(0);
      if ((paramGWindowStatusBar.help != null) && (!"".equals(paramGWindowStatusBar.help)))
        paramGWindowStatusBar.draw(0.0F, 0.0F, paramGWindowStatusBar.help);
      else
        paramGWindowStatusBar.draw(0.0F, 0.0F, paramGWindowStatusBar.defaultHelp);
      paramGWindowStatusBar.popClip();
    }
  }

  public GSize getMinSize(GWindowStatusBar paramGWindowStatusBar, GSize paramGSize) {
    paramGSize.dx = (this.bevelUP.L.dx + this.bevelUP.R.dx + 4.0F * metric(this.spaceStatusBar) + this.bevelDOWNsmall.L.dx + this.bevelDOWNsmall.R.dx);

    paramGSize.dy = (this.bevelUP.T.dy + this.bevelUP.B.dy + 4.0F * metric(this.spaceStatusBar) + this.bevelDOWNsmall.T.dy + this.bevelDOWNsmall.B.dy + paramGWindowStatusBar.jdField_root_of_type_ComMaddoxGwindowGWindowRoot.textFonts[0].height);

    return paramGSize;
  }
  public GRegion getClientRegion(GWindowStatusBar paramGWindowStatusBar, GRegion paramGRegion, float paramFloat) {
    paramGRegion.x = (this.bevelUP.L.dx + 2.0F * metric(this.spaceStatusBar) + this.bevelDOWNsmall.L.dx + paramFloat);
    paramGRegion.y = (this.bevelUP.T.dy + 2.0F * metric(this.spaceStatusBar) + this.bevelDOWNsmall.T.dy + paramFloat);
    paramGRegion.dx = (paramGWindowStatusBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dx - paramGRegion.x - this.bevelUP.R.dx - 2.0F * metric(this.spaceStatusBar) - this.bevelDOWNsmall.R.dx - paramFloat);
    paramGRegion.dy = (paramGWindowStatusBar.jdField_win_of_type_ComMaddoxGwindowGRegion.dy - paramGRegion.y - this.bevelUP.B.dy - 2.0F * metric(this.spaceStatusBar) - this.bevelDOWNsmall.B.dy - paramFloat);
    return paramGRegion;
  }

  public void resolutionChanged(GWindowRoot paramGWindowRoot)
  {
    paramGWindowRoot.textFonts[0].resolutionChanged();
    paramGWindowRoot.textFonts[1].resolutionChanged();
    this.metric = (int)(paramGWindowRoot.textFonts[0].height + 0.5F);
  }

  public void init(GWindowRoot paramGWindowRoot) {
    this.elements = GTexture.New("GUI/win95/elements.mat");
    this.elementsStreched = GTexture.New("GUI/win95/elementss.mat");
    this.cursors = GTexture.New("GUI/win95/cursors.mat");
    this.cursorsStreched = GTexture.New("GUI/win95/cursorss.mat");
    this.jdField_regionWhite_of_type_ComMaddoxGwindowGTexRegion = new GTexRegion(this.elements, 5.0F, 17.0F, 1.0F, 1.0F);
    this.bevelUP.set(new GRegion(4.0F, 16.0F, 16.0F, 16.0F), new GRegion(6.0F, 18.0F, 12.0F, 12.0F));
    this.jdField_bevelTabDialogClient_of_type_ComMaddoxGwindowGBevel = this.bevelUP;
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

    this.SBupButtonUP = new GTexRegion(this.elementsStreched, 20.0F, 16.0F, 12.0F, 10.0F);
    this.SBupButtonDOWN = new GTexRegion(this.elementsStreched, 32.0F, 16.0F, 12.0F, 10.0F);
    this.SBupButtonDISABLE = new GTexRegion(this.elementsStreched, 44.0F, 16.0F, 12.0F, 10.0F);
    this.SBupButtonOVER = this.SBupButtonUP;

    this.SBdownButtonUP = new GTexRegion(this.elementsStreched, 20.0F, 26.0F, 12.0F, 10.0F);
    this.SBdownButtonDOWN = new GTexRegion(this.elementsStreched, 32.0F, 26.0F, 12.0F, 10.0F);
    this.SBdownButtonDISABLE = new GTexRegion(this.elementsStreched, 44.0F, 26.0F, 12.0F, 10.0F);
    this.SBdownButtonOVER = this.SBdownButtonUP;

    this.SBleftButtonUP = new GTexRegion(this.elementsStreched, 20.0F, 48.0F, 10.0F, 12.0F);
    this.SBleftButtonDOWN = new GTexRegion(this.elementsStreched, 30.0F, 48.0F, 10.0F, 12.0F);
    this.SBleftButtonDISABLE = new GTexRegion(this.elementsStreched, 40.0F, 48.0F, 10.0F, 12.0F);
    this.SBleftButtonOVER = this.SBleftButtonUP;

    this.SBrightButtonUP = new GTexRegion(this.elementsStreched, 20.0F, 36.0F, 10.0F, 12.0F);
    this.SBrightButtonDOWN = new GTexRegion(this.elementsStreched, 30.0F, 36.0F, 10.0F, 12.0F);
    this.SBrightButtonDISABLE = new GTexRegion(this.elementsStreched, 40.0F, 36.0F, 10.0F, 12.0F);
    this.SBrightButtonOVER = this.SBrightButtonUP;

    this.checkBoxCheckEnable = new GTexRegion(this.elementsStreched, 32.0F, 64.0F, 13.0F, 13.0F);
    this.checkBoxCheckDisable = new GTexRegion(this.elementsStreched, 45.0F, 64.0F, 13.0F, 13.0F);
    this.checkBoxUnCheckEnable = new GTexRegion(this.elementsStreched, 58.0F, 64.0F, 13.0F, 13.0F);
    this.checkBoxUnCheckDisable = new GTexRegion(this.elementsStreched, 71.0F, 64.0F, 13.0F, 13.0F);

    paramGWindowRoot.textFonts[0] = GFont.New("arial10");
    paramGWindowRoot.textFonts[1] = GFont.New("arialb10");
    this.metric = (int)(paramGWindowRoot.textFonts[0].height + 0.5F);
    paramGWindowRoot.mouseCursors[0] = new GCursorTexRegion(this.cursors, 32.0F, 96.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0);
    paramGWindowRoot.mouseCursors[1] = new GCursorTexRegion(this.cursors, 0.0F, 0.0F, 32.0F, 32.0F, 5.0F, 4.0F, 1);
    paramGWindowRoot.mouseCursors[2] = new GCursorTexRegion(this.cursors, 32.0F, 0.0F, 32.0F, 32.0F, 14.0F, 15.0F, 2);
    paramGWindowRoot.mouseCursors[3] = new GCursorTexRegion(this.cursors, 64.0F, 0.0F, 32.0F, 32.0F, 14.0F, 17.0F, 3);
    paramGWindowRoot.mouseCursors[4] = new GCursorTexRegion(this.cursors, 96.0F, 0.0F, 32.0F, 32.0F, 3.0F, 2.0F, 4);
    paramGWindowRoot.mouseCursors[5] = new GCursorTexRegion(this.cursors, 0.0F, 32.0F, 32.0F, 32.0F, 14.0F, 15.0F, 5);
    paramGWindowRoot.mouseCursors[6] = new GCursorTexRegion(this.cursors, 32.0F, 32.0F, 32.0F, 32.0F, 15.0F, 16.0F, 6);
    paramGWindowRoot.mouseCursors[7] = new GCursorTexRegion(this.cursors, 64.0F, 32.0F, 32.0F, 32.0F, 15.0F, 15.0F, 7);
    paramGWindowRoot.mouseCursors[8] = new GCursorTexRegion(this.cursors, 96.0F, 32.0F, 32.0F, 32.0F, 15.0F, 15.0F, 8);
    paramGWindowRoot.mouseCursors[9] = new GCursorTexRegion(this.cursors, 0.0F, 64.0F, 32.0F, 32.0F, 15.0F, 16.0F, 9);
    paramGWindowRoot.mouseCursors[10] = new GCursorTexRegion(this.cursors, 32.0F, 64.0F, 32.0F, 32.0F, 14.0F, 14.0F, 10);
    paramGWindowRoot.mouseCursors[11] = new GCursorTexRegion(this.cursors, 64.0F, 64.0F, 32.0F, 32.0F, 16.0F, 15.0F, 11);
    paramGWindowRoot.mouseCursors[12] = new GCursorTexRegion(this.cursors, 96.0F, 64.0F, 32.0F, 32.0F, 14.0F, 15.0F, 12);
    paramGWindowRoot.mouseCursors[13] = new GCursorTexRegion(this.cursors, 0.0F, 96.0F, 32.0F, 32.0F, 15.0F, 15.0F, 13);

    this.selectMenuIcon = new GTexRegion(this.cursorsStreched, 116.0F, 104.0F, 12.0F, 12.0F);
    this.subMenuIcon = new GTexRegion(this.cursorsStreched, 116.0F, 116.0F, 12.0F, 12.0F);
  }

  public String i18n(String paramString) {
    return I18N.gwindow(paramString);
  }
}