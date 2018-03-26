[retrofit](https://github.com/square/retrofit)
##### [retrofit使用和解析](https://www.jianshu.com/p/a3e162261ab6)
##### 使用步骤
`compile 'com.squareup.retrofit2:retrofit:2.0.2'`
1. 创建实体类，根据返回数据格式解析定义
2. 创建用于描述网络请求的接口
- Retrofit将网络请求抽象成java接口，采用注解描述网络请求参数和网络配置请求参数
- 接口中的每个方法都需要使用注解标注

	public interface GetRequest_Interface {
	    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
	    Call<Translation>  getCall();
	    // @GET注解的作用:采用Get方法发送网络请求
	 
	    // getCall() = 接收网络请求数据的方法
	    // 其中返回类型为Call<*>，*是接收数据的类（即上面定义的Translation类）
	    // 如果想直接获得Responsebody中的内容，可以定义网络请求返回值为Call<ResponseBody>
	}

- 注解类型：
网络请求，如@GET,@POST等；标记类，如@FormUrlEncoded;网络请求参数，如@Header,@URL等
3. 创建Retrofit实例

	Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") // 设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) // 设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 支持RxJava平台
                .build();
4. 创建网络请求接口实例

		// 创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);
        //对 发送请求 进行封装
        Call<Reception> call = request.getCall();

5. 发送网络请求并处理返回数据

	//发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 对返回数据进行处理
                response.body().show();
            }
            //请求失败时候的回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                System.out.println("连接失败");
            }
        });
	  // 发送网络请求（同步）
	  Response<Reception> response = call.execute();
	  // 对返回数据进行处理
	  response.body().show();

##### 工作流程
1. 通过解析网络请求接口的注解配置网络请求参数
2. 通过动态代理生成网路请求对象
3. 通过网络请求适配器将网络请求对象进行平台适配，平台包括android,rxjava,guava,java8
4. 通过网络请求执行器发送网络请求
5. 通过数据转换器解析服务器返回的数据
6. 通过回调执行器切换线程
7. 用户在主线程处理返回结果

- 网络请求适配器CallAdapter,将默认的网络请求执行器转换成适合被不同平台来调用的网络请求执行期模式
- 网络请求执行器Call,创建Http请求
- 数据转换器Converter，将返回数据解析成我们需要的数据类型
- 回调执行器CallBackExecutor，线程切换

[源码分析图](https://upload-images.jianshu.io/upload_images/944365-56df9f9ed647f7da.png?imageMogr2/auto-orient/)

retrofit.create(Class)内部返回一个动态代理对象  
动态代理指的是：当你要调用某个类的方法前，插入你想要执行的代码。动态代理主要是利用了java反射机制
`Call<Reception> call = request.getCall();`
代码中request其实就是一个动态代理对象，并不是一个接口，当调用getCall时，执行的就是动态代理的方法 
调用后retrofit把接口翻译成一个http请求，也就是MethodHandler对象，这个对象中包含了：
- OkHttpClient发送网络请求的工具
- RequestFactory包含http请求的url,header等信息
- CallAdapter请求返回的数据类型
- Converter 数据转换器
当调用call.enqueue方法时就发送了这个请求，然后就可以处理Response数据了

接口
- CallBack
这个接口就是请求数据返回的接口
- Converter
这个接口的作用是将http返回的数据解析成java对象
- Call
这个接口的作用是发送一个http请求，默认是okHttpCall，子类可基于HttpClient或者HttpUrlConnetction的请求工具
- CallAdapter 
主要作用就是将Call对象转换成另一个对象，支持RxJava