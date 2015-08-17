#pragma once

#include <afxsock.h> 

class DartDataServer
{
public:
	DartDataServer(void);
	~DartDataServer(void);

	void runSocket();
	void sendDarts(float * darts);

private:
	bool m_bRunning;
	bool m_bClientConnected;
	bool m_bAnalyzerConnected;

	CSocket * m_serverSocket;
	CSocket * m_clientSocket;
	CSocket * m_clientSocketAnalyzer;

	SOCKET m_hClientSocket;

};
