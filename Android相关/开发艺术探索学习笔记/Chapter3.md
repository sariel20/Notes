#### View 事件体系
View是所有控件的基类。ViewGroup是一组View。
##### MotionEvent
触摸事件
ACTION_DOWN  手指刚接触屏幕
ACTION_MOVE 手指在屏幕上移动
ACTION_UP 手指从屏幕松开的一瞬间
通过MotionEvent可以得到点击事件发生的xy坐标
##### TouchSlop
系统所能识别出被认为是滑动的最小距离，如果滑动的距离小于这个常量，则被系统认定为不是滑动操作
通过`ViewConfiguration.get(getContext()).getScaledTouchSlop()`获取这个常量值
##### VelocityTracker
用于追踪手指在滑动过程中的速度，包括水平和垂直方向的速度。
1. 在onTouchEvent中追踪当前单击事件的速度，`velocityTracker.addMovement(event)`
2. 获取当前滑动速度
	`.computeCurrentVelocity(1000);
	.getXVelocity();
	.getYVelocity();`
	速度 = （终点位置 - 起点位置）/ 时间段
3. `.clear();
	.recycle();`	
##### GestureDetector
用于辅助检测用户的单击，滑动，长按，双击等行为。
##### Scroller
用于实现View的弹性滑动。
实现有过度效果的滑动效果，其过程不是瞬间完成，而是在一个时间段内完成滑动。

----
##### View的滑动
##### 使用scrollTo/scrollBy
scrollTo实现了基于当前位置的相对滑动
scrollBy实现了基于所传递参数的绝对滑动
##### 使用动画
通过动画使View进行平移
使用动画移动View，主要是操作View的`translationX`和`translationY`属性，既可以采用传统的View动画，也可以采用属性动画。
##### 改变布局参数
通过改变LayoutParams的参数值完成滑动效果。
