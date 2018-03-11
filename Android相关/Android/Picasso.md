##### [Picasso](https://www.jianshu.com/p/51dc758b52f9)
##### 功能介绍
1. 图片异步加载
`Picasso.with(context).load(url).into(iv)`

2. 图片转换

	//裁剪图片尺寸
    .resize(50, 50)
	//设置图片圆角
    .centerCrop()

3. 加载过程和错误处理

	//加载过程中的图片显示
    .placeholder(R.drawable.user_placeholder)
	//加载失败中的图片显示
	//如果重试3次（下载源代码可以根据需要修改）还是无法成功加载图片，则用错误占位符图片显示。
    .error(R.drawable.user_placeholder_error)

4. 在adapter中回收不在屏幕范围内的imageView和取消已经回收的imageView下载进程
5. 支持网络，本地，资源，Assets等加载
6. 自动添加磁盘和内存二级缓存
7. 优先级处理
8. 并发数根据网络状态改变
