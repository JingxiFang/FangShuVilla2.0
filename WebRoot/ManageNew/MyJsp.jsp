<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
     <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <!--external css-->
    <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap-daterangepicker/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" crossorigin="anonymous">
    <!-- Custom styles for this template -->
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/style-responsive.css" rel="stylesheet">
    <!-- fileinput 文件上传 -->
    <link href="assets/css/fileinput.css" media="all" rel="stylesheet" type="text/css"/>
	<link href="assets/themes/explorer-fas/theme.css" media="all" rel="stylesheet" type="text/css"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  
  </head>
  
  <body>
    <input id="input-700" name="kartik-input-700[]" type="file" multiple class="file-loading">
      <input id="input-701" name="kartik-input-701[]" type="file" multiple=true class="file-loading">
<input id="input-702" name="kartik-input-702[]" type="file" multiple=true class="file-loading">
   

 
    <!-- js placed at the end of the document so the pages load faster -->
    <script src="assets/js/jquery.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script class="include" type="text/javascript" src="assets/js/jquery.dcjqaccordion.2.7.js"></script>
    <script src="assets/js/jquery.scrollTo.min.js"></script>
    <script src="https://cdn.bootcss.com/jquery.nicescroll/3.7.6/jquery.nicescroll.iframehelper.js" type="text/javascript"></script>
    <script src="assets/js/common-scripts.js"></script>
    <script src="assets/js/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="assets/js/bootstrap-switch.js"></script>
	<script src="assets/js/jquery.tagsinput.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-daterangepicker/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap-daterangepicker/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
	<script type="text/javascript" src="assets/js/bootstrap-inputmask/bootstrap-inputmask.min.js"></script>
	<script src="assets/js/form-component.js"></script>    
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
    <script src="assets/js/plugins/piexif.js" type="text/javascript"></script>
    <script src="assets/js/plugins/sortable.js" type="text/javascript"></script>
    <script src="assets/js/fileinput.js" type="text/javascript"></script>
    <script src="assets/js/locales/zh.js" type="text/javascript"></script>
    <script src="assets/themes/fas/theme.js" type="text/javascript"></script>
    <script src="assets/themes/explorer-fas/theme.js" type="text/javascript"></script>
    
     <script>
        $("#input-700").fileinput({
            uploadUrl: "http://localhost/file-upload-single/1", // 服务器端上传处理程序
            uploadAsync: true,  //异步上传
            maxFileCount: 5     //最大上传文件数为5
        });
    </script>
    
  
    <script>
        $("#input-701").fileinput({
            uploadUrl: "http://localhost/file-upload-batch/1", // 服务器端上传处理程序
            uploadAsync: true,
            maxFileCount: 5
        });
</script>

 <script>
     $("#input-702").fileinput({
         uploadUrl: "http://localhost/file-upload-single/1", // 服务器端上传处理程序
         uploadAsync: true,  //异步上传
         minFileCount: 1,    //最小上传文件数： 1
         maxFileCount: 5,    //最大上传文件数： 5
         overwriteInitial: false,  //不能重载初始预览
         initialPreview: [         //初始预览数据
             "http://lorempixel.com/800/460/people/1",
             "http://lorempixel.com/800/460/people/2"
         ],
         initialPreviewAsData: true,      // 确定传入预览数据，而不是原生标记语言
         initialPreviewFileType: 'image', // 默认为`image`，可以在下面配置中被覆盖
         initialPreviewConfig: [          //初始预览配置
             {caption: "People-1.jpg", size: 576237, width: "120px", url: "/site/file-delete", key: 1},
             {caption: "People-2.jpg", size: 932882, width: "120px", url: "/site/file-delete", key: 2}, 
         ],
         uploadExtraData: {    //上传额外数据
             img_key: "1000",
             img_keywords: "happy, places",
         }
     });
 </script>
   </body>
</html>
