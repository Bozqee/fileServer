<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文件上传</title>
    <link type="text/css" rel="stylesheet" href="js/jquery-easyui/themes/default/easyui.css"/>
    <link type="text/css" rel="stylesheet" href="js/jquery-easyui/themes/icon.css"/>
    <script src="js/jquery-easyui/jquery.min.js"></script>
    <script src="js/jquery-easyui/uuid.js"></script>
    <script src="js/jquery-easyui/jquery.easyui.min.js"></script>
    <script src="js/jquery-easyui/jsrsasign-all-min.js"></script>
    <script src="js/jquery-easyui/plugins/jquery.cookie.js"></script>
    <script src="js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
    <script>
        $(function () {
            if ($.cookie('X-SID') === undefined) {
                $.cookie('X-SID',uuid(32,16))
            }
            if ($.cookie('priKey') === undefined) {
                var priKey = "MIIBVwIBADANBgkqhkiG9w0BAQEFAASCAUEwggE9AgEAAkEAkfe4YSBi/dwerNgu1fQyUiNZFthe4/8Fdyr2vtUMRM+7X6VAEmFbpSVtpLARoNajFmdesz5zi3SEHcaX3U8PMwIDAQABAkEAiWiHO3d/eLbEcbW4sVSGImiAL09UVJD3liztxstMF2F7tseFuV69cS0YR/bw+tuesSJWT5vHSiIS83u8tOOdMQIhAOo4MYs4+5OaphM8N3dFN4nK1YCs5/h5F9AEy3Hs/G9/AiEAn4qcpLhy0viegGvo/iNNQ5V6BKgtPTpO1vJwAYdBek0CIQCTUGy46EozeF1kU8d/GOXpoM3QdPAh8+fqSlm7ehb7+QIhAJjjaIGiEMeEYcCHqNwCUIS3thrIX7IRMoRiCFwuldzxAiEAzcXgPMqciKGW1FYAZFaBQ0lROx/m3Ezhwwv6rGoH/FQ="
                $.cookie('priKey',priKey);
                console.log($.cookie('priKey'));
            }
            $('#auth').bind('click', function(){
                $.ajax({
                    url:"/fileServer/auth/login",
                    type:"GET",
                    headers: {
                        'X-SID' : $.cookie('X-SID'),
                        'priKey': $.cookie('priKey')
                    },
                    success:function(data,textState,xhr) {
                        var sign = xhr.getResponseHeader("X-Signature")
                        // var priKey = xhr.getResponseHeader("privateKey")
                        $.cookie('X-Signature',sign)
                        // $.cookie('priKey',priKey);
                        alert("授权成功");
                    }
                });
            });

            $('#btn').bind('click', function(){
                // $('#ff').form('submit', {
                //     url: '/fileServer/file/upload',
                //     headers: {
                //         'X-SID' : $.cookie('X-SID'),
                //         'X-Signature' : $.cookie('X-Signature')
                //     },
                //     onSubmit: function(){
                //
                //     },
                //     success: function(data){
                //         var data = eval('(' + data + ')');
                //         window.location.href='/fileServer/download.jsp?uuid='+data;
                //     },
                //     error: function (response) {
                //         alert(response)
                //     }
                // });
                var file = $("input[name='file']").val();
                if (file == "") {
                    alert("请选择上传的目标文件! ")
                    return false;
                }
                var type = "file";
                var formData = new FormData();//这里需要实例化一个FormData来进行文件上传
                var uploadFile = document.getElementById("file").files[0];
                formData.append(type,uploadFile);
                $.ajax({
                    type : "post",
                    url : "/fileServer/file/upload",
                    data : formData,
                    processData : false,
                    contentType : false,
                    headers: {
                        'X-SID' : $.cookie('X-SID'),
                        'X-Signature' : $.cookie('X-Signature')
                    },
                    success : function(data){
                        var data = eval('(' + data + ')');
                        window.location.href='/fileServer/download.jsp?uuid='+data;
                    },
                    error: function (response) {
                        alert("请先授权")
                    }
                });
            });

        });




    </script>
</head>
<body>
    <a id="auth" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">点击授权</a>
    <form  id="ff" method="post" enctype="multipart/form-data">
        <input type="file" id="file" name="file" style="width:300px" ><br><br>
        <a id="btn" class="easyui-linkbutton" data-options="iconCls:'icon-save'">点击上传</a>
    </form>


<%--    <a id="download">点击下载</a>--%>

<%--    <table id="dg"></table>--%>

</body>
</html>
