


var format = 'image/png';
var bounds = [73.44696044921875, 3.408477306365967,
    135.08583068847656, 53.557926177978516];
var pro = new  ol.proj.Projection({
    code:'EPSG:4326',
    units:'degrees',
    axisOrientation:'neu'
});
var cityLayers = new ol.layer.Image({
    source: new ol.source.ImageWMS({
        ratio: 1,
        url: 'http://localhost:5858/geoserver/stars/wms',
        params: {
            'FORMAT': format,
            'VERSION': '1.1.1',
            "LAYERS": 'stars:city',
            "exceptions": 'application/vnd.ogc.se_inimage'
        }
    })
});
var provinceLayer =  new ol.layer.Image({
    source: new ol.source.ImageWMS({
        ratio: 1,
        url: app.GeoServer_url+'/stars/wms',
        params: {
            'FORMAT': format,
            'VERSION': '1.1.1',
            "LAYERS": 'stars:province',
            "exceptions": 'application/vnd.ogc.se_inimage'
        }
    })
});

var mapView = new ol.View({
    projection:pro,
    Zoom:8,
    minZoom:3,
    maxZoom:10
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
        provinceLayer,
        cityLayers
    ],
    view:mapView
});
var coord = null;
map.getView().fit(bounds,map.getSize());
map.on("singleclick",function (e) {
    var pixel = map.getEventPixel(e.originalEvent);
    coord = map.getCoordinateFromPixel(pixel);
    // map.hasFeatureAtPixel(pixel,{
    //     layerFilter:function (layer) {
    //         console.log("图层是否吻合");
    //         return console.log(layer.getSource().params_["LAYERS"]==="stars:city");
    //     }
    // });
    console.log("是否有要素");
    console.log(
        map.hasFeatureAtPixel(pixel,
            {
            layerFilter:function (layer) {
                console.log("图层是否吻合");
                return console.log(layer.getSource().params_["LAYERS"]==="stars:province");
            },
        })
    );
    // console.log("是否有要素:"+map.hasFeatureAtPixel(pixel));
    var coor = [7.85, 47.983333];
    var pt = new ol.geom.Point(ol.proj.fromLonLat(coord, 'EPSG:4326'));
    console.log(pt);
    console.log(coord);
    console.log(coord.join(','));
    GetFeatureByPt(coord);
});


//GeoServer的WFS服务，利用Filter来查询要素

////根据地图要素的坐标数据查询
////根据地图要素的属性信息进行查询
function GetFeatureByPt(coord) {
    console.log("开始属性查询");
    var requestbody = new ol.format.WFS().writeGetFeature({
        srsName:'EPSG:4326',
        featureNs:"http://localhost:5858/geoserver/stars",
        featurePrefix:'stars',
        featureTypes:['stars:province'],
        outputFormat: 'application/json',
        filter:ol.format.filter.intersects('geom',new ol.geom.Point(ol.proj.fromLonLat(coord, 'EPSG:4326')))
    });
    console.log(new XMLSerializer().serializeToString(requestbody))
    fetch(app.GeoServer_url+'/wfs',{
        method:'POST',
        mode: 'no-cors',
        body: new XMLSerializer().serializeToString(requestbody),
        headers : {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    }).then(function (response) {
        console.log(response.json());
    });
}
