package com.maddox.il2.builder;

import com.maddox.rts.HotKeyCmd;

class Builder$27 extends HotKeyCmd
{
  private final Builder this$0;

  public void end()
  {
    if (!this.this$0.isLoadedLandscape()) return;
    if (this.this$0.mouseState != 0) return;
    if (this.this$0.isFreeView()) return;
    this.this$0.beginSelectTarget();
  }
}