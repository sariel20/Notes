##### Binder相关知识
1. Linux内核基础知识
- 进程隔离/虚拟地址空间
- 系统调用
- binder驱动

2. Binder通信机制介绍
- 为什么使用binder
 - Android使用Linux内核拥有非常多的跨进程通信机制
 - 性能。在移动设备上广泛使用跨进程通信会对通信机制本身提出严格要求，而binder相当于传统的socket方式，更加高效
 - 安全。由于传统进程间通信对通信双方得身份没有进行严格验证，而binder机制支持通信双方的身份校验，也是android权限模型基础

3. Binder通信模型

4. 到底什么是binder
- 通常意义下，binder指的是一种通信机制
- 对server进程来说，binder指的是binder本地对象，对于Client来说，binder指的是binder代理对象
- 对传输过程而言，binder是可以进行跨进程传递的对象