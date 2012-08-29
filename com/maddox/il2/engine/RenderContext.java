package com.maddox.il2.engine;

import com.maddox.opengl.GLCaps;
import com.maddox.opengl.GLContext;
import com.maddox.opengl.Provider;
import com.maddox.rts.Cfg;
import com.maddox.rts.CfgFlags;
import com.maddox.rts.CfgInt;
import com.maddox.rts.IniFile;

public class RenderContext
{
  private static boolean bProfile = false;
  public static final String _PROFILE = "Profile";
  public static final String _POLYGON_OFFSET_FACTOR = "PolygonOffsetFactor";
  public static final String _POLYGON_OFFSET_UNITS = "PolygonOffsetUnits";
  private static IniFile iniFile;
  private static String iniSection;
  private static boolean iniChanged = false;
  public static final int TEX_USE_PALETTE = 1;
  public static final int TEX_USE_ALPHA = 2;
  public static final int TEX_USE_INDEX = 4;
  public static final int TEX_USE_DITHER = 8;
  public static final int TEX_POINTSAMPLING = 16;
  public static final int TEX_WIREFRAME = 32;
  public static final int TEX_DISABLE = 64;
  public static final int TEX_SHOW_MATERIALS = 128;
  public static final int TEX_USE_CLAMPED_SPRITES = 256;
  public static final int TEX_VERTEX_ARRAYS = 512;
  public static final int TEX_DISABLE_API_EXTENSIONS = 1024;
  public static final int TEX_ARB_MULTITEXTURE_EXT = 2048;
  public static final int TEX_VERTEX_ARRAY_EXT = 4096;
  public static final int TEX_CLIP_HINT_EXT = 8192;
  public static final int TEX_SECONDARY_COLOR_EXT = 16384;
  public static final int TEX_ENV_COMBINE_EXT = 32768;
  public static final int TEX_ANISOTROPIC_EXT = 65536;
  public static final int TEX_TEXCOMPRESS_ARB = 131072;
  public static final int TEX_POLYGON_STIPPLE = 262144;
  public static final int TEX_HARDWARE_TL = 524288;
  public static final int TEX_DRAW_LAND_BY_TRIANGLES = 1048576;
  public static final int TEX_RENDERER_DIRECTX = 8388608;
  public static final int SHADOW_NONE = 0;
  public static final int SHADOW_PROJECTIVE = 1;
  public static final int SHADOW_PROJECTIVE_TRANSPARENT = 2;
  public static final int SHADOW_VOLUME = 3;
  public static final int SHADOW_VOLUME_HQ = 4;
  private static float polygonOffsetFactor;
  private static float polygonOffsetUnuts;
  private static float[] polygonOffset = new float[2];

  private static int nCfgList = 0;
  private static boolean FirstCall = true;

  private static Cfg[] CfgList = new Cfg[64];

  private static boolean bActivated = false;
  public static CfgInt cfgTxrLandQual;
  public static CfgInt cfgTxrLandLarge;
  public static CfgInt cfgTxrQual;
  public static CfgInt cfgTxrLarge;
  public static CfgInt cfgTxrCompress;
  public static CfgFlags cfgTexFlags;
  public static CfgInt cfgTxrFilter;
  public static CfgInt cfgShadows;
  public static CfgFlags cfgShadowsFlags;
  public static CfgInt cfgSpecular;
  public static CfgInt cfgSpecularLight;
  public static CfgInt cfgDiffuseLight;
  public static CfgInt cfgDynamicalLights;
  public static CfgInt cfgMeshDetail;
  public static CfgInt cfgDrawCollisions;
  public static CfgInt cfgVisibilityDistance;
  public static CfgInt cfgHardwareShaders;
  public static CfgInt cfgForceShaders1x;
  public static CfgInt cfgLandShading;
  public static CfgInt cfgLandDetails;
  public static CfgInt cfgLandGeom;
  public static CfgInt cfgSky;
  public static CfgInt cfgWater;
  public static CfgInt cfgEffects;
  public static CfgInt cfgLandFogHaze;
  public static CfgInt cfgLandFogLow;
  public static CfgInt cfgForest;
  public static boolean bRenderEnable = true;
  public static boolean bPreRenderEnable = true;

  private static boolean bDrawMesh = true;

  public static boolean isProfile()
  {
    return bProfile;
  }
  public static void enableProfile(boolean paramBoolean) { if (paramBoolean == bProfile) return;
    bProfile = paramBoolean;
    Provider.setEnableProfile(bProfile);
  }

  public static void loadConfig(IniFile paramIniFile, String paramString)
  {
    IniFile localIniFile = iniFile;
    String str = iniSection;

    iniFile = paramIniFile;
    iniSection = paramString;

    iniChanged = false;
    if ((localIniFile != iniFile) || (str == null) || (!str.equals(iniSection)))
    {
      iniChanged = true;
    }enableProfile(paramIniFile.get(paramString, "Profile", bProfile));

    float f1 = paramIniFile.get(paramString, "PolygonOffsetFactor", -0.0625F);
    float f2 = paramIniFile.get(paramString, "PolygonOffsetUnits", -1.0F);
    preSetPolygonOffset(f1, f2);
  }

  public static void saveConfig()
  {
    CfgList_Save();
  }

  public static int texGetFlags()
  {
    return TexGetFlags();
  }

  public static int shadowGet()
  {
    return cfgShadows.get();
  }
  public static void shadowSet(int paramInt) {
    cfgShadows.set(paramInt); cfgShadows.apply();
  }

  private static Cfg CfgList_Add(Cfg paramCfg)
  {
    CfgList[(nCfgList++)] = paramCfg; return paramCfg;
  }

  private static CfgInt CfgList_AddInt(String paramString) {
    return (CfgInt)CfgList_Add(CfgGObj.findByName(paramString));
  }
  private static CfgFlags CfgList_AddFlags(String paramString) {
    return (CfgFlags)CfgList_Add(CfgGObj.findByName(paramString));
  }

  private static void CfgList_LoadAndPrepare()
  {
    if ((FirstCall) || (iniChanged)) {
      for (i = 0; i < nCfgList; i++) {
        if ((CfgList[i] == cfgLandFogHaze) || (CfgList[i] == cfgLandFogLow) || (CfgList[i] == cfgDrawCollisions)) {
          continue;
        }
        CfgList[i].load(iniFile, iniSection);
      }

      cfgTexFlags.set(5, false);
      cfgTexFlags.set(6, false);
      cfgTexFlags.set(7, false);
    }

    for (int i = 0; i < nCfgList; i++)
      CfgList[i].apply();
    for (int j = 0; j < nCfgList; j++)
      CfgList[j].reset();
  }

  private static void CfgList_Save() {
    for (int i = 0; i < nCfgList; i++) CfgList[i].save();
  }

  private static void preSetPolygonOffset(float paramFloat1, float paramFloat2)
  {
    polygonOffsetFactor = paramFloat1;
    polygonOffsetUnuts = paramFloat2;
  }
  public static void getPolygonOffset(float[] paramArrayOfFloat) {
    paramArrayOfFloat[0] = polygonOffsetFactor;
    paramArrayOfFloat[1] = polygonOffsetUnuts;
  }
  public static void setPolygonOffset(float paramFloat1, float paramFloat2) {
    SetPolygonOffset(paramFloat1, paramFloat2);
    GetPolygonOffset(polygonOffset);
    polygonOffsetFactor = polygonOffset[0];
    polygonOffsetUnuts = polygonOffset[1];
  }

  public static boolean isActivated()
  {
    return bActivated;
  }

  public static void activate(GLContext paramGLContext)
  {
    if (!bActivated) {
      bActivated = true;
      Begin(paramGLContext.getCaps().getColourBits(), paramGLContext.getCaps().getDepthBits());

      setPolygonOffset(polygonOffsetFactor, polygonOffsetUnuts);

      if (FirstCall) {
        cfgShadows = CfgList_AddInt("Shadows");
        cfgShadowsFlags = CfgList_AddFlags("ShadowsFlags");
        cfgTxrQual = CfgList_AddInt("TexQual");
        cfgTxrLarge = CfgList_AddInt("TexLarge");
        cfgTxrCompress = CfgList_AddInt("TexCompress");
        cfgTxrFilter = CfgList_AddInt("TexMipFilter");
        cfgTexFlags = CfgList_AddFlags("TexFlags");
        cfgSpecular = CfgList_AddInt("Specular");
        cfgSpecularLight = CfgList_AddInt("SpecularLight");
        cfgDiffuseLight = CfgList_AddInt("DiffuseLight");
        cfgDynamicalLights = CfgList_AddInt("DynamicalLights");
        cfgMeshDetail = CfgList_AddInt("MeshDetail");
        cfgDrawCollisions = CfgList_AddInt("DrawCollisions");
        cfgVisibilityDistance = CfgList_AddInt("VisibilityDistance");

        cfgTxrQual = CfgList_AddInt("TexLandQual");
        cfgTxrLarge = CfgList_AddInt("TexLandLarge");
        cfgLandShading = CfgList_AddInt("LandShading");
        cfgLandDetails = CfgList_AddInt("LandDetails");
        cfgLandGeom = CfgList_AddInt("LandGeom");
        cfgSky = CfgList_AddInt("Sky");
        cfgWater = CfgList_AddInt("Water");
        cfgEffects = CfgList_AddInt("Effects");
        cfgForest = CfgList_AddInt("Forest");

        cfgLandFogHaze = CfgList_AddInt("FogHaze");
        cfgLandFogLow = CfgList_AddInt("FogLow");
        cfgHardwareShaders = CfgList_AddInt("HardwareShaders");
        cfgForceShaders1x = CfgList_AddInt("ForceShaders1x");
      }

      CfgList_LoadAndPrepare();
      FirstCall = false;
      Render.prepareStates();
    }
  }

  public static void deactivate()
  {
    if (bActivated) {
      bActivated = false;
      End();
    }
  }

  private static native void SetDrawMesh(boolean paramBoolean);

  public static void setDrawMesh(boolean paramBoolean)
  {
    if (bDrawMesh == paramBoolean) return;
    bDrawMesh = paramBoolean;
    SetDrawMesh(paramBoolean);
  }
  public static boolean isDrawMesh() {
    return bDrawMesh; } 
  private static native void Begin(int paramInt1, int paramInt2);

  private static native void End();

  private static native int TexGetFlags();

  private static native void SetPolygonOffset(float paramFloat1, float paramFloat2);

  private static native void GetPolygonOffset(float[] paramArrayOfFloat);

  static { GObj.loadNative();
  }
}