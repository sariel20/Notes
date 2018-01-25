#### View的工作原理
View三大流程 measure->layout->draw
##### ViewRoot
链接WindowManager和DecorView的纽带，三大流程均是通过ViewRoot完成。
View的绘制流程是从ViewRoot的`performTraversals`方法开始，经过measure,layout和draw三个过程才能最终将一个View绘制出来
_measure_ 负责测量View得宽高
_layout_ 负责确定View在父容器得位置
_draw_ 负责将View绘制在屏幕上
##### DecorView
View层得事件都先经过DecorView，然后才传递给我们得View

##### 理解MeasureSpec
很大程度的决定了View的尺寸规格
在测量过程中，系统会将View的LayoutParams根据父容器所施加得规则转换成对应的MeasureSpec，然后根据这个measureSpec来测量出View得宽高

##### View工作流程（三大过程）
#####  _measure_ 过程  （测量过程）  
measure方法中会调用View的`onMeasure`方法

 	 void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
		setMeasuredDimension(getDefaultSize(
				getSuggestedMinimumWidth(),widthMeasureSpec)
				,getDefaultSize(getSuggestedMinimumHeight(),
									heightMeasureSpec));
	}

其中`setMeasuredDimension`方法会设置View宽高得测量值；  
`getDefaultSize`，这个方法返回的是specSize，表示View测量后的大小

ViewGroup的 _measure_ 过程
ViewGroup是一个抽象类，没有重写View的`onMeasure`方法，但提供了一个`measureChiden`方法，思想是取出子元素的`LayoutParams`，然后通过`getChildMeasureSpec`创建子元素的`MeasureSpec`，接着将`MeasureSpec`直接传递给VIew的`measure`方法进行测量。
除了完成自己的measure过程外，还会遍历去调用所有子元素得measure方法，各个子元素再递归去执行这个过程。

因View的measure过程和activity生命周期不同步，所以导致执行了onCreate,onStart,onResume时某个View无法保证测量完毕从而无法获取到正确的宽高
解决办法：
1. onWindowFocusChanged
View已经初始化完毕，可以获取宽高，这个方法会被频繁调用
2. view.post(runnable)
通过post可以将一个runnable投递到消息队列尾部，然后等待looper调用此runnable的时候，View也已经初始化完毕
3. ViewTreeObserver
View树的状态发生改变时可获取宽高
4. view.measure(int widthMeasureSpec,int heightMeasureSpec)

##### _layout_ 过程（布局过程）
作用是ViewGroup用来确定子元素位置，当ViewGroup的位置被确定后，它在`onLayout`中会遍历所有的子元素并调用其`layout`方法，在`layout`方法中`onLayout`又会被调用。
`layout`方法确定View本身的位置
`onLayout`方法确定所有子元素的位置
大致流程：
1. 通过`setFrame`方法设定View四个顶点的位置
2. 调用`onLayout`方法，父容器确定子元素的位置
3. 父元素通过`onLayout`方法调用子元素的`layout`方法，子元素通过自己的`layout`方法确定自身位置

##### _draw_ 过程（绘制过程）
作用是将View绘制在屏幕上
大致流程：
1. 绘制背景`background.draw(canvas)`
2. 绘制自己 `onDraw`
3. 绘制children(子元素) `dispatchDraw`
4. 绘制装饰`onDrawScrollBars`

##### 自定义VIew
自定义View分类
1. 继承View重写`onDraw`方法
2. 继承ViewGroup派生特殊的layout
3. 继承特定的View 如TextView
4. 继承特定的ViewGroup 如LinearLayout

###### 自定义View须知
1. 让View支持`wrap_content`
直接继承View或者ViewGroup的控件，如果不在onMeasure中对wrap_content进行特殊处理，那么当外接在布局中使用wrap_content就无法达到预期效果
2. 有必要的话让VIew支持padding
直接继承View的控件，如果不在draw中处理padding，那么padding属性就不会起作用
3. 尽量不要再View中使用Handler
因为View本身提供post方法，可替代Handler
4. View中如果有线程或者动画，需要及时停止
5. View带有滑动嵌套时，需要处理好滑动冲突问题

自定义属性
1. 在values中创建自定义属性xml，attrs_开头
	
		<declare-styleable name="view">
				<attr name="属性名" format="属性" />
		</..>
2. 在自定义View构造方法中解析自定义属性