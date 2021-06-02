package com.example.webh5.Image_click

/**
 *
 * @Package: com.example.webh5
 * @ClassName: WebViewText
 * @Author: szj
 * @CreateDate: 5/31/21 4:26 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class WebViewText {

//    <img class="currentImg" id="currentImg" onload="alog &amp;&amp; alog('speed.set', 'c_firstPageComplete', +new Date); alog.fire &amp;&amp; alog.fire('mark');" src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimages.h128.com%2Fupload%2F201908%2F24%2F201908242353068534.jpg%3Fx-oss-process%3Dimage%2Fauto-orient%2C1%2Fresize%2Cm_fill%2Cw_1421%2Ch_595%2Fquality%2Cq_100%2Fformat%2Cjpg&amp;refer=http%3A%2F%2Fimages.h128.com&amp;app=2002&amp;size=f9999,10000&amp;q=a80&amp;n=0&amp;g=0n&amp;fmt=jpeg?sec=1625046743&amp;t=215330867b75481538553a75b2d3c46d" width="700" height="467" style="top: 282px; left: 0px; width: 490px; height: 205.172px; cursor: pointer; display: block;" log-rightclick="p=5.102" title="点击查看源网页">

    var title:String = """
        <p><span style="font-size:18px">Android</span></p >
        <img src="https://c-ssl.duitang.com/uploads/item/201805/11/20180511145849_UkyGR.jpeg"  />
        
        <p><span style="font-size:18px">kotlin</span></p >
        <img src="https://images1.h128.com/upload/201908/24/201908242352201698.jpg?imageMogr2/auto-orient/thumbnail/787x/blur/1x0/quality/100"  />
        
        <p><span style="font-size:18px">C/C++</span></p >
        <img src="https://images1.h128.com/upload/201908/24/201908242353068534.jpg?imageMogr2/auto-orient/thumbnail/787x/blur/1x0/quality/100"  />
        
        <p><span style="font-size:18px">dart</span></p >
        <img src="https://images1.h128.com/upload/201908/24/201908242355285318.jpg?imageMogr2/auto-orient/thumbnail/787x/blur/1x0/quality/100" />
  
   <p><span style="font-size:18px">html</span></p >
        <img src="https://b-ssl.duitang.com/uploads/item/201708/04/20170804211410_i4myh.thumb.700_0.png"  />
    """.trimIndent()
        .replace("<img", "<img onClick=\"callAndroid(this.src)\"")



    var format =
        """<head>
            <meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0,user-scalable=no'>
             <style>img{max-width: 100%; width:auto; height:auto;}</style>
             
             <script>
                function callAndroid(url){
                        test.hello(getImages(),url);
                }
                function getImages(){      
                     var objs = document.getElementsByTagName("img");
                     var imgScr = '';       
                     for(var i=0;i<objs.length;i++){
                         if(i!=0){imgScr += '----'
                     }       
                     imgScr = imgScr + objs[i].src;
                  };
                    return imgScr;
                };
    </script></head>
        ${title.replace("<img", "<img onClick=\"callAndroid(this.src)\"")}"""



}