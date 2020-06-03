


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
            "LAYERS": 'stars:city',
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
    projection:pro,
    maxZoom:20
});

var map = new ol.Map({
    controls: ol.control.defaults({
        attribution: false
    }).extend([
        new ol.control.FullScreen(),
        new ol.control.ZoomSlider(),
        new ol.control.ZoomToExtent
    ]),
    target:'pt_map',
    layers: [
        cityLayers
    ],
    view:mapView
});
map.getView().fit(bounds,map.getSize());