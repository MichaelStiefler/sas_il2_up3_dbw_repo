package com.maddox.gwindow;

import java.util.ArrayList;

public class GWindowEditTextControl extends GWindowDialogControl
{
  public float border = 4.0F;
  public float editBorder = 0.0F;
  public GWindowEditText edit;
  public GWindowDialogControl e;
  public GWindowVScrollBar vScroll;
  public GWindowHScrollBar hScroll;
  public GWindowButton button;
  private float editDx;
  private float editDy;

  public void updateScrollsPos()
  {
    if (this.vScroll.isVisible()) this.vScroll.setPos(-this.edit.win.y, false);
    if (this.hScroll.isVisible()) this.hScroll.setPos(-this.edit.win.x, false); 
  }

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2)
  {
    if (paramInt1 == 2) {
      if (paramGWindow == this.vScroll) {
        if (this.edit != null) {
          editSetPos(this.edit.win.x, -this.vScroll.pos());
        }

        return true;
      }
      if (paramGWindow == this.hScroll) {
        if (this.edit != null) {
          editSetPos(-this.hScroll.pos(), this.edit.win.y);
        }

        return true;
      }
      if (paramGWindow == this.edit) {
        if (paramInt2 == 0)
          resized();
        else if (paramInt2 == 1) {
          clampCaretPos();
        }
        return true;
      }
    }
    if (paramInt1 == 17) {
      if (this.vScroll.isVisible())
        this.vScroll.scrollDz(this.root.mouseRelMoveZ);
      return true;
    }
    return super.notify(paramGWindow, paramInt1, paramInt2);
  }

  private void computeSizeEdit(float paramFloat)
  {
    this.edit.win.dx = paramFloat;
    this.edit.updateTextPos();
    this.editDx = 0.0F;
    this.editDy = 0.0F;
    GFont localGFont = this.root.textFonts[this.edit.font];
    int i = this.edit.textPos.size();
    for (int j = 0; j < i; j++) {
      GWindowEditText.PosLen localPosLen = (GWindowEditText.PosLen)this.edit.textPos.get(j);
      this.editDy += localGFont.height;
      if (this.editDx < localPosLen.width)
        this.editDx = localPosLen.width;
    }
  }

  public void editSetPos(float paramFloat1, float paramFloat2) {
    GFont localGFont = this.root.textFonts[this.edit.font];
    int i = Math.round(paramFloat2 / localGFont.height);
    this.edit.setPos(paramFloat1, i * localGFont.height);
  }
  public void clampCaretPos() {
    if (this.edit.isEmpty()) return;
    GFont localGFont = this.root.textFonts[this.edit.font];
    int i = 0;
    int j = this.edit.posCaret.ofs;
    int k = this.edit.textPos.size();
    for (int m = 0; m < k; m++) {
      GWindowEditText.PosLen localPosLen1 = (GWindowEditText.PosLen)this.edit.textPos.get(m);
      if ((this.edit.posCaret.item != localPosLen1.item) || (j < localPosLen1.ofs) || (j > localPosLen1.ofs + localPosLen1.len))
        continue;
      i = m;
      break;
    }

    float f1 = i * localGFont.height;
    float f2 = 0.0F;
    Object localObject;
    if (j > 0) {
      GWindowEditText.PosLen localPosLen2 = (GWindowEditText.PosLen)this.edit.textPos.get(i);
      StringBuffer localStringBuffer = this.edit.item(localPosLen2.item);
      localObject = GWindowEditText._getArrayRenderBuffer(j - localPosLen2.ofs);
      localStringBuffer.getChars(localPosLen2.ofs, j, localObject, 0);
      GSize localGSize = localGFont.size(localObject, 0, j - localPosLen2.ofs);
      f2 = localGSize.dx;
    }
    float f3 = -this.edit.win.y;
    if (f1 < f3)
      f3 = f1;
    else if (f1 > f3 + this.e.win.dy - localGFont.height) {
      f3 = f1 + localGFont.height - this.e.win.dy;
    }
    float f4 = -this.edit.win.x;
    if (f2 < f4) {
      f4 = f2 - lookAndFeel().metric();
      if (f4 < 0.0F) f4 = 0.0F; 
    }
    else {
      localObject = localGFont.size("|");
      if (f2 > f4 + this.e.win.dx - ((GSize)localObject).dx)
        f4 = f2 - this.e.win.dx + ((GSize)localObject).dx;
    }
    this.edit.setPos(-f4, -f3);
    updateScrollsPos();
  }

  public void resized() {
    if (this.edit == null) return;

    int i = 0;
    int j = 0;
    float f1 = 0.0F; float f2 = 0.0F;
    int k = 0;
    while (true) {
      f1 = this.win.dx - 2.0F * (this.border + this.editBorder);
      if (j != 0) f1 -= lookAndFeel().getVScrollBarW();
      f2 = this.win.dy - 2.0F * (this.border + this.editBorder);
      if (i != 0) f2 -= lookAndFeel().getHScrollBarH();
      computeSizeEdit(f1);
      k++; if (k == 3) break;
      i = this.editDx > f1 ? 1 : 0;
      j = this.editDy > f2 ? 1 : 0;
    }
    this.edit.win.dx = this.editDx;
    if (this.edit.win.dx < f1)
      this.edit.win.dx = f1;
    this.edit.win.dy = this.editDy;
    if (this.edit.win.dy < f2)
      this.edit.win.dy = f2;
    this.e.setPosSize(this.border + this.editBorder, this.border + this.editBorder, f1, f2);
    float f3 = this.edit.win.x;
    float f4 = this.edit.win.y;
    if (j != 0) {
      this.vScroll.setPos(this.win.dx - lookAndFeel().getVScrollBarW() - this.border, this.border);
      this.vScroll.setSize(lookAndFeel().getVScrollBarW(), f2 + 2.0F * this.editBorder);
      if (f4 + this.editDy < f2)
        f4 = f2 - this.editDy;
      this.vScroll.setRange(0.0F, this.editDy, f2, lookAndFeel().metric(), -f4);
      this.vScroll.showWindow();
    } else {
      this.vScroll.hideWindow();
      f4 = 0.0F;
    }
    if (i != 0) {
      this.hScroll.setPos(this.border, this.win.dy - lookAndFeel().getHScrollBarH() - this.border);
      this.hScroll.setSize(f1 + 2.0F * this.editBorder, lookAndFeel().getHScrollBarH());
      if (f3 + this.editDx < f1)
        f3 = f1 - this.editDx;
      this.hScroll.setRange(0.0F, this.editDx, f1, lookAndFeel().metric(), -f3);
      this.hScroll.showWindow();
    } else {
      this.hScroll.hideWindow();
      f3 = 0.0F;
    }
    if ((i != 0) && (j != 0)) {
      this.button.setPos(this.win.dx - this.border - lookAndFeel().getVScrollBarW(), this.win.dy - this.border - lookAndFeel().getHScrollBarH());

      this.button.setSize(lookAndFeel().getVScrollBarW(), lookAndFeel().getHScrollBarH());
      this.button.showWindow();
    } else {
      this.button.hideWindow();
    }
    editSetPos(f3, f4);
    clampCaretPos();
  }

  public void resolutionChanged() {
    this.border = lookAndFeel().getBorderSizeEditTextControl();
    resized();
  }

  public void render() {
    lookAndFeel().render(this);
  }

  public GSize getMinSize(GSize paramGSize) {
    paramGSize.dx = (2.0F * (lookAndFeel().getVScrollBarW() + this.border + this.editBorder));
    paramGSize.dy = (2.0F * (lookAndFeel().getHScrollBarH() + this.border + this.editBorder));
    return paramGSize;
  }

  public void afterCreated() {
    this.e = ((GWindowDialogControl)create(new GWindowDialogControl()));
    this.edit = ((GWindowEditText)this.e.create(new GWindowEditText()));
    this.edit.notifyWindow = this;
    this.border = lookAndFeel().getBorderSizeEditTextControl();
    super.afterCreated();
    this.hScroll = new GWindowHScrollBar(this);
    this.vScroll = new GWindowVScrollBar(this);
    this.hScroll.bAlwaysOnTop = true;
    this.vScroll.bAlwaysOnTop = true;
    this.hScroll.hideWindow();
    this.vScroll.hideWindow();
    this.button = new GWindowButton(this);
    this.button.bAcceptsKeyFocus = false;
    this.button.bAlwaysOnTop = true;
    this.button.bDrawOnlyUP = true;
    this.button.bDrawActive = false;
    this.button.hideWindow();

    resized();
  }
  public GWindowEditTextControl() {
  }

  public GWindowEditTextControl(GWindow paramGWindow) {
    doNew(paramGWindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
  }

  public GWindowEditTextControl(GWindow paramGWindow, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString)
  {
    this.toolTip = paramString;
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }
}