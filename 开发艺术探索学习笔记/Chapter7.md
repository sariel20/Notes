### Android动画深入分析
#### View动画种类
分别是平移动画，缩放动画，旋转动画和透明度动画
对应着四个Animation四个子类：TranslateAnimation,ScaleAnimation,RotateAnimation和AlphaAnimation
```
<set>
	<alpha/>
	<scale/>
	<translate/>
	<rotate/>
</set>
```
<set>标签表示动画集合，可包含若干个子动画
属性：
- android:interpolator,表示动画集合所采用的插值器，插值器影响动画速度
- android:shareInterpolator,表示集合中的动画是否和集合共享同一个插值器
<translate>平移动画，使view完成水平和竖直方向平移的效果
属性：
- android:fromXDelta 表示x起始值
- android:toXDelta 表示x结束值
- android:fromYDelta 表示y起始值
- android:toYDelta 表示y结束值
<scale>缩放动画
属性：
- android:fromXScale 水平方向缩放的起始值
- android:toXScale 水平方向缩放结束值
- android:fromYScale 竖直方向缩放起始值
- android:toYScale 竖直方向缩放结束值
- android:pivotX 缩放的轴点x坐标，影响缩放效果
- android:pivotY 缩放轴点y坐标，影响缩放效果
<rotate>旋转动画
属性：
- fromDegrees 旋转开始角度
- toDegrees 旋转结束角度
- pivotX 旋转轴点x坐标
- pivotY 旋转轴点y坐标
<alpha>透明度动画
属性:
- fromAlpha 透明度起始值
- toAlpha 透明度结束值
- duration 持续时间
- fillAfter 动画结束后view是否停留结束位置

使用：
```
Animation a = AnimationUtils.loadAnimation(this,R.anim.xml)
view.startAnimation(a)
```

#### 自定义view动画
继承Animation抽象类，重写initialize和applyTransformation方法，前者做一些初始化工作，后者进行相应的矩阵变换

#### 帧动画
顺序播放一组预先定义好的图片，类似电影播放

#### 特殊使用场景
##### LayoutAnimation
作用于ViewGroup，为ViewGroup指定一个动画，其子view出场时都会具有这种动画效果，常应用于ListView
1. 定义LayoutAnimatiomXML
```
<layoutAnimation 
                 android:animation="@anim/test_item_animation" //item的anim文件
                 android:animationOrder="normal"//子元素动画顺序，normal按顺序显示，reverse逆向显示，random随机动画显示
                 android:delay="0.5"> //时间延迟，0.5表示150ms
</layoutAnimation>
```
2. 定义子view的动画xml
3. ListView标签添加android:layoutAnimation属性指定xml文件  