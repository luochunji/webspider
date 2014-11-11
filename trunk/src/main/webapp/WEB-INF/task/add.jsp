<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>添加定时任务</title>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/calendar4.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/taskCommon.js"></script>
    <script type="text/javascript" language="JavaScript">
        function ActionEvent(operate,id) {
            var url = "<%=request.getContextPath()%>/task/"+operate+"?id="+id;
            if('detail' == operate){
                window.open(url);
            }else{
                $.ajax( {
                    type : "POST",
                    url : url,
                    dataType: "json",
                    success : function(data) {
                        alert(data);
                    },
                    error :function(){
                        alert("网络连接出错！");
                    }
                });
            }
        }
        function sureSubmit(objForm){
            var jsonArray = [];
            var quartzArray = []
            $(".content").each(function(){
                var json = {};
                json.keyword = $(this).find("[name='keyword']").val();
                json.platformId = $(this).find("[name='platformId']").val();
                json.filterCondition = $(this).find("[name='filterCondition']").val();
                json.filterValue = $(this).find("[name='filterValue']").val();
                jsonArray.push(json);
            })
            $(".qrtz").each(function(){
                var json = {};
                json.week = $(this).find("[name='week']").val();
                json.dayOfMonth = $(this).find("[name='dayOfMonth']").val();
                json.dayOfWeek = $(this).find("[name='dayOfWeek']").val();
                json.hour = $(this).find("[name='hour']").val();
                json.minute = $(this).find("[name='minute']").val();
                json.second = $(this).find("[name='second']").val();
                quartzArray.push(json);
            })
            var jsonStr = JSON.stringify(jsonArray);
            $("#jsonArray").val(jsonStr);
            $("#quartzArray").val(JSON.stringify(quartzArray));
            objForm.submit();
        }

        $(function () {
            $("#getAtr").click(function () {
                var str = '';
                str += "<tr bgcolor='f5f5f5' class='content'>";
                str += "<td width='10%'> <div align='right'>关键字 ：</div></td>";
                str += "<td width='15%'> <input name='keyword' size='30' maxlength='30'/></td>";
                str += "<td width='10%'> <div align='right'>请选择平台 ：</div></td>";
                str += "<td width='10%'>";
                str += "<select name='platformId'>";
                str += "<option value ='' selected>请选择</option>";
                str += "<c:forEach items='${pfMap}' var ='pf'>";
                str += "<option value ='${pf.key}'>${pf.value}</option>";
                str += "</c:forEach>";
                str += "</select>";
                str += "</td>";
                str += "<td width='10%'> <div align='right'>过滤条件 ：</div></td>";
                str += "<td width='10%'> <select name='filterCondition'>";
                str += "<option value ='price' selected>价格</option>";
                str += "</select>";
                str += "</td>";
                str += "<td width='10%'> <div align='right'>过滤值 ：</div></td>";
                str += "<td width='15%'> <input name='filterValue' size='30' maxlength='30'/></td>";
                str += "<td onclick='getDel(this)'><a href='#'>删除</a></td>";
                str += "</tr>";
                $("#addTr").append(str);
            });
        });

    </script>
</head>
<body>
<form action="<%=request.getContextPath()%>/task/addTask" id="taskForm" method="post">
    <input type="hidden" name="jsonArray" id="jsonArray"/>
    <input type="hidden" name="quartzArray" id="quartzArray"/>
    <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
        <tr bgcolor="6f8ac4">
            <td colspan="2" ><font color="#FFFFFF">请填写任务信息：</font></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="25%"> <div align="right">任务名称  ：</div></td>
            <td width="75%"> <input type="text" name="taskName" size="30" maxlength="30"/><font color="#FF0000">*</font></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="25%"> <div align="right">有效期  ：</div></td>
            <td width="75%">
                <input type="text" name="startTime" onclick="MyCalendar.SetDate(this)" size="20" readonly="readonly"/>
                -
                <input type="text" name="endTime" onclick="MyCalendar.SetDate(this)" size="20" readonly="readonly"/>
                <font color="#FF0000">*</font>
            </td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="25%"> <div align="right">任务执行频率  ：</div></td>
            <td width="75%">
                <input type="radio"  name ="everyWhat"  value ="monthly" onclick ="changeStatus(this.value)" />每月
                <input type="radio"  name ="everyWhat"  value ="weekly"  onclick ="changeStatus(this.value)" />每周
                <input type="radio"  name ="everyWhat"  value ="dayly"   onclick ="changeStatus(this.value)" />每天
            </td >
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="25%"> <div align="right">任务执行时间  ：</div></td>
            <td width="75%" class="qrtz">
                <div style="display:none" id="monthly_1" class="timeDiv">
                    选择第几个星期  ：
                    <select name="week">
                        <option value="1"> 一</option>
                        <option value="2"> 二</option>
                        <option value="3"> 三</option>
                        <option value="4"> 四</option>
                    </select>
                    <select name="dayOfMonth">
                        <option value="1"> 星期日</option>
                        <option value="2"> 星期一</option>
                        <option value="3"> 星期二</option>
                        <option value="4"> 星期三</option>
                        <option value="5"> 星期四</option>
                        <option value="6"> 星期五</option>
                        <option value="7"> 星期六</option>
                    </select>
                </div>
                <div style="display:none" id="weekly_1" class="timeDiv">
                    选择每周星期几执行  ：
                    <select name="dayOfWeek">
                        <option value="1"> 星期日</option>
                        <option value="2"> 星期一</option>
                        <option value="3"> 星期二</option>
                        <option value="4"> 星期三</option>
                        <option value="5"> 星期四</option>
                        <option value="6"> 星期五</option>
                        <option value="7"> 星期六</option>
                    </select>
                </div>
                <div style="display:block">
                    <select name="hour" styleId="amHours">
                        <option value="1"> 1</option>
                        <option value="2"> 2</option>
                        <option value="3"> 3</option>
                        <option value="4"> 4</option>
                        <option value="5"> 5</option>
                        <option value="6"> 6</option>
                        <option value="7"> 7</option>
                        <option value="8"> 8</option>
                        <option value="9"> 9</option>
                        <option value="10"> 10</option>
                        <option value="11"> 11</option>
                        <option value="12"> 12</option>
                        <option value="13"> 13</option>
                        <option value="14"> 14</option>
                        <option value="15"> 15</option>
                        <option value="16"> 16</option>
                        <option value="17"> 17</option>
                        <option value="18"> 18</option>
                        <option value="19"> 19</option>
                        <option value="20"> 20</option>
                        <option value="21"> 21</option>
                        <option value="22"> 22</option>
                        <option value="23"> 23</option>
                        <option value="0"> 0</option>
                    </select> 点
                    <input type="text" name="minute" style="width:20px;" value="0" onchange="valTime(this.value)"/>
                    分
                    <input type="text" name="second" style="width:20px;" value="0" onchange="valTime(this.value)"/>
                    秒（0-59之间的整数）
                </div>
            </td>
        </tr>

        <tr bgcolor="f5f5f5">
            <td width="25%"> <div align="right">任务执行时间  ：</div></td>
            <td width="75%" class="qrtz">
                <div style="display:none" id="monthly_2" class="timeDiv">
                    选择第几个星期  ：
                    <select name="week">
                        <option value="1"> 一</option>
                        <option value="2"> 二</option>
                        <option value="3"> 三</option>
                        <option value="4"> 四</option>
                    </select>
                    <select name="dayOfMonth">
                        <option value="1"> 星期日</option>
                        <option value="2"> 星期一</option>
                        <option value="3"> 星期二</option>
                        <option value="4"> 星期三</option>
                        <option value="5"> 星期四</option>
                        <option value="6"> 星期五</option>
                        <option value="7"> 星期六</option>
                    </select>
                </div>
                <div style="display:none" id="weekly_2" class="timeDiv">
                    选择每周星期几执行  ：
                    <select name="dayOfWeek">
                        <option value="1"> 星期日</option>
                        <option value="2"> 星期一</option>
                        <option value="3"> 星期二</option>
                        <option value="4"> 星期三</option>
                        <option value="5"> 星期四</option>
                        <option value="6"> 星期五</option>
                        <option value="7"> 星期六</option>
                    </select>
                </div>
                <div style="display:block">
                    <select name="hour" styleId="amHours">
                        <option value="1"> 1</option>
                        <option value="2"> 2</option>
                        <option value="3"> 3</option>
                        <option value="4"> 4</option>
                        <option value="5"> 5</option>
                        <option value="6"> 6</option>
                        <option value="7"> 7</option>
                        <option value="8"> 8</option>
                        <option value="9"> 9</option>
                        <option value="10"> 10</option>
                        <option value="11"> 11</option>
                        <option value="12"> 12</option>
                        <option value="13"> 13</option>
                        <option value="14"> 14</option>
                        <option value="15"> 15</option>
                        <option value="16"> 16</option>
                        <option value="17"> 17</option>
                        <option value="18"> 18</option>
                        <option value="19"> 19</option>
                        <option value="20"> 20</option>
                        <option value="21"> 21</option>
                        <option value="22"> 22</option>
                        <option value="23"> 23</option>
                        <option value="0"> 0</option>
                    </select> 点
                    <input type="text" name="minute" style="width:20px;" value="0" onchange="valTime(this.value)"/>
                    分
                    <input type="text" name="second" style="width:20px;" value="0" onchange="valTime(this.value)"/>
                    秒（0-59之间的整数）
                </div>
            </td>
        </tr>

        <tr bgcolor="f5f5f5">
            <td width="25%"> <div align="right">是否邮件预警  ：</div></td>
            <td width="75%">
                <input type="checkbox" name="sendMail" size="20"/>
            </td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td width="25%"> <div align="right">e-mail  ：</div></td>
            <td width="75%">
                <input type="text" name="email"  size="40"/>
            </td>
        </tr>
        <tr bgcolor="6f8ac4">
            <td colspan="2" ><font color="#FFFFFF">请填写任务内容：</font></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <table align="center" id="addTr">
                <tr bgcolor="f5f5f5" class="content">
                    <td width="10%"> <div align="right">关键字 ：</div></td>
                    <td width="15%"> <input name="keyword" size="30" maxlength="30"/></td>
                    <td width="10%"> <div align="right">请选择平台 ：</div></td>
                    <td width="10%">
                        <select name="platformId">
                            <option value ="" selected>请选择</option>
                            <c:forEach items="${pfMap}" var ="pf">
                                <option value ="${pf.key}">${pf.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td width="10%"> <div align="right">过滤条件 ：</div></td>
                    <td width="10%"> <select name="filterCondition">
                        <option value ="price" selected>价格</option>
                    </select>
                    </td>
                    <td width="10%"> <div align="right">过滤值 ：</div></td>
                    <td width="15%"> <input name="filterValue" size="30" maxlength="30"/></td>
                </tr>
            </table>
        </tr>
        <tr>
            <td align="center"><a href="#" id="getAtr">追加内容</a></td>
        </tr>
        <tr bgcolor="f5f5f5">
            <td colspan="2"> <div align="center">
                <input type="button" name="Add" value=" 确 认 " class="frm_btn" onClick="javascript:sureSubmit(this.form)">
                &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
            </div></td>
        </tr>
    </table>
</form>
</body>
</html>
