#pragma once
//*************************************************************************
// Globals
//*************************************************************************
HWND g_hWnd;
HICON g_hIconLarge, g_hIconSmall;
int g_iModType = 0;
int g_iRamSize = 0;
BOOL g_bTIREnabled = FALSE;
BOOL g_bExpertModeEnabled = FALSE;
BOOL g_b4GBAddressSpaceEnabled = FALSE;
BOOL g_bEnableModFilesCache = FALSE;
BOOL g_bMultipleInstancesEnabled = FALSE;

HBRUSH g_hBrushYellow = NULL;
HBRUSH g_hBrushRed = NULL;
HBRUSH g_hBrushGreen = NULL;
HBRUSH g_hBrushOrange = NULL;
int g_iMessageType = 0;
BOOL g_bRamToolTipVisible = FALSE;
TCHAR* g_szModTypes[4] = {L"Stock Game", L"Classic Mod Game", L"SAS Modact 3", L"Ultrapack 3"};
int g_iRamSizes[5] = {128, 256, 512, 768, 1024};
char g_szXmxSetting[5];
TCHAR szAppPath[MAX_PATH];
TCHAR szExePath[MAX_PATH];
TCHAR szDllPath[MAX_PATH];
TCHAR szBaseExeModPath[MAX_PATH];
TCHAR szBaseExeModTIRPath[MAX_PATH];
TCHAR szBaseExeStockPath[MAX_PATH];
TCHAR szBaseDllPath[MAX_PATH];
TCHAR szIniFile[MAX_PATH];
TCHAR szPathName[MAX_PATH];
