### Fragment
1. Fragment为什么被称为第五大组件
Android3.0引入，起初是解决大屏幕Android设备
有自己的生命周期，可以动态灵活的加载到Activity中，依附于Activity
2. 加载到activity的两种方式
- 静态加载
.xml文件中定义，标签定义
- 动态加载
使用FragmentManager	
获取FragmentTransaction	
创建需要添加的Fragment	
动态添加Fragment,即将创建的fragment添加到ACtvitiy布局文件中定义的占位符	
commit()结束	

3. FragmentPageAapter与FragmentStatePagerAdapter区别
前者适用于页面较少的情况，后者适用于页面较多的情况	
后者在每次切换ViewPage的时候是回收内存的，所以适合页面较多的情况	
前者是与Activity进行分离，所以适合比较少的页面，对内存影响较少

4. 生命周期
- onAttach
Fragment与ACtivity关联之后调用，此时Activity还没有创建完成
- onCreateView
初次创建Fragment调用
- onViewCreated
Fragment界面已经被绘制完毕，可以初始化Framgent控件资源
- onActivityCreated
Activity被渲染成功之后调用
- onStart
Fragment可见
- onResume
可交互
- onPause
- onStop
- onDestoryView
Fragment即将结束
- onDestory
即将被销毁
- onDetach
销毁

场景调用
- 屏幕灭掉，回到桌面
onPause()->onSaveInstanceState()->onStop()
- 屏幕解锁，回到应用
onStart()->onResume()
- 切换其他的Fragment
onPause()->onStop()->onDestroyView()
- 切换回本身
onCreateView()->onActivityCreated()->onStart()->onResume()

5. Fragment通信
- Fragment中调用Activity的方法使用getActivity
- 再Activity中调用Framgnet中的方法，使用接口回调
- Fragment互相通信，使用findFragmentById

6. replace,add,remove
replace替换Fragment实例，将最上层替换为该Fragment
add将Fragment添加到最上层
remove将Fragment在队列中删除