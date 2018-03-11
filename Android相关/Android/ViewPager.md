##### [ViewPage](https://www.jianshu.com/p/f70073f7e837#comments)
作用：左右切换当前view，实现滑动效果。
- Viewpager继承ViewGroup，是一个容器，在里面添加内容
- ViewPager类需要PagerAdapter适配器提供数据，与ListView类似

##### 使用
1. xml文件中加入标签`<android.support.v4.view.ViewPager/>`
2. 代码中加载要显示的内容，view或者fragment
3. 用相应的Adapter关联内容和viewPager
- PagerAdapter 数据源：List<View>
- FragmentPagerAdapter 数据源：List<Fragment>
- FragmentStatePagerAdapter 数据源：List<Fragment>
4. 在activity中绑定Adapter
- ViewPagerAdapter
	
	viewPager.setAdapter(new MyViewPagerAdapter(views));
	viewPager.setCurrentItem(0);
	viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	//设置页面切换时候的监听器(可选，用了之后要重写它的回调方法处理页面切换时候的事务)

- FragmentPagerAdapter

	vp.addOnPageChangeListener(this);
	//设置页面切换时的监听器(可选，用了之后要重写它的回调方法处理页面切换时候的事务)
	vp.setAdapter(new MyFragmentAdapter(getSupportFragmentManager(), list)

5. 设置切换、滑动动画
使用方法`setPageTransformer()`设置动画

##### FragmentStatePagerAdapter和FragmentPagerAdapter的区别
后者每一个生成的Fragment都会保存在内存中，而前者只保留当前显示的Fragment，其他离开视线的Fragmet会被销毁。
当拥有大量页面时，应使用前者。

##### [ViewPage滑动接口介绍](https://www.jianshu.com/p/73187304ffd7)