//------------------------     Openlayers  style部分  --------------------------------------------------------------//


//矢量样式
let fill = new ol.style.Fill({
    color: 'rgba(255,141,155,0.6)'
});

let stroke = new ol.style.Stroke({
    color: '#3399CC',
    width: 1.25
});

let iconStyle =new ol.style.Icon(({
    scale:0.05,
    src: 'resource/image/res2_4m.png'
}));
let locateStyle = new ol.style.Icon({
    anchor:[0.5,1],// 用图标的哪一点放到定位点上
    // anchorOrigin:"top-right",// 锚点的起算位置
    // anchorXUnits:"fraction",//锚点的x，y单位， fraction表示百分比
    scale:0.15,
    src:'resource/image/locate2.png'
})
const styleVector = new ol.style.Style({
    stroke: stroke,
    fill: fill,
    // image:new ol.style.Circle({
    //     radius: 2,
    //     fill:new ol.style.Fill({
    //         color: 'rgb(255,0,0)'
    //         })
    //     }),
    image:locateStyle
});

let highlightStyle = new ol.style.Style({
    fill: new ol.style.Fill({
        color: 'rgba(255,255,255,0.7)'
    }),
    stroke: new ol.style.Stroke({
        color: '#3399CC',
        width: 3
    }),
    image:new ol.style.Icon({
        anchor:[0.5,1],// 用图标的哪一点放到定位点上
        // anchorOrigin:"top-right",// 锚点的起算位置
        // anchorXUnits:"fraction",//锚点的x，y单位， fraction表示百分比
        scale:0.15,
        src:'resource/image/locate_highlight.png'
    })
});
