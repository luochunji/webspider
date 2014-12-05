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
    <link href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js" ></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/app.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/common.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/js/plugins/daterangepicker/bootstrap-datetimepicker.min.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/js/plugins/daterangepicker/bootstrap-datetimepicker.zh-CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/plugins/layer/layer.min.js"></script>
    <script>
        $(document).ready(function () {
            $("input[name$='Date']").datetimepicker({
                format: 'yyyy-mm-dd',
                language: 'zh-CN',
                minView:2,
                todayBtn:  1,
                autoclose: 1

            });
            if(${0==taskCount}){
                $.layer({
                    shade: [0],
                    area: ['auto','auto'],
                    dialog: {
                        msg: '您还未设置景区任务列表，请先设置！',
                        btns: 2,
                        type: 9,
                        btn: ['设置','取消'],
                        yes: function(){
                            window.location.href="<%=request.getContextPath()%>/task/list/";
                        }, no: function(){
                            return false
                        }
                    }
                });
            }
        });
        function reSubmit(){
            var url = $("#url").val();
            var action = '<%=request.getContextPath()%>/product/'+url;
            $("#productForm").attr("action",action);
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
            var ids = '';
            var arrChk=$("input[name='ids'][checked]");
            $(arrChk).each(function(){
                ids +=this.value+',';
            });
            $("#ids").val(ids);
            $("#clazz").val($("input[name='clazz']").val());
            $("#keyword").val($("#activityKeyw").val());
            $("#filterStore2").val($("input[name='filterStore']").val());
            $("#exportExcel").submit();
        }
    </script>
</head>
<body>
<form id="productForm" action="<%=request.getContextPath()%>/product/showResult" method="post">
    <input type="hidden" name="url" id="url" value="${bean.url}"/>
    <input type="hidden" name="clazz" value="${bean.clazz}"/>
    <input type="hidden" name="sort" id="sort" />
    <input type="hidden" name="filterStore" id="filterStore" value="${bean.filterStore}"/>
    <input type="hidden" name="platFormId" id="platFormId" value="${bean.platFormId}"/>
    <input type="hidden" name="page" id="page" value="${bean.page}"/>
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
                    <c:if test="${empty bean.platFormId}"><a href="#" class="active">不限</a></c:if>
                    <c:if test="${!empty bean.platFormId}">
                        <a title='默认' href="javascript:filterResult('platFormId','');">不限</a>
                    </c:if>
                    <c:forEach items="${platFormMap}" var="pf">
                        <c:if test="${pf.key == bean.platFormId}"><a href="#" class="active">${pf.value.name}</a></em></strong></c:if>
                        <c:if test="${pf.key != bean.platFormId}">
                            <a title='${pf.value.name}' href="javascript:filterResult('platFormId',${pf.key});">${pf.value.name}</a>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="row">
                <div class="keybox">
                    <span>排&nbsp;&nbsp;&nbsp;&nbsp;序：</span>
                    <c:if test="${empty bean.sort}"><a href="#" class="active" disabled>不限</a></c:if>
                    <c:if test="${!empty bean.sort}">
                        <a href="javascript:orderResult('');">不限</a>
                    </c:if>
                    <c:if test="${'pricedesc'==bean.sort}"><a href="#" class="active" disabled>价格高到低</a></c:if>
                    <c:if test="${'pricedesc'!=bean.sort}">
                        <a href="javascript:orderResult('pricedesc');">价格高到低</a>
                    </c:if>
                    <c:if test="${'priceasc'==bean.sort}"><a href="#" class="active" disabled>价格低到高</a></c:if>
                    <c:if test="${'priceasc'!=bean.sort}">
                        <a href="javascript:orderResult('priceasc');">价格低到高</a>
                    </c:if>
                </div>
            </div>
            <div class="row">
                    <div class="col-xs-12 searchbox">
                        <span>关键字:</span>
                        <input type="text" class="form-control keyinput" placeholder="请输入关键字" name="keyword" value="${bean.keyword}" id="activityKeyw">
                        <span>价格：</span>
                        <input type="text" class="form-control" name="minPrice" value="${bean.minPrice}"/>
                        <span>-</span>
                        <input type="text" class="form-control" name="maxPrice" value="${bean.maxPrice}"/>
                        <c:if test="${bean.url != 'showResult'}">
                                <span>日期区间：</span>
                                <input type="text" class="form-control timeinput" name="startDate" value="${bean.startDate}" readonly/>
                                <span>-</span>
                                <input type="text" class="form-control timeinput" name="endDate" value="${bean.endDate}" readonly/>
                        </c:if>
                        <button class="btn btn-primary" onclick="javascript:topage(1);">搜索</button>
                        <input type="button" class="btn btn-default" onclick="javascript:exportExcel();" value="导出">
                    </div>
            </div>
            <c:if test="${pageView.records== null || fn:length(pageView.records) == 0}">
                没有违规信息记录
            </c:if>
            <c:if test="${pageView.records== null || fn:length(pageView.records) != 0}">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th nowrap><input type="checkbox" id="chk_all"/>全选</th>
                        <th nowrap>序号</th>
                        <c:if test="${'showHistory'==bean.url}">
                            <th nowrap>任务属性</th>
                        </c:if>
                        <th nowrap>景区名称</th>
                        <th nowrap>门票类型</th>
                        <th nowrap>门票种类</th>
                        <th nowrap>网络售价</th>
                        <th nowrap>最低限价</th>
                        <th nowrap>来源平台</th>
                        <th nowrap>产品地址</th>
                        <th nowrap>网店名称</th>
                        <th nowrap>更新时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${pageView.records}" var="dto" varStatus="index">
                        <tr>
                            <td nowrap><input type="checkbox" name="ids" value="${dto.id}"></td>
                            <td nowrap><c:out value="${index.count}"/></td>
                            <c:if test="${'showHistory'==bean.url}">
                                <td nowrap><c:out value="${dto.taskType}"/></td>
                            </c:if>
                            <td nowrap><c:out value="${dto.scenicName}"/></td>
                            <td nowrap><c:out value="${dto.type}"/></td>
                            <td nowrap><c:out value="${dto.category}"/></td>
                            <td nowrap><font color="#FF0000"><c:out value="${dto.sellPrice}"/></font></td>
                            <td nowrap><font color="#FF0000"><c:out value="${dto.lowPrice}"/></font></td>
                            <td nowrap><c:out value="${dto.platForm}"/></td>
                            <td nowrap><a href="${dto.productUrl}" target="_blank">产品地址</a></td>
                            <td nowrap><c:out value="${dto.storeName}"/></td>
                            <td nowrap><fmt:formatDate value="${dto.datetime}" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="long" /></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
            <nav class="clearfix">
                <ul class="pagination">
                    <c:forEach begin="${pageView.pageindex.startindex}" end="${pageView.pageindex.endindex}" var="wp">
                        <c:if test="${pageView.currentpage==wp}"><li class="active"><a href="#">${wp}</a></li></c:if>
                        <c:if test="${pageView.currentpage!=wp}"><li><a href="javaScript:topage(${wp});">${wp}</a></li></c:if>
                    </c:forEach>
                </ul>
                <%@ include file="/WEB-INF/common/fenye.jsp" %>
            </nav>
        </div>
    </section>
</form>
<div style="display: none">
    <form id="exportExcel" action="<%=request.getContextPath()%>/product/exportExcel" method="post" target="_blank">
        <input type="hidden" name="ids" id="ids">
        <input type="hidden" name="clazz" id="clazz">
        <input type="hidden" name="keyword" id="keyword">
        <input type="hidden" name="filterStore" id="filterStore2">
    </form>
</div>
</body>
</html>
