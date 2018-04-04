//如果引入页面 加入data-main属性，那么paths就不用写了，直接引用当前requier.js所在的路径/+引用名称
//直接访问jsp和跳转过来的路径
require.config({
     baseUrl: '/js/lib',
    paths: {
    	jquery:'jquery-2.1.4.min',
        bootstrap: 'bootstrap-3.3.5.min',
        //jquery valiate
         jqueryValidate:'plugins/validate/jquery_validate.min',
        jqueryValidateMessages:'plugins/validate/messages_zh.min',
        //step验证
        staps:'plugins/staps/jquery.steps.min',
        //遮罩层提醒
        bootbox:'plugins/bootbox/bootbox-4.4.0.min',
        //  metisMenu 是个Bootstrap 3 风格的 jQuery 菜单插件,http://mm.onokumus.com/ 
        metisMenus:'plugins/metisMenu/jquery.metisMenu',
        //jQuery slimScroll插件是一个支持把内容放在一个盒子里面，固定一个高度，超出的则使用滚动
        slimscroll:'plugins/slimscroll/jquery.slimscroll.min',
        jqueryui:'plugins/jquery-ui/jquery-ui.min',
        //分页tab
        contabs:'contabs.min',

         //Switchery 让复选框变成ios形式
        Switchery:'plugins/switchery/switchery',
        
        //sweetalert,让弹出框更好看的
        sweetalert:'plugins/sweetalert/sweetalert.min',
        
        // Chosen chosen-select 让选择框变的美丽的啊 很棒的插件
        Chosen:'plugins/chosen/chosen.jquery',
        // suggest 这是一个基于 bootstrap 按钮式下拉菜单组件的搜索建议插件，必须使用于按钮式下拉菜单组件上。
        suggest :'plugins/suggest/bootstrap-suggest.min',
        
        //pace 页面的加载进度百分比
        Pace:'plugins/pace/pace.min',
        //弹出框 闲心
        layer:'plugins/layer/layer',
         //jquery,通知插件
       toastr:'plugins/toastr/toastr.min',
       //tree树状图形式表格
       treetable:'plugins/treetable/jquery.treetable',
       //ztree
       jquerytree:'plugins/jquerytress/jquery.ztree.all-3.5',
        //文件上传处理
        webuploader:'plugins/webuploader/webuploader.min',
        //日期控件
        datetimepicker:'plugins/date/bootstrap-datetimepicker.min',
        datetimepickerzh:'plugins/date/locales/bootstrap-datetimepicker.zh-CN',
        /*自自定义*/
        base:'base',
        /*系统依赖*/
        sys:'sys',
        test:'test'
    },
    //依赖关系bootstrap依赖jquery
    shim : {  
            bootstrap : {  
                deps : ['jquery'],  
                exports :'bootstrap'  
            }  
        }  
    
});

//这个可以运行的 修改ypeof define && define.amd改成define.amd?
//("function"== typeof define.amd?define: function(e,t) {  
// ("function" == typeof define && define.amd ? define: function(e, t) {    




