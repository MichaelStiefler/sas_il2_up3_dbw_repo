#pragma once
#define SELECTOR_MUTEX				L"SAS IL-2 EXECUTABLE SELECTOR MUTEX"
#define EXE_OFFSET_XMX				0x00022304
#define EXE_OFFSET_LAA				0x00000116
#define EXE_DATA_LAA				{0x2F}
#define EXE_OFFSET_MUTEX			0x0002206C
#define EXE_DATA_MUTEX				"MutexStartIL2FB"

#define TIMER_MESSAGE				L"IL-2 will start in %d seconds..."
#define TIMER_MESSAGE_1				L"IL-2 will start in 1 second..."
#define WM_IL2_STARTED				WM_USER+101
#define WM_IL2_STOPPED				WM_USER+102
#define WM_IL2_LOADED				WM_USER+103
#define WM_IL2_START_ERROR			WM_USER+104
#define WM_IL2_INSUFFICIENT_RAM		WM_USER+105
#define OPERATION_MODE_START		0
#define OPERATION_MODE_SETTINGS		1
#define TIMER_COUNTDOWN				1
#define TIMER_REENABLE_START		2

#define RAM_MESSAGE_GOOD			0
#define RAM_MESSAGE_CRITICAL		1
#define RAM_MESSAGE_ERROR			2

#define FILE_OPERATION_RETRIES		50

#define IL2_WINDOW_CAPTION			L"Il2-Sturmovik Forgotten Battles"
#define IL2_WINDOW_CLASS			L"MaddoxRtsWndClassW"

#define IL2_EXE_NAME				L"il2fb.exe"
#define IL2_CREATED_NAME			L"~~Kan.sas"
#define APP_SPARE_NAME				L"il2fb.sas"
#define WRAPPER_NAME				L"~~Arkan.sas"
#define lengthof(a) (sizeof a / sizeof a[0])
#define RunWorker(ThreadStartRoutine) 	CloseHandle(CreateThread(NULL,0,(LPTHREAD_START_ROUTINE)ThreadStartRoutine,NULL,0,NULL))

//*************************************************************************
// Function Prototypes
//*************************************************************************
void FillDropdown();
void LaunchIl2();
void ShowLastError();
int stepRamSize(int baseRamSize);
void GetFilesAndPaths();
void ReadIniSettings();
void SettingsToControls();
void ControlsToSettings();
BOOL WritePrivateProfileInt(LPCTSTR lpAppName, LPCTSTR lpKeyName, int nInteger, LPCTSTR lpFileName);
void WriteIniSettings();
void SetRamSliderTicks();
BOOL IsKeyDown(int vKey);
void CreateIl2FbExe();
void RenameExe(BOOL bStepOutOfWay);
void ShowTimerLeft(HWND hWnd, int iTimerLeft);
void EnableSettingChanges(BOOL bEnable);
void DeleteTempIl2Files();
BOOL SASES_OnInitDialog(HWND hwnd, HWND hwndFocus, LPARAM lParam);
void SASES_OnCommand(HWND hwnd, int id, HWND hwndCtl, UINT codeNotify);
void SASES_OnSize(HWND hwnd, UINT state, int cx, int cy);
void SASES_OnHScroll(HWND hwnd, HWND hwndCtl, UINT code, int pos);
//HBRUSH SASES_OnCtlColor(HWND hWnd, HDC hDC, HWND hWndCtl, UINT nCtlColor);
HBRUSH SASES_OnCtlColorEdit(HWND hwnd, HDC hdc, HWND hwndChild, int type);
LRESULT SASES_OnEraseBkgnd(HWND hwnd, HDC hDc);
LRESULT SASES_OnPaint(HWND hwnd);
void SASES_OnTimer(HWND hwnd, UINT_PTR nIDEvent);
void SASES_OnIl2Started();
void SASES_OnIl2Stopped();
void SASES_OnIl2Loaded();
void SASES_OnIl2StartError();
void SASES_OnIl2InsufficientRam(int iMessageType);
void ShowCurrentSettings();
void AfterIl2Stopped();
void BringToFront(HWND hwnd);
BOOL IsIl2Process(PPROCESS_INFORMATION ppi);
BOOL IsFolderWriteable();
BOOL IsFileWriteable(LPCTSTR szFilePath);
void CheckRamUsage();
BOOL SetFileAttributesWithRetry(LPCTSTR lpFileName, DWORD dwFileAttributes, int iAttempts);
BOOL DeleteFileWithRetry(LPCTSTR lpFileName, int iAttempts);
BOOL MoveFileWithRetry(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName, int iAttempts);
BOOL SecureDeleteFile(LPCTSTR lpFileName);
BOOL SecureSetFileAttributes(LPCTSTR lpFileName, DWORD dwFileAttributes);
BOOL SecureMoveFile(LPCTSTR lpExistingFileName, LPCTSTR lpNewFileName);

INT_PTR CALLBACK SASES_DialogProc(HWND hwndDlg, UINT uMsg, WPARAM wParam, LPARAM lParam);

BOOL bShowCountdown();
BOOL bShowRamUsage();
BOOL bShowRamUsageEdit();
BOOL bShowAutoStart();
BOOL bShowStartImmediately();
BOOL bShowExitOnIl2Quit();
BOOL bShowTIR();
BOOL bShowExpertMode();
BOOL bShowLAA();
BOOL bShowModFilesCache();
BOOL bShowMultipleInstances();