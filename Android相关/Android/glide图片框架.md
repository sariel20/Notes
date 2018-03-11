##### [Glide使用](https://www.jianshu.com/p/7ce7b02988a4)

##### 基础使用
1. 图片加载
`Glide.with(context).load(url).into(iv);`
with还可以使用activity,frgment的对象，将图片加载与生命周期保持一致

2. 占位图设置

	.placeholder(R.drawable.place_image)//图片加载出来前，显示的图片
    .error(R.drawable.error_image)//图片加载失败后，显示的图片

只支持int和drawable类型参数

3. 缩略图
`.thumbnail( 0.2f )`
图片会显示原始图片的20%大小

4. 动画开关
Glide默认开启淡入淡出的图片效果
`.crossFade()//或者使用 dontAnimate() 关闭动画`
也可以重载`crossFade(int duration)`控制动画持续时间
支持自定义动画

5. 图片大小与剪裁
`.override(width,height)//这里的单位是px`

6. 图片缓存处理
默认将图片放入内存缓存中，磁盘缓存也是默认开启、

	DiskCacheStrategy.NONE 什么都不缓存
	DiskCacheStrategy.SOURCE 只缓存全尺寸图
	DiskCacheStrategy.RESULT 只缓存最终的加载图
	DiskCacheStrategy.ALL 缓存所有版本图（默认行为）

7. 图片请求优先级

	.priority (Priority.HIGH )
	Priority.LOW
	Priority.NORMAL
	Priority.HIGH
	Priority.IMMEDIAT

8. 显示Gif和本地video