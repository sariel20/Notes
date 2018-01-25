客户端和服务端的请求标准
##### 协议：  
指计算机通信网络中两台计算机之间进行通信所必须共同遵守的规定或规则
##### HTTP协议：  
__超文本传输协议__是一种通信协议，它允许将超文本标记语言HTML文档从Web服务器传送到客户端的浏览器

##### URI
uniform resourec identifier,统一资源标识符，用来唯一的标识一个资源
如：file://a:1234/b/c/d.txt
组成部分：
1. 访问资源的命名机制
2. 存放资源的主机名
3. 资源自身的名称，由路径表示，着重强调于资源

##### URL
uniform resource locator，统一资源定位器，它是一种具体的URI，即URL可以用来标示一个资源，而且还指明了如何定位这个资源
组成部分：
1. 协议
2. 存有该资源的主机IP地址
3. 主机资源的具体地址

##### HTTP协议特点
1. 简单快速
2. 无连接，限制每次连接只处理一个请求，节省传输时间
3. 无状态，对以前处理的事务没有记忆能力

##### Cookie
![image](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/http.png)
Cookie技术是客户端的解决方案，Cookie就是由服务器发给客户端的特殊信息，而这些信息以文本文件的方式存放在客户端，然后客户端每次向服务器发送请求的时候都会带上这些特殊信息
![image](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/cookie.png)

##### Session
Session是另一种记录客户状态的机制，不同的是Cookie保存在客户端浏览器中，而Session是保存在服务器上。客户端浏览器访问服务器的时候，服务器把客户端信息以某种形式记录在服务器上。
工作原理
1. 创建Session
2. 服务器为该Session生成唯一SessionId
3. 创建之后，就可以调用Session相关的方法往Session中增加内容
4. 当客户端再次发送请求的时候，会将这个SessionId带上，服务器接受到请求之后就会依据SessionId找到相应的Session

##### 比较容易混淆的知识点
 1. http1.1 和 http1.0的区别
	缓存处理
	带宽优化及网络连接的使用
	Host头处理
	长连接  
 2. get和post的区别
	 get：获取资源，提交数据大小有限制，有安全问题
	 post：提供和更新资源，提交数据大小无限制
 3. cookie和session的区别
	存放位置不同，cookie在客户端，session在服务端
	存取方式不同
	安全性
	有效期不同
	对服务器造成的压力不同
 4. Etag和if-None-Match referer
