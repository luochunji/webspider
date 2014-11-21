<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>任务查询结果</title>
    <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <link href="<%=request.getContextPath()%>/css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js" ></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/app.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/FoshanRen.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/My97DatePicker/WdatePicker.js"></script>
    <script>
        $(function () {
            $('.Wdate').live('focus', function() {
                WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'});
            });
        });
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
            var action = '<%=request.getContextPath()%>/product/${url}';
            $("#productForm").attr("action",action);
            reSubmit();
        }
        function exportExcel(){
            <%--window.open('<%=request.getContextPath()%>/product/exportExcel?clazz=${bean.clazz}&ids=${ids})--%>
            var action = '<%=request.getContextPath()%>/product/exportExcel';
            $("#productForm").attr("action",action);
            reSubmit();
        }
    </script>
</head>
<body>
<form id="productForm" action="<%=request.getContextPath()%>/product/showResult" method="post">
    <input type="hidden" name="url" id="requestUrl" value="${url}"/>
    <input type="hidden" name="clazz" value="${bean.clazz}"/>
    <input type="hidden" name="sort" id="sort" />
    <input type="hidden" name="filterStore" id="filterStore" value="${bean.filterStore}"/>
    <input type="hidden" name="platFormId" id="platFormId" value="${bean.platFormId}"/>
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <section class="content-header">
        <h1>
            <small></small>
        </h1>
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> 网警首页</a></li>
        </ol>
    </section>
    <section class="content">
        <!--nav tabs-->
        <ul class="nav nav-tabs">
            <c:if test="${'ourStore'==bean.filterStore}">
                <li class="active"><a href="#">我司分销商网店列表</a></li>
            </c:if>
            <c:if test="${'ourStore'!=bean.filterStore}">
                <li><a href="javascript:filterResult('filterStore','ourStore');">我司分销商网店列表</a></li>
            </c:if>
            <c:if test="${'otherStore'==bean.filterStore}">
                <li class="active"><a href="#">非司分销商网店列表</a></li>
            </c:if>
            <c:if test="${'otherStore'!=bean.filterStore}">
                <li><a href="javascript:filterResult('filterStore','otherStore');">非司分销商网店列表</a></li>
            </c:if>
        </ul>
        <div class="tab-content">
            <div class="row">
                <div class="keybox">
                    <span>平台来源：</span>
                    <c:forEach items="${platFormMap}" var="pf">
                        <c:if test="${pf.key == bean.platFormId}"><a href="#" class="active">淘宝网</a></em></strong></c:if>
                        <c:if test="${pf.key != bean.platFormId}">
                            <a title='${pf.value.name}' href="javascript:filterResult('platFormId',${pf.key});">${pf.value.name}</a>
                        </c:if>
                    </c:forEach>
                    <c:if test="${empty bean.platFormId}"><a href="#" class="active">不限</a></c:if>
                    <c:if test="${!empty bean.platFormId}">
                        <a title='默认' href="javascript:filterResult('platFormId','');">不限</a>
                    </c:if>
                </div>
                <div class="searchbox">
                    <div class="input-group">
                        <input type="text" name="keyword" value="${bean.keyword}" class="form-control " placeholder="请输入关键字" id="activityKeyw">
                                        <span class="input-group-btn">
                                            <button class="btn btn-primary" type="button" id="btnSActivity" role="button"
                                                    aria-disabled="false" onclick="javascript:topage(1);"><span class="ui-button-text">搜索</span></button>
                                        </span>
                        <button class="btn btn-primary" onclick="javascript:exportExcel();">导出</button>
                    </div>
                </div>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th><input type="checkbox" id="chk_all"/>全选</th>
                    <th>序号</th>
                    <th>景区名称</th>
                    <th>门票类型</th>
                    <th>门票种类</th>
                    <th>网络售价</th>
                    <th>最低限价</th>
                    <th>来源平台</th>
                    <th>产品地址</th>
                    <th>网店名称</th>
                    <th>更新时间</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pageView.records}" var="dto" varStatus="index">
                    <tr>
                        <td><input type="checkbox" name="ids" value="${dto.id}"></td>
                        <td><c:out value="${index.count}"/></td>
                        <td><c:out value="${dto.scenicName}"/></td>
                        <td><c:out value="${dto.type}"/></td>
                        <td><c:out value="${dto.category}"/></td>
                        <td><font color="#FF0000"><c:out value="${dto.sellPrice}"/></font></td>
                        <td><font color="#FF0000"><c:out value="${dto.lowPrice}"/></font></td>
                        <td><c:out value="${dto.platForm}"/></td>
                        <td><a href="${dto.productUrl}" target="_blank">产品地址</a></td>
                        <td><c:out value="${dto.storeName}"/></td>
                        <td><fmt:formatDate value="${dto.datetime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav>
                <ul class="pagination">
                    <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                        <c:if test="${pageView.currentpage==wp}"><li class="active"><a href="#">${wp}</a></li></c:if>
                        <c:if test="${pageView.currentpage!=wp}"><li><a href="javaScript:topage(${wp});">${wp}</a></li></c:if>
                    </c:forEach>
                </ul>
            </nav>
        </div>
    </section>





<%--
    <input type="hidden" name="url" id="requestUrl" value="${url}"/>
    <input type="hidden" name="clazz" value="${bean.clazz}"/>
    <input type="hidden" name="sort" id="sort" />
    <input type="hidden" name="filterStore" id="filterStore" value="${bean.filterStore}"/>
    <input type="hidden" name="platFormId" id="platFormId" value="${bean.platFormId}"/>
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr>
            <td colspan="3">
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
            </td>
        </tr>
        <tr>
            <td colspan="3">
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
            </td>
        </tr>
        <tr>
            <td>
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
            </td>
        </tr>
        <tr>
            <td>
                <c:if test="${url =='showTempResult' || url =='showHistory'}">
                    <div style="float: right;margin-right: 20px;">
                        日期范围：
                        <input type="text" name="startDate" size="15" class="Wdate" readonly value="${bean.startDate}"/>
                        -
                        <input type="text" name="endDate"  size="15" class="Wdate" readonly value="${bean.endDate}"/>
                    </div>
                </c:if>
            </td>
            <td>
                <div style="float: right;margin-right: 20px;">
                    关键字：<input type="text" name="keyword" maxlength="20" size="20" value="${bean.keyword}"/>
                    <input type="button" value="搜索" onclick="javascript:topage(1);"/>
                    <input type="button" value="导出" onclick="javascript:exportExcel();"/>
                </div>
            </td>
        </tr>
    </table>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td style="color: #FFFFFF;"><input type="checkbox" id="chk_all"/>全选</td>
            <td style="color: #FFFFFF;">序号</td>
            <td style="color: #FFFFFF;">景区名称</td>
            <td style="color: #FFFFFF;">门票类型</td>
            <td style="color: #FFFFFF;">门票种类</td>
            <td style="color: #FFFFFF;">网络售价</td>
            <td style="color: #FFFFFF;">最低限价</td>
            <td style="color: #FFFFFF;">来源平台</td>
            <td style="color: #FFFFFF;">产品地址</td>
            <td style="color: #FFFFFF;">网店名称</td>
            <td style="color: #FFFFFF;">更新时间</td>
            </td>
        </tr>
        <tbody>
        <!--LOOP START-->
        <c:forEach items="${pageView.records}" var="dto" varStatus="index">
            <tr bgcolor="f5f5f5">
                <td><input type="checkbox" name="ids" value="${dto.id}"></td>
                <td><c:out value="${index.count}"/></td>
                <td><c:out value="${dto.scenicName}"/></td>
                <td><c:out value="${dto.type}"/></td>
                <td><c:out value="${dto.category}"/></td>
                <td><font color="#FF0000"><c:out value="${dto.sellPrice}"/></font></td>
                <td><font color="#FF0000"><c:out value="${dto.lowPrice}"/></font></td>
                <td><c:out value="${dto.platForm}"/></td>
                <td><a href="${dto.productUrl}" target="_blank">产品地址</a></td>
                <td><c:out value="${dto.storeName}"/></td>
                <td><fmt:formatDate value="${dto.datetime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></td>
            </tr>
        </c:forEach>
        <!--LOOP END-->
        </tbody>
        <tr>
            <td colspan="4">
                <div style="float: left;">
                    <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                        <c:if test="${pageView.currentpage==wp}"><div class='red' style="float:left">${wp}</div></c:if>
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
    </table>--%>
</form>
</body>
</html>
