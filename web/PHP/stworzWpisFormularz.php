<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pl" lang="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>Dodaj nowy wpis na bloga!</title>

	<script type="text/javascript" src="skrypt.js">
	</script>

</head>
<body onload="onload()">
	<?php include 'menu.php';?>

	<div id="miejsce_na_style">

	</div>

	<form action="wpis.php" method="POST" enctype="multipart/form-data">
		<h1>Kreator nowego wpisu na blogu</h1>

		  	Podaj nazwę użytkownika:
        		<input type="text" name="nazwaUzytkownika"><br />
			Podaj swoje hasło:
       		<input type="password" name="haslo"><br />
        	Podaj treść wpisu:<br />
        		<textarea name="trescWpisu" rows="15" cols="80"></textarea><br />
        	Podaj datę:
        		<input id="data_wpisu" type="text" name="data" onchange="zweryfikuj_poprawnosc_daty()"><br />
        	Podaj godzinę:
        		<input id="godzina_wpisu" type="text" name="godzina" onchange="zweryfikuj_poprawnosc_godziny()"><br />
				<br />
        	Załącz pliki: <br />
			<div id="dodawanie_zalacznikow">
	        <input type="file" name="zalacznik1" onclick="stworzNowyPrzyciskZalacznika()"><br />
			  <!---
			  przycisk i pojawiało się coś nowego
			  po wybraniu jednego pliku pojawia się nowy przycisk do kliknięcia pliku

			  wykorzystaj onClick
			  (nie trzeba sprawdzać czy aby na pewno się wybralo dany plik, wystarczy samo klinięcie)

			  można przyjąć limit plików np. 10 i zrobić pętle po stronie PHP żeby sprawdzała
			  te 10 plików czy są
			  --->
		  </div>
		  	  <br />
			  <input type="reset" value="Wyczyść" name="wyczysc" />
			  <input type="submit" value="Stwórz wpis!">
	</form>
</body>
</html>
