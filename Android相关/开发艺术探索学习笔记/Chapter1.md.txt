#### Activity的生命周期和启动模式
##### Activity生命周期分析
`onCreate` 正在被创建，初始化工作
`onStart` 正在被启动，处于后台可见状态，用户不可见
`onResume` 已经可见，可与用户进行交互
`onPause` 暂停状态，用户很难停止在此回调中，除非新activity是透明主题
`onStop` 即将停止，可做稍微重量级得回收工作
`onDestroy` 即将被销毁，可做最终得资源释放和回收工作
`onRestart` 正在重新启动，一般由不可见变为可见调用此方法

##### Activity启动过程
由`Instrumentation`来处理，然后通过`Binder`向`AMS`发送请求，`AMS`内部维护者一个`ActivityStack`并负责栈内得`Activity`的状态同步，`AMS`通过`ActivityThread`去同步`Activity`得状态从而完成生命周期方法得回调。

旧Activity的`onPause`调用后新的Activity才会启动。

##### 异常情况下的生命周期分析
__情况1:__
手机横竖屏切换后会销毁并重新创建Activity，这时会在`onStop`之前回调`onSaveInstanceState`，该方法用于保存该活动的临时数据，并在重新创建`onCreate`中返回保存的数据，或者在`onRestoreInstanceState`中返回，二者得区别是`onRestoreInstanceState`一旦被调用就说明参数一定是有值的，我们不用额外的判断是否为空。
默认情况下系统会在异常情况下自动保存并恢复数据，比如EditText中输入的值，ListView滚动位置等。
__情况2:__
内存不足时导致低优先级的Activity被杀死
前台>可见但并非前台>后台
内存不足时会按照上面的优先级销毁Activity
数据存储和恢复方式和情况1相同

如果不想在系统配置发生改变时重建Activity，可以给Activity指定`configChanges`属性。

##### 启动模式
1. standard 标准模式，每次启动activity都会重新创建新的实例，不管是否存在与栈内
2. singleTop 栈顶复用模式，如果新的activity已经位于栈顶，那么就不会被重新创建，同时`onNewIntent`方法会被回调，通过此方法的参数我们可以获取到当前请求得信息
3. singleTask 栈内复用模式，如果新的activity在栈内存在，则不会重新创建，并销毁此activity上面得所有活动，调到栈顶。
4. singleInstance 单实例模式，加强版singleTask，指定此模式的活动会单独位于一个任务栈内。

##### IntentFilter匹配规则
隐式调用需要Intent能够匹配目标组件得IntentFilter中所设置的过滤信息，如果不匹配将无法启动目标activity。
过滤信息有action,category,data

