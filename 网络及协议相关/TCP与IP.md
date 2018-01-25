#### TCP/IP网络模型

![image](https://github.com/sariel20/StudyNotes/blob/master/Android相关/img/tcp1.png)  

##### 实体层/物理层
硬件对接，0101的电子信号等

##### 链接层
1. 协议
	以太网协议规定了电信号得分组方式
2. mac地址
	每个网卡都有一个独一无二得mac地址
3. 广播
	以太网采用广播方式发送数据包
	![image](https://github.com/sariel20/StudyNotes/blob/master/Android相关/img/tcp2.png)  

##### 网络层
区分哪些MAC地址属于同一个子网络
1. ip协议，规定网络地址得协议
2. ip数据包，根据ip协议发送的数据
3. arp协议，需要一种机制，能够从ip地址得到mac地址
	__核心原理:ARP协议也是发出一个数据包，它所在子网络得每一台主机，都会收到这个数据包__

##### 传输层
当一个数据包从互联网发来得时候，需要区分是哪个应用所需要得数据包
建立端口与端口之间的通信

##### 应用层
规定应用程序得数据格式
![image](https://github.com/sariel20/StudyNotes/blob/master/Android相关/img/tcp3.png)  