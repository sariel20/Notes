#### Bitmap的加载和Cache
BitmapFactory提供四类方法：`decodeFile`,`decodeResource`,`decodeStream`和`decodeByteArray`，分别用于支持从文件系统，资源，输入流以及字节数组中加载出一个bitmap对象。
##### 如何高效的加载bitmap?
采用`BitmapFactory.Option`来加载所需尺寸的图片。按一定的采样率加载缩小后的图片，降低内存占用避免OOM，提高加载bitmap时的性能。
主要用到`BitmapFactory.Options`的`inSampleSize`参数，当`inSampleSize`为1时，图片大小为原始大小，`inSampleSize`为2时，采样后的图片为原始大小的1/2，而像素为原图的1/4，其占有内存大小也为原图的1/4。
`inSimpleSize`取值应为2的指数，如1，2，4，8，16等等。
通过采样率加载图片流程：
1. 将`BitmapFactory.Options`的`inJustDecodeBounds`参数设为true并加载图片
2. 从`BitmapFactory.Options`中取出图片的原始宽高信息，它们对应`outWidth`和`outHeight`参数。
3. 根据采样率的规则并结合目标View的所需大小计算出采样率`inSampleSize`
4. 将`BitmapFactory.Options`的`inJustDecodeBounds`参数设为false，然后重新加载图片

`inJustDecodeBounds`  当设置为true时，BitmapFactory只会解析图片的原始宽高，并不会真正的加载图片。

##### LruCache
Android提供的一个缓存类。内部采用LinkedHashMap以强引用的方式存储外接的缓存对象，提供了get和put方法，缓存满了的时候，LruCache会移除较早使用的缓存对象，然后在添加新的缓存对象。
##### DiskLruCache
用于实现存储设备缓存，即磁盘缓存，通过将缓存对象写入文件系统从而实现缓存的效果。

##### ImageLoader实现
ImageLoader应具备：
- 图片的同步加载
- 图片的异步加载
- 图片压缩。获取采样率改变图片大小。
- 内存缓存。LruCache
- 磁盘缓存。DiskLruCache
- 网络拉取

