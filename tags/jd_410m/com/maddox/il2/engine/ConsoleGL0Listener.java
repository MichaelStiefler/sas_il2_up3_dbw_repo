package com.maddox.il2.engine;

import com.maddox.rts.ConsoleListener;

class ConsoleGL0Listener
  implements ConsoleListener
{
  ConsoleGL0Render render;
  boolean bActive = false;

  public ConsoleGL0Listener(ConsoleGL0Render paramConsoleGL0Render) { this.render = paramConsoleGL0Render; }

  public void consoleChanged() {
    if (!this.bActive) {
      this.bActive = true;
      this.render.exlusiveDraw();
      this.bActive = false;
    }
  }
}