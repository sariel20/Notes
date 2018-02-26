Google在2014年推出的全新界面设计语言，并且在DesignSupport库中封装了一些具有代表性的控件和效果

##### Toolbar
标题栏，代替ActionBar
将App主题更换为Theme.AppCompat.NoActionBar或者Theme.AppCompat.Light.NoActionBar，前者表示深色主题，后者淡色主题
主题内属性：
1. colorPrimary Toolbar背景色
2. textColorPrimary Toolbar文字颜色
3. colorPrimaryDark 状态栏颜色
4. windowBackground 中间部分背景色
5. colorAccent 强调颜色，比如选中状态的颜色
6. navigationBarColor 底部功能区域背景色

简单使用：

	<android.support.v7.widget.Toolbar
		....
		<!--指定toolBar中的文字内容-->
		android:label="Fruits"
		<!--使用popupTheme可以兼容android5.0以下系统-->
		app:popupTheme="@Style/ThemeOverlay.Appcompat.Light"/>

`setSupportActionBar(toolbar);`

Toolbar中添加按钮
在menu文件夹中新建xml文件：

	<item
		android:id...
		android:icon...
		andorid:title...
		app:showAsAction=".."/>

showAsAction表示按钮位置，主要有以下几种可选
1. always 表示永远显示在Toolbar中，如果空间不够不显示
2. ifRoom 表示屏幕空间足够的情况下显示在Toolbar中，不够的话显示在菜单中
3. never 表示永远显示在菜单中
注意：Toolbar中的按钮只会显示图标，菜单中只会显示文字

	//绑定
	public boolean onCreateOptinsMenu(Menu menu){
		getMenuInflater().inflate(R.menu...,menu);
		return true;
	}
	//点击事件
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			...
		}
		return true;
	}

##### DrawerLayout
滑动菜单

	<android.support.v4.widget.DrawerLayout..>
		<FrameLayout..>
			//主屏幕显示内容
		</FrameLayout>
		//菜单部分
		...
	</...>
其中菜单必须指定layout_gravity属性，表示左侧还是右侧
`openDrawer(GravityCompat.START)` //显示菜单
`closeDrawers()`//关闭菜单

##### NavigationView
定制菜单布局的控件
需引入`compile 'com.android.support:design:code'`

	<android.support.v4.widget.DrawerLayout..>
		<FrameLayout..>
			//主屏幕显示内容
		</FrameLayout>
		//菜单部分
		<android.support.design.widget.NavigationView
			...
			<!--菜单xml文件-->
			app:menu=""
			<!--头部xml文件-->
			app:headerLayout=""/>
	</...>

##### FloatingActionButton
悬浮按钮

	<android.support.design.widget.FloatingActionButton
		...
		<!--指定位置-->
		android:layout_gravity="bottom|left"
		<!--阴影范围-->
		app:elevation="8dp"/>

##### Snackbar
Toast扩展，可与用户交互
`Snackbar.make(view,"",Snackbar.LENGTH_SHORT)
	.setAction("按钮文字",new View.OnClickListener(){...})
	.show();`

##### CoordinatorLayout
加强版FrameLayout,可以监听子控件的各种事件帮助做出响应
比如弹出的Snackbar挡住右下角的FloatingActionButton，使用CoordinatorLayout就可以让Snackbar弹出后FloatingActionButton自动向上移动，增加用户体验

##### CardView
卡片式布局，需引入`compile 'com.android.support:cardview-v7:code'`

	<android.support.v7.widget.CardView
		..>
		<TextView../>
	</..>

