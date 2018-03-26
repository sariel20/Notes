## [Rxjava介绍](http://gank.io/post/560e15be2dca930e00da1083)
### 什么是RxJava?
官方解释：一个在javaVM上使用可观测的序列来组成异步的、基于事件的程序的库。
就是一个异步框架，类似android中的handler，AsyncTask，RxJava相对来说更加简洁。可以把复杂逻辑穿成一条线的简洁	

### 原理
概念：扩展的观察者模式	

四个基本概念：
- Observable被观察者
- Observer观察者
- subscribe订阅、事件
- 被观察者和观察者通过订阅产生联系，从而被观察者可以在需要的时候发出时间来通知观察者

三个回调方法：
- onNext发出时需要出发onCompleted方法作为标志
- onCompleted事件队列完结。当不会再有onNext发出时，需要出发此方法作为标志
- onError事件队列异常。事件处理过程发生异常后回调该方法，并终止队列操作

###### onCompleted和onError互斥，回调一个就不会回调另外一个

### 基本实现
##### 观察者
```
Observer<String> observer = new Observer<String>() {
    @Override
    public void onNext(String s) {
        Log.d(tag, "Item: " + s);
    }

    @Override
    public void onCompleted() {
        Log.d(tag, "Completed!");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(tag, "Error!");
    }
};
```
除了Observer接口外，还内置了一个实现Observer的抽象类Subscriber。实现方式基本相同，区别：
1. Subscriber增加了onStart方法，在事件发出前调用，用于一些初始化操作。可选
2. unsubscribe 用于取消订阅，调用后Subscriber将不会再接受事件。

##### 被观察者
决定什么时候触发事件和触发怎样的事件。
```
Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
    @Override
    public void call(Subscriber<? super String> subscriber) {
        subscriber.onNext("Hello");
        subscriber.onNext("Hi");
        subscriber.onNext("Aloha");
        subscriber.onCompleted();
    }
});
```
create是最基本创造事件序列的方法，rxjava还提供了一些快捷创建事件队列的方法如just,from(T[])
```
Observable observable = Observable.just("Hello", "Hi", "Aloha");

String[] words = {"Hello", "Hi", "Aloha"};
Observable observable = Observable.from(words);
```

##### 订阅
创建了Observable和Observer后，再用subscribe方法将他们连接起来
```
observable.subscribe(observer);
```

此外subscribe还支持不完整回调，如：
```
Action1<String> onNextAction = new Action1<String>() {
    // onNext()
    @Override
    public void call(String s) {
        Log.d(tag, s);
    }
};
Action1<Throwable> onErrorAction = new Action1<Throwable>() {
    // onError()
    @Override
    public void call(Throwable throwable) {
        // Error handling
    }
};
Action0 onCompletedAction = new Action0() {
    // onCompleted()
    @Override
    public void call() {
        Log.d(tag, "completed");
    }
};

// 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
observable.subscribe(onNextAction);
// 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
observable.subscribe(onNextAction, onErrorAction);
// 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
```
Action0和Action1是RxJava中的接口，0代表无参，1代表一个参数，一共有九个Action接口

#### 线程控制Scheduler
用于线程切换，几种场景：
- Schedulers.immediate() 直接在当前线程运行，不指定线程，默认
- .newThread() 启用新线程并操作
- .io 读写文件，数据库，网络交互等操作的线程，与newThread的区别是该内部实现是用一个无数量上限的线程池，可重用空闲线程
- .computation 计算所使用的Scheduler.固定线程池，大小为CPU核数。
- .mainThread Android主线程
区分io和computation:
- 前者不要用于计算，避免创建不必要的线程
- 后者不要用于读写操作，避免浪费CPU

```
Observable.just(1, 2, 3, 4)
    .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
    .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
    .subscribe(new Action1<Integer>() {
        @Override
        public void call(Integer number) {
            Log.d(tag, "number:" + number);
        }
    });
```

#### 变换
例子：
```
Observable.just("images/logo.png") // 输入类型 String
    .map(new Func1<String, Bitmap>() {
        @Override
        public Bitmap call(String filePath) { // 参数类型 String
            return getBitmapFromPath(filePath); // 返回类型 Bitmap
        }
    })
    .subscribe(new Action1<Bitmap>() {
        @Override
        public void call(Bitmap bitmap) { // 参数类型 Bitmap
            showBitmap(bitmap);
        }
    });
```
map方法将参数中的String转换成了Bitmap对象后返回