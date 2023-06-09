<!-- Update result said by teacher -->

<!DOCTYPE html>
<html>

<head>
	<title>Update Student</title>

	<link type="text/css" rel="stylesheet" href="css/style.css">
	<link type="text/css" rel="stylesheet" href="css/add-student-style.css">	
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>Northeastern University</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Update Result</h3>
		
		<form action="ResultControllerServlet" method="GET">
		
			<input type="hidden" name="command" value="UPDATE" />
			<input type="hidden" name="eval_type" value="${result.eval_type}" />
			<input type="hidden" name="eval_name" value="${result.eval_name}" />
			
			<table>
				<tbody>
					
					<tr>
						<td><label>Evaluation Type:</label></td>
						<td>${result.eval_type}</td>
					</tr>
					<tr>
						<td><label>Evaluation Name:</label></td>
						<td>${result.eval_name}</td>
					</tr>

					<tr>
						<td><label>Score:</label></td>
						<td><input type="text" name="new_score" value="${result.score}"/></td>
					</tr>
					
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save" /></td>
					</tr>
					
				</tbody>
			</table>
		</form>
		
		<div style="clear: both;"></div>
		
		<p>
			<a href="ResultControllerServlet">Back to List</a>
		</p>
	</div>
</body>

</html>