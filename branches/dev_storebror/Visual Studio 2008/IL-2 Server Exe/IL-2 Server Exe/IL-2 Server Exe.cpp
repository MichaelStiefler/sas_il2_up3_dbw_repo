//*************************************************************************
// Includes
//*************************************************************************
#include "stdafx.h"
#include "IL-2 Server Exe.h"
#include "common.h"

//*************************************************************************
// Suppress new style warning messages
//*************************************************************************
#define _CRT_SECURE_NO_WARNINGS
#pragma warning( disable : 4996 )

//************************************
// Method:    _tmain
// FullName:  _tmain
// Access:    public 
// Returns:   int
// Qualifier:
// Parameter: int argc
// Parameter: _TCHAR * argv[]
//************************************
int _tmain(int argc, _TCHAR* argv[])
{
	// Make Sure IL-2 Server Executable is only running with one single instance.
	HANDLE hMutex = CreateMutex(NULL, TRUE, IL2SERVER_MUTEX);
	if ( (hMutex == NULL) || (GetLastError() == ERROR_ALREADY_EXISTS) )
	{
		if (hMutex != NULL)
		{
			ReleaseMutex(hMutex);
			CloseHandle(hMutex);
		}
		return 0;
	}

	GetFilesAndPaths();
	DeleteTempIl2Files();

	if (!IsFolderWriteable()) {
		_tprintf(L"*********************************************\r\n");
		_tprintf(L"*                 ERROR                     *\r\n");
		_tprintf(L"*********************************************\r\n");
		_tprintf(L"*                                           *\r\n");
		_tprintf(L"* Insufficient access permission in server  *\r\n");
		_tprintf(L"*            startup folder!                *\r\n");
		_tprintf(L"*                                           *\r\n");
		_tprintf(L"*********************************************\r\n");
		_tprintf(L"\r\n");
		return 1;
	}

	memset(g_szCmdLine, 0, sizeof(g_szCmdLine));
	for (int i=1; i<lengthof(argv); i++) {
		_tcscat(g_szCmdLine, L" ");
		_tcscat(g_szCmdLine, argv[i]);
	}
	TCHAR szVersionInfo[MAX_PATH];
	GetFileVersionX(szVersionInfo);
	_tprintf(L"Starting SAS/UP IL-2 Server\r\n");
	_tprintf(L"%s\r\n", szVersionInfo);

	ReadIniSettings();
	CreateIl2FbExe();

	StartIl2Server();

	DeleteTempIl2Files();

	ReleaseMutex(hMutex);
	CloseHandle(hMutex);

	return 0;
}

