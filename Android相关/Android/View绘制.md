##### View基础知识
_什么是View_
所有控件得基类。View是一种界面层得控件的一种抽象。
除了View还有ViewGroup，控件组，内部包含多个View，如LinearLayout
_View位置参数，坐标系_
View主要位置由四个顶点组成，分别对应View得四个属性：top,left,right,bottom
其中top是左上角众坐标，left是左上角横坐标，right是右下角横坐标，bottom是右下角众坐标
View宽高和坐标得关系：
width = right - left
height = bottom - top
3.0开始，View增加了几个额外的参数：x,y,translationX,translationY
xy是view得左上角坐标，而translationX和translationY是view左上角相对于父容器得偏移量


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


#### [自定义View](https://www.jianshu.com/p/146e5cec4863)
1. View的分类
- 单一视图，即一个View，如TextView
- 视图组，即多个View组成的ViewGroup,如LinearLayout
2. View简介
View是Android所有控件的基类
View为表现在屏幕上的各种视图

View事件体系	
MotionEvent  手指触碰屏幕后所产生的一系列事件	
TouchSlop 系统所能识别出得最小距离	
VelocityTracker 速度追踪	
GestureDetector 手势检测，单击，长按，双击，等行为	

View的构造函数：

	//如果View是在java代码里面new的，调用第一个构造函数
	public CircelView(Context context) {
        super(context);
    }

    //如果View是在.xml中声明的，则调用第二个构造函数
    //自定义属性是从AttributeSet中获得的
    public CircelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //不会自动调用，一般是第二个构造函数里主动调用
    //如View有style属性时
    public CircelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Api22之后才使用
    //不会自动调用，一般是第二个构造函数里主动调用
    //如view有style属性时
    public CircelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

##### 无论是measure过程，layout过程，draw过程，永远都是从View树的根部节点开始测量或计算，即树顶端开始，一层一层一个分支一个分支的进行，即树形递归，最终计算整个View树中各个View，最终确定整个View树的相关属性

3. 坐标系
定义：  
屏幕左上角为坐标原点  
向右x轴增大方向  
向下y轴增大方向  

View位置由4个顶点决定  
##### View的位置相对于父控件而言  
Top，子View上边界到父View上边界的距离   view.getTop()  
Left,子View左边界到父VIew左边界得距离	  .getLeft()  
Bottom,子View下边距到父View上边界的距离	.getButtom()  
Right,子View右边界到父view左边界的距离		.getRight()  

MotionEvent中  
getX,getY表示触摸点相对于其所在组件坐标系的坐标  
getRawX,getRawY表示触摸点相对于屏幕默认坐标系的坐标  

4. 角度（angle）与弧度（radian）  	

自定义view实际上是将一些简单的形状通过计算从而组合到一起形成得效果	
角度与弧度定义:  	
两条从圆心向圆周射出的射线形成的夹角  	
弧长/圆周长*360 = 角度  	
弧长/半径r = 弧度  	
相互转换： ang = 180/π * rad  	

5. 颜色相关  	
- 颜色模式  
ARGB8888 四通道高精度 32位  
ARGB4444 四通道低精度 16位  
RGB565 android屏幕默认模式 16位  
Alpha8 透明通道 8位  
字母表示通道类型  
数值表示该类型用多少位二进制描述  

### measure过程  
- ViewGroup.LayoutParams  
布局参数类，指定视图view的高度height和宽度width等布局参数  

MeasureSpec 测量规格类，测量view大小的依据，可决定视图的大小  
测量规格MeasureSpec = 测量模式mode + 测量大小size  
其中测量模式有三种  
1. UNSPECIFIED  不确定的
父视图不约束子视图View，一般自定义中用不到  
2. EXACTLY 确定尺寸
父视图为子视图指定一个确切的尺寸，子视图大小必须在该指定尺寸内  
应用于match_parent(强制性使子视图大小扩展至与父视图大小相等)和具体数值  
3. AT_MOST 
父视图为子视图制定一个最大尺寸，子视图必须确保自身&所有子视图可适应在该尺寸内  
应用于wrap_content自适应大小  
该模式下父视图无法确定子视图尺寸，只能由子视图自身根据需求计算尺寸  

	// 1. 获取测量模式（Mode）
    int specMode = MeasureSpec.getMode(measureSpec)

    // 2. 获取测量大小（Size）
    int specSize = MeasureSpec.getSize(measureSpec)

    // 3. 通过Mode 和 Size 生成新的SpecMode
    int measureSpec=MeasureSpec.makeMeasureSpec(size, mode)

源码分析  
	
	public static class MeasureSpec {
	//进位大小= 2的30次方
	//int的大小为32位，所以进位30位，使用31和32做标志位
    private static final int MODE_SHIFT = 30;
    private static final int MODE_MASK  = 0x3 << MODE_SHIFT;
    public static final int UNSPECIFIED = 0 << MODE_SHIFT;
    public static final int EXACTLY     = 1 << MODE_SHIFT;
    public static final int AT_MOST     = 2 << MODE_SHIFT;
    //根据提供的size和mode得到一个详细的测量结果，即measureSpec
    public static int makeMeasureSpec(int size,int mode) {
            return size + mode;
    }
    //获得测量模式
    public static int getMode(int measureSpec) {
        //noinspection ResourceType
        return (measureSpec & MODE_MASK);
    }
    //获得测量大小
    public static int getSize(int measureSpec) {
        return (measureSpec & ~MODE_MASK);
    }

MeasureSpec计算  
子View的MeasureSpec根据子View的布局参数（LayoutParams)和父容器的MeasureSpec值计算得来，具体逻辑封装在getChildMeasureSpec  
View自身LayoutParams+父容器MeasureSpec = 子ViewMeasureSpec = 最终宽高  
	
	//getChildMeasureSpec源码分析
	//根据父视图MeasureSpec和布局参数LayoutParams计算单个子view的MeasureSpec
	//参数说明：
	//spec 父view的详细测量值
	//padding view当前尺寸的内边距和外边距（padding，margin）
	//childDimension 子视图的布局参数
	public static int getChildMeasureSpec(int spec, int padding, int childDimension) {
		//父view的测量模式
        int specMode = MeasureSpec.getMode(spec);
        //父view的大小
        int specSize = MeasureSpec.getSize(spec);
        //通过父view计算出子view = 父大小 - 边距
        int size = Math.max(0, specSize - padding);
        //子view想要的实际大小和模式
        int resultSize = 0;
        int resultMode = 0;
        switch (specMode) {
        	//父view强加给子view确切的值
        	//一般父view设置为match_parent或者固定值
            case MeasureSpec.EXACTLY:
            	//当子view的layoutParams>0，即有确切的值
                if (childDimension >= 0) {
                	//子view大小为自身所赋的值
                    resultSize = childDimension;
                    resultMode = MeasureSpec.EXACTLY;
                    //当子view的LayoutParams为match_parent时（-1）
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                	//子view大小为父view大小
                    resultSize = size;
                    resultMode = MeasureSpec.EXACTLY;
                    //当view的layoutParams为wrap_content(-2)
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                	//子view决定自己得大小，但最大不能超过父view
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                }
                break;
            //当父view的模式为AT_MOST时。父view强加给子view一个最大的值
            case MeasureSpec.AT_MOST:
            //同上
                if (childDimension >= 0) {
                    resultSize = childDimension;
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                    resultSize = size;
                    resultMode = MeasureSpec.AT_MOST;
                }
                break;
            //父容器不对view有任何限制，要多大给多大
            case MeasureSpec.UNSPECIFIED:
                if (childDimension >= 0) {
                	//子view大小为自身所赋的值
                    resultSize = childDimension;
                    resultMode = MeasureSpec.EXACTLY;
                } else if (childDimension == LayoutParams.MATCH_PARENT) {
                	//因为父view为UNSPECIFIED，所以match_parent的话子类大小为0
                    resultSize = View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                    resultMode = MeasureSpec.UNSPECIFIED;
                } else if (childDimension == LayoutParams.WRAP_CONTENT) {
                	//同上
                    resultSize = View.sUseZeroUnspecifiedMeasureSpec ? 0 : size;
                    resultMode = MeasureSpec.UNSPECIFIED;
                }
                break;
        }
        return MeasureSpec.makeMeasureSpec(resultSize, resultMode);
    }

总结：  
- EXACTLY  
具体数值：EXACTLY + childSize  
match_parent：EXACTLY + parentSize 父容器的剩余空间  
wrap_content：AT_MOST + parentSize 大小不超过父容器的剩余空间  
- AT_MOST  
具体：EXACTLY + childSize  
m:AT_MOST + parentSize 大小不超过父容器的剩余空间   
w:AT_MOST + parentSize  
- UNSPECIFIED  
具体：EXACTLY + childSize  
m: UNSPECIFIED + 0  
w: UNSPECIFIED + 0  

##### 单一view的measure过程
具体流程：  
measure()->onMeasure()->setMeasureDimension()->getDefaultSize()
- measure()
基本测量逻辑判断，调用onMeasure()进行下一步
- onMeasure()
1. 根据view宽高的测量规格计算view的宽高值：getDefaultSize()
2. 存储测量后的子view的宽高值：setMeasuredDimension()，最终方法

	//measure
	public final void measure(int widthMeasureSpec, int heightMeasureSpec) {
        // 参数说明：View的宽 / 高测量规格
        ...
        int cacheIndex = (mPrivateFlags & PFLAG_FORCE_LAYOUT) == PFLAG_FORCE_LAYOUT ? -1 :
                mMeasureCache.indexOfKey(key);
        if (cacheIndex < 0 || sIgnoreMeasureCache) {
            
            onMeasure(widthMeasureSpec, heightMeasureSpec);
            // 计算视图大小 ->>分析1
        } else {
            ...
     	
    }

    //分析1，onMeasure
    //根据view宽高的测量规格计算view的宽高值，getDefaultSize()
    //存储测量后view的宽高，setMeasureDimension()
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
	    // 参数说明：View的宽 / 高测量规格
	    setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),  
	                         getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));  
	    // setMeasuredDimension() ：获得View宽/高的测量值 ->>分析2
	    // 传入的参数通过getDefaultSize()获得 ->>分析3
	}

	//分析2，setMeasureDimension
	//存储测量后的宽高
	protected final void setMeasuredDimension(int measuredWidth, int measuredHeight) {  
		//参数说明：测量后子View的宽 / 高值
        // 将测量后子View的宽 / 高值进行传递
            mMeasuredWidth = measuredWidth;  
            mMeasuredHeight = measuredHeight;  
          
            mPrivateFlags |= PFLAG_MEASURED_DIMENSION_SET;  
        } 
    // 由于setMeasuredDimension（）的参数是从getDefaultSize()获得的
    
    //分析3 getDefaultSize
    //根据view宽高的测量规格计算view的宽高值
    public static int getDefaultSize(int size, int measureSpec) {  
        // 参数说明：
        // size：提供的默认大小
        // measureSpec：宽/高的测量规格（含模式 & 测量大小）
            // 设置默认大小
            int result = size; 
            
            // 获取宽/高测量规格的模式 & 测量大小
            int specMode = MeasureSpec.getMode(measureSpec);  
            int specSize = MeasureSpec.getSize(measureSpec);  
          
            switch (specMode) {  
                // 模式为UNSPECIFIED时，使用提供的默认大小 = 参数Size
                case MeasureSpec.UNSPECIFIED:  
                    result = size;  
                    break;  
                // 模式为AT_MOST,EXACTLY时，使用View测量后的宽/高值 = measureSpec中的Size
                case MeasureSpec.AT_MOST:  
                case MeasureSpec.EXACTLY:  
                    result = specSize;  
                    break;  
            }  
         // 返回View的宽/高值
            return result;  
        }


##### ViewGroup的measure过程
具体流程： 
measure()->onMeasure()->measureChildren()->measureChild()->  
getChildMeasureSpec()->遍历子view测量&合并->setMeasureDimension()  

- measure 
基本测量逻辑判断
调用onMeasure进入下一步
- onMeasure（需复写）
1. 遍历所有子view&测量：measureChildren()
2. 合并所有子view尺寸，计算出最终viewGroup尺寸
3. 存储测量后的view宽高:setMeasureDimension()
- measureChildren()
遍历子view
调用measureChild进行子view下一步测量
- measureChild()
计算单个子view的MeasureSpec:getChildMeasureSpec()
调用每个子view的measure()进行下一步测量
- getChildMeasureSpec()
计算子view的MeasureSpec参数
（计算因素：父view的MeasureSpec和子view的LayoutParams）
- setMeasuredDimension()
存储测量后的子View宽高


### layout过程 
计算视图位置，即计算view的四个顶点位置	
##### 单一view的layout过程
开始-> layout() -> onLayout() ->完成
- layout()
确定view本身位置，即设置view本身四个顶点位置	

	// 1. 确定View的位置：setFrame（） / setOpticalFrame（）
    // 即初始化四个顶点的值、判断当前View大小和位置是否发生了变化 & 返回 
    // ->>分析1、分析2
    boolean changed = isLayoutModeOptical(mParent) ?
            setOpticalFrame(l, t, r, b) : setFrame(l, t, r, b);

    // 2. 若视图的大小 & 位置发生变化
    // 会重新确定该View所有的子View在父容器的位置：onLayout（）
    if (changed || (mPrivateFlags & PFLAG_LAYOUT_REQUIRED) == PFLAG_LAYOUT_REQUIRED) {  
        onLayout(changed, l, t, r, b);

1. setFrame()	
根据传入的四个位置值，设置view顶点位置
2. setOpticalFrame()	
实际上内部调用setFrame()	

- onLayout()	
由于单一view是没有子view的，所以onLayout是一个空实现	

##### ViewGroup的layout过程
开始-> layout() -> onLayout() -> (layout() -> onLayotu())n -> 完成
一开始计算viewGroup调用的是自身的layout和onLayout
当开始遍历子view和计算子view位置时，调用的是子view的layout和onLayout

	//onLayout()
	//计算viewGroup包含所有子view在父容器的位置
	//自定义viewGroup时必须复写onLayout
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
     // 参数说明
     // changed 当前View的大小和位置改变了 
     // left 左部位置
     // top 顶部位置
     // right 右部位置
     // bottom 底部位置
     // 1. 遍历子View：循环所有子View
          for (int i=0; i<getChildCount(); i++) {
              View child = getChildAt(i);   
              // 2. 计算当前子View的四个位置值
                // 2.1 位置的计算逻辑
                ...// 需自己实现，也是自定义View的关键
                // 2.2 对计算后的位置值进行赋值
                int mLeft  = Left
                int mTop  = Top
                int mRight = Right
                int mBottom = Bottom
              // 3. 根据上述4个位置的计算值，设置子View的4个顶点：调用子view的layout() & 传递计算过的参数
              // 即确定了子View在父容器的位置
              child.layout(mLeft, mTop, mRight, mBottom);
              // 该过程类似于单一View的layout过程中的layout（）和onLayout（），此处不作过多描述
          }
      }
    }


### draw过程
绘制view视图	
##### 单一的view绘制过程
开始-> draw() -> drawBackground() -> onDraw() -> dispatchDraw() ->
onDrawScrollBars() -> 结束	
- draw()
绘制自身view，过程：
1. 绘制view背景
2. 绘制view内容
3. 绘制子view
4. 绘制装饰（滚动条，渐变框等）
注意：在调用该方法前必须完成layout过程，viewGroup没有复写此方法

- drawBackground()
绘制view本身的背景
- onDraw()
绘制view本身的内容	
由于view内容各不相同，所以该方法是一个空实现，自定义绘制过程中，需子类实现复写该方法
- dispatchDraw()
由于单一view中无子view，所以该方法为空实现
- onDrawScrollBars()
绘制装饰

##### ViewGroup绘制过程
开始-> draw() -> drawBackground() -> onDraw() -> dispatchDraw() ->
（draw() -> drawBackground() -> onDraw() -> dispatchDraw() -> onDrawScrollBars())n -> 
onDrawScrollBars() -> 结束	
多数方法与单一view类似
- dispatchDraw()
遍历所有子view&绘制子view

