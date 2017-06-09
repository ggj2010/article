<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="../common/taglibs.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true"
	description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true"
	description="隐藏域名称（ID）"%>
<%@ attribute name="url" type="java.lang.String" required="true"
	description="树结构数据地址"%>
<%@ attribute name="value" type="java.lang.String" required="true"
	description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true"
	description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true"
	description="输入框值（Name）"%>
<%@ attribute name="cssClass" type="java.lang.String" required="true"
	description="样式名称"%>
<%@ attribute name="allowClear" type="java.lang.Boolean"
	required="false" description="是否允许清除"%>
<%@ attribute name="allowInput" type="java.lang.Boolean"
	required="false" description="文本框可填写"%>
<%@ attribute name="title" type="java.lang.String" required="true"
	description="选择框标题"%>
<%@ attribute name="extId" type="java.lang.Integer" required="false"
	description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false"
	description="是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true"%>
<input type="hidden" value="${value}" name="${name}" id="${id}Id" />
<input type="text" class="${cssClass}" name="${labelName}"
	id="${id}Name" value="${labelValue}"
	${allowInput?'':'readonly="readonly"'} />
<div class="input-group-btn">
	<buttion id="${id}Search"
		class="btn btn-default glyphicon glyphicon-search" type="button"
		onclick="${id}Search()"></buttion>
</div>
<script type="text/javascript">
	function ${id}Search() {
		  layer.open({
		    type: 2,
		    btn: ['确定','关闭'],
		    yes: function(index, layero){ //或者使用btn1
		    	setValue(layero);
		    },
		    title: '${title}',
		    shadeClose: true,
		    shade: false,
		    maxmin: true, //开启最大化最小化按钮
		    area: ['300px', '420px'],
		    content: '${path}/tag/treeselect?url=${url}&extId=${extId}&selectIds=${value}&checked=${checked}'
		})
	}	

	function setValue(layero){
	 //父类获取iframe里面的ztree对象
		var tree =window[layero.find('iframe')[0]['name']].$.fn.zTree.getZTreeObj("treeId")
	  	var ids = [], names = [], nodes = [];
		if(${checked}){
			nodes=tree.getCheckedNodes()
			for(var i=0; i<nodes.length; i++) {
				ids.push(nodes[i].id);
				names.push(nodes[i].name);
			}
			  $("#${id}Id").val(ids.join(","));
			$("#${id}Name").val(names.join(","));
			
		}else{
			 nodes = tree.getSelectedNodes();
			  $("#${id}Id").val(nodes[0].id);
			$("#${id}Name").val(nodes[0].name);
		}
			layer.closeAll();
	  }
</script>