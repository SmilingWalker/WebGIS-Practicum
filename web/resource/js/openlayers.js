


var format = 'image/png';
var bounds = [73.44696044921875, 3.408477306365967,
    135.08583068847656, 53.557926177978516];

var cityImage = new ol.layer.Image({
    //地图源
    source:new ol.source.ImageWMS({
        ratio:1,
        url: 'http://www.whugiserlc.cn:80/geoserver/stars/wms',
        //参数
        params: {
            'FORMAT': format,
            'VERSION': '1.1.1',
            "LAYERS": 'stars:BGD',
            "exceptions": 'application/vnd.ogc.se_inimage'
        }
    })
});
var pro = new ol.proj.Projection({
    code:'EPSG:4326',
    units:'degrees',
    axisOrientation:'neu'
    // global:true 这里如果要选择全局的话，必须在投影里设置切片，也就是slice参数
});

var mapView = new ol.View({
    projection:pro
});

var overViewMap = new ol.control.OverviewMap({
    //这里的配置必须和map的配置完全相同，否则不能显示出来
   layers:[
       new ol.layer.Image({
           //地图源
           source:new ol.source.ImageWMS({
               url: 'http://www.whugiserlc.cn:80/geoserver/stars/wms',
               //参数
               params: {
                   'FORMAT': format,
                   'VERSION': '1.1.1',
                   "LAYERS": 'stars:BGD',
                   "exceptions": 'application/vnd.ogc.se_inimage'
               }
           })
       })
   ],
    view:new ol.View({
        projection:pro
    })
});

var map = new ol.Map({
    controls: ol.control.defaults({
        attribution: false
    }).extend([
        overViewMap,
        new ol.control.FullScreen(),
        new ol.control.ZoomSlider(),
        new ol.control.ZoomToExtent
    ]),
    target:'map',
    layers: [
        cityImage
    ],
    view:mapView
});