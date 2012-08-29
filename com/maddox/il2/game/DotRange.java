package com.maddox.il2.game;

import com.maddox.il2.ai.Army;
import com.maddox.rts.NetMsgInput;
import com.maddox.rts.NetMsgOutput;
import java.io.IOException;

public class DotRange
{
  public static final double MAX_DOT = 25000.0D;
  public static final double MIN_DOT = 5.0D;
  protected double dot = 10000.0D;
  protected double icon = 3000.0D;
  protected double color = 3000.0D;
  protected double type = 3000.0D;
  protected double name = 3000.0D;
  protected double id = 3000.0D;
  protected double range = 3000.0D;

  public void setDefault() {
    set(14000.0D, 6000.0D, 6000.0D, 6000.0D, 6000.0D, 6000.0D);
  }
  public double dot() {
    return this.dot; } 
  public double dot(double paramDouble) { return this.dot * paramDouble; } 
  public double icon() { return this.icon; } 
  public double color() { return this.color; } 
  public double type() { return this.type; } 
  public double name() { return this.name; } 
  public double id() { return this.id; } 
  public double range() { return this.range; } 
  public int colorDot(double paramDouble, int paramInt1, int paramInt2) {
    int i = 0;
    if (paramDouble < this.color)
      i = Army.color(paramInt1) & 0xFFFFFF;
    int j = ((int)(alphaDot(paramDouble) * paramInt2) & 0xFF) << 24;
    return i | j;
  }
  public int colorIcon(double paramDouble, int paramInt1, int paramInt2) {
    int i = 0;
    if (paramDouble < this.color)
      i = Army.color(paramInt1) & 0xFFFFFF;
    int j = ((int)(alphaIcon(paramDouble) * paramInt2) & 0xFF) << 24;
    return i | j;
  }
  public int alphaColorDot(double paramDouble) {
    return (int)(alphaDot(paramDouble) * 255.0D) << 24;
  }
  public int alphaColorIcon(double paramDouble) {
    return (int)(alphaIcon(paramDouble) * 255.0D) << 24;
  }
  public double alphaDot(double paramDouble) {
    double d = this.dot / 2.0D;
    if (paramDouble <= d) return 1.0D;
    if (paramDouble >= this.dot) return 0.0D;
    return 1.0D - (paramDouble - d) / d;
  }
  public double alphaDot(double paramDouble1, double paramDouble2) {
    double d = this.dot * paramDouble2;
    if (paramDouble1 >= d) return 0.0D;
    return 1.0D - paramDouble1 / d;
  }
  public double alphaIcon(double paramDouble) {
    double d = this.icon / 2.0D;
    if (paramDouble <= d) return 1.0D;
    if (paramDouble >= this.icon) return 0.0D;
    return 1.0D - (paramDouble - d) / d;
  }

  private void validate() {
    if (this.dot < 5.0D) this.dot = 5.0D;
    if (this.dot > 25000.0D) this.dot = 25000.0D;
    if (this.color < 5.0D) this.color = 5.0D;
    if (this.color > this.dot) this.color = this.dot;
    if (this.type < 5.0D) this.type = 5.0D;
    if (this.type > this.dot) this.type = this.dot;
    if (this.name < 5.0D) this.name = 5.0D;
    if (this.name > this.dot) this.name = this.dot;
    if (this.id < 5.0D) this.id = 5.0D;
    if (this.id > this.dot) this.id = this.dot;
    if (this.range < 5.0D) this.range = 5.0D;
    if (this.range > this.dot) this.range = this.dot;
    this.icon = this.color;
    if (this.icon < this.type) this.icon = this.type;
    if (this.icon < this.name) this.icon = this.name;
    if (this.icon < this.id) this.icon = this.id;
    if (this.icon < this.range) this.icon = this.range;
  }

  public void set(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
  {
    if (paramDouble1 > 0.0D) this.dot = paramDouble1;
    if (paramDouble2 > 0.0D) this.color = paramDouble2;
    if (paramDouble3 > 0.0D) this.type = paramDouble3;
    if (paramDouble4 > 0.0D) this.name = paramDouble4;
    if (paramDouble5 > 0.0D) this.id = paramDouble5;
    if (paramDouble6 > 0.0D) this.range = paramDouble6;
    validate();
  }

  public void netInput(NetMsgInput paramNetMsgInput) throws IOException {
    set(paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat(), paramNetMsgInput.readFloat());
  }

  public void netOutput(NetMsgOutput paramNetMsgOutput)
    throws IOException
  {
    paramNetMsgOutput.writeFloat((float)this.dot);
    paramNetMsgOutput.writeFloat((float)this.color);
    paramNetMsgOutput.writeFloat((float)this.type);
    paramNetMsgOutput.writeFloat((float)this.name);
    paramNetMsgOutput.writeFloat((float)this.id);
    paramNetMsgOutput.writeFloat((float)this.range);
  }
  public DotRange() {
    setDefault();
  }
}