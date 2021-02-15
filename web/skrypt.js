function onload() {
   stworzStyle();
   sprawdzCiasteczko();
}

function stworzStyle() {
   var style = document.getElementsByTagName("link");
   var iloscStyli = style.length;
   var spisTresci = document.getElementById("miejsce_na_style");

   for (i = 0 ; i < iloscStyli ; i++) {
      var aktualnyElement = document.createElement('a');
      var nazwaStylu = style[i].title;

      aktualnyElement.innerHTML = nazwaStylu;
      aktualnyElement.setAttribute("onclick", "wybierzStyl(\"" + nazwaStylu + "\")");

      spisTresci.appendChild(aktualnyElement);
      spisTresci.appendChild(document.createElement('br'));
   }

   console.log("AA");
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

function stworzStyle() {
   var style = document.getElementsByTagName("link");
   var iloscStyli = style.length;
   var spisTresci = document.getElementById("Spis_treści");

   console.log("AA");
   for (i = 0 ; i < iloscStyli ; i++) {
      var aktualnyElement = document.createElement('a');
      var nazwaStylu = style[i].title;

      aktualnyElement.innerHTML = nazwaStylu;
      aktualnyElement.setAttribute("onclick", "wybierzStyl(\"" + nazwaStylu + "\")");

      spisTresci.appendChild(aktualnyElement);
      spisTresci.appendChild(document.createElement('br'));
   }
}
