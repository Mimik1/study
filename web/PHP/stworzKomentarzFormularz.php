<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pl" lang="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>Dodaj nowy komentarz na bloga!</title>

	<script type="text/javascript" src="skrypt.js">
	</script>
</head>
<body onload="onload()">
	<?php	include 'menu.php'; ?>

	<form action="koment.php" method="POST" enctype="multipart/form-data">
      <h1>Kreator nowego komentarza na blogu!</h1>

         Wybierz wpis, który chcesz skomentować:
           <select name="wpisDoSkomentowania"><br />
              <?php
					  if (isset($_GET['wybranyKomentarz'])) {
						  $wybranyKomentarz = $_GET['wybranyKomentarz'];
					  } else {
						  $wybranyKomentarz = "";
					  }

	              $katalog = new RecursiveDirectoryIterator('.');
	              foreach (new RecursiveIteratorIterator($katalog) as $sciezkaDoPliku => $plik) {
	                 if (! ($plik->isDir())) {
	                   if (preg_match("/\d{16}$/", $plik)) {
								 echo "test: " . $wybranyKomentarz . "<>" . basename($plik) . "<br />";
								 if (rtrim($wybranyKomentarz) == rtrim($plik)) {
									 echo "<option selected>" . basename($plik) . "</option>";
								 } else {
	                      	echo "<option>" . basename($plik) . "</option>";
							 	 }
	                   }
	                }
	             }
             ?>
          </select><br />

        Podaj swoją nazwę użytkownika:
            <input type="text" name="nazwaUzytkownika"><br />
        Podaj treść komentarza:<br />
            <textarea name="trescKomentarza" rows="10" cols="40"></textarea><br />
        Wybierz typ komentarza:
            <select name="typKomentarza">
              <option>Pozytywny</option>
              <option>Neutralny</option>
              <option>Negatywny</option>
            </select><br />
        <input type="reset" value="Wyczyść" name="wyczysc" />
        <input type="submit" value="Dodaj komentarz!">
	</form>
</body>
</html>
