#pragma once
//*************************************************************************
// Globals
//*************************************************************************
extern HWND g_hWnd;
extern LPWSTR g_lpCmdLine;
extern HICON g_hIconLarge, g_hIconSmall;
extern int g_iOperationMode;
extern int g_iNumIl2InstancesRunning;
extern int g_iModType;
extern int g_iRamSize;
extern int g_iStartDelay;
extern int g_iDelayLeft;
extern BOOL g_bStartImmediately;
extern BOOL g_bExitWithIl2;
extern BOOL g_bTIREnabled;
extern BOOL g_bExpertModeEnabled;
extern BOOL g_b4GBAddressSpaceEnabled;
extern BOOL g_bEnableModFilesCache;
extern BOOL g_bMultipleInstancesEnabled;
extern BOOL g_bAutoStartIl2;

extern HBRUSH g_hBrushYellow;
extern HBRUSH g_hBrushRed;
extern HBRUSH g_hBrushGreen;
extern HBRUSH g_hBrushOrange;
extern HFONT g_hListBoxFont;
extern int g_iMessageType;
extern BOOL g_bRamToolTipVisible;
extern TCHAR* g_szModTypes[4];
extern TCHAR* g_szModTypeExeParms[4];
extern int g_iRamSizes[5];
extern char g_szXmsSetting[5];
extern char g_szXmxSetting[5];
extern char g_szModFolder[5];
extern char g_szFilesFolder[7];
extern TCHAR szAppPath[MAX_PATH];
extern TCHAR szIniFile[MAX_PATH];
extern TCHAR szPathName[MAX_PATH];
extern TCHAR szIl2StartPath[MAX_PATH];
extern TCHAR szIl2CreatedFilePath[MAX_PATH];
extern TCHAR szWrapperFilePath[MAX_PATH];
extern TCHAR szAppSparePath[MAX_PATH];
