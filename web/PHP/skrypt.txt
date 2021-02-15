var ilosc_pol_zalacznikow = 1;
const MAX_ZALACZNIKOW = 10;

function onload() {
   zaktualizuj_date_wpisu();
   zaktualizuj_godzine_wpisu();
}

function zaktualizuj_date_wpisu(){
   var pole_data_wpisu = document.getElementById('data_wpisu');
   var aktualna_data = new Date();
   var dzien = aktualna_data.getDate();
   if(dzien < 10) {
      dzien = '0' + dzien;
   }
   var miesiac = aktualna_data.getMonth()+1;
   if(miesiac < 10) {
      miesiac = '0' + miesiac;
   }
   var rok = aktualna_data.getFullYear();
   var aktualna_data_w_formularzu = rok + '-' + miesiac + '-' + dzien;
   pole_data_wpisu.value = aktualna_data_w_formularzu;
}

function zweryfikuj_poprawnosc_daty() {
   var pole_data_wpisu = document.getElementById('data_wpisu').value;
   var regexp_daty = /^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/gi;
   if (!regexp_daty.test(pole_data_wpisu)) {
      zaktualizuj_date_wpisu();
   }
}

function zaktualizuj_godzine_wpisu() {
   var pole_godzina_wpisu = document.getElementById('godzina_wpisu');
   var aktualna_data = new Date();
   var godziny = aktualna_data.getHours();
   var minuty = aktualna_data.getMinutes();
   if(minuty < 10){
      minuty = '0' + minuty;
   }
   pole_godzina_wpisu.value = godziny + ":" + minuty;
}

function zweryfikuj_poprawnosc_godziny() {
   var pole_godzina_wpisu = document.getElementById('godzina_wpisu').value;
   var regexp_godziny = /^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/gi;
   if (!regexp_godziny.test(pole_godzina_wpisu)) {
      zaktualizuj_godzine_wpisu();
   }
}

function stworzNowyPrzyciskZalacznika() {
   if (ilosc_pol_zalacznikow < MAX_ZALACZNIKOW){
      ilosc_pol_zalacznikow++;

      var nowyPrzycisk = document.createElement('input');
      nowyPrzycisk.setAttribute("type", "file");
      nowyPrzycisk.setAttribute("name", "zalacznik" + ilosc_pol_zalacznikow);
      nowyPrzycisk.setAttribute("onclick", "stworzNowyPrzyciskZalacznika()");

      var nowa_linia = document.createElement('br');

      var pole_zalacznikow = document.getElementById("dodawanie_zalacznikow");
      pole_zalacznikow.appendChild(nowyPrzycisk);
      pole_zalacznikow.appendChild(nowa_linia);
   }
}

function stworzStyle() {
   var style = document.getElementsByTagName("link");
   var iloscStyli = style.length;
   console.log(iloscStyli);

   var spisTresci = document.getElementById("miejsce_na_style");

   for (i = 0 ; i < iloscStyli ; i++) {
      var aktualnyElement = document.createElement('a');
      var nazwaStylu = style[i].title;

      aktualnyElement.innerHTML = nazwaStylu;
      aktualnyElement.setAttribute("onclick", "wybierzStyl(\"" + nazwaStylu + "\")");

      console.log("AA");
      spisTresci.appendChild(aktualnyElement);
      spisTresci.appendChild(document.createElement('br'));
   }


}

function wybierzStyl(nazwaStylu) {
   var listaStyli = document.getElementsByTagName("link");
   var iloscStyli = listaStyli.length;
	for (var i = 0 ; i < iloscStyli ; i++) {
      var styl = listaStyli[i];
      if (styl.getAttribute("title") == nazwaStylu) {
         styl.disabled = false;
         console.log(styl.getAttribute("title") + " enabled");
      } else {
         styl.disabled = true;
         console.log(styl.getAttribute("title") + " disabled");
      }
	}

   ustawCiasteczko("style", nazwaStylu, 365);
}

function sprawdzCiasteczko() {
   var ciasteczko = wczytajCiasteczko("style");
   wybierzStyl(ciasteczko);
}

function ustawCiasteczko(nazwa, styl, dni) {
   var data = new Date();
   data.setTime(data.getTime() + (dni * 24 * 60 * 60 * 1000));
   var wygasa = "expires=" + data.toUTCString();
   document.cookie = nazwa + "=" + styl + ";" + wygasa + "; path=/";
 }

function wczytajCiasteczko(nazwa) {
   var styl = "";
   var nazwa = nazwa + "=";
   var ciasteczko = decodeURIComponent(document.cookie);
   var ciasteczko_tablica = ciasteczko.split(';');
   for (var i = 0 ; i < ciasteczko_tablica.length ; i++) {
      var fragment = ciasteczko_tablica[i];
      while (fragment.charAt(0) == ' ') {
         fragment = fragment.substring(1);
      }
      if (fragment.indexOf(nazwa) == 0) {
         styl = fragment.substring(nazwa.length, fragment.length);
         break;
      }
   }
   return styl;
}

/*
TIP: document.getElementsByTagName("link").length;
document.getElementsByTagName("link")[0];
document.selectedStyleSheetSet="print.css"

pętla do długości tablicy i dodawać elementy z onclickiem, żeby odpowiedni styl się odpalił

ciasteczka: było na wykładzie
tworzysz zmienną która będzie pamiętała wartość w ciasteczku

AJAX:
użyj long pool
serwer odpowiada dopiero gdy coś się pojawi (zamiast zasypywać pustymi zapytaniami)

 Uwaga: ilość komunikatów przechowywana na serwerze musi być ograniczona. - np. do 10ciu, które będą pamiętane w pliku

przykład jest gotowy na wykładzie

checkbox do uruchomienia łączności
*/
