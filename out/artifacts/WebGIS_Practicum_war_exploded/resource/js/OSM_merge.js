


var format = 'image/png';
var bounds = [73.44696044921875, 3.408477306365967,
    135.08583068847656, 53.557926177978516];

var cityImage = new ol.layer.Image({
    //地图源
    source:new ol.source.ImageWMS({
        ratio:1,
        url: app.GeoServer_url+'/stars/wms',
        //参数
        params: {
            'FORMAT': format,
            'VERSION': '1.1.1',
            "LAYERS": 'stars:bou2_4p',
            "exceptions": 'application/vnd.ogc.se_inimage'
        }
    }),
    opacity:0.5
});

var osmLayer = new ol.layer.Tile({
    //瓦片地图OSM底图
    source:new ol.source.OSM(),
})

//加载矢量geojson图层
var geojsonLayer = new ol.layer.Vector({
    source:new ol.source.Vector({
        url:"resource/data/GeoJSON_HB.json",
        format: new ol.format.GeoJSON() //geojson格式
    })
})

var pro = new ol.proj.Projection({

    // // oepnlayers 会自动进行投影，这里只需要转换一下就行了
    // code:'EPSG:3857',
    // units:'m',

    code:'EPSG:4326',
    units:'degrees',
    axisOrientation:'neu'
    // global:true 这里如果要选择全局的话，必须在投影里设置切片，也就是slice参数
});

var mapView = new ol.View({
    projection:pro
});

// var overViewMap = new ol.control.OverviewMap({
//     //这里的配置必须和map的配置完全相同，否则不能显示出来
//     layers:[
//         new ol.layer.Image({
//             //地图源
//             source:new ol.source.ImageWMS({
//                 url: app.GeoServer_url+'/geoserver/stars/wms',
//                 //参数
//                 params: {
//                     'FORMAT': format,
//                     'VERSION': '1.1.1',
//                     "LAYERS": 'stars:bou2_4p',
//                     "exceptions": 'application/vnd.ogc.se_inimage'
//                 }
//             })
//         })
//     ],
//     view:new ol.View({
//         projection:pro
//     })
// });

var map = new ol.Map({
    controls: ol.control.defaults({
    }).extend([
        new ol.control.FullScreen(),
        new ol.control.ZoomSlider(),
        new ol.control.ZoomToExtent()
    ]),
    target:'map',
    layers: [
        osmLayer,
        cityImage,
        geojsonLayer
    ],
    view:mapView
});

map.getView().fit(bounds,map.getSize());