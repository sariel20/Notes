#### Android的线程和线程池
在android中线程分为主线程和子线程，主线程主要处理和界面相关的工作，子线程往往用于耗时操作。

##### AsyncTask
轻量级异步任务类，它可以在线程池中执行异步任务，然后把执行的进度和最终结果传递到主线程并在主线程中更新UI。
三个泛型参数
`public abstract class AsyncTask<Params,Progress,Result>`
`Params` 表示参数类型
`Progress`表示后台任务执行进度的类型
`Result`表示返回结果的类型
四个核心方法
`onPreExecute()`在主线程中执行，准备工作
`doInBackground(Params...params)`，在线程池中执行异步任务
`onProgressUpdate(Progress...values)` 在主线程中执行，异步任务执行进度
`onPostExecute(Result result)` 在主线程中执行，任务完成后返回结果，即`doInBackground`的返回值
条件限制
- AsyncTask的对象必须在主线程中创建
- execute方法必须在UI线程调用
- 不要在程序中直接调用onPreExecute，onPostExecute，doInBackground和onProgressUpdate方法
- 一个AsyncTask对象只能执行一次，即只能调用一次execute方法

##### HandlerThread
继承Thread，可以使用Handler的Thread。
run方法中通过Looper.prepare()来创建消息队列，并通过.loop()开启消息循环
	
	@Override
    public void run() {
        mTid = Process.myTid();
        Looper.prepare();
        synchronized (this) {
            mLooper = Looper.myLooper();
            notifyAll();
        }
        Process.setThreadPriority(mPriority);
        onLooperPrepared();
        Looper.loop();
        mTid = -1;
    }
当明确不需要再使用HandlerThread时，可以通过`quit`或者`quitSafely`方法终止线程。

##### IntentService
继承Service，抽象类，因为是服务所以优先级比单纯的线程高，可用于执行高优先级的后台耗时任务。
	
	@Override
    public void onCreate() {
        // TODO: It would be nice to have an option to hold a partial wakelock
        // during processing, and to have a static startService(Context, Intent)
        // method that would launch the service & hand off a wakelock.

        super.onCreate();
        HandlerThread thread = new HandlerThread("IntentService[" + mName + "]");
        thread.start();

        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }
IntentService封装了HandlerThread和Handler

##### Android中的线程池
优点
- 线程重用，避免因为现成的创建和销毁带来的性能开销
- 有效控制线程池的最大并发数，避免大量线程之间抢占系统资源导致阻塞
- 能够对线程进行简单的管理，并提供定时执行以及指定间隔循环执行等功能

_ThreadPoolExecutor_
线程池的真正实现
	
	public ThreadPoolExecutor(int corePoolSize,
							int maximumPoolSize,
							long keepAliveTime,
							TimeUnit unit,
							BlockingQueue<Runnable> workQueue,
							ThreadFactory threadFactory)
`corePoolSize` 核心线程数，默认情况下，核心线程会一直存活，即使在闲置状态
`maximumPoolSize` 线程池容纳最大线程数，当活动线程达到这个数时，新任务会被阻塞
`keepAliveTime` 非核心线程闲置时长，超过这个时间就会被回收
`unit`用于指定keepAliveTime的时间单位
`workQueue` 任务队列，通过execute方法提交的Runnable对象会存储在这个参数中
`threadFactory` 线程工厂，为线程池提供创建新线程的功能

AsyncTask中对 ThreadPoolExecutor进行了配置，规格如下
- 核心线程数等于CPU核心数+1
- 线程池最大线程数为CPU核心数的2倍+1
- 核心线程无超时机制，非核心线程超时时间为1秒
- 人物队列容量为128

##### 线程池分类
1. FixedThreadPool  线程数量固定，除非线程池被关闭，否则不会被回收
2. CachedThreadPool 线程数量不定，最大线程数为Integer.MAX_VALUE
3. ScheduledThreadPool 核心线程数固定，非核心无限制，非核心闲置时立即回收
4. SingleThreadExecutor 只有核心线程，确保所有任务都在同一个线程中按顺序执行


