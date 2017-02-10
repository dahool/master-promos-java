<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!doctype html>

<html lang="en">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    	
	<title>Sorpresas Event</title>
	
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/5.0.0/normalize.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.rawgit.com/Eonasdan/bootstrap-datetimepicker/e8bddc60e73c1ec2475f827be36e1957af72e2ea/build/css/bootstrap-datetimepicker.css">
	
</head>
<body>

<div class="container">
  
	<form:form method="POST">
	  <div class="form-group">
	    <label for="eventTitle">Title:</label>
	    <input type="text" class="form-control" id="eventTitle" name="eventTitle" placeholder="Title" required>
	  </div>
	  <div class="form-group">
	    <label for="eventText">Text:</label>
	    <textarea class="form-control" rows="3" id="eventText" name="eventText" required></textarea>
	  </div>
	  <div class="form-group">
	    <label for="eventExpire">URL:</label>
	    <input type="url" class="form-control" id="eventUrl" name="eventUrl" placeholder="URL" required>
	  </div>	  
	  <div class="row">
	  <div class="col-md-6">
	  <div class="form-group">
 		<label for="eventExpire">Expire Date:</label>
		<input type='text' class="form-control" id="eventExpire" name="eventExpire" required>
	  </div>
	  </div>
	  </div>
	  <button type="submit" class="btn btn-default">Save</button>
	</form:form>

</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/moment.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.45/js/bootstrap-datetimepicker.min.js"></script>

<script type="text/javascript">
    $(function () {
        $('#eventExpire').datetimepicker({
        	format: 'DD/MM/YYYY HH:mm' 
        });
    });
</script>

</body>
</html>