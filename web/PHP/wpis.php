<?php
	$nazwaUzytkownika = $_POST['nazwaUzytkownika'];
	$haslo = $_POST['haslo'];
	$tresc_wpisu = $_POST['trescWpisu'];
	$data = $_POST['data'];
	$godzina = $_POST['godzina'];


	   include 'menu.php';

	$czyZnalezionoUzytkownika = False;
	$katalog = new RecursiveDirectoryIterator('.');

	// Autentykacja
	$sciezkaDoFolderuBloga = NULL;
	foreach (new RecursiveIteratorIterator($katalog) as $sciezkaDoPliku => $plik) {
		if (! ($plik->isDir())) {
			if ($plik->getFileName() == 'info.txt') {
				$linie = file($sciezkaDoPliku);

				$nazwaUzytkownikaZPliku = $linie[0];
				$nazwaUzytkownikaZPliku = rtrim($nazwaUzytkownikaZPliku, "\r\n");
				$hasloZPliku = $linie[1];
				$hasloZPliku = rtrim($hasloZPliku, "\r\n");

				if ($nazwaUzytkownika == $nazwaUzytkownikaZPliku) {
					if (md5($haslo) == $hasloZPliku) {
						$czyZnalezionoUzytkownika = True;
						$sciezkaDoFolderuBloga = $plik->getPath();
						break;
					}
				}
			}
		}
	}
	if (!$czyZnalezionoUzytkownika) {
		echo "Nie znaleziono uzytkownika lub podano niepoprawne dane! <br/>";
	}

	// Dodawanie wpisu
	if ($czyZnalezionoUzytkownika) {
		// Wygeneruj nazwę pliku
		$dataBezDywizow = str_replace("-", "", $data);
		$godzinaBezDwukropka = str_replace(":", "", $godzina);
		$sekundy = date("s");
		$unikalnyId = 0;

		do {
			$unikalnyNumer = sprintf("%02d", $unikalnyId);
			$nazwaPliku = $dataBezDywizow . $godzinaBezDwukropka . $sekundy . $unikalnyNumer;
			$sciezkaDoPlikuWpisu = "./" . $sciezkaDoFolderuBloga . "/" . $nazwaPliku;
			$unikalnyId = $unikalnyId + 1;
		} while (file_exists($sciezkaDoPlikuWpisu));

		// Zapisz plik
		$plik = fopen($sciezkaDoPlikuWpisu, 'w');
		fputs($plik, $tresc_wpisu);
		fclose($plik);

		//Zapisz załączniki
		$zalaczniki = array();
		for ($i = 1 ; $i <= sizeof($_FILES) ; $i++) {
			$nazwa_zalacznika = 'zalacznik' . $i;
			$obecny_zalacznik = $_FILES[$nazwa_zalacznika];
			array_push($zalaczniki, $obecny_zalacznik);
		}

		$numerObecnegoZalacznika = 1;

		foreach($zalaczniki as $zalacznik) {
			$katalogDocelowy = "./" . $sciezkaDoFolderuBloga . "/";
			$rozszerzenie = pathinfo($zalacznik['name'], PATHINFO_EXTENSION);
			$plikDocelowy = $katalogDocelowy . $dataBezDywizow . $godzinaBezDwukropka . $sekundy .
			$unikalnyNumer . $numerObecnegoZalacznika . "." . $rozszerzenie;

			//Dodaj plik jeśli nie istiał już taki
			if (file_exists($plikDocelowy)) {
				echo "Plik " . $zalacznik['name'] . "juz istnieje! <br />";
			} else {
				if (move_uploaded_file($zalacznik["tmp_name"], $plikDocelowy)) {
					echo "Pomyślnie dodano plik " . $zalacznik['name'] . "<br />";
				}
			}
			$numerObecnegoZalacznika = $numerObecnegoZalacznika + 1;
		}
	}
?>
