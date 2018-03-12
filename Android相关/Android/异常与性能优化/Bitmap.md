##### recycle
表示释放bitmap的内存的时候，会释放和这个bitmap有关的native内存，同时会给GC发送一条消息，让它在没有其他对象引用这个bitmap的时候进行垃圾回收。
调用recycle方法后，该bitmap会被标记为死亡状态，所以保证该bitmap不会再被使用的时候再调用recycle方法。

##### LRU算法 
LruCache算法内部通过泛型类并用LinkedHashMap实现，同时提供get,put方法添加缓存对象操作	

内部使用`LinkedHashMap`实现，提供`get`和`post`方法，来完成缓存的添加和获取操作，当缓存满的时候，lru会提供一个`trimToSize`方法把较早的缓存对象移除添加新的缓存对象。

##### 计算inSampleSize

	
	public static int calculateInSampleSize(bitmapFactory.Options opt,int reqWidth,int reqHeight){
		final int height = opt.outHeight;
		final int width = opt.outWidth;
		int inSampleSize = 1;
		if(height > reqHeight || width > reqWidth){
			if(width > height){
				inSampleSize = Math.round((float)height / (float)reqHeight);
			}else{
				inSampleSize = Math.round((float)width / (float)reqWidth);
			}
		}
		return inSampleSize;
	}

##### 缩略图
根据inSampleSize计算出的值，来相应的保存bitmap到内存中
`opt.inJustDecodeBounds` 设置为true时调用`.decodeFile`不会把原图放在内存中，设置为false后，将计算缩放比例后的Bitmap放入内存中

##### 三级缓存
网络缓存，本地缓存，内存缓存

##### [高清加载超大图解决方案，拒绝压缩](http://blog.csdn.net/lmj623565791/article/details/49300989)
1. 使用_BitmapRegionDecoder_,显示图片局部区域
2. 自定义大图显示控件，增加手势滑动操作