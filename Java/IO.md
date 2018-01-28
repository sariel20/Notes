##### java网络编程
1. IP和端口号
ip地址用于识别网络中的通信实体，比如主机，打印机等。
端口号用于区分具体通信程序。一个通信实体，不能有两个通信程序使用同一个端口号。

2. tcp和udp协议
TCP，面向连接保证可靠传输协议。通过tcp得到的是一个有序的无差错数据流。
UDP，无连接协议。每个数据包都是一个独立的信息。

3. URL 
统一资源定位。

##### socket
![enter image description here](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/socket.png)  

客户端
1. 创建客户端Socket实例，指定服务器地址和端口号
2. 获取输出流，向服务端发送信息
3. 获取输入流，并读取服务器相应信息
4. 关闭资源

服务端
1. 创建ServerSocket对象，绑定监听端口
2. 通过accept()方法监听客户端请求
3. 连接建立后，通过输入流读取客户端发送的请求信息
4. 通过输出流向客户端发送信息
5. 关闭资源

##### 阻塞IO
1. BIO数据在写入OutputStream 或者从InputStream读取时都有可能会阻塞
2. 当前一些需要大量HTTP长连接的情况

##### NIO
工作原理
![enter image description here](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/nio.png)  

通信模型
采用双向通道进行数据传输 
![enter image description here](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/nio2.png)  