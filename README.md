#### Java人脸识别算法

*   C/C++算法JNA封装

*   人脸检测

*   人脸特征

*   人脸识别

*   有授权(联系QQ:319720040)

#### 调用说明

> 引入 LjFaceAippv2.jar和lib/下的 jna-4.5.1.jar、opencv-341.jar
>
>
> 调用示例：
> LjAippv2Core  core = new LjAippv2Core();
> //创建人脸句柄
> int handle = core.LjFdCreateDetector(String modelPath)
> // 人脸检测（可能一张图片多个人脸)
> List faces = core.LjFdDetect(int lJhandle, byte\[] imageData)
> //人脸比对句柄
> int LjFvCreateVerifier(String modelPth)
> //提取人脸特征
> LjFeature LjFvExtractFeature(int LjHandlePtr, byte\[] imageData, LjFace face)
> //人脸比对(比较特征值)
> float LjFvCompareFeatures(LjFeature feature1, LjFeature feature2)
> //活体检测
> LjFace LjFdBiologyDetect(int lJHandle, byte\[] visibleImageData, byte\[] redImageData)
