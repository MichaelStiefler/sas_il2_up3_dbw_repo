package com.maddox.gwindow;

public class GWindowEditNumber extends GWindowDialogControl
  implements GWindowCellEdit
{
  public Class type;
  public Number min;
  public Number max;
  public double step = 1.0D;
  public GWindowEditBox box;
  public GWindowVScrollBar bar;

  public void setRange(Number paramNumber1, Number paramNumber2, Number paramNumber3)
  {
    this.min = paramNumber1;
    this.max = paramNumber2;
    if ((paramNumber1 == null) || (paramNumber2 == null)) {
      this.bar.hideWindow();
    } else {
      if (paramNumber3 != null) this.step = paramNumber3.doubleValue(); else
        this.step = 1.0D;
      this.bar.setRange(-1.0F, 1.0F, 0.0F, -1.0F, 0.0F);
      this.bar.showWindow();
    }
    resized();
  }

  private Number getNumberValue(Object paramObject) {
    if ((paramObject instanceof Number)) return (Number)paramObject;
    if ((paramObject instanceof String)) {
      if (this.type == Byte.class) return Byte.valueOf((String)paramObject);
      if (this.type == Short.class) return Short.valueOf((String)paramObject);
      if (this.type == Integer.class) return Integer.valueOf((String)paramObject);
      if (this.type == Long.class) return Long.valueOf((String)paramObject);
      if (this.type == Float.class) return Float.valueOf((String)paramObject);
      if (this.type == Double.class) return Double.valueOf((String)paramObject);
    }
    return null;
  }

  public void setCellEditValue(Object paramObject) {
    this.box.setValue(paramObject.toString(), false);
  }

  public Object getCellEditValue() {
    return this.box.getValue();
  }

  public boolean notify(GWindow paramGWindow, int paramInt1, int paramInt2) {
    if ((paramGWindow == this.bar) && (paramInt1 == 2)) {
      float f = this.bar.pos();
      Number localNumber = getNumberValue(getCellEditValue());
      double d1 = localNumber.doubleValue();
      if (f < 0.0F) d1 -= this.step; else
        d1 += this.step;
      if (this.step > 0.0D) {
        d1 = ()(d1 / this.step);
        d1 *= this.step;
      }
      double d2 = this.min.doubleValue();
      if (d1 < d2) d1 = d2;
      double d3 = this.max.doubleValue();
      if (d1 > d3) d1 = d3;
      if ((this.type == Float.class) || (this.type == Double.class)) {
        this.box.setValue(Double.toString(d1), false);
      } else {
        long l = ()d1;
        this.box.setValue(Long.toString(l), false);
      }
      this.bar.setPos(0.0F, false);
      return true;
    }
    return false;
  }

  public void resized() {
    super.resized();
    lookAndFeel().setupEditNumber(this);
  }

  public void render() {
    lookAndFeel().render(this);
  }

  public void afterCreated() {
    super.afterCreated();
    create(this.box = new GWindowEditBox());
    this.bar = new GWindowVScrollBar(this);
    if ((this.type.isAssignableFrom(Byte.class)) || (this.type.isAssignableFrom(Short.class)) || (this.type.isAssignableFrom(Integer.class)) || (this.type.isAssignableFrom(Long.class)))
    {
      this.box.bNumericOnly = true;
    }this.bar.hideWindow();
    resized();
  }

  public GWindowEditNumber(Class paramClass)
  {
    this.type = paramClass;
  }

  public GWindowEditNumber(GWindow paramGWindow, Class paramClass, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, String paramString)
  {
    this.toolTip = paramString;
    this.align = 0;
    this.type = paramClass;
    doNew(paramGWindow, paramFloat1, paramFloat2, paramFloat3, paramFloat4, true);
  }
}