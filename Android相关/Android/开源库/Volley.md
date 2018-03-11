[volley解析](http://blog.csdn.net/guolin_blog/article/details/17482095)
简单使用
1. 首先需要获取到一个RequestQueue对象
2. 创建一个StringRequest对象
3. 将StringRequest对象添加到RequestQueue里面
通过newRequestQueue(...)函数新建并启动一个请求队列`RequestQueue`后，只需要往这个`RequestQueue`不断add Request即可

![enter image description here](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/volley.png)  