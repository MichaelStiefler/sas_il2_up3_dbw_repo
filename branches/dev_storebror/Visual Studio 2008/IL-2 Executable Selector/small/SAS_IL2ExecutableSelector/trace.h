#include <windows.h>
#ifdef _DEBUG
bool _trace(TCHAR *format, ...);
#define TRACE _trace
#else
#define TRACE __noop //false && _trace
#endif