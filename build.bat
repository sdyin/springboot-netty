@set JAVA_TOOL_OPTIONS=-Dfile.encoding=GBK
@echo 选择工程将要运行的环境，输入序号后按回车键结束：
@echo.
@echo #######################################################################
@echo ##                       1. 打包                                     ##
@echo ##          	                                                     ##
@echo ##                                                                   ##
@echo ##                                                                   ##
@echo #######################################################################
@echo.

@set env=""

:input
@set /p input=请输入序号：
@if %input%==1 (
	@set env=develop
) else if %input%==2 (
	@set env=test
) else if %input%==3 (
	@set env=staging
) else if %input%==4 (
	@set env=production
) else (
	@echo 非法的输入！请重试！
	@echo.
	goto input
)

:: 此处需要设置或修改MAVEN_OPTS，否则在执行mvn install命令时可能会出现OutOfMemoryError错误
:: 等号后面不能加引号
@set MAVEN_OPTS=-Xmx512m -XX:MaxPermSize=128m


@call mvn clean install -Dmaven.test.skip=true -Denv=%env% -e -U
@popd

@echo.
@if %ERRORLEVEL%==0 (
	@echo 工程构建完毕！
) else (
	@echo 工程构建失败！
)

@echo.
@echo 按任意键退出…… && pause>nul