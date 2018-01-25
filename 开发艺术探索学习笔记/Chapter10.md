#### Android消息机制
Handler是Android消息机制的上层接口，通过Handler可以轻松的将任务切换到子线程去执行。
使用Handler可以很方便的在子线程中将更新UI操作切换到主线程中，或者需要在子线程中进行耗时操作，如I/O，访问网络等。
Handler的运行需要底层的`MessageQueue`和`Looper`支撑。
__MessageQueue__ 消息队列，它的内部存储了一组消息，以队列的形式对外提供插入和删除工作。内部采用单链表的数据结构存储消息列表。
__Looper__ 循环，`Looper`的任务是无限循环的从`MessageQueue`中获取消息并处理
`Looper`中还有一个特殊的概念`ThreadLocal`,作用是可以在每个线程中互不干扰的提供并存储数据。

##### 为什么不允许在子线程中访问UI?
因为Android的UI控件不是线程安全的，如果在多线程中并发访问肯呢个会导致UI控件处于不可预期的状态。
这一检查工作在`ViewRootImpl`中的`checkThread`方法完成

##### Handler工作原理
工作主要包含消息的发送和接收过程。
`Handler`创建时会采用当前线程的`looper`来构建内部消息循环系统。
通过`Handler`的`post`方法将一个`Runnable`投递到`Handler`内部的`Looper`去处理，也可以通过`Handler`的`send`方法发送一个消息。
当`Handler`的`send`被调用时，它会调用`MessageQueue`的`enqueueMessage`方法将这个消息放入消息队列中，然后`Looper`发现新消息并处理，最终消息的`Runnable`或者`Handler`的`handlerMessage`方法就会被调用。

##### ThreadLocal工作原理
ThreadLocal是一个线程内部数据存储类
Looper,ActivityThread以及AMS中都用到了ThreadLocal，当某些数据是以线程为作用域并且不同线程具有不同的数据副本的时候，就可以考虑采用ThreadLocal。

		threadLocal.set(true);
		Log.e(TAG, "ThreadLocal(main): " + threadLocal.get() + "");
		new Thread("Thread#1") {
		    @Override
		    public void run() {
		         threadLocal.set(false);
		         Log.e(TAG, "Thread#1: " + threadLocal.get() + "");
		    }
		}.start();		

        new Thread("Thread#2") {
            @Override
            public void run() {
                Log.e(TAG, "Thread#2: " + threadLocal.get() + "");
            }
        }.start();
输出结果
`ThreadLocal(main): true
Thread#1: false
Thread#2: null`

##### MessageQueue消息队列工作原理
主要包含两个操作，插入和读取。
读取操作本身会伴随着删除，插入对应方法是`enqueueMessage`，读取对应方法是`next`，读取后会将消息从队列中移除。

##### Looper工作原理
消息循环，不停地从MessageQueue中获取消息，有就处理，没有就阻塞。
为当前线程创建一个Looper：
	
	new Thread("#Thread"){
		....run(){
			//为当前线程创建一个Looper
			Looper.prepare();
			.....			
			//开启消息循环	
			Looper.loop();
			};
	}.start();

##### 主线程的消息循环
Android的主线程就是ActivityThread，主线程的入口方法为`main`，`main`方法中系统会通过`Looper.prepareMainLooper()`来创建主线程的Looper以及MessageQueue，并通过`Looper.loop()`开启主线程的消息循环。
消息循环开始后，ActivityThread还需要一个Handler来和消息队列进行交互，这个Handler就是ActivityThread.H，它内部定义了一组消息类型，主要包含四大组建的启动和停止过程。
