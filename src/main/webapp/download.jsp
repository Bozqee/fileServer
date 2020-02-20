<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<%
    String uuid = (String) request.getAttribute("uuid");
    String downloadPath = "/fileServer/file/download/"+uuid;
%>
<head>
    <title>下载文件</title>
    <link type="text/css" rel="stylesheet" href="js/jquery-easyui/themes/default/easyui.css"/>
    <link type="text/css" rel="stylesheet" href="js/jquery-easyui/themes/icon.css"/>
    <script src="js/jquery-easyui/jquery.min.js"></script>
    <script src="js/jquery-easyui/plugins/jquery.cookie.js"></script>
    <script src="js/jquery-easyui/jquery.easyui.min.js"></script>
    <script src="js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>

    <script>
        $(function () {
            $('#dd').dialog({
                title: '查看',
                width: 400,
                height: 300,
                closed: true,
                modal: true
            });
            $('#dg').datagrid({
                url:'/fileServer/file/get/'+getQueryVariable('uuid'),
                method:'GET',
                columns:[[
                    {field:'uuid',title:'uuid',width:100},
                    {field:'length',title:'length',width:100},
                    {field:'price',title:'Price',width:100,align:'right'}
                ]]
            });

        })

        function getQueryVariable(variable)
        {
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
                var pair = vars[i].split("=");
                if(pair[0] == variable){return pair[1];}
            }
            return(false);
        }

        function view() {
            $.ajax({
                url:"/fileServer/file/get/"+getQueryVariable('uuid'),
                type:"GET",
                headers: {
                    'X-SID' : $.cookie('X-SID'),
                    'X-Signature' : $.cookie('X-Signature')
                },
                success:function(data) {
                    // alert(data)
                    var data = eval('(' + data + ')');
                    $('#viewForm').form('load',{
                        uuid:data.uuid,
                        length:data.length,
                        type:data.type,
                        originalName:data.originalName,
                        createTime:data.createTime,
                        path:data.path
                    });
                    $('#dd').dialog({
                        closed: false
                    });
                },
                error: function (response) {
                    alert("请先授权")
                }
            });
        }
        function downloadFile() {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', "/fileServer/file/download/"+getQueryVariable('uuid'), true);
            xhr.setRequestHeader("X-SID", $.cookie('X-SID'));
            xhr.setRequestHeader("X-Signature",$.cookie('X-Signature'))
            xhr.responseType = 'blob';
            //关键部分
            xhr.onload = function (e) {
                console.log(this.response)
                if (this.status == 200) {
                    var blob = this.response;
                    var header = xhr.getResponseHeader("Content-Disposition")
                    var filename = header.substring(header.indexOf("=")+1)
                    var a = document.createElement('a');
                    blob.type = "application/octet-stream";
                    var url = URL.createObjectURL(blob);
                    a.href = url;
                    a.download = filename;
                    a.click();
                    window.URL.revokeObjectURL(url);
                } else {
                    alert("请先授权")
                }
            };
            xhr.send();
        }
    </script>

</head>

<body>
上传成功。。。 <br><br><br>
<a class="easyui-linkbutton" onclick="downloadFile()"  data-options="iconCls:'icon-print'">点击下载</a> &nbsp;&nbsp;
<a id="viewButton" class="easyui-linkbutton"  onclick="view()" data-options="iconCls:'icon-search'">点击查看</a>
<a href="/fileServer/index.jsp" class="easyui-linkbutton">继续上传</a>

<div id="dd">
    <form id="viewForm" method="get">
        <div style="text-align: center;margin-top: 10px;">
            <label for="uuid" style="width: 20%">uuid:</label>
            <input class="easyui-textbox" style="width: 80%" type="text" name="uuid" id="uuid" readonly/>
        </div>
        <div style="text-align: center;margin-top: 10px;">
            <label for="length" style="width: 20%">文件大小:</label>
            <input class="easyui-textbox" type="text" style="width: 80%" name="length" id="length" readonly/>
        </div>
        <div style="text-align: center;margin-top: 10px;">
            <label for="type" style="width: 20%">类型:</label>
            <input class="easyui-textbox" style="width: 80%" type="text" name="type" id="type" readonly/>
        </div>
        <div style="text-align: center;margin-top: 10px;">
            <label for="originalName" style="width: 20%">原始名称:</label>
            <input class="easyui-textbox" type="text" style="width: 80%" name="originalName" id="originalName" readonly/>
        </div>
        <div style="text-align: center;margin-top: 10px;">
            <label for="createTime" style="width: 20%">创建日期:</label>
            <input class="easyui-textbox" type="text" style="width: 80%" name="createTime" id="createTime" readonly/>
        </div>
        <div style="text-align: center;margin-top: 10px;">
            <label for="path" style="width: 20%">存储路径:</label>
            <input class="easyui-textbox" style="width: 80%" type="text" name="path" id="path" readonly/>
        </div>

    </form>
</div>
</body>
</html>
