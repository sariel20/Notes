[Butterknife解析](https://www.jianshu.com/p/0f3f4f7ca505)

一个依托java注解机制来实现辅助代码生成的框架

![enter image description here](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/butterknife1.png)  

原理
1. 开始会扫描java中所有ButterKnife注解
2. `ButterKnifeProcessor-><className>$$ViewBinder`
3. 调用bind发那个发加载生成的ViewBinder类

View不能设置成private 或者static（影响性能）
