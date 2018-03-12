##### ANR
1. 什么是ANR
Application Not Responding，应用程序无响应，用户可选择等待或者关闭	
在一个activity当中，最长的执行时间是5秒，超过5秒没有响应就会弹出ANR框		

2. 主要原因
- 应用程序得响应性是由ActivityManager和WindowManager系统监视的。当检测到任务执行时间过长时，就会弹出ANR
- 主线程被IO操作阻塞
- 主线程中存在耗时操作

3. 如何解决
- AsyncTask中处理耗时操作
- 使用Thread或者HandlerThread提高优先级
- 使用handler来处理工作线程的耗时任务
- onCreate和onResume回调中尽量避免耗时代码

4. Android哪些操作在主线程执行？
- Activity所有生命周期回调
- Service默认执行在主线程（如需开子线程应使用IntentService）
- BroadcastReceiver的onReceiver回调
- 没有使用子线程的looper的Handler的handlerMessage，post(Runnable)
- AsyncTask除了doInBackground

==========

##### OOM
1. 什么是OOM？
当前占用内存加上我们申请的内存资源超过了Dalvik虚拟机的最大内存限制就会抛出Out of memory异常，大部分造成oom的原因是bitmap

2. 容易混淆的概念
- 内存溢出
oom
- 内存抖动
短时间内大量对象被创建，然后又被释放，瞬间产生的对象会严重占用内存区域
- 内存泄漏
进程中的某个对象已经没有被引用，但是GC无法回收

3. 如何解决
- 优化Bitmap
图片显示相关，即使释放内存，图片压缩，inBitmap属性
- 其他
避免在onDraw方法里面执行对象创建；谨慎使用多进程