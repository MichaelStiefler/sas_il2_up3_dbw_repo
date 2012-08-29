package com.maddox.il2.engine;

import com.maddox.gwindow.GMesh;
import com.maddox.gwindow.GMesh.Loader;
import com.maddox.gwindow.GSize;
import com.maddox.util.HashMapExt;
import java.io.PrintStream;

public class GUIMesh extends GMesh
{
  public Mesh mesh = null;
  public float[] boundBox = { 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F };

  private static HashMapExt _shared = new HashMapExt();
  private static float[] _box = { 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F };

  private GUIMesh()
  {
  }

  GUIMesh(1 param1)
  {
    this();
  }

  public static class _Loader extends GMesh.Loader
  {
    public GMesh load(String paramString)
    {
      GUIMesh localGUIMesh = new GUIMesh(null);
      localGUIMesh.jdField_size_of_type_ComMaddoxGwindowGSize = new GSize(1.0F, 1.0F);
      try {
        localGUIMesh.mesh = new Mesh(paramString);
        int i = localGUIMesh.mesh.frames();
        if (i > 1) {
          localGUIMesh.mesh.setFrame(0);
          localGUIMesh.mesh.getBoundBox(localGUIMesh.boundBox);
          for (int j = 1; j < i; j++) {
            localGUIMesh.mesh.setFrame(j);
            localGUIMesh.mesh.getBoundBox(GUIMesh._box);
            if (localGUIMesh.boundBox[0] > GUIMesh._box[0]) localGUIMesh.boundBox[0] = GUIMesh.access$100()[0];
            if (localGUIMesh.boundBox[1] > GUIMesh._box[1]) localGUIMesh.boundBox[1] = GUIMesh.access$100()[1];
            if (localGUIMesh.boundBox[2] > GUIMesh._box[2]) localGUIMesh.boundBox[2] = GUIMesh.access$100()[2];
            if (localGUIMesh.boundBox[3] < GUIMesh._box[3]) localGUIMesh.boundBox[3] = GUIMesh.access$100()[3];
            if (localGUIMesh.boundBox[4] < GUIMesh._box[4]) localGUIMesh.boundBox[4] = GUIMesh.access$100()[4];
            if (localGUIMesh.boundBox[5] >= GUIMesh._box[5]) continue; localGUIMesh.boundBox[5] = GUIMesh.access$100()[5];
          }
          localGUIMesh.mesh.setFrame(0);
        } else {
          localGUIMesh.mesh.getBoundBox(localGUIMesh.boundBox);
        }
        localGUIMesh.jdField_size_of_type_ComMaddoxGwindowGSize.set(localGUIMesh.boundBox[3] - localGUIMesh.boundBox[0], localGUIMesh.boundBox[4] - localGUIMesh.boundBox[1]);
      } catch (Exception localException) {
        System.out.println(localException.getMessage());
        localException.printStackTrace();
      }
      return localGUIMesh;
    }

    public GMesh loadShared(String paramString) {
      Object localObject = GUIMesh._shared.get(paramString);
      if (localObject != null)
        return (GMesh)localObject;
      GMesh localGMesh = load(paramString);
      GUIMesh._shared.put(paramString, localGMesh);
      return localGMesh;
    }
  }
}