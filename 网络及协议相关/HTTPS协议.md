HTTPS并不是一个单独的协议，而是对工作在加密连接（SSL/TLS)上的常规HTTP协议。通过TCP和HTTP之间加入TLS来加密

##### SSL/TLS协议
SSL协议是一种安全传输协议，TLS是SSLv3.0的升级版  
![image](https://github.com/sariel20/StudyNotes/blob/master/Android相关/img/https.png)  

HTTPS架构图  
![image](https://github.com/sariel20/StudyNotes/blob/master/Android相关/img/https2.png)  

##### TLS/SSL
##### 密码学原理
对称加密：
加密数据用的密钥和解密数据用的密钥是一样的
不对称加密：（SSL使用不对称加密）
1. 私有密钥，一方保管
2. 公有密钥，双方公有 

##### 数字证书
数字证书就是互联网通讯中标志通讯各方身份信息的一串数字

为什么需要数字证书？
请求方需要证明所想要的目标是从目标主机发送来的，而不是第三方篡改后发送过来的

![image](https://github.com/sariel20/StudyNotes/blob/master/Android相关/img/https3.png)  

https实际就是TCP与HTTP层之间加入了SSL/TLS来为上层得安全保驾护航，主要用到对称加密，非对称加密，证书等技术进行客户端与服务器得数据加密传输，最终达到保证整个通信的安全性