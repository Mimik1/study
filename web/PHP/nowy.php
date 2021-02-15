<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="pl" lang="pl">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
<?php
    $nazwaBloga = $_POST['nazwaBloga'];
    $nazwaUzytkownika = $_POST['nazwaUzytkownika'];
    $haslo = $_POST['haslo'];
    $opis = $_POST['opisBloga'];

    include 'menu.php';

    if (!file_exists($nazwaBloga)) {
      mkdir($nazwaBloga, 0755, true);

      $sciezka_pliku_txt = $nazwaBloga . "/info.txt";
      $plik = fopen($sciezka_pliku_txt, 'w');

      if (flock($plik, LOCK_EX)) {
         fputs($plik, $nazwaUzytkownika . "\n");
         fputs($plik, md5($haslo) . "\n");
         fputs($plik, $opis);
         echo "Blog utworzony <br />";
      }

      flock($plik, LOCK_UN);
      fclose($plik);

   } else {
      echo "Katalog zajety!<br/>";
   }
?>
</body>
</html>
