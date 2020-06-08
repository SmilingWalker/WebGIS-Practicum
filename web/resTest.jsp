<%--
  Created by IntelliJ IDEA.
  User: 廖成
  Date: 2020/5/26
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="DAO.DButil" %>

<%@ page contentType="text/html;charset=UTF-8" import="DAO.jdbcUtiles" language="java" %>
<html>
<head>
    <title>坐标点检测</title>
    <script src="resource/js/ol.js"></script>
    <link rel="stylesheet" href="resource/css/ol.css" type="text/css">
    <link rel="stylesheet" href="resource/css/popup.css">
<%--    element ui 引入  --%>
    <!-- 引入样式 -->
    <link rel="stylesheet" href="./resource/css/index.css">


    <style>
        .map{
            position: absolute;
            height: 100%;
            width: 100%;
            z-index: 2;
        }
        body{
            margin: 0px;
            padding: 0px;
        }
        #search_area{
            float: left;
            margin-left: 50px;
            margin-top: 50px;
            z-index: 1;
            position: fixed;
        }
        #search-div{
            width: 350px;
            height: 40px;
            text-align: center;
        }
        #search-input{
            width: 300px;
            height: 30px;
            margin-top: 5px;
        }
        .poilist{
            list-style: none;
            background-color: #b1abab;
            padding: 0;
            margin: 0;
        }
        .div-l{
            float:left;
            width:49%;
        }
        .div-r{
            float:left;
            width:49%;
        }
        .poi-item{
            font-size: 12px;
            height: 60px;
            text-align: center;
            display: flex;
            align-items: center;
        }
        .poi-item:hover{
            background-color: #ffffff;
        }
        .poi-picture{
            height: 30px;
            width: 30px;
            border-radius: 15px;
            text-align: center;
        }
        .poi-detail{
            padding: 3px;
        }
        #details-form{
            background-color: #D2D2D2;
            border-radius: 10px;
        }
        form p {
            margin: 10px;
            padding: 5px;
        }
        label,input {
            vertical-align: middle;
        }
        label {
            width: 80px;
            display: inline-block;
        }
        input {
            width: 200px;
        }
        textarea{
            width: 200px;
            height: 80px;
        }
        #login_area{
            float: right;
            margin-left: 1400px;
            margin-top: 50px;
            z-index: 3;
            position:fixed ;
            background-color: #1E9FFF;
        }
        /*.register_div{*/
        /*    position: absolute;*/
        /*    z-index: 5;*/
        /*    background-color: #E6E6E6;*/
        /*    border-width: 2px;*/
        /*    border-radius:4px;*/
        /*}*/
        #el_tabs_log{
            /*position: absolute;*/
            /*z-index: 5;*/
            /*left: 35%;*/
            /*top: 30%;*/
            /*width: 30%;*/
            /*height: 300px;*/
            /*!*background-color: #E6E6E6;*!*/
            /*border-width: 2px;*/
            /*border-radius:4px;*/
            /*text-align: center;*/
        }
        /* 用户头像的样式设置  */
        .user_info_img{
            height: 50px;
            width: 50px;
            border-radius: 25px;
            object-fit:fill;
            float:left;
            margin-right: 40px;
        }
        .user_info{
            font-size: 14px;
            color: #409eff;
            margin: 5px;
        }
        .box-card {
            width: 250px;
            z-index: 5;
            float:right;
            margin-left: 80%;
            margin-top: 20px;
            position: absolute;
        }
    </style>

</head>
<body>
    <div id="search_area">
        <div id="search-div">
            <input type="text" class="input" id = "search-input"/>
            <button id = "search-button" onclick="OnSearch()">检索</button>
        </div>
        <ul  class="poilist" id="poi-list">
<%--            <li class="poi-item">--%>
<%--                <div class="div-l">--%>
<%--                    <div class="poi-detail">--%>
<%--                        <span id="city-name">--%>
<%--                            城市名称--%>
<%--                        </span>--%>
<%--                    </div >--%>
<%--                    <div class="poi-detail">--%>
<%--                        <span id="city-pinyin">--%>
<%--                            城市拼音--%>
<%--                        </span>--%>
<%--                    </div>--%>
<%--                    <div class="poi-detail">--%>
<%--                        <span id="city-details">--%>
<%--                            城市编码--%>
<%--                        </span>--%>
<%--                    </div>--%>
<%--                </div>--%>
<%--                <div class="div-r">--%>
<%--                    <img id="city-picture" class="poi-picture"  src="resource/image/locate.png" alt="城市详情图片" />--%>
<%--                </div>--%>
<%--            </li>--%>
        </ul>
        <form id="details-form">
            <div>
<%--                <p><label>城市: </label><input type="text" name="name" /></p>--%>
<%--                <p><label>坐标: </label>X: <input type="text" name="coords-X" style="width: 60px"/> Y: <input type="text"--%>
<%--                                                                                               name="coords-Y" style="width: 60px"/></p>--%>
<%--                <p><label>城市详情: </label><textarea name="introduce" ></textarea></p>--%>
<%--    &lt;%&ndash;            判断时候有图片，如果有图片就直接传递，如果没有就展示传递图片选择框&ndash;%&gt;--%>
<%--                <p><label>城市图片: </label><input type="file" name="city_image" ></p>--%>
<%--                <p><label>城市图片: </label><img type="image" name="city_image" src="resource/image/res2_4m.png"></p>--%>
<%--                <p><label>城市编码: </label><input type="text" name="coordinates" /></p>--%>
<%--                <div style="text-align: center;padding: 5px">--%>
<%--                    <select style="text-align: center;padding: 1px">--%>
<%--                        <option value="name">名称</option>--%>
<%--                        <option value="coord">坐标</option>--%>
<%--                        <option value="image">图片</option>--%>
<%--                        <option value="intro">介绍</option>--%>
<%--                        <option value="adcode99">城市编码</option>--%>
<%--                    </select>--%>
<%--                    <button type="button" style="width: 100px;border-radius: 5px;text-align: center">--%>
<%--                    修改信息 </button></div>--%>
            </div>
        </form>
        <div id="popup" class="ol-popup">
            <a href="#" id="popup-closer" class="ol-popup-closer"></a>
            <div id="popup-content"></div>
        </div>
    </div>
    <div id="app">

<%--        <register_cpn ref="aaa"></register_cpn>--%>
<%--        <login_tab_cpn ref="bbb">--%>
<%--            <register_cpn ref="aaa" slot="register" ></register_cpn>--%>
<%--            <login_cpn ref="ccc" slot="login"></login_cpn>--%>
<%--        </login_tab_cpn>--%>
        <user_info_card @login_click="show_login_dialog" :login="login"
        :username="username" :logintime="LoginTime" @login_out_click="re_log_out"></user_info_card>
        <login_dig  v-bind:visible="logDiaVisible" @dig_close = "close_login_dialog" ref="login_dialog">
            <login_tab_cpn ref="bbb" slot="dig_content">
                <register_cpn ref="aaa" slot="register" @register_success="re_log_success"  ></register_cpn>
                <login_cpn ref="ccc" slot="login" @login_success="re_log_success"></login_cpn>
            </login_tab_cpn>
        </login_dig>
    </div>
<%--登录注册部分，两个标签--%>

    <template id="log_tab" >
        <el-tabs v-model="activeName" @tab-click="handleClick" type="border-card"  id="el_tabs_log">
            <el-tab-pane label="用户登录" name="login">
                <div style="width: 100%;margin:0 auto;text-align: center">
                    <slot name="login" ></slot>
                </div>
            </el-tab-pane>
            <el-tab-pane label="用户注册" name="register">
                <div style="width: 100%;margin:0 auto;text-align: center">
                    <slot name="register"></slot>
                </div>
            </el-tab-pane>
        </el-tabs>
    </template>

<%--    用户注册组件      --%>

    <template id="register_cpn">
        <div class="register_div">
            <el-form :model="ruleForm" label-width="100px" status-icon  :rules="rules" ref="ruleForm"
                     style="margin-right: 40px">
                <el-form-item label="用户名" prop="username">
                    <el-input autocomplete="off" v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input autocomplete="off" type="new-password" v-model="ruleForm.password"></el-input>
                </el-form-item>
                <el-form-item label="验证密码" prop="checkPass">
                    <el-input autocomplete="off" type="new-password" v-model="ruleForm.checkPass"></el-input>
                </el-form-item>
                <el-form-item >
                    <el-button type="primary" @click="submitForm('ruleForm')" >提交</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </template>

<%--     用户登录组件       --%>

    <template id="login_cpn">
        <div class="register_div">
            <el-form :model="ruleForm" label-width="100px" status-icon  :rules="rules" ref="ruleForm"
                     style="margin-top: 50px;margin-right: 40px">
                <el-form-item label="用户名" prop="username">
                    <el-input autocomplete="off" v-model="ruleForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input autocomplete="off" type="new-password" v-model="ruleForm.password"></el-input>
                </el-form-item>
                <el-form-item >
                    <el-button type="primary" @click="submitForm('ruleForm')" >登录</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </div>
    </template>
<%--    用户登录弹窗设置   --%>
    <template id="login_dia">
        <el-dialog width="30%" :visible.sync="dialogVisible" :before-close="handleClose" @close="closeDig">
            <slot name="dig_content">这是一段信息</slot>
<%--        <span slot="footer" class="dialog-footer">--%>
<%--        <el-button @click="closeDig">取 消</el-button>--%>
<%--        <el-button type="primary" @click="closeDig">确 定</el-button>--%>
<%--      </span>--%>
        </el-dialog>
    </template>

<%--     登录详细信息控制  --%>
    <template id="user_info_card">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span style="font-size: 15px">登录信息</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="login_btn">{{setLoginBtn()}}</el-button>
            </div>
            <img :src="getLoginImg()" class="user_info_img">
<%--                展示用户头像信息 没有登录则展示一个+号--%>
            </img>
            <div class="user_info">
                <div v-if="login">
                    <div >{{username}}</div>
                    <div style="margin-top: 3px;color: chocolate" >{{logintime}}</div>
                </div>
                <div v-else>
                    <div style="margin-top: 10px">当前用户未登录</div>
                </div>
            </div>
        </el-card>
    </template>
    <div id="map">
    </div>
    <script src="resource/js/ol.js"></script>
    <script type="text/javascript" src = "resource/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src  = "resource/js/app.js"></script>
    <script type="text/javascript" src="resource/js/openlayer_style.js"></script>
    <script type="text/javascript" src  = "resource/js/OSM_merge.js"></script>
    <script type="text/javascript" src = "resource/js/merge_pop.js"></script>
    <script type="text/javascript" src="resource/js/highlight_feature.js"></script>

    <!-- import Vue before Element -->
    <script src="resource/js/vue.js" type="text/javascript"></script>
    <!-- import JavaScript -->
    <script src="./resource/js/index.js"></script>


    <script type="text/javascript" >
        const geojson =<%
            String geojson = DButil.SelectAll("SELECT name as name,ST_AsGeoJson(geom) as geom  from res2_4m");
            out.print(geojson);
        %>
//------------------------     Openlayers  style部分  --------------------------------------------------------------//

//---------------------------------- openlayers  图层控制部分         -----------------------------//
        var searchFeatures;

        let foreign_id;//整个建设要素的id

        var vectorSource = new ol.source.Vector({});
        const vectorLayer = new ol.layer.Vector({
            source: vectorSource,
            style: styleVector
        });
        //加入数据
        vectorSource.addFeatures((new ol.format.GeoJSON()).readFeatures(geojson));

        //初始不设置坐标点数据

        vectorSource.clear(true);
        map.addLayer(vectorLayer);

        //配置检索结果

        //矢量标注样式设置函数，设置image为图标ol.style.Icon
        function createLabelStyle(feature){
            return new ol.style.Style({
                image: locateStyle,
                text: new ol.style.Text({
                    textAlign: 'middle',            //位置
                    textBaseline: 'hanging',         //基准线
                    font: 'normal 14px 微软雅黑',    //文字样式
                    offsetY:4,
                    text: feature.get('name'),      //文本内容
                    fill: new ol.style.Fill({       //文本填充样式（即文字颜色)
                        color: '#000'
                    })
                })
            });
        }
        function createFeatureByGeoJson(geojson) {
            //清除矢量内容
            vectorSource.clear(true);
            //渲染搜索结果
            let features = JSON.parse(geojson).features;
            searchFeatures = JSON.parse(geojson).features;
            setFeaturesTable(features)
            for(let i=0;i<features.length;i++){
                //这里通过函数更新表单，产生结果数据
                
                let coordinate = features[i].geometry.coordinates;//坐标数据
                let name = features[i].properties.name;
                let newFeature = new ol.Feature({
                    geometry: new ol.geom.Point(coordinate),  //几何信息
                    name: name,
                    fid:features[i].properties.id
                });
                newFeature.setStyle(createLabelStyle(newFeature));      //设置要素样式
                vectorSource.addFeature(newFeature);
            }
        }

        $('#search-input').bind('keypress',function(event){
            if(event.keyCode == 13)
            {
                OnSearch();
            }

        });
        function OnSearch() {
            clearInfoTable();
            clearSearchTable();
            //根据搜索框内容发送Ajax请求
            let seachInput = document.getElementById("search-input");
            let inputStr = seachInput.value;
            if(inputStr !=null && inputStr!=="" && inputStr!==undefined){
                $.ajax({
                    url:"./SearchServlet",
                    data:{
                        name:inputStr
                    },
                    type:"POST",
                    dataType:"json",
                    success:function (data) {
                        if(data.status===200){

                            var geojson = data.data.geojson;
                            //实例化整个geojson
                            console.log(JSON.parse(geojson).features);
                            createFeatureByGeoJson(geojson);
                            // vectorSource.clear(true);
                            // vectorSource.addFeatures((new ol.format.GeoJSON()).readFeatures(geojson));
                        }
                        else {
                            clearSearchTable();
                        }
                    }
                })
            }
            else {
                clearSearchTable();
                console.log("空");
            }
        }

    //-------------------------------------动态添加table 函数部分------------------------------------------------//

        //清空展示框
        function clearSearchTable() {
            let ul = document.getElementById("poi-list")
            if(ul!=null){
                ul.innerText = "";
            }
        }

        function setFeaturesTable(features) {
            let ul = document.getElementById("poi-list")
            ul.innerText = "";
            console.log(features);
            let li = '';
        //    传入图层，输出html
            for(let i=0;i<features.length;i++){
                //这里通过函数更新表单，产生结果数据

                let name = features[i].properties.name;
                let pinyin = features[i].properties.pinyin;
                let code = features[i].properties.adcode99;
                let id = features[i].properties.id;
                // let imageUrl = features[i].properties.imageUrl;
                let imageUrl = 'resource/image/locate.png'
                li = `
            <li class="poi-item" id="`+id+`" index="`+i+`">
                <div class="div-l">
                    <div class="poi-detail">
                        <span id="city-name">
                            `+name+`
                        </span>
                    </div >
                    <div class="poi-detail">
                        <span id="city-pinyin">
                            `+pinyin+`
                        </span>
                    </div>
                    <div class="poi-detail">
                        <span id="city-details">
                            `+code+`
                        </span>
                    </div>
                </div>
                <div class="div-r">
                    <img id="city-picture" class="poi-picture"  src=`+ imageUrl+` alt="城市详情图片" />
                </div>
            </li>`
                ul.insertAdjacentHTML('beforeend',li);
                //生成时绑定事件

            }
        }

        //---------------------------- 展示详细信息-------------------------------------//
        //响应某一个poi-item的点击事件 然后发送info请求
        //动态绑定事件， 发送ajax请求， 得到详情页面，
        $("#poi-list").on('click',' .poi-item',function (e) {
            let fid = e.currentTarget.id;
            //页面的中心点跳转，
            let index = e.currentTarget.getAttribute("index");
            let feature = searchFeatures[index];
            let coords = feature.geometry.coordinates;
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

                    }
                    else {
                        alert("未检索到结果");
                    }
                }
            })
        })
        //清空详情框
        function clearInfoTable() {
            let form = document.getElementById("details-form");
            if(form!=null){
                form.innerText = "";
            }
        }

        //查询结果，getinfo，然后展示到地图上
        function showInfoForm(feature) {
            let form = document.getElementById("details-form");
            clearInfoTable();
            //装配信息
            let properties = feature.properties;
            let name = properties.name;
            let pinyin = properties.pinyin;
            let intro = properties.intro;
            let image = properties.image;
            let coordinates = feature.geometry.coordinates;
            let adcode99 = properties.adcode99;
            foreign_id = properties.id;
            console.log(foreign_id);
            //构建html
            let innerHtml = `
            <p><label>城市: </label><input type="text" name="name"  value="`+name+`" id="details-name"/></p>
            <p><label>坐标: </label>X: <input type="text" name="coords-X" style="width: 70px" value="`+coordinates[0]+`" /> Y: <input type="text"
                                                                                           name="coords-Y" style="width: 70px"  value="`+coordinates[1]+`"/></p>
            <p><label>城市详情: </label><textarea name="introduce" id="details-intro" >`+intro+`</textarea></p>`
                +getImageHtml(image)+
                `<p><label>城市编码: </label><input type="text" id="details-adcode99" name="code" value="`+adcode99+`"/></p>
            <div style="text-align: center;padding: 5px;">
                    <select style="text-align: center;padding: 1px" id="modify-select">
<!--                        <option value="name">名称</option>-->
<!--                        <option value="coord">坐标</option>-->
                        <option value="image">图片</option>
                        <option value="intro">介绍</option>
<!--                        <option value="adcode99">城市编码</option>-->
                    </select>
                    <button type="button" onclick="updateInfo()"  id="modify-info" style="width: 100px;border-radius: 5px;text-align: center">
                    修改信息 </button></div>`;


            form.insertAdjacentHTML("beforeend",innerHtml);

        }

        function updateInfo() {
            console.log("++++++++++++++++++");
            //还需要取得 修改的id
            //得到修改结果  首先判断修改类型，实际上后面开发时利用Vue v-if 使得非修改编辑框为disable，绑定,
            let sel = document.getElementById("modify-select");

            let type = sel.value;
            //得到参数
            let content = document.getElementById('details-'+type).value;


            //文件上传需要先转化为base字符串：这里进行更改
            if(type==='image'){
                //content 是文件路径
                let reader = new FileReader();
                reader.onload=function(e){
                    //加载完毕后进行数据传递，！！这里注意，必须要在这个函数内才有数据，这个函数表示图片数据转换成功
                    content = reader.result;  //或者 e.target.result都是一样的，都是base64码
                    console.log(content)
                    $.ajax({
                        url:"./UpdateInfoServlet",
                        data:{
                            type:type,
                            content:content,
                            fid:foreign_id
                        },
                        type:"POST",
                        dataType:"json",
                        success:function (data) {
                            if(data.status===200){
                                //更新成功后重新刷新列表
                                clearInfoTable();
                                clearSearchTable();
                                //需要通过建立html插入数据表单
                                var geojson = data.data.geojson;
                                //实例化整个geojson
                                let feature = JSON.parse(geojson).features[0];
                                showInfoForm(feature);
                                alert('信息修改成功');

                            }
                            else {
                                alert(data.data.error);
                            }
                        }
                    })
                    return;
                };
                reader.readAsDataURL(document.getElementById('details-'+type).files[0]);

            }

            else {
                //这里发送请求！！
                $.ajax({
                    url:"./UpdateInfoServlet",
                    data:{
                        type:type,
                        content:content,
                        fid:foreign_id
                    },
                    type:"POST",
                    dataType:"json",
                    success:function (data) {
                        if(data.status===200){
                            //更新成功后重新刷新列表
                            clearInfoTable();
                            clearSearchTable();
                            //需要通过建立html插入数据表单
                            var geojson = data.data.geojson;
                            //实例化整个geojson
                            let feature = JSON.parse(geojson).features[0];
                            showInfoForm(feature);
                            alert('信息修改成功');

                        }
                        else {
                            alert(data.data.error);
                        }
                    }
                })
            }



        }
        function getImageHtml(image) {
            if(image==null){
                return `<p><label>城市图片: </label>
                <input type="file" name="city_image" id="details-image" accept="image/png,image/gif,image/jpeg" ></p>`;
            }
            else {
                //object-fit 设置图片等比例缩放
                return `<p><label>城市图片: </label><img type="image" name="city_image" src="`+
                image+`" style="height: 130px;width: 130px;margin-left: 20px;object-fit:contain"></p>
                <p><label>重新上传: </label><input type="file" name="city_image" id="details-image" accept="image/png,image/gif,image/jpeg"></p>`;
            }
        }
    //---------------------------------------  注册登录，权限部分 ----------------------------------------------------------//



        let register_cpn = Vue.extend({
            template:"#register_cpn",
            data(){
                /***
                 * 验证函数
                 */
                let validateUsername = (rule, value, callback) => {
                    if (value === '') {
                        console.log("1.用户名");
                        callback(new Error('请输入用户名'));
                    } else {
                        //这里没有下面的那一环，就是因为这里不需要进行双重验证
                        callback();
                    }
                };
                let validatePass = (rule, value, callback) => {
                    if (value === '') {
                        callback(new Error('请输入密码'));
                    } else {
                        console.log("2.密码");
                        if (this.ruleForm.checkPass !== '') {
                            //这里为什么要验证，是要进行双重验证所以才有的    这句代码的含义，实际上就是 调用ruleform，里面的验证函数，对checkpass进行验证
                            this.$refs.ruleForm.validateField('checkPass');
                        }
                        callback();
                    }
                };
                let validatePass2 = (rule, value, callback) => {
                    if (value === '') {
                        callback(new Error('请再次输入密码'));
                    } else if (value !== this.ruleForm.password) {
                        callback(new Error('两次输入密码不一致!'));
                    } else {
                        callback();
                    }
                };
                return{
                    ruleForm:{
                        password:"",
                        checkPass:"",
                        username:"",
                    },
                    rules: {
                        password: [
                            { validator: validatePass, trigger: 'blur' }
                        ],
                        checkPass: [
                            { validator: validatePass2, trigger: 'blur' }
                        ],
                        username:[
                            { validator: validateUsername, trigger:'blur'}
                        ]
                    }
                }
            },
            methods: {
                //验证信息
                submitForm(formName) {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.register();
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                    });
                    console.log("信息提交")
                },
                resetForm(formName) {
                    this.$refs[formName].resetFields()
                },
                register(){
                    //登录函数，收集信息发送ajax请求
                    $.ajax({
                        url:"./RegisterServlet",
                        data:{
                            username:this.ruleForm.username,
                            password:this.ruleForm.password
                        },
                        type:"POST",
                        dataType:"json",
                        success:this.register_success_event
                    })
                },
                showError(){
                    this.$message({
                        showClose: true,
                        message: '用户名重复，注册失败',
                        type: 'error'
                    })
                },
                showSuccess(){
                    this.$message({
                        showClose: true,
                        message: '用户注册成功',
                        type: 'success'
                    })
                },
                register_success_event(data){
                    //注册事件
                    if(data.status==200){
                        //注册成功
                        this.showSuccess();
                        this.$emit("register_success",data.data)
                    }else {
                        this.showError();
                        this.resetForm("ruleForm");
                    }
                }

            }
        })

        let login_cpn = Vue.extend({
            template:"#login_cpn",
            data(){
                /***
                 * 验证函数
                 */
                let validateUsername = (rule, value, callback) => {
                    if (value === '') {
                        console.log("1.用户名");
                        callback(new Error('请输入用户名'));
                    } else {
                        //这里没有下面的那一环，就是因为这里不需要进行双重验证
                        callback();
                    }
                };
                let validatePass = (rule, value, callback) => {
                    if (value === '') {
                        callback(new Error('请输入密码'));
                    } else {
                        console.log("2.密码");
                        if (this.ruleForm.checkPass !== '') {
                            //这里为什么要验证，是要进行双重验证所以才有的    这句代码的含义，实际上就是 调用ruleform，里面的验证函数，对checkpass进行验证
                            this.$refs.ruleForm.validateField('checkPass');
                        }
                        callback();
                    }
                };
                return{
                    ruleForm:{
                        password:"",
                        username:"",
                    },
                    rules: {
                        password: [
                            { validator: validatePass, trigger: 'blur' }
                        ],
                        username:[
                            { validator: validateUsername, trigger:'blur'}
                        ]
                    }
                }
            },
            methods: {
                //验证信息
                submitForm(formName) {
                    this.$refs[formName].validate((valid) => {
                        if (valid) {
                            this.login(this.showError,this.showSuccess);
                        } else {
                            console.log('error submit!!');
                            return false;
                        }
                    });
                    console.log("信息提交")
                },
                resetForm(formName) {
                    this.$refs[formName].resetFields()
                },
                showError(){
                    this.$message({
                        showClose: true,
                        message: '用户名或密码错误',
                        type: 'error'
                    })
                },
                showSuccess(){
                    this.$message({
                        showClose: true,
                        message: '用户登录成功',
                        type: 'success'
                    })
                },
                login_success_event(data){
                    if(data.status===200){
                        //登录成功 弹窗
                        this.showSuccess();
                        //1.进行图标展示，关闭登录注册框
                        console.log(data.data);
                        this.$emit("login_success",data.data);

                    }
                    else {
                        //登录失败 弹窗
                        this.showError()
                    }
                },
                login(){
                    //登录函数，收集信息发送ajax请求
                    $.ajax({
                        url:"./LoginServlet",
                        data:{
                            username:this.ruleForm.username,
                            password:this.ruleForm.password
                        },
                        type:"POST",
                        dataType:"json",
                        success:this.login_success_event
                    })
                }
            }

        })
        let log_tab_cpn = Vue.extend({
            template:"#log_tab",
            data(){
                return{
                    activeName:"login"
                }
            },
            methods:{
                handleClick(tab, event) {
                    console.log(tab, event);
                }
            }
        })

    //-------------------------------------------- 注册头像弹窗部分 ----------------------------------------------------//

        let login_dig = Vue.extend({
            template:"#login_dia",
            data() {
                return {
                    dialogVisible: this.visible         //弹窗可见性控制
                };
            },
            props:{
                //从父组件拿过来的数据，和父组件一同更新，
                visible:{
                    required:true
                }
            },
            methods: {
                handleClose(done) {
                    this.$confirm('确认关闭？')
                        .then(_ => {
                            done();
                        })
                        .catch(_ => {});
                },
                closeDig(){
                    this.dialogVisible = false;
                    this.$emit("dig_close")
                },
            }
        })

        //-------------------------------------------- 用户头像 登录状态控制 ----------------------------------------------------//

        let user_info_card = Vue.extend({
            template:"#user_info_card",
            data(){
                return{
                    login_message:"登录",
                }
            },
            props:{
              //从父组件拿过来的数据，和父组件一同更新，
                login:{
                    default:false,
                },
                username:String,
                logintime:String
            },
            methods:{
                getLoginImg(){
                    if(this.login){
                        return "./resource/image/login_true.png"
                    }
                    else {
                        return "./resource/image/login_false.png"
                    }
                },
                setLoginBtn(){
                    if(this.login){
                        return "登出"
                    }
                    else{
                        return "登录"
                    }
                },
                login_btn(){
                    console.log("响应点击事件");
                    //响应login的点击事件
                    if(this.login){
                        console.log("登出");
                        // 已经登录的情况下
                        //1. 取消登录状态
                        // this.login = false; 这个需要让父组件来做
                        //2.向服务器发生ajax请求，告知登出，将服务端的session移除
                        this.$emit("login_out_click");
                    }
                    else {
                        //登录，响应弹窗事件，登录弹窗
                        console.log("开启登录");
                        this.$emit("login_click",true);
                    }
                }
            }
        })
        //-------------------------------------------- Vue实例，放在最后 ----------------------------------------------------//

        let app = new Vue({
            el: '#app',
            data:{
                logDiaVisible:false,
                login:false,
                username:"",
                LoginTime:""
            },
            components:{
                login_tab_cpn:log_tab_cpn,
                register_cpn:register_cpn,
                login_cpn:login_cpn,
                user_info_card:user_info_card,
                login_dig:login_dig
                },
            methods:{
                show_login_dialog(login_info){
                    this.logDiaVisible = login_info;
                    //打开对话框
                    this.$refs.login_dialog.dialogVisible=true;
                },
                close_login_dialog(){
                    console.log("关闭对话框");
                    this.logDiaVisible = false;
                },
                re_log_success(user){
                    //处理用户登录成功事件
                    console.log("VUE接收到用户成功登录信息");
                    //处理用户成功登录，
                    //关闭弹窗
                    this.dialogVisible = false;
                    this.$refs.login_dialog.dialogVisible = false;
                    this.login = true;
                    this.username =user.username;
                    this.LoginTime = user.LoginTime;
                    console.log(this.LoginTime);
                },
                re_log_out(){
                    //处理用户登录成功事件
                    console.log("VUE接收到用户成功登出信息");
                    //处理用户成功登录，
                    //关闭弹窗
                    this.login = false;
                    this.username ="";
                    this.LoginTime = "";
                }
            }
        })

    </script>

</body>
</html>
