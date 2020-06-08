/***
 * 高亮显示模块，实际上就是替换一种style 响应 pointmove 事件，进行要素遍历
 * @type {null}
 */

let selected = null;

map.on('pointermove', function(e) {
    if (selected !== null) {
        console.log(selected)
        selected.setStyle(styleVector);
        selected = null;

        //要素被样式被修改过
        map.getTargetElement().style.cursor="auto"
    }

    map.forEachFeatureAtPixel(e.pixel, function(f) {
        if(f.style_!=null){
            map.getTargetElement().style.cursor="pointer"
            selected = f;
            f.setStyle(highlightStyle);
            return true;
        }
        else{
            map.getTargetElement().style.cursor="auto"
        }
    });
});