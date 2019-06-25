<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<body>
	<form action="http://localhost:8091/fileDemo" method="post" 
		enctype="multipart/form-data"
	>
	
		<input name="image" type="file" />
		<input type="submit" value="确定"  />
	</form>
</body>
</html>