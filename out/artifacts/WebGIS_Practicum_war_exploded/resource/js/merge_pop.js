// 用于做弹窗测试，利用覆盖层来做

let container = document.getElementById('popup');
//获取id为popup-content的div标签
let content = document.getElementById('popup-content');
//获取id为popup-closer的a标签
let closer = document.getElementById('popup-closer');

/**
 * Create an overlay to anchor the popup to the map.
 */
let overlay = new ol.Overlay({
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
    let elementA = document.createElement('a');
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
    let elementDiv = document.createElement('div');
    //设置div标签的内容
    // setInnerText(elementDiv, info.att.text);
    setInnerText(elementDiv, "城市详细内容");
    //将div标签加入到内容div标签中
    content.appendChild(elementDiv);

    //创建一个图像标签
    let elementImg = document.createElement('img');
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
//查找要素            setFeaturesTable(feature)
//             return;
map.on("singleclick",function (e) {

    let pixel = map.getEventPixel(e.originalEvent);
    coord = map.getCoordinateFromPixel(pixel);
    map.forEachFeatureAtPixel(pixel,function (feature,layer) {

        //FIXME 这里我是根据样式来进行区分的，因为我给要素图层添加了样式，但是其他图层没有，
        if(feature.style_!=null){
            let coords = feature.values_.geometry.flatCoordinates;
            let fid = feature.values_.fid;
            console.log(feature);
            mapView.setCenter(coords);
            mapView.setZoom(6);
            //页面的中心点跳转，
            //ajax 查询详细信息
            $.ajax({
                url:"./infoServlet",
                data:{
                    fid:fid
                },
                type:"POST",
                dataType:"json",
                success:function (data) {
                    if(data.status===200){
                        clearInfoTable();
                        clearSearchTable();
                        //需要通过建立html插入数据表单
                        var geojson = data.data.geojson;
                        //实例化整个geojson
                        let feature = JSON.parse(geojson).features[0];
                        showInfoForm(feature)
                        return;
                    }
                    else {
                        alert("未检索到结果");
                    }
                }
            });
        }else {
            console.log("不在这个图层内")
        }
    },{
        hitTolerance:10
    });
    return;

    // console.log("是否有要素:"+map.hasFeatureAtPixel(pixel));
    let coor = [7.85, 47.983333];
    let pt = new ol.geom.Point(ol.proj.fromLonLat(coord, 'EPSG:4326'));
    console.log(pt);
    console.log(coord);
    console.log(coord.join(','));

});
