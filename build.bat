@set JAVA_TOOL_OPTIONS=-Dfile.encoding=GBK
@echo ѡ�񹤳̽�Ҫ���еĻ�����������ź󰴻س���������
@echo.
@echo #######################################################################
@echo ##                       1. ���                                     ##
@echo ##          	                                                     ##
@echo ##                                                                   ##
@echo ##                                                                   ##
@echo #######################################################################
@echo.

@set env=""

:input
@set /p input=��������ţ�
@if %input%==1 (
	@set env=develop
) else if %input%==2 (
	@set env=test
) else if %input%==3 (
	@set env=staging
) else if %input%==4 (
	@set env=production
) else (
	@echo �Ƿ������룡�����ԣ�
	@echo.
	goto input
)

:: �˴���Ҫ���û��޸�MAVEN_OPTS��������ִ��mvn install����ʱ���ܻ����OutOfMemoryError����
:: �Ⱥź��治�ܼ�����
@set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m


@call mvn clean install -Dmaven.test.skip=true -Denv=%env% -e -U
@popd

@echo.
@if %ERRORLEVEL%==0 (
	@echo ���̹�����ϣ�
) else (
	@echo ���̹���ʧ�ܣ�
)

@echo.
@echo ��������˳����� && pause>nul