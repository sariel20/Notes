##### Android构建流程
![enter image description here](https://github.com/sariel20/StudyNotes/blob/master/Android%E7%9B%B8%E5%85%B3/img/gj.png)  

编译java文件为.class文件，通过aapt生成资源文件为R.java，然后合并构建为.dex文件，通过apkbuilder打包为.apk，通过zipalgn对apk进行对齐操作，这样会减少apk对内存上的占用，至此完成构建

[jenkins持续集成构建](https://jenkins.io/index.html)
解决工程复杂程度高，自动化持续构建，减少构建时间，
