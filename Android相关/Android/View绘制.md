##### View树绘制流程
1. onMeasure
测量过程，测量视图大小，从顶层父View到子View地鬼调用measure方法
2. onLayout
布局过程，确定视图位置，进行页面布局，父view根据measure子view所得到的布局大小和布局参数将子view放在合适的位置上
3. onDraw 
绘制过程，将视图绘制出来


##### measure
递归过程，从上到下有序的进行遍历，根据父容器对子容器的一些测量参数获取到子容器的长宽高，然后返回给父容器进行统一测量
- LayoutParams
指定视图宽高的参数。可设定具体的值，wrap_content，match_parent
- MeasureSpec
测量规格，32位int，最高2位表示模式，剩下30位表示尺寸大小
可设置三种模式：
1. UNSPECIFIED
不对view有任何限制，要多大给多大
2. EXACTLY
父容器已经检测出view所需要的精准大小，对应layoutParams的match_parent和具体数值这两种模式
3. AT_MOST
父容器制定了一个可用大小即SpecSize,对应layoutParams的wrap_content

重要方法：
- measure
- onMeasure(widthMeasureSpec,heightMeasureSpec)
实现测量逻辑 
- setMeasuredDimension()
完成测量过程

##### layout
通过测量尺寸来摆放view的位置

##### draw
将view绘制在屏幕上

两个容易混淆的方法
1. invalidate()
请求系统，如果大小没有发生变化就不会调用
2. requestLayout()
方向变化，尺寸变化就会调用这个方法