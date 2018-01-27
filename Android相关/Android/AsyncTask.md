轻量级异步抽象类，本质上就是封装了线程池和handler的异步框架

使用方法
##### 三个参数：
1. `params` 在执行AsyncTask时所需要传入的参数，可用于在后台任务中使用。
2. `progress` 后台任务执行时，如果需要在界面上显示进度，则使用这里指定的泛型作为进度单位。
3. `result` 当任务执行完毕后，如果需要对结果进行返回，则使用这里指定的泛型作为返回值类型。

##### 四个方法：
1. `onPreExecute()`  在后台任务执行前调用，用于一些初始化操作，比如显示一个进度框
2. `doInBackground(Integer... params)` 在这里处理耗时任务，任务一旦完成就通过return将任务的执行结果返回。这个方法内不可更新UI，可调用`publishProgress()`方法反馈当前任务执行的进度
3. `onProgressUpdate(Integer... values)` 当在后台任务中调用了`publishProgress()`方法后，就会很快的调用这个方法，该方法中携带的参数就是在后台任务中传递过来的，这里可以进行UI操作，比如更新进度条
4. `onPostExecute(String result)` 当后台任务执行完毕并通过`return`语句返回时，这个方法就很快会被调用。返回的数据会作为参数传递到此方法中。

开启任务调用`new MyAsyncTask().execute()`即可

##### AsyncTask机制原理
1. 本质是一个静态的线程池，AsyncTask派生出的子类可以实现不同的异步任务，这些任务都是提交到静态的线程池中执行。
2. 线程池中的工作线程执行`doInBackground()`方法执行异步任务。
3. 当任务状态改变之后，工作线程会向ui线程发送消息，AsyncTask内部的internalHandler相应这些消息，并调用相关的回调函数。

封装了线程池使用handler在ui线程和子线程之间传递

##### 注意事项
1. 内存泄露。非静态内部类持有外部类匿名引用，导致外部类想要被回收时无法被回收。
2. 生命周期。要在onDestroy中销毁AsyncTask。
3. 结果丢失。如屏幕旋转，Activity被重新创建等
4. 并行or串行。