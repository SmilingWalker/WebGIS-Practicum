// 用于做弹窗测试，利用覆盖层来做

var container = document.getElementById('popup');
//获取id为popup-content的div标签
var content = document.getElementById('popup-content');
//获取id为popup-closer的a标签
var closer = document.getElementById('popup-closer');

/**
 * Create an overlay to anchor the popup to the map.
 */
var overlay = new ol.Overlay({
    //元素内容
    element: container,
    //If set to true the map is panned when calling setPosition,
    //so that the overlay is entirely visible in the current viewport.
    //The default is false.
    autoPan: true,
    ////覆盖层如何与位置坐标匹配
    positioning: 'bottom-center',
    //事件传播到地图视点的时候是否应该停止
    stopEvent: false,
    //The animation options used to pan the overlay into view.
    //This animation is only used when autoPan is enabled.
    autoPanAnimation: {
        //动画持续时间
        duration:250
    }
});

map.addOverlay(overlay);

function addInfo2Overlay() {

    //创建一个a标签元素
    var elementA = document.createElement('a');
    //设置a标签的样式类
    elementA.className = 'markerInfo';
    //设置a标签的超链接地址
    // elementA.href = info.att.titleURL;
    elementA.href = "www.baidu.com"
    //设置a标签的文本内容
    // setInnerText(elementA, info.att.title);
    setInnerText(elementA, "测试标签");
    //将a标签元素添加到内容div标签中
    content.appendChild(elementA);

    //创建一个div标签元素
    var elementDiv = document.createElement('div');
    //设置div标签的内容
    // setInnerText(elementDiv, info.att.text);
    setInnerText(elementDiv, "城市详细内容");
    //将div标签加入到内容div标签中
    content.appendChild(elementDiv);

    //创建一个图像标签
    var elementImg = document.createElement('img');
    //指定图像标签的URL
    // elementImg.src = info.att.imgURL;
    elementImg.src = "resource/image/res2_4m.png";
    //将img标签加入到内容div标签中
    content.appendChild(elementImg);

}
//设置文本函数
function setInnerText(element,text) {
    if (typeof element.textContent == 'string') {
        element.textContent = text;
    } else {
        element.innerText = text;
    }
}
//查找要素
map.on("singleclick",function (e) {

    var pixel = map.getEventPixel(e.originalEvent);
    coord = map.getCoordinateFromPixel(pixel);
    map.forEachFeatureAtPixel(pixel,function (feature,layer) {
        console.log(feature);
        //如果点击点上图层有要素
        //先判断是哪一个要素，然后进行弹窗
    },{
        hitTolerance:10
    });
    return;

    // console.log("是否有要素:"+map.hasFeatureAtPixel(pixel));
    var coor = [7.85, 47.983333];
    var pt = new ol.geom.Point(ol.proj.fromLonLat(coord, 'EPSG:4326'));
    console.log(pt);
    console.log(coord);
    console.log(coord.join(','));

});
