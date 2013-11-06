2012-12-16 17:00 添加git说明
1、下载windows下git工具：msysgit  地址：http://msysgit.googlecode.com/files/Git-1.8.0-preview20121022.exe
2、无目录登陆准备：
	linux下配置.netrc文件，windows下_netrc文件
	内容： machine code.google.com login xw8852xiaowei@gmail.com password [generated googlecode.com password：https://code.google.com/hosting/settings]
3、windows新建环境变量HOME指定为_netrc文件所在目录
4、创建git所用的空间目录 打开git-bash操作
5、切换至空间目录 （E:\git-code） cd /e/git-code
6、git init初始化目录
7、git clone https://code.google.com/p/msx7-android-tools/拷贝项目
8、git config --globaluser.email "xw8852@126.com"
	git config --globaluser.name "xw8852@126.com"
	配置email和名称
	git初始化完毕，其他操作参加git命令详解
—————————————————————————————————————— ————————————————————————————
2012-12-16 16:18 初始化项目
