<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pl" lang="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>Stwórz swojego bloga!</title>

	<script type="text/javascript" src="skrypt.js">
	</script>
</head>
<body>
	<?php	include 'menu.php'; ?>

	<form action="nowy.php" method="POST">
        <h1>Kreator nowego bloga</h1>

	  		Podaj nazwę bloga:
        		<input type="text" name="nazwaBloga"><br />
        	Podaj nazwę użytkownika:
        		<input type="text" name="nazwaUzytkownika"><br />
        	Podaj swoje hasło:
        		<input type="password" name="haslo"><br />
        	Opisz o czym będzie Twój blog:<br />
        		<textarea name="opisBloga" rows="15" cols="40"></textarea><br />

	        <input type="reset" value="Wyczyść" name="wyczysc" />
			  <input type="submit" value="Załóż bloga!">
	</form>
</body>
</html>
