package com.maddox.rts;

import java.io.PrintStream;

class NetSocketListener extends Thread
{
  public NetSocket socket;
  public NetPacket packet;
  public boolean bDoRun = true;

  public void run() {
    while (this.bDoRun)
      try {
        this.packet.time = 0L;
        if (this.socket.receive(this.packet)) {
          int i = this.packet.getLength();
          if (i > 1) {
            byte[] arrayOfByte = new byte[this.packet.getLength()];
            System.arraycopy(this.packet.getData(), this.packet.getOffset(), arrayOfByte, 0, this.packet.getLength());

            NetPacket localNetPacket = new NetPacket(arrayOfByte, 0, arrayOfByte.length, this.packet.getAddress(), this.packet.getPort());

            localNetPacket.time = this.packet.time;
            if (localNetPacket.time == 0L) {
              localNetPacket.time = Time.real();
            }
            RTSConf.cur.netEnv.listenerReceivedPacket(this.socket, localNetPacket);
          }
        }
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
  }

  public NetSocketListener(NetSocket paramNetSocket)
  {
    this.socket = paramNetSocket;
    paramNetSocket.setSoTimeout(0);
    this.packet = new NetPacket(new byte[paramNetSocket.getMaxDataSize()], 0);
  }
}