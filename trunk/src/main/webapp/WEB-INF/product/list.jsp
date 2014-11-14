<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>查询结果</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/FoshanRen.js"></script>
    <script>
        function reSubmit(){
            $("#productForm").submit();

        }
        function orderResult(order){
            $("#sort").val(order);
            reSubmit();

        }
        function filterResult(filter,value){
            $("#"+filter).val(value);
            $("#page").val(1);
            reSubmit();
        }
        function topage(page){
            $("#page").val(page);
            reSubmit();
        }
    </script>
</head>
<body>
<form id="productForm" action="<%=request.getContextPath()%>/product/list" method="post">
    <input type="hidden" name="sort" id="sort" />
    <input type="hidden" name="filterStore" id="filterStore" value="${bean.filterStore}"/>
    <input type="hidden" name="platFormId" id="platFormId" value="${bean.platFormId}"/>
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
</form>
<div class="filter">
    <div class="reorder_l">请选择过滤方式：
        <c:if test="${'ourStore'==bean.filterStore}"><strong><em>我公司分销商</em></strong></c:if>
        <c:if test="${'ourStore'!=bean.filterStore}">
            <a title='我公司分销商' href="javascript:filterResult('filterStore','ourStore');">我公司分销商</a>
        </c:if>
        | <c:if test="${'otherStore'==bean.filterStore}"><strong><em>非我公司分销商</em></strong></c:if>
        <c:if test="${'otherStore'!=bean.filterStore}">
            <a title='非我公司分销商' href="javascript:filterResult('filterStore','otherStore');">非我公司分销商</a>
        </c:if>
        | <c:if test="${empty bean.filterStore}"><strong><em>所有结果</em></strong></c:if>
        <c:if test="${!empty bean.filterStore}">
            <a title='所有结果' href="javascript:filterResult('filterStore','');">所有结果</a>
        </c:if>
    </div>
</div>
<div class="sort">
    <div class="reorder_l">请选择排序方式：
        <c:if test="${'pricedesc'==bean.sort}"><strong><em>价格高到低</em></strong></c:if>
        <c:if test="${'pricedesc'!=bean.sort}">
            <a title='价格高到低' href="javascript:orderResult('pricedesc');">价格高到低</a>
        </c:if>
        | <c:if test="${'priceasc'==bean.sort}"><strong><em>价格低到高</em></strong></c:if>
        <c:if test="${'priceasc'!=bean.sort}">
            <a title='价格低到高' href="javascript:orderResult('priceasc');">价格低到高</a>
        </c:if>
        | <c:if test="${empty bean.sort}"><strong><em>默认</em></strong></c:if>
        <c:if test="${!empty bean.sort}">
            <a title='默认' href="javascript:orderResult('');">默认</a>
        </c:if>
    </div>
</div>
<div class="platForm">
    <div class="reorder_l">平台来源：
        <c:forEach items="${platFormMap}" var="pf">
            <c:if test="${pf.key == bean.platFormId}"><strong><em>${pf.value.name}</em></strong></c:if>
            <c:if test="${pf.key != bean.platFormId}">
                <a title='${pf.value.name}' href="javascript:filterResult('platFormId',${pf.key});">${pf.value.name}</a>
            </c:if>
        </c:forEach>
        | <c:if test="${empty bean.platFormId}"><strong><em>所有</em></strong></c:if>
        <c:if test="${!empty bean.platFormId}">
            <a title='默认' href="javascript:filterResult('platFormId','');">所有</a>
        </c:if>
    </div>
</div>
<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4">
        <td style="color: #FFFFFF;">序号</td>
        <td style="color: #FFFFFF;">景区名称</td>
        <%--<td style="color: #FFFFFF;">产品名称</td>--%>
        <%--<td style="color: #FFFFFF;">产品图片</td>--%>
        <td style="color: #FFFFFF;">门票类型</td>
        <td style="color: #FFFFFF;">门票种类</td>
        <td style="color: #FFFFFF;"><font color="#FF0000">网络售价</font></td>
        <td style="color: #FFFFFF;"><font color="#FF0000">最低限价</font></td>
        <td style="color: #FFFFFF;">来源平台</td>
        <td style="color: #FFFFFF;">产品地址</td>
        <td style="color: #FFFFFF;">网店名称</td>
       <%-- <td style="color: #FFFFFF;">店铺链接</td>
        <td style="color: #FFFFFF;">产品类型</td>
        <td style="color: #FFFFFF;">产品种类</td>
        <td style="color: #FFFFFF;">消费时间</td>
        <td style="color: #FFFFFF;">更新时间</td>--%>
        </td>
    </tr>
    <tbody>
    <!--LOOP START-->
    <c:forEach items="${pageView.records}" var="product" varStatus="index">
        <tr bgcolor="f5f5f5" id="<c:out value='${product.id}'/>">
            <td><c:out value="${index.count}"/></td>
            <td><c:out value="${scenicMap[product.scenicId]}"/></td>
            <%--<td title="<c:out value="${product.productName}"/>">--%>
                <%--<c:if test="${fn:length(product.productName)>20}">--%>
                    <%--<c:out value="${fn:substring(product.productName, 0,20)}..."/>--%>
                <%--</c:if>--%>
                <%--<c:if test="${fn:length(product.productName)<=20}">--%>
                    <%--<c:out value="${product.productName}"/>--%>
                <%--</c:if>--%>
            <%--</td>--%>
            <td><c:out value="${product.type}"/></td>
            <td><c:out value="${product.category}"/></td>
            <td><font color="#FF0000"><c:out value="${product.price}"/></font></td>
            <td><font color="#FF0000"><c:out value="${product.low_price}"/></font></td>
            <td><c:out value="${platFormMap[product.platFormId].name}"/></td>
            <td><a href="${product.productUrl}" target="_blank">产品地址</a></td>
            <td><c:out value="${product.storeName}"/></td>
            <%--<td><a href="${product.storeUrl}" target="_blank">店铺链接</a></td>
            <td><c:out value="${product.category}"/></td>
            <td><c:out value="${product.type}"/></td>
            <td>
                <c:if test="${product.validWeeks == 'NORMAL'}">
                    平日票
                </c:if>
                <c:if test="${product.validWeeks == 'WEEKEND'}">
                    周末票
                </c:if>
                <c:if test="${product.validWeeks == 'OTHER' || product.validWeeks == null}">
                    其他
                </c:if>
            </td>
            <td><fmt:formatDate value="${product.timeStamp}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></td>--%>
        </tr>
    </c:forEach>
    <!--LOOP END-->
    </tbody>
    <tr>
        <td colspan="4">
            <div style="float: left;">
                <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                    <c:if test="${pageView.currentpage==wp}"><div class='red' style="float:left">${wp}</div></c:if>
                    <%--<c:if test="${pageView.currentpage!=wp}"><div class="page" style="float:left"><a href="<%=request.getContextPath()%>/task/showResult?sort=${bean.sort}&id=${taskId}&page=${wp}&taskExecuteId=${taskExecuteId}&storeName=${bean.storeName}&category=${bean.category}&type=${bean.type}&minPrice=${bean.minPrice}&maxPrice=${bean.maxPrice}&validWeeks=${bean.validWeeks}">[${wp}]</a></div></c:if>--%>
                    <c:if test="${pageView.currentpage!=wp}"><div class="page" style="float:left"><a href="javaScript:topage(${wp});">[${wp}]</a></div></c:if>
                </c:forEach>
                <div style="float:left;">&nbsp;&nbsp;</div>跳转到第
                <select name="selectPage" class="kuang" onChange="javaScript:topage(this.value)">
                    <c:forEach begin="1" end="${pageView.totalpage}" var="wp">
                        <option value="${wp}" <c:if test="${pageView.currentpage==wp}">selected</c:if>> ${wp} </option></c:forEach>
                </select>页
            </div>
        </td>
        <td colspan="7">
            <%@ include file="/WEB-INF/common/fenye.jsp" %>
        </td>
    </tr>
</table>
</body>
</html>
