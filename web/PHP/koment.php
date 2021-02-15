<?php
   $wpisDoSkomentowania = $_POST['wpisDoSkomentowania'];
   $nazwaUzytkownika = $_POST['nazwaUzytkownika'];
   $trescKomentarza = $_POST['trescKomentarza'];
   $typKomentarza = $_POST['typKomentarza'];

   include 'menu.php';

   // Komentarze umieszczane są w katalogu o nazwie: RRRRMMDDGGmmSSUU.k (w razie potrzeby skrypt musi tworzyć katalog automatycznie)

   // 1. znajdź gdzie leży ten plik
   $katalog = new RecursiveDirectoryIterator('.');
   $katalogBloga = NULL;
   $plikBloga = NULL;
   foreach (new RecursiveIteratorIterator($katalog) as $sciezkaDoPliku => $plik) {
      if (! ($plik->isDir())) {
       if (basename($plik) == $wpisDoSkomentowania) {
          $plikBloga = $plik;
          $katalogBloga = dirname($plik);
         }
      }
   }

   // 2. stwórz katalog jeśli nie istnieje
   if (!file_exists($plikBloga . ".k")) {
      mkdir($plikBloga . ".k", 0755, true);
   }

   // 3. wsadź tam komentarz
   $katalogZKomentarzami = $plikBloga . ".k/";

   // 3.1 Znajdź nanjniższy numer komentarza
   $indeksKomentarza = 0;
   while (file_exists($katalogZKomentarzami . "/" . $indeksKomentarza)) {
      $indeksKomentarza = $indeksKomentarza + 1;
   }

   // 3.2. Stwórz ten plik
   $sciezkaDoPlikuKomentarza = $katalogZKomentarzami . "/" . $indeksKomentarza;
   $plikKomentarza = fopen($sciezkaDoPlikuKomentarza, "w");
   fputs($plikKomentarza, $typKomentarza . "\n");
   $znacznikCzasu = date("Y-m-d H:i:s");
   fputs($plikKomentarza, $znacznikCzasu . "\n");
   fputs($plikKomentarza, $nazwaUzytkownika . "\n");
   fputs($plikKomentarza, $trescKomentarza);
   fclose($plikKomentarza);
?>
