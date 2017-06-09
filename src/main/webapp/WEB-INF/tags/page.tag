<%@ tag pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
	function setPageSize(number) {
		$("#pageSize").val(number);
		$('form').submit();
	}
</script>

<ul class="pager">
	<span class="page-list">每页显示 <span class="btn-group dropup">
			<button type="button"
				class="btn btn-default  btn-outline dropdown-toggle"
				data-toggle="dropdown" aria-expanded="false">
				<span class="page-size">${pageInfo.pageSize}</span> <span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu">
				<li class="${pageInfo.pageSize==1?'active':''}">
					<a href="javascript:setPageSize(1)">1</a>
				</li>
				<li class="${pageInfo.pageSize==5?'active':''}">
					<a href="javascript:setPageSize(5)">5</a>
				</li>
				<li class="${pageInfo.pageSize==10?'active':''}">
					<a href="javascript:setPageSize(10)">10</a>
				</li>
				<li class="${pageInfo.pageSize==15?'active':''}">
				<a href="javascript:setPageSize(15)">15</a>
				</li>
			</ul>
	</span>条记录


	<c:if test="${pageInfo.pageNum>1}">
		<li>
			<a href="#" aria-label="Previous" name="pages"
				value="${pageInfo.pageNum-1}"> <span aria-hidden="true">上一页</span>
			</a>
		</li>
	</c:if>
	<c:choose>
		<c:when test="${pageInfo.pageNum<=4}">
			<c:forEach begin="1" end="${pageInfo.pages<5?pageInfo.pages:4}"
				varStatus="i" var="number">
				<li class="${pageInfo.pageNum==i.index?'active':'' }">
					<a href="#" name="pages" value="${i.index}">${i.index}</a>
				</li>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<li>
				<a href="#" name="pages" value="1">1</a>
			</li>
			<li class="disabled">
				<a href="#">...</a>
			</li>
			<c:forEach begin="${pageInfo.pageNum-2}" end="${pageInfo.pageNum+2}"
				varStatus="i" var="number">
				<c:if test="${number<=pageInfo.pages }">
					<li class="${pageInfo.pageNum==number?'active':'' }">
						<a href="#" name="pages" value="${i.index}">${i.index}</a>
					</li>
				</c:if>
			</c:forEach>

			<c:if test="${pageInfo.pageNum+2<pageInfo.pages }">
				<li class="disabled">
					<a href="#">...</a>
				</li>
				<li>
					<a href="#" name="pages" value="${pageInfo.pages}">${pageInfo.pages}</a>
				</li>
			</c:if>
		</c:otherwise>
	</c:choose>

	<c:if test="${pageInfo.pages>1&&pageInfo.pageNum<pageInfo.pages}">
		<li>
			<a href="#" aria-label="Next" name="pages"
				value="${pageInfo.pageNum+1}"> <span aria-hidden="true">下一页</span>
			</a>
		</li>
	</c:if>
</ul>
