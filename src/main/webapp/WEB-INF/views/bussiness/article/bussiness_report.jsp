<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../../../common/taglibs.jsp" %>
<html zh-CN>
<head>
    <title>文章列表</title>
    <script src="https://cdn.bootcss.com/echarts/4.0.4/echarts.js"></script>
    <%@ include file="../../../common/header.jsp" %>
</head>
<body>

<div class="container-fluent">
    <ul class="nav nav-tabs">
        <shiro:hasPermission name="bussiness:article:view">
            <li class="active">
                <a href="#">报表</a>
            </li>
        </shiro:hasPermission>
    </ul>
    <div class="panel panel-default">
        <div class="panel-heading">文章报表</div>
        <div class="panel-body">
            <div id="main" style="width: 1200px;height:400px;"></div>
        </div>
    </div>
</div>
</body>
<script type="text/javascript">
    var xAxis=[], series1=[],series2=[],series3=[],series4=[],series5=[];
    <c:forEach items="${list}" var="list">
    xAxis.push('${list.dates}');
    series1.push( '${list.a}');
    series2.push( '${list.b}');
    series3.push( '${list.c}');
    series4.push( '${list.d}');
    series5.push( '${list.e}');
    </c:forEach>
    require(['jquery', 'bootstrap'], function ($) {
        require(['sys'], function () {
    })
        var myChart = echarts.init(document.getElementById('main'));
        // 指定图表的配置项和数据
        option = {
            title: {
                text: '文章报表'
            },
            tooltip : {
                trigger: 'axis',
                axisPointer: {
                    type: 'cross',
                    label: {
                        backgroundColor: '#6a7985'
                    }
                }
            },
            legend: {
                data:['待审核','审核中','已审核','已退稿','已删除']
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : xAxis
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'待审核',
                    type:'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    data:series1
                },
                {
                    name:'审核中',
                    type:'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    data:series2
                },
                {
                    name:'已审核',
                    type:'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    label: {
                        normal: {
                            show: true,
                            position: 'top'
                        }
                    },
                    data:series3
                },
                {
                    name:'已退稿',
                    type:'line',
                    stack: '总量',
                    label: {
                        normal: {
                            show: false
                        }
                    },
                    areaStyle: {normal: {}},
                    data:series4
                },
                {
                    name:'已删除',
                    type:'line',
                    stack: '总量',
                    areaStyle: {normal: {}},
                    data:series5
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    })
</script>

</html>