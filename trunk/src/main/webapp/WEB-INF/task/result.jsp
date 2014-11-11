<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>结果</title>
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
        function topage(page){
            $("#page").val(page);
            reSubmit();
        }
    </script>
</head>
<body>
<form id="productForm" action="<%=request.getContextPath()%>/task/showResult?taskId=${taskId}" method="post">
    <input type="hidden" name="sort" id="sort" />
    <%--<input type="hidden" name="taskId" value="${bean.taskId}"/>--%>
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td colspan="4" ><font color="#FFFFFF">查询产品：</font></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="25%"> 关键字  ：
                <select name = "taskExecuteId" onchange="javascript:reSubmit(this.form);">
                    <c:forEach items="${taskExecute}" var="te">
                        <option value="${te.id}">${te.keyword}</option>
                    </c:forEach>
                </select>
            </td>
            <td width="25%"> 店铺名称  ：<input type="text" name="storeName" size="30" maxlength="30" value="${bean.storeName}"/></td>
            <td width="25%"> 产品类型  ：<input type="text" name="type" size="30" value="${bean.type}"/></td>
            <td width="25%"> 产品种类  ：<input type="text" name="category" size="30" value="${bean.category}"/></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="25%" colspan="2"> 价格(元) ：在<input type="text" name="minPrice" size="10" value="${bean.minPrice}" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>
                与<input type="text" name="maxPrice" size="10" maxlength="10" value="${bean.maxPrice}" onkeypress="javascript:InputLongNumberCheck()"/>之间</td>
            <td width="25%"> 消费有效期 ：
                <select name="validWeeks" onchange="javascript:reSubmit();">
                    <c:forEach  items="${validsMap}" var="valids">
                        <option value="${valids.key}" <c:if test="${valids.key==bean.validWeeks }">selected</c:if> >${valids.value}
                        </option>
                    </c:forEach>
                </select>
            </td>
            <td width="25%"> <input type="submit" value=" 查询 " class="frm_btn"></td>
        </tr>
    </table>
</form>
<div class="sort">
    <div class="reorder_l">请选择排序方式：
        <c:if test="${'pricedesc'==bean.sort}"><strong><em>价格高到低</em></strong></c:if>
        <c:if test="${'pricedesc'!=bean.sort}">
            <%--<a title='价格高到低' href="<%=request.getContextPath()%>/task/showResult?sort=pricedesc&id=${taskId}&taskExecuteId=${taskExecuteId}&storeName=${bean.storeName}&category=${bean.category}&type=${bean.type}&minPrice=${bean.minPrice}&maxPrice=${bean.maxPrice}&validWeeks=${bean.validWeeks}">价格高到低</a>--%>
            <a title='价格高到低' href="javascript:orderResult('pricedesc');">价格高到低</a>
        </c:if>
        | <c:if test="${'priceasc'==bean.sort}"><strong><em>价格低到高</em></strong></c:if>
        <c:if test="${'priceasc'!=bean.sort}">
            <a title='价格低到高' href="javascript:orderResult('priceasc');">价格低到高</a>
        </c:if>
        | <c:if test="${empty param.sort}"><strong><em>默认</em></strong></c:if>
        <c:if test="${!empty param.sort}">
            <a title='默认' href="javascript:orderResult('');">默认</a>
        </c:if>
    </div>
</div>
<table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4">
        <td style="color: #FFFFFF;">产品名称</td>
        <%--<td style="color: #FFFFFF;">产品图片</td>--%>
        <td style="color: #FFFFFF;">产品链接</td>
        <td style="color: #FFFFFF;">店铺名称</td>
        <td style="color: #FFFFFF;">店铺链接</td>
        <td style="color: #FFFFFF;">产品类型</td>
        <td style="color: #FFFFFF;">产品种类</td>
        <td style="color: #FFFFFF;"><font color="#FF0000">价格</font></td>
        <%--<td style="color: #FFFFFF;"></td>--%>
        <%--<td style="color: #FFFFFF;">入园说明</td>--%>
        <td style="color: #FFFFFF;">消费时间</td>
        <td style="color: #FFFFFF;">来源平台</td>
        <td style="color: #FFFFFF;">更新时间</td>
        </td>
    </tr>
    <tbody>
    <!--LOOP START-->
    <c:forEach items="${pageView.records}" var="product">
        <tr bgcolor="f5f5f5" id="<c:out value='${product.id}'/>">
            <td title="<c:out value="${product.productName}"/>">
                <c:if test="${fn:length(product.productName)>20}">
                    <c:out value="${fn:substring(product.productName, 0,20)}..."/>
                </c:if>
                <c:if test="${fn:length(product.productName)<=20}">
                    <c:out value="${product.productName}"/>
                </c:if>
            </td>
            <td><a href="${product.productUrl}" target="_blank">产品链接</a></td>
            <td><c:out value="${product.storeName}"/></td>
            <td><a href="${product.storeUrl}" target="_blank">店铺链接</a></td>
            <td><c:out value="${product.category}"/></td>
            <td><c:out value="${product.type}"/></td>
            <td><font color="#FF0000"><c:out value="${product.price}"/></font></td>
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
            <td><c:out value="${platFormMap[product.platFormId].name}"/></td>
            <td><fmt:formatDate value="${product.timeStamp}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></td>
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
