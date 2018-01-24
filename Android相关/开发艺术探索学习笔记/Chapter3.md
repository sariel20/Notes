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
##### 动画
通过动画使View进行平移
使用动画移动View，主要是操作View的`translationX`和`translationY`属性，既可以采用传统的View动画，也可以采用属性动画。
##### 改变布局参数
通过改变View的LayoutParams的参数值完成滑动效果。

##### 三种滑动方式对比
scrollTo/scrollBy，操作简单，适合对View的内容滑动，不能滑动View本身
动画，操作简单，主要适用于没有交互的View和实现复杂的动画效果
改变布局参数，操作稍复杂，适用于有交互的View

##### 弹性滑动
Scroller
`mScroller.startScroll(startX,startY,dx,dy,duration)`
startX，startY表示滑动起点；dx,dy表示滑动距离；duration表示滑动时间
调用后会在内部调用`invalidate`方法，导致View重绘
原理根据时间流逝百分比来算出scrollX和scrollY改变的百分比并计算出当前的值，类似插值器。

动画
本身就是渐近过程，天然的具有弹性效果

延时策略
通过发送一系列延时消息从而达到一种渐近的效果，可使用Handler或者View的postDelayed方法，也可以使用线程的sleep方法

----
#### 事件分发机制
##### 点击事件的传递规则
所谓点击事件的事件分发，其实就是对MotionEvent事件的分发过程，即当一个MotionEvent产生以后，系统需要把这个事件传递给一个具体的View，而这个传递过程就是分发过程。 

三个重要方法 
`boolean dispatchTouchEvent(MotionEvent ev)` 
用来进行事件分发，返回结果受当前View的`onTouchEvent`和下级View的`dispatchTouchEvent`方法的影响，表示是否消耗当前事件 
`boolean onInterceptTouchEvent(MotionEvent ev)` 
在上述方法内部调用，判断是否拦截某个事件 
`boolean onTouchEvent(MotionEvent ev)` 
在第一个方法中调用，处理点击事件 

事件传递结论
1. 同一个事件序列是指从手指接触屏幕那一刻起，到手指离开屏幕那一刻结束，在这个过程产生得一系列事件，这个事件序列以down事件开始，中间含有数量不定得move事件，最终up结束
2. 正常情况下，一个事件序列只能被一个view拦截且消耗，这一条得原因可以参考3）
3. 某个view一旦决定拦截，那么这一个事件序列都只能由它处理，并且他的`onInterceptTouchEvent`不会再被调用
4. 某个view一旦开始处理事件，如果不消耗`ACTION_DOWN`事件，（onTouchEvent返回false），那么同一事件序列中的其他事件都不会再交给他处理，并且事件将重新交由他的父元素去处理，即父元素得`onTouchEVent`会被调用
5. 如果view不消耗出`ACTION_DOWN`以外得其他事件，那么这个点击事件会消失，此时父元素得`onTouchEvent`并不会被调用，并且当前view可以持续受到后续得事件，最终这些消失得点击事件会传递给activity处理
6. viewGroup默认不拦截任何事件，
7. view没有`onInterceptTouchEvent`方法，一旦有点击事件传递给他，那么他的`onTouchEvent`方法就会被调用
8. view得`onTouchEvent`默认都会消耗事件，除非他是不可点击的
9. view得`enable`属性不影响`onTouchEvent`得默认返回值
10. `onClick`会发生得前提是当前view是可点击得，并且她收到了down和up事件
11. 时间传递过程是由外向内，即事件总是先传递给父元素，然后再有父元素交给view，通过`requestDisallowInterceptTouchEvent`方法可以在子元素中干预父元素事件分发过程，但是`ACTION_DOWN`事件除外

![enter image description here](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/sjff.png) 

##### 滑动冲突
常见的冲突场景
1. 外部滑动方向与内部滑动方向不一致
2. 外部滑动方向与内部滑动方向一致

处理规则
场景1，当用户左右滑动时，需要让外部的view拦截点击事件，当用户上下滑动时，需要让内部的view拦截点击事件
场景2，根据业务处理

滑动冲突解决方式
外部拦截法：指点击事件都先经过父容器的拦截处理，如果父容器需要此事件就拦截，不需要就不拦截。
内部拦截法：指父容器不拦截任何事件，所有事件都传递给子元素，如果子元素需要此事件就直接消耗掉，否则就交由父容器处理。

