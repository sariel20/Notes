##### 简单使用
第一步
`OkHttpClient client = new OkHttpClient();`
第二步
创建`Request`对象，封装请求报文信息，如url，请求头，请求体，涉及的标志位等
使用Builder构建
第三步
同步：`response = client.newCall(request).execute();`
异步：`client.neCall(request).enqueue(new Callback(){})`
请求成功回调`onResponse`
请求失败回调`onFailure`

##### OkHttp源码解析
![image](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/okhttp1.png)

![image](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/okhttp2.png)