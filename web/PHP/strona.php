<?php
// sprawdź co zwróci fopen
header('Content-type: text/plain');
$plik = fopen($_GET['id'], 'r');
while (!feof($plik)) {
  $s = fgets($plik);
  echo $s;
}
fclose($plik);
?>
