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

##### View工作流程
View的 _measure_ 过程  
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
除了完成自己的measure过程外，还会遍历去调用所有子元素得measure方法，各个子元素再递归去执行这个过程。



